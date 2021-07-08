package uz.pdp.shop.bot;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.pdp.shop.bot.category.CategoryServiceBot;
import uz.pdp.shop.bot.order_details.OrderDetailsServiceBot;
import uz.pdp.shop.bot.order_product.OrderProductServiceBot;
import uz.pdp.shop.bot.product.ProductServiceBot;
import uz.pdp.shop.bot.user.UserServiceBot;
import uz.pdp.shop.service.category.CategoryService;
import uz.pdp.shop.service.product.ProductService;

import java.util.ArrayList;
import java.util.List;


public class Main extends TelegramLongPollingBot implements BaseBotService {

    private Long userChatId;
    private String userMessage;

    private final CategoryService categoryService;
    private final ProductService productService;
    private final CategoryServiceBot categoryServiceBot;
    private final ProductServiceBot productServiceBot;
    private final UserServiceBot userServiceBot;
    private final OrderProductServiceBot orderProductServiceBot;
    private final OrderDetailsServiceBot orderDetailsServiceBot;

    public Main(
            CategoryService categoryService,
            ProductService productService,
            CategoryServiceBot categoryServiceBot,
            ProductServiceBot productServiceBot,
            UserServiceBot userServiceBot,
            OrderProductServiceBot orderProductServiceBot,
            OrderDetailsServiceBot orderDetailsServiceBot
    ) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.categoryServiceBot = categoryServiceBot;
        this.productServiceBot = productServiceBot;
        this.userServiceBot = userServiceBot;
        this.orderProductServiceBot = orderProductServiceBot;
        this.orderDetailsServiceBot = orderDetailsServiceBot;
    }


    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            if (update.getMessage().getContact() == null) {
                userChatId = update.getMessage().getChatId();
                String inputText = update.getMessage().getText();
                switch (inputText) {
                    case "/start":
                        userMessage = "Xush kelibsiz";
                        userServiceBot.addUser(userChatId);
                        menu();
                        break;
                    case "Buy product":
                        InlineKeyboardMarkup categoryList = categoryServiceBot.getCategoryList(0);
                        userMessage = "Kerakli Kategoryani tanlang";
                        execute(null, categoryList);
                        break;
                    case "My Orders":
                        break;
                    case "Savatcha":

                        break;
                }
            }else {
                Contact contact = update.getMessage().getContact();
                Long chatId = update.getMessage().getChatId();
                boolean isAccepted = userServiceBot.addUserOrderAccept(contact, chatId);
                if (isAccepted)
                    userMessage="Buyurtmangiz qabul qilindi";
                else
                    userMessage = "Hurmatli xaridor, sizda hozircha tanlangan mahsulotlar yo'q!";
                execute(null,null);
            }
        } else if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();
            userChatId = update.getCallbackQuery().getMessage().getChatId();

            if (isProduct(data)) {
                SendPhoto sendPhoto = productServiceBot.sendPhoto(Integer.parseInt(data.substring(1)));
                sendPhoto.setChatId(userChatId);
                execute(sendPhoto);
            } else if (isCategory(data)) {
                InlineKeyboardMarkup categoryList
                        = categoryServiceBot.getCategoryList(Integer.parseInt(data.substring(1)));
                if (categoryList.getKeyboard().isEmpty()) {
                    InlineKeyboardMarkup productList
                            = productServiceBot.getProductListByCategoryId(Integer.valueOf(data.substring(1)));
                    userMessage = "Kerakli productni tanlang";
                    execute(null, productList);
                } else {
                    userMessage = "Kerakli Kategoryani tanlang";
                    execute(null, categoryList);
                }
            } else if (isOrderProduct(data)) {
                int productId = Integer.parseInt(data.substring(3, data.length() - 1));
                this.userMessage = orderProductServiceBot.addOrderProduct(
                        userChatId,
                        productId,
                        Integer.parseInt(data.substring(data.length() - 1))
                );
                execute(null, orderDetailsServiceBot.getOrderButtons(productId));
            } else if (isBackButton(data)) {
                userMessage = "Maxsulot tanlang";
                InlineKeyboardMarkup prevMenuList = productServiceBot.getPrevMenuList(Integer.parseInt(data.substring(4)));
                execute(null, prevMenuList);
            } else if (isAcceptButton(data)) {
                userMessage = "Buyurtmani tasdiqlash uchun raqamingizni yuboring";
                ReplyKeyboardMarkup contact = userServiceBot.getContact();
                execute(contact, null);
            }
        }

    }

    @Override
    public String getBotUsername() {
        return "http://t.me/g9_shop_bot";
    }

    @Override
    public String getBotToken() {
        return "1886663380:AAEsVCmqtAJ5hGZk7pil3VToovtPbW2M1VU";
    }


    private void execute(
            ReplyKeyboardMarkup replyKeyboardMarkup,
            InlineKeyboardMarkup inlineKeyboardMarkup
    ) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(userChatId);
        sendMessage.setText(userMessage);

        if (replyKeyboardMarkup != null) {
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            replyKeyboardMarkup.setResizeKeyboard(true);
            replyKeyboardMarkup.setOneTimeKeyboard(true);
            replyKeyboardMarkup.setSelective(true);
        }
        if (inlineKeyboardMarkup != null)
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public ReplyKeyboardMarkup menu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        keyboardRow.add("Buy product");
        keyboardRow1.add("My Orders");
        keyboardRow1.add("Cart");
        keyboardRows.add(keyboardRow);
        keyboardRows.add(keyboardRow1);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        execute(replyKeyboardMarkup, null);

        return replyKeyboardMarkup;
    }


}
