package com.example.testebsintegration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api")
public class WelcomeCntrl {

    final
    HttpServletRequest request;

    public WelcomeCntrl(HttpServletRequest httpServletRequest) {
        this.request = httpServletRequest;
    }
    @GetMapping("/welcome")
    @PreAuthorize("hasAuthority('UMX|AZ_SUPER_USER')")
    public String welcome(Authentication authentication){
        Object principal = SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
        System.out.println("callig service");
        return "Welcome To First Controller \n"+
                "sessionInfo := "+request.getAttribute("sessionInfo")+"\n"+
        "custom_attrs := "+request.getAttribute("custom_attrs")+"\n"+
        "userId := "+request.getAttribute("userId")+"\n"+
        "userName := "+request.getAttribute("userName")+
        "Security User Name := "+principal
                ;
    }
}
