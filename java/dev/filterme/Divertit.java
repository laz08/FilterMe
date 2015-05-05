package dev.filterme;


import android.graphics.Bitmap;
import android.graphics.Color;

public class Divertit extends Artistic {

    public Divertit(Bitmap bmp){
        super(bmp);
    }

    /**
     * Per a cada píxel, fem un 30% RED + 59% GREEN + 11% BLUE
     */
    private void canviaColor(){
        for(int i = 0; i < matFoto.length; ++i){
            int pixel = matFoto[i];
            int r = (int)(Color.red(pixel) * 1.3);
            int g = (int)(Color.green(pixel) * 1.3);
            int b = (int)(Color.blue(pixel)*1.3);
            matFotoResultat[i] = Color.argb(255, r, g, b);
        }
    }
    @Override
    Bitmap aplicaFiltre(){
        llegeixBitmapAMatriu();
        canviaColor();
        return escriuMatriuResABitmap();
    }
}
