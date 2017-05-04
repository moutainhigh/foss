package com.deppon.foss.module.transfer.stock.server.message.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 合车新增
 * Created by 335284 on 2017/3/31.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TogetherAdd {
}
