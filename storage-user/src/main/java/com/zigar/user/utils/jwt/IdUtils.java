package com.zigar.user.utils.jwt;

import java.util.UUID;

public class IdUtils {

    public static String nextStrId() {
        return UUID.randomUUID().toString().toString().replace("-", "").toUpperCase();
    }


}
