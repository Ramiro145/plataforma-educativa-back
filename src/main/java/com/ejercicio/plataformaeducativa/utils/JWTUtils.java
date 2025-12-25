package com.ejercicio.plataformaeducativa.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JWTUtils {

    @Value("${security.jwt.private.key}")
    private String privateKey;

    @Value("${security.jwt.user.generator}")
    private String userGenerator;


    //creacion de tokens
    public String createToken (Authentication authentication) {


        Algorithm algorithm = Algorithm.HMAC256(privateKey);

        //esto esta en el context holder al pasar filtros de seguridad
        String username = authentication.getPrincipal().toString();

        //me da roles y permisos separados por coma en un string
        String authorities = authentication.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String jwtToken = JWT.create()
                //usuario usado para generar tokens
                .withIssuer(userGenerator)
                //usuario que ingresa
                .withSubject(username)
                //claims que queremos definir
                .withClaim("authorities", authorities)
                .withIssuedAt(new Date())
                //30 min a miliseg
                .withExpiresAt(new Date(System.currentTimeMillis() + (30 * 60000)))
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(new Date(System.currentTimeMillis()))
                .sign(algorithm);

        return jwtToken;
    }


    //verificacion y validacion de tokens

    public DecodedJWT validateToken (String token){
        try {

            Algorithm algorithm = Algorithm.HMAC256(privateKey);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(userGenerator)
                    .build();

            //si sale bien decodifica el token

            DecodedJWT decodedJWT = verifier.verify(token);

            return decodedJWT;

        }catch (JWTVerificationException exception){
            throw new JWTVerificationException("Invalid token. Not authorized");
        }
    }

    //extraer el username de token

    public String exctractUsername (DecodedJWT decodedJWT){
        return decodedJWT.getSubject().toString();
    }

    //extraer claim especifica (definida previamente al generar el token)

    public Claim getSpecificClaim (DecodedJWT decodedJWT, String claimName){
        return decodedJWT.getClaim(claimName);
    }

    //extraer todas las claims

    public Map<String, Claim> returnAllClaims (DecodedJWT decodedJWT){
        return decodedJWT.getClaims();
    }
}
