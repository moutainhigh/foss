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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/impl/OuterBranchService.java
 * 
 * FILE NAME        	: OuterBranchService.java
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
package com.deppon.foss.module.pickup.common.client.service.impl;

import java.util.Date;
import java.util.List;

import org.mybatis.guice.transactional.Transactional;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.pickup.common.client.dao.IOuterBranchDao;
import com.deppon.foss.module.pickup.common.client.service.IBaseDataService;
import com.deppon.foss.module.pickup.common.client.service.IOuterBranchService;
import com.deppon.foss.module.pickup.common.client.vo.BranchQueryVo;
import com.google.inject.Inject;

/**
 * 外部网点服务类
 * 
 * @author 105089-foss-yangtong
 * @date 2012-10-24 上午10:50:13
 */
public class OuterBranchService implements IOuterBranchService {

	@Inject
	private IOuterBranchDao outerBranchDao;
	
	/**
	 * 注入基础资料DAO
	 */
	@Inject
	private IBaseDataService baseDataService;

	/**
	 * 
	 * 功能：插条记录
	 * 
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	@Transactional
	@Override
	public void addOuterBranch(OuterBranchEntity outerBranch) {
		outerBranchDao.addOuterBranch(outerBranch);

	}

	/**
	 * 
	 * 功能：更新条记录
	 * 
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	@Transactional
	@Override
	public void updateOuterBranch(OuterBranchEntity outerBranch) {
		outerBranchDao.updateOuterBranch(outerBranch);

	}

	/**
	 * 
	 * 功能：新增或更新记录
	 * 
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	@Override
	public void saveOrUpdate(OuterBranchEntity outerBranch) {
		if(!outerBranchDao.addOuterBranch(outerBranch)){
			outerBranchDao.updateOuterBranch(outerBranch);
		}
	}

	/**
	 * @param outerBranch
	 */
	public void delete(OuterBranchEntity outerBranch) {
		outerBranchDao.delete(outerBranch);
		
	}

	@Override
	public List<BranchQueryVo> queryListByBranch(OuterBranchEntity entity) {
		return outerBranchDao.queryListByBranch(entity);
	}

	@Override
	public OuterBranchEntity queryAgencyBranchInfo(String agencyBranchCode) {
		return outerBranchDao.queryAgencyBranchInfo(agencyBranchCode);
	}

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
	@Override
	public OuterBranchEntity queryOuterBranchByCode(String code,Date billTime){
		return baseDataService.queryOuterBranchByCode(code, billTime);
	}
}