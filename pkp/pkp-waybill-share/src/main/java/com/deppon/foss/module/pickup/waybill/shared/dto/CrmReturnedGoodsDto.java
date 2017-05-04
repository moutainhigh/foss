package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CrmReturnedGoodsDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Description 主键ID
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private Integer id;

	/**
	 * Description get 主键ID
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return Integer
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Description set 主键ID
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Description 原运单号（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private String oriWaybill;

	/**
	 * Description get 原运单号（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return String
	 */
	public String getOriWaybill() {
		return (oriWaybill == null ? "" : oriWaybill);
	}

	/**
	 * Description set 原运单号（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_ori_waybill
	 */
	public void setOriWaybill(String oriWaybill) {
		this.oriWaybill = oriWaybill;
	}

	/**
	 * Description 处理编号
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private String dealnumber;

	/**
	 * Description get 处理编号
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return String
	 */
	public String getDealnumber() {
		return (dealnumber == null ? "" : dealnumber);
	}

	/**
	 * Description set 处理编号
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_dealnumber
	 */
	public void setDealnumber(String dealnumber) {
		this.dealnumber = dealnumber;
	}

	/**
	 * Description 发货人姓名（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private String manSend;

	/**
	 * Description get 发货人姓名（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return String
	 */
	public String getManSend() {
		return (manSend == null ? "" : manSend);
	}

	/**
	 * Description set 发货人姓名（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_man_send
	 */
	public void setManSend(String manSend) {
		this.manSend = manSend;
	}

	/**
	 * Description 发货人客户编码（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private String manSendCode;

	/**
	 * Description get 发货人客户编码（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return String
	 */
	public String getManSendCode() {
		return (manSendCode == null ? "" : manSendCode);
	}

	/**
	 * Description set 发货人客户编码（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_man_send_code
	 */
	public void setManSendCode(String manSendCode) {
		this.manSendCode = manSendCode;
	}

	/**
	 * Description 收货人姓名（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private String manReceive;

	/**
	 * Description get 收货人姓名（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return String
	 */
	public String getManReceive() {
		return (manReceive == null ? "" : manReceive);
	}

	/**
	 * Description set 收货人姓名（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_man_receive
	 */
	public void setManReceive(String manReceive) {
		this.manReceive = manReceive;
	}

	/**
	 * Description 收货人客户编码（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private String manReceiveCode;

	/**
	 * Description get 收货人客户编码（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return String
	 */
	public String getManReceiveCode() {
		return (manReceiveCode == null ? "" : manReceiveCode);
	}

	/**
	 * Description set 收货人客户编码（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_man_receive_code
	 */
	public void setManReceiveCode(String manReceiveCode) {
		this.manReceiveCode = manReceiveCode;
	}

	/**
	 * Description 发货人电话（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private String phoneSend;

	/**
	 * Description get 发货人电话（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return String
	 */
	public String getPhoneSend() {
		return (phoneSend == null ? "" : phoneSend);
	}

	/**
	 * Description set 发货人电话（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_phone_send
	 */
	public void setPhoneSend(String phoneSend) {
		this.phoneSend = phoneSend;
	}

	/**
	 * Description 收货人电话（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private String phoneReceive;

	/**
	 * Description get 收货人电话（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return String
	 */
	public String getPhoneReceive() {
		return (phoneReceive == null ? "" : phoneReceive);
	}

	/**
	 * Description set 收货人电话（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_phone_receive
	 */
	public void setPhoneReceive(String phoneReceive) {
		this.phoneReceive = phoneReceive;
	}

	/**
	 * Description 出发部门（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private String deptStart;

	/**
	 * Description get 出发部门（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return String
	 */
	public String getDeptStart() {
		return (deptStart == null ? "" : deptStart);
	}

	/**
	 * Description set 出发部门（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_dept_start
	 */
	public void setDeptStart(String deptStart) {
		this.deptStart = deptStart;
	}

	/**
	 * Description 到达部门（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private String deptArrive;

	/**
	 * Description get 到达部门（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return String
	 */
	public String getDeptArrive() {
		return (deptArrive == null ? "" : deptArrive);
	}

	/**
	 * Description set 到达部门（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_dept_arrive
	 */
	public void setDeptArrive(String deptArrive) {
		this.deptArrive = deptArrive;
	}

	/**
	 * Description 代收货款（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private BigDecimal moneyReceive;

	/**
	 * Description get 代收货款（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return Integer
	 */
	public BigDecimal getMoneyReceive() {
		return moneyReceive;
	}

	/**
	 * Description set 代收货款（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_money_receive
	 */
	public void setMoneyReceive(BigDecimal moneyReceive) {
		this.moneyReceive = moneyReceive;
	}

	/**
	 * Description 保价（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private BigDecimal moneyInsured;

	/**
	 * Description get 保价（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return Integer
	 */
	public BigDecimal getMoneyInsured() {
		return moneyInsured;
	}

	/**
	 * Description set 保价（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_money_insured
	 */
	public void setMoneyInsured(BigDecimal moneyInsured) {
		this.moneyInsured = moneyInsured;
	}

	/**
	 * Description 发货人地址（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private String addressSend;

	/**
	 * Description get 发货人地址（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return String
	 */
	public String getAddressSend() {
		return (addressSend == null ? "" : addressSend);
	}

	/**
	 * Description set 发货人地址（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_address_send
	 */
	public void setAddressSend(String addressSend) {
		this.addressSend = addressSend;
	}

	/**
	 * Description 收货人地址（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private String addressReceive;

	/**
	 * Description get 收货人地址（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return String
	 */
	public String getAddressReceive() {
		return (addressReceive == null ? "" : addressReceive);
	}

	/**
	 * Description set 收货人地址（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_address_receive
	 */
	public void setAddressReceive(String addressReceive) {
		this.addressReceive = addressReceive;
	}

	/**
	 * Description 上报人姓名（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private String manReport;

	/**
	 * Description get 上报人姓名（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return String
	 */
	public String getManReport() {
		return (manReport == null ? "" : manReport);
	}

	/**
	 * Description set 上报人姓名（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_man_report
	 */
	public void setManReport(String manReport) {
		this.manReport = manReport;
	}

	/**
	 * Description 上报人工号（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private String manReportCode;

	/**
	 * Description get 上报人工号（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return String
	 */
	public String getManReportCode() {
		return (manReportCode == null ? "" : manReportCode);
	}

	/**
	 * Description set 上报人工号（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_man_report_code
	 */
	public void setManReportCode(String manReportCode) {
		this.manReportCode = manReportCode;
	}

	/**
	 * Description 上报时间（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private Date timeReport;

	/**
	 * Description get 上报时间（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return Date
	 */
	public Date getTimeReport() {
		return timeReport;
	}

	/**
	 * Description set 上报时间（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_time_report
	 */
	public void setTimeReport(Date timeReport) {
		this.timeReport = timeReport;
	}

	/**
	 * Description 上报内容（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private String reportContent;

	/**
	 * Description get 上报内容（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return String
	 */
	public String getReportContent() {
		return (reportContent == null ? "" : reportContent);
	}

	/**
	 * Description set 上报内容（FOSS传值）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_report_content
	 */
	public void setReportContent(String reportContent) {
		this.reportContent = reportContent;
	}

	/**
	 * Description 返货原因（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private String returnReason;

	/**
	 * Description get 返货原因（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return String
	 */
	public String getReturnReason() {
		return (returnReason == null ? "" : returnReason);
	}

	/**
	 * Description set 返货原因（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_return_reason
	 */
	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	/**
	 * Description 返货类型（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private String returnType;

	/**
	 * Description get 返货类型（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return String
	 */
	public String getReturnType() {
		return (returnType == null ? "" : returnType);
	}

	/**
	 * Description set 返货类型（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_return_type
	 */
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	/**
	 * Description 原单收货省份名称（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private String oriProvince;

	/**
	 * Description get 原单收货省份名称（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return String
	 */
	public String getOriProvince() {
		return (oriProvince == null ? "" : oriProvince);
	}

	/**
	 * Description set 原单收货省份名称（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_ori_province
	 */
	public void setOriProvince(String oriProvince) {
		this.oriProvince = oriProvince;
	}

	/**
	 * Description 原单收货省份ID（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private String oriProvinceId;

	/**
	 * Description get 原单收货省份ID（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return String
	 */
	public String getOriProvinceId() {
		return (oriProvinceId == null ? "" : oriProvinceId);
	}

	/**
	 * Description set 原单收货省份ID（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_ori_province_id
	 */
	public void setOriProvinceId(String oriProvinceId) {
		this.oriProvinceId = oriProvinceId;
	}

	/**
	 * Description 原单收货城市（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private String oriCity;

	/**
	 * Description get 原单收货城市（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return String
	 */
	public String getOriCity() {
		return (oriCity == null ? "" : oriCity);
	}

	/**
	 * Description set 原单收货城市（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_ori_city
	 */
	public void setOriCity(String oriCity) {
		this.oriCity = oriCity;
	}

	/**
	 * Description 原单收货城市ID（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private String oriCityId;

	/**
	 * Description get 原单收货城市ID（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return String
	 */
	public String getOriCityId() {
		return (oriCityId == null ? "" : oriCityId);
	}

	/**
	 * Description set 原单收货城市ID（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_ori_city_id
	 */
	public void setOriCityId(String oriCityId) {
		this.oriCityId = oriCityId;
	}

	/**
	 * Description 原单收货区/县（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private String oriArea;

	/**
	 * Description get 原单收货区/县（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return String
	 */
	public String getOriArea() {
		return (oriArea == null ? "" : oriArea);
	}

	/**
	 * Description set 原单收货区/县（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_ori_area
	 */
	public void setOriArea(String oriArea) {
		this.oriArea = oriArea;
	}

	/**
	 * Description 原单收货区/县ID（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private String oriAreaId;

	/**
	 * Description get 原单收货区/县ID（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return String
	 */
	public String getOriAreaId() {
		return (oriAreaId == null ? "" : oriAreaId);
	}

	/**
	 * Description set 原单收货区/县ID（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_ori_area_id
	 */
	public void setOriAreaId(String oriAreaId) {
		this.oriAreaId = oriAreaId;
	}

	/**
	 * Description 原单收货详细地址（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private String oriDetailaddress;

	/**
	 * Description get 原单收货详细地址（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return String
	 */
	public String getOriDetailaddress() {
		return (oriDetailaddress == null ? "" : oriDetailaddress);
	}

	/**
	 * Description set 原单收货详细地址（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_ori_detailaddress
	 */
	public void setOriDetailaddress(String oriDetailaddress) {
		this.oriDetailaddress = oriDetailaddress;
	}

	/**
	 * Description 代收货款（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private BigDecimal returnMoneyReceive;

	/**
	 * Description get 代收货款（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return Integer
	 */
	public BigDecimal getReturnMoneyReceive() {
		return returnMoneyReceive;
	}

	/**
	 * Description set 代收货款（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_return_money_receive
	 */
	public void setReturnMoneyReceive(BigDecimal returnMoneyReceive) {
		this.returnMoneyReceive = returnMoneyReceive;
	}

	/**
	 * Description 保价（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private BigDecimal returnMoneyInsured;

	/**
	 * Description get 保价（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return Integer
	 */
	public BigDecimal getReturnMoneyInsured() {
		return returnMoneyInsured;
	}

	/**
	 * Description set 保价（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_return_money_insured
	 */
	public void setReturnMoneyInsured(BigDecimal returnMoneyInsured) {
		this.returnMoneyInsured = returnMoneyInsured;
	}

	/**
	 * Description 收货人姓名（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private String returnManReceive;

	/**
	 * Description get 收货人姓名（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return String
	 */
	public String getReturnManReceive() {
		return (returnManReceive == null ? "" : returnManReceive);
	}

	/**
	 * Description set 收货人姓名（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_return_man_receive
	 */
	public void setReturnManReceive(String returnManReceive) {
		this.returnManReceive = returnManReceive;
	}

	/**
	 * Description 收货人手机号码（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private String returnPhoneReceive;

	/**
	 * Description get 收货人手机号码（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return String
	 */
	public String getReturnPhoneReceive() {
		return (returnPhoneReceive == null ? "" : returnPhoneReceive);
	}

	/**
	 * Description set 收货人手机号码（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_return_phone_receive
	 */
	public void setReturnPhoneReceive(String returnPhoneReceive) {
		this.returnPhoneReceive = returnPhoneReceive;
	}

	/**
	 * Description 收货人固定电话（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private String returnTelReceive;

	/**
	 * Description get 收货人固定电话（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return String
	 */
	public String getReturnTelReceive() {
		return (returnTelReceive == null ? "" : returnTelReceive);
	}

	/**
	 * Description set 收货人固定电话（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_return_tel_receive
	 */
	public void setReturnTelReceive(String returnTelReceive) {
		this.returnTelReceive = returnTelReceive;
	}

	/**
	 * Description 收货省份名称（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private String returnProvince;

	/**
	 * Description get 收货省份名称（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return String
	 */
	public String getReturnProvince() {
		return (returnProvince == null ? "" : returnProvince);
	}

	/**
	 * Description set 收货省份名称（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_return_province
	 */
	public void setReturnProvince(String returnProvince) {
		this.returnProvince = returnProvince;
	}

	/**
	 * Description 收货省份ID（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private String returnProvinceId;

	/**
	 * Description get 收货省份ID（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return String
	 */
	public String getReturnProvinceId() {
		return (returnProvinceId == null ? "" : returnProvinceId);
	}

	/**
	 * Description set 收货省份ID（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_return_province_id
	 */
	public void setReturnProvinceId(String returnProvinceId) {
		this.returnProvinceId = returnProvinceId;
	}

	/**
	 * Description 收货城市（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private String returnCity;

	/**
	 * Description get 收货城市（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return String
	 */
	public String getReturnCity() {
		return (returnCity == null ? "" : returnCity);
	}

	/**
	 * Description set 收货城市（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_return_city
	 */
	public void setReturnCity(String returnCity) {
		this.returnCity = returnCity;
	}

	/**
	 * Description 收货城市ID（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private String returnCityId;

	/**
	 * Description get 收货城市ID（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return String
	 */
	public String getReturnCityId() {
		return (returnCityId == null ? "" : returnCityId);
	}

	/**
	 * Description set 收货城市ID（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_return_city_id
	 */
	public void setReturnCityId(String returnCityId) {
		this.returnCityId = returnCityId;
	}

	/**
	 * Description 收货区/县（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private String returnArea;

	/**
	 * Description get 收货区/县（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return String
	 */
	public String getReturnArea() {
		return (returnArea == null ? "" : returnArea);
	}

	/**
	 * Description set 收货区/县（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_return_area
	 */
	public void setReturnArea(String returnArea) {
		this.returnArea = returnArea;
	}

	/**
	 * Description 收货区/县ID（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private String returnAreaId;

	/**
	 * Description get 收货区/县ID（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return String
	 */
	public String getReturnAreaId() {
		return (returnAreaId == null ? "" : returnAreaId);
	}

	/**
	 * Description set 收货区/县ID（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_return_area_id
	 */
	public void setReturnAreaId(String returnAreaId) {
		this.returnAreaId = returnAreaId;
	}

	/**
	 * Description 收货详细地址（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private String returnDetailaddress;

	/**
	 * Description get 收货详细地址（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return String
	 */
	public String getReturnDetailaddress() {
		return (returnDetailaddress == null ? "" : returnDetailaddress);
	}

	/**
	 * Description set 收货详细地址（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_return_detailaddress
	 */
	public void setReturnDetailaddress(String returnDetailaddress) {
		this.returnDetailaddress = returnDetailaddress;
	}

	/**
	 * Description 调查建议（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private String returnSurvey;

	/**
	 * Description get 调查建议（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return String
	 */
	public String getReturnSurvey() {
		return (returnSurvey == null ? "" : returnSurvey);
	}

	/**
	 * Description set 调查建议（返货单信息）
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_return_survey
	 */
	public void setReturnSurvey(String returnSurvey) {
		this.returnSurvey = returnSurvey;
	}

	/**
	 * Description 受理状态
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private String returnStatus;

	/**
	 * Description get 受理状态
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return String
	 */
	public String getReturnStatus() {
		return (returnStatus == null ? "" : returnStatus);
	}

	/**
	 * Description set 受理状态
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_return_status
	 */
	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}

	/**
	 * Description 创建时间
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private Date createTime;

	/**
	 * Description get 创建时间
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return Date
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * Description set 创建时间
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_create_time
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * Description 修改时间
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private Date updateTime;

	/**
	 * Description get 修改时间
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return Date
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * Description set 修改时间
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_update_time
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * Description 创建人名称
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private String createUser;

	/**
	 * Description get 创建人名称
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return String
	 */
	public String getCreateUser() {
		return (createUser == null ? "" : createUser);
	}

	/**
	 * Description set 创建人名称
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_create_user
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
	 * Description 修改人名称
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private String updateUser;

	/**
	 * Description get 修改人名称
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return String
	 */
	public String getUpdateUser() {
		return (updateUser == null ? "" : updateUser);
	}

	/**
	 * Description set 修改人名称
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_update_user
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	/**
	 * Description 创建人工号
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private String createUserCode;

	/**
	 * Description get 创建人工号
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return String
	 */
	public String getCreateUserCode() {
		return (createUserCode == null ? "" : createUserCode);
	}

	/**
	 * Description set 创建人工号
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_create_user_code
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	/**
	 * Description 修改人工号
	 * 
	 * @author 150960
	 * @version 0.1
	 */
	private String updateUserCode;

	/**
	 * Description get 修改人工号
	 * 
	 * @author 150960
	 * @version 0.1
	 * @return String
	 */
	public String getUpdateUserCode() {
		return (updateUserCode == null ? "" : updateUserCode);
	}

	/**
	 * Description set 修改人工号
	 * 
	 * @author 150960
	 * @version 0.1
	 * @param f_update_user_code
	 */
	public void setUpdateUserCode(String updateUserCode) {
		this.updateUserCode = updateUserCode;
	}

	// 创建开始时间
	private Date createStartTime;

	public Date getCreateStartTime() {
		return createStartTime;
	}

	public void setCreateStartTime(Date createStartTime) {
		this.createStartTime = createStartTime;
	}

	// 创建结束时间
	private Date createEndTime;

	public Date getCreateEndTime() {
		return createEndTime;
	}

	public void setCreateEndTime(Date createEndTime) {
		this.createEndTime = createEndTime;
	}

	// /////////////////////

	/**
	 * 订单来源
	 */
	private String orderSource;

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	/**
	 * FOSS ID （FOSS传值）
	 */
	private String fossId;

	/**
	 * @return the fossId
	 */
	public String getFossId() {
		return fossId;
	}

	/**
	 * @param fossId
	 *            the fossId to set
	 */
	public void setFossId(String fossId) {
		this.fossId = fossId;
	}

	/**
	 * 收货地址省份ID（FOSS传值）
	 */
	private String receiveProvinceId;
	/**
	 * 收货地址城市ID（FOSS传值）
	 */
	private String receiveCityId;
	/**
	 * 收货地址区域ID（FOSS传值）
	 */
	private String receiveAreaId;
	/**
	 * 收货地址省份名称（FOSS传值）
	 */
	private String receiveProvince;
	/**
	 * 收货地址城市名称（FOSS传值）
	 */
	private String receiveCity;
	/**
	 * 收货地址区域名称（FOSS传值）
	 */
	private String receiveArea;
	/**
	 * 发货地址省份ID（FOSS传值）
	 */
	private String sendProvinceId;
	/**
	 * 发货地址城市ID（FOSS传值）
	 */
	private String sendCityId;
	/**
	 * 发货地址区域ID（FOSS传值）
	 */
	private String sendAreaId;
	/**
	 * 发货地址省份名称（FOSS传值）
	 */
	private String sendProvince;
	/**
	 * 发货地址城市名称（FOSS传值）
	 */
	private String sendCity;
	/**
	 * 发货地址区域名称（FOSS传值）
	 */
	private String sendArea;

	/**
	 * @return the receiveProvinceId
	 */
	public String getReceiveProvinceId() {
		return receiveProvinceId;
	}

	/**
	 * @param receiveProvinceId
	 *            the receiveProvinceId to set
	 */
	public void setReceiveProvinceId(String receiveProvinceId) {
		this.receiveProvinceId = receiveProvinceId;
	}

	/**
	 * @return the receiveCityId
	 */
	public String getReceiveCityId() {
		return receiveCityId;
	}

	/**
	 * @param receiveCityId
	 *            the receiveCityId to set
	 */
	public void setReceiveCityId(String receiveCityId) {
		this.receiveCityId = receiveCityId;
	}

	/**
	 * @return the receiveAreaId
	 */
	public String getReceiveAreaId() {
		return receiveAreaId;
	}

	/**
	 * @param receiveAreaId
	 *            the receiveAreaId to set
	 */
	public void setReceiveAreaId(String receiveAreaId) {
		this.receiveAreaId = receiveAreaId;
	}

	/**
	 * @return the receiveProvince
	 */
	public String getReceiveProvince() {
		return receiveProvince;
	}

	/**
	 * @param receiveProvince
	 *            the receiveProvince to set
	 */
	public void setReceiveProvince(String receiveProvince) {
		this.receiveProvince = receiveProvince;
	}

	/**
	 * @return the receiveCity
	 */
	public String getReceiveCity() {
		return receiveCity;
	}

	/**
	 * @param receiveCity
	 *            the receiveCity to set
	 */
	public void setReceiveCity(String receiveCity) {
		this.receiveCity = receiveCity;
	}

	/**
	 * @return the receiveArea
	 */
	public String getReceiveArea() {
		return receiveArea;
	}

	/**
	 * @param receiveArea
	 *            the receiveArea to set
	 */
	public void setReceiveArea(String receiveArea) {
		this.receiveArea = receiveArea;
	}

	/**
	 * @return the sendProvinceId
	 */
	public String getSendProvinceId() {
		return sendProvinceId;
	}

	/**
	 * @param sendProvinceId
	 *            the sendProvinceId to set
	 */
	public void setSendProvinceId(String sendProvinceId) {
		this.sendProvinceId = sendProvinceId;
	}

	/**
	 * @return the sendCityId
	 */
	public String getSendCityId() {
		return sendCityId;
	}

	/**
	 * @param sendCityId
	 *            the sendCityId to set
	 */
	public void setSendCityId(String sendCityId) {
		this.sendCityId = sendCityId;
	}

	/**
	 * @return the sendAreaId
	 */
	public String getSendAreaId() {
		return sendAreaId;
	}

	/**
	 * @param sendAreaId
	 *            the sendAreaId to set
	 */
	public void setSendAreaId(String sendAreaId) {
		this.sendAreaId = sendAreaId;
	}

	/**
	 * @return the sendProvince
	 */
	public String getSendProvince() {
		return sendProvince;
	}

	/**
	 * @param sendProvince
	 *            the sendProvince to set
	 */
	public void setSendProvince(String sendProvince) {
		this.sendProvince = sendProvince;
	}

	/**
	 * @return the sendCity
	 */
	public String getSendCity() {
		return sendCity;
	}

	/**
	 * @param sendCity
	 *            the sendCity to set
	 */
	public void setSendCity(String sendCity) {
		this.sendCity = sendCity;
	}

	/**
	 * @return the sendArea
	 */
	public String getSendArea() {
		return sendArea;
	}

	/**
	 * @param sendArea
	 *            the sendArea to set
	 */
	public void setSendArea(String sendArea) {
		this.sendArea = sendArea;
	}
	//上报人部门编码
	private String reportDepartmentCode;

	public String getReportDepartmentCode() {
		return reportDepartmentCode;
	}

	public void setReportDepartmentCode(String reportDepartmentCode) {
		this.reportDepartmentCode = reportDepartmentCode;
	}
	/**
	 * 返货方式
	 * */
	private String returnMode;
	public String getReturnMode() {
		return returnMode;
	}
	public void setReturnMode(String returnMode) {
		this.returnMode = returnMode;
	}
	
	/**
	 * 返货原因明细
	 */
	private String returnDetails;
	/**
	 * 上报通道
	 */
	private String returnChanle;

	 

	public String getReturnDetails() {
		return returnDetails;
	}

	public void setReturnDetails(String returnDetails) {
		this.returnDetails = returnDetails;
	}

	public String getReturnChanle() {
		return returnChanle;
	}

	public void setReturnChanle(String returnChanle) {
		this.returnChanle = returnChanle;
	}
	
}
