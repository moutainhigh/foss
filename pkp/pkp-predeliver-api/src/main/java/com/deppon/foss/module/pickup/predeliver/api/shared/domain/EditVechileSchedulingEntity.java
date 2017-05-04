package com.deppon.foss.module.pickup.predeliver.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/** 
 * @ClassName: EditVechileSchedulingEntity 
 * @Description: 车辆排班发生更改表实体类 
 * @author 237982-foss-fangwenjun 
 * @date 2015-5-18 上午9:14:50 
 *  
 */
public class EditVechileSchedulingEntity extends BaseEntity {

	/**
	 * 类序列号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 小区编码
	 */
	private String smallZoneCode;
	
	/**
	 * 车牌号
	 */
	private String vechileNo;
	
	/**
	 * 修改日期
	 */
	private Date editDate;
	
	/**
	 * 排班状态
	 */
	private String schedulingStatus;
	
	/**
	 * 是否使用过
	 */
	private String isUsed;

	/**
	 * 获取smallZoneCode
	 * @return the smallZoneCode
	 */
	public String getSmallZoneCode() {
		return smallZoneCode;
	}

	/**
	 * 获取vechileNo
	 * @return the vechileNo
	 */
	public String getVechileNo() {
		return vechileNo;
	}

	/**
	 * 获取editDate
	 * @return the editDate
	 */
	public Date getEditDate() {
		return editDate;
	}
	
	/**
	 * 获取schedulingStatus
	 * @return the schedulingStatus
	 */
	public String getSchedulingStatus() {
		return schedulingStatus;
	}

	/**
	 * 获取isUsed
	 * @return the isUsed
	 */
	public String getIsUsed() {
		return isUsed;
	}

	/**
	 * 设置smallZoneCode
	 * @param smallZoneCode 要设置的smallZoneCode
	 */
	public void setSmallZoneCode(String smallZoneCode) {
		this.smallZoneCode = smallZoneCode;
	}

	/**
	 * 设置vechileNo
	 * @param vechileNo 要设置的vechileNo
	 */
	public void setVechileNo(String vechileNo) {
		this.vechileNo = vechileNo;
	}

	/**
	 * 设置editDate
	 * @param editDate 要设置的editDate
	 */
	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}
	
	/**
	 * 设置schedulingStatus
	 * @param schedulingStatus 要设置的schedulingStatus
	 */
	public void setSchedulingStatus(String schedulingStatus) {
		this.schedulingStatus = schedulingStatus;
	}

	/**
	 * 设置isUsed
	 * @param isUsed 要设置的isUsed
	 */
	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}


}
