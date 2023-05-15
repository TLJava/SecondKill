package com.example.service;

import com.example.common.vo.LoginVo;
import com.example.common.vo.RespBean;
import com.example.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LJava
 * @since 2023-05-05
 */
public interface IUserService extends IService<User> {

    /**
     * 登录
     * @param loginVo 封装起来的登录参数
     * @param request
     * @param response
     * @return 统一封装的返回结果
     */
    RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);

    /**
     * 根据cookie获取用户
     * @param userTicket
     * @return
     */
    User getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response);
}
