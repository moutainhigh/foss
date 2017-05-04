package com.deppon.foss.module.pickup.common.client.vo;

public class PtpWaybillInfoOrgVo {

	//提货方式
	private  String receiveMethod ;
	
	//目的站
	private  String targetOrgCode ;
	
	//包装信息
	//纸
	private  Integer paper ;
	/**
	 * 木
	 */
	private  Integer wood ;

	/**
	 * 纤
	 */
	private  Integer fibre ;

	/**
	 * 托
	 */
	private  Integer salver;

	/**
	 * 膜
	 */
	private  Integer membrane;

	/**
	 * 其他
	 */
	private  Integer otherPackage;
	
	/**
	 * 货物总量
	 */
	private  Integer goodsQtyTotal ;

	public String getReceiveMethod() {
		return receiveMethod;
	}

	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	public String getTargetOrgCode() {
		return targetOrgCode;
	}

	public void setTargetOrgCode(String targetOrgCode) {
		this.targetOrgCode = targetOrgCode;
	}

	public Integer getPaper() {
		return paper;
	}

	public void setPaper(Integer paper) {
		this.paper = paper;
	}

	public Integer getWood() {
		return wood;
	}

	public void setWood(Integer wood) {
		this.wood = wood;
	}

	public Integer getFibre() {
		return fibre;
	}

	public void setFibre(Integer fibre) {
		this.fibre = fibre;
	}

	public Integer getSalver() {
		return salver;
	}

	public void setSalver(Integer salver) {
		this.salver = salver;
	}

	public Integer getMembrane() {
		return membrane;
	}

	public void setMembrane(Integer membrane) {
		this.membrane = membrane;
	}

	public Integer getOtherPackage() {
		return otherPackage;
	}

	public void setOtherPackage(Integer otherPackage) {
		this.otherPackage = otherPackage;
	}

	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}
	
}
