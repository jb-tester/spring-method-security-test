package com.mytests.spring.security.methodsecuritytest.callsFromTheSameClassTests;

import com.mytests.spring.security.methodsecuritytest.data.ContactEntity;
import com.mytests.spring.security.methodsecuritytest.repositories.ContactRepository;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.List;


@Controller
public class AnnotatedMethodsCallsWithinSameClass {

    private final ContactRepository contactRepository;
    // used only to test that the similar methods work as expected when defined in the separate class:
    // uncomment all usages of separateService to check
    // private final SeparateService separateService;

    public AnnotatedMethodsCallsWithinSameClass(ContactRepository contactRepository
                                               //  , SeparateService separateService
    ) {
        this.contactRepository = contactRepository;
      //  this.separateService = separateService;
    }

    // this postfilter will be ignored since it is called from the same class only
    @PostFilter("filterObject.firstname != 'irina'")
    public List<ContactEntity> dummyPostFilter() {
        return contactRepository.findAll();
    }

    // this prefilter will be ignored since it is called from the same class only
    @PreFilter("filterObject.firstname != 'irina'")
    public Collection<ContactEntity> dummyPreFilter(Collection<ContactEntity> contacts){
        return contacts;
    }

    // this preAuthorize will be ignored since it is called from the same class only
    @PreAuthorize("hasRole('GUEST')")
    public String dummyPreAuthorize(){
        StringBuilder rez = new StringBuilder("should work for authenticated guests only: ");
        for (ContactEntity contact : contactRepository.findAll()) {
            rez.append(contact.getFirstname()).append("|");
        }
        return rez.toString();
    }

    public String usePreFilterFromSameClass(){
        StringBuilder rez = new StringBuilder("prefilter; will not be filtered: ");
        Collection<ContactEntity> entities = dummyPreFilter(contactRepository.findAll());
        // Collection<ContactEntity> entities = separateService.dummyPreFilter(contactRepository.findAll()); // similar method from separate class
        for (ContactEntity contact : entities) {
            rez.append(contact.getFirstname()).append(" | ");
        }
        return rez.toString();
    }


    public String usePostFilterFromSameClass(){
        StringBuilder rez = new StringBuilder("postfilter; will not be filtered: ");
       List<ContactEntity> entities = dummyPostFilter();
       // List<ContactEntity> entities = separateService.dummyPostFilter(); // similar method from separate class
        for (ContactEntity contact : entities) {
            rez.append(contact.getFirstname()).append("|");
        }
        return rez.toString();
    }

    @GetMapping("/invalid/preFilter")
    public String useDummyPrefilter(Model model){
        model.addAttribute("rezult", usePreFilterFromSameClass());
        return "invalid_tests";
    }
    @GetMapping("/invalid/postFilter")
    public String useDummyPostfilter(Model model){
        model.addAttribute("rezult", usePostFilterFromSameClass());
        return "invalid_tests";
    }
    @GetMapping("/invalid/preAuth")
    public String useDummyPreAuth(Model model){
        String authResult = dummyPreAuthorize();
        // String authResult = separateService.dummyPreAuthrorize();  // similar method from separate class
        model.addAttribute("rezult", authResult);
        return "invalid_tests";

    }

}
