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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/CusAddressService.java
 * 
 * FILE NAME        	: CusAddressService.java
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

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ICusAddressDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusAddressService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAddressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.CusAddressException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 客户接送货地址Service接口
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-11-20 下午6:54:03
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-11-20 下午6:54:03
 * @since
 * @version
 */
public class CusAddressService implements ICusAddressService {

    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(CusAddressService.class);

    /**
     * 客户接送货地址DAO接口.
     */
    private ICusAddressDao cusAddressDao;
    
    /**
     * 设置 客户接送货地址DAO接口.
     *
     * @param cusAddressDao the new 客户接送货地址DAO接口
     */
    public void setCusAddressDao(ICusAddressDao cusAddressDao) {
	this.cusAddressDao = cusAddressDao;
    }
    
    /**
     * 新增客户接送货地址.
     *
     * @param entity 客户接送货地址实体
     * @return 1：成功；-1：失败
     * @throws CusAddressException 
     * @author 094463-foss-xieyantao
     * @date 2012-11-20 下午1:44:28
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ICusAddressService#addCusAddress(com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAddressEntity)
     */
    @Transactional
    @Override
    public int addCusAddress(CusAddressEntity entity)
	    throws CusAddressException {

	if (null == entity) {

	    throw new CusAddressException("传入的参数不允许为空！");
	} else {
	    if (null == entity.getCrmId()) {
		throw new CusAddressException("客户接送货地址CRM_ID不允许为空！");
	    }
	    
	    // 先验证在数据库是否存在
	    boolean isFlag = cusAddressDao.queryCusAddressByCrmId(entity.getCrmId(), null);

	    if (isFlag) {// 存在就修改
		cusAddressDao.updateCusAddress(entity);
	    } else {
		entity.setId(UUIDUtils.getUUID());
		// 第一次记录新增时，虚拟编码为记录的id
		entity.setVirtualCode(entity.getId());

		cusAddressDao.addCusAddress(entity);
	    }
			
	    return FossConstants.SUCCESS;
	}
    }

    /**
     * 根据code作废客户接送货地址.
     *
     * @param crmId 
     * @param modifyUser 
     * @return 1：成功；-1：失败
     * @throws CusAddressException 
     * @author dp-xieyantao
     * @date 2012-11-20 下午1:44:28
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ICusAddressService#deleteCusAddressByCode(java.lang.String,
     * java.lang.String)
     */
    @Transactional
    @Override
    public int deleteCusAddressByCode(BigDecimal crmId, String modifyUser)
	    throws CusAddressException {

	if (null == crmId) {
	    throw new CusAddressException("接送货地址的crmId不允许为空！");
	}
	LOGGER.debug("crmId: "+ crmId);

	return cusAddressDao.deleteCusAddressByCode(crmId, modifyUser);
    }

    /**
     * 修改客户接送货地址 对于有版本控制的编辑，先逻辑删除该记录，再新增一条记录.
     *
     * @param entity 客户接送货地址实体
     * @return 1：成功；-1：失败
     * @throws CusAddressException 
     * @author dp-xieyantao
     * @date 2012-11-20 下午1:44:28
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ICusAddressService#updateCusAddress(com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAddressEntity)
     */
    @Transactional
    @Override
    public int updateCusAddress(CusAddressEntity entity)
	    throws CusAddressException {

	if (null == entity) {
	    throw new CusAddressException("传入的参数不允许为空！");
	}
	if (null == entity.getCrmId()) {
	    throw new CusAddressException("接送货地址的crmId不允许为空！");
	}
	/*// 作废记录
	int flag = cusAddressDao.deleteCusAddressByCode(entity.getCrmId(),
		entity.getModifyUser());

	if (flag > 0) {// 作废成功
	    entity.setActive(FossConstants.ACTIVE);
	    entity.setId(UUIDUtils.getUUID());

	    return cusAddressDao.addCusAddress(entity);
	}*/
	return cusAddressDao.updateCusAddress(entity);

//	return FossConstants.FAILURE;
    }

    /**
     * <p>
     * 根据crmId,最后一次修改时间查询客户接送货地址是否存在
     * </p>.
     *
     * @param crmId 
     * @param lastupdatetime 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-11-26 上午10:27:27
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ICusAddressService#queryCusAddressByCrmId(java.math.BigDecimal,
     * java.util.Date)
     */
    @Override
    public boolean queryCusAddressByCrmId(BigDecimal crmId, Date lastupdatetime) {
	if(null == crmId){
	    throw new CusAddressException("接送货地址的crmId不允许为空！");
	}else {
	    LOGGER.debug("crmId: "+crmId + "lastupdatetime: " + lastupdatetime);
	    return cusAddressDao.queryCusAddressByCrmId(crmId, lastupdatetime);
	}
    }

    /**
	  * 新增散客接送货地址信息
	  *
	  * auther:wangpeng_078816
	  * date:2014-4-22
	  *
	  */
	@Override
	@Transactional
	public int addNonfixedCusAddress(CusAddressEntity entity) {
	if (null == entity) {
	    throw new CusAddressException("传入的参数不允许为空！");
	} else {
		entity.setId(UUIDUtils.getUUID());
		// 第一次记录新增时，虚拟编码为记录的id
		entity.setVirtualCode(entity.getId());
		cusAddressDao.addCusAddress(entity);
	    return FossConstants.SUCCESS;
	}}

    /**
     * <p>根据ownCustId查询客户接送货地址</p> 
     * @author css 
     * @date 2015-07-21 19:29:29
     * @param ownCustId    
     * @return
     * @see
     */
	@Override
	public CusAddressEntity queryCusAddressByOwnCustId(String ownCustId) {
		
		return cusAddressDao.queryCusAddressByOwnCustId(ownCustId);
	}

    /**
     * 修改客户接送货地址
     * @author css 
     * @date 2015-07-21 19:34:29
     * @param ownCustId    
     * @return
     * @see
     */
	@Override
	public int updateCusAddressByCusfossid(CusAddressEntity entity) {
		if (null == entity) {
		    return FossConstants.FAILURE;
		}
		return cusAddressDao.updateCusAddressByCusfossid(entity);
	}

}
