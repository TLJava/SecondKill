package com.example.service;

import com.example.common.vo.GoodsVo;
import com.example.pojo.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pojo.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LJava
 * @since 2023-05-15
 */
public interface IOrderService extends IService<Order> {

    /**
     * 秒杀
     * @param user
     * @param goods
     * @return
     */
    Order secKill(User user, GoodsVo goods);
}
