package org.senla.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class JwtResponseHandler {
    public static ResponseEntity<Object> generatedResponse(String message,
                                                           HttpStatus status,
                                                           Object responseObj){
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("status", status);
        map.put("data", responseObj);
        return new ResponseEntity<>(map, status);
    }
}