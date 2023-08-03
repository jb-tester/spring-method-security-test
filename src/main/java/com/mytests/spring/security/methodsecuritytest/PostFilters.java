package com.mytests.spring.security.methodsecuritytest;

import com.mytests.spring.security.methodsecuritytest.data.ContactEntity;
import com.mytests.spring.security.methodsecuritytest.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Component
@Secured("ROLE_ADMIN")
public class PostFilters {

    @Autowired
    private ContactRepository contactRepository;

    @PostFilter("filterObject.firstname != authentication.name")
    public List<ContactEntity> filterOutThePrincipal() {
        return contactRepository.findAll();
    }

    // @PostFilter tests: try diff filterObject types
    // @PostFilter supports arrays, collections, maps, and streams (so long as the stream is still open).

    @PostFilter("filterObject.firstname != 'qqq'")
    public List<ContactEntity> listTest() {
        return contactRepository.findAll();
    }

    @PostFilter("filterObject.firstname != 'qqq'")
    public ContactEntity[] arrayTest() {
        return contactRepository.findAll().toArray(ContactEntity[]::new);
    }

    @PostFilter("filterObject.value.firstname != 'qqq'")
    public Map<String, ContactEntity> mapTest() {
        Map<String, ContactEntity> map = new HashMap<>();
        for (ContactEntity contactEntity : contactRepository.findAll()) {
            map.put(contactEntity.getLastname(), contactEntity);
        }
        return map;
    }

    @PostFilter("filterObject.firstname != 'qqq'")
    public Stream<ContactEntity> streamTests() {
        return contactRepository.findAll().stream();
    }




}
