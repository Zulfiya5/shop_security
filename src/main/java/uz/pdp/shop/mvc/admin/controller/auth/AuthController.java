package uz.pdp.shop.mvc.admin.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.pdp.shop.security.ApplicationJwtTokenFilter;
import uz.pdp.shop.service.auth.AuthService;

@Controller
@RequestMapping("api/admin/auth")
public class AuthController {
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationJwtTokenFilter applicationJwtTokenFilter;


    @Autowired
    public AuthController(AuthService authService, PasswordEncoder passwordEncoder, ApplicationJwtTokenFilter applicationJwtTokenFilter) {
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
        this.applicationJwtTokenFilter = applicationJwtTokenFilter;
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ){
        UserDetails user = authService.loadUserByUsername(username);
        if(!passwordEncoder.encode(password).matches(user.getPassword()))
            return "/login/login";

       return "";
    }
}
