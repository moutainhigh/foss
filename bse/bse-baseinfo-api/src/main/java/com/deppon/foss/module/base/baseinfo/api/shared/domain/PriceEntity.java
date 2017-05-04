/**
 *  initial comments.
 */
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
 * PROJECT NAME	: pkp-pricing-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/domain/PriceEntity.java
 * 
 * FILE NAME        	: PriceEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;
import com.deppon.foss.framework.entity.BaseEntity;

/**
 * PriceEntity计价条目 (配合定价项目降价发券方案引用PKP)
 * @author 187862-dujunhui
 * @date 2014-11-11 下午2:01:37
 * @version 1.0.0
 */
public class PriceEntity extends BaseEntity {
    /**
     * 
     */
    private static final long serialVersionUID = 8178707383441942728L;
    /**
     * 计价条目名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    /**
     * 是否激活
     */
    private String active;

    /**
     * 父节点ID
     */
    private String refId;

    /**
     * 父节点CODE
     */
    private String refCode;
    
    /**
     * 所属父级名称, 用于显示
     */
    private String refName;

    /**
     * 
     * 所属父级名称
     * @author 025000-FOSS-helong
     * @date 2013-7-2
     */
    public String getRefName() {
		return refName;
	}


    /**
     * 
     * 所属父级名称
     * @author 025000-FOSS-helong
     * @date 2013-7-2
     */
	public void setRefName(String refName) {
		this.refName = refName;
	}

	/**
     * 版本号
     */
    private Long versionNo;

    /**
     * 描述
     */
    private String remarks;

    /**
     * 有效起始时间
     */
    private Date beginTime;

    /**
     * 有效截止时间
     */
    private Date endTime;
    
    /**
     * 修改时间
     */
    private Date  modifyTime; 

    /**
     * 创建人部门CODE
     */
    private String createOrgCode;

    /**
     * 修改人部门CODE
     */
    private String modifyOrgCode;
    
    /**
     * 归集类型ID
     */
    private String blongPricingId;
    
    /**
     * 归集类型CODE
     */
    private String blongPricingCode;
    
    /**
     * 归集类型名称
     */
    private String blongPricingName;
    
 
    
    
    public String getModifyUserName() {
        return modifyUserName;
    }


    
    public void setModifyUserName(String modifyUserName) {
        this.modifyUserName = modifyUserName;
    }

    /**
     * 修改人
     */
    private String modifyUserName; 

    
    /**
     * 营业日期 
     */
    private Date receiveDate;

    
    /**
     * 获取 营业日期.
     *
     * @return the 营业日期
     */
    public Date getReceiveDate() {
        return receiveDate;
    }

    
    /**
     * 设置 营业日期.
     *
     * @param receiveDate the new 营业日期
     */
    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    /**
     * 获取 计价条目名称.
     *
     * @return the 计价条目名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置 计价条目名称.
     *
     * @param name the new 计价条目名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取 编码.
     *
     * @return the 编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置 编码.
     *
     * @param code the new 编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取 是否激活.
     *
     * @return the 是否激活
     */
    public String getActive() {
        return active;
    }

    /**
     * 设置 是否激活.
     *
     * @param active the new 是否激活
     */
    public void setActive(String active) {
        this.active = active;
    }

    /**
     * 获取 父节点ID.
     *
     * @return the 父节点ID
     */
    public String getRefId() {
        return refId;
    }

    /**
     * 设置 父节点ID.
     *
     * @param refId the new 父节点ID
     */
    public void setRefId(String refId) {
        this.refId = refId;
    }

    /**
     * 获取 父节点CODE.
     *
     * @return the 父节点CODE
     */
    public String getRefCode() {
        return refCode;
    }

    /**
     * 设置 父节点CODE.
     *
     * @param refCode the new 父节点CODE
     */
    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    /**
     * 获取 版本号.
     *
     * @return the 版本号
     */
    public Long getVersionNo() {
        return versionNo;
    }

    /**
     * 设置 版本号.
     *
     * @param versionNo the new 版本号
     */
    public void setVersionNo(Long versionNo) {
        this.versionNo = versionNo;
    }

    /**
     * 获取 描述.
     *
     * @return the 描述
     */
    public String getRemarks() {
		return remarks;
	}
   
    /**
     * 设置 描述.
     *
     * @param description the new 描述
     */
    public void setRemarks(String remarks) {
		this.remarks = remarks;
	} 

    /**
     * 获取 有效起始时间.
     *
     * @return the 有效起始时间
     */
    public Date getBeginTime() {
        return beginTime;
    }

	/**
     * 设置 有效起始时间.
     *
     * @param beginTime the new 有效起始时间
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * 获取 有效截止时间.
     *
     * @return the 有效截止时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置 有效截止时间.
     *
     * @param endTime the new 有效截止时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取 创建人部门CODE.
     *
     * @return the 创建人部门CODE
     */
    public String getCreateOrgCode() {
        return createOrgCode;
    }

    /**
     * 设置 创建人部门CODE.
     *
     * @param createOrgCode the new 创建人部门CODE
     */
    public void setCreateOrgCode(String createOrgCode) {
        this.createOrgCode = createOrgCode;
    }

    /**
     * 获取 修改人部门CODE.
     *
     * @return the 修改人部门CODE
     */
    public String getModifyOrgCode() {
        return modifyOrgCode;
    }

    /**
     * 设置 修改人部门CODE.
     *
     * @param modifyOrgCode the new 修改人部门CODE
     */
    public void setModifyOrgCode(String modifyOrgCode) {
        this.modifyOrgCode = modifyOrgCode;
    }

	/**
	 * 获取 归集类型ID.
	 *
	 * @return the 归集类型ID
	 */
	public String getBlongPricingId() {
		return blongPricingId;
	}

	/**
	 * 设置 归集类型ID.
	 *
	 * @param blongPricingId the new 归集类型ID
	 */
	public void setBlongPricingId(String blongPricingId) {
		this.blongPricingId = blongPricingId;
	}

	/**
	 * 获取 归集类型CODE.
	 *
	 * @return the 归集类型CODE
	 */
	public String getBlongPricingCode() {
		return blongPricingCode;
	}

	/**
	 * 设置 归集类型CODE.
	 *
	 * @param blongPricingCode the new 归集类型CODE
	 */
	public void setBlongPricingCode(String blongPricingCode) {
		this.blongPricingCode = blongPricingCode;
	}

	/**
	 * 获取 归集类型名称.
	 *
	 * @return the 归集类型名称
	 */
	public String getBlongPricingName() {
		return blongPricingName;
	}

	/**
	 * 设置 归集类型名称.
	 *
	 * @param blongPricingName the new 归集类型名称
	 */
	public void setBlongPricingName(String blongPricingName) {
		this.blongPricingName = blongPricingName;
	}
	
    
	/**
	 * 获取 修改时间.
	 *
	 * @return the 修改时间
	 */
	public Date getModifyTime() {
	        return modifyTime;
	}

	/**
	 * 设置 修改时间.
	 *
	 * @param modifyTime the new 修改时间
	 */
	public void setModifyTime(Date modifyTime) {
	        this.modifyTime = modifyTime;
	}
}