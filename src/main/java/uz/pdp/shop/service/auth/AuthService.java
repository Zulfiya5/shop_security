package uz.pdp.shop.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.shop.entity.user.UserDatabase;
import uz.pdp.shop.repository.UserRepository;

import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDatabase> optionalUserDatabase = userRepository.findByPhoneNumber(username);

        if (optionalUserDatabase.isEmpty())
            throw new UsernameNotFoundException(username + " is not found");

        return optionalUserDatabase.get();
    }
}
