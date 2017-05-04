package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;

public class RewardOrPunishAgreementEntity extends BaseEntity {
	private static final long serialVersionUID = -2224553936459353036L;
	/**id*/
	private String id;
	/**奖罚类型 */
	private String rewardOrPunishType;
	/**时间段 */
	private String timeLimit;
	/**协议金额*/
	private BigDecimal agreementMoney;
	/**奖罚金额上限*/
	private BigDecimal rewardOrPunishMaxMoney ;
	/**配载单号*/
	private String vehicleAssemBillNo;
	/**创建人*/
	private String createUserName;
	/**创建人工号*/
	private String creatorUserCode;
	/**创建时间*/
	private Date createTime;
	/**修改人*/
	private String modifyUserName;
	/**修改人工号*/
	private String modifyUserCode;
	/**修改时间*/
	private Date modifyTime;
	/**时间上限*/
	private int timeUp ;
	/**时间下限*/
	private int timeDown ;
	
	
	public int getTimeUp() {
		return timeUp;
	}
	public void setTimeUp(int timeUp) {
		this.timeUp = timeUp;
	}
	public int getTimeDown() {
		return timeDown;
	}
	public void setTimeDown(int timeDown) {
		this.timeDown = timeDown;
	}
	public String getRewardOrPunishType() {
		return rewardOrPunishType;
	}
	public void setRewardOrPunishType(String rewardOrPunishType) {
		this.rewardOrPunishType = rewardOrPunishType;
	}
	public String getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}
	public BigDecimal getAgreementMoney() {
		return agreementMoney;
	}
	public void setAgreementMoney(BigDecimal agreementMoney) {
		this.agreementMoney = agreementMoney;
	}
	public BigDecimal getRewardOrPunishMaxMoney() {
		return rewardOrPunishMaxMoney;
	}
	public void setRewardOrPunishMaxMoney(BigDecimal rewardOrPunishMaxMoney) {
		this.rewardOrPunishMaxMoney = rewardOrPunishMaxMoney;
	}
	public String getVehicleAssemBillNo() {
		return vehicleAssemBillNo;
	}
	public void setVehicleAssemBillNo(String vehicleAssemBillNo) {
		this.vehicleAssemBillNo = vehicleAssemBillNo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getCreatorUserCode() {
		return creatorUserCode;
	}
	public void setCreatorUserCode(String creatorUserCode) {
		this.creatorUserCode = creatorUserCode;
	}
	@DateFormat
	public Date getCreateTime() {
		return createTime;
	}
	@DateFormat
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getModifyUserName() {
		return modifyUserName;
	}
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}
	public String getModifyUserCode() {
		return modifyUserCode;
	}
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}
	@DateFormat
	public Date getModifyTime() {
		return modifyTime;
	}
	@DateFormat
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	@Override
	public String toString() {
		return "RewardOrPunishAgreementEntity [id=" + id + ", rewardOrPunishType=" + rewardOrPunishType + ", timeLimit="
				+ timeLimit + ", agreementMoney=" + agreementMoney + ", rewardOrPunishMaxMoney="
				+ rewardOrPunishMaxMoney + ", vehicleAssemBillNo=" + vehicleAssemBillNo + ", createUserName="
				+ createUserName + ", creatorUserCode=" + creatorUserCode + ", createTime=" + createTime
				+ ", modifyUserName=" + modifyUserName + ", modifyUserCode=" + modifyUserCode + ", modifyTime="
				+ modifyTime + ", timeUp=" + timeUp + ", timeDown=" + timeDown + ", getCreateUser()=" + getCreateUser()
				+ ", getModifyUser()=" + getModifyUser() + ", getCreateDate()=" + getCreateDate() + ", getModifyDate()="
				+ getModifyDate() + ", hashCode()=" + hashCode() + ", getClass()=" + getClass() + ", toString()="
				+ super.toString() + "]";
	}
	
	
}
