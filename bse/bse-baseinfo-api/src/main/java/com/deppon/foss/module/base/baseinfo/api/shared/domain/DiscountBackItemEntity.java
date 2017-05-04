package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 事后折信息详情类
 * @author 132599 ShenWeiHua 2015-002-06
 *
 */
public class DiscountBackItemEntity extends BaseEntity{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * crm系统的信息id
	 */
	private BigDecimal crmId; 
	
	/**
	 * 对应事后折基础资料的信息crm_id
	 */
	private BigDecimal discountCrmId; 
	/**
	 * 等级 即折扣的一个顺序
	 */
	private String degree;
	/**
	 * 最小金额
	 */
	private BigDecimal minMoney;
	/**
	 * 最大金额
	 */
	private BigDecimal maxMoney;
	/**
	 * 运费折扣
	 */
	private BigDecimal rate;
	
	/**
	 * crmID
	 * @return
	 */
	public BigDecimal getCrmId() {
		return crmId;
	}
	/**
	 * crmID
	 * @param crmId
	 */
	public void setCrmId(BigDecimal crmId) {
		this.crmId = crmId;
	}
	/**
	 * 对应事后折基础资料的信息crm_id
	 * @return
	 */
	public BigDecimal getDiscountCrmId() {
		return discountCrmId;
	}
	/**
	 * 对应事后折基础资料的信息crm_id
	 * @param discountCrmId
	 */
	public void setDiscountCrmId(BigDecimal discountCrmId) {
		this.discountCrmId = discountCrmId;
	}
	/**
	 * 获取等级
	 * @return
	 */
	public String getDegree() {
		return degree;
	}
	/**
	 * 设置等级
	 * @param degree
	 */
	public void setDegree(String degree) {
		this.degree = degree;
	}
	/**
	 * 获取最小金额
	 * @return
	 */
	public BigDecimal getMinMoney() {
		return minMoney;
	}
	/**
	 * 设置最小金额
	 * @param minMoney
	 */
	public void setMinMoney(BigDecimal minMoney) {
		this.minMoney = minMoney;
	}
	/**
	 * 获取最大金额
	 * @return
	 */
	public BigDecimal getMaxMoney() {
		return maxMoney;
	}
	/**
	 * 设置最大金额
	 * @param maxMoney
	 */
	public void setMaxMoney(BigDecimal maxMoney) {
		this.maxMoney = maxMoney;
	}
	/**
	 * 获取折扣信息
	 * @return
	 */
	public BigDecimal getRate() {
		return rate;
	}
	/**
	 * 设置折扣信息
	 * @param rate
	 */
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	
}
