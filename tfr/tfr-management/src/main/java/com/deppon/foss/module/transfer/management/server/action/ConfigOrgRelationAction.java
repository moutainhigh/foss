/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-management
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/server/action/CertificateBagAction.java
 *  
 *  FILE NAME          :CertificateBagAction.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/**   
 * File Name：ConfigOrgRelationAction.java   
 *   
 * Version:1.0
 * ：2013-3-28   
 * Copyright (c) 2013  Deppon All Rights Reserved. 2013    
 * @author: liangfuxiang liangfux@cn.ibm.com
 */

package com.deppon.foss.module.transfer.management.server.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.transfer.management.api.server.service.IConfigItemEntityService;
import com.deppon.foss.module.transfer.management.api.server.service.IConfigOrgRelationService;
import com.deppon.foss.module.transfer.management.api.shared.vo.ConfigOrgRelationVo;

/**
 * Class Description： 配置项与组织对应关系ACTION
 * 
 * @author: liangfuxiang liangfux@cn.ibm.com
 * @version: 2013-3-28 下午5:59:28
 */

public class ConfigOrgRelationAction extends AbstractAction {

	private static final long serialVersionUID = -3325500995143198494L;

	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(ConfigOrgRelationAction.class);
	/**
	 * 配置项与组织对应关系服务类接口
	 */
	private IConfigOrgRelationService configOrgRelationService;
	/**
	 * 配置项与组织对应关系VO
	 */
	private ConfigOrgRelationVo configOrgRelationVo = new ConfigOrgRelationVo();

	/**
	 * 配置项信息service
	 */
	private IConfigItemEntityService configItemEntityService;

	/**
	 * @param configItemEntityService the configItemEntityService to set Date:2013-4-2上午9:55:34
	 */

	public void setConfigItemEntityService(IConfigItemEntityService configItemEntityService) {
		this.configItemEntityService = configItemEntityService;
	}

	/**
	 * @param configOrgRelationService the configOrgRelationService to set Date:2013-3-29下午2:01:30
	 */

	public void setConfigOrgRelationService(IConfigOrgRelationService configOrgRelationService) {
		this.configOrgRelationService = configOrgRelationService;
	}

	/**
	 * configOrgRelationVo
	 * 
	 * @return the configOrgRelationVo
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public ConfigOrgRelationVo getConfigOrgRelationVo() {
		return configOrgRelationVo;
	}

	/**
	 * @param configOrgRelationVo the configOrgRelationVo to set Date:2013-3-29下午2:02:12
	 */

	public void setConfigOrgRelationVo(ConfigOrgRelationVo configOrgRelationVo) {
		this.configOrgRelationVo = configOrgRelationVo;
	}

	/**
	 * 
	 * @Title: queryConfigOrgRelationEntities
	 * @Description: 查询组织配置项信息
	 * @return 设定文件
	 * @return String 返回类型
	 * @see queryConfigOrgRelationEntities
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-3-29 下午2:13:39
	 * @throws
	 */
	@JSON
	public String queryConfigOrgRelationEntities() {
		try {
			// 获取组织配置项信息信息(默认分页)
			configOrgRelationVo.setConfigOrgRelationEntityList(configOrgRelationService.queryConfigOrgRelationEntityList(configOrgRelationVo.getConfigOrgRelationEntity(), this.start, this.limit));
			// 获取组织配置项信息数量
			this.setTotalCount(configOrgRelationService.queryConfigOrgRelationEntityTotalCount(configOrgRelationVo.getConfigOrgRelationEntity()));
		}
		catch (BusinessException e) {
			// 记录错误日志
			logger.error("ConfigOrgRelationAction[queryConfigOrgRelationEntities()]:" + e.toString());
			// 返回异常
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 
	 * @Title: queryDistinctConfigTypes
	 * @Description: 查询配置项不重复的类型信息
	 * @return 设定文件
	 * @return String 返回类型
	 * @see queryDistinctConfigTypes
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-2 上午9:53:49
	 * @throws
	 */
	@JSON
	public String queryDistinctConfigTypes() {

		try {
			// 获取配置项不重复的类型信息
			configOrgRelationVo.setDistinctConfigTypeEntityList(configItemEntityService.queryDistinctConfigTypes());
		}
		catch (BusinessException e) {
			// 记录错误日志
			logger.error("ConfigOrgRelationAction[queryDistinctConfigTypes()]:" + e.toString());
			// 返回异常
			return returnError(e);
		}

		return returnSuccess();
	}

	/**
	 * 
	 * @Title: queryConfigItemEntitsByConfigType
	 * @Description: 根据配置项类型，查询有效的配置项信息
	 * @return 设定文件
	 * @return String 返回类型
	 * @see queryConfigItemEntitsByConfigType
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-2 上午10:00:15
	 * @throws
	 */
	@JSON
	public String queryConfigItemEntitsByConfigType() {

		try {
			configOrgRelationVo.setConfigItemEntityList(configItemEntityService.queryConfigItemEntitsByConfigType(configOrgRelationVo.getConfigItemEntity()));
		}
		catch (BusinessException e) {
			// 记录错误日志
			logger.error("ConfigOrgRelationAction[queryConfigItemEntitsByConfigType()]:" + e.toString());
			// 返回异常
			return returnError(e);
		}

		return returnSuccess();
	}

	/**
	 * @Title: abolishConfigOrgRelation
	 * @Description: 作废配置参数信息
	 * @return 设定文件
	 * @return String 返回类型
	 * @see abolishConfigOrgRelation
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-3 下午2:20:09
	 * @throws
	 */
	@JSON
	public String abolishConfigOrgRelation() {

		try {
			// 作废指定ID下的配置参数信息
			configOrgRelationService.abolishConfigOrgRelationById(configOrgRelationVo.getIdList());
		}
		catch (BusinessException e) {
			// 记录错误日志
			logger.error("ConfigOrgRelationAction[abolishConfigOrgRelation()]:" + e.toString());
			// 返回异常
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 
	 * @Title: updateConfigOrgRealtion
	 * @Description: 修改配置参数信息
	 * @return 设定文件
	 * @return String 返回类型
	 * @see updateConfigOrgRealtion
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-3 下午3:16:06
	 * @throws
	 */
	@JSON
	public String modifyConfigOrgRelation() {
		try {
			// 修改配置参数信息
			configOrgRelationService.modifyConfigOrgRealtion(configOrgRelationVo.getConfigOrgRelationEntity());
		}
		catch (BusinessException e) {
			// 记录错误日志
			logger.error("ConfigOrgRelationAction[modifyConfigOrgRealtion()]:" + e.toString());
			// 返回异常
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 
	 * @Title: saddConfigOrgRelationList
	 * @Description: 批量增加配置项信息
	 * @return 设定文件
	 * @return String 返回类型
	 * @see addConfigOrgRelationList
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-7 下午7:58:44
	 * @throws
	 */
	@JSON
	public String addConfigOrgRelationList() {

		try {
			// 增加配置项信息
			configOrgRelationService.addConfigOrgRelationList(configOrgRelationVo.getConfigOrgRelationEntityList());
		}
		catch (BusinessException e) {
			// 记录错误日志
			logger.error("ConfigOrgRelationAction[saddConfigOrgRelationList()]:" + e.toString());
			// 返回异常
			return returnError(e);
		}

		return returnSuccess();
	}

	/**
	 * 
	 * @Title: queryConfigOrgRelationEntity
	 * @Description: 查询配置参数信息
	 * @return 设定文件
	 * @return String 返回类型
	 * @see queryConfigOrgRelationEntity
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-11 上午10:31:40
	 * @throws
	 */
	@JSON
	public String queryConfigOrgRelationEntity() {
		try {
			// 查询唯一的配置参数信息
			configOrgRelationVo.setConfigOrgRelationEntity(configOrgRelationService.queryConfigOrgRelationEntity(configOrgRelationVo.getConfigOrgRelationEntity()));
		}
		catch (BusinessException e) {
			// 记录错误日志
			logger.error("ConfigOrgRelationAction[queryConfigOrgRelationEntity()]:" + e.toString());
			// 返回异常
			return returnError(e);
		}

		return returnSuccess();
	}
}
