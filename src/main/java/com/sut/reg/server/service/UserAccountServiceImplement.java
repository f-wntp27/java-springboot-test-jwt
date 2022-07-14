package com.sut.reg.server.service;

import java.util.Optional;

import com.sut.reg.server.entity.UserAccount;
import com.sut.reg.server.repository.UserAccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class UserAccountServiceImplement implements UserAccountServiceInterface, UserDetailsService {
    
    @Autowired
    private UserAccountRepository userRepo;
    
    @Autowired
    private BCryptPasswordEncoder bCryptEncoder;

    @Override
    public Integer saveUserAccount(UserAccount userAccount) {
        userAccount.setPassword(bCryptEncoder.encode(userAccount.getPassword()));
        return userRepo.save(userAccount).getId();
    }

    @Override
    public Optional<UserAccount> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserAccount> opt = userRepo.findByUsername(username);
        
        User springUser = null;

        if (opt.isEmpty()) {
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        } else {
            UserAccount user = opt.get();
            springUser = new User(username, user.getPassword(), emptyList());
        }

        return springUser;
    }
}
