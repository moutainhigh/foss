package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @date 2016/12/30
 * @author 353654
 *
 */
public class CubcGrayRequestEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//来源单据号
	private List<String> sourceBillNos;
	
	//来源单据子类型  W运单  R小票....
	private String sourceBillType;
	
	//全类名+方法名
	private String serviceCode;
	
	//客户编码
	private List<String> customerCodes;
	
	//客户类型
	private String customerType;
	
	//来源系统  FOSS
	private String origin;

	public List<String> getSourceBillNos() {
		return sourceBillNos;
	}

	public void setSourceBillNos(List<String> sourceBillNos) {
		this.sourceBillNos = sourceBillNos;
	}

	public String getSourceBillType() {
		return sourceBillType;
	}

	public void setSourceBillType(String sourceBillType) {
		this.sourceBillType = sourceBillType;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public List<String> getCustomerCodes() {
		return customerCodes;
	}

	public void setCustomerCodes(List<String> customerCodes) {
		this.customerCodes = customerCodes;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public CubcGrayRequestEntity(List<String> sourceBillNos,
			String sourceBillType, String serviceCode, String origin) {
		super();
		this.sourceBillNos = sourceBillNos;
		this.sourceBillType = sourceBillType;
		this.serviceCode = serviceCode;
		this.origin = origin;
	}

	public CubcGrayRequestEntity() {
		super();
	}

	public CubcGrayRequestEntity(String serviceCode,
			List<String> customerCodes, String customerType, String origin) {
		super();
		this.serviceCode = serviceCode;
		this.customerCodes = customerCodes;
		this.customerType = customerType;
		this.origin = origin;
	}

	public CubcGrayRequestEntity(String serviceCode, String origin) {
		super();
		this.serviceCode = serviceCode;
		this.origin = origin;
	}
	
}
