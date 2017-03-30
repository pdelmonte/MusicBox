package org.bts.android.musicbox;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by pedrodelmonte on 13/03/17.
 */

public class PlayService extends Service {

    private static final String TAG = PlayService.class.getSimpleName();
    private IBinder mBinder = new LocalBinder();
    private MediaPlayer mediaPlayer;
    Timer timer;
    ArrayList<Integer> playlist;
    public int musicIndex = 0;


    public class LocalBinder extends Binder{
        PlayService getService(){ return PlayService.this;}
    }

    public PlayService () {
        Log.i(PlayService.TAG, "Empty Constructor");
    }

    public PlayService (IBinder serviceInfo) {
        Log.i(PlayService.TAG, "Not empty constructor");
        this.mBinder = serviceInfo;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(PlayService.TAG, "On Create");

//        ArrayList<Item> playlist2 = (ArrayList<Item>) PlayerActivity.getListItem();
//        playlist2.get(musicIndex);
//
//        playlist = new ArrayList<>();
//        playlist.add(R.raw.bensoundbrazilsamba);
//        playlist.add(R.raw.bensoundcountryboy);
//        playlist.add(R.raw.bensoundindia);
//        playlist.add(R.raw.bensoundlittleplanet);
//        playlist.add(R.raw.bensoundpsychedelic);
//        playlist.add(R.raw.bensoundrelaxing);
//        playlist.add(R.raw.bensoundtheelevatorbossanova);

        mediaPlayer = MediaPlayer.create(this,playlist.get(musicIndex));

        mediaPlayer.setLooping(true);

        timer = new Timer();

    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public ArrayList<Integer> getPlaylist() {
        return playlist;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.w(PlayService.TAG, "Bind");
        return this.mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.w(PlayService.TAG, "UnBind");
        return super.onUnbind(intent);
    }

    public void playNext() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mediaPlayer.reset();
                mediaPlayer = MediaPlayer.create(PlayService.this,playlist.get(++musicIndex));
                mediaPlayer.start();
                if (playlist.size() > musicIndex+1) {
                    playNext();
                }
            }
        },mediaPlayer.getDuration()+100);
    }

    public int getMusicIndex() {
        return musicIndex;
    }

    public void setMusicIndex(int musicIndex) {
        this.musicIndex = musicIndex;
    }
}
