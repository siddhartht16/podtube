package com.podtube.services;

import javax.servlet.http.HttpSession;

public final class ServiceUtils {

    public static boolean isValidSession(HttpSession httpSession){
        boolean result = false;
        if (httpSession.getAttribute("id") == null){
            System.out.println(result);
            return result;
        }

        result = true;
        return result;
    }//isValidSession..
}
