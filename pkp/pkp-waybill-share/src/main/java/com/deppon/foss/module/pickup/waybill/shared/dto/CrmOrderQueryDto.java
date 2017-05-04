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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/CrmOrderQueryDto.java
 * 
 * FILE NAME        	: CrmOrderQueryDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * CRM系统订单查询请求实体
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-11-13 下午3:02:31, </p>
 * @author foss-sunrui
 * @date 2012-11-13 下午3:02:31
 * @since
 * @version
 */
public class CrmOrderQueryDto implements Serializable {

    /**
	 * The Constant serialVersionUID.
	 */
    private static final long serialVersionUID = 5272943363427410568L;

    /**
	 * The order type.
	 */
    private String orderType;

    /**
	 * The order number.
	 */
    private String orderNumber;

    /**
	 * The begin time.
	 */
    private Date beginTime;

    /**
	 * The end time.
	 */
    private Date endTime;

    /**
	 * The accept status.
	 */
    private String acceptStatus;

    /**
	 * The shipper cust.
	 */
    private String shipperCust;

    /**
	 * The shipper linkman.
	 */
    private String shipperLinkman;

    /**
	 * The shipper mobile.
	 */
    private String shipperMobile;

    /**
	 * The shipper phone.
	 */
    private String shipperPhone;
    
    /**
	 * The sales department.
	 */
    private String salesDept;
    
  //运单号
  	private String waybillNumber;


	/**
	 * The page num.
	 */
    private int pageNum;

    /**
	 * The page size.
	 */
    private int pageSize;
    
    
    /*****************  以下为快递业务新增属性    ******************/
    //产品类型
    private String productCode;

	
	/**
	 * @return the orderType .
	 */
	public String getOrderType() {
		return orderType;
	}

	
	/**
	 *@param orderType the orderType to set.
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	
	/**
	 * @return the orderNumber .
	 */
	public String getOrderNumber() {
		return orderNumber;
	}

	
	/**
	 *@param orderNumber the orderNumber to set.
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	
	/**
	 * @return the beginTime .
	 */
	public Date getBeginTime() {
		return beginTime;
	}

	
	/**
	 *@param beginTime the beginTime to set.
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	
	/**
	 * @return the endTime .
	 */
	public Date getEndTime() {
		return endTime;
	}

	
	/**
	 *@param endTime the endTime to set.
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	
	/**
	 * @return the acceptStatus .
	 */
	public String getAcceptStatus() {
		return acceptStatus;
	}

	
	/**
	 *@param acceptStatus the acceptStatus to set.
	 */
	public void setAcceptStatus(String acceptStatus) {
		this.acceptStatus = acceptStatus;
	}

	
	/**
	 * @return the shipperCust .
	 */
	public String getShipperCust() {
		return shipperCust;
	}

	
	/**
	 *@param shipperCust the shipperCust to set.
	 */
	public void setShipperCust(String shipperCust) {
		this.shipperCust = shipperCust;
	}

	
	/**
	 * @return the shipperLinkman .
	 */
	public String getShipperLinkman() {
		return shipperLinkman;
	}

	
	/**
	 *@param shipperLinkman the shipperLinkman to set.
	 */
	public void setShipperLinkman(String shipperLinkman) {
		this.shipperLinkman = shipperLinkman;
	}

	
	/**
	 * @return the shipperMobile .
	 */
	public String getShipperMobile() {
		return shipperMobile;
	}

	
	/**
	 *@param shipperMobile the shipperMobile to set.
	 */
	public void setShipperMobile(String shipperMobile) {
		this.shipperMobile = shipperMobile;
	}

	
	/**
	 * @return the shipperPhone .
	 */
	public String getShipperPhone() {
		return shipperPhone;
	}

	
	/**
	 *@param shipperPhone the shipperPhone to set.
	 */
	public void setShipperPhone(String shipperPhone) {
		this.shipperPhone = shipperPhone;
	}

	
	/**
	 * @return the pageNum .
	 */
	public int getPageNum() {
		return pageNum;
	}

	
	/**
	 *@param pageNum the pageNum to set.
	 */
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	
	/**
	 * @return the pageSize .
	 */
	public int getPageSize() {
		return pageSize;
	}

	
	/**
	 *@param pageSize the pageSize to set.
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	/**
	  * @return  the salesDept
	 */
	public String getSalesDept() {
		return salesDept;
	}


	/**
	 * @param salesDept the salesDept to set
	 */
	public void setSalesDept(String salesDept) {
		this.salesDept = salesDept;
	}


	
	public String getProductCode() {
		return productCode;
	}


	
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}


    public String getWaybillNumber() {
		return waybillNumber;
	}


	public void setWaybillNumber(String waybillNumber) {
		this.waybillNumber = waybillNumber;
	}
}