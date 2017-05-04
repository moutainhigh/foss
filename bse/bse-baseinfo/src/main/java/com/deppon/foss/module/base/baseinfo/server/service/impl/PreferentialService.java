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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/PreferentialService.java
 * 
 * FILE NAME        	: PreferentialService.java
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
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.foss.framework.cache.CacheManager;
import com.deppon.foss.framework.cache.ICache;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPreferentialDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPreferentialService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PreferentialEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PreferentialInfoDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.PreferentialException;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.common.FossTTLCache;
import com.deppon.foss.util.define.FossConstants;

/**
 * 客户优惠信息Service接口实现
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-11-26 上午10:40:03
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-11-26 上午10:40:03
 * @since
 * @version
 */
public class PreferentialService implements IPreferentialService {

    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(PreferentialService.class);

    /**
     * 客户优惠信息Dao接口.
     */
    private IPreferentialDao preferentialDao;
    
    /**
     * 设置 客户优惠信息Dao接口.
     * 
     * @param preferentialDao
     *            the new 客户优惠信息Dao接口
     */
    public void setPreferentialDao(IPreferentialDao preferentialDao) {
	this.preferentialDao = preferentialDao;
    }
    
    /**
     * <p>
     * 新增客户优惠信息
     * </p>
     * .
     * 
     * @param entity
     * @return
     * @throws PreferentialException
     * @author 094463-foss-xieyantao
     * @date 2012-11-26 上午10:40:03
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPreferentialService#addPreferential(com.deppon.foss.module.base.baseinfo.api.shared.domain.PreferentialEntity)
     */
    @Override
    public int addPreferential(PreferentialEntity entity)
	    throws PreferentialException {

	if (null == entity) {

	    return FossConstants.FAILURE;
	}
	if (null == entity.getCrmId()) {
	    throw new PreferentialException("客户优惠信息CRM_ID不允许为空！");
	}
	
	// 先验证在数据库是否存在
	boolean isFlag = preferentialDao.queryPreferentialByCrmId(entity.getCrmId(), null);

	if (isFlag) {// 存在就修改
	    preferentialDao.updatePreferential(entity);
	} else {
	    entity.setId(UUIDUtils.getUUID());
	    preferentialDao.addPreferential(entity);
	}
	
	return FossConstants.SUCCESS;
    }

    /**
     * <p>
     * 作废优惠信息
     * </p>
     * .
     * 
     * @param crmId
     * @param modifyUser
     * @return
     * @throws PreferentialException
     * @author 094463-foss-xieyantao
     * @date 2012-11-26 上午10:40:05
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPreferentialService#deletePreferentialByCode(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public int deletePreferentialByCode(BigDecimal crmId, String modifyUser)
	    throws PreferentialException {

	if (null == crmId) {
	    return FossConstants.FAILURE;
	}
	LOGGER.debug("crmId: " + crmId);
	return preferentialDao.deletePreferentialByCode(crmId, modifyUser);
    }

    /**
     * <p>
     * 修改客户优惠信息
     * </p>
     * .
     * 
     * @param entity
     * @return
     * @throws PreferentialException
     * @author 094463-foss-xieyantao
     * @date 2012-11-26 上午10:40:05
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPreferentialService#updatePreferential(com.deppon.foss.module.base.baseinfo.api.shared.domain.PreferentialEntity)
     */
    @Override
    public int updatePreferential(PreferentialEntity entity)
	    throws PreferentialException {

	if (null == entity) {
	    return FossConstants.FAILURE;
	}
	if (null == entity.getCrmId()) {
	    throw new PreferentialException("客户合同优惠信息CRM_ID不允许为空！");
	}
	/*// 作废记录
	int flag = preferentialDao.deletePreferentialByCode(entity.getCrmId(),
		entity.getModifyUser());

	if (flag > 0) {// 作废成功
	    entity.setActive(FossConstants.ACTIVE);
	    entity.setId(UUIDUtils.getUUID());

	    return preferentialDao.addPreferential(entity);
	}*/

	return preferentialDao.updatePreferential(entity);
    }

    /**
     * <p>
     * 根据crmId,最后一次修改时间查询客户优惠信息是否存在
     * </p>
     * .
     * 
     * @param crmId
     * @param lastupdatetime
     * @return
     * @author 094463-foss-xieyantao
     * @date 2012-11-26 上午10:40:05
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPreferentialService#queryPreferentialByCrmId(java.math.BigDecimal,
     *      java.util.Date)
     */
    @Override
    public boolean queryPreferentialByCrmId(BigDecimal crmId,
	    Date lastupdatetime) {

	return preferentialDao.queryPreferentialByCrmId(crmId, lastupdatetime);
    }

    /**
     * <p>
     * 根据客户编码、时间查询客户当前时间内的客户优惠信息
     * </p>
     * .
     * 
     * @param customerCode
     *            客户编码
     * @param date
     *            查询时间
     * @return
     * @author 094463-foss-xieyantao
     * @date 2012-12-20 上午10:20:09
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPreferentialService#queryPreferentialInfo(java.lang.String,
     *      java.util.Date)
     */
    @Override
    public PreferentialInfoDto queryPreferentialInfo(String customerCode,
			Date date, String productCode) {
    	//313353 sonar
		List<PreferentialInfoDto> list = this.sonarSplitCheck(customerCode);
		if(null == list){
			return null;
		}
		
		if (null == date) {
			date = new Date();
		}
		PreferentialInfoDto dto = null;
		for (PreferentialInfoDto infoDto : list) {
			if (null == infoDto) {
				continue;
			}
			// 获取合同开始时间
			Date beginTime = infoDto.getBeginTime();
			// 获取合同结束时间
			Date endTime = infoDto.getEndTime();
			// 获取 执行起始日期.
			Date effectiveDate = infoDto.getEffectiveDate();
			// 获取 执行到期日期.
			Date expirationDate = infoDto.getExpirationDate();
			if (null != effectiveDate && null != expirationDate
					&& null != beginTime && null != endTime
					&& beginTime.getTime() <= date.getTime()
					&& endTime.getTime() > date.getTime()
					&& effectiveDate.getTime() <= date.getTime()
					&& expirationDate.getTime() > date.getTime()) {

				// 根据传入的日期找出有效的合同

				// 快递逻辑：产品代码为Express且优惠类型Express
				if (PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20005
						.equals(productCode)
						&& StringUtils.isNotEmpty(infoDto.getFtype())) {
					if (productCode.equals(infoDto.getFtype())) {
						dto = infoDto;
						break;
					} else {
						continue;
					}
					// 零担逻辑：产品代码为Null且优惠类型为null或者LTT
				} else if (StringUtils.isEmpty(productCode)||(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20012
						.equals(productCode)
						&& StringUtils.isNotEmpty(infoDto.getFtype()))) {

					if (!PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20005
							.equals(infoDto.getFtype())) {
						dto = infoDto;
						break;
					} else {
						continue;
					}
				}

				// if(productCode!=null&&infoDto.getFtype()!=null){
				// if(productCode.equals(infoDto.getFtype())){
				// dto = infoDto;
				// break;
				// }
				// }else{
				//
				// if("EXPRESS".equals(productCode)
				// &&infoDto.getFtype()==null ){
				// continue;
				// }else{
				// dto = infoDto;
				// break;
				// }
				//
				// }

			}
			/*
			 * else if(null != beginTime && null != endTime){ // 根据传入的日期找出有效的合同
			 * if (beginTime.getTime() <= date.getTime() && endTime.getTime() >
			 * date.getTime()) { dto = infoDto; break; }
			 * 
			 * }
			 */

		}
		return dto;

	}
    
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private List<PreferentialInfoDto> sonarSplitCheck(String customerCode) {
		List<PreferentialInfoDto> list = null;
		if (StringUtil.isBlank(customerCode)) {
			return null;
		}
		
		list = queryPreferentialInfoDtos(customerCode);
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		return list;
	}

    /**
     * <p>
     * 根据客户编码查询客户优惠信息
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-2-22 下午5:35:26
     * @param customerCode
     * @return
     * @see
     */
    public List<PreferentialInfoDto> queryPreferentialInfoDtos(
	    String customerCode) {
	// 从缓存里面获取数据
	List<PreferentialInfoDto> list =null;
	//list= queryListCache(customerCode);

	if (CollectionUtils.isEmpty(list)) {
	    // 根据客户编码查询客户合同优惠信息
	    return preferentialDao
		    .queryPreferentialInfoDtosByCustCode(customerCode);
	}

	return list;
    }
    
    /**
     * <p>
     * 根据客户编码查询客户优惠信息
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-2-22 下午5:35:26
     * @param customerCode
     * @return
     * @see
     */
    public List<PreferentialInfoDto> queryPriceVersionInfoDtos(
	    String customerCode) {
	    // 根据客户编码查询客户合同优惠信息
	    return preferentialDao
		    .queryPriceVersionInfoDtosByCustCode(customerCode);
    }

    
    /**
     * <p>
     * 清除根据客户编码查询客户合同优惠信息缓存
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-2-22 下午5:03:17
     * @param key
     * @see
     */
    @SuppressWarnings("unchecked")
    public void invalidList(String key) {
	((ICache<String, List<PreferentialInfoDto>>) CacheManager.getInstance()
		.getCache(FossTTLCache.PREFERENTIALINFODTO_LIST_CACHE_UUID))
		.invalid(key);
    }

    // 取缓存中的数据
    /*@SuppressWarnings({ "unchecked", "rawtypes" })
    private List<PreferentialInfoDto> queryListCache(String key) {
	List<PreferentialInfoDto> resultList = new ArrayList<PreferentialInfoDto>();
	try {
	    CacheManager cacheManager = CacheManager.getInstance();
	    if (cacheManager == null) {
		return resultList;
	    }
	    ICache cache = cacheManager
		    .getCache(FossTTLCache.PREFERENTIALINFODTO_LIST_CACHE_UUID);
	    if (cache == null) {
		return resultList;
	    }
	    resultList = (List<PreferentialInfoDto>) cache.get(key);
	} catch (Exception t) {
	    LOGGER.error("cache找不到", t);
	}
	return resultList;
    }*/
    
    
    /**
     * <p>
     * 根据客户编码、时间查询客户当前时间内的客户优惠信息
     * </p>
     * .
     * 
     * @param customerCode
     *            客户编码
     * @param date
     *            查询时间
     * @return
     * @author 132599-foss-shenweihua
     * @date 2013-8-29 上午10:20:09
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPreferentialService#queryPreferentialInfo(java.lang.String,
     *      java.util.Date)
     */
	@Override
	public PreferentialInfoDto queryPriceVersionInfo(String customerCode,
			Date date) {
		if (StringUtil.isBlank(customerCode)) {
			return null;
		}
		if (null == date) {
			date = new Date();
		}
		PreferentialInfoDto dto = null;
		List<PreferentialInfoDto> list = queryPriceVersionInfoDtos(customerCode);
		if (CollectionUtils.isEmpty(list)) {
			return dto;
		}
		for (PreferentialInfoDto infoDto : list) {
			if (null != infoDto) {
				// 获取合同开始时间
				Date beginTime = infoDto.getBeginTime();
				// 获取合同结束时间
				Date endTime = infoDto.getEndTime();
				if (null != beginTime && null != endTime) {
					// 根据传入的日期找出有效的合同
					if (beginTime.getTime() <= date.getTime()
							&& endTime.getTime() > date.getTime()) {
						dto = infoDto;
						break;
					}
				}
			}
		}
		return dto;

	}
	@Override
	public List<PreferentialInfoDto> queryCusBargainByCodeAndTime(
			String customerCode, Date date) {
		// TODO Auto-generated method stub
		
		return preferentialDao.queryCusBargainByCodeAndTime(customerCode,date);
	}

}
