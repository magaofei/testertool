package hello;

import com.sun.net.httpserver.Headers;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class GreetingController {

    @GetMapping("/greeting")
    public String greetingForm(Model model) {
        model.addAttribute("greeting", new Greeting());
        // greeting html
        System.out.println(model);
        return "greeting";
    }

//    @PostMapping("/greeting")
//    public void greetingSubmit(@ModelAttribute Greeting greeting) {
////        return "result";
////        return "/download";
//
//
//    }

    @RequestMapping(path = "/download", method = RequestMethod.POST)
    public ResponseEntity<Resource> download(String param) throws IOException {

        // ...

        String fileName = "redis.conf";
        String filePath = "/Users/mark/Downloads/";

        File file = new File(filePath + fileName);

        // 设置下载名
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", fileName);


        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

}
