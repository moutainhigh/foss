package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.DelayDeductRecordEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DelayDeductRecordQueryDto;
/**
 * 
 * 延时扣款记录申请审核VO
 * @author 308861 
 * @date 2016-10-29 上午10:11:48
 * @since
 * @version
 */
public class DelayDeductRecordVo implements Serializable{
	/** 
	* 序列化
	*/ 
	private static final long serialVersionUID = 6240219859220916527L;

	/**
	 * 获取当前登录人部门名称
	 */
	private String userDeptOrgName;
	/**
	 * 获取当前登录人部门编码
	 */
	private String userDeptOrgCode;
	/**
	 * 当前登录人工号
	 */
	private String empCode;
	/**
	 * 当前登录人工号
	 */
	private String empName; 
	/**
	 * 最大申请扣款时间
	 */
	private Date finalEndTime;
	/**
	 * 最大申请扣款时间
	 */
	private String isLeagueSaledept;
	
	/**
	 * 延时扣款记录实体
	 */
	private DelayDeductRecordEntity delayDeductRecordentity;
	/**
	 * 延时扣款记录列表
	 */
	private List<DelayDeductRecordEntity> delayDeductRecordList;

	/**
	 * 延时扣款申请审核查询dto
	 */
	private DelayDeductRecordQueryDto queryDto;

	
	
	public Date getFinalEndTime() {
		return finalEndTime;
	}

	public void setFinalEndTime(Date finalEndTime) {
		this.finalEndTime = finalEndTime;
	}

	public String getUserDeptOrgName() {
		return userDeptOrgName;
	}

	public void setUserDeptOrgName(String userDeptOrgName) {
		this.userDeptOrgName = userDeptOrgName;
	}

	public String getUserDeptOrgCode() {
		return userDeptOrgCode;
	}

	public void setUserDeptOrgCode(String userDeptOrgCode) {
		this.userDeptOrgCode = userDeptOrgCode;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	

	public DelayDeductRecordEntity getDelayDeductRecordentity() {
		return delayDeductRecordentity;
	}

	public void setDelayDeductRecordentity(
			DelayDeductRecordEntity delayDeductRecordentity) {
		this.delayDeductRecordentity = delayDeductRecordentity;
	}

	public List<DelayDeductRecordEntity> getDelayDeductRecordList() {
		return delayDeductRecordList;
	}

	public void setDelayDeductRecordList(
			List<DelayDeductRecordEntity> delayDeductRecordList) {
		this.delayDeductRecordList = delayDeductRecordList;
	}

	public DelayDeductRecordQueryDto getQueryDto() {
		return queryDto;
	}

	public void setQueryDto(DelayDeductRecordQueryDto queryDto) {
		this.queryDto = queryDto;
	}

	public String getIsLeagueSaledept() {
		return isLeagueSaledept;
	}

	public void setIsLeagueSaledept(String isLeagueSaledept) {
		this.isLeagueSaledept = isLeagueSaledept;
	}
	
}
