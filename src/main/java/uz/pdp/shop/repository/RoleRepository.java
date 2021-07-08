package uz.pdp.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shop.entity.role.RoleDatabase;
import uz.pdp.shop.entity.role.UserRole;

public interface RoleRepository extends JpaRepository<RoleDatabase, Integer> {
    RoleDatabase findByUserRole(UserRole userRole);
}
