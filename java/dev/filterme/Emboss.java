package dev.filterme;

import android.graphics.Bitmap;

public class Emboss extends Millora {

    protected int[][] Matrix = new int[][] {
            { -1, -1, -1},
            { -1, 8, -1 },
            { -1, -1, -1 }
    };


    public Emboss(Bitmap img){
        super(img);
        kernel = Matrix;
        div = 1;
        offset = 127;
    }

}
