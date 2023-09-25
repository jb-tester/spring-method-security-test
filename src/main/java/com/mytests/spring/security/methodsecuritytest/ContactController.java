package com.mytests.spring.security.methodsecuritytest;

import com.mytests.spring.security.methodsecuritytest.data.ContactEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

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
    @Autowired
    private PostFilters postFilters;

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

    ////////////////////////////////////////////////////////////////////////////
    /////   PostFilters. Collections, Arrays, Maps and Streams are supported ///
    ///////////////////////////////////////////////////////////////////////////

    @RequestMapping("/usernames2")
    public String filteredUserNamesPostFilter(ModelMap model) {
        model.addAttribute("usernames_attr1", "post filter with contacts list");
        model.addAttribute("filtered_names", postFilters.filterOutThePrincipal());
        return "usernames";
    }

    @RequestMapping("/postFilterArray")
    public String postFilterArray(ModelMap model) {
        StringBuilder rez = new StringBuilder();
        for (ContactEntity contact : postFilters.arrayTest()) {
            rez.append("- ").append(contact.getFirstname()).append(" \n");
        }
        model.addAttribute("comment", "postfilter with contacts array");
        model.addAttribute("contents", rez);
        return "dummy";
    }
    @RequestMapping("/postFilterMap")
    public String postFilterMap(ModelMap model) {
        StringBuilder rez = new StringBuilder();
        postFilters.mapTest().forEach((k, v) -> rez.append(k).append(":").append(v));
        model.addAttribute("comment", "postfilter with contacts map");
        model.addAttribute("contents", rez);
        return "dummy";
    }
    @RequestMapping("/postFilterStream")
    public String postFilterStream(ModelMap model) {
        StringBuilder rez = new StringBuilder();
        postFilters.streamTests().forEach(c -> rez.append(c.getFirstname()).append("|"));
        model.addAttribute("comment", "postfilter with contacts stream");
        model.addAttribute("contents", rez);
        return "dummy";
    }

    //////////////////////////////////////////////////////
    /////   PreFilters. Only Collections are supported ///
    /////////////////////////////////////////////////////

    @RequestMapping("/usernames1")
    public String filteredUserNamesPreFilter(ModelMap model) {
        model.addAttribute("usernames_attr1", "usernames_filtered_with_prefilter");
        model.addAttribute("filtered_names", contactsServiceAdmin.displayOthers());
        return "usernames";
    }

    // error: Pre-filtering on array types is not supported.

    @RequestMapping("/preFilterVararg")
    public String preFilterVararg(ModelMap model) {
        model.addAttribute("comment", "prefilter with String vararg");
        model.addAttribute("contents", contactsServiceAdmin.displayFilteredVarargStrings());
        return "dummy";
    }

    // error: Pre-filtering on array types is not supported.
    @RequestMapping("/preFilterArray")
    public String preFilterArray(ModelMap model) {
        model.addAttribute("comment", "prefilter with contacts array");
        model.addAttribute("contents", contactsServiceAdmin.displayFilteredArray());
        return "dummy";
    }
    // error: A PreFilter expression was set but the method argument typeclass java.util.HashMap is not filterable
    @RequestMapping("/preFilterMap")
    public String preFilterMap(ModelMap model) {
        model.addAttribute("comment", "prefilter with contacts map");
        model.addAttribute("contents", contactsServiceAdmin.displayFilteredMap());
        return "dummy";
    }
    // error: A PreFilter expression was set but the method argument typeclass java.util.stream.ReferencePipeline$Head is not filterable
    @RequestMapping("/preFilterStream")
    public String preFilterStream(ModelMap model) {
        model.addAttribute("comment", "prefilter with contacts stream");
        model.addAttribute("contents", contactsServiceAdmin.displayFilteredStream());
        return "dummy";
    }

}
