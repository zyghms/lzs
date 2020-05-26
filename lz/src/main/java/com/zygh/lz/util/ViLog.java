package com.zygh.lz.util;

import java.lang.annotation.*;

/**
 * 自定义注解类
 */
@Target(ElementType.METHOD) //注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
public @interface ViLog {
    /** 日志类型     */
    String logType() default "";


    /** 功能模块     */
    String module() default "";

}
