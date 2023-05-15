package com.example.common.verify;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.utils.ValidatorUtil;
import com.example.validator.IsMobile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义注解(IsMobile)对应的校验类：手机号码校验规则
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    // 是否为必填
    private boolean required = false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(required){
            return ValidatorUtil.isMobile(s);
        }
        else{
            if(StringUtils.isEmpty(s)){
                return true;
            }
            else {
                return ValidatorUtil.isMobile(s);
            }
        }
    }
}
