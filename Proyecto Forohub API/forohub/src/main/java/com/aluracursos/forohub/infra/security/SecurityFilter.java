package com.aluracursos.forohub.infra.security;

import com.aluracursos.forohub.domain.user.UsuarioRepository;
import com.aluracursos.forohub.infra.errors.JWTException;
import com.aluracursos.forohub.infra.security.jwt.TokenService;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.bind.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            var token = authHeader.substring(7);

            try {

                var emailDeUsuario = tokenService.getSubject(token);

                if (emailDeUsuario != null && tokenService.isTokenValid(token)) {

                    UserDetails usuario = usuarioRepository.findByEmail(emailDeUsuario);
                    var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            } catch (JWTException e) {
                throw new JWTException(e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }

}
