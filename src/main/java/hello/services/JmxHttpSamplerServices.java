package hello.services;

import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.protocol.http.sampler.HTTPSampler;
import org.apache.jmeter.threads.ThreadGroup;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JmxHttpSamplerServices {


    static public ResponseEntity<Resource> getJmxFile(HTTPSampler httpSampler, LoopController loopController,
                                                      ThreadGroup threadGroup)  throws IOException{

        String fullFilePath = JMXCreator.createJmxFile(
                httpSampler,
                loopController,
                threadGroup
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
