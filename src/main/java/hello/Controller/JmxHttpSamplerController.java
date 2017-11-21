package hello.Controller;


//import hello.Modal.JmxHttpSampler;
import jmx.JMXCreator;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.protocol.http.sampler.HTTPSampler;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class JmxHttpSamplerController {

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String greetingForm(Model model) {
        model.addAttribute("HTTPSampler", new HTTPSampler());
        model.addAttribute("LoopController", new LoopController());

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
            @ModelAttribute HTTPSampler httpSampler, @ModelAttribute LoopController loopController)
            throws IOException {

        // ...


        String fullFilePath = JMXCreator.createJmxFile(
                httpSampler,
                loopController
        );

//        String fileName = "redis.conf";
//        String filePath = "/Users/mark/Downloads/";

        File file = new File(fullFilePath);
        String[] fileName = fullFilePath.split("/");

        // 设置下载名
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", fileName[fileName.length-1]);


        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

}
