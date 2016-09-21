package xyz.vanluren.locateme;

import com.android.volley.Request;
import com.android.volley.Response;

import java.util.Map;


public class ServerRequest {

    private static final String REQUEST_URL = "localhost:3000/users";
    private Map<String, String> requestParams;

    public ServerRequest(String url, String name, String password, Response.Listener<String> stringListener) {
        super(Request.Method.POST, REQUEST_URL, listener, null);
    }

    //TODO: Implementer JSONOBJECT metoden
    //TODO: Implementer Post, Get, Put, Delete
}