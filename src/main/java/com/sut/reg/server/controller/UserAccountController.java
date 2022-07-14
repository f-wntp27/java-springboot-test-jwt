package com.sut.reg.server.controller;

import java.security.Principal;

import com.sut.reg.server.entity.UserAccount;
import com.sut.reg.server.entity.UserRequest;
import com.sut.reg.server.entity.UserResponse;
import com.sut.reg.server.service.UserAccountServiceImplement;
import com.sut.reg.server.util.JWTUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserAccountController {
    
    @Autowired
    private UserAccountServiceImplement userAccountService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/saveUser")
    public ResponseEntity<UserAccount> saveUser(@RequestBody UserAccount userAccount) {
        userAccountService.saveUserAccount(userAccount);
        return ResponseEntity.ok(userAccount);
    }

    @PostMapping("/loginUser")
    public ResponseEntity<UserResponse> login(@RequestBody UserRequest userReq) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userReq.getUsername(), userReq.getPassword()));
        String token = jwtUtil.generateToken(userReq.getUsername());
        return ResponseEntity.ok(new UserResponse(token, "Token generated successfully!"));
    }
    
    @GetMapping("/getData")
	public ResponseEntity<String> testAfterLogin(Principal p){
		return ResponseEntity.ok("You are accessing data after a valid Login. You are :" +p.getName());
	}
}
