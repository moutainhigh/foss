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
 * PROJECT NAME	: bse-dict-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/dict/api/server/dao/IConfigurationParamsDao.java
 * 
 * FILE NAME        	: IConfigurationParamsDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.dict.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
/**
 *  DAO接口
 * @author 087584-foss-lijun
 * @date 2012-10-19 下午3:28:14
 */
public interface IConfigurationParamsDao {
    /**
     * 插入
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-30 上午12:34:48
     */
    ConfigurationParamsEntity addConfigurationParams(ConfigurationParamsEntity entity);

    /**
     * 根据VIRTUAL_CODE删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-30 上午12:34:48
     */
    ConfigurationParamsEntity deleteConfigurationParams(ConfigurationParamsEntity entity);

    /**
     * 根据VIRTUAL_CODE批量删除 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-30 上午12:34:48
     */
    ConfigurationParamsEntity deleteConfigurationParamsMore(String[] codes , String deleteUser);
	
    /**
     * 更新 
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-30 上午12:34:48
     */
    ConfigurationParamsEntity updateConfigurationParams(ConfigurationParamsEntity entity);
	
	
	
    /**
     * 以下全为查询 
     */
	
    /**
     * 精确查询
     * 根据VIRTUAL_CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-30 上午12:34:48
     */
    ConfigurationParamsEntity queryConfigurationParamsByVirtualCode(String code);	
	
	
    /**
     * 精确查询
     * 根据多个VIRTUAL_CODE 查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-30 上午12:34:48
     * @see com.deppon.foss.module.base.dict.api.server.dao.IConfigurationParamsDao#queryConfigurationParamsByCode(java.lang.String)
     */
    List<ConfigurationParamsEntity> queryConfigurationParamsBatchByVirtualCode(String[] codes);
    
   /** 
    *<p>精确查询  根据多个CODE 进行批量查询</P>
    * @author 130566-foss-ZengJunfan
    * @date 2013-5-27下午6:11:01
    *@param codes
    *@return
    */
    List<ConfigurationParamsEntity> queryConfigurationParamsBatchByCode(String[] codes);
    
    /**
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 087584-foss-lijun
     * @date 2012-11-30 上午12:34:48
     */
    List<ConfigurationParamsEntity> queryConfigurationParamsExactByEntity(
	    ConfigurationParamsEntity entity, int start, int limit) ;
    
    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-30 上午12:34:48
     */
    long queryConfigurationParamsExactByEntityCount(ConfigurationParamsEntity entity);
	
    /**
     * 根据entity模糊查询，
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-30 上午12:34:48
     */
    List<ConfigurationParamsEntity> queryConfigurationParamsByEntity(ConfigurationParamsEntity entity,
	    int start, int limit);
	
    /**
     * 查询queryConfigurationParamsByEntity返回的记录总数,用于分页
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-30 上午12:34:48
     */
    long queryConfigurationParamsByEntityCount(ConfigurationParamsEntity entity);
		
    
	
    /**
     * 根据entity精确查询,用于数据下载
     * entity里面根据表结构，要动态（可不传入）传入MODIFY_TIME,员工编号，部门编号,
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-30 上午12:34:48
     */
    List<ConfigurationParamsEntity> queryConfigurationParamsForDownload(ConfigurationParamsEntity entity);
			

    
    
    /**
     * 下面是特殊查询
     */
    
    /**
     * 精确查询
     * 通过 ConfigurationParams的CODE和OrgAdministrativeInfo的CODE查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-24 下午7:09:26
     */
    ConfigurationParamsEntity queryConfigurationParamsByOrgCode(String code,String orgCode);	
    	
    /**
     * 递归查询当前机构及其以上级别(父级)的 参数配置 
	  * Description: 离线下载功能的开关配置 BUG-55198
	  * @author deppon-157229-zxy
	  * @version 1.0 2013-10-11 上午8:10:37
	  * @param params
	  * @return
	 */
    public List<ConfigurationParamsEntity> queryConfigurationParamsByOrgCode(ConfigurationParamsEntity params);
    /**
     * 提供给接送货根据code和时间来查询
     * @param Code
     * @param date
     * @return
     */
    ConfigurationParamsEntity queryConfigurationByCodeAndDate(String code,Date date);
    
    String querySysConfig(ConfigurationParamsEntity params);
}
