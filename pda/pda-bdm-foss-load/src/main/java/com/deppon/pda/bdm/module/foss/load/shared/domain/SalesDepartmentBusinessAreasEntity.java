package com.deppon.pda.bdm.module.foss.load.shared.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @Description TODO 城市对应的所有营业区
 * @author 268974
 * @date 2015-12-17
 */
public class SalesDepartmentBusinessAreasEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	// 默认营业区
	private String defaultAreaCode;
	// 默认营业区
	private String defaultAreaName;

	// 营业区对应的营业部
	private List<SalesDepartmentAreaEntitys> kdPartSalesDeptEntitys;

	public List<SalesDepartmentAreaEntitys> getKdPartSalesDeptEntitys() {
		return kdPartSalesDeptEntitys;
	}

	public void setKdPartSalesDeptEntitys(
			List<SalesDepartmentAreaEntitys> kdPartSalesDeptEntitys) {
		this.kdPartSalesDeptEntitys = kdPartSalesDeptEntitys;
	}

	public String getDefaultAreaCode() {
		return defaultAreaCode;
	}

	public void setDefaultAreaCode(String defaultAreaCode) {
		this.defaultAreaCode = defaultAreaCode;
	}

	public String getDefaultAreaName() {
		return defaultAreaName;
	}

	public void setDefaultAreaName(String defaultAreaName) {
		this.defaultAreaName = defaultAreaName;
	}

}
