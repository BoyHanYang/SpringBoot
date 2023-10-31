package com.example.springboot.controller;

import com.example.springboot.entity.FileModel;
import com.example.springboot.entity.Page;
import com.example.springboot.entity.User;
import com.example.springboot.ex.ServiceException;
import com.example.springboot.service.FileService;
import com.example.springboot.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @Author yangbohan
 * @Date 2023/10/26 20:02
 */

@RequestMapping("/file") // 通过这里配置使下面的映射都在 /file 下
@Controller
@ResponseBody // 该注解表示该类的所有方法返回的数据直接写给浏览器，如果是对象转为 json 数据
public class FileController {

    @Autowired
    private FileService fileService;


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(ModelAndView mv) {
        List<FileModel> list = fileService.list();
        mv.addObject("list", list);
        mv.setViewName("file/list");
        return mv;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add() {
        ModelAndView mv = new ModelAndView("file/add");
        return mv;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView upload(@RequestParam("img") MultipartFile file, @SessionAttribute(Constant.LOGIN_USER) User user) {
        ModelAndView mv = new ModelAndView("redirect:/file/list");
        if (!file.isEmpty()){
            try {
                fileService.save(file, user.getId());
            } catch (ServiceException e) {
                e.printStackTrace();
                mv.setViewName("file/add");
            }
        }
        return mv;
    }

}

