package com.fit.ah.twitter.helper.url_connection;

import android.util.Log;

import com.fit.ah.twitter.helper.Config;
import com.fit.ah.twitter.helper.MyGson;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class HttpManager {

    public static <T> MyApiResult<T> get(String url, Class<T> outputType, NameValuePair... inputParams){

        String urlParams = URLEncodedUtils.format(Arrays.asList(inputParams), "utf-8");
        HttpGet httpGet =  new HttpGet(Config.url + "?" + urlParams);

        DefaultHttpClient client = new DefaultHttpClient();

        final MyApiResult<T> result = new MyApiResult<T>();

        try{
            HttpResponse response = client.execute(httpGet);

            InputStream stream = response.getEntity().getContent();

            String strJson = convertStreamToString(stream);

            T x = MyGson.build().fromJson(strJson, outputType);

            result.isError = false;
            result.value = x;

        } catch(IOException e){
            Log.e("HttpManager", e.getMessage());
            result.isError = true;
            result.value = null;
            result.errorMessage = e.getMessage();
        }

        return result;
    }

    private static String convertStreamToString(InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;

        while ((line = bufferedReader.readLine()) != null){
            stringBuilder.append(line + "\n");
        }

        return stringBuilder.toString();
    }


}
