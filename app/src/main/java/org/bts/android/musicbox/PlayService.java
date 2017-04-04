package org.bts.android.musicbox;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
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

public class PlayService extends Service implements MediaPlayer.OnPreparedListener {

    private static final String TAG = PlayService.class.getSimpleName();
    private IBinder mBinder = new LocalBinder();
    private MediaPlayer mediaPlayer;
    Timer timer;
    ArrayList<Integer> playlist;
    public int musicIndex = 0;


    public class LocalBinder extends Binder{
        PlayService getService(){ return PlayService.this;}
    }

    //Define Basic Constructors for Service implementation
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

        //Creating ArrayList with songs to add to Media Player using raw files while using and integer to index positions
        playlist = new ArrayList<>();
        playlist.add(R.raw.bensoundbrazilsamba);
        playlist.add(R.raw.bensoundcountryboy);
        playlist.add(R.raw.bensoundindia);
        playlist.add(R.raw.bensoundlittleplanet);
        playlist.add(R.raw.bensoundpsychedelic);
        playlist.add(R.raw.bensoundrelaxing);
        playlist.add(R.raw.bensoundtheelevatorbossanova);

        this.mediaPlayer = new MediaPlayer();
        this.mediaPlayer.setOnPreparedListener(this);
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

    public int getMusicIndex() {

        return musicIndex;
    }

    public void setMusicIndex(int musicIndex) {

        this.musicIndex = musicIndex;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.d(PlayService.TAG, "In-onPrepared");

        this.mediaPlayer.start();

    }

    public void startOrPausePlayer() {
        if (this.mediaPlayer.isPlaying()) {
            this.pausePlayer();
            Log.i(PlayService.TAG,"Pause");
        } else {
            this.startPlayer(this.musicIndex);
            Log.i(PlayService.TAG,"Play");
        }
    }

    private void startPlayer(int songIdx) {
        this.mediaPlayer.reset();
        try {
            this.mediaPlayer.setDataSource(this,
                    Uri.parse("android.resource://"
                            + getPackageName()
                            + "/raw/"
                            + playlist.get(songIdx))
            );
            this.musicIndex = songIdx;
            this.mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pausePlayer() {
        this.mediaPlayer.pause();
    }

    public void stopPlayer() {
        this.mediaPlayer.stop();
    }

    public void playableTrackViewClicked(int position) {
        Log.d(PlayService.TAG, "In-playableTrackViewClicked");

        if (this.mediaPlayer != null && this.mediaPlayer.isPlaying()) {
            this.mediaPlayer.stop();
        }
        Log.i(PlayService.TAG,"Starting Music " + position);
        this.startPlayer(position);
    }

    public void playNext() {
        mediaPlayer.reset();
        if (playlist.size() > musicIndex + 1) {
            setMusicIndex(++musicIndex);

            try {
                this.mediaPlayer.setDataSource(this,
                        Uri.parse("android.resource://"
                                + getPackageName()
                                + "/raw/"
                                + playlist.get(musicIndex))
                );

                this.mediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            setMusicIndex(0);
        }
    }

    public void playPrev() {
        mediaPlayer.reset();
        if (musicIndex != 0) {
            setMusicIndex(--musicIndex);

            try {
                this.mediaPlayer.setDataSource(this,
                        Uri.parse("android.resource://"
                                + getPackageName()
                                + "/raw/"
                                + playlist.get(musicIndex))
                );

                this.mediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            setMusicIndex(0);
        }
    }

}
