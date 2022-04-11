package com.nico.animbynative;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;

public class MainActivity extends AppCompatActivity implements AssertReleaseTask.ReleaseCallback {
    private SurfaceView mSurfaceView = null;
    int pos = 0;
    String[] pngs = {"frame0.png", "frame1.png",
            "frame2.png",
            "frame3.png",
            "frame4.png",
            "frame5.png",
            "frame6.png",
            "frame7.png",
            "frame8.png",
            "frame9.png",
            "frame10.png",
            "frame11.png",
            "frame12.png",
            "frame13.png",
            "frame14.png",
            "frame15.png",
            "frame16.png",
            "frame17.png",
            "frame18.png",
            "frame19.png"};
    private boolean isPlaying = false;
    private Surface surface = null;
    private PngLoader pngLoader = null;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                if (pos >= pngs.length) {
                    pos = 0;
                }
                String path = getExternalFilesDir(null).getAbsolutePath() + File.separator + pngs[pos];
                pngLoader.loadPNGImage(path, surface);
                pos += 1;
            }


            Message message = obtainMessage();
            message.what = 1;
            message.obj = surface;
            sendMessageDelayed(message, 30);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pngLoader = new PngLoader();
        mSurfaceView = new SurfaceView(this);
        ViewGroup.LayoutParams parms = new ViewGroup.LayoutParams(1024, 1024);
        mSurfaceView.setLayoutParams(parms);
        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                surface = holder.getSurface();
                Message msg = mHandler.obtainMessage();
                msg.what = 1;
                msg.obj = surface;
                mHandler.sendMessageDelayed(msg, 1000);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
        mSurfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    isPlaying = false;
                    mHandler.removeCallbacksAndMessages(null);
                } else {
                    Message msg = mHandler.obtainMessage();
                    msg.what= 1;
                    msg.obj = surface;
                    mHandler.sendMessageDelayed(msg, 100);
                }
            }
        });
        setContentView(mSurfaceView);
    }




    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        AssertReleaseTask task = new AssertReleaseTask(this, pngs, this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void onReleaseComplete() {

    }
}