package com.example.springboot.controller;

import com.example.springboot.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author yangbohan
 * @Date 2023/10/26 20:08
 */

@Controller
public class HelloController {


    /**
     * 标记了 @RequestMapping 的方法就是一个 handler 方法
     * handler 方法在SpringBoot中的作用就是接收请求，处理请求，返回响应
     *
     * @RequestMapping("/hello") 表示接收 /hello 请求
     */
    @RequestMapping("/hello")
    public void hello(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("hello world");


//        System.out.println("request = " + request);
//        System.out.println("response = " + response);

        // 接收请求参数
        String name = request.getParameter("name");
        System.out.println("name = " + name);

        // 响应内容
        response.getWriter().write("hello springboot");
    }

    /**
     * 返回页面
     * @return 逻辑视图名称
     */
    @RequestMapping("/page")
    public String index(HttpServletRequest request){
        request.setAttribute("message", "hello springboot");
        // 逻辑视图名称
        return "index";
    }


    @RequestMapping("/str")
    @ResponseBody
    public String str(){
        return "str";
    }

    @RequestMapping("/object")
    public User obj(){
        User user = new User();
        user.setId(1);
        user.setUserName("admin");
        user.setPassword("123456");
        user.setNickName("管理员");
        return user;
    }
}