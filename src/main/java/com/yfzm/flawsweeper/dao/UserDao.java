package com.yfzm.flawsweeper.dao;

import com.yfzm.flawsweeper.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserDao extends JpaRepository<UserEntity, String> {

    UserEntity findByUserId(String uid);

    UserEntity findByUsername(String username);

    UserEntity findByUsernameAndPasswordAndTypeAndStatus(String username, String password, int type, byte status);

    List<UserEntity> findAllByType(int type);
}
