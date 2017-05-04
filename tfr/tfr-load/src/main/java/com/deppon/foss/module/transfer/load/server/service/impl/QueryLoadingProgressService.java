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
package com.deppon.foss.module.transfer.load.server.service.impl;

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
import com.deppon.foss.module.transfer.load.api.server.dao.IQueryLoadingProgressDao;
import com.deppon.foss.module.transfer.load.api.server.service.IQueryLoadingProgressService;
import com.deppon.foss.module.transfer.load.api.shared.define.TaskProgressConstants;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryLoadingProgressConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryLoadingProgressResultDto;
import com.deppon.foss.module.transfer.load.api.shared.exception.QueryProgressException;

/**
 * 查询装车进度service实现类
 * 
 * @author 046130-foss-xuduowei
 * @date 2012-11-19 下午4:44:31
 */
public class QueryLoadingProgressService implements
		IQueryLoadingProgressService {
	/**
	 *  数据访问层接口
	 */
	private IQueryLoadingProgressDao queryLoadingProgressDao;
	/**
	 *  车辆接口
	 */
	private IOwnVehicleService ownVehicleService;
	/**
	 *  获取指定部门接口
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	private List<QueryLoadingProgressResultDto> queryLoadingProgressResultList;

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
	public List<QueryLoadingProgressResultDto> queryLoadingProgressInfo(
			QueryLoadingProgressConditionDto queryLoadingProgressConditionDto, int limit, int start) {
		// 获取处理后的查询条件
		QueryLoadingProgressConditionDto conDto = handleQueryCondition(queryLoadingProgressConditionDto);

		 queryLoadingProgressResultList = queryLoadingProgressDao
				.queryLoadingProgressInfo(conDto, limit, start);

		for (int i = 0; i < queryLoadingProgressResultList.size(); i++) {
			QueryLoadingProgressResultDto resultDto = queryLoadingProgressResultList.get(i);
					
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
						queryLoadingProgressResultList.get(i).setRatedWeight(ratedWeight);
						if (StringUtils.equals(vehicleBusinessType,	DictionaryValueConstants.DATA_OWNERSHIP_TYPE_LEASED)) {
							queryLoadingProgressResultList.get(i).setRatedVolume(ratedVolume);
						} else {
							// 无操作
						}
					} else {
						queryLoadingProgressResultList.get(i).setRatedVolume(ratedVolume);
						queryLoadingProgressResultList.get(i).setRatedWeight(ratedWeight);
					}
				}
			} else {
				// 无操作
			}
			//额定净空
			BigDecimal rVolume = null;
			if(null == queryLoadingProgressResultList.get(i).getRatedVolume()){
				rVolume = BigDecimal.ZERO;
			}else{
				rVolume = queryLoadingProgressResultList.get(i).getRatedVolume();
			}
			//已装体积
			BigDecimal lVolume = resultDto.getLoadedVolume();
			//额定载重
			BigDecimal rWeight = null;
			if(null == queryLoadingProgressResultList.get(i).getRatedWeight()){
				rWeight = BigDecimal.ZERO;
			}else{
				rWeight = queryLoadingProgressResultList.get(i).getRatedWeight();
			}
			//已装重量
			BigDecimal lWeight = resultDto.getLoadedWeight();
			//剩余体积
			if(rVolume!=null && lVolume!=null){
				queryLoadingProgressResultList.get(i).setLeftVolume(rVolume.subtract(lVolume));
			}else if(lVolume==null){
				queryLoadingProgressResultList.get(i).setLeftVolume(rVolume);
			}
			
			//额定净空 和 已装体积 都为NULL则都初始化为0.
			if(null == lVolume && null == rVolume){
				queryLoadingProgressResultList.get(i).setLeftVolume(BigDecimal.ZERO);
				queryLoadingProgressResultList.get(i).setRatedVolume(BigDecimal.ZERO);
			}
			
			//剩余重量
			if(rWeight!=null && lWeight!=null){
				queryLoadingProgressResultList.get(i).setLeftWeight(rWeight.subtract(lWeight));
			}else if(lWeight == null){
				queryLoadingProgressResultList.get(i).setLeftWeight(rWeight);
			}
			//额定载重和已装重量都为NULL则都初始化为0.
			if(null == rWeight && null == lWeight){
				queryLoadingProgressResultList.get(i).setLeftWeight(BigDecimal.ZERO);
			}
		}

		List<QueryLoadingProgressResultDto> queryLoadingProgressResult = 
				handleLoadingProgressResult(queryLoadingProgressResultList,conDto,limit,start);

		return queryLoadingProgressResult;
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
			QueryLoadingProgressConditionDto queryLoadingProgressConditionDto,int limit,int start) {
		if(null ==queryLoadingProgressResultList){
			
			// 获取处理后的查询条件
			QueryLoadingProgressConditionDto conDto = handleQueryCondition(queryLoadingProgressConditionDto);

			return queryLoadingProgressDao
					.queryLoadingProgressInfo(conDto, limit, start).size();
		}
		return queryLoadingProgressResultList.size();
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
	private List<QueryLoadingProgressResultDto> handleLoadingProgressResult(
			List<QueryLoadingProgressResultDto> queryLoadingProgressResultList,QueryLoadingProgressConditionDto conDto, int limit, int start) {
		// 设置百分比格式
		DecimalFormat df = new DecimalFormat(TaskProgressConstants.QUERY_LOAD_PROGRESS_PERCENT);
		for (int i = 0; i < queryLoadingProgressResultList.size(); i++) {
			QueryLoadingProgressResultDto qprDto = queryLoadingProgressResultList
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
				queryLoadingProgressResultList.get(i).setVolumeProgress(
						df.format(volume));

			}else{
				queryLoadingProgressResultList.get(i).setVolumeProgress("0%");
			}
			// 设置重量百分比
			if (loadedWeight != null && ratedWeight != null && BigDecimal.ZERO.compareTo(ratedWeight)==-1) {
				double weight = loadedWeight.divide(ratedWeight, 2,
						BigDecimal.ROUND_HALF_UP).doubleValue();
				queryLoadingProgressResultList.get(i).setWeightProgress(
						df.format(weight));
			}else{
				queryLoadingProgressResultList.get(i).setWeightProgress("0%");
			}

		}
		
		return this.sort(queryLoadingProgressResultList, conDto, limit, start);
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
	private QueryLoadingProgressConditionDto handleQueryCondition(
			QueryLoadingProgressConditionDto queryLoadingProgressConditionDto) {
		//设置当前部门code
		String deptCode = obtainCurrentDeptCode();
		queryLoadingProgressConditionDto.setDeptCode(deptCode);
		//设置当前时间
		queryLoadingProgressConditionDto.setCurrentDate(new Date());
		// 排序方向
		String sequence = queryLoadingProgressConditionDto.getSequence();
		// 排序字段
		String sequenceType = queryLoadingProgressConditionDto.getSequenceType();
		// 拼接形成排序规则
		String sortRule = sequenceType + "_" + sequence;
		queryLoadingProgressConditionDto.setSortRule(sortRule);
		String all = "ALL";
		if(all.equals(queryLoadingProgressConditionDto.getTaskState())){
			queryLoadingProgressConditionDto.setTaskState(null);
		}
		if(all.equals(queryLoadingProgressConditionDto.getTaskType())){
			queryLoadingProgressConditionDto.setTaskType(null);
		}
		if(all.equals(queryLoadingProgressConditionDto.getTimeOver())){
			queryLoadingProgressConditionDto.setTimeOver(null);
		}
		return queryLoadingProgressConditionDto;
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
	private List<QueryLoadingProgressResultDto> sort(
			List<QueryLoadingProgressResultDto> queryloadingprogressresultlist,
			QueryLoadingProgressConditionDto conDto, int limit, int start) {
		
		if(null == queryloadingprogressresultlist){
			return null;
		}
		
		String asc="ASC";
		
		//TEMP
		QueryLoadingProgressResultDto qlprd = null;
		if(StringUtils.equals(conDto.getSequenceType(), "ORDER_BY_PLANDEPARTDATE")){
			
			//存放 计划发车时间 为null的所有数据
			List<QueryLoadingProgressResultDto> queryLoadingProgressResultListNull = new ArrayList<QueryLoadingProgressResultDto>();			
			//存放 计划发车时间不为null的所有数据
			List<QueryLoadingProgressResultDto> queryLoadingProgressResultListNotNull = new ArrayList<QueryLoadingProgressResultDto>();			
			
			//循环把所有的 计划发车时间 为 null 与 not null 的数据分开
			for(int i = 0; i < queryloadingprogressresultlist.size(); i++){
				QueryLoadingProgressResultDto ii = queryloadingprogressresultlist.get(i);
				
				if(null == ii.getPlanDepartDate()){
					queryLoadingProgressResultListNull.add(ii);
				}else{
					queryLoadingProgressResultListNotNull.add(ii);
				}
			}
			
			//冒泡循环
			for(int i = 0; i < queryLoadingProgressResultListNotNull.size(); i++){
				for(int j = 0; j < queryLoadingProgressResultListNotNull.size(); j++){
					QueryLoadingProgressResultDto ii = queryLoadingProgressResultListNotNull.get(i);
					QueryLoadingProgressResultDto jj = queryLoadingProgressResultListNotNull.get(j);
					
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
		}else if(StringUtils.equals(conDto.getSequenceType(), "ORDER_BY_LEFTWEIGHT")){
			for(int i = 0; i < queryloadingprogressresultlist.size(); i++){
				for(int j = 0; j < queryloadingprogressresultlist.size(); j++){
					QueryLoadingProgressResultDto ii = queryloadingprogressresultlist.get(i);
					QueryLoadingProgressResultDto jj = queryloadingprogressresultlist.get(j);
					if(jj.getLeftWeight().compareTo(ii.getLeftWeight())==-1){
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
		}else if(StringUtils.equals(conDto.getSequenceType(), "ORDER_BY_LEFTVOLUME")){
			for(int i = 0; i < queryloadingprogressresultlist.size(); i++){
				for(int j = 0; j < queryloadingprogressresultlist.size(); j++){
					QueryLoadingProgressResultDto ii = queryloadingprogressresultlist.get(i);
					QueryLoadingProgressResultDto jj = queryloadingprogressresultlist.get(j);
					if(jj.getLeftVolume().compareTo(ii.getLeftVolume())==-1){
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
					QueryLoadingProgressResultDto ii = queryloadingprogressresultlist.get(i);
					QueryLoadingProgressResultDto jj = queryloadingprogressresultlist.get(j);
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
		
		List<QueryLoadingProgressResultDto> queryloadingprogressresultlistTemp=new ArrayList<QueryLoadingProgressResultDto>();
		
		//设定截取的数据 
		int limitTemp= start+limit;
		if(queryloadingprogressresultlist.size()<limitTemp){
			limitTemp=queryloadingprogressresultlist.size();
		}
		for(int i=start;i<limitTemp;i++){
			queryloadingprogressresultlistTemp.add(queryloadingprogressresultlist.get(i));
		}
		if(queryloadingprogressresultlistTemp.size()==0){
			queryloadingprogressresultlistTemp=new ArrayList<QueryLoadingProgressResultDto>(0);
		}
		return queryloadingprogressresultlistTemp;
	}

	/**
	 * 设置 数据访问层接口.
	 *
	 * @param queryLoadingProgressDao the new 数据访问层接口
	 */
	public void setQueryLoadingProgressDao(
			IQueryLoadingProgressDao queryLoadingProgressDao) {
		this.queryLoadingProgressDao = queryLoadingProgressDao;
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
	 * 设置 数据缓存
	 *
	 * @param queryLoadingProgressResultList the new 数据缓存
	 */
	public void setQueryLoadingProgressResultList(
			List<QueryLoadingProgressResultDto> queryLoadingProgressResultList) {
		this.queryLoadingProgressResultList = queryLoadingProgressResultList;
	}

	/**
	 * 取得 数据缓存
	 *
	 * @param queryLoadingProgressResultList the new 数据缓存
	 */
	public List<QueryLoadingProgressResultDto> getQueryLoadingProgressResultList() {
		return queryLoadingProgressResultList;
	}
	
	

}