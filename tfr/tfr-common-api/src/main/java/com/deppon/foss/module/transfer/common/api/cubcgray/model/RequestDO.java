package com.deppon.foss.module.transfer.common.api.cubcgray.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * 
 *<pre>
 *功能:请求实体
 *作者：132028
 *日期：2016年12月19日上午10:21:45
 *</pre>
 */
public class RequestDO implements Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8288357652556279215L;
	
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



	/**
	 * <p>创建一个新的实例 RequestDO.TODO</p>
	 * @author 132028
	 * @date 2016年12月23日 下午4:28:01
	 */
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



	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
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

	
	@Override
	public String toString() {
		return "RequestDO [requestId=" + requestId + ", serviceCode=" + serviceCode + ", customerCodes="
				+ Arrays.toString(customerCodes) + ", customerType=" + customerType + ", sourceBillNos="
				+ Arrays.toString(sourceBillNos) + ", sourceBillType=" + sourceBillType + ", origin=" + origin
				+ ", isStartDeptPartner=" + isStartDeptPartner + "]";
	}

	
}
