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
 *  
 *  PROJECT NAME  : tfr-scheduling
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/service/impl/AdviseWorkNumberService.java
 * 
 *  FILE NAME     :AdviseWorkNumberService.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.scheduling.server.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.base.baseinfo.api.server.service.IHeavyBubbleRatioService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.scheduling.api.define.ForecastConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IAdviseWorkNumberService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IForecastService;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IBillingDao;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IForecastQuantityDao;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IInTransitDao;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.ForecastQuantityEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.InTransitEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.AdviseWorkNumberDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.CountVehicleDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.TimeUtils;

/**
 * 预测货量service实现类
 * 
 * @author huyue
 * @date 2012-10-31 下午5:23:40
 */
public class AdviseWorkNumberService implements IAdviseWorkNumberService {
	//货量预测service
	private IForecastService forecastService;
	//货量预测dao
	private IForecastQuantityDao forecastQuantityDao;
	//货量预测开单量dao
	private IBillingDao billingDao;
	//货量预测在途量dao
	private IInTransitDao inTransitDao;
	//参数配置service
	private IConfigurationParamsService configurationParamsService;
	//车辆相关service
	private IVehicleService vehicleService;
	//重泡比 zwd
	private IHeavyBubbleRatioService heavyBubbleRatioService;
	
	//常量, 保留3位小数
	private static final int three = 3;
	//获取 货量预测service
	public IForecastService getForecastService() {
		return forecastService;
	}
	//设置 货量预测service
	public void setForecastService(IForecastService forecastService) {
		this.forecastService = forecastService;
	}
	//获取 货量预测dao
	public IForecastQuantityDao getForecastQuantityDao() {
		return forecastQuantityDao;
	}
	//设置 货量预测dao
	public void setForecastQuantityDao(IForecastQuantityDao forecastQuantityDao) {
		this.forecastQuantityDao = forecastQuantityDao;
	}
	//获取 货量预测开单量dao
	public IBillingDao getBillingDao() {
		return billingDao;
	}
	//设置 货量预测开单量dao
	public void setBillingDao(IBillingDao billingDao) {
		this.billingDao = billingDao;
	}
	//获取 货量预测在途量dao
	public IInTransitDao getInTransitDao() {
		return inTransitDao;
	}
	//设置 货量预测在途量dao
	public void setInTransitDao(IInTransitDao inTransitDao) {
		this.inTransitDao = inTransitDao;
	}
	//获取 参数配置service
	public IConfigurationParamsService getConfigurationParamsService() {
		return configurationParamsService;
	}
	//设置 参数配置service
	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	//获取 车辆相关service
	public IVehicleService getVehicleService() {
		return vehicleService;
	}
	/**
	 * 重泡比 zwd
	 */
	public void setHeavyBubbleRatioService(
			IHeavyBubbleRatioService heavyBubbleRatioService) {
		this.heavyBubbleRatioService = heavyBubbleRatioService;
	}
	//设置 车辆相关service
	public void setVehicleService(IVehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}

	/**
	 * 获取计算上班人数所需的货量预测信息,车辆统计信息
	 * 
	 * @author huyue
	 * @date 2012-12-11 下午5:02:33
	 */
	public AdviseWorkNumberDto getForecastData(String orgCode, String action) throws TfrBusinessException {
		//新建DTO
		AdviseWorkNumberDto adviseWorkNumberDto = new AdviseWorkNumberDto();
		ForecastQuantityEntity forecastQuantityEntity = new ForecastQuantityEntity();
		//设置类型
		forecastQuantityEntity.setType(action);
		//设置所属外场
		forecastQuantityEntity.setBelongOrgCode(orgCode);
		// 选取第一个时间,时间是从近到远排列的,所以可以取到当天的货量预测
		List<Date> timeList = forecastService.queryForecastTimeList(forecastQuantityEntity);
		//如果为空
		if (timeList.isEmpty()) {
			//抛异常
			throw new TfrBusinessException(ForecastConstants.FORECAST_ADVISEWORKNUMBER_CANTFIND, new Object[] { action });
		}
		//否则取第一个时间
		Date forecastTime = timeList.get(0);
		//设置货量预测时间
		forecastQuantityEntity.setForecastTime(forecastTime);
		// 查询出最新的一批货量预测
		List<ForecastQuantityEntity> forecastQuantityList = forecastQuantityDao.queryByStatistics(forecastQuantityEntity);
		//如果为空
		if (forecastQuantityList.isEmpty()) {
			//抛异常
			throw new TfrBusinessException(ForecastConstants.FORECAST_ADVISEWORKNUMBER_CANTFINDFORECASTDATA, "");
		}
		// 统计总货量,记录所有的主键ID便于之后查询从表
		//设总重量和体积为0
		BigDecimal weightTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
		BigDecimal volumeTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
		
		//xiaobc update start...........................
				BigDecimal expressWeightTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
				BigDecimal expressVolumeTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
		//xiaobc update start...........................
		
		List<String> iDList = new ArrayList<String>();
		//循环最新一批货量预测
		for (int i = 0; i < forecastQuantityList.size(); i++) {
			//累加重量
			weightTotal = weightTotal.add(forecastQuantityList.get(i).getWeightTotal());
			//累加体积
			volumeTotal = volumeTotal.add(forecastQuantityList.get(i).getVolumeTotal());
			//累加票数
			iDList.add(forecastQuantityList.get(i).getForecastQuantityId());
			//xiaobc update start......
			//快递重量累加
			expressWeightTotal = expressWeightTotal.add(forecastQuantityList.get(i).getExpressWeightTotal());
			//快递体积累加
			expressVolumeTotal = expressVolumeTotal.add(forecastQuantityList.get(i).getExpressVolumeTotal());
			//xiaobc update end......
		}
		// 记录总重量体积
		adviseWorkNumberDto.setTotalWeight(weightTotal);
		adviseWorkNumberDto.setTotalVolume(volumeTotal);
		// 记录预测执行的时间
		adviseWorkNumberDto.setStatisticsTime(forecastQuantityList.get(0).getStatisticsTime());
		// 获取货量预测开始&结束时间
		// 根据预测时间点 ,组织code 查询预测时间段
		// 获取开始时间点
		String start = "0300";
		//查询配值的组织业务参数
		ConfigurationParamsEntity entityStart = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR, ConfigurationParamsConstants.TFR_PARM__FORECAST_START, orgCode);
		if (entityStart != null && StringUtils.isNotEmpty(entityStart.getConfValue())) {
			//不为空则赋值
			start = entityStart.getConfValue();
		}
		// 获取持续天数
		int day = 1;
		//查询配值的组织业务参数
		ConfigurationParamsEntity entityDuration = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR, ConfigurationParamsConstants.TFR_PARM__FORECAST_DURATION, orgCode);
		if (entityDuration != null && StringUtils.isNotEmpty(entityDuration.getConfValue())) {
			//不为空则赋值
			day = Integer.valueOf(entityDuration.getConfValue());
		}
		//转换TIME
		Date startTime = TimeUtils.createStartTime(forecastTime, start);
		Date endTime = TimeUtils.convertStringToDate(forecastTime, start, day);
		//判断转换是否成功
		if (null == startTime || null == endTime) {
			//抛异常
			throw new TfrBusinessException(ForecastConstants.FORECAST_TRANSFORTIME_ERROR, "");
		}
		// 记录货量预测开始时间
		adviseWorkNumberDto.setForecastStartTime(startTime);
		// 记录货量预测结束时间
		adviseWorkNumberDto.setForecastEndTime(endTime);
		// 统计车辆类型和数量
		List<CountVehicleDto> countVehicleList = new ArrayList<CountVehicleDto>();
		// 循环ID LIST 查询在途表数据.得到全部在途车牌号
		List<VehicleAssociationDto> vehicleAssociationList = new ArrayList<VehicleAssociationDto>();
		Set<String> setVehicleType = new HashSet<String>();
		//循环货量预测ID list
		for (int i = 0; i < iDList.size(); i++) {
			InTransitEntity inTransitEntity = new InTransitEntity();
			//设置ID
			inTransitEntity.setForecastQuantityId(iDList.get(i));
			//根据ID查寻在途货量
			List<InTransitEntity> inTransitList = inTransitDao.queryinTransitList(inTransitEntity);
			// 循环list查询车辆信息
			for (int j = 0; j < inTransitList.size(); j++) {
				if (StringUtils.isNotEmpty(inTransitList.get(j).getIntransitVehicleNo())) {
					// 根据车牌号查基础资料车辆信息
					VehicleAssociationDto vehicleAssociationDto = vehicleService.queryVehicleAssociationDtoByVehicleNo(inTransitList.get(j).getIntransitVehicleNo());
					//如果不为空
					if (null != vehicleAssociationDto && StringUtils.isNotEmpty(vehicleAssociationDto.getVehicleLengthName())) {
						//获取车型名称
						setVehicleType.add(vehicleAssociationDto.getVehicleLengthName());
						vehicleAssociationList.add(vehicleAssociationDto);
					}
				}
			}
		}
		// 根据车辆entityList统计每种车辆数量
		Object vehicleType[] = setVehicleType.toArray();
		// 根据所有 在途车牌号循环
		for (int i = 0; i < vehicleType.length; i++) {
			CountVehicleDto countVehicleDto = new CountVehicleDto();
			int count = 0;
			//循环车型dto
			for (int j = 0; j < vehicleAssociationList.size(); j++) {
				//如果车型等于该车型则count累加
				if (StringUtils.equals(vehicleAssociationList.get(j).getVehicleLengthName(), vehicleType[i].toString())) {
					count++;
				}
			}
			//设置车辆type 
			countVehicleDto.setVehicleType(vehicleType[i].toString());
			//设置该类型的数量
			countVehicleDto.setCount(count);
			countVehicleList.add(countVehicleDto);
		}
		// 记录车牌号统计信息List
		adviseWorkNumberDto.setCountVehicleList(countVehicleList);
		
		//xiaobc update start......................
		// 调用综合接口，根据部门编码去查询重泡比，如果存在则使用查询到的重泡比，如果不存在则使用公用的重量体积转换参数。
		
				BigDecimal volumeRatio =  this.queryExpressVolumeConParameter(orgCode);//拿到快递体积比率
				BigDecimal volume = adviseWorkNumberDto.getTotalVolume();//拿到总体积
				BigDecimal newVolume = expressWeightTotal.multiply(volumeRatio);//快递总体积=快递总重量*快递体积比率
				volume = volume.subtract(expressVolumeTotal).add(newVolume);
				volume = volume.setScale(ConstantsNumberSonar.SONAR_NUMBER_3,BigDecimal.ROUND_HALF_UP);//保留三位小数	
				adviseWorkNumberDto.setTotalVolume(volume);
		//xiaobc update start......................
		return adviseWorkNumberDto;
	}
	
	/**
	 * 查询数据字典，查出快递货物体积的比率
	 * @author 200978-foss-xiaobingcheng
	 * 2014-8-9
	 * @return
	 */
	public BigDecimal queryExpressVolumeConParameter(String orgCode){
 /// zwd 调用综合接口，根据部门编码去查询重泡比，如果存在则使用查询到的重泡比，如果不存在则使用公用的重量体积转换参数。
		BigDecimal converParameter=BigDecimal.ZERO;
		String  stringValue = "";
		try{
			if(StringUtils.isNotEmpty(orgCode)){
				// zwd 调用综合接口，根据部门编码去查询重泡比，如果存在则使用查询到的重泡比，如果不存在则使用公用的重量体积转换参数。
				stringValue = heavyBubbleRatioService.queryHeavyBubbleParamByOutfield(orgCode);
			}

		}catch(Exception e){
			throw new TfrBusinessException("调综合接口根据外场编码来查询重泡比参数异常"+e.toString());
		}
		if(stringValue!=null && StringUtils.isNotEmpty(stringValue)){
			double doubleValue = Double.valueOf(stringValue.toString());
			converParameter = new BigDecimal(doubleValue);
			BigDecimal a =new BigDecimal("1.000");
			//重泡比为重量体积转换参数分之一
			 converParameter = a.divide(converParameter,ConstantsNumberSonar.SONAR_NUMBER_3);
			 return converParameter;
		}else{
			ConfigurationParamsEntity paramEntity = configurationParamsService.queryConfigurationParamsByOrgCode(
					DictionaryConstants.SYSTEM_CONFIG_PARM__TFR , 
					ConfigurationParamsConstants.TFR_PARM_EXPRESS_CONVERTVOLUME_PARAMETERS, 
					"DIP");
			if(paramEntity!=null){
				String value=paramEntity.getConfValue();
				try {
					double dvalue = Double.parseDouble(value);
					converParameter = new BigDecimal(dvalue);
				} catch (Exception e) {
					throw new TfrBusinessException("快递转换体积参数转换错误："+e.toString());
				}
				return converParameter;
				
			}else{
				throw new TfrBusinessException("请配置快递转换体积参数！");
			}
		}
		

	}
	
	
}