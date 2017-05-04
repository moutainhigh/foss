package com.deppon.foss.module.transfer.stock.api.shared.domain;

import java.io.Serializable;


/**
 * 异常货同步未处理的单号到达部门实体类
 * @author 218381
 *
 */
public class WaybillArridept implements Serializable{
	private static final long serialVersionUID = 1L;
	//运单号
	private String waybillNo;
	//库存部门code
	private String stockCode;
	//库存部门name
	private String stockName;
	//到达部门名称
	private String arrideptName;
	//到达部门编码
	private String arrideptCode;
	//大区code
	private String bigRegionCode;
	//大区名称
	private String bigRegionName;
	//事业部code
	private String divisionCode;
	//事业部名称
	private String divisionName;
	
	public String getStockCode() {
		return stockCode;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	public String getBigRegionCode() {
		return bigRegionCode;
	}
	public void setBigRegionCode(String bigRegionCode) {
		this.bigRegionCode = bigRegionCode;
	}
	public String getBigRegionName() {
		return bigRegionName;
	}
	public void setBigRegionName(String bigRegionName) {
		this.bigRegionName = bigRegionName;
	}
	public String getDivisionCode() {
		return divisionCode;
	}
	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}
	public String getDivisionName() {
		return divisionName;
	}
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getArrideptName() {
		return arrideptName;
	}
	public void setArrideptName(String arrideptName) {
		this.arrideptName = arrideptName;
	}
	public String getArrideptCode() {
		return arrideptCode;
	}
	public void setArrideptCode(String arrideptCode) {
		this.arrideptCode = arrideptCode;
	}
	
}
