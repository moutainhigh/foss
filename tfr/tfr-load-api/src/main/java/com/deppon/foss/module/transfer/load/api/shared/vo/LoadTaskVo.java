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
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/vo/LoadTaskVo.java
 *  
 *  FILE NAME          :LoadTaskVo.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.shared.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportWayBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadSerialNoEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoadTaskDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoadWayBillDetailDto;

/**
 * 装车任务Vo
 * @author ibm-zhangyixin
 * @date 2012-12-25 下午6:49:14
 */
public class LoadTaskVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9034207710840811989L;
	/**装车任务dto**/
	private LoadTaskDto loadTaskDto = new LoadTaskDto();
	/**装卸车人员参与情况entity**/
	private LoaderParticipationEntity loaderParticipation = new LoaderParticipationEntity();
	/**装卸车人员参与情况entity集合**/
	private List<LoaderParticipationEntity> loaderParticipationList = new ArrayList<LoaderParticipationEntity>();
	/**装车任务dto集合**/
	private List<LoadTaskDto> loadTaskList = new ArrayList<LoadTaskDto>();
	/**装车运单明细集合**/
	private List<LoadWayBillDetailDto> loadWaybillDetailList = new ArrayList<LoadWayBillDetailDto>();
	/**装车扫描明细**/
	private LoadSerialNoEntity loadSerialNo = new LoadSerialNoEntity();
	/**派送装车差异No**/
	private String gaprepNo;		//派送装车差异No
	/**派送装车差异Id**/
	private String loadGaprepId;	//派送装车差异Id
	/**派送装车明细数据集合**/
	private List<DeliverLoadGapReportWayBillEntity> reportWayBills = new ArrayList<DeliverLoadGapReportWayBillEntity>();//派送装车明细数据
	/**派送装车数据**/
	private DeliverLoadGapReportEntity loadGapReport = new DeliverLoadGapReportEntity();	//派送装车
	/**扫描明细集合**/
	private List<LoadSerialNoEntity> loadSerialNoList = new ArrayList<LoadSerialNoEntity>();
	/**
	 * 外场编码
	 */
	private String outfieldCode;
	
	public String getOutfieldCode() {
		return outfieldCode;
	}

	public void setOutfieldCode(String outfieldCode) {
		this.outfieldCode = outfieldCode;
	}

	/**
	 * 获取 装车任务dto*.
	 *
	 * @return the 装车任务dto*
	 */
	public LoadTaskDto getLoadTaskDto() {
		return loadTaskDto;
	}

	/**
	 * 设置 装车任务dto*.
	 *
	 * @param loadTaskDto the new 装车任务dto*
	 */
	public void setLoadTaskDto(LoadTaskDto loadTaskDto) {
		this.loadTaskDto = loadTaskDto;
	}

	/**
	 * 获取 装车任务dto集合*.
	 *
	 * @return the 装车任务dto集合*
	 */
	public List<LoadTaskDto> getLoadTaskList() {
		return loadTaskList;
	}

	/**
	 * 设置 装车任务dto集合*.
	 *
	 * @param loadTaskList the new 装车任务dto集合*
	 */
	public void setLoadTaskList(List<LoadTaskDto> loadTaskList) {
		this.loadTaskList = loadTaskList;
	}

	/**
	 * 获取 装卸车人员参与情况entity*.
	 *
	 * @return the 装卸车人员参与情况entity*
	 */
	public LoaderParticipationEntity getLoaderParticipation() {
		return loaderParticipation;
	}

	/**
	 * 设置 装卸车人员参与情况entity*.
	 *
	 * @param loaderParticipation the new 装卸车人员参与情况entity*
	 */
	public void setLoaderParticipation(LoaderParticipationEntity loaderParticipation) {
		this.loaderParticipation = loaderParticipation;
	}

	/**
	 * 获取 装卸车人员参与情况entity集合*.
	 *
	 * @return the 装卸车人员参与情况entity集合*
	 */
	public List<LoaderParticipationEntity> getLoaderParticipationList() {
		return loaderParticipationList;
	}

	/**
	 * 设置 装卸车人员参与情况entity集合*.
	 *
	 * @param loaderParticipationList the new 装卸车人员参与情况entity集合*
	 */
	public void setLoaderParticipationList(
			List<LoaderParticipationEntity> loaderParticipationList) {
		this.loaderParticipationList = loaderParticipationList;
	}

	/**
	 * 获取 装车运单明细集合*.
	 *
	 * @return the 装车运单明细集合*
	 */
	public List<LoadWayBillDetailDto> getLoadWaybillDetailList() {
		return loadWaybillDetailList;
	}

	/**
	 * 设置 装车运单明细集合*.
	 *
	 * @param loadWaybillDetailList the new 装车运单明细集合*
	 */
	public void setLoadWaybillDetailList(
			List<LoadWayBillDetailDto> loadWaybillDetailList) {
		this.loadWaybillDetailList = loadWaybillDetailList;
	}

	/**
	 * 获取 装车扫描明细*.
	 *
	 * @return the 装车扫描明细*
	 */
	public LoadSerialNoEntity getLoadSerialNo() {
		return loadSerialNo;
	}

	/**
	 * 设置 装车扫描明细*.
	 *
	 * @param loadSerialNo the new 装车扫描明细*
	 */
	public void setLoadSerialNo(LoadSerialNoEntity loadSerialNo) {
		this.loadSerialNo = loadSerialNo;
	}

	/**
	 * 获取 扫描明细集合*.
	 *
	 * @return the 扫描明细集合*
	 */
	public List<LoadSerialNoEntity> getLoadSerialNoList() {
		return loadSerialNoList;
	}

	/**
	 * 设置 扫描明细集合*.
	 *
	 * @param loadSerialNoList the new 扫描明细集合*
	 */
	public void setLoadSerialNoList(List<LoadSerialNoEntity> loadSerialNoList) {
		this.loadSerialNoList = loadSerialNoList;
	}

	/**
	 * 获取 派送装车差异No*.
	 *
	 * @return the 派送装车差异No*
	 */
	public String getGaprepNo() {
		return gaprepNo;
	}

	/**
	 * 设置 派送装车差异No*.
	 *
	 * @param gaprepNo the new 派送装车差异No*
	 */
	public void setGaprepNo(String gaprepNo) {
		this.gaprepNo = gaprepNo;
	}

	/**
	 * 获取 派送装车差异Id*.
	 *
	 * @return the 派送装车差异Id*
	 */
	public String getLoadGaprepId() {
		return loadGaprepId;
	}

	/**
	 * 设置 派送装车差异Id*.
	 *
	 * @param loadGaprepId the new 派送装车差异Id*
	 */
	public void setLoadGaprepId(String loadGaprepId) {
		this.loadGaprepId = loadGaprepId;
	}

	/**
	 * 获取 派送装车明细数据集合*.
	 *
	 * @return the 派送装车明细数据集合*
	 */
	public List<DeliverLoadGapReportWayBillEntity> getReportWayBills() {
		return reportWayBills;
	}

	/**
	 * 设置 派送装车明细数据集合*.
	 *
	 * @param reportWayBills the new 派送装车明细数据集合*
	 */
	public void setReportWayBills(
			List<DeliverLoadGapReportWayBillEntity> reportWayBills) {
		this.reportWayBills = reportWayBills;
	}

	/**
	 * 获取 派送装车数据*.
	 *
	 * @return the 派送装车数据*
	 */
	public DeliverLoadGapReportEntity getLoadGapReport() {
		return loadGapReport;
	}

	/**
	 * 设置 派送装车数据*.
	 *
	 * @param loadGapReport the new 派送装车数据*
	 */
	public void setLoadGapReport(DeliverLoadGapReportEntity loadGapReport) {
		this.loadGapReport = loadGapReport;
	}
	
}