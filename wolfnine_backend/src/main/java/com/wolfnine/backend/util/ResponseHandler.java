package com.wolfnine.backend.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(Object responseObj, String message, HttpStatus status) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("status", status.value());
        map.put("data", responseObj);

        return new ResponseEntity<Object>(map,status);
    }

    public static ResponseEntity<Object> generateResponse(Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", "");
        map.put("status", HttpStatus.OK.value());
        map.put("data", responseObj);
        return new ResponseEntity<Object>(map,HttpStatus.OK);
    }
}
