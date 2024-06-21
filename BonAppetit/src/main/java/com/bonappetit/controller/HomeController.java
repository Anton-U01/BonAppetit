package com.bonappetit.controller;

import com.bonappetit.model.entity.CategoryEnum;
import com.bonappetit.model.entity.Recipe;
import com.bonappetit.model.entity.dto.RecipeDto;
import com.bonappetit.repo.UserRepository;
import com.bonappetit.service.CurrentUser;
import com.bonappetit.service.RecipeService;
import com.bonappetit.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    private final RecipeService recipeService;
    private final UserService userService;
    private final CurrentUser currentUser;

    public HomeController(RecipeService recipeService, UserService userService, CurrentUser currentUser) {
        this.recipeService = recipeService;
        this.userService = userService;
        this.currentUser = currentUser;
    }

    @GetMapping("/")
    public String notLoggedIndex(){
        if(currentUser.isLoggedIn()){
            return "redirect:/home";
        }
        return "index";
    }

    @GetMapping("/home")
    @Transactional
    public String loggedIndex(Model model){
        if(!currentUser.isLoggedIn()){
            return "redirect:/";
        }

       List<Recipe> cocktailRecipes = recipeService.findAllRecipesByCategoryName(CategoryEnum.COCKTAIL);
       List<Recipe> mainDishRecipes = recipeService.findAllRecipesByCategoryName(CategoryEnum.MAIN_DISH);
       List<Recipe> dessertRecipes = recipeService.findAllRecipesByCategoryName(CategoryEnum.DESSERT);
       List<RecipeDto> favouritesRecipes = userService.getUsersFavouritesRecipes(currentUser.getUsername())
               .stream()
               .map(RecipeDto::new)
               .collect(Collectors.toList());
       model.addAttribute("cocktails",cocktailRecipes);
       model.addAttribute("mainDishes",mainDishRecipes);
       model.addAttribute("desserts",dessertRecipes);
       model.addAttribute("favourites",favouritesRecipes);
       return "home";
    }

}
