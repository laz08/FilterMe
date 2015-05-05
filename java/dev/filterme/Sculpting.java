package dev.filterme;

import android.graphics.Bitmap;

public class Sculpting extends Millora {

    protected int[][] Matrix = new int[][] {
            { -2, -1, 0},
            { -1, 1, 1},
            { 0, 1, 2}
    };


    public Sculpting(Bitmap img){
        super(img);
        kernel = Matrix;
        div = 1;
        offset = 0;
    }

}
