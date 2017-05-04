package com.deppon.foss.module.pickup.pricing.server.calculateservice.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 *******************************************
 * <b style="font-family:微软雅黑">
 *	<small>Description:
 *	绑定标注类，用于打在某个容器的某个控件属性上，
 *表示此空间将来会与某个JavaBean对象的某个属性进行绑定操作。
 *	</small>
 * </b></br>
 * <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
 * <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
 ********************************************
 * <div style="font-family:微软雅黑,font-size:70%"> 
 * 1 2011-3-24 rogger 新增
 * </div>  
 ********************************************
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface AfterAdv {
	/**
	 * 运用的时候需要给标注传入一个字符串参数，表示需要绑定的JavaBean属性的名字。
	 * value
	 * @return String
	 * @since:0.6
	 */
	String value();
}
