package com.school.project.service;


import com.school.project.config.JwtConfig;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.PrivilegedAction;
import java.util.Date;

@Service
public class JwtService { //Classe responsável por criar tokens


    private final JwtConfig jwtConfig;

    public JwtService(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String extractRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }

    public String generateToken(String username, String role) { //Recebe o username do usuario e retorna um token JWT
        return Jwts.builder() // Inicia a contrução do JWT
                .setSubject(username) // Define quem é o "dono" do token
                .claim("role", role) // Define a role que o token irá carregar
                .setIssuedAt(new Date()) // Quando o token foi criado
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) //Define quando o token expira no caso ai é 1 hr a explicação da conta é o seguinte: 1000ms = 1s *60 = 1min*60 = 1hr
                .signWith(getKey(), SignatureAlgorithm.HS256) // Isso aqui assina o token, garante que o token não foi alterado sem ser detectado e so quem tem o SECRET pode gerar um válido o HS256 é um algoritmo de assinatura (hash+chave)
                .compact(); //Finaliza a construção do token e gera ele

                /* Estrutura do JWT : HEADER.PAYLOAD.SIGNATURE
                HEADER -> {
                                "alg": "HS256",
                                "typ": "JWT"
                           }
                PAYLOAD -> {
                                "sub": "ian",
                                "iat": 1710000000,
                                "exp": 1710003600
                 */
    }
}
