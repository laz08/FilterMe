package dev.filterme;


import android.graphics.Bitmap;
import android.graphics.Color;

public class Grayscale extends Artistic {

    public Grayscale(Bitmap bmp){
        super(bmp);
    }

    /**
     * Per a cada píxel, fem un 30% RED + 59% GREEN + 11% BLUE
     */
    private void blancNegre(){
        for(int i = 0; i < matFoto.length; ++i){
            int pixel = matFoto[i];
            int nouCol = (Color.red(pixel) + Color.green(pixel) + Color.blue(pixel))/3;
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
