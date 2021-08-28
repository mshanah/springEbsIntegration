package com.example.testebsintegration.secure.filter;


import oracle.apps.fnd.ext.common.AppsRequestWrapper;
import oracle.apps.fnd.ext.common.AppsSession;
import oracle.apps.fnd.ext.common.EBiz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


public class EbsSecFilter implements Filter {
    public static final Logger LOGGER = LoggerFactory.getLogger(EbsSecFilter.class);
    @Autowired
    DataSource ebsDS;

    private String applServerId = "C9FCE3DA03454AC4E053F858A8C05CF722702530081919445239154981853878";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    };
    Connection connection;
    Collection<GrantedAuthority> getUserRoles(Connection conn,String userName){

        Collection<GrantedAuthority> roles = new ArrayList<GrantedAuthority>() ;
       // List<String> roles = new ArrayList<String>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(" select WUR.ROLE_NAME  from fnd_user u ,  wf_user_role_assignments_v wura , wf_user_roles wur  where wura.user_name = u.user_name  and wur.role_orig_system = 'UMX'  and wur.partition_id = 13  and wura.role_name = wur.role_name  and wura.user_name = wur.user_name  and u.user_name = :1 ");
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String role = (String)rs.getObject(1);
                roles.add(new SimpleGrantedAuthority(role));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null)
                try {
                    stmt.close();
                } catch (SQLException e) {}
        }

        return  roles;
    }
    private AppsSession validateAndReturnSession(HttpServletRequest request,HttpServletResponse response) throws Exception {

        if (LOGGER.isDebugEnabled())
            LOGGER.debug("validateSession start");

        request.setAttribute("appServerId", applServerId);
        if (ebsDS != null){
            try {
                connection=ebsDS.getConnection();
            } catch (SQLException throwables) {
                throw new ServletException("Uablble to retrive Connection "+throwables.getMessage()) ;
            }
        }else{
            throw new ServletException("Data Source is null ") ;
        }

            EBiz ebs=new EBiz(connection,applServerId);
            AppsRequestWrapper appsRequestWrapper=new AppsRequestWrapper(request, response,connection,ebs);
            //LOGGER.debug(ebs.getAppsServletAgent());
            AppsSession session=(AppsSession)appsRequestWrapper.getAppsSession();



//            AppsSession session = new AppsSession(conn, applServerId, sessionCookie);
if (session != null) {
    String sessionStatus = session.check();
    if (!sessionStatus.equals("VALID")) {
        String error = "Session is not VALID, please log into EBS instance " + applServerId;
        LOGGER.error(error);
        response.setHeader("Location", ebs.getAppsServletAgent()+ "/AppsLogin");
        response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
    }
    if (LOGGER.isDebugEnabled())
        LOGGER.debug("Return @session (Cookie Value): {}", session.getCookieValue());
    Map<String, String> sessionInfo = session.getInfo();
    request.setAttribute("sessionInfo", sessionInfo);
    request.setAttribute("custom_attrs", session.getAttributes(true));
    request.setAttribute("userId", session.getUserId());
    request.setAttribute("userName", session.getUserName());
    Collection<GrantedAuthority> roles=this.getUserRoles(connection,session.getUserName());
    UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(session.getUserName(),"",roles);
    //token.setAuthenticated(true);
    SecurityContextHolder.getContext().setAuthentication(token);

}
else{
    response.setHeader("Location", ebs.getAppsServletAgent()+ "/AppsLogin");
    response.setStatus(
            HttpServletResponse.SC_MOVED_TEMPORARILY
            );

}
        return session;
    }
    @Override
    public void doFilter(ServletRequest  servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        try {

            AppsSession apss=validateAndReturnSession((HttpServletRequest) servletRequest,(HttpServletResponse) servletResponse);
            servletRequest.setAttribute("apps-session",apss);
        } catch (Exception e) {
            e.printStackTrace();

            throw new ServletException(e.getMessage()) ;

        }finally {
            try{
            connection.close();
            } catch (SQLException throwables) {
                LOGGER.debug("Uable to close Connection "+throwables.getMessage());
            }
        }

        LOGGER.info("Attemp Auth"+((HttpServletRequest)servletRequest).getRequestURI());
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
