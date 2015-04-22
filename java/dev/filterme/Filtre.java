package dev.filterme;


import android.graphics.Bitmap;

public abstract class Filtre {

    protected Bitmap imatge;

    public Filtre(Bitmap img){
        imatge = img;
    }

    abstract Bitmap aplicaFiltre();

}
