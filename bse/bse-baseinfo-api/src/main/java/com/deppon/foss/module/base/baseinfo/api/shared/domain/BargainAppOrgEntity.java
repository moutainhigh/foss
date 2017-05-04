/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/BargainAppOrgEntity.java
 * 
 * FILE NAME        	: BargainAppOrgEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 合同适用部门实体类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-16 下午4:16:21 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-16 下午4:16:21
 * @since
 * @version
 */
public class BargainAppOrgEntity extends BaseEntity{
    
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -3044193903469598097L;
    
    /**
     * 对应部门标杆编码.
     */
    private String unifiedCode;
    
    /**
     * 开始时间.
     */
    private Date beginTime;
    
    /**
     * 结束时间.
     */
    private Date endTime;
    
    /**
     * 工作流号.
     */
    private String workflowNo;
    
    /**
     * OA审批状态.
     */
    private String oaApproveStatus;
    
    /**
     * 审批人.
     */
    private String approver;
    
    /**
     * 工作流类型.
     */
    private String workflowStatus;
    
    /**
     * 状态.
     */
    private String status;
    
    /**
     * 是否归属部门.
     */
    private String orgBelonging;
    
    /**
     * 是否启用.
     */
    private String active;
    
    /**
     * 合同ID.
     */
    private BigDecimal bargainId;
    
    /**
     * 虚拟编码.
     */
    private String virtualCode;
    
    /**
     * 在CRM中fid.
     */
    private BigDecimal crmId; 
    
    /**
     * 多个合同适用部门对应一个客户合同.
     */
    private CusBargainEntity bargainEntity;
    
    /**
     * 获取 对应部门标杆编码.
     *
     * @return  the unifiedCode
     */
    public String getUnifiedCode() {
        return unifiedCode;
    }
    
    /**
     * 设置 对应部门标杆编码.
     *
     * @param unifiedCode the unifiedCode to set
     */
    public void setUnifiedCode(String unifiedCode) {
        this.unifiedCode = unifiedCode;
    }
    
    /**
     * 获取 开始时间.
     *
     * @return  the beginTime
     */
    public Date getBeginTime() {
        return beginTime;
    }
    
    /**
     * 设置 开始时间.
     *
     * @param beginTime the beginTime to set
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }
    
    /**
     * 获取 结束时间.
     *
     * @return  the endTime
     */
    public Date getEndTime() {
        return endTime;
    }
    
    /**
     * 设置 结束时间.
     *
     * @param endTime the endTime to set
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
    /**
     * 获取 工作流号.
     *
     * @return  the workflowNo
     */
    public String getWorkflowNo() {
        return workflowNo;
    }
    
    /**
     * 设置 工作流号.
     *
     * @param workflowNo the workflowNo to set
     */
    public void setWorkflowNo(String workflowNo) {
        this.workflowNo = workflowNo;
    }
    
    /**
     * 获取 oA审批状态.
     *
     * @return  the oaApproveStatus
     */
    public String getOaApproveStatus() {
        return oaApproveStatus;
    }
    
    /**
     * 设置 oA审批状态.
     *
     * @param oaApproveStatus the oaApproveStatus to set
     */
    public void setOaApproveStatus(String oaApproveStatus) {
        this.oaApproveStatus = oaApproveStatus;
    }
    
    /**
     * 获取 审批人.
     *
     * @return  the approver
     */
    public String getApprover() {
        return approver;
    }
    
    /**
     * 设置 审批人.
     *
     * @param approver the approver to set
     */
    public void setApprover(String approver) {
        this.approver = approver;
    }
    
    /**
     * 获取 工作流类型.
     *
     * @return  the workflowStatus
     */
    public String getWorkflowStatus() {
        return workflowStatus;
    }
    
    /**
     * 设置 工作流类型.
     *
     * @param workflowStatus the workflowStatus to set
     */
    public void setWorkflowStatus(String workflowStatus) {
        this.workflowStatus = workflowStatus;
    }
    
    /**
     * 获取 状态.
     *
     * @return  the status
     */
    public String getStatus() {
        return status;
    }
    
    /**
     * 设置 状态.
     *
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     * 获取 是否归属部门.
     *
     * @return  the orgBelonging
     */
    public String getOrgBelonging() {
        return orgBelonging;
    }
    
    /**
     * 设置 是否归属部门.
     *
     * @param orgBelonging the orgBelonging to set
     */
    public void setOrgBelonging(String orgBelonging) {
        this.orgBelonging = orgBelonging;
    }
    
    /**
     * 获取 是否启用.
     *
     * @return  the active
     */
    public String getActive() {
        return active;
    }
    
    /**
     * 设置 是否启用.
     *
     * @param active the active to set
     */
    public void setActive(String active) {
        this.active = active;
    }
    
    /**
     * 获取 合同ID.
     *
     * @return  the bargainId
     */
    public BigDecimal getBargainId() {
        return bargainId;
    }
    
    /**
     * 设置 合同ID.
     *
     * @param bargainId the bargainId to set
     */
    public void setBargainId(BigDecimal bargainId) {
        this.bargainId = bargainId;
    }
    
    /**
     * 获取 虚拟编码.
     *
     * @return  the virtualCode
     */
    public String getVirtualCode() {
        return virtualCode;
    }
    
    /**
     * 设置 虚拟编码.
     *
     * @param virtualCode the virtualCode to set
     */
    public void setVirtualCode(String virtualCode) {
        this.virtualCode = virtualCode;
    }
    
    /**
     * 获取 在CRM中fid.
     *
     * @return  the crmId
     */
    public BigDecimal getCrmId() {
        return crmId;
    }
    
    /**
     * 设置 在CRM中fid.
     *
     * @param crmId the crmId to set
     */
    public void setCrmId(BigDecimal crmId) {
        this.crmId = crmId;
    }
    
    /**
     * 获取 多个合同适用部门对应一个客户合同.
     *
     * @return  the bargainEntity
     */
    public CusBargainEntity getBargainEntity() {
        return bargainEntity;
    }
    
    /**
     * 设置 多个合同适用部门对应一个客户合同.
     *
     * @param bargainEntity the bargainEntity to set
     */
    public void setBargainEntity(CusBargainEntity bargainEntity) {
        this.bargainEntity = bargainEntity;
    }
    
    
}
