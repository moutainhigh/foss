package com.deppon.foss.module.transfer.partialline.api.shared.dto;

import java.util.Date;

/** 
 * @author: ibm-liuzhaowei
 * @description: 签收实体（落地配公司上传签收结果时使用）
 * @date: 2013-08-05 下午04:39:39
 * 
 */
public class LdpSignUpResultDto {
	
	/**
	 * 签收流水号
	 * 
	 */
	private String signUpId;
	/**
	 * 运单号
	 * 
	 */
	private String waybillNo;
	/**
	 * 代理公司名称
	 * 
	 */
	private String agentCompanyName;
	/**
	 * 代理公司编码
	 * 
	 */
	private String agentCompanyCode;
	/**
	 * 代理网点名称
	 * 
	 */
	private String agentOrgName;
	/**
	 * 代理网点编码
	 * 
	 */
	private String agentOrgCode;
	/**
	 * 到达时间(到达代理网点的时间)(yyyy-mm-dd HH24:mm:ss)
	 * 
	 */
	private Date receiveTime;
	/**
	 * 送货时间(代理准备送货的时间)(yyyy-mm-dd HH24:mm:ss)
	 * 
	 */
	private Date deliveryTime;
	/**
	 * 送货人(代理的员工)
	 * 
	 */
	private String deliveryUserName;
	/**
	 * 签收时间(yyyy-mm-dd HH24:mm:ss)
	 * 
	 */
	private Date signUpTime;
	/**
	 * 签收人(客户)
	 * 
	 */
	private String signUpUserName;
	/**
	 * 件数
	 * 
	 */
	private Integer goodsNum;
	/**
	 * 签收状态：
	 * 正常签收-1 
	 * 部分签收-3
	 * 拒签-4（落地配不可二次派送）
	 * 
	 */
	private String signUpType;
	/**
	 * 货物拉回异常描述：
	 * 司机晚送:DRIVER_LATE                                
	 * 付款问题:PAY_QUESTION                               
	 * 无法卸货:UNLOAD                                     
	 * 区域车辆禁行:CAR_FORBID                               
	 * 发票问题拒收:BILL_QUESTION                            
	 * 车辆故障/事故:CAR_BREAK                                
	 * 客户要求更改地址:CUSTOM_CHANGE_ADDRESS                  
	 * 客户不在无人收货:CUSTOM_NOTIN                           
	 * 排单有误，线路安排不对:LINE_WRONG                           
	 * 开单地址有误/电话录入错误:PHONE_WRONG                        
	 * 因货物破损/件数不符等原因客户拒收:GOODS_BREAK                    
	 * 客户要求开箱验货/送货上楼协商未果:CONSULT_UNSUCCESS              
	 * 其他:OTHER
	 * 
	 */
	private String exceptionDescribe;
	
	
	public String getSignUpId() {
		return signUpId;
	}
	public void setSignUpId(String signUpId) {
		this.signUpId = signUpId;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getAgentCompanyName() {
		return agentCompanyName;
	}
	public void setAgentCompanyName(String agentCompanyName) {
		this.agentCompanyName = agentCompanyName;
	}
	public String getAgentCompanyCode() {
		return agentCompanyCode;
	}
	public void setAgentCompanyCode(String agentCompanyCode) {
		this.agentCompanyCode = agentCompanyCode;
	}
	public String getAgentOrgName() {
		return agentOrgName;
	}
	public void setAgentOrgName(String agentOrgName) {
		this.agentOrgName = agentOrgName;
	}
	public String getAgentOrgCode() {
		return agentOrgCode;
	}
	public void setAgentOrgCode(String agentOrgCode) {
		this.agentOrgCode = agentOrgCode;
	}
	public Date getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}
	public Date getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public String getDeliveryUserName() {
		return deliveryUserName;
	}
	public void setDeliveryUserName(String deliveryUserName) {
		this.deliveryUserName = deliveryUserName;
	}
	public Date getSignUpTime() {
		return signUpTime;
	}
	public void setSignUpTime(Date signUpTime) {
		this.signUpTime = signUpTime;
	}
	public String getSignUpUserName() {
		return signUpUserName;
	}
	public void setSignUpUserName(String signUpUserName) {
		this.signUpUserName = signUpUserName;
	}
	public Integer getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}
	public String getSignUpType() {
		return signUpType;
	}
	public void setSignUpType(String signUpType) {
		this.signUpType = signUpType;
	}
	public String getExceptionDescribe() {
		return exceptionDescribe;
	}
	public void setExceptionDescribe(String exceptionDescribe) {
		this.exceptionDescribe = exceptionDescribe;
	}

}
