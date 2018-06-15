package com.yfzm.flawsweeper.util;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.UUID;

public class Util {
    public static String createRandomId() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        return str.replace("-", "");
    }

    public static String getAndEncodeJsonData(HttpServletRequest request, String key) {
        String strJsonData = request.getParameter(key);
        try {
            strJsonData = URLDecoder.decode(strJsonData, "utf-8");
        } catch (NullPointerException e) {
            System.out.println("空参数列表");
            return null;
        } catch (UnsupportedEncodingException e) {
            System.out.println("编码错误");
            return null;
        }
        return strJsonData;
    }
}
