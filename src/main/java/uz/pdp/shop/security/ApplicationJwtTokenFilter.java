package uz.pdp.shop.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.pdp.shop.config.SecurityConfig;
import uz.pdp.shop.entity.role.RoleDatabase;
import uz.pdp.shop.entity.user.UserDatabase;
import uz.pdp.shop.service.auth.AuthService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ApplicationJwtTokenFilter extends OncePerRequestFilter {

    @Value("${jwt.secret.key}")
    private String secretKey;

    private final AuthService authService;

    @Autowired
    public ApplicationJwtTokenFilter(AuthService authService) {
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String bearerToken = httpServletRequest.getHeader("Authorization");

        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        String token = bearerToken.replace("Bearer ", "");

        if (!isValidToken(token)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        String phoneNumber = getClaims(token).getSubject();
        UserDetails userDetails = authService.loadUserByUsername(phoneNumber);
        UserDatabase user = (UserDatabase) userDetails;
        List<RoleDatabase> userRoles = user.getRoles();

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        phoneNumber,
                        null,
                        getUserAuthorities(userRoles)
                    );

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isValidToken(String token) {
        Claims claims = getClaims(token);
        Date expiryDate = claims.getExpiration();

        return expiryDate.getTime() > new Date().getTime();
    }


    private List<SimpleGrantedAuthority> getUserAuthorities(
            List<RoleDatabase> userRoles
    ) {
        List<SimpleGrantedAuthority> permissions = new ArrayList<>();
        userRoles
                .forEach(roleDatabase -> {
                    roleDatabase.getUserRole().getPermissions()
                            .forEach(userPermission -> {
                                SimpleGrantedAuthority simpleGrantedAuthority
                                        = new SimpleGrantedAuthority(userPermission.name());
                                permissions.add(simpleGrantedAuthority);
                            });
                });
        return permissions;

    }


}
