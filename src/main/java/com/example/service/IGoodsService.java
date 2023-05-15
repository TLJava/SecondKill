package com.example.service;

import com.example.common.vo.GoodsVo;
import com.example.pojo.Goods;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LJava
 * @since 2023-05-15
 */
public interface IGoodsService extends IService<Goods> {

    /**
     * 功能描述，返回商品列表
     * @return
     */
    List<GoodsVo> findGoodsVo();

    /**
     * 获取商品详情
     * @param goodsId
     * @return
     */
    GoodsVo findGoodsVoByGoodsId(Long goodsId);
}
