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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/IOuterBranchService.java
 * 
 * FILE NAME        	: IOuterBranchService.java
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
package com.deppon.foss.module.pickup.common.client.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.pickup.common.client.vo.BranchQueryVo;

/**
 * 外部网点服务接口
 * @author 105089-foss-yangtong
 * @date 2012-10-24 下午3:40:46
 */
public interface IOuterBranchService {
	
	/**
     * 插条记录
     */
	void addOuterBranch(OuterBranchEntity outerBranch);
	/**
	 * 更新条记录
	 */
	void updateOuterBranch(OuterBranchEntity outerBranch);
	/**
	 * 新增或更新记录
	 */
	void saveOrUpdate(OuterBranchEntity outerBranch);
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
	
	/**
	 * 
	 * 根据代理网点编码查询代理网点信息
	 * @author 097972-foss-dengtingting
	 * @date 2013-1-21 下午4:29:25
	 */
	OuterBranchEntity queryAgencyBranchInfo(String agencyBranchCode);
	
    /**
 	 * 根据历史时间和代理网点编码查询代理网点信息（查询历史代理网点信息）
 	 * 
 	 * 若时间为空，则只根据代理网点编码查询代理网点信息
 	 * 否则将根据时间，查询在creatTime和modifyTime时间段的代理网点
 	 * 不根据Active='Y'来查询
 	 * 
 	 * @author 026123-foss-lifengteng
 	 * @date 2013-4-17 下午6:02:26
 	 */
	OuterBranchEntity queryOuterBranchByCode(String code, Date billTime);
}