package com.app.k2app.JSON;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class Base64Utils {

    public Base64Utils() {
    }

    public static String bmpToStrBase64(Bitmap bitmap) {

        Bitmap bm = bitmap;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 80, stream); //bm is the bitmap object
        byte[] b = stream.toByteArray();
        String strBase64 = Base64.encodeToString(b, 0);

        return strBase64;
    }
}
