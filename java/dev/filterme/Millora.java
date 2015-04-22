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
        //Carreguem matriu de la foto (matFoto). Intent de ser més eficient
        imatge.getPixels(matFoto, 0, w, 0, 0, w, h);

        //Apliquem el filtre
        //Respecte tota la imatge
        for(int i = 0; i < h-2; ++i){
            int acces = i*w;
            for(int j = 0; j < w-2; ++j){

                R = G = B = 0.0;
                //multiply every value of the filter with corresponding image pixel
                //Size is the size of the filter matrix
                for(int x = 0; x < SIZE; x++) {
                    int acces2 = (i+x)*w;
                    for (int y = 0; y < SIZE; y++) {
                        /**
                         * Accés a vector com si fos una kernel:
                         * i*amplada_matriu + j
                         * En el nostre cas....:
                         * (i+x)*w + (j+y)
                         */

                        int pixel_tmp = matFoto[acces2 + j + y];
                        //Apliquem filtre per valor de la kernel corresponent

                        double val = kernel[x][y];
                        //red += image[imageX][imageY].r * filter[filterX][filterY];
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



                // Antic
                //result.setPixel(i, j, Color.argb(255, r, g, b));
                int col = Color.argb(255, r, g, b);
                matFotoResultat[acces + j +1] = col;

                /*
                if()
                    System.out.println("Pixel x: " + i + "\n Pixel y: " + j);
                    */
            }
        }


        result.setPixels(matFotoResultat, 0, w, 0, 0, w, h);
        return result;
    }
}
