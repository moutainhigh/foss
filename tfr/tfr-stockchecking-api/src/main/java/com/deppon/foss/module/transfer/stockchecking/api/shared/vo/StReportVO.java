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
 *  PROJECT NAME  : tfr-stockchecking-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/api/shared/vo/StReportVO.java
 *  
 *  FILE NAME          :StReportVO.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stockchecking.api.shared.vo;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StDifferDetailEntity;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StReportDetailDto;
import com.deppon.foss.module.transfer.stockchecking.api.shared.dto.StReportDto;

/**
 * 清仓差异报告VO
 * @author foss-wuyingjie
 * @date 2012-12-26 上午9:57:49
 */
public class StReportVO {
	
	/**清仓差异报告、查询及查询条件*/
	private StReportDto stReportDto = new StReportDto();
	/**清仓差异报告明细*/
	private StDifferDetailEntity stDifferDetail = new StDifferDetailEntity();
	/**清仓差异报告查询结果*/
	private List<StReportDto> stReportDtoList = new ArrayList<StReportDto>();
	/**清仓差异报告明细列表*/
	private List<StReportDetailDto> stReportDetailDtoList = new ArrayList<StReportDetailDto>();
	/**清仓差异报告相关联明细列表*/
	private List<StReportDetailDto> stReportRelativeDetailDtoList = new ArrayList<StReportDetailDto>();
	/**对应外场code*/
	private String transferCode;
	/**清仓差异报告明细列表*/
	private List<StDifferDetailEntity> stDifferDetailList = new ArrayList<StDifferDetailEntity>();
	
	  /**少货件数**/  
	private String lessGoodsCount;
	/**多货件数（不包含放错库区）**/
    private String exceedDoodsCount;
    /**多货放错库区件数**/
	private String errorGoodsAreaCount;
	/**是否外场**/
	private String transferCenter;

	/**已签收件数**/
	private String signCount;
	/**已作废件数**/
	private String obsoleteCount;
	/**已中止作废件数**/
	private String abortedCount;
	
	public String getTransferCenter() {
		return transferCenter;
	}

	public void setTransferCenter(String transferCenter) {
		this.transferCenter = transferCenter;
	}

	public String getLessGoodsCount() {
		return lessGoodsCount;
	}

	public void setLessGoodsCount(String lessGoodsCount) {
		this.lessGoodsCount = lessGoodsCount;
	}

	public String getExceedDoodsCount() {
		return exceedDoodsCount;
	}

	public void setExceedDoodsCount(String exceedDoodsCount) {
		this.exceedDoodsCount = exceedDoodsCount;
	}

	public String getErrorGoodsAreaCount() {
		return errorGoodsAreaCount;
	}

	public void setErrorGoodsAreaCount(String errorGoodsAreaCount) {
		this.errorGoodsAreaCount = errorGoodsAreaCount;
	}

	public String getSignCount() {
		return signCount;
	}

	public void setSignCount(String signCount) {
		this.signCount = signCount;
	}

	public String getObsoleteCount() {
		return obsoleteCount;
	}

	public void setObsoleteCount(String obsoleteCount) {
		this.obsoleteCount = obsoleteCount;
	}

	public String getAbortedCount() {
		return abortedCount;
	}

	public void setAbortedCount(String abortedCount) {
		this.abortedCount = abortedCount;
	}

	public List<StDifferDetailEntity> getStDifferDetailList() {
		return stDifferDetailList;
	}

	public void setStDifferDetailList(List<StDifferDetailEntity> stDifferDetailList) {
		this.stDifferDetailList = stDifferDetailList;
	}

	public String getTransferCode() {
		return transferCode;
	}

	public void setTransferCode(String transferCode) {
		this.transferCode = transferCode;
	}
	/**
	 * 获取 清仓差异报告、查询及查询条件.
	 *
	 * @return the 清仓差异报告、查询及查询条件
	 */
	public StReportDto getStReportDto() {
		return stReportDto;
	}

	/**
	 * 设置 清仓差异报告、查询及查询条件.
	 *
	 * @param stReportDto the new 清仓差异报告、查询及查询条件
	 */
	public void setStReportDto(StReportDto stReportDto) {
		this.stReportDto = stReportDto;
	}

	/**
	 * 获取 清仓差异报告查询结果.
	 *
	 * @return the 清仓差异报告查询结果
	 */
	public List<StReportDto> getStReportDtoList() {
		return stReportDtoList;
	}

	/**
	 * 设置 清仓差异报告查询结果.
	 *
	 * @param stReportDtoList the new 清仓差异报告查询结果
	 */
	public void setStReportDtoList(List<StReportDto> stReportDtoList) {
		this.stReportDtoList = stReportDtoList;
	}

	/**
	 * 获取 清仓差异报告明细列表.
	 *
	 * @return the 清仓差异报告明细列表
	 */
	public List<StReportDetailDto> getStReportDetailDtoList() {
		return stReportDetailDtoList;
	}

	/**
	 * 设置 清仓差异报告明细列表.
	 *
	 * @param stReportDetailDtoList the new 清仓差异报告明细列表
	 */
	public void setStReportDetailDtoList(List<StReportDetailDto> stReportDetailDtoList) {
		this.stReportDetailDtoList = stReportDetailDtoList;
	}

	/**
	 * 获取 清仓差异报告相关联明细列表.
	 *
	 * @return the 清仓差异报告相关联明细列表
	 */
	public List<StReportDetailDto> getStReportRelativeDetailDtoList() {
		return stReportRelativeDetailDtoList;
	}

	/**
	 * 设置 清仓差异报告相关联明细列表.
	 *
	 * @param stReportRelativeDetailDtoList the new 清仓差异报告相关联明细列表
	 */
	public void setStReportRelativeDetailDtoList(List<StReportDetailDto> stReportRelativeDetailDtoList) {
		this.stReportRelativeDetailDtoList = stReportRelativeDetailDtoList;
	}

	/**
	 * 获取 清仓差异报告明细.
	 *
	 * @return the 清仓差异报告明细
	 */
	public StDifferDetailEntity getStDifferDetail() {
		return stDifferDetail;
	}

	/**
	 * 设置 清仓差异报告明细.
	 *
	 * @param stDifferDetail the new 清仓差异报告明细
	 */
	public void setStDifferDetail(StDifferDetailEntity stDifferDetail) {
		this.stDifferDetail = stDifferDetail;
	}
	
	
}