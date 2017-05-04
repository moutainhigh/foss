package com.deppon.pda.bdm.module.foss.upgrade.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
  * @ClassName ExpressCityEntity 
  * @Description TODO 落地配、试点城市
  * @author mt 
  * @date 2013-8-16 上午9:41:17
 */
public class ExpressCityEntity extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6019339846207547299L;
	/**
	 * 城市CODE
	 */
	private String districtCode;
	/**
	 * 城市类别定义在数据字典中：包括，落地配和试点城市两类
	 */
	private String type;
	/**
	 * 数据版本
	 */
	private String versionNo;
	/**
	 * 操作标识
	 */
	private String operFlag;
	public String getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	public String getOperFlag() {
		return operFlag;
	}
	public void setOperFlag(String operFlag) {
		this.operFlag = operFlag;
	}
}
