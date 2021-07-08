package uz.pdp.shop.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.pdp.shop.entity.user.UserDatabase;
import uz.pdp.shop.model.receive.user.UserSignInReceiveModel;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


public class ApplicationUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    //@Value("${jwt.secret.key}")
    private String secretKey = "HJAtS9ALVpOPTCXw5W0Ifx2sHcBwmTKNKaqTgwD4";

    //
    // @Value("${jwt.expiration.date}")
    private String expirationDate = "86_400_000L";


    public ApplicationUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();
        Authentication authentication = null;
        try {
            UserSignInReceiveModel userSignInReceiveModel
                    = objectMapper.readValue(request.getInputStream(), UserSignInReceiveModel.class);

            authentication = new UsernamePasswordAuthenticationToken(
                    userSignInReceiveModel.getUsername(),
                    userSignInReceiveModel.getPassword()
            );
            return authenticationManager.authenticate(authentication);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return authentication;
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException, ServletException {

        System.out.println(secretKey);

        UserDatabase userDatabase = (UserDatabase) authResult.getPrincipal();

        String token = Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 86_400_000L)) // 1 kun
                .setSubject(userDatabase.getUsername())
                .claim("authorities", authResult.getAuthorities())
                .compact();

        response.addHeader("token", "Bearer " + token);
    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
