package com.example.pokeat.datamodels;
import org.json.JSONException;
import org.json.JSONObject;

public class Restaurant {

    private String nome, indirizzo, numTelefono, imageUrl, id;
    private Float importoMin;
    public static final String ENDPOINT = "restaurants";

    public Restaurant(JSONObject jsonRestaurant) throws JSONException {
        nome = jsonRestaurant.getString("name");
        indirizzo = jsonRestaurant.getString("address");
        importoMin = (float)jsonRestaurant.getDouble("min_order");
        imageUrl = jsonRestaurant.getString("image_url");
        numTelefono = jsonRestaurant.getString("phone_number");
        id = jsonRestaurant.getString("id");
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public float getImportoMin() {
        return importoMin;
    }

    public void setImportoMin(float importoMin) {
        this.importoMin = importoMin;
    }

}
