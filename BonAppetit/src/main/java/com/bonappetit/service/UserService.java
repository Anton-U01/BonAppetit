package com.bonappetit.service;

import com.bonappetit.model.entity.dto.UserRegisterDto;

public interface UserService {
    boolean registerUser(UserRegisterDto userRegisterDto);
}
