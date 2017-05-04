/**
 *  initial comments.
 */

/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-airfreight-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/airfreight/api/shared/vo/SaleBookingSpaceVo.java
 *  
 *  FILE NAME          :SaleBookingSpaceVo.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.shared.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.SaleBookingSpaceDto;
/**
 * 营业部订舱管理VO
 * @author 038300-foss-pengzhen
 * @date 2012-11-2 下午4:49:40
 */
public class SaleBookingSpaceVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9155972642823027399L;
	/**订舱id*/
	private String id;
	/**申请部门编码*/
	private String applyOrgCode;
	/**申请部门名称*/
	private String applyOrgName;
	/**受理部门编码*/
	private String acceptOrgCode;
	/**受理部门名称*/
	private String acceptOrgName;
	/**受理状态*/
	private String acceptState;
	/**申请开始时间*/
	private Date createTimeFrom;
	/**申请结束时间*/
	private Date createTimeTo;
	/**目的城市 */
	private String arrvRegionName;
	/**目的城市编码*/
	private String arrvRegionCode;
	/**受理备注*/
	private String acceptNotes;
	/**受理类型:同意或拒绝*/
	private boolean acceptType;
	/**营业部订舱列表*/
	private List<SaleBookingSpaceDto> bookingList;
	/**营业部订舱信息dto*/
	private SaleBookingSpaceDto dto;
	/**受理id列表*/
	private List<String> acceptIds;
	/**未受理总条数*/
	private Long noAcceptCount;
	/**运单号*/
	private String waybillNo;
	
	/**
	 * 获取 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * 设置 运单号.
	 *
	 * @param waybillNo the new 运单号
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * 获取 申请部门名称.
	 *
	 * @return the 申请部门名称
	 */
	public String getApplyOrgName() {
		return applyOrgName;
	}
	
	/**
	 * 获取 营业部订舱列表.
	 *
	 * @return the 营业部订舱列表
	 */
	public List<SaleBookingSpaceDto> getBookingList() {
		return bookingList;
	}
	
	/**
	 * 设置 营业部订舱列表.
	 *
	 * @param bookingList the new 营业部订舱列表
	 */
	public void setBookingList(List<SaleBookingSpaceDto> bookingList) {
		this.bookingList = bookingList;
	}
	
	/**
	 * 设置 申请部门名称.
	 *
	 * @param applyOrgName the new 申请部门名称
	 */
	public void setApplyOrgName(String applyOrgName) {
		this.applyOrgName = applyOrgName;
	}
	
	/**
	 * 获取 受理部门名称.
	 *
	 * @return the 受理部门名称
	 */
	public String getAcceptOrgName() {
		return acceptOrgName;
	}
	
	/**
	 * 设置 受理部门名称.
	 *
	 * @param acceptOrgName the new 受理部门名称
	 */
	public void setAcceptOrgName(String acceptOrgName) {
		this.acceptOrgName = acceptOrgName;
	}
	
	/**
	 * 获取 受理状态.
	 *
	 * @return the 受理状态
	 */
	public String getAcceptState() {
		return acceptState;
	}
	
	/**
	 * 设置 受理状态.
	 *
	 * @param acceptState the new 受理状态
	 */
	public void setAcceptState(String acceptState) {
		this.acceptState = acceptState;
	}
	
	/**
	 * 获取 申请开始时间.
	 *
	 * @return the 申请开始时间
	 */
	@DateFormat
	public Date getCreateTimeFrom() {
		return createTimeFrom;
	}
	
	/**
	 * 设置 申请开始时间.
	 *
	 * @param createTimeFrom the new 申请开始时间
	 */
	@DateFormat
	public void setCreateTimeFrom(Date createTimeFrom) {
		this.createTimeFrom = createTimeFrom;
	}
	
	/**
	 * 获取 申请结束时间.
	 *
	 * @return the 申请结束时间
	 */
	@DateFormat
	public Date getCreateTimeTo() {
		return createTimeTo;
	}
	
	/**
	 * 设置 申请结束时间.
	 *
	 * @param createTimeTo the new 申请结束时间
	 */
	@DateFormat
	public void setCreateTimeTo(Date createTimeTo) {
		this.createTimeTo = createTimeTo;
	}
	
	/**
	 * 获取 目的城市.
	 *
	 * @return the 目的城市
	 */
	public String getArrvRegionName() {
		return arrvRegionName;
	}
	
	/**
	 * 设置 目的城市.
	 *
	 * @param arrvRegionName the new 目的城市
	 */
	public void setArrvRegionName(String arrvRegionName) {
		this.arrvRegionName = arrvRegionName;
	}
	
	/**
	 * 获取 受理备注.
	 *
	 * @return the 受理备注
	 */
	public String getAcceptNotes() {
		return acceptNotes;
	}
	
	/**
	 * 设置 受理备注.
	 *
	 * @param acceptNotes the new 受理备注
	 */
	public void setAcceptNotes(String acceptNotes) {
		this.acceptNotes = acceptNotes;
	}
	
	/**
	 * 获取 营业部订舱信息dto.
	 *
	 * @return the 营业部订舱信息dto
	 */
	public SaleBookingSpaceDto getDto() {
		return dto;
	}
	
	/**
	 * 设置 营业部订舱信息dto.
	 *
	 * @param dto the new 营业部订舱信息dto
	 */
	public void setDto(SaleBookingSpaceDto dto) {
		this.dto = dto;
	}
	
	/**
	 * 判断是否 受理类型:同意或拒绝.
	 *
	 * @return the 受理类型:同意或拒绝
	 */
	public boolean isAcceptType() {
		return acceptType;
	}
	
	/**
	 * 设置 受理类型:同意或拒绝.
	 *
	 * @param acceptType the new 受理类型:同意或拒绝
	 */
	public void setAcceptType(boolean acceptType) {
		this.acceptType = acceptType;
	}
	
	/**
	 * 获取 受理id列表.
	 *
	 * @return the 受理id列表
	 */
	public List<String> getAcceptIds() {
		return acceptIds;
	}
	
	/**
	 * 设置 受理id列表.
	 *
	 * @param acceptIds the new 受理id列表
	 */
	public void setAcceptIds(List<String> acceptIds) {
		this.acceptIds = acceptIds;
	}
	
	/**
	 * 获取 订舱id.
	 *
	 * @return the 订舱id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * 设置 订舱id.
	 *
	 * @param id the new 订舱id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 获取 未受理总条数.
	 *
	 * @return the 未受理总条数
	 */
	public Long getNoAcceptCount() {
		return noAcceptCount;
	}
	
	/**
	 * 设置 未受理总条数.
	 *
	 * @param noAcceptCount the new 未受理总条数
	 */
	public void setNoAcceptCount(Long noAcceptCount) {
		this.noAcceptCount = noAcceptCount;
	}
	
	/**
	 * 获取 申请部门编码.
	 *
	 * @return the 申请部门编码
	 */
	public String getApplyOrgCode() {
		return applyOrgCode;
	}
	
	/**
	 * 设置 申请部门编码.
	 *
	 * @param applyOrgCode the new 申请部门编码
	 */
	public void setApplyOrgCode(String applyOrgCode) {
		this.applyOrgCode = applyOrgCode;
	}
	
	/**
	 * 获取 受理部门编码.
	 *
	 * @return the 受理部门编码
	 */
	public String getAcceptOrgCode() {
		return acceptOrgCode;
	}
	
	/**
	 * 设置 受理部门编码.
	 *
	 * @param acceptOrgCode the new 受理部门编码
	 */
	public void setAcceptOrgCode(String acceptOrgCode) {
		this.acceptOrgCode = acceptOrgCode;
	}
	
	/**
	 * 获取 目的城市编码.
	 *
	 * @return the 目的城市编码
	 */
	public String getArrvRegionCode() {
		return arrvRegionCode;
	}
	
	/**
	 * 设置 目的城市编码.
	 *
	 * @param arrvRegionCode the new 目的城市编码
	 */
	public void setArrvRegionCode(String arrvRegionCode) {
		this.arrvRegionCode = arrvRegionCode;
	}
}