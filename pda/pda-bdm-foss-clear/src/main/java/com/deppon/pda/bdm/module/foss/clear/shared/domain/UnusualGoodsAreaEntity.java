package com.deppon.pda.bdm.module.foss.clear.shared.domain;

import java.io.Serializable;
/**
 * 
 * TODO(判断异常货区实体)
 * @description 判断异常货区实体
 * @author 268974 wangzhili
 * @date 2015-11-14 上午10:06:10
 */

public class UnusualGoodsAreaEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * 货区编号
	 */
	private String crgAreaCode;
	/*
	 * 外场编号
	 */
	private String wstrBigDepartmentCodeU;
	public String getCrgAreaCode() {
		return crgAreaCode;
	}
	public void setCrgAreaCode(String crgAreaCode) {
		this.crgAreaCode = crgAreaCode;
	}
	public String getWstrBigDepartmentCodeU() {
		return wstrBigDepartmentCodeU;
	}
	public void setWstrBigDepartmentCodeU(String wstrBigDepartmentCodeU) {
		this.wstrBigDepartmentCodeU = wstrBigDepartmentCodeU;
	}
	
}
