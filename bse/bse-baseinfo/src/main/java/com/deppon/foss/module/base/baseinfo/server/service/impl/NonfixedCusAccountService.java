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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/NonfixedCusAccountService.java
 * 
 * FILE NAME        	: NonfixedCusAccountService.java
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
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.INonfixedCusAccountDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusAccountService;
import com.deppon.foss.module.base.baseinfo.api.server.service.INonfixedCusAccountService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NonfixedCusAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.CusAccountException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 临欠散客开户银行账户Service接口实现
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-11-20 下午6:28:11
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-11-20 下午6:28:11
 * @since
 * @version
 */
public class NonfixedCusAccountService implements INonfixedCusAccountService {

    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(NonfixedCusAccountService.class);

    /**
     * 临欠散客开户银行账户DAO接口.
     */
    private INonfixedCusAccountDao nonfixedCusAccountDao;

    /**
     * 设置 临欠散客开户银行账户DAO接口.
     * 
     * @param nonfixedCusAccountDao
     *            the new 临欠散客开户银行账户DAO接口
     */
    public void setNonfixedCusAccountDao(
	    INonfixedCusAccountDao nonfixedCusAccountDao) {
	this.nonfixedCusAccountDao = nonfixedCusAccountDao;
    }
    

	//wp_078816_crm2_20140430
    //固定客户银行账户接口
    private ICusAccountService cusAccountService;
    
    /**
     * @param cusAccountService the cusAccountService to set
     */
    public void setCusAccountService(ICusAccountService cusAccountService) {
    	this.cusAccountService = cusAccountService;
    }

    /**
     * 新增临欠散客开户银行账户.
     * 
     * @param entity
     *            临欠散客开户银行账户实体
     * @return 1：成功；-1：失败
     * @throws CusAccountException
     * @author 094463-foss-xieyantao
     * @date 2012-11-20 上午9:33:05
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ICusAccountService#addCusAccount(com.deppon.foss.module.base.baseinfo.api.shared.domain.NonfixedCustomerEntity)
     */
    @Transactional
    @Override
    public int addCusAccount(NonfixedCusAccountEntity entity)
	    throws CusAccountException {

	if (null == entity) {

	    return FossConstants.FAILURE;
	}
	entity.setId(UUIDUtils.getUUID());

	return nonfixedCusAccountDao.addCusAccount(entity);
    }

    /**
     * 根据开户账号ID作废临欠散客开户银行账户.
     * 
     * @param crmId
     * @param modifyUser
     * @return 1：成功；-1：失败
     * @throws CusAccountException
     * @author dp-xieyantao
     * @date 2012-11-20 上午9:33:05
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ICusAccountService#deleteCusAccountByCode(java.lang.String,
     *      java.lang.String)
     */
    @Transactional
    @Override
    public int deleteCusAccountByCode(BigInteger crmId, String modifyUser)
	    throws CusAccountException {

	if (null == crmId) {
	    throw new CusAccountException("临欠散客开户银行账户ID不允许为空！");
	} else {
	    LOGGER.debug("crmId: " + crmId);
	    return nonfixedCusAccountDao.deleteCusAccountByCode(crmId,
		    modifyUser);
	}
    }

    /**
     * 修改临欠散客开户银行账户 对于有版本控制的编辑，先逻辑删除该记录，再新增一条记录.
     * 
     * @param entity
     *            临欠散客开户银行账户实体
     * @return 1：成功；-1：失败
     * @throws CusAccountException
     * @author dp-xieyantao
     * @date 2012-11-20 上午9:33:05
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ICusAccountService#updateCusAccount(com.deppon.foss.module.base.baseinfo.api.shared.domain.NonfixedCustomerEntity)
     */
    @Transactional
    @Override
    public int updateCusAccount(NonfixedCusAccountEntity entity)
	    throws CusAccountException {

	if (null == entity) {
	    return FossConstants.FAILURE;
	}
	if (null == entity.getCrmId()) {
	    return FossConstants.FAILURE;
	}
	//根据crmId判断临欠散客账号在数据库是否存在
	NonfixedCusAccountEntity cusAccount = nonfixedCusAccountDao.queryCusAccountByCrmId(entity.getCrmId());
	if(null != cusAccount){
	   /* // 如果存在该记录，先作废记录
	    int flag = nonfixedCusAccountDao.deleteCusAccountByCode(
		    entity.getCrmId(), entity.getModifyUser());
	    //作废成功再新增记录
	    if (flag > 0) {// 作废成功
		entity.setId(UUIDUtils.getUUID());

		return nonfixedCusAccountDao.addCusAccount(entity);
	    }*/
	    //修改临欠散客开户银行账户
	    return nonfixedCusAccountDao.updateCusAccount(entity);
	}else {
	    //如果该记录在数据库不存在，则新增一条记录
	    entity.setId(UUIDUtils.getUUID());

	    return nonfixedCusAccountDao.addCusAccount(entity);
	}
    }

    /**
     * <p>
     * 根据crmId,最后一次修改时间查询临欠散客开户银行账户是否存在
     * </p>
     * .
     * 
     * @param crmId
     * @param lastupdatetime
     * @return
     * @author 094463-foss-xieyantao
     * @date 2012-11-26 下午2:26:23
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ICusAccountService#queryCusAccountByCrmId(java.math.BigDecimal,
     *      java.util.Date)
     */
    @Override
    public boolean queryCusAccountByCrmId(BigDecimal crmId, Date lastupdatetime) {
	if (null == crmId) {
	    throw new CusAccountException("临欠散客开户银行账户ID不允许为空！");
	} else if (null == lastupdatetime) {
	    throw new CusAccountException("最后一次修改时间不允许为空！");
	} else {
		//wp_078816_crm2_20140430
	    return cusAccountService.queryCusAccountByCrmId(crmId, lastupdatetime);
	}
    }

    /**
     * <p>
     * 根据临欠散客的客户编码查询散客的银行账号
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-2-21 上午9:17:18
     * @param custCode
     *            临欠散客客户编码
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.INonfixedCusAccountService#queryCusAccountByCustCode(java.lang.String)
     */
    @Override
    public List<NonfixedCusAccountEntity> queryCusAccountByCustCode(
	    String custCode) {
	if (StringUtil.isBlank(custCode)) {
	    throw new CusAccountException("临欠散客客户编码不允许为空！");
	}
	//wp_078816_crm2_20140430
	//查询编码查询账户信息
	List<CusAccountEntity> cusAccList = cusAccountService.queryAccountLatestNewInfoByCustCode(custCode);
	//散客账户列表
	List<NonfixedCusAccountEntity>  nonAccList = new ArrayList<NonfixedCusAccountEntity>();
	
	for (CusAccountEntity cusAccountEntity : cusAccList) {
		NonfixedCusAccountEntity entity = this.transformCustomerAccountToNonfixedCustAccount(cusAccountEntity);
		if(null == entity){
			continue;
		}
		nonAccList.add(entity);
	}
	  return nonAccList;
    }
    
    /**
     * <p>
     * 根据客户编码查询时间最近的客户银行账户
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-5-2 下午5:02:13
     * @param custCode
     *            客户编码
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.INonfixedCusAccountService#queryAccountInfoByCustCode(java.lang.String)
     */
    @Override
    public List<NonfixedCusAccountEntity> queryAccountInfoByCustCode(
	    String custCode) {
	if(StringUtil.isBlank(custCode)){
	   return null; 
	}
	//wp_078816_crm2_20140430
		//查询编码查询账户信息
		List<CusAccountEntity> cusAccList = cusAccountService.queryAccountInfoByCustCode(custCode);
		//散客账户列表
		List<NonfixedCusAccountEntity>  nonAccList = new ArrayList<NonfixedCusAccountEntity>();
		
		for (CusAccountEntity cusAccountEntity : cusAccList) {
			NonfixedCusAccountEntity entity = this.transformCustomerAccountToNonfixedCustAccount(cusAccountEntity);
			if(null == entity){
				continue;
			}
			nonAccList.add(entity);
		}
		  return nonAccList;
    }

    /**
     * 固定客户账户转化为散客账户
     *
     * auther:wangpeng_078816
     * date:2014-4-30
     *
     */
    private NonfixedCusAccountEntity transformCustomerAccountToNonfixedCustAccount(CusAccountEntity entity){
    	NonfixedCusAccountEntity nonEntity = new NonfixedCusAccountEntity();
    	if(null == entity){
    		
    		return null;
    	}
		nonEntity.setAccountName(entity.getAccountName());
		nonEntity.setAccountNature(entity.getAccountNature());
		nonEntity.setAccountNo(entity.getAccountNo());
		nonEntity.setBankCode(entity.getBankCode());
		nonEntity.setBranchBankCode(entity.getBranchBankCode());
		nonEntity.setBranchBankName(entity.getBranchBankName());
		nonEntity.setCityCode(entity.getCityCode());
		nonEntity.setCityName(entity.getCityName());
		nonEntity.setProvCode(entity.getProvCode());
		nonEntity.setProvinceName(entity.getProvinceName());
		nonEntity.setOpeningBankName(entity.getOpeningBankName());
		nonEntity.setOtherBranchBankName(entity.getOtherBranchBankName());
		if(null != entity.getCrmId()){
			
			nonEntity.setCrmId(entity.getCrmId().toBigInteger());
		}
		nonEntity.setCustomer(entity.getCustomer());
		nonEntity.setDefaultAccount(entity.getDefaultAccount());
		nonEntity.setId(entity.getId());
		nonEntity.setMobilePhone(entity.getMobilePhone());
		if(null != entity.getCustomerDto()){
			if(null != entity.getCustomerDto().getCrmCusId()){
				
				nonEntity.setNoncustCrmId(entity.getCustomerDto().getCrmCusId().toBigInteger());
			}
		}
		nonEntity.setNotes(entity.getNotes());
		nonEntity.setCreateDate(entity.getCreateDate());
		nonEntity.setCreateUser(entity.getCreateUser());
		nonEntity.setModifyUser(entity.getModifyUser());
		nonEntity.setModifyDate(entity.getCreateDate());
		
		return nonEntity;
    	
    }

}
