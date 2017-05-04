package com.deppon.foss.module.pickup.pricing.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 整车线路价格方案实体
 * @author hehaisu
 * @date 2015-1-20 下午2:52:31
 */
public class CarloadLinePricePlanEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1336693213305152423L;
	/**
	 * 始发城市code
	 */
	private String departureCityCode;
	/**
	 * 始发城市名称
	 */
	private String departureCityName;
	 /**
	  * 到达城市code
	  */
	private String arrivalCityCode;
	/**
	 * 到达城壕名称
	 */
	private String arrivalCityName;
	/**
	 * 是否有效
	 */
	private String active;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建人
	 */
	private String createUserName;
	/**
	 * 修改人
	 */
	private String modifyUserName;
	
	
	public String getModifyUserName() {
		return modifyUserName;
	}
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}
	public String getDepartureCityCode() {
		return departureCityCode;
	}
	public void setDepartureCityCode(String departureCityCode) {
		this.departureCityCode = departureCityCode;
	}
	public String getDepartureCityName() {
		return departureCityName;
	}
	public void setDepartureCityName(String departureCityName) {
		this.departureCityName = departureCityName;
	}
	public String getArrivalCityCode() {
		return arrivalCityCode;
	}
	public void setArrivalCityCode(String arrivalCityCode) {
		this.arrivalCityCode = arrivalCityCode;
	}
	public String getArrivalCityName() {
		return arrivalCityName;
	}
	public void setArrivalCityName(String arrivalCityName) {
		this.arrivalCityName = arrivalCityName;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
}
