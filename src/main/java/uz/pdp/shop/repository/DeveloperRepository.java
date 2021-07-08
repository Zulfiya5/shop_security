package uz.pdp.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.shop.entity.developer.DeveloperEntity;
import uz.pdp.shop.projection.DeveloperProject;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "developerList", path = "developer", excerptProjection = DeveloperProject.class)
public interface DeveloperRepository extends JpaRepository<DeveloperEntity, Integer> {
    List<DeveloperEntity> findByNameAndUsername(@Param("name") String name, @Param("username") String username);
}
