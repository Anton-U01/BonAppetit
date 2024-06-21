package com.bonappetit.service.impl;

import com.bonappetit.model.entity.Category;
import com.bonappetit.model.entity.CategoryEnum;
import com.bonappetit.model.entity.Recipe;
import com.bonappetit.model.entity.User;
import com.bonappetit.model.entity.dto.AddRecipeDto;
import com.bonappetit.repo.CategoryRepository;
import com.bonappetit.repo.RecipeRepository;
import com.bonappetit.repo.UserRepository;
import com.bonappetit.service.CurrentUser;
import com.bonappetit.service.RecipeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CurrentUser currentUser;
    public RecipeServiceImpl(RecipeRepository recipeRepository, ModelMapper modelMapper, CategoryRepository categoryRepository, UserRepository userRepository, CurrentUser currentUser) {
        this.recipeRepository = recipeRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.currentUser = currentUser;
    }

    @Override
    public boolean addNewRecipe(AddRecipeDto addRecipeDto) {
        Recipe recipe = modelMapper.map(addRecipeDto, Recipe.class);
        Optional<Category> optionalCategory = categoryRepository.findByCategoryName(CategoryEnum.valueOf(addRecipeDto.getCategory()));
        if(optionalCategory.isEmpty()){
            return false;
        }
        Category category = optionalCategory.get();
        recipe.setCategory(category);
        Optional<User> optionalUser = userRepository.findByUsername(currentUser.getUsername());
        if(optionalUser.isEmpty()){
            return false;
        }
        User author = optionalUser.get();
        recipe.setAddedBy(author);
        recipeRepository.saveAndFlush(recipe);

        return true;
    }
}
