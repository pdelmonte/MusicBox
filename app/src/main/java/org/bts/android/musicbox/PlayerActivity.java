package org.bts.android.musicbox;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class PlayerActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = PlayerActivity.class.getSimpleName();
    private MediaPlayer mediaPlayer;
    private PlayService playService;
    private boolean mIsBound;

       
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder serviceInfo) {
            playService = new PlayService(serviceInfo);
            mIsBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIsBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        final Button btnStart = (Button) findViewById(R.id.play_btn);
        final Button btnStop = (Button) findViewById(R.id.stop_btn);
        final Button btnNext = (Button) findViewById(R.id.next_btn);
        final Button btnPrev = (Button) findViewById(R.id.prev_btn);
        final SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);

        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnPrev.setOnClickListener(this);

        mediaPlayer = MediaPlayer.create(this, R.raw.bensoundbrazilsamba);
        seekBar.setClickable(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(this.mServiceConnection);
    }

    @Override
    public void onClick(View whichView) {

        switch(whichView.getId()) {
            case R.id.play_btn:
                Intent PlayServiceIntent = new Intent(this, PlayService.class);
                //startService(PlayServiceIntent);
                bindService(PlayServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
                Log.i(PlayerActivity.TAG, "Play");
                break;

            case R.id.stop_btn:
                mediaPlayer.stop();
                mediaPlayer.prepareAsync();
                Log.i(PlayerActivity.TAG, "Stop");
                break;

            case R.id.next_btn:
                Log.i(PlayerActivity.TAG, "Next");
                break;

            case R.id.prev_btn:
                Log.i(PlayerActivity.TAG, "Prev");
                break;

            default:
                Log.i(PlayerActivity.TAG,"Clicked Item not registered");
        }

    }

}
