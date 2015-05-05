package dev.filterme;


import android.graphics.Bitmap;
import android.graphics.Color;

public class Sepia extends Artistic {

    public Sepia(Bitmap bmp){
        super(bmp);
    }

    /**
     * Per a cada píxel, donem valor 255 o 0
     */
    private void aplicaSepia(){
        for(int i = 0; i < matFoto.length; ++i){
            int pixel = matFoto[i];
            int r = Color.red(pixel);
            int g = Color.green(pixel);
            int b = Color.blue(pixel);

            // Nous colors
            int R =  (int)(r * 0.393 + g * 0.769 + b * 0.189);
            int G = (int)(r * 0.349 + g * 0.686 + b * 0.168);
            int B = (int)(r * 0.272 + g * 0.534 + b * 0.131);

            if (R < 0) R = 0;
            else if (R > 255) R = 255;

            if (G < 0) G = 0;
            else if (G > 255) G = 255;

            if (B < 0) B = 0;
            else if (B > 255) B = 255;

            matFotoResultat[i] = Color.argb(255, R, G, B);
        }
    }
    
    @Override
    Bitmap aplicaFiltre(){
        llegeixBitmapAMatriu();
        aplicaSepia();
        return escriuMatriuResABitmap();
    }
}
