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
 *  PROJECT NAME  : tfr-unload-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/shared/domain/UnloadDiffReportEntity.java
 *  
 *  FILE NAME          :UnloadDiffReportEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.unload.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/** 
 * @className: UnloadDiffReportEntity
 * @author: ShiWei shiwei@outlook.com
 * @description: 卸车差异报告实体类
 * @date: 2012-12-7 下午5:14:28
 * 
 */
public class UnloadDiffReportEntity extends BaseEntity{

	private static final long serialVersionUID = -5834099154211907317L;
	
		//卸车差异报告编号
		private String diffReportNo;
		//车牌号
		private String vehicleNo;
		//卸车任务编号
		private String unloadTaskNo;
		//卸车任务ID
		private String unloadTaskId;
		//差异报告处理状态
		private String handleStatus;
		//生成部门code
		private String orgCode;
		//理货员姓名
		private String loadManName;
		//理货员工号
		private String loadManCode;
		//多货件数
		private int moreGoodsPieces;
		//少货件数
		private int lackGoodsPieces;
		//手输件数 chigo
		private int byhandGoodsPieces;
		//卸车类型
		private String unloadType;
		//报告生成时间
		private Date reportCreateTime;
		//理货员所在部门
		private String loadManDeptName;
		//是否PDA处理
		private String pdaHandleStatus;
		
		/**
		 * @return the loadManDeptName
		 */
		public String getLoadManDeptName() {
			return loadManDeptName;
		}
		/**
		 * @param loadManDeptName the loadManDeptName to set
		 */
		public void setLoadManDeptName(String loadManDeptName) {
			this.loadManDeptName = loadManDeptName;
		}
		public String getUnloadType() {
			return unloadType;
		}
		public void setUnloadType(String unloadType) {
			this.unloadType = unloadType;
		}
		public Date getReportCreateTime() {
			return reportCreateTime;
		}
		public void setReportCreateTime(Date reportCreateTime) {
			this.reportCreateTime = reportCreateTime;
		}
		public String getDiffReportNo() {
			return diffReportNo;
		}
		public void setDiffReportNo(String diffReportNo) {
			this.diffReportNo = diffReportNo;
		}
		public String getVehicleNo() {
			return vehicleNo;
		}
		public void setVehicleNo(String vehicleNo) {
			this.vehicleNo = vehicleNo;
		}
		public String getUnloadTaskNo() {
			return unloadTaskNo;
		}
		public void setUnloadTaskNo(String unloadTaskNo) {
			this.unloadTaskNo = unloadTaskNo;
		}
		public String getUnloadTaskId() {
			return unloadTaskId;
		}
		public void setUnloadTaskId(String unloadTaskId) {
			this.unloadTaskId = unloadTaskId;
		}
		public String getHandleStatus() {
			return handleStatus;
		}
		public void setHandleStatus(String handleStatus) {
			this.handleStatus = handleStatus;
		}
		public String getOrgCode() {
			return orgCode;
		}
		public void setOrgCode(String orgCode) {
			this.orgCode = orgCode;
		}
		public String getLoadManName() {
			return loadManName;
		}
		public void setLoadManName(String loadManName) {
			this.loadManName = loadManName;
		}
		public String getLoadManCode() {
			return loadManCode;
		}
		public void setLoadManCode(String loadManCode) {
			this.loadManCode = loadManCode;
		}
		public int getMoreGoodsPieces() {
			return moreGoodsPieces;
		}
		public void setMoreGoodsPieces(int moreGoodsPieces) {
			this.moreGoodsPieces = moreGoodsPieces;
		}
		public int getLackGoodsPieces() {
			return lackGoodsPieces;
		}
		public void setLackGoodsPieces(int lackGoodsPieces) {
			this.lackGoodsPieces = lackGoodsPieces;
		}
		public int getByhandGoodsPieces() {
			return byhandGoodsPieces;
		}
		public void setByhandGoodsPieces(int byhandGoodsPieces) {
			this.byhandGoodsPieces = byhandGoodsPieces;
		}
		public String getPdaHandleStatus() {
			return pdaHandleStatus;
		}
		public void setPdaHandleStatus(String pdaHandleStatus) {
			this.pdaHandleStatus = pdaHandleStatus;
		}

}