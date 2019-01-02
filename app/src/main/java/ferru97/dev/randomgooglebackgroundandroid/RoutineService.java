package ferru97.dev.randomgooglebackgroundandroid;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class RoutineService extends Service {

    //wait time
    public static long INTERVAL = 600000; // 10 min default
    // run on another Thread to avoid crash
    private Handler mHandler = new Handler();
    // timer handling
    private Timer timer = null;
    //Service status
    public static boolean isRunning = false;
    //Notification
    public static NotificationCompat.Builder mBuilder;
    //Notification manager
    public static NotificationManagerCompat notificationManager;
    //Notification ID
    public static final int nID = 1;



    public RoutineService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onCreate() {
        INTERVAL = MainActivity.uSettings.getDelay() * 60000;
        // cancel if already existed
        if(timer != null)
            timer.cancel();
        else
            timer = new Timer();
        isRunning = true;

        mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Keyword Text")
                .setContentText("Next background in..")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setOngoing(true);
        startForeground(nID, mBuilder.build());

        // schedule task
        timer.scheduleAtFixedRate(new DelayTesk(), 0, INTERVAL);
    }


    @Override
    public void onDestroy(){
        isRunning = false;
        //notificationManager.cancel(nID);
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
