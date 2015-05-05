package dev.filterme;


import android.graphics.Bitmap;
import android.graphics.Color;

public class Negative extends Artistic {

    public Negative(Bitmap bmp){
        super(bmp);
    }

    private void negatiu(){
        for(int i = 0; i < matFoto.length; ++i){
            int pixel = matFoto[i];
            int r = 255-Color.red(pixel);
            int g = 255-(Color.green(pixel));
            int b = 255-(Color.blue(pixel));
            matFotoResultat[i] = Color.argb(255, r, g, b);
        }
    }
    @Override
    Bitmap aplicaFiltre(){
        llegeixBitmapAMatriu();
        negatiu();
        return escriuMatriuResABitmap();
    }
}
