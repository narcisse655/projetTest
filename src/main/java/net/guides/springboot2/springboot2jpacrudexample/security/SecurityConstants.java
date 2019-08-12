package net.guides.springboot2.springboot2jpacrudexample.security;

public class SecurityConstants {

    public static final String SECRET = "narcisse@nyams.net";
    public static final long EXPIRATION_TIME = 864_000_000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization"; //nom du header où sera mis jwt token

}