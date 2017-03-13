package org.bts.android.musicbox;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by pedrodelmonte on 13/03/17.
 */

public class PlayService extends Service {

    private static final String TAG = PlayService.class.getSimpleName();
    private IBinder mBinder = new Binder();

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
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.w(PlayService.TAG, "Bind");
        //PlayerActivity.mediaPlayer.start();
        return this.mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.w(PlayService.TAG, "UnBind");
        return super.onUnbind(intent);
    }
}
