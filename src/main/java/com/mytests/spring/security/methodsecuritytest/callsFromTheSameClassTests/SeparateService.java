package com.mytests.spring.security.methodsecuritytest.callsFromTheSameClassTests;

import com.mytests.spring.security.methodsecuritytest.data.ContactEntity;
import com.mytests.spring.security.methodsecuritytest.repositories.ContactRepository;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class SeparateService {
    private final ContactRepository contactRepository;

    public SeparateService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @PostFilter("filterObject.firstname != 'irina'")
    public List<ContactEntity> dummyPostFilter() {
        return contactRepository.findAll();
    }


    @PreFilter("filterObject.firstname != 'irina'")
    public Collection<ContactEntity> dummyPreFilter(Collection<ContactEntity> contacts){
        return contacts;
    }

    @PreAuthorize("hasRole('GUEST')")
    public String dummyPreAuthrorize(){
        StringBuilder rez = new StringBuilder("should work for authenticated guests only: ");
        for (ContactEntity contact : contactRepository.findAll()) {
            rez.append(contact.getFirstname()).append("|");
        }
        return rez.toString();
    }
}
