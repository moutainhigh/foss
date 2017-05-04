/**
 * 
 */
package com.deppon.foss.module.transfer.platform.api.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 105795
 *
 */
public class QueryConditionStockMovtionDto implements Serializable {


	private static final long serialVersionUID = 1L;
	//运单号
	private String waybillNo;
	//运输性质
	private String productCode;
	//外场
	private String origOrgCode;
	private String origOrgName;
	
	//当前位置
	private String colIndex;
	//库区
	private String goodsAreaCode;
	
	//货区编码list
	private List<String> goodsAreaCodeList=new ArrayList<String>();
	
	/**
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	/**
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	/**
	 * @return the productCode
	 */
	public String getProductCode() {
		return productCode;
	}
	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	/**
	 * @return the origOrgCode
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}
	/**
	 * @param origOrgCode the origOrgCode to set
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}
	
	/**
	 * @return the colIndex
	 */
	public String getColIndex() {
		return colIndex;
	}
	/**
	 * @param colIndex the colIndex to set
	 */
	public void setColIndex(String colIndex) {
		this.colIndex = colIndex;
	}
	/**
	 * @return the goodsAreaCode
	 */
	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}
	/**
	 * @param goodsAreaCode the goodsAreaCode to set
	 */
	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}
	/**
	 * @return the origOrgName
	 */
	public String getOrigOrgName() {
		return origOrgName;
	}
	/**
	 * @param origOrgName the origOrgName to set
	 */
	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}
	/**
	 * @return the goodsAreaCodeList
	 */
	public List<String> getGoodsAreaCodeList() {
		return goodsAreaCodeList;
	}
	/**
	 * @param goodsAreaCodeList the goodsAreaCodeList to set
	 */
	public void setGoodsAreaCodeList(List<String> goodsAreaCodeList) {
		this.goodsAreaCodeList = goodsAreaCodeList;
	}
	
	
	 
	
}
