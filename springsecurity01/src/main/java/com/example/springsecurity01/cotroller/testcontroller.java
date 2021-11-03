package com.example.springsecurity01.cotroller;

import com.example.springsecurity01.entity.User;
import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class testcontroller {

    @GetMapping({"/index.do","/"})
    public ModelAndView index(){
        ModelAndView mv=new ModelAndView();
        mv.addObject("newText","你好，Thymeleaf！");
        mv.addObject("gender","1");

        mv.setViewName("index.html");
        return mv;
    }
    @GetMapping("/login.do")
    public ModelAndView login(){
        ModelAndView mv=new ModelAndView();
        mv.setViewName("login.html");
        return mv;
    }

    @PostMapping("/sign.do")
    public ModelAndView sign(String username,String password){
        ModelAndView md=new ModelAndView();
        User user=new User();
        user.setUsername(username);
        user.setPassword(password);
        log.info("用户信息:{}",user);
        md.setViewName("index.html");
        return md;
    }
}
