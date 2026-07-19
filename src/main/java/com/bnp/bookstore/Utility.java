package com.bnp.bookstore;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utility {

    public static <T> T convertObject(Object obj, Class<T> clazz) {
        return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .convertValue(obj, clazz);
    }
}
