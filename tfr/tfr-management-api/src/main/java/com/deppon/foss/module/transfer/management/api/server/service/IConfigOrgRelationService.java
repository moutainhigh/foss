/**   
 * File Name：IConfigOrgRelationService.java   
 *   
 * Version:1.0
 * ：2013-3-28   
 * Copyright (c) 2013  Deppon All Rights Reserved. 2013    
 * @author: liangfuxiang liangfux@cn.ibm.com
 */

package com.deppon.foss.module.transfer.management.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.management.api.shared.domain.ConfigOrgRelationEntity;

/**
 * Class Description： 配置项与组织对应关系服务类接口
 * 
 * @author: liangfuxiang liangfux@cn.ibm.com
 * @version: 2013-3-28 下午6:01:01
 */

public interface IConfigOrgRelationService extends IService {

	/**
	 * @param limit
	 * @param start
	 * @Title: queryConfigOrgRelationEntityList
	 * @Description: 查询组织配置项数据信息列表
	 * @param configOrgRelationEntity
	 * @return 设定文件
	 * @return List<ConfigOrgRelationEntity> 返回类型
	 * @see queryConfigOrgRelationEntityList
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-3-29 下午2:07:10
	 * @throws
	 */
	List<ConfigOrgRelationEntity> queryConfigOrgRelationEntityList(ConfigOrgRelationEntity configOrgRelationEntity, int start, int limit);

	/**
	 * @Title: queryConfigOrgRelationEntityTotalCount
	 * @Description: 查询组织配置项数据信息数量
	 * @param configOrgRelationEntity
	 * @return 设定文件
	 * @return Long 返回类型
	 * @see queryConfigOrgRelationEntityTotalCount
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-3-29 下午2:12:22
	 * @throws
	 */
	Long queryConfigOrgRelationEntityTotalCount(ConfigOrgRelationEntity configOrgRelationEntity);

	/**
	 * 
	 * @Title: abolishConfigOrgRelationById
	 * @Description: 作废配置参数信息
	 * @param idList 设定文件
	 * @return void 返回类型
	 * @see abolishConfigOrgRelationById
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-3 下午2:23:15
	 * @throws
	 */
	void abolishConfigOrgRelationById(List<String> idList);

	/**
	 * 
	 * @Title: abolishConfigOrgRelationEntityList
	 * @Description: 作废配置参数信息
	 * @param configOrgRelationEntityList 设定文件
	 * @return void 返回类型
	 * @see abolishConfigOrgRelationById
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-3 下午2:23:15
	 * @throws
	 */
	void abolishConfigOrgRelationEntityList(List<ConfigOrgRelationEntity> configOrgRelationEntityList);

	/**
	 * @Title: modifyConfigOrgRealtion
	 * @Description: 修改配置参数信息
	 * @param configOrgRelationEntity 设定文件
	 * @return void 返回类型
	 * @see modifyConfigOrgRealtion
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-3 下午3:17:20
	 * @throws
	 */
	void modifyConfigOrgRealtion(ConfigOrgRelationEntity configOrgRelationEntity) throws TfrBusinessException;

	/**
	 * @Title: addConfigOrgRelationList
	 * @Description: 批量增加配置项信息
	 * @param ConfigOrgRelationDto 设定文件
	 * @return void 返回类型
	 * @see addConfigOrgRelationList
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-7 下午8:00:34
	 * @throws
	 */
	void addConfigOrgRelationList(List<ConfigOrgRelationEntity> configOrgRelationEntityList) throws TfrBusinessException;

	/**
	 * @Title: queryConfigOrgRelationEntity
	 * @Description: 查询配置参数信息
	 * @param configItemEntity
	 * @return 设定文件
	 * @return ConfigItemEntity 返回类型
	 * @see queryConfigOrgRelationEntity
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-11 上午10:33:49
	 * @throws
	 */
	ConfigOrgRelationEntity queryConfigOrgRelationEntity(ConfigOrgRelationEntity configOrgRelationEntity);

	/**
	 * @Title: queryConfigOrgRelationEntityList
	 * @Description: 查询配置参数信息
	 * @param configOrgRelationEntity
	 * @return 设定文件
	 * @return List<ConfigOrgRelationEntity> 返回类型
	 * @see queryConfigOrgRelationEntityList
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-13 下午5:31:17
	 * @throws
	 */
	List<ConfigOrgRelationEntity> queryConfigOrgRelationEntityListNoPage(ConfigOrgRelationEntity configOrgRelationEntity);

	/** 
	* @Title: queryDipConfigOrgInfoByConfType 
	* @Description: 查询顶级组织(DIP)配置项信息
	* @param confType
	* @return  设定文件 
	* @return List<ConfigOrgRelationEntity>    返回类型 
	* @see queryDipConfigOrgInfoByConfType
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-5-22 下午1:42:21   
	* @throws 
	*/ 
	List<ConfigOrgRelationEntity> queryDipConfigOrgInfoByConfType(String confType);

	/** 
	* @Title: queryConfigOrgInfoByConfTypeAndOrgCode 
	* @Description: 根据部门编码,配置项烈性查询有效的配置项信息
	* @param confType
	* @param orgCode
	* @return  设定文件 
	* @return List<ConfigOrgRelationEntity>    返回类型 
	* @see queryConfigOrgInfoByConfTypeAndOrgCode
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-5-22 下午2:11:35   
	* @throws 
	*/ 
	List<ConfigOrgRelationEntity> queryConfigOrgInfoByConfTypeAndOrgCode(String confType, String orgCode);

}
