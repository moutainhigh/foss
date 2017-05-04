package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 部门收支平衡表实体
 * 
 * @author dp-zengbinwen
 * @date 2012-10-10 下午1:47:47
 * @since 2012-10-10
 * @version 1.0
 */
public class CreditOrgEntity extends BaseEntity {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 2936645324133354741L;

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 部门编码
	 */
	private String orgCode;

	/**
	 * 部门名称
	 */
	private String orgName;

	/**
	 * 已用额度 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal usedAmount;

	/**
	 * 是否超期
	 */
	private String isOverdue;

	/**
	 * 业务日期
	 */
	private Date businessDate;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	/**
	 * 修改人名称
	 */
	private String modifyUserName;

	/**
	 * 修改人编码
	 */
	private String modifyUserCode;

	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * 备注信息
	 */
	private String notes;

	/**
	 * 最大账期
	 */
	private int maxAccountDays;

	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @return orgName
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @param orgName
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * @return usedAmount
	 */
	public BigDecimal getUsedAmount() {
		return usedAmount;
	}

	/**
	 * @param usedAmount
	 */
	public void setUsedAmount(BigDecimal usedAmount) {
		this.usedAmount = usedAmount;
	}

	/**
	 * @return isOverdue
	 */
	public String getIsOverdue() {
		return isOverdue;
	}

	/**
	 * @param isOverdue
	 */
	public void setIsOverdue(String isOverdue) {
		this.isOverdue = isOverdue;
	}

	/**
	 * @return businessDate
	 */
	public Date getBusinessDate() {
		return businessDate;
	}

	/**
	 * @param businessDate
	 */
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	/**
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return modifyUserName
	 */
	public String getModifyUserName() {
		return modifyUserName;
	}

	/**
	 * @param modifyUserName
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	/**
	 * @return modifyUserCode
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}

	/**
	 * @param modifyUserCode
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

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
	 * @return notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return maxAccountDays
	 */
	public int getMaxAccountDays() {
		return maxAccountDays;
	}

	/**
	 * @param maxAccountDays
	 */
	public void setMaxAccountDays(int maxAccountDays) {
		this.maxAccountDays = maxAccountDays;
	}

}
