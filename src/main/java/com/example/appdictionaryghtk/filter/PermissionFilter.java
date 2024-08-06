package com.example.appdictionaryghtk.filter;

import com.example.appdictionaryghtk.entity.Permission;
import com.example.appdictionaryghtk.entity.User;
import com.example.appdictionaryghtk.service.permission.IPermissionService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class PermissionFilter extends OncePerRequestFilter {
    private final IPermissionService permissionService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestUri = request.getRequestURI();
        String requestMethod = request.getMethod();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            User user = (User) principal;
            Set<Permission> permissions = permissionService.getPermissionsByUser(user);

            boolean hasPermission = permissions.stream()
                    .anyMatch(permission -> requestUri.contains(permission.getPath())
                            && permission.getMethod().equalsIgnoreCase(requestMethod));

            if (!hasPermission) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Access Denied");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
