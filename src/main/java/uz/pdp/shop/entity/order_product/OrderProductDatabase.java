package uz.pdp.shop.entity.order_product;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import uz.pdp.shop.entity.user.UserDatabase;
import uz.pdp.shop.repository.ProductRepository;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class OrderProductDatabase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private UserDatabase userDatabase;

    @OneToMany(fetch = FetchType.LAZY)
    List<OrderProductCountDatabase> orderProductCountDatabases;

    private int stateId;

    private BigDecimal totalSum = BigDecimal.valueOf(0);

    public String getOrderStatus() {
        if (stateId == OrderProductState.CREATED.getStateId())
            return OrderProductState.CREATED.getMessage();
        else if (stateId == OrderProductState.CHECK.getStateId())
            return OrderProductState.CHECK.getMessage();
        else if (stateId == OrderProductState.IN_PROCESSING.getStateId())
            return OrderProductState.IN_PROCESSING.getMessage();
        else if (stateId == OrderProductState.SUCCESS.getStateId())
            return OrderProductState.SUCCESS.getMessage();
        else if (stateId == OrderProductState.ACCEPTED.getStateId()){
            return OrderProductState.ACCEPTED.getMessage();
        }
        else
            return OrderProductState.CANCEL.getMessage();
    }

    //public BigDecimal getTotalSum(List<OrderProductCountDatabase> orderProductCountDatabases){ //  umumiy summani hisoblayabmiz
//
    //     BigDecimal sum = BigDecimal.valueOf(0);
    //     orderProductCountDatabases.forEach(orderProductCountDatabase -> {
    //
    //     });
    //
    //     return sum;
    // }

}
