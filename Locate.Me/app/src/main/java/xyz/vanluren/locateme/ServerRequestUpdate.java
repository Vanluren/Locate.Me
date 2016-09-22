package xyz.vanluren.locateme;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by villadsvalur on 22/09/2016.
 */

public class ServerRequestUpdate extends StringRequest {

    private static final String REQUEST_URL = "http://10.0.2.2:3000/users";
    private final HashMap params;

    public ServerRequestUpdate(String id, double lat, double lng, Response.Listener<String> listener) {

        super(Method.PUT, REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("id", id);
        params.put("lat", lat);
        params.put("lng", lng);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
