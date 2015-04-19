package com.project.sam.bargrocery.controllers;


import android.util.Log;


import com.project.sam.bargrocery.Utilities.JSON;
import modelclasses.*;

import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;


/**
 * Controller to send a shopping list to the database and then receive the cheapest prices for items
 * Created by Samantha Hamor on 3/13/2015.
 */
public class sendList  {
    public PriceAssociation[] postList(Item[] itemList) throws URISyntaxException,  IOException {
        return makePostRequest(itemList);
    }

    public PriceAssociation[] makePostRequest(Item[] itemList) throws URISyntaxException, IOException {
        // Create HTTP client
        HttpClient client = new DefaultHttpClient();

        URI uri = URIUtils.createURI("http", "10.0.3.2", 8080, "/list/", null, null);// 10.0.2.2 is localhost's IP address in Android emulator

        // Construct request
        HttpPost httpPost = new HttpPost(uri);

        if(itemList.length != 0){

            StringWriter sw = new StringWriter();
            JSON.getObjectMapper().writeValue(sw, itemList);

            // Add JSON object to request
            StringEntity reqEntity = new StringEntity(sw.toString());
            reqEntity.setContentType("application/json");
            httpPost.setEntity(reqEntity);

            // Execute request
            HttpResponse response = client.execute(httpPost);
            Log.i("CON", "Sent executed");

            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                HttpEntity en = response.getEntity();
                Log.i("CON", "items returned");
                return JSON.getObjectMapper().readValue(en.getContent(), PriceAssociation[].class);
            }else{
                Log.i("CON", "Error occured in transmission");
                return null;
            }
        }
        Log.i("CON", "Error Occured: end of class");
        return null;

    }
}