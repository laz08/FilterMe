package dev.filterme;


import android.graphics.Bitmap;

public abstract class Artistic extends Filtre {

    protected int[] matFoto;
    protected int[] matFotoResultat;
    protected int w;
    protected int h;

    public Artistic(Bitmap bmp){
        super(bmp);
    }


    /**
     * Prepara les dues matrius, i carrega la imatge a MatFoto.
     * Perpara la matriu resultant.
     */
    protected void llegeixBitmapAMatriu(){
        w = imatge.getWidth();
        h = imatge.getHeight();
        matFoto = new int[h*w];
        matFotoResultat = new int[h*w];

        //Carreguem matriu de la foto (matFoto). Intent de ser més eficient
        imatge.getPixels(matFoto, 0, w, 0, 0, w, h);
    }

    //Escriu resultat a Matriu
    protected Bitmap escriuMatriuResABitmap(){
        Bitmap result = Bitmap.createBitmap(w, h, imatge.getConfig());
        result.setPixels(matFotoResultat, 0, w, 0, 0, w, h);
        return result;
    }

}
