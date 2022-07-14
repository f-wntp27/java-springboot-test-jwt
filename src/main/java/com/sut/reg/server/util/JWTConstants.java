package com.sut.reg.server.util;

import javax.crypto.SecretKey;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JWTConstants {
    
    public static SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    
}
