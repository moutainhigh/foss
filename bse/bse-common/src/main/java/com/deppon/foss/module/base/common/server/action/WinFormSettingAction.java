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
 * PROJECT NAME	: bse-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/server/action/WinFormSettingAction.java
 * 
 * FILE NAME        	: WinFormSettingAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.server.action;

 
import org.apache.commons.lang.StringUtils;  
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.common.api.server.service.IWinFormSettingService;
import com.deppon.foss.module.base.common.api.shared.domain.WinFormSettingEntity;
import com.deppon.foss.module.base.common.api.shared.exception.MessageException;
import com.deppon.foss.module.base.common.api.shared.vo.MsgVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;

/**
 * 消息弹出框设置Action
 * @author 101911-foss-zhouChunlai
 * @date 2012-12-24 上午9:03:40
 */
public class WinFormSettingAction extends AbstractAction {

	private static final long serialVersionUID = -7142531313452305091L;

	/**
	 * logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(WinFormSettingAction.class);
	
	/**
	 * 消息弹出框设置Service
	 */
	IWinFormSettingService winFormSettingService;

	/**
	 * 消息Vo
	 */
	public MsgVo msgVo;

	/**
	 * 修改当前用户弹出间隔设置
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2012-12-24 上午9:07:23
	 * @return 
	 */
	public String updateSetting() {  
		try {
			if(null == msgVo){
				return returnError("参数有误");
			}
			winFormSettingService.uptUserSetting(msgVo.getWinEntity());
			return returnSuccess("设置成功");
		} catch (MessageException e) {
			LOGGER.error(e.getMessage(), e);
			return returnError(e);
		}
	}
	/**
	 * 修改当前用户弹出间隔设置
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2012-12-24 上午9:07:23
	 * @return 
	 */
	public String uptAutoAlertFlag() {  
		try {
			//获取当前用户设置信息
			CurrentInfo currentInfo=FossUserContext.getCurrentInfo();
			if(null == currentInfo){
				return returnError("session过期，请刷新页面!");
			}
			
			if(null == msgVo){
				return returnError("参数有误");
			}
			//获取前端弹出设置
			String autoAlertFlag=msgVo.getWinEntity().getAutoAlertFlag();
			//校验前端输入参数的合法性
			if(StringUtils.isEmpty(autoAlertFlag)){
				return returnError("是否自动弹出设置不能为空");
			}
			//更新当前用户设置信息
			winFormSettingService.uptUserSetting(currentInfo,autoAlertFlag);
			return returnSuccess();
		} catch (MessageException e) {
			LOGGER.error(e.getMessage(), e);
			return returnError(e);
		}
	}
	
	/**
	 * 查询当前用户弹出间隔设置
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2012-12-24 上午9:07:03
	 * @return 
	 */
	@JSON	
	public String queryWinFormSettingInfo() {
		try {
			if(null ==msgVo){
				return returnError("参数有误");
			}
			//获取当前用户编码
			String userCode=msgVo.getWinEntity().getUserCode();
			//根据当前用户编码读取当前用户的设置信息
			WinFormSettingEntity entity = winFormSettingService.getUserSettingByUserCode(userCode);
			msgVo.setWinEntity(entity);
			return returnSuccess();
		} catch (MessageException e) {
			LOGGER.error(e.getMessage(), e);
			return returnError(e);
		}
	}

	public void setWinFormSettingService(
			IWinFormSettingService winFormSettingService) {
		this.winFormSettingService = winFormSettingService;
	}

	public MsgVo getMsgVo() {
		return msgVo;
	}
	
	public void setMsgVo(MsgVo msgVo) {
		this.msgVo = msgVo;
	}

}
