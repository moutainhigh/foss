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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/ISalesDepartmentDao.java
 * 
 * FILE NAME        	: ISalesDepartmentDao.java
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
package com.deppon.foss.module.pickup.waybill.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesBillingGroupEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesProductEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.QueryPickupPointDto;
import com.deppon.foss.module.pickup.waybill.shared.vo.BranchQueryVo;

/**
 * 
 * 营业部数据持久层接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-10-16 下午7:54:47, </p>
 * @author foss-sunrui
 * @date 2012-10-16 下午7:54:47
 * @since
 * @version
 */
public interface ISalesDepartmentDao {

    /**
     * 
     * <p>插入一条新纪录</p> 
     * @author foss-sunrui
     * @date 2012-10-29 上午11:48:55
     * @param record
     * @return
     * @see
     */
    int insert(SaleDepartmentEntity record);

    /**
     * 
     * <p>按需插入一条新纪录</p> 
     * @author foss-sunrui
     * @date 2012-10-29 上午11:48:55
     * @param record
     * @return
     * @see
     */
    boolean insertSelective(SaleDepartmentEntity record);

    /**
     * 
     * <p>通过主键查询</p> 
     * @author foss-sunrui
     * @date 2012-10-29 上午11:49:27
     * @param id
     * @return
     * @see
     */
    SaleDepartmentEntity queryByPrimaryKey(String id);
    
    /**
     * 
     * <p>通过营业部编码查询</p> 
     * @author foss-sunrui
     * @date 2012-10-29 上午11:49:27
     * @param id
     * @return
     * @see
     */
    SaleDepartmentEntity queryByCode(String code);

    /**
     * 
     * <p>通过主键更新记录</p> 
     * @author foss-sunrui
     * @date 2012-10-29 上午11:49:42
     * @param record
     * @return
     * @see
     */
    int updateByPrimaryKeySelective(SaleDepartmentEntity record);

    /**
     * 
     * <p>通过主键按需更新记录</p> 
     * @author foss-sunrui
     * @date 2012-10-29 上午11:49:45
     * @param record
     * @return
     * @see
     */
    int updateByPrimaryKey(SaleDepartmentEntity record);
    
    /**
     * <p>根据营业部名字 等信息查询提货网点</p> 
     * @author foss-jiangfei
     * @date 2012-10-31 下午5:00:05
     * @param name
     * @return
     * @see
     */
    List<SaleDepartmentEntity> queryByDepartmentInfo (QueryPickupPointDto dto);
    /**
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author foss-jiangfei
     * @date 2012-11-2 下午5:32:19
     */
    List<SaleDepartmentEntity> querySaleDepartmentExactByEntity(
	    SaleDepartmentEntity entity, int start, int limit) ;
    

    /**
     * 
     * <p>根据外场的部门编码查询其驻地营业部的部门编码</p> 
     * @author foss-jiangfei
     * @date Nov 21, 2012 2:24:13 PM
     * @param transferCenterCode
     * @return
     * @see
     */
    String queryStationSaleCodeByTransferCenterCode(String transferCenterCode);    
    
    /**
     * 
     * 通过 标识编码查询
     * @author 026113-foss-linwensheng
     * @date 2012-11-28 上午10:30:33
     */
    SaleDepartmentEntity querySaleDepartmentByCode(String code);
    
    /**
     * 
     * 根据部门名称查询
     * @author 025000-FOSS-helong
     * @date 2012-12-17 上午11:05:01
     */
    List<SaleDepartmentEntity> querySaleDepartmentByName(String name);

//    /**
//     * 
//     * 根据部门名称和所属集中开单组查询
//     * @author 025000-FOSS-helong
//     * @date 2012-12-17 上午11:05:01
//     */
//    List<SaleDepartmentEntity> querySaleDepartmentByNameForCentralized(String name,String billingGroup);

	/**
	 * @param saleDepartment
	 */
	void delete(SaleDepartmentEntity saleDepartment);
	
	/**
	 * 根据营业部信息 查询营业部信息集合
	 * @author 097972-foss-dengtingting
	 * @date 2013-3-27 上午11:02:37
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
     * 根据条件查询营业部信息
     * @author WangQianJin
     * @date 2013-7-19 上午11:15:54
     */
    SaleDepartmentEntity queryDepartmentInfoByDto(QueryPickupPointDto dto2);
    
    /**
 	 * 
 	 * 根据部门Code和所属集中开单组查询
 	 * 
 	 * @author WangQianJin
 	 * @date 2013-08-02
 	 */
 	List<SalesBillingGroupEntity> querySalesBillGroupByCodeAndBillCode(String code,String billingGroup);
 	
 	
 	 /**
     * <p>根据营业部名字 等信息查询提货网点</p> 
     * @author foss-jiangfei
     * @date 2012-10-31 下午5:00:05
     * @param name
     * @return
     * @see
     */
    List<SaleDepartmentEntity> queryByDepartmentInfoVirtual (QueryPickupPointDto dto);
    
    /**
     * 根据部门编码与运输性质获取信息数量
     * @param entity
     * @author WangQianJin
     * @return
     */
    int queryCountByCodeAndProduct(SalesProductEntity entity);

}