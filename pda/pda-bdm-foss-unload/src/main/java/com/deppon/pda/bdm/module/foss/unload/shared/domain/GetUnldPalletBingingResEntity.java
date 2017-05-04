package com.deppon.pda.bdm.module.foss.unload.shared.domain;
/**
 * 
 * @description 返回托盘绑定任务实体
 * @version 1.0
 * @author wenwuneng 
 * @update 2013-8-12 下午4:47:55
 */
/**  
 *@author 201638
 *@date   2014-9-22
 */
/**  
 *@author 201638
 *@date   2014-9-22
 */
public class GetUnldPalletBingingResEntity {
	/**
	 * 叉车票数
	 */
	private int cardNo;
	
	/**
	 * 运单号
	 */
	private String wblCode;
	/**
	 * 绑定件数
	 */
	private int bindingNum;
	/**
	 * 开单件数
	 */
	private int pieces;
	/**
	 * 托盘绑定唯一编号
	 */
	private String bindingNo;
	/**
	 * 目的站
	 */
	private String deptDestName;
	/**
	 * 运输性质
	 */
	private String transType;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 库区名称
	 */
	private String stock;
	
	/**库区编码 */
	private String goodAreaCode;

	/**	行政区域 */
	private String adminiRegions;
	
	public String getWblCode() {
		return wblCode;
	}
	public void setWblCode(String wblCode) {
		this.wblCode = wblCode;
	}
	public int getBindingNum() {
		return bindingNum;
	}
	public void setBindingNum(int bindingNum) {
		this.bindingNum = bindingNum;
	}
	public String getBindingNo() {
		return bindingNo;
	}
	public void setBindingNo(String bindingNo) {
		this.bindingNo = bindingNo;
	}
	public String getDeptDestName() {
		return deptDestName;
	}
	public void setDeptDestName(String deptDestName) {
		this.deptDestName = deptDestName;
	}
	public int getPieces() {
		return pieces;
	}
	public void setPieces(int pieces) {
		this.pieces = pieces;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getCardNo() {
		return cardNo;
	}
	public void setCardNo(int cardNo) {
		this.cardNo = cardNo;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	/**
	 * @return the goodAreaCode
	 */
	public String getGoodAreaCode() {
		return goodAreaCode;
	}
	/**
	 * @param goodAreaCode the goodAreaCode to set
	 */
	public void setGoodAreaCode(String goodAreaCode) {
		this.goodAreaCode = goodAreaCode;
	}
	/**
	 * @return the adminiRegions
	 */
	public String getAdminiRegions() {
		return adminiRegions;
	}
	/**
	 * @param adminiRegions the adminiRegions to set
	 */
	public void setAdminiRegions(String adminiRegions) {
		this.adminiRegions = adminiRegions;
	}
	
	
}
