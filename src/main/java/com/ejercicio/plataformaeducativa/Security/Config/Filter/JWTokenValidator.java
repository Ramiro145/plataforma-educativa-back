package com.ejercicio.plataformaeducativa.Security.Config.Filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ejercicio.plataformaeducativa.utils.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

import org.springframework.http.HttpHeaders;

//filtro que sucede con cada request
public class JWTokenValidator extends OncePerRequestFilter {


    private JWTUtils jwtUtils;

    public JWTokenValidator(JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    protected void doFilterInternal (@NonNull HttpServletRequest request,
                                     @NonNull HttpServletResponse response,
                                     @NonNull FilterChain filterChain) throws IOException, ServletException {

        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(jwtToken != null){

            //eliminar lo de "bearer "

            jwtToken = jwtToken.substring(7);

            DecodedJWT decodedJWT = jwtUtils.validateToken(jwtToken);

            String username = jwtUtils.exctractUsername(decodedJWT);

            String authorities = jwtUtils.getSpecificClaim(decodedJWT,"authorities").asString();

            Collection<? extends GrantedAuthority> authoritiesList = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

            SecurityContext securityContext = SecurityContextHolder.getContext();
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authoritiesList);

            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);

        }

        filterChain.doFilter(request,response);



    }

}
