package com.languagePartner.controller;

import com.languagePartner.util.FileUploader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//add xxx/user  xx/user/id

@Controller
public class WebsocketTestController {
    @GetMapping("/index")
    public String getIndex() throws Exception {
//        if(true){
//            //throw new SystemException(Code.SAVE_ERROR, "systemerror");
//            //throw new BusinessException(Code.BUSINESS_ERROR, "businesserror");
//            throw new RuntimeException();
//        }
        new FileUploader().upload();
        return "index";
    }
}
