/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 038590-foss-wanghui
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
 * PROJECT NAME	: pkp-order-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/shared/dto/DispatchOrderDto.java
 * 
 * FILE NAME        	: DispatchOrderDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * 
 * @ClassName: ExpressOrderDto 
 * @Description: 快递订单DTO 
 * @author YANGBIN
 * @date 2014-5-13 下午2:11:10 
 *
 */
public class ExpressOrderDto implements Serializable {

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/
	private static final long serialVersionUID = 1L;
	// 产品编码
	private String productCode;
	// 试点城市编码
	private String cityCode;
	// 试点城市有效起始时间
	private Date startTime;
	// 试点城市有效截止时间
	private Date endTime;
	// 查询条数
	private int rownum;
	//jobid号
	private String jobId;
	
	//运输性质集合 10.14
	private List<String> productCodes;
	
	
	public List<String> getProductCodes() {
		return productCodes;
	}
	public void setProductCodes(List<String> productCodes) {
		this.productCodes = productCodes;
	}
	
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getRownum() {
		return rownum;
	}
	public void setRownum(int rownum) {
		this.rownum = rownum;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
}