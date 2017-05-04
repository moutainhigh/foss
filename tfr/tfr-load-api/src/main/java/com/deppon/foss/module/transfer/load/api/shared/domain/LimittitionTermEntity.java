package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.module.transfer.load.api.shared.domain.RewardOrPunishAgreementEntity;
import com.deppon.foss.util.UUIDUtils;

public class LimittitionTermEntity {

	private Long id;
	// 条款号
	private String itemNo;
	// 条款类型
	private String itemType;
	// 生效时间
	private Date itemActiveTime;
	// 无效时间
	private Date itemInactiveTime;
	// 条款金额
	private BigDecimal itemAmount;
	// 交接单号
	private String handoverBillNo;

	/**
	 * 创建人工号
	 */
	private String createNo;
	/**
	 * 更新人工号
	 */
	private String updateNo;
	/**
	 * 创建人名称
	 */
	private String createName;
	/**
	 * 更新人名称
	 */
	private String updateName;
	/**
	 * 创建组织编号
	 */
	private String createOrgCode;
	/**
	 * 更新组织编号
	 */
	private String updateOrgCode;
	/**
	 * 创建组织名称
	 */
	private String createOrgName;
	/**
	 * 更新组织名称
	 */
	private String updateOrgName;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public Date getItemActiveTime() {
		return itemActiveTime;
	}

	public void setItemActiveTime(Date itemActiveTime) {
		this.itemActiveTime = itemActiveTime;
	}

	public Date getItemInactiveTime() {
		return itemInactiveTime;
	}

	public void setItemInactiveTime(Date itemInactiveTime) {
		this.itemInactiveTime = itemInactiveTime;
	}

	public BigDecimal getItemAmount() {
		return itemAmount;
	}

	public void setItemAmount(BigDecimal itemAmount) {
		this.itemAmount = itemAmount;
	}

	public String getHandoverBillNo() {
		return handoverBillNo;
	}

	public void setHandoverBillNo(String handoverBillNo) {
		this.handoverBillNo = handoverBillNo;
	}

	public String getCreateNo() {
		return createNo;
	}

	public void setCreateNo(String createNo) {
		this.createNo = createNo;
	}

	public String getUpdateNo() {
		return updateNo;
	}

	public void setUpdateNo(String updateNo) {
		this.updateNo = updateNo;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public String getCreateOrgCode() {
		return createOrgCode;
	}

	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	public String getUpdateOrgCode() {
		return updateOrgCode;
	}

	public void setUpdateOrgCode(String updateOrgCode) {
		this.updateOrgCode = updateOrgCode;
	}

	public String getCreateOrgName() {
		return createOrgName;
	}

	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	public String getUpdateOrgName() {
		return updateOrgName;
	}

	public void setUpdateOrgName(String updateOrgName) {
		this.updateOrgName = updateOrgName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public RewardOrPunishAgreementEntity toRewardOrPunishAgreementEntity() {
		RewardOrPunishAgreementEntity entity = new RewardOrPunishAgreementEntity();
		entity.setAgreementMoney(itemAmount);
//		entity.setCreateDate(createTime);
		entity.setCreateTime(createTime);
		entity.setCreateUserName(createName);
		entity.setCreatorUserCode(createNo);
		entity.setId(UUIDUtils.getUUID());
		entity.setModifyTime(updateTime);
		entity.setModifyUserCode(updateNo);
		entity.setModifyUserName(updateName);
		entity.setRewardOrPunishMaxMoney(itemAmount);
		entity.setRewardOrPunishType(itemType);
//		entity.setTimeDown(timeDown);
		entity.setTimeLimit(itemNo);
//		entity.setTimeUp(timeUp);
		entity.setVehicleAssemBillNo(handoverBillNo);
		
		return entity;
	}

}
