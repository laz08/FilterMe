package dev.filterme;

import android.graphics.Bitmap;

public class EdgeDetection extends Millora {

    protected int[][] Matrix = new int[][] {
            { 1, 1, 1},
            { 0, 0, 0 },
            { -1, -1, -1 }
    };


    public EdgeDetection(Bitmap img){
        super(img);
        kernel = Matrix;
        div = 1;
        offset = 127;
    }

}
