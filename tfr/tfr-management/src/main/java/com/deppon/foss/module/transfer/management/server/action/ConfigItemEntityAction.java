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
 * File Name：ConfigItemEntityAction.java   
 *   
 * Version:1.0
 * ：2013-4-16   
 * Copyright (c) 2013  Deppon All Rights Reserved. 2013    
 * @author: liangfuxiang liangfux@cn.ibm.com
 */

package com.deppon.foss.module.transfer.management.server.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.transfer.management.api.server.service.IConfigItemEntityService;
import com.deppon.foss.module.transfer.management.api.shared.domain.ConfigItemEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.ConfigTypeEntity;
import com.deppon.foss.module.transfer.management.api.shared.vo.ConfigItemVo;

/**
 * Class Description：
 * 
 * @author: liangfuxiang liangfux@cn.ibm.com
 * @version: 2013-4-16 上午9:06:37
 */

public class ConfigItemEntityAction extends AbstractAction {

	/**
	 * serialVersionUID:
	 * 
	 * @since Ver 1.0
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(ConfigItemEntityAction.class);

	/**
	 * 配置项VO
	 */
	private ConfigItemVo configItemVo = new ConfigItemVo();

	/**
	 * configItemVo
	 * 
	 * @return the configItemVo
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public ConfigItemVo getConfigItemVo() {
		return configItemVo;
	}

	/**
	 * @param configItemVo the configItemVo to set Date:2013-4-17下午9:10:02
	 */

	public void setConfigItemVo(ConfigItemVo configItemVo) {
		this.configItemVo = configItemVo;
	}

	/**
	 * 服务类
	 */
	private IConfigItemEntityService configItemEntityService;

	/**
	 * @param configItemEntityService the configItemEntityService to set Date:2013-4-16下午3:49:30
	 */

	public void setConfigItemEntityService(IConfigItemEntityService configItemEntityService) {
		this.configItemEntityService = configItemEntityService;
	}

	/**
	 * 
	 * @Title: queryAllConfigTypes
	 * @Description: 为了初始化查询条件，查出所有有效的配置项类型
	 * @return 设定文件
	 * @return String 返回类型
	 * @see queryAllConfigTypes
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-17 下午7:39:18
	 * @throws
	 */
	@JSON
	public String queryAllConfigTypes() {
		try {
			configItemVo.setConfigTypeEntityList(configItemEntityService.queryAllConfigTypes());
		}
		catch (BusinessException e) {
			logger.error("ConfigItemEntityAction[queryConfigTypeEntityList()]:" + e.getMessage());
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 
	 * @Title: addConfigTypeEntity
	 * @Description: 新增配置项类型信息
	 * @return 设定文件
	 * @return String 返回类型
	 * @see addConfigTypeEntity
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-16 上午9:19:51
	 * @throws
	 */
	@JSON
	public String addConfigTypeEntity() {
		try {
			configItemEntityService.addConfigTypeEntity(configItemVo.getConfigTypeEntity());
		}
		catch (BusinessException e) {
			logger.error("ConfigItemEntityAction[addConfigTypeEntity()]:" + e.getMessage());
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 
	 * @Title: abolishConfigTypeEntity
	 * @Description: 作废配置项类型信息(此时，对应的配置项类型下的所有配置项都将作废，而且部门对应配置参数中的所有 该配置项类型的配置参数将作废)
	 * @return 设定文件
	 * @return String 返回类型
	 * @see abolishConfigTypeEntity
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-16 上午9:26:31
	 * @throws
	 */
	@JSON
	public String abolishConfigTypeEntity() {
		try {
			List<String> idList = configItemVo.getIdList();
			// 非空时，封装为对象集合处理
			if (CollectionUtils.isNotEmpty(idList)) {
				List<ConfigTypeEntity> configTypeEntityList = new ArrayList<ConfigTypeEntity>();
				ConfigTypeEntity configTypeEntity = null;
				for (int i = 0; i < idList.size(); i++) {
					configTypeEntity = new ConfigTypeEntity();
					configTypeEntity.setId(idList.get(i));
					configTypeEntityList.add(configTypeEntity);
				}
				// 作废
				configItemEntityService.abolishConfigTypeEntity(configTypeEntityList);
			}
		}
		catch (BusinessException e) {
			logger.error("ConfigItemEntityAction[abolishConfigTypeEntity()]:" + e.getMessage());
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 
	 * @Title: modifyConfigTypeEntity
	 * @Description: 修改配置项类型信息
	 * @return 设定文件
	 * @return String 返回类型
	 * @see modifyConfigTypeEntity
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-16 上午9:31:40
	 * @throws
	 */
	@JSON
	public String modifyConfigTypeEntity() {
		try {
			configItemEntityService.modifyConfigTypeEntity(configItemVo.getConfigTypeEntity());
		}
		catch (BusinessException e) {
			logger.error("ConfigItemEntityAction[modifyConfigTypeEntity()]:" + e.getMessage());
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 
	 * @Title: queryConfigTypeEntityList
	 * @Description: 查询配置项类型信息
	 * @return 设定文件
	 * @return String 返回类型
	 * @see queryConfigTypeEntityList
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-16 下午2:15:25
	 * @throws
	 */
	@JSON
	public String queryConfigTypeEntityList() {
		try {
			configItemVo.setConfigTypeEntityList(configItemEntityService.queryConfigTypeEntityList(configItemVo.getConfigTypeEntity(), this.start, this.limit));
			this.setTotalCount((Long) configItemEntityService.queryConfigTypeEntityListCount(configItemVo.getConfigTypeEntity()));
		}
		catch (BusinessException e) {
			logger.error("ConfigItemEntityAction[queryConfigTypeEntityList()]:" + e.getMessage());
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 
	 * @Title: queryConfigTypeEntity
	 * @Description: 查询配置项类型信息
	 * @return 设定文件
	 * @return String 返回类型
	 * @see queryConfigTypeEntity
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-17 下午3:30:33
	 * @throws
	 */
	@JSON
	public String queryConfigTypeEntity() {
		try {
			configItemVo.setConfigTypeEntity(configItemEntityService.queryConfigTypeEntity(configItemVo.getConfigTypeEntity()));
		}
		catch (BusinessException e) {
			logger.error("ConfigItemEntityAction[queryConfigTypeEntity()]:" + e.getMessage());
			return returnError(e);
		}

		return returnSuccess();
	}

	/**
	 * 
	 * @Title: addConfigItemEntity
	 * @Description: 增加配置项信息
	 * @return 设定文件
	 * @return String 返回类型
	 * @see addConfigItemEntity
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-16 上午9:32:45
	 * @throws
	 */
	@JSON
	public String addConfigItemEntity() {
		try {
			configItemEntityService.addConfigItemEntity(configItemVo.getConfigItemEntity());
		}
		catch (BusinessException e) {
			logger.error("ConfigItemEntityAction[addConfigItemEntity()]:" + e.getMessage());
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 
	 * @Title: abolishConfigItemEntity
	 * @Description: 作废配置项信息
	 * @return 设定文件
	 * @return String 返回类型
	 * @see abolishConfigItemEntity
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-16 上午9:33:50
	 * @throws
	 */
	@JSON
	public String abolishConfigItemEntity() {
		try {

			List<String> idList = configItemVo.getIdList();
			// 非空时，封装为对象集合处理
			if (CollectionUtils.isNotEmpty(idList)) {
				List<ConfigItemEntity> configItemEntityList = new ArrayList<ConfigItemEntity>();
				ConfigItemEntity configItemEntity = null;
				for (int i = 0; i < idList.size(); i++) {
					configItemEntity = new ConfigItemEntity();
					configItemEntity.setId(idList.get(i));
					configItemEntityList.add(configItemEntity);
				}

				configItemEntityService.abolishConfigItemEntity(configItemEntityList);
			}
		}
		catch (BusinessException e) {
			logger.error("ConfigItemEntityAction[abolishConfigItemEntity()]:" + e.getMessage());
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 
	 * @Title: modifyConfigItemEntity
	 * @Description: 修改配置项信息
	 * @return 设定文件
	 * @return String 返回类型
	 * @see modifyConfigItemEntity
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-16 上午9:34:31
	 * @throws
	 */
	@JSON
	public String modifyConfigItemEntity() {
		try {
			configItemEntityService.modifyConfigItemEntity(configItemVo.getConfigItemEntity());
		}
		catch (BusinessException e) {
			logger.error("ConfigItemEntityAction[modifyConfigItemEntity()]:" + e.getMessage());
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 
	 * @Title: queryConfigItemEntityList
	 * @Description: 查询配置项信息
	 * @return 设定文件
	 * @return String 返回类型
	 * @see queryConfigItemEntityList
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-16 上午9:36:07
	 * @throws
	 */
	@JSON
	public String queryConfigItemEntityList() {
		try {
			configItemVo.setConfigItemEntityList(configItemEntityService.queryConfigItemEntityList(configItemVo.getConfigItemEntity(), this.start, this.limit));
			this.setTotalCount(configItemEntityService.queryConfigItemEntityListCount(configItemVo.getConfigItemEntity()));
		}
		catch (BusinessException e) {
			logger.error("ConfigItemEntityAction[queryConfigItemEntityList()]:" + e.getMessage());
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 
	 * @Title: queryConfigItemEntity
	 * @Description: 查询配置项信息
	 * @return 设定文件
	 * @return String 返回类型
	 * @see queryConfigItemEntity
	 * @author: liangfuxiang liangfux@cn.ibm.com
	 * @version: 2013-4-17 下午3:31:38
	 * @throws
	 */
	@JSON
	public String queryConfigItemEntity() {
		try {
			configItemVo.setConfigItemEntity(configItemEntityService.queryConfigItemEntity(configItemVo.getConfigItemEntity()));
		}
		catch (BusinessException e) {
			logger.error("ConfigItemEntityAction[queryConfigItemEntity()]:" + e.getMessage());
			return returnError(e);
		}

		return returnSuccess();
	}

}
