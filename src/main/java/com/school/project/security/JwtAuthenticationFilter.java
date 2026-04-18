package com.school.project.security;

import com.school.project.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        //1. Verifica se tem token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request,response);
            return;
        }

        //2. Extrai o token
        String token = authHeader.substring(7);

        try {
            //3. Valida token e pega username
            String username = jwtService.extractUsername(token);

            //4. Cria autenticação
            String role = jwtService.extractRole(token);

            var authority = new SimpleGrantedAuthority("ROLE_"+ role);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, List.of(authority));

            //5. Coloca no contexto do Spring
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            // Token inválido -> ignora (ou poderia retornar erro)
        }

        filterChain.doFilter(request,response);
    }
}
