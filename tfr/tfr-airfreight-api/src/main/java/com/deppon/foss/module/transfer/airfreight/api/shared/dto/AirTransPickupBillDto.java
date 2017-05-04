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
 *  package_name  : 
 *  
 *  FILE PATH          :/AirTransPickupBillDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirChangeInventoryDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirChangeInventoryEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirRevisebillDetailEntity;

/**
 * 制作中转提货清单dto
 * @author 099197-foss-zhoudejun
 * @date 2012-11-12 下午1:54:33
 */
public class AirTransPickupBillDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4733007977305928866L;
	/**
	 * 中转单号
	 */
	private String airTransferPickupbillNo;
	/**
	 * 航空公司二字码
	 */
	private String airLineTwoletter;
	/**
	 * 正单号
	 */
	private String airWaybillNo;
		
	/**
	 * 提交状态
	 */
	private String status;
	
	/**
	 * 当前制单人
	 */
	private String createUserName;
	/**
	 * 当前制单时间
	 */
	private Date createTime;
	/**
	 * 运单号
	 */
	private String waybillNo;
	/**
	 * 航空正单ID
	 */
	private String airWaybillId;
	/**
	 * 目的站代码
	 */
	private String arrvRegionCode;
	/**
	 * 目的站
	 */
	private String arrvRegionName;
	/**
	 * 到达网点
	 */
	private String deptOrgName;
	/**
	 * 是否上传edi
	 */
	private String isNotUploadToEdiFlag;
	/**
	 * 部门编码
	 */
	private String deptCode;
	/**
	 * 空运总调(录入合票清单的部门)
	 */
	private String origOrgCode;
	/**
	 * 制单开始时间
	 */
	private Date beginInTime;
	/**
	 * 制单结束时间
	 */
	private Date endInTime;
	/**
	 * 到达网点编码
	 */
	private String destOrgCode;
	/**
	 * 到达网点名称
	 */
	private String destOrgName;
	/**
	 * 航空公司名称
	 */
	private String airLineName;
	/**
	 * 航班号
	 */
	private String flightNo;
	/**
	 * 航班日期
	 */
	private Date flightDate;
	
	/**
	 * 中转标记
	 */
	private String beTransfer;
	/**
	 * 合大票清单实体
	 */
	private AirPickupbillEntity airPickupbillEntity;
	/**
	 * 中装合大票明细List
	 */
	private List<AirChangeInventoryEntity> airChangeInventoryList;
	/**
	 * 合大票明细日志
	 */
	private List<AirRevisebillDetailEntity> airRevisebillDetailList;
	/**
	 * 合大票明细LIST
	 */
	private List<AirPickupbillDetailEntity> airPickupbillDetailList;
	/**
	 * 变更List
	 */
	private List<AirChangeInventoryDetailEntity> airChangeInventoryDetailList;
	/**
	 * 是否存在
	 */
	private boolean inexistence;
	
	/**
	 * 中转提货清单主键id
	 */
	private String AirTransferPickUpBillId;
	
	/**
	 * 获取 中转单号.
	 * @return the 中转单号
	 */
	public String getAirTransferPickupbillNo() {
		return airTransferPickupbillNo;
	}
	
	/**
	 * 设置 中转单号.
	 * @param airTransferPickupbillNo the new 中转单号
	 */
	public void setAirTransferPickupbillNo(String airTransferPickupbillNo) {
		this.airTransferPickupbillNo = airTransferPickupbillNo;
	}
	
	/**
	 * 获取 航空公司二字码.
	 * @return the 航空公司二字码
	 */
	public String getAirLineTwoletter() {
		return airLineTwoletter;
	}
	
	/**
	 * 设置 航空公司二字码.
	 * @param airLineTwoletter the new 航空公司二字码
	 */
	public void setAirLineTwoletter(String airLineTwoletter) {
		this.airLineTwoletter = airLineTwoletter;
	}
	
	/**
	 * 获取 正单号.
	 * @return the 正单号
	 */
	public String getAirWaybillNo() {
		return airWaybillNo;
	}
	
	/**
	 * 设置 正单号.
	 * @param airWaybillNo the new 正单号
	 */
	public void setAirWaybillNo(String airWaybillNo) {
		this.airWaybillNo = airWaybillNo;
	}
	
	/**
	 * 获取 当前制单人.
	 * @return the 当前制单人
	 */
	public String getCreateUserName() {
		if(createUserName==null){
			return FossUserContext.getCurrentInfo().getUserName();
		}else{
			return createUserName;
		}
	}
	
	/**
	 * 设置 当前制单人.
	 * @param createUserName the new 当前制单人
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
	/**
	 * 获取 当前制单时间.
	 * @return the 当前制单时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	
	/**
	 * 设置 当前制单时间.
	 * @param createTime the new 当前制单时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * 获取 运单号.
	 * @return the 运单号
	 */
	public String getWaybillNo() {
		return waybillNo;
	}
	
	/**
	 * 设置 运单号.
	 * @param waybillNo the new 运单号
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	
	/**
	 * 获取 航空正单ID.
	 * @return the 航空正单ID
	 */
	public String getAirWaybillId() {
		return airWaybillId;
	}
	
	/**
	 * 设置 航空正单ID.
	 * @param airWaybillId the new 航空正单ID
	 */
	public void setAirWaybillId(String airWaybillId) {
		this.airWaybillId = airWaybillId;
	}
	/**
	 * 获取 目的站代码.
	 * @return the 目的站代码
	 */
	public String getArrvRegionCode() {
		return arrvRegionCode;
	}
	
	/**
	 * 设置 目的站代码.
	 * @param arrvRegionName the new 目的站代码
	 */
	public void setArrvRegionCode(String arrvRegionCode) {
		this.arrvRegionCode = arrvRegionCode;
	}
	/**
	 * 获取 目的站.
	 * @return the 目的站
	 */
	public String getArrvRegionName() {
		return arrvRegionName;
	}
	
	/**
	 * 设置 目的站.
	 * @param arrvRegionName the new 目的站
	 */
	public void setArrvRegionName(String arrvRegionName) {
		this.arrvRegionName = arrvRegionName;
	}
	
	/**
	 * 获取 到达网点.
	 * @return the 到达网点
	 */
	public String getDeptOrgName() {
		return deptOrgName;
	}
	
	/**
	 * 设置 到达网点.
	 * @param deptOrgName the new 到达网点
	 */
	public void setDeptOrgName(String deptOrgName) {
		this.deptOrgName = deptOrgName;
	}
	
	/**
	 * 获取 是否上传edi.
	 * @return the 是否上传edi
	 */
	public String getIsNotUploadToEdiFlag() {
		return isNotUploadToEdiFlag;
	}
	
	/**
	 * 设置 是否上传edi.
	 * @param isNotUploadToEdiFlag the new 是否上传edi
	 */
	public void setIsNotUploadToEdiFlag(String isNotUploadToEdiFlag) {
		this.isNotUploadToEdiFlag = isNotUploadToEdiFlag;
	}
	
	/**
	 * 获取 部门编码.
	 * @return the 部门编码
	 */
	public String getDeptCode() {
		return deptCode;
	}
	
	/**
	 * 设置 部门编码.
	 * @param deptCode the new 部门编码
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	
	/**
	 * 获取 空运总调(录入合票清单的部门).
	 * @return the 空运总调(录入合票清单的部门)
	 */
	public String getOrigOrgCode() {
		return origOrgCode;
	}
	
	/**
	 * 设置 空运总调(录入合票清单的部门).
	 * @param origOrgCode the new 空运总调(录入合票清单的部门)
	 */
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}
	
	/**
	 * 获取 制单开始时间.
	 * @return the 制单开始时间
	 */
	public Date getBeginInTime() {
		return beginInTime;
	}
	
	/**
	 * 设置 制单开始时间.
	 * @param beginInTime the new 制单开始时间
	 */
	public void setBeginInTime(Date beginInTime) {
		this.beginInTime = beginInTime;
	}
	
	/**
	 * 获取 制单结束时间.
	 * @return the 制单结束时间
	 */
	public Date getEndInTime() {
		return endInTime;
	}
	
	/**
	 * 设置 制单结束时间.
	 * @param endInTime the new 制单结束时间
	 */
	public void setEndInTime(Date endInTime) {
		this.endInTime = endInTime;
	}
	/**
	 * 获取 到达网点编码
	 * @return the 到达网点名编码
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}
	
	/**
	 * 设置 到达网点编码.
	 * @param destOrgCode the new 到达网点编码
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}
	
	/**
	 * 获取 到达网点名称.
	 * @return the 到达网点名称
	 */
	public String getDestOrgName() {
		return destOrgName;
	}
	
	/**
	 * 设置 到达网点名称.
	 * @param destOrgName the new 到达网点名称
	 */
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}
	
	/**
	 * 获取 航空公司名称.
	 * @return the 航空公司名称
	 */
	public String getAirLineName() {
		return airLineName;
	}
	
	/**
	 * 设置 航空公司名称.
	 * @param airLineName the new 航空公司名称
	 */
	public void setAirLineName(String airLineName) {
		this.airLineName = airLineName;
	}
	
	/**
	 * 获取 合大票清单实体.
	 * @return the 合大票清单实体
	 */
	public AirPickupbillEntity getAirPickupbillEntity() {
		return airPickupbillEntity;
	}
	
	/**
	 * 设置 合大票清单实体.
	 * @param airPickupbillEntity the new 合大票清单实体
	 */
	public void setAirPickupbillEntity(AirPickupbillEntity airPickupbillEntity) {
		this.airPickupbillEntity = airPickupbillEntity;
	}
	
	/**
	 * @return 
	 */
	public List<AirPickupbillDetailEntity> getAirPickupbillDetailList() {
		return airPickupbillDetailList;
	}
	
	/**
	 * @param airPickupbillDetailList 
	 */
	public void setAirPickupbillDetailList(
			List<AirPickupbillDetailEntity> airPickupbillDetailList) {
		this.airPickupbillDetailList = airPickupbillDetailList;
	}
	
	/**
	 * 获取 中装合大票明细List.
	 * @return the 中装合大票明细List
	 */
	public List<AirChangeInventoryEntity> getAirChangeInventoryList() {
		return airChangeInventoryList;
	}
	
	/**
	 * 设置 中装合大票明细List.
	 * @param airChangeInventoryList the new 中装合大票明细List
	 */
	public void setAirChangeInventoryList(
			List<AirChangeInventoryEntity> airChangeInventoryList) {
		this.airChangeInventoryList = airChangeInventoryList;
	}
	
	/**
	 * 获取 合大票明细日志.
	 * @return the 合大票明细日志
	 */
	public List<AirRevisebillDetailEntity> getAirRevisebillDetailList() {
		return airRevisebillDetailList;
	}
	
	/**
	 * 设置 合大票明细日志.
	 * @param airRevisebillDetailList the new 合大票明细日志
	 */
	public void setAirRevisebillDetailList(
			List<AirRevisebillDetailEntity> airRevisebillDetailList) {
		this.airRevisebillDetailList = airRevisebillDetailList;
	}
	
	/**
	 * @return 
	 */
	public List<AirChangeInventoryDetailEntity> getAirChangeInventoryDetailList() {
		return airChangeInventoryDetailList;
	}
	
	/**
	 * @param airChangeInventoryDetailList 
	 */
	public void setAirChangeInventoryDetailList(
			List<AirChangeInventoryDetailEntity> airChangeInventoryDetailList) {
		this.airChangeInventoryDetailList = airChangeInventoryDetailList;
	}
	
	/**
	 * @return 
	 */
	public boolean isInexistence() {
		return inexistence;
	}
	
	/**
	 * @param inexistence 
	 */
	public void setInexistence(boolean inexistence) {
		this.inexistence = inexistence;
	}
	
	/**
	 * 获取 航班号.
	 * @return the 航班号
	 */
	public String getFlightNo() {
		return flightNo;
	}
	
	/**
	 * 设置 航班号.
	 * @param flightNo the new 航班号
	 */
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	
	/**
	 * 获取 航班日期.
	 * @return the 航班日期
	 */
	public Date getFlightDate() {
		return flightDate;
	}
	
	/**
	 * 设置 航班日期.
	 * @param flightDate the new 航班日期
	 */
	@DateFormat
	public void setFlightDate(Date flightDate) {
		this.flightDate = flightDate;
	}
	
	/**
	 * @return 
	 */
	public String getAirTransferPickUpBillId() {
		return AirTransferPickUpBillId;
	}
	
	/**
	 * @param airTransferPickUpBillId 
	 */
	public void setAirTransferPickUpBillId(String airTransferPickUpBillId) {
		AirTransferPickUpBillId = airTransferPickUpBillId;
	}

	public String getBeTransfer() {
		return beTransfer;
	}

	public void setBeTransfer(String beTransfer) {
		this.beTransfer = beTransfer;
	}
		public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}