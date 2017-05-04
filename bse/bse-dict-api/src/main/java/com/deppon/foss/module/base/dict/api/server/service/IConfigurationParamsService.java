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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/dict/api/server/service/IConfigurationParamsService.java
 * 
 * FILE NAME        	: IConfigurationParamsService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.dict.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.api.shared.exception.ConfigurationParamsException;
/**
 * 系统配置参数 Service接口
 * @author 087584-foss-lijun
 * @date 2012-10-19 下午4:19:49
 */
public interface IConfigurationParamsService extends IService{
    /**
     * 插入
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 下午4:19:49
     */
    ConfigurationParamsEntity addConfigurationParams(
	    ConfigurationParamsEntity entity)
	    throws ConfigurationParamsException;
	
    /**
     * 根据CODE删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 下午4:19:49
     */
    ConfigurationParamsEntity deleteConfigurationParams(ConfigurationParamsEntity entity);
	
    /**
     * 根据CODE批量删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 下午4:19:49
     */
    ConfigurationParamsEntity deleteConfigurationParamsMore(String[] codes , String deleteUser);
	
    /**
     * 更新 
     * @author 087584-foss-lijun
     * @date 2012-10-19 下午4:19:49
     */
    ConfigurationParamsEntity updateConfigurationParams(ConfigurationParamsEntity entity);
	
	
	
    /**
     * 以下全为查询 
     */
	
    /**
     * 根据编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 下午4:19:49
     */
    ConfigurationParamsEntity queryConfigurationParamsByVirtualCode(String code);	
	
	
    /**
     * 精确查询
     * 根据多个编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 下午4:19:49
     * @see com.deppon.foss.module.base.dict.api.server.dao.IConfigurationParamsDao#queryConfigurationParamsByCode(java.lang.String)
     */
    List<ConfigurationParamsEntity> queryConfigurationParamsBatchByVirtualCode(String[] codes);
    /**
     * 
     *<p>精确查询  根据多个编码Code查询</P>
     * @author 130566-foss-ZengJunfan
     * @date 2013-5-27下午6:00:03
     *@param codes
     *@return
     */
    List<ConfigurationParamsEntity> queryConfigurationParamsBatchByCode(String[] codes);
    
    /**
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 087584-foss-lijun
     * @date 2012-10-19 下午4:19:49
     */
    List<ConfigurationParamsEntity> queryConfigurationParamsExactByEntity(
	    ConfigurationParamsEntity entity, int start, int limit) ;
    
    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 下午4:19:49
     */
    long queryConfigurationParamsExactByEntityCount(ConfigurationParamsEntity entity);
	
    /**
     * 根据entity模糊查询，
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 下午4:19:49
     */
    List<ConfigurationParamsEntity> queryConfigurationParamsByEntity(
	    ConfigurationParamsEntity entity, int start, int limit);
	
    /**
     * 查询queryConfigurationParamsByEntity返回的记录总数,用于分页
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 下午4:19:49
     */
    long queryConfigurationParamsByEntityCount(ConfigurationParamsEntity entity);
		
    
    
    /**
     * 下面是特殊方法
     */

    /**
     * 批量新增
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-30 下午2:33:35
     */
    void addConfigurationParamsMore(List<ConfigurationParamsEntity> entitys);

    
    /**
     * 精确查询
     * 
     * 通过 参数所属模块parmMouudle ，参数编码 parmCode，和组织机构orgCode ，查询组织参数。
     * 
     * parmMouudle（所属模块） 传入参数请参考  DictionaryConstants 中 常量：SYSTEM_CONFIG_PARM__BAS SYSTEM_CONFIG_PARM__PKP SYSTEM_CONFIG_PARM__TFR SYSTEM_CONFIG_PARM__STL
     * parmCode（parmCode） ，传入参数详情参考 ConfigurationParamsConstants 中的定义，如果没有，自己补全  
     * orgCode 组织机构
     * @author 087584-foss-lijun
     * @date 2012-10-24 下午7:09:26
     */
    ConfigurationParamsEntity queryConfigurationParamsByOrgCode(String parmMouudle,String parmCode,String orgCode);
    
    /**
     * 精确查询(不走缓存)
     * 
     * 通过 参数所属模块parmMouudle ，参数编码 parmCode，和组织机构orgCode ，查询组织参数。
     * TODO（方法详细描述说明、方法参数的具体涵义）
     *  @author 130566-foss-ZengJunFan
     * @date 2014-4-24 下午3:54:53
     */
    ConfigurationParamsEntity queryConfigurationParamsByOrgCodeNoCache(String parmMouudle,String parmCode,String orgCode);
    /**
     * 精确查询
     * 
     * 根据“系统配置编码”查询第一个系统参数配置，适用于无部门编码的系统配置
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-24 上午11:11:15
     * @param code
     *            系统配置的编码，对应表中的CODE
     */
    ConfigurationParamsEntity queryConfigurationParamsOneByCode(String code);
    	
	
    /**
     * 精确查询
     * 
     * 根据“系统配置编码”查询第一个系统参数配置的“配置项值”，适用于无部门编码的系统配置
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-24 上午11:11:15
     * @param code
     *            系统配置的编码，对应表中的CODE
     */
    String queryConfValueByCode(String code); 
    
    /**
     * 下面是工具方法
     */
    
    
    /**
     * 将部门名称添加进去，
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-22 下午7:43:53
     */
    ConfigurationParamsEntity attachOrg(ConfigurationParamsEntity entity);
    
    /**
     * 将部门名称添加进去，
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-22 下午7:43:53
     */
    List<ConfigurationParamsEntity> attachOrg(List<ConfigurationParamsEntity> entitys);
    
    /**
     * 
     * @Description: 刷新缓存
     * @author FOSSDP-sz
     * @date 2013-2-25 上午10:43:11
     * @param parmMouudle
     * @param parmCode
     * @param orgCode
     * @version V1.0
     */
    void refreshConfigurationParamsCache(String parmMouudle,String parmCode,String orgCode);
    
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
    
    String querySysConfig(String confType, String code, String orgCode);

	boolean queryBseSwitch4Ecs();

	boolean queryTfrSwitch4Ecs();

	boolean queryStlSwitch4Ecs();

	boolean queryPkpSwitch4Ecs(); 
	
	/**
	 * 快递派送地址库ESB开关
	 * @author 313353
	 * @date 2016年8月15日 下午14:56:42
	 */
	public boolean queryExpressDeliveryAddress();
	
	/**
	 * 使用solr文本搜索车牌号
	 * @Title: queryVehicleBSolr 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @return    设定文件 
	 * @return boolean    返回类型 
	 * @throws 
	 * @user 310854-liuzhenhua
	 */
	public boolean queryVehicleBSolr();
}
