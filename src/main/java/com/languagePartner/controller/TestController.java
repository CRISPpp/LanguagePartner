package com.languagePartner.controller;

import com.languagePartner.common.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test")
    public R<String> test(){
        return R.success("test");
    }
}
