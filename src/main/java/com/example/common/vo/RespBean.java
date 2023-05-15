package com.example.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 公共返回对象（统一封装返回结果类型）
 */
@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {

    private long code;
    private String message;
    private Object obj;

    /**
     * 功能：成功返回结果（无参数的情况）
     * @return
     */
    public static RespBean success() {
        return new RespBean(RespBeanEnum.SUCCESS.getCode(), RespBeanEnum.SUCCESS.getMessage(), null);
    }

    /**
     * 重载成功返回结果
     * @param obj 返回消息封装对象
     * @return 统一封装的返回结果
     */
    public static RespBean success(Object obj){
        return new RespBean(RespBeanEnum.SUCCESS.getCode(), RespBean.success().getMessage(), obj);
    }

    /**
     * 失败返回结果
     * @param respBeanEnum 失败类型枚举信息
     * @return 统一封装的返回结果
     */
    public static RespBean error(RespBeanEnum respBeanEnum){
        return new RespBean(respBeanEnum.getCode(), respBeanEnum.getMessage(), null);
    }

    /**
     * 重载失败返回的结果
     * @param respBeanEnum 返回结果类型枚举
     * @param obj 封装的消息对象
     * @return 统一封装的返回结果
     */
    public static RespBean error(RespBeanEnum respBeanEnum, Object obj){
        return new RespBean(respBeanEnum.getCode(), respBeanEnum.getMessage(), obj);
    }

}
