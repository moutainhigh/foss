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
 *  PROJECT NAME  : tfr-common-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/api/shared/dto/OaReportNolabel.java
 *  
 *  FILE NAME          :OaReportNolabel.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-oa-itf
 * PACKAGE NAME: com.deppon.foss.module.transfer.oa.server.domain
 * FILE    NAME: OaReportNolabel.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.common.api.shared.dto;

import java.util.Date;

/**
 * 上报无标签多货实体
 * @author 046130-foss-xuduowei
 * @date 2012-11-16 下午3:44:29
 */
public class OaReportNolabel {
	/**
	 *  品名
	 */
	private String goodsName;
	
	/**
	 *  品牌
	 */
	private String brand;
	
	/**
	 *  品类
	 */
	private String categorys;
	
	/**
	 *  包装
	 */
	private String goodsPacked;
	
	/**
	 *  内物属性
	 */
	private String attributes;
	
	/**
	 *  重量
	 */
	private double weight;
	
	/**
	 *  体积
	 */
	private double volume;
	
	/**
	 *  交接单号
	 */
	private String replayBill;
	
	/**
	 *  上报人工号
	 */
	private String userId;
	
	/**
	 *  包装关键字
	 */
	private String packageKey;
	
	/**
	 *  手写关键字
	 */
	private String handKey;
	
	/**
	 *  同车少货备注
	 */
	private String lessGoodsNote;
	
	/**
	 *  发现货区
	 */
	private String findPlace;
	
	/**
	 *  货区发现时间
	 */
	private Date gafTime;
	
	/**
	 *  上一环节部门
	 */
	private String deptName;
	
	/**
	 *  上一环节部门编号
	 */
	private String deptOrgid;
	
	/**
	 *  短信通知对象
	 */
	private String noticeObjects;
	
	/**
	 *  正面照地址
	 */
	private String imageZm;
	
	/**
	 *  整体照地址
	 */
	private String imageZt;
	
	/**
	 *  内物照地址
	 */
	private String imageNw;
	
	/**
	 *  事件经过
	 */
	private String eventReport;
	
	/**
	 *  无标签运单号
	 */
	private String noLabelWayBill;
	
	/**
	 *  无标签流水号
	 */
	private String noLabelSerail;
	
	/**
	 * 是否同车少货
	 */
	private String isLessGoods;
	
	/**
	 * 时候有交接单
	 */
	private String isReplayBill;
	
	/**
	 * 责任事业部标杆编码
	 */
	
	private String finalSysCode;
	
	/**
	 * 是否为泄漏货
	 */
	private String leakGoods;
	
	/**
	 * 是否为快递货
	 */
	private String expressGoods;
	
	
	/**
	 * @return the leakGoods
	 */
	public String getLeakGoods() {
		return leakGoods;
	}


	/**
	 * @param leakGoods the leakGoods to set
	 */
	public void setLeakGoods(String leakGoods) {
		this.leakGoods = leakGoods;
	}


	/**
	 * @return the expressGoods
	 */
	public String getExpressGoods() {
		return expressGoods;
	}


	/**
	 * @param expressGoods the expressGoods to set
	 */
	public void setExpressGoods(String expressGoods) {
		this.expressGoods = expressGoods;
	}


	/**
	 * 
	 */
	public OaReportNolabel(){
		
	}
	
		
	/**
	 * 获取 责任事业部标杆编码.
	 *
	 * @return the 责任事业部标杆编码
	 */
	public String getFinalSysCode() {
		return finalSysCode;
	}
	
	/**
	 * 设置 责任事业部标杆编码.
	 *
	 * @param finalSysCode the new 责任事业部标杆编码
	 */
	public void setFinalSysCode(String finalSysCode) {
		this.finalSysCode = finalSysCode;
	}
	
	/**
	 * 获取 品名.
	 *
	 * @return the 品名
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * 设置 品名.
	 *
	 * @param goodsName the new 品名
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * 获取 品牌.
	 *
	 * @return the 品牌
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * 设置 品牌.
	 *
	 * @param brand the new 品牌
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * 获取 品类.
	 *
	 * @return the 品类
	 */
	public String getCategorys() {
		return categorys;
	}

	/**
	 * 设置 品类.
	 *
	 * @param categorys the new 品类
	 */
	public void setCategorys(String categorys) {
		this.categorys = categorys;
	}

	/**
	 * 获取 包装.
	 *
	 * @return the 包装
	 */
	public String getGoodsPacked() {
		return goodsPacked;
	}

	/**
	 * 设置 包装.
	 *
	 * @param goodsPacked the new 包装
	 */
	public void setGoodsPacked(String goodsPacked) {
		this.goodsPacked = goodsPacked;
	}

	/**
	 * 获取 内物属性.
	 *
	 * @return the 内物属性
	 */
	public String getAttributes() {
		return attributes;
	}

	/**
	 * 设置 内物属性.
	 *
	 * @param attributes the new 内物属性
	 */
	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	/**
	 * 获取 重量.
	 *
	 * @return the 重量
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * 设置 重量.
	 *
	 * @param weight the new 重量
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * 获取 体积.
	 *
	 * @return the 体积
	 */
	public double getVolume() {
		return volume;
	}

	/**
	 * 设置 体积.
	 *
	 * @param volume the new 体积
	 */
	public void setVolume(double volume) {
		this.volume = volume;
	}

	/**
	 * 获取 交接单号.
	 *
	 * @return the 交接单号
	 */
	public String getReplayBill() {
		return replayBill;
	}

	/**
	 * 设置 交接单号.
	 *
	 * @param replayBill the new 交接单号
	 */
	public void setReplayBill(String replayBill) {
		this.replayBill = replayBill;
	}

	/**
	 * 获取 上报人工号.
	 *
	 * @return the 上报人工号
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 设置 上报人工号.
	 *
	 * @param userId the new 上报人工号
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 获取 包装关键字.
	 *
	 * @return the 包装关键字
	 */
	public String getPackageKey() {
		return packageKey;
	}

	/**
	 * 设置 包装关键字.
	 *
	 * @param packageKey the new 包装关键字
	 */
	public void setPackageKey(String packageKey) {
		this.packageKey = packageKey;
	}

	/**
	 * 获取 手写关键字.
	 *
	 * @return the 手写关键字
	 */
	public String getHandKey() {
		return handKey;
	}

	/**
	 * 设置 手写关键字.
	 *
	 * @param handKey the new 手写关键字
	 */
	public void setHandKey(String handKey) {
		this.handKey = handKey;
	}

	/**
	 * 获取 同车少货备注.
	 *
	 * @return the 同车少货备注
	 */
	public String getLessGoodsNote() {
		return lessGoodsNote;
	}

	/**
	 * 设置 同车少货备注.
	 *
	 * @param lessGoodsNote the new 同车少货备注
	 */
	public void setLessGoodsNote(String lessGoodsNote) {
		this.lessGoodsNote = lessGoodsNote;
	}

	/**
	 * 获取 发现货区.
	 *
	 * @return the 发现货区
	 */
	public String getFindPlace() {
		return findPlace;
	}

	/**
	 * 设置 发现货区.
	 *
	 * @param findPlace the new 发现货区
	 */
	public void setFindPlace(String findPlace) {
		this.findPlace = findPlace;
	}

	/**
	 * 获取 货区发现时间.
	 *
	 * @return the 货区发现时间
	 */
	public Date getGafTime() {
		return gafTime;
	}

	/**
	 * 设置 货区发现时间.
	 *
	 * @param gafTime the new 货区发现时间
	 */
	public void setGafTime(Date gafTime) {
		this.gafTime = gafTime;
	}

	/**
	 * 获取 上一环节部门.
	 *
	 * @return the 上一环节部门
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * 设置 上一环节部门.
	 *
	 * @param deptName the new 上一环节部门
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * 获取 上一环节部门编号.
	 *
	 * @return the 上一环节部门编号
	 */
	public String getDeptOrgid() {
		return deptOrgid;
	}

	/**
	 * 设置 上一环节部门编号.
	 *
	 * @param deptOrgid the new 上一环节部门编号
	 */
	public void setDeptOrgid(String deptOrgid) {
		this.deptOrgid = deptOrgid;
	}

	/**
	 * 获取 短信通知对象.
	 *
	 * @return the 短信通知对象
	 */
	public String getNoticeObjects() {
		return noticeObjects;
	}

	/**
	 * 设置 短信通知对象.
	 *
	 * @param noticeObjects the new 短信通知对象
	 */
	public void setNoticeObjects(String noticeObjects) {
		this.noticeObjects = noticeObjects;
	}

	/**
	 * 获取 正面照地址.
	 *
	 * @return the 正面照地址
	 */
	public String getImageZm() {
		return imageZm;
	}

	/**
	 * 设置 正面照地址.
	 *
	 * @param imageZm the new 正面照地址
	 */
	public void setImageZm(String imageZm) {
		this.imageZm = imageZm;
	}

	/**
	 * 获取 整体照地址.
	 *
	 * @return the 整体照地址
	 */
	public String getImageZt() {
		return imageZt;
	}

	/**
	 * 设置 整体照地址.
	 *
	 * @param imageZt the new 整体照地址
	 */
	public void setImageZt(String imageZt) {
		this.imageZt = imageZt;
	}

	/**
	 * 获取 内物照地址.
	 *
	 * @return the 内物照地址
	 */
	public String getImageNw() {
		return imageNw;
	}

	/**
	 * 设置 内物照地址.
	 *
	 * @param imageNw the new 内物照地址
	 */
	public void setImageNw(String imageNw) {
		this.imageNw = imageNw;
	}

	/**
	 * 获取 事件经过.
	 *
	 * @return the 事件经过
	 */
	public String getEventReport() {
		return eventReport;
	}

	/**
	 * 设置 事件经过.
	 *
	 * @param eventReport the new 事件经过
	 */
	public void setEventReport(String eventReport) {
		this.eventReport = eventReport;
	}

	/**
	 * 获取 无标签运单号.
	 *
	 * @return the 无标签运单号
	 */
	public String getNoLabelWayBill() {
		return noLabelWayBill;
	}

	/**
	 * 设置 无标签运单号.
	 *
	 * @param noLabelWayBill the new 无标签运单号
	 */
	public void setNoLabelWayBill(String noLabelWayBill) {
		this.noLabelWayBill = noLabelWayBill;
	}

	/**
	 * 获取 无标签流水号.
	 *
	 * @return the 无标签流水号
	 */
	public String getNoLabelSerail() {
		return noLabelSerail;
	}

	/**
	 * 设置 无标签流水号.
	 *
	 * @param noLabelSerail the new 无标签流水号
	 */
	public void setNoLabelSerail(String noLabelSerail) {
		this.noLabelSerail = noLabelSerail;
	}

	/**
	 * 获取 是否同车少货.
	 *
	 * @return the 是否同车少货
	 */
	public String getIsLessGoods() {
		return isLessGoods;
	}

	/**
	 * 设置 是否同车少货.
	 *
	 * @param isLessGoods the new 是否同车少货
	 */
	public void setIsLessGoods(String isLessGoods) {
		this.isLessGoods = isLessGoods;
	}

	/**
	 * 获取 时候有交接单.
	 *
	 * @return the 时候有交接单
	 */
	public String getIsReplayBill() {
		return isReplayBill;
	}

	/**
	 * 设置 时候有交接单.
	 *
	 * @param isReplayBill the new 时候有交接单
	 */
	public void setIsReplayBill(String isReplayBill) {
		this.isReplayBill = isReplayBill;
	}
		
		
}