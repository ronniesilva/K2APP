package com.app.k2app.JSON;

import com.app.k2app.pojo.PojoContactsItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ParserContacts {

    public ParserContacts() {
    }

    public static ArrayList<PojoContactsItem> ParserJSONContacts(JSONObject jsonObject) {

        ArrayList<PojoContactsItem> arrayAux = new ArrayList<>();
        arrayAux.clear();
        if (jsonObject != null && jsonObject.length() > 0) {
            try {

                for (int i = 0; i < jsonObject.getJSONArray("contacts").length(); i++){

                    JSONObject j = jsonObject.getJSONArray("contacts").getJSONObject(i);

                    PojoContactsItem pojo = new PojoContactsItem();

                    //id
                    if (!j.isNull("id")){
                        pojo.setId(j.getInt("id"));
                    }

                    //Nome de Exibicao
                    if (!j.isNull("showName")){
                        pojo.setShowName(j.getString("showName"));
                    }

                    //URL Foto perfil do contato
                    if (!j.isNull("nmPhoto")){
                        pojo.setImageUrl(j.getString("nmPhoto"));
                    }

                    //distancia
                    if (!j.isNull("msgStatus")){
                        pojo.setMsgStatus(j.getString("msgStatus"));
                    }

                    //IMPORTANTE: colocar na sequencia que o pojo ira receber
                    arrayAux.add(pojo);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return arrayAux;
    }
}
