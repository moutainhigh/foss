package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;
/**
 * 
 * @author 243921 zhangtingting
 * @date 2017-03-02 15:23:20
 * VTS自动受理反签收反结清：请求参数
 *
 */
public class TPSReverseSignSettleRequestDto implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	
	// 运单号
	private String waybillNo;
		
	// 接口类型
	private String autoReverseType;

	
	public String getAutoReverseType() {
		return autoReverseType;
	}

	public void setAutoReverseType(String autoReverseType) {
		this.autoReverseType = autoReverseType;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

}
