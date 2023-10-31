package com.example.springboot.controller;

import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @Author yangbohan
 * @Date 2023/10/24 20:26
 */
@Controller
public class CaptchaController {
    @RequestMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 生成验证码
        CaptchaUtil.out(request,response);
    }
}
