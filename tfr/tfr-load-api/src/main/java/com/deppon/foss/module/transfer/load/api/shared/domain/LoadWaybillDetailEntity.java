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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/domain/LoadWaybillDetailEntity.java
 *  
 *  FILE NAME          :LoadWaybillDetailEntity.java
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

import java.io.Serializable;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * 装车运单明细
 * LoadWaybillDetailEntity
 * @author ibm-zhangyixin
 * @date 2012-11-13 下午4:20:15
 */
public class LoadWaybillDetailEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -3239883005308805872L;

	/**ID**/
	private String id;

	/**LoadTask_ID**/
    private String loadTaskId;		
    
    /**库存件数**/
    private Integer stockQty;		

    /**已扫描件数**/
    private Integer scanQty;		

    /**已装车件数**/
    private Integer loadQty;		

    /**运单号**/
    private String waybillNo;		

    /**备注**/
    private String notes;			

    /**运输性质**/
    private String transportType;	

    /**货名**/
    private String goodsName;		

    /**包装**/
    private String pack;			

    /**收货部门**/
    private String receiveOrgName;	

    /**到达部门**/
    private String reachOrgName;	

    /**建立任务时间**/
    private Date taskBeginTime;	

    /**出发部门编号**/
    private String origOrgCode;		

    /**是否合车**/
    private String beJoinCar;
    
    /**装车重量**/
    private double loadWeightTotal;
    
    /**装车体积**/
    private double loadVolumeTotal;
    
	/**
	 * 派送单号
	 */
	private String billNo;
	
	/**
	 * 收货人
	 */
	private String consignee;
	
	/**
	 * 开单件数
	 */
	private Integer waybillGoodsQty;
	
	/**
	 * 排单件数
	 */
	private Integer deliverBillQty;
	
	/**
	 * 包号
	 */
	private String packageNo;

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public Integer getWaybillGoodsQty() {
		return waybillGoodsQty;
	}

	public void setWaybillGoodsQty(Integer waybillGoodsQty) {
		this.waybillGoodsQty = waybillGoodsQty;
	}

	public Integer getDeliverBillQty() {
		return deliverBillQty;
	}

	public void setDeliverBillQty(Integer deliverBillQty) {
		this.deliverBillQty = deliverBillQty;
	}
    
    /** 
     * id
     * @author ibm-zhangyixin
     * @date 2012-12-25 下午6:38:58
     * @see com.deppon.foss.framework.entity.BaseEntity#getId()
     */
    public String getId() {
        return id;
    }

    /** 
     * id
     * @author ibm-zhangyixin
     * @date 2012-12-25 下午6:38:58
     * @see com.deppon.foss.framework.entity.BaseEntity#setId(java.lang.String)
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取 loadTask_ID*.
     *
     * @return the loadTask_ID*
     */
    public String getLoadTaskId() {
        return loadTaskId;
    }

    /**
     * 设置 loadTask_ID*.
     *
     * @param loadTaskId the new loadTask_ID*
     */
    public void setLoadTaskId(String loadTaskId) {
        this.loadTaskId = loadTaskId;
    }

    /**
     * 获取 库存件数*.
     *
     * @return the 库存件数*
     */
    public Integer getStockQty() {
		return stockQty;
	}

	/**
	 * 设置 库存件数*.
	 *
	 * @param stockQty the new 库存件数*
	 */
	public void setStockQty(Integer stockQty) {
		this.stockQty = stockQty;
	}

	/**
	 * 获取 已扫描件数*.
	 *
	 * @return the 已扫描件数*
	 */
	public Integer getScanQty() {
        return scanQty;
    }

    /**
     * 设置 已扫描件数*.
     *
     * @param scanQty the new 已扫描件数*
     */
    public void setScanQty(Integer scanQty) {
        this.scanQty = scanQty;
    }

    /**
     * 获取 已装车件数*.
     *
     * @return the 已装车件数*
     */
    public Integer getLoadQty() {
        return loadQty;
    }

    /**
     * 设置 已装车件数*.
     *
     * @param loadQty the new 已装车件数*
     */
    public void setLoadQty(Integer loadQty) {
        this.loadQty = loadQty;
    }

    /**
     * 获取 运单号*.
     *
     * @return the 运单号*
     */
    public String getWaybillNo() {
        return waybillNo;
    }

    /**
     * 设置 运单号*.
     *
     * @param waybillNo the new 运单号*
     */
    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }

    /**
     * 获取 备注*.
     *
     * @return the 备注*
     */
    public String getNotes() {
        return notes;
    }

    /**
     * 设置 备注*.
     *
     * @param notes the new 备注*
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * 获取 运输性质*.
     *
     * @return the 运输性质*
     */
    public String getTransportType() {
        return transportType;
    }

    /**
     * 设置 运输性质*.
     *
     * @param transportType the new 运输性质*
     */
    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    /**
     * 获取 货名*.
     *
     * @return the 货名*
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * 设置 货名*.
     *
     * @param goodsName the new 货名*
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /**
     * 获取 包装*.
     *
     * @return the 包装*
     */
    public String getPack() {
		return pack;
	}

	/**
	 * 设置 包装*.
	 *
	 * @param pack the new 包装*
	 */
	public void setPack(String pack) {
		this.pack = pack;
	}

	/**
	 * 获取 收货部门*.
	 *
	 * @return the 收货部门*
	 */
	public String getReceiveOrgName() {
        return receiveOrgName;
    }

    /**
     * 设置 收货部门*.
     *
     * @param receiveOrgName the new 收货部门*
     */
    public void setReceiveOrgName(String receiveOrgName) {
        this.receiveOrgName = receiveOrgName;
    }

    /**
     * 获取 到达部门*.
     *
     * @return the 到达部门*
     */
    public String getReachOrgName() {
        return reachOrgName;
    }

    /**
     * 设置 到达部门*.
     *
     * @param reachOrgName the new 到达部门*
     */
    public void setReachOrgName(String reachOrgName) {
        this.reachOrgName = reachOrgName;
    }

    /**
     * 获取 建立任务时间*.
     *
     * @return the 建立任务时间*
     */
    public Date getTaskBeginTime() {
        return taskBeginTime;
    }

    /**
     * 设置 建立任务时间*.
     *
     * @param taskBeginTime the new 建立任务时间*
     */
    public void setTaskBeginTime(Date taskBeginTime) {
        this.taskBeginTime = taskBeginTime;
    }

    /**
     * 获取 出发部门编号*.
     *
     * @return the 出发部门编号*
     */
    public String getOrigOrgCode() {
        return origOrgCode;
    }

    /**
     * 设置 出发部门编号*.
     *
     * @param origOrgCode the new 出发部门编号*
     */
    public void setOrigOrgCode(String origOrgCode) {
        this.origOrgCode = origOrgCode;
    }

	/**
	 * 获取 是否合车*.
	 *
	 * @return the 是否合车*
	 */
	public String getBeJoinCar() {
		return beJoinCar;
	}

	/**
	 * 设置 是否合车*.
	 *
	 * @param beJoinCar the new 是否合车*
	 */
	public void setBeJoinCar(String beJoinCar) {
		this.beJoinCar = beJoinCar;
	}

	/**
	 * 获取 装车重量*.
	 *
	 * @return the 装车重量*
	 */
	public double getLoadWeightTotal() {
		return loadWeightTotal;
	}

	/**
	 * 设置 装车重量*.
	 *
	 * @param loadWeightTotal the new 装车重量*
	 */
	public void setLoadWeightTotal(double loadWeightTotal) {
		this.loadWeightTotal = loadWeightTotal;
	}

	/**
	 * 获取 装车体积*.
	 *
	 * @return the 装车体积*
	 */
	public double getLoadVolumeTotal() {
		return loadVolumeTotal;
	}

	/**
	 * 设置 装车体积*.
	 *
	 * @param loadVolumeTotal the new 装车体积*
	 */
	public void setLoadVolumeTotal(double loadVolumeTotal) {
		this.loadVolumeTotal = loadVolumeTotal;
	}

	public final String getPackageNo() {
		return packageNo;
	}

	public final void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}

	@Override
	public String toString() {
		return "LoadWaybillDetailEntity [id=" + id + ", loadTaskId=" + loadTaskId + ", stockQty=" + stockQty
				+ ", scanQty=" + scanQty + ", loadQty=" + loadQty + ", waybillNo=" + waybillNo + ", notes=" + notes
				+ ", transportType=" + transportType + ", goodsName=" + goodsName + ", pack=" + pack
				+ ", receiveOrgName=" + receiveOrgName + ", reachOrgName=" + reachOrgName + ", taskBeginTime="
				+ taskBeginTime + ", origOrgCode=" + origOrgCode + ", beJoinCar=" + beJoinCar + ", loadWeightTotal="
				+ loadWeightTotal + ", loadVolumeTotal=" + loadVolumeTotal + ", billNo=" + billNo + ", consignee="
				+ consignee + ", waybillGoodsQty=" + waybillGoodsQty + ", deliverBillQty=" + deliverBillQty
				+ ", packageNo=" + packageNo + ", getCreateUser()=" + getCreateUser() + ", getModifyUser()="
				+ getModifyUser() + ", getCreateDate()=" + getCreateDate() + ", getModifyDate()=" + getModifyDate()
				+ ", hashCode()=" + hashCode() + ", getClass()=" + getClass() + ", toString()=" + super.toString()
				+ "]";
	}
}