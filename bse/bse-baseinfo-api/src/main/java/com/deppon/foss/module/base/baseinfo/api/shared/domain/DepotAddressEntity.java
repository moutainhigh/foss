package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;
import java.util.List;

import com.deppon.foss.base.util.define.DepotState;
import com.deppon.foss.base.util.define.DepotType;
import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.shared.util.string.StringUtil;

/**
 * 
* @ClassName: DepotAddressEntity 
* @Description: 进仓地址管理实体
* @author 310854-liuzhenhua
* @date 2016-9-1 下午2:40:16 
*
 */
public class DepotAddressEntity extends BaseEntity {
	private static final long serialVersionUID = -5322904563817745783L;

	/**
	 * 仓库名称
	 */
	private String depotName;

	/**
	 * 仓库管理状态
	 */
	private String depotState;
	
	private String depotStateStr;

	/**
	 * 仓库类型
	 */
	private String depotType;
	
	private String depotTypeStr;

	/**
	 * 仓库备注
	 */
	private String depotRemark;

	/**
	 * 省
	 */
	private String provCode;
	
	private String provCodeStr;

	/**
	 * 市
	 */
	private String cityCode;
	
	private String cityCodeStr;

	/**
	 * 区/县
	 */
	private String countyCode;
	
	private String countyCodeStr;

	/**
	 * 街道地址
	 */
	private String address;

	/**
	 * 管理部门code
	 */
	private String orgCode;
	
	private String orgName;
	
	private Date  confirmDateTime;

	/**
	 * 经度
	 */
	private String longitude;

	/**
	 * 纬度
	 */
	private String latitude;
	
	private String lonLat;
	
	private List<String> batchIds;

	/**
	 * 退回原因
	 */
	private String repulseReason;
	
	private String active;

	public String getDepotName() {
		return depotName;
	}

	public void setDepotName(String depotName) {
		this.depotName = depotName;
	}

	public String getDepotState() {
		return depotState;
	}

	public void setDepotState(String depotState) {
		this.depotState = depotState;
		if(!StringUtil.isEmpty(depotState)){
			depotStateStr = DepotState.getName(Integer.valueOf(depotState));
		}
	}

	public String getDepotType() {
		return depotType;
	}

	public void setDepotType(String depotType) {
		this.depotType = depotType;
		if(!StringUtil.isEmpty(depotType)){
			depotTypeStr = DepotType.getName(Integer.valueOf(depotType));
		}
	}

	public String getDepotRemark() {
		return depotRemark;
	}

	public void setDepotRemark(String depotRemark) {
		this.depotRemark = depotRemark;
	}

	public String getProvCode() {
		return provCode;
	}

	public void setProvCode(String provCode) {
		this.provCode = provCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getRepulseReason() {
		if(!depotState.equals("3")){
			repulseReason = "";
		}
		return repulseReason;
	}

	public void setRepulseReason(String repulseReason) {
		this.repulseReason = repulseReason;
	}

	public String getDepotStateStr() {
		return depotStateStr;
	}

	public void setDepotStateStr(String depotStateStr) {
		this.depotStateStr = depotStateStr;
	}

	public String getDepotTypeStr() {
		return depotTypeStr;
	}

	public void setDepotTypeStr(String depotTypeStr) {
		this.depotTypeStr = depotTypeStr;
	}

	public String getProvCodeStr() {
		return provCodeStr;
	}

	public void setProvCodeStr(String provCodeStr) {
		this.provCodeStr = provCodeStr;
	}

	public String getCityCodeStr() {
		return cityCodeStr;
	}

	public void setCityCodeStr(String cityCodeStr) {
		this.cityCodeStr = cityCodeStr;
	}

	public String getCountyCodeStr() {
		return countyCodeStr;
	}

	public void setCountyCodeStr(String countyCodeStr) {
		this.countyCodeStr = countyCodeStr;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Date getConfirmDateTime() {
		return confirmDateTime;
	}

	public void setConfirmDateTime(Date confirmDateTime) {
		this.confirmDateTime = confirmDateTime;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getLonLat() {
		return lonLat;
	}

	public void setLonLat(String lonLat) {
		this.lonLat = lonLat;
	}

	public List<String> getBatchIds() {
		return batchIds;
	}

	public void setBatchIds(List<String> batchIds) {
		this.batchIds = batchIds;
	}

}
