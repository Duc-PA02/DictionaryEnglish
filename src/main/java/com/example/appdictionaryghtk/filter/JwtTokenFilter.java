package com.example.appdictionaryghtk.filter;

import com.example.appdictionaryghtk.component.JwtTokenUtils;
import com.example.appdictionaryghtk.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    @Value("${api.prefix}")
    private String apiPrefix;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtils jwtTokenUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if(isBypassToken(request)) {
                filterChain.doFilter(request, response);
                return;
            }
            final String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "authHeader null or not started with Bearer");
                return;
            }
            final String token = authHeader.substring(7);
            final String username = jwtTokenUtil.extractUsername(token);
            if (username != null) {
                User userDetails = (User) userDetailsService.loadUserByUsername(username);
                if(jwtTokenUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    System.out.println("Auth at jwtfilter: " +SecurityContextHolder.getContext().getAuthentication());
                }
            }
            filterChain.doFilter(request, response); //enable bypass
        }catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(e.getMessage());
        }
    }
    private boolean isBypassToken(@NonNull HttpServletRequest request){
        final List<Pair<String, String>> bypassTokens = Arrays.asList(
                Pair.of("api/v1/auth/register", "POST"),
                Pair.of("api/v1/auth/login", "POST"),
                Pair.of("api/v1/auth/forgot-password", "POST"),
                Pair.of("api/v1/auth/reset-password", "POST"),
                Pair.of("english/search", "GET"),
                Pair.of("english/type", "GET"),
                Pair.of("english/home", "GET"),
                Pair.of("api/v1/searchWord/keyword", "GET"),
                Pair.of("api/v1/searchWord/user", "GET"),
                Pair.of("api/v1/searchWord/fuzzy", "GET"),
                Pair.of("api/v1/searchWord/save", "POST"),
                Pair.of("api/v1/translate", "POST"),
                Pair.of("api/v1/translate/language", "GET"),
                Pair.of("api/v1/chatAI/generate", "POST"),
                Pair.of("api/v1/translate", "POST")

        );
        for (Pair<String, String> bypassToken : bypassTokens){
            if (request.getServletPath().contains(bypassToken.getLeft()) && request.getMethod().equals(bypassToken.getRight())){
                return true;
            }
        }
        return false;
    }
}
