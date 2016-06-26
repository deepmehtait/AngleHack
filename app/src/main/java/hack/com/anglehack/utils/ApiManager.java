package hack.com.anglehack.utils;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import hack.com.anglehack.constants.Constants;
import hack.com.anglehack.controllers.AppController;

/**
 * Created by david_000 on 6/26/2016.
 */
public class ApiManager {

    public ApiManager() {
    }

    /** Fetch a Striiv Data from Amazon Kinesis. */
    public void fetchStriiv() {

    }

    public void fetchNutrition(String foodItem) {
        String nutritionUrl = Constants.NUTRITION + foodItem;
        System.out.println(foodItem);
        GetRequest request = new GetRequest(null, null, nutritionUrl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                System.out.println(jsonObject);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println(volleyError);
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }

    public void fetchDoctors() {

    }

}
