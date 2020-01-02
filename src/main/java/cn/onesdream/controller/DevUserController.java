package cn.onesdream.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/dev")
public class DevUserController {
    @RequestMapping("/list")
    public String list(){
        return "/developer/appinfolist";
    }

}
