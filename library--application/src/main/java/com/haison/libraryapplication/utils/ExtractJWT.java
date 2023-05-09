package com.haison.libraryapplication.utils;

import java.util.*;

public class ExtractJWT {

    /*
        {
          "ver": 1,
          "jti": "AT.W7Qy_PZJ-MAyY04oUt82omudjNbB1hAciqosmmmUvWQ",
          "iss": "https://dev-74231503.okta.com/oauth2/default",
          "aud": "api://default",
          "iat": 1682178974,
          "exp": 1682182574,
          "cid": "0oa97fg71vI8GjbsO5d7",
          "uid": "00u97utideKYDh8IQ5d7",
          "scp": [
            "openid",
            "email"
          ],
          useType:"admin",
          "auth_time": 1682178972,
          "sub": "haison@gmail.com"
        }
*/
    public static String payloadJWTExtraction(String token,String extraction) {

        String newToken =  token.replace("Bearer","").trim();

        String[] chunks = newToken.split("\\.");

        Base64.Decoder decoder = Base64.getUrlDecoder();

        String payload = new String(decoder.decode(chunks[1]));

        String[] entries = payload.split(",");

        Map<String, String> map = new HashMap<>();

        for (String entry : entries) {
            String[] keyValue = entry.split(":");

            if(keyValue[0].equals(extraction)) {
                String key = keyValue[0];

                int remove = 1;

                if(keyValue[1].contains("}")) remove = 2;

                String value = keyValue[1].substring(1,keyValue[1].length()-remove);

                map.put(key,value);
            }
        }

        return map.getOrDefault(extraction, null);
    }
}
