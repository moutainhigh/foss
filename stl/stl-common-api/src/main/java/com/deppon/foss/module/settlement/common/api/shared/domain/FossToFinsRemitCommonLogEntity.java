package com.deppon.foss.module.settlement.common.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;


/**
 * @Description: 记录推送第三方付款数据到财务自助响应
 * @author 321603
 * 2016-10-14
 */
public class FossToFinsRemitCommonLogEntity extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	private String billNum;
	
	private String isSuccess;
	
	private String faileReason;
	
	
	public String getBillNum() {
		return billNum;
	}

	public void setBillNum(String billNum) {
		this.billNum = billNum;
	}

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getFaileReason() {
		return faileReason;
	}

	public void setFaileReason(String faileReason) {
		this.faileReason = faileReason;
	}
	
}
