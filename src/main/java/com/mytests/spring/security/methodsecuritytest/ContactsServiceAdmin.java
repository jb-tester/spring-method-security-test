package com.mytests.spring.security.methodsecuritytest;

import com.mytests.spring.security.methodsecuritytest.data.ContactEntity;
import com.mytests.spring.security.methodsecuritytest.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

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
    @Autowired
    private PreFilters preFilters;


    public String displayOthers() {
        return (preFilters.filterOthers(contactRepository.findAll()));
    }




    public String displayFilteredArray(){
        return preFilters.preFilterArrayTest(contactRepository.findAll().toArray(ContactEntity[]::new));
    }


    public String displayFilteredMap() {
        Map<String, ContactEntity> map = new HashMap<>();
        for (ContactEntity contactEntity : contactRepository.findAll()) {
            map.put(contactEntity.getLastname(), contactEntity);
        }
        return preFilters.preFilterMapTest(map);
    }




    public String displayFilteredStream() {
        return preFilters.preFilterStreamTest(contactRepository.findAll().stream());
    }

    public String displayFilteredVarargStrings() {
        return preFilters.filterString("a test", "b test", "c test").toString();
    }



    //

}
