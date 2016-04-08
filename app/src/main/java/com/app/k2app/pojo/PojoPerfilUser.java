package com.app.k2app.pojo;

import java.util.ArrayList;

public class PojoPerfilUser {

    private Integer id;
    private String email;
    private String nascimento;
    private Integer sexo;
    private Integer oriSexual;
    private String nmExibicao;
    private String msgStatus;
    private String tel;
    private String nmFoto;
    private String lang;
    private ArrayList<PojoPerfilUserPhotos> arrayFotos;


    //GETTERS AND SETTERS

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public Integer getSexo() {
        return sexo;
    }

    public void setSexo(Integer sexo) {
        this.sexo = sexo;
    }

    public Integer getOriSexual() {
        return oriSexual;
    }

    public void setOriSexual(Integer oriSexual) {
        this.oriSexual = oriSexual;
    }

    public String getNmExibicao() {
        return nmExibicao;
    }

    public void setNmExibicao(String nmExibicao) {
        this.nmExibicao = nmExibicao;
    }

    public String getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(String msgStatus) {
        this.msgStatus = msgStatus;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getNmFoto() {
        return nmFoto;
    }

    public void setNmFoto(String nmFoto) {
        this.nmFoto = nmFoto;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public ArrayList<PojoPerfilUserPhotos> getArrayFotos() {
        return arrayFotos;
    }

    public void setArrayFotos(ArrayList<PojoPerfilUserPhotos> arrayFotos) {
        this.arrayFotos = arrayFotos;
    }
}