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
 *  
 *  PROJECT NAME  : tfr-scheduling-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/domain/TransportPathEntity.java
 * 
 *  FILE NAME     :TransportPathEntity.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.scheduling.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;

/**
 * 走货路径entity
 */
public class TransportPathEntity extends BaseEntity{
	
	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 1212561811462013889L;
	/**
	 * 主键ID
	 */
	private String transportPathId;
	/**
	 * 运单号
	 */
    private String waybillNo;
    /**
     * 开单时间
     */
    private Date billingTime;
    /**
     * 开单所属部门编号
     */
    private String billingOrgCode;
    
    private String billingOrgCodeName;
    /**
     * 最终到达部门编号
     */
    private String destOrgCode;
    
    private String destOrgCodeName;
    /**
     * 现所在部门编号
     */
    private String currentOrgCode;
    
    private String currentOrgCodeName;
    /**
     * 总重量
     */
    private BigDecimal totalWeight;
    /**
     * 总体积
     */
    private BigDecimal totalVolume;
    /**
     * 运单总货件数量
     */
    private Integer goodsQtyTotal;
    /**
     * 运输类型
     */
    private String transportModel;
    /**
     * 运输车辆牌号
     */
    private String vehicleNo;
    /**
     * 走货路径
     */
	private String transportPath;
	/**
	 * 现所处状态
	 */
    private String action;
    /**
     * 上一状态
     */
    private String beforeAction;
    /**
     * 包装部门编号
     */
    private String packingOrgCode;
    
    private String packingOrgCodeName;
    /**
     * 下一到达部门编号
     */
    private String nextOrgCode;
    
    private String nextOrgCodeName;
    /**
     * 预计出发时间
     */
    private Date planStartTime;
    /**
     * 预计到达时间
     */
    private Date planArriveTime;
    /**
     * 是否分批配载
     */
    private String ifPartialStowage;
    /**
     * 包装后件数
     */
    private Integer packedNum;
    
    /**
     * 计算同一个运单同时有多少个流水号走同一条路径
     */
    private String waybillNoCount;

    /**
     * 获取 主键ID.
     *
     * @return the 主键ID
     */
    public String getTransportPathId() {
        return transportPathId;
    }

    /**
     * 设置 主键ID.
     *
     * @param transportPathId the new 主键ID
     */
    public void setTransportPathId(String transportPathId) {
        this.transportPathId = transportPathId;
    }

    /**
     * 获取 运单号.
     *
     * @return the 运单号
     */
    public String getWaybillNo() {
        return waybillNo;
    }

    /**
     * 设置 运单号.
     *
     * @param waybillNo the new 运单号
     */
    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }
    
    /**
     * 获取 开单时间.
     *
     * @return the 开单时间
     */
    @DateFormat
    public Date getBillingTime() {
        return billingTime;
    }
    
    /**
     * 设置 开单时间.
     *
     * @param billingTime the new 开单时间
     */
    @DateFormat
    public void setBillingTime(Date billingTime) {
        this.billingTime = billingTime;
    }

    /**
     * 获取 开单所属部门编号.
     *
     * @return the 开单所属部门编号
     */
    public String getBillingOrgCode() {
        return billingOrgCode;
    }

    /**
     * 设置 开单所属部门编号.
     *
     * @param billingOrgCode the new 开单所属部门编号
     */
    public void setBillingOrgCode(String billingOrgCode) {
        this.billingOrgCode = billingOrgCode;
    }

    /**
     * 获取 最终到达部门编号.
     *
     * @return the 最终到达部门编号
     */
    public String getDestOrgCode() {
        return destOrgCode;
    }

    /**
     * 设置 最终到达部门编号.
     *
     * @param destOrgCode the new 最终到达部门编号
     */
    public void setDestOrgCode(String destOrgCode) {
        this.destOrgCode = destOrgCode;
    }

    /**
     * 获取 现所在部门编号.
     *
     * @return the 现所在部门编号
     */
    public String getCurrentOrgCode() {
        return currentOrgCode;
    }

    /**
     * 设置 现所在部门编号.
     *
     * @param currentOrgCode the new 现所在部门编号
     */
    public void setCurrentOrgCode(String currentOrgCode) {
        this.currentOrgCode = currentOrgCode;
    }

    /**
     * 获取 总重量.
     *
     * @return the 总重量
     */
    public BigDecimal getTotalWeight() {
        return totalWeight;
    }

    /**
     * 设置 总重量.
     *
     * @param totalWeight the new 总重量
     */
    public void setTotalWeight(BigDecimal totalWeight) {
        this.totalWeight = totalWeight;
    }

    /**
     * 获取 总体积.
     *
     * @return the 总体积
     */
    public BigDecimal getTotalVolume() {
        return totalVolume;
    }

    /**
     * 设置 总体积.
     *
     * @param totalVolume the new 总体积
     */
    public void setTotalVolume(BigDecimal totalVolume) {
        this.totalVolume = totalVolume;
    }

    /**
     * 获取 运单总货件数量.
     *
     * @return the 运单总货件数量
     */
    public Integer getGoodsQtyTotal() {
        return goodsQtyTotal;
    }

    /**
     * 设置 运单总货件数量.
     *
     * @param goodsQtyTotal the new 运单总货件数量
     */
    public void setGoodsQtyTotal(Integer goodsQtyTotal) {
        this.goodsQtyTotal = goodsQtyTotal;
    }

    /**
     * 获取 运输类型.
     *
     * @return the 运输类型
     */
    public String getTransportModel() {
        return transportModel;
    }

    /**
     * 设置 运输类型.
     *
     * @param transportModel the new 运输类型
     */
    public void setTransportModel(String transportModel) {
        this.transportModel = transportModel;
    }

    /**
     * 获取 运输车辆牌号.
     *
     * @return the 运输车辆牌号
     */
    public String getVehicleNo() {
        return vehicleNo;
    }

    /**
     * 设置 运输车辆牌号.
     *
     * @param vehicleNo the new 运输车辆牌号
     */
    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    /**
     * 获取 走货路径.
     *
     * @return the 走货路径
     */
    public String getTransportPath() {
        return transportPath;
    }

    /**
     * 设置 走货路径.
     *
     * @param transportPath the new 走货路径
     */
    public void setTransportPath(String transportPath) {
        this.transportPath = transportPath;
    }

    /**
     * 获取 现所处状态.
     *
     * @return the 现所处状态
     */
    public String getAction() {
        return action;
    }

    /**
     * 获取 上一状态.
     *
     * @return the 上一状态
     */
    public String getBeforeAction() {
		return beforeAction;
	}

	/**
	 * 设置 上一状态.
	 *
	 * @param beforeAction the new 上一状态
	 */
	public void setBeforeAction(String beforeAction) {
		this.beforeAction = beforeAction;
	}

    /**
     * 设置 现所处状态.
     *
     * @param action the new 现所处状态
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * 获取 包装部门编号.
     *
     * @return the 包装部门编号
     */
    public String getPackingOrgCode() {
        return packingOrgCode;
    }

    /**
     * 设置 包装部门编号.
     *
     * @param packingOrgCode the new 包装部门编号
     */
    public void setPackingOrgCode(String packingOrgCode) {
        this.packingOrgCode = packingOrgCode;
    }

    /**
     * 获取 下一到达部门编号.
     *
     * @return the 下一到达部门编号
     */
    public String getNextOrgCode() {
        return nextOrgCode;
    }

    /**
     * 设置 下一到达部门编号.
     *
     * @param nextOrgCode the new 下一到达部门编号
     */
    public void setNextOrgCode(String nextOrgCode) {
        this.nextOrgCode = nextOrgCode;
    }
    
    /**
     * 获取 预计出发时间.
     *
     * @return the 预计出发时间
     */
    @DateFormat
    public Date getPlanStartTime() {
        return planStartTime;
    }
    
    /**
     * 设置 预计出发时间.
     *
     * @param planStartTime the new 预计出发时间
     */
    @DateFormat
    public void setPlanStartTime(Date planStartTime) {
        this.planStartTime = planStartTime;
    }
    
    /**
     * 获取 预计到达时间.
     *
     * @return the 预计到达时间
     */
    @DateFormat
    public Date getPlanArriveTime() {
        return planArriveTime;
    }
    
    /**
     * 设置 预计到达时间.
     *
     * @param planArriveTime the new 预计到达时间
     */
    @DateFormat
    public void setPlanArriveTime(Date planArriveTime) {
        this.planArriveTime = planArriveTime;
    }

    /**
     * 获取 是否分批配载.
     *
     * @return the 是否分批配载
     */
    public String getIfPartialStowage() {
		return ifPartialStowage;
	}

	/**
	 * 设置 是否分批配载.
	 *
	 * @param ifPartialStowage the new 是否分批配载
	 */
	public void setIfPartialStowage(String ifPartialStowage) {
		this.ifPartialStowage = ifPartialStowage;
	}

	/**
	 * 获取 包装后件数.
	 *
	 * @return the 包装后件数
	 */
	public Integer getPackedNum() {
        return packedNum;
    }

    /**
     * 设置 包装后件数.
     *
     * @param packedNum the new 包装后件数
     */
    public void setPackedNum(Integer packedNum) {
        this.packedNum = packedNum;
    }

	/**
	 * 
	 *
	 * @return 
	 */
	public String getBillingOrgCodeName() {
		return billingOrgCodeName;
	}

	/**
	 * 
	 *
	 * @param billingOrgCodeName 
	 */
	public void setBillingOrgCodeName(String billingOrgCodeName) {
		this.billingOrgCodeName = billingOrgCodeName;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getDestOrgCodeName() {
		return destOrgCodeName;
	}

	/**
	 * 
	 *
	 * @param destOrgCodeName 
	 */
	public void setDestOrgCodeName(String destOrgCodeName) {
		this.destOrgCodeName = destOrgCodeName;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getCurrentOrgCodeName() {
		return currentOrgCodeName;
	}

	/**
	 * 
	 *
	 * @param currentOrgCodeName 
	 */
	public void setCurrentOrgCodeName(String currentOrgCodeName) {
		this.currentOrgCodeName = currentOrgCodeName;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getPackingOrgCodeName() {
		return packingOrgCodeName;
	}

	/**
	 * 
	 *
	 * @param packingOrgCodeName 
	 */
	public void setPackingOrgCodeName(String packingOrgCodeName) {
		this.packingOrgCodeName = packingOrgCodeName;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String getNextOrgCodeName() {
		return nextOrgCodeName;
	}

	/**
	 * 
	 *
	 * @param nextOrgCodeName 
	 */
	public void setNextOrgCodeName(String nextOrgCodeName) {
		this.nextOrgCodeName = nextOrgCodeName;
	}

	/**
	 * 计算同一个运单同时有多少个流水号走同一条路径
	 * @return the waybiilNoCount
	 */
	public String getWaybillNoCount() {
		return waybillNoCount;
	}

	/**
	 * 计算同一个运单同时有多少个流水号走同一条路径
	 * @param waybiilNoCount the waybiilNoCount to set
	 */
	public void setWaybillNoCount(String waybillNoCount) {
		this.waybillNoCount = waybillNoCount;
	}
    
}