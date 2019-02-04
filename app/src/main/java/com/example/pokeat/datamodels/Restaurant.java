package com.example.pokeat.datamodels;

import android.media.Image;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

public class Restaurant {

    private String nome, indirizzo, importoMin, numTelefono;
    private int image;

    public Restaurant(String nome, String indirizzo, String importoMin, String numTelefono, int image){
        this.nome = nome;
        this.indirizzo = "Indirizzo: " + indirizzo;
        this.importoMin = "Importo minimo: " + importoMin;
        this.numTelefono = "Telefono: " + numTelefono;
        this.image = image;
    }

    public String getNumTelefono() {
        return numTelefono;
    }

    public void setNumTelefono(String numTelefono) {
        this.numTelefono = numTelefono;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getImportoMin() {
        return importoMin;
    }

    public void setImportoMin(String importoMin) {
        this.importoMin = importoMin;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
