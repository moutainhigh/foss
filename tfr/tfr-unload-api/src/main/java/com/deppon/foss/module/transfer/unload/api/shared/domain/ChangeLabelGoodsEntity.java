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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/shared/domain/ChangeLabelGoodsEntity.java
 *  
 *  FILE NAME          :ChangeLabelGoodsEntity.java
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
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 重贴标签货物
 * @author ibm-zhangyixin
 * @date 2012-11-26 下午7:33:07
 */
public class ChangeLabelGoodsEntity extends BaseEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 854619993228304243L;

	/**ID**/
	private String id;

	/**单据编号**/
    private String billNo;			

    /**运单号**/
    private String waybillNo;		

    /**流水号**/
    private String serialNo;		

    /**更换原因**/
    private String changeReason;	

    /**发现时间**/
    private Date discoverTime;		

    /**发现环节**/
    private String discoverTache;	

    /**处理状态**/
    private String handleStatus;	

    /**处理时间**/
    private Date handleTime;		

    /**处理人编号**/
    private String handlerCode;		

    /**处理人名称**/
    private String handlerName;		

    /**发现部门名称**/
    private String orgName;			

    /**发现部门编号**/
    private String orgCode;			

    /** 
     * id
     * @author ibm-zhangyixin
     * @date 2012-12-25 下午6:51:43
     * @see com.deppon.foss.framework.entity.BaseEntity#getId()
     */
    public String getId() {
        return id;
    }

    /** 
     * id
     * @author ibm-zhangyixin
     * @date 2012-12-25 下午6:51:43
     * @see com.deppon.foss.framework.entity.BaseEntity#setId(java.lang.String)
     */
    public void setId(String id) {
        this.id = id;
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
     * 获取 流水号*.
     *
     * @return the 流水号*
     */
    public String getSerialNo() {
        return serialNo;
    }

    /**
     * 设置 流水号*.
     *
     * @param serialNo the new 流水号*
     */
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    /**
     * 获取 更换原因*.
     *
     * @return the 更换原因*
     */
    public String getChangeReason() {
        return changeReason;
    }

    /**
     * 设置 更换原因*.
     *
     * @param changeReason the new 更换原因*
     */
    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }

    /**
     * 获取 发现时间*.
     *
     * @return the 发现时间*
     */
    public Date getDiscoverTime() {
        return discoverTime;
    }

    /**
     * 设置 发现时间*.
     *
     * @param discoverTime the new 发现时间*
     */
    public void setDiscoverTime(Date discoverTime) {
        this.discoverTime = discoverTime;
    }

    /**
     * 获取 发现环节*.
     *
     * @return the 发现环节*
     */
    public String getDiscoverTache() {
        return discoverTache;
    }

    /**
     * 设置 发现环节*.
     *
     * @param discoverTache the new 发现环节*
     */
    public void setDiscoverTache(String discoverTache) {
        this.discoverTache = discoverTache;
    }

    /**
     * 获取 处理状态*.
     *
     * @return the 处理状态*
     */
    public String getHandleStatus() {
        return handleStatus;
    }

    /**
     * 设置 处理状态*.
     *
     * @param handleStatus the new 处理状态*
     */
    public void setHandleStatus(String handleStatus) {
        this.handleStatus = handleStatus;
    }

    /**
     * 获取 处理时间*.
     *
     * @return the 处理时间*
     */
    public Date getHandleTime() {
        return handleTime;
    }

    /**
     * 设置 处理时间*.
     *
     * @param handleTime the new 处理时间*
     */
    public void setHandleTime(Date handleTime) {
        this.handleTime = handleTime;
    }

    /**
     * 获取 处理人编号*.
     *
     * @return the 处理人编号*
     */
    public String getHandlerCode() {
        return handlerCode;
    }

    /**
     * 设置 处理人编号*.
     *
     * @param handlerCode the new 处理人编号*
     */
    public void setHandlerCode(String handlerCode) {
        this.handlerCode = handlerCode;
    }

    /**
     * 获取 处理人名称*.
     *
     * @return the 处理人名称*
     */
    public String getHandlerName() {
        return handlerName;
    }

    /**
     * 设置 处理人名称*.
     *
     * @param handlerName the new 处理人名称*
     */
    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }

    /**
     * 获取 发现部门名称*.
     *
     * @return the 发现部门名称*
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * 设置 发现部门名称*.
     *
     * @param orgName the new 发现部门名称*
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * 获取 发现部门编号*.
     *
     * @return the 发现部门编号*
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * 设置 发现部门编号*.
     *
     * @param orgCode the new 发现部门编号*
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
}