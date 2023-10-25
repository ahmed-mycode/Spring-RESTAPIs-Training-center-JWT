package com.center.constant;

public class JWUtil {

    public static final long EXPIRE_ACCESS_TOKEN = 10 * 60 * 1000;

    public static final long EXPIRE_REFRESH_TOKEN = 120 * 60 * 1000;

    public static final String ISSUER = "SpringBootApplication";

    public static final String SECRET = "MyPrivateSecret";

    //It is a prefix that a frontend application sends before the token
    public static final String BEARER_PREFIX = "Bearer ";

    public static final String AUTH_HEADER = "Authorization";
}
