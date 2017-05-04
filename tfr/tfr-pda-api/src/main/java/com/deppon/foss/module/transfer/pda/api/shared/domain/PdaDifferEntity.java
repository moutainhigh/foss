/**
 * 
 */
package com.deppon.foss.module.transfer.pda.api.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author niuly
 * @function 清仓差异
 */
public class PdaDifferEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	//差异报告编号
	private String reportCode;
	//少货件数
	private int lessQty;
	//货区
	private String goodsAreaName;
	//差异报告生成时间
	private Date createTime;
	/**
	 * @return the reportCode
	 */
	public String getReportCode() {
		return reportCode;
	}
	/**
	 * @param reportCode the reportCode to set
	 */
	public void setReportCode(String reportCode) {
		this.reportCode = reportCode;
	}
	/**
	 * @return the lessQty
	 */
	public int getLessQty() {
		return lessQty;
	}
	/**
	 * @param lessQty the lessQty to set
	 */
	public void setLessQty(int lessQty) {
		this.lessQty = lessQty;
	}
	/**
	 * @return the goodsAreaName
	 */
	public String getGoodsAreaName() {
		return goodsAreaName;
	}
	/**
	 * @param goodsAreaName the goodsAreaName to set
	 */
	public void setGoodsAreaName(String goodsAreaName) {
		this.goodsAreaName = goodsAreaName;
	}
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
