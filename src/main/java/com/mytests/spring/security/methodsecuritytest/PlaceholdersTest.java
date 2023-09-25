package com.mytests.spring.security.methodsecuritytest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class PlaceholdersTest {

    // test placeholders in annotations (not in SpEL) - ok both for application.properties and application.yaml
    @Value("${my.prop.name}")
    String foo;
    @RequestMapping("${my.prop.url}")
    public String mappingWithPlaceholder(){
        return "with placeholder";
    }

    // placeholders inside SpEL: ok for application.properties, doesn't work for application.yaml
    @PreAuthorize("#pr.name == ${my.prop.name} and hasRole(${my.prop.role})")
    @RequestMapping("/with_placeholder_in_spel")
    public String samplesTest1(ModelMap model, @P("pr") Principal principal){
        return principal.getName();
    }
}
