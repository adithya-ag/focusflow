package com.test.focusflow.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/start-test")
    public String startTest() {
        return "test";
    }
}
