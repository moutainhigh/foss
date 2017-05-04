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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/domain/LoaderWorkloadEntity.java
 *  
 *  FILE NAME          :LoaderWorkloadEntity.java
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
import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 装卸车工作量
 * @author ibm-zhangyixin
 * @date 2012-11-28 下午4:15:48
 */
public class LoaderWorkloadEntity extends BaseEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2742265026724390431L;

	/**ID**/
	private String id;
	
	/**理货员编号**/
    private String loaderCode;
    
    /**理货员名称**/
    private String loaderName;
    
    /**理货员所属部门编号**/
    private String loaderOrgCode;

    /**理货员所属部门名称**/
    private String loaderOrgName;

    /**装卸车队编号**/
    private String loadOrgCode;	

    /**装卸车队名称**/
    private String loadOrgName;	

    /**车队所属部门编号**/
    private String orgCode;		

    /**车队所属部门名称**/
    private String orgName;		

    /**操作类型**/
    private String handleType;	

    /**任务类型**/
    private String taskType;	
    
    /**交接单号**/
    private String handoverNo;
    
    /**车牌号**/
    private String vehicleNo;

    /**任务号**/
    private String taskNo;		

    /**任务_ID**/
    private String taskId;		

    /**加入任务时间**/
    private Date joinTime;		

    /**离开任务时间**/
    private Date leaveTime;		

    /**重量**/
    private BigDecimal weight;	

    /**票数**/
    private Integer waybillQty;	

    /**件数**/
    private Integer goodsQty;	

    /**体积**/
    private BigDecimal volume;	

    /**备注**/
    private String notes;		

    /**调整人编号**/
    private String adjusterCode;

    /**调整人姓名**/
    private String adjusterName;

    /**调整时间**/
    private Date adjustTime;	

    /**调整部门编码**/
    private String adjustOrgCode;

    /**调整部门名称**/
    private String adjustOrgName;
    /**创建时间**/
    private Date createTime;
    
    /**货物类型(A,B货)**/
    private String goodsType;
    
	/**A货件数**/
	private Integer acount;
	
	/**B货件数**/
	private Integer bcount;
    
    /**快递件数**/
    private Integer expressCount; 
    
    /**272681 快递或快递**/
    private String expressOrLd;
    
    //数据来源，标注是否来自悟空
    private String dataSource;
    
    //悟空用的时间截
    private String taskEndTimeWK;
    
    private BigDecimal weightTotal;
    
    private BigDecimal volumeTotal;
    
    private Integer goodsQtyTotal;
    
    
    public BigDecimal getWeightTotal() {
		return weightTotal;
	}

	public void setWeightTotal(BigDecimal weightTotal) {
		this.weightTotal = weightTotal;
	}

	public BigDecimal getVolumeTotal() {
		return volumeTotal;
	}

	public void setVolumeTotal(BigDecimal volumeTotal) {
		this.volumeTotal = volumeTotal;
	}

	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}



	public String getTaskEndTimeWK() {
		return taskEndTimeWK;
	}

	public void setTaskEndTimeWK(String taskEndTimeWK) {
		this.taskEndTimeWK = taskEndTimeWK;
	}

	/** 
     * @author ibm-zhangyixin
     * @date 2012-12-25 下午6:22:56
     * @see com.deppon.foss.framework.entity.BaseEntity#getId()
     */
    public String getId() {
        return id;
    }

    /** 
     * @author ibm-zhangyixin
     * @date 2012-12-25 下午6:22:56
     * @see com.deppon.foss.framework.entity.BaseEntity#setId(java.lang.String)
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取 理货员编号*.
     *
     * @return the 理货员编号*
     */
    public String getLoaderCode() {
        return loaderCode;
    }

    /**
     * 设置 理货员编号*.
     *
     * @param loaderCode the new 理货员编号*
     */
    public void setLoaderCode(String loaderCode) {
        this.loaderCode = loaderCode;
    }

    /**
     * 获取 理货员名称*.
     *
     * @return the 理货员名称*
     */
    public String getLoaderName() {
        return loaderName;
    }

    /**
     * 设置 理货员名称*.
     *
     * @param loaderName the new 理货员名称*
     */
    public void setLoaderName(String loaderName) {
        this.loaderName = loaderName;
    }

    /**
     * 获取 装卸车队编号*.
     *
     * @return the 装卸车队编号*
     */
    public String getLoadOrgCode() {
        return loadOrgCode;
    }

    /**
     * 设置 装卸车队编号*.
     *
     * @param loadOrgCode the new 装卸车队编号*
     */
    public void setLoadOrgCode(String loadOrgCode) {
        this.loadOrgCode = loadOrgCode;
    }

    /**
     * 获取 装卸车队名称*.
     *
     * @return the 装卸车队名称*
     */
    public String getLoadOrgName() {
        return loadOrgName;
    }

    /**
     * 设置 装卸车队名称*.
     *
     * @param loadOrgName the new 装卸车队名称*
     */
    public void setLoadOrgName(String loadOrgName) {
        this.loadOrgName = loadOrgName;
    }

    /**
     * 获取 操作类型*.
     *
     * @return the 操作类型*
     */
    public String getHandleType() {
        return handleType;
    }

    /**
     * 设置 操作类型*.
     *
     * @param handleType the new 操作类型*
     */
    public void setHandleType(String handleType) {
        this.handleType = handleType;
    }

    /**
     * 获取 任务类型*.
     *
     * @return the 任务类型*
     */
    public String getTaskType() {
        return taskType;
    }

    /**
     * 设置 任务类型*.
     *
     * @param taskType the new 任务类型*
     */
    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    /**
     * 获取 任务号*.
     *
     * @return the 任务号*
     */
    public String getTaskNo() {
        return taskNo;
    }

    /**
     * 设置 任务号*.
     *
     * @param taskNo the new 任务号*
     */
    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    /**
     * 获取 任务_ID*.
     *
     * @return the 任务_ID*
     */
    public String getTaskId() {
		return taskId;
	}

	/**
	 * 设置 任务_ID*.
	 *
	 * @param taskId the new 任务_ID*
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	/**
	 * 获取 加入任务时间*.
	 *
	 * @return the 加入任务时间*
	 */
	public Date getJoinTime() {
        return joinTime;
    }

    /**
     * 设置 加入任务时间*.
     *
     * @param joinTime the new 加入任务时间*
     */
    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    /**
     * 获取 离开任务时间*.
     *
     * @return the 离开任务时间*
     */
    public Date getLeaveTime() {
        return leaveTime;
    }

    /**
     * 设置 离开任务时间*.
     *
     * @param leaveTime the new 离开任务时间*
     */
    public void setLeaveTime(Date leaveTime) {
        this.leaveTime = leaveTime;
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
     * 获取 票数*.
     *
     * @return the 票数*
     */
    public Integer getWaybillQty() {
        return waybillQty;
    }

    /**
     * 设置 票数*.
     *
     * @param waybillQty the new 票数*
     */
    public void setWaybillQty(Integer waybillQty) {
        this.waybillQty = waybillQty;
    }

    /**
     * 获取 件数*.
     *
     * @return the 件数*
     */
    public Integer getGoodsQty() {
        return goodsQty;
    }

    /**
     * 设置 件数*.
     *
     * @param goodsQty the new 件数*
     */
    public void setGoodsQty(Integer goodsQty) {
        this.goodsQty = goodsQty;
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
     * 获取 调整人编号*.
     *
     * @return the 调整人编号*
     */
    public String getAdjusterCode() {
        return adjusterCode;
    }

    /**
     * 设置 调整人编号*.
     *
     * @param adjusterCode the new 调整人编号*
     */
    public void setAdjusterCode(String adjusterCode) {
        this.adjusterCode = adjusterCode;
    }

    /**
     * 获取 调整人姓名*.
     *
     * @return the 调整人姓名*
     */
    public String getAdjusterName() {
        return adjusterName;
    }

    /**
     * 设置 调整人姓名*.
     *
     * @param adjusterName the new 调整人姓名*
     */
    public void setAdjusterName(String adjusterName) {
        this.adjusterName = adjusterName;
    }

    /**
     * 获取 调整时间*.
     *
     * @return the 调整时间*
     */
    public Date getAdjustTime() {
        return adjustTime;
    }

    /**
     * 设置 调整时间*.
     *
     * @param adjustTime the new 调整时间*
     */
    public void setAdjustTime(Date adjustTime) {
        this.adjustTime = adjustTime;
    }

    /**
     * 获取 调整部门编码*.
     *
     * @return the 调整部门编码*
     */
    public String getAdjustOrgCode() {
        return adjustOrgCode;
    }

    /**
     * 设置 调整部门编码*.
     *
     * @param adjustOrgCode the new 调整部门编码*
     */
    public void setAdjustOrgCode(String adjustOrgCode) {
        this.adjustOrgCode = adjustOrgCode;
    }

    /**
     * 获取 调整部门名称*.
     *
     * @return the 调整部门名称*
     */
    public String getAdjustOrgName() {
        return adjustOrgName;
    }

    /**
     * 设置 调整部门名称*.
     *
     * @param adjustOrgName the new 调整部门名称*
     */
    public void setAdjustOrgName(String adjustOrgName) {
        this.adjustOrgName = adjustOrgName;
    }

	/**
	 * 获取 车队所属部门编号*.
	 *
	 * @return the 车队所属部门编号*
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * 设置 车队所属部门编号*.
	 *
	 * @param orgCode the new 车队所属部门编号*
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * 获取 车队所属部门名称*.
	 *
	 * @return the 车队所属部门名称*
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * 设置 车队所属部门名称*.
	 *
	 * @param orgName the new 车队所属部门名称*
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * 获取 理货员所属部门编号*.
	 *
	 * @return the 理货员所属部门编号*
	 */
	public String getLoaderOrgCode() {
		return loaderOrgCode;
	}

	/**
	 * 设置 理货员所属部门编号*.
	 *
	 * @param loaderOrgCode the new 理货员所属部门编号*
	 */
	public void setLoaderOrgCode(String loaderOrgCode) {
		this.loaderOrgCode = loaderOrgCode;
	}

	/**
	 * 获取 理货员所属部门名称*.
	 *
	 * @return the 理货员所属部门名称*
	 */
	public String getLoaderOrgName() {
		return loaderOrgName;
	}

	/**
	 * 设置 理货员所属部门名称*.
	 *
	 * @param loaderOrgName the new 理货员所属部门名称*
	 */
	public void setLoaderOrgName(String loaderOrgName) {
		this.loaderOrgName = loaderOrgName;
	}

	/**
	 * Gets the 创建时间*.
	 *
	 * @return the 创建时间*
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * Sets the 创建时间*.
	 *
	 * @param createTime the new 创建时间*
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	 * Date:2013-7-10上午10:55:34
	 */
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	/**   
	 * handoverNo   
	 *   
	 * @return  the handoverNo   
	 */
	
	public String getHandoverNo() {
		return handoverNo;
	}

	/**   
	 * @param handoverNo the handoverNo to set
	 * Date:2013-7-18下午3:13:00
	 */
	public void setHandoverNo(String handoverNo) {
		this.handoverNo = handoverNo;
	}

	/**   
	 * vehicleNo   
	 *   
	 * @return  the vehicleNo   
	 */
	
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**   
	 * @param vehicleNo the vehicleNo to set
	 * Date:2013-7-18下午3:13:00
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	
	
	public Integer getExpressCount() {
		return expressCount;
	}

	public void setExpressCount(Integer expressCount) {
		this.expressCount = expressCount;
	}

	public Integer getAcount() {
		return acount;
	}

	public void setAcount(Integer acount) {
		this.acount = acount;
	}

	public Integer getBcount() {
		return bcount;
	}

	public void setBcount(Integer bcount) {
		this.bcount = bcount;
	}

	public String getExpressOrLd() {
		return expressOrLd;
	}

	public void setExpressOrLd(String expressOrLd) {
		this.expressOrLd = expressOrLd;
	}


	
	
	
}