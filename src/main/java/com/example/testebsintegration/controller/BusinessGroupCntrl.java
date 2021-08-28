package com.example.testebsintegration.controller;

import com.example.testebsintegration.repos.BusinessGroupsRepo;
import com.example.testebsintegration.services.BusinessGroupSrvcs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class BusinessGroupCntrl {
    @Autowired
    BusinessGroupSrvcs businessGroupSrvcs;
    @GetMapping("/businessgroup")
    public ResponseEntity getBusinessGroup(Authentication authentication) {
      return businessGroupSrvcs.getBusinessGroup();
    }
}
