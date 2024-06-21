package com.bonappetit.model.entity.dto;

import com.bonappetit.model.entity.Recipe;

public class RecipeDto {
    private long id;
    private String name;
    private String ingredients;

    public RecipeDto(Recipe recipe) {
        this.id = recipe.getId();
        this.name = recipe.getName();
        this.ingredients = recipe.getIngredients();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
}
