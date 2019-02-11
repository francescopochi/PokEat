package com.example.pokeat.datamodels;

public class Product {

    private String nome;
    private float prezzo;
    private int quantita;

    public Product(String nome, float prezzo) {
        this.nome = nome;
        this.prezzo = prezzo;
        this.quantita = 0;
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

    public void increaseQuantity(){
        this.quantita++;
    }
    public void decreaseQuantity(){
        if(quantita == 0) return;
        this.quantita--;
    }

    public float getSubtotal(){
        return quantita * prezzo;
    }

}
