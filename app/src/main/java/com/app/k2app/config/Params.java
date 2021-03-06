package com.app.k2app.config;

/**
 *
 *  Neste arquivo de Parâmetros ficarão configurações
 *  que serão recebidas do banco de dados.
 *
 *  Atenção: Esse arquivo depois não existirá
 *
 */
public class Params {

    /* dominio localhost */
    //public static final String WsBaseDir = "/ws";

    /* dominio Openshift */
    public static final String WsBaseDir = "";

    public static final String WsDirPhotoPerfil = "/images/perfil";
    public static final String WsDirPostImage = "/images/post";

    public static final Integer ID_MIN_TOTAL = 18;
    public static final Integer ID_MAX_TOTAL = 120;
    public static final Integer ID_MIN_VALUE_DEFAULT = 18;
    public static final Integer ID_MAX_VALUE_DEFAULT = 100;

}
