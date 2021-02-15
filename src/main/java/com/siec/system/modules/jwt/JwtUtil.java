package com.siec.system.modules.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.Date;

public class JwtUtil {

    // 过期时间一小时
    public static final long EXPIRE_TIME = 60 * 60 * 1000;
            //24 * 60 * 30 * 1000;

    /**
     * 校验token是否正确
     *
     * @param token    密钥
     * @param username 登录名
     * @param password 密码
     * @return
     */
    public static boolean verify(String token, String username, String password) {
        try {
            //根据密码生成JWT效验器
            Algorithm algorithm = Algorithm.HMAC256(password);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .build();

            //效验TOKEN
            DecodedJWT jwt = verifier.verify(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取登录名
     *获得token中的信息无需secret解密也能获得
     * @param token
     * @return
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成token签名EXPIRE_TIME 分钟后过期
     *
     * @param username 用户名
     * @param password  用户密码
     * @return
     */
    public static String sign(String username, String password) {

        // 指定过期时间
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(password);
        // 附带username信息
        return JWT.create()
                .withClaim("username", username)
                .withExpiresAt(date)
                .sign(algorithm);

    }
}
