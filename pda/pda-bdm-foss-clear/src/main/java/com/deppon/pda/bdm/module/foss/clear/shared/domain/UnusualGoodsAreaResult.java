package com.deppon.pda.bdm.module.foss.clear.shared.domain;

import java.io.Serializable;

/**
 * 
 * TODO(判断异常货区返回实体)
 * @description 判断异常货区返回实体
 * @author 268974 wangzhili
 * @date 2015-11-14 上午10:06:10
 */

public class UnusualGoodsAreaResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * 是否异常
	 */
	private String isabnormal;
	
	public String getIsabnormal() {
		return isabnormal;
	}
	public void setIsabnormal(String isabnormal) {
		this.isabnormal = isabnormal;
	}
	
}
