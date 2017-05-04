package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;

/***
 * 
 * @clasaName:com.deppon.foss.module.pickup.waybill.shared.dto
 * @author: yuting@163.com
 * @description: 批量发送短信 数据相关实体类
 * @date:2014年7月12日 上午8:55:37
 */
@SuppressWarnings("serial")
public class BatchSendSMSDto implements Serializable{
	
	/*############### 业务需求添加的额外字段  ##############**/	
	/* 发货统计**/
	private Integer deliveryCount;
	
	/* 签单返回统计**/
	private Integer signBillBackCount;
	
	/* 签单成功签收统计**/
	private Integer signBillSuccessCount;
	
	/* 签单成功返回统计**/
	private Integer signBillSuccessBackCount;
	
	/* 客户手机号**/
	private String customerMobile; 
	
	/* 客户部门编码 **/
	private String customerOrgCode;
	
	/* 操作部门的名称**/
	private String currentDeptName;
	
	/* 标记号码是否重复**/
	private String flag;
	
	public Integer getDeliveryCount() {
		return deliveryCount;
	}
	public void setDeliveryCount(Integer deliveryCount) {
		this.deliveryCount = deliveryCount;
	}
	public String getCustomerMobile() {
		return customerMobile;
	}
	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}
	public Integer getSignBillBackCount() {
		return signBillBackCount;
	}
	public void setSignBillBackCount(Integer signBillBackCount) {
		this.signBillBackCount = signBillBackCount;
	}
	public Integer getSignBillSuccessCount() {
		return signBillSuccessCount;
	}
	public void setSignBillSuccessCount(Integer signBillSuccessCount) {
		this.signBillSuccessCount = signBillSuccessCount;
	}
	public Integer getSignBillSuccessBackCount() {
		return signBillSuccessBackCount;
	}
	public void setSignBillSuccessBackCount(Integer signBillSuccessBackCount) {
		this.signBillSuccessBackCount = signBillSuccessBackCount;
	}
	public String getCustomerOrgCode() {
		return customerOrgCode;
	}
	public void setCustomerOrgCode(String customerOrgCode) {
		this.customerOrgCode = customerOrgCode;
	}
	public String getCurrentDeptName() {
		return currentDeptName;
	}
	public void setCurrentDeptName(String currentDeptName) {
		this.currentDeptName = currentDeptName;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
}
