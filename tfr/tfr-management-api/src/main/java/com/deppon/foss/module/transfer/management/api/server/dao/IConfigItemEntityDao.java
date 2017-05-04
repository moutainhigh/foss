/**   
 * File Name：IConfigItemEntity.java   
 *   
 * Version:1.0
 * ：2013-4-2   
 * Copyright (c) 2013  Deppon All Rights Reserved. 2013    
 * @author: liangfuxiang liangfux@cn.ibm.com
 */

package com.deppon.foss.module.transfer.management.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.management.api.shared.domain.ConfigItemEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.ConfigTypeEntity;

/**
 * Class Description：配置相关DAO
 * 
 * @author: liangfuxiang liangfux@cn.ibm.com
 * @version: 2013-4-2 上午9:36:29
 */

public interface IConfigItemEntityDao {

	/**
	 * @Title: queryDistinctConfigTypes
	 * @Description: 查询配置项有效的类型信息
	 * @return 设定文件
	 * @return List<ConfigItemEntity> 返回类型
	 * @see queryDistinctConfigTypes
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-2 上午9:37:43
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
	 * @version: 2013-4-2 上午10:03:54
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
	 * @version: 2013-4-16 下午3:54:19
	 * @throws
	 */
	void addConfigTypeEntity(ConfigTypeEntity configTypeEntity);

	/**
	 * @Title: abolishConfigTypeEntity
	 * @Description: 作废配置类型
	 * @param newConfigTypeEntityList 设定文件
	 * @return void 返回类型
	 * @see abolishConfigTypeEntity
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-16 下午4:07:52
	 * @throws
	 */
	void abolishConfigTypeEntity(List<ConfigTypeEntity> newConfigTypeEntityList);

	/**
	 * @Title: queryConfigTypeEntityCount
	 * @Description: 查询配置类型数量
	 * @param configTypeEntity
	 * @return 设定文件
	 * @return Long 返回类型
	 * @see queryConfigTypeEntityCount
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-16 下午4:22:48
	 * @throws
	 */
	Long queryConfigTypeEntityCount(ConfigTypeEntity configTypeEntity);

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
	 * @version: 2013-4-16 下午4:47:57
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
	 * @version: 2013-4-16 下午4:54:15
	 * @throws
	 */
	void addConfigItemEntity(ConfigItemEntity configItemEntity);

	/**
	 * @Title: queryConfigItemEntityCount
	 * @Description: 判断新增的配置项是否已经存在
	 * @param configItemEntity
	 * @return 设定文件
	 * @return Long 返回类型
	 * @see queryConfigItemEntityCount
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-16 下午5:02:23
	 * @throws
	 */
	Long queryConfigItemEntityCount(ConfigItemEntity configItemEntity);

	/**
	 * @Title: abolishConfigItemEntity
	 * @Description: 作废配置项信息
	 * @param newConfigItemEntityList 设定文件
	 * @return void 返回类型
	 * @see abolishConfigItemEntity
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-16 下午5:26:10
	 * @throws
	 */
	void abolishConfigItemEntity(List<ConfigItemEntity> newConfigItemEntityList);

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
	 * @version: 2013-4-16 下午5:57:13
	 * @throws
	 */
	List<ConfigItemEntity> queryConfigItemEntityList(ConfigItemEntity configItemEntity, int start, int limit);

	/**
	 * @Title: queryConfigTypeEntityListCount
	 * @Description:查询配置项类型信息数量
	 * @param configTypeEntity
	 * @return 设定文件
	 * @return Object 返回类型
	 * @see queryConfigTypeEntityListCount
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-16 下午7:00:27
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
	 * @version: 2013-4-16 下午7:01:17
	 * @throws
	 */
	Long queryConfigItemEntityListCount(ConfigItemEntity configItemEntity);

	/**
	 * @Title: queryConfigTypeEntity
	 * @Description: 查询配置项类型信息
	 * @param configTypeEntity
	 * @return 设定文件
	 * @return ConfigTypeEntity 返回类型
	 * @see queryConfigTypeEntity
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-17 下午3:42:29
	 * @throws
	 */
	ConfigTypeEntity queryConfigTypeEntity(ConfigTypeEntity configTypeEntity);

	/**
	 * @Title: queryConfigItemEntity
	 * @Description: 查询配置项信息
	 * @param configItemEntity
	 * @return 设定文件
	 * @return ConfigItemEntity 返回类型
	 * @see queryConfigItemEntity
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-17 下午3:42:46
	 * @throws
	 */
	ConfigItemEntity queryConfigItemEntity(ConfigItemEntity configItemEntity);

	/**
	 * @Title: queryAllConfigTypes
	 * @Description: 查询所有有效的配置项类型信息
	 * @return 设定文件
	 * @return List<ConfigTypeEntity> 返回类型
	 * @see queryAllConfigTypes
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-17 下午7:47:20
	 * @throws
	 */
	List<ConfigTypeEntity> queryAllConfigTypes();

	/**
	 * @Title: abolishConfigItemEntityList
	 * @Description: 作废配置项信息
	 * @param configItemEntityList 设定文件
	 * @return void 返回类型
	 * @see abolishConfigItemEntityList
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-18 上午11:14:16
	 * @throws
	 */
	void abolishConfigItemEntityList(List<ConfigItemEntity> configItemEntityList);

	/** 
	* @Title: modifyConfigItemTypeRefItem 
	* @Description: 配置项信息需要做相应修改。
	* @param configTypeEntity  设定文件 
	* @return void    返回类型 
	* @see modifyConfigItemTypeRefItem
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-4-18 下午12:54:27   
	* @throws 
	*/ 
	void modifyConfigItemTypeRefItem(ConfigTypeEntity configTypeEntity);

	/** 
	* @Title: modifyConfigItemTypeRefOrg 
	* @Description: 组织配置项需要做相应修改。
	* @param configTypeEntity  设定文件 
	* @return void    返回类型 
	* @see modifyConfigItemTypeRefOrg
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-4-18 下午12:54:45   
	* @throws 
	*/ 
	void modifyConfigItemTypeRefOrg(ConfigTypeEntity configTypeEntity);

	/** 
	* @Title: modifyConfigItemEntityRefOrg 
	* @Description: modifyConfigItemEntityRefOrg
	* @param configItemEntity  设定文件 
	* @return void    返回类型 
	* @see modifyConfigItemEntityRefOrg
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-4-18 下午2:30:40   
	* @throws 
	*/ 
	void modifyConfigItemEntityRefOrg(ConfigItemEntity configItemEntity);

	/** 
	* @Title: abolishConfigItemEntityRefOrg 
	* @Description: 作废配置项相关组织。
	* @param newConfigItemEntityList  设定文件 
	* @return void    返回类型 
	* @see abolishConfigItemEntityRefOrg
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-4-18 下午2:42:29   
	* @throws 
	*/ 
	void abolishConfigItemEntityRefOrg(List<ConfigItemEntity> newConfigItemEntityList);

}
