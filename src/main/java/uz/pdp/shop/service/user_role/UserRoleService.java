package uz.pdp.shop.service.user_role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.shop.entity.role.PermissionDatabase;
import uz.pdp.shop.entity.role.RoleDatabase;
import uz.pdp.shop.model.receive.user_role.UserRoleReceiveModel;
import uz.pdp.shop.repository.PermissionRepository;
import uz.pdp.shop.repository.UserRoleRepository;

import java.util.List;

@Service
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;
    private final PermissionRepository permissionRepository;

    @Autowired
    public UserRoleService(UserRoleRepository userRepository, PermissionRepository permissionRepository) {
        this.userRoleRepository = userRepository;
        this.permissionRepository = permissionRepository;
    }


    /*public boolean addRole(UserRoleReceiveModel userRoleReceiveModel) {
        try {
            RoleDatabase roleDatabase = new RoleDatabase();
            roleDatabase.setUserRole(userRoleReceiveModel.getName());
            List<PermissionDatabase> permissionDatabases =
                    roleDatabase.getPermissionDatabases();
            permissionDatabases.add(permissionRepository.getOne(userRoleReceiveModel.getPermissionId()));
            userRoleRepository.save(roleDatabase);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }*/
}
