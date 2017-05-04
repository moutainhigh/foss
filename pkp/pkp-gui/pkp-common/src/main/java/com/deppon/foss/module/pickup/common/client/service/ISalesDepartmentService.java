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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/service/ISalesDepartmentService.java
 * 
 * FILE NAME        	: ISalesDepartmentService.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesBillingGroupEntity;
import com.deppon.foss.module.pickup.common.client.dto.QueryPickupPointDto;
import com.deppon.foss.module.pickup.common.client.vo.BranchQueryVo;
import com.deppon.foss.module.pickup.waybill.shared.dto.SalesDepartmentCityDto;

/**
 * 营业部服务接口
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-jiangfei,date:2012-10-31 下午4:09:00,
 * </p>
 * 
 * @author foss-jiangfei
 * @date 2012-10-31 下午4:09:00
 * @since
 * @version
 */
public interface ISalesDepartmentService {
	/**
	 * <查询营业网点信息>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-10-31 下午4:19:18
	 * @param code
	 * @param name
	 * @param isqueryFuzzy
	 * @return
	 * @see
	 */
	List<SaleDepartmentEntity> querySalesDepartmentInfo(
			QueryPickupPointDto dto);

	/**
	 * 插条记录
	 */
	void addSalesDepartment(SaleDepartmentEntity saleDepartment);

	/**
	 * 更新条记录
	 */
	void updateSalesDepartment(SaleDepartmentEntity saleDepartment);

	/**
	 * 新增或更新记录
	 */
	void saveOrUpdate(SaleDepartmentEntity saleDepartment);
	
    /**
     * 
     * 根据部门名称查询
     * @author 025000-FOSS-helong
     * @date 2012-12-17 上午11:05:01
     */
    List<SaleDepartmentEntity> querySaleDepartmentByName(String name);
    /**
	 * @param saleDepartment
	 */
	 void delete(SaleDepartmentEntity saleDepartment);
    /**
     * 
     * 根据部门名称和所属集中开单组查询
     * @author 025000-FOSS-helong
     * @date 2012-12-17 上午11:05:01
     */
    List<SaleDepartmentEntity> querySaleDepartmentByNameForCentralized(String name,String billingGroup);

    /**
     * 
     * 通过 标识编码查询
     * @author 026113-foss-linwensheng
     * @date 2012-11-28 上午10:30:33
     */
	SaleDepartmentEntity querySaleDepartmentByCode(String code);
	
	/**
	 * 根据营业部信息 查询营业部信息集合
	 * @author 097972-foss-dengtingting
	 * @date 2013-3-27 下午3:06:46
	 */
	List<BranchQueryVo> queryListByDepartment(SaleDepartmentEntity entity);
	
	/**
	 * 
	 * 内部带货查询部门
	 * @author 025000-FOSS-helong
	 * @date 2013-4-16 下午08:41:41
	 * @param name
	 * @return
	 */
	List<SaleDepartmentEntity> querySaleDepartmentInner(String name);

	/**
	 * 根据历史时间和营业部编码查询营业部信息（查询历史营业部信息）
	 * 
	 * 若时间为空，则只根据营业部编码查询营业部信息
	 * 否则将根据时间，查询在creatTime和modifyTime时间段的营业部
	 * 不根据Active='Y'来查询
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-17 下午6:02:26
	 */
	SaleDepartmentEntity querySaleDepartmentByCode(String code, Date billTime);
	
	/**
	 * 校验运输性质和提货网点是否匹配
	 * @author WangQianJin
	 * @date 2013-7-19 上午10:58:12
	 */
	String checkProductAndTarget(QueryPickupPointDto dto);		
	
	/**
 	 * 
 	 * 根据部门Code和所属集中开单组查询
 	 * 
 	 * @author WangQianJin
 	 * @date 2013-08-02
 	 */
 	List<SalesBillingGroupEntity> querySalesBillGroupByCodeAndBillCode(String code,String billingGroup);

	/**
	 * 根据名称查询营业部，若isArrived为true则只查询可做到达的营业部
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-3 下午3:53:32
	 */
	List<SaleDepartmentEntity> querySaleDeptByNameOnline(String name, boolean isArrived);
	

	SalesDepartmentCityDto querySalesDepartmentCityInfo(SalesDepartmentCityDto dto);
	
	List<SaleDepartmentEntity> querySaleDepartmentByNameForCentralizedExp(String name,String billingGroup,String waybillNo);
	
}