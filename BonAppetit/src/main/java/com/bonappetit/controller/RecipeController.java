package com.bonappetit.controller;

import com.bonappetit.model.entity.dto.AddRecipeDto;
import com.bonappetit.service.CurrentUser;
import com.bonappetit.service.RecipeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RecipeController {
    private final RecipeService recipeService;
    private final CurrentUser currentUser;

    public RecipeController(RecipeService recipeService, CurrentUser currentUser) {
        this.recipeService = recipeService;
        this.currentUser = currentUser;
    }

    @ModelAttribute("addRecipe")
    public AddRecipeDto createRecipeDto(){
        return new AddRecipeDto();
    }
    @GetMapping("/recipe-add")
    public String viewAddRecipe(){
        return "recipe-add";
    }
    @PostMapping("/recipe-add")
    public String addNewRecipe(@Valid AddRecipeDto addRecipeDto,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes){
            if(bindingResult.hasErrors() || !recipeService.addNewRecipe(addRecipeDto)){
                redirectAttributes.addFlashAttribute("addRecipe",addRecipeDto);
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addRecipe",bindingResult);

                return "redirect:/recipe-add";
            }
            return "redirect:/home";
    }
    @PostMapping("/add-to-favourites/{id}")
    public String addToFavourites(@PathVariable Long id){
        recipeService.setRecipeAsFavourite(id,currentUser.getUsername());

        return "redirect:/home";
    }
}

