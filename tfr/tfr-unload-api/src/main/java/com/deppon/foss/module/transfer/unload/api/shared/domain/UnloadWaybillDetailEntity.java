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
 *  PROJECT NAME  : tfr-unload-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/shared/domain/UnloadWaybillDetailEntity.java
 *  
 *  FILE NAME          :UnloadWaybillDetailEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.unload.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 卸车任务明细表
 * @author ibm-zhangyixin
 * @date 2012-12-7 上午11:06:58
 */
public class UnloadWaybillDetailEntity extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = -927406921753104188L;
	
	/**id**/
	private String id;

	/**UNLOAD_TASK_ID 主表ID**/
    private String unloadTaskId;		

    /**单据编号**/
    private String billNo;				

    /**运单号**/
    private String waybillNo;			

    /**已操作件数**/
    private Integer operationGoodsQty;	

    /**扫描件数**/
    private Integer scanGoodsQty;		

    /**出发部门**/
    private String origOrgCode;			

    /**到达部门**/
    private String destOrgCode;			

    /**出发部门**/
    private String origOrgName;			
    
    /**到达部门**/
    private String destOrgName;			

    /**货名**/
    private String goodsName;			

    /**包装**/
    private String pack;				

    /**运输性质**/
    private String transportType;		

    /**任务建立时间**/
    private Date taskBeginTime;			

    /**建立任务部门编码**/
    private String unloadOrgCode;		

    /**交接重量**/
    private BigDecimal unloadWeightTotal;

    /**交接体积**/
    private BigDecimal unloadVolumeTotal;

    /**交接件数**/
    private Integer handOverPieces;		

    /**交接单编号**/
    private String handOverBillNo;        

    /**重量**/
    private BigDecimal weight;		

    /**体积**/
    private BigDecimal volume;		
    
    /**包号**/
    private String packageNo;
    
    /** zwd 200968  运单生效状态 - YES NO*/
	private String wayBillStatus;
	/** zwd 200968  运单生效状态 - YES NO*/
	public String getWayBillStatus() {
		return wayBillStatus;
	}
	/** zwd 200968  运单生效状态 - YES NO*/
	public void setWayBillStatus(String wayBillStatus) {
		this.wayBillStatus = wayBillStatus;
	}
    
    /**
     * 获取 重量*.
     *
     * @return the 重量*
     */
    public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * 设置 重量*.
	 *
	 * @param weight the new 重量*
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
	 * 获取 体积*.
	 *
	 * @return the 体积*
	 */
	public BigDecimal getVolume() {
		return volume;
	}

	/**
	 * 设置 体积*.
	 *
	 * @param volume the new 体积*
	 */
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	/**
     * 获取 交接件数*.
     *
     * @return the 交接件数*
     */
	public Integer getHandOverPieces() {
		return handOverPieces;
	}

	/**
	 * 设置 交接件数*.
	 *
	 * @param handOverPieces the new 交接件数*
	 */
	public void setHandOverPieces(Integer handOverPieces) {
		this.handOverPieces = handOverPieces;
	}

	/**
	 * 获取 交接单编号*.
	 *
	 * @return the 交接单编号*
	 */
	public String getHandOverBillNo() {
		return handOverBillNo;
	}

	/**
	 * 设置 交接单编号*.
	 *
	 * @param handOverBillNo the new 交接单编号*
	 */
	public void setHandOverBillNo(String handOverBillNo) {
		this.handOverBillNo = handOverBillNo;
	}

	/** 
	 * id
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 下午6:55:11
	 * @see com.deppon.foss.framework.entity.BaseEntity#getId()
	 */
	public String getId() {
        return id;
    }

    /** 
     * id
     * @author ibm-zhangyixin
     * @date 2012-12-25 下午6:55:11
     * @see com.deppon.foss.framework.entity.BaseEntity#setId(java.lang.String)
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取 uNLOAD_TASK_ID 主表ID*.
     *
     * @return the uNLOAD_TASK_ID 主表ID*
     */
    public String getUnloadTaskId() {
        return unloadTaskId;
    }

    /**
     * 设置 uNLOAD_TASK_ID 主表ID*.
     *
     * @param unloadTaskId the new uNLOAD_TASK_ID 主表ID*
     */
    public void setUnloadTaskId(String unloadTaskId) {
        this.unloadTaskId = unloadTaskId;
    }

    /**
     * 获取 单据编号*.
     *
     * @return the 单据编号*
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 设置 单据编号*.
     *
     * @param billNo the new 单据编号*
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
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
     * 获取 已操作件数*.
     *
     * @return the 已操作件数*
     */
    public Integer getOperationGoodsQty() {
        return operationGoodsQty;
    }

    /**
     * 设置 已操作件数*.
     *
     * @param operationGoodsQty the new 已操作件数*
     */
    public void setOperationGoodsQty(Integer operationGoodsQty) {
        this.operationGoodsQty = operationGoodsQty;
    }

    /**
     * 获取 扫描件数*.
     *
     * @return the 扫描件数*
     */
    public Integer getScanGoodsQty() {
        return scanGoodsQty;
    }

    /**
     * 设置 扫描件数*.
     *
     * @param scanGoodsQty the new 扫描件数*
     */
    public void setScanGoodsQty(Integer scanGoodsQty) {
        this.scanGoodsQty = scanGoodsQty;
    }

    /**
     * 获取 出发部门*.
     *
     * @return the 出发部门*
     */
    public String getOrigOrgCode() {
        return origOrgCode;
    }

    /**
     * 设置 出发部门*.
     *
     * @param origOrgCode the new 出发部门*
     */
    public void setOrigOrgCode(String origOrgCode) {
        this.origOrgCode = origOrgCode;
    }

    /**
     * 获取 到达部门*.
     *
     * @return the 到达部门*
     */
    public String getDestOrgCode() {
        return destOrgCode;
    }

    /**
     * 设置 到达部门*.
     *
     * @param destOrgCode the new 到达部门*
     */
    public void setDestOrgCode(String destOrgCode) {
        this.destOrgCode = destOrgCode;
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
     * 获取 任务建立时间*.
     *
     * @return the 任务建立时间*
     */
    public Date getTaskBeginTime() {
        return taskBeginTime;
    }

    /**
     * 设置 任务建立时间*.
     *
     * @param taskBeginTime the new 任务建立时间*
     */
    public void setTaskBeginTime(Date taskBeginTime) {
        this.taskBeginTime = taskBeginTime;
    }

    /**
     * 获取 建立任务部门编码*.
     *
     * @return the 建立任务部门编码*
     */
    public String getUnloadOrgCode() {
        return unloadOrgCode;
    }

    /**
     * 设置 建立任务部门编码*.
     *
     * @param unloadOrgCode the new 建立任务部门编码*
     */
    public void setUnloadOrgCode(String unloadOrgCode) {
        this.unloadOrgCode = unloadOrgCode;
    }

	/**
	 * 获取 交接重量*.
	 *
	 * @return the 交接重量*
	 */
	public BigDecimal getUnloadWeightTotal() {
		return unloadWeightTotal;
	}

	/**
	 * 设置 交接重量*.
	 *
	 * @param unloadWeightTotal the new 交接重量*
	 */
	public void setUnloadWeightTotal(BigDecimal unloadWeightTotal) {
		this.unloadWeightTotal = unloadWeightTotal;
	}

	/**
	 * 获取 交接体积*.
	 *
	 * @return the 交接体积*
	 */
	public BigDecimal getUnloadVolumeTotal() {
		return unloadVolumeTotal;
	}

	/**
	 * 设置 交接体积*.
	 *
	 * @param unloadVolumeTotal the new 交接体积*
	 */
	public void setUnloadVolumeTotal(BigDecimal unloadVolumeTotal) {
		this.unloadVolumeTotal = unloadVolumeTotal;
	}

	public String getOrigOrgName() {
		return origOrgName;
	}

	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}

	public String getDestOrgName() {
		return destOrgName;
	}

	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	public final String getPackageNo() {
		return packageNo;
	}

	public final void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}
}