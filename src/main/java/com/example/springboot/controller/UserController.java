package com.example.springboot.controller;


import com.example.springboot.entity.User;
import com.example.springboot.ex.RegisterException;
import com.example.springboot.ex.ServiceException;
import com.example.springboot.service.FileService;
import com.example.springboot.service.UserService;
import com.example.springboot.util.Constant;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author yangbohan
 * @Date 2023/10/25 21:50
 */
@Controller
public class UserController {
        private UserService userService = new UserService();
    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public String signUpPage(HttpServletRequest request) {
        return "signUp";
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public String singUpPage(User user, HttpServletRequest request, String captcha,
                             String repassword, RedirectAttributes redirectAttributes) {
        if (!CaptchaUtil.ver(captcha, request)) {
            CaptchaUtil.clear(request);
            // 保存错误信息
            redirectAttributes.addFlashAttribute("message", "验证码错误");
            return "redirect:/signUp";
        }
        CaptchaUtil.clear(request);
        try {
            userService.signUp(user, repassword);
        } catch (RegisterException e) {
            e.printStackTrace();
            // 注册失败
            String message = e.getMessage();
            // 由于是重定向，所以这里使用 session 保存错误信息
//            request.getSession().setAttribute("message", message);
            // 使用 RedirectAttributes 保存错误信息， RedirectAttributes 用于重定向间传递数据
            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/signUp";
        }

        redirectAttributes.addFlashAttribute("message", "注册成功");
        // 注册成功
        return "redirect:/login";
    }

    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    public String list(Model model) {
        // templates/user/list.html
        // 查询用户列表
        List<User> list = userService.list();
        // 存到域中
        model.addAttribute("list", list);
        return "user/list";
    }

    @RequestMapping(value = "/user/edit", method = RequestMethod.GET)
    public String edit(Model model, Integer id) {
        // 查询用户信息
        User byId = userService.getById(id);
        // 存到域中
        model.addAttribute("user", byId);
        return "user/edit";
    }

    @RequestMapping(value = "/user/edit", method = RequestMethod.POST)
    public String edit(User user, RedirectAttributes redirectAttributes) {
        // 修改用户信息
        try {
            userService.update(user);
            return "redirect:/user/list";
        } catch (ServiceException e) {
            // 保存错误信息
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/user/edit?id=" + user.getId();
        }
    }

    @RequestMapping(value = "/user/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        // 删除 id 为 1的用户，/user/delete/1
        // 删除用户信息
        try {
            userService.delete(id);
            return "redirect:/user/list";
        } catch (ServiceException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/user/list";
        }
    }

    @GetMapping("/user/avatar")
    public String avatar() {
        return "user/avatar";
    }

    @PostMapping("/user/avatar")
    public String changeAvatar(MultipartFile img, @SessionAttribute(Constant.LOGIN_USER) User user,
                               RedirectAttributes redirectAttributes) throws ServiceException {
        String path = fileService.save(img, user.getId());

        // 更新用户头像
        user.setAvatar(path);
        // 更新数据库数据
        User u = new User();
        u.setId(user.getId());
        u.setAvatar(path);
        userService.update(u);

        redirectAttributes.addFlashAttribute("message", "头像修改成功");
        return "redirect:/user/avatar";
    }


}
