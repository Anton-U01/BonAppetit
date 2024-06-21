package com.bonappetit.service;

import com.bonappetit.model.entity.CategoryEnum;
import com.bonappetit.model.entity.Recipe;
import com.bonappetit.model.entity.dto.AddRecipeDto;

import java.util.List;

public interface RecipeService {
    boolean addNewRecipe(AddRecipeDto addRecipeDto);

    List<Recipe> findAllRecipesByCategoryName(CategoryEnum categoryEnum);

    void setRecipeAsFavourite(Long id,String username);

}
