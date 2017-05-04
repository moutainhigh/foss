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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/OwedLimitRegionService.java
 * 
 * FILE NAME        	: OwedLimitRegionService.java
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IOwedLimitRegionDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOwedLimitRegionService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwedLimitRegionEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.OwedLimitDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.OwedLimitRegionException;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 临欠额度区间范围信息Service接口实现类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-2-25 下午2:28:33 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-2-25 下午2:28:33
 * @since
 * @version
 */
public class OwedLimitRegionService implements IOwedLimitRegionService {
    
    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(OwedLimitRegionService.class);
    

    /**
     * 临欠额度区间范围信息DAO接口.
     */
    private IOwedLimitRegionDao owedLimitRegionDao;
    
    /**
     * 营业部 Service接口.
     */
    private ISaleDepartmentService saleDepartmentService;
    /**
	 * 配置参数的service
	 */
	private IConfigurationParamsService configurationParamsService;
    
    /**
     * 设置 营业部 Service接口.
     *
     * @param saleDepartmentService the saleDepartmentService to set
     */
    public void setSaleDepartmentService(
    	ISaleDepartmentService saleDepartmentService) {
        this.saleDepartmentService = saleDepartmentService;
    }
    
    /**
	 * @param configurationParamsService the configurationParamsService to set
	 */
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	/**
     * 设置 临欠额度区间范围信息DAO接口.
     *
     * @param owedLimitRegionDao the owedLimitRegionDao to set
     */
    public void setOwedLimitRegionDao(IOwedLimitRegionDao owedLimitRegionDao) {
        this.owedLimitRegionDao = owedLimitRegionDao;
    }

    /**
     * <p>添加临欠额度区间范围信息</p>.
     *
     * @param entity 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-2-25 下午2:28:33
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IOwedLimitRegionService#addInfo(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwedLimitRegionEntity)
     */
    @Override
    public int addInfo(OwedLimitRegionEntity entity) {
	
	if(null == entity){
	    return FossConstants.FAILURE;
	}
	Date date = new Date();
	entity.setId(UUIDUtils.getUUID());
	entity.setCreateDate(date);
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	entity.setActive(FossConstants.ACTIVE);
	OwedLimitRegionEntity entity2 = owedLimitRegionDao.queryInfoByRegionValue(entity.getOwedlimitMin(),null);
	if(null != entity2){
	    throw new OwedLimitRegionException("额度最小值已存在额度区间范围内！");
	}
	entity2 = owedLimitRegionDao.queryInfoByRegionValue(entity.getOwedlimitMax(),null);
	if(null != entity2){
	    throw new OwedLimitRegionException("额度最大值已存在额度区间范围内！");
	}
	if(entity .getOwedlimitMin().compareTo(entity.getOwedlimitMax()) == 0 || entity .getOwedlimitMin().compareTo(entity.getOwedlimitMax()) == 1){
	    throw new OwedLimitRegionException("额度最小值必须小于额度最大值！");
	}
	entity2 = owedLimitRegionDao.queryInfoByRegionValues(entity.getOwedlimitMin(), entity.getOwedlimitMax(), null);
	if(null != entity2){
	    throw new OwedLimitRegionException("存在相同范围的额度区间范围!");
	}
	
	return owedLimitRegionDao.addInfo(entity);
    }

    /**
     * <p>作废临欠额度区间范围信息</p>.
     *
     * @param ids
     * @param modifyUser 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-2-25 下午2:28:34
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IOwedLimitRegionService#deleteInfos(java.util.List, java.lang.String)
     */
    @Transactional
    @Override
    public int deleteInfos(List<String> ids, String modifyUser) {
	if(CollectionUtils.isEmpty(ids)){
	    return FossConstants.FAILURE;
	}
	LOGGER.debug("modifyUser : "+modifyUser);
	
	return owedLimitRegionDao.deleteInfos(ids, modifyUser);
    }

    /**
     * <p>修改临欠额度区间范围信息</p>.
     *
     * @param entity 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-2-25 下午2:28:34
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IOwedLimitRegionService#updateInfo(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwedLimitRegionEntity)
     */
    @Override
    public int updateInfo(OwedLimitRegionEntity entity) {
	if(null == entity){
	    throw new OwedLimitRegionException("传入的参数不允许为空！");
	}else {
	    if(StringUtil.isBlank(entity.getId())){
		throw new OwedLimitRegionException("临欠额度区间范围ID不允许为空！");
	    }else {
		OwedLimitRegionEntity entity2 = owedLimitRegionDao.queryInfoByRegionValue(entity.getOwedlimitMin(),entity.getId());
		if(null != entity2){
		    throw new OwedLimitRegionException("额度最小值已存在额度区间范围内！");
		}
		entity2 = owedLimitRegionDao.queryInfoByRegionValue(entity.getOwedlimitMax(),entity.getId());
		if(null != entity2){
		    throw new OwedLimitRegionException("额度最大值已存在额度区间范围内！");
		}
		if(entity .getOwedlimitMin().compareTo(entity.getOwedlimitMax()) == 0 || entity .getOwedlimitMin().compareTo(entity.getOwedlimitMax()) == 1){
		    throw new OwedLimitRegionException("额度最小值必须小于额度最大值！");
		}
		entity2 = owedLimitRegionDao.queryInfoByRegionValues(entity.getOwedlimitMin(), entity.getOwedlimitMax(), entity.getId());
		if(null != entity2){
		    throw new OwedLimitRegionException("存在相同范围的额度区间范围!");
		}
		return owedLimitRegionDao.updateInfo(entity);
	    }
	}
    }

    /**
     * <p>分页查询临欠额度区间范围信息</p>.
     *
     * @param entity 
     * @param limit 
     * @param start 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-2-25 下午2:28:34
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IOwedLimitRegionService#queryAllInfos(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwedLimitRegionEntity, int, int)
     */
    @Override
    public List<OwedLimitRegionEntity> queryAllInfos(
	    OwedLimitRegionEntity entity, int limit, int start) {
	if(null == entity){
	    throw new OwedLimitRegionException("传入的参数不允许为空！");
	}else {
	    entity.setActive(FossConstants.ACTIVE);
	    
	    return owedLimitRegionDao.queryAllInfos(entity, limit, start);
	}
    }

    /**
     * <p>查询总记录数</p>.
     *
     * @param entity 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-2-25 下午2:28:34
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IOwedLimitRegionService#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.OwedLimitRegionEntity)
     */
    @Override
    public Long queryRecordCount(OwedLimitRegionEntity entity) {
	if(null == entity){
	    throw new OwedLimitRegionException("传入的参数不允许为空！");
	}else {
	    entity.setActive(FossConstants.ACTIVE);
	    
	    return owedLimitRegionDao.queryRecordCount(entity);
	}
    }
    
    /**
     * <p>批量封装DTO</p>.
     *
     * @param list 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-2-26 下午1:49:36
     * @see
     */
    /*@Transactional
    private List<OwedLimitDto> convertInfos(List<OwedLimitDto> list){
	if(CollectionUtils.isEmpty(list)){
	    return list;
	}else {
	    List<OwedLimitDto> dtoList = new ArrayList<OwedLimitDto>();
	    for(OwedLimitDto dto : list){
		//判断当前时间是否2013-07-01 00:00:00
		if(isDividingDate()){
		    if(null != dto){
			//根据营业部门编码验证该营业部开业是否有3个月
			boolean is = validateDeptOpenDate(dto.getDeptCode());
			if(is){//如果已经开业超过3个月，修改营业部最大临欠额度
			   dtoList.add(convertInfo(dto));
			}
		    }
		}else {
		    dtoList.add(convertInfo(dto));
		}
		
	    }
	    return dtoList;
	}
    }*/
    /**
     * 
     *<p>批量封装DTO 新（配合结算优化项目）</p>	
     * @date 2014-7-22 下午3:18:56
     * @author 130566-ZengJunfan
     * @param list
     * @return
     */
    private List<OwedLimitDto> convertInfosNew(List<OwedLimitDto> list) {
    	List<OwedLimitDto> dtoList = new ArrayList<OwedLimitDto>();
		if(CollectionUtils.isEmpty(list)){
			return list;
		}
			for (OwedLimitDto owedLimitDto : list) {
				if(null !=owedLimitDto){
					getDtoList(dtoList, owedLimitDto);
				}
			}
		
		return dtoList;
	}

	private void getDtoList(List<OwedLimitDto> dtoList,
			OwedLimitDto owedLimitDto) {
		//校验是否开业满3个月
		boolean is =validateDeptOpenDate(owedLimitDto.getDeptCode());
		//根据部门编码查询营业部信息
		SaleDepartmentEntity deptEntity = saleDepartmentService.querySaleDepartmentByCode(owedLimitDto.getDeptCode());
		//满三个月
		if(is){
			//
			ConfigurationParamsEntity entity =configurationParamsService.queryConfigurationParamsOneByCode(DictionaryValueConstants.OWED_LIMIT_MULTIPLE);
			if(null !=entity && StringUtil.isNotBlank(entity.getConfValue())){
				getOwedLimitDto(owedLimitDto, deptEntity, entity);
			}
		}else{
			//否则不满三个月 30000
			owedLimitDto.setOwedLimit(NumberConstants.OWEDLIMIT);
		}
		//DMANA-5178纯到达部门的临时欠款额度统一配置为10万dujunhui-187862
		if(null != deptEntity){
			if(deptEntity.getLeave().equals("N") && deptEntity.getArrive().equals("Y")){
				owedLimitDto.setOwedLimit(NumberConstants.OWEDLIMITOFPUREARRIVE);
			}
		}
		dtoList.add(owedLimitDto);
	}

	private void getOwedLimitDto(OwedLimitDto owedLimitDto,
			SaleDepartmentEntity deptEntity, ConfigurationParamsEntity entity) {
		double multiple =Double.parseDouble(entity.getConfValue());
		//最大的乘以0.3
		BigDecimal newOwedLimit =owedLimitDto.getTaking().multiply(new BigDecimal(multiple));
		//DMANA-5178同时做出发和到达的部门，临欠额度按照部门前3个月最高月份收入计算后大于3万元，按照计算的额度进行配置。小于等于3万元按照3万元配置。
		if(null != deptEntity){
			if(deptEntity.getLeave().equals("Y") && deptEntity.getArrive().equals("Y")){
				if(newOwedLimit.doubleValue()<=NumberConstants.OWEDLIMIT.doubleValue()){
					owedLimitDto.setOwedLimit(NumberConstants.OWEDLIMIT);
				}else{
					owedLimitDto.setOwedLimit(newOwedLimit);
				}
			}else{
				owedLimitDto.setOwedLimit(newOwedLimit);
			}
		}
	}

    /**
     * <p>根据传入的营业部收入查询临欠额度封装到DTO中</p>.
     *
     * @param dto 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-2-26 下午1:41:16
     * @see
     */
   /* private OwedLimitDto convertInfo(OwedLimitDto dto){
	if(null == dto){
	    return dto;
	}else {
	    OwedLimitRegionEntity entity = owedLimitRegionDao.queryInfoByTaking(dto.getTaking(),null);
	    if(null != entity){
		if(null != entity.getOwedlimit()){
		  //设置营业部的最大临欠额度
		    dto.setOwedLimit(entity.getOwedlimit());
		}else {
		    throw new OwedLimitRegionException("","区间额度不存在，请配置！");
		}
		
	    }else {
		throw new OwedLimitRegionException("","没有相应的额度区间，请配置！");
	    }
	    
	    return dto;
	}
    }*/
    
    /**
     * <p>批量更新营业部最大临欠额度</p>.
     *
     * @param list 所有营业部最近三个月最大营业收入信息DTO集合
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-2-26 上午9:31:20
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IOwedLimitRegionService#batchUpdateAmountOweds(java.util.List)
     */
    @Transactional
    @Override
    public int batchUpdateAmountOweds(List<OwedLimitDto> list) {
	if(CollectionUtils.isEmpty(list)){
	    return FossConstants.FAILURE;
	}else {
	    //批量封装DTO
	    list = convertInfosNew(list);
	    //批量更新营业部最大临欠额度
//	    owedLimitRegionDao.batchUpdateDeptAmountOwed(list);
	    for(OwedLimitDto dto : list){
		owedLimitRegionDao.updateDepartmentAmountOwed(dto);
	    }
	    return FossConstants.SUCCESS;
	}
    }
    
   
	/**
     * <p>根据营业部门编码验证该营业部开业是否有3个月</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-6-19 下午6:15:16
     * @param deptCode 部门编码
     * @return
     * @see
     */
    private boolean validateDeptOpenDate(String deptCode){
	//根据部门编码查询营业部信息
	SaleDepartmentEntity entity = saleDepartmentService.querySaleDepartmentByCode(deptCode);
	if(null != entity){
	    Date openingDate = entity.getOpeningDate();
	    Calendar calendar = new GregorianCalendar();
	    if(openingDate != null){
		calendar.setTime(openingDate);
		//开业时间+3个月
		calendar.add(NumberConstants.NUMBER_2, NumberConstants.NUMBER_3);
		if(calendar.getTime().before(new Date())){
		    return true;
		}else {
		    return false;
		}
	    }
	}
	
	return false;
    }
    
    /**
     * <p>判断当前时间是否2013-07-01 00:00:00</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-6-19 下午6:38:24
     * @return
     * @throws ParseException
     * @see
     */
    /*private boolean isDividingDate(){
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	//获取2013-07-01 00:00:00时间
	Date date;
	try {
	    date = format.parse(DateTimeConstants.DEVIDING_DATE);
	    if(new Date().before(date)){//当前时间与2013-07-01 00:00:00比较
		return false;
	    } else {
		return true;
	    }
	} catch (ParseException e) {
	    e.printStackTrace();
	}
	
	return false;
    }*/
}
