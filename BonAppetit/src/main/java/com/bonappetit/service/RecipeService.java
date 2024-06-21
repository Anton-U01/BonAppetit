package com.bonappetit.service;

import com.bonappetit.model.entity.dto.AddRecipeDto;

public interface RecipeService {
    boolean addNewRecipe(AddRecipeDto addRecipeDto);
}
