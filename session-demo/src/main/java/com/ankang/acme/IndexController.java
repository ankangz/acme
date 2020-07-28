package com.ankang.acme;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class IndexController {
    @GetMapping("/index")
    public String index(HttpServletRequest request) {
        request.getSession().setAttribute("ankang","value");
        return "success";
    }
    
    
}
