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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/LeasedWhitelistVo.java
 * 
 * FILE NAME        	: LeasedWhitelistVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.WhitelistAuditEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.WhitelistAuditQueryDto;
/**
 * 用来交互“外请白名单（司机、外请车）”信息的VO
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-8 下午3:13:18</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-8 下午3:13:18
 * @since
 * @version
 */
public class LeasedWhitelistVo implements Serializable {
    
    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = -3150728276211988591L;

    /**
     * "外请白名单（司机、车辆）"对象
     */
    private WhitelistAuditEntity whitelistAudit;
    

    /**
     * "外请白名单（司机、车辆）"列表集合
     */
    private List<WhitelistAuditEntity> whitelistAuditList;
    
    /**
     * 批量操作ID集合
     */
    private List<String> batchIds;
    
    /**
     * 意见备注
     */
    private String comment;
    
    /**
     * 车辆白名单查询dto
     */
    private WhitelistAuditQueryDto whitelistAuditQueryDto;
    
    /**
     * @return  the whitelistAudit
     */
    public WhitelistAuditEntity getWhitelistAudit() {
        return whitelistAudit;
    }
    
    /**
     * @param whitelistAudit the whitelistAudit to set
     */
    public void setWhitelistAudit(WhitelistAuditEntity whitelistAudit) {
        this.whitelistAudit = whitelistAudit;
    }

    /**
     * @return  the whitelistAuditList
     */
    public List<WhitelistAuditEntity> getWhitelistAuditList() {
        return whitelistAuditList;
    }
    
    /**
     * @param whitelistAuditList the whitelistAuditList to set
     */
    public void setWhitelistAuditList(List<WhitelistAuditEntity> whitelistAuditList) {
        this.whitelistAuditList = whitelistAuditList;
    }
    
    /**
     * @return  the batchIds
     */
    public List<String> getBatchIds() {
        return batchIds;
    }
    
    /**
     * @param batchIds the batchIds to set
     */
    public void setBatchIds(List<String> batchIds) {
        this.batchIds = batchIds;
    }

    /**
     * @return  the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

	
	/**
	 * @get
	 * @return whitelistAuditQueryDto
	 */
	public WhitelistAuditQueryDto getWhitelistAuditQueryDto() {
		/*
		 * @get
		 * @return whitelistAuditQueryDto
		 */
		return whitelistAuditQueryDto;
	}

	
	/**
	 * @set
	 * @param whitelistAuditQueryDto
	 */
	public void setWhitelistAuditQueryDto(
			WhitelistAuditQueryDto whitelistAuditQueryDto) {
		/*
		 *@set
		 *@this.whitelistAuditQueryDto = whitelistAuditQueryDto
		 */
		this.whitelistAuditQueryDto = whitelistAuditQueryDto;
	}
    
}
