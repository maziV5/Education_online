package com.maziV5.msmservice.utils;

import java.util.Random;

public class RandomUtils {
    public static String getRandom(){
        String num = "";
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            String s = random.nextInt(9) + "";
            num += s;
        }

        System.out.println(num);
        return num;
    }

    public static void main(String[] args) {
        String random = RandomUtils.getRandom();
    }
}
