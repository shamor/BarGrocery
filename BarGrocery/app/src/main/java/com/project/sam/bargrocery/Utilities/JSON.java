package com.project.sam.bargrocery.Utilities;

import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Created by sam on 3/16/2015.
 * Class to provide access to a singleton {@link ObjectMapper} instance,
 * for converting model objects to/from JSON format.
 */
public class JSON {
    private static final ObjectMapper theObjectMapper = new ObjectMapper();

    public static ObjectMapper getObjectMapper() {
        return theObjectMapper;
    }
}
