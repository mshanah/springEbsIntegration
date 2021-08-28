package com.example.testebsintegration.repos;

import com.example.testebsintegration.entities.PerBusinessGroups;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BusinessGroupsRepo extends PagingAndSortingRepository<PerBusinessGroups,Long> {
    @Query("select bg from PerBusinessGroups bg where businessGroupId = ?#{ principal?.BusinessGroupId }")
    Iterable<PerBusinessGroups> findAll();

}
