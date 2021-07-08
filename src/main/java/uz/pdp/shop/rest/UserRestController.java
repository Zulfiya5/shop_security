package uz.pdp.shop.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.shop.entity.user.UserDatabase;
import uz.pdp.shop.model.receive.user.UserReceiveModel;
import uz.pdp.shop.model.response.base.BaseResponse;
import uz.pdp.shop.repository.UserRepository;
import uz.pdp.shop.service.user.UserService;

import java.util.List;

@RestController
@RequestMapping("api/shop/user")
public class UserRestController {

    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public UserRestController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/add")
    public BaseResponse addUser(
            @RequestBody UserReceiveModel userReceiveModel
    ) {
        return userService.addUser(userReceiveModel);
    }

    @PreAuthorize("hasAnyAuthority('ALL','READ')")
    @GetMapping("/list")
    public List<UserDatabase> getUserList(){
        return  userRepository.findAll();
    }
}
