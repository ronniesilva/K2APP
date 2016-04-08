package com.app.k2app.pojo;

public class PojoLogin {

    private Integer id;
    private Integer emailOK;
    private String nmExibicao;
    private String userFoto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmailOK() {
        return emailOK;
    }

    public void setEmailOK(Integer emailOK) {
        this.emailOK = emailOK;
    }

    public String getNmExibicao() {
        return nmExibicao;
    }

    public void setNmExibicao(String nmExibicao) {
        this.nmExibicao = nmExibicao;
    }

    public String getUserFoto() {
        return userFoto;
    }

    public void setUserFoto(String userFoto) {
        this.userFoto = userFoto;
    }
}
