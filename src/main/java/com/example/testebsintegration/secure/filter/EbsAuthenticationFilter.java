package com.example.testebsintegration.secure.filter;

import com.example.testebsintegration.secure.EbsSessionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;


public class EbsAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public static final Logger LOGGER = LoggerFactory.getLogger(EbsSecFilter.class);
    private static final String FILTER_APPLIED = "__spring_security_EbsAuth_filterApplied";
    @Autowired
    DataSource ebsDS;

    private String applServerId = "C9FCE3DA03454AC4E053F858A8C05CF722702530081919445239154981853878";


    public EbsAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher("/api/*"));
        setAuthenticationManager(authenticationManager);
        // this.setContinueChainBeforeSuccessfulAuthentication(true);
    }
    private EbsSessionHelper sessionHelper;
    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token =new UsernamePasswordAuthenticationToken("GUEST","");

        if (httpServletRequest.getAttribute(FILTER_APPLIED) != null) {

            return SecurityContextHolder.getContext().getAuthentication();
        } else {
            //do something
            httpServletRequest.setAttribute(FILTER_APPLIED, true);


            try {
                sessionHelper = new EbsSessionHelper(ebsDS.getConnection(), applServerId, httpServletRequest, httpServletResponse);
                //UsernamePasswordAuthenticationToken newtoken=new UsernamePasswordAuthenticationToken("GUEST","");
                token.setDetails(sessionHelper);
                //newtoken= (UsernamePasswordAuthenticationToken) getAuthenticationManager().authenticate(newtoken);
                //if (token ==null){
                  //  httpServletResponse.setHeader("Location", sessionHelper.getLoginURL());
                    //httpServletResponse.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
                //}
            } catch (Exception e) {
                e.printStackTrace();
            }
            return getAuthenticationManager().authenticate(token);
        }
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //LOGGER.info("Success " + request.getRequestURI());
        SecurityContextHolder.getContext().setAuthentication(authResult);
        //super.successfulAuthentication(request, response, chain, authResult);
        if (sessionHelper != null ) {
            //String loginUrl = response.encodeRedirectURL(sessionHelper.getLoginURL());
            sessionHelper.closeConnection();
            //response.sendRedirect(loginUrl);
        }

        chain.doFilter(request, response);

    }
    @Override
    public void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException authExp) throws IOException, ServletException {
        //LOGGER.info("Failed " + request.getRequestURI());
        //SecurityContextHolder.getContext().setAuthentication(authResult);
        //super.successfulAuthentication(request, response, chain, authResult);
        //chain.doFilter(request, response);
        if (sessionHelper != null ) {
            String loginUrl = response.encodeRedirectURL(sessionHelper.getLoginURL());
            sessionHelper.closeConnection();
            response.sendRedirect(loginUrl);
        }else{
            super.unsuccessfulAuthentication(request,response,authExp);
        }

    }

}