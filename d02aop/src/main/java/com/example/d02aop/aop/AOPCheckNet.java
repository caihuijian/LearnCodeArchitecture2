package com.example.d02aop.aop;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by hjcai on 2021/7/8.
 */
@Target(ElementType.METHOD) // Target 放在哪个位置
@Retention(RetentionPolicy.RUNTIME)// RUNTIME 运行时 xUtils  CLASS 代表编译时期 ButterKnife   SOURCE 代表资源
public @interface AOPCheckNet { // @interface 注解
}