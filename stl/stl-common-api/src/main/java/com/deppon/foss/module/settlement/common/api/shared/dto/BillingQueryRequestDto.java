package com.deppon.foss.module.settlement.common.api.shared.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * ptp查询结算单据传入参数dto
 * 
 * @author gpz
 * @date 2016年8月5日
 */
public class BillingQueryRequestDto implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 737858035232655437L;

	/**
	 * 开始时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	
	/**
	 * 结束时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;

	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}
