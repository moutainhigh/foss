package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;

/**
 * 压力测试Vo.
 *
 * @author foss-zhangxiaohui
 * @date 2013-4-8 下午4:57:25
 */
public class PressureTestVo implements Serializable{

	/**
	 * 序列化ID.
	 */
	private static final long serialVersionUID = 7381600139342599004L;

	/**
	 * 组织编码.
	 */
	private String code;
	
	/**
	 * 获取 组织编码.
	 *
	 * @return  the code
	 */
	public String getCode() {
	    return code;
	}

	
	/**
	 * 设置 组织编码.
	 *
	 * @param code the code to set
	 */
	public void setCode(String code) {
	    this.code = code;
	}
	
	

}