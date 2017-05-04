/**
 *  initial comments.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-load
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/service/impl/LoaderWorkloadModifyService.java
 *  
 *  FILE NAME          :LoaderWorkloadModifyService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.server.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.dao.ILoaderWorkloadModifyDao;
import com.deppon.foss.module.transfer.load.api.server.service.ILoadTaskQueryService;
import com.deppon.foss.module.transfer.load.api.server.service.ILoaderWorkloadModifyService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderWorkloadEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.UnloadTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoadTaskDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoaderWorkloadDetailDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoaderWorkloadDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;

/**
 * 修改装卸车工作量Service
 * @author ibm-zhangyixin
 * @date 2012-12-20 下午5:30:38
 */
public class LoaderWorkloadModifyService implements ILoaderWorkloadModifyService {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoaderWorkloadModifyService.class);
	private ILoaderWorkloadModifyDao loaderWorkloadModifyDao;
	
	/**
	 * 装车任务service
	 */
	private ILoadTaskQueryService loadTaskQueryService;
	
	

	public ILoaderWorkloadModifyDao getLoaderWorkloadModifyDao() {
		return loaderWorkloadModifyDao;
	}

	public void setLoaderWorkloadModifyDao(
			ILoaderWorkloadModifyDao loaderWorkloadModifyDao) {
		this.loaderWorkloadModifyDao = loaderWorkloadModifyDao;
	}

	/**
	 * 修改装卸车工作量查询方法
	 * @author ibm-zhangyixin
	 * @param start 
	 * @param limit 
	 * @date 2012-12-24 上午10:21:09
	 */
	@Override
	public List<LoaderWorkloadDetailDto> queryLoaderWorkloadDetail(
			LoaderWorkloadDetailDto loaderWorkloadDetailDto, int limit,
			int start) {
		return loaderWorkloadModifyDao.queryLoaderWorkloadDetail(loaderWorkloadDetailDto, limit, start);
	}
	/**
	 * 修改装卸车工作量查询方法(快递)
	 * @author zhangpeng
	 * @param start 
	 * @param limit 
	 * @date 2015-12-2 上午10:21:09
	 */
	@Override
	public List<LoaderWorkloadDetailDto> queryLoaderWorkloadDetailExpress(
			LoaderWorkloadDetailDto loaderWorkloadDetailDto, int limit,
			int start) {
		return loaderWorkloadModifyDao.queryLoaderWorkloadDetailExpress(loaderWorkloadDetailDto, limit, start);
	}

	/**
	 * 修改装卸车工作量查询 获取总记录数
	 * @author ibm-zhangyixin
	 * @date 2012-12-24 下午4:57:59
	 */
	@Override
	public Long getTotCount(LoaderWorkloadDetailDto loaderWorkloadDetailDto) {
		return loaderWorkloadModifyDao.getTotCount(loaderWorkloadDetailDto);
	}

	@Override
	public LoaderWorkloadDetailDto querySummaryDetail(
			LoaderWorkloadDetailDto loaderWorkloadDetailDto) {
		return loaderWorkloadModifyDao.querySummaryDetail(loaderWorkloadDetailDto);
	}
	

	/**
	 * 统计查询的总票数, 总重量, 总件数(快递)
	 * @author zhangpeng
	 * @date 2015-12-5下午5:19:11
	 */
	@Override
	public LoaderWorkloadDetailDto querySummaryDetailExpress(
			LoaderWorkloadDetailDto loaderWorkloadDetailDto) {
		return loaderWorkloadModifyDao.querySummaryDetailExpress(loaderWorkloadDetailDto);
	}

	/**
	 * 根据任务编号查询出当前任务的工作量
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 上午11:32:02
	 */
	@Override
	public List<LoaderWorkloadDto> queryLoaderWorksByTaskNo(String taskNo) {
		return loaderWorkloadModifyDao.queryLoaderWorksByTaskNo(taskNo);
	}
	
	/**
	 * 根据任务编号查询出当前任务的工作量（快递）
	 * @author ibm-zhangyixin
	 * @date 2015-12-05 上午11:32:02
	 */
	@Override
	public List<LoaderWorkloadDto> queryLoaderWorksByTaskNoExpress(String taskNo) {
		return loaderWorkloadModifyDao.queryLoaderWorksByTaskNoExpress(taskNo);
	}

	
	/**
	 * @Title: getUnloadTaskByUnloadTaskNo 
	 * @Description: 根据卸车任务号查询卸车任务 
	 * @param taskNO
	 * @return    
	 * @return UnloadTaskDto    返回类型 
	 * getUnloadTaskByUnloadTaskNo
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-23下午2:22:24
	 */
	public UnloadTaskEntity getUnloadTaskByUnloadTaskNo(String taskNO) {
		return loaderWorkloadModifyDao.getUnloadTaskByUnloadTaskNo(taskNO);
	}

	/**
	 * 新增理货员货量信息
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 下午4:19:46
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ILoaderWorkloadModifyService#saveLoaderWork(com.deppon.foss.module.transfer.load.api.shared.dto.LoaderWorkloadDto)
	 */
	@Transactional
	@Override
	public void saveLoaderWork(LoaderWorkloadDto loaderWorkloadDto) {
		String taskNo = loaderWorkloadDto.getTaskNo();
		List<LoaderWorkloadDto> loaderWorkloadDtos = queryLoaderWorksByTaskNo(taskNo);
		
		if(loaderWorkloadDtos.size() <= 0) {
			LOGGER.error("保存失败, 任务号错误.");
			throw new TfrBusinessException("保存失败, 数据错误." + taskNo, "");
		}
		
		LoaderWorkloadEntity loaderWorkload = getLoaderWorkloadByTaskNo(taskNo);
		if(loaderWorkload == null) {
			LOGGER.error("保存失败, 任务号错误.");
			throw new TfrBusinessException("保存失败, 任务号错误." + taskNo, "");
		}
		
		Date unloadStartTime = null;
		Date unloadEndTime = null;
		if(StringUtils.equals(loaderWorkload.getHandleType(), LoadConstants.LOADER_WORKLOAD_HANDLE_TYPE_LOAD)) {
			//如果操作类型是装车
			LoadTaskDto loadTask = loadTaskQueryService.getLoadTaskByTaskNo(loaderWorkload.getTaskNo());
			if(loadTask == null) {
				LOGGER.error("保存失败, 任务号错误.");
				throw new TfrBusinessException("保存失败, 装车任务号错误.", "");
			}
			unloadStartTime = DateUtils.convert(loadTask.getLoadStartTime());
			unloadEndTime = DateUtils.convert(loadTask.getLoadEndTime());
		} else {
			//卸车
			UnloadTaskEntity unloadTask = getUnloadTaskByUnloadTaskNo(loaderWorkload.getTaskNo());
			if(unloadTask == null) {
				LOGGER.error("保存失败, 任务号错误.");
				throw new TfrBusinessException("保存失败, 卸车任务号错误.", "");
			}
			unloadStartTime = unloadTask.getUnloadStartTime();
			unloadEndTime = unloadTask.getUnloadEndTime();
		}
		
		Date beginDate = DateUtils.convert(loaderWorkloadDto.getBeginDate());
		Date endDate = DateUtils.convert(loaderWorkloadDto.getEndDate());
		if(unloadStartTime.compareTo(beginDate) == 1) {
			//如果unloadStartTime大于beginDate说明beginDate选择的有误
			LOGGER.error("保存失败, 加入任务时间不在任务时间范围内 .");
			throw new TfrBusinessException("保存失败, 加入任务时间不在任务时间范围内 .", "");
		}
		if(unloadEndTime.compareTo(endDate) == -1) {
			//如果unloadEndTime小于endDate说明endDate选择的有误
			LOGGER.error("保存失败, 离开任务时间不在任务时间范围内 .");
			throw new TfrBusinessException("保存失败, 离开任务时间不在任务时间范围内 .", "");
		}
		BigDecimal totWeight = loaderWorkloadDto.getTotWeightTemp();
		Integer totWaybillQty = loaderWorkloadDto.getTotWaybillQtyTemp();
		//因为需要装卸车货量的其他信息, 所以直接从数据库中查一个出来
//		LoaderWorkloadEntity loaderWorkload = getLoaderWorkloadByTaskNo(taskNo);
		
		BigDecimal weight = BigDecimal.ZERO;
		Integer waybillQty = 0;
		weight = weight.add(loaderWorkloadDto.getWeight());
		waybillQty = waybillQty + loaderWorkloadDto.getWaybillQty();
		for(LoaderWorkloadDto lw : loaderWorkloadDtos) {
			weight = weight.add(lw.getWeight());
			waybillQty = waybillQty + lw.getWaybillQty();
		}
		if(weight.compareTo(totWeight) > 0) {
			LOGGER.error("保存失败, 理货员重量不能大于总重量.");
			throw new TfrBusinessException("保存失败, 理货员重量不能大于总重量.", "");
		}
		if(waybillQty > totWaybillQty) {
			LOGGER.error("保存失败, 理货员票数不能大于总票数.");
			throw new TfrBusinessException("保存失败, 理货员票数不能大于总票数.", "");
		}
		
		loaderWorkload.setId(UUIDUtils.getUUID());
		loaderWorkload.setLoaderCode(loaderWorkloadDto.getLoaderCode());
		loaderWorkload.setLoaderName(loaderWorkloadDto.getLoaderName());
		loaderWorkload.setWeight(loaderWorkloadDto.getWeight());
		loaderWorkload.setWaybillQty(loaderWorkloadDto.getWaybillQty());
		loaderWorkload.setLoaderOrgCode(loaderWorkloadDto.getLoaderOrgCode());
		loaderWorkload.setLoaderOrgName(loaderWorkloadDto.getLoaderOrgName());
		loaderWorkload.setNotes(loaderWorkloadDto.getNotes());
		loaderWorkload.setJoinTime(DateUtils.convert(loaderWorkloadDto.getBeginDate()));
		loaderWorkload.setLeaveTime(DateUtils.convert(loaderWorkloadDto.getEndDate()));
		loaderWorkload.setAdjusterCode(FossUserContext.getCurrentUser().getEmpCode());
		loaderWorkload.setAdjusterName(FossUserContext.getCurrentUser().getEmpName());
		loaderWorkload.setAdjustTime(new Date());
		loaderWorkload.setAdjustOrgCode(FossUserContext.getCurrentDept().getCode());
		loaderWorkload.setAdjustOrgName(FossUserContext.getCurrentDept().getName());
		LoaderWorkloadEntity loaderWorkloadEntity = getLoaderWorkloadByTaskNo(taskNo);
		loaderWorkload.setGoodsType(loaderWorkloadEntity.getGoodsType());
		loaderWorkloadModifyDao.saveLoaderWork(loaderWorkload);
	}
	
	
	/**
	 * 新增理货员货量信息(快递)
	 * @author zhangpeng
	 * @date 2015-12-08下午4:19:46
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ILoaderWorkloadModifyService#saveLoaderWork(com.deppon.foss.module.transfer.load.api.shared.dto.LoaderWorkloadDto)
	 */
	@Transactional
	@Override
	public void saveLoaderWorkExpress(LoaderWorkloadDto loaderWorkloadDto) {
		String taskNo = loaderWorkloadDto.getTaskNo();
		List<LoaderWorkloadDto> loaderWorkloadDtos = queryLoaderWorksByTaskNoExpress(taskNo);
		
		if(loaderWorkloadDtos.size() <= 0) {
			LOGGER.error("保存失败, 任务号错误.");
			throw new TfrBusinessException("保存失败, 数据错误." + taskNo, "");
		}
		
		LoaderWorkloadEntity loaderWorkload = getLoaderWorkloadByTaskNoExpress(taskNo);
		if(loaderWorkload == null) {
			LOGGER.error("保存失败, 任务号错误.");
			throw new TfrBusinessException("保存失败, 任务号错误." + taskNo, "");
		}
		
		Date unloadStartTime = null;
		Date unloadEndTime = null;
		if(StringUtils.equals(loaderWorkload.getHandleType(), LoadConstants.LOADER_WORKLOAD_HANDLE_TYPE_LOAD)) {
			//如果操作类型是装车
			LoadTaskDto loadTask = loadTaskQueryService.getLoadTaskByTaskNo(loaderWorkload.getTaskNo());
			if(loadTask == null) {
				LOGGER.error("保存失败, 任务号错误.");
				throw new TfrBusinessException("保存失败, 装车任务号错误.", "");
			}
			unloadStartTime = DateUtils.convert(loadTask.getLoadStartTime());
			unloadEndTime = DateUtils.convert(loadTask.getLoadEndTime());
		} else {
			//卸车
			UnloadTaskEntity unloadTask = getUnloadTaskByUnloadTaskNo(loaderWorkload.getTaskNo());
			if(unloadTask == null) {
				LOGGER.error("保存失败, 任务号错误.");
				throw new TfrBusinessException("保存失败, 卸车任务号错误.", "");
			}
			unloadStartTime = unloadTask.getUnloadStartTime();
			unloadEndTime = unloadTask.getUnloadEndTime();
		}
		
		Date beginDate = DateUtils.convert(loaderWorkloadDto.getBeginDate());
		Date endDate = DateUtils.convert(loaderWorkloadDto.getEndDate());
		if(unloadStartTime.compareTo(beginDate) == 1) {
			//如果unloadStartTime大于beginDate说明beginDate选择的有误
			LOGGER.error("保存失败, 加入任务时间不在任务时间范围内 .");
			throw new TfrBusinessException("保存失败, 加入任务时间不在任务时间范围内 .", "");
		}
		if(unloadEndTime.compareTo(endDate) == -1) {
			//如果unloadEndTime小于endDate说明endDate选择的有误
			LOGGER.error("保存失败, 离开任务时间不在任务时间范围内 .");
			throw new TfrBusinessException("保存失败, 离开任务时间不在任务时间范围内 .", "");
		}
		BigDecimal totWeight = loaderWorkloadDto.getTotWeightTemp();
		Integer totWaybillQty = loaderWorkloadDto.getTotWaybillQtyTemp();
		BigDecimal volumetot = BigDecimal.ZERO;
		BigDecimal weighttot = BigDecimal.ZERO;
		Integer goodsQtytot = 0;
		//因为需要装卸车货量的其他信息, 所以直接从数据库中查一个出来
//		LoaderWorkloadEntity loaderWorkload = getLoaderWorkloadByTaskNo(taskNo);

		//sonar 218427 ==修改为
		if(StringUtils.equals(loaderWorkloadDto.getDataSource(), "WUKONG")){
		if("WUKONG".equals(loaderWorkloadDto.getDataSource())){
			BigDecimal weight = BigDecimal.ZERO;
			BigDecimal volume = BigDecimal.ZERO;
			
			Integer waybillQty = 0;
			weight = weight.add(loaderWorkloadDto.getWeight());
			waybillQty = waybillQty + loaderWorkloadDto.getWaybillQty();
			volume = volume.add(loaderWorkloadDto.getVolume());
			for(LoaderWorkloadDto lw : loaderWorkloadDtos){
				weight=weight.add(lw.getWeight());
				volume = volume.add(lw.getVolume());
				waybillQty = waybillQty+lw.getWaybillQty();
			if(lw.getWeightTotal().compareTo(BigDecimal.ZERO)>0){
				weighttot=lw.getWeightTotal(); 
			}
			if(lw.getVolumeTotal().compareTo(BigDecimal.ZERO)>0){
				volumetot=lw.getVolumeTotal(); 
			}
			if(lw.getGoodsQtyTotal()>0){
				goodsQtytot=lw.getGoodsQtyTotal(); 
			}
			}
			if(weight.compareTo(weighttot)>0){
				LOGGER.error("保存失败, 理货员重量不能大于总重量.");
				throw new TfrBusinessException("保存失败, 理货员重量不能大于总重量.", "");
			}
			if(volume.compareTo(volumetot)>0){
				LOGGER.error("保存失败, 理货员体积不能大于总体积.");
				throw new TfrBusinessException("保存失败, 理货员体积不能大于总体积.", "");
			}
			if(waybillQty > goodsQtytot) {
				LOGGER.error("保存失败, 理货员票数不能大于总票数.");
				throw new TfrBusinessException("保存失败, 理货员票数不能大于总票数.", "");
			}
			loaderWorkload.setId(UUIDUtils.getUUID());
			loaderWorkload.setLoaderCode(loaderWorkloadDto.getLoaderCode());
			loaderWorkload.setLoaderName(loaderWorkloadDto.getLoaderName());
			loaderWorkload.setWeight(loaderWorkloadDto.getWeight());
			loaderWorkload.setWaybillQty(loaderWorkloadDto.getWaybillQty());
			loaderWorkload.setLoaderOrgCode(loaderWorkloadDto.getLoaderOrgCode());
			loaderWorkload.setLoaderOrgName(loaderWorkloadDto.getLoaderOrgName());
			loaderWorkload.setNotes(loaderWorkloadDto.getNotes());
			loaderWorkload.setJoinTime(DateUtils.convert(loaderWorkloadDto.getBeginDate()));
			loaderWorkload.setLeaveTime(DateUtils.convert(loaderWorkloadDto.getEndDate()));
			loaderWorkload.setAdjusterCode(FossUserContext.getCurrentUser().getEmpCode());
			loaderWorkload.setAdjusterName(FossUserContext.getCurrentUser().getEmpName());
			loaderWorkload.setAdjustTime(new Date());
			loaderWorkload.setAdjustOrgCode(FossUserContext.getCurrentDept().getCode());
			loaderWorkload.setAdjustOrgName(FossUserContext.getCurrentDept().getName());
			LoaderWorkloadEntity loaderWorkloadEntity = getLoaderWorkloadByTaskNoExpress(taskNo);
			loaderWorkload.setGoodsType(loaderWorkloadEntity.getGoodsType());
			loaderWorkload.setExpressOrLd(loaderWorkloadEntity.getExpressOrLd());
			loaderWorkload.setGoodsQty(loaderWorkloadDto.getGoodsQty());
			loaderWorkload.setExpressOrLd(loaderWorkloadEntity.getExpressOrLd());
			loaderWorkload.setDataSource(loaderWorkloadDto.getDataSource());
			loaderWorkload.setTaskEndTimeWK(loaderWorkloadDto.getTaskEndTimeWK());
			loaderWorkload.setVolumeTotal(volumetot);
			loaderWorkload.setWeightTotal(weighttot);
			loaderWorkload.setGoodsQtyTotal(goodsQtytot);
			loaderWorkloadModifyDao.saveLoaderWorkWK(loaderWorkload);
			
		}
		
		else{

		BigDecimal weight = BigDecimal.ZERO;
		Integer waybillQty = 0;
		weight = weight.add(loaderWorkloadDto.getWeight());
		waybillQty = waybillQty + loaderWorkloadDto.getWaybillQty();
		for(LoaderWorkloadDto lw : loaderWorkloadDtos) {
			weight = weight.add(lw.getWeight());
			waybillQty = waybillQty + lw.getWaybillQty();
		}
		if(weight.compareTo(totWeight) > 0) {
			LOGGER.error("保存失败, 理货员重量不能大于总重量.");
			throw new TfrBusinessException("保存失败, 理货员重量不能大于总重量.", "");
		}
		if(waybillQty > totWaybillQty) {
			LOGGER.error("保存失败, 理货员票数不能大于总票数.");
			throw new TfrBusinessException("保存失败, 理货员票数不能大于总票数.", "");
		}

		loaderWorkload.setId(UUIDUtils.getUUID());
		loaderWorkload.setLoaderCode(loaderWorkloadDto.getLoaderCode());
		loaderWorkload.setLoaderName(loaderWorkloadDto.getLoaderName());
		loaderWorkload.setWeight(loaderWorkloadDto.getWeight());
		loaderWorkload.setWaybillQty(loaderWorkloadDto.getWaybillQty());
		loaderWorkload.setLoaderOrgCode(loaderWorkloadDto.getLoaderOrgCode());
		loaderWorkload.setLoaderOrgName(loaderWorkloadDto.getLoaderOrgName());
		loaderWorkload.setNotes(loaderWorkloadDto.getNotes());
		loaderWorkload.setJoinTime(DateUtils.convert(loaderWorkloadDto.getBeginDate()));
		loaderWorkload.setLeaveTime(DateUtils.convert(loaderWorkloadDto.getEndDate()));
		loaderWorkload.setAdjusterCode(FossUserContext.getCurrentUser().getEmpCode());
		loaderWorkload.setAdjusterName(FossUserContext.getCurrentUser().getEmpName());
		loaderWorkload.setAdjustTime(new Date());
		loaderWorkload.setAdjustOrgCode(FossUserContext.getCurrentDept().getCode());
		loaderWorkload.setAdjustOrgName(FossUserContext.getCurrentDept().getName());
		LoaderWorkloadEntity loaderWorkloadEntity = getLoaderWorkloadByTaskNoExpress(taskNo);
		loaderWorkload.setGoodsType(loaderWorkloadEntity.getGoodsType());
		loaderWorkload.setExpressOrLd(loaderWorkloadEntity.getExpressOrLd());
		loaderWorkload.setGoodsQty(loaderWorkloadDto.getGoodsQty());
		loaderWorkload.setExpressOrLd(loaderWorkloadEntity.getExpressOrLd());
		loaderWorkloadModifyDao.saveLoaderWorkExpress(loaderWorkload);

		}
		}
	}
	
	/**
	 * 修改理货员货量信息
	 * @author ibm-zhangyixin
	 * @date 2012-12-26 下午3:04:00
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ILoaderWorkloadModifyService#modifyLoaderWork(com.deppon.foss.module.transfer.load.api.shared.dto.LoaderWorkloadDto)
	 */
	@Transactional
	@Override
	public void modifyLoaderWork(LoaderWorkloadDto loaderWorkloadDto) {
		String id = loaderWorkloadDto.getId();
		BigDecimal totWeight = loaderWorkloadDto.getTotWeightTemp();
		Integer totWaybillQty = loaderWorkloadDto.getTotWaybillQtyTemp();
		
		LoaderWorkloadEntity loaderWorkload = queryLoaderWorksById(id);
		if(loaderWorkload == null) {
			LOGGER.error("修改失败, 装卸车任务号错误.");
			throw new TfrBusinessException("修改失败, 装卸车任务号错误.", "");
		}
//		Date unloadStartTime = null;
//		Date unloadEndTime = null;
//		if(StringUtils.equals(loaderWorkload.getHandleType(), LoadConstants.LOADER_WORKLOAD_HANDLE_TYPE_LOAD)) {
//			//如果操作类型是装车
//			LoadTaskDto loadTask = loadTaskQueryService.getLoadTaskByTaskNo(loaderWorkload.getTaskNo());
//			if(loadTask == null) {
//				LOGGER.error("保存失败, 任务号错误.");
//				throw new TfrBusinessException("保存失败, 装车任务号错误.", "");
//			}
//			unloadStartTime = DateUtils.convert(loadTask.getLoadStartTime());
//			unloadEndTime = DateUtils.convert(loadTask.getLoadEndTime());
//		} else {
//			//卸车
//			UnloadTaskEntity unloadTask = getUnloadTaskByUnloadTaskNo(loaderWorkload.getTaskNo());
//			if(unloadTask == null) {
//				LOGGER.error("保存失败, 任务号错误.");
//				throw new TfrBusinessException("保存失败, 卸车任务号错误.", "");
//			}
//			unloadStartTime = unloadTask.getUnloadStartTime();
//			unloadEndTime = unloadTask.getUnloadEndTime();
//		}
		
//		Date beginDate = DateUtils.convert(loaderWorkloadDto.getBeginDate());
//		Date endDate = DateUtils.convert(loaderWorkloadDto.getEndDate());
//		if(unloadStartTime.compareTo(beginDate) == 1) {
//			//如果unloadStartTime大于beginDate说明beginDate选择的有误
//			LOGGER.error("保存失败, 加入任务时间不在任务时间范围内 .");
//			throw new TfrBusinessException("保存失败, 加入任务时间不在任务时间范围内 .", "");
//		}
//		if(unloadEndTime.compareTo(endDate) == -1) {
//			//如果unloadEndTime小于endDate说明endDate选择的有误
//			LOGGER.error("保存失败, 离开任务时间不在任务时间范围内 .");
//			throw new TfrBusinessException("保存失败, 离开任务时间不在任务时间范围内 .", "");
//		}
		
		List<LoaderWorkloadDto> loaderWorkloadDtos = queryLoaderWorksByTaskNo(loaderWorkload.getTaskNo());
		BigDecimal weight = BigDecimal.ZERO;
		Integer waybillQty = 0;
		weight = weight.add(loaderWorkloadDto.getWeight());
		waybillQty = waybillQty + loaderWorkloadDto.getWaybillQty();
		for(LoaderWorkloadDto lw : loaderWorkloadDtos) {
			weight = weight.add(lw.getWeight());
			waybillQty = waybillQty + lw.getWaybillQty();
		}
		if(weight.compareTo(totWeight) > 0) {
			LOGGER.error("保存失败, 理货员重量不能大于总重量.");
			throw new TfrBusinessException("保存失败, 理货员重量不能大于总重量.", "");
		}
		if(waybillQty > totWaybillQty) {
			LOGGER.error("保存失败, 理货员票数不能大于总票数.");
			throw new TfrBusinessException("保存失败, 理货员票数不能大于总票数.", "");
		}
		
//		loaderWorkload.setLoaderCode(loaderWorkloadDto.getLoaderCode());
//		loaderWorkload.setLoaderName(loaderWorkloadDto.getLoaderName());
		loaderWorkload.setWeight(loaderWorkloadDto.getWeight());
		loaderWorkload.setWaybillQty(loaderWorkloadDto.getWaybillQty());
//		loaderWorkload.setLoaderOrgCode(loaderWorkloadDto.getLoaderOrgCode());
//		loaderWorkload.setLoaderOrgName(loaderWorkloadDto.getLoaderOrgName());
//		loaderWorkload.setNotes(loaderWorkloadDto.getNotes());
//		loaderWorkload.setJoinTime(DateUtils.convert(loaderWorkloadDto.getBeginDate()));
//		loaderWorkload.setLeaveTime(DateUtils.convert(loaderWorkloadDto.getEndDate()));
		loaderWorkload.setAdjusterCode(FossUserContext.getCurrentUser().getEmpCode());
		loaderWorkload.setAdjusterName(FossUserContext.getCurrentUser().getEmpName());
		loaderWorkload.setAdjustTime(new Date());
		loaderWorkload.setAdjustOrgCode(FossUserContext.getCurrentDept().getCode());
		loaderWorkload.setAdjustOrgName(FossUserContext.getCurrentDept().getName());
		loaderWorkloadModifyDao.modifyLoaderWork(loaderWorkload);
	}
	
	/**
	 * 修改理货员货量信息(快递)
	 * @author zhangpeng
	 * @date 2015-12-07 下午3:04:00
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ILoaderWorkloadModifyService#modifyLoaderWork(com.deppon.foss.module.transfer.load.api.shared.dto.LoaderWorkloadDto)
	 */
	@Transactional
	@Override
	public void modifyLoaderWorkExpress(LoaderWorkloadDto loaderWorkloadDto) {
		String id = loaderWorkloadDto.getId();
		BigDecimal totWeight = loaderWorkloadDto.getTotWeightTemp();
		Integer totWaybillQty = loaderWorkloadDto.getTotWaybillQtyTemp();
		
		LoaderWorkloadEntity loaderWorkload = queryLoaderWorksByIdExpress(id);
		if(loaderWorkload == null) {
			LOGGER.error("修改失败, 装卸车任务号错误.");
			throw new TfrBusinessException("修改失败, 装卸车任务号错误.", "");
		}

		List<LoaderWorkloadDto> loaderWorkloadDtos = queryLoaderWorksByTaskNoExpress(loaderWorkload.getTaskNo());

		//sonar优化 218427 ==修改为 equals
		if(StringUtils.equals(loaderWorkloadDto.getDataSource(), "WUKONG")){		
		if("WUKONG".equals(loaderWorkloadDto.getDataSource())){
			BigDecimal weight = BigDecimal.ZERO;
			BigDecimal volume = BigDecimal.ZERO;
			Integer waybillQty = 0;
			weight = weight.add(loaderWorkloadDto.getWeight());
			waybillQty = waybillQty + loaderWorkloadDto.getWaybillQty();
			volume = volume.add(loaderWorkloadDto.getVolume());
			for(LoaderWorkloadDto lw : loaderWorkloadDtos){
				weight=weight.add(lw.getWeight());
				volume = volume.add(lw.getVolume());
				waybillQty = waybillQty+lw.getWaybillQty();
			}
			if(weight.compareTo(loaderWorkloadDto.getWeight())>0){
				LOGGER.error("保存失败, 理货员重量不能大于总重量.");
				throw new TfrBusinessException("保存失败, 理货员重量不能大于总重量.", "");
			}
			if(volume.compareTo(loaderWorkloadDto.getVolumeTotal())>0){
				LOGGER.error("保存失败, 理货员体积不能大于总体积.");
				throw new TfrBusinessException("保存失败, 理货员体积不能大于总体积.", "");
			}
			if(waybillQty > loaderWorkloadDto.getGoodsQtyTotal()) {
				LOGGER.error("保存失败, 理货员票数不能大于总票数.");
				throw new TfrBusinessException("保存失败, 理货员票数不能大于总票数.", "");
			}
			loaderWorkload.setWeight(loaderWorkloadDto.getWeight());
			loaderWorkload.setWaybillQty(loaderWorkloadDto.getWaybillQty());
			loaderWorkload.setAdjusterCode(FossUserContext.getCurrentUser().getEmpCode());
			loaderWorkload.setAdjusterName(FossUserContext.getCurrentUser().getEmpName());
			loaderWorkload.setAdjustTime(new Date());
			loaderWorkload.setAdjustOrgCode(FossUserContext.getCurrentDept().getCode());
			loaderWorkload.setAdjustOrgName(FossUserContext.getCurrentDept().getName());
			loaderWorkload.setGoodsQty(loaderWorkloadDto.getGoodsQty());
			loaderWorkloadModifyDao.modifyLoaderWorkWK(loaderWorkload);
			
		}else{
			
		BigDecimal weight = BigDecimal.ZERO;
		Integer waybillQty = 0;
		weight = weight.add(loaderWorkloadDto.getWeight());
		waybillQty = waybillQty + loaderWorkloadDto.getWaybillQty();
		for(LoaderWorkloadDto lw : loaderWorkloadDtos) {
			weight = weight.add(lw.getWeight());
			waybillQty = waybillQty + lw.getWaybillQty();
		}
		if(weight.compareTo(totWeight) > 0) {
			LOGGER.error("保存失败, 理货员重量不能大于总重量.");
			throw new TfrBusinessException("保存失败, 理货员重量不能大于总重量.", "");
		}
		if(waybillQty > totWaybillQty) {
			LOGGER.error("保存失败, 理货员票数不能大于总票数.");
			throw new TfrBusinessException("保存失败, 理货员票数不能大于总票数.", "");
		}
		
		loaderWorkload.setWeight(loaderWorkloadDto.getWeight());
		loaderWorkload.setWaybillQty(loaderWorkloadDto.getWaybillQty());
		loaderWorkload.setAdjusterCode(FossUserContext.getCurrentUser().getEmpCode());
		loaderWorkload.setAdjusterName(FossUserContext.getCurrentUser().getEmpName());
		loaderWorkload.setAdjustTime(new Date());
		loaderWorkload.setAdjustOrgCode(FossUserContext.getCurrentDept().getCode());
		loaderWorkload.setAdjustOrgName(FossUserContext.getCurrentDept().getName());
		loaderWorkload.setGoodsQty(loaderWorkloadDto.getGoodsQty());
		loaderWorkloadModifyDao.modifyLoaderWork(loaderWorkload);
		}
		}
	}
	
	/**
	 * 根据装卸车工作量TaskNo获取装卸车工作量信息
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 下午4:31:33
	 */
	private LoaderWorkloadEntity getLoaderWorkloadByTaskNo(String taskNo) {
		return loaderWorkloadModifyDao.getLoaderWorkloadByTaskNo(taskNo);
	}
	
	/**
	 * 根据装卸车工作量TaskNo获取装卸车工作量信息(快递)
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 下午4:31:33
	 */
	private LoaderWorkloadEntity getLoaderWorkloadByTaskNoExpress(String taskNo) {
		return loaderWorkloadModifyDao.getLoaderWorkloadByTaskNoExpress(taskNo);
	}
	
	
	/**
	 * 根据装卸车工作量ID获取装卸车工作量信息
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 下午4:31:33
	 */
	@Override
	public LoaderWorkloadEntity queryLoaderWorksById(String id) {
		return loaderWorkloadModifyDao.queryLoaderWorksById(id);
	}

	/**   
	 * @param loadTaskQueryService the loadTaskQueryService to set
	 * Date:2013-6-3下午1:56:24
	 */
	public void setLoadTaskQueryService(ILoadTaskQueryService loadTaskQueryService) {
		this.loadTaskQueryService = loadTaskQueryService;
	}

	
	/**
	 * 根据装卸车工作量ID获取装卸车工作量信息(快递)
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 下午4:31:33
	 */
	@Override
	public LoaderWorkloadEntity queryLoaderWorksByIdExpress(String id) {
		return loaderWorkloadModifyDao.queryLoaderWorksByIdExpress(id);
		
		
		
	
	}




	

	
}