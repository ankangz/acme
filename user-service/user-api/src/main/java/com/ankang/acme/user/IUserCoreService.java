package com.ankang.acme.user;

import com.ankang.acme.user.dto.CheckAuthRequest;
import com.ankang.acme.user.dto.CheckAuthResponse;
import com.ankang.acme.user.dto.UserLoginRequest;
import com.ankang.acme.user.dto.UserLoginResponse;

public interface IUserCoreService {

    /**
     * 登录
     * @param request
     * @return
     */
    UserLoginResponse login(UserLoginRequest request);
    
    CheckAuthResponse validToken(CheckAuthRequest request);
}
