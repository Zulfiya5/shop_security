package uz.pdp.shop.mvc.admin.controller.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.shop.repository.OrderProductRepository;

@Controller
@RequestMapping("/")
public class HomeAdminController {

    private final OrderProductRepository orderProductRepository;

    @Autowired
    public HomeAdminController(OrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }

    @GetMapping()
    public String getHomePage(){
        return "home/home";
    }

    @GetMapping("admin")
    public String getAdminPage(
            Model model
    ){
        return "/login/login";


       /* model.addAttribute("orderProductList", orderProductRepository.findAll());
        return "admin/orders/orders";*/
    }

}
