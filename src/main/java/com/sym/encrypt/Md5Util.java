package com.sym.encrypt;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具
 *
 * @author shenyanming
 * Create on 2021/07/07 17:33
 */
public class Md5Util {

    public static String encode(String psd) {
        // 加盐处理
        psd = psd + "quick-cash";
        // 指定加密算法类型
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException();
        }
        // 将需要加密的字符串中转换成byte类型的数组,然后进行随机哈希过程
        byte[] bs = digest.digest(psd.getBytes(StandardCharsets.UTF_8));
        // 循环遍历bs,然后让其生成32位字符串,固定写法. bs长度16，每次遍历生成2个16进制字符, 拼接字符串过程
        StringBuilder stringBuffer = new StringBuilder();
        for (byte b : bs) {
            // 将一个byte转换为int类型
            int i = b & 0xff;
            //int类型的i需要转换成16进制字符
            String hexString = Integer.toHexString(i);
            if (hexString.length() < 2) {
                hexString = "0" + hexString;
            }
            stringBuffer.append(hexString);
        }
        return stringBuffer.toString();
    }

}
