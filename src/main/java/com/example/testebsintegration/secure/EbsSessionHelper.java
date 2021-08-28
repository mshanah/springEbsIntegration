package com.example.testebsintegration.secure;

import oracle.apps.fnd.ext.common.AppsRequestWrapper;
import oracle.apps.fnd.ext.common.AppsSession;
import oracle.apps.fnd.ext.common.EBiz;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;


public class EbsSessionHelper {

    public static final Logger LOGGER = LoggerFactory.getLogger(EbsSessionHelper.class);
    private Connection connection;
    private String applServerId;
    private EBiz eBiz;
    private AppsSession session;

    public EbsSessionHelper(Connection connection,String applServerId,HttpServletRequest request,HttpServletResponse response) throws Exception {
        this.connection=connection;
        this.applServerId=applServerId;
        this.eBiz = new EBiz(connection, applServerId);
        this.session=getAppsSession(request,response);
    }
    public  Collection<GrantedAuthority> getUserRoles( ) {
        String userName=session.getUserName();
        Collection<GrantedAuthority> roles = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.prepareStatement(" select WUR.ROLE_NAME  from fnd_user u ,  wf_user_role_assignments_v wura , wf_user_roles wur  where wura.user_name = u.user_name  and wur.role_orig_system = 'UMX'  and wur.partition_id = 13  and wura.role_name = wur.role_name  and wura.user_name = wur.user_name  and u.user_name = :1 ");
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String role = (String) rs.getObject(1);
                roles.add(new SimpleGrantedAuthority(role));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null)
                try {
                    stmt.close();
                } catch (SQLException e) {
                }
        }
        return roles;
    }

    private AppsSession getAppsSession( HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("validateSession start");

        AppsRequestWrapper appsRequestWrapper = new AppsRequestWrapper(request, response, connection, this.eBiz);
        this.session = (AppsSession) appsRequestWrapper.getAppsSession();
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("validateSession return "+this.session);
        return session;
    }
    public AppsSession getSession(){
        return this.session;
    }
    public String getLoginURL(){
        return this.eBiz.getAppsServletAgent()+ "/AppsLogin";
    }
    public void closeConnection(){
        try{
            this.connection.close();
        }catch (Exception e){

        }
    }
}