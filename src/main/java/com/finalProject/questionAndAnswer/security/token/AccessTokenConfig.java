package com.finalProject.questionAndAnswer.security.token;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.stereotype.Component;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

@Component
public class AccessTokenConfig {
    /**
     *      step 1 . create bean keyPair ( public key and private key )
     *      public key and private key it will store in memory
     *      Only application spring know where location of keyPair
     *      @return KeyPair
     */
    @Bean("accessTokenKeyPair")
    KeyPair accessTokenKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA"); // we use algorithm RSA that why we put "RSA"
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    /**
     *      step 2. create bean RSA key by using keyPair  by using bean accessTokenKeyPair in step 1
     *      @return RSAKey
     */
    @Bean("accessTokenRSAKey")
    RSAKey accessTokenRSAKey(@Qualifier("accessTokenKeyPair") KeyPair keyPair) {
        return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
                .privateKey(keyPair.getPrivate())
                .keyID(UUID.randomUUID().toString())
                .build();
    }

    /**
     *      step 3. Create bean JWKSource ( JSON Web Key Source ) by using bean accessTokenKeyPair in step 2
     *      @return JWKSource
     */
    @Bean("accessTokenJWKSource")
    JWKSource<SecurityContext> accessTokenJWKSource(@Qualifier("accessTokenRSAKey") RSAKey rsaKey) {
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext)
                -> jwkSelector.select(jwkSet);
    }

    /**
     *      step 4. Create bean JwtDecoder use RSA Public key for decoding by using bean accessTokenKeyPair in step 2
     *      explain: bean JwtDecoder is a skill for Decode JWT Token
     *      @return JwtDecoder
     */
    @Bean("accessTokenJwtDecoder")
    JwtDecoder accessTokenJwtDecoder(@Qualifier("accessTokenRSAKey") RSAKey rsaKey) throws JOSEException {
        return NimbusJwtDecoder
                .withPublicKey(rsaKey.toRSAPublicKey())
                .build();
    }

    /**
     *      step 5.create bean JSWKSource for encoding JWT Token  by using bean accessTokenJWKSource in step 3
     *      @return JwtEncoder
     */
    @Bean("accessTokenJwtEncoder")
    JwtEncoder accessTokenJwtEncoder(@Qualifier("accessTokenJWKSource") JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

    /**
     *   summery : spring application use JWT security
     *
     *         1. add dependencies
     *             implementation 'org.springframework.boot:spring-boot-starter-oauth2-resource-server' for gradle
     *             after add dependencies spring application will required bean JwtDecoder for decode JWT token
     *
     *         2. create class with name example: AccessTokenConfig.java
     *             and mark annotation @Component
     *             create bean KeyPair ===================> follow by step 1 (line 30) in this file
     *             create bean RSAKey required bean KeyPair ===================> follow by step 2 (line 41) in this file
     *             create bean JWKSource required bean RSAKey ===================> follow by step 3 (line 53) in this file
     *             bean JwtDecode required bean RSAKey ===================> follow by step 4 (line 65) in this file
     *             bean JwtEncoder required bean JWKSource  ===================> follow by step 5 (line 76) in this file
     *
     *             explain : bean JwtDecode required bean RSAKey
     *                       bean JwtEncoder required bean JWKSource
     *                       and
     *                       bean JWKSource required bean RSAKey
     *
     *         totally: step 1. create bean KeyPair
     *                  step 2. create bean RSAKey ---> required bean KeyPair
     *                  step 3. create bean JWKSource ---> required bean RSAKey
     *                  step 4. create bean JwtDecoder ---> required bean RSAKey
     *                  step 5. create bean JwtEncoder ---> required bean JWKSource
     */
}
