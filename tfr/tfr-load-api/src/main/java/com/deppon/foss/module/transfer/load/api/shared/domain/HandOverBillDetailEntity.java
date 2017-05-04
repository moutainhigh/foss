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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/domain/HandOverBillDetailEntity.java
 *  
 *  FILE NAME          :HandOverBillDetailEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;

/**
 * 
 * 交接单运单库存实体
 * @author 045923-foss-shiwei
 * @date 2012-10-12 上午8:59:47
 */
public class HandOverBillDetailEntity extends BaseEntity{

	private static final long serialVersionUID = -7558984709673959271L;
	
	//运单号
	private String waybillNo;
	//交接单编号
	private String handOverBillNo;
	//交接类型
	private String handOverType;
	//运输性质
	private String transProperty;
	//运输性质code
	private String transPropertyCode;
	//实际重量
	private BigDecimal weightAc;
	//实际体积
	private BigDecimal cubageAc;
	//货物名称
	private String goodsName;
	//包装
	private String packing;
	//体积
	private BigDecimal cubage;
	//重量
	private BigDecimal weight;
	//件数
	private BigDecimal pieces;
	//入库日期
	private Date instorageDate;
	//到达部门
	private String arriveDept;
	//保险价值
	private BigDecimal insuranceValue;
	//开单日期
	private Date waybillDate;
	//开单件数
	private BigDecimal waybillPieces;
	//是否贵重物品
	private String isPreciousGoods;
	//运单备注
	private String waybillNote;
	//备注
	private String note;
	//收货部门
	private String receiveOrgName;
	//收货人
	private String consignee;
	//目的站
	private String destination;
	//运单金额
	private BigDecimal waybillFee;
    //币种
    private String currencyCode;
    //出发部门编号
    private String origOrgCode;
    //预计到达时间
    private Date planArriveTime;
    //代收货款
    private BigDecimal codAmount;
    //是否整车
    private String isCarLoad;
    //是否必走货
    private String isPriorityGoods;
    //是否合车
    private String isJoinCar;
    //是否优先货
    private String isFastGoods;
    //库区编码
    private String goodsAreaCode;
    //库区名称
    private String goodsAreaName;
    //库区类别
    private String goodsAreaType;
    //此字段区分快递的笼/包/运单
    private String cargoType;
    //件号
    private String cargoNo;
    //配载单号
    private String vehicleAssembleNo;
    //实际体积
  	//private BigDecimal actualCubage;
  	//实际重量
  	//private BigDecimal actualWeight;
  	
    //库存流水号list
    private List<SerialNoStockEntity> serialNoStockList;
    //已交接流水号list
    private List<HandOverBillSerialNoDetailEntity> serialNoHandOveredList;
   
    
    /**
     * 货物类型
     */
    private String goodsType;
    
    /**
     * 提货网点code
     */
    private String pkpOrgCode;
    
    /**
     * 提货网点name
     */
    private String pkpOrgName;
    
	public String getPkpOrgCode() {
		return pkpOrgCode;
	}
	public void setPkpOrgCode(String pkpOrgCode) {
		this.pkpOrgCode = pkpOrgCode;
	}
	public String getPkpOrgName() {
		return pkpOrgName;
	}
	public void setPkpOrgName(String pkpOrgName) {
		this.pkpOrgName = pkpOrgName;
	}
	public List<HandOverBillSerialNoDetailEntity> getSerialNoHandOveredList() {
		return serialNoHandOveredList;
	}
	public void setSerialNoHandOveredList(
			List<HandOverBillSerialNoDetailEntity> serialNoHandOveredList) {
		this.serialNoHandOveredList = serialNoHandOveredList;
	}
	public List<SerialNoStockEntity> getSerialNoStockList() {
		return serialNoStockList;
	}
	public void setSerialNoStockList(List<SerialNoStockEntity> serialNoStockList) {
		this.serialNoStockList = serialNoStockList;
	}
	public String getGoodsAreaName() {
		return goodsAreaName;
	}
	public void setGoodsAreaName(String goodsAreaName) {
		this.goodsAreaName = goodsAreaName;
	}
	public String getIsFastGoods() {
		return isFastGoods;
	}
	public void setIsFastGoods(String isFastGoods) {
		this.isFastGoods = isFastGoods;
	}
	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}
	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}
	public String getGoodsAreaType() {
		return goodsAreaType;
	}
	public void setGoodsAreaType(String goodsAreaType) {
		this.goodsAreaType = goodsAreaType;
	}
	public String getIsJoinCar() {
		return isJoinCar;
	}
	public void setIsJoinCar(String isJoinCar) {
		this.isJoinCar = isJoinCar;
	}
	public String getIsPriorityGoods() {
		return isPriorityGoods;
	}
	public void setIsPriorityGoods(String isPriorityGoods) {
		this.isPriorityGoods = isPriorityGoods;
	}
	public BigDecimal getCodAmount() {
		return codAmount;
	}
	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}
	public String getIsCarLoad() {
		return isCarLoad;
	}
	public void setIsCarLoad(String isCarLoad) {
		this.isCarLoad = isCarLoad;
	}
	@DateFormat
	public Date getPlanArriveTime() {
		return planArriveTime;
	}
	@DateFormat
	public void setPlanArriveTime(Date planArriveTime) {
		this.planArriveTime = planArriveTime;
	}
	public String getHandOverBillNo() {
		return handOverBillNo;
	}
	public void setHandOverBillNo(String handOverBillNo) {
		this.handOverBillNo = handOverBillNo;
	}
	public String getHandOverType() {
		return handOverType;
	}
	public void setHandOverType(String handOverType) {
		this.handOverType = handOverType;
	}
	public String getTransProperty() {
		return transProperty;
	}
	public void setTransProperty(String transProperty) {
		this.transProperty = transProperty;
	}
	public BigDecimal getWeightAc() {
		return weightAc;
	}
	public void setWeightAc(BigDecimal weightAc) {
		this.weightAc = weightAc;
	}
	public BigDecimal getCubageAc() {
		return cubageAc;
	}
	public void setCubageAc(BigDecimal cubageAc) {
		this.cubageAc = cubageAc;
	}
	public String getIsPreciousGoods() {
		return isPreciousGoods;
	}
	public void setIsPreciousGoods(String isPreciousGoods) {
		this.isPreciousGoods = isPreciousGoods;
	}
	public String getReceiveOrgName() {
		return receiveOrgName;
	}
	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getOrigOrgCode() {
		return origOrgCode;
	}
	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getPacking() {
		return packing;
	}
	public void setPacking(String packing) {
		this.packing = packing;
	}
	public BigDecimal getCubage() {
		return cubage;
	}
	public void setCubage(BigDecimal cubage) {
		this.cubage = cubage;
	}
	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	public BigDecimal getPieces() {
		return pieces;
	}
	public void setPieces(BigDecimal pieces) {
		this.pieces = pieces;
	}
	@DateFormat
	public Date getInstorageDate() {
		return instorageDate;
	}
	@DateFormat
	public void setInstorageDate(Date instorageDate) {
		this.instorageDate = instorageDate;
	}
	public String getArriveDept() {
		return arriveDept;
	}
	public void setArriveDept(String arriveDept) {
		this.arriveDept = arriveDept;
	}
	public BigDecimal getInsuranceValue() {
		return insuranceValue;
	}
	public void setInsuranceValue(BigDecimal insuranceValue) {
		this.insuranceValue = insuranceValue;
	}
	@DateFormat
	public Date getWaybillDate() {
		return waybillDate;
	}
	@DateFormat
	public void setWaybillDate(Date waybillDate) {
		this.waybillDate = waybillDate;
	}
	public BigDecimal getWaybillPieces() {
		return waybillPieces;
	}
	public void setWaybillPieces(BigDecimal waybillPieces) {
		this.waybillPieces = waybillPieces;
	}
	public String getWaybillNote() {
		return waybillNote;
	}
	public void setWaybillNote(String waybillNote) {
		this.waybillNote = waybillNote;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public BigDecimal getWaybillFee() {
		return waybillFee;
	}
	public void setWaybillFee(BigDecimal waybillFee) {
		this.waybillFee = waybillFee;
	}
	public String getTransPropertyCode() {
		return transPropertyCode;
	}
	public void setTransPropertyCode(String transPropertyCode) {
		this.transPropertyCode = transPropertyCode;
	}
	/**   
	 * goodsType   
	 *   
	 * @return  the goodsType   
	 */
	
	public String getGoodsType() {
		return goodsType;
	}
	/**   
	 * @param goodsType the goodsType to set
	 * Date:2013-8-2下午6:13:46
	 */
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	/**
	* @description 获取 货物类型 
	* @return
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年5月5日 下午1:53:43
	 */
	public String getCargoType() {
		return cargoType;
	}
	
	/**
	* @description 设置  货物类型
	* @param cargoType
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年5月5日 下午1:54:30
	 */
	public void setCargoType(String cargoType) {
		this.cargoType = cargoType;
	}
	public String getCargoNo() {
		return cargoNo;
	}
	public void setCargoNo(String cargoNo) {
		this.cargoNo = cargoNo;
	}
	public String getVehicleAssembleNo() {
		return vehicleAssembleNo;
	}
	public void setVehicleAssembleNo(String vehicleAssembleNo) {
		this.vehicleAssembleNo = vehicleAssembleNo;
	}
	
	
//	public BigDecimal getActualCubage() {
//		return actualCubage;
//	}
//	public void setActualCubage(BigDecimal actualCubage) {
//		this.actualCubage = actualCubage;
//	}
//	public BigDecimal getActualWeight() {
//		return actualWeight;
//	}
//	public void setActualWeight(BigDecimal actualWeight) {
//		this.actualWeight = actualWeight;
//	}
	
	
}