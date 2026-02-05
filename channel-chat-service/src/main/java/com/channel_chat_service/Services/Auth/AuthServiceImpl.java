package com.channel_chat_service.Services.Auth;

import com.channel_chat_service.DTO.SignUpUser;
import com.channel_chat_service.DTO.User;
import com.channel_chat_service.Entity.UserEntity;
import com.channel_chat_service.Repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthSerivce {

    @Autowired
    private UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(SignUpUser signUpUser) {
        UserEntity userE = new UserEntity();
        userE.setName(signUpUser.getName());
        userE.setEmail(signUpUser.getEmail());
        userE.setPassword(new BCryptPasswordEncoder().encode(signUpUser.getPassword()));
        userE.setPhone(signUpUser.getPhone());
        //BeanUtils.copyProperties(signUpUser, userE);

        return userRepository.save(userE).getUser();
    }

    @Override
    public Boolean presentByEmail(String email) {
        return userRepository.findFirstByEmail(email) != null;
    }
}
