package xyz.vanluren.locateme;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by villadsvalur on 22/09/2016.
 */

public class ServerRequestSignup extends StringRequest{
    private static final String REQUEST_URL = "http://localhost/users";
    private final HashMap params;

    public ServerRequestSignup(String name, String email, String password, int lat, int lng, Response.Listener<String> listener) {

        super(Request.Method.POST, REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("name", name);
        params.put("email", email);
        params.put("password", password);
        params.put("lat", lat);
        params.put("lat", lng);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
