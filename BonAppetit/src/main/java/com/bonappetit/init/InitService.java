package com.bonappetit.init;

import com.bonappetit.model.entity.Category;
import com.bonappetit.model.entity.CategoryEnum;
import com.bonappetit.repo.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class InitService implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final Map<CategoryEnum,String> categoriesMap = Map.of(
            CategoryEnum.DESSERT,"Sweet finale, indulgent and delightful; dessert crowns the dining experience with joy.",
            CategoryEnum.COCKTAIL,"Sip of sophistication, cocktails blend flavors, creating a spirited symphony in every glass.",
            CategoryEnum.MAIN_DISH, "Heart of the meal, substantial and satisfying; main dish delights taste buds."

    );

    public InitService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(categoryRepository.count() > 0){
            return;
        }
        List<Category> categoryList = Arrays.stream(CategoryEnum.values())
                .map(c -> new Category(c,categoriesMap.get(c)))
                .collect(Collectors.toList());
        this.categoryRepository.saveAllAndFlush(categoryList);
    }
}
