package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.io.Serializable;
import java.util.Date;

//CUBC归属服务实体类
public class RequestDO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 请求ID
	 */
	private Long requestId ;

	/**
	 * 服务编码
	 */
	private String serviceCode;
	
	/**
	 * 客户编码
	 */
	private String[] customerCodes;
	
	/**
	 * 客户类型
	 */
	private String customerType;
	
	/**
	 * 来源单据编号
	 */
	private String[] sourceBillNos;
	
	/**
	 * 来源单据类型
	 */
	private String sourceBillType;
	
	/**
	 * 来源系统
	 */
	private String origin;
	
	/**
	 * 始发部门是否是合伙人
	 */
	private int isStartDeptPartner; 
	
	
	/**
	 * 非反序列化，禁止使用该方法
	 * @param requestId
	 */
	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}




	public RequestDO() {
		super();
		this.requestId  = new Date().getTime();
	}
	
	
	
	public String getServiceCode() {
		return serviceCode;
	}

	public RequestDO(Long requestId, String serviceCode, String[] customerCodes, String customerType,
			String[] sourceBillNos, String sourceBillType, String origin, int isStartDeptPartner) {
		super();
		this.requestId = requestId;
		this.serviceCode = serviceCode;
		this.customerCodes = customerCodes;
		this.customerType = customerType;
		this.sourceBillNos = sourceBillNos;
		this.sourceBillType = sourceBillType;
		this.origin = origin;
		this.isStartDeptPartner = isStartDeptPartner;
	}




	public String[] getCustomerCodes() {
		return customerCodes;
	}




	public void setCustomerCodes(String[] customerCodes) {
		this.customerCodes = customerCodes;
	}




	public String getCustomerType() {
		return customerType;
	}




	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}




	public String[] getSourceBillNos() {
		return sourceBillNos;
	}




	public void setSourceBillNos(String[] sourceBillNos) {
		this.sourceBillNos = sourceBillNos;
	}




	public String getSourceBillType() {
		return sourceBillType;
	}




	public void setSourceBillType(String sourceBillType) {
		this.sourceBillType = sourceBillType;
	}




	public String getOrigin() {
		return origin;
	}




	public void setOrigin(String origin) {
		this.origin = origin;
	}




	public int getIsStartDeptPartner() {
		return isStartDeptPartner;
	}




	public void setIsStartDeptPartner(int isStartDeptPartner) {
		this.isStartDeptPartner = isStartDeptPartner;
	}




	public Long getRequestId() {
		return requestId;
	}




	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

}
