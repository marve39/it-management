package data.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.cloud.netflix.feign.FeignClient;

@RestController
//@FeignClient(name = "bigbluebutton", url = "${id.jumpaja.url}")
public class MeetingController {

    @RequestMapping("/meeting-api")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}
