package uz.pdp.shop.mvc.admin.controller.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.pdp.shop.entity.order_product.OrderProductDatabase;
import uz.pdp.shop.model.response.base.BaseAdminResponse;
import uz.pdp.shop.model.response.base.BaseResponse;
import uz.pdp.shop.model.response.base.ResponseStatus;
import uz.pdp.shop.repository.OrderProductRepository;

import java.util.Optional;

@Controller
@RequestMapping("/api/admin/orders")
public class OrderAdminController {

    private final OrderProductRepository orderProductRepository;

    @Autowired
    public OrderAdminController(OrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }

    @GetMapping("/state/change")
    public BaseResponse editOrderStatus(
            @RequestParam("state_id") int stateId,
            @RequestParam("order_id") int orderId
    ) {
        Optional<OrderProductDatabase> optionalOrderProductDatabase
                = orderProductRepository.findById(orderId);

        if (optionalOrderProductDatabase.isEmpty())
            return ResponseStatus.UNKNOWN_ERROR_ADMIN;

        OrderProductDatabase orderProductDatabase
                = optionalOrderProductDatabase.get();

        orderProductDatabase.setStateId(stateId);
        orderProductRepository.save(orderProductDatabase);

        return ResponseStatus.SUCCESS_ADMIN;


    }

}
