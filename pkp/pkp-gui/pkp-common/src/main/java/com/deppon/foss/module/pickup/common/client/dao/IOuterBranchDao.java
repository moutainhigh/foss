/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/IOuterBranchDao.java
 * 
 * FILE NAME        	: IOuterBranchDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.common.client.dao;


import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.OuterBranchParamsDto;
import com.deppon.foss.module.pickup.common.client.vo.BranchQueryVo;


/**
 * 组织信息数据访问接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:dp-yangtong,date:2012-10-12 上午9:36:44, </p>
 * @author dp-yangtong
 * @date 2012-10-12 上午9:36:44
 * @since
 * @version
 */
public interface IOuterBranchDao {
	
	
	/**
	 * 
	 * 功能：插条记录
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	boolean addOuterBranch(OuterBranchEntity outerBranch);

	/**
	 * 
	 * 功能：更新条记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	void updateOuterBranch(OuterBranchEntity outerBranch);
	
	 /**
     * 根据代理网点编码查询外发代理网点和合作伙伴（代理公司）信息 
     * @author oss-jiangfei
     * @date 2012-11-24 上午9:21:53
     * @param agencyBranchCode 代理网点编码
     * @return AgencyBranchOrCompanyDto
     * @see
     */
	OuterBranchEntity queryAgencyBranchCompanyInfo(String agencyBranchCode);
	
	/**
	 * 
	 * 根据代理网点编码查询代理网点信息
	 * @author 097972-foss-dengtingting
	 * @date 2013-1-21 下午4:29:25
	 */
	OuterBranchEntity queryAgencyBranchInfo(String agencyBranchCode);
    
    /**
     * 根据传入参数查询代理网点（空运代理网点和偏线代理网点） 
     * @author 094463-foss-xieyantao
     * @date 2012-11-2 上午9:00:56
     * @param dto 参数封装DTO（包括：目的站、代理网点名称、代理网点类型、用于版本控制时间）
     * @return
     * @see
     */
	List<OuterBranchEntity> queryOuterBranchs(OuterBranchParamsDto dto);

	/**
	 * @param outerBranch
	 */
	void delete(OuterBranchEntity outerBranch);
	
	/**
	 * 根据传入参数查询代理网点信息
	 * @author 097972-foss-dengtingting
	 * @date 2013-3-27 下午6:01:15
	 */
	List<BranchQueryVo> queryListByBranch(OuterBranchEntity entity);

}