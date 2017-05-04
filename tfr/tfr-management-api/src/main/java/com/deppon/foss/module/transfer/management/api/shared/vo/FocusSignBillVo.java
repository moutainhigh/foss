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
 *  PROJECT NAME  : tfr-management-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/shared/vo/FocusSignBillVo.java
 *  
 *  FILE NAME          :FocusSignBillVo.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.management.api.shared.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.FocusSignBillEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.WaybillFeeDetailEntity;
import com.deppon.foss.module.transfer.management.api.shared.dto.FocusSignBillDto;
import com.deppon.foss.module.transfer.management.api.shared.dto.SignBillTotalDto;
/**
 * 集中接货签单vo
 * @author 038300-foss-pengzhen
 * @date 2012-11-27 下午4:34:29
 */
public class FocusSignBillVo implements Serializable{
	
	private static final long serialVersionUID = -39166334160346443L;
	/**签单id*/
	private String id;
	/**签单编号*/
	private String signBillNo;
	/**司机编号*/
    private String driverCode;
    /**司机姓名*/
    private String driverName;
    /**接货员编号*/
    private String receiverCode;
    /**接货员姓名*/
    private String receiverName;
    /**车牌号*/
    private String vehicleNo;
    /**车型*/
    private String vehicleTypeLength;
    /**空缺公里*/
    private BigDecimal vacancyKm;
    /**行驶公里*/
    private BigDecimal runKm;
    /**签单日期*/
    private Date signBillDate;
    /**总票数*/
    private Integer waybillQtyTotal;
    /**总件数*/
    private Integer goodsQtyTotal;
    /**用车部门*/
	private String useTruckOrgCode;
	/**用车部门Name*/
	private String useTruckOrgName;
    /**司机提成总额*/
    private Long driverRoyaltyAmount;
    /**上楼票数*/
    private Integer upstairsBillQty;
    /**单独接货票数*/
    private Integer singleReceiveBillQty;
    /**签单起始日期*/
    private Date signBillDateFrom;
    /**签单结束日期*/
    private Date signBillDateTo;
    /**运单号*/
    private String wayBillNo;
    /**运单基本信息*/
    private WaybillEntity waybillInfo;
    /**明细删除列表*/
    private List<String> deleteIdList;
    /**明细增加列表*/
    private List<WaybillFeeDetailEntity> addList;
    /**明细更新列表*/
    private List<WaybillFeeDetailEntity> updateList;
    /**集中接货签章查询列表*/
    private List<FocusSignBillDto> focusSignBillDtoList;
    /**集中签单实体*/
    private FocusSignBillEntity focusSignBillEntity;
    /**集中签单明细列表*/
    private List<WaybillFeeDetailEntity> feeDetailList;
    /**操作类型*/
    private boolean operType;
    /**签单合计信息*/
	private SignBillTotalDto totalDto;
	/**
	 * 获取 签单id.
	 *
	 * @return the 签单id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * 设置 签单id.
	 *
	 * @param id the new 签单id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 获取 签单编号.
	 *
	 * @return the 签单编号
	 */
	public String getSignBillNo() {
		return signBillNo;
	}
	
	/**
	 * 设置 签单编号.
	 *
	 * @param signBillNo the new 签单编号
	 */
	public void setSignBillNo(String signBillNo) {
		this.signBillNo = signBillNo;
	}
	
	/**
	 * 获取 司机编号.
	 *
	 * @return the 司机编号
	 */
	public String getDriverCode() {
		return driverCode;
	}
	
	/**
	 * 设置 司机编号.
	 *
	 * @param driverCode the new 司机编号
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}
	
	/**
	 * 获取 司机姓名.
	 *
	 * @return the 司机姓名
	 */
	public String getDriverName() {
		return driverName;
	}
	
	/**
	 * 设置 司机姓名.
	 *
	 * @param driverName the new 司机姓名
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	
	/**
	 * 获取 接货员编号.
	 *
	 * @return the 接货员编号
	 */
	public String getReceiverCode() {
		return receiverCode;
	}
	
	/**
	 * 设置 接货员编号.
	 *
	 * @param receiverCode the new 接货员编号
	 */
	public void setReceiverCode(String receiverCode) {
		this.receiverCode = receiverCode;
	}
	
	/**
	 * 获取 接货员姓名.
	 *
	 * @return the 接货员姓名
	 */
	public String getReceiverName() {
		return receiverName;
	}
	
	/**
	 * 设置 接货员姓名.
	 *
	 * @param receiverName the new 接货员姓名
	 */
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	
	/**
	 * 获取 车牌号.
	 *
	 * @return the 车牌号
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	
	/**
	 * 设置 车牌号.
	 *
	 * @param vehicleNo the new 车牌号
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	
	/**
	 * 获取 车型.
	 *
	 * @return the 车型
	 */
	public String getVehicleTypeLength() {
		return vehicleTypeLength;
	}
	
	/**
	 * 设置 车型.
	 *
	 * @param vehicleTypeLength the new 车型
	 */
	public void setVehicleTypeLength(String vehicleTypeLength) {
		this.vehicleTypeLength = vehicleTypeLength;
	}
	
	/**
	 * 获取 空缺公里.
	 *
	 * @return the 空缺公里
	 */
	public BigDecimal getVacancyKm() {
		return vacancyKm;
	}
	
	/**
	 * 设置 空缺公里.
	 *
	 * @param vacancyKm the new 空缺公里
	 */
	public void setVacancyKm(BigDecimal vacancyKm) {
		this.vacancyKm = vacancyKm;
	}
	
	/**
	 * 获取 行驶公里.
	 *
	 * @return the 行驶公里
	 */
	public BigDecimal getRunKm() {
		return runKm;
	}
	
	/**
	 * 设置 行驶公里.
	 *
	 * @param runKm the new 行驶公里
	 */
	public void setRunKm(BigDecimal runKm) {
		this.runKm = runKm;
	}
	
	/**
	 * 获取 签单日期.
	 *
	 * @return the 签单日期
	 */
	public Date getSignBillDate() {
		return signBillDate;
	}
	
	/**
	 * 设置 签单日期.
	 *
	 * @param signBillDate the new 签单日期
	 */
	public void setSignBillDate(Date signBillDate) {
		this.signBillDate = signBillDate;
	}
	
	/**
	 * 获取 总票数.
	 *
	 * @return the 总票数
	 */
	public Integer getWaybillQtyTotal() {
		return waybillQtyTotal;
	}
	
	/**
	 * 设置 总票数.
	 *
	 * @param waybillQtyTotal the new 总票数
	 */
	public void setWaybillQtyTotal(Integer waybillQtyTotal) {
		this.waybillQtyTotal = waybillQtyTotal;
	}
	
	/**
	 * 获取 总件数.
	 *
	 * @return the 总件数
	 */
	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}
	
	/**
	 * 设置 总件数.
	 *
	 * @param goodsQtyTotal the new 总件数
	 */
	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}
	
	/**
	 * 获取 司机提成总额.
	 *
	 * @return the 司机提成总额
	 */
	public Long getDriverRoyaltyAmount() {
		return driverRoyaltyAmount;
	}
	
	/**
	 * 设置 司机提成总额.
	 *
	 * @param driverRoyaltyAmount the new 司机提成总额
	 */
	public void setDriverRoyaltyAmount(Long driverRoyaltyAmount) {
		this.driverRoyaltyAmount = driverRoyaltyAmount;
	}
	
	/**
	 * 获取 上楼票数.
	 *
	 * @return the 上楼票数
	 */
	public Integer getUpstairsBillQty() {
		return upstairsBillQty;
	}
	
	/**
	 * 设置 上楼票数.
	 *
	 * @param upstairsBillQty the new 上楼票数
	 */
	public void setUpstairsBillQty(Integer upstairsBillQty) {
		this.upstairsBillQty = upstairsBillQty;
	}
	
	/**
	 * 获取 单独接货票数.
	 *
	 * @return the 单独接货票数
	 */
	public Integer getSingleReceiveBillQty() {
		return singleReceiveBillQty;
	}
	
	/**
	 * 设置 单独接货票数.
	 *
	 * @param singleReceiveBillQty the new 单独接货票数
	 */
	public void setSingleReceiveBillQty(Integer singleReceiveBillQty) {
		this.singleReceiveBillQty = singleReceiveBillQty;
	}
	
	/**
	 * 获取 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWayBillNo() {
		return wayBillNo;
	}
	
	/**
	 * 设置 运单号.
	 *
	 * @param wayBillNo the new 运单号
	 */
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}
	
	/**
	 * 获取 运单基本信息.
	 *
	 * @return the 运单基本信息
	 */
	public WaybillEntity getWaybillInfo() {
		return waybillInfo;
	}
	
	/**
	 * 设置 运单基本信息.
	 *
	 * @param waybillInfo the new 运单基本信息
	 */
	public void setWaybillInfo(WaybillEntity waybillInfo) {
		this.waybillInfo = waybillInfo;
	}
	
	/**
	 * 获取 明细删除列表.
	 *
	 * @return the 明细删除列表
	 */
	public List<String> getDeleteIdList() {
		return deleteIdList;
	}
	
	/**
	 * 设置 明细删除列表.
	 *
	 * @param deleteIdList the new 明细删除列表
	 */
	public void setDeleteIdList(List<String> deleteIdList) {
		this.deleteIdList = deleteIdList;
	}
	
	/**
	 * 获取 明细增加列表.
	 *
	 * @return the 明细增加列表
	 */
	public List<WaybillFeeDetailEntity> getAddList() {
		return addList;
	}
	
	/**
	 * 设置 明细增加列表.
	 *
	 * @param addList the new 明细增加列表
	 */
	public void setAddList(List<WaybillFeeDetailEntity> addList) {
		this.addList = addList;
	}
	
	/**
	 * 获取 明细更新列表.
	 *
	 * @return the 明细更新列表
	 */
	public List<WaybillFeeDetailEntity> getUpdateList() {
		return updateList;
	}
	
	/**
	 * 设置 明细更新列表.
	 *
	 * @param updateList the new 明细更新列表
	 */
	public void setUpdateList(List<WaybillFeeDetailEntity> updateList) {
		this.updateList = updateList;
	}
	
	/**
	 * 获取 集中签单实体.
	 *
	 * @return the 集中签单实体
	 */
	public FocusSignBillEntity getFocusSignBillEntity() {
		return focusSignBillEntity;
	}
	
	/**
	 * 设置 集中签单实体.
	 *
	 * @param focusSignBillEntity the new 集中签单实体
	 */
	public void setFocusSignBillEntity(FocusSignBillEntity focusSignBillEntity) {
		this.focusSignBillEntity = focusSignBillEntity;
	}
	
	/**
	 * 获取 集中接货签章查询列表.
	 *
	 * @return the 集中接货签章查询列表
	 */
	public List<FocusSignBillDto> getFocusSignBillDtoList() {
		return focusSignBillDtoList;
	}
	
	/**
	 * 设置 集中接货签章查询列表.
	 *
	 * @param focusSignBillDtoList the new 集中接货签章查询列表
	 */
	public void setFocusSignBillDtoList(List<FocusSignBillDto> focusSignBillDtoList) {
		this.focusSignBillDtoList = focusSignBillDtoList;
	}
	
	/**
	 * 获取 用车部门.
	 *
	 * @return the 用车部门
	 */
	public String getUseTruckOrgCode() {
		return useTruckOrgCode;
	}
	
	/**
	 * 设置 用车部门.
	 *
	 * @param useTruckOrgCode the new 用车部门
	 */
	public void setUseTruckOrgCode(String useTruckOrgCode) {
		this.useTruckOrgCode = useTruckOrgCode;
	}
	
	/**
	 * 获取 签单起始日期.
	 *
	 * @return the 签单起始日期
	 */
	public Date getSignBillDateFrom() {
		return signBillDateFrom;
	}
	
	/**
	 * 设置 签单起始日期.
	 *
	 * @param signBillDateFrom the new 签单起始日期
	 */
	public void setSignBillDateFrom(Date signBillDateFrom) {
		this.signBillDateFrom = signBillDateFrom;
	}
	
	/**
	 * 获取 签单结束日期.
	 *
	 * @return the 签单结束日期
	 */
	public Date getSignBillDateTo() {
		return signBillDateTo;
	}
	
	/**
	 * 设置 签单结束日期.
	 *
	 * @param signBillDateTo the new 签单结束日期
	 */
	public void setSignBillDateTo(Date signBillDateTo) {
		this.signBillDateTo = signBillDateTo;
	}
	
	/**
	 * 获取 集中签单明细列表.
	 *
	 * @return the 集中签单明细列表
	 */
	public List<WaybillFeeDetailEntity> getFeeDetailList() {
		return feeDetailList;
	}
	
	/**
	 * 设置 集中签单明细列表.
	 *
	 * @param feeDetailList the new 集中签单明细列表
	 */
	public void setFeeDetailList(List<WaybillFeeDetailEntity> feeDetailList) {
		this.feeDetailList = feeDetailList;
	}
	
	/**
	 * 判断是否 操作类型.
	 *
	 * @return the 操作类型
	 */
	public boolean isOperType() {
		return operType;
	}
	
	/**
	 * 设置 操作类型.
	 *
	 * @param operType the new 操作类型
	 */
	public void setOperType(boolean operType) {
		this.operType = operType;
	}
	
	/**
	 * 获取 签单合计信息.
	 *
	 * @return the 签单合计信息
	 */
	public SignBillTotalDto getTotalDto() {
		return totalDto;
	}

	/**
	 * 设置 签单合计信息.
	 *
	 * @param totalDto the new 签单合计信息
	 */
	public void setTotalDto(SignBillTotalDto totalDto) {
		this.totalDto = totalDto;
	}

	/**   
	 * useTruckOrgName   
	 *   
	 * @return  the useTruckOrgName   
	 */
	
	public String getUseTruckOrgName() {
		return useTruckOrgName;
	}

	/**   
	 * @param useTruckOrgName the useTruckOrgName to set
	 * Date:2013-3-25上午10:23:37
	 */
	public void setUseTruckOrgName(String useTruckOrgName) {
		this.useTruckOrgName = useTruckOrgName;
	}
}