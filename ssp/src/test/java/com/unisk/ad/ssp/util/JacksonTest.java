package com.unisk.ad.ssp.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by sunyunjie on 5/31/16.
 */
public class JacksonTest {

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree("{\"id\":\"f0ec439aac8e9eb5a4c151aba5b18ebb\",\"seatbid\":[{\"bid\":[{\"adid\":\"1326\",\"wurl\":\"http://dsp.example.com/winnotice?price=%%WIN_PRICE%%\",\"adurl\":\"http://www.baidu.com\",\"nurl\":{\"0\":[\"url1\",\"url2\"]},\"admt\":4,\"price\":12000,\"curl\":[\"http://dsp1.com/adclick?id=123398923\"],\"crid\":\"2376\",\"adh\":50,\"adw\":320,\"impid\":\"5cdef32a55397c48b8baeb3cee0c5b5c\"}],\"ext\":{}}]}");
            System.out.println(node.findValuesAsText("adurl"));
            System.out.println(node.findValues("0"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
