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
 *  PROJECT NAME  : tfr-stockchecking-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/api/shared/domain/StDifferReportEntity.java
 *  
 *  FILE NAME          :StDifferReportEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stockchecking.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 清仓差异报告实体类
 * @author foss-wuyingjie
 * @date 2012-10-16 下午3:43:23
 */
public class StDifferReportEntity extends BaseEntity{
	private static final long serialVersionUID = 281836422625961929L;
	/**清仓任务ID*/
    private String stTaskId;
    /**清仓差异报告编号*/
    private String reportCode;
    /**多货件数*/
    private Integer exceedGoodsQty;
    /**少货件数*/
    private Integer lessGoodsQty;
    /**处理状态*/
    private String handleStatus;
    /**创建日期*/
    private Date createTime;
    /**货区编号*/
    private String goodsAreaCode;
    /**获取名称*/
    private String goodsAreaName;
    /**部门编号*/
    private String deptcode;
    //PDA处理差异报告状态
    private String pdaHandleStatus;

    /**
     * 获取 清仓任务ID.
     *
     * @return the 清仓任务ID
     */
    public String getStTaskId() {
        return stTaskId;
    }

    /**
     * 设置 清仓任务ID.
     *
     * @param stTaskId the new 清仓任务ID
     */
    public void setStTaskId(String stTaskId) {
        this.stTaskId = stTaskId;
    }

    /**
     * 获取 清仓差异报告编号.
     *
     * @return the 清仓差异报告编号
     */
    public String getReportCode() {
        return reportCode;
    }

    /**
     * 设置 清仓差异报告编号.
     *
     * @param reportCode the new 清仓差异报告编号
     */
    public void setReportCode(String reportCode) {
        this.reportCode = reportCode;
    }

    /**
     * 获取 多货件数.
     *
     * @return the 多货件数
     */
    public Integer getExceedGoodsQty() {
        return exceedGoodsQty;
    }

    /**
     * 设置 多货件数.
     *
     * @param exceedGoodsQty the new 多货件数
     */
    public void setExceedGoodsQty(Integer exceedGoodsQty) {
        this.exceedGoodsQty = exceedGoodsQty;
    }

    /**
     * 获取 少货件数.
     *
     * @return the 少货件数
     */
    public Integer getLessGoodsQty() {
        return lessGoodsQty;
    }

    /**
     * 设置 少货件数.
     *
     * @param lessGoodsQty the new 少货件数
     */
    public void setLessGoodsQty(Integer lessGoodsQty) {
        this.lessGoodsQty = lessGoodsQty;
    }

    /**
     * 获取 处理状态.
     *
     * @return the 处理状态
     */
    public String getHandleStatus() {
        return handleStatus;
    }

    /**
     * 设置 处理状态.
     *
     * @param handleStatus the new 处理状态
     */
    public void setHandleStatus(String handleStatus) {
        this.handleStatus = handleStatus;
    }

    /**
     * 获取 创建日期.
     *
     * @return the 创建日期
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置 创建日期.
     *
     * @param createTime the new 创建日期
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取 货区编号.
     *
     * @return the 货区编号
     */
    public String getGoodsAreaCode() {
        return goodsAreaCode;
    }

    /**
     * 设置 货区编号.
     *
     * @param goodsAreaCode the new 货区编号
     */
    public void setGoodsAreaCode(String goodsAreaCode) {
        this.goodsAreaCode = goodsAreaCode;
    }

    /**
     * 获取 获取名称.
     *
     * @return the 获取名称
     */
    public String getGoodsAreaName() {
        return goodsAreaName;
    }

    /**
     * 设置 获取名称.
     *
     * @param goodsAreaName the new 获取名称
     */
    public void setGoodsAreaName(String goodsAreaName) {
        this.goodsAreaName = goodsAreaName;
    }

	/**
	 * 获取 部门编号.
	 *
	 * @return the 部门编号
	 */
	public String getDeptcode() {
		return deptcode;
	}

	/**
	 * 设置 部门编号.
	 *
	 * @param deptcode the new 部门编号
	 */
	public void setDeptcode(String deptcode) {
		this.deptcode = deptcode;
	}

	/**
	 * @return the pdaHandleStatus
	 */
	public String getPdaHandleStatus() {
		return pdaHandleStatus;
	}

	/**
	 * @param pdaHandleStatus the pdaHandleStatus to set
	 */
	public void setPdaHandleStatus(String pdaHandleStatus) {
		this.pdaHandleStatus = pdaHandleStatus;
	}
	
}