package com.ankang.acme.user.services;

import com.alibaba.druid.util.StringUtils;
import com.ankang.acme.user.IUserCoreService;
import com.ankang.acme.user.ResponseCodeEnum;
import com.ankang.acme.user.dal.entity.User;
import com.ankang.acme.user.dal.persistence.UserMapper;
import com.ankang.acme.user.dto.CheckAuthRequest;
import com.ankang.acme.user.dto.CheckAuthResponse;
import com.ankang.acme.user.dto.UserLoginRequest;
import com.ankang.acme.user.dto.UserLoginResponse;
import com.ankang.acme.user.exception.ExceptionUtil;
import com.ankang.acme.user.exception.ServiceException;
import com.ankang.acme.user.exception.ValidateException;
import com.ankang.acme.user.utils.JwtTokenUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service("userCoreService")
public class UserCoreServiceImpl implements IUserCoreService {
    
    Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    UserMapper userMapper;
    @Override
    public UserLoginResponse login(UserLoginRequest request) {
        log.info("Login Request:"+request);
        UserLoginResponse response = new UserLoginResponse();
        try {
            beforeValidate(request);
            User user = userMapper.getUserByUserName(request.getUserName());
            if(null == user || !user.getPassword().equals(request.getPassword())){
                response.setCode(ResponseCodeEnum.USERORPASSWOR_ERROR.getCode());
                response.setMsg(ResponseCodeEnum.USERORPASSWOR_ERROR.getMsg());
                return response;
            }else{
                
                Map<String,Object> map = new HashMap<>();
                map.put("uid",user.getId());
                map.put("exp", DateTime.now().plusSeconds(40).toDate().getTime()/1000);

                String token = JwtTokenUtils.generatorToken(map);
                response.setToken(token);
                response.setUid(user.getId());
                response.setCode(ResponseCodeEnum.SUCCESS.getCode());
                response.setMsg(ResponseCodeEnum.SUCCESS.getMsg());
                response.setAvatar(user.getAvatar());
                response.setMobile(user.getMobile());
                
            }
        } catch (Exception e) {
            System.out.println(e);
            ServiceException serviceException = (ServiceException) ExceptionUtil.handlerException4biz(e);
            response.setCode(serviceException.getErrorCode());
            response.setMsg(serviceException.getErrorMessage());
        }
        log.info("Login Response:"+response);
        return response;
    }

    @Override
    public CheckAuthResponse validToken(CheckAuthRequest request) {
        
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
