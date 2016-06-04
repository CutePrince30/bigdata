package com.unisk.ad.ssp.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by sunyunjie on 5/31/16.
 */
public class JacksonTest {

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree("{\"appid\":\"xxx\",\"slotid\":\"xxx\",\"device\":{\"ip\":\"10.23.45.67\",\"os\":\"iOS\",\"model\":\"iPhone5,1\",\"geo\":{\"lon\":116.4736795,\"type\":1,\"lat\":39.9960702},\"osv\":\"7.0.6\",\"js\":1,\"dnt\":0,\"sh\":1024,\"s_density\":2,\"connectiontype\":2,\"dpidsha1\":\"7c222fb2927d828af22f592134e8932480637c0d\",\"didsha1\":\"1231231238912839123812\",\"macsha1\":\"2445934589348534534534\",\"ua\":\"Mozilla/5.0 (iPhone; CPU iPhone OS 7_0_6 like Mac OS X)AppleWebKit/534.46 (KHTML, like Gecko) Mobile/9B206\",\"carrier\":\"46000\",\"language\":\"zh\",\"make\":\"Apple\",\"sw\":768,\"imei\":\"12312312312312\"}}");

            System.out.println(new ObjectMapper().writeValueAsString(node.findPath("device")));

//            Iterator<String> keys = node.fieldNames();
//            while (keys.hasNext()) {
//                String fieldName = keys.next();
//                System.out.println(fieldName + ": " + node.path(fieldName).toString());
//            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
