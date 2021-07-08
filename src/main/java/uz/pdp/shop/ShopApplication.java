package uz.pdp.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import uz.pdp.shop.bot.Main;
import uz.pdp.shop.bot.category.CategoryServiceBot;
import uz.pdp.shop.bot.order_details.OrderDetailsServiceBot;
import uz.pdp.shop.bot.order_product.OrderProductServiceBot;
import uz.pdp.shop.bot.product.ProductServiceBot;
import uz.pdp.shop.bot.user.UserServiceBot;
import uz.pdp.shop.entity.role.RoleDatabase;
import uz.pdp.shop.entity.role.UserRole;
import uz.pdp.shop.entity.user.UserDatabase;
import uz.pdp.shop.repository.RoleRepository;
import uz.pdp.shop.repository.UserRepository;
import uz.pdp.shop.service.category.CategoryService;
import uz.pdp.shop.service.product.ProductService;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
public class ShopApplication implements WebMvcConfigurer, CommandLineRunner {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @Autowired
    CategoryServiceBot categoryServiceBot;

    @Autowired
    ProductServiceBot productServiceBot;

    @Autowired
    UserServiceBot userServiceBot;

    @Autowired
    OrderProductServiceBot orderProductServiceBot;

    @Autowired
    OrderDetailsServiceBot orderDetailsServiceBot;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;


    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        telegramBotsApi.registerBot(new Main(
                categoryService,
                productService,
                categoryServiceBot,
                productServiceBot,
                userServiceBot,
                orderProductServiceBot,
                orderDetailsServiceBot
        ));

/*
        RoleDatabase superAdminRole
                = roleRepository.findByUserRole(UserRole.SUPER_ADMIN);
        UserDatabase superAdmin = new UserDatabase();
        superAdmin.setPhoneNumber("00000");
        superAdmin.setRoles(Collections.singletonList(superAdminRole));
        superAdmin.setPassword(passwordEncoder.encode("super123"));
        userRepository.save(superAdmin);*/
    }
}
