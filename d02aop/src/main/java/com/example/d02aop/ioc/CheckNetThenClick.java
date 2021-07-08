package com.example.d02aop.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by hjcai on 2021/7/8.
 */
@Target(ElementType.METHOD)
public @Retention(RetentionPolicy.RUNTIME)
@interface CheckNetThenClick {
    //value代表可以该注解可以添加一个参数
    int value();
}
