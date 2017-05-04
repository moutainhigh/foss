package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 增值服务的响应实体
 * @author FOSS-273279 liding
 * 2016-4-20 上午10:13:02
 */
public class ESCValueAddDto implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
		
		
	/**
	 * 费用code
	 */
	private String  priceEntityCode;
	/**
	 * 费用名称
	 */
	private String priceEntityName;
	/**
	 * 归集类别 code
	 */
	private String belongToPriceEntityCode;
	/**
	 * 归集类型名称
	 */
	private String belongToPriceEntityName;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 金额
	 */
	private BigDecimal fee;
	/**
	 * 上限
	 */
	private BigDecimal maxFee;
	/**
	 * 下限
	 */
	private BigDecimal minFee;
	/**
	 * 是否可修改
	 */
	private String canmodify;
	/**
	 * 是否可删除
	 */
	private String candelete;
	
	private String subType;
	/**
	 * 营销活动结束时间
	 */
	private Date activeEndTime;
	/**
	 * 营销活动折扣关联的CRM_ID
	 */
	private BigDecimal optionsCrmId;
	
	/**
	 * 营销活动CODE
	 */
	private String activeCode;
	/**
	 * 营销活动名称
	 */
	private String activeName;
	/**
	 * 营销活动开始时间
	 */
	private Date activeStartTime;
	
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	public String getActiveCode() {
		return activeCode;
	}
	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}
	public String getActiveName() {
		return activeName;
	}
	public void setActiveName(String activeName) {
		this.activeName = activeName;
	}
	public Date getActiveStartTime() {
		return activeStartTime;
	}
	public void setActiveStartTime(Date activeStartTime) {
		this.activeStartTime = activeStartTime;
	}
	public Date getActiveEndTime() {
		return activeEndTime;
	}
	public void setActiveEndTime(Date activeEndTime) {
		this.activeEndTime = activeEndTime;
	}
	public BigDecimal getOptionsCrmId() {
		return optionsCrmId;
	}
	public void setOptionsCrmId(BigDecimal optionsCrmId) {
		this.optionsCrmId = optionsCrmId;
	}
	
	
	public String getPriceEntityCode() {
		return priceEntityCode;
	}
	public void setPriceEntityCode(String priceEntityCode) {
		this.priceEntityCode = priceEntityCode;
	}
	public String getPriceEntityName() {
		return priceEntityName;
	}
	public void setPriceEntityName(String priceEntityName) {
		this.priceEntityName = priceEntityName;
	}
	public String getBelongToPriceEntityCode() {
		return belongToPriceEntityCode;
	}
	public void setBelongToPriceEntityCode(String belongToPriceEntityCode) {
		this.belongToPriceEntityCode = belongToPriceEntityCode;
	}
	public String getBelongToPriceEntityName() {
		return belongToPriceEntityName;
	}
	public void setBelongToPriceEntityName(String belongToPriceEntityName) {
		this.belongToPriceEntityName = belongToPriceEntityName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getFee() {
		return fee;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	public BigDecimal getMaxFee() {
		return maxFee;
	}
	public void setMaxFee(BigDecimal maxFee) {
		this.maxFee = maxFee;
	}
	public BigDecimal getMinFee() {
		return minFee;
	}
	public void setMinFee(BigDecimal minFee) {
		this.minFee = minFee;
	}
	public String getCanmodify() {
		return canmodify;
	}
	public void setCanmodify(String canmodify) {
		this.canmodify = canmodify;
	}
	public String getCandelete() {
		return candelete;
	}
	public void setCandelete(String candelete) {
		this.candelete = candelete;
	}
	
}
