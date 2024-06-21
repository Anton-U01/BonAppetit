package com.bonappetit.service;

import com.bonappetit.model.entity.Recipe;
import com.bonappetit.model.entity.dto.UserLoginDto;
import com.bonappetit.model.entity.dto.UserRegisterDto;

import java.util.List;

public interface UserService {
    boolean registerUser(UserRegisterDto userRegisterDto);

    boolean login(UserLoginDto userLoginDto);

    void logout();

    List<Recipe> getUsersFavouritesRecipes(String username);
}
