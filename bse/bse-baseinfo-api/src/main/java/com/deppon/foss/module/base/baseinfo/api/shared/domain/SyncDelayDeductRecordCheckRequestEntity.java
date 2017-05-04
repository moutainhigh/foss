package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * 同步延时扣款审核请求参数
 * @author 308861 
 * @date 2016-11-1 上午10:26:21
 * @since
 * @version
 */
public class SyncDelayDeductRecordCheckRequestEntity extends BaseEntity {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1145449489764L;
	/**
	 * 审核状态1：待审核；2：已审核；3：已拒绝；4：已失效
	 */
	private String checkStatus;
	/**
	 * 审核人名称/修改人名称
	 */
	private String checkPersonName;
	/**
	 * 审核人编码/修改人编码
	 */
	private String checkPersonCode;
	/**
	 * 审核时间/修改时间
	 */
	private Date checkTime;
	/**
	 * 审核意见
	 */
	private String checkOpinion;
	/**
	 * 审核部门名称
	 */
	private String checkOrgName;
	/**
	 * 审核部门编码
	 */
	private String checkOrgCode;
	public String getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	public String getCheckPersonName() {
		return checkPersonName;
	}
	public void setCheckPersonName(String checkPersonName) {
		this.checkPersonName = checkPersonName;
	}
	public String getCheckPersonCode() {
		return checkPersonCode;
	}
	public void setCheckPersonCode(String checkPersonCode) {
		this.checkPersonCode = checkPersonCode;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public String getCheckOpinion() {
		return checkOpinion;
	}
	public void setCheckOpinion(String checkOpinion) {
		this.checkOpinion = checkOpinion;
	}
	public String getCheckOrgName() {
		return checkOrgName;
	}
	public void setCheckOrgName(String checkOrgName) {
		this.checkOrgName = checkOrgName;
	}
	public String getCheckOrgCode() {
		return checkOrgCode;
	}
	public void setCheckOrgCode(String checkOrgCode) {
		this.checkOrgCode = checkOrgCode;
	}
}
