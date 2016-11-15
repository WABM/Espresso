package io.wabm.supermarket.misc.util;

/**
 * Created by MainasuK on 2016-10-16.
 */
public class ConsoleLog {

    public static void print(String str) {
        StackTraceElement e = new Exception().getStackTrace()[1];
        System.out.println("^ " + e.getClassName() + "[" + e.getLineNumber() + "] #" + e.getMethodName() + ": " + str);
    }

}
