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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/DepartureStandardService.java
 * 
 * FILE NAME        	: DepartureStandardService.java
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
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.cache.CacheManager;
import com.deppon.foss.framework.cache.ICache;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressDepartureStandardDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressDepartureStandardService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDepartureStandardEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressLineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.AgingDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.DepartureStandardException;
import com.deppon.foss.module.base.baseinfo.server.util.LineUtils;
import com.deppon.foss.util.common.FossTTLCache;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 发车标准服务类
 * 
 * @author foss-zhujunyong
 * @date Oct 26, 2012 3:49:51 PM
 * @version 1.0
 */
public class ExpressDepartureStandardService implements IExpressDepartureStandardService {

	@Inject
	private IExpressDepartureStandardDao expressdepartureStandardDao;

	//private IExpressLineService expresslineService;

	//private static final Logger log = Logger
			//.getLogger(ExpressDepartureStandardService.class);

	/**
	 * @param departureStandardDao
	 *            the departureStandardDao to set
	 */
	public void setExpressdepartureStandardDao(
			IExpressDepartureStandardDao expressdepartureStandardDao) {
		this.expressdepartureStandardDao = expressdepartureStandardDao;
	}

	/*public void setExpresslineService(IExpressLineService expresslineService) {
		this.expresslineService = expresslineService;
	}*/

	/**
	 * <p>
	 * 添加发车标准
	 * </p>
	 * 
	 * @author foss-zhujunyong
	 * @date Oct 26, 2012 3:49:51 PM
	 * @param departureStandard
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IDepartureStandardService#addDepartureStandard(com.deppon.foss.module.base.baseinfo.api.shared.domain.DepartureStandardEntity)
	 */
	@Override
	@Transactional
	public ExpressDepartureStandardEntity addDepartureStandard(
			ExpressDepartureStandardEntity departureStandard) {
		if (StringUtils.isBlank(departureStandard.getLineVirtualCode())
				|| departureStandard.getOrder() == null
				|| departureStandard.getOrder() <= 0) {
			throw new DepartureStandardException(
					DepartureStandardException.DEPARTURESTANDARD_DATA_ERROR);
		}
		// 如果该班次号已经存在，则抛出异常
		ExpressDepartureStandardEntity exist = queryDepartureStandardByLineVirtualCodeAndSequence(
				departureStandard.getLineVirtualCode(),
				departureStandard.getOrder());
		if (exist != null) {
			throw new DepartureStandardException(
					DepartureStandardException.DEPARTURESTANDARD_ORDER_EXIST);
		}
		ExpressDepartureStandardEntity entity = expressdepartureStandardDao
				.addDepartureStandard(departureStandard);

	/*	ExpressLineEntity line = expresslineService.queryLineByVirtualCode(entity
				.getLineVirtualCode());*/
		/*// 如果是到达线路和始发线路
		if (StringUtils.equals(line.getLineSort(),
				DictionaryValueConstants.BSE_LINE_SORT_TARGET)
				|| StringUtils.equals(line.getLineSort(),
						DictionaryValueConstants.BSE_LINE_SORT_SOURCE)) {
			// 如果是始发到达线路新增修改发车标准的时候要同步给gps 时效
			if (StringUtils.isNotBlank(entity.getLeaveTime())
					&& StringUtils.isNotBlank(entity.getArriveTime())) {
				// 准点出发时间
				String leaveTime = entity.getLeaveTime();
				// 准点到达时间(eg: 1645)
				String arriveTime = entity.getArriveTime();
				long fastAging = 0;
				DateFormat df = new SimpleDateFormat("HHmm");
				try {
					Date d1 = df.parse(leaveTime);
					Date d2 = df.parse(arriveTime);
					long hours = d2.getTime()
							+ (entity.getArriveDay() * 24 * 3600 * 1000)
							- d1.getTime();
					Double.longBitsToDouble(hours);
					fastAging = hours / 3600;
				} catch (Exception e) {
				}
				line.setFastAging(fastAging);
				// 同步到短途GPS
				expresslineService.sendLineInfoToGps(line, NumberConstants.TWO);

			}
		}*/
		if (entity != null) {
			invalidList(entity.getLineVirtualCode());
		}
		return entity;
	}

	

	/**
	 * <p>
	 * 作废发车标准
	 * </p>
	 * 
	 * @author foss-zhujunyong
	 * @date Oct 26, 2012 3:49:51 PM
	 * @param departureStandard
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IDepartureStandardService#deleteDepartureStandard(com.deppon.foss.module.base.baseinfo.api.shared.domain.DepartureStandardEntity)
	 */
	@Override
	@Transactional
	public ExpressDepartureStandardEntity deleteDepartureStandard(
			ExpressDepartureStandardEntity departureStandard) {
		ExpressDepartureStandardEntity entity = expressdepartureStandardDao
				.deleteDepartureStandard(departureStandard);
		if (entity != null) {
			invalidList(entity.getLineVirtualCode());
		}
		// List<DepartureStandardEntity> departureStandardEntitys =
		// queryDepartureStandardListByLineVirtualCode(departureStandard.getLineVirtualCode());

		/*ExpressLineEntity line = expresslineService.queryLineByVirtualCode(departureStandard
				.getLineVirtualCode());
		// 删除发车标准后新路的时效发生变化为0同步到短途GPS
		line.setFastAging((long) NumberConstants.ZERO);
		expresslineService.sendLineInfoToGps(line, NumberConstants.TWO);
*/
		return entity;
	}

	/**
	 * <p>
	 * 更新发车标准
	 * </p>
	 * 
	 * @author foss-zhujunyong
	 * @date Oct 26, 2012 3:49:51 PM
	 * @param departureStandard
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IDepartureStandardService#updateDepartureStandard(com.deppon.foss.module.base.baseinfo.api.shared.domain.DepartureStandardEntity)
	 */
	@Override
	@Transactional
	public ExpressDepartureStandardEntity updateDepartureStandard(
			ExpressDepartureStandardEntity departureStandard) {
		if (StringUtils.isBlank(departureStandard.getLineVirtualCode())
				|| departureStandard.getOrder() == null
				|| departureStandard.getOrder() <= 0) {
			throw new DepartureStandardException(
					DepartureStandardException.DEPARTURESTANDARD_DATA_ERROR);
		}

		// 如果该班次号已经存在，则抛出异常
		ExpressDepartureStandardEntity exist = queryDepartureStandardByLineVirtualCodeAndSequence(
				departureStandard.getLineVirtualCode(),
				departureStandard.getOrder());
		if (exist != null
				&& !StringUtils.equals(exist.getVirtualCode(),
						departureStandard.getVirtualCode())) {
			throw new DepartureStandardException(
					DepartureStandardException.DEPARTURESTANDARD_ORDER_EXIST);
		}

		ExpressDepartureStandardEntity entity = expressdepartureStandardDao
				.updateDepartureStandard(departureStandard);

		/*ExpressLineEntity line = expresslineService.queryLineByVirtualCode(entity
				.getLineVirtualCode());
		// 如果是到达线路和始发线路
		if (StringUtils.equals(line.getLineSort(),
				DictionaryValueConstants.BSE_LINE_SORT_TARGET)
				|| StringUtils.equals(line.getLineSort(),
						DictionaryValueConstants.BSE_LINE_SORT_SOURCE)) {
			// 如果是始发到达线路新增修改发车标准的时候要同步给gps 时效
			if (StringUtils.isNotBlank(entity.getLeaveTime())
					&& StringUtils.isNotBlank(entity.getArriveTime())) {
				// 准点出发时间
				String leaveTime = entity.getLeaveTime();
				// 准点到达时间(eg: 1645)
				String arriveTime = entity.getArriveTime();
				long fastAging = 0;
				DateFormat df = new SimpleDateFormat("HHmm");
				try {
					Date d1 = df.parse(leaveTime);
					Date d2 = df.parse(arriveTime);
					// long hours = d2.getTime() - d1.getTime();
					long hours = d2.getTime()
							+ (entity.getArriveDay() * 24 * 3600 * 1000)
							- d1.getTime();
					Double.longBitsToDouble(hours);
					fastAging = hours / 3600;
				} catch (Exception e) {
				}
				line.setFastAging(fastAging);
				// 同步到短途GPS
				expresslineService.sendLineInfoToGps(line, NumberConstants.TWO);

			}
		}*/

		if (entity != null) {
			invalidList(entity.getLineVirtualCode());
		}
		return entity;
	}

	/**
	 * <p>
	 * 查询发车标准详情
	 * </p>
	 * 
	 * @author foss-zhujunyong
	 * @date Oct 26, 2012 3:49:51 PM
	 * @param id
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IDepartureStandardService#queryDepartureStandardById(java.lang.String)
	 */
	@Override
	public ExpressDepartureStandardEntity queryDepartureStandardById(String id) {
		return expressdepartureStandardDao.queryDepartureStandardById(id);
	}

	/**
	 * <p>
	 * 查询特定线路下的发车标准列表
	 * </p>
	 * 
	 * @author foss-zhujunyong
	 * @date Oct 26, 2012 3:49:51 PM
	 * @param lineVirtualCode
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IDepartureStandardService#queryDepartureStandardListByLineVirtualCode(java.lang.String)
	 */
	@Override
	public List<ExpressDepartureStandardEntity> queryDepartureStandardListByLineVirtualCode(
			String lineVirtualCode) {
		List<ExpressDepartureStandardEntity> resultList = new ArrayList<ExpressDepartureStandardEntity>();
		if (StringUtils.isBlank(lineVirtualCode)) {
			return resultList;
		}

		resultList = expressdepartureStandardDao
				.queryDepartureStandardListByLineVirtualCode(lineVirtualCode);

		if (resultList == null) {
			resultList = new ArrayList<ExpressDepartureStandardEntity>();
		}

		return resultList;
	}

	/**
	 * 
	 * <p>
	 * 查询特定线路下指定班次的发车标准实体
	 * </p>
	 * 
	 * @author foss-zhujunyong
	 * @date Nov 5, 2012 2:37:03 PM
	 * @param lineVirtualCode
	 *            线路虚拟编码
	 * @param sequence
	 *            发车班次
	 * @return
	 * @see
	 */
	@Override
	public ExpressDepartureStandardEntity queryDepartureStandardByLineVirtualCodeAndSequence(
			String lineVirtualCode, int sequence) {
		if (StringUtils.isBlank(lineVirtualCode) || sequence <= 0) {
			return null;
		}
		return expressdepartureStandardDao.queryDepartureStandardByOrder(
				lineVirtualCode, sequence);

	}

	/**
	 * 
	 * <p>
	 * 根据线路作废发车标准
	 * </p>
	 * 
	 * @author foss-zhujunyong
	 * @date Nov 12, 2012 3:32:49 PM
	 * @param departureStandard
	 * @return
	 * @see
	 */
	@Override
	public int deleteDepartureStandardByLine(String lineVirtualCode,
			String modifyUser) {
		if (StringUtils.isBlank(lineVirtualCode)
				|| StringUtils.isBlank(modifyUser)) {
			return FossConstants.FAILURE;
		}
		int result = expressdepartureStandardDao.deleteDepartureStandardByLine(
				lineVirtualCode, modifyUser);
		invalidList(lineVirtualCode);
		return result;
	}

	/**
	 * 
	 * <p>
	 * 找出指定线路下离传入时间（只取时分）最近的一个发车标准
	 * </p>
	 * 该发车标准要求具备
	 * 
	 * @author foss-zhujunyong
	 * @date Nov 14, 2012 5:34:22 PM
	 * @param line
	 *            线路实体
	 * @param time
	 *            只取时分
	 * @return
	 * @see
	 */
	@Override
	public ExpressDepartureStandardEntity queryDepartureStandardByLineAndTime(
			ExpressLineEntity line, Date time) {
		// 检查参数
		if (line == null || time == null) {
			return null;
		}
		// 找该线路对应的发车标准
		List<ExpressDepartureStandardEntity> standardList = queryDepartureStandardListByLineVirtualCode(line
				.getVirtualCode());
		// 如果找不到发车标准，则返回null
		if (CollectionUtils.isEmpty(standardList)) {
			return null;
		}

		// 遍历班次，取下一班的发车时间和到达时间
		for (ExpressDepartureStandardEntity standard : standardList) {
			Date leaveDate = LineUtils.createStandardTime(time,
					standard.getLeaveTime());
			// 发车标准集合是按发车时间顺序排列的，所以只需进行到第一个符合条件的即可
			if (leaveDate.after(time)) {
				// 如果是中转线路的话，把arrivetime和arriveDay根据时效算出来填上
				if (StringUtils.isBlank(standard.getArriveTime())) {
					long hourOffset = LineUtils.isFast(standard
							.getProductType()) ? line.getFastAging() : line
							.getCommonAging();
					int minuteOffset = LineUtils
							.convertHourToMinute(hourOffset);
					AgingDto dto = LineUtils.calculatePartLineStandard(
							standard.getLeaveTime(), minuteOffset);
					standard.setArriveTime(dto.getTime());
					standard.setArriveDay((long) dto.getDay());
				}
				return standard;
			}
		}
		// 如果当天所有班次都错过了，则取当天的第一班车用来算短途时间
		return standardList.get(0);
	}

	@SuppressWarnings("unchecked")
	private void invalidList(String key) {
		((ICache<String, List<ExpressDepartureStandardEntity>>) CacheManager
				.getInstance().getCache(
						FossTTLCache.EXPRESS_DEPARTURESTANDARD_LIST_CACHE_UUID))
				.invalid(key);
	}

	// 取缓存中的数据
	/*@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<ExpressDepartureStandardEntity> queryListCache(String key) {
		List<ExpressDepartureStandardEntity> resultList = new ArrayList<ExpressDepartureStandardEntity>();
		try {
			CacheManager cacheManager = CacheManager.getInstance();
			if (cacheManager == null) {
				return resultList;
			}
			ICache cache = cacheManager
					.getCache(FossTTLCache.EXPRESS_DEPARTURESTANDARD_LIST_CACHE_UUID);
			if (cache == null) {
				return resultList;
			}
			resultList = (List<ExpressDepartureStandardEntity>) cache.get(key);
		} catch (Exception t) {
			log.error("cache找不到", t);
		}
		return resultList;
	}*/

}
