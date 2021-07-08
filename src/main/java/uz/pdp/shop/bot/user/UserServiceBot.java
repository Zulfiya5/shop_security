package uz.pdp.shop.bot.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import uz.pdp.shop.entity.order_product.OrderProductDatabase;
import uz.pdp.shop.entity.order_product.OrderProductState;
import uz.pdp.shop.entity.user.UserDatabase;
import uz.pdp.shop.repository.OrderProductRepository;
import uz.pdp.shop.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceBot {

    private final UserRepository userRepository;
    private final OrderProductRepository orderProductRepository;

    @Autowired
    public UserServiceBot(UserRepository userRepository, OrderProductRepository orderProductRepository) {
        this.userRepository = userRepository;
        this.orderProductRepository = orderProductRepository;
    }


    public void addUser(long chatId) {
        Optional<UserDatabase> optionalUserDatabase
                = userRepository.findByChatId(chatId);

        if (optionalUserDatabase.isEmpty()) {
            UserDatabase userDatabase = new UserDatabase();
            userDatabase.setChatId(chatId);
            userRepository.save(userDatabase);
        }

    }

    public ReplyKeyboardMarkup getContact() {

        ReplyKeyboardMarkup getContact = new ReplyKeyboardMarkup();
        getContact.setSelective(true);
        getContact.setResizeKeyboard(true);
        getContact.setOneTimeKeyboard(false);

        List<KeyboardRow> contact = new ArrayList<>();

        KeyboardRow contacts = new KeyboardRow();
        contacts.add(new KeyboardButton("\uD83D\uDC64Share your Contact").setRequestContact(true));

        contact.add(contacts);
        getContact.setKeyboard(contact);

        return getContact;

    }

    public boolean addUserOrderAccept(Contact contact, long chatId) {
        UserDatabase userDatabase = userRepository.findByChatId(chatId).get();
        Optional<OrderProductDatabase> optionalOrderProductDatabase =
                orderProductRepository.findByUserDatabaseAndStateId(userDatabase, OrderProductState.CREATED.getStateId());
        if (optionalOrderProductDatabase.isEmpty())
                return false;

        if (userDatabase.getPhoneNumber() == null) {
            userDatabase.setPhoneNumber(contact.getPhoneNumber());
            userDatabase.setName(contact.getLastName() + " " + contact.getFirstName());
            userRepository.save(userDatabase);
        }

        OrderProductDatabase orderProductDatabase = optionalOrderProductDatabase.get();
        orderProductDatabase.setStateId(OrderProductState.ACCEPTED.getStateId());
        orderProductRepository.save(orderProductDatabase);
        return true;
    }
}

