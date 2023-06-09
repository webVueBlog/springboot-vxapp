package com.wxapp.app.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class JWTUtils {
    public static final String AUTH_HEADER_KEY = "Authorization";

    public static final String TOKEN_PREFIX = "Bearer ";

    //过期时间一周
    private static final long EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000;

    private static final String secret = "4s2dsb-1ds2-4r42-sss2-3cass23s3ff33650";

    /**
     * 验证 JWT token 是否合法
     *
     * @param token 待验证的 JWT token
     * @return 如果 token 合法，则返回 true；否则返回 false。
     */
    public static boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 创建一个 JWT 验证器
            JWTVerifier verifier = JWT.require(algorithm).build();
            // 使用验证器对 token 进行验证
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获取token中信息无需secret解密也能获得
     * @param token
     * @return token中包含的id
     */
    public static Long getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("id").asLong();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 签发token
     * @param id
     * @return 加密的token
     */
    public static String sign(Long id) {
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带username信息
            return JWT.create()
                    .withClaim("id", id)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
