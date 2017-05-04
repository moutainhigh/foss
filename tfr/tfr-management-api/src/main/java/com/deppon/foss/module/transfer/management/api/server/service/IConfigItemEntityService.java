/**   
 * File Name：IConfigItemEntityService.java   
 *   
 * Version:1.0
 * ：2013-4-2   
 * Copyright (c) 2013  Deppon All Rights Reserved. 2013    
 * @author: liangfuxiang liangfux@cn.ibm.com
 */

package com.deppon.foss.module.transfer.management.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.management.api.shared.domain.ConfigItemEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.ConfigTypeEntity;

/**
 * Class Description：配置相关服务类
 * 
 * @author: liangfuxiang liangfux@cn.ibm.com
 * @version: 2013-4-2 上午9:38:47
 */

public interface IConfigItemEntityService extends IService {
	/**
	 * 
	 * @Title: queryDistinctConfigTypes
	 * @Description: 查询配置项有效的类型信息
	 * @return 设定文件
	 * @return List<ConfigItemEntity> 返回类型
	 * @see queryDistinctConfigTypes
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-2 上午9:39:27
	 * @throws
	 */
	List<ConfigItemEntity> queryDistinctConfigTypes();

	/**
	 * 
	 * @Title: queryConfigItemEntitsByConfigType
	 * @Description: 根据配置项类型，查询有效的配置项信息
	 * @param configItemEntity
	 * @return 设定文件
	 * @return List<ConfigItemEntity> 返回类型
	 * @see queryConfigItemEntitsByConfigType
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-2 上午10:03:04
	 * @throws
	 */
	List<ConfigItemEntity> queryConfigItemEntitsByConfigType(ConfigItemEntity configItemEntity);

	/**
	 * @Title: addConfigTypeEntity
	 * @Description: 新增配置项类型信息
	 * @param configTypeEntity 设定文件
	 * @return void 返回类型
	 * @see addConfigTypeEntity
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-16 下午3:51:51
	 * @throws
	 */
	void addConfigTypeEntity(ConfigTypeEntity configTypeEntity);

	/**
	 * @Title: abolishConfigTypeEntity
	 * @Description: 作废配置项类型信息(此时，对应的配置项类型下的所有配置项都将作废，而且部门对应配置参数中的所有 该配置项类型的配置参数将作废)
	 * @param configTypeEntityList 设定文件
	 * @return void 返回类型
	 * @see abolishConfigTypeEntity
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-16 下午3:57:39
	 * @throws
	 */
	void abolishConfigTypeEntity(List<ConfigTypeEntity> configTypeEntityList);

	/**
	 * @Title: modifyConfigTypeEtity
	 * @Description: 修改配置项类型信息
	 * @param configTypeEntity 设定文件
	 * @return void 返回类型
	 * @see modifyConfigTypeEtity
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-16 下午4:27:52
	 * @throws
	 */
	void modifyConfigTypeEntity(ConfigTypeEntity configTypeEntity);

	/**
	 * @param limit
	 * @param start
	 * @Title: queryConfigTypeEntityList
	 * @Description: 查询配置项类型信息
	 * @param configTypeEntity
	 * @return 设定文件
	 * @return List<ConfigTypeEntity> 返回类型
	 * @see queryConfigTypeEntityList
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-16 下午4:45:51
	 * @throws
	 */
	List<ConfigTypeEntity> queryConfigTypeEntityList(ConfigTypeEntity configTypeEntity, int start, int limit);

	/**
	 * @Title: addConfigItemEntity
	 * @Description: 增加配置项信息
	 * @param configItemEntity 设定文件
	 * @return void 返回类型
	 * @see addConfigItemEntity
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-16 下午4:50:12
	 * @throws
	 */
	void addConfigItemEntity(ConfigItemEntity configItemEntity);

	/**
	 * @Title: abolishConfigItemEntity
	 * @Description: 作废配置项信息
	 * @param configItemEntityList 设定文件
	 * @return void 返回类型
	 * @see abolishConfigItemEntity
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-16 下午5:23:22
	 * @throws
	 */
	void abolishConfigItemEntity(List<ConfigItemEntity> configItemEntityList);

	/**
	 * @Title: modifyConfigItemEntity
	 * @Description: 修改配置项信息
	 * @param configItemEntity 设定文件
	 * @return void 返回类型
	 * @see modifyConfigItemEntity
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-16 下午5:38:04
	 * @throws
	 */
	void modifyConfigItemEntity(ConfigItemEntity configItemEntity);

	/**
	 * @param limit
	 * @param start
	 * @Title: queryConfigItemEntityList
	 * @Description: 查询配置项信息
	 * @param configItemEntity
	 * @return 设定文件
	 * @return List<ConfigItemEntity> 返回类型
	 * @see queryConfigItemEntityList
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-16 下午5:55:15
	 * @throws
	 */
	List<ConfigItemEntity> queryConfigItemEntityList(ConfigItemEntity configItemEntity, int start, int limit);

	/**
	 * @Title: queryConfigTypeEntityListCount
	 * @Description: 查询配置项类型信息数量
	 * @param configTypeEntity
	 * @return 设定文件
	 * @return Object 返回类型
	 * @see queryConfigTypeEntityListCount
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-16 下午6:57:01
	 * @throws
	 */
	Long queryConfigTypeEntityListCount(ConfigTypeEntity configTypeEntity);

	/**
	 * @Title: queryConfigItemEntityListCount
	 * @Description: 查询配置项信息数量
	 * @param configItemEntity
	 * @return 设定文件
	 * @return Long 返回类型
	 * @see queryConfigItemEntityListCount
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-16 下午6:58:45
	 * @throws
	 */
	Long queryConfigItemEntityListCount(ConfigItemEntity configItemEntity);

	/** 
	* @Title: queryConfigTypeEntity 
	* @Description: 查询配置项类型信息
	* @param configTypeEntity
	* @return  设定文件 
	* @return ConfigTypeEntity    返回类型 
	* @see queryConfigTypeEntity
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-4-17 下午3:32:26   
	* @throws 
	*/ 
	ConfigTypeEntity queryConfigTypeEntity(ConfigTypeEntity configTypeEntity);

	/** 
	* @Title: queryConfigItemEntity 
	* @Description: 查询配置项信息
	* @param configItemEntity
	* @return  设定文件 
	* @return ConfigItemEntity    返回类型 
	* @see queryConfigItemEntity
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-4-17 下午3:32:53   
	* @throws 
	*/ 
	ConfigItemEntity queryConfigItemEntity(ConfigItemEntity configItemEntity);

	/** 
	* @Title: queryAllConfigTypes 
	* @Description: 查询所有有效的配置项类型信息
	* @param configTypeEntity
	* @return  设定文件 
	* @return List<ConfigTypeEntity>    返回类型 
	* @see queryAllConfigTypes
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-4-17 下午7:40:08   
	* @throws 
	*/ 
	List<ConfigTypeEntity> queryAllConfigTypes() throws TfrBusinessException;

}
