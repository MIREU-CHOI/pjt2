package net.e4net.demo.android;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class SampleController {

    @Autowired
    private FcmService fcmService;

    @RequestMapping("/public/sample")
    public String goSamplePage() {
        return "samplePage";
    }

    @GetMapping("/public/sample/data")
    public @ResponseBody Map<String, Object> sampleData(String param) {
        Map<String, Object> data = new HashMap<>();
        data.put("result", true);
        data.put("data", "param=" + param);
        return data;
    }


    @RequestMapping("/public/fcm")
    public String goFcmPage(){
        return "fcmPage";
    }

//    @GetMapping("public/fcm/push")
//    public @ResponseBody Map<String, Object> sendFcmPush(@RequestParam Map<String, Object> param){
//        Map<String, Object> map = new HashMap<>();
//
//        map.put("param", param);
//        map.put("result", fcmService.sendPush(param));
//        return map;
//    }
}



