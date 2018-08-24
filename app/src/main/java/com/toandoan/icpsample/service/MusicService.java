package com.toandoan.icpsample.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import com.toandoan.icpsample.IMusicService;
import com.toandoan.icpsample.R;

/**
 * Created by doan.van.toan on 8/24/18.
 */

public class MusicService extends Service {
    private static final int NOTIFICATION_ID = 1;
    private Notification mNotification;

    private IMusicService.Stub mBinder = new IMusicService.Stub() {

        @Override
        public String getSongName() throws RemoteException {
            return mPlayerManager.getSongName();
        }

        @Override
        public void changeMediaStatus() throws RemoteException {
            mPlayerManager.changeMediaStatus();
        }

        @Override
        public void playSong() throws RemoteException {
            mPlayerManager.playSong();
        }

        @Override
        public void play() throws RemoteException {
            mPlayerManager.play();
        }

        @Override
        public void pause() throws RemoteException {
            mPlayerManager.pause();
        }

        @Override
        public int getCurrentDuration() throws RemoteException {
            return mPlayerManager.getCurrentDuration();
        }

        @Override
        public int getTotalDuration() throws RemoteException {
            return mPlayerManager.getTotalDuration();
        }
    };

    private MediaPlayerManager mPlayerManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mPlayerManager = new MediaPlayerManager(getApplicationContext());
        startForegroundService();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private void startForegroundService() {
        startForeground(NOTIFICATION_ID, getNotification());
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private Notification getNotification() {
        if (mNotification == null) {
            String title = "IPC Testing";
            try {
                title = mPlayerManager.getSongName();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mNotification = new Notification.Builder(this).setContentTitle(title)
                    .setContentText("IPC Testing")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .build();
        }
        return mNotification;
    }
}
