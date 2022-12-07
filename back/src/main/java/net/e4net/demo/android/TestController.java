package net.e4net.demo.android;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

    @RequestMapping("/public/test")
    public String goTestPage() {
        return "samplePage2";
    }


}
