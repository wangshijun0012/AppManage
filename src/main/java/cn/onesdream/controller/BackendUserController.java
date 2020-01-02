package cn.onesdream.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/backend")
public class BackendUserController {
    @RequestMapping("/list")
    public String list(){
        return "/backend/applist";
    }

}
