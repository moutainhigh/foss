package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;

/**
 * 
 * TODO(快递返货工单受理 pda上传实体)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:245960,date:2015-8-19 下午2:01:10,content:TODO
 * </p>
 * 
 * @author 245960
 * @date 2015-8-19 下午2:01:10
 * @since
 * @version
 */
public class QryKdBackGoodsDealEntity implements Serializable {

	/**
	 * TODO（序列化标识）
	 */
	private static final long serialVersionUID = 1L;

	// 员工编号
	private String userCode;

	// 部门编号
	private String deptCode;

	/**
	 * @return  the userCode
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * @param userCode the userCode to set
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * @return  the deptCode
	 */
	public String getDeptCode() {
		return deptCode;
	}

	/**
	 * @param deptCode the deptCode to set
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	
}
