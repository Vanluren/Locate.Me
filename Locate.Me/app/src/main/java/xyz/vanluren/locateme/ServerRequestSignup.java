package xyz.vanluren.locateme;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class ServerRequestSignup extends StringRequest{
    //TODO private static final String REQUEST_URL = "";
    private final HashMap params;

    public ServerRequestSignup(String name, String email, String password, String lat, String lng, String mac_address, Response.Listener<String> listener) {

        super(Request.Method.POST, REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("name", name);
        params.put("email", email);
        params.put("password", password);
        params.put("lat", lat);
        params.put("lng", lng);
        params.put("mac_address", mac_address);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
