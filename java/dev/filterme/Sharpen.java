package dev.filterme;

import android.graphics.Bitmap;

public class Sharpen extends Millora {

    protected int[][] Matrix = new int[][] {
            { 0, -2, 0},
            { -2, 11, -2 },
            { 0, -2, 0 }
    };


    public Sharpen(Bitmap img){
        super(img);
        kernel = Matrix;
        div = 3;
        offset = 0;
    }

}
