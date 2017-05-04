package com.deppon.pda.bdm.module.foss.upgrade.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

  
/**
 * TODO(查询条件实体)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:chengang,date:2013-4-10 下午2:25:54,content:TODO </p>
 * @author chengang
 * @date 2013-4-10 下午2:25:54
 * @since
 * @version
 */

public class BaseDataEntity extends BaseEntity{
	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 是否激活
	 */
	private String isActive;
	/**
	 * 开始时间
	 */
	private String startUpdTime;
	/**
	 * 结束时间
	 */
	private String endUpdTime;
	
	/**
	 * 部门ID
	 */
	private String deptCode;
	
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getStartUpdTime() {
		return startUpdTime;
	}
	public void setStartUpdTime(String startUpdTime) {
		this.startUpdTime = startUpdTime;
	}
	public String getEndUpdTime() {
		return endUpdTime;
	}
	public void setEndUpdTime(String endUpdTime) {
		this.endUpdTime = endUpdTime;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	
	
	
	
}
