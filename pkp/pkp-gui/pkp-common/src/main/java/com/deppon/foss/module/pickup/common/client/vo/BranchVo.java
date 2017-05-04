/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/vo/BranchVo.java
 * 
 * FILE NAME        	: BranchVo.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.common.client.vo;

import java.util.Date;

/**
 * 
 * 提货网点
 * 
 * @author 025000-FOSS-helong
 * @date 2012-12-13 下午05:53:34
 */
public class BranchVo {

	/**
	 * 网点名称
	 */
	private String name;


	/**
	 * 网点编号
	 */
	private String code;

	/**
	 * 单件重量上限
	 */
	private Integer singlePieceLimitkg;

	/**
	 * 单票重量上限
	 */
	private Integer singleBillLimitkg;

	/**
	 * 单件体积上限
	 */
	private Integer singlePieceLimitvol;

	/**
	 * 单票体积上限
	 */
	private Integer singleBillLimitvol;
	
	/**
	 * 目的站
	 */
	private String targetOrgName;
	
    /**
     * 是否可代收货款
	 */	
	private String canAgentCollected;
	
	/**
	 * 是否可以货到付款
	 */
	private String arriveCharge;
	
   /**
    * 是否可自提.
    */
    private String pickupSelf;

   /**
    * 是否送货上门.
    */
    private String delivery;
    
    
    /**
     * 是否做到达
     */
    private String arrive;
    
    
    /**
     * 取消到达日期
     */	
     private Date cancelArrivalDate;

     /**
     * 转货部门
     */	
     private String transferGoodDept;
     
    /**
     * 是否可返回签单
     */	
    private String canReturnSignBill;

    /**
     * 是否可返回签单
     */	
	public String getCanReturnSignBill() {
		return canReturnSignBill;
	}
	
	/**
	 * 是否加盟网点
	 */
	private String isLeagueSaledept;
	
	/**
	* 是否二级网点-308861
	*/
	private String isTwoLevelNetwork;
	   
	/**
	* 网点模式-308861
	*/
	private String networkModel;
	
	public String getIsLeagueSaledept() {
		return isLeagueSaledept;
	}

	public void setIsLeagueSaledept(String isLeagueSaledept) {
		this.isLeagueSaledept = isLeagueSaledept;
	}

	public String getIsTwoLevelNetwork() {
		return isTwoLevelNetwork;
	}

	public void setIsTwoLevelNetwork(String isTwoLevelNetwork) {
		this.isTwoLevelNetwork = isTwoLevelNetwork;
	}

	public String getNetworkModel() {
		return networkModel;
	}

	public void setNetworkModel(String networkModel) {
		this.networkModel = networkModel;
	}

	/**
     * 是否可返回签单
     */	
	public void setCanReturnSignBill(String canReturnSignBill) {
		this.canReturnSignBill = canReturnSignBill;
	}
	
	
	  private String canExpressReturnSignBill;

	    /**
	     * 是否快递可返回签单
	     */	
	  public String getCanExpressReturnSignBill() {
			return canExpressReturnSignBill;
		}
		
		/**
	     * 是否快递可返回签单
	     */	
	  public void setCanExpressReturnSignBill(String canExpressReturnSignBill) {
			this.canExpressReturnSignBill = canExpressReturnSignBill;
		}
		
		
	/**
     * 该外部部门或者营业部所属的城市code
     */
    private String cityCode;

	/**
	 * 网点名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 网点名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 网点编号
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 网点编号
	 */
	public void setCode(String code) {
		this.code = code;
	}

	
	
	/**
	 * 单件重量上限
	 */
	public Integer getSinglePieceLimitkg() {
		return singlePieceLimitkg;
	}

	
	/**
	 * 单件重量上限
	 */
	public void setSinglePieceLimitkg(Integer singlePieceLimitkg) {
		this.singlePieceLimitkg = singlePieceLimitkg;
	}

	

	/**
	 * 单票重量上限
	 */
	public Integer getSingleBillLimitkg() {
		return singleBillLimitkg;
	}


	/**
	 * 单票重量上限
	 */
	public void setSingleBillLimitkg(Integer singleBillLimitkg) {
		this.singleBillLimitkg = singleBillLimitkg;
	}

	
	/**
	 * 单件体积上限
	 */
	public Integer getSinglePieceLimitvol() {
		return singlePieceLimitvol;
	}

	/**
	 * 单件体积上限
	 */
	public void setSinglePieceLimitvol(Integer singlePieceLimitvol) {
		this.singlePieceLimitvol = singlePieceLimitvol;
	}

	/**
	 * 单票体积上限
	 */
	public Integer getSingleBillLimitvol() {
		return singleBillLimitvol;
	}

	/**
	 * 单票体积上限
	 */
	public void setSingleBillLimitvol(Integer singleBillLimitvol) {
		this.singleBillLimitvol = singleBillLimitvol;
	}

    /**
     * 取消到达日期
     */	
   public Date getCancelArrivalDate() {
		return cancelArrivalDate;
	}
   /**
    * 取消到达日期
    */	
	public void setCancelArrivalDate(Date cancelArrivalDate) {
		this.cancelArrivalDate = cancelArrivalDate;
	}
	
	 /**
    * 转货部门
    */	
	public String getTransferGoodDept() {
		return transferGoodDept;
	}
	 /**
    * 转货部门
    */	
	public void setTransferGoodDept(String transferGoodDept) {
		this.transferGoodDept = transferGoodDept;
	}
	
    /**
     * 该外部部门或者营业部所属的城市code
	 * @return the cityCode
	 */
	public String getCityCode() {
		return cityCode;
	}

	/**
	 * 该外部部门或者营业部所属的城市code
	 * @param cityCode the cityCode to set
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	/**
     * 是否可自提.
     * @return the pickupSelf
     */
    public String getPickupSelf() {
    	return pickupSelf;
    }

	/**
	 * 是否可自提.
	 * @param pickupSelf the pickupSelf to set
	 */
	public void setPickupSelf(String pickupSelf) {
		this.pickupSelf = pickupSelf;
	}
	
	/**
	 * 是否送货上门.
	 * @return the pickupToDoor
	 */
	public String getDelivery() {
		return delivery;
	}
	
	/**
	 * 是否送货上门.
	 * @param pickupToDoor the pickupToDoor to set
	 */
	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	/**
     * 是否可代收货款
	 */	
	public String getCanAgentCollected() {
		return canAgentCollected;
	}

    /**
     * 是否可代收货款
	 */	
	public void setCanAgentCollected(String canAgentCollected) {
		this.canAgentCollected = canAgentCollected;
	}

	/**
	 * @return the targetOrgName
	 */
	public String getTargetOrgName() {
		return targetOrgName;
	}

	/**
	 * @param targetOrgName the targetOrgName to set
	 */
	public void setTargetOrgName(String targetOrgName) {
		this.targetOrgName = targetOrgName;
	}

	public String getArriveCharge() {
		return arriveCharge;
	}

	public void setArriveCharge(String arriveCharge) {
		this.arriveCharge = arriveCharge;
	}

	public String getArrive() {
		return arrive;
	}

	public void setArrive(String arrive) {
		this.arrive = arrive;
	}
	
}