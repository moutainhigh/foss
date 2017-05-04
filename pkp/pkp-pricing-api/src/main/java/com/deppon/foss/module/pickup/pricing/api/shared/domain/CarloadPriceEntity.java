package com.deppon.foss.module.pickup.pricing.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class CarloadPriceEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1336693213305152423L;
	
	 private String id ; 			//Id
	 private String scenarioName;   //方案名称
	 private String active;           //是否激活（Y,N）
	 private Long version;          //版本号
	 private String currentUsedVersion; //当前版本（是，否）
	 private Date createTime;       //创建时间
	 private String createUser;       //创建人
	 private Date beginTime ;       //生效日期
	 private Date endTime ;         //截止日期
	 private Date modifyTime;       	//修改日期
	 private String modifyUser;       	//修改人
	 private String remark ;           	//备注
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getScenarioName() {
		return scenarioName;
	}
	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	public String getCurrentUsedVersion() {
		return currentUsedVersion;
	}
	public void setCurrentUsedVersion(String currentUsedVersion) {
		this.currentUsedVersion = currentUsedVersion;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
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
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	 
	 

	
}
