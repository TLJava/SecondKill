package com.example.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.common.vo.GoodsVo;
import com.example.pojo.User;
import com.example.service.IGoodsService;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 商品相关
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IGoodsService goodsService;
    /**
     * 对用户是否已登录进行校验，控制页面跳转到商品页面
     * @param model
     * // @param ticket  用户请求中携带的cookie，用户校验用户是否已登录
     * @return
     */
    @RequestMapping("/toList")
    public String toList(Model model, User user){
        // 优化：这些判断都封装到MVC配置类的参数条件判断中了
        //if (StringUtils.isEmpty(ticket)) {
        //    // 没有携带cookie，跳转到登录页面
        //    return "login";
        //}
        ////User user = (User) session.getAttribute(ticket);
        //User user = userService.getUserByCookie(ticket, request, response);
        //if(user == null){
        //    // cookie对应的用户不存在，跳转到登录页面
        //    return "login";
        //}
        model.addAttribute("user", user);
        model.addAttribute("goodsList", goodsService.findGoodsVo());
        // 跳转到商品列表页面
        return "goodsList";
    }

    @RequestMapping("/toDetail/{goodsId}")
    public String toDetail(Model model, User user,@PathVariable Long goodsId){
        model.addAttribute("user", user);
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();
        Date nowDate = new Date();
        // 记录秒杀状态
        int secKillStatus = 0;
        // 秒杀倒计时
        int remainSeconds = 0;
        // 秒杀还未开始
        if(nowDate.before(startDate)){
            remainSeconds = (int)((startDate.getTime()-nowDate.getTime()) / 1000);
        } else if(nowDate.after(endDate)){
            // 秒杀已结束
            secKillStatus = 2;
            remainSeconds = -1;
        } else {
            // 秒杀中
            secKillStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("remainSeconds", remainSeconds);
        model.addAttribute("secKillStatus", secKillStatus);
        model.addAttribute("goods", goodsVo);
        return "goodsDetail";
    }
}
