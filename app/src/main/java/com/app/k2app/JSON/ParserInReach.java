package com.app.k2app.JSON;

import android.util.Log;

import com.app.k2app.application.MyApplication;
import com.app.k2app.config.Config;
import com.app.k2app.config.Params;
import com.app.k2app.pojo.PojoInReachItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ParserInReach {
    private static String TAG = "k2debug";

    public ParserInReach() {
    }

    public static ArrayList<PojoInReachItem> ParserJSONInReach(JSONObject jsonObject) {

        ArrayList<PojoInReachItem> arrayAux = new ArrayList<>();
        arrayAux.clear();
        if (jsonObject != null && jsonObject.length() > 0) {
            try {

                for (int i = 0; i < jsonObject.getJSONArray("inreach").length(); i++) {

                    JSONObject j = jsonObject.getJSONArray("inreach").getJSONObject(i);
                    PojoInReachItem pojo = new PojoInReachItem();

                    //id
                    pojo.setId(j.getInt("id"));

                    //Nome de Exibicao
                    String nomeParts = j.getString("showName");
                    String[] nParts = nomeParts.split("\\ ");
                    String showName;
                    if (nParts[0].length() >= 15) { //testa se nome tem mais do que 15 caracteres
                        showName = nParts[0].substring(0, 14); //mostra apenas 15 caracteres
                    } else {
                        showName = nParts[0]; // nome menor do que 15 caracteres, mostra nome completo
                    }
                    pojo.setNome(showName);

                    //Quantidade de fotos
                    pojo.setQtdPhotos(j.getInt("qtdPhotos"));


                    //URL Foto perfil
                    String urlFotoPerfil = MyApplication.getWsUrlPhotoPerfil()  + "/" + j.getString("nmPhoto");
                    //Log.d(TAG, urlFotoPerfil);
                    pojo.setImageUrl(urlFotoPerfil);

                    try {
                        //distancia
                        String file = j.getString("dist");
                        String[] dParts = file.split("\\.");
                        String dist = null;
                        Log.i(Config.TAG, "ParserInReach: file " + file);
                        if (dParts[0].equals("0")) { // Menor que 1.000m
                            Log.i(Config.TAG, "ParserInReach: dParts[0].length(): " + dParts[1].length());
                            if (dParts[1].length() == 1) {
                                dist = "0 m"; // distancia 0.0
                            } else {
                                if (dParts[1].substring(0, 1).equals("0")) {
                                    if (dParts[1].substring(1, 2).equals("0")) {
                                        dist = dParts[1].substring(2, 3) + " m"; //mostra 0m
                                    } else {
                                        dist = dParts[1].substring(1, 3) + " m"; //mostra 00m
                                    }
                                } else {
                                    dist = dParts[1].substring(0, 3) + " m"; //mostra 000m
                                }
                            }
                        } else if (dParts[0].length() > 1) { // Maior que 9.999m
                            dist = dParts[0] + " km";
                        } else { // Entre 1.000m e 9.999m
                            dist = dParts[0] + "." + dParts[1].substring(0, 3) + " m";
                        }
                        pojo.setDist(dist);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    arrayAux.add(pojo);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return arrayAux;
    }
}
