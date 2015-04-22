package dev.filterme;

import android.graphics.Bitmap;

public class EdgeDetection extends Millora {

    protected int[][] blurMatrix = new int[][] {
            { 1, 1, 1},
            { 0, 0, 0 },
            { -1, -1, -1 }
    };


    public EdgeDetection(Bitmap img){
        super(img);
        kernel = blurMatrix;
        div = 1;
        offset = 127;
    }

}
