package com.app.k2app.pojo;

public class PojoInReachItem {

    private int id;
    private String nome;
    private String imageUrl;
    private String dist;
    private int qtdPhotos;

    // GETTERS AND SETTERS

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public int getQtdPhotos() {
        return qtdPhotos;
    }

    public void setQtdPhotos(int qtdPhotos) {
        this.qtdPhotos = qtdPhotos;
    }
}
