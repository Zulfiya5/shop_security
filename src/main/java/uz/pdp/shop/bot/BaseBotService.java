package uz.pdp.shop.bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public interface BaseBotService {
    default boolean isProduct(String data) {
        return data.startsWith("p");
    }
    default boolean isCategory(String data) {
        return data.startsWith("c");
    }
    default boolean isOrderProduct(String data) {
        return data.startsWith("o_p");
    }

    default boolean isCart(String data){
        return data.startsWith("cart");
    }
    default boolean isBackButton(String data){
        return data.startsWith("back");
    }
    default boolean isAcceptButton(String data){
        return data.startsWith("accept");
    }


}
