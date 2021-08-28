package com.example.testebsintegration.secure;

import oracle.apps.fnd.ext.common.AppsSessionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

@Component
public class EbsAuthenticationProvider implements AuthenticationProvider {
    public static final Logger LOGGER = LoggerFactory.getLogger(EbsAuthenticationProvider.class);
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("try to authinticate");
        if(authentication != null) {
            EbsSessionHelper sessionHelper = (EbsSessionHelper) authentication.getDetails();
            UsernamePasswordAuthenticationToken newToken = null;
            if (sessionHelper == null) {
                return null;
            } else {
                if (sessionHelper.getSession() != null) {
                    String sessionStatus = sessionHelper.getSession().check();
                    if ("VALID".equals(sessionStatus)) {
                        Collection<GrantedAuthority> roles = sessionHelper.getUserRoles();
                        Map<String,String> cusAtts=sessionHelper.getSession().getAttributes(true);
                        Map<String,String> sessAtts=sessionHelper.getSession().getInfo();
                        System.out.println(sessAtts);
                        User user=new User(sessionHelper.getSession().getUserName(),
                                sessionHelper.getSession().getUserId(),
                                sessAtts,cusAtts
                                );

                        newToken = new UsernamePasswordAuthenticationToken(user, "", roles);
                    }
                }
            }
            return newToken;
        }else{
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
