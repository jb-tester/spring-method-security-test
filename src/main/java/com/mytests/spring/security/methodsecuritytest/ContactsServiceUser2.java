package com.mytests.spring.security.methodsecuritytest;

import com.mytests.spring.security.methodsecuritytest.data.ContactEntity;
import com.mytests.spring.security.methodsecuritytest.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RolesAllowed;
import java.util.List;


@Service
@DeclareRoles("ROLE_USER")
public class ContactsServiceUser2 {


    @Autowired
    private ContactRepository contactRepository;



    public List<ContactEntity> byFirstAndLastName(String name, String lastname){
        return contactRepository.findByFirstnameAndLastname(name, lastname);
    };
}
