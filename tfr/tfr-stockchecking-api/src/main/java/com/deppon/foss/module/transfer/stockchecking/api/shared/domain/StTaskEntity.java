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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stockchecking/api/shared/domain/StTaskEntity.java
 *  
 *  FILE NAME          :StTaskEntity.java
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
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 清仓任务实体类
 * @author foss-wuyingjie
 * @date 2012-10-16 下午2:48:47
 */
public class StTaskEntity extends BaseEntity{

	private static final long serialVersionUID = -5523515392095682208L;
	/**清仓任务编号*/
    private String taskNo;
    /**库区编号*/
    private String goodsareacode;
    /**库区名称*/
    private String goodsareaname;
    /**清仓任务状态*/
    private String taskStatus;
    /**部门编号*/
    private String deptcode;
    /**清仓任务创建时间*/
    private Date createtime;
    /**清仓任务完成时间*/
    private Date finishtime;
    /**是否为PDA创建任务*/
    private String ispda;
    /**建立清仓任务的pda设备编号 -- PDA_NO */
    private String pdaNo;
    /**创建人编号 -- CREATOR_CODE */
    private String creatorCode;
    /**创建人名称 -- CREATOR_NAME */
    private String creatorName;
    /**起始件数*/
    private Integer startQty;
    /**结束件数*/
    private Integer endQty;
    /**提货方式*/
    private String receiveMethod;
    /**分区*/
    private String districtCode;
    /**分区名称*/
    private String districtName;
    /**运单提货方式list*/
    private List<String> receiveMethodList;
    /**行政区域list*/
    private List<String> districtCodeList;
    
    public List<String> getReceiveMethodList() {
		return receiveMethodList;
	}

	public void setReceiveMethodList(List<String> receiveMethodList) {
		this.receiveMethodList = receiveMethodList;
	}

	public List<String> getDistrictCodeList() {
		return districtCodeList;
	}

	public void setDistrictCodeList(List<String> districtCodeList) {
		this.districtCodeList = districtCodeList;
	}

	public String getReceiveMethod() {
		return receiveMethod;
	}

	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public Integer getStartQty() {
		return startQty;
	}

	public void setStartQty(Integer startQty) {
		this.startQty = startQty;
	}

	public Integer getEndQty() {
		return endQty;
	}

	public void setEndQty(Integer endQty) {
		this.endQty = endQty;
	}

	/**
     * 获取 清仓任务编号.
     *
     * @return the 清仓任务编号
     */
    public String getTaskNo() {
        return taskNo;
    }

    /**
     * 设置 清仓任务编号.
     *
     * @param taskNo the new 清仓任务编号
     */
    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    /**
     * 获取 库区编号.
     *
     * @return the 库区编号
     */
    public String getGoodsareacode() {
        return goodsareacode;
    }

    /**
     * 设置 库区编号.
     *
     * @param goodsareacode the new 库区编号
     */
    public void setGoodsareacode(String goodsareacode) {
        this.goodsareacode = goodsareacode;
    }

    /**
     * 获取 库区名称.
     *
     * @return the 库区名称
     */
    public String getGoodsareaname() {
        return goodsareaname;
    }

    /**
     * 设置 库区名称.
     *
     * @param goodsareaname the new 库区名称
     */
    public void setGoodsareaname(String goodsareaname) {
        this.goodsareaname = goodsareaname;
    }

    /**
     * 获取 清仓任务状态.
     *
     * @return the 清仓任务状态
     */
    public String getTaskStatus() {
		return taskStatus;
	}

	/**
	 * 设置 清仓任务状态.
	 *
	 * @param taskStatus the new 清仓任务状态
	 */
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
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
     * 获取 清仓任务创建时间.
     *
     * @return the 清仓任务创建时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 设置 清仓任务创建时间.
     *
     * @param createtime the new 清仓任务创建时间
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * 获取 清仓任务完成时间.
     *
     * @return the 清仓任务完成时间
     */
    public Date getFinishtime() {
        return finishtime;
    }

    /**
     * 设置 清仓任务完成时间.
     *
     * @param finishtime the new 清仓任务完成时间
     */
    public void setFinishtime(Date finishtime) {
        this.finishtime = finishtime;
    }

    /**
     * 获取 是否为PDA创建任务.
     *
     * @return the 是否为PDA创建任务
     */
    public String getIspda() {
        return ispda;
    }

    /**
     * 设置 是否为PDA创建任务.
     *
     * @param ispda the new 是否为PDA创建任务
     */
    public void setIspda(String ispda) {
        this.ispda = ispda;
    }

	/**
	 * 获取 建立清仓任务的pda设备编号.
	 *
	 * @return the 建立清仓任务的pda设备编号
	 */
	public String getPdaNo() {
		return pdaNo;
	}

	/**
	 * 设置 建立清仓任务的pda设备编号.
	 *
	 * @param pdaNo the new 建立清仓任务的pda设备编号
	 */
	public void setPdaNo(String pdaNo) {
		this.pdaNo = pdaNo;
	}

	/**
	 * 获取 创建人编号.
	 *
	 * @return the 创建人编号
	 */
	public String getCreatorCode() {
		return creatorCode;
	}

	/**
	 * 设置 创建人编号.
	 *
	 * @param creatorCode the new 创建人编号
	 */
	public void setCreatorCode(String creatorCode) {
		this.creatorCode = creatorCode;
	}

	/**
	 * 获取 创建人名称.
	 *
	 * @return the 创建人名称
	 */
	public String getCreatorName() {
		return creatorName;
	}

	/**
	 * 设置 创建人名称.
	 *
	 * @param creatorName the new 创建人名称
	 */
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
}