package org.bts.android.musicbox;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by pedrodelmonte on 13/03/17.
 */

public class PlayService extends Service {

    private static final String TAG = PlayService.class.getSimpleName();
    private IBinder mBinder = new LocalBinder();
    private MediaPlayer mediaPlayer;

    public String SongList[] = {"bensoundbrazilsamba.mp3", "bensoundcountryboy.mp3", "bensoundindia.mp3", "bensoundlittleplanet.mp3", "bensoundpsychedelic.mp3", "bensoundrelaxing.mp3", "bensoundtheelevatorbossanova.mp3" };

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
        this.mediaPlayer = MediaPlayer.create(this, R.raw.bensoundrelaxing);
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
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


}
