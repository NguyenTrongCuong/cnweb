package com.example.demo.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HttpUtils {

    public static void writeJSONToHttpServletResponse(HttpServletResponse response, String body) throws IOException {
        response.setContentType("application/json");
        response.getWriter().write(body);
        response.flushBuffer();
    }

}
