package com.app.k2app.JSON;

import android.util.Log;

import com.app.k2app.config.Config;
import com.app.k2app.pojo.PojoMenuDrawerUserPerfil;

import org.json.JSONException;
import org.json.JSONObject;

public class ParserMenuDraw {

        public static PojoMenuDrawerUserPerfil ParserJSONDrawerHeader(JSONObject jsonObject) {

        PojoMenuDrawerUserPerfil itemDrawerHeader = new PojoMenuDrawerUserPerfil();

        if (jsonObject != null && jsonObject.length() > 0) {
            try {

                JSONObject j = jsonObject.getJSONObject("usrMenuDraw");
                Log.i(Config.TAG, "PARSER PERFIL JSON PERFIL_DRAW: " + j.toString());

                //id
                if (!j.isNull("id")) {
                    itemDrawerHeader.setId(j.getInt("id"));
                }

                if (!j.isNull("email")) {
                    itemDrawerHeader.setEmail(j.getString("email"));
                }

                if (!j.isNull("showName")) {
                    itemDrawerHeader.setNmExibicao(j.getString("showName"));
                    Log.i(Config.TAG, "PARSER PERFIL JSON PERFIL_DRAW name: " + j.getString("showName"));
                }

                if (!j.isNull("nmPhoto")) {
                    itemDrawerHeader.setUserFoto(j.getString("nmPhoto"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return itemDrawerHeader;
    }
}
