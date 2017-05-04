package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @package com.deppon.pda.bdm.module.foss.accept.shared.domain
 * @file ProductInfoEntity.java
 * @description 产品信息查询
 * @author ChenLiang
 * @created 2012-12-31 下午3:02:07
 * @version 1.0
 */
public class ProductInfoEntity implements Serializable {

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 出发城市
	 */
	private String departDeptCity;

	/**
	 * 到达城市
	 */
	private String assemblyCity;

	/**
	 * 日期
	 */
	private Date date;

	public String getDepartDeptCity() {
		return departDeptCity;
	}

	public void setDepartDeptCity(String departDeptCity) {
		this.departDeptCity = departDeptCity;
	}

	public String getAssemblyCity() {
		return assemblyCity;
	}

	public void setAssemblyCity(String assemblyCity) {
		this.assemblyCity = assemblyCity;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
