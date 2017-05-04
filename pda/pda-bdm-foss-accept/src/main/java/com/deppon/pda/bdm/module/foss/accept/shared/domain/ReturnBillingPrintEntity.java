package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;

/**
 * 获取开单信息打印--实体类
 * @author 245955
 *Date 2016-03-15
 */
public class ReturnBillingPrintEntity  implements Serializable {
	private static final long serialVersionUID = 1L;
    
	//运单号
	private String  waybillCode;
	//查询标示位
    private String  changCode;
    
	public String getWaybillCode() {
		return waybillCode;
	}
	public void setWaybillCode(String waybillCode) {
		this.waybillCode = waybillCode;
	}
	public String getChangCode() {
		return changCode;
	}
	public void setChangCode(String changCode) {
		this.changCode = changCode;
	}
}
