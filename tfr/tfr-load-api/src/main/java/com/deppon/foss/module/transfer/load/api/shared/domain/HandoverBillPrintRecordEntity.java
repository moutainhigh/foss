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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/domain/HandoverBillPrintRecordEntity.java
 *  
 *  FILE NAME          :HandoverBillPrintRecordEntity.java
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

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 交接单打印记录表 
 * @author ibm-zhangyixin
 * @date 2012-10-30 下午5:21:27
 */
public class HandoverBillPrintRecordEntity extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**ID**/
	private String id;

	/**交接单号**/
    private String handoverNo;

    /**操作人编号**/
    private String operatorCode;

    /**操作人姓名**/
    private String operatorName;

    /**操作时间**/
    private Date operatTime;

    /**部门编号**/
    private String orgCode;
    
    /**部门名称**/
    private String orgName;

    /**新增时间**/
    private Date createTime;

    /** 
     * ID
     * @author ibm-zhangyixin
     * @date 2012-12-25 下午5:41:44
     * @see com.deppon.foss.framework.entity.BaseEntity#getId()
     */
    public String getId() {
        return id;
    }

    /** 
     * ID
     * @author ibm-zhangyixin
     * @date 2012-12-25 下午5:41:44
     * @see com.deppon.foss.framework.entity.BaseEntity#setId(java.lang.String)
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取 交接单号*.
     *
     * @return the 交接单号*
     */
    public String getHandoverNo() {
        return handoverNo;
    }

    /**
     * 设置 交接单号*.
     *
     * @param handoverNo the new 交接单号*
     */
    public void setHandoverNo(String handoverNo) {
        this.handoverNo = handoverNo;
    }

    /**
     * 获取 操作人编号*.
     *
     * @return the 操作人编号*
     */
    public String getOperatorCode() {
        return operatorCode;
    }

    /**
     * 设置 操作人编号*.
     *
     * @param operatorCode the new 操作人编号*
     */
    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    /**
     * 获取 操作人姓名*.
     *
     * @return the 操作人姓名*
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * 设置 操作人姓名*.
     *
     * @param operatorName the new 操作人姓名*
     */
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    /**
     * 获取 操作时间*.
     *
     * @return the 操作时间*
     */
    public Date getOperatTime() {
        return operatTime;
    }

    /**
     * 设置 操作时间*.
     *
     * @param operatTime the new 操作时间*
     */
    public void setOperatTime(Date operatTime) {
        this.operatTime = operatTime;
    }

    /**
     * 获取 部门编号*.
     *
     * @return the 部门编号*
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * 设置 部门编号*.
     *
     * @param orgCode the new 部门编号*
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    /**
     * 获取 部门名称*.
     *
     * @return the 部门名称*
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * 设置 部门名称*.
     *
     * @param orgName the new 部门名称*
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * 获取 新增时间*.
     *
     * @return the 新增时间*
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置 新增时间*.
     *
     * @param createTime the new 新增时间*
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}