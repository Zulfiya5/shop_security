package uz.pdp.shop.mvc.admin.controller.user_role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.shop.entity.role.PermissionDatabase;
import uz.pdp.shop.service.user_role.UserPermissionService;

import java.util.List;

@Controller
@RequestMapping("/api/admin/permission")
public class UserPermissionController {

    private final UserPermissionService permissionService;

    @Autowired
    public UserPermissionController(UserPermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/list")
    private String  getPermissionList(Model model){
        List<PermissionDatabase> list = permissionService.list();

        model.addAttribute(list);

        return "admin/permission/list";
    }


}
