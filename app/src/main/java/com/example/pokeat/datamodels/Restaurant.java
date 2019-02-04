package com.example.pokeat.datamodels;

public class Restaurant {

    public String nome, indirizzo, importoMin, numTelefono;

    public Restaurant(String nome, String indirizzo, String importoMin, String numTelefono){
        this.nome = nome;
        this.indirizzo = "Indirizzo: " + indirizzo;
        this.importoMin = "Importo minimo: " + importoMin;
        this.numTelefono = "Telefono: " + numTelefono;
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
}
