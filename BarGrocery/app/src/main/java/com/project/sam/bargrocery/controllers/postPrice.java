package com.project.sam.bargrocery.controllers;

import android.util.Log;

import com.project.sam.bargrocery.Utilities.JSON;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;

import modelclasses.PriceAssociation;

/**
 * Created by sam on 4/26/2015.
 */
public class postPrice {
    public Integer postPrice(PriceAssociation price) throws URISyntaxException, IOException {
        return makePostRequest(price);
    }

    public Integer makePostRequest(PriceAssociation price) throws URISyntaxException, IOException {
        // Create HTTP client
        HttpClient client = new DefaultHttpClient();

        URI uri = URIUtils.createURI("http", "10.0.3.2", 8080, "/price/", null, null);// 10.0.2.2 is localhost's IP address in Android emulator

        // Construct request
        HttpPost httpPost = new HttpPost(uri);

        if(price != null){

            StringWriter sw = new StringWriter();
            JSON.getObjectMapper().writeValue(sw, price);

            // Add JSON object to request
            StringEntity reqEntity = new StringEntity(sw.toString());
            reqEntity.setContentType("application/json");
            httpPost.setEntity(reqEntity);

            // Execute request
            HttpResponse response = client.execute(httpPost);

            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                HttpEntity en = response.getEntity();
                return JSON.getObjectMapper().readValue(en.getContent(), Integer.class);
            }else{
                Log.i("CON", "Error occured in transmission");
                return null;
            }
        }
        Log.i("CON", "Error Occured: end of class");
        return null;

    }
}
