package com.ecnu.notehub.util;

import java.util.Random;

/**
 * @author onion
 * @date 2019/11/16 -10:16 下午
 */
public class KeyGenerateUtil {
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }
}
