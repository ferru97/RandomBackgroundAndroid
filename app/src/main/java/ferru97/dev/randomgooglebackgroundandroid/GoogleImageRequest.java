package ferru97.dev.randomgooglebackgroundandroid;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class GoogleImageRequest {

    //Custm Search Engine ID
    public static String CSE_ID = "015383445286146004163:s5brxbups1i";
    //Google Project API KEY
    public static String API_KEY = "AIzaSyCVomv1sG6XEJOVOt9yLWb6syL2aZgaauE" ;
    //search keywords
    private static String keyword = "naruto smartphone wallpaper";
    //App context
    private static  Context AppContext;



    public static void BackgroundRequest(Context context)
    {
        AppContext = context;
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
            downloadImage(link);

        }catch (JSONException e){Log.e("JSON ERROR",e.toString());}

    }


    private static void downloadImage(String link){

        RequestQueue queue = Volley.newRequestQueue(AppContext);
        ImageRequest request = new ImageRequest(link, new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        setBackground(bitmap);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ErrorImageDownload", error.toString());
                    }
        });
        queue.add(request);

    }


    private static void setBackground(Bitmap bitmap){

        WindowManager wm = (WindowManager) AppContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        Bitmap fixedBitmap = returnBitmap(bitmap, width, height);

        WallpaperManager myWallpaperManager = WallpaperManager.getInstance(AppContext);
        try {
            myWallpaperManager.setBitmap(fixedBitmap);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }


    private static Bitmap returnBitmap(Bitmap originalImage, int width, int height){
        Bitmap background = Bitmap.createBitmap((int)width, (int)height, Bitmap.Config.ARGB_8888);

        float originalWidth = originalImage.getWidth();
        float originalHeight = originalImage.getHeight();

        Canvas canvas = new Canvas(background);

        float scale = width / originalWidth;

        float xTranslation = 0.0f;
        float yTranslation = (height - originalHeight * scale) / 2.0f;

        Matrix transformation = new Matrix();
        transformation.postTranslate(xTranslation, yTranslation);
        transformation.preScale(scale, scale);

        Paint paint = new Paint();
        paint.setFilterBitmap(true);

        canvas.drawBitmap(originalImage, transformation, paint);

        return background;
    }

}
