package com.example.pokeat.datamodels;

import org.json.JSONException;
import org.json.JSONObject;

public class Product {

    private String nome, productImgUrl;
    private float prezzo;
    private int quantita = 0;

    public Product(JSONObject jsonProduct) throws JSONException {
        nome = jsonProduct.getString("name");
        prezzo = Float.parseFloat(jsonProduct.getString("price"));
        productImgUrl = jsonProduct.getString("image_url");
    }

    public Product(String nome, float prezzo, int quantita){
        this.nome = nome;
        this.prezzo = prezzo;
        this.quantita = quantita;
    }

    public Product(String nome, float prezzo){
        this.nome = nome;
        this.prezzo = prezzo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public void increaseQuantity() {
        this.quantita++;
    }

    public void decreaseQuantity() {
        if (quantita == 0) return;
        this.quantita--;
    }

    public float getSubtotal() {
        return quantita * prezzo;
    }

}
