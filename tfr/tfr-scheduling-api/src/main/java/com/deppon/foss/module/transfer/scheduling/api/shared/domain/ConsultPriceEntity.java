package com.deppon.foss.module.transfer.scheduling.api.shared.domain;

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 询价信息的Entity
 * @author 268084
 *
 */
public class ConsultPriceEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 询价信息表的主编号
	 */
	private String consultInfoId;
	/**
	 * 询价编号
	 */
	private String consultPriceNo;
	/**
	 * 报价信息
	 */
	private BigDecimal quotedInfo;
	/**
	 * 询价部门
	 */
	private String needVehicleDept;
	
	
	
	public String getConsultInfoId() {
		return consultInfoId;
	}
	public void setConsultInfoId(String consultInfoId) {
		this.consultInfoId = consultInfoId;
	}
	public String getConsultPriceNo() {
		return consultPriceNo;
	}
	public void setConsultPriceNo(String consultPriceNo) {
		this.consultPriceNo = consultPriceNo;
	}
	public BigDecimal getQuotedInfo() {
		return quotedInfo;
	}
	public void setQuotedInfo(BigDecimal quotedInfo) {
		this.quotedInfo = quotedInfo;
	}
	public String getNeedVehicleDept() {
		return needVehicleDept;
	}
	public void setNeedVehicleDept(String needVehicleDept) {
		this.needVehicleDept = needVehicleDept;
	}
	
	
}
