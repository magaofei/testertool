package hello.Controller;


//import hello.Modal.JmxHttpSampler;

import hello.services.JmxHttpSamplerServices;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.protocol.http.sampler.HTTPSampler;
import org.apache.jmeter.protocol.http.util.HTTPArgument;
import org.apache.jmeter.threads.ThreadGroup;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

@Controller
public class JmxHttpSamplerController {

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String index(Model model) {

        return "greeting";
    }

//    @PostMapping("/greeting")
//    public void greetingSubmit(@ModelAttribute JmxHttpSampler greeting) {
////        return "result";
////        return "/download";
//
//
//    }

    @RequestMapping(path = "/download", method = RequestMethod.POST)
    public ResponseEntity<Resource> download(
            @ModelAttribute HTTPSampler httpSampler, @ModelAttribute LoopController loopController,
            @ModelAttribute ThreadGroup threadGroup, @ModelAttribute HTTPArgument httpArgument)
            throws IOException {

        // ...

//        JmxHttpSamplerServices jmxHttpSamplerServices = new JmxHttpSamplerServices();


        return JmxHttpSamplerServices.getJmxFile(httpSampler, loopController, threadGroup, httpArgument);


    }

    @RequestMapping(path = "/testArgument", method = RequestMethod.GET)
    public void argument (@ModelAttribute Arguments arguments) {
        System.out.println(arguments);
//        arguments.
        //        System.out.println(jMeterEntity);

//        return httpArgument.getName();
    }

}
