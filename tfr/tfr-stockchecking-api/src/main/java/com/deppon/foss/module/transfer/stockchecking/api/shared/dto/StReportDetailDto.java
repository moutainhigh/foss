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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/api/shared/dto/StReportDetailDto.java
 *  
 *  FILE NAME          :StReportDetailDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stockchecking.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;
import com.deppon.foss.module.transfer.stockchecking.api.shared.domain.StDifferDetailEntity;

/**
 * 清仓报告明细业务实体
 * @author foss-wuyingjie
 * @date 2012-12-26 上午9:49:44
 */
public class StReportDetailDto extends StDifferDetailEntity{
	private static final long serialVersionUID = -533658461837249940L;
	/**清仓差异报告编号*/
	private String reportCode;
	/**货区编号*/
    private String goodsAreaCode;
    /**获取名称*/
    private String goodsAreaName;
    /**清仓差异报告创建时间*/
    private Date reportCreateTime;
    /**差异类型描述*/
    private String differenceTypeDesc;
    @SuppressWarnings("unused")
	private String diffInfo;

	/**清仓人*/
    private String operator;
    
    //存储差异明细
    private List<StDifferDetailEntity> differDetailList;
    /**差异原因**/
    private String differenceReason;
    /**运单目的站*/
    private String billTargetOrgName;
    private String billTargetOrgCode;
    /**货物名称*/
    private String goodsName;
	/**包装*/
    private String goodsPackage;
    /**重量*/
    private BigDecimal goodsWeight;

	/**体积*/
    private BigDecimal goodsVolume;
    
   //运输性质
  	private String transProperty;
  	/**起始件数*/
	private int startQty;
	/**结束件数*/
	private int endQty;
	/**提货方式*/
	private String receiveMethod;
	/**分区名称*/
	private String districtName;
	/**行政区*/
	private String administrativeRegion;
	/**件区*/
	private String  pieceRegion;
    
    /**
	 * @return the transProperty
	 */
	public String getTransProperty() {
		return transProperty;
	}

	/**
	 * @param transProperty the transProperty to set
	 */
	public void setTransProperty(String transProperty) {
		this.transProperty = transProperty;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsPackage() {
		return goodsPackage;
	}

	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}

	public BigDecimal getGoodsWeight() {
		return goodsWeight;
	}

	public void setGoodsWeight(BigDecimal goodsWeight) {
		this.goodsWeight = goodsWeight;
	}

	public BigDecimal getGoodsVolume() {
		return goodsVolume;
	}

	public void setGoodsVolume(BigDecimal goodsVolume) {
		this.goodsVolume = goodsVolume;
	}
	
	public List<StDifferDetailEntity> getDifferDetailList() {
		return differDetailList;
	}

	public void setDifferDetailList(List<StDifferDetailEntity> differDetailList) {
		this.differDetailList = differDetailList;
	}

	/**
	 * 获取 清仓差异报告编号.
	 *
	 * @return the 清仓差异报告编号
	 */
	public String getReportCode() {
		return reportCode;
	}
	
	/**
	 * 设置 清仓差异报告编号.
	 *
	 * @param reportCode the new 清仓差异报告编号
	 */
	public void setReportCode(String reportCode) {
		this.reportCode = reportCode;
	}
	
	/**
	 * 获取 货区编号.
	 *
	 * @return the 货区编号
	 */
	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}
	
	/**
	 * 设置 货区编号.
	 *
	 * @param goodsAreaCode the new 货区编号
	 */
	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}
	
	/**
	 * 获取 获取名称.
	 *
	 * @return the 获取名称
	 */
	public String getGoodsAreaName() {
		return goodsAreaName;
	}
	
	/**
	 * 设置 获取名称.
	 *
	 * @param goodsAreaName the new 获取名称
	 */
	public void setGoodsAreaName(String goodsAreaName) {
		this.goodsAreaName = goodsAreaName;
	}
	
	/**
	 * 获取 清仓差异报告创建时间.
	 *
	 * @return the 清仓差异报告创建时间
	 */
	public Date getReportCreateTime() {
		return reportCreateTime;
	}
	
	/**
	 * 设置 清仓差异报告创建时间.
	 *
	 * @param reportCreateTime the new 清仓差异报告创建时间
	 */
	public void setReportCreateTime(Date reportCreateTime) {
		this.reportCreateTime = reportCreateTime;
	}
	
	/**
	 * 获取 差异类型描述.
	 *
	 * @return the 差异类型描述
	 */
	public String getDifferenceTypeDesc() {
		return differenceTypeDesc;
	}
	
	/**
	 * 设置 差异类型描述.
	 *
	 * @param differenceTypeDesc the new 差异类型描述
	 */
	public void setDifferenceTypeDesc(String differenceTypeDesc) {
		this.differenceTypeDesc = differenceTypeDesc;
	}
	
	/**
	 * 获取 清仓人.
	 *
	 * @return the 清仓人
	 */
	public String getOperator() {
		return operator;
	}
	
	/**
	 * 设置 清仓人.
	 *
	 * @param operator the new 清仓人
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	public String getDiffInfo() {
		if(StringUtils.equals(TransferConstants.GOODS_STATUS_OK, this.getDifferenceType())){
			return "正常";
		}else if(StringUtils.equals(TransferConstants.GOODS_STATUS_LACK, this.getDifferenceType())){
			return "少货";
		}else if(StringUtils.equals(TransferConstants.GOODS_STATUS_SURPLUS, this.getDifferenceType())){
			return "多货";
		}else if(StringUtils.equals(TransferConstants.GOODS_STATUS_SURPLUS_ERROR_GOODSAREA, this.getDifferenceType())){
			return "放错货区";
			//return "多货-夹带";
		}else if(StringUtils.equals(TransferConstants.GOODS_STATUS_SURPLUS_CARRY, this.getDifferenceType())){
			return "多货-夹带";
			//return "多货-异地夹带";
		}else if(StringUtils.equals(TransferConstants.GOODS_STATUS_SURPLUS_CARRY_OTHERS, this.getDifferenceType())){
			return "多货-异地夹带";
		}else if(StringUtils.equals(TransferConstants.GOODS_STATUS_SIGN, this.getDifferenceType())){
			return "已签收";
		}else if(StringUtils.equals(TransferConstants.GOODS_STATUS_OBSOLETE, this.getDifferenceType())){
			return "已作废";
		}else if(StringUtils.equals(TransferConstants.GOODS_STATUS_ABORTED, this.getDifferenceType())){
			return "中止作废";
		}else if(StringUtils.equals(TransferConstants.GOODS_STATUS_RFC_DEST, this.getDifferenceType())){
			return "更改目的站";
		}else{
			return "";
		}
	}

	public void setDiffInfo(String diffInfo) {
		this.diffInfo = diffInfo;
	}

	public String getBillTargetOrgName() {
		return billTargetOrgName;
	}

	public void setBillTargetOrgName(String billTargetOrgName) {
		this.billTargetOrgName = billTargetOrgName;
	}

	public String getBillTargetOrgCode() {
		return billTargetOrgCode;
	}

	public void setBillTargetOrgCode(String billTargetOrgCode) {
		this.billTargetOrgCode = billTargetOrgCode;
	}

	public String getDifferenceReason() {
		
		if(StringUtils.equals(TransferConstants.DIFFERENCE_REASON_FCHQ, this.differenceReason)){
			return "放错货区";
		}else if(StringUtils.equals(TransferConstants.DIFFERENCE_REASON_ZCLS, this.differenceReason)){
			return "装车漏扫";
		}else if(StringUtils.equals(TransferConstants.DIFFERENCE_REASON_QSWCK, this.differenceReason)){
			return "签收未出库";
		}else if(StringUtils.equals(TransferConstants.DIFFERENCE_REASON_QT, this.differenceReason)){
			return "其他";
		}else if(StringUtils.equals(TransferConstants.DIFFERENCE_REASON_QCLS, this.differenceReason)){
			return "清仓漏扫";
		}
		else{
			return this.differenceReason;
		}
	}

	public void setDifferenceReason(String differenceReason) {
		this.differenceReason = differenceReason;
	}
	/**
	 * 获取起始件数.
	 *
	 * @return the 起始件数
	 */
	public int getStartQty() {
		return startQty;
	}

	/**
	 * 设置 运起始件数.
	 *
	 * @param startQty the new 起始件数
	 */
	public void setStartQty(int startQty) {
		this.startQty = startQty;
	}

	/**
	 * 获取结束件数.
	 *
	 * @return the 结束件数
	 */
	public int getEndQty() {
		return endQty;
	}

	/**
	 * 设置 结束件数.
	 *
	 * @param endQty the new 结束件数
	 */
	public void setEndQty(int endQty) {
		this.endQty = endQty;
	}

	/**
	 * 获取提货方式.
	 *
	 * @return the 提货方式
	 */
	public String getReceiveMethod() {
		return receiveMethod;
	}

	/**
	 * 设置 提货方式.
	 *
	 * @param receiveMethod the new 提货方式
	 */
	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	/**
	 * 获取分区名称.
	 *
	 * @return the 分区名称
	 */
	public String getDistrictName() {
		return districtName;
	}

	/**
	 * 设置 分区名称.
	 *
	 * @param districtName the new 分区名称
	 */
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	/**
	 * 获取行政区名称.
	 *
	 * @return the 获取行政区名称
	 */
	public String getAdministrativeRegion() {
		return administrativeRegion;
	}

	/**
	 * 设置 行政区名称.
	 *
	 * @param administrativeRegion the new 行政区名称
	 */
	public void setAdministrativeRegion(String administrativeRegion) {
		this.administrativeRegion = administrativeRegion;
	}

	/**
	 * 获取分区名称.
	 *
	 * @return the 获取分区名称
	 */
	public String getPieceRegion() {
		return pieceRegion;
	}

	/**
	 * 设置 分区名称.
	 *
	 * @param pieceRegion the new 分区名称
	 */
	public void setPieceRegion(String pieceRegion) {
		this.pieceRegion = pieceRegion;
	}
	
}