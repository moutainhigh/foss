package com.deppon.pda.bdm.module.foss.upgrade.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 
 * TODO(时效区域与部门对应关系实体)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:wenwuneng,date:2013-11-05 下午4:22:31,content:TODO </p>
 * @author wenwuneng
 * @date 2013-11-05 下午4:22:31
 * @since
 * @version
 */
public class EffectiveRegionOrgEntity extends BaseEntity{
	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 时效区域ID
	 */
	private String effectiveRegionId;
	/**
	 * 时效区域CODE
	 */
	private String effectiveRegionCode;
	/**
	 * 包含组织ID
	 */
	private String includeOrgId;
	/**
	 * 包含组织Code
	 */
	private String includeOrgCode;
	/**
	 * 生效日期
	 */
	private Date beginTime;
	/**
	 * 结束日期
	 */
	private Date endTime;
	/**
	 * 操作标示
	 */
	private String operFlag;
	public String getEffectiveRegionId() {
		return effectiveRegionId;
	}
	public void setEffectiveRegionId(String effectiveRegionId) {
		this.effectiveRegionId = effectiveRegionId;
	}
	public String getEffectiveRegionCode() {
		return effectiveRegionCode;
	}
	public void setEffectiveRegionCode(String effectiveRegionCode) {
		this.effectiveRegionCode = effectiveRegionCode;
	}
	public String getIncludeOrgId() {
		return includeOrgId;
	}
	public void setIncludeOrgId(String includeOrgId) {
		this.includeOrgId = includeOrgId;
	}
	public String getIncludeOrgCode() {
		return includeOrgCode;
	}
	public void setIncludeOrgCode(String includeOrgCode) {
		this.includeOrgCode = includeOrgCode;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getOperFlag() {
		return operFlag;
	}
	public void setOperFlag(String operFlag) {
		this.operFlag = operFlag;
	}
	
	
	
	
}
