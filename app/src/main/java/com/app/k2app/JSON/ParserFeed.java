package com.app.k2app.JSON;

import com.app.k2app.pojo.PojoFeedItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ParserFeed {

    public ParserFeed() {
    }

    public static ArrayList<PojoFeedItem> ParserJSONFeed(JSONObject jsonObject) {

        ArrayList<PojoFeedItem> arrayAux = new ArrayList<>();
        arrayAux.clear();
        if (jsonObject != null && jsonObject.length() > 0) {
            try {

                for (int i = 0; i < jsonObject.getJSONArray("posts").length(); i++){

                    JSONObject j = jsonObject.getJSONArray("posts").getJSONObject(i);
                    PojoFeedItem pojoFeedItem = new PojoFeedItem();

                    //id
                    if (!j.isNull("id")){
                        pojoFeedItem.setId(j.getInt("id"));
                    }

                    //URL Foto perfil
                    if (!j.isNull("userImgUrl")){
                        pojoFeedItem.setUserImage(j.getString("userImgUrl"));
                    }

                    //Nome de Exibicao
                    if (!j.isNull("nmUser")){
                        pojoFeedItem.setShowName(j.getString("nmUser"));
                    }

                    //distancia
                    if (!j.isNull("dist")){
                        pojoFeedItem.setDist(j.getString("dist"));
                    }

                    // tempo de postagem
                    if (!j.isNull("oldTime")){
                        pojoFeedItem.setOldTime(j.getInt("oldTime"));
                    }

                    //URL POST IMAGEM
                    if (!j.isNull("postImgUrl")){
                        pojoFeedItem.setImageUrl(j.getString("postImgUrl"));
                    }

                    //URL POST TEXTO
                    if (!j.isNull("postText")){
                        pojoFeedItem.setText(j.getString("postText"));
                    }

                    //Quantidade de Likes
                    if (!j.isNull("qtdLike")){
                        pojoFeedItem.setQtdLike(j.getInt("qtdLike"));
                    }

                    //Quantidade de Likes
                    if (!j.isNull("stsLike")){
                        pojoFeedItem.setLikeStatus(j.getInt("stsLike"));
                    }

                    //IMPORTANTE: colocar na sequencia que o pojo ira receber
                    arrayAux.add(pojoFeedItem);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return arrayAux;
    }
}
