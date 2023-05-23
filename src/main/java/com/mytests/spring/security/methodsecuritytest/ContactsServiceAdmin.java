package com.mytests.spring.security.methodsecuritytest;

import com.mytests.spring.security.methodsecuritytest.data.ContactEntity;
import com.mytests.spring.security.methodsecuritytest.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * *
 * <p>Created by irina on 12/16/2021.</p>
 * <p>Project: spring-method-security-test</p>
 * *
 */
@Service
@Secured("ROLE_ADMIN")
public class ContactsServiceAdmin {

    @Autowired
    private ContactRepository contactRepository;

    // @PreFilter("filterObject != authentication.principal.username") // could be omitted
    @PreFilter(value = "filterObject != authentication.principal.username", filterTarget = "usernames") // no reference https://youtrack.jetbrains.com/issue/IDEA-157668
    public String filterOthers(List<String> usernames) {
        StringBuilder rez = new StringBuilder("other contacts:\n");
        for (String username : usernames) {
            rez.append("- ").append(username).append(" \n");
        }
        return rez.toString();
    }


    public String displayOthers(){
        return (filterOthers(contactRepository.getFirstnames()));
    }


    @PostFilter ("filterObject.firstname != authentication.name")
    public List<ContactEntity> filterOutThePrincipal(){
         return contactRepository.findAll();
    }

}
