package com.bonappetit.service.impl;

import com.bonappetit.model.entity.User;
import com.bonappetit.model.entity.dto.UserLoginDto;
import com.bonappetit.model.entity.dto.UserRegisterDto;
import com.bonappetit.repo.UserRepository;
import com.bonappetit.service.CurrentUser;
import com.bonappetit.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final CurrentUser currentUser;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, CurrentUser currentUser) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.currentUser = currentUser;
    }

    @Override
    public boolean registerUser(UserRegisterDto userRegisterDto) {
        if(!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())){
            return false;
        }
        Optional<User> optionalUser = userRepository.findByUsernameAndEmail(userRegisterDto.getUsername(), userRegisterDto.getEmail());
        if(optionalUser.isPresent()){
            return false;
        }

        User user = modelMapper.map(userRegisterDto, User.class);
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        userRepository.saveAndFlush(user);

        return true;
    }

    @Override
    public boolean login(UserLoginDto userLoginDto) {
        Optional<User> optionalUser = userRepository.findByUsername(userLoginDto.getUsername());
        if(optionalUser.isEmpty()){
            return false;
        }
        User user = optionalUser.get();
        if(!passwordEncoder.matches(userLoginDto.getPassword(),user.getPassword())){
            return false;
        }

        currentUser.login(user);

        return true;
    }

    @Override
    public void logout() {
        currentUser.logout();
    }
}
