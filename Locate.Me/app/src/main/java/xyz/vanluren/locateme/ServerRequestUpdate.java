package xyz.vanluren.locateme;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by villadsvalur on 22/09/2016.
 */

public class ServerRequestUpdate extends StringRequest {

    //TODO private static final String REQUEST_URL = "";
    private final HashMap params;

    public ServerRequestUpdate(String email, String lat, String lng, Response.Listener<String> listener) {

        super(Method.PUT, REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("email", email);
        params.put("lat", lat);
        params.put("lng", lng);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
