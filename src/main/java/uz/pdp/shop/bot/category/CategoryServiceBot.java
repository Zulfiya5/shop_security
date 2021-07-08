package uz.pdp.shop.bot.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import uz.pdp.shop.entity.category.CategoryDatabase;
import uz.pdp.shop.repository.CategoryRepository;
import uz.pdp.shop.service.category.CategoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryServiceBot {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;


    public InlineKeyboardMarkup getCategoryList(
            Integer id
    ) {

        List<CategoryDatabase> mainCategories = categoryRepository
                .findAll()
                .stream()
                .filter(
                        categoryDatabase -> (categoryDatabase.getParentId() == id))
                .collect(Collectors.toList());

        List<List<InlineKeyboardButton>> mainList = new ArrayList<>();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(mainList);

        List<InlineKeyboardButton> keyButtonRow = new ArrayList<>();
        int counter = 0;

        for (CategoryDatabase mainCategory : mainCategories) {
            keyButtonRow.add(new InlineKeyboardButton(mainCategory.getName())
                    .setCallbackData(
                            "c" + mainCategory.getId()
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


}
