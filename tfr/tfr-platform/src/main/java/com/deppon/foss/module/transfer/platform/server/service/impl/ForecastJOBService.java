/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  
 *  
 *  you may not use this file except in compliance with the License.
 *  
 *  
 *  You may obtain a copy of the License at
 *  
 *  
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
 *  
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  
 *  
 *  See the License for the specific language governing permissions and
 *  
 *  
 *  limitations under the License.

 *  
 *  PROJECT NAME  : tfr-platform
 *  
 *  
 *  PACKAGE NAME  : 

 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/platorm/server/service/impl/ForecastJOBService.java
 * 
 *  FILE NAME     :ForecastJOBService.java

 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 

 *  HOME PAGE     :  http://www.deppon.com

 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.

 *  VERSION       :0.1

 *  LAST MODIFY TIME:
 *  
 *  
 ***
 ****************************************************************************/
package com.deppon.foss.module.transfer.platform.server.service.impl;
/**
 * 导入包
 */

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SiteGroupEntity;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.transfer.common.api.shared.define.TfrJobBusinessTypeEnum;
import com.deppon.foss.module.transfer.common.api.shared.domain.TfrJobProcessLogEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.platform.api.server.service.IForecastJOBService;
import com.deppon.foss.module.transfer.platform.api.shared.define.ForecastConstants;
import com.deppon.foss.module.transfer.platform.api.shared.define.PlatformConstants;
import com.deppon.foss.module.transfer.platform.api.shared.domain.AverageCalculateEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.ForecastQuantityEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.ForecastDto;
import com.deppon.foss.module.transfer.scheduling.api.define.TransportPathConstants;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PathDetailEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TransportPathEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.TimeUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;

/**
 * 预测货量 JOB 类，各种工具方法或者各种和DAO打交道的都在 IForecastService 中，
 * 本类中只负责所有的JOB端的数据逻辑处理以及业务逻辑处理
 * 
 * 
 * @author yuyongxiang-134019
 * @date 2014-03-06 14:02:18
 */
public class ForecastJOBService extends ForecastService implements IForecastJOBService {
	
	private final Logger logger = LoggerFactory.getLogger(ForecastJOBService.class);
	
	//计算job执行次数以刷新各种部门code list的值
	//job每小时执行一次(目前每次有8个线程)
	private int jobCount = 0;
	
	//所有外场Code
	private List<String> m_allTransferCenterCodes = null;
	public List<String> getAllTransferCenterCodes() {
		if(CollectionUtils.isEmpty(m_allTransferCenterCodes)) {
			m_allTransferCenterCodes = super.forecastQuantityJOBDao.selectBasOrgByCode(null, null, true);
		}
		return m_allTransferCenterCodes;
	}
	
//	//所有外场所辐射的营业部
//	private Map<String, List<String>> m_allSalesDept = null;
//	public Map<String, List<String>> getAllSalesDept() {
//		if(m_allSalesDept == null) {
//			m_allSalesDept = new HashMap<String, List<String>>();
//		}
//		return m_allSalesDept;
//	}
	
	/**所有驻地派送部**/
	private List<String> m_allDeliveryStation = null;
	public List<String> getAlldeliveryStation() {
		if(CollectionUtils.isEmpty(m_allDeliveryStation)) {
			m_allDeliveryStation = forecastQuantityJOBDao.queryAlldeliveryStation();
			Set<String> s = new HashSet<String>();
			s.addAll(m_allDeliveryStation);
			List<String> t = new ArrayList<String>();
			t.addAll(s);
			m_allDeliveryStation = t;
		}
		return m_allDeliveryStation;
	}
	
	//所有的始发线路出发部门
	private Map<String, List<String>> m_allDeptCodeSource;
	public Map<String, List<String>> getAllDeptCodeSource() {
		if(m_allDeptCodeSource == null) {
			m_allDeptCodeSource = new HashMap<String, List<String>>();
			List<String> outStrList = getAllTransferCenterCodes();
			if(outStrList!=null && outStrList.size()>0){
				for (String outStr : outStrList) {
					m_allDeptCodeSource.put(outStr, queryDeptCodeSource(outStr));
				}
			}
		}
		return m_allDeptCodeSource;
	}
	/**
	 * 根据当前外场code找始发部门(始发线路)
	 * @author 163580
	 * @date 2014-5-17 上午10:50:56
	 * @param orgCode 
	 * @return 返回始发线路orgCode到达的所有出发部门
	 * @see
	 */
	public List<String> queryDeptCodeSource(String orgCode) {
		List<String> temp = forecastQuantityJOBDao.queryDeptCodeSource(orgCode);
		Set<String> s = new HashSet<String>();
		s.addAll(temp);
		List<String> t = new ArrayList<String>(s.size());
		t.addAll(s);
		return t;
	}
	
	//所有的始发线路到达部门
	private Map<String, List<String>> m_allDeptCodeTarget;
	public Map<String, List<String>> getAllDeptCodeTarget() {
		if(m_allDeptCodeTarget == null) {
			m_allDeptCodeTarget = new HashMap<String, List<String>>();
			List<String> outStrList = getAllTransferCenterCodes();
			if(outStrList!=null && outStrList.size()>0){
				for (String outStr : outStrList) {
					m_allDeptCodeTarget.put(outStr, queryDeptCodeTarget(outStr));
				}
			}
		}
		return m_allDeptCodeTarget;
	}
	/**
	 * 根据当前外场code找到达部门(到达线路)
	 * @author 163580
	 * @date 2014-5-17 上午11:01:42
	 * @param orgCode
	 * @return 返回到达线路orgCode出发的所有的到达部门
	 * @see
	 */
	public List<String> queryDeptCodeTarget(String orgCode) {
		List<String> temp = forecastQuantityJOBDao.queryDeptCodeTarget(orgCode);
		Set<String> s = new HashSet<String>();
		s.addAll(temp);
		List<String> t = new ArrayList<String>(s.size());
		t.addAll(s);
		return t;
	}
	
	//中转到中转所有到达部门
	private Map<String, List<String>> m_allDeptCodeArrivalTransfer;
	public Map<String, List<String>> getAllDeptCodeArrivalTransfer() {
		if(m_allDeptCodeArrivalTransfer == null) {
			m_allDeptCodeArrivalTransfer = new HashMap<String, List<String>>();
			List<String> outStrList = getAllTransferCenterCodes();
			if(outStrList!=null && outStrList.size()>0){
				for (String outStr : outStrList) {
					m_allDeptCodeArrivalTransfer.put(outStr, queryDeptCodeArrivalTransfer(outStr));
				}
			}
		}
		return m_allDeptCodeArrivalTransfer;
	}
	/**
	 * 根据当前外场code找到到达部门(中转到中转)
	 * @author 163580
	 * @date 2014-5-17 上午11:06:09
	 * @param orgCode
	 * @return 返回中转到中转线路orgCode出发的所有到达部门
	 * @see
	 */
	public List<String> queryDeptCodeArrivalTransfer(String orgCode) {
		List<String> temp = forecastQuantityJOBDao.queryDeptCodeArrivalTransfer(orgCode);
		Set<String> s = new HashSet<String>();
		s.addAll(temp);
		List<String> t = new ArrayList<String>(s.size());
		t.addAll(s);
		return t;
	}
	
	//中转到中转线路orgCode到达的所有出发部门
	private Map<String, List<String>> m_allDeptCodeDepartTransfer;
	public Map<String, List<String>> getAllDeptCodeDepartTransfer() {
		if(m_allDeptCodeDepartTransfer == null) {
			m_allDeptCodeDepartTransfer = new HashMap<String, List<String>>();
			List<String> outStrList = getAllTransferCenterCodes();
			if(outStrList!=null && outStrList.size()>0){
				for (String outStr : outStrList) {
					m_allDeptCodeDepartTransfer.put(outStr, queryDeptCodeDepartTransfer(outStr));
				}
			}
		}
		return m_allDeptCodeDepartTransfer;
	}
	/**
	 * 根据当前外场code找到出发部门(中转到中转)
	 * @author 163580
	 * @date 2014-5-17 上午11:06:09
	 * @param orgCode
	 * @return 返回中转到中转线路orgCode到达的所有出发部门
	 * @see
	 */
	public List<String> queryDeptCodeDepartTransfer(String orgCode) {
		List<String> temp = forecastQuantityJOBDao.queryDeptCodeDepartTransfer(orgCode);
		Set<String> s = new HashSet<String>();
		s.addAll(temp);
		List<String> t = new ArrayList<String>(s.size());
		t.addAll(s);
		return t;
	}
	
	//外场集中重量配置参数
	private Map<String, BigDecimal> m_deptWeightConfig;
	public Map<String, BigDecimal> getDeptWeightConfig() {
		if(m_deptWeightConfig == null) {
			m_deptWeightConfig = new HashMap<String, BigDecimal>();
		}
		return m_deptWeightConfig;
	}

	//外场集中体积配置参数
	private Map<String, BigDecimal> m_deptVolumeConfig;
	public Map<String, BigDecimal> getDeptVolumeConfig() {
		if(m_deptVolumeConfig == null) {
			m_deptVolumeConfig = new HashMap<String, BigDecimal>();
		}
		return m_deptVolumeConfig;
	}
	
	/**
	 * 预测外场货量方法 分出发,到达 调用预测各外场方法
	 * 
	 * @author yuyongxiang-134019
	 * @date 2014-03-06 14:03:30
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.IForecastJOBService#forecastTotal(java.lang.String,java.lang.String,
	 *      java.util.Date)
	 */
	@Override
	public void forecastTransferCenterTotal(String action,String threadNo,String threadCount, Date statistics) throws TfrBusinessException {
		if(jobCount >= PlatformConstants.SONAR_NUMBER_384) {
			//job 1小时执行一次, 每次8个线程(包括出发到达);
			//48小时后刷新本类中的各种部门变量
			m_allDeliveryStation = null;
			m_allDeptCodeSource = null;
			m_allDeptCodeTarget = null;
			m_allDeptCodeArrivalTransfer = null;
			m_allDeptCodeDepartTransfer = null;
			m_deptWeightConfig = null;
			m_deptVolumeConfig = null;
			//重新计数
			jobCount = PlatformConstants.SONAR_NUMBER_0;
		}
		jobCount++;
		// 首先查询所有外场信息
		//设置外场为 yes
		//精确查询 动态的查询条件。 
		//如果传入的对象为空，
		//传入一个对象，
		//可查出所有的数据，
		//如果传入的对象的属性不为空或者空白，
		//则设置为查询条件
		List<String> codes = super.forecastQuantityJOBDao.selectBasOrgByCode(threadNo, threadCount, true);
		
		//如果为空
		if (CollectionUtils.isEmpty(codes)) {
			logger.error("调用综合接口查询所有外场信息没有得到数据!");
			return;
		//不为空
		} else {
			for (String code: codes) {
				if(StringUtils.isNotBlank(code)){
					try{
						//集中接货外场重量配置参数
						ConfigurationParamsEntity weightConf = super.configurationParamsService
								.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,
										ConfigurationParamsConstants.TFR_PARM__FORECAST_WEIGHT, code);
						if (weightConf != null && StringUtils.isNotEmpty(weightConf.getConfValue())) {
							BigDecimal weightConfig = new BigDecimal(weightConf.getConfValue());
							getDeptWeightConfig().put(code, weightConfig);
						}
						
						//集中接货外场体积配置参数
						ConfigurationParamsEntity volumeConf = super.configurationParamsService
								.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,
										ConfigurationParamsConstants.TFR_PARM__FORECAST_VOLUME, code);
						if (weightConf != null && StringUtils.isNotEmpty(volumeConf.getConfValue())) {
							BigDecimal volumeConfig = new BigDecimal(weightConf.getConfValue());
							getDeptVolumeConfig().put(code, volumeConfig);
						}
						
						//预测货量 各外场出发,到达 调用预测各线路方法
						this.forecastTransferCenter(action, statistics, code);
					}catch(Exception e){
						logger.error("货量预测部门: " + code, e);
						// 记录出错日志
						TfrJobProcessLogEntity jobProcessLogEntity = new TfrJobProcessLogEntity();
						if(StringUtils.equals(action, ForecastConstants.FORECAST_ARRIVE)) {
							jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.FORECAST_TRANSFERCENTER_ARRIVE.getBizName());
							jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.FORECAST_TRANSFERCENTER_ARRIVE.getBizCode());
						} else {
							jobProcessLogEntity.setBizName(TfrJobBusinessTypeEnum.FORECAST_TRANSFERCENTER_DEPART.getBizName());
							jobProcessLogEntity.setBizCode(TfrJobBusinessTypeEnum.FORECAST_TRANSFERCENTER_DEPART.getBizCode());
						}
						jobProcessLogEntity.setRemark("任务执行失败！" + code);
						jobProcessLogEntity.setExceptionInfo(ExceptionUtils.getFullStackTrace(e));
						jobProcessLogEntity.setCreateTime(Calendar.getInstance().getTime());
						tfrCommonService.addJobProcessLog(jobProcessLogEntity);
					}
				}
				
			}
		}
	}
	
	@Override
	public void zyxTest() {
//		Date statistics = DateUtils.convert("2014-06-26 15", "yyyy-MM-dd HH");
//		String code = "W3100020616";
//		//集中接货外场重量配置参数
//		ConfigurationParamsEntity weightConf = super.configurationParamsService
//				.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,
//						ConfigurationParamsConstants.TFR_PARM__FORECAST_WEIGHT, code);
//		if (weightConf != null && StringUtils.isNotEmpty(weightConf.getConfValue())) {
//			BigDecimal weightConfig = new BigDecimal(weightConf.getConfValue());
//			getDeptWeightConfig().put(code, weightConfig);
//		}
//		
//		//集中接货外场体积配置参数
//		ConfigurationParamsEntity volumeConf = super.configurationParamsService
//				.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,
//						ConfigurationParamsConstants.TFR_PARM__FORECAST_VOLUME, code);
//		if (weightConf != null && StringUtils.isNotEmpty(volumeConf.getConfValue())) {
//			BigDecimal volumeConfig = new BigDecimal(weightConf.getConfValue());
//			getDeptVolumeConfig().put(code, volumeConfig);
//		}
////		//TODO 待删除
//		forecastTransferCenter(ForecastConstants.FORECAST_ARRIVE, statistics, code);
	}
	
	
	/**
	 * 预测货量 各外场出发,到达 调用预测各线路方法
	 * 
	 * @author yuyongxiang-134019
	 * @date 2014-03-06 14:05:19
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.IForecastJOBService#forecastTransferCenter()
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	protected void forecastTransferCenter(String action, Date statistics, String orgCode) throws TfrBusinessException {
		// 判断是出发还是到达
		if (StringUtils.equals(action, ForecastConstants.FORECAST_ARRIVE)) {// 到达

			this.forecastTransferCenterArrive(action, statistics, orgCode);
			
		//如果为出发
		} else if (StringUtils.equals(action, ForecastConstants.FORECAST_DEPART)) {
			
			this.forecastTransferCenterDepart(action, statistics, orgCode);
			
		} else {
			//抛异常
			throw new TfrBusinessException(ForecastConstants.FORECAST_ACTION_ERROR, "");
		}
	}
	
	/**
	 * 
	 * @param action
	 *            ： 出发 【 预测类型（出发，到达）】
	 * @param statistics
	 *            执行时间节点
	 * @param orgCode
	 *            当前外场的 部门code
	 * @param allTransferCenterCodes (orgCode所辐射的外场)
	 * @author yuyongxiang-134019
	 * @date 2014-03-06 18:30:48
	 */
	@SuppressWarnings("unchecked")
	protected void forecastTransferCenterDepart(String action, Date statistics,
			String orgCode) {
		
		// 查询该部门走货路径最后出发时间
		Date maxStartTime = super.pathDetailDao.queryMaxStartTime(orgCode);
		if (null == maxStartTime) {
			logger.info("查询部门" + orgCode + "的走货路径最后到达时间没有得到数据!");
			return;
		}

		// 根据预测时间点 ,组织code 查询预测时间段
		//开始时间点
		ConfigurationParamsEntity entityStart = super.configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,ConfigurationParamsConstants.TFR_PARM__FORECAST_START,orgCode);
		if (entityStart != null && StringUtils.isNotEmpty(entityStart.getConfValue())) {
			super.start1 = entityStart.getConfValue();
		}
		//持续天数
		ConfigurationParamsEntity entityDuration = super.configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,ConfigurationParamsConstants.TFR_PARM__FORECAST_DURATION,orgCode);
		if (entityDuration != null&& StringUtils.isNotEmpty(entityDuration.getConfValue())) {
			super.day1 = Integer.valueOf(entityDuration.getConfValue());
		}
		
		// 新建预测时间 根据当前时间判断
		Date forecastTime;
		// 修改时间格式 只取日期
		// 计算开始时间
		Date midStartTime = DateUtils.convert(DateUtils.convert(statistics, DateUtils.DATE_FORMAT),DateUtils.DATE_FORMAT);
		// 计算结束时间
		Date midEndTime = TimeUtils.createStartTime(midStartTime, super.start1);

		// 如果现在时间小于当天的周期起始时间,并且大于0点.则预测的周期应该是昨天的周期
		if (statistics.after(midStartTime) && statistics.before(midEndTime)) {
			// 减一天
			forecastTime = TimeUtils.convertStringToDate(statistics, super.start1, -1);
		} else {
			// 否则就是当天的周期
			forecastTime = TimeUtils.createStartTime(statistics, super.start1);
		}
		
//		// 判断需要计算几天的 小于1天返回1
		//2014-07-21 maxArriveTime时间在数据库存在2015-02月分的数据, 这是不应该的, 所以下方for循环处改为使用day1
//		int diffDay = DateUtils.getTimeDiff(forecastTime, maxStartTime).intValue() + 1;
		
		//始发线路orgCode到达的所有出发部门
		List<String> relevantOrgCodesStart = getAllDeptCodeSource().get(orgCode);
		if(CollectionUtils.isEmpty(relevantOrgCodesStart)) {
			relevantOrgCodesStart = queryDeptCodeSource(orgCode);
			getAllDeptCodeSource().put(orgCode, relevantOrgCodesStart);
		}
		
		//当前外场code找到到达部门(中转到中转)--据当前外场code找到出发部门(中转到中转)
		List<String> allTransferCenterCodes = getAllDeptCodeDepartTransfer().get(orgCode);
		if(CollectionUtils.isEmpty(allTransferCenterCodes)) {
			allTransferCenterCodes = queryDeptCodeDepartTransfer(orgCode);
			getAllDeptCodeArrivalTransfer().put(orgCode, allTransferCenterCodes);
		}
		
		//当前外场code找到达部门(到达线路)
		List<String> relevantOrgCodes = getAllDeptCodeTarget().get(orgCode);
		if(CollectionUtils.isEmpty(relevantOrgCodes)) {
			relevantOrgCodes = queryDeptCodeTarget(orgCode);
			getAllDeptCodeTarget().put(orgCode, relevantOrgCodes);
		}
		// 循环
		for (int d = 0; d < day1; d++) {
			// 如果预测开始时间大于最大时间.则不继续循环否则继续
			if (forecastTime.after(maxStartTime)) {
				break;
			}

			// 设置 周期开始时间  预测时间
			Date forecastStartTime = forecastTime;
			// 计算 周期结束时间 结束时间
			Date forecastEndTime = TimeUtils.convertStringToDate(forecastTime, super.start1, super.day1);
			// 如果为空
			if (null == forecastEndTime) {
				logger.error("转换时间出错!方法: forecastTransferCenterDepart");
				break;
			}
			
			//用来计算全部
			Map<String, Object> map = new HashMap<String, Object>();
			

//			  /**出发 - 本地出发*/
			for (String relevantOrgCode : relevantOrgCodesStart) {
				//relevantOrgCode 对应走货路径明细中下一部门
				this.departRelevant(relevantOrgCode,orgCode, forecastStartTime, forecastEndTime, statistics,
						ForecastConstants.DEPART_LOCALDEPARTURE, map, relevantOrgCodes);
			}

//		    /**出发 - 二次中转*/
			for (String transferCenterCode : allTransferCenterCodes) {
				this.departRelevant(transferCenterCode,orgCode, forecastStartTime, forecastEndTime, statistics,
						ForecastConstants.DEPART_SECONDARYTRANSIT, map, relevantOrgCodes);
			}

		    /**出发 - 到达中转*/
			for (String relevantOrgCode : relevantOrgCodes) {
				this.departRelevant(orgCode, relevantOrgCode, forecastStartTime, forecastEndTime, statistics,
						ForecastConstants.DEPART_ARRIVALTRANSIT, map, relevantOrgCodes);
			}
			
		    /**出发 - 派送货量*/
			for (String relevantOrgCode : getAlldeliveryStation()) {
				this.departRelevant(orgCode, relevantOrgCode, forecastStartTime, forecastEndTime, statistics,
						ForecastConstants.DEPART_DELIVERYVOLUME, map, relevantOrgCodes);
			}
			
//		    /**出发 - 全部*/
			// 开单未交接的货量
			List<ForecastDto> billingList=null;
			// 在途货量
			List<ForecastDto> inTransitList=null;
			// 在库货量
			List<ForecastDto> inventoryList=null;
			
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				Map<String, Object> mapTemp=(Map<String, Object>) map.get(entry.getKey());
				billingList = (List<ForecastDto>) mapTemp.get("billingList");
				inTransitList = (List<ForecastDto>) mapTemp.get("inTransitList");
				inventoryList = (List<ForecastDto>) mapTemp.get("inventoryList");
				
				super.forecast(ForecastConstants.FORECAST_DEPART,ForecastConstants.DEPART_ALL,forecastStartTime, statistics, 
						orgCode, entry.getKey(), billingList,inTransitList, inventoryList, relevantOrgCodes);
			}
			

			// 设置时间为结束时间
			forecastTime = forecastEndTime;
		}
		
		
		//预测未来货量的计算方法：zyx
		//测未来货量的时间范围同转运场所配置查询的货量统计保持一致(当前凌晨3点为界  0点以后 3点以前预测的时候当天的数据
		//	例如: 当前时间为 2014-03-24 :02:00:00  此时预测24号02:00 ==> 03:00的数据
		//	例如: 当前时间为 2014-03-24 :03:00:00  此时预测25号03:00 ==> 04:00的数据)
		//时间点, 用来比较当前时间在该时间点的位置见上述规则
		Date start1Time = TimeUtils.createStartTime(new Date(), start1);
		
		//查询条件
		ForecastQuantityEntity forecastQuantityParam = new ForecastQuantityEntity();
		forecastQuantityParam.setBelongOrgCode(orgCode);
		forecastQuantityParam.setType(action);
		forecastQuantityParam.setDataType(ForecastConstants.DATA_TYPE_TWO);
		forecastQuantityParam.setStatisticsTime(statistics);
		forecastQuantityParam.setStatisticsHHMM(DateUtils.convert(DateUtils.convert(DateUtils.convert(statistics, "HH"), "HH"), "HHmm"));
		forecastQuantityParam.setStatisticsDate(DateUtils.getStartDatetime(statistics));
		//如果预测时间大于所配置的时间, 就从当前开始预测, 反之则从上一日开始
		if(statistics.compareTo(start1Time) >= 0) {
			forecastQuantityParam.setForecastTime(DateUtils.getStartDatetime(statistics));
		} else {
			forecastQuantityParam.setForecastTime(DateUtils.addDayToDate(DateUtils.getStartDatetime(statistics), -1));
		}
		
		forecastTime = forecastQuantityParam.getForecastTime();
		
		for(int i = 0; i < day1; i++) {
			ForecastQuantityEntity forecastQuantityP = null;
			ForecastQuantityEntity forecastQuantityY = null;
			ForecastQuantityEntity forecastQuantityX = null;
			ForecastQuantityEntity forecastQuantityB = null;
			
			forecastTime = DateUtils.addDayToDate(forecastTime, i);
			
			boolean flag = false;
			if(DateUtils.getStartDatetime(statistics).compareTo(DateUtils.getStartDatetime(forecastTime)) == 0) {
				//如果被预测的日期等于当前日期, 说明当前计算的是当天货量
				//当天货量需特殊处理
				flag = true;
			}
			
			//本地出发
			forecastQuantityParam.setDeparturearrival(ForecastConstants.DEPART_LOCALDEPARTURE);
			ForecastQuantityEntity forecastQuantityParamTemp = new ForecastQuantityEntity();
			BeanUtils.copyProperties(forecastQuantityParam, forecastQuantityParamTemp);
			Map<String, ForecastQuantityEntity> forecastMap = forecastQuantity(flag, forecastTime, forecastQuantityParamTemp);
			forecastQuantityP = forecastMap.get("P");
			forecastQuantityY = forecastMap.get("Y");
			forecastQuantityX = forecastMap.get("X");
			forecastQuantityB = forecastMap.get("B");
			
//			  /**出发 - 本地出发*/
			for (String relevantOrgCode : relevantOrgCodesStart) {
				forecastQuantityParam.setBelongOrgCode(relevantOrgCode);
				forecastQuantityParam.setRelevantOrgCode(orgCode);
				forecastQuantityParam.setDeparturearrival(ForecastConstants.DEPART_LOCALDEPARTURE);
				forecastingQuantity(forecastQuantityParam, flag, forecastQuantityP, forecastQuantityY, forecastQuantityX, forecastQuantityB);
			}

			//二次中转
			forecastQuantityParam.setDeparturearrival(ForecastConstants.DEPART_SECONDARYTRANSIT);
			forecastQuantityParamTemp = new ForecastQuantityEntity();
			BeanUtils.copyProperties(forecastQuantityParam, forecastQuantityParamTemp);
			forecastMap = forecastQuantity(flag, forecastTime, forecastQuantityParamTemp);
			forecastQuantityP = forecastMap.get("P");
			forecastQuantityY = forecastMap.get("Y");
			forecastQuantityX = forecastMap.get("X");
			forecastQuantityB = forecastMap.get("B");
//		    /**出发 - 二次中转*/
			for (String transferCenterCode : allTransferCenterCodes) {
				forecastQuantityParam.setBelongOrgCode(transferCenterCode);
				forecastQuantityParam.setRelevantOrgCode(orgCode);
				forecastQuantityParam.setDeparturearrival(ForecastConstants.DEPART_SECONDARYTRANSIT);
				forecastingQuantity(forecastQuantityParam, flag, forecastQuantityP, forecastQuantityY, forecastQuantityX, forecastQuantityB);
			}

			//到达中转
			forecastQuantityParam.setDeparturearrival(ForecastConstants.DEPART_ARRIVALTRANSIT);
			forecastQuantityParamTemp = new ForecastQuantityEntity();
			BeanUtils.copyProperties(forecastQuantityParam, forecastQuantityParamTemp);
			forecastMap = forecastQuantity(flag, forecastTime, forecastQuantityParamTemp);
			forecastQuantityP = forecastMap.get("P");
			forecastQuantityY = forecastMap.get("Y");
			forecastQuantityX = forecastMap.get("X");
			forecastQuantityB = forecastMap.get("B");
//		    /**出发 - 到达中转*/
			for (String relevantOrgCode : relevantOrgCodes) {
				forecastQuantityParam.setRelevantOrgCode(relevantOrgCode);
				forecastQuantityParam.setDeparturearrival(ForecastConstants.DEPART_ARRIVALTRANSIT);
				forecastingQuantity(forecastQuantityParam, flag, forecastQuantityP, forecastQuantityY, forecastQuantityX, forecastQuantityB);
			}
			
			//派送货量
			forecastQuantityParam.setDeparturearrival(ForecastConstants.DEPART_DELIVERYVOLUME);
			forecastQuantityParamTemp = new ForecastQuantityEntity();
			BeanUtils.copyProperties(forecastQuantityParam, forecastQuantityParamTemp);
			forecastMap = forecastQuantity(flag, forecastTime, forecastQuantityParamTemp);
			forecastQuantityP = forecastMap.get("P");
			forecastQuantityY = forecastMap.get("Y");
			forecastQuantityX = forecastMap.get("X");
			forecastQuantityB = forecastMap.get("B");
//		    /**出发 - 派送货量*/
			for (String relevantOrgCode : getAlldeliveryStation()) {
				forecastQuantityParam.setRelevantOrgCode(relevantOrgCode);
				forecastQuantityParam.setDeparturearrival(ForecastConstants.DEPART_DELIVERYVOLUME);
				forecastingQuantity(forecastQuantityParam, flag, forecastQuantityP, forecastQuantityY, forecastQuantityX, forecastQuantityB);
			}
			
			//全部
			forecastQuantityParam.setDeparturearrival(ForecastConstants.DEPART_ALL);
			forecastQuantityParamTemp = new ForecastQuantityEntity();
			BeanUtils.copyProperties(forecastQuantityParam, forecastQuantityParamTemp);
			forecastMap = forecastQuantity(flag, forecastTime, forecastQuantityParamTemp);
			forecastQuantityP = forecastMap.get("P");
			forecastQuantityY = forecastMap.get("Y");
			forecastQuantityX = forecastMap.get("X");
			forecastQuantityB = forecastMap.get("B");
			List<String> allDeptCode = new ArrayList<String>();
			//所有外场code
			allDeptCode.addAll(allTransferCenterCodes);
			//当前外场辐射的所有营业部
			allDeptCode.addAll(relevantOrgCodes);
			//所有派送部
			allDeptCode.addAll(getAlldeliveryStation());
			//全部
			//去重
			Set<String> temp = new HashSet<String>();
			temp.addAll(allDeptCode);
			List<String> deptCodes = new ArrayList<String>();
			deptCodes.addAll(temp);
			for (String relevantOrgCode : deptCodes) {
				forecastQuantityParam.setRelevantOrgCode(relevantOrgCode);
				forecastQuantityParam.setDeparturearrival(ForecastConstants.DEPART_ALL);
				forecastingQuantity(forecastQuantityParam, flag, forecastQuantityP, forecastQuantityY, forecastQuantityX, forecastQuantityB);
			}
			
			forecastQuantityParam.setForecastTime(DateUtils.addDayToDate(forecastQuantityParam.getForecastTime(), PlatformConstants.SONAR_NUMBER_1));
		}
		//zyx end
	}
	
	/**
	 * 计算上周总货量以及前N日货量
	 * @author 163580
	 * @date 2014-5-20 下午3:30:03
	 * @param flag
	 * @param forecastTime
	 * @param forecastQuantityParam
	 * @return
	 * @see
	 */
	private Map<String, ForecastQuantityEntity> forecastQuantity(boolean flag, Date forecastTime, ForecastQuantityEntity forecastQuantityParam) {
		Map<String, ForecastQuantityEntity> forecastMap = new HashMap<String, ForecastQuantityEntity>(PlatformConstants.SONAR_NUMBER_4);
		String statisticsHHMM = forecastQuantityParam.getStatisticsHHMM();
		if(flag) {
			//当日货量
			
			//当前外场总货量
			forecastQuantityParam.setRelevantOrgCode("");
			//实际开单货量
			forecastQuantityParam.setDataType(ForecastConstants.DATA_TYPE_TWO);
			
			//Q=某一时间点的实际开单货量值
			ForecastQuantityEntity forecastQuantityQ = forecastQuantityDaoPlatform.countForecastQtyOneDay(forecastQuantityParam);
			if(forecastQuantityQ == null) {
				forecastQuantityQ = ForecastQuantityEntity.newForecastQuantityEntity();
			}
			
			//W=上周在Q时间点时的实际货量值
			Date forecastTimeTemp = DateUtils.addDayToDate(forecastTime, ForecastConstants.NUMBER_MINUS_7);
			forecastQuantityParam.setForecastTime(forecastTimeTemp);
			forecastQuantityParam.setStatisticsDate(forecastTimeTemp);
			ForecastQuantityEntity forecastQuantityW = forecastQuantityDaoPlatform.countForecastQtyOneDay(forecastQuantityParam);
			if(forecastQuantityW == null) {
				forecastQuantityW = ForecastQuantityEntity.newForecastQuantityEntity();
			}
			
			//Y=上周当天总货量值(以该天晚上12点统计的那一次为准)
			//例如当前时间为 2014-06-07 10:00:00  那么统计的时间则应该为 2014-06-01 00:00:00(业务要求)
			forecastTimeTemp = DateUtils.addDayToDate(forecastTime, ForecastConstants.NUMBER_MINUS_6);
			forecastQuantityParam.setForecastTime(forecastTimeTemp);
			forecastQuantityParam.setStatisticsDate(forecastTimeTemp);
			forecastQuantityParam.setStatisticsHHMM("0000");
			ForecastQuantityEntity forecastQuantityY = forecastQuantityDaoPlatform.countForecastQtyOneDay(forecastQuantityParam);
			if(forecastQuantityY == null) {
				forecastQuantityY = ForecastQuantityEntity.newForecastQuantityEntity();
			}
			forecastMap.put("Y", forecastQuantityY);
			//还原为之前的时间点
			forecastQuantityParam.setStatisticsHHMM(statisticsHHMM);
//			forecastTimeTemp = DateUtils.addDayToDate(forecastTime, ForecastConstants.NUMBER_MINUS_7);
//			forecastTimeTemp = getMonday(forecastTimeTemp);
//			forecastQuantityParam.setForecastTime(forecastTimeTemp);
//			forecastQuantityParam.setStatisticsDate(forecastTimeTemp);
//			ForecastQuantityEntity forecastQuantityY = forecastQuantityDaoPlatform.countForecastQtyOneWeek(forecastQuantityParam);
//			if(forecastQuantityY == null) {
//				forecastQuantityY = ForecastQuantityEntity.newForecastQuantityEntity();
//			}
//			forecastMap.put("Y", forecastQuantityY);
			
			//（1）当天总货量预测值计算公式： P = Q / (W / Y)
			//（P=当日总货量预测值，Q=某一时间点的实际开单货量值，W=上周在Q时间点时的实际货量值，Y=上周当天总货量值）。
			//如果W、Y值均为0时，P显示“0”。
			ForecastQuantityEntity forecastQuantityP = forecastQuantityQ.divide(forecastQuantityW.divide(forecastQuantityY));
			if(forecastQuantityP == null) {
				forecastQuantityP = ForecastQuantityEntity.newForecastQuantityEntity();
			}
			forecastMap.put("P", forecastQuantityP);
		} else {
			//未来货量
			//实际开单货量
			forecastQuantityParam.setDataType(ForecastConstants.DATA_TYPE_TWO);
//			System.err.println(1);
			//A=前1日货量值
			Date forecastTimeTemp = DateUtils.addDayToDate(forecastTime, ForecastConstants.NUMBER_MINUS_1);
			forecastQuantityParam.setForecastTime(forecastTimeTemp);
			forecastQuantityParam.setStatisticsDate(forecastTimeTemp);
			ForecastQuantityEntity forecastQuantityA = forecastQuantityDaoPlatform.countForecastQtyOneDay(forecastQuantityParam);
			if(forecastQuantityA == null) {
				forecastQuantityA = ForecastQuantityEntity.newForecastQuantityEntity();
			}
			//B=前6日货量值
			forecastTimeTemp = DateUtils.addDayToDate(forecastTime, ForecastConstants.NUMBER_MINUS_6);
			forecastQuantityParam.setForecastTime(forecastTimeTemp);
			forecastQuantityParam.setStatisticsDate(forecastTimeTemp);
			ForecastQuantityEntity forecastQuantityB = forecastQuantityDaoPlatform.countForecastQtyOneDay(forecastQuantityParam);
			if(forecastQuantityB == null) {
				forecastQuantityB = ForecastQuantityEntity.newForecastQuantityEntity();
			}
			forecastMap.put("B", forecastQuantityB);
			
			//C=前8日货量值
			forecastTimeTemp = DateUtils.addDayToDate(forecastTime, ForecastConstants.NUMBER_MINUS_8);
			forecastQuantityParam.setForecastTime(forecastTimeTemp);
			forecastQuantityParam.setStatisticsDate(forecastTimeTemp);
			ForecastQuantityEntity forecastQuantityC = forecastQuantityDaoPlatform.countForecastQtyOneDay(forecastQuantityParam);
			if(forecastQuantityC == null) {
				forecastQuantityC = ForecastQuantityEntity.newForecastQuantityEntity();
			}
			
			//D=前13日货量值
			forecastTimeTemp = DateUtils.addDayToDate(forecastTime, ForecastConstants.NUMBER_MINUS_13);
			forecastQuantityParam.setForecastTime(forecastTimeTemp);
			forecastQuantityParam.setStatisticsDate(forecastTimeTemp);
			ForecastQuantityEntity forecastQuantityD = forecastQuantityDaoPlatform.countForecastQtyOneDay(forecastQuantityParam);
			if(forecastQuantityD == null) {
				forecastQuantityD = ForecastQuantityEntity.newForecastQuantityEntity();
			}
			
			//E=前15日货量值
			forecastTimeTemp = DateUtils.addDayToDate(forecastTime, ForecastConstants.NUMBER_MINUS_15);
			forecastQuantityParam.setForecastTime(forecastTimeTemp);
			forecastQuantityParam.setStatisticsDate(forecastTimeTemp);
			ForecastQuantityEntity forecastQuantityE = forecastQuantityDaoPlatform.countForecastQtyOneDay(forecastQuantityParam);
			if(forecastQuantityE == null) {
				forecastQuantityE = ForecastQuantityEntity.newForecastQuantityEntity();
			}
			
			//未来一天总货量预测值计算公式：   X = A * ((B + D) / (C + E)) * 100%
			//( X=未来一天货量预测值，A=前1日货量值，B=前6日货量值，C=前8日货量值，D=前13日货量值，E=前15日货量值。
			//A、B、C、D、E数据均来至之前货量统计中保存的实际货量的统计值)
			ForecastQuantityEntity forecastQuantityX = forecastQuantityA.multiply(forecastQuantityB.add(forecastQuantityD).divide(forecastQuantityC.add(forecastQuantityE)));
			forecastMap.put("X", forecastQuantityX);
		}
		
		return forecastMap;
	}
	
	/**
	 * 根据日期获取该日期所在周的周一
	 * @author 163580
	 * @date 2014-4-16 下午2:23:45
	 * @param date
	 * @see
	 */
	public static Date getMonday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 关于DAY_OF_WEEK的说明
		// Field number for get and set indicating
		// the day of the week. This field takes values
		// SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY,
		// and SATURDAY
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return cal.getTime();
	}
	
	/**
	 * 
	 * 预测未来货量的计算
	 * a.未来一天未开单货量预测模型：对未来一天未开单部分的货量进行预测。
	 *	(1)未来一天总货量预测值计算公式：X = A * ((B + D) / (C + E)) * 100%
	 *	(X=未来一天货量预测值，A=前1日货量值，B=前6日货量值，C=前8日货量值，D=前13日货量值，E=前15日货量值。
	 *	A、B、C、D、E数据均来至之前货量统计中保存的实际货量的统计值)，当C+E=0时，预测未开单货量显示为“0”。
	 *
	 *（2）未来一天各线路货量预测值计算公式：M = X * (N / B)
	 * (X=未来一天货量预测值，B=前6日货量值，M=未来一天某一线路的货量预测值，N=前6日此条线路的货量值，
	 * N、B数据均来至之前货量统计中保存的实际货量的统计值)。当B=0时，未来一天某一线路的货量预测值M=“0”。
	 * 
	 *（3）未来一天未开单货量预测计算公式：H=M-S（H=未来一天未开单货量预测值，M=未来一天某一线路货量预测货值，S=未来一天已开单货量值）
	 *
	 *（4）如果未来一天已开单货量值S≥未来一天某一线路的货量预测值M，则未来一天未开单货量预测值H=0。
	 *
	 * b.对当日货量预测模型：对当天为开单部分的货量进行预测。
	 *（1）当天总货量预测值计算公式： P = Q / (W / Y)
	 *（P=当日总货量预测值，Q=某一时间点的实际开单货量值，W=上周在Q时间点时的实际货量值，Y=上周总货量值）。
	 * 如果W、Y值均为0时，P显示“0”。
	 *
	 *（2）当天各线路货量预测值计算公式：M = P * (N / Y)
	 *(M=当日某一线路货量预测值,P=当日总货量预测值，N=上周此线路货量值，Y=上周总货量值)。当Y=0时，M显示“0”。
	 *
	 *（3）当日未开单货量预测计算公式：R = M - S
	 *（R=当日某线路未开单货量预测值，M=当日某一线路货量预测货值，S=当日某线路已开单货量值）
	 *（4）如果当日某线路已开单货量值S≥当日某一线路的货量预测值M，则当日未开单货量预测值R=0。
	 * @author 163580
	 * @date 2014-3-24 下午5:24:07
	 * @param statistics job执行时间
	 * @param startTime 时间点
	 * @param day 预测截至日期
	 * @param orgCode 所属外场
	 * @param relevantOrgCode 营业部编号
	 * @param action 操作类型(出发/到达)
	 * @param departurearrival 明细类型(本地出发/二次中转/到达中转等等...)
	 * @see
	 */
	private void forecastingQuantity(ForecastQuantityEntity forecastQuantityParam,
			boolean flag, ForecastQuantityEntity forecastQuantityP, ForecastQuantityEntity forecastQuantityY,
			ForecastQuantityEntity forecastQuantityX, ForecastQuantityEntity forecastQuantityB) {
		String orgCode = forecastQuantityParam.getBelongOrgCode();
		String relevantOrgCode = forecastQuantityParam.getRelevantOrgCode();
		String action = forecastQuantityParam.getType();
		Date statisticsTime = forecastQuantityParam.getStatisticsTime();
		Date forecastTime = forecastQuantityParam.getForecastTime();
		Date statisticsDate = forecastQuantityParam.getStatisticsDate();
		String statisticsHHMM = forecastQuantityParam.getStatisticsHHMM();
		String departurearrival = forecastQuantityParam.getDeparturearrival();
		
		String region = "无";
		List<SiteGroupEntity> siteGroupList = siteGroupService.querySiteGroupsBySiteCode(relevantOrgCode);
		if (CollectionUtils.isEmpty(siteGroupList)) {
			logger.error("调用综合接口根据组织查询站点组为空! 类: " + siteGroupService + " 方法: querySiteGroupsBySiteCode orgCode : " + relevantOrgCode);
			siteGroupList = new ArrayList<SiteGroupEntity>();
		}
		
		// 如果出发 查询出发站点组
		if (StringUtil.equals(ForecastConstants.FORECAST_DEPART, action)) {
			//出发类型
			
			//所属站点组
			for (int i = 0; i < siteGroupList.size(); i++) {
				// 如果站点组类型等于出发,则该条为外场出发站点组
				if (StringUtil.equals(siteGroupList.get(i).getType(), DictionaryValueConstants.BSE_SITE_GROUP_TYPE_CF)) {
					region = siteGroupList.get(i).getVirtualCode();
				}
			}
		} else {
			//到达类型
			// 到达 查询到达站点组
			for (int i = 0; i < siteGroupList.size(); i++) {
				// 如果站点组类型等于到达,则该条为外场到达站点组
				if (StringUtil.equals(siteGroupList.get(i).getType(), DictionaryValueConstants.BSE_SITE_GROUP_TYPE_DD)) {
					region = siteGroupList.get(i).getVirtualCode();
				}
			}
		}
		
		if(flag) {
			//如果是当天货量
			//对当日货量预测模型：对当天未开单部分的货量进行预测。
			
			//N=上周此线路货量值
			//例如当前时间为 2014-06-07 10:00:00  那么统计的时间则应该为 2014-06-01 00:00:00(业务要求)
//			forecastQuantityParam.setForecastTime(forecastTimeTemp);
//			
			Date forecastTimeTemp = DateUtils.addDayToDate(forecastTime, ForecastConstants.NUMBER_MINUS_6);
			forecastQuantityParam.setForecastTime(forecastTimeTemp);
			forecastQuantityParam.setStatisticsDate(forecastTimeTemp);
			forecastQuantityParam.setStatisticsHHMM("0000");
			ForecastQuantityEntity forecastQuantityN = forecastQuantityDaoPlatform.countForecastQtyOneDay(forecastQuantityParam);
			if(forecastQuantityN == null) {
				forecastQuantityN = ForecastQuantityEntity.newForecastQuantityEntity();
			}
			forecastQuantityParam.setStatisticsHHMM(statisticsHHMM);
//			Date forecastTimeTemp = DateUtils.addDayToDate(forecastTime, ForecastConstants.NUMBER_MINUS_7);
//			forecastTimeTemp = getMonday(forecastTimeTemp);
//			forecastQuantityParam.setForecastTime(forecastTimeTemp);
//			ForecastQuantityEntity forecastQuantityN = forecastQuantityDaoPlatform.countForecastQtyOneWeek(forecastQuantityParam);
////			ForecastQuantityEntity forecastQuantityN = forecastQuantityQ.divide(forecastQuantityW.divide(forecastQuantityY));
//			if(forecastQuantityN == null) {
//				forecastQuantityN = ForecastQuantityEntity.newForecastQuantityEntity();
//			}
			
			//（2）当天各线路货量预测值计算公式： M = P * (N / Y)
			//(M=当日某一线路货量预测值,P=当日总货量预测值，N=上周此线路货量值，Y=上周总货量值)。当Y=0时，M显示“0”。
			ForecastQuantityEntity forecastQuantityM = forecastQuantityP.multiply(forecastQuantityN.divide(forecastQuantityY));
			if(forecastQuantityM == null) {
				forecastQuantityM = ForecastQuantityEntity.newForecastQuantityEntity();
			}
			
			//S=当日某线路已开单货量值
			forecastQuantityParam.setForecastTime(forecastTime);
			forecastQuantityParam.setStatisticsDate(forecastTime);
			ForecastQuantityEntity forecastQuantityS = forecastQuantityDaoPlatform.countForecastQtyOneDay(forecastQuantityParam);
			if(forecastQuantityS == null) {
				forecastQuantityS = ForecastQuantityEntity.newForecastQuantityEntity();
			}
			
			//（3）当日未开单货量预测计算公式：R = M - S
			//R=M-S（R=当日某线路未开单货量预测值，M=当日某一线路货量预测货值，S=当日某线路已开单货量值）
			ForecastQuantityEntity forecastQuantityR = forecastQuantityM.subtract(forecastQuantityS);
			
			forecastQuantityR.setForecastQuantityId(UUIDUtils.getUUID());
			forecastQuantityR.setDataType(ForecastConstants.DATA_TYPE_ONE);
			forecastQuantityR.setBelongOrgCode(orgCode);
			forecastQuantityR.setRelevantOrgCode(relevantOrgCode);
			forecastQuantityR.setRegion(region);
			forecastQuantityR.setType(action);
			forecastQuantityR.setStatisticsTime(statisticsTime);
			forecastQuantityR.setForecastTime(forecastTime);
			forecastQuantityR.setStatisticsDate(statisticsDate);
			forecastQuantityR.setStatisticsHHMM(statisticsHHMM);
			forecastQuantityR.setDeparturearrival(departurearrival);
//			forecastQuantityDaoPlatform.addforecastQuantity(forecastQuantityR);
			super.addForecastQuantityEntityService(forecastQuantityR);
		} else {
			//（2）未来一天各线路货量预测值计算公式： M = X * (N / B)
			//(X=未来一天货量预测值，B=前6日货量值，M=未来一天某一线路的量预测货值，N=前6日此条线路的货量值，
			//N、B数据均来至之前货量统计中保存的实际货量的统计值(这里其实就是data_type = 2的数据))
			//计算当前部门到当前线路的货量
			forecastQuantityParam.setRelevantOrgCode(relevantOrgCode);
			
			//N=前6日此条线路的货量值
			Date forecastTimeTemp = DateUtils.addDayToDate(forecastTime, ForecastConstants.NUMBER_MINUS_6);
			forecastQuantityParam.setForecastTime(forecastTimeTemp);
			forecastQuantityParam.setStatisticsDate(forecastTimeTemp);
			ForecastQuantityEntity forecastQuantityN = forecastQuantityDaoPlatform.countForecastQtyOneDay(forecastQuantityParam);
			if(forecastQuantityN == null) {
				forecastQuantityN = ForecastQuantityEntity.newForecastQuantityEntity();
			}
			
			//未来一天各线路货量预测值计算公式： M = X * (N / B)
			ForecastQuantityEntity forecastQuantityM = forecastQuantityX.multiply(forecastQuantityN.divide(forecastQuantityB));
			
			//（3）未来一天未开单货量预测计算公式：H = M - S
			//（H=未来一天未开单货量预测值，M=未来一天某一线路货量预测货值，S=未来一天已开单货量值）
			
			//S = 未来一天已开单货量值
			forecastQuantityParam.setDataType(ForecastConstants.DATA_TYPE_TWO);
			forecastTimeTemp = DateUtils.addDayToDate(forecastTime, PlatformConstants.SONAR_NUMBER_1);
			forecastQuantityParam.setForecastTime(forecastTimeTemp);
			forecastQuantityParam.setStatisticsDate(statisticsDate);
			ForecastQuantityEntity forecastQuantityS = forecastQuantityDaoPlatform.countForecastQtyOneDay(forecastQuantityParam);
			if(forecastQuantityS == null) {
				forecastQuantityS = ForecastQuantityEntity.newForecastQuantityEntity();
			}
			
			//未来一天未开单货量预测计算公式：H = M - S
			ForecastQuantityEntity forecastQuantityH = forecastQuantityM.subtract(forecastQuantityS);
			
			//未来一天未开单货量
			forecastQuantityH.setForecastQuantityId(UUIDUtils.getUUID());
			forecastQuantityH.setDataType(ForecastConstants.DATA_TYPE_ONE);
			forecastQuantityH.setBelongOrgCode(orgCode);
			forecastQuantityH.setRelevantOrgCode(relevantOrgCode);
			forecastQuantityH.setRegion(region);
			forecastQuantityH.setType(action);
			forecastQuantityH.setStatisticsTime(statisticsTime);
			forecastQuantityH.setForecastTime(forecastTime);
			forecastQuantityH.setStatisticsDate(statisticsDate);
			forecastQuantityH.setStatisticsHHMM(statisticsHHMM);
			forecastQuantityH.setDeparturearrival(departurearrival);
			//forecastQuantityDaoPlatform.addforecastQuantity(forecastQuantityH);
			super.addForecastQuantityEntityService(forecastQuantityH);
			
			
			//老的计算方式(计算货物四周均量然后保存)
//			ForecastQuantityEntity forecastQuantity = forecastQuantityDaoPlatform.countForecastQty(forecastQuantityParam);
//			if(forecastQuantity == null) {
//				forecastQuantity = ForecastQuantityEntity.newForecastQuantityEntity();
//			}
//			forecastQuantity.setForecastQuantityId(UUIDUtils.getUUID());
//			forecastQuantity.setDataType(ForecastConstants.DATA_TYPE_ONE);
//			forecastQuantity.setBelongOrgCode(orgCode);
//			forecastQuantity.setRelevantOrgCode(relevantOrgCode);
//			forecastQuantity.setRegion(region);
//			forecastQuantity.setType(action);
//			forecastQuantity.setStatisticsTime(statistics);
//			forecastQuantity.setForecastTime(forecastQuantityParam.getForecastTime());
//			forecastQuantity.setStatisticsDate(DateUtils.getStartDatetime(statistics));
//			forecastQuantity.setStatisticsHHMM(forecastQuantityParam.getStatisticsHHMM());
//			forecastQuantity.setDeparturearrival(forecastQuantityParam.getDeparturearrival());
//			forecastQuantityDaoPlatform.addforecastQuantity(forecastQuantity);
		}
	}
	
	/**
	 * 
	 * @Title: forecastTransferCenterArrive 
	 * @Description: 到达 【 预测类型（出发，到达）】
	 * @author yuyongxiang-134019-yuyongxiang@deppon.com
	 * @date 2014年3月19日 上午9:58:59 
	 * @param action
	 * @param statistics
	 * @param orgCode    设定文件 
	 * @param allTransferCenterCodes (所有外场)
	 * @param salesDepartmentCodeList (orgCode所辐射的营业部)
	 * @return void    返回类型
	 */
	@SuppressWarnings("unchecked")
	protected void forecastTransferCenterArrive(String action, Date statistics, String orgCode){
		// 查询该部门走货路径最后到达时间
		Date maxArriveTime = pathDetailDao.queryMaxArriveTime(orgCode);
		
		//如果为空
		if (null == maxArriveTime) {
			logger.info("查询部门" + orgCode + "的走货路径最后到达时间没有得到数据!");
			return;
		}
		
		// 根据预测时间点 ,组织code 查询预测时间段
		// 获取开始时间点
		ConfigurationParamsEntity entityStart = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR, ConfigurationParamsConstants.TFR_PARM__FORECAST_START, orgCode);
		//如果不为空
		if (entityStart != null && StringUtils.isNotEmpty(entityStart.getConfValue())) {
			super.start1 = entityStart.getConfValue();
		}
		// 获取持续天数
		ConfigurationParamsEntity entityDuration = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR, ConfigurationParamsConstants.TFR_PARM__FORECAST_DURATION, orgCode);
		//如果不为空
		if (entityDuration != null && StringUtils.isNotEmpty(entityDuration.getConfValue())) {
			super.day1 = Integer.valueOf(entityDuration.getConfValue());
		}
		
//		List<String> relevantOrgCodes = getSalesDeptListByTransferCode(orgCode);
//		List<String> relevantOrgCodes = salesDepartmentCodeList;
		
		// 新建预测时间 根据当前时间判断
		Date forecastTime;
		// 修改时间格式 只取日期
		//开始时间
		Date midStartTime = DateUtils.convert(DateUtils.convert(statistics, DateUtils.DATE_FORMAT), DateUtils.DATE_FORMAT);
		//到达时间
		Date midEndTime = TimeUtils.createStartTime(midStartTime, super.start1);
		
		// 如果现在时间小于当天的周期起始时间,并且大于0点.则预测的周期应该是昨天的周期
		if (statistics.after(midStartTime) && statistics.before(midEndTime)) {
			//计算时间 减一天
			forecastTime = TimeUtils.convertStringToDate(statistics, super.start1, -1);
		} else {// 否则就是当天的周期
			forecastTime = TimeUtils.createStartTime(statistics, super.start1);
		}
		// 判断需要计算几天的 小于1天返回1
		//2014-07-21 maxArriveTime时间在数据库存在2015-02月分的数据, 这是不应该的, 所以下方for循环处改为使用day1
//		int diffDay = DateUtils.getTimeDiff(forecastTime, maxArriveTime).intValue() + 1;
		
		//中转到中转线路orgCode到达的所有出发部门
		List<String> allTransferCenterCodes = getAllDeptCodeDepartTransfer().get(orgCode);
		if(CollectionUtils.isEmpty(allTransferCenterCodes)) {
			allTransferCenterCodes = queryDeptCodeDepartTransfer(orgCode);
			getAllDeptCodeArrivalTransfer().put(orgCode, allTransferCenterCodes);
		}
		
		//始发线路orgCode到达的所有出发部门
		List<String> relevantOrgCodes = getAllDeptCodeTarget().get(orgCode);
		if(CollectionUtils.isEmpty(relevantOrgCodes)) {
			relevantOrgCodes = queryDeptCodeTarget(orgCode);
			getAllDeptCodeTarget().put(orgCode, relevantOrgCodes);
		}
		
		for (int d = PlatformConstants.SONAR_NUMBER_0; d < day1; d++) {
			// 如果预测开始时间大于最大时间.则不继续循环否则继续
			if (forecastTime.after(maxArriveTime)) {
				break;
			} else {
				//设置预测时间
				Date forecastStartTime = forecastTime;
				//设置结束时间
				Date forecastEndTime = TimeUtils.convertStringToDate(forecastTime, super.start1, super.day1);
				
				if (null == forecastEndTime) {
					logger.error("转换时间出错!方法: forecastTransferCenterArrive");
				}
				
				Map<String,Object> map =new HashMap<String, Object>();
				
	//		    /**到达 - 长途到达*/
				for (String relevantOrgCode : allTransferCenterCodes) {
					this.arriveRelevant(relevantOrgCode,orgCode,forecastStartTime, forecastEndTime, statistics,
							ForecastConstants.ARRIVE_LONGREACH, map, relevantOrgCodes);
				}
	//		    /**到达 - 短途到达*/
				for (String relevantOrgCode : relevantOrgCodes) {
					this.arriveRelevant(relevantOrgCode, orgCode, forecastStartTime, forecastEndTime, statistics,
							ForecastConstants.ARRIVE_SHORTREACH, map, relevantOrgCodes);
				}
	//		    /**到达 - 集中接货*/
				for (String relevantOrgCode:relevantOrgCodes) {
					this.arriveRelevant(relevantOrgCode, orgCode, forecastStartTime, forecastEndTime, statistics,
							ForecastConstants.ARRIVE_CENTRALIZEDPICK, map, relevantOrgCodes);
				}
				
	//		    /**到达 - 全部*/
				// 开单未交接的货量
				List<ForecastDto> billingList=null;
				// 在途货量
				List<ForecastDto> inTransitList=null;
				// 在库货量
				List<ForecastDto> inventoryList=null;
				
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					Map<String, Object> mapTemp=(Map<String, Object>) map.get(entry.getKey());
					billingList = (List<ForecastDto>) mapTemp.get("billingList");
					inTransitList = (List<ForecastDto>) mapTemp.get("inTransitList");
					inventoryList = (List<ForecastDto>) mapTemp.get("inventoryList");
					
					super.forecast(ForecastConstants.FORECAST_ARRIVE,ForecastConstants.ARRIVE_ALL,forecastStartTime, 
							statistics, orgCode, entry.getKey(), billingList,inTransitList, inventoryList,relevantOrgCodes);
				}
				
				
				// 更新forecastTime为截止时间
				forecastTime = forecastEndTime;
			}
		}
		
		//预测未来货量的计算方法：zyx
		//预测未来货量的时间范围同转运场所配置查询的货量统计保持一致(当前凌晨3点为界  0点以后 3点以前预测的时候当天的数据
		//	例如: 当前时间为 2014-03-24 :02:00:00  此时预测24号02:00 == >03:00的数据
		//	例如: 当前时间为 2014-03-24 :03:00:00  此时预测25号03:00 == >04:00的数据)
		//时间点, 用来比较当前时间在该时间点的位置
		Date start1Time = TimeUtils.createStartTime(new Date(), start1);
		
		//查询条件
		ForecastQuantityEntity forecastQuantityParam = new ForecastQuantityEntity();
		forecastQuantityParam.setBelongOrgCode(orgCode);
		forecastQuantityParam.setType(action);
		forecastQuantityParam.setDataType(ForecastConstants.DATA_TYPE_TWO);
		forecastQuantityParam.setStatisticsTime(statistics);
		forecastQuantityParam.setStatisticsHHMM(DateUtils.convert(DateUtils.convert(DateUtils.convert(statistics, "HH"), "HH"), "HHmm"));
		forecastQuantityParam.setStatisticsDate(DateUtils.getStartDatetime(statistics));
		//如果预测时间大于所配置的时间, 就从当前开始预测, 反之则从上一日开始
		if(statistics.compareTo(start1Time) >= PlatformConstants.SONAR_NUMBER_0) {
			forecastQuantityParam.setForecastTime(DateUtils.getStartDatetime(statistics));
		} else {
			forecastQuantityParam.setForecastTime(DateUtils.addDayToDate(DateUtils.getStartDatetime(statistics), -1));
		}
		
		forecastTime = forecastQuantityParam.getForecastTime();
		
		for(int i = PlatformConstants.SONAR_NUMBER_0; i < day1; i++) {
			ForecastQuantityEntity forecastQuantityP = null;
			ForecastQuantityEntity forecastQuantityY = null;
			ForecastQuantityEntity forecastQuantityX = null;
			ForecastQuantityEntity forecastQuantityB = null;
			
			boolean flag = false;
			if(DateUtils.getStartDatetime(statistics).compareTo(DateUtils.getStartDatetime(forecastTime)) == 0) {
				//如果被预测的日期等于当前日期, 说明当前计算的是当天货量
				//当天货量需特殊处理
				flag = true;
			}
			
			forecastQuantityParam.setDeparturearrival(ForecastConstants.ARRIVE_LONGREACH);
			ForecastQuantityEntity forecastQuantityParamTemp = new ForecastQuantityEntity();
			BeanUtils.copyProperties(forecastQuantityParam, forecastQuantityParamTemp);
			Map<String, ForecastQuantityEntity> forecastMap = forecastQuantity(flag, forecastTime, forecastQuantityParamTemp);
			forecastQuantityP = forecastMap.get("P");
			forecastQuantityY = forecastMap.get("Y");
			forecastQuantityX = forecastMap.get("X");
			forecastQuantityB = forecastMap.get("B");
			
//			/**到达 - 长途到达*/
			for (String relevantOrgCode : allTransferCenterCodes) {
				forecastQuantityParam.setRelevantOrgCode(relevantOrgCode);
				forecastQuantityParam.setDeparturearrival(ForecastConstants.ARRIVE_LONGREACH);
				forecastingQuantity(forecastQuantityParam, flag, forecastQuantityP, forecastQuantityY, forecastQuantityX, forecastQuantityB);
			}
			
			forecastQuantityParam.setDeparturearrival(ForecastConstants.ARRIVE_SHORTREACH);
			forecastQuantityParamTemp = new ForecastQuantityEntity();
			BeanUtils.copyProperties(forecastQuantityParam, forecastQuantityParamTemp);
			forecastMap = forecastQuantity(flag, forecastTime, forecastQuantityParamTemp);
			forecastQuantityP = forecastMap.get("P");
			forecastQuantityY = forecastMap.get("Y");
			forecastQuantityX = forecastMap.get("X");
			forecastQuantityB = forecastMap.get("B");
//		    /**到达 - 短途到达*/
			for (String relevantOrgCode : relevantOrgCodes) {
				forecastQuantityParam.setRelevantOrgCode(relevantOrgCode);
				forecastQuantityParam.setDeparturearrival(ForecastConstants.ARRIVE_SHORTREACH);
				forecastingQuantity(forecastQuantityParam, flag, forecastQuantityP, forecastQuantityY, forecastQuantityX, forecastQuantityB);
			}
			
			forecastQuantityParam.setDeparturearrival(ForecastConstants.ARRIVE_CENTRALIZEDPICK);
			forecastQuantityParamTemp = new ForecastQuantityEntity();
			BeanUtils.copyProperties(forecastQuantityParam, forecastQuantityParamTemp);
			forecastMap = forecastQuantity(flag, forecastTime, forecastQuantityParamTemp);
			forecastQuantityP = forecastMap.get("P");
			forecastQuantityY = forecastMap.get("Y");
			forecastQuantityX = forecastMap.get("X");
			forecastQuantityB = forecastMap.get("B");
//		    /**到达 - 集中接货*/
			for (String relevantOrgCode:relevantOrgCodes) {
				forecastQuantityParam.setRelevantOrgCode(relevantOrgCode);
				forecastQuantityParam.setDeparturearrival(ForecastConstants.ARRIVE_CENTRALIZEDPICK);
				forecastingQuantity(forecastQuantityParam, flag, forecastQuantityP, forecastQuantityY, forecastQuantityX, forecastQuantityB);
			}
			
			
			forecastQuantityParam.setDeparturearrival(ForecastConstants.DEPART_ALL);
			forecastQuantityParamTemp = new ForecastQuantityEntity();
			BeanUtils.copyProperties(forecastQuantityParam, forecastQuantityParamTemp);
			forecastMap = forecastQuantity(flag, forecastTime, forecastQuantityParamTemp);
			forecastQuantityP = forecastMap.get("P");
			forecastQuantityY = forecastMap.get("Y");
			forecastQuantityX = forecastMap.get("X");
			forecastQuantityB = forecastMap.get("B");
			List<String> allDeptCode = new ArrayList<String>();
			//所有外场code
			allDeptCode.addAll(allTransferCenterCodes);
			//当前外场辐射的所有营业部
			allDeptCode.addAll(relevantOrgCodes);
			//所有派送部
			allDeptCode.addAll(getAlldeliveryStation());
			//全部
			//去重
			Set<String> temp = new HashSet<String>();
			temp.addAll(allDeptCode);
			List<String> deptCodes = new ArrayList<String>();
			deptCodes.addAll(temp);
			for (String relevantOrgCode : deptCodes) {
				forecastQuantityParam.setRelevantOrgCode(relevantOrgCode);
				forecastQuantityParam.setDeparturearrival(ForecastConstants.DEPART_ALL);
				forecastingQuantity(forecastQuantityParam, flag, forecastQuantityP, forecastQuantityY, forecastQuantityX, forecastQuantityB);
			}
			
			forecastQuantityParam.setForecastTime(DateUtils.addDayToDate(forecastQuantityParam.getForecastTime(), 1));
		}
		//end
	}
	
	public static void main(String[] args) {
		List<String> strs = new ArrayList<String>(PlatformConstants.SONAR_NUMBER_100);
		for(int i = PlatformConstants.SONAR_NUMBER_0; i < PlatformConstants.SONAR_NUMBER_100; i++) {
			strs.add(i+"");
		}
		
		int count = 0;
		count = strs.size() / PlatformConstants.SONAR_NUMBER_30;
		if(strs.size() % PlatformConstants.SONAR_NUMBER_30 > PlatformConstants.SONAR_NUMBER_0) {
			count++;
		}
		System.err.println(count);
	}
	
	/**
	 * 当前方法根据大的状态来拆分 list里面需要显示的字段并且做计算
	 * @param orgCode 出发部门
	 * @param relevantOrgCode 到达部门
	 * @param forecastStartTime 预测周期开始时间
	 * @param forecastEndTime 预测周期结束时间
	 * @param forecastTime 当前周期的开始时间
	 * @param statistics job任务触发时间
	 * 
	 * @author yuyongxiang - 134019
	 * @date 2014-03-07 09:30:31
	 */
	@SuppressWarnings("unchecked")
	private void departRelevant(String orgCode, String relevantOrgCode, Date forecastStartTime, Date forecastEndTime, 
			Date statistics, String status,Map<String, Object> map, List<String> salesDepartmentCodeList){
		// 开单未交接的货量
		List<ForecastDto> billingList=null;
		// 在途货量
		List<ForecastDto> inTransitList=null;
		// 在库货量
		List<ForecastDto> inventoryList=null;
		
		if(ForecastConstants.DEPART_LOCALDEPARTURE.equals(status)) {
			//本地出发
			//开单未交接的货量
			List<PathDetailEntity> departBillingList = forecastQuantityJOBDao.queryBillingUnHandoverLocal(orgCode, relevantOrgCode, 
					forecastStartTime, forecastEndTime);
			billingList = this.waybillProcess(departBillingList);
			// 查询出发在途货量
			List<PathDetailEntity> departInTransitList = forecastQuantityJOBDao.queryOnthewayLocal(orgCode, relevantOrgCode, 
					forecastStartTime, forecastEndTime);
			inTransitList = this.waybillProcess(departInTransitList);
			// 查询出发在库货量
			List<PathDetailEntity> departInventoryList = super.departInventory(orgCode, relevantOrgCode,forecastStartTime, forecastEndTime);
			inventoryList = this.waybillProcess(departInventoryList);
		} else if(ForecastConstants.DEPART_SECONDARYTRANSIT.equals(status)) {
			//二次中转
			//开单未交接的货量
			List<PathDetailEntity> departBillingList = forecastQuantityJOBDao.queryBillingUnHandoverSecond(orgCode, relevantOrgCode, forecastStartTime, forecastEndTime);
			billingList = this.waybillProcess(departBillingList);
			// 查询出发在途货量
			List<PathDetailEntity> departInTransitList = forecastQuantityJOBDao.queryOnthewaySecond(orgCode, relevantOrgCode, forecastStartTime, forecastEndTime);
			inTransitList = this.waybillProcess(departInTransitList);
			// 查询出发在库货量
			List<PathDetailEntity> departInventoryList = super.departInventory(orgCode, relevantOrgCode,forecastStartTime, forecastEndTime);
			inventoryList = this.waybillProcess(departInventoryList);
		} else if(ForecastConstants.DEPART_ARRIVALTRANSIT.equals(status)) {
			//到达中转
			//开单未交接的货量
			List<PathDetailEntity> departBillingList = forecastQuantityJOBDao.queryBillingUnHandoverArrival(orgCode, relevantOrgCode, forecastStartTime, forecastEndTime);
			billingList = this.waybillProcess(departBillingList);
			// 查询出发在途货量
			List<PathDetailEntity> departInTransitList = forecastQuantityJOBDao.queryOnthewayArrival(orgCode, relevantOrgCode, forecastStartTime, forecastEndTime);
			inTransitList = this.waybillProcess(departInTransitList);
			// 查询出发在库货量
			List<PathDetailEntity> departInventoryList = super.departInventory(orgCode, relevantOrgCode,forecastStartTime, forecastEndTime);
			inventoryList = this.waybillProcess(departInventoryList);
		} else {
			//派送货量
			//开单未交接的货量
			List<PathDetailEntity> departBillingList = forecastQuantityJOBDao.queryBillingUnHandoverTransit(orgCode, relevantOrgCode, forecastStartTime, forecastEndTime);
			billingList = this.waybillProcess(departBillingList);
			
			// 查询出发在途货量
			List<PathDetailEntity> departInTransitList =  forecastQuantityJOBDao.queryOnthewayTransit(orgCode, relevantOrgCode, forecastStartTime, forecastEndTime);
			inTransitList = this.waybillProcess(departInTransitList);
			// 查询出发在库货量
			List<PathDetailEntity> departInventoryList = super.departInventoryV(orgCode, relevantOrgCode,forecastStartTime, forecastEndTime);
			inventoryList = this.waybillProcess(departInventoryList);
		}
		
		// 根据到达部门统计条目信息 新增到主表
		super.forecast(ForecastConstants.FORECAST_DEPART, status, forecastStartTime, statistics, orgCode, relevantOrgCode,
				billingList, inTransitList, inventoryList, salesDepartmentCodeList);

		if(map.containsKey(relevantOrgCode)){
			Map<String,Object> mapTemp= (Map<String, Object>)map.get(relevantOrgCode);
			
			((List<ForecastDto>) mapTemp.get("inventoryList")).addAll(inventoryList);
			((List<ForecastDto>) mapTemp.get("billingList")).addAll(billingList);
			((List<ForecastDto>) mapTemp.get("inTransitList")).addAll(inTransitList);
		}else{
			Map<String,Object>mapTemp=new HashMap<String, Object>(PlatformConstants.SONAR_NUMBER_3);
			//全部统计
			mapTemp.put("inventoryList", inventoryList);
			mapTemp.put("billingList", billingList);
			mapTemp.put("inTransitList", inTransitList);
			map.put(relevantOrgCode, mapTemp);
		}
		
	}

		/**
		 * @Title: arriveRelevant 
		 * @Description: 当前方法根据大的状态来拆分 list里面需要显示的字段并且做计算
		 * 例 ： 到达-长途到达（直接辐射的营业部）
		 * @author yuyongxiang-134019-yuyongxiang@deppon.com
		 * @date 2014年3月21日 上午10:55:50 
		 * @param orgCode
		 * @param relevantOrgCode
		 * @param forecastStartTime
		 * @param forecastEndTime
		 * @param statistics
		 * @param status    设定文件 
		 * @return void    返回类型
		 */
		@SuppressWarnings("unchecked")
		private void arriveRelevant(String orgCode, String relevantOrgCode, Date forecastStartTime, Date forecastEndTime, 
				Date statistics, String status, Map<String,Object> map, List<String> salesDepartmentCodeList){
				/**
				 * 开单未交接的货量
				 */
				List<ForecastDto> billingList = new ArrayList<ForecastDto>(); 
				/**
				 * 在途货量
				 */
				List<ForecastDto> inTransitList = new ArrayList<ForecastDto>();
				/**
				 * 在库货量 (到达没有在库货量)
				 */
				List<ForecastDto> inventoryList = new ArrayList<ForecastDto>(PlatformConstants.SONAR_NUMBER_0);
				if(StringUtils.equals(ForecastConstants.ARRIVE_LONGREACH, status)) {
					//长途到达
					//orgCode 到达部门
					//relevantOrgCode 出发部门
					List<PathDetailEntity> arriveBillingList = forecastQuantityJOBDao.queryBillingUnHandoverLongReach(orgCode, relevantOrgCode, forecastStartTime, forecastEndTime);
					billingList = this.waybillProcess(arriveBillingList);
					
					// 查询到达在途货量
					List<PathDetailEntity> arriveInTransitList = forecastQuantityJOBDao.queryOnthewayPickLongReach(orgCode, relevantOrgCode, forecastStartTime, forecastEndTime);
					inTransitList = this.waybillProcess(arriveInTransitList);
					
				} else if(ForecastConstants.ARRIVE_SHORTREACH.equals(status)) {
					//短途到达
					//长途到达
					//orgCode 到达部门
					//relevantOrgCode 出发部门
					List<PathDetailEntity> arriveBillingList = forecastQuantityJOBDao.queryBillingUnHandoverShortReach(orgCode, relevantOrgCode, forecastStartTime, forecastEndTime);
					billingList = this.waybillProcess(arriveBillingList);
					
					// 查询到达在途货量
					List<PathDetailEntity> arriveInTransitList = forecastQuantityJOBDao.queryOnthewayPickShortReach(orgCode, relevantOrgCode, forecastStartTime, forecastEndTime);
					inTransitList = this.waybillProcess(arriveInTransitList);
				} else if(ForecastConstants.ARRIVE_CENTRALIZEDPICK.equals(status)){
					//集中接货到达
					//开单未交接的货量
					//orgCode 到达部门
					//relevantOrgCode 出发部门
					List<PathDetailEntity> arriveBillingList = forecastQuantityJOBDao.queryBillingUnHandoverPick(orgCode, relevantOrgCode, forecastStartTime, forecastEndTime);
					billingList = this.waybillProcess(arriveBillingList, status, orgCode);

					//集中接货货物目前没有在途的
					// 查询到达在途货量
//					List<PathDetailEntity> arriveInTransitList = forecastQuantityJOBDao.queryOnthewayPick(orgCode, relevantOrgCode, forecastStartTime, forecastEndTime);
//					inTransitList = this.waybillProcess(arriveInTransitList);
					
				}
				
				// 根据到达部门统计条目信息 新增到主表
				super.forecast(ForecastConstants.FORECAST_ARRIVE, status, forecastStartTime, statistics, 
						orgCode, relevantOrgCode, billingList, inTransitList, inventoryList, salesDepartmentCodeList);
				
				
				if(map.containsKey(relevantOrgCode)){
					Map<String,Object>mapTemp=(Map<String, Object>) map.get(relevantOrgCode);
					((List<ForecastDto>) mapTemp.get("inventoryList")).addAll(inventoryList);
					((List<ForecastDto>) mapTemp.get("billingList")).addAll(billingList);
					((List<ForecastDto>) mapTemp.get("inTransitList")).addAll(inTransitList);
				}else{
					
					Map<String,Object>mapTemp=new HashMap<String, Object>(PlatformConstants.SONAR_NUMBER_3);
					//全部统计
					mapTemp.put("inventoryList", inventoryList);
					mapTemp.put("billingList", billingList);
					mapTemp.put("inTransitList", inTransitList);
					map.put(relevantOrgCode, mapTemp);
				}
		}
		
		

		/**
		 * 此方法主要功能 ： 
		 * 				查询走货路径主表，并且计算 当前外场到下一外场种的每一票货有 多重 和 体积 
		 * 
		 * @param list : 开单未交接 ， 在途 ， 库存余货 三种被状态的list
		 * @return 
		 */
		protected  List<ForecastDto> waybillProcess(List<PathDetailEntity> list){
			 List<ForecastDto> inventoryList=new ArrayList<ForecastDto>();
			
			// 在库部分查询重量体积等主表字段 合并主表信息和明细表信息到Dto
			for (PathDetailEntity pe : list) {
				
				//根据waybillNo查询一条走货路径信息
				TransportPathEntity transportPathEntity = super.transportationPathDao.queryTransportPath(pe.getWaybillNo());
				
				if (null == transportPathEntity) {
					logger.error("根据运单号查询走货路径总表信息没有得到数据! 运单号: "+ pe.getWaybillNo());
				} else {

					transportPathEntity.setWaybillNoCount(pe.getWaybillNoCount());
					
					// 复制 由于ORM缓存导致同一个运单查询出来的是同一个对象所以这边复制一个新的对象用来修改数据
					TransportPathEntity transportPathEntityTemp = new TransportPathEntity();
					BeanUtils.copyProperties(transportPathEntity,transportPathEntityTemp);
					
					// 重新计算重量体积
					transportPathEntityTemp = this.reCalculate(transportPathEntityTemp);

					ForecastDto forecastDto = new ForecastDto();
					// 设置
					forecastDto.setPathDetailEntity(pe);
					// 设置
					forecastDto.setTransportPathEntity(transportPathEntityTemp);
					// 新增
					inventoryList.add(forecastDto);
				}
			}
			return inventoryList;
		}

		/**
		 * 集中接货运单重量体积等相关信息计算
		 * @author 163580
		 * @date 2014-6-26 上午8:38:23
		 * @param arriveBillingList
		 * @param status
		 * @return
		 * @see
		 */
		protected  List<ForecastDto> waybillProcess(List<PathDetailEntity> arriveBillingList, String status, String orgCode){
			List<ForecastDto> inventoryList = new ArrayList<ForecastDto>();
			BigDecimal weightConfig = getDeptWeightConfig().get(orgCode);
			if(weightConfig == null) {
				weightConfig = BigDecimal.ONE;
			}
			BigDecimal volumeConfig = getDeptVolumeConfig().get(orgCode);
			if(volumeConfig == null) {
				volumeConfig = BigDecimal.ONE;
			}
			for (PathDetailEntity pathDetail : arriveBillingList) {
				
				//根据waybillNo查询一条走货路径信息
				TransportPathEntity transportPathEntity = super.transportationPathDao.queryTransportPath(pathDetail.getWaybillNo());
				
				if (null == transportPathEntity) {
					logger.error("根据运单号查询走货路径总表信息没有得到数据! 运单号: "+ pathDetail.getWaybillNo());
				} else {
					transportPathEntity.setWaybillNoCount(pathDetail.getWaybillNoCount());
					
					TransportPathEntity transportPathEntityTemp = new TransportPathEntity();
					BeanUtils.copyProperties(transportPathEntity,transportPathEntityTemp);
					//集中接货不存在分配之类的. 所以直接设置重量体积
					transportPathEntity.setTotalWeight(weightConfig);
					transportPathEntity.setTotalVolume(volumeConfig);
					ForecastDto forecastDto = new ForecastDto();
					forecastDto.setPathDetailEntity(pathDetail);
					forecastDto.setTransportPathEntity(transportPathEntityTemp);
					inventoryList.add(forecastDto);
				}
			}
			return inventoryList;
		}
		
		/**
		 * @author 140222
		 * 分批配载重新计算重量体积
		 * @param transportPathEntity
		 * @return
		 */
		protected TransportPathEntity reCalculate(TransportPathEntity transportPathEntity) {
			//获取运单信息
			WaybillEntity waybillEntity = super.waybillManagerService.queryWaybillBasicByNo(transportPathEntity.getWaybillNo());
			//重量
			BigDecimal weight=BigDecimal.ONE;
			//体积
			BigDecimal volume=BigDecimal.ONE;
			//本次走货走了此运单几件货
			BigDecimal goodsQtyTotal=BigDecimal.ZERO;
			if(waybillEntity!=null){
				goodsQtyTotal = BigDecimal.valueOf(waybillEntity.getGoodsQtyTotal());
				weight = waybillEntity.getGoodsWeightTotal();
				volume = waybillEntity.getGoodsVolumeTotal();
			}
			
			//是否分批配载
			// 如果分批
		if (null != transportPathEntity.getIfPartialStowage()
				&& StringUtils.equals(TransportPathConstants.PARTIALSTOWAGE,
						transportPathEntity.getIfPartialStowage())) {
			if (null != transportPathEntity.getWaybillNoCount()) {
					try{
					BigDecimal goodsQtyTotalTemp = BigDecimal.valueOf(Double.valueOf(transportPathEntity.getWaybillNoCount()));
					//比总件数还小时
						if(goodsQtyTotalTemp.compareTo(goodsQtyTotal)<=PlatformConstants.SONAR_NUMBER_0){
							goodsQtyTotal = goodsQtyTotalTemp;
						}
					}catch (Exception e) {
						goodsQtyTotal =BigDecimal.ZERO;
					}
				}
				
				//重量运单的总重量
				if(weight!= null){
					weight = weight.multiply(BigDecimal.valueOf(ton));//乘以1000 精准度
					weight = weight.divide(BigDecimal.valueOf(ton).multiply(BigDecimal.valueOf(ton)));//把KG换算成T
				}
				
				//
				if(goodsQtyTotal.compareTo(BigDecimal.ZERO)==0){
					weight = BigDecimal.ZERO;
					volume = BigDecimal.ZERO;
				}else{//重量和体积
					BigDecimal goodsQty=BigDecimal.ZERO;
					if(waybillEntity!=null&&waybillEntity.getGoodsQtyTotal()!=null){
						goodsQty = new BigDecimal(waybillEntity.getGoodsQtyTotal());
					}
					//平均重量和体积
					BigDecimal weightV =BigDecimal.ZERO;
					BigDecimal volumeV =BigDecimal.ZERO;
					//被除数不能为零
					//2016年12月23日10:35:57 wwb 311396 sonar优化
					if(goodsQty.compareTo(BigDecimal.ZERO)>0 && weight != null){
						weightV = weight.divide(goodsQty,PlatformConstants.SONAR_NUMBER_3, BigDecimal.ROUND_HALF_UP);
						volumeV = volume.divide(goodsQty,PlatformConstants.SONAR_NUMBER_3, BigDecimal.ROUND_HALF_UP);
					}
					//平均重量乘以件数
					weight = weightV.multiply(goodsQtyTotal);
					//平均体积乘以件数
					volume = volumeV.multiply(goodsQtyTotal);
				}
			}else{//不分批的时候一票货不管有多少件都算一所以这个地方要取总重量所以值应该为1(PS单位换算除外)
				//这个地方是除以1000是为了把KG换算成T
				weight = weight.multiply(BigDecimal.valueOf(ton));//乘以1000 精准度
				weight = weight.divide(BigDecimal.valueOf(ton).multiply(BigDecimal.valueOf(ton)),PlatformConstants.SONAR_NUMBER_3, BigDecimal.ROUND_HALF_UP);//把KG换算成T
			}
			
			transportPathEntity.setTotalWeight(weight);
			transportPathEntity.setTotalVolume(volume);
			return transportPathEntity;
		}
		
		/**
		 * @deprecated 计算重量 和 体积
		 * 分批配载重新计算重量体积
		 * @author huyue
		 * @date 2012-11-27 下午5:13:41
		 */
		protected TransportPathEntity reCalculateOld(TransportPathEntity transportPathEntity) {
			
			AverageCalculateEntity averageCalculateEntity = new AverageCalculateEntity();
			averageCalculateEntity.setOrgCode(transportPathEntity.getBillingOrgCode());
			
			//重量
			BigDecimal weight=BigDecimal.ONE;
			//体积
			BigDecimal volume=BigDecimal.ONE;
			//本次走货走了此运单几件货
			BigDecimal goodsQtyTotal=BigDecimal.ONE;
			//是否分批配载
			// 如果分批
			if (null != transportPathEntity.getIfPartialStowage() && StringUtils.equals(TransportPathConstants.PARTIALSTOWAGE, transportPathEntity.getIfPartialStowage())) {
				if(null != transportPathEntity.getWaybillNoCount()){
					try{
					goodsQtyTotal = BigDecimal.valueOf(Double.valueOf(transportPathEntity.getWaybillNoCount()));
					}catch (Exception e) {
						goodsQtyTotal =BigDecimal.ONE;
					}
				}
				//weight = (走货件数 /总件数)/1000  PS:这个地方是除以1000是为了把KG换算成T
				weight=(goodsQtyTotal.divide(BigDecimal.valueOf(transportPathEntity.getGoodsQtyTotal()),ten,RoundingMode.HALF_DOWN)).divide(BigDecimal.valueOf(ton));
				volume=goodsQtyTotal.divide(BigDecimal.valueOf(transportPathEntity.getGoodsQtyTotal()),ten,RoundingMode.HALF_DOWN);
			}else{//不分批的时候一票货不管有多少件都算一所以这个地方要取总重量所以值应该为1(PS单位换算除外)
				//这个地方是除以1000是为了把KG换算成T
				weight=goodsQtyTotal.divide(BigDecimal.valueOf(ton));
				//由于这个地方不需要转换所以就注释掉了
				//volume=goodsQtyTotal;
			}

			if (null != transportPathEntity.getTotalWeight() && transportPathEntity.getTotalWeight().compareTo(BigDecimal.ZERO)>PlatformConstants.SONAR_NUMBER_0 && null != transportPathEntity.getGoodsQtyTotal()) {
				//实际走货重量(T) = 总重量(KG) * ((走货件数 /总件数)/1000)(T)
				BigDecimal newWeight = transportPathEntity.getTotalWeight().multiply(weight);
				transportPathEntity.setTotalWeight(newWeight);
			} else {
				// 查询平均重量
				averageCalculateEntity = super.averageCalculateDaoPlatform.queryAverageCalculate(averageCalculateEntity);
				if (null == averageCalculateEntity) {
					transportPathEntity.setTotalWeight(BigDecimal.ZERO);
				} else {
					// 取平均每件重量
					//实际走货重量(T) = 总重量(KG) * ((走货件数 /总件数)/1000)(T)
					BigDecimal newWeight = averageCalculateEntity.getAverageWeight().multiply(weight);
					transportPathEntity.setTotalWeight(newWeight);
				}
			}
			// 计算体积 (体积不能为null 不能为0，总件数不能为null)
			if (null != transportPathEntity.getTotalVolume() && transportPathEntity.getTotalVolume().compareTo(BigDecimal.ZERO)>PlatformConstants.SONAR_NUMBER_0 && null != transportPathEntity.getGoodsQtyTotal()) {
				BigDecimal newVolume = transportPathEntity.getTotalVolume().multiply(volume);
				transportPathEntity.setTotalVolume(newVolume);
			} else {
				// 查询平均体积
				averageCalculateEntity = super.averageCalculateDaoPlatform.queryAverageCalculate(averageCalculateEntity);
				if (null == averageCalculateEntity) {
					transportPathEntity.setTotalVolume(BigDecimal.ZERO);
				} else {
					// 取平均每件体积
					BigDecimal newVolume = averageCalculateEntity.getAverageVolume().multiply(volume);
					transportPathEntity.setTotalVolume(newVolume);
				}
			}
			return transportPathEntity;
		}
	}