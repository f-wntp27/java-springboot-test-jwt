package com.sut.reg.server.service;

import java.util.Optional;

import com.sut.reg.server.entity.UserAccount;

public interface UserAccountServiceInterface {
    Integer saveUserAccount(UserAccount userAccount);
    Optional<UserAccount> findByUsername(String username);
}
