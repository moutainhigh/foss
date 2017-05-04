package com.deppon.foss.module.pickup.predeliver.api.shared.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * @ClassName: AllowBlank 
 * @Description: 允许为空注解类 
 * @author 237982-foss-fangwenjun 
 * @date 2015-4-14 上午10:52:31 
 *  
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AllowBlank {
	
	/**
	 * @Title: value
	 * @Description: 设置注解的值
	 * @return true允许为空  false不允许为空
	 */
	public boolean value() default false;
}
