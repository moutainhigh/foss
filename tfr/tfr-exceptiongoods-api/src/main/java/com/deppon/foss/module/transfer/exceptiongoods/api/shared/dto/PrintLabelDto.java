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
 *  FILE PATH          :/PrintLabelDto.java
 * 
 *  FILE NAME          :PrintLabelDto.java
 *  
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.exceptiongoods.api.shared.dto;

import java.util.Date;

/**
 * 打印指定标签查询结果实体类
 * @author 097457-foss-wangqiang
 * @date 2012-12-25 下午12:08:20
 */
public class PrintLabelDto implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5029625324390749093L;
	
	/** 运单号*/
	private String waybillNo;
	/** 流水号*/
	private String serialNo;
	/** 关联原流水号*/
	private String originalSerialNo;
	/** 条码标签显示件数*/
	private String labelGoodsQty;
	/** 件数变动事项*/
	private String goodsQtyChanged;
	/** 货物位置*/
	private String goodsPosition;
	/** 打印次数*/
	private Integer printCount;
	/** 操作人code*/
	private String operateCode;
	/**部门code*/
	private String orgCode;
	/**运单状态*/
	private String wayBillStatus;
	/**
	 * id
	 */
	private String id;
	
	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	/**
	* @description 获取操作人工号
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月8日 上午10:59:33
	*/
	public String getOperateCode() {
		return operateCode;
	}


	
	/**
	* @description 设置操作人工号
	* @param operateCode
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月8日 上午10:59:47
	*/
	public void setOperateCode(String operateCode) {
		this.operateCode = operateCode;
	}


	
	/**
	* @description 获取部门code
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月8日 上午10:59:59
	*/
	public String getOrgCode() {
		return orgCode;
	}


	
	/**
	* @description 设置部门code
	* @param orgCode
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月8日 上午11:00:07
	*/
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}


	/**
	 * 打印类型：0:老式打印机 1:针对巴枪扫描打印的
	* @fields printType
	* @author 14022-foss-songjie
	* @update 2015年2月3日 下午5:14:08
	* @version V1.0
	*/
	private int printType;
	
	/**
 * 查询开始日期
	* @fields queryTimeBegin
	* @author 14022-foss-songjie
	* @update 2015年2月4日 下午2:39:21
	* @version V1.0
	*/
	private Date queryTimeBegin;

	/**
	 * 查询截至日期
	* @fields queryTimeEnd
	* @author 14022-foss-songjie
	* @update 2015年2月4日 下午2:39:35
	* @version V1.0
	*/
	private Date queryTimeEnd;
	
	/**
	* @description 打印类型：0:老式打印机 1:针对巴枪扫描打印的
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年2月3日 下午5:21:38
	*/
	public int getPrintType() {
		return printType;
	}
	
	
	/**
	* @description 打印类型：0:老式打印机 1:针对巴枪扫描打印的
	* @param printType
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年2月3日 下午5:21:43
	*/
	public void setPrintType(int printType) {
		this.printType = printType;
	}

	/**	 * 获取 运单号.
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
	 * 获取 流水号.
	 *
	 * @return the 流水号
	 */
	public String getSerialNo() {
		return serialNo;
	}
	
	/**
	 * 设置 流水号.
	 *
	 * @param serialNo the new 流水号
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	
	/**
	 * 获取 关联原流水号.
	 *
	 * @return the 关联原流水号
	 */
	public String getOriginalSerialNo() {
		return originalSerialNo;
	}
	
	/**
	 * 设置 关联原流水号.
	 *
	 * @param originalSerialNo the new 关联原流水号
	 */
	public void setOriginalSerialNo(String originalSerialNo) {
		this.originalSerialNo = originalSerialNo;
	}
	
	/**
	 * 获取 条码标签显示件数.
	 *
	 * @return the 条码标签显示件数
	 */
	public String getLabelGoodsQty() {
		return labelGoodsQty;
	}
	
	/**
	 * 设置 条码标签显示件数.
	 *
	 * @param labelGoodsQty the new 条码标签显示件数
	 */
	public void setLabelGoodsQty(String labelGoodsQty) {
		this.labelGoodsQty = labelGoodsQty;
	}
	
	/**
	 * 获取 件数变动事项.
	 *
	 * @return the 件数变动事项
	 */
	public String getGoodsQtyChanged() {
		return goodsQtyChanged;
	}
	
	/**
	 * 设置 件数变动事项.
	 *
	 * @param goodsQtyChanged the new 件数变动事项
	 */
	public void setGoodsQtyChanged(String goodsQtyChanged) {
		this.goodsQtyChanged = goodsQtyChanged;
	}
	
	/**
	 * 获取 货物位置.
	 *
	 * @return the 货物位置
	 */
	public String getGoodsPosition() {
		return goodsPosition;
	}
	
	/**
	 * 设置 货物位置.
	 *
	 * @param goodsPosition the new 货物位置
	 */
	public void setGoodsPosition(String goodsPosition) {
		this.goodsPosition = goodsPosition;
	}
	
	/**
	 * 获取 打印次数.
	 *
	 * @return the 打印次数
	 */
	public Integer getPrintCount() {
		return printCount;
	}
	
	/**
	 * 设置 打印次数.
	 *
	 * @param printCount the new 打印次数
	 */
	public void setPrintCount(Integer printCount) {
		this.printCount = printCount;
	}

	
	
	

	
	

	
	/**
	* @description 查询开始日期
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年2月4日 下午2:40:52
	*/
	public Date getQueryTimeBegin() {
		return queryTimeBegin;
	}

	
	/**
	* @description 查询开始日期
	* @param queryTimeBegin
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年2月4日 下午2:40:57
	*/
	public void setQueryTimeBegin(Date queryTimeBegin) {
		this.queryTimeBegin = queryTimeBegin;
	}

	
	/**
	* @description 查询截至日期
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年2月4日 下午2:41:03
	*/
	public Date getQueryTimeEnd() {
		return queryTimeEnd;
	}

	
	/**
	* @description 查询截至日期
	* @param queryTimeEnd
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年2月4日 下午2:41:06
	*/
	public void setQueryTimeEnd(Date queryTimeEnd) {
		this.queryTimeEnd = queryTimeEnd;
	}



	
	/**
	* @description 获取运单状态
	* @param wayBillStatus
	* @version 1.0
	* @author 322610-foss-songjl
	* @update 2016年6月20日16:18:47
	*/
	public String getWayBillStatus() {
		return wayBillStatus;
	}



	
	/**
	* @description 获取运单状态
	* @param wayBillStatus
	* @version 1.0
	* @author 322610-foss-songjl
	* @update 2016年6月20日16:18:47
	*/
	public void setWayBillStatus(String wayBillStatus) {
		this.wayBillStatus = wayBillStatus;
	}
	
	
	}