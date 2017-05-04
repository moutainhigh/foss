/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/SaleDepartmentEntity.java
 * 
 * FILE NAME        	: SaleDepartmentEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MapDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 营业部
 * 
 * @author 087584-foss-lijun
 * @date 2012-10-20 下午2:35:52
 */
public class SaleDepartmentEntity  extends BaseEntity {
	
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = -3967231352083822843L;

    /**
    * 数据版本号
    */	
    private Long versionNo;

    /**
    *部门编码
    */	
    private String code;

    /**
    *部门名称
    */	
    private String name;

    /**
    *拼音
    */	
    private String pinyin;

    /**
    *可出发
    */	
    private String leave;

    /**
    *可到达
    */	
    private String arrive;

    /**
    *是否驻地部门
    */	
    private String station;

    /**
    *广告语
    */	
    private String slogans;

    /**
    *开业日期
    */	
    private Date openingDate;

    /**
    *最大临欠额度
    */	
    private BigDecimal maxTempArrears;

    /**
    *已用临欠额度
    */	
    private BigDecimal usedTempArrears;

//    /**
//    *所属集中开单组
//    */	
//    private String billingGroup;
    
//    /**
//    *所属集中开单组
//    */	
//    private String billingGroupName;
    //是否是精准大票
    private String isBigGoods;

    public String getIsBigGoods() {
		return isBigGoods;
	}
	public void setIsBigGoods(String isBigGoods) {
		this.isBigGoods = isBigGoods;
	}


	/**
    *驻地营业部所属外场
    */	
    private String transferCenter;

    /**
    *驻地营业部所属外场
    */	
    private String transferCenterName;

    /**
    * 取消到达日期
    */	
    private Date cancelArrivalDate;

    /**
    * 转货部门
    */	
    private String transferGoodDept;
    
    /**
    * 转货部门
    */	
    private String transferGoodDeptName;
    
    /**
    *可自提
    */	
    private String pickupSelf;

    /**
    *可派送
    */	
    private String delivery;

    /**
    *可空运到达
    */	
    private String airArrive;

    /**
    *可汽运到达
    */	
    private String truckArrive;

    /**
    *单件重量上限
    */	
    private Integer singlePieceLimitkg;

    /**
    *单票重量上限
    */	
    private Integer singleBillLimitkg;

    /**
    *单件体积上限
    */	
    private Integer singlePieceLimitvol;

    /**
    *单票体积上限
    */	
    private Integer singleBillLimitvol;

    /**
    *自提区域描述
    */	
    private String pickupAreaDesc;

    /**
    *派送区域描述
    */	
    private String deliveryAreaDesc;

    /**
    *派送区坐标编号
    */	
    private String deliveryCoordinate;

    /**
    *是否启用
    */	
    private String active;
    
    /**
    * 是否在集中接送货范围内
    */	
    private String inCentralizedShuttle;

    /**
    * 是否可开装卸费
    */	
    private String canPayServiceFee;
    
    /**
     * 是否可开快递一票多件
     */	
     private String canExpressOneMany;

    /**
    * 是否可返回签单
    */	
    private String canReturnSignBill;

    /**
    * 是否可货到付款
    */	
    private String canCashOnDelivery;

    /**
    * 是否可代收货款
    */	
    private String canAgentCollected;
    
    /**
    * 提货网点编码，为4位数字，当为到达部门时必填
    */	
    private String stationNumber;

    /**
     * 所属集中开单组
     */
    private List<MapDto> billingGroupList;
    
    /**
     * 自提区域描述是否扩展
     */
    private String pickAreaIsExpand;
    /**
     * 派送区域描述是否扩展
     */
    private String deliveryAreaIsExpand;
    //快递系统添加属性
    
    /**
     * 可快递返回签单
     */
    private String  canExpressReturnSignBill;   
    /**
     * 可快递接货
     */
    private String canExpressPickupToDoor;
    /**
     * 补录不可修改快递目的站
     */
    private String canUpdateDestination; 
    /**
     * 可快递派送
     */
    private String canExpressDelivery;
    /**
     * 可快递自提
     */
    private String canExpressPickupSelf;
    /**
     * 快递派送区域描述
     */
    private String expressDeliveryAreaDesc;
    /**
     * 快递自提区域描述
     */
    private String expressPickupAreaDesc;
    /**
     * 快递自提区域是否扩展
     */
    private String expressPickupAreaIsExp;
    /**
     * 快递派送区域是否扩展
     */
    private String expressDeliveryAreaIsExp;
    /**
     * 快递派送地图坐标编码
     */
    private String expressDeliveryCoordinate;
    /**
     * 是否卫星点部
     */
    private String satelliteDept;
	/**
     * 快递派送电子地图审核状态(配合DMANA8577-电子地图线上编辑以及数据的监控需求)
     */
    private String verifyState;
    /**
     * 快递派送电子地图审核人工号(配合DMANA8577-电子地图线上编辑以及数据的监控需求)
     */
    private String verifyManCode;
    /**
     * 快递派送电子地图审核时间(配合DMANA8577-电子地图线上编辑以及数据的监控需求)
     */
    private Date verifyTime;
    /**
     * 快递派送电子地图申请时间(配合DMANA8577-电子地图线上编辑以及数据的监控需求)
     */
    private Date applyTime;
    /**
     * 快递派送电子地图申请人工号(配合DMANA8577-电子地图线上编辑以及数据的监控需求)
     */
    private String applyManCode;
    /**
     * 营业部服务面积（平方千米）(配合DMANA8577-电子地图线上编辑以及数据的监控需求)
     */
    private BigDecimal departServiceArea;
    /**
     * 快递员人数(配合DMANA8577-电子地图线上编辑以及数据的监控需求)
     */
  	private int expressManNum;
    /**
     * 是否可上门发货
     */
    private String canExpressDoorToDoor;
  	/**
     * 是否可到快递一票多件（快递开通一票多件外发需求-187862）
     */	
    private String canArriveExpressOneMany;
    /**
      * 是否可货到付款(外发多件)（快递开通一票多件外发需求-187862）
      */	
    private String canCashOnDeliveryMany;
    /**
      * 是否可代收货款(外发多件)（快递开通一票多件外发需求-187862）
      */	
    private String canAgentCollectedMany;
    /**

     * 代收货款上限
     */
    private String agentCollectedUpperLimit;
    /**

     * 是否可家装送装
     */
    private String canHomeImproSend;
    /**
     * 是否加盟网点
     */	
   private String isLeagueSaleDept;
   
   /**
    * 是否二级网点-308861
    */
   private String isTwoLevelNetwork;
   
   /**
    * 网点模式-308861
    */
   private String networkModel;
   
   /**
    * 是否是否二级网点get方法
    */
    public String getIsTwoLevelNetwork() {
    	return isTwoLevelNetwork;
	}
    /**
     * 是否是否二级网点set方法
     */
	public void setIsTwoLevelNetwork(String isTwoLevelNetwork) {
		this.isTwoLevelNetwork = isTwoLevelNetwork;
	}
	/**
    * 网点模式get方法
    */
	public String getNetworkModel() {
		return networkModel;
	}
	/**
    * 网点模式是set方法
    */
	public void setNetworkModel(String networkModel) {
		this.networkModel = networkModel;
	}
	/**
     * 是否卫星点部get方法
     */
	public String getSatelliteDept() {
		return satelliteDept;
	}
    /**
     * 是否卫星点部set方法
     */
	public void setSatelliteDept(String satelliteDept) {
		this.satelliteDept = satelliteDept;
	}
	/**
     * 快递派送坐标编码get方法
     */
    public String getExpressDeliveryCoordinate() {
		return expressDeliveryCoordinate;
	}
    /**
     * 快递派送坐标编码set方法
     */
	public void setExpressDeliveryCoordinate(String expressDeliveryCoordinate) {
		this.expressDeliveryCoordinate = expressDeliveryCoordinate;
	}
	/**
     * @return  the billingGroupList
     */
    public List<MapDto> getBillingGroupList() {
        return billingGroupList;
    }

    
    /**
     * @param billingGroupList the billingGroupList to set
     */
    public void setBillingGroupList(List<MapDto> billingGroupList) {
        this.billingGroupList = billingGroupList;
    }

    /**
     * 
     * <p>是否驻地营业部</p> 
     * @author foss-zhujunyong
     * @date Nov 7, 2012 2:51:13 PM
     * @return
     * @see
     */
    public boolean checkStation(){
	return StringUtils.equals(station, FossConstants.YES);
    }

    /**
     * 
     * <p>是否可到达</p> 
     * @author foss-zhujunyong
     * @date May 28, 2013 4:54:22 PM
     * @return
     * @see
     */
    public boolean checkArrive() {
	return StringUtils.equals(arrive, FossConstants.YES);
    }
    
	/**
	 * @return versionNo
	 */
	public Long getVersionNo() {
		return versionNo;
	}

	/**
	 * @param  versionNo  
	 */
	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}

	/**
	 * @return code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param  code  
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param  name  
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return pinyin
	 */
	public String getPinyin() {
		return pinyin;
	}

	/**
	 * @param  pinyin  
	 */
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	/**
	 * @return leave
	 */
	public String getLeave() {
		return leave;
	}

	/**
	 * @param  leave  
	 */
	public void setLeave(String leave) {
		this.leave = leave;
	}

	/**
	 * @return arrive
	 */
	public String getArrive() {
		return arrive;
	}

	/**
	 * @param  arrive  
	 */
	public void setArrive(String arrive) {
		this.arrive = arrive;
	}

	/**
	 * @return station
	 */
	public String getStation() {
		return station;
	}

	/**
	 * @param  station  
	 */
	public void setStation(String station) {
		this.station = station;
	}

	/**
	 * @return slogans
	 */
	public String getSlogans() {
		return slogans;
	}

	/**
	 * @param  slogans  
	 */
	public void setSlogans(String slogans) {
		this.slogans = slogans;
	}

	/**
	 * @return openingDate
	 */
	public Date getOpeningDate() {
		return openingDate;
	}

	/**
	 * @param  openingDate  
	 */
	public void setOpeningDate(Date openingDate) {
		this.openingDate = openingDate;
	}

	/**
	 * @return maxTempArrears
	 */
	public BigDecimal getMaxTempArrears() {
		return maxTempArrears;
	}

	/**
	 * @param  maxTempArrears  
	 */
	public void setMaxTempArrears(BigDecimal maxTempArrears) {
		this.maxTempArrears = maxTempArrears;
	}

	/**
	 * @return usedTempArrears
	 */
	public BigDecimal getUsedTempArrears() {
		return usedTempArrears;
	}

	/**
	 * @param  usedTempArrears  
	 */
	public void setUsedTempArrears(BigDecimal usedTempArrears) {
		this.usedTempArrears = usedTempArrears;
	}

//	/**
//	 * @return billingGroup
//	 */
//	public String getBillingGroup() {
//		return billingGroup;
//	}
//
//	/**
//	 * @param  billingGroup  
//	 */
//	public void setBillingGroup(String billingGroup) {
//		this.billingGroup = billingGroup;
//	}
//
//	/**
//	 * @return billingGroupName
//	 */
//	public String getBillingGroupName() {
//		return billingGroupName;
//	}
//
//	/**
//	 * @param  billingGroupName  
//	 */
//	public void setBillingGroupName(String billingGroupName) {
//		this.billingGroupName = billingGroupName;
//	}

	/**
	 * @return transferCenter
	 */
	public String getTransferCenter() {
		return transferCenter;
	}

	/**
	 * @param  transferCenter  
	 */
	public void setTransferCenter(String transferCenter) {
		this.transferCenter = transferCenter;
	}

	/**
	 * @return transferCenterName
	 */
	public String getTransferCenterName() {
		return transferCenterName;
	}

	/**
	 * @param  transferCenterName  
	 */
	public void setTransferCenterName(String transferCenterName) {
		this.transferCenterName = transferCenterName;
	}

	/**
	 * @return cancelArrivalDate
	 */
	public Date getCancelArrivalDate() {
		return cancelArrivalDate;
	}

	/**
	 * @param  cancelArrivalDate  
	 */
	public void setCancelArrivalDate(Date cancelArrivalDate) {
		this.cancelArrivalDate = cancelArrivalDate;
	}

	/**
	 * @return transferGoodDept
	 */
	public String getTransferGoodDept() {
		return transferGoodDept;
	}

	/**
	 * @param  transferGoodDept  
	 */
	public void setTransferGoodDept(String transferGoodDept) {
		this.transferGoodDept = transferGoodDept;
	}

	/**
	 * @return transferGoodDeptName
	 */
	public String getTransferGoodDeptName() {
		return transferGoodDeptName;
	}

	/**
	 * @param  transferGoodDeptName  
	 */
	public void setTransferGoodDeptName(String transferGoodDeptName) {
		this.transferGoodDeptName = transferGoodDeptName;
	}

	/**
	 * @return pickupSelf
	 */
	public String getPickupSelf() {
		return pickupSelf;
	}

	/**
	 * @param  pickupSelf  
	 */
	public void setPickupSelf(String pickupSelf) {
		this.pickupSelf = pickupSelf;
	}

	/**
	 * @return delivery
	 */
	public String getDelivery() {
		return delivery;
	}

	/**
	 * @param  delivery  
	 */
	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	/**
	 * @return airArrive
	 */
	public String getAirArrive() {
		return airArrive;
	}

	/**
	 * @param  airArrive  
	 */
	public void setAirArrive(String airArrive) {
		this.airArrive = airArrive;
	}

	/**
	 * @return truckArrive
	 */
	public String getTruckArrive() {
		return truckArrive;
	}

	/**
	 * @param  truckArrive  
	 */
	public void setTruckArrive(String truckArrive) {
		this.truckArrive = truckArrive;
	}

	/**
	 * @return singlePieceLimitkg
	 */
	public Integer getSinglePieceLimitkg() {
		return singlePieceLimitkg;
	}

	/**
	 * @param  singlePieceLimitkg  
	 */
	public void setSinglePieceLimitkg(Integer singlePieceLimitkg) {
		this.singlePieceLimitkg = singlePieceLimitkg;
	}

	/**
	 * @return singleBillLimitkg
	 */
	public Integer getSingleBillLimitkg() {
		return singleBillLimitkg;
	}

	/**
	 * @param  singleBillLimitkg  
	 */
	public void setSingleBillLimitkg(Integer singleBillLimitkg) {
		this.singleBillLimitkg = singleBillLimitkg;
	}

	/**
	 * @return singlePieceLimitvol
	 */
	public Integer getSinglePieceLimitvol() {
		return singlePieceLimitvol;
	}

	/**
	 * @param  singlePieceLimitvol  
	 */
	public void setSinglePieceLimitvol(Integer singlePieceLimitvol) {
		this.singlePieceLimitvol = singlePieceLimitvol;
	}

	/**
	 * @return singleBillLimitvol
	 */
	public Integer getSingleBillLimitvol() {
		return singleBillLimitvol;
	}

	/**
	 * @param  singleBillLimitvol  
	 */
	public void setSingleBillLimitvol(Integer singleBillLimitvol) {
		this.singleBillLimitvol = singleBillLimitvol;
	}

	/**
	 * @return pickupAreaDesc
	 */
	public String getPickupAreaDesc() {
		return pickupAreaDesc;
	}

	/**
	 * @param  pickupAreaDesc  
	 */
	public void setPickupAreaDesc(String pickupAreaDesc) {
		this.pickupAreaDesc = pickupAreaDesc;
	}

	/**
	 * @return deliveryAreaDesc
	 */
	public String getDeliveryAreaDesc() {
		return deliveryAreaDesc;
	}

	/**
	 * @param  deliveryAreaDesc  
	 */
	public void setDeliveryAreaDesc(String deliveryAreaDesc) {
		this.deliveryAreaDesc = deliveryAreaDesc;
	}

	/**
	 * @return deliveryCoordinate
	 */
	public String getDeliveryCoordinate() {
		return deliveryCoordinate;
	}

	/**
	 * @param  deliveryCoordinate  
	 */
	public void setDeliveryCoordinate(String deliveryCoordinate) {
		this.deliveryCoordinate = deliveryCoordinate;
	}

	/**
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param  active  
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return inCentralizedShuttle
	 */
	public String getInCentralizedShuttle() {
		return inCentralizedShuttle;
	}

	/**
	 * @param  inCentralizedShuttle  
	 */
	public void setInCentralizedShuttle(String inCentralizedShuttle) {
		this.inCentralizedShuttle = inCentralizedShuttle;
	}

	/**
	 * @return canPayServiceFee
	 */
	public String getCanPayServiceFee() {
		return canPayServiceFee;
	}

	/**
	 * @param  canPayServiceFee  
	 */
	public void setCanPayServiceFee(String canPayServiceFee) {
		this.canPayServiceFee = canPayServiceFee;
	}

	/**
	 * @return  the canExpressOneMany
	 */
	public String getCanExpressOneMany() {
		return canExpressOneMany;
	}
	/**
	 * @param canExpressOneMany the canExpressOneMany to set
	 */
	public void setCanExpressOneMany(String canExpressOneMany) {
		this.canExpressOneMany = canExpressOneMany;
	}
	/**
	 * @return canReturnSignBill
	 */
	public String getCanReturnSignBill() {
		return canReturnSignBill;
	}

	/**
	 * @param  canReturnSignBill  
	 */
	public void setCanReturnSignBill(String canReturnSignBill) {
		this.canReturnSignBill = canReturnSignBill;
	}

	/**
	 * @return canCashOnDelivery
	 */
	public String getCanCashOnDelivery() {
		return canCashOnDelivery;
	}

	/**
	 * @param  canCashOnDelivery  
	 */
	public void setCanCashOnDelivery(String canCashOnDelivery) {
		this.canCashOnDelivery = canCashOnDelivery;
	}

	/**
	 * @return canAgentCollected
	 */
	public String getCanAgentCollected() {
		return canAgentCollected;
	}

	/**
	 * @param  canAgentCollected  
	 */
	public void setCanAgentCollected(String canAgentCollected) {
		this.canAgentCollected = canAgentCollected;
	}

	/**
	 * @return stationNumber
	 */
	public String getStationNumber() {
		return stationNumber;
	}

	/**
	 * @param  stationNumber  
	 */
	public void setStationNumber(String stationNumber) {
		this.stationNumber = stationNumber;
	}


	public String getPickAreaIsExpand() {
		return pickAreaIsExpand;
	}


	public void setPickAreaIsExpand(String pickAreaIsExpand) {
		this.pickAreaIsExpand = pickAreaIsExpand;
	}


	public String getDeliveryAreaIsExpand() {
		return deliveryAreaIsExpand;
	}


	public void setDeliveryAreaIsExpand(String deliveryAreaIsExpand) {
		this.deliveryAreaIsExpand = deliveryAreaIsExpand;
	}


	public String getCanExpressReturnSignBill() {
		return canExpressReturnSignBill;
	}


	public void setCanExpressReturnSignBill(String canExpressReturnSignBill) {
		this.canExpressReturnSignBill = canExpressReturnSignBill;
	}


	public String getCanExpressPickupToDoor() {
		return canExpressPickupToDoor;
	}


	public void setCanExpressPickupToDoor(String canExpressPickupToDoor) {
		this.canExpressPickupToDoor = canExpressPickupToDoor;
	}


	public String getCanUpdateDestination() {
		return canUpdateDestination;
	}
	 
	 
	public void setCanUpdateDestination(String canUpdateDestination) {
		this.canUpdateDestination = canUpdateDestination;
	}
	
	
	public String getCanExpressDelivery() {
		return canExpressDelivery;
	}


	public void setCanExpressDelivery(String canExpressDelivery) {
		this.canExpressDelivery = canExpressDelivery;
	}


	public String getCanExpressPickupSelf() {
		return canExpressPickupSelf;
	}


	public void setCanExpressPickupSelf(String canExpressPickupSelf) {
		this.canExpressPickupSelf = canExpressPickupSelf;
	}


	public String getExpressDeliveryAreaDesc() {
		return expressDeliveryAreaDesc;
	}


	public void setExpressDeliveryAreaDesc(String expressDeliveryAreaDesc) {
		this.expressDeliveryAreaDesc = expressDeliveryAreaDesc;
	}


	public String getExpressPickupAreaDesc() {
		return expressPickupAreaDesc;
	}


	public void setExpressPickupAreaDesc(String expressPickupAreaDesc) {
		this.expressPickupAreaDesc = expressPickupAreaDesc;
	}


	public String getExpressPickupAreaIsExp() {
		return expressPickupAreaIsExp;
	}


	public void setExpressPickupAreaIsExp(String expressPickupAreaIsExp) {
		this.expressPickupAreaIsExp = expressPickupAreaIsExp;
	}


	public String getExpressDeliveryAreaIsExp() {
		return expressDeliveryAreaIsExp;
	}


	public void setExpressDeliveryAreaIsExp(String expressDeliveryAreaIsExp) {
		this.expressDeliveryAreaIsExp = expressDeliveryAreaIsExp;
	}
	/**
	 * @return  the verifyState
	 */
	public String getVerifyState() {
		return verifyState;
	}
	/**
	 * @param verifyState the verifyState to set
	 */
	public void setVerifyState(String verifyState) {
		this.verifyState = verifyState;
	}
	/**
	 * @return  the verifyManCode
	 */
	public String getVerifyManCode() {
		return verifyManCode;
	}
	/**
	 * @param verifyManCode the verifyManCode to set
	 */
	public void setVerifyManCode(String verifyManCode) {
		this.verifyManCode = verifyManCode;
	}
	/**
	 * @return  the verifyTime
	 */
	public Date getVerifyTime() {
		return verifyTime;
	}
	/**
	 * @param verifyTime the verifyTime to set
	 */
	public void setVerifyTime(Date verifyTime) {
		this.verifyTime = verifyTime;
	}
	/**
	 * @return  the departServiceArea
	 */
	public BigDecimal getDepartServiceArea() {
		return departServiceArea;
	}
	/**
	 * @param departServiceArea the departServiceArea to set
	 */
	public void setDepartServiceArea(BigDecimal departServiceArea) {
		this.departServiceArea = departServiceArea;
	}
	/**
	 * @return  the expressManNum
	 */
	public int getExpressManNum() {
		return expressManNum;
	}
	/**
	 * @param expressManNum the expressManNum to set
	 */
	public void setExpressManNum(int expressManNum) {
		this.expressManNum = expressManNum;
	}
	/**
	 * @return  the applyTime
	 */
	public Date getApplyTime() {
		return applyTime;
	}
	/**
	 * @param applyTime the applyTime to set
	 */
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	/**
	 * @return  the applyManCode
	 */
	public String getApplyManCode() {
		return applyManCode;
	}
	/**
	 * @param applyManCode the applyManCode to set
	 */
	public void setApplyManCode(String applyManCode) {
		this.applyManCode = applyManCode;
	}
	public String getCanExpressDoorToDoor() {
		return canExpressDoorToDoor;
	}
	public void setCanExpressDoorToDoor(String canExpressDoorToDoor) {
		this.canExpressDoorToDoor = canExpressDoorToDoor;
	}
	public String getCanArriveExpressOneMany() {
		return canArriveExpressOneMany;
	}
	public void setCanArriveExpressOneMany(String canArriveExpressOneMany) {
		this.canArriveExpressOneMany = canArriveExpressOneMany;
	}
	public String getCanCashOnDeliveryMany() {
		return canCashOnDeliveryMany;
	}
	public void setCanCashOnDeliveryMany(String canCashOnDeliveryMany) {
		this.canCashOnDeliveryMany = canCashOnDeliveryMany;
	}
	public String getCanAgentCollectedMany() {
		return canAgentCollectedMany;
	}
	public void setCanAgentCollectedMany(String canAgentCollectedMany) {
		this.canAgentCollectedMany = canAgentCollectedMany;
	}

	public String getAgentCollectedUpperLimit() {
		return agentCollectedUpperLimit;
	}
	public void setAgentCollectedUpperLimit(String agentCollectedUpperLimit) {
		this.agentCollectedUpperLimit = agentCollectedUpperLimit;
	}

	public String getCanHomeImproSend() {
		return canHomeImproSend;
	}
	public void setCanHomeImproSend(String canHomeImproSend) {
		this.canHomeImproSend = canHomeImproSend;
	}
	public String getIsLeagueSaleDept() {
		return isLeagueSaleDept;
	}
	public void setIsLeagueSaleDept(String isLeagueSaleDept) {
		this.isLeagueSaleDept = isLeagueSaleDept;
	}
	
}