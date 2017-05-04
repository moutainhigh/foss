package com.deppon.foss.module.transfer.scheduling.api.shared.vo;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.ConsultPriceEntity;

public class ConsultPriceVO implements java.io.Serializable{

	/**
	 * 得到询价信息VO
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 询价信息的实体
	 */
	private ConsultPriceEntity consultPriceEntity;
	/**
	 * 询价编号
	 */
	private String consultPriceNo;
	
	
	public ConsultPriceEntity getConsultPriceEntity() {
		return consultPriceEntity;
	}
	public void setConsultPriceEntity(ConsultPriceEntity consultPriceEntity) {
		this.consultPriceEntity = consultPriceEntity;
	}
	public String getConsultPriceNo() {
		return consultPriceNo;
	}
	public void setConsultPriceNo(String consultPriceNo) {
		this.consultPriceNo = consultPriceNo;
	}
	
	
	
	
}
