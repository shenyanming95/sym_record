package com.sym.encrypt.RSA;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * 基于 JDK 的 RSA加解密工具
 *
 * 字符串格式的密钥在未在特殊说明情况下都为BASE64编码格式<br/>
 * 由于非对称加密速度极其缓慢, 一般文件不使用它来加密而是使用对称加密, <br/>
 * 非对称加密算法可以用来对对称加密的密钥加密, 这样保证密钥的安全也就保证了数据的安全
 * </p>
 */
public class RSAUtil {

    /*
     * 加密算法RSA
     */
    private static final String RSA_ALGORITHM = "RSA";

    /*
     * 签名算法
     */
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /*
     * 获取公钥的key
     */
    public static final String PUBLIC_KEY = "RSAPublicKey";

    /*
     * 获取私钥的key
     */
    public static final String PRIVATE_KEY = "RSAPrivateKey";

    /*
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /*
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /*
     * 默认编码
     */
    private static Charset charset = Charset.forName("UTF-8");

    /**
     * 使用JDK生成公钥和密钥, 密钥对都做了base64处理
     *
     * @param keySize 64的整数倍
     * @return 返回公钥和密钥
     * @throws NoSuchAlgorithmException
     */
    public static Map<String, String> generateKey(Integer keySize) throws NoSuchAlgorithmException {
        if (null == keySize) keySize = 1024;

        // 使用JDK的API, 开始生产密钥对
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        keyPairGen.initialize(keySize);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        // 对生成好的密钥对进行Base64编码处理
        Map<String, String> keyMap = new HashMap<>(2);
        keyMap.put(PUBLIC_KEY, Base64.encodeBase64URLSafeString(publicKey.getEncoded()));
        keyMap.put(PRIVATE_KEY, Base64.encodeBase64URLSafeString(privateKey.getEncoded()));
        return keyMap;
    }

    /**
     * 对数据进行数字签名.
     * 数字签名只能用私钥加密, 然后拥有公钥的人才可以正确校验此签名
     *
     * @param data       原数据
     * @param privateKey 私钥(BASE64编码)
     */
    public static String signature(String data, String privateKey) throws Exception {
        // 先对私钥做BASE64解码, 获取到私钥对象 PrivateKey
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PrivateKey _privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 获取签名对象
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(_privateKey);
        signature.update(data.getBytes(charset)); //将数据通过UTF-8编码取得字节数组
        return Base64.encodeBase64URLSafeString(signature.sign());
    }

    /**
     * 校验数字签名
     *
     * @param data      原数据
     * @param publicKey 公钥(BASE64编码)
     * @param sign      数字签名(BASE64编码)
     */
    public static boolean verifySignature(String data, String publicKey, String sign) throws Exception {
        // 先对进行BASE64编码的字符串进行解码
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        byte[] signBytes = Base64.decodeBase64(sign);

        // 获取公钥对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PublicKey _publicKey = keyFactory.generatePublic(keySpec);

        // 获取签名对象
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(_publicKey);
        signature.update(data.getBytes(charset)); //将数据通过UTF-8编码取得字节数组
        return signature.verify(signBytes);
    }

    /* 分段加解密处理 - start */

    /**
     * 私钥分段解密, 这是为了防止原数据过大, RSA会解密失败
     *
     * @param encryptedData 已加密的数据(BASE64编码)
     * @param privateKey    私钥(BASE64编码)
     * @return 原数据
     */
    public static String decryptByPrivateKeyWithFragment(String encryptedData, String privateKey) throws Exception {
        // BASE64解码
        byte[] dataBytes = Base64.decodeBase64(encryptedData);
        byte[] keyBytes = Base64.decodeBase64(privateKey);

        // 获取私钥对象
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PrivateKey _privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 分段解密处理
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, _privateKey);
        int inputLen = dataBytes.length;
        int offSet = 0;
        byte[] cache;
        int i = 0;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(dataBytes, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(dataBytes, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return new String(decryptedData, charset);
        }
    }

    /**
     * 公钥分段解密, 这是为了防止原数据过大, RSA会解密失败
     *
     * @param data      已加密的数据(BASE64编码)
     * @param publicKey 公钥(BASE64编码)
     */
    public static String decryptByPublicKeyWithFragment(String data, String publicKey) throws Exception {
        // BASE64解码
        byte[] dataArray = Base64.decodeBase64(data);
        byte[] keyBytes = Base64.decodeBase64(publicKey);

        // 获取公钥对象
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PublicKey _publicKey = keyFactory.generatePublic(x509KeySpec);

        // 分段加密处理
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, _publicKey);
        int inputLen = dataArray.length;
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(dataArray, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(dataArray, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return new String(decryptedData, charset);
        }
    }

    /**
     * 公钥分段加密, 这是为了防止原数据过大, RSA会加密失败
     *
     * @param data      原数据
     * @param publicKey 公钥(BASE64编码)
     * @return 经过BASE64编码后的加密串
     */
    public static String encryptByPublicKeyWithFragment(String data, String publicKey) throws Exception {
        // 将原数据转换为数组, 将公钥进行BASE64解码
        byte[] dataArray = data.getBytes(charset);
        byte[] keyBytes = Base64.decodeBase64(publicKey);

        // 获取公钥对象
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PublicKey _publicKey = keyFactory.generatePublic(x509KeySpec);

        // 分段进行加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, _publicKey);
        int inputLen = dataArray.length;
        int offSet = 0;
        byte[] cache;
        int i = 0;
        try ( ByteArrayOutputStream out = new ByteArrayOutputStream() ){
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(dataArray, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(dataArray, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            out.close();
            return Base64.encodeBase64URLSafeString(encryptedData);
        }
    }

    /**
     * 私钥分段加密, 这是为了防止原数据过大, RSA会加密失败
     *
     * @param data       原数据
     * @param privateKey 私钥(BASE64编码)
     * @return 经过BASE64编码后的加密串
     */
    public static String encryptByPrivateKeyWithFragment(String data, String privateKey) throws Exception {
        // 将原数据转换为数组, 将私钥进行BASE64解码
        byte[] dataArray = data.getBytes(charset);
        byte[] keyBytes = Base64.decodeBase64(privateKey);

        // 获取私钥对象
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PrivateKey _privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 分段进行加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, _privateKey);
        int inputLen = dataArray.length;
        int offSet = 0;
        byte[] cache;
        int i = 0;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(dataArray, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(dataArray, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            out.close();
            return Base64.encodeBase64URLSafeString(encryptedData);
        }
    }

    /* 分段加解密处理 - end */




    /* 全量加解密处理 - start */

    /**
     * 公钥加密
     * @param data 原数据
     * @param base64PublicKey 公钥（被Base64编码过）
     * @return 原数据加密后的字符串
     */
    public static String encryptByPublicKey(String data,String base64PublicKey) throws Exception{
        // 通过 publicKey 字符串获取公钥对象
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(base64PublicKey));
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        // 开始加密
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE,publicKey);
        byte[] bytes = cipher.doFinal(data.getBytes());
        return Base64.encodeBase64URLSafeString(bytes);
    }


    /**
     * 私钥加密
     * @param data 原数据
     * @param base64PrivateKey  私钥(被Base64编码过)
     * @return 原数据加密后的字符串
     */
    public static String encryptByPrivateKey(String data,String base64PrivateKey) throws Exception{
        // 通过 privateKey 字符串获取私钥对象
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(base64PrivateKey));
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        // 开始加密
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE,privateKey);
        byte[] bytes = cipher.doFinal(data.getBytes());
        return Base64.encodeBase64URLSafeString(bytes);
    }


    /**
     * 公钥解密
     * @param data 加密数据
     * @param base64PublicKey 公钥（被Base64编码过）
     * @return 解密后的原数据
     */
    public static String decryptByPublicKey(String data,String base64PublicKey) throws Exception{
        // 通过 publicKey 字符串获取公钥对象
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(base64PublicKey));
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        // 开始加密
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE,publicKey);
        byte[] bytes = cipher.doFinal(Base64.decodeBase64(data));
        return new String(bytes);
    }


    /**
     * 私钥解密
     * @param data 加密数据 （被Base64编码过）
     * @param base64PrivateKey 私钥（被Base64编码过）
     * @return 解密后的原数据
     */
    public static String decryptByPrivateKey(String data,String base64PrivateKey) throws Exception{
        // 通过 publicKey 字符串获取公钥对象
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(base64PrivateKey));
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        // 开始加密
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE,privateKey);
        byte[] bytes = cipher.doFinal(Base64.decodeBase64(data));
        return new String(bytes);
    }


    /* 全量加解密处理 - end */

}
