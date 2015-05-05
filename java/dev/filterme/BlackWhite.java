package dev.filterme;


import android.graphics.Bitmap;
import android.graphics.Color;

public class BlackWhite extends Artistic {

    public BlackWhite(Bitmap bmp){
        super(bmp);
    }

    /**
     * Per a cada píxel, donem valor 255 o 0
     */
    private void blancNegre(){
        for(int i = 0; i < matFoto.length; ++i){
            int pixel = matFoto[i];
            int nouCol;
            if (Color.red(pixel) < 128){
                nouCol = 0;
            }
            else nouCol = 255;
            matFotoResultat[i] = Color.argb(255, nouCol, nouCol, nouCol);
        }
    }
    
    @Override
    Bitmap aplicaFiltre(){
        llegeixBitmapAMatriu();
        blancNegre();
        return escriuMatriuResABitmap();
    }
}
