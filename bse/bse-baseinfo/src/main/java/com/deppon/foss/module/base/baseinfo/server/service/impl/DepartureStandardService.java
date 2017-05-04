/**
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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/DepartureStandardService.java
 * 
 * FILE NAME        	: DepartureStandardService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 
 *
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.cache.CacheManager;
import com.deppon.foss.framework.cache.ICache;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IDepartureStandardDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IDepartureStandardService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISendDepartureStandardToWDGHService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DepartureStandardEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.AgingDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.DepartureStandardException;
import com.deppon.foss.module.base.baseinfo.server.util.LineUtils;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.common.FossTTLCache;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;


/**
 * 发车标准服务类
 * @author foss-zhujunyong
 * @date Oct 26, 2012 3:49:51 PM
 * @version 1.0
 */
public class DepartureStandardService implements IDepartureStandardService {
    
    @Inject
    private IDepartureStandardDao departureStandardDao;
    
    private ILineService lineService;
    
    private static final Logger log = LoggerFactory.getLogger(DepartureStandardService.class);
    
    /**
     * 同步发车标准给wdgh 系统接口service
     */
    private ISendDepartureStandardToWDGHService sendDepartureStandardToWDGHService;
    
    public void setSendDepartureStandardToWDGHService(
    		ISendDepartureStandardToWDGHService sendDepartureStandardToWDGHService) {
		this.sendDepartureStandardToWDGHService = sendDepartureStandardToWDGHService;
	}

    /**
     * @param departureStandardDao the departureStandardDao to set
     */
    public void setDepartureStandardDao(IDepartureStandardDao departureStandardDao) {
        this.departureStandardDao = departureStandardDao;
    }

    public void setLineService(ILineService lineService) {
		this.lineService = lineService;
	}

	/** 
     * <p>添加发车标准</p> 
     * @author foss-zhujunyong
     * @date Oct 26, 2012 3:49:51 PM
     * @param departureStandard
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IDepartureStandardService#addDepartureStandard(com.deppon.foss.module.base.baseinfo.api.shared.domain.DepartureStandardEntity)
     */
    @Override
    @Transactional
    public DepartureStandardEntity addDepartureStandard(
	    DepartureStandardEntity departureStandard) {
	if (StringUtils.isBlank(departureStandard.getLineVirtualCode()) || departureStandard.getOrder() == null || departureStandard.getOrder() <= 0) {
	    throw new DepartureStandardException(DepartureStandardException.DEPARTURESTANDARD_DATA_ERROR);
	}
	// 如果该班次号已经存在，则抛出异常
	DepartureStandardEntity exist = queryDepartureStandardByLineVirtualCodeAndSequence(departureStandard.getLineVirtualCode(), departureStandard.getOrder());
	if (exist != null) {
	    throw new DepartureStandardException(DepartureStandardException.DEPARTURESTANDARD_ORDER_EXIST);
	}
	DepartureStandardEntity entity = departureStandardDao.addDepartureStandard(departureStandard);
	
	LineEntity line = lineService.queryLineByVirtualCode(entity.getLineVirtualCode());
	if(null==line){
		throw new DepartureStandardException("line为空");	
	}
	//如果是到达线路和始发线路
	if (StringUtils.equals(line.getLineSort(), DictionaryValueConstants.BSE_LINE_SORT_TARGET)||StringUtils.equals(line.getLineSort(), DictionaryValueConstants.BSE_LINE_SORT_SOURCE)){
	//如果是始发到达线路新增修改发车标准的时候要同步给gps  时效
	 if(StringUtils.isNotBlank(entity.getLeaveTime())&&StringUtils.isNotBlank(entity.getArriveTime())){
		//准点出发时间
	    String leaveTime = entity.getLeaveTime();
	    // 准点到达时间(eg: 1645)
	    String arriveTime = entity.getArriveTime();
	    long fastAging=0;
	    DateFormat df = new SimpleDateFormat("HHmm");
	    try {
		        Date d1 = df.parse(leaveTime);
		        Date d2 = df.parse(arriveTime);
		        long hours = d2.getTime()+(entity.getArriveDay()*NumberConstants.NUMBER_24*NumberConstants.NUMBER_3600*NumberConstants.NUMBER_1000) - d1.getTime();
		       // Double.longBitsToDouble(hours);
		        fastAging = hours/NumberConstants.NUMBER_3600;
		    }catch (Exception e){
		    	log.error("时间格式有误:"+e.getMessage(), e);
		    }
	    line.setFastAging(fastAging);
					  //同步到短途GPS
		lineService.sendLineInfoToGps(line, NumberConstants.TWO);

	 	}
	 }
	
	//同步 发车标准 到 WDGH
	syncDepartureStandardToWdgh(entity, NumberConstants.ONE);
	
	if (entity != null) {
	    invalidList(entity.getLineVirtualCode());
	}
	return entity;
    }

    /** 
     * <p>作废发车标准</p> 
     * @author foss-zhujunyong
     * @date Oct 26, 2012 3:49:51 PM
     * @param departureStandard
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IDepartureStandardService#deleteDepartureStandard(com.deppon.foss.module.base.baseinfo.api.shared.domain.DepartureStandardEntity)
     */
    @Override
    @Transactional
    public DepartureStandardEntity deleteDepartureStandard(
	    DepartureStandardEntity departureStandard) {
    	
    	DepartureStandardEntity entity = departureStandardDao.deleteDepartureStandard(departureStandard);
	
    	if (entity != null) {
    		invalidList(entity.getLineVirtualCode());
    	}
    	//按virtualcode查询出删除的发车标准
		List<DepartureStandardEntity> list = departureStandardDao.queryDepartureStandardByVirtueCode(departureStandard.getVirtualCode());
		if(CollectionUtils.isNotEmpty(list)){
			DepartureStandardEntity dstEntity = list.get(0);
			//同步发车标准到WDGH
			syncDepartureStandardToWdgh(dstEntity,NumberConstants.THREE);
			}
		
			//	List<DepartureStandardEntity> departureStandardEntitys = queryDepartureStandardListByLineVirtualCode(departureStandard.getLineVirtualCode());
	
			LineEntity line = lineService.queryLineByVirtualCode(departureStandard.getLineVirtualCode());
			//删除发车标准后新路的时效发生变化为0同步到短途GPS
			line.setFastAging((long)NumberConstants.ZERO);
			lineService.sendLineInfoToGps(line, NumberConstants.TWO);

	
			return entity;
    }
    
    /** 
     * <p>更新发车标准</p> 
     * @author foss-zhujunyong
     * @date Oct 26, 2012 3:49:51 PM
     * @param departureStandard
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IDepartureStandardService#updateDepartureStandard(com.deppon.foss.module.base.baseinfo.api.shared.domain.DepartureStandardEntity)
     */
    @Override
    @Transactional
    public DepartureStandardEntity updateDepartureStandard(
	    DepartureStandardEntity departureStandard) {
	if (StringUtils.isBlank(departureStandard.getLineVirtualCode()) || departureStandard.getOrder() == null || departureStandard.getOrder() <= 0) {
	    throw new DepartureStandardException(DepartureStandardException.DEPARTURESTANDARD_DATA_ERROR);
	}
	
	// 如果该班次号已经存在，则抛出异常
	DepartureStandardEntity exist = queryDepartureStandardByLineVirtualCodeAndSequence(departureStandard.getLineVirtualCode(), departureStandard.getOrder());
	if (exist != null && !StringUtils.equals(exist.getVirtualCode(), departureStandard.getVirtualCode())) {
	    throw new DepartureStandardException(DepartureStandardException.DEPARTURESTANDARD_ORDER_EXIST);
	}
	
	DepartureStandardEntity entity = departureStandardDao.updateDepartureStandard(departureStandard);
	
	
	LineEntity line = lineService.queryLineByVirtualCode(entity.getLineVirtualCode());
	if(null==line){
		throw new DepartureStandardException("line为空");	
	}
	//如果是到达线路和始发线路
	if (StringUtils.equals(line.getLineSort(), DictionaryValueConstants.BSE_LINE_SORT_TARGET)||StringUtils.equals(line.getLineSort(), DictionaryValueConstants.BSE_LINE_SORT_SOURCE)){
	//如果是始发到达线路新增修改发车标准的时候要同步给gps  时效
	 if(StringUtils.isNotBlank(entity.getLeaveTime())&&StringUtils.isNotBlank(entity.getArriveTime())){
		//准点出发时间
	    String leaveTime = entity.getLeaveTime();
	    // 准点到达时间(eg: 1645)
	    String arriveTime = entity.getArriveTime();
	    long fastAging=0;
	    DateFormat df = new SimpleDateFormat("HHmm");
	    try {
		        Date d1 = df.parse(leaveTime);
		        Date d2 = df.parse(arriveTime);
//		        long hours = d2.getTime() - d1.getTime();
		        long hours = d2.getTime()+(entity.getArriveDay()*NumberConstants.NUMBER_24*NumberConstants.NUMBER_3600*NumberConstants.NUMBER_1000) - d1.getTime();
		        //Double.longBitsToDouble(hours);
		        fastAging = hours/NumberConstants.NUMBER_3600;
		    }catch (Exception e){
		    	log.error("时间格式有误:"+e.getMessage(), e);
		    }
	    line.setFastAging(fastAging);
					  //同步到短途GPS
		lineService.sendLineInfoToGps(line, NumberConstants.TWO);
			
	 	}
	 }
	
	//同步发车标准到WDGH
	syncDepartureStandardToWdgh(entity,NumberConstants.TWO);
	
	if (entity != null) {
	    invalidList(entity.getLineVirtualCode());
	}
	return entity;
    }
    
/*	*//**
     * 
     * <p>更新发车标准信息，包含同步信息到WDGH</p> 
     * @author foss-qirongsheng
     * @date Mar 25, 2016 5:53:23 PM
     * @param line
     * @return
     * @see
     *//*
    private DepartureStandardEntity updateDepartureStandards(DepartureStandardEntity departureStandard) {
    	DepartureStandardEntity entity = departureStandardDao.deleteDepartureStandard(departureStandard);
    	if (entity == null) {
    	    return null;
    	}
    	
    	//按virtualcode查询出删除的发车标准
		List<DepartureStandardEntity> list = departureStandardDao.queryDepartureStandardByVirtueCode(departureStandard.getVirtualCode());
		if(CollectionUtils.isNotEmpty(list)){
			DepartureStandardEntity dstEntity = list.get(0);
			//同步发车标准到WDGH
			syncDepartureStandardToWdgh(dstEntity,NumberConstants.THREE);
			}
    	
    	entity = departureStandardDao.updateDepartureStandard(entity);
    	//同步更新发车标准信息到wdgh
    	syncDepartureStandardToWdgh(entity, NumberConstants.TWO);
    	return entity;
	}*/
    
    /** 
     * <p>查询发车标准详情</p> 
     * @author foss-zhujunyong
     * @date Oct 26, 2012 3:49:51 PM
     * @param id
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IDepartureStandardService#queryDepartureStandardById(java.lang.String)
     */
    @Override
    public DepartureStandardEntity queryDepartureStandardById(String id) {
	return departureStandardDao.queryDepartureStandardById(id);
    }

    /** 
     * <p>查询特定线路下的发车标准列表</p> 
     * @author foss-zhujunyong
     * @date Oct 26, 2012 3:49:51 PM
     * @param lineVirtualCode
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IDepartureStandardService#queryDepartureStandardListByLineVirtualCode(java.lang.String)
     */
    @Override
    public List<DepartureStandardEntity> queryDepartureStandardListByLineVirtualCode(
	    String lineVirtualCode) {
	List<DepartureStandardEntity> resultList = new ArrayList<DepartureStandardEntity>();
	if (StringUtils.isBlank(lineVirtualCode)) {
	    return resultList;
	}
	
	 resultList = departureStandardDao.queryDepartureStandardListByLineVirtualCode(lineVirtualCode);
	
	if (resultList == null) {
	    resultList = new ArrayList<DepartureStandardEntity>();
	}
	
	return resultList;
    }
    
    /**
     * 
     * <p>查询特定线路下指定班次的发车标准实体</p> 
     * @author foss-zhujunyong
     * @date Nov 5, 2012 2:37:03 PM
     * @param lineVirtualCode 线路虚拟编码
     * @param sequence 发车班次
     * @return
     * @see
     */
    @Override
    public DepartureStandardEntity queryDepartureStandardByLineVirtualCodeAndSequence(String lineVirtualCode, int sequence) {
	if (StringUtils.isBlank(lineVirtualCode) || sequence <= 0) {
	    return null;
	}
	return departureStandardDao.queryDepartureStandardByOrder(lineVirtualCode, sequence);
	
    }

    /**
     * 
     * <p>根据线路作废发车标准</p> 
     * @author foss-zhujunyong
     * @date Nov 12, 2012 3:32:49 PM
     * @param departureStandard
     * @return
     * @see
     */
    @Override
    public int deleteDepartureStandardByLine(String lineVirtualCode, String modifyUser) {
	if (StringUtils.isBlank(lineVirtualCode) || StringUtils.isBlank(modifyUser)) {
	    return FossConstants.FAILURE;
	}
	int result = departureStandardDao.deleteDepartureStandardByLine(lineVirtualCode, modifyUser);
	invalidList(lineVirtualCode);
	return result;
    }

    /**
     * 
     * <p>找出指定线路下离传入时间（只取时分）最近的一个发车标准</p>
     * 该发车标准要求具备 
     * @author foss-zhujunyong
     * @date Nov 14, 2012 5:34:22 PM
     * @param line 线路实体
     * @param time 只取时分
     * @return
     * @see
     */
    @Override
    public DepartureStandardEntity queryDepartureStandardByLineAndTime(LineEntity line, Date time){
	// 检查参数
	if (line == null || time == null) {
	    return null;
	}
	// 找该线路对应的发车标准
	List<DepartureStandardEntity> standardList = queryDepartureStandardListByLineVirtualCode(line.getVirtualCode());
	// 如果找不到发车标准，则返回null
	if (CollectionUtils.isEmpty(standardList)) {
	    return null;
	}

	// 遍历班次，取下一班的发车时间和到达时间
	for (DepartureStandardEntity standard : standardList) {
	    Date leaveDate = LineUtils.createStandardTime(time, standard.getLeaveTime());
	    // 发车标准集合是按发车时间顺序排列的，所以只需进行到第一个符合条件的即可
	    if (leaveDate.after(time)) {
		// 如果是中转线路的话，把arrivetime和arriveDay根据时效算出来填上
		if (StringUtils.isBlank(standard.getArriveTime())){
		    long hourOffset = LineUtils.isFast(standard.getProductType()) ? line.getFastAging() : line.getCommonAging();
		    int minuteOffset = LineUtils.convertHourToMinute(hourOffset);
		    AgingDto dto = LineUtils.calculatePartLineStandard(standard.getLeaveTime(), minuteOffset);
		    standard.setArriveTime(dto.getTime());
		    standard.setArriveDay((long)dto.getDay());
		}
		return standard;
	    }
	}
	// 如果当天所有班次都错过了，则取当天的第一班车用来算短途时间
	return standardList.get(0);
    }

    @SuppressWarnings("unchecked")
    private void invalidList(String key) {
	((ICache<String, List<DepartureStandardEntity>>)CacheManager.getInstance().getCache(FossTTLCache.DEPARTURESTANDARD_LIST_CACHE_UUID)).invalid(key);
    }

    // 取缓存中的数据
    /*@SuppressWarnings({ "unchecked", "rawtypes" })
    private List<DepartureStandardEntity> queryListCache(String key) {
	List<DepartureStandardEntity> resultList = new ArrayList<DepartureStandardEntity>();
	try {
	    CacheManager cacheManager = CacheManager.getInstance();
	    if (cacheManager == null) {
		return resultList;
	    }
	    ICache cache = cacheManager.getCache(FossTTLCache.DEPARTURESTANDARD_LIST_CACHE_UUID);
	    if (cache == null) {
		return resultList;
	    }
	    resultList = (List<DepartureStandardEntity>) cache.get(key);
	} catch (Exception t) {
	    log.error("cache找不到", t);
	}
	return resultList;
    }*/
    
    /**
     *     DMANA-2870
     *     新增始发线路、到达线路时生成默认发车标准
     *     author:187862-dujunhui
     *     date:2014-08-20 下午1:53
     * 
     * **/
    public DepartureStandardEntity addDefaultDepartureStandard(
    	    DepartureStandardEntity departureStandard) {
				return departureStandardDao.addDepartureStandard(departureStandard);
    	
    }
    
    /**
     *<p>同步发车标准给网点规划</p>
     *@author 269231 -qirongsheng
     *@date 2016-1-27 下午2:48:41
     *@param lineEntity
     *@param type
     */
    private void syncDepartureStandardToWdgh(DepartureStandardEntity departureStandard, Integer type) {
    	if(null !=departureStandard){
        	//同步接口
        	sendDepartureStandardToWDGHService.syncDepartureStandard(departureStandard, type.toString());
    	}
	}
}
