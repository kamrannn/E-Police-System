package com.app.epolice.util;

import org.bouncycastle.asn1.cms.TimeStampedData;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Optional;

/**
 * The type Response utility.
 */
@Service
public class ResponseUtility {
    /**
     * Get list response hash map.
     *
     * @param obj     the obj
     * @param message the message
     * @return the hash map
     */
    public static HashMap<String, Object> getResponse(String message, Object obj, String path) throws ParseException {
        HashMap<String, Object> map = new HashMap<>();
        if(obj==null){
            map.put("message",message);
            map.put("Timestamp", DateTime.getStringDateTime());
            map.put("uri", path);
        }else{
            map.put("message",message);
            map.put("Timestamp", DateTime.getStringDateTime());
            map.put("uri", path);
            map.put("result",obj);
        }
        return map;
    }
}
