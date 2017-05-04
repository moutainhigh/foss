package com.deppon.pda.bdm.module.foss.upgrade.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
  * @ClassName ExpressEmpDistrictEntity 
  * @Description TODO 快递员负责行政区域
  * @author mt 
  * @date 2013-8-16 上午9:36:19
 */
public class ExpressEmpDistrictEntity extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6750680817110998920L;
	/**
	 * 行政区域
	 */
	private String districtCode;
	/**
	 * 所属快递员
	 */
	private String empCode;
	/**
	 * 操作标识
	 */
	private String operFlag;
	/**
	 * 数据版本
	 */
	private String versionNo;
	/**
	 * 备注
	 */
	private String description;
	
	public String getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getOperFlag() {
		return operFlag;
	}
	public void setOperFlag(String operFlag) {
		this.operFlag = operFlag;
	}
	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
