package org.bts.android.musicbox;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class PlayerActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static final String TAG = PlayerActivity.class.getSimpleName();
    private PlayService playService;
    private boolean mIsBound;
    private static ArrayList<Item> listItem = new ArrayList<Item>();

    public static List<Item> getListItem() {
        return listItem;
    }



    private ServiceConnection mServiceConnection = new ServiceConnection() {

        //Setting up and instanciating the Service and binding it
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

        //Setting up buttons and listview objects on the main player activity

        final Button btnStart = (Button) findViewById(R.id.play_btn);
        final Button btnStop = (Button) findViewById(R.id.stop_btn);
        final Button btnNext = (Button) findViewById(R.id.next_btn);
        final Button btnPrev = (Button) findViewById(R.id.prev_btn);


        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnPrev.setOnClickListener(this);


        //Creating an Array with music files inside the Raw Folder

        int[] rawValues = {
                R.raw.bensoundbrazilsamba,
                R.raw.bensoundcountryboy,
                R.raw.bensoundindia,
                R.raw.bensoundlittleplanet,
                R.raw.bensoundpsychedelic,
                R.raw.bensoundrelaxing,
                R.raw.bensoundtheelevatorbossanova
        };

        //Looping the array created above to create an ArrayList to populate the ListView object in the activity

        for (int idx = 0; idx < rawValues.length; idx++) {
            listItem.add(new Item("", this.getResources().getResourceEntryName(rawValues[idx]) ,rawValues[idx]));
        }

        final ListView mListView = (ListView) this.findViewById(R.id.song_list_view);
        mListView.setAdapter(new MyListAdapter(this, 0, listItem));
        mListView.setOnItemClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        //On Start calls the service to be ready to play songs
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
        //Setting up buttons functionality

        switch(whichView.getId()) {
            case R.id.play_btn:
                this.playService.startOrPausePlayer();
                Log.i(PlayerActivity.TAG, "Play/Pause");
                break;

            case R.id.stop_btn:
                this.playService.stopPlayer();
                Log.i(PlayerActivity.TAG, "Stop");
                break;

            case R.id.next_btn:
                this.playService.playNext();
                Log.i(PlayerActivity.TAG, "Next");
                break;

            case R.id.prev_btn:
                this.playService.playPrev();
                Log.i(PlayerActivity.TAG, "Prev");
                break;

            default:
                Log.i(PlayerActivity.TAG,"Clicked Item not registered");
        }

    }

    @Override
    protected void onDestroy() {

        //Clears the ArrayList before destroying in order to avoid duplicate objects in it in case of orientation changes on the device
        listItem.clear();

        super.onDestroy();

        //Making sure that Service is unbound only in case if the App is terminating

        if(isFinishing()) {
            unbindService(this.mServiceConnection);
        } else {

        }

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Toast.makeText(this, "Element " + position + ", with ID = " + id,Toast.LENGTH_SHORT).show();
        //Setting up ListView item selection functionality

        if(mIsBound) {
            this.playService.playableTrackViewClicked(position);
        }

    }
}
