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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/server/service/impl/WinFormSettingService.java
 * 
 * FILE NAME        	: WinFormSettingService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.common.server.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.common.api.server.dao.IWinFormSettingDao;
import com.deppon.foss.module.base.common.api.server.service.IWinFormSettingService;
import com.deppon.foss.module.base.common.api.shared.domain.WinFormSettingEntity;
import com.deppon.foss.module.base.common.api.shared.exception.MessageException;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.util.define.FossConstants;

/**
 * 消息弹出框间隔设置Service
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2012-12-24 上午9:28:47
 */
public class WinFormSettingService implements IWinFormSettingService {

	/**
	 * 消息弹出框间隔设置Dao
	 */
	private IWinFormSettingDao winFormSettingDao;

	/**
	 * 查询当前用户间隔设置
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @param
	 * @date 2012-12-24 上午8:59:00
	 * @return
	 * @see com.deppon.foss.module.base.common.api.server.service.IWinFormSettingService#getUserSettingByUserId(com.deppon.foss.module.base.common.api.shared.domain.WinFormSettingEntity)
	 */
	@Transactional
	@Override
	public WinFormSettingEntity getUserSettingByUserCode(String userCode) {
		if (StringUtils.isEmpty(userCode)) {
			throw new MessageException("当前用户编码不能为空");
		}
		//根据当前用户编码获取该用户配置信息
		WinFormSettingEntity entity = winFormSettingDao.getUserSettingByUserCode(userCode);
		//如果该用户是第一次登录则设置默认值
		if (null == entity) {
			//初始化默认配置信息
			entity = initWinFormSettingInfo(userCode);
		}
		return entity;
	}

	/**
	 * 当用户第一次设置时，默认插入一条
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @param
	 * @date 2012-12-25 下午9:16:05
	 * @return
	 */
	private WinFormSettingEntity initWinFormSettingInfo(String userCode) {
		//实例化弹出间隔设置实体
		WinFormSettingEntity entity = new WinFormSettingEntity();
		//设置用户编码
		entity.setUserCode(userCode);
		// 自动弹出
		entity.setAutoAlertFlag(FossConstants.ACTIVE);
		// 相隔8分钟弹出
		entity.setIntervalTime(NumberConstants.NUMERAL_EIGHT);
		//设置ID标识
		entity.setId(UUID.randomUUID().toString());
		//设置创建时间
		entity.setCreateTime(new Timestamp(new Date().getTime()));
		//保存弹出间隔设置时间
		int i = winFormSettingDao.insertWinFormSetting(entity);
		if (1 != i) {
			throw new MessageException("新增失败");
		}
		return entity;
	}

	/**
	 * 据据前端传入参数更新当前用户设置
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @param
	 * @date 2012-12-24 上午9:08:23
	 * @return
	 */
	@Transactional
	@Override
	public void uptUserSetting(WinFormSettingEntity entity) {
		if (null == entity) {
			throw new MessageException("参数有误");
		}
		if (StringUtils.isEmpty(entity.getId())) {
			throw new MessageException("Id不能为空");
		}
		if (entity.getIntervalTime() == NumberConstants.NUMERAL_ZORE
				|| entity.getIntervalTime() < NumberConstants.NUMERAL_EIGHT) {
			throw new MessageException("间隔时间不能小于8分钟");
		}
		if (StringUtils.isEmpty(entity.getAutoAlertFlag())) {
			throw new MessageException("是否自动弹出设置不能为空");
		}

		int i = winFormSettingDao.uptWinFormSettingById(entity);
		if (1 != i) {
			throw new MessageException("更新失败");
		}
	}

	/**
	 * 根据当前用户设置弹出间隔设置
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @param
	 * @date 2012-12-24 上午8:59:31
	 * @return
	 * @see com.deppon.foss.module.base.common.api.server.service.IWinFormSettingService#updateSetting(com.deppon.foss.module.base.common.api.shared.domain.WinFormSettingEntity)
	 */
	@Transactional
	@Override
	public void uptUserSetting(CurrentInfo currentInfo, String autoAlertFlag) {
		if (null == currentInfo) {
			throw new MessageException("参数有误");
		}
		if (StringUtils.isEmpty(autoAlertFlag)) {
			throw new MessageException("是否自动弹出设置不能为空");
		}
		WinFormSettingEntity winEntity = winFormSettingDao
				.getUserSettingByUserCode(currentInfo.getEmpCode());
		if (null == winEntity) {
			initWinFormSettingInfo(currentInfo.getEmpCode());
		}
		int i = winFormSettingDao.uptWinFormSettingByUserCode(
				currentInfo.getEmpCode(), autoAlertFlag);
		if (1 != i) {
			throw new MessageException("更新失败");
		}
	}

	public IWinFormSettingDao getWinFormSettingDao() {
		return winFormSettingDao;
	}

	public void setWinFormSettingDao(IWinFormSettingDao winFormSettingDao) {
		this.winFormSettingDao = winFormSettingDao;
	}
}
