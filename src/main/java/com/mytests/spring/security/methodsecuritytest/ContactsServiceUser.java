package com.mytests.spring.security.methodsecuritytest;

import com.mytests.spring.security.methodsecuritytest.data.ContactEntity;
import com.mytests.spring.security.methodsecuritytest.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import java.util.List;


@Service
@RolesAllowed("ROLE_USER")
public class ContactsServiceUser {


    @Autowired
    private ContactRepository contactRepository;

    public List<ContactEntity> byFirstName(String name){
       return contactRepository.findAllByFirstname(name);
    }


}
