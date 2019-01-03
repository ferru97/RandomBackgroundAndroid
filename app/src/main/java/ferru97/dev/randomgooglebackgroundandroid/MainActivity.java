package ferru97.dev.randomgooglebackgroundandroid;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static UserSettings uSettings;
    private EditText CSE_ID;
    private EditText keyword;
    private EditText delay;
    private Switch OnOff;
    private Intent routineService;
    public static int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE =1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);

        CSE_ID = (EditText) findViewById(R.id.cseid);
        keyword = (EditText) findViewById(R.id.keyword);
        delay = (EditText) findViewById(R.id.delay);
        OnOff = (Switch) findViewById(R.id.switch2);
        uSettings = new UserSettings(getApplicationContext());
        routineService = new Intent(getApplicationContext(), RoutineService.class);

        if(uSettings.checkFilExist()){
            CSE_ID.setText(uSettings.getCSE_ID());
            keyword.setText(uSettings.getkeyword());
            delay.setText(String.valueOf(uSettings.getDelay()));
        }

        OnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton cb, boolean on){
                if(on){
                    uSettings.setCSE_ID(CSE_ID.getText().toString());
                    uSettings.setkeyword(keyword.getText().toString());
                    uSettings.setDelay(Integer.valueOf(delay.getText().toString()));
                    uSettings.saveSettings(getApplicationContext());

                    if(!RoutineService.isRunning)
                        startService(routineService);
                }
                else{
                    if(RoutineService.isRunning)
                        stopService(routineService);
                }

            }
        });

    }

    public void changeNow(View v){

        GoogleImageRequest.BackgroundRequest(getApplicationContext());
    }

    public void Save(View v){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            int name = (int)(Math.random() * 9000 + 1);
            SaveImage.save(String.valueOf(name),getApplicationContext());
        }else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
        }


    }
}
