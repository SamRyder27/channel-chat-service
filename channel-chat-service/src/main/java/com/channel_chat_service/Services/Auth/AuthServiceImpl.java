package com.channel_chat_service.Services.Auth;

import com.channel_chat_service.DTO.SignUpUser;

import com.channel_chat_service.Entity.UserEntity;
import com.channel_chat_service.Repository.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthSerivce, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public SignUpUser createUser(SignUpUser signUpUser) {
        UserEntity userE = new UserEntity();
        userE.setName(signUpUser.getName());
        userE.setEmail(signUpUser.getEmail());
        userE.setPassword(new BCryptPasswordEncoder().encode(signUpUser.getPassword()));
        userE.setPhone(signUpUser.getPhone());
        //BeanUtils.copyProperties(signUpUser, userE);

        return userRepository.save(userE).getSignUpUser();
    }

    @Override
    public Boolean presentByEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findFirstByEmail(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Email does not exist in the DB: " + username));

        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }

//    @Override
//    public UserDetails updatePassword(UserDetails user, @Nullable String newPassword) {
//        return null;
//    }
}
