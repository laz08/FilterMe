package dev.filterme;

import android.graphics.Bitmap;

public class FindEdges extends Millora {

    protected int[][] Matrix = new int[][] {
            { 0, 0, 0 ,0, 0},
            { 0, 0, 0 ,0, 0},
            { -1, -1, 2 ,0, 0},
            { 0, 0, 0 ,0, 0},
            { 0, 0, 0 ,0, 0},
    };


    public FindEdges(Bitmap img){
        super(img);
        SIZE = 5;
        kernel = Matrix;
        div = 1;
        offset = 0;
    }

}
