package com.ankang.acme.user.services;

import com.alibaba.druid.util.StringUtils;
import com.ankang.acme.user.IUserCoreService;
import com.ankang.acme.user.constants.ResponseCodeEnum;
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
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
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
                response.setCode(ResponseCodeEnum.USERORPASSWORD_ERRROR.getCode());
                response.setMsg(ResponseCodeEnum.USERORPASSWORD_ERRROR.getMsg());
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
        CheckAuthResponse checkAuthResponse = new CheckAuthResponse();
        try {
            beforeValidateAuth(request);
            Claims claims = JwtTokenUtils.parseToken(request.getToken());
            checkAuthResponse.setUid(claims.get("uid").toString());
            checkAuthResponse.setCode(ResponseCodeEnum.SUCCESS.getCode());
            checkAuthResponse.setMsg(ResponseCodeEnum.SUCCESS.getMsg());
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            checkAuthResponse.setCode(ResponseCodeEnum.TOKEN_EXPIRE.getCode());
            checkAuthResponse.setMsg(ResponseCodeEnum.TOKEN_EXPIRE.getMsg());
        } catch (SignatureException e) {
            e.printStackTrace();
            checkAuthResponse.setCode(ResponseCodeEnum.SIGNATURE_ERROR.getCode());
            checkAuthResponse.setMsg(ResponseCodeEnum.SIGNATURE_ERROR.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            ServiceException serviceException = (ServiceException) ExceptionUtil.handlerException4biz(e);
            checkAuthResponse.setCode(serviceException.getErrorCode());
            checkAuthResponse.setMsg(serviceException.getErrorMessage());
        } finally {
            log.info("response:"+checkAuthResponse);
        }
        return checkAuthResponse;
    }


    private void beforeValidateAuth(CheckAuthRequest request){
        if(null == request){
            throw new ValidateException("请求对象为空！");
        }
        if(StringUtils.isEmpty(request.getToken())){
            throw new ValidateException("token为空！");
        }
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
