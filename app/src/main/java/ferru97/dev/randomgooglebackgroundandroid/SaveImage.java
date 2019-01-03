package ferru97.dev.randomgooglebackgroundandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveImage {

    private static final String directoryName = "RandBack";


    public static void save(String name, Context context){
        Bitmap image  = GoogleImageRequest.currBackground;

        if(image!=null){
            FileOutputStream fileOutputStream = null;
            try{
                File f = createFile(name);
                fileOutputStream = new FileOutputStream(f);
                image.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.close();
                Toast.makeText(context, "Background Saved!", Toast.LENGTH_LONG).show();

                MediaScannerConnection.scanFile(context, new String[]{f.toString()}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            public void onScanCompleted(String path, Uri uri) {
                                Log.i("ExternalStorage", "Scanned " + path + ":");
                                Log.i("ExternalStorage", "-> uri=" + uri);
                            }
                        });
            }catch (FileNotFoundException e){
                Log.e("File Error",e.toString());
            }catch (IOException e){
                Log.e("File Error",e.toString());
            }
        }

    }

    private static File createFile(String name){

        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File myDir = new File(root,directoryName);
        myDir.mkdirs();

        return new File(myDir,name+".png");

    }

}
