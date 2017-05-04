package com.deppon.foss.module.pickup.waybill.server.utils.cubc.model;

import java.io.Serializable;
import java.util.Date;

import com.deppon.foss.util.UUIDUtils;

/**
 * 
    * @ClassName: VestLogDo
    * @Description: 灰度接口日志的实体
    * @author 323098
    * @date 2017年1月1日
    *
 */
public class VestLogDo  implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -609325566001341995L;
	private String id=UUIDUtils.getUUID();
	/**
	 * 请求id
	 */
	private Long requestId;
	
	/**
	 * 服务编码
	 */
	private String serviceCode;
	
	/**
	 * 客户编码
	 */
	private String customerCode;
	
	/**
	 * 客户类型
	 */
	private String customerType;
	
	/**
	 * 来源单据编码
	 */
	private String sourceBillNo;
	
	/**
	 * 来源单据类型
	 */
	private String sourceBillType;
	
	/**
	 * 归属服务系统编码
	 */
	private String systemCode;
	
	/**
	 * 请求时间
	 */
	private Date requestTime;
	
	/**
	 * 响应时间
	 */
	private Date responseTime;
	
	/**
	 * 来源系统
	 */
	private String origin;
	
	/**
	 * 始发部门是否是合伙人
	 */
	private int isStartDeptPartner;
	
	/**
	 * 响应内容
	 */
	private String responseContent;
	/**  
	 * 获取serviceCode  
	 * @return serviceCode serviceCode  
	 */
	public String getServiceCode() {
		return serviceCode;
	}

	
	/**  
	 * 获取isStartDeptPartner  
	 * @return isStartDeptPartner isStartDeptPartner  
	 */
	public int getIsStartDeptPartner() {
		return isStartDeptPartner;
	}


	
	/**  
	 * 设置isStartDeptPartner  
	 * @param isStartDeptPartner isStartDeptPartner  
	 */
	public void setIsStartDeptPartner(int isStartDeptPartner) {
		this.isStartDeptPartner = isStartDeptPartner;
	}


	/**  
	 * 设置serviceCode  
	 * @param serviceCode serviceCode  
	 */
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	
	/**  
	 * 获取customerCode  
	 * @return customerCode customerCode  
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	
	/**  
	 * 设置customerCode  
	 * @param customerCode customerCode  
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	
	/**  
	 * 获取customerType  
	 * @return customerType customerType  
	 */
	public String getCustomerType() {
		return customerType;
	}

	
	/**  
	 * 设置customerType  
	 * @param customerType customerType  
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	
	/**  
	 * 获取sourceBillNo  
	 * @return sourceBillNo sourceBillNo  
	 */
	public String getSourceBillNo() {
		return sourceBillNo;
	}

	
	/**  
	 * 设置sourceBillNo  
	 * @param sourceBillNo sourceBillNo  
	 */
	public void setSourceBillNo(String sourceBillNo) {
		this.sourceBillNo = sourceBillNo;
	}

	
	/**  
	 * 获取sourceBillType  
	 * @return sourceBillType sourceBillType  
	 */
	public String getSourceBillType() {
		return sourceBillType;
	}

	
	/**  
	 * 设置sourceBillType  
	 * @param sourceBillType sourceBillType  
	 */
	public void setSourceBillType(String sourceBillType) {
		this.sourceBillType = sourceBillType;
	}

	
	/**  
	 * 获取systemCode  
	 * @return systemCode systemCode  
	 */
	public String getSystemCode() {
		return systemCode;
	}

	
	/**  
	 * 设置systemCode  
	 * @param systemCode systemCode  
	 */
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	
	/**  
	 * 获取requestTime  
	 * @return requestTime requestTime  
	 */
	public Date getRequestTime() {
		return requestTime;
	}

	
	/**  
	 * 设置requestTime  
	 * @param requestTime requestTime  
	 */
	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	
	/**  
	 * 获取responseTime  
	 * @return responseTime responseTime  
	 */
	public Date getResponseTime() {
		return responseTime;
	}

	
	/**  
	 * 设置responseTime  
	 * @param responseTime responseTime  
	 */
	public void setResponseTime(Date responseTime) {
		this.responseTime = responseTime;
	}

	
	
	public String getOrigin() {
		return origin;
	}


	public void setOrigin(String origin) {
		this.origin = origin;
	}


	public String getResponseContent() {
		return responseContent;
	}


	public void setResponseContent(String responseContent) {
		this.responseContent = responseContent;
	}

	public Long getRequestId() {
		return requestId;
	}


	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "VestLogDo [id=" + id + ", requestId=" + requestId
				+ ", serviceCode=" + serviceCode + ", customerCode="
				+ customerCode + ", customerType=" + customerType
				+ ", sourceBillNo=" + sourceBillNo + ", sourceBillType="
				+ sourceBillType + ", systemCode=" + systemCode
				+ ", requestTime=" + requestTime + ", responseTime="
				+ responseTime + ", origin=" + origin + ", isStartDeptPartner="
				+ isStartDeptPartner + ", responseContent=" + responseContent
				+ "]";
	}
	}
