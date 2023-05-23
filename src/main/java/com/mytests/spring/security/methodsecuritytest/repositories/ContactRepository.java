package com.mytests.spring.security.methodsecuritytest.repositories;

import com.mytests.spring.security.methodsecuritytest.data.ContactEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * *
 * <p>Created by irina on 12/16/2021.</p>
 * <p>Project: spring-method-security-test</p>
 * *
 */
@RepositoryDefinition(domainClass = ContactEntity.class, idClass = Long.class)
public interface ContactRepository {
    @PreAuthorize("#firstname == authentication.name")
    List<ContactEntity> findByFirstnameAndLastname(String firstname, String lastname);

    @PreAuthorize("#fname == authentication.name")  // not resolved https://youtrack.jetbrains.com/issue/IDEA-285148
    List<ContactEntity> findAllByFirstname(@Param("fname") String firstname);


    @PreAuthorize("hasRole('USER')")
    List<ContactEntity> findAll();

    @Query("select c.firstname from ContactEntity c")
    List<String> getFirstnames();


}
