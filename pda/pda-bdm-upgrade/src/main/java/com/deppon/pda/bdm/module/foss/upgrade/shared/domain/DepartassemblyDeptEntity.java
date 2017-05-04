package com.deppon.pda.bdm.module.foss.upgrade.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;



  
/**
 * TODO(始发部门配置实体类)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:chengang,date:2013-4-10 下午2:29:05,content:TODO </p>
 * @author chengang
 * @date 2013-4-10 下午2:29:05
 * @since
 * @version
 */

public class DepartassemblyDeptEntity extends BaseEntity {
	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;

	
	
	/**
	 * 出发部门
	 */
	private String departDeptId;
	
	/**
	 * 配载部门
	 */
	private String assemblyDeptId;
	
	/**
	 * 配载类型
	 */
	private String assemblyType;
	
	/**
	 * 是否生效
	 */
	private String isActive;
	
	/**
	 * 生效时间
	 */
	private String activeTime;
	
	/**
	 * 版本号
	 */
	private String version;
	
	/**
	 * 操作标记
	 */
	private String operFlag;
	
	/**
	 * 最后更新时间
	 */
	private String updTime;
	
	/**
	 * 是否默认
	 */
	private String isDefault;

	public String getDepartDeptId() {
		return departDeptId;
	}

	public void setDepartDeptId(String departDeptId) {
		this.departDeptId = departDeptId;
	}

	public String getAssemblyDeptId() {
		return assemblyDeptId;
	}

	public void setAssemblyDeptId(String assemblyDeptId) {
		this.assemblyDeptId = assemblyDeptId;
	}

	public String getAssemblyType() {
		return assemblyType;
	}

	public void setAssemblyType(String assemblyType) {
		this.assemblyType = assemblyType;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(String activeTime) {
		this.activeTime = activeTime;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getOperFlag() {
		return operFlag;
	}

	public void setOperFlag(String operFlag) {
		this.operFlag = operFlag;
	}

	public String getUpdTime() {
		return updTime;
	}

	public void setUpdTime(String updTime) {
		this.updTime = updTime;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	
	
	
	
}	
