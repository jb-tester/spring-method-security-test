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

    //@PreFilter("filterObject.firstname != authentication.name") // filterTarget could be omitted
    @PreFilter(value = "filterObject.firstname != authentication.name", filterTarget = "contacts")
    // no reference https://youtrack.jetbrains.com/issue/IDEA-157668
    public String filterOthers(List<ContactEntity> contacts) {
        StringBuilder rez = new StringBuilder("other contacts:\n");
        for (ContactEntity username : contacts) {
            rez.append("- ").append(username.getFirstname()).append(" \n");
        }
        return rez.toString();
    }


    public String displayOthers() {
        return (filterOthers(contactRepository.findAll()));
    }


    @PostFilter("filterObject.firstname != authentication.name")
    public List<ContactEntity> filterOutThePrincipal() {
        return contactRepository.findAll();
    }

    // @PreFilter tests: try different types
    // @PreFilter supports arrays, collections, maps, and streams (so long as the stream is still open).

    @PreFilter("filterObject.firstname == authentication.name")
    public Collection<ContactEntity> preFilterCollectionTest(Collection<ContactEntity> contacts){
        return contactRepository.findAll();
    }

    @PreFilter("filterObject.firstname == authentication.name")
    public Collection<ContactEntity> preFilterArrayTest(ContactEntity[] contacts){
        return contactRepository.findAll();
    }

    @PreFilter("filterObject.value.firstname == authentication.name")
    public Collection<ContactEntity> preFilterMapTest(Map<String, ContactEntity> contacts){
        return contactRepository.findAll();
    }

    @PreFilter("filterObject.firstname == authentication.name")
    public Collection<ContactEntity> preFilterStreamTest(Stream<ContactEntity> contacts){
        return contactRepository.findAll();
    }
    // ???
    @PreFilter(value = "filterObject.startsWith('a')", filterTarget = "arg1")
    public List<String> filterString(String... arg1) {
        return Arrays.stream(arg1).toList();
    }


    public String displayFilteredVarargStrings() {
        return (filterString("a test", "b test", "c test").toString());
    }
 ///
    // @PostFilter tests: try diff filterObject types
    // @PostFilter supports arrays, collections, maps, and streams (so long as the stream is still open).

    @PostFilter("filterObject.firstname != ''")
    public List<ContactEntity> listTest() {
        return contactRepository.findAll();
    }

    @PostFilter("filterObject.firstname != ''")
    public ContactEntity[] arrayTest() {
        return contactRepository.findAll().toArray(ContactEntity[]::new);
    }

    @PostFilter("filterObject.value.firstname != ''")
    public Map<String, ContactEntity> mapTest() {
        Map<String, ContactEntity> map = new HashMap<>();
        for (ContactEntity contactEntity : contactRepository.findAll()) {
            map.put(contactEntity.getLastname(), contactEntity);
        }
        return map;
    }

    @PostFilter("filterObject.firstname != ''")
    public Stream<ContactEntity> streamTests() {
        return contactRepository.findAll().stream();
    }
    //

}
