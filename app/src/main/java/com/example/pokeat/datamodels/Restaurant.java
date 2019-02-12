package com.example.pokeat.datamodels;

import org.json.JSONException;
import org.json.JSONObject;

public class Restaurant {

    private String nome, indirizzo, importoMin, numTelefono, imageUrl;
    private int image;

    public Restaurant(String nome, String indirizzo, String importoMin, String numTelefono, int image){
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.importoMin = importoMin;
        this.numTelefono = numTelefono;
        this.image = image;
    }

    public Restaurant(JSONObject jsonRestaurant) throws JSONException {
        nome = jsonRestaurant.getString("name");
        indirizzo = jsonRestaurant.getString("address");
        importoMin = jsonRestaurant.getString("min_order");
        imageUrl = jsonRestaurant.getString("image_url");
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
