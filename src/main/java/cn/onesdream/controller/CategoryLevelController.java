package cn.onesdream.controller;

import cn.onesdream.service.AppCategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/")
public class CategoryLevelController {
    @Resource
    private AppCategoryService appCategoryService;
    @RequestMapping("/categorylevellist.json")
    @ResponseBody
    public Object categorylevellist(String pid){
        Long id = Long.parseLong(pid);
        if(id != null && id == 0 ){
            if(id == 1 || id == 2){
                return appCategoryService.a
            }
        }
    }
}
