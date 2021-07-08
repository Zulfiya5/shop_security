package uz.pdp.shop.mvc.admin.controller.user_role;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.shop.model.receive.user_role.UserRoleReceiveModel;
import uz.pdp.shop.service.user_role.UserRoleService;

@Controller
@RequestMapping("/api/admin/role")
public class UserRoleController {

    private final UserRoleService userRoleService;

    @Autowired
    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    /*@PostMapping("/add")
    public String addRole(
            @ModelAttribute UserRoleReceiveModel userRoleReceiveModel,
            Model model
    ) {
        boolean ans = userRoleService.addRole(userRoleReceiveModel);
        model.addAttribute("response",ans);
        return "admin/role/list";
    }*/
}
