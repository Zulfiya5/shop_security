package uz.pdp.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shop.entity.order_product.OrderProductCountDatabase;

public interface OrderProductCountRepository extends JpaRepository<OrderProductCountDatabase, Integer> {

}
