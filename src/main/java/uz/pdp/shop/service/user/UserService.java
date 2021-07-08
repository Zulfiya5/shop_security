package uz.pdp.shop.service.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.shop.entity.role.UserRole;
import uz.pdp.shop.entity.user.UserDatabase;
import uz.pdp.shop.model.receive.user.UserReceiveModel;
import uz.pdp.shop.model.response.base.BaseResponse;
import uz.pdp.shop.model.response.base.ResponseStatus;
import uz.pdp.shop.repository.RoleRepository;
import uz.pdp.shop.repository.UserRepository;

import java.util.Collections;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public BaseResponse addUser(
            UserReceiveModel userReceiveModel
    ) {
        UserDatabase userDatabase = new UserDatabase();
        userDatabase.setName(userReceiveModel.getName());
        userDatabase.setAge(userReceiveModel.getAge());
        userDatabase.setEmail(userReceiveModel.getEmail());
        userDatabase.setPhoneNumber(userReceiveModel.getPhoneNumber());
        userDatabase.setPassword(passwordEncoder.encode(userReceiveModel.getPassword()));
        setUserRole(userReceiveModel, userDatabase);
        userRepository.save(userDatabase);

        return ResponseStatus.SUCCESS_REST;
    }

    private void setUserRole(
            UserReceiveModel userReceiveModel,
            UserDatabase userDatabase
    ) {
        if (userReceiveModel.getUserRole() == null) // user default holatda USER rolini beramiz, agar u bizni ishchimiz bolmasa
            userDatabase.setRoles(Collections.singletonList(
                    roleRepository.findByUserRole(UserRole.USER)
            ));
        else
            userDatabase.setRoles(Collections.singletonList(
                    roleRepository.findByUserRole(userReceiveModel.getUserRole())
            ));
    }
}
