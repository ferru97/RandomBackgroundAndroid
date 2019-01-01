package ferru97.dev.randomgooglebackgroundandroid;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleImageRequest {

    //Custm Search Engine ID
    public static String CSE_ID = "015383445286146004163:s5brxbups1i";
    //Google Project API KEY
    public static String API_KEY = "AIzaSyCVomv1sG6XEJOVOt9yLWb6syL2aZgaauE" ;
    //search keywords
    private static String keyword = "naruto smartphone wallpaper";



    public static void BackgroundRequest(Context context)
    {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://www.googleapis.com/customsearch/v1?key="+API_KEY+"&q="+keyword+"&cx="+CSE_ID+"&searchType=image";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Log the first 500 characters of the response string.
                        decodeJson(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("REQUEST","ERROR");
            }
        });

    // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


    private static void decodeJson(String respnse){

        try{
            JSONObject obj = new JSONObject(respnse);
            JSONArray items = new JSONArray(obj.getString("items"));

            int random = (int)(Math.random() * 9 + 0);
            JSONObject image = new JSONObject(items.getString(random));
            String link = image.getString("link");

        }catch (JSONException e){Log.e("JSON ERROR",e.toString());}

    }

}
