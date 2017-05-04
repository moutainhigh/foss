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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/SpecialAddressService.java
 * 
 * FILE NAME        	: SpecialAddressService.java
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
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISpecialAddressDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISpecialAddressService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SpecialAddressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SpecialAddresstDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.SpecialAddressException;
import com.deppon.foss.util.define.FossConstants;
/**
 * 
 * 特殊地址服务操作类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:何波,date:2013-2-20 下午2:21:55 </p>
 * @author 何波
 * @date 2013-2-20 下午2:21:55
 * @since
 * @version
 */
public class SpecialAddressService implements ISpecialAddressService {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SpecialAddresstDto.class);
	private ISpecialAddressDao specialAddressDao;
	private IAdministrativeRegionsService administrativeRegionsService;

	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}
	
	public void setSpecialAddressDao(ISpecialAddressDao specialAddressDao) {
		this.specialAddressDao = specialAddressDao;
	}


	/**
	 * 
	 * <p>新增特殊地址</p> 
	 * @author 何波
	 * @date 2013-1-31 下午3:26:10
	 * @param specialAddressDao
	 * @see
	 */
	@Override
	public int addSpecialAddress(SpecialAddressEntity specialAddress) throws SpecialAddressException {
		
		if (specialAddress == null) {

			return FossConstants.FAILURE;
		}
		SpecialAddressEntity sa= new  SpecialAddressEntity();
		//去掉特殊地址电话和描述的验证
		sa.setType(specialAddress.getType());
		sa.setNationCode(specialAddress.getNationCode());
		sa.setProvinceCode(specialAddress.getProvinceCode());
		sa.setCityCode(specialAddress.getCityCode());
		sa.setCountyCode(specialAddress.getCountyCode());
		sa.setAddress(specialAddress.getAddress());
		List<SpecialAddressEntity> specialAddressList =  specialAddressDao.querySpecialAddress(sa);
		if (!CollectionUtils.isEmpty(specialAddressList)) {
			LOGGER.warn("特殊地址信息已存在！");
		   throw new SpecialAddressException("特殊地址信息已存在！");
		   
		}
		specialAddressDao.addSpecialAddress(specialAddress);
		return FossConstants.SUCCESS;
	}

	/**
	 * 
	 * <p>作废特殊地址</p> 
	 * @author 何波
	 * @date 2013-1-31 下午3:26:58
	 * @param id
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISpecialAddressService#deleteSpecialAddress(java.lang.String)
	 */
	@Override
	public int deleteSpecialAddress(List<String> ids, String modifyUser) throws SpecialAddressException{
		if(CollectionUtils.isEmpty(ids)){
			return FossConstants.FAILURE;
		}
		for (String id : ids) {
			SpecialAddressEntity specialAddress = querySpecialAddressBySelective(id);
			if(specialAddress == null){
				LOGGER.warn("特殊地址信息不存在");
				throw new SpecialAddressException("特殊地址信息不存在");
			}
			specialAddress.setActive(FossConstants.INACTIVE);
			specialAddress.setModifyUser(modifyUser);
			specialAddressDao.deleteSpecialAddress(specialAddress);
		}
		
		return FossConstants.SUCCESS;
	}


	/**
	 * 
	 *<p>修改特殊地址</p> 
	 * @author 何波
	 * @date 2013-1-31 下午3:27:56
	 * @param specialAddress
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISpecialAddressService#updateSpecialAddress(com.deppon.foss.module.base.baseinfo.api.shared.domain.SpecialAddressEntity)
	 */
	@Override
	public int updateSpecialAddress(SpecialAddressEntity specialAddress) throws SpecialAddressException{

		if (specialAddress == null) {

			return FossConstants.FAILURE;
		}
		specialAddressDao.updateSpecialAddress(specialAddress);
		return FossConstants.SUCCESS;
	}

	/**
	 * 
	 * <p>查询特殊地址</p> 
	 * @author 何波
	 * @date 2013-1-31 下午3:28:10
	 * @param specialAddress
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISpecialAddressService#querySpecialAddress(com.deppon.foss.module.base.baseinfo.api.shared.domain.SpecialAddressEntity)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<SpecialAddressEntity> querySpecialAddress(
			SpecialAddressEntity specialAddress) throws SpecialAddressException {
		if (specialAddress == null) {
			return null;
		}
		List<SpecialAddressEntity> specialAddressList = specialAddressDao
				.querySpecialAddress(specialAddress);
		
		return specialAddressList;
	}


	/**
	 * 
	 * <p>查询一条有效的特殊地址</p> 
	 * @author 何波
	 * @date 2013-2-18 下午5:34:46
	 * @param id
	 * @return
	 * @see
	 */
	@Override
	@Transactional(readOnly = true)
	public SpecialAddressEntity querySpecialAddressBySelective(String id) {
		
		SpecialAddressEntity specialAddress = specialAddressDao.querySpecialAddressBySelective(id);

		return specialAddress;
	}
	
	/**
	 * 
	 * <p>将特殊地址信息数据复制到DTO中</p> 
	 * @author 何波
	 * @date 2013-2-20 下午2:02:16
	 * @param sa
	 * @param offset
	 * @param limit
	 * @return
	 * @throws SpecialAddressException 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISpecialAddressService#querySpecialAddressDtoListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.SpecialAddressEntity, int, int)
	 */
	@Override
	@SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
	public PaginationDto querySpecialAddressDtoListBySelectiveCondition(
			SpecialAddressEntity sa, int offset, int limit)
			throws SpecialAddressException {
		//获取数据
		PaginationDto paginationDto = querySpecialAddressListBySelectiveCondition(sa, offset, limit);
		if(null != paginationDto){
		    List<SpecialAddressEntity> specialAddresstList = paginationDto.getPaginationDtos();
		    if(CollectionUtils.isNotEmpty(specialAddresstList)){
			
			//初始化准备数据
			List<SpecialAddresstDto> flightDtos = new ArrayList<SpecialAddresstDto>(specialAddresstList.size());
			SpecialAddresstDto flightDto = null;
			//执行数据获取
				for (SpecialAddressEntity specialAddress : specialAddresstList) {
					if (null != specialAddress) {
						try {
							
							flightDto = new SpecialAddresstDto(specialAddress);

						} catch (SpecialAddressException e) {
							LOGGER.warn("特殊地址信息Entity数据复制到Dto失败");
							throw new SpecialAddressException(
									"特殊地址信息Entity数据复制到Dto失败");
						}
						//通过国家省市区县编查询出对应的名称
						String nationCode = specialAddress.getNationCode();
						String nationName = administrativeRegionsService
								.queryAdministrativeRegionsNameByCode(nationCode);
						flightDto.setNationName(nationName);
						String provCode = specialAddress.getProvinceCode();
						String provName = administrativeRegionsService
								.queryAdministrativeRegionsNameByCode(provCode);
						flightDto.setProvinceName(provName);
						String cityCode = specialAddress.getCityCode();
						String cityName = administrativeRegionsService
								.queryAdministrativeRegionsNameByCode(cityCode);
						flightDto.setCityName(cityName);
						String areaCode = specialAddress.getCountyCode();
						String areaName = administrativeRegionsService
								.queryAdministrativeRegionsNameByCode(areaCode);
						flightDto.setCountyName(areaName);

					}
					if (null != flightDto) {
						flightDtos.add(flightDto);
					}
				}
				paginationDto.setPaginationDtos(flightDtos);
			}
		}
	
		return paginationDto;
	}
	
	/**
	 * 
	 * <p>将查询出的数据放入DTO中</p> 
	 * @author 何波
	 * @date 2013-2-20 下午2:00:23
	 * @param sa
	 * @param offset
	 * @param limit
	 * @return
	 * @throws SpecialAddressException 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISpecialAddressService#querySpecialAddressListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.SpecialAddressEntity, int, int)
	 */
	@Override
	@Transactional(readOnly = true)
	public PaginationDto querySpecialAddressListBySelectiveCondition(
			SpecialAddressEntity sa, int offset, int limit)
			throws SpecialAddressException {
		PaginationDto paginationDto = new PaginationDto();
		if (offset < 0 || limit < 0) {
		    return paginationDto;
		}
		if(null == sa){
		    sa = new SpecialAddressEntity();
		}
		List<SpecialAddressEntity> specialAddressList = specialAddressDao.querySpecialAddressListBySelectiveCondition(sa, offset, limit);
		if (CollectionUtils.isNotEmpty(specialAddressList)) {
		   //查询多的总条数
		   Long totalCount = specialAddressDao.querySpecialAddressCountBySelectiveCondition(sa);
		   paginationDto.setPaginationDtos(specialAddressList);
		   paginationDto.setTotalCount(totalCount);
		}
		
		return paginationDto;
	}
	
	

}
