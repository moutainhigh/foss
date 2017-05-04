package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
* @功能 官网请求参数到接送货走货路径主要参数值
* @author 105888-foss-zhangxingwang
* @date 2013-8-31 12:09:38
*
*/
public class FreightRouteParamCommonsDto implements Serializable {
	private static final long serialVersionUID = 1L;
	//请求参数ID
	private String paramsId;
	//出发部门编码
	private String startOrgCode;
	//到达部门编码
	private String lastLoadOrgCode;
	//产品类型
	private String productCode;
	//制单时间
	private Date billTime;
	public String getParamsId() {
		return paramsId;
	}
	public void setParamsId(String paramsId) {
		this.paramsId = paramsId;
	}
	public String getStartOrgCode() {
		return startOrgCode;
	}
	public void setStartOrgCode(String startOrgCode) {
		this.startOrgCode = startOrgCode;
	}
	public String getLastLoadOrgCode() {
		return lastLoadOrgCode;
	}
	public void setLastLoadOrgCode(String lastLoadOrgCode) {
		this.lastLoadOrgCode = lastLoadOrgCode;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public Date getBillTime() {
		return billTime;
	}
	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}
}
