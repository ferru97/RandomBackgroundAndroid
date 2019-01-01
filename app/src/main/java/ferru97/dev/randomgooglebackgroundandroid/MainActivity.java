package ferru97.dev.randomgooglebackgroundandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this, RoutineService.class));
    }

    public void changeNow(View v){
        GoogleImageRequest.BackgroundRequest(getApplicationContext());
    }
}
