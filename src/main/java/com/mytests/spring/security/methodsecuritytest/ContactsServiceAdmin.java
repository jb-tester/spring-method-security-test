package com.mytests.spring.security.methodsecuritytest;

import com.mytests.spring.security.methodsecuritytest.data.ContactEntity;
import com.mytests.spring.security.methodsecuritytest.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Service;

import java.util.Collections;
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

    //@PreFilter("filterObject.firstname != authentication.name") // filterTarget could be omitted
    @PreFilter(value = "filterObject.firstname != authentication.name", filterTarget = "contacts") // no reference https://youtrack.jetbrains.com/issue/IDEA-157668
    public String filterOthers(List<ContactEntity> contacts) {
        StringBuilder rez = new StringBuilder("other contacts:\n");
        for (ContactEntity username : contacts) {
            rez.append("- ").append(username.getFirstname()).append(" \n");
        }
        return rez.toString();
    }


    public String displayOthers(){
        return (filterOthers(contactRepository.findAll()));
    }


    @PostFilter ("filterObject.firstname != authentication.name")
    public List<ContactEntity> filterOutThePrincipal(){
         return contactRepository.findAll();
    }

     // dummy section

    // ???
    @PreFilter(value = "filterObject.startsWith('a')", filterTarget = "arg1")
    public List<String> filterString(String arg1){
        return Collections.singletonList(arg1);
    }


    public String displayFilteredString(){
        return (filterString("a test").toString());
    }
    //

}
