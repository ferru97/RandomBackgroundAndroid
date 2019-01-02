package ferru97.dev.randomgooglebackgroundandroid;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class RoutineService extends Service {

    //wait time
    public static long INTERVAL = 600; // 10 min default
    // run on another Thread to avoid crash
    private Handler mHandler = new Handler();
    // timer handling
    private Timer timer = null;
    //Service status
    public static boolean isRunning = false;


    public RoutineService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onCreate() {
        INTERVAL = MainActivity.uSettings.getDelay() * 60;
        // cancel if already existed
        if(timer != null)
            timer.cancel();
        else
            timer = new Timer();
        isRunning = true;

        // schedule task
        timer.scheduleAtFixedRate(new DelayTesk(), 0, INTERVAL);
    }


    @Override
    public void onDestroy(){
        isRunning = false;
    }



    class DelayTesk extends TimerTask {

        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {

                @Override
                public void run() {
                    // display toast
                    GoogleImageRequest.BackgroundRequest(getApplicationContext());
                }

            });
        }

    }

}
