/**   
 * File Name：IConfigOrgRelationDao.java   
 *   
 * Version:1.0
 * ：2013-3-28   
 * Copyright (c) 2013  Deppon All Rights Reserved. 2013    
 * @author: liangfuxiang liangfux@cn.ibm.com
 */

package com.deppon.foss.module.transfer.management.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.management.api.shared.domain.ConfigOrgRelationEntity;

/**
 * Class Description： 配置项与组织对应关系DAO接口
 * 
 * @author: liangfuxiang liangfux@cn.ibm.com
 * @version: 2013-3-28 下午6:03:35
 */

public interface IConfigOrgRelationDao {

	/**
	 * @Title: queryConfigOrgRelationEntityList
	 * @Description: 查询组织配置信息列表
	 * @param configOrgRelationEntity
	 * @param start
	 * @param limit
	 * @return 设定文件
	 * @return List<ConfigOrgRelationEntity> 返回类型
	 * @see queryConfigOrgRelationEntityList
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-3-29 下午2:21:18
	 * @throws
	 */
	List<ConfigOrgRelationEntity> queryConfigOrgRelationEntityList(ConfigOrgRelationEntity configOrgRelationEntity, int start, int limit);

	/**
	 * @Title: queryConfigOrgRelationEntityTotalCount
	 * @Description: 查询组织配置信息数量
	 * @param configOrgRelationEntity
	 * @return 设定文件
	 * @return Long 返回类型
	 * @see queryConfigOrgRelationEntityTotalCount
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-3-29 下午2:22:36
	 * @throws
	 */
	Long queryConfigOrgRelationEntityTotalCount(ConfigOrgRelationEntity configOrgRelationEntity);

	/**
	 * @Title: queryConfigOrgRelationEntityList
	 * @Description: 查询当前组织及其下级组织的配置项信息
	 * @param conditionMap
	 * @param start
	 * @param limit
	 * @return 设定文件
	 * @return List<ConfigOrgRelationEntity> 返回类型
	 * @see queryConfigOrgRelationEntityList
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-3-29 下午4:42:45
	 * @throws
	 */
	List<ConfigOrgRelationEntity> queryConfigOrgRelationEntityList(Map<String, Object> conditionMap, int start, int limit);

	/**
	 * @Title: queryConfigOrgRelationEntityTotalCount
	 * @Description: 查询当前组织及其下级组织的配置项信息数量
	 * @param conditionMap
	 * @return 设定文件
	 * @return Long 返回类型
	 * @see queryConfigOrgRelationEntityTotalCount
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-3-29 下午4:54:24
	 * @throws
	 */
	Long queryConfigOrgRelationEntityTotalCount(Map<String, Object> conditionMap);

	/**
	 * 
	 * @Title: saveConfigOrgRelationEntityList
	 * @Description: 保存组织配置项信息列表
	 * @param configOrgRelationEntityList 设定文件
	 * @return void 返回类型
	 * @see saveConfigOrgRelationEntityList
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-1 下午7:49:04
	 * @throws
	 */
	void saveConfigOrgRelationEntityList(List<ConfigOrgRelationEntity> configOrgRelationEntityList);

	/**
	 * @Title: updateConfigOrgRelationEntityList
	 * @Description: 更新组织配置项信息列表
	 * @param configOrgRelationEntityList 设定文件
	 * @return void 返回类型
	 * @see updateConfigOrgRelationEntityList
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-1 下午7:50:04
	 * @throws
	 */
	void updateConfigOrgRelationEntityList(List<ConfigOrgRelationEntity> configOrgRelationEntityList);

	/**
	 * @Title: updateConfigOrgRelationEntity
	 * @Description: 更新组织配置项信息
	 * @param configOrgRelationEntity 设定文件
	 * @return void 返回类型
	 * @see updateConfigOrgRelationEntity
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-1 下午7:51:21
	 * @throws
	 */
	void updateConfigOrgRelationEntity(ConfigOrgRelationEntity configOrgRelationEntity);

	/**
	 * @Title: abolishConfigOrgRelation
	 * @Description: 作废配置参数
	 * @param abolishMap 设定文件
	 * @return void 返回类型
	 * @see abolishConfigOrgRelation
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-3 下午2:34:34
	 * @throws
	 */
	void abolishConfigOrgRelation(Map<String, Object> abolishMap);

	/**
	 * @Title: modifyConfigOrgRealtion
	 * @Description: 修改配置参数信息
	 * @param configOrgRelationEntity 设定文件
	 * @return void 返回类型
	 * @see modifyConfigOrgRealtion
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-3 下午3:25:44
	 * @throws
	 */
	void updateConfigOrgRealtion(ConfigOrgRelationEntity configOrgRelationEntity);

	/**
	 * @Title: addConfigOrgRelationList
	 * @Description: 匹练增加配置项信息
	 * @param newConfigOrgRelationEntityList 设定文件
	 * @return void 返回类型
	 * @see addConfigOrgRelationList
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-7 下午8:15:57
	 * @throws
	 */
	void addConfigOrgRelationList(List<ConfigOrgRelationEntity> newConfigOrgRelationEntityList);

	/**
	 * @Title: queryCountByConfigOrgRelationEntity
	 * @Description: 查询配置参数信息是否存在
	 * @param configOrgRelationEntity
	 * @return 设定文件
	 * @return int 返回类型
	 * @see queryCountByConfigOrgRelationEntity
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-8 上午9:53:14
	 * @throws
	 */
	Long queryCountByConfigOrgRelationEntity(ConfigOrgRelationEntity configOrgRelationEntity);

	/**
	 * @Title: queryConfigOrgRelationEntity
	 * @Description: 查询配置参数信息
	 * @param configOrgRelationEntity
	 * @return 设定文件
	 * @return ConfigOrgRelationEntity 返回类型
	 * @see queryConfigOrgRelationEntity
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-11 上午10:40:54
	 * @throws
	 */
	ConfigOrgRelationEntity queryConfigOrgRelationEntity(ConfigOrgRelationEntity configOrgRelationEntity);

	/**
	 * @Title: queryConfigOrgRelationEntityListNoPage
	 * @Description: 查询配置参数信息
	 * @param conditionMap
	 * @return 设定文件
	 * @return List<ConfigOrgRelationEntity> 返回类型
	 * @see queryConfigOrgRelationEntityListNoPage
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-13 下午5:33:09
	 * @throws
	 */
	List<ConfigOrgRelationEntity> queryConfigOrgRelationEntityListNoPage(Map<String, Object> conditionMap);

	/**
	 * @Title: abolishConfigOrgRelationEntityList
	 * @Description: 作废配置参数
	 * @param configOrgRelationEntityList 设定文件
	 * @return void 返回类型
	 * @see abolishConfigOrgRelationEntityList
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-18 上午11:00:33
	 * @throws
	 */
	void abolishConfigOrgRelationEntityList(List<ConfigOrgRelationEntity> configOrgRelationEntityList);

	/** 
	* @Title: queryDipConfigOrgInfoByConfType 
	* @Description: 查询顶级组织(DIP)配置项信息
	* @param confType
	* @return  设定文件 
	* @return List<ConfigOrgRelationEntity>    返回类型 
	* @see queryDipConfigOrgInfoByConfType
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-5-22 下午1:44:16   
	* @throws 
	*/ 
	List<ConfigOrgRelationEntity> queryDipConfigOrgInfoByConfType(String confType);

	/** 
	* @Title: queryConfigOrgInfoByConfTypeAndOrgCode 
	* @Description: 根据部门编码,配置项烈性查询有效的配置项信息
	* @param queryMap
	* @return  设定文件 
	* @return List<ConfigOrgRelationEntity>    返回类型 
	* @see queryConfigOrgInfoByConfTypeAndOrgCode
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-5-22 下午2:17:17   
	* @throws 
	*/ 
	List<ConfigOrgRelationEntity> queryConfigOrgInfoByConfTypeAndOrgCode(Map<String, Object> queryMap);
}
