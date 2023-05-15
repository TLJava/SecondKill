package com.example.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component
public class MD5Util {

    /**
     * 对传入的字符串进行加密
     * @param src 需要加密的字符串
     * @return 加密后的字符串
     */
    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    // 自定义盐的值
    private static final String salt = "1a2b3c4d";

    /**
     * 第一次加密（对用户输入的明文密码进行加密），在前端进行加密
     * @param inputPass 用户输入的明文密码
     * @return 第一次加密后得到的密文字符串
     */
    public static String inputPassToFormPass(String inputPass){
        String str = salt.charAt(0)+salt.charAt(2)+inputPass+salt.charAt(5)+salt.charAt(4);
        return md5(str);
    }

    /**
     * 第二次加密（在后端将密码存入数据库之前对密文再次加密）
     * @param formPass 前端已经加密过的密码密文
     * @param salt 加密用到的盐
     * @return 第二次加密后的密文
     */
    public static String formPassToDBPass(String formPass, String salt){
        String str = salt.charAt(0)+salt.charAt(2)+formPass+salt.charAt(5)+salt.charAt(4);
        return md5(str);
    }

    /**
     * 对两次加密算法进行封装
     * @param inputPass 用户输入的明文密码
     * @param salt 加密用到的盐
     * @return 两次加密后得到的密文
     */
    public static String inputPassToDBPass(String inputPass, String salt){
        String formPass = inputPassToFormPass(inputPass);
        String dbPass = formPassToDBPass(formPass, salt);
        return dbPass;
    }

    // 测试加密是否成功
    public static void main(String[] args) {
        // 第一次加密后得到的密文：ce21b747de5af71ab5c2e20ff0a60eea
        System.out.println(inputPassToFormPass("123456"));
        System.out.println(formPassToDBPass("ce21b747de5af71ab5c2e20ff0a60eea", "1a2b3c4d"));
        System.out.println(inputPassToDBPass("123456","1a2b3c4d"));
    }

}
