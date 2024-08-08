package com.finalProject.questionAndAnswer.feature.testing;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {


    @GetMapping
    public Map<?,?> test(){
        return Map.of("message","sucess");
    }




}
