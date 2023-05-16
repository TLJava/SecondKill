package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.vo.GoodsVo;
import com.example.common.vo.RespBeanEnum;
import com.example.pojo.Order;
import com.example.pojo.SeckillOrder;
import com.example.pojo.User;
import com.example.service.IGoodsService;
import com.example.service.IOrderService;
import com.example.service.ISeckillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 秒杀控制器
 */
@Controller
@RequestMapping("/secKill")
public class SecKillController {

    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private ISeckillOrderService seckillOrderService;
    @Autowired
    private IOrderService orderService;

    @RequestMapping("/doSecKill")
    public String doSecKill(Model model, User user, Long goodsId){
        // 用户不存在，跳转到登录页面，先进行登录
        if(user == null){
            return "login";
        }
        model.addAttribute("user", user);
        GoodsVo goods = goodsService.findGoodsVoByGoodsId(goodsId);
        // 判断库存
        if(goods.getStockCount() < 1){
            model.addAttribute("errmsg", RespBeanEnum.EMPTY_STOCK.getMessage());
            // 跳转到秒杀失败提示页面
            return "secKillFail";
        }
        // 判断是否重复抢购
        SeckillOrder secKillOrder = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>().eq("user_id", user
                .getId()).eq("goods_id", goodsId));
        if(secKillOrder != null){
            model.addAttribute("errmsg", RespBeanEnum.REPEATE_ERROR.getMessage());
            return "secKillFail";
        }
        Order order = orderService.secKill(user, goods);
        model.addAttribute("order", order);
        model.addAttribute("goods", goods);
        // 抢购成功，返回订单详情页面
        return  "orderDetail";
    }
}
