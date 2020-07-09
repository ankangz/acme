package com.ankang.acme.user.services;

import com.alibaba.druid.util.StringUtils;
import com.ankang.acme.user.IUserCoreService;
import com.ankang.acme.user.dto.UserLoginRequest;
import com.ankang.acme.user.dto.UserLoginResponse;
import com.ankang.acme.user.exception.ExceptionUtil;
import com.ankang.acme.user.exception.ServiceException;
import com.ankang.acme.user.exception.ValidateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserCoreServiceImpl implements IUserCoreService {
    
    Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    public UserLoginResponse login(UserLoginRequest request) {
        UserLoginResponse response = new UserLoginResponse();

        try {
            beforeValidate(request);
        } catch (Exception e) {
            ServiceException serviceException = (ServiceException) ExceptionUtil.handlerException4biz(e);
        }
        return null;
    }
    
    private void beforeValidate(UserLoginRequest request){
        if(null == request){
            throw new ValidateException("请求对象为空！");
        }
        if(StringUtils.isEmpty(request.getUserName())){
            throw new ValidateException("用户名为空！");
        }
        if(StringUtils.isEmpty(request.getPassword())){
            throw new ValidateException("密码为空！");
        }
    }
}
