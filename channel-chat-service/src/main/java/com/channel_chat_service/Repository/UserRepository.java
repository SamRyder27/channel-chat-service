package com.channel_chat_service.Repository;

import com.channel_chat_service.DTO.User;
import com.channel_chat_service.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    User findFirstByEmail(String email);
}
