package com.mytests.spring.security.methodsecuritytest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * *
 * <p>Created by irina on 7/11/2022.</p>
 * <p>Project: spring-method-security-test</p>
 * *
 */
@Controller
public class ContactController {

    @Autowired
    private ContactsServiceUser contactsServiceUser;
    @Autowired
    private ContactsServiceUser2 contactsServiceUser2;
    @Autowired
    private ContactsServiceAdmin contactsServiceAdmin;

    @RequestMapping("/byName/{name}")
    public String byName(ModelMap model, @PathVariable String name) {
        model.addAttribute("byName_attr1", "search contacts by firstname");
        model.addAttribute("contacts", contactsServiceUser.byFirstName(name));
        return "contactsByName";
    }
    @RequestMapping("/byName/{name}-{surname}")
    public String byNameAndSurname(ModelMap model, @PathVariable String name, @PathVariable String surname) {
        model.addAttribute("byNameAndSurname_attr1", "search contacts by firstname and lastname");
        model.addAttribute("contacts", contactsServiceUser2.byFirstAndLastName(name,surname));
        return "contactsByNameAndSurname";
    }

    @RequestMapping("/usernames1")
    public String filteredUserNamesPreFilter(ModelMap model) {
        model.addAttribute("usernames_attr1", "usernames_filtered_with_prefilter");
        model.addAttribute("filtered_names", contactsServiceAdmin.displayOthers());
        return "usernames";
    }
    @RequestMapping("/usernames2")
    public String filteredUserNamesPostFilter(ModelMap model) {
        model.addAttribute("usernames_attr1", "usernames_filtered_with_postfilter");
        model.addAttribute("filtered_names", contactsServiceAdmin.filterOutThePrincipal());
        return "usernames";
    }

    // dummy section

    @RequestMapping("/dummy1")
    public String filteredUserNamesPreFilter2(ModelMap model) {
        model.addAttribute("comment", "prefilter with String arg");
        model.addAttribute("contents", contactsServiceAdmin.displayFilteredVarargStrings());
        return "dummy";
    }

    //
}
