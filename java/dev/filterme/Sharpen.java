package dev.filterme;

import android.graphics.Bitmap;

public class Sharpen extends Millora {

    protected int[][] blurMatrix = new int[][] {
            { 0, -2, 0},
            { -2, 11, -2 },
            { 0, -2, 0 }
    };


    public Sharpen(Bitmap img){
        super(img);
        kernel = blurMatrix;
        div = 3;
        offset = 0;
    }

}
