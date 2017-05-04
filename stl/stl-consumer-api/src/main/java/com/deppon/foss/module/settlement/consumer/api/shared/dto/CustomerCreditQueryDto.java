package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.util.Date;

/**
 * 客户额度查询基本查询条件
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-11-16 下午5:53:20
 */
public class CustomerCreditQueryDto {

	/**
	 * 有效性
	 */
	private String active;

	/**
	 * 创建时间：开始
	 */
	private Date startCreateTime;

	/**
	 * 创建时间：结束
	 */
	private Date endCreateTime;

	/**
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return startCreateTime
	 */
	public Date getStartCreateTime() {
		return startCreateTime;
	}

	/**
	 * @param startCreateTime
	 */
	public void setStartCreateTime(Date startCreateTime) {
		this.startCreateTime = startCreateTime;
	}

	/**
	 * @return endCreateTime
	 */
	public Date getEndCreateTime() {
		return endCreateTime;
	}

	/**
	 * @param endCreateTime
	 */
	public void setEndCreateTime(Date endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

}
