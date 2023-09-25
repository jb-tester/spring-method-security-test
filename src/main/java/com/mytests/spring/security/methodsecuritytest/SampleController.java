package com.mytests.spring.security.methodsecuritytest;

import com.mytests.spring.security.methodsecuritytest.data.SampleEntity;
import com.mytests.spring.security.methodsecuritytest.repositories.SampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;


@Controller
public class SampleController {


    @Autowired
    private SampleRepository sampleRepository;

    @PreAuthorize("hasAnyRole('USER','ADMIN') and (#pr.name == ${my.prop.name})")
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

    @PreAuthorize("@authUtils.check(#result!='error')")
    @RequestMapping("/samples/test2/{pv}")
    public String samplesTest2(ModelMap model, @PathVariable @P("result") String pv) {
        model.addAttribute("samples_test2_attr1", sampleRepository.findAllByVersionAndColor(5,"pink"));
        model.addAttribute("samples_test2_attr2", pv);
        return "samples_test2";
    }

    @PostAuthorize("returnObject.id!=3")
    @RequestMapping("/samples/test3/{color}")
    @ResponseBody
    public SampleEntity samplesTest3(ModelAndView model, @PathVariable String color) {
        SampleEntity sample = sampleRepository.findFirstByColor(color);
        model.setViewName("samples_test3");
        model.addObject("samples_test3_attr1", sample.toString());
        return sample;
    }


}
