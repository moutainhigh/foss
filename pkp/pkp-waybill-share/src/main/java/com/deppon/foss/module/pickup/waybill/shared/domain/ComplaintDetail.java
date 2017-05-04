package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.io.Serializable;
@SuppressWarnings("serial")
public class ComplaintDetail implements Serializable{

	/**
	 * Description 凭证号
	 * @author 120750 
	 * @version 0.1 
	 */
	private String voucherNumber;
	
	/**
	 * Description get 凭证号
	 * @author 120750 
	 * @version 0.1 
	 * @return String
	 */
	public String getVoucherNumber(){
		return (voucherNumber == null?"":voucherNumber);
	}
	
	/**
	 * Description set 凭证号
	 * @author 120750 
	 * @version 0.1 
	 * @param f_voucher_number
	 */
	public void setVoucherNumber(String voucherNumber){
		this.voucherNumber = voucherNumber;
	}
	/**
	 * Description 处理编号
	 * @author 120750 
	 * @version 0.1 
	 */
	private String dealNumber;
	
	/**
	 * Description get 处理编号
	 * @author 120750 
	 * @version 0.1 
	 * @return String
	 */
	public String getDealNumber(){
		return (dealNumber == null?"":dealNumber);
	}
	
	/**
	 * Description set 处理编号
	 * @author 120750 
	 * @version 0.1 
	 * @param f_deal_number
	 */
	public void setDealNumber(String dealNumber){
		this.dealNumber = dealNumber;
	}
	
	/**
	 * Description 来电人-字符长度20
	 * @author 120750 
	 * @version 0.1 
	 */
	private String callMan;
	
	/**
	 * Description get 来电人-字符长度20
	 * @author 120750 
	 * @version 0.1 
	 * @return String
	 */
	public String getCallMan(){
		return (callMan == null?"":callMan);
	}
	
	/**
	 * Description set 来电人-字符长度20
	 * @author 120750 
	 * @version 0.1 
	 * @param f_call_man
	 */
	public void setCallMan(String callMan){
		this.callMan = callMan;
	}
	/**
	 * Description 来电人类型 - 发货方=SHIPMAN、收货方=RECEIVEMAN、第三方=THIRDPARTY、内部同事=INNERCOLLE
	 * @author 120750 
	 * @version 0.1 
	 */
	private String callType;
	
	/**
	 * Description get 来电人类型 - 发货方=SHIPMAN、收货方=RECEIVEMAN、第三方=THIRDPARTY、内部同事=INNERCOLLE
	 * @author 120750 
	 * @version 0.1 
	 * @return String
	 */
	public String getCallType(){
		return (callType == null?"":callType);
	}
	
	/**
	 * Description set 来电人类型 - 发货方=SHIPMAN、收货方=RECEIVEMAN、第三方=THIRDPARTY、内部同事=INNERCOLLE
	 * @author 120750 
	 * @version 0.1 
	 * @param f_call_type
	 */
	public void setCallType(String callType){
		this.callType = callType;
	}
	
	
	/**
	 * Description 来电客户- 字符长度40
	 * @author 120750 
	 * @version 0.1 
	 */
	private String callCust;
	
	
	
	/**
	 * Description get 来电客户- 字符长度40
	 * @author 120750 
	 * @version 0.1 
	 * @return String
	 */
	public String getCallCust(){
		return (callCust == null?"":callCust);
	}
	
	/**
	 * Description set 来电客户- 字符长度40
	 * @author 120750 
	 * @version 0.1 
	 * @param f_call_cust
	 */
	public void setCallCust(String callCust){
		this.callCust = callCust;
	}
	/**
	 * Description 客户等级-来电客户：固定客户、散客、潜客，备注：字符长度20
	 * @author 120750 
	 * @version 0.1 
	 */
	private String callCustLevel;
	
	/**
	 * Description get 客户等级-来电客户：固定客户、散客、潜客，备注：字符长度20
	 * @author 120750 
	 * @version 0.1 
	 * @return String
	 */
	public String getCallCustLevel(){
		return (callCustLevel == null?"":callCustLevel);
	}
	
	/**
	 * Description set 客户等级-来电客户：固定客户、散客、潜客，备注：字符长度20
	 * @author 120750 
	 * @version 0.1 
	 * @param f_call_cust_level
	 */
	public void setCallCustLevel(String callCustLevel){
		this.callCustLevel = callCustLevel;
	}
	/**
	 * Description 客户类型 - 来电客户：企业、个人，备注：字符长度20
	 * @author 120750 
	 * @version 0.1 
	 */
	private String callCustType;
	
	/**
	 * Description get 客户类型 - 来电客户：企业、个人，备注：字符长度20
	 * @author 120750 
	 * @version 0.1 
	 * @return String
	 */
	public String getCallCustType(){
		return (callCustType == null?"":callCustType);
	}
	
	/**
	 * Description set 客户类型 - 来电客户：企业、个人，备注：字符长度20
	 * @author 120750 
	 * @version 0.1 
	 * @param f_call_cust_type
	 */
	public void setCallCustType(String callCustType){
		this.callCustType = callCustType;
	}
	
	/**
	 * Description 工单来源-苏州电话=SUZHOU、合肥电话=HEFEI、官网=GUANWANG、企业邮箱=EMAIL
	 * @author 120750 
	 * @version 0.1 
	 */
	private String source;
	
	/**
	 * Description get 工单来源-苏州电话=SUZHOU、合肥电话=HEFEI、官网=GUANWANG、企业邮箱=EMAIL
	 * @author 120750 
	 * @version 0.1 
	 * @return String
	 */
	public String getSource(){
		return (source == null?"":source);
	}
	
	/**
	 * Description set 工单来源-苏州电话=SUZHOU、合肥电话=HEFEI、官网=GUANWANG、企业邮箱=EMAIL
	 * @author 120750 
	 * @version 0.1 
	 * @param f_source
	 */
	public void setSource(String source){
		this.source = source;
	}
	
	/**
	 * Description 客户要求-字符长度 200
	 * @author 120750 
	 * @version 0.1 
	 */
	private String custDemand;
	
	/**
	 * Description get 客户要求-字符长度 200
	 * @author 120750 
	 * @version 0.1 
	 * @return String
	 */
	public String getCustDemand(){
		return (custDemand == null?"":custDemand);
	}
	
	/**
	 * Description set 客户要求-字符长度 200
	 * @author 120750 
	 * @version 0.1 
	 * @param f_cust_demand
	 */
	public void setCustDemand(String custDemand){
		this.custDemand = custDemand;
	}
	
	/**
	 * Description 上报时间
	 * @author 120750 
	 * @version 0.1 
	 */
	private Long reportTime;
	
	public Long getReportTime() {
		return reportTime;
	}

	public void setReportTime(Long reportTime) {
		this.reportTime = reportTime;
	}
	/**
	 * Description 处理事项 - 字符长度 200
	 * @author 120750 
	 * @version 0.1 2014-09-18 21:08:57
	 */
	private String dealMatter;
	
	/**
	 * Description get 处理事项 - 字符长度 200
	 * @author 120750 
	 * @version 0.1 2014-09-18 21:08:57
	 * @return String
	 */
	public String getDealMatter(){
		return (dealMatter == null?"":dealMatter);
	}
	
	/**
	 * Description set 处理事项 - 字符长度 200
	 * @author 120750 
	 * @version 0.1 2014-09-18 21:08:57
	 * @param f_deal_matter
	 */
	public void setDealMatter(String dealMatter){
		this.dealMatter = dealMatter;
	}
	/**
	 * Description 责任部门
	 * @author 148706 
	 */
	private String dutyDepartment;
	/**
	 * Description 责任人
	 * @author 148706 
	 */
	private String dutyPerson;

	public String getDutyDepartment() {
		return dutyDepartment;
	}

	public void setDutyDepartment(String dutyDepartment) {
		this.dutyDepartment = dutyDepartment;
	}

	public String getDutyPerson() {
		return dutyPerson;
	}

	public void setDutyPerson(String dutyPerson) {
		this.dutyPerson = dutyPerson;
	}
	/**
	 * Description 工单级别 - 一级=LEVEL_1、二级=LEVEL_2、三级=LEVEL_3、四级=LEVEL_4、五级=LEVEL_5
	 * @author 120750 
	 * @version 0.1 
	 */
	private String compLevel;
	
	/**
	 * Description get 工单级别 - 一级=LEVEL_1、二级=LEVEL_2、三级=LEVEL_3、四级=LEVEL_4、五级=LEVEL_5
	 * @author 120750 
	 * @version 0.1 
	 * @return String
	 */
	public String getCompLevel(){
		return (compLevel == null?"":compLevel);
	}
	
	/**
	 * Description set 工单级别 - 一级=LEVEL_1、二级=LEVEL_2、三级=LEVEL_3、四级=LEVEL_4、五级=LEVEL_5
	 * @author 120750 
	 * @version 0.1 
	 * @param f_comp_level
	 */
	public void setCompLevel(String compLevel){
		this.compLevel = compLevel;
	}
	/**
	 * Description 联系电话-字符长度20
	 * @author 120750 
	 * @version 0.1 
	 */
	private String contactPhone;
	
	/**
	 * Description get 联系电话-字符长度20
	 * @author 120750 
	 * @version 0.1 
	 * @return String
	 */
	public String getContactPhone(){
		return (contactPhone == null?"":contactPhone);
	}
	
	/**
	 * Description set 联系电话-字符长度20
	 * @author 120750 
	 * @version 0.1 
	 * @param f_contact_phone
	 */
	public void setContactPhone(String contactPhone){
		this.contactPhone = contactPhone;
	}
	/**
	 * Description 业务类型
	 * @author 120750 
	 * @version 0.1 2014-09-18 21:08:57
	 */
	private String bussType;
	
	/**
	 * Description get 业务类型
	 * @author 120750 
	 * @version 0.1 2014-09-18 21:08:57
	 * @return String
	 */
	public String getBussType(){
		return (bussType == null?"":bussType);
	}
	
	/**
	 * Description set 业务类型
	 * @author 120750 
	 * @version 0.1 2014-09-18 21:08:57
	 * @param f_buss_type
	 */
	public void setBussType(String bussType){
		this.bussType = bussType;
	}
}
