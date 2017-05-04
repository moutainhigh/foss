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
 * PROJECT NAME	: bse-dict
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/dict/server/action/ConfigurationParamsAction.java
 * 
 * FILE NAME        	: ConfigurationParamsAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.dict.server.action;

import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.base.dict.api.shared.exception.MessageType;
import com.deppon.foss.module.base.dict.api.shared.vo.ConfigurationParamsVo;

public class ConfigurationParamsAction extends AbstractAction {

	private static final long serialVersionUID = -4387687988772020011L;
	
	//配置参数service
	private IConfigurationParamsService configurationParamsService;
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
    //VO
	private ConfigurationParamsVo configurationParamsVo = new ConfigurationParamsVo();
	public ConfigurationParamsVo getConfigurationParamsVo() {
		return configurationParamsVo;
	}

	public void setConfigurationParamsVo(ConfigurationParamsVo configurationParamsVo) {
		this.configurationParamsVo = configurationParamsVo;
	}

	/**
	 * .
	 * <p>
	 * 根据entity的属性查询 配置参数<br/>
	 * 方法名：queryConfigurationParamsByEntity
	 * </p>
	 * 
	 * @author 078838-foss-zhangbin
	 * @时间 2012-11-16
	 * @since JDK1.6
	 */
	@JSON
	public String queryConfigurationParamsByEntity() {
		try {
			List<ConfigurationParamsEntity> configurationParamsEntityLis = configurationParamsService.queryConfigurationParamsExactByEntity(configurationParamsVo.getConfigurationParamsEntity(), start,limit);
			configurationParamsVo.setConfigurationParamsEntityList(configurationParamsEntityLis);
			this.setTotalCount(configurationParamsService.queryConfigurationParamsExactByEntityCount(configurationParamsVo.getConfigurationParamsEntity()));
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * .
	 * <p>
	 * 作废参数配置 <br/>
	 * 方法名：deleteConfigurationParams
	 * </p>
	 * 
	 * @author 078838-foss-zhangbin
	 * @时间 2012-11-16
	 * @since JDK1.6
	 */
	@JSON
	public String deleteConfigurationParams() {
		try {
			UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
			String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
			List<String> configurationParamsVirtualCodeList = configurationParamsVo.getConfigurationParamsVirtualCodeList();
			String[] array = new String[configurationParamsVirtualCodeList.size()];
			for(int i=0,l=configurationParamsVirtualCodeList.size();i<l; i++){
				array[i]=configurationParamsVirtualCodeList.get(i);
			}
			configurationParamsService.deleteConfigurationParamsMore(array, userCode);
			return returnSuccess(MessageType.DELETE_CONFIGURATIONPARAMS_SUCCESS);//提示作废成功
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	


	/**
	 * .
	 * <p>
	 * 新增参数配置 <br/>
	 * 方法名：addConfigurationParams
	 * </p>
	 * 
	 * @author 078838-foss-zhangbin
	 * @时间 2012-11-07
	 * @since JDK1.6
	 */
	@JSON
	public String addConfigurationParams() {
		try {
			configurationParamsService.addConfigurationParamsMore(configurationParamsVo.getConfigurationParamsEntityList());
			return returnSuccess(MessageType.SAVE_CONFIGURATIONPARAMS_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	

	/**
	 * .
	 * <p>
	 * 修改参数配置 <br/>
	 * 方法名：updateConfigurationParams
	 * </p>
	 * 
	 * @author 078838-foss-zhangbin
	 * @时间 2012-11-16
	 * @since JDK1.6
	 */
	@JSON
	public String updateConfigurationParams() {
		try {
			configurationParamsService.updateConfigurationParams(configurationParamsVo.getConfigurationParamsEntity());
			return returnSuccess(MessageType.UPDATE_CONFIGURATIONPARAMS_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * .
	 * <p>
	 * 查询配置参数相关信息 <br/>
	 * 方法名：searchConfigurationParamsInfo
	 * </p>
	 * 
	 * @author 078838-foss-zhangbin
	 * @时间 2012-11-16
	 * @since JDK1.6
	 */
	@JSON
	public String searchConfigurationParamsInfo() {
		try {
			ConfigurationParamsEntity configurationParamsEntity = configurationParamsService.queryConfigurationParamsByVirtualCode(configurationParamsVo.getConfigurationParamsEntity().getVirtualCode());
			configurationParamsVo.setConfigurationParamsEntity(configurationParamsEntity);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	


}
