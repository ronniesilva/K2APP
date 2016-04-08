package com.app.k2app.JSON;

import com.app.k2app.pojo.PojoLogin;

import org.json.JSONException;
import org.json.JSONObject;

public class ParserLogin {

        public static PojoLogin ParserJSONLogin(JSONObject jsonObject) {

        PojoLogin pLogin = new PojoLogin();

        if (jsonObject != null && jsonObject.length() > 0) {
            try {
                JSONObject j = jsonObject.getJSONObject("login");

                //id
                if (!j.isNull("id")) {
                    pLogin.setId(j.getInt("id"));
                }
                if (!j.isNull("email")) {
                    pLogin.setEmailOK(j.getInt("email"));
                }

                if (!j.isNull("showname")) {
                    pLogin.setNmExibicao(j.getString("showname"));
                }

                if (!j.isNull("nmphoto")) {
                    pLogin.setUserFoto(j.getString("nmphoto"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return pLogin;
    }
}
