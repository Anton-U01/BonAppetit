package com.bonappetit.controller;

import com.bonappetit.model.entity.dto.UserRegisterDto;
import com.bonappetit.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("userRegister")
    public UserRegisterDto createDto(){
        return new UserRegisterDto();
    }
    @GetMapping("/register")
    public String viewRegister(){
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@Valid UserRegisterDto userRegisterDto,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors() || !userService.registerUser(userRegisterDto)){
            redirectAttributes.addFlashAttribute("userRegister",userRegisterDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegister",bindingResult);

            return "redirect:/register";
        }
        return "redirect:/login";
    }
    @GetMapping("/login")
    public String viewLogin(){
        return "login";
    }
}
