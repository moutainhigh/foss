package com.deppon.pda.bdm.module.foss.crgreg.shared.domain;


/**
 * 货区下拉列表实体
 * @author xujun
 *
 */
public class StockPostionEntity {
	/**
	 * 库位名称
	 */
	private String valueName;
	/**
	 * 库位编码
	 */
	private String valueCode;
	/**
	 * 货区编码
	 */
	private String areaCode;
	
	public String getValueName() {
		return valueName;
	}
	public void setValueName(String valueName) {
		this.valueName = valueName;
	}
	public String getValueCode() {
		return valueCode;
	}
	public void setValueCode(String valueCode) {
		this.valueCode = valueCode;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	
	
}
