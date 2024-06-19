package com.bonappetit.service.impl;

import com.bonappetit.model.entity.User;
import com.bonappetit.model.entity.dto.UserRegisterDto;
import com.bonappetit.repo.UserRepository;
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

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
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
}
