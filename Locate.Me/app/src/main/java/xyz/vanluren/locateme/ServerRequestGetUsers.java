package xyz.vanluren.locateme;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;


/**
 * Created by villadsvalur on 22/09/2016.
 */

public class ServerRequestGetUsers extends StringRequest{

    //TODO private static final String REQUEST_URL = "";
    public ServerRequestGetUsers(Response.Listener<String> listener) {
        super(Method.GET, REQUEST_URL, listener, null);
    }

}
