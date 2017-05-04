/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/WaybillListByComplexCondationDto.java
 * 
 * FILE NAME        	: WaybillListByComplexCondationDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 1、收货部门： 运单开单部门编码 2、运输性质： 产品条目编码 3、目的站： 到达网点/简称 4、起始单号： 运单号 5、结束单号： 运单号
 * 6、起始开单日期： 运单开单日期 7、结束开单日期： 运单结束日期 8、发货人： 发货人姓名 9、收货人： 收货人姓名 10、发货人手机： 发货人手机
 * 11、收货人手机： 12、发货人电话 13、收货人电话
 * 
 * @author 026113-foss-linwensheng
 */
public class WaybillListByComplexCondationDto implements Serializable {

	/** 序列. */
	private static final long serialVersionUID = 1L;

	/** 1、收货部门： 运单开单部门编码. */
	private String receiveOrgCode;

	/** 2、运输性质： 产品条目编码. */
	private String productCode;
	
	/**
	 * 多选运输性质 DMANA-4340
	 * @author 218459-foss-dsw
	 */
	private List<String> productCodes;

	/** 目的站： 到达网点/简称. */
	private String targetOrgCode;

	/** 起始单号 */
	private String startWaybillNo;
	/** 结束单号： 运单号 */
	private String endWaybillNo;
	/** 起始开单日期： 运单开单日期. */
	private Date startBillTime;

	/** 结束开单日期： 运单结束日期. */
	private Date endBilltime;

	/** 发货人（（托运人）--传的是ID，前端使用共工组件）： 发货人姓名. */
	private String deliveryCustomerContact;

	/** 收货人： 收货人姓名. (code)*/
	private String receiveCustomerContact;

	/** 发货联系人手机. */
	private String deliveryCustomerMobilephone;

	/** 收货客户手机. */
	private String receiveCustomerMobilephone;

	/** 发货人电话. */
	private String deliveryCustomerPhone;
	/**
	 * 收货人电话.
	 */
	private String receiveCustomerPhone;

	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 派送开始时间
	 */
	private Date beginDeliverTime;
	/**
	 * 派送结束时间
	 */
	private Date endDeliverTime;
	/**
	 * 派送部门(code)
	 */
	private String deliverDept;
	/**
	 * 付款方式
	 */
	private String payMethod;
	/**
	 *  开单部门
	 */
	private String createOrgCode;
	
	/**
	 * 始发配载部门(code)
	 */
	private String loadOrgCode;
	/**
	 * 清单类型
	 */
	private String billType;
	/**
	 * TODO 标识类型（建立数据字典）
	 */
	private String deptType;

	/**
	 * TODO 收货类型（建立数据字典）
	 */
	private String receiveType;
	
	/**
	 * 废除的单号列表(多个单号用逗号隔开)
	 */
	private String deleteWaybillNos;
	
	/**
	 * 排序字段
	 */
	private String sortField;
	
	/**
	 * 排序类型
	 */
	private String sortType;
	
	/**
	 * 客户分群
	 */
	private String flabelleavemonth;

	public String getFlabelleavemonth() {
		return flabelleavemonth;
	}


	public void setFlabelleavemonth(String flabelleavemonth) {
		this.flabelleavemonth = flabelleavemonth;
	}


	public String getDeleteWaybillNos() {
		return deleteWaybillNos;
	}

	
	public void setDeleteWaybillNos(String deleteWaybillNos) {
		this.deleteWaybillNos = deleteWaybillNos;
	}

	/**
	 * @return deliverDept : set the property deliverDept.
	 */
	public String getDeliverDept() {
		return deliverDept;
	}

	/**
	 * @param deliverDept : return the property deliverDept.
	 */
	public void setDeliverDept(String deliverDept) {
		this.deliverDept = deliverDept;
	}

	/**
	 * @return billType : set the property billType.
	 */
	public String getBillType() {
		return billType;
	}

	/**
	 * @param billType : return the property billType.
	 */
	public void setBillType(String billType) {
		this.billType = billType;
	}

	/**
	 * @return deptType : set the property deptType.
	 */
	public String getDeptType() {
		return deptType;
	}

	/**
	 * @param deptType : return the property deptType.
	 */
	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}

	/**
	 * @return loadOrgCode : set the property loadOrgCode.
	 */
	public String getLoadOrgCode() {
		return loadOrgCode;
	}

	/**
	 * @param loadOrgCode : return the property loadOrgCode.
	 */
	public void setLoadOrgCode(String loadOrgCode) {
		this.loadOrgCode = loadOrgCode;
	}

	/**
	 * @return createOrgCode : set the property createOrgCode.
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * @param createOrgCode : return the property createOrgCode.
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	/**
	 * @return payMethod : set the property payMethod.
	 */
	public String getPayMethod() {
		return payMethod;
	}

	/**
	 * @param payMethod : return the property payMethod.
	 */
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	/**
	 * @return waybillNo : set the property waybillNo.
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo
	 *            : return the property waybillNo.
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return beginDeliverTime : set the property beginDeliverTime.
	 */
	public Date getBeginDeliverTime() {
		return beginDeliverTime;
	}

	/**
	 * @param beginDeliverTime
	 *            : return the property beginDeliverTime.
	 */
	public void setBeginDeliverTime(Date beginDeliverTime) {
		this.beginDeliverTime = beginDeliverTime;
	}

	/**
	 * @return endDeliverTime : set the property endDeliverTime.
	 */
	public Date getEndDeliverTime() {
		return endDeliverTime;
	}

	/**
	 * @param endDeliverTime
	 *            : return the property endDeliverTime.
	 */
	public void setEndDeliverTime(Date endDeliverTime) {
		this.endDeliverTime = endDeliverTime;
	}

	/**
	 * @return the startWaybillNo
	 */

	public String getStartWaybillNo() {
		return startWaybillNo;
	}

	/**
	 * @param startWaybillNo
	 *            the startWaybillNo to set
	 */
	public void setStartWaybillNo(String startWaybillNo) {
		this.startWaybillNo = startWaybillNo;
	}

	/**
	 * @return the endWaybillNo
	 */
	public String getEndWaybillNo() {
		return endWaybillNo;
	}

	/**
	 * @param endWaybillNo
	 *            the endWaybillNo to set
	 */
	public void setEndWaybillNo(String endWaybillNo) {
		this.endWaybillNo = endWaybillNo;
	}

	/**
	 * Gets the receive org code.
	 * 
	 * @return the receive org code
	 */
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	/**
	 * Sets the receive org code.
	 * 
	 * @param receiveOrgCode
	 *            the new receive org code
	 */
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	/**
	 * Gets the product code.
	 * 
	 * @return the product code
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * Sets the product code.
	 * 
	 * @param productCode
	 *            the new product code
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * Gets the target org code.
	 * 
	 * @return the target org code
	 */
	public String getTargetOrgCode() {
		return targetOrgCode;
	}

	/**
	 * Sets the target org code.
	 * 
	 * @param targetOrgCode
	 *            the new target org code
	 */
	public void setTargetOrgCode(String targetOrgCode) {
		this.targetOrgCode = targetOrgCode;
	}

	/**
	 * Gets the start bill time.
	 * 
	 * @return the start bill time
	 */
	public Date getStartBillTime() {
		return startBillTime;
	}

	/**
	 * Sets the start bill time.
	 * 
	 * @param startBillTime
	 *            the new start bill time
	 */
	public void setStartBillTime(Date startBillTime) {
		this.startBillTime = startBillTime;
	}

	/**
	 * Gets the end billtime.
	 * 
	 * @return the end billtime
	 */
	public Date getEndBilltime() {
		return endBilltime;
	}

	/**
	 * Sets the end billtime.
	 * 
	 * @param endBilltime
	 *            the new end billtime
	 */
	public void setEndBilltime(Date endBilltime) {
		this.endBilltime = endBilltime;
	}

	/**
	 * Gets the delivery customer contact.
	 * 
	 * @return the delivery customer contact
	 */
	public String getDeliveryCustomerContact() {
		return deliveryCustomerContact;
	}

	/**
	 * Sets the delivery customer contact.
	 * 
	 * @param deliveryCustomerContact
	 *            the new delivery customer contact
	 */
	public void setDeliveryCustomerContact(String deliveryCustomerContact) {
		this.deliveryCustomerContact = deliveryCustomerContact;
	}

	/**
	 * Gets the receive customer contact.
	 * 
	 * @return the receive customer contact
	 */
	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}

	/**
	 * Sets the receive customer contact.
	 * 
	 * @param receiveCustomerContact
	 *            the new receive customer contact
	 */
	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}

	/**
	 * Gets the delivery customer mobilephone.
	 * 
	 * @return the delivery customer mobilephone
	 */
	public String getDeliveryCustomerMobilephone() {
		return deliveryCustomerMobilephone;
	}

	/**
	 * Sets the delivery customer mobilephone.
	 * 
	 * @param deliveryCustomerMobilephone
	 *            the new delivery customer mobilephone
	 */
	public void setDeliveryCustomerMobilephone(
			String deliveryCustomerMobilephone) {
		this.deliveryCustomerMobilephone = deliveryCustomerMobilephone;
	}

	/**
	 * Gets the receive customer mobilephone.
	 * 
	 * @return the receive customer mobilephone
	 */
	public String getReceiveCustomerMobilephone() {
		return receiveCustomerMobilephone;
	}

	/**
	 * Sets the receive customer mobilephone.
	 * 
	 * @param receiveCustomerMobilephone
	 *            the new receive customer mobilephone
	 */
	public void setReceiveCustomerMobilephone(String receiveCustomerMobilephone) {
		this.receiveCustomerMobilephone = receiveCustomerMobilephone;
	}

	/**
	 * Gets the delivery customer phone.
	 * 
	 * @return the delivery customer phone
	 */
	public String getDeliveryCustomerPhone() {
		return deliveryCustomerPhone;
	}

	/**
	 * Sets the delivery customer phone.
	 * 
	 * @param deliveryCustomerPhone
	 *            the new delivery customer phone
	 */
	public void setDeliveryCustomerPhone(String deliveryCustomerPhone) {
		this.deliveryCustomerPhone = deliveryCustomerPhone;
	}

	/**
	 * Gets the receive customer phone.
	 * 
	 * @return the receive customer phone
	 */
	public String getReceiveCustomerPhone() {
		return receiveCustomerPhone;
	}

	/**
	 * Sets the receive customer phone.
	 * 
	 * @param receiveCustomerPhone
	 *            the new receive customer phone
	 */
	public void setReceiveCustomerPhone(String receiveCustomerPhone) {
		this.receiveCustomerPhone = receiveCustomerPhone;
	}

	public String getReceiveType() {
		return receiveType;
	}

	public void setReceiveType(String receiveType) {
		this.receiveType = receiveType;
	}


	
	public String getSortField() {
		return sortField;
	}


	
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}


	
	public String getSortType() {
		return sortType;
	}


	
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}


	public List<String> getProductCodes() {
		return productCodes;
	}


	public void setProductCodes(List<String> productCodes) {
		this.productCodes = productCodes;
	}
	


}