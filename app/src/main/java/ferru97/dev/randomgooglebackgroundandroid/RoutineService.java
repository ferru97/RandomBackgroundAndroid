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
    public static final long INTERVAL = 10 * 1000; // 10 seconds
    // run on another Thread to avoid crash
    private Handler mHandler = new Handler();
    // timer handling
    private Timer timer = null;


    public RoutineService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onCreate() {
        // cancel if already existed
        if(timer != null)
            timer.cancel();
        else
            timer = new Timer();

        // schedule task
        timer.scheduleAtFixedRate(new DelayTesk(), 0, INTERVAL);
    }



    class DelayTesk extends TimerTask {

        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {

                @Override
                public void run() {
                    // display toast
                    Toast.makeText(getApplicationContext(), "TEST", Toast.LENGTH_SHORT).show();
                }

            });
        }

    }

}
