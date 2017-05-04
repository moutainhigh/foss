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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/ExhibitionKeywordService.java
 * 
 * FILE NAME        	: ExhibitionKeywordService.java
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
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IExhibitionKeywordDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExhibitionKeywordService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExhibitionKeywordEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExhibitionKeywordDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ExhibitionKeywordException;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.define.FossConstants;
/**
 * 
 * 展馆关键字信息服务操作类Sercive
 * @author: 189284
 * @date:2014-12-26 上午9:36:41
 * @since
 * @version
 */
public class ExhibitionKeywordService implements IExhibitionKeywordService {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ExhibitionKeywordDto.class);
	private IExhibitionKeywordDao exhibitionKeywordDao;
	private IAdministrativeRegionsService administrativeRegionsService;

	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}
	
	public void setExhibitionKeywordDao(IExhibitionKeywordDao exhibitionKeywordDao) {
		this.exhibitionKeywordDao = exhibitionKeywordDao;
	}
	/**
	 * 
	 * @Description:<p> 获取当前用户</p>
	 * @author: 189284
	 * @time:2014-12-26 上午9:36:41
	 *@see
	 */
	private UserEntity getCurrentUser() {
		UserEntity user = FossUserContext.getCurrentUser();
		return null == user ? new UserEntity() : user;
	}
	/**
	 * 
	 * <p>新增展馆关键字信息</p> 
     * @author: 189284
	 * @time:2014-12-26 上午9:36:41
	 * @return
	 * @see
	 */
   /**
    * 
   * @Title: addExhibitionKeyword 
   * @Description: <p>新增展馆关键字信息</p>
   * @param exhibitionKeyword
   * @return
   * @throws ExhibitionKeywordException
   * @see com.deppon.foss.module.base.baseinfo.api.shared.domain.ExhibitionKeywordEntity
   * @author 189284 ZhangXu
   * @date 2014-12-27 上午11:22:28
    */
	@Override
	public int addExhibitionKeyword(ExhibitionKeywordEntity exhibitionKeyword) throws ExhibitionKeywordException {
		
		if (exhibitionKeyword == null) {
		  throw new ExhibitionKeywordException(ExhibitionKeywordException.EXHIBITIONKEYWORD_PARAMS_ERROR);
		}
		//货物类型必填
		if(StringUtils.isBlank(exhibitionKeyword.getType())){
			throw new ExhibitionKeywordException(ExhibitionKeywordException.EXHIBITIONKEYWORD_PARAMS_ERROR);	
		}
		//展馆关键字和地址必须填写一个
		if(StringUtils.isBlank(exhibitionKeyword.getExhibitionKeyword())
				&&StringUtils.isBlank(exhibitionKeyword.getExhibitionAddress())){
			throw new ExhibitionKeywordException(ExhibitionKeywordException.EXHIBITION_KEYWORD_ADDRESS);	
		}
		List<ExhibitionKeywordEntity> exhibitionKeywordList =  exhibitionKeywordDao.queryExhibitionKeyword(exhibitionKeyword);
		if (!CollectionUtils.isEmpty(exhibitionKeywordList)) {
			LOGGER.warn(exhibitionKeywordList.get(0).getExhibitionKeyword()+"展馆信息已经存在了!");
		   throw new ExhibitionKeywordException(ExhibitionKeywordException.EXHIBITIONKEYWORD_DUPLICATE);
		   
		}
		/**
		 * 获取 登陆用户信息（员工工号）
		 */
		String createUser = getCurrentUser().getEmployee().getEmpCode();
		exhibitionKeyword.setCreateUser(createUser);
		exhibitionKeywordDao.addExhibitionKeyword(exhibitionKeyword);
		return FossConstants.SUCCESS;
	}

	/**
	 * 
	* @Title: deleteExhibitionKeyword 
	* @Description:作废 展馆关键字信息  
	* @param ids
	* @param modifyUser
	* @return
	* @throws ExhibitionKeywordException
	* @see com.deppon.foss.module.base.baseinfo.api.server.service.IExhibitionKeywordService#deleteExhibitionKeyword(java.util.List, java.lang.String)    返回类型 
	* @author 189284 ZhangXu
	* @date 2014-12-29 下午5:21:20
	 */
	@Override
	public int deleteExhibitionKeyword(List<String> ids, String modifyUser) throws ExhibitionKeywordException{
		if(CollectionUtils.isEmpty(ids)){
			return FossConstants.FAILURE;
		}
		for (String id : ids) {
			ExhibitionKeywordEntity exhibitionKeyword = queryExhibitionKeywordBySelective(id);
			if(exhibitionKeyword == null){
				LOGGER.warn("展馆关键字信息不存在");
				throw new ExhibitionKeywordException("展馆关键字信息不存在");
			}
			exhibitionKeyword.setActive(FossConstants.INACTIVE);
			exhibitionKeyword.setModifyUser(modifyUser);
			exhibitionKeywordDao.deleteExhibitionKeyword(exhibitionKeyword);
		}
		
		return FossConstants.SUCCESS;
	}


	/**
	 * 
	* @Title: updateExhibitionKeyword 
	* @Description: 修改 展馆关键字信息  
	* @param ExhibitionKeyword
	* @return
	* @throws ExhibitionKeywordException
	* @see com.deppon.foss.module.base.baseinfo.api.server.service.IExhibitionKeywordService#updateExhibitionKeyword(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExhibitionKeywordEntity)    返回类型 
	* @author 189284 ZhangXu
	* @date 2014-12-29 下午5:21:35
	 */
	@Override
	public int updateExhibitionKeyword(ExhibitionKeywordEntity exhibitionKeyword) throws ExhibitionKeywordException{

		if (exhibitionKeyword == null) {

			return FossConstants.FAILURE;
		}
		//货物类型不能为空
		if(StringUtils.isBlank(exhibitionKeyword.getType())){
			throw new ExhibitionKeywordException(ExhibitionKeywordException.EXHIBITIONKEYWORD_PARAMS_ERROR);	
		}
		//展馆关键字和地址必须填写一个
		if(StringUtils.isBlank(exhibitionKeyword.getExhibitionKeyword())
				&&StringUtils.isBlank(exhibitionKeyword.getExhibitionAddress())){
			throw new ExhibitionKeywordException(ExhibitionKeywordException.EXHIBITION_KEYWORD_ADDRESS);	
		}
		//验证重复，排除当前Id的数据
		List<ExhibitionKeywordEntity> exhibitionKeywordList =  exhibitionKeywordDao.queryExhibitionKeywordNotId(exhibitionKeyword);
		if (!CollectionUtils.isEmpty(exhibitionKeywordList)) {
			LOGGER.warn(exhibitionKeywordList.get(0).getExhibitionKeyword()+"展馆信息已经存在了!");
		   throw new ExhibitionKeywordException(ExhibitionKeywordException.EXHIBITIONKEYWORD_DUPLICATE);
		   
		}
		/**
		 * 获取 登陆用户信息（员工工号）
		 */
		String createUser = getCurrentUser().getEmployee().getEmpCode();
		exhibitionKeyword.setCreateUser(createUser);
		exhibitionKeywordDao.updateExhibitionKeyword(exhibitionKeyword);
		return FossConstants.SUCCESS;
	}

	/**
	 * 
	* @Title: queryExhibitionKeyword 
	* @Description: 查询 展馆关键字信息  
	* @param ExhibitionKeyword
	* @return
	* @throws ExhibitionKeywordException
	* @see com.deppon.foss.module.base.baseinfo.api.server.service.IExhibitionKeywordService#queryExhibitionKeyword(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExhibitionKeywordEntity)    返回类型 
	* @author 189284 ZhangXu
	* @date 2014-12-29 下午5:21:46
	 */
	@Override
	@Transactional(readOnly = true)
	public List<ExhibitionKeywordEntity> queryExhibitionKeyword(
			ExhibitionKeywordEntity exhibitionKeyword) throws ExhibitionKeywordException {
		if (exhibitionKeyword == null) {
			return null;
		}
		List<ExhibitionKeywordEntity> exhibitionKeywordList = exhibitionKeywordDao
				.queryExhibitionKeyword(exhibitionKeyword);
		
		return exhibitionKeywordList;
	}


	/**
	 * 
	* @Title: queryExhibitionKeywordBySelective 
	* @Description: 查询 展馆关键字信息  
	* @param id
	* @return
	* @see com.deppon.foss.module.base.baseinfo.api.server.service.IExhibitionKeywordService#queryExhibitionKeywordBySelective(java.lang.String)    返回类型 
	* @author 189284 ZhangXu
	* @date 2014-12-29 下午5:22:26
	 */
	@Override
	@Transactional(readOnly = true)
	public ExhibitionKeywordEntity queryExhibitionKeywordBySelective(String id) {
		
		ExhibitionKeywordEntity exhibitionKeyword = exhibitionKeywordDao.queryExhibitionKeywordBySelective(id);

		return exhibitionKeyword;
	}
	
	/**
	 * 
	* @Title: queryExhibitionKeywordDtoListBySelectiveCondition 
	* @Description: 分页 查询 展馆关键字信息  
	* @param  sa
	* @param  offset
	* @param  limit
	*  @throws ExhibitionKeywordException 
	*  
	* @return PaginationDto    返回类型 
	* @author 189284 ZhangXu
	* @date 2014-12-27 下午2:35:38 
	* @throws
	 */
	@Override
	@SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
	public PaginationDto queryExhibitionKeywordDtoListBySelectiveCondition(
			ExhibitionKeywordEntity sa, int offset, int limit)
			throws ExhibitionKeywordException {
		//获取数据
		PaginationDto paginationDto = queryExhibitionKeywordListBySelectiveCondition(sa, offset, limit);
		if(null != paginationDto){
		    List<ExhibitionKeywordEntity> exhibitionKeywordtList = paginationDto.getPaginationDtos();
		    if(CollectionUtils.isNotEmpty(exhibitionKeywordtList)){
			
			//初始化准备数据
			List<ExhibitionKeywordDto> flightDtos = new ArrayList<ExhibitionKeywordDto>(exhibitionKeywordtList.size());
			ExhibitionKeywordDto flightDto = null;
			//执行数据获取
				for (ExhibitionKeywordEntity exhibitionKeyword : exhibitionKeywordtList) {
					if (null != exhibitionKeyword) {
						try {
							
							flightDto = new ExhibitionKeywordDto(exhibitionKeyword);

						} catch (ExhibitionKeywordException e) {
							LOGGER.warn("展馆关键字信息Entity数据复制到Dto失败");
							throw new ExhibitionKeywordException(
									"展馆关键字信息Entity数据复制到Dto失败");
						}
						//通过国家省市区县编查询出对应的名称
						//String nationCode = ExhibitionKeyword.getNationCode();
						//String nationName = administrativeRegionsService
								//.queryAdministrativeRegionsNameByCode(nationCode);
						//flightDto.setNationName(nationName);
						String provCode = exhibitionKeyword.getProvinceCode();
						String provName = administrativeRegionsService
								.queryAdministrativeRegionsNameByCode(provCode);
						flightDto.setProvinceName(provName);
						String cityCode = exhibitionKeyword.getCityCode();
						String cityName = administrativeRegionsService
								.queryAdministrativeRegionsNameByCode(cityCode);
						flightDto.setCityName(cityName);
						String areaCode = exhibitionKeyword.getCountyCode();
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
	* @Title: queryExhibitionKeywordListBySelectiveCondition 
	* @Description: 查询  展馆关键字信息  
	* @param sa
	* @param offset
	* @param limit
	* @return
	* @throws ExhibitionKeywordException
	* @see com.deppon.foss.module.base.baseinfo.api.server.service.IExhibitionKeywordService#queryExhibitionKeywordListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExhibitionKeywordEntity, int, int)    返回类型 
	* @author 189284 ZhangXu
	* @date 2014-12-29 下午5:22:56
	 */
	@Override
	@Transactional(readOnly = true)
	public PaginationDto queryExhibitionKeywordListBySelectiveCondition(
			ExhibitionKeywordEntity sa, int offset, int limit)
			throws ExhibitionKeywordException {
		PaginationDto paginationDto = new PaginationDto();
		if (offset < 0 || limit < 0) {
		    return paginationDto;
		}
		/**
		 * 参数错误
		 */
		if(null == sa){
			throw new ExhibitionKeywordException( ExhibitionKeywordException .EXHIBITIONKEYWORD_PARAMS_ERROR); 
		}
		/**
		 * 货物类型 为空
		 */
		if(null != sa && StringUtils.isBlank(sa.getType())){
			throw new ExhibitionKeywordException( ExhibitionKeywordException .EXHIBITIONKEYWORD_TYPE_ERROR); 
		}
		List<ExhibitionKeywordEntity> exhibitionKeywordList = exhibitionKeywordDao.queryExhibitionKeywordListBySelectiveCondition(sa, offset, limit);
		if (CollectionUtils.isNotEmpty(exhibitionKeywordList)) {
		   //查询多的总条数
		   Long totalCount = exhibitionKeywordDao.queryExhibitionKeywordCountBySelectiveCondition(sa);
		   paginationDto.setPaginationDtos(exhibitionKeywordList);
		   paginationDto.setTotalCount(totalCount);
		}
		
		return paginationDto;
	}
	/**
	 * 
	* @Title: queryExhibitionKeywordListByTargetOrgName 
	* @Description: 根据目的站地址查询目的站地址是否含有展馆关键字信息
	* @param condition
	* @return
	* @throws ExhibitionKeywordException
	* @see com.deppon.foss.module.base.baseinfo.api.server.service.IExhibitionKeywordService#queryExhibitionKeywordListByTargetOrgName(java.lang.String)    返回类型 
	* @author 189284 ZhangXu
	* @date 2014-12-29 下午5:23:15
	 */
	@Override
	@Transactional(readOnly = true)
	public List<ExhibitionKeywordEntity> queryExhibitionKeywordListByTargetOrgName(
			String condition) throws ExhibitionKeywordException{
		// TODO Auto-generated method stub
		List<ExhibitionKeywordEntity> exhibitionKeywordList = exhibitionKeywordDao
				.queryExhibitionKeywordListByTargetOrgName(condition);
		return exhibitionKeywordList;
		
	}
	
	

}
