package com.ankang.acme.sso.intercept;

import com.ankang.acme.sso.controller.Anoymous;
import com.ankang.acme.sso.controller.BaseController;
import com.ankang.acme.sso.utils.CookieUtil;
import com.ankang.acme.user.IUserCoreService;
import com.ankang.acme.user.dto.CheckAuthRequest;
import com.ankang.acme.user.dto.CheckAuthResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.lang.reflect.Method;

public class TokenIntercepter extends HandlerInterceptorAdapter {
    private final String ACCESS_TOKEN = "access_token";

    @Autowired
    IUserCoreService iUserCoreService;

    @Override
    public boolean preHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler) throws Exception {

        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Object bean = handlerMethod.getBean();
        if(!(bean instanceof BaseController)){
            throw new RuntimeException("must extends BaseController");
        }
        if(isAnoymous(handlerMethod)){
            return true;
        }

        String token = CookieUtil.getCookieValue(request, ACCESS_TOKEN);
        if(StringUtils.isEmpty(token)){
            if(CookieUtil.isAjax(request)){
                response.getWriter().write("{'code':'-1','msg':'error'}");
                return false;
            }
            response.sendRedirect("/pages/login.html");
            return false;
        }
        CheckAuthRequest checkAuthRequest = new CheckAuthRequest();
        checkAuthRequest.setToken(token);
        CheckAuthResponse checkAuthResponse = iUserCoreService.validToken(checkAuthRequest);
        if("000000".equals(checkAuthResponse.getCode())){
            BaseController baseController = (BaseController) bean;
            baseController.setUid(checkAuthResponse.getUid());
            return super.preHandle(request, response, handler);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(checkAuthResponse));
        return false;
    }

    
    private boolean isAnoymous(HandlerMethod handlerMethod){

        Class clazz = handlerMethod.getBean().getClass();
        if(null != clazz.getAnnotation(Anoymous.class)){
            return true;
        }
        Method method = handlerMethod.getMethod();
        return null != method.getAnnotation(Anoymous.class);
    }
   
}
