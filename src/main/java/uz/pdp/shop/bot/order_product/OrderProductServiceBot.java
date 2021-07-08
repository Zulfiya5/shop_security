package uz.pdp.shop.bot.order_product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.shop.bot.order_details.OrderDetailsServiceBot;
import uz.pdp.shop.entity.order_product.OrderProductCountDatabase;
import uz.pdp.shop.entity.order_product.OrderProductDatabase;
import uz.pdp.shop.entity.order_product.OrderProductState;
import uz.pdp.shop.entity.product.ProductDatabase;
import uz.pdp.shop.entity.user.UserDatabase;
import uz.pdp.shop.repository.OrderProductCountRepository;
import uz.pdp.shop.repository.OrderProductRepository;
import uz.pdp.shop.repository.ProductRepository;
import uz.pdp.shop.repository.UserRepository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class OrderProductServiceBot {

    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;
    private final UserRepository userRepository;
    private final OrderProductCountRepository orderProductCountRepository;
    private final OrderDetailsServiceBot orderDetailsServiceBot;

    @Autowired
    public OrderProductServiceBot(ProductRepository productRepository, OrderProductRepository orderProductRepository, UserRepository userRepository, OrderProductCountRepository orderProductCountRepository, OrderDetailsServiceBot orderDetailsServiceBot) {
        this.productRepository = productRepository;
        this.orderProductRepository = orderProductRepository;
        this.userRepository = userRepository;
        this.orderProductCountRepository = orderProductCountRepository;
        this.orderDetailsServiceBot = orderDetailsServiceBot;
    }

    public String addOrderProduct(
            long chatId,
            int productId,
            int orderCount
    ) {
        ProductDatabase productDatabase = productRepository.getOne(productId); // productni bazamizdan oldik
        UserDatabase userDatabase = userRepository.findByChatId(chatId).get(); // userni bazamizdan oldik
        Optional<OrderProductDatabase> optionalOrderProductDatabase
                = orderProductRepository.findByUserDatabaseAndStateId(userDatabase, OrderProductState.CREATED.getStateId());

        if (optionalOrderProductDatabase.isPresent()) {
            OrderProductDatabase orderProductDatabase = optionalOrderProductDatabase.get();
            boolean helper = hasProductInOrderProduct(orderProductDatabase, productId, orderCount);

            if (!helper)
                addNewProductToOrderProduct(orderProductDatabase,productId,orderCount);
            getTotalSum(orderProductDatabase,productDatabase,orderCount);
            orderProductRepository.save(orderProductDatabase);
        } else {
            addNewOrder(userDatabase,productDatabase, productId, orderCount);
        }

        return orderDetailsServiceBot.getCartOrders(userDatabase);
    }

    /**
     * yangi order yaratish
     * @param userDatabase
     * @param productId
     * @param orderCount
     */
    private void addNewOrder(
            UserDatabase userDatabase,
            ProductDatabase productDatabase,
            int productId,
            int orderCount
    ){
        OrderProductDatabase orderProductDatabase = new OrderProductDatabase();
        orderProductDatabase.setStateId(OrderProductState.CREATED.getStateId());
        orderProductDatabase.setUserDatabase(userDatabase);

        OrderProductCountDatabase orderProductCountDatabase = new OrderProductCountDatabase();
        orderProductCountDatabase.setProductOrderCount(orderCount);
        orderProductCountDatabase.setProductId(productId);
        orderProductCountRepository.save(orderProductCountDatabase);

        orderProductDatabase.setOrderProductCountDatabases(Collections.singletonList(orderProductCountDatabase));
        getTotalSum(orderProductDatabase,productDatabase,orderCount);
        orderProductRepository.save(orderProductDatabase);
    }

    /**
     * order bor va ichida product ham bor shuni sonini update qildik
     * @param orderProductDatabase
     * @param productId
     * @param orderCount
     * @return
     */
    private boolean hasProductInOrderProduct(
            OrderProductDatabase orderProductDatabase,
            int productId,
            int orderCount
    ){
        List<OrderProductCountDatabase> orderProductCountDatabases
                = orderProductDatabase.getOrderProductCountDatabases();

        AtomicBoolean helper = new AtomicBoolean(false);
        orderProductCountDatabases.forEach((orderProductCountDatabase)->{
            if (orderProductCountDatabase.getProductId() == productId) {
                orderProductCountDatabase.setProductOrderCount(
                        orderProductCountDatabase.getProductOrderCount() + orderCount
                );
                orderProductCountRepository.save(orderProductCountDatabase);
                helper.set(true);
            }
        });

        return helper.get();
    }

    /**
     * order bor va ichida product yoq shunga yangi product qoshdik
     * @param orderProductDatabase
     * @param productId
     * @param orderCount
     */
    private void addNewProductToOrderProduct(
            OrderProductDatabase orderProductDatabase,
            int productId,
            int orderCount
    ){
        OrderProductCountDatabase orderProductCountDatabase
                = new OrderProductCountDatabase();
        orderProductCountDatabase.setProductOrderCount(orderCount);
        orderProductCountDatabase.setProductId(productId);
        orderProductCountRepository.save(orderProductCountDatabase);
        List<OrderProductCountDatabase> orderProductCountDatabases1
                = orderProductDatabase.getOrderProductCountDatabases();
        orderProductCountDatabases1.add(orderProductCountDatabase);
    }

    private void getTotalSum(
            OrderProductDatabase orderProductDatabase,
            ProductDatabase productDatabase,
            int orderCount
    ){
        BigDecimal helper = productDatabase.getPrice().multiply(BigDecimal.valueOf(orderCount));
        orderProductDatabase.setTotalSum(orderProductDatabase.getTotalSum().add(helper));
    }

}

