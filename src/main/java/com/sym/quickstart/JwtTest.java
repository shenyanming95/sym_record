package com.sym.quickstart;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sym.util.DateUtil;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shenym
 * @date 2020/2/28 11:18
 */

public class JwtTest {

    private final static String TOKEN_SECRET = "Sym_secret";

    private String token;

    /**
     * 生成一个token
     */
    @Test
    public void createToken() {
        // 设置头部信息
        Map<String, Object> headerMap = new HashMap<>(4);
        headerMap.put("sign", "toBeNo.1");
        headerMap.put("id", "idi");

        // 用户信息
        String userInfo = "{666}";

        // 私钥和加密算法
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);

        // 过期时间
        Date expireDate = DateUtil.plusDays(new Date(), 1);

        // 创建token
        String token = JWT.create()
                .withHeader(headerMap)
                .withClaim("info", userInfo)
                .withExpiresAt(expireDate).sign(algorithm);
        System.out.println(token);
        this.token = token;
    }

    /**
     * 验证token
     */
    @Test
    public void verifyToken() {
        // 先创建token
        createToken();
        String rightToken = this.token;

        // 创建认证器
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        JWTVerifier verifier = JWT.require(algorithm).build();

        // 标识
        boolean isSuccess = false;

        try {
            verifier.verify(rightToken);
            isSuccess = true;
        } catch (AlgorithmMismatchException e) {
            System.err.println("加密算法不一致");
        } catch (SignatureVerificationException e) {
            System.err.println("token非法");
        } catch (TokenExpiredException e) {
            System.err.println("token过期");
        } catch (InvalidClaimException e) {
            System.err.println("附加内容不一致");
        }

        if(isSuccess){
            System.out.println("token认证通过");
        }
    }


    /**
     * 获取附加在token的额外信息
     */
    @Test
    public void getOtherMessage(){
        // 先创建token
        createToken();
        String rightToken = this.token;

        // 创建解析器
        DecodedJWT jwt = JWT.decode(token);
        String info = jwt.getClaim("info").asString();
        System.out.println(info);
    }
}
