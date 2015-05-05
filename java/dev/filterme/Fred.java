package dev.filterme;


import android.graphics.Bitmap;
import android.graphics.Color;

public class Fred extends Artistic {

    public Fred(Bitmap bmp){
        super(bmp);
    }

    /**
     * Per a cada píxel, fem un 30% RED + 59% GREEN + 11% BLUE
     */
    private void canviaColor(){
        for(int i = 0; i < matFoto.length; ++i){
            int pixel = matFoto[i];
            int r = Color.red(pixel);
            int g = (Color.green(pixel));
            int b = (int)(Color.blue(pixel) * 1.3);
            if (r < 0) r = 0;
            else if (r > 255) r = 255;

            if (g < 0) g = 0;
            else if (g > 255) g = 255;

            if (b < 0) b = 0;
            else if (b > 255) b = 255;
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
