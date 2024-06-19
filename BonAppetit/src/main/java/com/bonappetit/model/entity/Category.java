package com.bonappetit.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity{
    @Column(unique = true,nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryEnum categoryName;
    private String description;
    @OneToMany(mappedBy = "category")
    private List<Recipe> recipes;

    public Category() {
        recipes = new ArrayList<>();
    }

    public Category(CategoryEnum categoryEnum, String description) {
        super();
        this.categoryName = categoryEnum;
        this.description = description;
    }

    public CategoryEnum getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(CategoryEnum categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
