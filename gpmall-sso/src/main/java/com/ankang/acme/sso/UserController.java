package com.ankang.acme.sso;

import com.ankang.acme.user.IUserCoreService;
import com.ankang.acme.user.dto.UserLoginRequest;
import com.ankang.acme.user.dto.UserLoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    
    @Autowired
    IUserCoreService iUserCoreService;
    
    @PostMapping("/login")
    public ResponseEntity doLogin(String username,String password){
        UserLoginRequest request = new UserLoginRequest();
        request.setUserName(username);
        request.setPassword(password);
        UserLoginResponse response = iUserCoreService.login(request);
        return ResponseEntity.ok(response);
        
    }
}
