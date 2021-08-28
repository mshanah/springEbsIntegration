package com.example.testebsintegration.services;

import com.example.testebsintegration.entities.PerBusinessGroups;
import com.example.testebsintegration.repos.BusinessGroupsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class BusinessGroupSrvcs {
    @Autowired
    BusinessGroupsRepo businessGroupsRepo;

    public ResponseEntity<Iterable<PerBusinessGroups>> getBusinessGroup(){

        return new ResponseEntity(
                businessGroupsRepo.findAll(),   HttpStatus.OK);

    }
}
