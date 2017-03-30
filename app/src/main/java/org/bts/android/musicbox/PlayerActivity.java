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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static final String TAG = PlayerActivity.class.getSimpleName();
    private PlayService playService;
    private boolean mIsBound;
    private static ArrayList<Item> listItem = new ArrayList<Item>();
    public ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();



    public static List<Item> getListItem() {
        return listItem;
    }



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

        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnPrev.setOnClickListener(this);
        seekBar.setClickable(false);


        int[] rawValues = {
                R.raw.bensoundbrazilsamba,
                R.raw.bensoundcountryboy,
                R.raw.bensoundindia,
                R.raw.bensoundlittleplanet,
                R.raw.bensoundpsychedelic,
                R.raw.bensoundrelaxing,
                R.raw.bensoundtheelevatorbossanova
        };

        for (int idx = 0; idx < rawValues.length; idx++) {
            listItem.add(new Item("", "Title" ,rawValues[idx]));
        }

        final ListView mListView = (ListView) this.findViewById(R.id.song_list_view);
        mListView.setAdapter(new MyListAdapter(this, 0, listItem));
        mListView.setOnItemClickListener(this);

        ArrayList<HashMap<String, String>> songsListData = new ArrayList<HashMap<String, String>>();

        //new attempt starts here
        SongsManager songManager = new SongsManager();
        // get all songs from sdcard
        this.songsList = songManager.getPlayList();

        // looping through playlist
        for (int i = 0; i < songsList.size(); i++) {
            // creating new HashMap
            HashMap<String, String> song = songsList.get(i);
            // adding HashList to ArrayList
            songsListData.add(song);
            Log.i(PlayerActivity.TAG, ""+ songsListData.get(i));
        }


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
                Log.i(PlayerActivity.TAG, "Stop");
                break;

            case R.id.next_btn:
                this.playService.getMediaPlayer().stop();
                Log.i(PlayerActivity.TAG, "Index: "+playService.getMusicIndex());
                this.playService.getMediaPlayer().prepareAsync();
                int i = this.playService.getMusicIndex();
                this.playService.setMusicIndex(i+1);
                Log.i(PlayerActivity.TAG, "Index: "+playService.getMusicIndex());
                //this.playService.getMediaPlayer().selectTrack(playService.getMusicIndex());
                //this.playService.getMediaPlayer().create(playService, playService.getPlaylist().get(playService.getMusicIndex()));
                this.playService.getMediaPlayer().start();
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
        listItem.clear();
        super.onDestroy();

        if(isFinishing()) {
            unbindService(this.mServiceConnection);
        } else {

        }

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "Element " + position + ", with ID = " + id,Toast.LENGTH_SHORT).show();
        if (playService.getMediaPlayer().isPlaying()){
//            playService.getMediaPlayer().stop();
//            playService.getMediaPlayer().prepareAsync();
            this.playService.getMediaPlayer().reset();
            this.playService.getMediaPlayer().create(this, listItem.get(position).getmTrackId()).start();
//            this.playService.getMediaPlayer().setDataSource(listItem.get(position).getPath());
        } else {
            this.playService.getMediaPlayer().create(this, listItem.get(position).getmTrackId()).start();
//            this.playService.getMediaPlayer().reset();
//            this.playService.setMusicIndex(position);
            this.playService.getMediaPlayer().start();
        }
    }
}
