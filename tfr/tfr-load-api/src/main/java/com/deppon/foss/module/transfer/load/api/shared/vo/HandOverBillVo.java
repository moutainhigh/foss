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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/vo/HandOverBillVo.java
 *  
 *  FILE NAME          :HandOverBillVo.java
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
import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverBaseDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.BaseDataDictDto;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillOptLogEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillSerialNoDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SerialNoStockEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.NewHandOverBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QuerySerialNoListForWaybillConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryWaybillForHandOverBillConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.SerialNoLoadExceptionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.UpdateHandOverBillDto;

/** 
 * @className: HandOverBillVo
 * @author: 045923-foss-shiwei
 * @description: 交接单模块之Vo类
 * @date: 2012-10-20 上午8:52:53
 * 
 */
public class HandOverBillVo implements Serializable {

	private static final long serialVersionUID = -7990369287797626170L;

	/**
	 * 新增时的dto
	 */
	private NewHandOverBillDto newHandOverBillDto;
	
	/**
	 * 查询交接运单时，接收前台传入的查询条件
	 */
	private QueryWaybillForHandOverBillConditionDto queryWaybillForHandOverBillDto;
	
	/**
	 * 查询交接运单，打卡运单，获取运单下的流水号时的查询条件
	 */
	private QuerySerialNoListForWaybillConditionDto querySerialNoListForWaybillConditionDto;
	
	/**
	 * 定义查询交接运单界面的库存运单list
	 */
	private List<HandOverBillDetailEntity> waybillStockList;
	
	/**
	 * 定义通过快速添加功能获取到的运单库存信息
	 */
	private HandOverBillDetailEntity waybillStock;
	
	/**
	 * 定义查询交接运单界面，运单对应的流水号list
	 */
	private List<SerialNoStockEntity> serialNoStockList;
	
	/**
	 * 双击某运单查询流水号时的运单号
	 */
	private String waybillNo;
	
	/**
	 * 查询交接单时，返回到前台的交接单信息list
	 */
	private List<QueryHandOverBillDto> handOverBillList;
	
	/**
	 * 查询交接单时，前台传入的查询条件dto
	 */
	private QueryHandOverBillConditionDto queryHandOverBillConditionDto;
	
	/**
	 * 查看交接单详情界面时，通过交接单号获取运单明细
	 */
	private String handOverBillNo;
	
	/**
	 * 查询交接单详情界面，查询该交接单的修改日志
	 */
	private List<HandOverBillOptLogEntity> handOverBillOptLogList;
	
	/**
	 * 用于修改交接单(无PDA)界面，获取交接单下所有流水号实体list
	 */
	private List<HandOverBillSerialNoDetailEntity> serialNoList;
	
	/**
	 * 用于修改交接单(PDA)界面，获取交接单下所有流水号实体list
	 */
	private List<SerialNoLoadExceptionDto> pdaSerialNoList;
	
	/**
	 * 用于修改交接单用例，接收前台传入的信息
	 */
	private UpdateHandOverBillDto updateHandOverBillDto;
	
	/**
	 * 打印交接单时,验证交接单
	 */
	private List<String> handOverBillNos;
	
	/**
	 * 车牌号
	 */
	private String vehicleNo;
	
	/**
	 * 交接类型
	 */
	private String handOverType;
	
	/**
	 * 打印交接单时,验证交接单结果, 大于0则提示还有checkPrintHandOverBillRestlt个交接单尚未选择
	 */
	private Long checkPrintHandOverBillRestlt;
	
	/**
	 * 查看交接单详情时，根据交接单号获取的交接单基本信息实体
	 */
	private HandOverBillEntity baseEntity;
	
	/**
	 * 配载单号
	 */
	private String vehicleassembleNo;
	
	/**
	 * 到达部门code
	 */
	private String arriveDeptCode;
	
	/**
	 * 根据到达部门获取的外场实体（可能为null）
	 */
	private OutfieldEntity outfield;
	
	/**
	 * 输入车牌号带出的司机信息
	 */
	private DriverBaseDto driverInfo;
	
	/**
	 * 出发部门名称
	 */
	private String departOrgName;
	
	/**
	 * 当前大部门code
	 */
	private String superOrgCode;
	
	/**
	 * 错误信息
	 */
	private String errorMessage;
	/**
	 * 三级产品list（运输性质）
	 */
	private List<BaseDataDictDto> productList;

	/**
	 * 快递转换体积参数
	 * */
	private BigDecimal expressConvertParameter;
	

	/**
	 * 交接单在租车标记表中的状态，如果为Y则说明不许修改，为N就允许修改
	 ***/
	private String holdingState;
	
	
	public String getHoldingState() {
		return holdingState;
	}

	public void setHoldingState(String holdingState) {
		this.holdingState = holdingState;
	}

	
	public BigDecimal getExpressConvertParameter() {
		return expressConvertParameter;
	}

	public void setExpressConvertParameter(BigDecimal expressConvertParameter) {
		this.expressConvertParameter = expressConvertParameter;
	}

	public List<BaseDataDictDto> getProductList() {
		return productList;
	}

	public void setProductList(List<BaseDataDictDto> productList) {
		this.productList = productList;
	}

	/**
	 * 当前部门是否为营业部
	 */
	private String beSalesDept;
	
	 /**是否分部
	 ***/
	private String beDivision;
	
	public String getBeDivision() {
		return beDivision;
	}
	public void setBeDivision(String beDivision) {
		this.beDivision = beDivision;
	}
	
	public String getBeSalesDept() {
		return beSalesDept;
	}

	public void setBeSalesDept(String beSalesDept) {
		this.beSalesDept = beSalesDept;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getSuperOrgCode() {
		return superOrgCode;
	}

	public void setSuperOrgCode(String superOrgCode) {
		this.superOrgCode = superOrgCode;
	}

	public String getDepartOrgName() {
		return departOrgName;
	}

	public void setDepartOrgName(String departOrgName) {
		this.departOrgName = departOrgName;
	}

	public HandOverBillDetailEntity getWaybillStock() {
		return waybillStock;
	}

	public void setWaybillStock(HandOverBillDetailEntity waybillStock) {
		this.waybillStock = waybillStock;
	}

	public DriverBaseDto getDriverInfo() {
		return driverInfo;
	}

	public void setDriverInfo(DriverBaseDto driverInfo) {
		this.driverInfo = driverInfo;
	}

	public String getArriveDeptCode() {
		return arriveDeptCode;
	}

	public void setArriveDeptCode(String arriveDeptCode) {
		this.arriveDeptCode = arriveDeptCode;
	}

	public OutfieldEntity getOutfield() {
		return outfield;
	}

	public void setOutfield(OutfieldEntity outfield) {
		this.outfield = outfield;
	}

	public String getVehicleassembleNo() {
		return vehicleassembleNo;
	}

	public void setVehicleassembleNo(String vehicleassembleNo) {
		this.vehicleassembleNo = vehicleassembleNo;
	}

	public QueryWaybillForHandOverBillConditionDto getQueryWaybillForHandOverBillDto() {
		return queryWaybillForHandOverBillDto;
	}

	public void setQueryWaybillForHandOverBillDto(
			QueryWaybillForHandOverBillConditionDto queryWaybillForHandOverBillDto) {
		this.queryWaybillForHandOverBillDto = queryWaybillForHandOverBillDto;
	}
	
	public HandOverBillEntity getBaseEntity() {
		return baseEntity;
	}

	public void setBaseEntity(HandOverBillEntity baseEntity) {
		this.baseEntity = baseEntity;
	}

	public List<SerialNoLoadExceptionDto> getPdaSerialNoList() {
		return pdaSerialNoList;
	}

	public void setPdaSerialNoList(List<SerialNoLoadExceptionDto> pdaSerialNoList) {
		this.pdaSerialNoList = pdaSerialNoList;
	}

	public List<HandOverBillSerialNoDetailEntity> getSerialNoList() {
		return serialNoList;
	}

	public void setSerialNoList(List<HandOverBillSerialNoDetailEntity> serialNoList) {
		this.serialNoList = serialNoList;
	}

	public List<HandOverBillOptLogEntity> getHandOverBillOptLogList() {
		return handOverBillOptLogList;
	}

	public void setHandOverBillOptLogList(
			List<HandOverBillOptLogEntity> handOverBillOptLogList) {
		this.handOverBillOptLogList = handOverBillOptLogList;
	}

	public String getHandOverBillNo() {
		return handOverBillNo;
	}

	public void setHandOverBillNo(String handOverBillNo) {
		this.handOverBillNo = handOverBillNo;
	}

	public QueryHandOverBillConditionDto getQueryHandOverBillConditionDto() {
		return queryHandOverBillConditionDto;
	}

	public void setQueryHandOverBillConditionDto(
			QueryHandOverBillConditionDto queryHandOverBillConditionDto) {
		this.queryHandOverBillConditionDto = queryHandOverBillConditionDto;
	}

	public List<QueryHandOverBillDto> getHandOverBillList() {
		return handOverBillList;
	}

	public void setHandOverBillList(List<QueryHandOverBillDto> handOverBillList) {
		this.handOverBillList = handOverBillList;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public List<HandOverBillDetailEntity> getWaybillStockList() {
		return waybillStockList;
	}

	public void setWaybillStockList(List<HandOverBillDetailEntity> waybillStockList) {
		this.waybillStockList = waybillStockList;
	}

	public List<SerialNoStockEntity> getSerialNoStockList() {
		return serialNoStockList;
	}

	public void setSerialNoStockList(List<SerialNoStockEntity> serialNoStockList) {
		this.serialNoStockList = serialNoStockList;
	}

	public NewHandOverBillDto getNewHandOverBillDto() {
		return newHandOverBillDto;
	}

	public void setNewHandOverBillDto(NewHandOverBillDto newHandOverBillDto) {
		this.newHandOverBillDto = newHandOverBillDto;
	}

	public List<String> getHandOverBillNos() {
		return handOverBillNos;
	}

	public void setHandOverBillNos(List<String> handOverBillNos) {
		this.handOverBillNos = handOverBillNos;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public Long getCheckPrintHandOverBillRestlt() {
		return checkPrintHandOverBillRestlt;
	}

	public void setCheckPrintHandOverBillRestlt(Long checkPrintHandOverBillRestlt) {
		this.checkPrintHandOverBillRestlt = checkPrintHandOverBillRestlt;
	}

	public UpdateHandOverBillDto getUpdateHandOverBillDto() {
		return updateHandOverBillDto;
	}

	public void setUpdateHandOverBillDto(UpdateHandOverBillDto updateHandOverBillDto) {
		this.updateHandOverBillDto = updateHandOverBillDto;
	}

	public QuerySerialNoListForWaybillConditionDto getQuerySerialNoListForWaybillConditionDto() {
		return querySerialNoListForWaybillConditionDto;
	}

	public void setQuerySerialNoListForWaybillConditionDto(
			QuerySerialNoListForWaybillConditionDto querySerialNoListForWaybillConditionDto) {
		this.querySerialNoListForWaybillConditionDto = querySerialNoListForWaybillConditionDto;
	}

	public String getHandOverType() {
		return handOverType;
	}

	public void setHandOverType(String handOverType) {
		this.handOverType = handOverType;
	}
	
}