package com.example.testebsintegration.secure;

import java.util.Map;

public class User {

    public User(String userName, String userId, Map<String,String> sessionInfo, Map<String,String>  extraInfo) {
        this.userName = userName;
        this.userId = userId;
        this.extraInfo = extraInfo;
        this.sessionInfo = sessionInfo;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserId() {
        return userId;
    }



    private String userName;
    private String userId;
    private Map<String,String> sessionInfo;
    private Map<String,String> extraInfo;

    public String getAttributeValue(EbsAttributes attKey){

        String attValue=sessionInfo.get(attKey.getAttribute());
        if(attKey ==null){
            attValue=extraInfo.get(attKey.getAttribute());
        }
        if("NULL".equalsIgnoreCase(attValue)||"".equals(attValue))
        {
            attValue=null;
        }
        return attValue;
    }
    public Long getBusinessGroupId(){
        String bgId="0";
                return Long.parseLong(bgId);
    }

}
