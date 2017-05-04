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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/domain/ChangePathEntity.java
 * 
 *  FILE NAME     :ChangePathEntity.java
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
 * 修改走货路径entity
 */
public class ChangePathEntity extends BaseEntity implements Cloneable{

	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 80900140785706413L;
	/**
	 * 主键ID
	 */
    private String changePathId;
    /**
     * 出发部门编码
     */
    private String origOrgCode;
    /**
     * 下一到达部门编码
     */
    private String destOrgCode;
    /**
     * 修改前路径
     */
    private String origPath;
    /**
     * 修改后路径
     */
    private String changePath;
    /**
     * 生效开始时间
     */
    private Date effectStartTime;
    /**
     * 生效结束时间
     */
    private Date effectEndTime;
    /**
     * 改变类型 (合车,非合车)
     */
    private String changeType;
    /**
     * 调整运单号
     */
    private String waybillNo;
    /**
     * 调整运单原货区编号
     */
    private String origGoodsAreaCode;
    /**
     * 路段号
     */
    private String routeNo;
    /**
     * 预计出发时间
     */
    private Date planStartTime;
    /**
     * 预计到达时间
     */
    private Date planArriveTime;

    /**
     * 获取 调整运单号.
     *
     * @return the 调整运单号
     */
    public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * 设置 调整运单号.
	 *
	 * @param waybillNo the new 调整运单号
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * 获取 调整运单原货区编号.
	 *
	 * @return the 调整运单原货区编号
	 */
	public String getOrigGoodsAreaCode() {
		return origGoodsAreaCode;
	}

	/**
	 * 设置 调整运单原货区编号.
	 *
	 * @param origGoodsAreaCode the new 调整运单原货区编号
	 */
	public void setOrigGoodsAreaCode(String origGoodsAreaCode) {
		this.origGoodsAreaCode = origGoodsAreaCode;
	}

	/**
	 * 获取 主键ID.
	 *
	 * @return the 主键ID
	 */
	public String getChangePathId() {
        return changePathId;
    }

    /**
     * 设置 主键ID.
     *
     * @param changePathId the new 主键ID
     */
    public void setChangePathId(String changePathId) {
        this.changePathId = changePathId;
    }

    /**
     * 获取 出发部门编码.
     *
     * @return the 出发部门编码
     */
    public String getOrigOrgCode() {
        return origOrgCode;
    }

    /**
     * 设置 出发部门编码.
     *
     * @param origOrgCode the new 出发部门编码
     */
    public void setOrigOrgCode(String origOrgCode) {
        this.origOrgCode = origOrgCode;
    }

    /**
     * 获取 下一到达部门编码.
     *
     * @return the 下一到达部门编码
     */
    public String getDestOrgCode() {
        return destOrgCode;
    }

    /**
     * 设置 下一到达部门编码.
     *
     * @param destOrgCode the new 下一到达部门编码
     */
    public void setDestOrgCode(String destOrgCode) {
        this.destOrgCode = destOrgCode;
    }

    /**
     * 获取 修改前路径.
     *
     * @return the 修改前路径
     */
    public String getOrigPath() {
        return origPath;
    }

    /**
     * 设置 修改前路径.
     *
     * @param origPath the new 修改前路径
     */
    public void setOrigPath(String origPath) {
        this.origPath = origPath;
    }

    /**
     * 获取 修改后路径.
     *
     * @return the 修改后路径
     */
    public String getChangePath() {
        return changePath;
    }

    /**
     * 设置 修改后路径.
     *
     * @param changePath the new 修改后路径
     */
    public void setChangePath(String changePath) {
        this.changePath = changePath;
    }

    /**
     * 获取 生效开始时间.
     *
     * @return the 生效开始时间
     */
    @DateFormat
    public Date getEffectStartTime() {
        return effectStartTime;
    }

    /**
     * 设置 生效开始时间.
     *
     * @param effectStartTime the new 生效开始时间
     */
    @DateFormat
    public void setEffectStartTime(Date effectStartTime) {
        this.effectStartTime = effectStartTime;
    }

    /**
     * 获取 生效结束时间.
     *
     * @return the 生效结束时间
     */
    @DateFormat
    public Date getEffectEndTime() {
        return effectEndTime;
    }
    
    /**
     * 设置 生效结束时间.
     *
     * @param effectEndTime the new 生效结束时间
     */
    @DateFormat
    public void setEffectEndTime(Date effectEndTime) {
        this.effectEndTime = effectEndTime;
    }

    /**
     * 获取 改变类型 (合车,非合车).
     *
     * @return the 改变类型 (合车,非合车)
     */
    public String getChangeType() {
        return changeType;
    }

    /**
     * 设置 改变类型 (合车,非合车).
     *
     * @param changeType the new 改变类型 (合车,非合车)
     */
    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

	/**
	 * 获取 路段号.
	 *
	 * @return the 路段号
	 */
	public String getRouteNo() {
		return routeNo;
	}

	/**
	 * 设置 路段号.
	 *
	 * @param routeNo the new 路段号
	 */
	public void setRouteNo(String routeNo) {
		this.routeNo = routeNo;
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
	 * 覆盖父类克隆方法
	 * @author 038300-foss-pengzhen
	 * @date 2012-12-25 下午9:10:07
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
    
}