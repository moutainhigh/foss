package com.deppon.foss.module.settlement.pay.api.shared.domain;

import java.io.Serializable;

/**
 * @author 218392 张永雪
 * @date 2015-12-08 15:15:20
 */
public class CashCheckConfirmEntityRequest implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 部门标杆编码
	 */
	protected String deptBenchmarkCode;

	public String getDeptBenchmarkCode() {
		return deptBenchmarkCode;
	}

	public void setDeptBenchmarkCode(String deptBenchmarkCode) {
		this.deptBenchmarkCode = deptBenchmarkCode;
	}
	
}
