package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.io.Serializable;
/**
 * 
 * @ClassName: WSCWayBillReturnResult 
 * @Description: TODO(查询待刷卡数据返回结果实体) 
 * @author &268974  wangzhili
 * @date 2016-2-24 下午2:42:01 
 *
 */
public class WSCWayBillReturnResult implements Serializable{

	 
	/**  
	 * @author 268974  wangzhili
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 *   
	 * @since Ver 1.0   
	*/
	private static final long serialVersionUID = 1L;
	//运单号
	private String wayBillNo;
	//数据来源
	private String wayBillSource;
	//待刷卡金额
	private double waitSwipeAmount;
	//发货人名称
	private String sendCustomerName;
	//待刷卡运单数据条目编号
	private String wscItemId;
	// 发货人编号
	private String sendCustomerCode;
	//运单总金额
	private double wayBillAmount;
	//归属系统
	private String affiliation;
	
	public String getAffiliation() {
		return affiliation;
	}
	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}
	public double getWayBillAmount() {
		return wayBillAmount;
	}
	public void setWayBillAmount(double wayBillAmount) {
		this.wayBillAmount = wayBillAmount;
	}
	public String getWayBillNo() {
		return wayBillNo;
	}
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}
	public String getWayBillSource() {
		return wayBillSource;
	}
	public void setWayBillSource(String wayBillSource) {
		this.wayBillSource = wayBillSource;
	}
	
	public double getWaitSwipeAmount() {
		return waitSwipeAmount;
	}
	public void setWaitSwipeAmount(double waitSwipeAmount) {
		this.waitSwipeAmount = waitSwipeAmount;
	}
	public String getSendCustomerName() {
		return sendCustomerName;
	}
	public void setSendCustomerName(String sendCustomerName) {
		this.sendCustomerName = sendCustomerName;
	}
	public String getWscItemId() {
		return wscItemId;
	}
	public void setWscItemId(String wscItemId) {
		this.wscItemId = wscItemId;
	}
	public String getSendCustomerCode() {
		return sendCustomerCode;
	}
	public void setSendCustomerCode(String sendCustomerCode) {
		this.sendCustomerCode = sendCustomerCode;
	}

	
	

}
