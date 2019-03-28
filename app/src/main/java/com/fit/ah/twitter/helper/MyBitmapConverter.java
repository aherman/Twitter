package com.fit.ah.twitter.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class MyBitmapConverter {

    public static String BitmapToString(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = bmp;
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    public static Bitmap StringToBitmap(String encodedImage){
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    public static Bitmap resize(Bitmap image) {
        Bitmap temp = image;
        if(temp.getWidth() > 4000)
            temp = Bitmap.createScaledBitmap(image,
                    (int) (image.getWidth() * 0.1), (int) (image.getHeight() * 0.1), false);
        if(temp.getWidth() > 2000)
            temp = Bitmap.createScaledBitmap(image,
                    (int) (image.getWidth() * 0.2), (int) (image.getHeight() * 0.2), false);
        if(temp.getWidth() > 1000)
            temp = Bitmap.createScaledBitmap(image,
                    (int) (image.getWidth() * 0.3), (int) (image.getHeight() * 0.3), false);
        if(temp.getWidth() > 510)
            temp = Bitmap.createScaledBitmap(image,
                    (int) (image.getWidth() * 0.8), (int) (image.getHeight() * 0.8), false);
        return temp;
    }
}
