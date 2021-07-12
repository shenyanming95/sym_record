package com.sym.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sym.encrypt.Md5Util;
import com.sym.encrypt.RsaUtil;
import org.junit.Test;

import java.util.*;

/**
 * @author shenyanming
 * Create on 2021/07/07 17:36
 */
public class EncryptTest {
    private String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCBcUwrZ4Ha6gxclFuLyikaYHQP+46G5Owh5lQIVGwKCX7QhdwfM7japUM8zjZFD1rR/0zbLu7kjk4OFJ4chiecMcBnU7rClV040Xom2HZUHbRIQHHWZSmFPewhF7HOwmC8djNdoY7eq3I+7lQsRqVm+aUTCa04mR8/2M+vKKQ7IwIDAQAB";
    private String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIFxTCtngdrqDFyUW4vKKRpgdA/7jobk7CHmVAhUbAoJftCF3B8zuNqlQzzONkUPWtH/TNsu7uSOTg4UnhyGJ5wxwGdTusKVXTjReibYdlQdtEhAcdZlKYU97CEXsc7CYLx2M12hjt6rcj7uVCxGpWb5pRMJrTiZHz/Yz68opDsjAgMBAAECgYBmBCgW8T3bjahucpJv6sVYP3pZelYLJHKiCQhjhD2d73vd1hH1GWtwDt2eF5uX9BEM/lvRRv0Kbsk/uaofKvAdvp/W6FIR7TwNCq0N6GMSmglXrLZFCmXDRlDOsXccUxAf8qZvn69Q6SbwoptgCdZHaBfeJd9ZClB/TaSsZck4CQJBAMimWZjn0sKAke2FXsxMht2eGgVhokvBlMRNaoVtK8RZEgZGVdNMRg+s2YzxVcJNhqaggWdgcHrjodO7OmirfiUCQQClJmCebJthh8NmkzuXrzTqqM94W9BQmxNXsYM1Gs+oclI8Orb/NZKnUfGtRwysggTSKVlETOp8rudWm1zNs92nAkEAnE1Zhk8wUuuswYYfbq4+cOz0ADUqJDFMHg7gZ8e/TFjGPbUUrmDAQv23CqAE2jNiLPLjWxA5DsG8Eh/LRDYzMQJAJbar3cHDgfQ05Rm+RBdYqDXAmyWsXpvrQN1irx/eSANDUEnAPGuJf/gwjndpJ4PRggS7Q+OksLBoV9jGyprGhQJBAMDbkSzeQGELKVjv6FoAijsmzVnhsBDEpsQO3SufL1AZ3W0rtzu5AquWJh+0xcWYqtQZzGqCvA3vgJ5HNZD7bYo=";

    @Test
    public void md5Test(){
        System.out.println(Md5Util.encode(UUID.randomUUID().toString()));
    }

    /**
     * 测试生成秘钥对
     */
    @Test
    public void testOne() throws Exception {
        Map<String, String> keyMap = RsaUtil.generateKey(1024);
        String publicKey = keyMap.get(RsaUtil.PUBLIC_KEY);
        String privateKey = keyMap.get(RsaUtil.PRIVATE_KEY);
        System.out.println("公钥："+publicKey);
        System.out.println("私钥："+privateKey);
    }

    /**
     * 测试数字签名
     */
    @Test
    public void testTwo() throws Exception {
        String data = "他日若遂凌云志, 敢笑黄巢不丈夫";

        // 生成数字签名
        String sign = RsaUtil.signature(data, privateKey);
        System.out.println("数字签名后："+sign);

        // 校验数字签名
        boolean resultOne = RsaUtil.verifySignature(data, publicKey, sign);
        System.out.println("校验数字签名结果："+resultOne);

        // 校验数字签名
        boolean resultTwo = RsaUtil.verifySignature("仰天大笑出门去, 我辈岂是蓬蒿人", publicKey, sign);
        System.out.println("校验数字签名结果："+resultTwo);
    }


    /**
     * 这个方法可以告诉你为啥需要分段加解密操作
     */
    @Test
    public void testThree() throws Exception {
        String data = dataJson();

        // 公钥加密
        String s1 = RsaUtil.encryptByPublicKey(data, publicKey);
        System.out.println("公钥加密："+s1);

        // 私钥解密
        String s2 = RsaUtil.decryptByPrivateKey(s1, privateKey);
        System.out.println("私钥解密："+s2);
    }


    /**
     * 测试分段加密：公钥加密, 私钥解密
     */
    @Test
    public void testFour() throws Exception {
        String data = dataJson();

        // 公钥加密
        String s1 = RsaUtil.encryptByPublicKeyWithFragment(data, publicKey);
        System.out.println("公钥加密："+s1);

        // 私钥解密
        String s2 = RsaUtil.decryptByPrivateKeyWithFragment(s1, privateKey);
        System.out.println("私钥解密："+s2);
    }


    /**
     * 测试分段加密：私钥加密, 公钥解密
     */
    @Test
    public void testFive() throws Exception {
        String data = dataJson();

        // 私钥加密
        String s1 = RsaUtil.encryptByPrivateKeyWithFragment(data, privateKey);
        System.out.println("私钥加密："+s1);

        // 公钥解密
        String s2 = RsaUtil.decryptByPublicKeyWithFragment(s1, publicKey);
        System.out.println("公钥解密："+s2);
    }


    /**
     * 测试全量加解密
     */
    @Test
    public void testSix() throws Exception {
        String data = "仰天大笑出门去,我辈岂是蓬蒿人";

        String s1 = RsaUtil.encryptByPrivateKey(data, privateKey);
        String s2= RsaUtil.decryptByPublicKey(s1, publicKey);
        System.out.println("私钥加密："+s1);
        System.out.println("公钥解密："+s2);

        System.out.println();

        String s3 = RsaUtil.encryptByPublicKey(data, publicKey);
        String s4 = RsaUtil.decryptByPrivateKey(s3, privateKey);
        System.out.println("公钥加密："+s3);
        System.out.println("私钥解密："+s4);
    }


    /**
     * 数据项
     */
    private String dataJson(){
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String,Object>> list = new ArrayList<>(4);
        for( int i=0; i<4; i++ ){
            Map<String,Object> map = new HashMap<>(5);
            map.put("mobile","123321");
            map.put("source","KUSOG");
            map.put("type","INPUT");
            map.put("bank","中国银行");
            map.put("bankNo","123");
            list.add(map);
        }
        try {
            return objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
