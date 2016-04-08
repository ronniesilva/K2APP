package com.app.k2app.JSON;

import android.util.Log;

import com.app.k2app.config.Config;
import com.app.k2app.pojo.PojoPerfilUserPhotos;
import com.app.k2app.pojo.PojoPerfilUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ParserPerfil {

    public ParserPerfil() {
    }

    public static PojoPerfilUser ParserJSONPerfil(JSONObject jsonObject) {

        PojoPerfilUser itemPerfil = new PojoPerfilUser();

        if (jsonObject != null && jsonObject.length() > 0) {
            try {
                JSONObject jObj = jsonObject.getJSONObject("usrPerfil");
                JSONArray jArr = jObj.getJSONArray("photos");

                //Log.i(Config.TAG, "PARSER PERFIL : jObj" + jObj.toString());
                //Log.i(Config.TAG, "PARSER PERFIL : jArr " + jArr.toString());

                // Iniciando todas as variaveis

                //id
                if (!jObj.isNull("email")) {
                    itemPerfil.setEmail(jObj.getString("email"));
                }

                if (!jObj.isNull("birthday")) {
                    itemPerfil.setNascimento(jObj.getString("birthday"));
                }

                if (!jObj.isNull("sex")) {
                    itemPerfil.setSexo(jObj.getInt("sex"));
                }

                if (!jObj.isNull("oriSex")) {
                    itemPerfil.setOriSexual(jObj.getInt("oriSex"));
                }

                if (!jObj.isNull("showName")) {
                    itemPerfil.setNmExibicao(jObj.getString("showName"));
                    Log.i(Config.TAG, "PARSER PERFIL showName: " + itemPerfil.getNmExibicao());
                }

                if (!jObj.isNull("msgStatus")) {
                    itemPerfil.setMsgStatus(jObj.getString("msgStatus"));
                }

                if (!jObj.isNull("tel")) {
                    itemPerfil.setTel(jObj.getString("tel"));
                }

                if (!jObj.isNull("nmPhoto")) {
                    itemPerfil.setNmFoto(jObj.getString("nmPhoto"));
                }

                ArrayList<PojoPerfilUserPhotos> arrayFotos = new ArrayList<>();
                for (int i = 0; i < jArr.length(); i++) {
                    JSONObject obj = jArr.getJSONObject(i);

                    PojoPerfilUserPhotos pojoPhotos = new PojoPerfilUserPhotos();

                    pojoPhotos.setId(obj.getInt("id"));

                    pojoPhotos.setNomeFoto(obj.getString("name"));

                    if (!obj.isNull("legend")) {
                        pojoPhotos.setLegenda(obj.getString("legend"));
                    }

                    pojoPhotos.setCurtidas(obj.getInt("qtdLike"));

                    arrayFotos.add(pojoPhotos);
                }

                itemPerfil.setArrayFotos(arrayFotos);
                Log.i(Config.TAG, "PARSER PERFIL : itemPerfil " + itemPerfil.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return itemPerfil;
    }
}
