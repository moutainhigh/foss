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
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/MacWhiteService.java
 * 
 * FILE NAME        	: MacWhiteService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IMacWhiteDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMacWhiteService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MacWhiteEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MacWhiteException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * MAC地址白名单Service接口实现
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-3-12 上午9:58:37 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-3-12 上午9:58:37
 * @since
 * @version
 * 新增：增，删，改，查 。 author:132599-foss-shenweihua,date:2013-4-25 上午11:01:13
 */
/**
 * @author Administrator
 *
 */
public class MacWhiteService implements IMacWhiteService{
	
	/**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MacWhiteService.class);
    
    /**
     * MAC地址白名单DAO接口.
     */
    private IMacWhiteDao macWhiteDao;
    
    
    
    /**
     * 设置 mAC地址白名单DAO接口.
     *
     * @param macWhiteDao the macWhiteDao to set
     */
    public void setMacWhiteDao(IMacWhiteDao macWhiteDao) {
        this.macWhiteDao = macWhiteDao;
    }


    /**
     * <p>验证MAC地址是否存在</p>.
     *
     * @param mac 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-3-12 上午9:58:37
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.MacWhiteService#queryMacAddressByMac(java.lang.String)
     */
    @Override
    public boolean queryMacAddressByMac(String mac) {
	if(StringUtil.isBlank(mac)){
	    return false;
	}else {
	    return macWhiteDao.queryMacAddressByMac(mac);
	}
    }

    /**
     * <p>新增MAC地址白名单信息</p> 
     * @author 132599-foss-shenweihua
     * @date 2013-4-25 上午10:40:21
     * @param entity
     * @return
     * @see
     */
	@Override
	public int addMacWhite(MacWhiteEntity entity) {
		if(null == entity){
		    throw new MacWhiteException("传入的参数不允许为空！");
		}
		// 第一次记录新增时，虚拟编码为记录的id
		entity.setId(UUIDUtils.getUUID());
		
		entity.setCreateDate(new Date());
		
		String macAddress = entity.getMacAddress();
		//把地址中的“-”换成“：”
		macAddress = macAddress.replace("-", ":");
		entity.setMacAddress(macAddress);
		//设置状态为有效
		entity.setActive(FossConstants.ACTIVE);
		//打印日志
		LOGGER.debug("id: " + entity.getId());
		if(queryMacAddressByMac(macAddress)){
			 throw new MacWhiteException("改地址已经存在！");
		}
		return macWhiteDao.addMacWhite(entity);
	}

	/**
     * <p>修改MAC地址白名单信息</p> 
     * @author 132599-foss-shenweihua
     * @date 2013-4-25 上午10:40:21
     * @param entity
     * @return
     * @see
     */
	@Override
	public int updateMacWhite(MacWhiteEntity entity) {
		if(null == entity){
		    throw new MacWhiteException("传入的参数不允许为空！");
		}else if(StringUtils.isBlank(entity.getId())){
		    throw new MacWhiteException("ID不允许为空！");
		}
		List<String> list = new ArrayList<String>();
		list.add(entity.getId());
		//作废mac地址白名单信息
		int result = macWhiteDao.deleteMacWhiteById(list);
		list = null;
		if(result > 0){
		    //作废成功
		    entity.setActive(FossConstants.ACTIVE);
		    entity.setId(UUIDUtils.getUUID());
		    entity.setCreateDate(new Date());
		    
		    return macWhiteDao.addMacWhite(entity);
		}else {
		    return FossConstants.FAILURE;
		}
	}

	/**
     * <p>作废MAC地址白名单信息</p> 
     * @author 132599-foss-shenweihua
     * @date 2013-4-25 上午10:40:21
     * @param idList MAC地址白名单信息ID集合
     * @return
     * @see
     */
	@Override
	public int deleteMacWhiteById(List<String> idList) {
		if(CollectionUtils.isEmpty(idList)){
		    throw new MacWhiteException("虚拟编码不允许为空！");
		}else{
		    return macWhiteDao.deleteMacWhiteById(idList);
		}
	}

	/**
     * 根据传入对象查询符合条件所有MAC地址白名单信息
     * 
     * @author 132599-foss-shenweihua
     * @date 2013-4-25 上午10:50:21
     * @param entity
     *            MAC地址白名单信息实体
     * @param limit
     *            每页最大显示记录数
     * @param start
     *            开始记录数
     * @return 符合条件的实体列表
     * @see
     */
	@Override
	public List<MacWhiteEntity> queryAllMacWhite(MacWhiteEntity entity,
			int limit, int start) {
		if(null == entity){
		    throw new MacWhiteException("传入的参数不允许为空！");
		}else {
		    entity.setActive(FossConstants.ACTIVE);
		    return macWhiteDao.queryAllMacWhite(entity, limit, start);
		}
	}

	/**
     * 统计总记录数
     * 
     * @author dp-xieyantao
     * @date 2013-4-15 下午3:10:32
     * @param entity
     *             MAC地址白名单信息实体
     * @return
     * @see
     */
	@Override
	public Long queryRecordCount(MacWhiteEntity entity) {
		if(null == entity){
		    throw new MacWhiteException("传入的参数不允许为空！");
		}else {
		    entity.setActive(FossConstants.ACTIVE);
		    return macWhiteDao.queryRecordCount(entity);
		}
	}

    /**
     * 验证Mac地址
     * 
     * @author foss-shenweihua
     * @date 2013-4-27 下午8:20:32
     * @param macAddress
     *             MAC地址白名单信息实体
     * @return
     * @see
     */
	@Override
	public List<MacWhiteEntity> validateMacAddress(String macAddress) {
		
		return null;
	}

	
}
