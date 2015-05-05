package dev.filterme;

import android.graphics.Bitmap;

public class MotionBlur extends Millora {

    protected int[][] blurMatrix = new int[][] {
            { 1, 0, 0 ,0, 0, 0, 0, 0, 0},
            { 0, 1, 0 ,0, 0, 0, 0, 0, 0},
            { 0, 0, 1 ,0, 0, 0, 0, 0, 0},
            { 0, 0, 0 ,1, 0, 0, 0, 0, 0},
            { 0, 0, 0 ,0, 1, 0, 0, 0, 0},
            { 0, 0, 0 ,0, 0, 1, 0, 0, 0},
            { 0, 0, 0 ,0, 0, 0, 1, 0, 0},
            { 0, 0, 0 ,0, 0, 0, 0, 1, 0},
            { 0, 0, 0 ,0, 0, 0, 0, 0, 1},
    };


    public MotionBlur(Bitmap img){
        super(img);
        SIZE = 9;
        kernel = blurMatrix;
        div = 9;
        offset = 0;
    }

}
