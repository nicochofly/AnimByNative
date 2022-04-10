package com.nico.animbynative;

import android.view.Surface;

public class PngLoader {

    static {
        System.loadLibrary("native-png");
    }

    public native void loadPNGImage(String imagePath, Surface surface);
}
