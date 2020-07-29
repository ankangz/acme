package com.ankang.acme.sso.controller;

import com.ankang.acme.user.IUserCoreService;
import com.ankang.acme.user.dto.UserLoginRequest;
import com.ankang.acme.user.dto.UserLoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController extends BaseController{
    
    @Autowired
    IUserCoreService iUserCoreService;
    
    @PostMapping("/login")
    @Anoymous
    public UserLoginResponse doLogin(String username, String password, HttpServletResponse httpServletResponse){
        UserLoginRequest request = new UserLoginRequest();
        request.setUserName(username);
        request.setPassword(password);
        UserLoginResponse response = iUserCoreService.login(request);
        httpServletResponse.addHeader("Set-Cookie","access_token="+response.getToken()+";Path=/;HttpOnly");
        return response;
        
    }

    @GetMapping("/test")
    public String testLogin(){
        return "success"+getUid();

    }
}
