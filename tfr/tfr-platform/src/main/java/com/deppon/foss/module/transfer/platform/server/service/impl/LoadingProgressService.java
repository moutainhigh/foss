/**
 *  initial comments.
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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/service/impl/QueryLoadingProgressService.java
 *  
 *  FILE NAME          :QueryLoadingProgressService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-load
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.server.service.impl
 * FILE    NAME: QueryLoadingProgressService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.platform.server.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOwnVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.platform.api.server.dao.ILoadingProgressDao;
import com.deppon.foss.module.transfer.platform.api.server.service.ILoadingProgressService;
import com.deppon.foss.module.transfer.platform.api.shared.define.LoadingProgressConstants;
import com.deppon.foss.module.transfer.platform.api.shared.dto.LoadingProgressConditionDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.LoadingProgressResultDto;
import com.deppon.foss.module.transfer.platform.api.shared.exception.QueryProgressException;
import com.deppon.foss.module.transfer.scheduling.api.shared.exception.ParameterException;

/**
 * 查询装车进度service实现类
 * 
 * @author 046130-foss-xuduowei
 * @date 2012-11-19 下午4:44:31
 */
public class LoadingProgressService implements
		ILoadingProgressService {
	/**
	 *  数据访问层接口
	 */
	private ILoadingProgressDao loadingProgressDao;
	/**
	 *  车辆接口
	 */
	private IOwnVehicleService ownVehicleService;
	/**
	 *  获取指定部门接口
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	private List<LoadingProgressResultDto> loadingProgressResultList;

	/**
	 * 查询装车进度service
	 * 
	 * @param queryLoadingProgressConditionDto
	 *            查询条件
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-19 下午4:44:33
	 * @see com.deppon.foss.module.transfer.load.api.server.service.
	 *      IQueryLoadingProgressService#queryLoadingProgressInfo(
	 *      com.deppon.foss.module.transfer.load.api.shared.dto.QueryLoadingProgressConditionDto)
	 */
	@Override
	public List<LoadingProgressResultDto> queryLoadingProgressInfo(
			LoadingProgressConditionDto loadingProgressConditionDto, int limit, int start) {
		// 获取处理后的查询条件
		LoadingProgressConditionDto conDto = handleQueryCondition(loadingProgressConditionDto);

		 loadingProgressResultList = loadingProgressDao
				.queryLoadingProgressInfo(conDto, limit, start);
		 
		 for (int i = 0; i < loadingProgressResultList.size(); i++) {
				LoadingProgressResultDto resultDto = loadingProgressResultList.get(i);
				//车牌号
				String vehicleNo = resultDto.getVehicleNumber();
				if (!StringUtils.isEmpty(vehicleNo)) {
					String[] vehicleNos = new String[1];
					vehicleNos[0] = vehicleNo;
					List<VehicleAssociationDto> vehicleList = ownVehicleService.queryOwnVehicleAssociationDtosByVehicleNos(vehicleNos);
					if (vehicleList.size() > 0) {
						VehicleAssociationDto vehicleDto = vehicleList.get(0);
						// 最大净空
						BigDecimal ratedVolume = vehicleDto.getVehicleSelfVolume();
						// 最大载重
						BigDecimal ratedWeight = vehicleDto.getVehicleDeadLoad();
						// 车辆使用类型 短途或长途
						String vehicleUseType = vehicleDto.getVehicleUsedTypeCode();
						// 车辆所属类型 公司车或外请车
						String vehicleBusinessType = vehicleDto.getVehicleOwnershipType();
						/*
						 * 若车辆类型为短途，则额定净空、额定载重为车辆基础资料中额定净空、额定载重
						 * 若车辆类型为长途，则额定载重为车辆基础资料中额定载重，额定净空
						 * 若为公司车辆， 则额定净空为发车计划中该车柜额定净空，
						 * 若为外请车，则为外请车基础资料中额定净空
						 */
						if (StringUtils.equals(vehicleUseType, DictionaryValueConstants.BES_VEHICLE_USED_TYPE_COACH)) {
							resultDto.setRatedWeight(ratedWeight);
							if (StringUtils.equals(vehicleBusinessType,	DictionaryValueConstants.DATA_OWNERSHIP_TYPE_LEASED)) {
								resultDto.setRatedVolume(ratedVolume);
							} else {
								// 无操作
							}
						} else {
							resultDto.setRatedVolume(ratedVolume);
							resultDto.setRatedWeight(ratedWeight);
						}
					}
				} else {
					// 无操作
				}
				//额定净空
				BigDecimal rVolume = null;
				if(null == resultDto.getRatedVolume()){
					rVolume = BigDecimal.ZERO;
				}else{
					rVolume = resultDto.getRatedVolume();
				}
				//已装体积
				BigDecimal lVolume = resultDto.getLoadedVolume();
				//额定载重
				BigDecimal rWeight = null;
				if(null == resultDto.getRatedWeight()){
					rWeight = BigDecimal.ZERO;
				}else{
					rWeight = resultDto.getRatedWeight();
				}
				//已装重量
				BigDecimal lWeight = resultDto.getLoadedWeight();
				//剩余体积
				if(rVolume!=null && lVolume!=null){
					resultDto.setLeftVolume(rVolume.subtract(lVolume));
				}else if(lVolume==null){
					resultDto.setLeftVolume(rVolume);
				}
				
				//额定净空 和 已装体积 都为NULL则都初始化为0.
				if(null == lVolume && null == rVolume){
					resultDto.setLeftVolume(BigDecimal.ZERO);
					resultDto.setRatedVolume(BigDecimal.ZERO);
				}
				
				//剩余重量
				if(rWeight!=null && lWeight!=null){
					resultDto.setLeftWeight(rWeight.subtract(lWeight));
				}else if(lWeight == null){
					resultDto.setLeftWeight(rWeight);
				}
				//额定载重和已装重量都为NULL则都初始化为0.
				if(null == rWeight && null == lWeight){
					resultDto.setLeftWeight(BigDecimal.ZERO);
				}
			
			}

		
		List<LoadingProgressResultDto> loadingProgressResult = 
				handleLoadingProgressResult(loadingProgressResultList,conDto,limit,start);

		return loadingProgressResult;
	}
	
	/**
	 * 查询装车进度总数service
	 * 
	 * @param queryLoadingProgressConditionDto
	 *            查询条件
	 * @author 046130-foss-yuyongxiang
	 * @date 2013年4月15日 11:29:38
	 * @see com.deppon.foss.module.transfer.load.api.server.service.
	 *      IQueryLoadingProgressService#queryLoadingProgressInfoCount(
	 *      com.deppon.foss.module.transfer.load.api.shared.dto.QueryLoadingProgressConditionDto)
	 */
	@Override
	public Integer queryLoadingProgressInfoCount(
			LoadingProgressConditionDto loadingProgressConditionDto,int limit,int start) {
		if(null ==loadingProgressResultList){
			
			// 获取处理后的查询条件
			LoadingProgressConditionDto conDto = handleQueryCondition(loadingProgressConditionDto);

			return loadingProgressDao
					.queryLoadingProgressInfo(conDto, limit, start).size();
		}
		return loadingProgressResultList.size();
	}

	/**
	 * 
	 * 处理查询结果
	 * 
	 * @param queryLoadingProgressResultList
	 *            查询结果集
	 * @param QueryLoadingProgressConditionDto
	 * 			     查询装车进度条件
	 * @param limit
	 * 			     长度
	 * @param start
	 * 			     起始
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-22 下午3:13:03
	 */
	private List<LoadingProgressResultDto> handleLoadingProgressResult(
			List<LoadingProgressResultDto> loadingProgressResultList,LoadingProgressConditionDto conDto, int limit, int start) {
		// 设置百分比格式
		DecimalFormat df = new DecimalFormat(LoadingProgressConstants.QUERY_LOAD_PROGRESS_PERCENT);
		for (int i = 0; i < loadingProgressResultList.size(); i++) {
			LoadingProgressResultDto qprDto = loadingProgressResultList
					.get(i);
			// 已装体积
			BigDecimal loadedVolume = qprDto.getLoadedVolume();
			// 已装重量
			BigDecimal loadedWeight = qprDto.getLoadedWeight();
			// 额定体积
			BigDecimal ratedVolume = qprDto.getRatedVolume();
			// 额定重量
			BigDecimal ratedWeight = qprDto.getRatedWeight();

			// 设置体积百分比
			if (loadedVolume != null && ratedVolume != null && BigDecimal.ZERO.compareTo(ratedVolume)==-1) {
				double volume = loadedVolume.divide(ratedVolume, 2,
						BigDecimal.ROUND_HALF_UP).doubleValue();
				loadingProgressResultList.get(i).setVolumeProgress(
						df.format(volume));

			}else{
				loadingProgressResultList.get(i).setVolumeProgress("0%");
			}
			// 设置重量百分比
			if (loadedWeight != null && ratedWeight != null && BigDecimal.ZERO.compareTo(ratedWeight)==-1) {
				double weight = loadedWeight.divide(ratedWeight, 2,
						BigDecimal.ROUND_HALF_UP).doubleValue();
				loadingProgressResultList.get(i).setWeightProgress(
						df.format(weight));
			}else{
				loadingProgressResultList.get(i).setWeightProgress("0%");
			}

		}
		
		return this.sort(loadingProgressResultList, conDto, limit, start);
	}

	/**
	 * 
	 * 处理查询条件
	 * 
	 * @param queryLoadingProgressConditionDto
	 *            查询条件
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-22 下午3:14:35
	 */
	private LoadingProgressConditionDto handleQueryCondition(
			LoadingProgressConditionDto loadingProgressConditionDto) {
		//设置当前部门code
		String deptCode = obtainCurrentDeptCode();
		if(null == loadingProgressConditionDto){
			throw new ParameterException("传入装车查询信息为空"); 
		}
		loadingProgressConditionDto.setDeptCode(deptCode);
		//设置当前时间
		loadingProgressConditionDto.setCurrentDate(new Date());
		// 排序方向
		String sequence = loadingProgressConditionDto.getSequence();
		// 排序字段
		String sequenceType = loadingProgressConditionDto.getSequenceType();
		// 拼接形成排序规则
		String sortRule = sequenceType + "_" + sequence;
		loadingProgressConditionDto.setSortRule(sortRule);
		String all = "ALL";
//		if(ALL.equals(loadingProgressConditionDto.getTaskState())){
//			loadingProgressConditionDto.setTaskState(null);
//		}
		//shiwei 派送装车有submit状态，属于finished的一种，故传list，sql用in
		ArrayList<String> taskStateList = new ArrayList<String>();
		if(StringUtils.equals(loadingProgressConditionDto.getTaskState(), LoadingProgressConstants.FINISHED)){
			taskStateList.add(LoadingProgressConstants.FINISHED);
			taskStateList.add(LoadingProgressConstants.SUBMITED);
		}else{
			taskStateList.add(loadingProgressConditionDto.getTaskState());
		}
		loadingProgressConditionDto.setTaskStateList(taskStateList);
		
		if(all.equals(loadingProgressConditionDto.getTaskType())){
			loadingProgressConditionDto.setTaskType(null);
		}
		if(all.equals(loadingProgressConditionDto.getTimeOver())){
			loadingProgressConditionDto.setTimeOver(null);
		}
		return loadingProgressConditionDto;
	}

	/**
	 * 
	 * 获取当前登录人所在部门
	 * 
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-28 下午7:40:46
	 */
	private String obtainCurrentDeptCode() {
		// 当前操作部门
		List<String> bizTypes = new ArrayList<String>();
		// 设置外场类型
		bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		OrgAdministrativeInfoEntity activeDeptInfo = FossUserContext
				.getCurrentDept();
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoComplexService
				.queryOrgAdministrativeInfoByCode(activeDeptInfo.getCode(),
						bizTypes);
		if (org != null) {

			return org.getCode();
		} else {
			throw new QueryProgressException("当前登录人无操作部门");
		}
	}
	
	/**
	 * 
	 * 处理查询结果
	 * 
	 * @param queryLoadingProgressResultList
	 *            查询结果集
	 * @param QueryLoadingProgressConditionDto
	 * 			     查询装车进度条件
	 * @param limit
	 * 			     长度
	 * @param start
	 * 			     起始
	 * @author 137019-foss-yuyongxiang
	 * @date 2013年4月13日 11:32:50
	 */
	private List<LoadingProgressResultDto> sort(
			List<LoadingProgressResultDto> queryloadingprogressresultlist,
			LoadingProgressConditionDto conDto, int limit, int start) {
		
		if(null == queryloadingprogressresultlist){
			return null;
		}
		
		String asc="ASC";
		
		//TEMP
		LoadingProgressResultDto qlprd = null;
		if(StringUtils.equals(conDto.getSequenceType(), "ORDER_BY_PLANDEPARTDATE")){
			
			//存放 计划发车时间 为null的所有数据
			List<LoadingProgressResultDto> queryLoadingProgressResultListNull = new ArrayList<LoadingProgressResultDto>();			
			//存放 计划发车时间不为null的所有数据
			List<LoadingProgressResultDto> queryLoadingProgressResultListNotNull = new ArrayList<LoadingProgressResultDto>();			
			
			//循环把所有的 计划发车时间 为 null 与 not null 的数据分开
			for(int i = 0; i < queryloadingprogressresultlist.size(); i++){
				LoadingProgressResultDto ii = queryloadingprogressresultlist.get(i);
				
				if(null == ii.getPlanDepartDate()){
					queryLoadingProgressResultListNull.add(ii);
				}else{
					queryLoadingProgressResultListNotNull.add(ii);
				}
			}
			
			//冒泡循环
			for(int i = 0; i < queryLoadingProgressResultListNotNull.size(); i++){
				for(int j = 0; j < queryLoadingProgressResultListNotNull.size(); j++){
					LoadingProgressResultDto ii = queryLoadingProgressResultListNotNull.get(i);
					LoadingProgressResultDto jj = queryLoadingProgressResultListNotNull.get(j);
					
						if(jj.getPlanDepartDate().before(ii.getPlanDepartDate())){
							qlprd = ii;
							queryLoadingProgressResultListNotNull.set(i, jj);
							queryLoadingProgressResultListNotNull.set(j, qlprd);
						}
				}
			}
			
			//先清空queryloadingprogressresultlist
			queryloadingprogressresultlist.clear();
			//这个把所有的计划时间不为空的添加进queryloadingprogressresultlist
			queryloadingprogressresultlist.addAll(queryLoadingProgressResultListNotNull);
			//这个把所有的计划时间为空的添加进queryloadingprogressresultlist
			queryloadingprogressresultlist.addAll(queryLoadingProgressResultListNull);
		
			//if(StringUtils.equals(conDto.getSequence(),desc)){
				// 按计划发车时间降序
				// 不执行其他操作
			//}else 
			if(StringUtils.equals(conDto.getSequence(),asc)){
				//按计划发车时间升序
				//反转
				Collections.reverse(queryloadingprogressresultlist);
			}
		}else if(StringUtils.equals(conDto.getSequenceType(), "ORDER_BY_LOADEDWEIGHT")){
			for(int i = 0; i < queryloadingprogressresultlist.size(); i++){
				for(int j = 0; j < queryloadingprogressresultlist.size(); j++){
					LoadingProgressResultDto ii = queryloadingprogressresultlist.get(i);
					LoadingProgressResultDto jj = queryloadingprogressresultlist.get(j);
					if(jj.getLoadedWeight().compareTo(ii.getLoadedWeight())==-1){
						qlprd = ii;
						queryloadingprogressresultlist.set(i, jj);
						queryloadingprogressresultlist.set(j, qlprd);
					}
				}
			}
		
			//if(StringUtils.equals(conDto.getSequence(),desc)){
				// 按已装重量升序，剩余重量降序
				// 不执行其他操作
			//}else 
			if(StringUtils.equals(conDto.getSequence(),asc)){
				//按已装重量降序，剩余重量升序
				//反转
				Collections.reverse(queryloadingprogressresultlist);
			}
		}else if(StringUtils.equals(conDto.getSequenceType(), "ORDER_BY_LOADEDVOLUME")){
			for(int i = 0; i < queryloadingprogressresultlist.size(); i++){
				for(int j = 0; j < queryloadingprogressresultlist.size(); j++){
					LoadingProgressResultDto ii = queryloadingprogressresultlist.get(i);
					LoadingProgressResultDto jj = queryloadingprogressresultlist.get(j);
					if(jj.getLoadedVolume().compareTo(ii.getLoadedVolume())==-1){
						qlprd = ii;
						queryloadingprogressresultlist.set(i, jj);
						queryloadingprogressresultlist.set(j, qlprd);
					}
				}
			}
		
			//if(StringUtils.equals(conDto.getSequence(),desc)){
				// 按已装体积升序，剩余体积降序
				// 不执行其他操作
			//}else 
			if(StringUtils.equals(conDto.getSequence(),asc)){
				//按已装体积降序，剩余体积升序
				//反转
				Collections.reverse(queryloadingprogressresultlist);
			}
			
			//按建立任务时间排序(默认排序)
		}else{
			//if(StringUtils.equals(conDto.getSequenceType(), "ORDER_BY_CREATETASKDATE")){
			for(int i = 0; i < queryloadingprogressresultlist.size(); i++){
				for(int j = 0; j < queryloadingprogressresultlist.size(); j++){
					LoadingProgressResultDto ii = queryloadingprogressresultlist.get(i);
					LoadingProgressResultDto jj = queryloadingprogressresultlist.get(j);
							if(jj.getCreateTaskDate().before(ii.getCreateTaskDate())){
								qlprd = ii;
								queryloadingprogressresultlist.set(i, jj);
								queryloadingprogressresultlist.set(j, qlprd);
							}
						}
					}
					
					//if(StringUtils.equals(conDto.getSequence(),desc)){
						//<!-- 按建立任务时间降序 -->
						// 不执行其他操作
					//}else
					if(StringUtils.equals(conDto.getSequence(),asc)){
						////<!-- 按建立任务时间升序 -->
						//反转
						Collections.reverse(queryloadingprogressresultlist);
					}
					
					//按计划发车时间排序
		}
		
		List<LoadingProgressResultDto> queryloadingprogressresultlistTemp=new ArrayList<LoadingProgressResultDto>();
		
		//设定截取的数据 
		int limitTemp= start+limit;
		if(queryloadingprogressresultlist.size()<limitTemp){
			limitTemp=queryloadingprogressresultlist.size();
		}
		for(int i=start;i<limitTemp;i++){
			queryloadingprogressresultlistTemp.add(queryloadingprogressresultlist.get(i));
		}
		if(queryloadingprogressresultlistTemp.size()==0){
			queryloadingprogressresultlistTemp=new ArrayList<LoadingProgressResultDto>(0);
		}
		return queryloadingprogressresultlistTemp;
	}

	/**
	 * 设置 数据访问层接口.
	 *
	 * @param queryLoadingProgressDao the new 数据访问层接口
	 */
	public void setLoadingProgressDao(
			ILoadingProgressDao loadingProgressDao) {
		this.loadingProgressDao = loadingProgressDao;
	}

	/**
	 * 设置 车辆接口.
	 *
	 * @param ownVehicleService the new 车辆接口
	 */
	public void setOwnVehicleService(IOwnVehicleService ownVehicleService) {
		this.ownVehicleService = ownVehicleService;
	}

	/**
	 * 设置 获取指定部门接口.
	 *
	 * @param orgAdministrativeInfoComplexService the new 获取指定部门接口
	 */
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}

	/** 
	 * @return loadingProgressResultList 
	 */
	public List<LoadingProgressResultDto> getLoadingProgressResultList() {
		return loadingProgressResultList;
	}

	/** 
	 * @param loadingProgressResultList 要设置的 loadingProgressResultList 
	 */
	public void setLoadingProgressResultList(
			List<LoadingProgressResultDto> loadingProgressResultList) {
		this.loadingProgressResultList = loadingProgressResultList;
	}

}