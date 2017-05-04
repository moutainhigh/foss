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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IBillAdvertisingSloganForDepService.java
 * 
 * FILE NAME        	: IBillAdvertisingSloganForDepService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
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
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BillSloganAppOrgEntity;

/**
 * 部门单据广告语service接口
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-10-18 上午9:43:12
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-10-18 上午9:43:12
 * @since
 * @version
 */
public interface IBillAdvertisingSloganForDepService extends IService {

	/**
	 * 新增部门单据广告语
	 * 
	 * @author dp-xieyantao
	 * @date 2012-10-18 上午9:43:12
	 * @param entity
	 * @return
	 * @see
	 */
	int addBillAdvertisingSloganForDept(BillSloganAppOrgEntity entity);

	/**
	 * 根据id作废部门单据广告语信息
	 * 
	 * @author dp-xieyantao
	 * @date 2012-10-18 上午9:43:12
	 * @param idstr
	 *            id拼接的字符串
	 * @param modifyUser
	 *            修改人
	 * @return
	 * @see
	 */
	int deleteBillAdvertisingSloganForDeptByCode(String idstr, String modifyUser);

	/**
	 * 修改部门单据广告语信息
	 * 
	 * @author dp-xieyantao
	 * @date 2012-10-18 上午9:43:12
	 * @param entity
	 * @return
	 * @see
	 */
	int updateBillAdvertisingSloganForDept(BillSloganAppOrgEntity entity);

	/**
	 * 根据传入对象查询符合条件所有部门单据广告语信息
	 * 
	 * @author dp-xieyantao
	 * @date 2012-10-18 上午9:43:12
	 * @param entity
	 * @param limit
	 * @param start
	 * @return
	 * @see
	 */
	List<BillSloganAppOrgEntity> queryBillAdvertisingSloganForDepts(
			BillSloganAppOrgEntity entity, int limit, int start);

	/**
	 * 统计总记录数
	 * 
	 * @author dp-xieyantao
	 * @date 2012-10-18 上午9:43:12
	 * @param entity
	 * @return
	 * @see
	 */
	Long queryRecordCount(BillSloganAppOrgEntity entity);

	/**
	 * <p>
	 * 根据单据广告语虚拟编码、部门编码查询部门单据广告语
	 * </p>
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2013-1-10 下午2:55:12
	 * @param orgCode
	 *            部门编码
	 * @param sloganVirtualCode
	 *            单据广告语虚拟编码
	 * @param appOrgId
	 *            部门单据广告语ID
	 * @return
	 * @see
	 */
	BillSloganAppOrgEntity querySloganAppOrgByCode(String orgCode,
			String sloganVirtualCode, String appOrgId);

}
