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
 *  PROJECT NAME  : tfr-exceptiongoods-api
 *  
 * package_name  : 
 *  
 *  FILE PATH          :/ContrabandGoodsEntity.java
 * 
 *  FILE NAME          :ContrabandGoodsEntity.java
 *  
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;

/**
 * 违禁品实体
 * 
 * @author 097457-foss-wangqiang
 * @date 2012-12-25 下午12:05:20
 */
public class ContrabandGoodsEntity extends BaseEntity {

	private static final long serialVersionUID = 1406296354480889788L;
	/** 运单号 */
	private String waybillNo;
	/** OA差错编号 */
	private String oaErrorNo;
	/** 发现时间 */
	private Date findTime;
	/** 移交状态 */
	private String handoverStatus;
	/** 发现部门 */
	private String findOrgCode;
	/** 发现部门名称 */
	private String findOrgName;
	/** 移交部门 */
	private String handoverOrgCode;
	/** 移交部门名称 */
	private String handoverOrgName;
	/** 开单部门编号 */
	private String createBillOrgCode;
	/** 开单部门名称 */
	private String createBillOrgName;
	/** 运输性质编号 */
	private String productCode;
	/** 运输性质名称 */
	private String productName;
	/** 起始时间 */
	private Date beginTime;
	/** 结束时间 */
	private Date endTime;
	/** 处理结果 */
	private String processResult;
	/** 差错状态 */
	private int exceptionStatus;
	/** 差错结果 */
	private int exceptionResult;
	/** 货物名称 */
	private String goodsName;

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
	 * @param waybillNo
	 *            the new 运单号
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * 获取 oA差错编号.
	 * 
	 * @return the oA差错编号
	 */
	public String getOaErrorNo() {
		return oaErrorNo;
	}

	/**
	 * 设置 oA差错编号.
	 * 
	 * @param oaErrorNo
	 *            the new oA差错编号
	 */
	public void setOaErrorNo(String oaErrorNo) {
		this.oaErrorNo = oaErrorNo;
	}

	/**
	 * 获取 发现时间.
	 * 
	 * @return the 发现时间
	 */
	public Date getFindTime() {
		return findTime;
	}

	/**
	 * 设置 发现时间.
	 * 
	 * @param findTime
	 *            the new 发现时间
	 */
	public void setFindTime(Date findTime) {
		this.findTime = findTime;
	}

	/**
	 * 获取 移交状态.
	 * 
	 * @return the 移交状态
	 */
	public String getHandoverStatus() {
		return handoverStatus;
	}

	/**
	 * 设置 移交状态.
	 * 
	 * @param handoverStatus
	 *            the new 移交状态
	 */
	public void setHandoverStatus(String handoverStatus) {
		this.handoverStatus = handoverStatus;
	}

	/**
	 * 获取 发现部门.
	 * 
	 * @return the 发现部门
	 */
	public String getFindOrgCode() {
		return findOrgCode;
	}

	/**
	 * 设置 发现部门.
	 * 
	 * @param findOrgCode
	 *            the new 发现部门
	 */
	public void setFindOrgCode(String findOrgCode) {
		this.findOrgCode = findOrgCode;
	}

	/**
	 * 获取 发现部门名称.
	 * 
	 * @return the 发现部门名称
	 */
	public String getFindOrgName() {
		return findOrgName;
	}

	/**
	 * 设置 发现部门名称.
	 * 
	 * @param findOrgName
	 *            the new 发现部门名称
	 */
	public void setFindOrgName(String findOrgName) {
		this.findOrgName = findOrgName;
	}

	/**
	 * 获取 移交部门.
	 * 
	 * @return the 移交部门
	 */
	public String getHandoverOrgCode() {
		return handoverOrgCode;
	}

	/**
	 * 设置 移交部门.
	 * 
	 * @param handoverOrgCode
	 *            the new 移交部门
	 */
	public void setHandoverOrgCode(String handoverOrgCode) {
		this.handoverOrgCode = handoverOrgCode;
	}

	/**
	 * 获取 移交部门名称.
	 * 
	 * @return the 移交部门名称
	 */
	public String getHandoverOrgName() {
		return handoverOrgName;
	}

	/**
	 * 设置 移交部门名称.
	 * 
	 * @param handoverOrgName
	 *            the new 移交部门名称
	 */
	public void setHandoverOrgName(String handoverOrgName) {
		this.handoverOrgName = handoverOrgName;
	}

	/**
	 * 获取 起始时间.
	 * 
	 * @return the 起始时间
	 */
	@DateFormat
	public Date getBeginTime() {
		return beginTime;
	}

	/**
	 * 设置 起始时间.
	 * 
	 * @param beginTime
	 *            the new 起始时间
	 */
	@DateFormat
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * 获取 结束时间.
	 * 
	 * @return the 结束时间
	 */
	@DateFormat
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * 设置 结束时间.
	 * 
	 * @param endTime
	 *            the new 结束时间
	 */
	@DateFormat
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * 获取 处理结果.
	 * 
	 * @return the 处理结果
	 */
	public String getProcessResult() {
		return processResult;
	}

	/**
	 * 设置 处理结果.
	 * 
	 * @param processResult
	 *            the new 处理结果
	 */
	public void setProcessResult(String processResult) {
		this.processResult = processResult;
	}

	/**
	 * 获取 开单部门编号.
	 * 
	 * @return the 开单部门编号
	 */
	public String getCreateBillOrgCode() {
		return createBillOrgCode;
	}

	/**
	 * 设置 开单部门编号.
	 * 
	 * @param createBillOrgCode
	 *            the new 开单部门编号
	 */
	public void setCreateBillOrgCode(String createBillOrgCode) {
		this.createBillOrgCode = createBillOrgCode;
	}

	/**
	 * 获取 开单部门名称.
	 * 
	 * @return the 开单部门名称
	 */
	public String getCreateBillOrgName() {
		return createBillOrgName;
	}

	/**
	 * 设置 开单部门名称.
	 * 
	 * @param createBillOrgName
	 *            the new 开单部门名称
	 */
	public void setCreateBillOrgName(String createBillOrgName) {
		this.createBillOrgName = createBillOrgName;
	}

	/**
	 * 获取 运输性质编号.
	 * 
	 * @return the 运输性质编号
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * 设置 运输性质编号.
	 * 
	 * @param productCode
	 *            the new 运输性质编号
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * 获取 运输性质名称.
	 * 
	 * @return the 运输性质名称
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * 设置 运输性质名称.
	 * 
	 * @param productName
	 *            the new 运输性质名称
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * 获取 差错状态.
	 * 
	 * @return the 差错状态
	 */
	public int getExceptionStatus() {
		return exceptionStatus;
	}

	/**
	 * 设置 差错状态.
	 * 
	 * @param exceptionStatus
	 *            the new 差错状态
	 */
	public void setExceptionStatus(int exceptionStatus) {
		this.exceptionStatus = exceptionStatus;
	}

	/**
	 * 获取 差错结果.
	 * 
	 * @return the 差错结果
	 */
	public int getExceptionResult() {
		return exceptionResult;
	}

	/**
	 * 设置 差错结果.
	 * 
	 * @param exceptionResult
	 *            the new 差错结果
	 */
	public void setExceptionResult(int exceptionResult) {
		this.exceptionResult = exceptionResult;
	}

	/**
	 * 获取 货物名称.
	 * 
	 * @return the 货物名称
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * 设置 货物名称.
	 * 
	 * @param goodsName
	 *            the new 货物名称
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

}