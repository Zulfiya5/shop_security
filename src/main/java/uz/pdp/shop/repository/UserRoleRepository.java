package uz.pdp.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shop.entity.role.RoleDatabase;
import uz.pdp.shop.model.receive.user_role.UserRoleReceiveModel;

public interface UserRoleRepository extends JpaRepository<RoleDatabase,Integer> {
}
