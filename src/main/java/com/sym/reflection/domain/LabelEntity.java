package com.sym.reflection.domain;

import lombok.Data;

/**
 * @Auther: shenym
 * @Date: 2019-01-04 14:23
 */
@Data
public class LabelEntity {

    private int labelId;
    private String labelName;
    private char isDel;
    private double weight;
    private static int commonId;
    boolean isFlag;

    public int count(int... ns) {
        int sum = 0;
        for (int n : ns) {
            sum += n;
        }
        return sum;
    }

    public String concat(String... strs) {
        String retStr = "";
        for (String str : strs) {
            retStr.concat(str + " ");
        }
        return retStr;
    }
}
