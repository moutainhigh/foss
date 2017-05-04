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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/ISaleDepartmentService.java
 * 
 * FILE NAME        	: ISaleDepartmentService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PickAreaAndDeliveryAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
/**
 * 营业部 Service接口
 * 
 * @author 087584-foss-lijun
 * @date 2012-11-2 下午5:32:19
 */
public interface ISaleDepartmentService extends IService {
    /**
     * 插入
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午5:32:19
     */
    SaleDepartmentEntity addSaleDepartment(SaleDepartmentEntity entity);
	
    /**
     * 根据CODE删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午5:32:19
     */
    SaleDepartmentEntity deleteSaleDepartment(SaleDepartmentEntity entity);
	
    /**
     * 根据CODE批量删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午5:32:19
     */
    //SaleDepartmentEntity deleteSaleDepartmentMore(String[] codes , String deleteUser);
	
    /**
     * 更新 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午5:32:19
     */
    SaleDepartmentEntity updateSaleDepartment(SaleDepartmentEntity entity);
	
    /**
     * 
     *<p>用于同步WDGH的派送自提的更新方法</p>
     *@author 130566-zengJunfan
     *@date   2013-9-6下午2:04:33
     * @param entity
     * @return
     */
    int updateSaleDepartmentBySync(PickAreaAndDeliveryAreaEntity entity);
	
    /**
     * 以下全为查询 
     */
	
    /**
     * 根据编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午5:32:19
     */
    SaleDepartmentEntity querySaleDepartmentByCode(String code);	
    
    /**
     * 根据编码查询(不走缓存)
     * 
     * @author foss-zhangxiaohui
     * @date 2013-04-25 下午5:29:19
     */
    SaleDepartmentEntity querySaleDepartmentByCodeNoCache(String code);	
	

    /**
     * 
     * <p>根据营业部部门编码查询营业部实体</p> 
     * @author foss-zhujunyong
     * @date Feb 3, 2013 11:44:04 AM
     * @param code
     * @return
     * @see
     */
    SaleDepartmentEntity querySimpleSaleDepartmentByCode(String code);	
	
    /**
     * 精确查询
     * 根据多个编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午5:32:19
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISaleDepartmentDao#querySaleDepartmentByCode(java.lang.String)
     */
    List<SaleDepartmentEntity> querySaleDepartmentBatchByCode(String[] codes);
    
    /**
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午5:32:19
     */
    List<SaleDepartmentEntity> querySaleDepartmentExactByEntity(
	    SaleDepartmentEntity entity, int start, int limit) ;
    
    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午5:32:19
     */
    long querySaleDepartmentExactByEntityCount(SaleDepartmentEntity entity);
	
    /**
     * 根据entity模糊查询，
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午5:32:19
     */
    List<SaleDepartmentEntity> querySaleDepartmentByEntity(
	    SaleDepartmentEntity entity,int start, int limit);
	
    /**
     * 查询querySaleDepartmentByEntity返回的记录总数,用于分页
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午5:32:19
     */
    long querySaleDepartmentByEntityCount(SaleDepartmentEntity entity);
		
    
    /**
     * 
     * <p>根据外场的部门编码查询其驻地营业部的部门编码</p> 
     * 如果有多个驻地部门，随便找一个
     * 
     * @author foss-zhujunyong
     * @date Nov 7, 2012 2:24:13 PM
     * @param transferCenterCode
     * @return
     * @see
     */
    String queryStationSaleCodeByTransferCenterCode(String transferCenterCode);    

    /**
     * 
     * <p>根据外场的部门编码查询其可到达驻地营业部的部门编码</p> 
     * @author foss-zhujunyong
     * @date Nov 7, 2012 2:24:13 PM
     * @param transferCenterCode
     * @return
     * @see
     */
    String queryArriveStationSaleCodeByTransferCenterCode(String transferCenterCode);    

    /**
     * 
     * <p>根据外场的部门编码查询其可出发驻地营业部的部门编码</p> 
     * @author foss-zhujunyong
     * @date Nov 7, 2012 2:24:13 PM
     * @param transferCenterCode
     * @return
     * @see
     */
    String queryLeaveStationSaleCodeByTransferCenterCode(String transferCenterCode);    

    
    /**
     * 下面是工具方法
     */
    
    
    /**
     * 提供给结算
     * 
     * 根据部门编码设置 最大临欠额度
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午5:31:57
     * @param code 部门编码
     * @param maxTempArrears 最大临欠额度
     */
    boolean changeMaxTempArrears(String code,BigDecimal maxTempArrears);   
    
    /**
     * 提供给结算
     * 
     * 根据部门编码设置 已用临欠额度
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午5:31:57
     * @param code 部门编码
     * @param maxTempArrears 已用临欠额度
     */
    boolean changeUsedTempArrears(String code, BigDecimal usedTempArrears);

    /**
     * 
     * <p>查询营业部数据，不包括冗余属性</p> 
     * @author foss-zhujunyong
     * @date Mar 23, 2013 5:46:10 PM
     * @param entity
     * @param start
     * @param limit
     * @return
     * @see
     */
    List<SaleDepartmentEntity> querySimpleSaleDepartmentExactByEntity(SaleDepartmentEntity entity, int start, int limit);

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
	 * 根据营业部编码查询营业部信息，自提和派送区域范围不拼接扩展表的
	 * 
	 * auther:wangpeng_078816
	 * date:2014-4-19
	 *
	 */
    SaleDepartmentEntity querySaleDepartmentInfoByCode(String code);
    
    /**
     * 
     * <p>根据外场的部门编码查询其驻地营业部List</p> 
     * 
     * @author 187862-dujunhui
     * @date 2014-6-5 上午10:00:43
     * @param transferCenterCode
     * @return
     * @see
     */
	List<SaleDepartmentEntity> queryStationListByTransferCenterCode(String transferCenterCode);
	
	/**
	 * 提供营业部Code 查询时汽运到达的驻地外场
	 * @author 130566-ZengJunFan
	 * @param code
	 * @return
	 */
	String queryTransferCenterCodeByStationCode(String salesDepartmentCode);
	/**
	 * 通过code从缓存中查询营业部简单信息，若缓存中没有找到再走一次数据库查询
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author 268984 
	 * @date 2016-2-24 下午2:17:01
	 * @param code
	 * @return
	 * @see
	 */
	SaleDepartmentEntity querySimpleSaleDepartmentByCodeCache(String code);

	/**
	 * <p>查询营业部信息，不关联组织名称等只查询基础信息</p> 
	 * @author Foss-151211-yangtaohong 
	 * @date 2016-5-12 下午8:35:24
	 * @param code
	 * @return
	 * @see
	 */
	SaleDepartmentEntity querySaleDepartmentNoAttach(String code);
}
