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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/domain/PathDetailEntity.java
 * 
 *  FILE NAME     :PathDetailEntity.java
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

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;

/**
 * 走货路径明细entity
 */
public class PathDetailEntity extends BaseEntity implements Cloneable {
	
	/**
	 * 版本号
	 */
	private static final long serialVersionUID = -3063814886122231960L;
	/**
	 * 主键ID
	 */
	private String pathDetailId;
	/**
	 * 运单号
	 */
    private String waybillNo;
    /**
     * 流水号
     */
    private String goodsNo;
    /**
     * 出发部门
     */
    private String origOrgCode;
    /**
     * 出发部门名称
     */
    private String origOrgName;

	/**
     * 下一到达部门
     */
    private String objectiveOrgCode;
    /**
     * 下一到达部门名称
     */
    private String objectiveOrgName;
    /**
     * 线路段号 (1,2,3)
     */
    private String routeNo;
    /**
     * 计划出发时间
     */
    private Date planStartTime;
    /**
     * 实际出发时间
     */
    private Date actualStartTime;
    /**
     * 是否离开状态
     */
    private String arriveOrLeave;
    /**
     * 计划到达时间
     */
    private Date planArriveTime;
    /**
     * 实际到达时间
     */
    private Date actualArriveTime;
    /**
     * 开单部门
     */
    private String billingOrgCode;
    /**
     * 下一路段到达部门
     */
    private String nextDestOrgCode;
    /**
     * 车牌号
     */
    private String vehicleNo;
    /**
     * 上一路段车牌号
     */
    private String beforeVehicleNo;
    
    /**是否修改过计划出发时间
     * 
     */
    private String ifChangeTime;
    
    /**
     * 调整出发时间
     */
    private Date modifyStartTime;
    
    /**
     * 调整到达时间
     */
    private Date modifyArriveTime;
    
    /**
     * 车辆明细任务ID
     */
    private String truckDetailId;
    
    /**
     * 下一部门到达时间
     */
    private Date nextArriveTime;
    
    /**
     * 计划的派送或提货时间
     */
    private Date planPickupTime;
    
    /**
     * 计算同一个运单同时有多少个流水号走同一条路径
     */
    private String waybillNoCount;
    
    public String getOrigOrgName() {
		return origOrgName;
	}

	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}

	public String getObjectiveOrgName() {
		return objectiveOrgName;
	}

	public void setObjectiveOrgName(String objectiveOrgName) {
		this.objectiveOrgName = objectiveOrgName;
	}
    /**
     * 获取 主键ID.
     *
     * @return the 主键ID
     */
    public String getPathDetailId() {
        return pathDetailId;
    }

    /**
     * 设置 主键ID.
     *
     * @param pathDetailId the new 主键ID
     */
    public void setPathDetailId(String pathDetailId) {
        this.pathDetailId = pathDetailId;
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
     * 获取 流水号.
     *
     * @return the 流水号
     */
    public String getGoodsNo() {
        return goodsNo;
    }

    /**
     * 设置 流水号.
     *
     * @param goodsNo the new 流水号
     */
    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
    }

    /**
     * 获取 出发部门.
     *
     * @return the 出发部门
     */
    public String getOrigOrgCode() {
        return origOrgCode;
    }

    /**
     * 设置 出发部门.
     *
     * @param origOrgCode the new 出发部门
     */
    public void setOrigOrgCode(String origOrgCode) {
        this.origOrgCode = origOrgCode;
    }

    /**
     * 获取 下一到达部门.
     *
     * @return the 下一到达部门
     */
    public String getObjectiveOrgCode() {
        return objectiveOrgCode;
    }

    /**
     * 设置 下一到达部门.
     *
     * @param objectiveOrgCode the new 下一到达部门
     */
    public void setObjectiveOrgCode(String objectiveOrgCode) {
        this.objectiveOrgCode = objectiveOrgCode;
    }

    /**
     * 获取 线路段号 (1,2,3).
     *
     * @return the 线路段号 (1,2,3)
     */
    public String getRouteNo() {
        return routeNo;
    }

    /**
     * 设置 线路段号 (1,2,3).
     *
     * @param routeNo the new 线路段号 (1,2,3)
     */
    public void setRouteNo(String routeNo) {
        this.routeNo = routeNo;
    }

    /**
     * 获取 计划出发时间.
     *
     * @return the 计划出发时间
     */
    @DateFormat
    public Date getPlanStartTime() {
        return planStartTime;
    }

    /**
     * 设置 计划出发时间.
     *
     * @param planStartTime the new 计划出发时间
     */
    @DateFormat
    public void setPlanStartTime(Date planStartTime) {
        this.planStartTime = planStartTime;
    }

    /**
     * 获取 实际出发时间.
     *
     * @return the 实际出发时间
     */
    @DateFormat
    public Date getActualStartTime() {
        return actualStartTime;
    }

    /**
     * 设置 实际出发时间.
     *
     * @param actualStartTime the new 实际出发时间
     */
    @DateFormat
    public void setActualStartTime(Date actualStartTime) {
        this.actualStartTime = actualStartTime;
    }

    /**
     * 获取 是否离开状态.
     *
     * @return the 是否离开状态
     */
    public String getArriveOrLeave() {
        return arriveOrLeave;
    }

    /**
     * 设置 是否离开状态.
     *
     * @param arriveOrLeave the new 是否离开状态
     */
    public void setArriveOrLeave(String arriveOrLeave) {
        this.arriveOrLeave = arriveOrLeave;
    }

    /**
     * 获取 计划到达时间.
     *
     * @return the 计划到达时间
     */
    public Date getPlanArriveTime() {
        return planArriveTime;
    }

    /**
     * 设置 计划到达时间.
     *
     * @param planArriveTime the new 计划到达时间
     */
    public void setPlanArriveTime(Date planArriveTime) {
        this.planArriveTime = planArriveTime;
    }

    /**
     * 获取 实际到达时间.
     *
     * @return the 实际到达时间
     */
    public Date getActualArriveTime() {
        return actualArriveTime;
    }

    /**
     * 设置 实际到达时间.
     *
     * @param actualArriveTime the new 实际到达时间
     */
    public void setActualArriveTime(Date actualArriveTime) {
        this.actualArriveTime = actualArriveTime;
    }

	/**
	 * 获取 开单部门.
	 *
	 * @return the 开单部门
	 */
	public String getBillingOrgCode() {
		return billingOrgCode;
	}

	/**
	 * 设置 开单部门.
	 *
	 * @param billingOrgCode the new 开单部门
	 */
	public void setBillingOrgCode(String billingOrgCode) {
		this.billingOrgCode = billingOrgCode;
	}

	/**
	 * 获取 下一路段到达部门.
	 *
	 * @return the 下一路段到达部门
	 */
	public String getNextDestOrgCode() {
		return nextDestOrgCode;
	}

	/**
	 * 设置 下一路段到达部门.
	 *
	 * @param nextDestOrgCode the new 下一路段到达部门
	 */
	public void setNextDestOrgCode(String nextDestOrgCode) {
		this.nextDestOrgCode = nextDestOrgCode;
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
	 * 获取 上一路段车牌号.
	 *
	 * @return the 上一路段车牌号
	 */
	public String getBeforeVehicleNo() {
		return beforeVehicleNo;
	}

	/**
	 * 设置 上一路段车牌号.
	 *
	 * @param beforeVehicleNo the new 上一路段车牌号
	 */
	public void setBeforeVehicleNo(String beforeVehicleNo) {
		this.beforeVehicleNo = beforeVehicleNo;
	}
	
	
	/**
	 * 获取 是否修改过计划出发时间.
	 *
	 * @return the 是否修改过计划出发时间
	 */
	public String getIfChangeTime() {
		return ifChangeTime;
	}

	/**
	 * 设置 是否修改过计划出发时间.
	 *
	 * @param ifChangeTime the new 是否修改过计划出发时间
	 */
	public void setIfChangeTime(String ifChangeTime) {
		this.ifChangeTime = ifChangeTime;
	}

	/**
	 * 获取 调整出发时间.
	 *
	 * @return the 调整出发时间
	 */
	public Date getModifyStartTime() {
		return modifyStartTime;
	}

	/**
	 * 设置 调整出发时间.
	 *
	 * @param modifyStartTime the new 调整出发时间
	 */
	public void setModifyStartTime(Date modifyStartTime) {
		this.modifyStartTime = modifyStartTime;
	}

	/**
	 * 获取 调整到达时间.
	 *
	 * @return the 调整到达时间
	 */
	public Date getModifyArriveTime() {
		return modifyArriveTime;
	}

	/**
	 * 设置 调整到达时间.
	 *
	 * @param modifyArriveTime the new 调整到达时间
	 */
	public void setModifyArriveTime(Date modifyArriveTime) {
		this.modifyArriveTime = modifyArriveTime;
	}

	/**
	 * 获取 装车ID.
	 *
	 * @return the 装车ID
	 */
	public String getTruckDetailId() {
		return truckDetailId;
	}

	/**
	 * 设置 装车ID.
	 *
	 * @param truckDetailId the new 装车ID
	 */
	public void setTruckDetailId(String truckDetailId) {
		this.truckDetailId = truckDetailId;
	}

	/**
	 * 获取 下一部门到达时间.
	 *
	 * @return the 下一部门到达时间
	 */
	public Date getNextArriveTime() {
		return nextArriveTime;
	}
	
	/**
	 * 设置 下一部门到达时间.
	 *
	 * @param nextArriveTime the new 下一部门到达时间
	 */
	public void setNextArriveTime(Date nextArriveTime) {
		this.nextArriveTime = nextArriveTime;
	}

	/**
	 * 获取 计划的派送或提货时间.
	 *
	 * @return the 计划的派送或提货时间
	 */
	public Date getPlanPickupTime() {
		return planPickupTime;
	}

	/**
	 * 设置 计划的派送或提货时间.
	 *
	 * @param planPickupTime the new 计划的派送或提货时间
	 */
	public void setPlanPickupTime(Date planPickupTime) {
		this.planPickupTime = planPickupTime;
	}

	/** 
	 * 覆盖父类克隆方法
	 * @author 038300-foss-pengzhen
	 * @date 2012-12-25 下午7:58:15
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	/**
	 *  计算同一个运单同时有多少个流水号走同一条路径
	 * @return the waybiilNoCount
	 */
	public String getWaybillNoCount() {
		return waybillNoCount;
	}

	/**
	 *  计算同一个运单同时有多少个流水号走同一条路径
	 * @param waybiilNoCount the waybiilNoCount to set
	 */
	public void setWaybillNoCount(String waybillNoCount) {
		this.waybillNoCount = waybillNoCount;
	}

}