package com.deppon.pda.bdm.module.foss.upgrade.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * TODO(基础数据-网点组实体)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:chengang,date:2013-5-17 下午4:01:26,content:TODO </p>
 * @author chengang
 * @date 2013-5-17 下午4:01:26
 * @since
 * @version
 */
public class NetGroupEntity extends BaseEntity{
	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 部门编号
	 */
	private String orgCode;
	/**
	 * 类型
	 */
	private String orgType;
	/**
	 * 路由虚拟编码
	 */
	private String routeVirtualCode;
	/**
	 * 操作标记
	 */
	private String operFlag;
	/**
	 * 上一次更新时间
	 */
	private Date updTime;
	/**
	 * 版本号
	 */
	private Long version;
	/**
	 * 是否生效
	 */
	private String isActive;
	/**
	 * 生效时间
	 */
	private Date activeTime;
	
	public String getOperFlag() {
		return operFlag;
	}
	public void setOperFlag(String operFlag) {
		this.operFlag = operFlag;
	}
	public Date getUpdTime() {
		return updTime;
	}
	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}
	
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public Date getActiveTime() {
		return activeTime;
	}
	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgType() {
		return orgType;
	}
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	public String getRouteVirtualCode() {
		return routeVirtualCode;
	}
	public void setRouteVirtualCode(String routeVirtualCode) {
		this.routeVirtualCode = routeVirtualCode;
	}
	
	
}
