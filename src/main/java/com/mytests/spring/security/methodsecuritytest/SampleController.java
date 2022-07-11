package com.mytests.spring.security.methodsecuritytest;

import com.mytests.spring.security.methodsecuritytest.data.SampleEntity;
import com.mytests.spring.security.methodsecuritytest.repositories.SampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import java.security.Principal;


@Controller
public class SampleController {


    @Autowired
    private SampleRepository sampleRepository;

    @PreAuthorize("hasAnyRole('USER','ADMIN') and (#pr.name == 'irina')")
    @RequestMapping("/samples/test1")
    public String samplesTest1(ModelMap model, @P("pr") Principal my_principal) // https://youtrack.jetbrains.com/issue/IDEA-285147
    {
        StringBuilder rez = new StringBuilder();
        for (SampleEntity sample : sampleRepository.versionAndColor(10, "red")) {
            rez.append(sample.toString());
        }
        model.addAttribute("samples_test1_attr", rez.toString());
        model.addAttribute("samples_test1_principal", my_principal.getName());
        return "samples_test1";
    }
}
