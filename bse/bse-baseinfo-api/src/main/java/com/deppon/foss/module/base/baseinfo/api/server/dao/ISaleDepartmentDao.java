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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/ISaleDepartmentDao.java
 * 
 * FILE NAME        	: ISaleDepartmentDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.Date;
import java.util.List;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SaleDepartmentInfoDto;
/**
 * 营业部 DAO接口
 * @author 087584-foss-lijun
 * @date 2012-11-2 下午5:31:31
 */
public interface ISaleDepartmentDao {
    /**
     * 插入
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午5:31:31
     */
    SaleDepartmentEntity addSaleDepartment(SaleDepartmentEntity entity);

    /**
     * 根据CODE删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午5:31:31
     */
    SaleDepartmentEntity deleteSaleDepartment(SaleDepartmentEntity entity);

    /**
     * 根据CODE批量删除 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午5:31:31
     */
    SaleDepartmentEntity deleteSaleDepartmentMore(String[] codes , String deleteUser);
	
    /**
     * 更新 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午5:31:31
     */
    SaleDepartmentEntity updateSaleDepartment(SaleDepartmentEntity entity);
	
	
	
    /**
     * 以下全为查询 
     */
	
    /**
     * 精确查询
     * 根据CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午5:31:31
     */
    SaleDepartmentEntity querySaleDepartmentByCode(String code);	
	

    /**
     * 通过 标识编码CODE,是否有效ACTIVE精确查询
     * 
     * 两个参数都可传空，当传空时，不做为查询条件，查询时，取更新时间最近的一条
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:39:55
     * @param code 标识编码（组织编码）
     * @param active FossConstants.YES:FossConstants.NO
     */
    List<SaleDepartmentEntity> querySaleDepartmentByCodeActive(List<String> codes,
	    String active); 
    
    /**
     * 精确查询
     * 根据多个CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午5:31:31
     * @see com.deppon.foss.module.base.dict.api.server.dao.ISaleDepartmentDao#querySaleDepartmentByCode(java.lang.String)
     */
    List<SaleDepartmentEntity> querySaleDepartmentBatchByCode(String[] codes);
    
    /**
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午5:31:31
     */
    List<SaleDepartmentEntity> querySaleDepartmentExactByEntity(
	    SaleDepartmentEntity entity, int start, int limit) ;
    
    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午5:31:31
     */
    long querySaleDepartmentExactByEntityCount(SaleDepartmentEntity entity);
	
    /**
     * 根据entity模糊查询，
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午5:31:31
     */
    List<SaleDepartmentEntity> querySaleDepartmentByEntity(SaleDepartmentEntity entity,
	    int start, int limit);
	
    /**
     * 查询querySaleDepartmentByEntity返回的记录总数,用于分页
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午5:31:31
     */
    long querySaleDepartmentByEntityCount(SaleDepartmentEntity entity);
		
	
    /**
     * 根据entity精确查询,用于数据下载
     * entity里面根据表结构，要动态（可不传入）传入MODIFY_TIME,员工编号，部门编号,
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-7 下午7:55:20
     */
    List<SaleDepartmentEntity> querySaleDepartmentForDownload(SaleDepartmentEntity entity);
    /**
     * 
     * @Description: 根据营业部编号批量查询营业部信息
     * Company:IBM
     * @author FOSSDP-sz
     * @date 2013-1-22 下午7:47:06
     * @param codes
     * @return
     * @version V1.0
     */
    List<SaleDepartmentInfoDto> querySaleDepartmentInfoByCodes(List<String> codes);

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
	 * 分页下载营业部信息
	 * @param entity
	 * @param i
	 * @param thousand
	 * @return
	 */
	List<SaleDepartmentEntity> querySaleDepartmentForDownloadByPage(
			SaleDepartmentEntity entity, int start, int lmited);
	
	/**
	 * 根据CODE查询部门快递员数
	 * @author 187862-dujunhui
	 * @param saleDepartmentCode
	 * @return
	 */
	long queryExpressManNumBySaleDepartmentCode(
			String saleDepartmentCode);
}
