package dev.filterme;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PixelFormat;

public abstract class Millora extends Filtre {


    protected int SIZE = 3; //Redefinir si cal
    protected double div;
    protected double offset;
    protected int[][] kernel;
    protected int[] matFoto;
    protected int[] matFotoResultat;

    public Millora(Bitmap img){
        super(img);
    }

    @Override
    Bitmap aplicaFiltre() {
        int w = imatge.getWidth();
        int h = imatge.getHeight();
        Bitmap result = Bitmap.createBitmap(w, h, imatge.getConfig());

        double R, G, B; //Globals, suma de tots els comp. de la imatge
        int r, g, b; //Per a cada pixel del bitmap resultant, mirem quin valor li correspon

        matFoto = new int[h*w];
        matFotoResultat = new int[h*w];
        //Carreguem matriu de la foto (matFoto). Més eficient que fer un set i get Pixel cada cop
        imatge.getPixels(matFoto, 0, w, 0, 0, w, h);

        //Apliquem el filtre
        //Respecte tota la imatge
        for(int i = 1; i < h; ++i){
            int acces = i*w;
            for(int j = 1; j < w; ++j){
                R = G = B = 0.0;
                //multipliquem cada valor del filtre amb el pixel que li correspon
                //Size is the size of the filter matrix
                for(int x = 0; x < SIZE && ((i+x) <= h); x++) {
                    int acces2 = (i-1+x)*w;
                    for (int y = 0; y < SIZE && (acces2+j+y-1 < matFoto.length-1); y++) {
                        /**
                         * Accés a vector com si fos una kernel:
                         * i*amplada_matriu + j
                         * En el nostre cas....:
                         * (i+x)*w + (j+y)
                         */

                        int pixel_tmp = matFoto[acces2 + j + y - 1];
                        //Apliquem filtre per valor de la kernel corresponent

                        double val = kernel[x][y];
                        R = R + Color.red(pixel_tmp) * val;
                        G = G + Color.green(pixel_tmp) * val;
                        B = B + Color.blue(pixel_tmp) * val;
                    }
                }

                //Calculem valors que corresponen a cada pixel
                r = (int)(R / div + offset);
                g = (int)(G / div + offset);
                b = (int)(B / div + offset);

                //Trunquem els valors
                if (r < 0) r = 0;
                else if (r > 255) r = 255;

                if (g < 0) g = 0;
                else if (g > 255) g = 255;

                if (b < 0) b = 0;
                else if (b > 255) b = 255;

                int col = Color.argb(255, r, g, b);
                matFotoResultat[acces + j] = col;
            }
        }


        result.setPixels(matFotoResultat, 0, w, 0, 0, w, h);
        return result;
    }
}
