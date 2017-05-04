package com.deppon.pda.bdm.module.foss.login.shared.domain;

import com.deppon.pda.bdm.module.core.shared.domain.DomainEntity;

/**
 * 
 * @package com.deppon.foss.module.bamCode.shared.domain
 * @file PrivilegeEntity.java
 * @description 权限实体类
 * @author chengang
 * @created 2012-10-9 下午3:17:08
 * @version 1.0
 */
public class PrivilegeEntity extends DomainEntity {

	/**
	 * @description
	 */

	private static final long serialVersionUID = 537144152424292848L;

	/**
	 * 权限编码
	 */
	private String privilegeCode;

	/**
	 * 权限名称
	 */
	private String privilegeName;

	/**
	 * 权限说明
	 */
	private String remark;
	private int privilegeIndex;

	public String getPrivilegeCode() {
		return privilegeCode;
	}

	public void setPrivilegeCode(String privilegeCode) {
		this.privilegeCode = privilegeCode;
	}

	public String getPrivilegeName() {
		return privilegeName;
	}

	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getPrivilegeIndex() {
		return privilegeIndex;
	}

	public void setPrivilegeIndex(int privilegeIndex) {
		this.privilegeIndex = privilegeIndex;
	}
	

}
