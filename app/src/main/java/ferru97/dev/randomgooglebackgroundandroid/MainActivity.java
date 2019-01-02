package ferru97.dev.randomgooglebackgroundandroid;

import android.app.Service;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static UserSettings uSettings;
    private EditText CSE_ID;
    private EditText keyword;
    private EditText delay;
    private Switch OnOff;
    private Intent routineService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
