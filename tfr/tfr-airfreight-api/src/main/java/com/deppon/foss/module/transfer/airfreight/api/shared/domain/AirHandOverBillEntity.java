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
 *  FILE PATH          :/AirHandOverBillEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 航空交接单实体
 * @author 038300-foss-pengzhen
 * @date 2012-12-25 下午6:46:15
 */
public class AirHandOverBillEntity {
	private String id;  
	/**
	 * 交接部门编号
	 */
    private String orgCode; 
    /**
     * 交接部门名称
     */
    private String orgName;  
    /**
     * 交接单号
     */
    private String airHandoverNo; 
  /**
   * 航班日期
   */
    private Date flightDate;  
  /**
   * 航班号
   */
    private String flightNo;  
    /**
     * 起飞时间
     */
    private Date takeOffTime; 
    /**
     * 订舱号
     */
    private String bookingNo;  
    /**
     * 舱位重量
     */
    private BigDecimal spaceWeight;  
    /**
     * 交接单位
     */
    private String handoverOrg;  
    /**
     * 交接人
     */
    private String handoverEmp;  
    /**
     * 备注
     */
    private String notes; 
    /**
     * 制单人名称
     */
    private String createUserName;  
    /**
     * 制单人编号
     */
    private String createUserCode; 
  /**
   * 创建时间
   */
    private Date createTime;  
  /**
   * 修改人编号
   */
    private String modifyUserCode;  
  /**
   * 修改人名称
   */
    private String modifyUserName;  
  /**
   * 修改时间
   */
    private Date modifyTime;  
  /**
   * 总票数
   */
    private Integer waybillQtyTotal;  
  /**
   * 总件数
   */
    private Integer goodsQtyTotal;  
    /**
     * 总毛重
     */
    private BigDecimal grossWeightTotal; 
    /**
     * 计费总重量
     */
    private BigDecimal billingWeightTotal; 
    /**
     * 正单库存状态
     */
    private String airWaybillStockState; 

    /**
     * 等级
     */
    private String airLevel; 
    
    /**
     * 交接类型
     * */
    private String airHandOverType;
    
    /**
     *交接 总体积
     * */

    private BigDecimal handoverVolumeTotal; 
    /**
     * 快递卸车状态 取消:CANCLED，未开始:UNSTART,卸车中:UNLOADING  完成:FINISHED
     * */
    private String expressUnloadStatus;
    
    
    /**
     * 快递到达下一部门code
     * */
    private String expressArriveCode;
    
    /**
     * 快递到达下一部门名称
     * */
    private String expressArriveName;
    
    /**
     * 快递卸车任务分配状态 已分配-ASSINGED 未分配-UNASSIGN
     * */
    private String expressAssignStatus;
    
	/**
	 * 
	 *
	 * @return 
	 */
	public String getId() {
        return id;
    }

    /**
     * 
     *
     * @param id 
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取 交接部门编号.
     *
     * @return the 交接部门编号
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * 设置 交接部门编号.
     *
     * @param orgCode the new 交接部门编号
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    /**
     * 获取 交接部门名称.
     *
     * @return the 交接部门名称
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * 设置 交接部门名称.
     *
     * @param orgName the new 交接部门名称
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * 获取 交接单号.
     *
     * @return the 交接单号
     */
    public String getAirHandoverNo() {
		return airHandoverNo;
	}

	/**
	 * 设置 交接单号.
	 *
	 * @param airHandoverNo the new 交接单号
	 */
	public void setAirHandoverNo(String airHandoverNo) {
		this.airHandoverNo = airHandoverNo;
	}

	/**
	 * 获取 航班日期.
	 *
	 * @return the 航班日期
	 */
	public Date getFlightDate() {
        return flightDate;
    }

    /**
     * 设置 航班日期.
     *
     * @param flightDate the new 航班日期
     */
    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }

    /**
     * 获取 航班号.
     *
     * @return the 航班号
     */
    public String getFlightNo() {
        return flightNo;
    }

    /**
     * 设置 航班号.
     *
     * @param flightNo the new 航班号
     */
    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    /**
     * 获取 起飞时间.
     *
     * @return the 起飞时间
     */
    public Date getTakeOffTime() {
        return takeOffTime;
    }

    /**
     * 设置 起飞时间.
     *
     * @param takeOffTime the new 起飞时间
     */
    public void setTakeOffTime(Date takeOffTime) {
        this.takeOffTime = takeOffTime;
    }

    /**
     * 获取 订舱号.
     *
     * @return the 订舱号
     */
    public String getBookingNo() {
        return bookingNo;
    }

    /**
     * 设置 订舱号.
     *
     * @param bookingNo the new 订舱号
     */
    public void setBookingNo(String bookingNo) {
        this.bookingNo = bookingNo;
    }

    /**
     * 获取 舱位重量.
     *
     * @return the 舱位重量
     */
    public BigDecimal getSpaceWeight() {
        return spaceWeight;
    }

    /**
     * 设置 舱位重量.
     *
     * @param spaceWeight the new 舱位重量
     */
    public void setSpaceWeight(BigDecimal spaceWeight) {
        this.spaceWeight = spaceWeight;
    }

    /**
     * 获取 交接单位.
     *
     * @return the 交接单位
     */
    public String getHandoverOrg() {
        return handoverOrg;
    }

    /**
     * 设置 交接单位.
     *
     * @param handoverOrg the new 交接单位
     */
    public void setHandoverOrg(String handoverOrg) {
        this.handoverOrg = handoverOrg;
    }

    /**
     * 获取 交接人.
     *
     * @return the 交接人
     */
    public String getHandoverEmp() {
        return handoverEmp;
    }

    /**
     * 设置 交接人.
     *
     * @param handoverEmp the new 交接人
     */
    public void setHandoverEmp(String handoverEmp) {
        this.handoverEmp = handoverEmp;
    }

    /**
     * 获取 备注.
     *
     * @return the 备注
     */
    public String getNotes() {
        return notes;
    }

    /**
     * 设置 备注.
     *
     * @param notes the new 备注
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * 获取 制单人名称.
     *
     * @return the 制单人名称
     */
    public String getCreateUserName() {
        return createUserName;
    }

    /**
     * 设置 制单人名称.
     *
     * @param createUserName the new 制单人名称
     */
    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    /**
     * 获取 制单人编号.
     *
     * @return the 制单人编号
     */
    public String getCreateUserCode() {
        return createUserCode;
    }

    /**
     * 设置 制单人编号.
     *
     * @param createUserCode the new 制单人编号
     */
    public void setCreateUserCode(String createUserCode) {
        this.createUserCode = createUserCode;
    }

    /**
     * 获取 创建时间.
     *
     * @return the 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置 创建时间.
     *
     * @param createTime the new 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取 修改人编号.
     *
     * @return the 修改人编号
     */
    public String getModifyUserCode() {
        return modifyUserCode;
    }

    /**
     * 设置 修改人编号.
     *
     * @param modifyUserCode the new 修改人编号
     */
    public void setModifyUserCode(String modifyUserCode) {
        this.modifyUserCode = modifyUserCode;
    }

    /**
     * 获取 修改人名称.
     *
     * @return the 修改人名称
     */
    public String getModifyUserName() {
        return modifyUserName;
    }

    /**
     * 设置 修改人名称.
     *
     * @param modifyUserName the new 修改人名称
     */
    public void setModifyUserName(String modifyUserName) {
        this.modifyUserName = modifyUserName;
    }

    /**
     * 获取 修改时间.
     *
     * @return the 修改时间
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 设置 修改时间.
     *
     * @param modifyTime the new 修改时间
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
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
     * 获取 总毛重.
     *
     * @return the 总毛重
     */
    public BigDecimal getGrossWeightTotal() {
        return grossWeightTotal;
    }

    /**
     * 设置 总毛重.
     *
     * @param grossWeightTotal the new 总毛重
     */
    public void setGrossWeightTotal(BigDecimal grossWeightTotal) {
        this.grossWeightTotal = grossWeightTotal;
    }

    /**
     * 获取 计费总重量.
     *
     * @return the 计费总重量
     */
    public BigDecimal getBillingWeightTotal() {
        return billingWeightTotal;
    }

    /**
     * 设置 计费总重量.
     *
     * @param billingWeightTotal the new 计费总重量
     */
    public void setBillingWeightTotal(BigDecimal billingWeightTotal) {
        this.billingWeightTotal = billingWeightTotal;
    }

    /**
     * 获取 正单库存状态.
     *
     * @return the 正单库存状态
     */
    public String getAirWaybillStockState() {
        return airWaybillStockState;
    }

    /**
     * 设置 正单库存状态.
     *
     * @param airWaybillStockState the new 正单库存状态
     */
    public void setAirWaybillStockState(String airWaybillStockState) {
        this.airWaybillStockState = airWaybillStockState;
    }

	public String getAirLevel() {
		return airLevel;
	}

	public void setAirLevel(String airLevel) {
		this.airLevel = airLevel;
	}

	public String getAirHandOverType() {
		return airHandOverType;
	}

	public void setAirHandOverType(String airHandOverType) {
		this.airHandOverType = airHandOverType;
	}

	public BigDecimal getHandoverVolumeTotal() {
		return handoverVolumeTotal;
	}

	public void setHandoverVolumeTotal(BigDecimal handoverVolumeTotal) {
		this.handoverVolumeTotal = handoverVolumeTotal;
	}

	public String getExpressUnloadStatus() {
		return expressUnloadStatus;
	}

	public void setExpressUnloadStatus(String expressUnloadStatus) {
		this.expressUnloadStatus = expressUnloadStatus;
	}

	public String getExpressArriveCode() {
		return expressArriveCode;
	}

	public void setExpressArriveCode(String expressArriveCode) {
		this.expressArriveCode = expressArriveCode;
	}

	public String getExpressArriveName() {
		return expressArriveName;
	}

	public void setExpressArriveName(String expressArriveName) {
		this.expressArriveName = expressArriveName;
	}

	public String getExpressAssignStatus() {
		return expressAssignStatus;
	}

	public void setExpressAssignStatus(String expressAssignStatus) {
		this.expressAssignStatus = expressAssignStatus;
	}
    
}