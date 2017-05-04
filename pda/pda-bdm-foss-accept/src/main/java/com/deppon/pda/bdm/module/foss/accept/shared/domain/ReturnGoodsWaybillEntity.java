package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;
/**
 * 返货上报实体
 * @author 268974 wangzhili
 * @Date 2015-10-14
 */
public class ReturnGoodsWaybillEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 校验单号
	 */
	private String wayBillCode;
	/**
	 * 返货方式
	 */
	private String returnMode;
	

	public String getWayBillCode() {
		return wayBillCode;
	}

	public void setWayBillCode(String wayBillCode) {
		this.wayBillCode = wayBillCode;
	}

	public String getReturnMode() {
		return returnMode;
	}

	public void setReturnMode(String returnMode) {
		this.returnMode = returnMode;
	}

}
