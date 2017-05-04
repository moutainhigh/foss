/**
 * 
 * 
 * 
 * 
 *  initial comments.
 *  
 *  
 *  
 *  
 *  
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  
 *  
 * 
 *  you may not use this file except in compliance with the License.
 *  
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  
 *  
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 
 *  See the License for the specific language governing permissions and
 *  
 *  limitations under the License.
 *  
 * 
 *  
 *  Contributors:
 *  
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-unload
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/server/service/impl/QueryUnloadingProgressService.java
 *  
 *  FILE NAME          :QueryUnloadingProgressService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 ******************************************************************************/
/**
 * 
 * 
 * PROJECT NAME: tfr-unload
 * 
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.server.service.impl
 * 
 * FILE    NAME: QueryUnloadingProgressService.java
 * 
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 * 
 *
 *
 *相关业务用例
 *
 *	BUC_FOSS_5.30.10_150 查看卸车记录
 *
 *
 *
 *
 *
 *用例描述
 *
 *		调度分配卸货任务给理货员之后之后，装卸车组经理、车队经理、车队调度、场内调度、配载员可以监控卸车扫描进度
 *
 *
 *
 *
 *界面描述
 *		一、界面主要分为三个部分：
 *
 *		1、查询卸车进度查询条件：
 *
 *		单据编号:
 *			交接单单号、配载单单号、完成接货任务编号、拉回货任务编号，
 *
 *		车牌号：
 *			卸车车牌号，
 *
 *		出发部门：
 *			出发部门
 *
 *		卸车类型：
 *			长途卸车、短途卸车、集中卸车、全部，
 *
 *		卸车状态：
 *			未开始、进行中、已完成、全部，
 *
 *		建立任务时间：
 *			建立卸车任务时间
 *
 *		是否超时：
 *			是、否
 *		
 *
 *		2、卸车标准：车重量标准、卸车体积标准 
 *
 *		3、卸车进度列表：参见【卸车进度列表】
 *
 *	二：功能按钮：
 *			查询
 *
 *
 *
 *
 *
 *操作步骤
 *
 *
 *	1	进入查询卸车进度界面		
 *
 *			界面加载时默认显示本部门信息
 *
 *
 * 2	填写待卸车查询条件，点击“查询“按钮（参见业务规则SR-2，SR-6，SR-8）
 *		
 *			卸车进度列表	显示满足条件的卸车进度列表（参见业务规则SR-1、SR-3~5、SR-7、SR-9）
 *
 *
 *
 *
 *业务规则
 *
 *
 *	SR-1	
 *
 *		显示到达部门为本部门的已分配的交接单/配载单/接（转）货任务/拉回货任务
 *
 *	SR-2	
 *
 *		查询条件：建立任务时间为必填，默认为当天00:00:00-当天23:59:59 
 *
 *
 *	SR-3	
 *
 *		当卸车重量、卸车体积进度中有一个超过80%，该条记录用黄色标示出来
 *
 *
 *	SR-4	
 *	
 *			当卸车重量、卸车体积进度中有一个超过90%，该条记录用绿色标示出来
 *
 *
 *	SR-5	
 *
 *		卸车类型包括：长途卸车、短途卸车、集中卸车
 *
 *
 *	SR-6	
 *
 *		卸车重量标准、卸车体积标准读取于外场卸货标准基础资料，不可修改
 *
 *
 *	SR-7	
 *
 *		预计完成时间为当前时间+max（剩余体积/卸车体积标准，剩余重量/卸车重量标准）
 *
 *
 *	SR-8	
 *
 *		卸车状态为：进行中、已完成、全部，默认为全部
 *	
 *
 *	SR-11	
 *		
 *		计划完成时间：车辆到达时间+30min+max（总体积/卸车体积标准，总重量/卸车重量标准）
 *
 *	SR-12	
 *
 *		卸车完成时间为空时，若当前时间>计划完成时间，则是否超时为是，否则为否
 *
 *		卸车完成时间不为空时，若卸车完成时间>计划完成时间，则是否超时为是，否则为否
 *
 *		超时任务用红色标示出来
 *
 *
 *	SR-13	
 *
 *		当卸车重量、卸车体积进度都达到100%，该条记录用紫色标示出来
 *
 *
 *
 *
 *
 */
package com.deppon.foss.module.transfer.unload.server.service.impl;
/**
 * 引入包
 */
import java.math.BigDecimal;
/**
 * 引入包
 */
import java.text.DecimalFormat;
/**
 * 引入包
 */
import java.util.ArrayList;
/**
 * 引入包
 */
import java.util.Date;
/**
 * 引入包
 */
import java.util.HashMap;
/**
 * 引入包
 */
import java.util.List;
/**
 * 引入包
 */
import java.util.Map;
/**
 * 引入包
 */
import com.deppon.foss.base.util.define.BizTypeConstants;
/**
 * 引入包
 */
import com.deppon.foss.module.base.baseinfo.api.server.service.ILoadAndUnloadEfficiencyTonService;
/**
 * 引入包
 */
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
/**
 * 引入包
 */
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadEfficiencyTonEntity;
/**
 * 引入包
 */
/**
 * 引入包
 */
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
/**
 * 引入包
 */
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
/**
 * 引入包
 */
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.unload.api.server.dao.IQueryUnloadingProgressDao;
/**
 * 引入包
 */
import com.deppon.foss.module.transfer.unload.api.server.service.IQueryUnloadingProgressService;
/**
 * 引入包
 */
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
/**
 * 引入包
 */
import com.deppon.foss.module.transfer.unload.api.shared.dto.LoadAndUnloadStandardDto;
/**
 * 引入包
 */
import com.deppon.foss.module.transfer.unload.api.shared.dto.QueryBillInfoResultDto;
/**
 * 引入包
 */
import com.deppon.foss.module.transfer.unload.api.shared.dto.QueryUnloadingProgressConditionDto;
/**
 * 引入包
 */
import com.deppon.foss.module.transfer.unload.api.shared.dto.QueryUnloadingProgressResultDto;
/**
 * 引入包
 */
import com.deppon.foss.module.transfer.unload.api.shared.exception.QueryUnloadProgressException;
/**
 * 引入包
 */
import com.deppon.foss.util.define.FossConstants;

/**
 * 查询卸车进度实现类
 * 
 * 
 * @author 046130-foss-xuduowei
 * @date 2012-12-12 下午3:34:07
 */
public class QueryUnloadingProgressService implements
		IQueryUnloadingProgressService {
	/**
	 * 日志
	 * 
	 * 
	 */
	/*private static final Logger LOGGER = 
			LoggerFactory.getLogger(QueryUnloadingProgressService.class);*/
	/**
	 * dao接口
	 * 
	 * 
	 */
	private IQueryUnloadingProgressDao queryUnloadingProgressDao;
	/**
	 *  获取指定部门接口
	 *  
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	/**
	 * 
	 * 装卸车标准
	 * 
	 */
	private ILoadAndUnloadEfficiencyTonService loadAndUnloadEfficiencyTonService;
	/**
	 * 
	 * 查询卸车进度
	 * 
	 * 
	 * @param queryUnloadingProgressConditionDto
	 * 
	 *            查询卸车进度条件
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-12 下午3:16:30
	 */
	@Override
	public List<QueryUnloadingProgressResultDto> queryUnloadingProgressInfo(
			QueryUnloadingProgressConditionDto queryUnloadingProgressConditionDto,int limit,int start) {
		/**
		 * 先对查询条件进行处理
		 */
		QueryUnloadingProgressConditionDto afterHandle = handleCondition(queryUnloadingProgressConditionDto);
		// 获取查询返回结果
		List<QueryUnloadingProgressResultDto> resultList = queryUnloadingProgressDao
				.queryUnloadingProgressInfo(afterHandle,limit,start);

		/**
		 * 
		 * 遍历查询结果，通过任务号查询单据编号以及在分配卸车任务表中的数据
		 */
		for (int i = 0; i < resultList.size(); i++) {
			QueryUnloadingProgressResultDto resultDto = resultList.get(i);
			// 卸车任务id
			String id = resultDto.getId();
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", id);
			List<QueryBillInfoResultDto> billList = queryUnloadingProgressDao
					.queryBillInfo(map);
			// 单据编号，多个单据编号用逗号隔开
			StringBuffer sBuffer = new StringBuffer();
			//出发部门，多个部门用逗号隔开
			StringBuffer deptBuffer = new StringBuffer();
			//获取预计完成时间
			long currentTime = new Date().getTime();	
			long planTime;
			if(resultDto.getPredictCompleteMinutes()==null){
				planTime = 0;
			}else{
				planTime = resultDto.getPredictCompleteMinutes().multiply(new BigDecimal(ConstantsNumberSonar.SONAR_NUMBER_60*ConstantsNumberSonar.SONAR_NUMBER_1000)).longValue();
			}
			Date predictCompleteTime = new Date(currentTime + planTime);
			resultList.get(i).setPredictCompleteTime(predictCompleteTime);
			int length = billList.size();
			for (int j = 0; j < length; j++) {
				QueryBillInfoResultDto billDto = billList.get(j);
				sBuffer.append(billDto.getBillNumber());
				if (length > 1 && j != length - 1) {
					sBuffer.append(UnloadConstants.QUERY_UNLOAD_PROGRESS_COMMA);
				}
				
				deptBuffer.append(billDto.getLeaveDept());
				if(length > 1 && j != length - 1){
					deptBuffer.append(UnloadConstants.QUERY_UNLOAD_PROGRESS_COMMA);
				}

			}
			/**
			 * 为卸车进度查询结果设置总体积、总重量、总件数、到达时间和分配时间
			 */	
			//单据编号
			resultList.get(i).setBillNumber(sBuffer.toString());
			//出发部门
			resultList.get(i).setLeaveDept(deptBuffer.toString());
			
		}
		/**
		 * 增加对进度的处理
		 */
		return handleUnloadingProgressResult(resultList);
	}
	/**
	 * 
	 * 查询卸车进度
	 * 
	 * 
	 * @param queryUnloadingProgressConditionDto
	 * 
	 *            查询卸车进度条件
	 * @author 134019-yuyongxiang
	 * @date 2013年7月15日 19:13:04
	 */
	@Override
	public Long queryUnloadingProgressInfoCount(
			QueryUnloadingProgressConditionDto queryUnloadingProgressConditionDto) {
		/**
		 * 先对查询条件进行处理
		 */
		QueryUnloadingProgressConditionDto afterHandle = handleCondition(queryUnloadingProgressConditionDto);
		
		return  queryUnloadingProgressDao.queryUnloadingProgressInfoCount(afterHandle);
	}
	/**
	 * 
	 * 处理查询结果
	 * 
	 * 
	 * 
	 * @param queryLoadingProgressResultList
	 *            查询结果集
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-22 下午3:13:03
	 */
	private List<QueryUnloadingProgressResultDto> handleUnloadingProgressResult(
			List<QueryUnloadingProgressResultDto> queryUnloadingProgressResultList) {
		// 设置百分比格式    UnloadConstants.QUERY_UNLOAD_PROGRESS_PERCENT ##%
		DecimalFormat df = new DecimalFormat(UnloadConstants.QUERY_UNLOAD_PROGRESS_PERCENT);
		for (int i = 0; i < queryUnloadingProgressResultList.size(); i++) {
			QueryUnloadingProgressResultDto qprDto = queryUnloadingProgressResultList
					.get(i);
			// 已卸体积
			BigDecimal unloadedVolume = qprDto.getUnloadedVolume();
			// 已卸重量
			BigDecimal unloadedWeight = qprDto.getUnloadedWeight();
			// 总体积
			BigDecimal totalVolume = qprDto.getTotalVolume();
			if(totalVolume == null){
				totalVolume = BigDecimal.ZERO;
			}
			// 总重量
			BigDecimal totalWeight = qprDto.getTotalWeight();
			if(totalWeight == null){
				totalWeight = BigDecimal.ZERO;
			}
			//LOGGER.info("totalVolume:" + totalVolume);
			//LOGGER.info("totalWeight:" + totalWeight);
			if(unloadedVolume==null){
				unloadedVolume = new BigDecimal("0");
				qprDto.setLeftVolume(qprDto.getTotalVolume());
			}
			if(unloadedWeight==null){
				unloadedWeight = new BigDecimal("0");
				qprDto.setLeftWeight(qprDto.getTotalWeight());
			}
			if(qprDto.getUnloadedPieces()==0){
				qprDto.setLeftPieces(qprDto.getTotalPieces());
			}
			// 设置体积进度百分比
			if (unloadedVolume != null 
					&& totalVolume != null 
					&& Float.valueOf(0).compareTo(totalVolume.floatValue()) != 0) {
				double volume = unloadedVolume.divide(totalVolume, 2,
						BigDecimal.ROUND_HALF_UP).doubleValue();
				//获取百分比表示
				String volumeProgress = df.format(volume);
				//LOGGER.info("volumeProgress:" + volumeProgress);
				queryUnloadingProgressResultList.get(i).setVolumeProgress(volumeProgress);
			}else{
				//暂无操作
			}
			// 设置重量进度百分比
			if (unloadedWeight != null 
					&& totalWeight != null
					&& Float.valueOf(0).compareTo(totalWeight.floatValue()) != 0) {
				double weight = unloadedWeight.divide(totalWeight, 2,
						BigDecimal.ROUND_HALF_UP).doubleValue();
				//获取百分比表示
				String weightPorgress = df.format(weight);
				//LOGGER.info("weightPorgress:" + weightPorgress);
				queryUnloadingProgressResultList.get(i).setWeightProgress(weightPorgress);
			}else{
				//暂无操作
			}
			//体积进度字符表示
			String unloadingVolumeProgress = unloadedVolume.toString() + 
					UnloadConstants.QUERY_UNLOAD_PROGRESS_SLASH + 
					totalVolume.toString();
			//重量进度字符表示
			String unloadingWeightProgress = unloadedWeight.toString() + 
					UnloadConstants.QUERY_UNLOAD_PROGRESS_SLASH + 
					totalWeight.toString();
			//设置体积进度字符表示
			queryUnloadingProgressResultList.get(i).setUnLoadingVolumnProgress(unloadingVolumeProgress);
			//设置重量进度字符表示
			queryUnloadingProgressResultList.get(i).setUnLoadingWeightProgress(unloadingWeightProgress);
		}
		//返回结果
		return queryUnloadingProgressResultList;
	}
	/**
	 * 
	 * 处理卸车查询条件
	 * 
	 * 
	 * 
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-12 下午4:09:09
	 */
	private QueryUnloadingProgressConditionDto handleCondition(
			QueryUnloadingProgressConditionDto condition) {
		condition.setCurrentDate(new Date());
		String deptCode = obtainCurrentDeptCode();
		//将当前部门作为查询条件
		condition.setDeptCode(deptCode);
		
		//排序类型  从大到小 从小到大
		String sequence = condition.getSequence();
		//排序方式  任务创建时间、重量、体积等
		String sequenceType = condition.getSequenceType();
		//拼接查询条件
		String sortRule = sequenceType + UnloadConstants.QUERY_UNLOAD_PROGRESS_UNDERLINE + sequence;
		condition.setSortRule(sortRule);
		//创建任务理货员，默认显示的理货员为创建任务的理货员
		condition.setIsCreator(FossConstants.YES);
		return condition;
	}
	/**
	 * 
	 * 查询当前部门装卸车标准
	 * 
	 * 
	 * 
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-18 下午9:16:13
	 */
	@Override
	public LoadAndUnloadStandardDto queryLoadAndUnloadStd() {
		LoadAndUnloadStandardDto standarDto = new LoadAndUnloadStandardDto();
		//获取当前部门
		String deptCode = obtainCurrentDeptCode();
		
		LoadAndUnloadEfficiencyTonEntity  effiEntity = loadAndUnloadEfficiencyTonService.queryLoadAndUnloadEfficiencyTonByOrgCode(deptCode);
		if(effiEntity==null){
			throw new QueryUnloadProgressException("未找到本部门的卸车标准！");
		}
		//设置 卸车体积标准.
		standarDto.setUnloadVolumeStd(effiEntity.getUnloadVolumeStd());
		//设置 卸车重量标准.
		standarDto.setUnloadWeightStd(effiEntity.getUnloadWeightStd());
		return standarDto;
	}
	/**
	 * 
	 * 获取指定部门
	 * 
	 * 
	 * 
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-18 下午2:16:59
	 */
	private String obtainCurrentDeptCode() {
		// 当前操作部门
		List<String> bizTypes = new ArrayList<String>();
		// 设置外场类型
		bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
		OrgAdministrativeInfoEntity activeDeptInfo = FossUserContext.getCurrentDept();
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(activeDeptInfo.getCode(),bizTypes);
		if (org != null) {
			return org.getCode();
		} else {
			throw new QueryUnloadProgressException("当前登录人直属部门无对应外场部门");
		}
	}
	/**
	 * 设置 dao接口.
	 * 
	 *
	 * @param queryUnloadingProgressDao the new dao接口
	 */
	public void setQueryUnloadingProgressDao(IQueryUnloadingProgressDao queryUnloadingProgressDao) {
		this.queryUnloadingProgressDao = queryUnloadingProgressDao;
	}
	/**
	 * 设置 获取指定部门接口.
	 *
	 * @param orgAdministrativeInfoComplexService the new 获取指定部门接口
	 */
	public void setOrgAdministrativeInfoComplexService(IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	/**
	 * 设置 装卸车标准.
	 *
	 * @param loadAndUnloadEfficiencyTonService the new 装卸车标准
	 */
	public void setLoadAndUnloadEfficiencyTonService(ILoadAndUnloadEfficiencyTonService loadAndUnloadEfficiencyTonService) {
		this.loadAndUnloadEfficiencyTonService = loadAndUnloadEfficiencyTonService;
	}
}