package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="EcomOrderDetailDto")
public class EcomOrderDetailDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3235211211382920768L;

	/**
	 * 原始订单号
	 */
	protected String originalNumber;
	
	/**
	 * 运单号
	 */
	protected String waybillNumber;
	
	/**
	 * 总重量
	 */
	protected Double totalWeight;
	
	/**
	 * 总体积
	 */
	protected Double totalVolume;
	
	/**
	 * 是否子母件
	 */
	protected String isPicPackage;
	
	/**
	 * 修改类型
	 */
	private String modifyType;
	
	/**
	 * 是否存在下单
	 */
	private String isExisted;

	public String getOriginalNumber() {
		return originalNumber;
	}

	public void setOriginalNumber(String originalNumber) {
		this.originalNumber = originalNumber;
	}

	public String getWaybillNumber() {
		return waybillNumber;
	}

	public void setWaybillNumber(String waybillNumber) {
		this.waybillNumber = waybillNumber;
	}

	public Double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}

	public Double getTotalVolume() {
		return totalVolume;
	}

	public void setTotalVolume(Double totalVolume) {
		this.totalVolume = totalVolume;
	}

	public String getIsPicPackage() {
		return isPicPackage;
	}

	public void setIsPicPackage(String isPicPackage) {
		this.isPicPackage = isPicPackage;
	}

	public String getModifyType() {
		return modifyType;
	}

	public void setModifyType(String modifyType) {
		this.modifyType = modifyType;
	}

	public String getIsExisted() {
		return isExisted;
	}

	public void setIsExisted(String isExisted) {
		this.isExisted = isExisted;
	}



	
}
