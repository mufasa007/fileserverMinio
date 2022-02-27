package com.activeclub.fileserverminio.core.utils;

import java.util.UUID;

public class RandomUtil {

    public static String getUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
