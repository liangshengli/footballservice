package com.football.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by mac on 17/11/2.
 */
public class ExceptionUtil {
    public static String getTrace(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        throwable.printStackTrace(writer);
        StringBuffer buffer = stringWriter.getBuffer();
        return buffer.toString();
    }
}
