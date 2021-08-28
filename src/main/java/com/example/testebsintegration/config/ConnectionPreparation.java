package com.example.testebsintegration.config;

import com.example.testebsintegration.secure.EbsAttributes;
import com.example.testebsintegration.secure.User;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
@Component
@Aspect
public class ConnectionPreparation {
    @AfterReturning(pointcut= "execution(* oracle.jdbc.pool.OracleDataSource.getConnection(..))",
            returning = "connection")
    public Connection setClientIdentifier(Connection connection) throws SQLException, SQLException {

        if(SecurityContextHolder. getContext().getAuthentication() != null && SecurityContextHolder. getContext().getAuthentication().isAuthenticated()){
            User user=(User)SecurityContextHolder. getContext().getAuthentication().getPrincipal();
            String respId=user.getAttributeValue(EbsAttributes.RESPONSIBILITY_ID);
            String respApplId=user.getAttributeValue(EbsAttributes.RESPONSIBILITY_APPLICATION_ID);

            System.out.println("Initilized with User Id {"+user.getUserId()+"} , RespId {"+respId+"} , RespApplId {"+respApplId+"}");
        CallableStatement cs=connection.prepareCall("{call FND_GLOBAL.APPS_INITIALIZE(?,?,?)}");
        cs.setInt(1,Integer.parseInt(user.getUserId()));
        cs.setInt(2,Integer.parseInt(respId));
        cs.setInt(3,Integer.parseInt(respApplId));
        cs.execute();
        cs.close();}
        return connection;
    }

}
