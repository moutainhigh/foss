package com.deppon.foss.module.pickup.waybill.shared.request;

import java.io.Serializable;
import java.util.List;

public class QueryOmsOrderRequest implements Serializable {
	
	
	// 订单类型 ok
	private String orderType;

	// 订单号 ok
	private String orderNumber;
	// 开始时间 ok
	private String beginTime;
	// 结束时间 ok
	private String endTime;
	// 受理状态 orderStatus
	private String acceptStatus;
	// 发货客户名字 shipperName
	// private String shipperCust;
	private String shipperName;
	// 发货客户联系人 contactName
	// private String shipperLinkman;
	private String contactName;
	// 发货客户手机 contactMobile
	// private String shipperMobile;
	private String contactMobile;
	// 发货客户电话 contactPhone
	// private String shipperPhone;
	private String contactPhone;
	// 操作部门的标杆编码 deptCode
	// private String salesDept;
	private String deptCode;
	// 运单号 ok
	private String waybillNumber;
	// 分页查询，第几页 currentPage
	// private int pageNum;
	private int currentPage;
	// 分页查询，每页显示条数 ok
	private int pageSize;

	protected List<String> transType;

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getAcceptStatus() {
		return acceptStatus;
	}

	public void setAcceptStatus(String acceptStatus) {
		this.acceptStatus = acceptStatus;
	}

	public String getShipperName() {
		return shipperName;
	}

	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getWaybillNumber() {
		return waybillNumber;
	}

	public void setWaybillNumber(String waybillNumber) {
		this.waybillNumber = waybillNumber;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<String> getTransType() {
		return transType;
	}

	public void setTransType(List<String> transType) {
		this.transType = transType;
	}
}
