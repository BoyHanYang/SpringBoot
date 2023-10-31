package com.example.springboot.controller;



import com.example.springboot.entity.User;
import com.example.springboot.service.UserService;
import com.example.springboot.util.Constant;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author yangbohan
 * @Date 2023/10/24 0:15
 */
@Controller
public class LoginController {
    private UserService userService = new UserService();

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.POST)
    public String login(@RequestParam("username") String username, String password, String captcha, HttpServletRequest request, HttpSession session) {
        // 获取参数
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        String captcha = request.getParameter("captcha");

        // 校验验证码
        if (!CaptchaUtil.ver(captcha, request)) {
            // 验证码错误
            // 清除验证码
            CaptchaUtil.clear(request);
            // 重定向到登录页面
            return "redirect:/login";
        }

        // 清除验证码
        CaptchaUtil.clear(request);

        // 校验用户名密码
        User user = userService.login(username,password);

        if (user == null) {
            // 登录失败
            return "redirect:/login";
        }
        // 登录成功
        // 将用户信息保存到 session 中
        request.getSession().setAttribute(Constant.LOGIN_USER, user);
        // 重定向到首页
        return "redirect:/index";
    }

//    @RequestMapping("/testx")
//    public String pageerror() {
//        return "pageerror!";
//    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }
}
