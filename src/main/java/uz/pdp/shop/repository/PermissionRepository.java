package uz.pdp.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shop.entity.role.PermissionDatabase;

public interface PermissionRepository extends JpaRepository<PermissionDatabase,Integer> {

}
