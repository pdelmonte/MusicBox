package org.bts.android.musicbox;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.List;

public class PlayerActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = PlayerActivity.class.getSimpleName();
    private PlayService playService;
    private boolean mIsBound;
    List<Item> listItem = new ArrayList<Item>();


    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder serviceInfo) {
            PlayService.LocalBinder binder = (PlayService.LocalBinder) serviceInfo;
            playService = binder.getService();
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
        final RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.song_list_view);

        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnPrev.setOnClickListener(this);
        seekBar.setClickable(false);


        //for (int idx = 0; idx < listItem.size(); idx++) {
        //    listItem.add(new Item("", "");
        //}

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(new MyListAdapterRecycler(this, (ArrayList<Item>) listItem));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent PlayServiceIntent = new Intent(this, PlayService.class);
        bindService(PlayServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
      //
    }

    @Override
    public void onClick(View whichView) {

        switch(whichView.getId()) {
            case R.id.play_btn:
                if (playService.getMediaPlayer().isPlaying()){
                    playService.getMediaPlayer().pause();
                } else {
                    this.playService.getMediaPlayer().start();
                }

                Log.i(PlayerActivity.TAG, "Play/Pause");
                break;

            case R.id.stop_btn:
                this.playService.getMediaPlayer().stop();
                this.playService.getMediaPlayer().prepareAsync();
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

    @Override
    protected void onDestroy() {

        super.onDestroy();

        if(isFinishing()) {
            unbindService(this.mServiceConnection);
        } else {

        }

    }
}
