package com.example.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.common.vo.LoginVo;
import com.example.common.vo.RespBean;
import com.example.common.vo.RespBeanEnum;
import com.example.common.exception.GlobalException;
import com.example.pojo.User;
import com.example.mapper.UserMapper;
import com.example.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.utils.CookieUtil;
import com.example.utils.MD5Util;
import com.example.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LJava
 * @since 2023-05-05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 登录
     * @param loginVo 封装起来的登录参数
     * @param request
     * @param response
     * @return
     */
    @Override
    public RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        // 判空处理，即使前端已经判断过也要进行，防止绕过前端页面的恶意攻击
        // 这部分校验已经由自定义注解完成
        //if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
        //    return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        //}
        ////判断手机号码格式是否正确
        //if(!ValidatorUtil.isMobile(mobile)){
        //    return RespBean.error(RespBeanEnum.MOBILE_ERROT);
        //}
        //根据手机号获取用户
        User user = userMapper.selectById(mobile);
        if(user == null){
            // 定义好全局异常之后，只需要抛出异常，而不用在程序里直接返回
            //return RespBean.error(RespBeanEnum.LOGIN_ERROR);
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        //判断密码是否正确
        if(!MD5Util.formPassToDBPass(password, user.getSlat()).equals(user.getPassword())){
            //return RespBean.error(RespBeanEnum.LOGIN_ERROR);
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        // 生成cookie，用于校验用户是否已经登录
        String ticket = UUIDUtil.uuid();
        redisTemplate.opsForValue().set("user:"+ticket, user);
        //request.getSession().setAttribute(ticket, user);
        CookieUtil.setCookie(request, response, "userTicket", ticket);
        return RespBean.success();
    }

    /**
     * 功能描述：根据cookie获取用户
     * @param userTicket
     * @return
     */
    @Override
    public User getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response) {
        if(StringUtils.isEmpty(userTicket)){
            return null;
        }
        User user = (User)redisTemplate.opsForValue().get("user"+userTicket);
        if(user != null){
            CookieUtil.setCookie(request, response, "userTicket", userTicket);
        }
        return user;
    }
}
