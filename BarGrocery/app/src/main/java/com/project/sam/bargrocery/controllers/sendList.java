package com.project.sam.bargrocery.controllers;


import com.project.sam.bargrocery.JSON;
import com.project.sam.modelclases.Items;
import com.project.sam.modelclases.PriceAssociation;

import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Samantha Hamor on 3/13/2015.
 */
public class sendList  {
    public PriceAssociation[] postList(List<Items> itemList, List<Integer> quants) throws URISyntaxException,  IOException {
        return makePostRequest(itemList, quants);
    }

    public PriceAssociation[] makePostRequest(List<Items> itemList, List<Integer> quants) throws URISyntaxException, IOException {
        // Create HTTP client
        HttpClient client = new DefaultHttpClient();

        // Construct request
        HttpPost httpPost = new HttpPost("http://10.0.2.2:8081/list"); // 10.0.2.2 is localhost's IP address in Android emulator

        if(!itemList.isEmpty() && !quants.isEmpty()){

            StringWriter sw = new StringWriter();
            JSON.getObjectMapper().writeValue(sw, itemList);

            // Add JSON object to request
            StringEntity reqEntity = new StringEntity(sw.toString());
            reqEntity.setContentType("application/json");
            httpPost.setEntity(reqEntity);

            // Execute request
            HttpResponse response = client.execute(httpPost);

            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                HttpEntity en = response.getEntity();
                return JSON.getObjectMapper().readValue(en.getContent(), PriceAssociation[].class);
            }else{
                return null;
            }
        }

        return null;

    }
}