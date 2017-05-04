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
 *  FILE PATH          :/NoLabelGoodsEntity.java
 * 
 *  FILE NAME          :NoLabelGoodsEntity.java
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

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;
/**
 * 无标签多货实体
 * @author 097457-foss-wangqiang
 * @date 2012-12-25 下午12:05:41
 */
public class NoLabelGoodsEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5170871248014757767L;
	
	/** 无标签单号*/
	private String noLabelBillNo;
	/** 无标签流水号*/
    private String noLabelSerialNo;
    /** OA差错编号*/
    private String oaErrorNo;
    /** 品名*/
    private String goodsName;
    /** 包装*/
    private String packageType;
    /** 交接单号*/
    private String handoverNo;
    /** 车次号*/
    private String vehicleNo;
    /** 件数*/
    private Integer goodsQty;
    /** 找货状态*/
    private String findGoodsStatus;
    /** 上一环节部门编号*/
    private String previousOrgCode;
    /** 上一环节部门名称*/
    private String previousOrgName;
    /** 品牌*/
    private String goodsBrand;
    /** 品类*/
    private String goodsType;
    /** 包装关键字*/
    private String packageKeyword;
    /** 内物属性*/
    private String goodsProperty;
    /** 手写关键字*/
    private String handwritingKeyword;
    /** 同车少货备注*/
    private String lossGoodsNotes;
    /** 事件经过*/
    private String eventProcess;
    /** 短信通知对象*/
    private String noteNotifyPerson;
   
    /** 发现时间*/
    private Date findTime;
    /** 无标签发现类型*/
    private String findType;
    /** 发现人工号*/
    private String findUserCode;
    /** 发现人姓名*/
    private String findUserName;
    /** 发现货区编号*/
    private String goodsAreaCode;
    /** 发现货区名称*/
    private String goodsAreaName;
    /** 发现部门编号*/
    private String findOrgCode;
    /** 发现部门名称*/
    private String findOrgName;
    
    
    /** 正面照路径*/
    private String frontPhoto;
    /** 整体照路径*/
    private String entiretyPhoto;
    /** 内物照路径*/
    private String goodsPhoto;
    /** 附加照片A路径*/
    private String goodsPhotoA;
    /** 附加照片B路径*/
    private String goodsPhotoB;
    
    /** 正面照文件名*/
    private String frontPhotoName;
    /** 整体照文件名*/
    private String entiretyPhotoName;
    /** 内物照文件名*/
    private String goodsPhotoName;
    /** 附加照片A文件名*/
    private String goodsPhotoAName;
    /** 附加照片B文件名*/
    private String goodsPhotoBName;
    
    
    /** 重量*/
    private BigDecimal weight;
    /** 体积*/
    private BigDecimal volume;
    /** 长宽高 */
    private String volumeLWH;
    
    /** 创建人编号*/
    private String createUserCode;
    /** 创建人名称*/
    private String createUserName;
    
    /** 是否已打印无标签*/
    private String isPrintNoLabel;
    /** 打印无标签操作人编号*/
    private String printUserCode;
    /** 打印无标签操作人姓名*/
    private String printUserName;
    /** 打印无标签时间*/
    private Date printTime;
   
    
    /** 是否上报OA*/
    private String isReportOa;
    /** 上报OA时间*/
    private Date reportOATime;
    /** 上报OA操作人编号*/
    private String reportOAUserCode;
    /** 上报OA操作人姓名*/
    private String reportOAUserName;
    
    /** 原运单号*/
    private String originalWaybillNo;
    /** 原流水号*/
    private String originalSerialNo;
    
    /** 是否已打印原标签*/
    private String isPrintOriginalLabel;
    /** 打印原标签操作人姓名*/
    private String printOriginalLabelUserName;
    /** 打印原标签时间*/
    private Date printOriginalLabelTime;
    
    /** 库存状态*/
    private String stockStatus;
    /** 入库时间*/
    private String inStockTime;
    /** 库存状态*/
    private String inStockDeviceType;
    /** 库存状态*/
    private String inStockUserName;
    
    /** 开始时间*/
    private Date beginTime;
    /** 结束时间*/
    private Date endTime;
    
    //是否为泄漏货
    private String leakGoods;
    
    //是否为快递货
    private String expressGoods;
    
    //无标签多货状态
    private String goodsStatus;
    
	public String getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

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
	 * 获取 无标签单号.
	 *
	 * @return the 无标签单号
	 */
	public String getNoLabelBillNo() {
		return noLabelBillNo;
	}
	
	/**
	 * 设置 无标签单号.
	 *
	 * @param noLabelBillNo the new 无标签单号
	 */
	public void setNoLabelBillNo(String noLabelBillNo) {
		this.noLabelBillNo = noLabelBillNo;
	}
	
	/**
	 * 获取 无标签流水号.
	 *
	 * @return the 无标签流水号
	 */
	public String getNoLabelSerialNo() {
		return noLabelSerialNo;
	}
	
	/**
	 * 设置 无标签流水号.
	 *
	 * @param noLabelSerialNo the new 无标签流水号
	 */
	public void setNoLabelSerialNo(String noLabelSerialNo) {
		this.noLabelSerialNo = noLabelSerialNo;
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
	 * @param oaErrorNo the new oA差错编号
	 */
	public void setOaErrorNo(String oaErrorNo) {
		this.oaErrorNo = oaErrorNo;
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
	 * 获取 包装.
	 *
	 * @return the 包装
	 */
	public String getPackageType() {
		return packageType;
	}
	
	/**
	 * 设置 包装.
	 *
	 * @param packageType the new 包装
	 */
	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}
	
	/**
	 * 获取 交接单号.
	 *
	 * @return the 交接单号
	 */
	public String getHandoverNo() {
		return handoverNo;
	}
	
	/**
	 * 设置 交接单号.
	 *
	 * @param handoverNo the new 交接单号
	 */
	public void setHandoverNo(String handoverNo) {
		this.handoverNo = handoverNo;
	}
	
	/**
	 * 获取 车次号.
	 *
	 * @return the 车次号
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	
	/**
	 * 设置 车次号.
	 *
	 * @param vehicleNo the new 车次号
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	
	/**
	 * 获取 件数.
	 *
	 * @return the 件数
	 */
	public Integer getGoodsQty() {
		return goodsQty;
	}
	
	/**
	 * 设置 件数.
	 *
	 * @param goodsQty the new 件数
	 */
	public void setGoodsQty(Integer goodsQty) {
		this.goodsQty = goodsQty;
	}
	
	/**
	 * 获取 重量.
	 *
	 * @return the 重量
	 */
	public BigDecimal getWeight() {
		return weight;
	}
	
	/**
	 * 设置 重量.
	 *
	 * @param weight the new 重量
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	
	/**
	 * 获取 体积.
	 *
	 * @return the 体积
	 */
	public BigDecimal getVolume() {
		return volume;
	}
	
	/**
	 * 设置 体积.
	 *
	 * @param volume the new 体积
	 */
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	
	/**
	 * 获取 找货状态.
	 *
	 * @return the 找货状态
	 */
	public String getFindGoodsStatus() {
		return findGoodsStatus;
	}
	
	/**
	 * 设置 找货状态.
	 *
	 * @param findGoodsStatus the new 找货状态
	 */
	public void setFindGoodsStatus(String findGoodsStatus) {
		this.findGoodsStatus = findGoodsStatus;
	}
	
	/**
	 * 获取 库存状态.
	 *
	 * @return the 库存状态
	 */
	public String getStockStatus() {
		return stockStatus;
	}
	
	/**
	 * 设置 库存状态.
	 *
	 * @param stockStatus the new 库存状态
	 */
	public void setStockStatus(String stockStatus) {
		this.stockStatus = stockStatus;
	}
	
	/**
	 * 获取 是否上报OA.
	 *
	 * @return the 是否上报OA
	 */
	public String getIsReportOa() {
		return isReportOa;
	}
	
	/**
	 * 设置 是否上报OA.
	 *
	 * @param isReportOa the new 是否上报OA
	 */
	public void setIsReportOa(String isReportOa) {
		this.isReportOa = isReportOa;
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
	 * @param findTime the new 发现时间
	 */
	public void setFindTime(Date findTime) {
		this.findTime = findTime;
	}
	
	/**
	 * 获取 上一环节部门编号.
	 *
	 * @return the 上一环节部门编号
	 */
	public String getPreviousOrgCode() {
		return previousOrgCode;
	}
	
	/**
	 * 设置 上一环节部门编号.
	 *
	 * @param previousOrgCode the new 上一环节部门编号
	 */
	public void setPreviousOrgCode(String previousOrgCode) {
		this.previousOrgCode = previousOrgCode;
	}
	
	/**
	 * 获取 无标签发现类型.
	 *
	 * @return the 无标签发现类型
	 */
	public String getFindType() {
		return findType;
	}
	
	/**
	 * 设置 无标签发现类型.
	 *
	 * @param findType the new 无标签发现类型
	 */
	public void setFindType(String findType) {
		this.findType = findType;
	}
	
	/**
	 * 获取 发现人工号.
	 *
	 * @return the 发现人工号
	 */
	public String getFindUserCode() {
		return findUserCode;
	}
	
	/**
	 * 设置 发现人工号.
	 *
	 * @param findUserCode the new 发现人工号
	 */
	public void setFindUserCode(String findUserCode) {
		this.findUserCode = findUserCode;
	}
	
	/**
	 * 获取 发现人姓名.
	 *
	 * @return the 发现人姓名
	 */
	public String getFindUserName() {
		return findUserName;
	}
	
	/**
	 * 设置 发现人姓名.
	 *
	 * @param findUserName the new 发现人姓名
	 */
	public void setFindUserName(String findUserName) {
		this.findUserName = findUserName;
	}
	
	/**
	 * 获取 发现货区编号.
	 *
	 * @return the 发现货区编号
	 */
	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}
	
	/**
	 * 设置 发现货区编号.
	 *
	 * @param goodsAreaCode the new 发现货区编号
	 */
	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}
	
	/**
	 * 获取 发现货区名称.
	 *
	 * @return the 发现货区名称
	 */
	public String getGoodsAreaName() {
		return goodsAreaName;
	}
	
	/**
	 * 设置 发现货区名称.
	 *
	 * @param goodsAreaName the new 发现货区名称
	 */
	public void setGoodsAreaName(String goodsAreaName) {
		this.goodsAreaName = goodsAreaName;
	}
	
	/**
	 * 获取 品牌.
	 *
	 * @return the 品牌
	 */
	public String getGoodsBrand() {
		return goodsBrand;
	}
	
	/**
	 * 设置 品牌.
	 *
	 * @param goodsBrand the new 品牌
	 */
	public void setGoodsBrand(String goodsBrand) {
		this.goodsBrand = goodsBrand;
	}
	
	/**
	 * 获取 品类.
	 *
	 * @return the 品类
	 */
	public String getGoodsType() {
		return goodsType;
	}
	
	/**
	 * 设置 品类.
	 *
	 * @param goodsType the new 品类
	 */
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	
	/**
	 * 获取 包装关键字.
	 *
	 * @return the 包装关键字
	 */
	public String getPackageKeyword() {
		return packageKeyword;
	}
	
	/**
	 * 设置 包装关键字.
	 *
	 * @param packageKeyword the new 包装关键字
	 */
	public void setPackageKeyword(String packageKeyword) {
		this.packageKeyword = packageKeyword;
	}
	
	/**
	 * 获取 内物属性.
	 *
	 * @return the 内物属性
	 */
	public String getGoodsProperty() {
		return goodsProperty;
	}
	
	/**
	 * 设置 内物属性.
	 *
	 * @param goodsProperty the new 内物属性
	 */
	public void setGoodsProperty(String goodsProperty) {
		this.goodsProperty = goodsProperty;
	}
	
	/**
	 * 获取 手写关键字.
	 *
	 * @return the 手写关键字
	 */
	public String getHandwritingKeyword() {
		return handwritingKeyword;
	}
	
	/**
	 * 设置 手写关键字.
	 *
	 * @param handwritingKeyword the new 手写关键字
	 */
	public void setHandwritingKeyword(String handwritingKeyword) {
		this.handwritingKeyword = handwritingKeyword;
	}
	
	/**
	 * 获取 同车少货备注.
	 *
	 * @return the 同车少货备注
	 */
	public String getLossGoodsNotes() {
		return lossGoodsNotes;
	}
	
	/**
	 * 设置 同车少货备注.
	 *
	 * @param lossGoodsNotes the new 同车少货备注
	 */
	public void setLossGoodsNotes(String lossGoodsNotes) {
		this.lossGoodsNotes = lossGoodsNotes;
	}
	
	/**
	 * 获取 事件经过.
	 *
	 * @return the 事件经过
	 */
	public String getEventProcess() {
		return eventProcess;
	}
	
	/**
	 * 设置 事件经过.
	 *
	 * @param eventProcess the new 事件经过
	 */
	public void setEventProcess(String eventProcess) {
		this.eventProcess = eventProcess;
	}
	
	/**
	 * 获取 短信通知对象.
	 *
	 * @return the 短信通知对象
	 */
	public String getNoteNotifyPerson() {
		return noteNotifyPerson;
	}
	
	/**
	 * 设置 短信通知对象.
	 *
	 * @param noteNotifyPerson the new 短信通知对象
	 */
	public void setNoteNotifyPerson(String noteNotifyPerson) {
		this.noteNotifyPerson = noteNotifyPerson;
	}
	
	/**
	 * 获取 正面照.
	 *
	 * @return the 正面照
	 */
	public String getFrontPhoto() {
		return frontPhoto;
	}
	
	/**
	 * 设置 正面照.
	 *
	 * @param frontPhoto the new 正面照
	 */
	public void setFrontPhoto(String frontPhoto) {
		this.frontPhoto = frontPhoto;
	}
	
	/**
	 * 获取 整体照.
	 *
	 * @return the 整体照
	 */
	public String getEntiretyPhoto() {
		return entiretyPhoto;
	}
	
	/**
	 * 设置 整体照.
	 *
	 * @param entiretyPhoto the new 整体照
	 */
	public void setEntiretyPhoto(String entiretyPhoto) {
		this.entiretyPhoto = entiretyPhoto;
	}
	
	/**
	 * 获取 内物照.
	 *
	 * @return the 内物照
	 */
	public String getGoodsPhoto() {
		return goodsPhoto;
	}
	
	/**
	 * 设置 内物照.
	 *
	 * @param goodsPhoto the new 内物照
	 */
	public void setGoodsPhoto(String goodsPhoto) {
		this.goodsPhoto = goodsPhoto;
	}
	
	/**
	 * 获取 原运单号.
	 *
	 * @return the 原运单号
	 */
	public String getOriginalWaybillNo() {
		return originalWaybillNo;
	}
	
	/**
	 * 设置 原运单号.
	 *
	 * @param originalWaybillNo the new 原运单号
	 */
	public void setOriginalWaybillNo(String originalWaybillNo) {
		this.originalWaybillNo = originalWaybillNo;
	}
	
	/**
	 * 获取 原流水号.
	 *
	 * @return the 原流水号
	 */
	public String getOriginalSerialNo() {
		return originalSerialNo;
	}
	
	/**
	 * 设置 原流水号.
	 *
	 * @param originalSerialNo the new 原流水号
	 */
	public void setOriginalSerialNo(String originalSerialNo) {
		this.originalSerialNo = originalSerialNo;
	}
	
	/**
	 * 获取 附加照片A.
	 *
	 * @return the 附加照片A
	 */
	public String getGoodsPhotoA() {
		return goodsPhotoA;
	}
	
	/**
	 * 设置 附加照片A.
	 *
	 * @param goodsPhotoA the new 附加照片A
	 */
	public void setGoodsPhotoA(String goodsPhotoA) {
		this.goodsPhotoA = goodsPhotoA;
	}
	
	/**
	 * 获取 附加照片B.
	 *
	 * @return the 附加照片B
	 */
	public String getGoodsPhotoB() {
		return goodsPhotoB;
	}
	
	/**
	 * 设置 附加照片B.
	 *
	 * @param goodsPhotoB the new 附加照片B
	 */
	public void setGoodsPhotoB(String goodsPhotoB) {
		this.goodsPhotoB = goodsPhotoB;
	}
	
	/**
	 * 获取 是否已打印无标签.
	 *
	 * @return the 是否已打印无标签
	 */
	public String getIsPrintNoLabel() {
		return isPrintNoLabel;
	}
	
	/**
	 * 设置 是否已打印无标签.
	 *
	 * @param isPrintNoLabel the new 是否已打印无标签
	 */
	public void setIsPrintNoLabel(String isPrintNoLabel) {
		this.isPrintNoLabel = isPrintNoLabel;
	}
	
	/**
	 * 获取 是否已打印原标签.
	 *
	 * @return the 是否已打印原标签
	 */
	public String getIsPrintOriginalLabel() {
		return isPrintOriginalLabel;
	}
	
	/**
	 * 设置 是否已打印原标签.
	 *
	 * @param isPrintOriginalLabel the new 是否已打印原标签
	 */
	public void setIsPrintOriginalLabel(String isPrintOriginalLabel) {
		this.isPrintOriginalLabel = isPrintOriginalLabel;
	}
	
	/**
	 * 获取 开始时间.
	 *
	 * @return the 开始时间
	 */
	@DateFormat
	public Date getBeginTime() {
		return beginTime;
	}
	
	/**
	 * 设置 开始时间.
	 *
	 * @param beginTime the new 开始时间
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
	 * @param endTime the new 结束时间
	 */
	@DateFormat
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	/**
	 * 获取 长宽高.
	 *
	 * @return the 长宽高
	 */
	public String getVolumeLWH() {
		return volumeLWH;
	}
	
	/**
	 * 设置 长宽高.
	 *
	 * @param volumeLWH the new 长宽高
	 */
	public void setVolumeLWH(String volumeLWH) {
		this.volumeLWH = volumeLWH;
	}
	
	/**
	 * 获取 创建人编号.
	 *
	 * @return the 创建人编号
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}
	
	/**
	 * 设置 创建人编号.
	 *
	 * @param createUserCode the new 创建人编号
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	
	/**
	 * 获取 创建人名称.
	 *
	 * @return the 创建人名称
	 */
	public String getCreateUserName() {
		return createUserName;
	}
	
	/**
	 * 设置 创建人名称.
	 *
	 * @param createUserName the new 创建人名称
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
	/**
	 * 获取 打印无标签操作人编号.
	 *
	 * @return the 打印无标签操作人编号
	 */
	public String getPrintUserCode() {
		return printUserCode;
	}
	
	/**
	 * 设置 打印无标签操作人编号.
	 *
	 * @param printUserCode the new 打印无标签操作人编号
	 */
	public void setPrintUserCode(String printUserCode) {
		this.printUserCode = printUserCode;
	}
	
	/**
	 * 获取 打印无标签操作人姓名.
	 *
	 * @return the 打印无标签操作人姓名
	 */
	public String getPrintUserName() {
		return printUserName;
	}
	
	/**
	 * 设置 打印无标签操作人姓名.
	 *
	 * @param printUserName the new 打印无标签操作人姓名
	 */
	public void setPrintUserName(String printUserName) {
		this.printUserName = printUserName;
	}
	
	/**
	 * 获取 打印无标签时间.
	 *
	 * @return the 打印无标签时间
	 */
	public Date getPrintTime() {
		return printTime;
	}
	
	/**
	 * 设置 打印无标签时间.
	 *
	 * @param printTime the new 打印无标签时间
	 */
	public void setPrintTime(Date printTime) {
		this.printTime = printTime;
	}
	
	/**
	 * 获取 上报OA时间.
	 *
	 * @return the 上报OA时间
	 */
	public Date getReportOATime() {
		return reportOATime;
	}
	
	/**
	 * 设置 上报OA时间.
	 *
	 * @param reportOATime the new 上报OA时间
	 */
	public void setReportOATime(Date reportOATime) {
		this.reportOATime = reportOATime;
	}
	
	/**
	 * 获取 上报OA操作人编号.
	 *
	 * @return the 上报OA操作人编号
	 */
	public String getReportOAUserCode() {
		return reportOAUserCode;
	}
	
	/**
	 * 设置 上报OA操作人编号.
	 *
	 * @param reportOAUserCode the new 上报OA操作人编号
	 */
	public void setReportOAUserCode(String reportOAUserCode) {
		this.reportOAUserCode = reportOAUserCode;
	}
	
	/**
	 * 获取 上报OA操作人姓名.
	 *
	 * @return the 上报OA操作人姓名
	 */
	public String getReportOAUserName() {
		return reportOAUserName;
	}
	
	/**
	 * 设置 上报OA操作人姓名.
	 *
	 * @param reportOAUserName the new 上报OA操作人姓名
	 */
	public void setReportOAUserName(String reportOAUserName) {
		this.reportOAUserName = reportOAUserName;
	}
	
	/**
	 * 获取 上一环节部门名称.
	 *
	 * @return the 上一环节部门名称
	 */
	public String getPreviousOrgName() {
		return previousOrgName;
	}
	
	/**
	 * 设置 上一环节部门名称.
	 *
	 * @param previousOrgName the new 上一环节部门名称
	 */
	public void setPreviousOrgName(String previousOrgName) {
		this.previousOrgName = previousOrgName;
	}
	
	/**
	 * 获取 打印原标签操作人姓名.
	 *
	 * @return the 打印原标签操作人姓名
	 */
	public String getPrintOriginalLabelUserName() {
		return printOriginalLabelUserName;
	}
	
	/**
	 * 设置 打印原标签操作人姓名.
	 *
	 * @param printOriginalLabelUserName the new 打印原标签操作人姓名
	 */
	public void setPrintOriginalLabelUserName(String printOriginalLabelUserName) {
		this.printOriginalLabelUserName = printOriginalLabelUserName;
	}
	
	/**
	 * 获取 打印原标签时间.
	 *
	 * @return the 打印原标签时间
	 */
	public Date getPrintOriginalLabelTime() {
		return printOriginalLabelTime;
	}
	
	/**
	 * 设置 打印原标签时间.
	 *
	 * @param printOriginalLabelTime the new 打印原标签时间
	 */
	public void setPrintOriginalLabelTime(Date printOriginalLabelTime) {
		this.printOriginalLabelTime = printOriginalLabelTime;
	}
	
	/**
	 * 获取 入库时间.
	 *
	 * @return the 入库时间
	 */
	public String getInStockTime() {
		return inStockTime;
	}
	
	/**
	 * 设置 入库时间.
	 *
	 * @param inStockTime the new 入库时间
	 */
	public void setInStockTime(String inStockTime) {
		this.inStockTime = inStockTime;
	}
	
	/**
	 * 获取 库存状态.
	 *
	 * @return the 库存状态
	 */
	public String getInStockDeviceType() {
		return inStockDeviceType;
	}
	
	/**
	 * 设置 库存状态.
	 *
	 * @param inStockDeviceType the new 库存状态
	 */
	public void setInStockDeviceType(String inStockDeviceType) {
		this.inStockDeviceType = inStockDeviceType;
	}
	
	/**
	 * 获取 库存状态.
	 *
	 * @return the 库存状态
	 */
	public String getInStockUserName() {
		return inStockUserName;
	}
	
	/**
	 * 设置 库存状态.
	 *
	 * @param inStockUserName the new 库存状态
	 */
	public void setInStockUserName(String inStockUserName) {
		this.inStockUserName = inStockUserName;
	}
	
	/**
	 * 获取 发现部门编号.
	 *
	 * @return the 发现部门编号
	 */
	public String getFindOrgCode() {
		return findOrgCode;
	}
	
	/**
	 * 设置 发现部门编号.
	 *
	 * @param findOrgCode the new 发现部门编号
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
	 * @param findOrgName the new 发现部门名称
	 */
	public void setFindOrgName(String findOrgName) {
		this.findOrgName = findOrgName;
	}

	/**
	 * 获取 正面照文件名.
	 *
	 * @return the 正面照文件名
	 */
	public String getFrontPhotoName() {
		return frontPhotoName;
	}

	/**
	 * 设置 正面照文件名.
	 *
	 * @param frontPhotoName the new 正面照文件名
	 */
	public void setFrontPhotoName(String frontPhotoName) {
		this.frontPhotoName = frontPhotoName;
	}

	/**
	 * 获取 整体照文件名.
	 *
	 * @return the 整体照文件名
	 */
	public String getEntiretyPhotoName() {
		return entiretyPhotoName;
	}

	/**
	 * 设置 整体照文件名.
	 *
	 * @param entiretyPhotoName the new 整体照文件名
	 */
	public void setEntiretyPhotoName(String entiretyPhotoName) {
		this.entiretyPhotoName = entiretyPhotoName;
	}

	/**
	 * 获取 内物照文件名.
	 *
	 * @return the 内物照文件名
	 */
	public String getGoodsPhotoName() {
		return goodsPhotoName;
	}

	/**
	 * 设置 内物照文件名.
	 *
	 * @param goodsPhotoName the new 内物照文件名
	 */
	public void setGoodsPhotoName(String goodsPhotoName) {
		this.goodsPhotoName = goodsPhotoName;
	}

	/**
	 * 获取 附加照片A文件名.
	 *
	 * @return the 附加照片A文件名
	 */
	public String getGoodsPhotoAName() {
		return goodsPhotoAName;
	}

	/**
	 * 设置 附加照片A文件名.
	 *
	 * @param goodsPhotoAName the new 附加照片A文件名
	 */
	public void setGoodsPhotoAName(String goodsPhotoAName) {
		this.goodsPhotoAName = goodsPhotoAName;
	}

	/**
	 * 获取 附加照片B文件名.
	 *
	 * @return the 附加照片B文件名
	 */
	public String getGoodsPhotoBName() {
		return goodsPhotoBName;
	}

	/**
	 * 设置 附加照片B文件名.
	 *
	 * @param goodsPhotoBName the new 附加照片B文件名
	 */
	public void setGoodsPhotoBName(String goodsPhotoBName) {
		this.goodsPhotoBName = goodsPhotoBName;
	}
	
}