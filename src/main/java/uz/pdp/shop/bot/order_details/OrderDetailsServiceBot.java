package uz.pdp.shop.bot.order_details;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import uz.pdp.shop.entity.order_product.OrderProductCountDatabase;
import uz.pdp.shop.entity.order_product.OrderProductDatabase;
import uz.pdp.shop.entity.order_product.OrderProductState;
import uz.pdp.shop.entity.product.ProductDatabase;
import uz.pdp.shop.entity.user.UserDatabase;
import uz.pdp.shop.repository.OrderProductRepository;
import uz.pdp.shop.repository.ProductRepository;
import uz.pdp.shop.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailsServiceBot {


    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderDetailsServiceBot(UserRepository userRepository, OrderProductRepository orderProductRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.orderProductRepository = orderProductRepository;
        this.productRepository = productRepository;
    }


    public String getCartOrders(
            UserDatabase userDatabase
    ) {
        StringBuilder result;
        Optional<OrderProductDatabase> optionalOrderProductDatabase =
                orderProductRepository.findByUserDatabaseAndStateId(userDatabase, OrderProductState.CREATED.getStateId());
        if (optionalOrderProductDatabase.isPresent()){
            OrderProductDatabase orderProductDatabase = optionalOrderProductDatabase.get();
            List<OrderProductCountDatabase> orderProductCountDatabases =
                    orderProductDatabase.getOrderProductCountDatabases();

            result = new StringBuilder("Savatcha : \n\n");
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < orderProductCountDatabases.size(); i++) {
                ProductDatabase productDatabase
                        = productRepository.findById(orderProductCountDatabases.get(i).getProductId()).get();
                list.add(
                        orderProductCountDatabases.get(i).getProductOrderCount() + " * " +
                               productDatabase.getName() + "\n"
                );
                result.append(list.get(i));
            }
        }else
            result = new StringBuilder("Sizda hali buyurtma mavjud emas!");
        return result.toString();
    }
    public InlineKeyboardMarkup getOrderButtons(
            int productId
    ){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> list = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();
        inlineKeyboardButtons.add(new InlineKeyboardButton().setText("Orqaga").setCallbackData("back"+productId));
        inlineKeyboardButtons.add(new InlineKeyboardButton().setText("Tasdiqlash").setCallbackData("accept"));
        list.add(inlineKeyboardButtons);
        return inlineKeyboardMarkup.setKeyboard(list);
    }
}
