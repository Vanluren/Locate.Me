package xyz.vanluren.locateme;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static xyz.vanluren.locateme.R.id.map;

//Bla Bla jeg checker lige branching
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    protected GoogleApiClient mGoogleApiClient;
    protected  Location mLastLocation;
    protected Marker mCurrLocationMarker;
    protected LocationRequest mLocationRequest;
    private static final String TAG = MapsActivity.class.getSimpleName();
    private String USER_EMAIL;
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


    //Create Activity'en
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Bundle extras = getIntent().getExtras();
        USER_EMAIL = extras.getString("Email");


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

        //Beder brugeren om at tænde for bluetooth, hvis det ikke allerede er det.

        if(bluetoothAdapter == null){
            Toast.makeText(getApplicationContext(), "The bluetoothadapter is null",Toast.LENGTH_SHORT).show();
        }else{
            if(!bluetoothAdapter.isEnabled()){
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(turnOn, 1);

            Intent becomeVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                startActivity(becomeVisible);

            Toast.makeText(getApplicationContext(), "You are now discoverable",Toast.LENGTH_SHORT).show();
            }else {

                Intent becomeVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                startActivity(becomeVisible);
                Toast.makeText(getApplicationContext(), "You are discoverable", Toast.LENGTH_SHORT).show();

                if (BluetoothReciever.discoveredUser != null) {
                    BluetoothOtherUserNotification.notify(getApplicationContext(), BluetoothOtherUserNotification.build);
                }
            }
        }


    }

    @Override
    protected void onPause(){
        super.onPause();
        try {
            //Check om API Clienten er åben
            if(mGoogleApiClient.isConnected()){
                //Fjern updates, og luk API Clienten
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
                mGoogleApiClient.disconnect();
                Log.d(TAG, "The API client has been disconnected");
            }

        }catch (Exception e){}

    }


    //Tegner map og bestemmer hvornår vi kan lave API kald
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Chek om android os er M
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Check for permissions i runtime
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Connect til API'en
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }

        }

        //Connect selvom det ikke er Android M
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }


        Intent discoverableIntent = new
                Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
        startActivity(discoverableIntent);
    }

    // GoogleApiClient, er et object der giver os lov til at lave kald til Google API'et
    protected synchronized void buildGoogleApiClient() {

        //API Client bliver bygget
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        //Gad vide hvilken exception den kan kaste??
        //TODO: find ud af hvilken exception der kan kastes her.
        try {
            if(!mGoogleApiClient.isConnected()){
                mGoogleApiClient.connect();
                Log.d(TAG, "The API Client has been connected");
            }
            else{
                Log.d(TAG, "Somthing went wrong, your API Client has not been connected");
            }
        }catch (Exception e){

        }

    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);//sætter intervallet hvorved der bliver requestet nye koordinater til 1000 ms
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);//Hvis vi skal være lidt gode ved vore brugeres batteri tid, skal der balanceres mellem power og accuracy...

        //Runtime check om appen har lov til at bruge LocationServices
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        //Lol, log alt, D står for Debug
        Log.d(TAG, "Location services has been suspended. Please reconnect.");
    }

    //Håndterer *SJOVT NOK* hvis der sker en ændring i brugerens location
    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Placer en marker der hvor vores user er.
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        final double lat = location.getLatitude();
        final double lng = location.getLongitude();

        String stringLat = String.valueOf(lat);
        String stringLng = String.valueOf(lng);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("You Are Here");
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //Lad "kameraet" kigge på vores marker
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(8));

        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }


        final Response.Listener<String> responseListenerGetUsers = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                JSONArray jsonResponse= null;
                try {
                    jsonResponse = new JSONArray(response);
                    Log.d(TAG, "The server has responded with at json array of users");

                    for(int i = 0; i<jsonResponse.length(); i++){
                        JSONObject jsonObject = jsonResponse.getJSONObject(i);
                        Double otherUserLat = Double.parseDouble(jsonObject.get("lat").toString());
                        Double otherUserLng = Double.parseDouble(jsonObject.get("lng").toString());
                        String otherUserName = jsonObject.get("name").toString();
                        LatLng otherUserLatLng = new LatLng(otherUserLat, otherUserLng);



                        MarkerOptions otherUserMarkerOptions = new MarkerOptions();
                        otherUserMarkerOptions.position(otherUserLatLng);
                        otherUserMarkerOptions.title(otherUserName);
                        Marker otherUserMarker = mMap.addMarker(otherUserMarkerOptions);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        };




        Response.Listener<String> responseListenerUpdate = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject jsonResponse = null;
                try {
                    jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");


                    if (!success) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                        builder.setMessage("Location update failed")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };


        ServerRequestUpdate updateLocationRequest = new ServerRequestUpdate(USER_EMAIL, String.valueOf(lat), String.valueOf(lng),  responseListenerUpdate);
        ServerRequestGetUsers getOtherUserLocations = new ServerRequestGetUsers(responseListenerGetUsers);
        RequestQueue queue = Volley.newRequestQueue(MapsActivity.this);
        queue.add(updateLocationRequest);
        queue.add(getOtherUserLocations);


    }




    //Vi bør nok overvejer at håndtere at forbindelsen til locationListeneren ikke connecter.
    //Men det gider jeg faktisk ikke lige nu
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }



    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    //Metoden der håndterer et tjek for om man har de rigtige permissions.
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    //Metoden der håndterer det resultat der må være fra et permission tjek
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

}
