package com.deppon.pda.bdm.module.foss.login.shared.domain;

import java.util.Date;
import java.util.List;

import com.deppon.pda.bdm.module.core.shared.domain.DeptEntity;
import com.deppon.pda.bdm.module.core.shared.domain.UserEntity;


/**
 * PDA登陆反馈信息
 * @author gaojia
 * @date Sep 6,2012 14:33:30 PM
 * @version 1.0
 * @since
 */
public class PdaLoginRetInfo {
	
	/**
	 * 当前时间
	 */
	private Date currTime;
	/**
	 * 数据版本
	 */
	private String dataVer;
	/**
	 * 软件版本
	 */
	private String pgmVer;
	/**
	 * 是否数据更新
	 */
	private boolean reqDataUpgrade;
	/**
	 * 是否版本更新
	 */
	private boolean reqPgmUpgrade;
	/**
	 * 用户权限
	 */
	private List<PrivilegeEntity> userPrivilege;
	/**
	 * 强制更新
	 */
	private boolean forcFlag;
	/**
	 * 用户
	 */
	private UserEntity userEntity;
	/**
	 * 部门
	 */
	private DeptEntity deptEntity;
	/**
	 * 大部门
	 */
	private DeptEntity bigDept;
	
	/**
	 * 用户信息
	 */
	private List<UserInfoEntity> users;
	/**
	 * 长途目的站信息
	 */
	private List<DestDeptInfoEntity> longDestDepts;
	/**
	 * 短途目的站信息
	 */
	private List<DestDeptInfoEntity> shortDestDepts;
	/**
	 * 开单始发外场编码  用来计算走货路由
	 */
	private String departureFieldCode;
	/**
	 * 快递员出发营业部
	 */
	private String courierSourceOrgCode;
	
	/**
	 * 始发驻地营业部
	 */
	private String sourceStationSaleDept;
	/**
	 * 始发驻地营业部时效区域
	 */
	private String stationSaleDeptEffRegion;
	public DeptEntity getBigDept() {
		return bigDept;
	}
	public void setBigDept(DeptEntity bigDept) {
		this.bigDept = bigDept;
	}
	public UserEntity getUserEntity() {
		return userEntity;
	}
	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}
	public DeptEntity getDeptEntity() {
		return deptEntity;
	}
	public void setDeptEntity(DeptEntity deptEntity) {
		this.deptEntity = deptEntity;
	}
	public Date getCurrTime() {
		return currTime;
	}
	public void setCurrTime(Date currTime) {
		this.currTime = currTime;
	}
	public String getDataVer() {
		return dataVer;
	}
	public void setDataVer(String dataVer) {
		this.dataVer = dataVer;
	}
	public String getPgmVer() {
		return pgmVer;
	}
	public void setPgmVer(String pgmVer) {
		this.pgmVer = pgmVer;
	}
	
	public boolean isReqDataUpgrade() {
		return reqDataUpgrade;
	}
	public void setReqDataUpgrade(boolean reqDataUpgrade) {
		this.reqDataUpgrade = reqDataUpgrade;
	}
	public boolean isReqPgmUpgrade() {
		return reqPgmUpgrade;
	}
	public void setReqPgmUpgrade(boolean reqPgmUpgrade) {
		this.reqPgmUpgrade = reqPgmUpgrade;
	}
	
	public List<PrivilegeEntity> getUserPrivilege() {
		return userPrivilege;
	}
	public void setUserPrivilege(List<PrivilegeEntity> userPrivilege) {
		this.userPrivilege = userPrivilege;
	}
	public boolean isForcFlag() {
		return forcFlag;
	}
	public void setForcFlag(boolean forcFlag) {
		this.forcFlag = forcFlag;
	}
	public List<UserInfoEntity> getUsers() {
		return users;
	}
	public void setUsers(List<UserInfoEntity> users) {
		this.users = users;
	}
	public List<DestDeptInfoEntity> getLongDestDepts() {
		return longDestDepts;
	}
	public void setLongDestDepts(List<DestDeptInfoEntity> longDestDepts) {
		this.longDestDepts = longDestDepts;
	}
	public List<DestDeptInfoEntity> getShortDestDepts() {
		return shortDestDepts;
	}
	public void setShortDestDepts(List<DestDeptInfoEntity> shortDestDepts) {
		this.shortDestDepts = shortDestDepts;
	}
	public String getDepartureFieldCode() {
		return departureFieldCode;
	}
	public void setDepartureFieldCode(String departureFieldCode) {
		this.departureFieldCode = departureFieldCode;
	}
	public String getCourierSourceOrgCode() {
		return courierSourceOrgCode;
	}
	public void setCourierSourceOrgCode(String courierSourceOrgCode) {
		this.courierSourceOrgCode = courierSourceOrgCode;
	}
	public String getSourceStationSaleDept() {
		return sourceStationSaleDept;
	}
	public void setSourceStationSaleDept(String sourceStationSaleDept) {
		this.sourceStationSaleDept = sourceStationSaleDept;
	}
	public String getStationSaleDeptEffRegion() {
		return stationSaleDeptEffRegion;
	}
	public void setStationSaleDeptEffRegion(String stationSaleDeptEffRegion) {
		this.stationSaleDeptEffRegion = stationSaleDeptEffRegion;
	}
	
	
}