package dev.filterme;

import android.graphics.Bitmap;

public class Emboss extends Millora {

    protected int[][] blurMatrix = new int[][] {
            { -1, -1, -1},
            { -1, 8, -1 },
            { -1, -1, -1 }
    };


    public Emboss(Bitmap img){
        super(img);
        kernel = blurMatrix;
        div = 1;
        offset = 127;
    }

}
