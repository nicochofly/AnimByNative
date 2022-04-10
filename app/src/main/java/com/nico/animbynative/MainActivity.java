package com.nico.animbynative;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.File;

public class MainActivity extends AppCompatActivity implements AssertReleaseTask.ReleaseCallback {
    private SurfaceView mSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setupView();
    }


    private void setupView() {
        mSurfaceView = new SurfaceView(this);
        setContentView(mSurfaceView);
        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                PngLoader nativeImageLoader = new PngLoader();
                File file = new File(getExternalFilesDir(null), "input.png");
                nativeImageLoader.loadPNGImage(file.getAbsolutePath(), holder.getSurface());
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }
//    @Override
//    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        AssertReleaseTask task = new AssertReleaseTask(this, "input.png", this);
//        AsyncTaskCompat.executeParallel(task);
//    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        AssertReleaseTask task = new AssertReleaseTask(this, "input.png", this);
//        AsyncTaskCompat.executeParallel(task);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void onReleaseComplete() {

    }
}