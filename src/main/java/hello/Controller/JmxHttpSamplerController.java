package hello.Controller;


//import hello.Modal.JmxHttpSampler;
import hello.services.JmxHttpSamplerServices;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.protocol.http.sampler.HTTPSampler;
import org.apache.jmeter.threads.ThreadGroup;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

@EnableAutoConfiguration
@Controller
public class JmxHttpSamplerController {

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String greetingForm(Model model) {
//        model.addAllAttributes("HTTPSampler", Array<new HTTPSampler>);
        model.addAttribute("HTTPSampler", new HTTPSampler());
        model.addAttribute("LoopController", new LoopController());
        model.addAttribute("ThreadGroup", new ThreadGroup());

        return "index";
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
            @ModelAttribute ThreadGroup threadGroup)
            throws IOException {

        // ...

//        JmxHttpSamplerServices jmxHttpSamplerServices = new JmxHttpSamplerServices();


        return JmxHttpSamplerServices.getJmxFile(httpSampler, loopController, threadGroup);


    }

}
