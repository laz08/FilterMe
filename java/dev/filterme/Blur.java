package dev.filterme;

import android.graphics.Bitmap;
import android.graphics.Color;

public class Blur extends Millora {

    protected int[][] blurMatrix = new int[][] {
            { 1, 2, 1},
            { 1, 4, 2 },
            { 1, 2, 1 }
    };


    public Blur(Bitmap img){
        super(img);
        kernel = blurMatrix;
        div = 16;
        offset = 0;
    }

}
