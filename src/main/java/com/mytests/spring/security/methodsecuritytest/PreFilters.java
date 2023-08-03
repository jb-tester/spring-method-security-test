package com.mytests.spring.security.methodsecuritytest;

import com.mytests.spring.security.methodsecuritytest.data.ContactEntity;
import com.mytests.spring.security.methodsecuritytest.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Component
public class PreFilters {

    @Autowired
    private ContactRepository contactRepository;

    //@PreFilter("filterObject.firstname != authentication.name") // filterTarget could be omitted
    @PreFilter(value = "filterObject.firstname != 'qqq'", filterTarget = "contacts")
    // no reference https://youtrack.jetbrains.com/issue/IDEA-157668
    public String filterOthers(List<ContactEntity> contacts) {
        StringBuilder rez = new StringBuilder("other contacts:\n");
        for (ContactEntity username : contacts) {
            rez.append("- ").append(username.getFirstname()).append(" \n");
        }
        return rez.toString();
    }

    // @PreFilter tests: try different types
    // the documentation says that @PreFilter supports arrays, collections, maps, and streams (so long as the stream is still open).
    // However, it looks like only Collections are supported.

    // this prefilter will be ignored since it is called from the same class only
    @PreFilter("filterObject.firstname == authentication.name")
    public Collection<ContactEntity> preFilterCollectionTest(Collection<ContactEntity> contacts){
        return contacts;
    }

    public String usePreFilterFromSameClass(){
        StringBuilder rez = new StringBuilder("will not be filtered: ");
        for (ContactEntity contact : preFilterCollectionTest(contactRepository.findAll())) {
            rez.append(contact.getFirstname()).append(" | ");
        }
        return rez.toString();
    }

    // array: causes error: Pre-filtering on array types is not supported.
    @PreFilter("filterObject.firstname == 'qqq'")
    public String preFilterArrayTest(ContactEntity[] contacts){
        StringBuilder rez = new StringBuilder("other contacts:\n");
        for (ContactEntity username : contacts) {
            rez.append("- ").append(username.getFirstname()).append(" \n");
        }
        return rez.toString();
    }
    // map: causes error: A PreFilter expression was set but the method argument typeclass java.util.HashMap is not filterable
    @PreFilter("filterObject.value.firstname == 'qqq'")
    public String preFilterMapTest(Map<String, ContactEntity> contacts){
        StringBuilder rez = new StringBuilder("other contacts:\n");
        contacts.forEach((k, v) -> rez.append(k).append(":").append(v).append("|"));
        return rez.toString();
    }
    // stream: causes error: A PreFilter expression was set but the method argument typeclass java.util.stream.ReferencePipeline$Head is not filterable
    @PreFilter("filterObject.firstname == 'qqq'")
    public String preFilterStreamTest(Stream<ContactEntity> contacts){
        StringBuilder rez = new StringBuilder("other contacts:\n");
        contacts.forEach(c -> rez.append(c).append("|"));
        return rez.toString();
    }
    // vararg: causes error: Pre-filtering on array types is not supported. Using a Collection will solve this problem
    @PreFilter(value = "filterObject.startsWith('a')", filterTarget = "arg1")
    public List<String> filterString(String... arg1) {
        return Arrays.stream(arg1).toList();
    }



}
