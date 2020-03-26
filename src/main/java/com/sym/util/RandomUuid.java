package com.sym.util;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomUuid {
    private static List<Character> exclude = Arrays.asList('I', 'O');
    private static char[] arr = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    public static String generateRandom(int length) {
        char[] uuidChars = new char[length];
        // 字符从0-9,A-Z中随机取
        Random random = new Random();
        for (int index = 0; index < uuidChars.length; ) {
            int next = random.nextInt(arr.length);
            char current = arr[next];
            if (exclude.contains(current)) {
                continue;
            }
            uuidChars[index] = current;
            index++;
        }
        return new String(uuidChars);
    }

    public static void main(String[] args) {
        String s = RandomUuid.generateRandom(6);
        System.out.println(s);
        System.out.println(System.currentTimeMillis());
    }

}