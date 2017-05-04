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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/CusAccountService.java
 * 
 * FILE NAME        	: CusAccountService.java
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
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ICusAccountDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusAccountService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.CusAccountException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 客户开户银行Service接口实现
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-11-20 下午6:28:11 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-11-20 下午6:28:11
 * @since
 * @version
 */
public class CusAccountService implements ICusAccountService {
    
    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CusAccountService.class);
    
    /**
     * 客户开户银行DAO接口.
     */
    private ICusAccountDao cusAccountDao;
    
    /**
     * 设置 客户开户银行DAO接口.
     *
     * @param cusAccountDao the new 客户开户银行DAO接口
     */
    public void setCusAccountDao(ICusAccountDao cusAccountDao) {
        this.cusAccountDao = cusAccountDao;
    }
    
    /**
     * 新增客户开户银行.
     *
     * @param entity 客户开户银行实体
     * @return 1：成功；-1：失败
     * @throws CusAccountException 
     * @author 094463-foss-xieyantao
     * @date 2012-11-20 上午9:33:05
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ICusAccountService#addCusAccount(com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity)
     */
    @Transactional
    @Override
    public int addCusAccount(CusAccountEntity entity) throws CusAccountException{

	if (null == entity) {

	    return FossConstants.FAILURE;
	}
	if(null == entity.getCrmId()){
	    throw new CusAccountException("客户银行账号ID不允许为空！");
	}
	
	//先验证该银行账号在数据库是否存在
	boolean isFlag = cusAccountDao.queryCusAccountByCrmId(entity.getCrmId(),null);
	
	if(isFlag){//存在就修改
	    cusAccountDao.updateCusAccount(entity);
	}else {
	    entity.setId(UUIDUtils.getUUID());
	    // 第一次记录新增时，虚拟编码为记录的id
	    entity.setVirtualCode(entity.getId());

	    cusAccountDao.addCusAccount(entity);
	}
	
	return FossConstants.SUCCESS;
    }

    /**
     * 根据CRMID作废客户开户银行.
     *
     * @param crmId 
     * @param modifyUser 
     * @return 1：成功；-1：失败
     * @throws CusAccountException 
     * @author dp-xieyantao
     * @date 2012-11-20 上午9:33:05
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ICusAccountService#deleteCusAccountByCode(java.lang.String, java.lang.String)
     */
    @Transactional
    @Override
    public int deleteCusAccountByCode(BigDecimal crmId, String modifyUser)  throws CusAccountException{
	
	if(null == crmId){
	    throw new CusAccountException("开户银行的crmId不允许为空");
	}else {
	    LOGGER.debug("crmId: "+crmId);
	    return cusAccountDao.deleteCusAccountByCode(crmId, modifyUser); 
	}
	
    }

    /**
     * 修改客户开户银行
     * 对于有版本控制的编辑，先逻辑删除该记录，再新增一条记录.
     *
     * @param entity 客户开户银行实体
     * @return 1：成功；-1：失败
     * @throws CusAccountException 
     * @author dp-xieyantao
     * @date 2012-11-20 上午9:33:05
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ICusAccountService#updateCusAccount(com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity)
     */
    @Transactional
    @Override
    public int updateCusAccount(CusAccountEntity entity) throws CusAccountException{
	
	if(null == entity){
	    return FossConstants.FAILURE;
	}
	if(null == entity.getCrmId()){
	    throw new CusAccountException("客户银行账号ID不允许为空！");
	}
	/*//作废记录
	int flag = cusAccountDao.deleteCusAccountByCode(entity.getCrmId(), entity.getModifyUser());
	
	if(flag > 0){//作废成功
	    entity.setActive(FossConstants.ACTIVE);
	    entity.setId(UUIDUtils.getUUID());
	    
	    return cusAccountDao.addCusAccount(entity);
	}*/
	
	return cusAccountDao.updateCusAccount(entity);
//	return FossConstants.FAILURE;
    }
    
    /**
     * <p>根据crmId,最后一次修改时间查询客户开户银行是否存在</p>.
     *
     * @param crmId 
     * @param lastupdatetime 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-11-26 下午2:26:23
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ICusAccountService#queryCusAccountByCrmId(java.math.BigDecimal, java.util.Date)
     */
    @Override
    public boolean queryCusAccountByCrmId(BigDecimal crmId, Date lastupdatetime) {
	
	return cusAccountDao.queryCusAccountByCrmId(crmId, lastupdatetime);
    }
    
    /**
     * <p>
     * 根据客户编码查询时间最近的客户银行账户
     * </p>.
     *
     * @param custCode 客户编码
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-5-2 上午9:15:40
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ICusAccountService#queryAccountInfoByCustCode(java.lang.String)
     */
    @Override
    public List<CusAccountEntity> queryAccountInfoByCustCode(String custCode) {
	if(StringUtils.isBlank(custCode)){
	    return null;
	}else {
	    return cusAccountDao.queryAccountInfoByCustCode(custCode);
	}
    }

    /**
     * 根据客户编码查询有效的银行账户信息
     *
     * auther:wangpeng_078816
     * date:2014-4-30
     *
     */
	@Override
	public List<CusAccountEntity> queryAccountLatestNewInfoByCustCode(
			String custCode) {
		if(StringUtils.isBlank(custCode)){
		    return null;
		}else {
		    return cusAccountDao.queryAccountInfoByCustCode(custCode);
		}
	}

}
