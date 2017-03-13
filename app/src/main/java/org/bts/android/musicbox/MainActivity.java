package org.bts.android.musicbox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(MainActivity.TAG, "In-onCreate()");

        final Button btnStartActivity = (Button) findViewById(R.id.btn_main_activity_start_activity);
        btnStartActivity.setOnClickListener(this);
    }

    @Override
    public void onClick(View whichView) {

        if (whichView.getId() == R.id.btn_main_activity_start_activity) {

            Log.e(MainActivity.TAG, "Button 1 Clicked");
            Intent activityIntent = new Intent(this, PlayerActivity.class);
            startActivityForResult(activityIntent, 0);

        }
    }
}
