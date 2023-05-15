package com.example.controller;

import com.example.common.vo.LoginVo;
import com.example.common.vo.RespBean;
import com.example.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 登录控制类
 * 这个类是用于实现跳转到登录页面，不能用RestController注解，因为这个注解会给该类的所有方法返回值加上一个ResponseBody
 */
@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @Autowired
    IUserService userService;

    /**
     * 功能描述：跳转登录页面
     * @return
     */
    @RequestMapping("/toLogin")
    public String toLogin() {
        //@Controller + @RequestMapping 组合注解可以实现在方法中return页面名称
        // 最终返回给用户的就是对应名称的页面
        return "login";
    }

    /**
     * 登录功能
     * @param loginVo
     * @return
     */
    @RequestMapping("/doLogin")
    @ResponseBody
    public RespBean doLogin(@Valid LoginVo loginVo, HttpServletRequest request, HttpServletResponse response){

        return userService.doLogin(loginVo, request, response);
    }

}
