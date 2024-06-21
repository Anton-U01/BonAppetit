package com.bonappetit.controller;

import com.bonappetit.model.entity.dto.UserLoginDto;
import com.bonappetit.model.entity.dto.UserRegisterDto;
import com.bonappetit.service.CurrentUser;
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
    private final CurrentUser currentUser;

    public UserController(UserService userService, CurrentUser currentUser) {
        this.userService = userService;
        this.currentUser = currentUser;
    }

    @ModelAttribute("userRegister")
    public UserRegisterDto createDto(){
        return new UserRegisterDto();
    }
    @GetMapping("/register")
    public String viewRegister(){
        if(currentUser.isLoggedIn()){
            return "redirect:/home";
        }
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@Valid UserRegisterDto userRegisterDto,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes){
        if(currentUser.isLoggedIn()){
            return "redirect:/home";
        }

        if(bindingResult.hasErrors() || !userService.registerUser(userRegisterDto)){
            redirectAttributes.addFlashAttribute("userRegister",userRegisterDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegister",bindingResult);

            return "redirect:/register";
        }
        return "redirect:/login";
    }

    @ModelAttribute("userLogin")
    public UserLoginDto createUserLogin(){
        return new UserLoginDto();
    }
    @GetMapping("/login")
    public String viewLogin(){
        if(currentUser.isLoggedIn()){
            return "redirect:/home";
        }

        return "login";
    }
    @PostMapping("/login")
    public String login(@Valid UserLoginDto userLoginDto,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes){
        if(currentUser.isLoggedIn()){
            return "redirect:/home";
        }

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("userLogin",userLoginDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLogin",bindingResult);

            return "redirect:/login";
        }

        boolean success = userService.login(userLoginDto);
        if(!success){
            redirectAttributes.addFlashAttribute("userLogin",userLoginDto);
            redirectAttributes.addFlashAttribute("incorrectInput",true);

            return "redirect:/login";
        }

        return "redirect:/home";
    }

    @PostMapping("/logout")
    public String logout(){
        if(!currentUser.isLoggedIn()){
            return "redirect:/";
        }

        userService.logout();
        return "redirect:/";
    }
}
