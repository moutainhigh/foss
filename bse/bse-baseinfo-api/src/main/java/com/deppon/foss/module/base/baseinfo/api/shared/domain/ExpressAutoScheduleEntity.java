package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 快递快递自动调度城市管理实体
 * @author yangkang
 *
 */
public class ExpressAutoScheduleEntity extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	/**
	 * 城市名称
	 */
	private String cityName;
	/**
	 * 城市编码
	 */
	private String cityCode;
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private  String endTime;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 修改人工号
	 */
	private String modifyUserPsncode;
	/**
	 * 是否启用
	 */
	private String active;
	
	/**
     * 版本号.
     */
    private Long versionNo;
    
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}


	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getModifyUserPsncode() {
		return modifyUserPsncode;
	}

	public void setModifyUserPsncode(String modifyUserPsncode) {
		this.modifyUserPsncode = modifyUserPsncode;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public Long getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}
    
}
