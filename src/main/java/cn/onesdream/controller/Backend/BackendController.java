package cn.onesdream.controller.Backend;

import cn.onesdream.pojo.BackendUser;
import cn.onesdream.service.BackendUserService;
import cn.onesdream.tools.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 管理者登录控制层
 */
@Controller
@RequestMapping("/backend")
public class BackendController {

    //管理者相关的业务类
    @Resource
    private BackendUserService backendUserService;

    //管理者登录
    @RequestMapping("/login")
    public String login() {
        return "/backendlogin";
    }

    //管理者登录处理
    @RequestMapping("/dologin")
    public String dologin(HttpServletRequest request, HttpSession session) {
        String userCode = request.getParameter("userCode");
        String userPassword = request.getParameter("userPassword");
//    调用servi方法进行管理者用户匹配
        BackendUser backendUser = backendUserService.getTheUser(userCode, userPassword);
        if (backendUser != null) {
//      登录成功  放入session
            session.setAttribute(Constants.USER_SESSION, backendUser);
            return "/backend/main";
        } else {
//      登陆失败 带着错误信息返回登录页面
            request.setAttribute("error", "用户名或密码错误！");
            return "/backendlogin";
        }
    }

    //退出登录
    @RequestMapping("/loginOut")
    public String loginout(HttpSession Session) {
//    清除session 返回主界面选择登录的模块
        Session.removeAttribute(Constants.USER_SESSION);
        return "redirect:../index.jsp";
    }

}
