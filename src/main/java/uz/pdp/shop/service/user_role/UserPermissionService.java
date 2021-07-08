package uz.pdp.shop.service.user_role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.shop.entity.role.PermissionDatabase;
import uz.pdp.shop.repository.PermissionRepository;

import java.util.List;

@Service
public class UserPermissionService {

    private final PermissionRepository permissionRepository;

    @Autowired
    public UserPermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public List<PermissionDatabase> list(){
        return permissionRepository.findAll();
    }
}
