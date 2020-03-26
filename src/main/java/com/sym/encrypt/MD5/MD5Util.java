package com.sym.encrypt.MD5;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5加密例子
 *
 * Created by 沈燕明 on 2019/8/1 9:50.
 */
public class MD5Util {

    public static String encoder(String psd) {
        try {
            //加盐处理
            psd = psd + "quick-cash";
            //1,指定加密算法类型
            MessageDigest digest = MessageDigest.getInstance("MD5");
            //2,将需要加密的字符串中转换成byte类型的数组,然后进行随机哈希过程
            byte[] bs = digest.digest(psd.getBytes(StandardCharsets.UTF_8));
            //3,循环遍历bs,然后让其生成32位字符串,固定写法
            //bs长度16，每次遍历生成2个16进制字符
            //4,拼接字符串过程
            StringBuilder stringBuffer = new StringBuilder();
            for (byte b : bs) {
                int i = b & 0xff;       //==》将一个byte转换为int类型
                //int类型的i需要转换成16进制字符
                String hexString = Integer.toHexString(i);
                if (hexString.length() < 2) {
                    hexString = "0" + hexString;
                }
                stringBuffer.append(hexString);
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
