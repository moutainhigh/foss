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
 *  PROJECT NAME  : tfr-package-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/packaging/api/shared/domain/QueryUnpackConditionEntity.java
 *  
 *  FILE NAME          :QueryUnpackConditionEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/**
 * 
 */
package com.deppon.foss.module.transfer.packaging.api.shared.domain;


import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;


/**
 * 查询营业部代打包装的查询条件实体
 * @author 046130-foss-xuduowei
 * @date 2012-10-12 下午6:19:47
 */
public class QueryUnpackConditionEntity extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3892293131035425109L;
	/**
	 * 开单起始时间
	 */
	private Date waybillBeginDate ;
	/**
	 * 开单结束时间
	 */
	private Date waybillEndDate;
	/**
	 * 货物状态
	 */
	private String goodsStatus;
	/**
	 * 代包装部门
	 */
	private String packDept;
	/**
	 * 开单部门编号
	 */
	private String waybillCreateDeptCode;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 货区编号
	 */
	private String areaCode;
	/**
	 * 走货路径状态名称
	 */
	private String statusName;
	/**
	 * 运输类型名称
	 */
	private String transportType;
	/**
	 * 数据字典的激活状态
	 */
	private String dictActive;
	
	/**
	 * 获取 开单起始时间.
	 *
	 * @return the 开单起始时间
	 */

	public Date getWaybillBeginDate() {
		return waybillBeginDate;
	}
	
	/**
	 * 设置 开单起始时间.
	 *
	 * @param waybillBeginDate the new 开单起始时间
	 */
	
	public void setWaybillBeginDate(Date waybillBeginDate) {
		this.waybillBeginDate = waybillBeginDate;
	}
	
	/**
	 * 获取 开单结束时间.
	 *
	 * @return the 开单结束时间
	 */
	
	public Date getWaybillEndDate() {
		return waybillEndDate;
	}
	
	/**
	 * 设置 开单结束时间.
	 *
	 * @param waybillEndDate the new 开单结束时间
	 */
	
	public void setWaybillEndDate(Date waybillEndDate) {
		this.waybillEndDate = waybillEndDate;
	}
	
	/**
	 * 获取 货物状态.
	 *
	 * @return the 货物状态
	 */
	public String getGoodsStatus() {
		return goodsStatus;
	}
	
	/**
	 * 设置 货物状态.
	 *
	 * @param goodsStatus the new 货物状态
	 */
	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}
	
	/**
	 * 获取 代包装部门.
	 *
	 * @return the 代包装部门
	 */
	public String getPackDept() {
		return packDept;
	}
	
	/**
	 * 设置 代包装部门.
	 *
	 * @param packDept the new 代包装部门
	 */
	public void setPackDept(String packDept) {
		this.packDept = packDept;
	}
	
	/**
	 * 获取 货区编号.
	 *
	 * @return the 货区编号
	 */
	public String getAreaCode() {
		return areaCode;
	}
	
	/**
	 * 设置 货区编号.
	 *
	 * @param areaCode the new 货区编号
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	
	/**
	 * 获取 走货路径状态名称.
	 *
	 * @return the 走货路径状态名称
	 */
	public String getStatusName() {
		return statusName;
	}
	
	/**
	 * 设置 走货路径状态名称.
	 *
	 * @param statusName the new 走货路径状态名称
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	/**
	 * 获取 数据字典的激活状态.
	 *
	 * @return the 数据字典的激活状态
	 */
	public String getDictActive() {
		return dictActive;
	}
	
	/**
	 * 设置 数据字典的激活状态.
	 *
	 * @param dictActive the new 数据字典的激活状态
	 */
	public void setDictActive(String dictActive) {
		this.dictActive = dictActive;
	}
	
	/**
	 * 获取 开单部门编号.
	 *
	 * @return the 开单部门编号
	 */
	public String getWaybillCreateDeptCode() {
		return waybillCreateDeptCode;
	}
	
	/**
	 * 设置 开单部门编号.
	 *
	 * @param waybillCreateDeptCode the new 开单部门编号
	 */
	public void setWaybillCreateDeptCode(String waybillCreateDeptCode) {
		this.waybillCreateDeptCode = waybillCreateDeptCode;
	}
	
	
	
	

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * 获取 运输类型名称.
	 *
	 * @return the 运输类型名称
	 */
	public String getTransportType() {
		return transportType;
	}
	
	/**
	 * 设置 运输类型名称.
	 *
	 * @param transportType the new 运输类型名称
	 */
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	
	
	
	
}