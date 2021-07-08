package uz.pdp.shop.bot.product;

import com.google.common.io.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import uz.pdp.shop.bot.Main;
import uz.pdp.shop.entity.attachment.AttachmentDatabase;
import uz.pdp.shop.entity.attachment_content.AttachmentContentDatabase;
import uz.pdp.shop.entity.category.CategoryDatabase;
import uz.pdp.shop.entity.product.ProductDatabase;
import uz.pdp.shop.repository.AttachmentContentRepository;
import uz.pdp.shop.repository.AttachmentRepository;
import uz.pdp.shop.repository.CategoryRepository;
import uz.pdp.shop.repository.ProductRepository;
import uz.pdp.shop.service.product.ProductService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceBot {

    private ProductService productService;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final AttachmentContentRepository attachmentContentRepository;
    private final AttachmentRepository attachmentRepository;

    @Autowired
    public ProductServiceBot(ProductService productService, ProductRepository productRepository, CategoryRepository categoryRepository, AttachmentContentRepository attachmentContentRepository, AttachmentRepository attachmentRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.attachmentContentRepository = attachmentContentRepository;
        this.attachmentRepository = attachmentRepository;

    }

    public InlineKeyboardMarkup getProductListByCategoryId(
            Integer categoryId
    ) {
        List<List<InlineKeyboardButton>> mainList = new ArrayList<>();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(mainList);

        List<InlineKeyboardButton> keyButtonRow = new ArrayList<>();
        int counter = 0;
        CategoryDatabase category = categoryRepository.getOne(categoryId);
        List<ProductDatabase> productDatabaseList = productRepository.findAllByCategoryDatabase(category);


        for (ProductDatabase productDatabase : productDatabaseList) {
            keyButtonRow.add(new InlineKeyboardButton(productDatabase.getName())
                    .setCallbackData(
                            "p" + productDatabase.getId()
                    ));

            counter++;

            if (counter % 3 == 0) {
                mainList.add(keyButtonRow);
                keyButtonRow = new ArrayList<>();
            }

        }
        if (!keyButtonRow.isEmpty())
            mainList.add(keyButtonRow);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getPrevMenuList(int productId) {
        ProductDatabase productDatabase = productRepository.findById(productId).get();
        CategoryDatabase categoryDatabase = productDatabase.getCategoryDatabase();
        return getProductListByCategoryId(categoryDatabase.getId());
    }


    public SendPhoto sendPhoto(
            int productId
    ) throws IOException {

        Optional<ProductDatabase> optionalProductDatabase
                = productRepository.findById(productId);

        ProductDatabase productDatabase = optionalProductDatabase.get();
        List<AttachmentDatabase> attachmentDatabases
                = productDatabase.getAttachmentDatabases();


        if (attachmentDatabases.isEmpty())
            return null;

        AttachmentContentDatabase attachmentContentDatabase
                = attachmentDatabases.get(0).getAttachmentContentDatabase();

        InputStream inputStream
                = ByteSource.wrap(attachmentContentDatabase.getBytes()).openStream();

        SendPhoto sendPhoto
                = new SendPhoto().setPhoto(
                productDatabase.getName(),
                inputStream
        );

        String response = "Narxi: " + productDatabase.getPrice() + "\n" +
                "Izoh: " + productDatabase.getParams();

        sendPhoto.setCaption(response);
        sendPhoto.setReplyMarkup(getProductOrderCount(productId));

        return sendPhoto;

    }

    private InlineKeyboardMarkup getProductOrderCount(int productId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> list = new ArrayList<>();

        List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText(String.valueOf(i));
            inlineKeyboardButton.setCallbackData("o_p" + productId + i);

            inlineKeyboardButtons.add(inlineKeyboardButton);
            if (i % 3 == 0) {
                list.add(inlineKeyboardButtons);
                inlineKeyboardButtons = new ArrayList<>();
            }
        }
        inlineKeyboardMarkup.setKeyboard(list);

        return inlineKeyboardMarkup;
    }

}

