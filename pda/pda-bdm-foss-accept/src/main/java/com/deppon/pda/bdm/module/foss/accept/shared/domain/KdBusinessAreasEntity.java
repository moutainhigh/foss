package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @Description TODO 城市对应的所有营业区
 * @author 268974
 * @date 2015-12-17
 */
public class KdBusinessAreasEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	// 默认营业区
	private String defaultAreaCode;
	// 默认营业区
	private String defaultAreaName;
	// 当前城市对应的营业区
	private List<kdPartSalesAreaEntitys> kdPartSalesAreaEntitys;
	// 营业区对应的营业部
	private List<KdPartSalesDeptEntity> kdPartSalesDeptEntitys;

	/*
	 * public String getAreaCode() { return areaCode; } public void
	 * setAreaCode(String areaCode) { this.areaCode = areaCode; } public String
	 * getAreaName() { return areaName; } public void setAreaName(String
	 * areaName) { this.areaName = areaName; }
	 */

	

	public List<kdPartSalesAreaEntitys> getKdPartSalesAreaEntitys() {
		return kdPartSalesAreaEntitys;
	}

	public void setKdPartSalesAreaEntitys(
			List<kdPartSalesAreaEntitys> kdPartSalesAreaEntitys) {
		this.kdPartSalesAreaEntitys = kdPartSalesAreaEntitys;
	}

	public void setKdPartSalesDeptEntitys(
			List<KdPartSalesDeptEntity> kdPartSalesDeptEntitys) {
		this.kdPartSalesDeptEntitys = kdPartSalesDeptEntitys;
	}
	public List<KdPartSalesDeptEntity> getKdPartSalesDeptEntitys() {
		return kdPartSalesDeptEntitys;
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
