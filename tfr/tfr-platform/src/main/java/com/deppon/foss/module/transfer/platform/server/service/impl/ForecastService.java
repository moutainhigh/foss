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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/platorm/server/service/impl/ForecastService.java
 * 
 *  FILE NAME     :ForecastService.java

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

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.server.components.export.excel.ExcelExporter;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IGoodsAreaService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISiteGroupService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SiteGroupEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.transfer.common.api.server.service.ITfrCommonService;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.define.TaskTruckConstant;
import com.deppon.foss.module.transfer.platform.api.server.dao.IAverageCalculateDao;
import com.deppon.foss.module.transfer.platform.api.server.dao.IBillingDao;
import com.deppon.foss.module.transfer.platform.api.server.dao.IChangeQuantityDao;
import com.deppon.foss.module.transfer.platform.api.server.dao.IForecastQuantityDao;
import com.deppon.foss.module.transfer.platform.api.server.dao.IForecastQuantityJOBDao;
import com.deppon.foss.module.transfer.platform.api.server.dao.IInTransitDao;
import com.deppon.foss.module.transfer.platform.api.server.dao.IRealWeightAndVolumeDao;
import com.deppon.foss.module.transfer.platform.api.server.service.IForecastService;
import com.deppon.foss.module.transfer.platform.api.shared.define.ForecastConstants;
import com.deppon.foss.module.transfer.platform.api.shared.define.ForecastJOBConstants;
import com.deppon.foss.module.transfer.platform.api.shared.define.PlatformConstants;
import com.deppon.foss.module.transfer.platform.api.shared.domain.AverageCalculateEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.BillingEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.ChangeQuantityEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.ForecastQuantityEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.InTransitEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.StatisticalInquiriesEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.ForecastDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.RealWeightAndVolumeDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.SerialnoDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.StatisticalInquiriesDto;
import com.deppon.foss.module.transfer.platform.api.shared.vo.ForecastVO;
import com.deppon.foss.module.transfer.scheduling.api.define.TransportPathConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IPathDetailDao;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.ITransportationPathDao;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IStowagePlansService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PathDetailEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.TimeUtils;
import com.deppon.foss.module.transfer.stock.api.shared.exception.StockException;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * @ClassName: ForecastService 
 * @Description:  预测货量service实现类
 * @author yuyongxiang-134019-yuyongxiang@deppon.com
 * @date 2014年3月11日 下午4:46:45 
 *
 */
public class ForecastService implements IForecastService,ForecastJOBConstants {
	/**
	 * logger
	 */
	private final Logger logger = LoggerFactory.getLogger(ForecastService.class);
	/**
	 * 货量预测dao
	 */
	protected IForecastQuantityDao forecastQuantityDaoPlatform;
	/**
	 * 货量预测开单量dao
	 */
	protected IBillingDao billingDaoPlatform;
	/**
	 * 货量预测在途量dao
	 */
	protected IInTransitDao inTransitDaoPlatform;
	/**
	 * 货量预测改变dao
	 */
	protected IChangeQuantityDao changeQuantityDaoPlatform;
	/**
	 * 走货路径dao
	 */
	protected ITransportationPathDao transportationPathDao;
	/**
	 * 走货路径明细dao
	 */
	protected IPathDetailDao pathDetailDao;
	/**
	 * 实际重量和体积dao
	 */
	protected IRealWeightAndVolumeDao realWeightAndVolumeDaoPlatform;
	/**
	 * 平均重量体积dao
	 */
	protected IAverageCalculateDao averageCalculateDaoPlatform;
	/**
	 * 中转公共service
	 */
	protected ITfrCommonService tfrCommonService;
	/**
	 * 货区service
	 */
	protected IGoodsAreaService goodsAreaService;
	/**
	 * 营业部门service
	 */
	protected ISaleDepartmentService saleDepartmentService;
	/**
	 * 组织相关service
	 */
	protected IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 组织相关service
	 */
	protected IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	/**
	 * 线路service
	 */
	protected ILineService lineService;
	/**
	 * 配置参数service
	 */
	protected IConfigurationParamsService configurationParamsService;
	/**
	 * 网点组service
	 */
	protected ISiteGroupService siteGroupService;
	/**
	 * 配载方案配置
	 */
	protected IStowagePlansService stowagePlansService;
	
	/**
	 * 货量预测的job专用dao 所有与货量预测有关系，却又涉及到别的模块表的 单独接口可以加在次DAO里面
	 */
	protected IForecastQuantityJOBDao forecastQuantityJOBDao;
	
	/** 查询运单信息接口*/
	protected IWaybillManagerService waybillManagerService;
	
	/**
	 * 持续天数
	 */
	protected int day1 = PlatformConstants.SONAR_NUMBER_1;
	/**
	 * 常量
	 */
	protected int day2 = PlatformConstants.SONAR_NUMBER_30;
	/**
	 * 开始时间点
	 */
	protected String start1 = "0300";
	/**
	 * 常量
	 */
	protected String start2 = "0000";
	
	
	public void setRealWeightAndVolumeDaoPlatform(IRealWeightAndVolumeDao realWeightAndVolumeDaoPlatform) {
		this.realWeightAndVolumeDaoPlatform = realWeightAndVolumeDaoPlatform;
	}
	/**
	 * 设置
	 */
	public void setSaleDepartmentService(ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}
	/**
	 * 设置
	 */
	public void setOrgAdministrativeInfoComplexService(IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	/**
	 * 设置
	 */
	public void setLineService(ILineService lineService) {
		this.lineService = lineService;
	}
	/**
	 * 设置
	 */
	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	/**
	 * 设置
	 */
	public void setForecastQuantityDaoPlatform(IForecastQuantityDao forecastQuantityDaoPlatform) {
		this.forecastQuantityDaoPlatform = forecastQuantityDaoPlatform;
	}
	/**
	 * 设置
	 */
	public void setBillingDaoPlatform(IBillingDao billingDaoPlatform) {
		this.billingDaoPlatform = billingDaoPlatform;
	}
	/**
	 * 设置
	 */
	public void setInTransitDaoPlatform(IInTransitDao inTransitDaoPlatform) {
		this.inTransitDaoPlatform = inTransitDaoPlatform;
	}
	/**
	 * 获取走货路径明细DAO
	 */
	public IPathDetailDao getPathDetailDao() {
		return pathDetailDao;
	}
	/**
	 * 设置
	 */
	public void setPathDetailDao(IPathDetailDao pathDetailDao) {
		this.pathDetailDao = pathDetailDao;
	}
	/**
	 * 设置
	 */
	public void setChangeQuantityDaoPlatform(IChangeQuantityDao changeQuantityDaoPlatform) {
		this.changeQuantityDaoPlatform = changeQuantityDaoPlatform;
	}
	/**
	 * 获取
	 */
	public ITransportationPathDao getTransportationPathDao() {
		return transportationPathDao;
	}
	/**
	 * 设置
	 */
	public void setTransportationPathDao(ITransportationPathDao transportationPathDao) {
		this.transportationPathDao = transportationPathDao;
	}
	/**
	 * 获取
	 */
	public ITfrCommonService getTfrCommonService() {
		return tfrCommonService;
	}
	/**
	 * 设置
	 */
	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
		this.tfrCommonService = tfrCommonService;
	}
	/**
	 * 获取
	 */
	public IGoodsAreaService getGoodsAreaService() {
		return goodsAreaService;
	}
	/**
	 * 设置
	 */
	public void setGoodsAreaService(IGoodsAreaService goodsAreaService) {
		this.goodsAreaService = goodsAreaService;
	}
	/**
	 * 获取
	 */
	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		return orgAdministrativeInfoService;
	}
	/**
	 * 设置
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	/**
	 * 设置
	 */
	public void setAverageCalculateDaoPlatform(IAverageCalculateDao averageCalculateDaoPlatform) {
		this.averageCalculateDaoPlatform = averageCalculateDaoPlatform;
	}
	/**
	 * 设置
	 */
	public void setSiteGroupService(ISiteGroupService siteGroupService) {
		this.siteGroupService = siteGroupService;
	}

	/**
	 * @param stowagePlansService the stowagePlansService to set
	 */
	public void setStowagePlansService(IStowagePlansService stowagePlansService) {
		this.stowagePlansService = stowagePlansService;
	}
	public void setForecastQuantityJOBDao(
			IForecastQuantityJOBDao forecastQuantityJOBDao) {
		this.forecastQuantityJOBDao = forecastQuantityJOBDao;
	}
	
	
	/**
	 * 设置 查询运单信息接口.
	 *
	 * @param waybillManagerService the new 查询运单信息接口
	 */
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	/**
	 * 
	 * @Title: queryByPage 
	 * @Description: 分页查询 并且根据数据进行统计
	 * @author yuyongxiang-134019-yuyongxiang@deppon.com
	 * @date 2014年3月11日 下午3:31:11 
	 * @param @param forecastQuantityEntity
	 * @param @param page
	 * @param @param count
	 * @param @return
	 * @param @throws TfrBusinessException    设定文件 
	 * @throws
	 */
	public List<ForecastQuantityEntity> queryByPage(ForecastQuantityEntity forecastQuantityEntity, int page, int count) throws TfrBusinessException {
		logger.error("货量预测查询queryByPage：forecastQuantityEntity.出发部门"+forecastQuantityEntity.getBelongOrgCode()
				+"forecastQuantityEntity.到达部门"+forecastQuantityEntity.getRelevantOrgCode());
		
		if(forecastQuantityEntity!=null && forecastQuantityEntity.getDeparturearrival()!=null){
			//出发 - 到达中转\派送货量(其他类型包含到达 ==>出发和到达部门互换)
			if(!forecastQuantityEntity.getDeparturearrival().equals(ForecastConstants.DEPART_ARRIVALTRANSIT)
					&& !forecastQuantityEntity.getDeparturearrival().equals(ForecastConstants.DEPART_DELIVERYVOLUME)){
				//出发部门和到达部分互换
				forecastQuantityEntity.setRelevantOrgCode(forecastQuantityEntity.getBelongOrgCode());
				forecastQuantityEntity.setBelongOrgCode("");
			}
		}
		
		//logger.error("A货量预测查询queryByPage--String：forecastQuantityEntity"+forecastQuantityEntity.toString());
		// 根据组织号获取地区list
		List<ForecastQuantityEntity> regionList = forecastQuantityDaoPlatform.queryByRegionList(forecastQuantityEntity);
		//新建list
		List<ForecastQuantityEntity> forecastQuantityList = new ArrayList<ForecastQuantityEntity>();
		
		if(regionList!=null && regionList.size()>0){
		//循环
		for (int i = 0; i < regionList.size(); i++) {
			//计算行值
			int row = i;
				// 获取地区LIST时过滤掉了到达部门,在查询条件中加入
//				if (StringUtils.isNotEmpty(forecastQuantityEntity.getRelevantOrgCode())) {
//					//设置相关部门
//					regionList.get(row).setRelevantOrgCode(forecastQuantityEntity.getRelevantOrgCode());
//				}
				//regionList.get(row).setRelevantOrgCode(forecastQuantityEntity.getBelongOrgCode());
				//logger.error("B货量预测查询queryByPage--String：regionList.get(row)"+regionList.get(row).toString())//;
				//查询list
				List<ForecastQuantityEntity> list = forecastQuantityDaoPlatform.queryforecastQuantityList(regionList.get(row));
				
				logger.error("C货量预测查询queryByPage--String：regionList.get(row)"+regionList.get(row).toString());
				//查询未开单货量
				List<ForecastQuantityEntity> unbillings = forecastQuantityDaoPlatform.queryUnbillingQuantity(regionList.get(row));
				for(ForecastQuantityEntity f : unbillings) {
					f.setUnbillingWeightTot(f.getWeightTotal());
					f.setUnbillingVolumeTot(f.getVolumeTotal());
					f.setUnbillingWaybillQtyTot(f.getWaybillQtyTotal().intValue());
					f.setDataType(ForecastConstants.DATA_TYPE_ONE);
				}
				
				for(ForecastQuantityEntity fq : list) {
					fq.setWeightTot(fq.getWeightTotal());
					fq.setVolumeTot(fq.getVolumeTotal());
					fq.setWaybillQtyTot(fq.getWaybillQtyTotal().intValue());
				}
				
				if(list.size() < unbillings.size()) {
					int size = list.size() - unbillings.size();
					for(int j = 0; j < size; j++) {
						unbillings.add(ForecastQuantityEntity.newForecastQuantityEntity());
					}
				}
				//拼接数据(行转列)
				for(ForecastQuantityEntity fq : list) {
					String releventOrgCode = fq.getRelevantOrgCode();
					
					//出发 - 到达中转\派送货量(其他类型包含到达 ==>出发和到达部门互换)
					if(!forecastQuantityEntity.getDeparturearrival().equals(ForecastConstants.DEPART_ARRIVALTRANSIT)
							&& !forecastQuantityEntity.getDeparturearrival().equals(ForecastConstants.DEPART_DELIVERYVOLUME)){
						releventOrgCode = fq.getBelongOrgCode();
					}
					
					for(ForecastQuantityEntity unfq : unbillings) {
						String unbillingReleventOrgCode = unfq.getRelevantOrgCode();
						
						//出发 - 到达中转\派送货量(其他类型包含到达 ==>出发和到达部门互换)
						if(!forecastQuantityEntity.getDeparturearrival().equals(ForecastConstants.DEPART_ARRIVALTRANSIT)
								&& !forecastQuantityEntity.getDeparturearrival().equals(ForecastConstants.DEPART_DELIVERYVOLUME)){
							unbillingReleventOrgCode = unfq.getBelongOrgCode();
						}
						
						if(StringUtils.equals(releventOrgCode, unbillingReleventOrgCode)) {
							//拼接数据(行转列)
							//预测的未开单重量
							BigDecimal unbillingWeightTot = unfq.getUnbillingWeightTot();
							unbillingWeightTot = unbillingWeightTot == null ? BigDecimal.ZERO : unbillingWeightTot;
							
							//预测的未开单体积
							BigDecimal unbillingVolumeTot = unfq.getUnbillingVolumeTot();
							unbillingVolumeTot = unbillingVolumeTot == null ? BigDecimal.ZERO : unbillingVolumeTot;
							
							//预测的未开单票数
							Integer unbillingWaybillQtyTot = unfq.getUnbillingWaybillQtyTot();
							unbillingWaybillQtyTot = unbillingWaybillQtyTot == null ? 0 : unbillingWaybillQtyTot;
							
							//实际的重量
							BigDecimal weightTotal = fq.getWeightTotal();
							weightTotal = weightTotal == null ? BigDecimal.ZERO : weightTotal;
							
							//实际的体积
							BigDecimal volumeTotal = fq.getVolumeTotal();
							volumeTotal = volumeTotal == null ? BigDecimal.ZERO : volumeTotal;
							
							//实际的票数
							BigDecimal waybillQtyTotal = fq.getWaybillQtyTotal();
							waybillQtyTotal = waybillQtyTotal == null ? BigDecimal.ZERO : waybillQtyTotal;
							fq.setUnbillingWeightTot(unbillingWeightTot);
							fq.setUnbillingVolumeTot(unbillingVolumeTot);
							fq.setUnbillingWaybillQtyTot(unbillingWaybillQtyTot);
							fq.setWeightTot(weightTotal.add(unbillingWeightTot));
							fq.setVolumeTot(volumeTotal.add(unbillingVolumeTot));
							fq.setWaybillQtyTot(waybillQtyTotal.intValue() + unbillingWaybillQtyTot);
							
							break;
						}
					}
					if(forecastQuantityEntity!=null && forecastQuantityEntity.getDeparturearrival()!=null){
						//出发 - 到达中转\派送货量(其他类型包含到达 ==>出发和到达部门互换)
						if(!forecastQuantityEntity.getDeparturearrival().equals(ForecastConstants.DEPART_ARRIVALTRANSIT)
								&& !forecastQuantityEntity.getDeparturearrival().equals(ForecastConstants.DEPART_DELIVERYVOLUME)){
							//出发部门和到达部分互换
							String releventName = fq.getRelevantOrgCode();
							fq.setRelevantOrgCode(fq.getBelongOrgCode());
							fq.setRelevantOrgCodeName(fq.getBelongOrgCodeName());
							fq.setBelongOrgCode(releventOrgCode);
							fq.setBelongOrgCodeName(releventName);
						}
					}
				}
				//添加到list
				forecastQuantityList.addAll(list);
			}
		}
		//返回
		return forecastQuantityList;
	}
	
	/**
	 * 
	 * @Title: queryByPageCount 
	 * @Description: 根据地区分页获取全部地区count
	 * @author yuyongxiang-134019-yuyongxiang@deppon.com
	 * @date 2014年3月11日 下午3:31:40 
	 * @param @param forecastQuantityEntity
	 * @param @return    设定文件 
	 * @throws
	 */
	public int queryByPageCount(ForecastQuantityEntity forecastQuantityEntity){
		// 根据组织号获取地区list
		List<ForecastQuantityEntity> regionList = forecastQuantityDaoPlatform.queryByRegionList(forecastQuantityEntity);
		//返回
		return regionList.size();
	}
	

	/**
	 * 
	 * @Title: getCount 
	 * @Description: 分页getCount
	 * @author yuyongxiang-134019-yuyongxiang@deppon.com
	 * @date 2014年3月11日 下午3:31:54 
	 * @param @param forecastQuantityEntity
	 * @param @return    设定文件 
	 * @throws
	 */
	@Override
	public Long getCount(ForecastQuantityEntity forecastQuantityEntity) {
		//返回
		return forecastQuantityDaoPlatform.getCount(forecastQuantityEntity);
	}

	/**
	 * 
	 * @Title: queryForecastTimeList 
	 * @Description: 查询某外场最新一批货量预测的预测天数LIST
	 * @author yuyongxiang-134019-yuyongxiang@deppon.com
	 * @date 2014年3月11日 下午3:32:08 
	 * @param @param forecastQuantityEntity
	 * @param @return
	 * @param @throws TfrBusinessException    设定文件 
	 * @throws
	 */
	@Override
	public List<Date> queryForecastTimeList(ForecastQuantityEntity forecastQuantityEntity) throws TfrBusinessException {
		int day = 1;
		String start = "0300";
		//开始时间点
		ConfigurationParamsEntity entityStart = configurationParamsService
				.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR, 
				ConfigurationParamsConstants.TFR_PARM__FORECAST_START, forecastQuantityEntity.getBelongOrgCode());
		if (entityStart != null && StringUtils.isNotEmpty(entityStart.getConfValue())) {
			start = entityStart.getConfValue();
		}
		//持续天数
		ConfigurationParamsEntity entityDuration = configurationParamsService
				.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR,
						ConfigurationParamsConstants.TFR_PARM__FORECAST_DURATION, forecastQuantityEntity.getBelongOrgCode());
		if (entityDuration != null&& StringUtils.isNotEmpty(entityDuration.getConfValue())) {
			day = Integer.valueOf(entityDuration.getConfValue());
		}
		Date date = new Date();
		Date forecastTime = TimeUtils.createStartTime(date, start);
		List<Date> result = new ArrayList<Date>();
		for(int i = 0; i < day; i++) {
			result.add(DateUtils.addDayToDate(forecastTime, i));
		}
		return result;
		//返回
//		return forecastQuantityDaoPlatform.queryForecastTimeList(forecastQuantityEntity);
	}

	/**
	 * 
	 * @Title: selectMaxStatisticsTime 
	 * @Description: 查询某外场最新一批货量预测的预测时间
	 * @author yuyongxiang-134019-yuyongxiang@deppon.com
	 * @date 2014年3月11日 下午3:36:07
	 *
	 * @param forecastQuantityEntity
	 * @return
	 * @throws TfrBusinessException    设定文件 
	 *
	 * @see com.deppon.foss.module.transfer.platform.api.server.service.IForecastService#selectMaxStatisticsTime(com.deppon.foss.module.transfer.platform.api.shared.domain.ForecastQuantityEntity)
	 */
	@Override
	public Date selectMaxStatisticsTime(ForecastQuantityEntity forecastQuantityEntity) throws TfrBusinessException {
		//返回
		return forecastQuantityDaoPlatform.selectMaxStatisticsTime(forecastQuantityEntity);
	}

	/**
	 * 
	 * @Title: queryForecastQuantityList 
	 * @Description: 查询外场某线路总货量预测
	 * @author yuyongxiang-134019-yuyongxiang@deppon.com
	 * @date 2014年3月11日 下午3:36:38
	 *
	 * @param forecastQuantityEntity
	 * @return
	 * @throws TfrBusinessException    设定文件 
	 *
	 * @see com.deppon.foss.module.transfer.platform.api.server.service.IForecastService#queryForecastQuantityList(com.deppon.foss.module.transfer.platform.api.shared.domain.ForecastQuantityEntity)
	 */
	@Override
	public List<ForecastQuantityEntity> queryForecastQuantityList(ForecastQuantityEntity forecastQuantityEntity) throws TfrBusinessException {
		//根据周期和其他参数查询总表信息 返回list
		List<ForecastQuantityEntity> forecastQuantityList = forecastQuantityDaoPlatform.selectByPeriod(forecastQuantityEntity);
		//循环
		for (int q = 0; q < forecastQuantityList.size(); q++) {
			//设置所属部门
			forecastQuantityList.get(q).setBelongOrgCodeName(getNameByCode(forecastQuantityList.get(q).getBelongOrgCode()));
			//设置相关部门
			forecastQuantityList.get(q).setRelevantOrgCodeName(getNameByCode(forecastQuantityList.get(q).getRelevantOrgCode()));
			//如果地区不为空
			if (StringUtils.isNotEmpty(forecastQuantityList.get(q).getRegion()) && null != siteGroupService.querySiteGroupByCode(forecastQuantityList.get(q).getRegion())) {
				//设置地区名称
				forecastQuantityList.get(q).setRegionName(siteGroupService.querySiteGroupByCode(forecastQuantityList.get(q).getRegion()).getName());
			}
		}
		//返回
		return forecastQuantityList;
	}

	/**
	 * 
	 * @Title: queryTotalList 
	 * @Description: 查询外场所有线路总货量预测
	 * @author yuyongxiang-134019-yuyongxiang@deppon.com
	 * @date 2014年3月11日 下午3:36:52
	 *
	 * @param forecastQuantityEntity
	 * @return
	 * @throws TfrBusinessException    设定文件 
	 *
	 * @see com.deppon.foss.module.transfer.platform.api.server.service.IForecastService#queryTotalList(com.deppon.foss.module.transfer.platform.api.shared.domain.ForecastQuantityEntity)
	 */
	public List<ForecastQuantityEntity> queryTotalList(ForecastQuantityEntity forecastQuantityEntity) throws TfrBusinessException {
		//新建list
		List<ForecastQuantityEntity> totalList = new ArrayList<ForecastQuantityEntity>();
		//根据周期和其他参数查询总表信息 返回list
		List<ForecastQuantityEntity> forecastQuantityList = forecastQuantityDaoPlatform.selectByPeriod(forecastQuantityEntity);
		//循环
		if (forecastQuantityList.size() > 0) {
			//循环
			for (int q = 0; q < forecastQuantityList.size(); q++) {
				//设置所属部门
				forecastQuantityList.get(q).setBelongOrgCodeName(getNameByCode(forecastQuantityList.get(q).getBelongOrgCode()));
				//设置相关部门
				forecastQuantityList.get(q).setRelevantOrgCodeName(getNameByCode(forecastQuantityList.get(q).getRelevantOrgCode()));
				//如果不为空
				if (StringUtils.isNotEmpty(forecastQuantityList.get(q).getRegion()) && null != siteGroupService.querySiteGroupByCode(forecastQuantityList.get(q).getRegion())) {
					//设置地区
					forecastQuantityList.get(q).setRegionName(siteGroupService.querySiteGroupByCode(forecastQuantityList.get(q).getRegion()).getName());
				}
			}
			// 创建有序set
			Set<String> setStatisticsHHMM = new LinkedHashSet<String>();
			//循环
			for (int i = 0; i < forecastQuantityList.size(); i++) {
				// 把24小时都加进LIST中.便于后面分时间点统计
				setStatisticsHHMM.add(forecastQuantityList.get(i).getStatisticsHHMM());
			}
			// 复制模版
			//新建entity
			ForecastQuantityEntity modelEntity = new ForecastQuantityEntity();
			//复制
			BeanUtils.copyProperties(forecastQuantityList.get(0), modelEntity);
			
			modelEntity.setRelevantOrgCode(null);
			modelEntity.setWeightTotal(null);
			modelEntity.setVolumeTotal(null);
			modelEntity.setWaybillQtyTotal(null);
			modelEntity.setStatisticsHHMM(null);
			
			Object statisticsHHMM[] = setStatisticsHHMM.toArray();
			//循环
			for (int i = 0; i < statisticsHHMM.length; i++) {
				// 设置统计字段
				//设置重量为0
				BigDecimal weightTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
				//设置体积为0
				BigDecimal volumeTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
				//设置件数为0
				Integer waybillQtyTotal = 0;
				//循环
				for (int j = 0; j < forecastQuantityList.size(); j++) {
					// 如果相等则累计
					if (StringUtils.equals(statisticsHHMM[i].toString(), forecastQuantityList.get(j).getStatisticsHHMM())) {
						//重量累计
						weightTotal = weightTotal.add(forecastQuantityList.get(j).getWeightTotal());
						//体积累计
						volumeTotal = volumeTotal.add(forecastQuantityList.get(j).getVolumeTotal());
						//件数累计
						waybillQtyTotal += forecastQuantityList.get(j).getWaybillQtyTotal().intValue();
					}
				}
				//设置新entity
				ForecastQuantityEntity newEntity = new ForecastQuantityEntity();
				//复制
				BeanUtils.copyProperties(modelEntity, newEntity);
				//设置重量
				newEntity.setWeightTotal(weightTotal.setScale(three, BigDecimal.ROUND_HALF_DOWN));
				//设置体积
				newEntity.setVolumeTotal(volumeTotal.setScale(three, BigDecimal.ROUND_HALF_DOWN));
				//设置件数字
				newEntity.setWaybillQtyTotal(BigDecimal.valueOf(waybillQtyTotal));
				//设置时间
				newEntity.setStatisticsHHMM(statisticsHHMM[i].toString());
				//添加到list
				totalList.add(newEntity);
			}
		}
		//返回list
		return totalList;

	}

	/**
	 * 
	 * @Title: querySpecHourList 
	 * @Description: 根据特定时间点查询货量预测.如果为总量则进行统计
	 * @author yuyongxiang-134019-yuyongxiang@deppon.com
	 * @date 2014年3月11日 下午3:37:24
	 *
	 * @param forecastQuantityEntity
	 * @return
	 * @throws TfrBusinessException    设定文件 
	 *
	 * @see com.deppon.foss.module.transfer.platform.api.server.service.IForecastService#querySpecHourList(com.deppon.foss.module.transfer.platform.api.shared.domain.ForecastQuantityEntity)
	 */
	@Override
	public ForecastQuantityEntity querySpecHourList(ForecastQuantityEntity forecastQuantityEntity) throws TfrBusinessException {
		// 判断是否总量
		if (StringUtils.isNotEmpty(forecastQuantityEntity.getRelevantOrgCode())) {
			// 不是总量
			List<ForecastQuantityEntity> forecastQuantityList = queryForecastQuantityList(forecastQuantityEntity);
			//如果是一条
			if (forecastQuantityList.size() == 1) {
				//设置所属部门
				forecastQuantityList.get(0).setBelongOrgCodeName(getNameByCode(forecastQuantityList.get(0).getBelongOrgCode()));
				//设置相关部门
				forecastQuantityList.get(0).setRelevantOrgCodeName(getNameByCode(forecastQuantityList.get(0).getRelevantOrgCode()));
				//如果区域不为空
				if (StringUtils.isNotEmpty(forecastQuantityList.get(0).getRegion()) && null != siteGroupService.querySiteGroupByCode(forecastQuantityList.get(0).getRegion())) {
					//设置地区
					forecastQuantityList.get(0).setRegionName(siteGroupService.querySiteGroupByCode(forecastQuantityList.get(0).getRegion()).getName());
				}
				//返回
				return forecastQuantityList.get(0);
			//如果大于1条
			} else if (forecastQuantityList.size() > 1) {
				//报异常
				throw new TfrBusinessException(ForecastConstants.FORECAST_TIMEPOINT_MORETHANONE, "");
			//如果小于1条
			} else {
				//返回空
				return null;
			}
		} else {
			// 总量 需要合并
			//查询外场某线路总货量预测
			List<ForecastQuantityEntity> forecastQuantityList = queryForecastQuantityList(forecastQuantityEntity);
			//如不为空
			if (CollectionUtils.isNotEmpty(forecastQuantityList)) {
				//新建entity
				ForecastQuantityEntity newEntity = new ForecastQuantityEntity();
				//复制
				BeanUtils.copyProperties(forecastQuantityList.get(0), newEntity);
				//设置所属部门
				newEntity.setBelongOrgCodeName(getNameByCode(newEntity.getBelongOrgCode()));
				//如果地区不为空
				if (StringUtils.isNotEmpty(forecastQuantityList.get(0).getRegion()) && null != siteGroupService.querySiteGroupByCode(forecastQuantityList.get(0).getRegion())) {
					//设置地区名称
					newEntity.setRegionName(siteGroupService.querySiteGroupByCode(forecastQuantityList.get(0).getRegion()).getName());
				}
				
				newEntity.setRelevantOrgCode(null);
				newEntity.setWeightTotal(null);
				newEntity.setVolumeTotal(null);
				newEntity.setWaybillQtyTotal(null);
				
				//设置重量为0
				BigDecimal weightTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
				//设置体积为0
				BigDecimal volumeTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
				//设置件数为0
				Integer waybillQtyTotal = 0;
				//循环
				for (int i = 0; i < forecastQuantityList.size(); i++) {
					//重量累计
					weightTotal = weightTotal.add(forecastQuantityList.get(i).getWeightTotal());
					//体积累计
					volumeTotal = volumeTotal.add(forecastQuantityList.get(i).getVolumeTotal());
					//件数累计
					waybillQtyTotal += forecastQuantityList.get(i).getWaybillQtyTotal().intValue();
				}
				//设置总重量
				newEntity.setWeightTotal(weightTotal.setScale(three, BigDecimal.ROUND_HALF_DOWN));
				//设置总体积
				newEntity.setVolumeTotal(volumeTotal.setScale(three, BigDecimal.ROUND_HALF_DOWN));
				//设置总件数
				newEntity.setWaybillQtyTotal(BigDecimal.valueOf(waybillQtyTotal));
				//返回
				return newEntity;
			} else {
				//新建entity
				ForecastQuantityEntity newEntity = new ForecastQuantityEntity();
				//设置重量为0
				BigDecimal weightTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
				//设置体积为0
				BigDecimal volumeTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
				//设置重量
				newEntity.setWeightTotal(weightTotal);
				//设置体积
				newEntity.setVolumeTotal(volumeTotal);
				//设置预测日期
				newEntity.setStatisticsDate(forecastQuantityEntity.getStatisticsDate());
				//设置预测HHMM
				newEntity.setStatisticsHHMM(forecastQuantityEntity.getStatisticsHHMM());
				//返回
				return newEntity;
			}
		}
	}

	/**
	 * 
	 * @Title: countRealWeightAndVolume 
	 * @Description: 查询某外场,某线路/全部,出发或到达货物实际重量&体积
	 * @author yuyongxiang-134019-yuyongxiang@deppon.com
	 * @date 2014年3月11日 下午3:38:02
	 *
	 * @param forecastQuantityEntity
	 * @return
	 * @throws TfrBusinessException    设定文件 
	 *
	 * @see com.deppon.foss.module.transfer.platform.api.server.service.IForecastService#countRealWeightAndVolume(com.deppon.foss.module.transfer.platform.api.shared.domain.ForecastQuantityEntity)
	 */
	@Override
	public RealWeightAndVolumeDto countRealWeightAndVolume(ForecastQuantityEntity forecastQuantityEntity) throws TfrBusinessException {
		//新建map
		Map<String, Object> map = new HashMap<String, Object>();
		// 根据预测时间点 ,组织code 查询预测时间段
		// 获取开始时间点
		ConfigurationParamsEntity entityStart = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR, ConfigurationParamsConstants.TFR_PARM__FORECAST_START, forecastQuantityEntity.getBelongOrgCode());
		//如果不为空
		if (entityStart != null && StringUtils.isNotEmpty(entityStart.getConfValue())) {
			start1 = entityStart.getConfValue();
		}
		// 获取持续天数
		ConfigurationParamsEntity entityDuration = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR, ConfigurationParamsConstants.TFR_PARM__FORECAST_DURATION, forecastQuantityEntity.getBelongOrgCode());
		//如果不为空
		if (entityDuration != null && StringUtils.isNotEmpty(entityDuration.getConfValue())) {
			day1 = Integer.valueOf(entityDuration.getConfValue());
		}
		//计算开始时间
		Date startTime = TimeUtils.createStartTime(forecastQuantityEntity.getForecastTime(), start1);
		//计算结束时间
		Date endTime = TimeUtils.convertStringToDate(forecastQuantityEntity.getForecastTime(), start1, day1);
		//如果为空
		if (null == startTime || null == endTime) {
			//抛异常
			throw new TfrBusinessException(ForecastConstants.FORECAST_TRANSFORTIME_ERROR, "");
		}
		// 首先判断出发或到达
		//如果出发
		if (StringUtils.equals(forecastQuantityEntity.getType(), ForecastConstants.FORECAST_DEPART)) {// 出发
			//查询出发实际货量
			map.put("status", LoadConstants.LOAD_TASK_STATE_CANCELED);
			map.put("billType", TaskTruckConstant.BILL_TYPE_HANDOVER);
			map.put(origOrg, forecastQuantityEntity.getBelongOrgCode());
			map.put("destOrgCode", forecastQuantityEntity.getRelevantOrgCode());
			map.put("StartTime", startTime);
			map.put("EndTime", endTime);
			//返回
			return realWeightAndVolumeDaoPlatform.queryRealDepart(map);
			//如果到达
		} else if (StringUtils.equals(forecastQuantityEntity.getType(), ForecastConstants.FORECAST_ARRIVE)) {// 到达
			//查询到达实际货量
			map.put("status", LoadConstants.LOAD_TASK_STATE_CANCELED);
			map.put("billType", TaskTruckConstant.BILL_TYPE_HANDOVER);
			map.put(origOrg, forecastQuantityEntity.getRelevantOrgCode());
			map.put("destOrgCode", forecastQuantityEntity.getBelongOrgCode());
			map.put("StartTime", startTime);
			map.put("EndTime", endTime);
			//返回
			return realWeightAndVolumeDaoPlatform.queryRealArrive(map);
		} else {
			//抛异常
			throw new TfrBusinessException(ForecastConstants.FORECAST_TYPE_ERROR, "");
		}
	}

	/**
	 * 
	 * @Title: calculateAverageWeightAndVolume 
	 * @Description: 
	 * 计算营业部平均重量体积 JOB
	 * 
	 * 
	 * 货量预测需要根据本中转场的货物信息进行,
	 * 
	 * 
	 * 包括货物重量及体积.
	 * 
	 * 但是在开单时会有一些货物并没有录入重量及体积,
	 * 
	 * 为了计算方便,
	 * 
	 * 则需要根据该外场辐射营业部开单的全部重量&体积计算得出平均值,
	 * 
	 * 赋予没有录入重量及体积的货物便于计算.
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 需要进行货量预测
	 * 
	 * 部分货开单时没有录入重量&体积,
	 * 
	 * 无法满足货量预测要求.
	 * 
	 * 获得该中转场的平均货物重量&体积,
	 * 
	 * 将该平均重量&体积用于填补开单未录入重量&体积信息的货物.
	 * 
	 * 
	 *
	 * 
	 * g --总重量, 
	 * 
	 * v --总体积,
	 * 
	 * p --总票数, 
	 * 
	 * j --总件数 
	 * 
	 * gav(平均重量) = g/p
	 * 
	 * vav(平均体积) = v/p
	 * 
	 * 每票件数 = j/p
	 * 
	 * 每月计算一次，间隔计算日期可以进行设置。
	 * 
	 * 根据各营业部门,分别统计,分别计算.
	 * @author yuyongxiang-134019-yuyongxiang@deppon.com
	 * @date 2014年3月11日 下午3:38:49
	 *
	 * @param time
	 * @throws TfrBusinessException    设定文件 
	 *
	 * @see com.deppon.foss.module.transfer.platform.api.server.service.IForecastService#calculateAverageWeightAndVolume(java.util.Date)
	 */
	public void calculateAverageWeightAndVolume(Date time) throws TfrBusinessException {
		// 查询所有可以作为出发的营业部LIST
		//新建entity
		SaleDepartmentEntity saleDepartmentEntity = new SaleDepartmentEntity();
		//设置为yse
		saleDepartmentEntity.setLeave(FossConstants.YES);
		//查询所有营业部门
		List<SaleDepartmentEntity> saleDepartmentList = saleDepartmentService.querySaleDepartmentExactByEntity(saleDepartmentEntity, 0, Integer.MAX_VALUE);
		//如果为空
		if (CollectionUtils.isEmpty(saleDepartmentList)) {
			//记日志
			logger.error("调用综合接口查询所有可以作为出发的营业部LIST为空!");
			//返回
			return;
		}
		// 循环获取时间范围内所有开单重量体积票数件数
		//循环营业部门
		for (int i = 0; i < saleDepartmentList.size(); i++) {
			//新建map
			Map<String, Object> map = new HashMap<String, Object>();
			// 根据预测时间点 ,组织code 查询预测时间段
			// 获取开始时间点
			ConfigurationParamsEntity entityStart = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR, ConfigurationParamsConstants.TFR_PARM__AVERAGE_CALCULATE_START, saleDepartmentList.get(i).getCode());
			//不为空
			if (entityStart != null && StringUtils.isNotEmpty(entityStart.getConfValue())) {
				/** 
				 * 赋值 
				 */
				start2 = entityStart.getConfValue();
			}
			// 获取持续天数
			ConfigurationParamsEntity entityDuration = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__TFR, ConfigurationParamsConstants.TFR_PARM__AVERAGE_CALCULATE_DURATION, saleDepartmentList.get(i).getCode());
			//不为空
			if (entityDuration != null && StringUtils.isNotEmpty(entityDuration.getConfValue())) {
				/** 
				 * 赋值 
				 */
				day2 = Integer.valueOf(entityDuration.getConfValue());
			}
			// 修改时间格式
			Date calculateTime = DateUtils.convert(DateUtils.convert(time, DateUtils.DATE_FORMAT), DateUtils.DATE_FORMAT);
			// 开始日期是N天前
			Date startTime = TimeUtils.convertStringToDate(calculateTime, start2, (-day2));
			// 结束日期是今天
			Date endTime = TimeUtils.createStartTime(calculateTime, start2);
			//如为空
			if (null == startTime || null == endTime) {
				//记录异常
				logger.error("转换时间出错!方法: calculateAverageWeightAndVolume");
				//返回
				return;
			}
			//设置信息
			map.put(origOrg, saleDepartmentList.get(i).getCode());
			// 90状态为作废 BY 交接单配载单
			map.put("state", LoadConstants.HANDOVERBILL_STATE_ALREADY_CANCEL);
			map.put("StartTime", startTime);
			map.put("EndTime", endTime);
			//查询实际货量
			RealWeightAndVolumeDto realWeightAndVolumeDto = realWeightAndVolumeDaoPlatform.queryAverage(map);
			//如果为空
			if (null == realWeightAndVolumeDto) {
				//记日志
				logger.error("查询实际重量体积票数件数为空!方法: calculateAverageWeightAndVolume 调用类: " + realWeightAndVolumeDaoPlatform + " 调用方法: queryAverage" + " 参数:" + map);
				//返回
				return;
			} else {
				//计算平均重量
				BigDecimal averageWeight = realWeightAndVolumeDto.getTotalWeight().divide(BigDecimal.valueOf((double) realWeightAndVolumeDto.getWaybillQtyTotal()),PlatformConstants.SONAR_NUMBER_3, BigDecimal.ROUND_HALF_UP);
				//计算平均体积
				BigDecimal averageVolume = realWeightAndVolumeDto.getTotalVolume().divide(BigDecimal.valueOf((double) realWeightAndVolumeDto.getWaybillQtyTotal()),PlatformConstants.SONAR_NUMBER_3, BigDecimal.ROUND_HALF_UP);
				//计算平均件数
				int averageGoodsPerWaybill = Math.round(((float) realWeightAndVolumeDto.getGoodsQtyTotal()) / ((float) realWeightAndVolumeDto.getWaybillQtyTotal()));
				//新建entity
				AverageCalculateEntity averageCalculateEntity = new AverageCalculateEntity();
				//设置部门
				averageCalculateEntity.setOrgCode(saleDepartmentList.get(i).getCode());
				//查询平均重量体积
				averageCalculateEntity = averageCalculateDaoPlatform.queryAverageCalculate(averageCalculateEntity);
				// 计算并判断是否存在数据,存在则更新,否则新增
				if (null == averageCalculateEntity) {
					averageCalculateEntity = new AverageCalculateEntity();
					// 如果为空则新增
					//UUID
					averageCalculateEntity.setAverageCalculateId(UUIDUtils.getUUID());
					//重量
					averageCalculateEntity.setTotalWeight(realWeightAndVolumeDto.getTotalWeight());
					//体积
					averageCalculateEntity.setTotalVolume(realWeightAndVolumeDto.getTotalVolume());
					//票数
					averageCalculateEntity.setTotalWaybillQty(realWeightAndVolumeDto.getWaybillQtyTotal());
					//件数
					averageCalculateEntity.setTotalGoodsQty(realWeightAndVolumeDto.getGoodsQtyTotal());
					//部门
					averageCalculateEntity.setOrgCode(saleDepartmentList.get(i).getCode());
					//平均重量
					averageCalculateEntity.setAverageWeight(averageWeight.setScale(three, BigDecimal.ROUND_HALF_DOWN));
					//平均体积
					averageCalculateEntity.setAverageVolume(averageVolume.setScale(three, BigDecimal.ROUND_HALF_DOWN));
					//平均件数字
					averageCalculateEntity.setAverageGoodsQty(averageGoodsPerWaybill);
					//修改时间
					averageCalculateEntity.setChangeTime(time);
					//新增
					averageCalculateDaoPlatform.addAverageCalculate(averageCalculateEntity);
				} else {
					// 如果不为空则更新
					//重量
					averageCalculateEntity.setTotalWeight(realWeightAndVolumeDto.getTotalWeight());
					//体积
					averageCalculateEntity.setTotalVolume(realWeightAndVolumeDto.getTotalVolume());
					//票数
					averageCalculateEntity.setTotalWaybillQty(realWeightAndVolumeDto.getWaybillQtyTotal());
					//件数
					averageCalculateEntity.setTotalGoodsQty(realWeightAndVolumeDto.getGoodsQtyTotal());
					//平均重量
					averageCalculateEntity.setAverageWeight(averageWeight.setScale(three, BigDecimal.ROUND_HALF_DOWN));
					//平均体积
					averageCalculateEntity.setAverageVolume(averageVolume.setScale(three, BigDecimal.ROUND_HALF_DOWN));
					//平均件数
					averageCalculateEntity.setAverageGoodsQty(averageGoodsPerWaybill);
					//修改时间
					averageCalculateEntity.setChangeTime(time);
					//更新
					averageCalculateDaoPlatform.updateAverageCalculateSelective(averageCalculateEntity);
				}
			}
		}
	}

	/**
	 * 
	 * @Title: queryBillingList 
	 * @Description: 查询外场某线路开单货量预测
	 * @author yuyongxiang-134019-yuyongxiang@deppon.com
	 * @date 2014年3月11日 下午3:39:36
	 *
	 * @param billingEntity
	 * @return
	 * @throws TfrBusinessException    设定文件 
	 *
	 * @see com.deppon.foss.module.transfer.platform.api.server.service.IForecastService#queryBillingList(com.deppon.foss.module.transfer.platform.api.shared.domain.BillingEntity)
	 */
	@Override
	public List<BillingEntity> queryBillingList(BillingEntity billingEntity) throws TfrBusinessException {
		//查询货量预测开单表信息 批量 group by 营业区
		List<BillingEntity> billingList = billingDaoPlatform.querybillingListGroupBy(billingEntity);
		// modify by liangfuxiang 2013-3-22上午10:28:06 begin 修改异常
		// 新建list
		// List<BillingEntity> newBillingList = new ArrayList<BillingEntity>();
		// 循环
		// for (int q = 0; q < newBillingList.size(); q++) {
		for (int q = 0; q < billingList.size(); q++) {
			// modify by liangfuxiang 2013-3-22上午10:28:14 end;
			//如果不为空
			if (StringUtils.isNotEmpty(billingList.get(q).getBillingSalesDistrict())) {
				//获取营业部名称
				String name = getNameByCode(billingList.get(q).getBillingSalesDistrict());
				//设置
				billingList.get(q).setBillingSalesDistrictName(name);
			}
		}
		//返回
		return billingList;
	}

	/**
	 * 
	 * @Title: queryInTransitList 
	 * @Description: 查询外场某线路在途中货量预测
	 * @author yuyongxiang-134019-yuyongxiang@deppon.com
	 * @date 2014年3月11日 下午3:39:56
	 *
	 * @param inTransitEntity
	 * @return
	 * @throws TfrBusinessException    设定文件 
	 *
	 * @see com.deppon.foss.module.transfer.platform.api.server.service.IForecastService#queryInTransitList(com.deppon.foss.module.transfer.platform.api.shared.domain.InTransitEntity)
	 */
	@Override
	public List<InTransitEntity> queryInTransitList(InTransitEntity inTransitEntity) throws TfrBusinessException {
		//查询货量预测在途表信息 批量
		List<InTransitEntity> inTransitList = inTransitDaoPlatform.queryinTransitList(inTransitEntity);
		//循环
		for (int q = 0; q < inTransitList.size(); q++) {
			//设置所属名称
			inTransitList.get(q).setBelongOrgCodeName(getNameByCode(inTransitList.get(q).getBelongOrgCode()));
			//设置相关名称
			inTransitList.get(q).setRelevantOrgCodeName(getNameByCode(inTransitList.get(q).getRelevantOrgCode()));
		}
		//返回
		return inTransitList;
	}

	/**
	 * 
	 * @Title: changeQuantity 
	 * @Description: 更新货量预测合车调整
	 * @author yuyongxiang-134019-yuyongxiang@deppon.com
	 * @date 2014年3月11日 下午3:40:15
	 *
	 * @param forecastQuantityList
	 * @param changeQuantityEntity
	 * @throws TfrBusinessException    设定文件 
	 *
	 * @see com.deppon.foss.module.transfer.platform.api.server.service.IForecastService#changeQuantity(java.util.List, com.deppon.foss.module.transfer.platform.api.shared.domain.ChangeQuantityEntity)
	 */
	@Override
	public void changeQuantity(List<ForecastQuantityEntity> forecastQuantityList, ChangeQuantityEntity changeQuantityEntity) throws TfrBusinessException {
		// 更新调整后的两条记录
		//循环
		for (int i = 0; i < forecastQuantityList.size(); i++) {
			//更新
			forecastQuantityDaoPlatform.updateforecastQuantitySelective(forecastQuantityList.get(i));
		}
		//new date
		Date changeTime = new Date();
		//如果不为空
		if (null != forecastQuantityList.get(0)) {
			//获取预测时间
			changeTime = forecastQuantityList.get(0).getForecastTime();
		}
		// 更新时间
		changeQuantityEntity.setChangeTime(changeTime);
		// 更新类型
		changeQuantityEntity.setType(ForecastConstants.FORECAST_DEPART);
		// 更新状态
		changeQuantityEntity.setStatus(ForecastConstants.CHANGE_QUANTITY_EFFECT);
		// 查询改变表是否存在该条目,不存在则新增,否则更新
		ChangeQuantityEntity oldEntity = changeQuantityDaoPlatform.querychangeQuantity(changeQuantityEntity);
		if (null != oldEntity) {
			// 存在则更新
			//设置调整重量
			oldEntity.setModifyWeight(oldEntity.getModifyWeight().add(changeQuantityEntity.getModifyWeight()));
			//更新
			changeQuantityDaoPlatform.updatechangeQuantitySelective(oldEntity);
		} else {
			//不存在
			//新增 
			//设置UUID
			changeQuantityEntity.setChangeQtyId(UUIDUtils.getUUID());
			//更新
			changeQuantityDaoPlatform.addchangeQuantity(changeQuantityEntity);
		}
	}

	/**
	 * 
	 * @Title: queryChangeInByDate 
	 * @Description: 查询合入本线路的货量
	 * @author yuyongxiang-134019-yuyongxiang@deppon.com
	 * @date 2014年3月11日 下午3:42:05
	 *
	 * @param modifyDate
	 * @param orgCode
	 * @param destorgCode
	 * @return
	 * @throws TfrBusinessException    设定文件 
	 *
	 * @see com.deppon.foss.module.transfer.platform.api.server.service.IForecastService#queryChangeInByDate(java.util.Date, java.lang.String, java.lang.String)
	 */
	public List<ChangeQuantityEntity> queryChangeInByDate(Date modifyDate, String orgCode, String destorgCode) throws TfrBusinessException {
		//新建entity
		ChangeQuantityEntity changeQuantityEntity = new ChangeQuantityEntity();
		//设置调整时间
		changeQuantityEntity.setChangeTime(modifyDate);
		//设置新部门
		changeQuantityEntity.setNewDestOrg(destorgCode);
		//设置所属部门
		changeQuantityEntity.setBelongTransferCenter(orgCode);
		//查询调整货量表信息 批量
		List<ChangeQuantityEntity> changeQuantityList = changeQuantityDaoPlatform.querychangeQuantityList(changeQuantityEntity);
		//循环
		for (int q = 0; q < changeQuantityList.size(); q++) {
			//设置所属部门名称
			changeQuantityList.get(q).setBelongTransferCenterName(getNameByCode(changeQuantityList.get(q).getBelongTransferCenter()));
			//设置新到达部门
			changeQuantityList.get(q).setNewDestOrgName(getNameByCode(changeQuantityList.get(q).getNewDestOrg()));
			//设置以前到达部门
			changeQuantityList.get(q).setOrigDestOrgName(getNameByCode(changeQuantityList.get(q).getOrigDestOrg()));
			if(StringUtils.equals(ForecastConstants.FORECAST_DEPART, changeQuantityList.get(q).getType())){
				changeQuantityList.get(q).setType("出发");
			}
		}
		//返回
		return changeQuantityList;
	}

	/**
	 * 
	 * @Title: queryChangeOutByDate 
	 * @Description: 查询合出本线路的货量
	 * @author yuyongxiang-134019-yuyongxiang@deppon.com
	 * @date 2014年3月11日 下午3:42:45
	 *
	 * @param modifyDate
	 * @param orgCode
	 * @param destorgCode
	 * @return
	 * @throws TfrBusinessException    设定文件 
	 *
	 * @see com.deppon.foss.module.transfer.platform.api.server.service.IForecastService#queryChangeOutByDate(java.util.Date, java.lang.String, java.lang.String)
	 */
	public List<ChangeQuantityEntity> queryChangeOutByDate(Date modifyDate, String orgCode, String destorgCode) throws TfrBusinessException {
		//新建entity
		ChangeQuantityEntity changeQuantityEntity = new ChangeQuantityEntity();
		//设置调整时间
		changeQuantityEntity.setChangeTime(modifyDate);
		//设置新部门
		changeQuantityEntity.setOrigDestOrg(destorgCode);
		//设置所属部门
		changeQuantityEntity.setBelongTransferCenter(orgCode);
		//查询调整货量表信息 批量
		List<ChangeQuantityEntity> changeQuantityList = changeQuantityDaoPlatform.querychangeQuantityList(changeQuantityEntity);
		//循环
		for (int q = 0; q < changeQuantityList.size(); q++) {
			//设置所属部门名称
			changeQuantityList.get(q).setBelongTransferCenterName(getNameByCode(changeQuantityList.get(q).getBelongTransferCenter()));
			//设置新到达部门
			changeQuantityList.get(q).setNewDestOrgName(getNameByCode(changeQuantityList.get(q).getNewDestOrg()));
			//设置以前到达部门
			changeQuantityList.get(q).setOrigDestOrgName(getNameByCode(changeQuantityList.get(q).getOrigDestOrg()));
			if(StringUtils.equals(ForecastConstants.FORECAST_DEPART, changeQuantityList.get(q).getType())){
				changeQuantityList.get(q).setType("出发");
			}
		}
		//返回
		return changeQuantityList;
	}

	/**
	 * 
	 * @Title: forecast 
	 * @Description: 根据外场部门统计条目信息 新增到主表
	 * @author yuyongxiang-134019-yuyongxiang@deppon.com
	 * @date 2014年3月11日 下午3:44:46 
	 * @param action
	 * @param status
	 * @param forecastTime
	 * @param statistics
	 * @param orgCode
	 * @param billingList
	 * @param inTransitList
	 * @param inventoryList
	 * @param relevantOrgCode
	 * @param salesDepartmentCodeList (orgCode所辐射的营业部)
	 * @throws TfrBusinessException    设定文件 
	 * @return void    返回类型
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	protected void forecast(String action,String status, Date forecastTime, Date statistics, String orgCode, 
			String relevantOrgCode, List<ForecastDto> billingList, List<ForecastDto> inTransitList,
			 List<ForecastDto> inventoryList, List<String> salesDepartmentCodeList) throws TfrBusinessException {
		// 拆分预测时间
		Date statisticsDate = DateUtils.convert(DateUtils.convert(statistics, DateUtils.DATE_FORMAT), DateUtils.DATE_FORMAT);
		Date tmpDate = DateUtils.convert(DateUtils.convert(statistics, "yyyy-MM-dd HH"), "yyyy-MM-dd HH");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HHmm");
		String statisticsHHMM = sdf2.format(tmpDate);
		// 设置统计字段
		//总 = 开单 + 在途 + 在库
		//总重量
		BigDecimal weightTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
		//总体积
		BigDecimal volumeTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
		//总单数
		Integer waybillQtyTotal = 0;
		
		//卡航 = 开单卡航 + 在途卡航 + 在库卡航
		BigDecimal gpsEnabledResWeightTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
		BigDecimal gpsEnabledResVolumeTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
		Integer gpsEnabledResQtyTotal = 0;
		
		//城运 = 开单城运 + 在途城运 + 在库城运
		BigDecimal precisionIfsWeightTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
		BigDecimal precisionIfsVolumeTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
		Integer precisionIfsQtyTotal = 0;
		
		//快递 = 开单快递 + 在途快递 + 在库快递
		BigDecimal expressWeightTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
		BigDecimal expressVolumeTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
		Integer expressQtyTotal = 0;
		
		//在库
		BigDecimal inventoryWeightTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
		BigDecimal inventoryVolumeTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
		Integer inventoryQtyTotal = 0;
		
		//开单
		BigDecimal billingWeightTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
		BigDecimal billingVolumeTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
		Integer billingQtyTotal = 0;
		
		//在途
		BigDecimal intransitWeightTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
		BigDecimal intransitVolumeTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
		Integer intransitQtyTotal = 0;
		
		Set<String> setTotalWaybill = new HashSet<String>();
		Set<String> setInTransitWaybill = new HashSet<String>();
		Set<String> setBillingWaybill = new HashSet<String>();
		Set<String> setInventoryWaybill = new HashSet<String>();
		Set<String> setGpsEnabledResWaybill = new HashSet<String>();
		Set<String> setPrecisionIfsWaybill = new HashSet<String>();
		Set<String> setExpressWaybill = new HashSet<String>();
		List<ForecastDto> billingBeLongList = new ArrayList<ForecastDto>();
		Set<String> setSalesDepartmentCode = new HashSet<String>();
		Set<String> setVehicleCode = new HashSet<String>();
//		// 根据组织查询所有的辐射营业部
//		List<String> salesDepartmentCodeList = getSalesDeptListByTransferCode(orgCode);
		if (CollectionUtils.isEmpty(salesDepartmentCodeList)) {
			logger.error("调用综合接口根据组织查询所有辐射营业部为空! 方法: getSalesDeptListByTransferCode  orgCode : " + orgCode);
			salesDepartmentCodeList = new ArrayList<String>();
		}
		// 循环开单货件LIST
		for (int j = 0; j < billingList.size(); j++) {
			// 总量增加
			weightTotal = weightTotal.add(billingList.get(j).getTransportPathEntity().getTotalWeight());
			volumeTotal = volumeTotal.add(billingList.get(j).getTransportPathEntity().getTotalVolume());
			setTotalWaybill.add(billingList.get(j).getTransportPathEntity().getWaybillNo());
			// 开单量增加
			billingWeightTotal = billingWeightTotal.add(billingList.get(j).getTransportPathEntity().getTotalWeight());
			billingVolumeTotal = billingVolumeTotal.add(billingList.get(j).getTransportPathEntity().getTotalVolume());
			setBillingWaybill.add(billingList.get(j).getTransportPathEntity().getWaybillNo());
			// 判断是否精准卡航
			if (null != billingList.get(j).getTransportPathEntity().getTransportModel() && StringUtil.equals(billingList.get(j).getTransportPathEntity().getTransportModel(),
			PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT)) {
				// 卡航量增加
				gpsEnabledResWeightTotal = gpsEnabledResWeightTotal.add(billingList.get(j).getTransportPathEntity().getTotalWeight());
				gpsEnabledResVolumeTotal = gpsEnabledResVolumeTotal.add(billingList.get(j).getTransportPathEntity().getTotalVolume());
				setGpsEnabledResWaybill.add(billingList.get(j).getTransportPathEntity().getWaybillNo());
			}
			// 判断是否精准城运
			if (null != billingList.get(j).getTransportPathEntity().getTransportModel() && StringUtil.equals(billingList.get(j).getTransportPathEntity().getTransportModel(),
			PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT)) {
				// 城运量增加
				precisionIfsWeightTotal = precisionIfsWeightTotal.add(billingList.get(j).getTransportPathEntity().getTotalWeight());
				precisionIfsVolumeTotal = precisionIfsVolumeTotal.add(billingList.get(j).getTransportPathEntity().getTotalVolume());
				setPrecisionIfsWaybill.add(billingList.get(j).getTransportPathEntity().getWaybillNo());
			}
			// 判断是否快递
			if (null != billingList.get(j).getTransportPathEntity().getTransportModel() && StringUtil.equals(billingList.get(j).getTransportPathEntity().getTransportModel(),
			PricingConstants.ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE)) {
				// 快递量增加
				expressWeightTotal = expressWeightTotal.add(billingList.get(j).getTransportPathEntity().getTotalWeight());
				expressVolumeTotal = expressVolumeTotal.add(billingList.get(j).getTransportPathEntity().getTotalVolume());
				setExpressWaybill.add(billingList.get(j).getTransportPathEntity().getWaybillNo());
			}
			String salesDept;
			// 如果出发则营业部为开单部门
			if (StringUtil.equals(ForecastConstants.FORECAST_DEPART, action)) {
				salesDept = billingList.get(j).getPathDetailEntity().getBillingOrgCode();
			} else {// 否则到达则营业部为上一部门
				salesDept = billingList.get(j).getPathDetailEntity().getOrigOrgCode();
			}
			// 判断是否为本外场辐射营业部
			if (salesDepartmentCodeList.contains(salesDept)) {
				// 如果是则把本条保存 以备统计本部门开单货量使用
				billingBeLongList.add(billingList.get(j));
				setSalesDepartmentCode.add(salesDept);
			}
		}
		// 循环在途货件LIST
		for (int j = 0; j < inTransitList.size(); j++) {
			// 总量增加
			weightTotal = weightTotal.add(inTransitList.get(j).getTransportPathEntity().getTotalWeight());
			volumeTotal = volumeTotal.add(inTransitList.get(j).getTransportPathEntity().getTotalVolume());
			setTotalWaybill.add(inTransitList.get(j).getTransportPathEntity().getWaybillNo());
			// 在途量增加
			intransitWeightTotal = intransitWeightTotal.add(inTransitList.get(j).getTransportPathEntity().getTotalWeight());
			intransitVolumeTotal = intransitVolumeTotal.add(inTransitList.get(j).getTransportPathEntity().getTotalVolume());
			setInTransitWaybill.add(inTransitList.get(j).getTransportPathEntity().getWaybillNo());
			// 判断是否精准卡航
			if (null != inTransitList.get(j).getTransportPathEntity().getTransportModel() && StringUtil.equals(inTransitList.get(j).getTransportPathEntity().getTransportModel(),
			PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT)) {
				// 卡航量增加
				gpsEnabledResWeightTotal = gpsEnabledResWeightTotal.add(inTransitList.get(j).getTransportPathEntity().getTotalWeight());
				gpsEnabledResVolumeTotal = gpsEnabledResVolumeTotal.add(inTransitList.get(j).getTransportPathEntity().getTotalVolume());
				setGpsEnabledResWaybill.add(inTransitList.get(j).getTransportPathEntity().getWaybillNo());
			}
			// 判断是否精准城运
			if (null != inTransitList.get(j).getTransportPathEntity().getTransportModel() && StringUtil.equals(inTransitList.get(j).getTransportPathEntity().getTransportModel(),
			PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT)) {
				// 城运量增加
				precisionIfsWeightTotal = precisionIfsWeightTotal.add(inTransitList.get(j).getTransportPathEntity().getTotalWeight());
				precisionIfsVolumeTotal = precisionIfsVolumeTotal.add(inTransitList.get(j).getTransportPathEntity().getTotalVolume());
				setPrecisionIfsWaybill.add(inTransitList.get(j).getTransportPathEntity().getWaybillNo());
			}
			// 判断是否快递
			if (null != inTransitList.get(j).getTransportPathEntity().getTransportModel() && StringUtil.equals(inTransitList.get(j).getTransportPathEntity().getTransportModel(),
				PricingConstants.ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE)) {
				// 快递量增加
				expressWeightTotal = expressWeightTotal.add(inTransitList.get(j).getTransportPathEntity().getTotalWeight());
				expressVolumeTotal = expressVolumeTotal.add(inTransitList.get(j).getTransportPathEntity().getTotalVolume());
				setExpressWaybill.add(inTransitList.get(j).getTransportPathEntity().getWaybillNo());
			}
			String vehicleNo;
			// 如果出发则车牌号为上一段车牌号
			if (StringUtil.equals(ForecastConstants.FORECAST_DEPART, action)) {
				vehicleNo = inTransitList.get(j).getPathDetailEntity().getBeforeVehicleNo();
			} else {// 否则到达则车牌号为本段车牌号
				vehicleNo = inTransitList.get(j).getPathDetailEntity().getVehicleNo();
			}
			setVehicleCode.add(vehicleNo);
		}
		// 循环在库货件LIST
		for (int j = 0; j < inventoryList.size(); j++) {
			// 总量增加
			
			//总重量
			weightTotal = weightTotal.add(inventoryList.get(j).getTransportPathEntity().getTotalWeight());
			//总体积
			volumeTotal = volumeTotal.add(inventoryList.get(j).getTransportPathEntity().getTotalVolume());
			 //运单号 
			setTotalWaybill.add(inventoryList.get(j).getTransportPathEntity().getWaybillNo());
			
			// 在库量增加
			 // 总重量
			inventoryWeightTotal = inventoryWeightTotal.add(inventoryList.get(j).getTransportPathEntity().getTotalWeight());
			 //总体积
			inventoryVolumeTotal = inventoryVolumeTotal.add(inventoryList.get(j).getTransportPathEntity().getTotalVolume());
			 // 运单
			setInventoryWaybill.add(inventoryList.get(j).getTransportPathEntity().getWaybillNo());
			// 判断是否精准卡航
			if (null != inventoryList.get(j).getTransportPathEntity().getTransportModel() && StringUtil.equals(inventoryList.get(j).getTransportPathEntity().getTransportModel(), PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT)) {
				// 卡航量增加
				gpsEnabledResWeightTotal = gpsEnabledResWeightTotal.add(inventoryList.get(j).getTransportPathEntity().getTotalWeight());
				gpsEnabledResVolumeTotal = gpsEnabledResVolumeTotal.add(inventoryList.get(j).getTransportPathEntity().getTotalVolume());
				setGpsEnabledResWaybill.add(inventoryList.get(j).getTransportPathEntity().getWaybillNo());
			}
			// 判断是否精准城运
			if (null != inventoryList.get(j).getTransportPathEntity().getTransportModel() && StringUtil.equals(inventoryList.get(j).getTransportPathEntity().getTransportModel(),
			PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT)) {
				// 城运量增加
				precisionIfsWeightTotal = precisionIfsWeightTotal.add(inventoryList.get(j).getTransportPathEntity().getTotalWeight());
				precisionIfsVolumeTotal = precisionIfsVolumeTotal.add(inventoryList.get(j).getTransportPathEntity().getTotalVolume());
				setPrecisionIfsWaybill.add(inventoryList.get(j).getTransportPathEntity().getWaybillNo());
			}
			// 判断是否快递
			if (null != inventoryList.get(j).getTransportPathEntity().getTransportModel() && StringUtil.equals(inventoryList.get(j).getTransportPathEntity().getTransportModel(),
				PricingConstants.ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE)) {
				// 快递量增加
				expressWeightTotal = expressWeightTotal.add(inventoryList.get(j).getTransportPathEntity().getTotalWeight());
				expressVolumeTotal = expressVolumeTotal.add(inventoryList.get(j).getTransportPathEntity().getTotalVolume());
				setExpressWaybill.add(inventoryList.get(j).getTransportPathEntity().getWaybillNo());
			}
		}
		// 设置总票数
		waybillQtyTotal = setTotalWaybill.size();
		// 设置开单票数
		billingQtyTotal = setBillingWaybill.size();
		// 设置在途票数
		intransitQtyTotal = setInTransitWaybill.size();
		// 设置在库票数
		inventoryQtyTotal = setInventoryWaybill.size();
		// 设置卡航票数
		gpsEnabledResQtyTotal = setGpsEnabledResWaybill.size();
		// 设置城运票数
		precisionIfsQtyTotal = setPrecisionIfsWaybill.size();
		// 设置城运票数
		expressQtyTotal = setExpressWaybill.size();
		// 根据orgCode 查询region站点组
		String region = "无";
		List<SiteGroupEntity> siteGroupList = siteGroupService.querySiteGroupsBySiteCode(relevantOrgCode);
		if (CollectionUtils.isEmpty(siteGroupList)) {
			logger.error("调用综合接口根据组织查询站点组为空! 类: " + siteGroupService + " 方法: querySiteGroupsBySiteCode orgCode : " + relevantOrgCode);
			siteGroupList = new ArrayList<SiteGroupEntity>();
		}
		// 如果出发 查询出发站点组
		if (StringUtil.equals(ForecastConstants.FORECAST_DEPART, action)) {
			for (int s = 0; s < siteGroupList.size(); s++) {
				// 如果站点组类型等于出发,则该条为外场出发站点组
				if (StringUtil.equals(siteGroupList.get(s).getType(), DictionaryValueConstants.BSE_SITE_GROUP_TYPE_CF)) {
					region = siteGroupList.get(s).getVirtualCode();
				}
			}
		} else {// 到达 查询到达站点组
			for (int s = 0; s < siteGroupList.size(); s++) {
				// 如果站点组类型等于到达,则该条为外场到达站点组
				if (StringUtil.equals(siteGroupList.get(s).getType(), DictionaryValueConstants.BSE_SITE_GROUP_TYPE_DD)) {
					region = siteGroupList.get(s).getVirtualCode();
				}
			}
		}
		
		// 提交总表
		String forecastQuantityId = UUIDUtils.getUUID();
		ForecastQuantityEntity forecastQuantityEntity = new ForecastQuantityEntity();
		
		// 查询当天合车改变量 记录到新记录中
		// 只有出发才有合车
		if (StringUtil.equals(ForecastConstants.FORECAST_DEPART, action)) {
			BigDecimal changeVolume = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
			List<ChangeQuantityEntity> outList = this.queryChangeOutByDate(statisticsDate, orgCode, relevantOrgCode);
			List<ChangeQuantityEntity> inList = this.queryChangeInByDate(statisticsDate, orgCode, relevantOrgCode);
			// 合出的减去 合入的增加
			for (int q = 0; q < outList.size(); q++) {
				changeVolume = changeVolume.subtract(outList.get(q).getModifyWeight());
			}
			for (int q = 0; q < inList.size(); q++) {
				changeVolume = changeVolume.add(inList.get(q).getModifyWeight());
			}
			// 只有不为空才更新该字段
			if (outList.size() > 0 || inList.size() > 0) {
				forecastQuantityEntity.setDeviationVolume(changeVolume.setScale(three, BigDecimal.ROUND_HALF_DOWN));
			}
		}
		
		forecastQuantityEntity.setForecastQuantityId(forecastQuantityId);
		forecastQuantityEntity.setBelongOrgCode(orgCode);
		forecastQuantityEntity.setRegion(region);
		forecastQuantityEntity.setRelevantOrgCode(relevantOrgCode);
		forecastQuantityEntity.setWeightTotal(weightTotal.setScale(three, BigDecimal.ROUND_HALF_DOWN));
		forecastQuantityEntity.setVolumeTotal(volumeTotal.setScale(three, BigDecimal.ROUND_HALF_DOWN));
		forecastQuantityEntity.setWaybillQtyTotal(BigDecimal.valueOf(waybillQtyTotal));
		forecastQuantityEntity.setGpsEnabledResWeightTotal(gpsEnabledResWeightTotal.setScale(three, BigDecimal.ROUND_HALF_DOWN));
		forecastQuantityEntity.setGpsEnabledResVolumeTotal(gpsEnabledResVolumeTotal.setScale(three, BigDecimal.ROUND_HALF_DOWN));
		forecastQuantityEntity.setGpsEnabledResQtyTotal(BigDecimal.valueOf(gpsEnabledResQtyTotal));
		forecastQuantityEntity.setPrecisionIfsWeightTotal(precisionIfsWeightTotal.setScale(three, BigDecimal.ROUND_HALF_DOWN));
		forecastQuantityEntity.setPrecisionIfsVolumeTotal(precisionIfsVolumeTotal.setScale(three, BigDecimal.ROUND_HALF_DOWN));
		forecastQuantityEntity.setPrecisionIfsQtyTotal(BigDecimal.valueOf(precisionIfsQtyTotal));
		forecastQuantityEntity.setExpressWeightTotal(expressWeightTotal.setScale(three, BigDecimal.ROUND_HALF_DOWN));
		forecastQuantityEntity.setExpressVolumeTotal(expressVolumeTotal.setScale(three, BigDecimal.ROUND_HALF_DOWN));
		forecastQuantityEntity.setExpressQtyTotal(expressQtyTotal);
		forecastQuantityEntity.setInventoryWeightTotal(inventoryWeightTotal.setScale(three, BigDecimal.ROUND_HALF_DOWN));
		forecastQuantityEntity.setInventoryVolumeTotal(inventoryVolumeTotal.setScale(three, BigDecimal.ROUND_HALF_DOWN));
		forecastQuantityEntity.setInventoryQtyTotal(BigDecimal.valueOf(inventoryQtyTotal));
		forecastQuantityEntity.setBillingWeightTotal(billingWeightTotal.setScale(three, BigDecimal.ROUND_HALF_DOWN));
		forecastQuantityEntity.setBillingVolumeTotal(billingVolumeTotal.setScale(three, BigDecimal.ROUND_HALF_DOWN));
		forecastQuantityEntity.setBillingQtyTotal(BigDecimal.valueOf(billingQtyTotal));
		forecastQuantityEntity.setIntransitWeightTotal(intransitWeightTotal.setScale(three, BigDecimal.ROUND_HALF_DOWN));
		forecastQuantityEntity.setIntransitVolumeTotal(intransitVolumeTotal.setScale(three, BigDecimal.ROUND_HALF_DOWN));
		forecastQuantityEntity.setIntransitQtyTotal(BigDecimal.valueOf(intransitQtyTotal));
		forecastQuantityEntity.setStatisticsTime(statistics);
		forecastQuantityEntity.setStatisticsDate(statisticsDate);
		forecastQuantityEntity.setStatisticsHHMM(statisticsHHMM);
		forecastQuantityEntity.setDeparturearrival(status);
		// 转换为日期保存 日期代表本日期周期
		forecastTime = DateUtils.convert(DateUtils.convert(forecastTime, DateUtils.DATE_FORMAT), DateUtils.DATE_FORMAT);
		forecastQuantityEntity.setForecastTime(forecastTime);
		forecastQuantityEntity.setType(action);
		forecastQuantityEntity.setDataType(ForecastConstants.DATA_TYPE_TWO);
		
		addForecastQuantityEntityService(forecastQuantityEntity);
		// 调用方法做开单表数据
		this.billingForecast(forecastQuantityId, action, statistics, orgCode, relevantOrgCode, region, billingBeLongList, setSalesDepartmentCode);
		// 调用方法做在途表数据
		this.inTransitForecast(forecastQuantityId, action, statistics, orgCode, relevantOrgCode, region, inTransitList, setVehicleCode);
	}

	/**
	 * 
	 * @Title: billingForecast 
	 * @Description: 
	 * 				根据总表UUID,现部门,到达部门,始发配载部门list等生成开单表信息
	 * 
	 * 				开单(运单)
	 * 				计算 : 开单数(运单数),开单总重量,开单体积
	 *
	 * 				所有开单(运单)中分为
	 * 					精准卡航 :
	 * 						计算  : 开单数(运单数),总重量,总体积
	 * 					精准城运 :
	 * 						计算 : 开单数(运单数),总重量,总体积
	 * 
	 * 				PS : 运单包含 精准卡航、精准城运
	 * 
	 * @author yuyongxiang-134019-yuyongxiang@deppon.com
	 * @date 2014年3月11日 下午3:47:27
	 *
	 * @param forecastQuantityId
	 * @param action
	 * @param statistics
	 * @param orgCode
	 * @param relevantOrgCode
	 * @param region
	 * @param billingBeLongList
	 * @param setSalesDepartmentCode
	 * @throws TfrBusinessException    设定文件 
	 *
	 * @see com.deppon.foss.module.transfer.platform.api.server.service.IForecastService#billingForecast(java.lang.String, java.lang.String, java.util.Date, java.lang.String, java.lang.String, java.lang.String, java.util.List, java.util.Set)
	 */
	@Override
	public void billingForecast(String forecastQuantityId, String action, Date statistics, String orgCode, 
			String relevantOrgCode, String region, List<ForecastDto> billingBeLongList, Set<String> setSalesDepartmentCode) throws TfrBusinessException {
		Object salesDepartmentCode[] = setSalesDepartmentCode.toArray();
		//开单部门 或 出发部门 TEMP
		String salesDept = "";
		
		// 根据所有所属营业部循环
		for (int i = 0; i < salesDepartmentCode.length; i++) {
			// 出发营业部
			String salesDeptOrgCode = salesDepartmentCode[i].toString();
			String salesDistrict = "";
			// 查询上级部门中指定类型为营业小区的部门
			List<String> bizTypes = new ArrayList<String>();
			bizTypes.add(BizTypeConstants.ORG_SMALL_REGION);
			OrgAdministrativeInfoEntity upOrgCodeEntity = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(salesDeptOrgCode, bizTypes);
			if (null == upOrgCodeEntity || StringUtil.isEmpty(upOrgCodeEntity.getCode())) {
				logger.error("调用综合接口查询营业部所属营业小区没有数据! 类: " + orgAdministrativeInfoComplexService + " 方法: queryOrgAdministrativeInfoByCode orgCode : " + salesDeptOrgCode + " bizTypes : " + bizTypes);
			} else {
				// 设置出发营业部所属营业区
				
				salesDistrict = upOrgCodeEntity.getCode();
			}
			
			//设置统计字段 运单下面分成  精准卡航、精准城运
			
			// 开单(运单)
			//总重量
			BigDecimal billingWeightTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
			//总体积
			BigDecimal billingVolumeTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
			//总单数
			Integer billingWaybillQty = 0;
			
			//精准卡航
			//GPS 总重量
			BigDecimal gpsEnabledResWeightTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
			//GPS 总体积
			BigDecimal gpsEnabledResVolumeTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
			//GPS 总单数
			Integer gpsEnabledResQtyTotal = 0;
			
			//精准城运
			//总重量(普:城运)
			BigDecimal precisionIfsWeightTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
			//总体积(普:城运)
			BigDecimal precisionIfsVolumeTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
			//总单数(普:城运)
			Integer precisionIfsQtyTotal = 0;
			
			//快递
			//总重量(快递)
			BigDecimal expressWeightTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
			//总体积(快递)
			BigDecimal expressVolumeTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
			//总单数(快递)
			Integer expressQtyTotal = 0;
			
			//SET 去除重复
			Set<String> setBillingWaybill = new HashSet<String>();
			Set<String> setGpsEnabledResWaybill = new HashSet<String>();
			Set<String> setPrecisionIfsWaybill = new HashSet<String>();
			Set<String> setExpressWaybill = new HashSet<String>();
			
			// 循环list 找到符合条件的条目统计
			for (int j = 0; j < billingBeLongList.size(); j++) {
				// 如果是出发货量,则看开单营业部是否属于出发营业部
				if (StringUtil.equals(ForecastConstants.FORECAST_DEPART, action)) {
					
					salesDept = billingBeLongList.get(j).getPathDetailEntity().getBillingOrgCode();
				} else {// 否则是到达货量,则看出发部门是否属于出发营业部,并且出发部门应等于开单部门
					
					salesDept = billingBeLongList.get(j).getPathDetailEntity().getOrigOrgCode();
				}
				// 如果本条是该出发营业部的,则统计
				if (StringUtil.equals(salesDept, salesDeptOrgCode)) {
					// 开单量增加
					billingWeightTotal = billingWeightTotal.add(billingBeLongList.get(j).getTransportPathEntity().getTotalWeight());
					billingVolumeTotal = billingVolumeTotal.add(billingBeLongList.get(j).getTransportPathEntity().getTotalVolume());
					setBillingWaybill.add(billingBeLongList.get(j).getTransportPathEntity().getWaybillNo());
					// 判断是否精准卡航
					if (null != billingBeLongList.get(j).getTransportPathEntity().getTransportModel() && StringUtil.equals(billingBeLongList.get(j).getTransportPathEntity().getTransportModel(), PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT)) {
						// 卡航量增加
						gpsEnabledResWeightTotal = gpsEnabledResWeightTotal.add(billingBeLongList.get(j).getTransportPathEntity().getTotalWeight());
						gpsEnabledResVolumeTotal = gpsEnabledResVolumeTotal.add(billingBeLongList.get(j).getTransportPathEntity().getTotalVolume());
						setGpsEnabledResWaybill.add(billingBeLongList.get(j).getTransportPathEntity().getWaybillNo());
					}
					// 判断是否精准城运
					if (null != billingBeLongList.get(j).getTransportPathEntity().getTransportModel() && StringUtil.equals(billingBeLongList.get(j).getTransportPathEntity().getTransportModel(),
					PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT)) {
						// 城运量增加
						precisionIfsWeightTotal = precisionIfsWeightTotal.add(billingBeLongList.get(j).getTransportPathEntity().getTotalWeight());
						precisionIfsVolumeTotal = precisionIfsVolumeTotal.add(billingBeLongList.get(j).getTransportPathEntity().getTotalVolume());
						setPrecisionIfsWaybill.add(billingBeLongList.get(j).getTransportPathEntity().getWaybillNo());
					}
					// 判断是否快递
					if (null != billingBeLongList.get(j).getTransportPathEntity().getTransportModel() && StringUtil.equals(billingBeLongList.get(j).getTransportPathEntity().getTransportModel(),
					PricingConstants.ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE)) {
						// 快递量增加
						expressWeightTotal = expressWeightTotal.add(billingBeLongList.get(j).getTransportPathEntity().getTotalWeight());
						expressVolumeTotal = expressVolumeTotal.add(billingBeLongList.get(j).getTransportPathEntity().getTotalVolume());
						setExpressWaybill.add(billingBeLongList.get(j).getTransportPathEntity().getWaybillNo());
					}
				}
			}
			/**开单票数*/
			billingWaybillQty = setBillingWaybill.size();
			/**卡航票数*/
			gpsEnabledResQtyTotal = setGpsEnabledResWaybill.size();
			/**城运票数*/
			precisionIfsQtyTotal = setPrecisionIfsWaybill.size();
			/**快递票数*/
			expressQtyTotal = setExpressWaybill.size();
			
			// 提交开单表
			BillingEntity billingEntity = new BillingEntity();
			
			billingEntity.setBillingId(UUIDUtils.getUUID());
			billingEntity.setBelongOrgCode(orgCode);
			billingEntity.setRelevantOrgCode(relevantOrgCode);
			billingEntity.setBillingSalesDepartment(salesDeptOrgCode);
			billingEntity.setBillingSalesDistrict(salesDistrict);
			billingEntity.setBillingWeight(billingWeightTotal.setScale(three, BigDecimal.ROUND_HALF_DOWN));
			billingEntity.setBillingVolume(billingVolumeTotal.setScale(three, BigDecimal.ROUND_HALF_DOWN));
			billingEntity.setBillingQty(billingWaybillQty);
			billingEntity.setGpsEnabledResWeight(gpsEnabledResWeightTotal.setScale(three, BigDecimal.ROUND_HALF_DOWN));
			billingEntity.setGpsEnabledResVolume(gpsEnabledResVolumeTotal.setScale(three, BigDecimal.ROUND_HALF_DOWN));
			billingEntity.setGpsEnabledResQty(gpsEnabledResQtyTotal);
			billingEntity.setPrecisionIfsWeight(precisionIfsWeightTotal.setScale(three, BigDecimal.ROUND_HALF_DOWN));
			billingEntity.setPrecisionIfsVolume(precisionIfsVolumeTotal.setScale(three, BigDecimal.ROUND_HALF_DOWN));
			billingEntity.setPrecisionIfsQty(precisionIfsQtyTotal);
			billingEntity.setGpsEnabledResQty(gpsEnabledResQtyTotal);
			billingEntity.setExpressWeight(expressWeightTotal.setScale(three, BigDecimal.ROUND_HALF_DOWN));
			billingEntity.setExpressVolume(expressVolumeTotal.setScale(three, BigDecimal.ROUND_HALF_DOWN));
			billingEntity.setExpressQty(expressQtyTotal);
			billingEntity.setForecastQuantityId(forecastQuantityId);
			billingEntity.setRegion(region);
			billingEntity.setType(action);
			billingEntity.setStatisticsTime(statistics);
			
			//更新
			billingDaoPlatform.addbilling(billingEntity);
		}
	}
	
	/**
	 * 根据总表UUID,现部门,到达部门,车牌号list等生成在途表信息
	 * 
	 * 在途(运单)
	 * 计算 : 在途总单数 ,在途运总重量,在途运体积
	 *
	 * 所有在途运单中分为
	 * 	精准卡航 :
	 * 		计算  : 总单数,总重量,总体积
	 * 	精准城运 :
	 * 		计算 : 总单数,总重量,总体积
	 * 
	 * PS : 运单包含 精准卡航、精准城运
	 * @author huyue
	 * @date 2012-11-28 下午9:01:20
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.IForecastService#billingForecast(java.lang.String,
	 *      java.lang.String, java.util.Date, java.lang.String,
	 *      java.lang.String, java.lang.String, java.util.List, java.util.Set)
	 */
	public void inTransitForecast(String forecastQuantityId, String action, Date statistics, String orgCode, String relevantOrgCode, String region, List<ForecastDto> inTransitList, Set<String> setVehicleCode) throws TfrBusinessException {
		Object vehicleCode[] = setVehicleCode.toArray();
		// 根据所有 在途车牌号循环
		for (int i = 0; i < vehicleCode.length; i++) {
			// 在途车牌号
			String vehicleNo = (String)vehicleCode[i];
			if(StringUtils.isEmpty(vehicleNo)) {
				//为空则进行下一次循环
				continue;
			}
			//在途运单总重量
			BigDecimal intransitWeightTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
			//在途运单总体积
			BigDecimal intransitVolumeTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
			//在途运单总票数
			Integer intransitQtyTotal = 0;
			
			//在途运单 中 卡航 总重量
			BigDecimal gpsEnabledResWeightTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
			//在途运单 中 卡航 总体积
			BigDecimal gpsEnabledResVolumeTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
			//在途运单 中 卡航 总票数
			Integer gpsEnabledResQtyTotal = 0;
			
			//在途运单 中 城运 总重量
			BigDecimal precisionIfsWeightTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
			//在途运单 中 城运 总体积
			BigDecimal precisionIfsVolumeTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
			//在途运单 中 城运 总票数
			Integer precisionIfsQtyTotal = 0;
			
			//快递
			//总重量(快递)
			BigDecimal expressWeightTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
			//总体积(快递)
			BigDecimal expressVolumeTotal = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
			//总单数(快递)
			Integer expressQtyTotal = 0;
			
			Set<String> setInTransitWaybill = new HashSet<String>();
			Set<String> setGpsEnabledResWaybill = new HashSet<String>();
			Set<String> setPrecisionIfsWaybill = new HashSet<String>();
			Set<String> setExpressWaybill = new HashSet<String>();
			
			// 循环list 找到符合条件的条目统计
			for (int j = 0; j < inTransitList.size(); j++) {
				String vehicle;
				// 如果是出发货量,则看开单营业部是否属于出发营业部
				if (StringUtil.equals(ForecastConstants.FORECAST_DEPART, action)) {
					vehicle = inTransitList.get(j).getPathDetailEntity().getBeforeVehicleNo();
				} else {// 否则是到达货量,则看出发部门是否属于出发营业部,并且出发部门应等于开单部门
					vehicle = inTransitList.get(j).getPathDetailEntity().getVehicleNo();
				}
				// 如果本条是该出发营业部的,则统计
				if (StringUtil.equals(vehicle, vehicleNo)) {
					// 在途量增加
					intransitWeightTotal = intransitWeightTotal.add(inTransitList.get(j).getTransportPathEntity().getTotalWeight());
					intransitVolumeTotal = intransitVolumeTotal.add(inTransitList.get(j).getTransportPathEntity().getTotalVolume());
					setInTransitWaybill.add(inTransitList.get(j).getTransportPathEntity().getWaybillNo());
					// 判断是否精准卡航
					if (null != inTransitList.get(j).getTransportPathEntity().getTransportModel() && StringUtil.equals(inTransitList.get(j).getTransportPathEntity().getTransportModel(), PricingConstants.ProductEntityConstants.PRICING_PRODUCT_LONG_DISTANCE_FAST_FREIGHT)) {
						// 卡航量增加
						gpsEnabledResWeightTotal = gpsEnabledResWeightTotal.add(inTransitList.get(j).getTransportPathEntity().getTotalWeight());
						gpsEnabledResVolumeTotal = gpsEnabledResVolumeTotal.add(inTransitList.get(j).getTransportPathEntity().getTotalVolume());
						setGpsEnabledResWaybill.add(inTransitList.get(j).getTransportPathEntity().getWaybillNo());
					}
					// 判断是否精准城运
					if (null != inTransitList.get(j).getTransportPathEntity().getTransportModel() && StringUtil.equals(inTransitList.get(j).getTransportPathEntity().getTransportModel(),
					PricingConstants.ProductEntityConstants.PRICING_PRODUCT_SHORT_DISTANCE_FAST_FREIGHT)) {
						// 城运量增加
						precisionIfsWeightTotal = precisionIfsWeightTotal.add(inTransitList.get(j).getTransportPathEntity().getTotalWeight());
						precisionIfsVolumeTotal = precisionIfsVolumeTotal.add(inTransitList.get(j).getTransportPathEntity().getTotalVolume());
						setPrecisionIfsWaybill.add(inTransitList.get(j).getTransportPathEntity().getWaybillNo());
					}
					// 判断是否快递
					if (null != inTransitList.get(j).getTransportPathEntity().getTransportModel() && StringUtil.equals(inTransitList.get(j).getTransportPathEntity().getTransportModel(),
					PricingConstants.ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE)) {
						// 快递量增加
						expressWeightTotal = expressWeightTotal.add(inTransitList.get(j).getTransportPathEntity().getTotalWeight());
						expressVolumeTotal = expressVolumeTotal.add(inTransitList.get(j).getTransportPathEntity().getTotalVolume());
						setExpressWaybill.add(inTransitList.get(j).getTransportPathEntity().getWaybillNo());
					}
				}
			}
			
			//计算在途 运单总数
			intransitQtyTotal = setInTransitWaybill.size();
			//计算在途 运单中卡航总数
			gpsEnabledResQtyTotal = setGpsEnabledResWaybill.size();
			//计算在途 运单中城运总数
			precisionIfsQtyTotal = setPrecisionIfsWaybill.size();
			//计算在途 运单中快递总数
			expressQtyTotal = setExpressWaybill.size();
			
			// 提交在途表
			InTransitEntity inTransitEntity = new InTransitEntity();
			
			inTransitEntity.setIntransitId(UUIDUtils.getUUID());
			inTransitEntity.setBelongOrgCode(orgCode);
			inTransitEntity.setRelevantOrgCode(relevantOrgCode);
			inTransitEntity.setIntransitVehicleNo(vehicleNo);
			inTransitEntity.setIntransitWeight(intransitWeightTotal.setScale(three, BigDecimal.ROUND_HALF_DOWN));
			inTransitEntity.setIntransitVolume(intransitVolumeTotal.setScale(three, BigDecimal.ROUND_HALF_DOWN));
			inTransitEntity.setIntransitQty(intransitQtyTotal);
			inTransitEntity.setGpsEnabledResWeight(gpsEnabledResWeightTotal.setScale(three, BigDecimal.ROUND_HALF_DOWN));
			inTransitEntity.setGpsEnabledResVolume(gpsEnabledResVolumeTotal.setScale(three, BigDecimal.ROUND_HALF_DOWN));
			inTransitEntity.setGpsEnabledResQty(gpsEnabledResQtyTotal);
			inTransitEntity.setPrecisionIfsWeight(precisionIfsWeightTotal.setScale(three, BigDecimal.ROUND_HALF_DOWN));
			inTransitEntity.setPrecisionIfsVolume(precisionIfsVolumeTotal.setScale(three, BigDecimal.ROUND_HALF_DOWN));
			inTransitEntity.setPrecisionIfsQty(precisionIfsQtyTotal);
			inTransitEntity.setExpressWeight(expressWeightTotal.setScale(three, BigDecimal.ROUND_HALF_DOWN));
			inTransitEntity.setExpressVolume(expressVolumeTotal.setScale(three, BigDecimal.ROUND_HALF_DOWN));
			inTransitEntity.setExpressQty(expressQtyTotal);
			inTransitEntity.setForecastQuantityId(forecastQuantityId);
			inTransitEntity.setRegion(region);
			inTransitEntity.setType(action);
			inTransitEntity.setStatisticsTime(statistics);
			
			//新增
			inTransitDaoPlatform.addinTransit(inTransitEntity);
		}
	}

	/**
	 * 
	 * @Title: arriveBilling 
	 * @Description: 查询到达开单LIST
	 * @author yuyongxiang-134019-yuyongxiang@deppon.com
	 * @date 2014年3月22日 上午9:10:08
	 *
	 * @param objectiveOrgCode
	 * @param origOrgCode
	 * @param forecastStartTime
	 * @param forecastEndTime
	 * @param pickupCentralized 为true是 集中接货 为false是非集中货
	 * @return
	 * @throws TfrBusinessException    设定文件 
	 *
	 * @see com.deppon.foss.module.transfer.platform.api.server.service.IForecastService#arriveBilling(java.lang.String, java.lang.String, java.util.Date, java.util.Date)
	 */
	protected List<PathDetailEntity> arriveBilling(String objectiveOrgCode, String origOrgCode, Date forecastStartTime, Date forecastEndTime,Boolean pickupCentralized) throws TfrBusinessException {
		Map<String, Object> billingMap = new HashMap<String, Object>();
		// 找到状态为未离开的
		billingMap.put(origOrg, origOrgCode);
		billingMap.put(objective, objectiveOrgCode);
		List<String> list=new ArrayList<String>();
		//状态为未离开或者为已交接的状态为 开单为交接 （把以交接的算在为交接的原因是交接单的那里在作废交接单的时候不修改走货路径的状态所以这边为了这样的偏差）
		list.add(TransportPathConstants.PATHDETAIL_STATUS_NOTLEAVE);
		list.add(TransportPathConstants.PATHDETAIL_STATUS_HANDOVER);
		billingMap.put("arriveOrLeaveList", list);
		billingMap.put(countStartTime, forecastStartTime);
		billingMap.put(countEndTime, forecastEndTime);
		
		if(pickupCentralized){
			billingMap.put("pickupCentralizedTrue", "true");			
		}else{
			billingMap.put("pickupCentralizedFalse", "false");
		}
		return forecastQuantityJOBDao.queryPathDetailByArrive(billingMap);
	}

	/**
	 * 
	 * @Title: arriveInTransit 
	 * @Description: 查询到达在途LIST
	 * @author yuyongxiang-134019-yuyongxiang@deppon.com
	 * @date 2014年3月22日 上午9:12:02
	 *
	 * @param objectiveOrgCode
	 * @param origOrgCode
	 * @param forecastStartTime
	 * @param forecastEndTime
	 * @param pickupCentralized 为true是 集中接货 为false是非集中货
	 * @return
	 * @throws TfrBusinessException    设定文件 
	 *
	 * @see com.deppon.foss.module.transfer.platform.api.server.service.IForecastService#arriveInTransit(java.lang.String, java.lang.String, java.util.Date, java.util.Date)
	 */
	protected List<PathDetailEntity> arriveInTransit(String objectiveOrgCode, String origOrgCode, Date forecastStartTime, Date forecastEndTime,Boolean pickupCentralized) throws TfrBusinessException {
		Map<String, Object> inTransitMap = new HashMap<String, Object>();
		inTransitMap = new HashMap<String, Object>();
		// 找到状态为出发的
		inTransitMap.put(origOrg, origOrgCode);
		inTransitMap.put(objective, objectiveOrgCode);
		
		List<String> list=new ArrayList<String>();
		//状态为未离开或者为已交接的状态为 开单为交接 （把以交接的算在为交接的原因是交接单的那里在作废交接单的时候不修改走货路径的状态所以这边为了这样的偏差）
		list.add(TransportPathConstants.PATHDETAIL_STATUS_LEAVE);
		inTransitMap.put("arriveOrLeaveList", list);
		inTransitMap.put(countStartTime, forecastStartTime);
		inTransitMap.put(countEndTime, forecastEndTime);
		
		
		if(pickupCentralized){
			inTransitMap.put("pickupCentralizedTrue", "true");			
		}else{
			inTransitMap.put("pickupCentralizedFalse", "false");
		}
		
		List<PathDetailEntity> arriveLeaveList = forecastQuantityJOBDao.queryPathDetailByArrive(inTransitMap);
		
		for (int a = 0; a < arriveLeaveList.size(); a++) {
			// 为空的都是交接没有车牌号的
			if (StringUtils.isEmpty(arriveLeaveList.get(a).getVehicleNo())) {
				arriveLeaveList.get(a).setVehicleNo("N/A");
			}
		}
		return arriveLeaveList;
	}


	/**
	 * 
	 * @Title: departRelevantOrgCode 
	 * @Description: 出发 － 本地货量
	 * @author yuyongxiang-134019-yuyongxiang@deppon.com
	 * @date 2014年3月22日 上午9:12:54 
	 * @param orgCode
	 * @param forecastStartTime
	 * @param forecastEndTime
	 * @return
	 * @throws TfrBusinessException    设定文件 
	 * @return List<String>    返回类型
	 */
	protected List<String> departRelevantOrgCode(String orgCode, Date forecastStartTime, Date forecastEndTime) throws TfrBusinessException {
		Map<String, Object> billingMap = new HashMap<String, Object>();
		// 找到状态为未离开的
		billingMap.put(origOrg, orgCode);
		billingMap.put(arriveOrLeave, TransportPathConstants.PATHDETAIL_STATUS_NOTLEAVE);
		billingMap.put(countStartTime, forecastStartTime);
		billingMap.put(countEndTime, forecastEndTime);
		return pathDetailDao.departRelevantOrgCode(billingMap);
	}

	/**
	 * 查询出发开单LIST
	 * 
	 * arriveOrLeave: 状态为 -> 未离开,<br/>
	 * 
	 * 上段车牌号不空 就是出发货量里的在途货量<br/>
	 * 
	 * SQL: <br/>
	 *
	 <pan>
	  SELECT
		(*)
		FROM TFR.T_OPT_PATH_DETAIL 
		WHERE <br/>
		ORIG_ORG_CODE = #{origOrgCode} 
		AND OBJECTIVE_ORG_CODE = #{objectiveOrgCode}
		AND ARRIVE_OR_LEAVE = #{arriveOrLeave}
		AND #{countStartTime} <= MODIFY_START_TIME
		AND #{countEndTime} > MODIFY_START_TIME
		AND BEFORE_VEHICLE_NO = 'N/A'
	</pan>
	 * 
	 * @author yu
	 * @date 2012-11-29 上午11:31:04
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.IForecastService#departBilling(java.lang.String,
	 *      java.util.Date, java.util.Date)
	 */
	public List<PathDetailEntity> departBilling(String origOrgCode, String objectiveOrgCode, Date forecastStartTime, Date forecastEndTime) throws TfrBusinessException {
		Map<String, Object> billingMap = new HashMap<String, Object>();
		// 找到状态为未离开的 上一段车牌号为空的
		billingMap.put(origOrg, origOrgCode);
		billingMap.put(objective, objectiveOrgCode);
		billingMap.put(arriveOrLeave, TransportPathConstants.PATHDETAIL_STATUS_NOTLEAVE);
		billingMap.put(countStartTime, forecastStartTime);
		billingMap.put(countEndTime, forecastEndTime);
		return pathDetailDao.queryStartBilling(billingMap);
	}
	
	/**
	 * 
	 * @Title: departBillingV 
	 * @Description: 
	 * @author yuyongxiang-134019-yuyongxiang@deppon.com
	 * @date 2014年3月17日 上午9:02:54 
	 * @return    设定文件 
	 * @return List<PathDetailEntity>    返回类型
	 */
	protected List<PathDetailEntity> departBillingV(String origOrgCode, String objectiveOrgCode, Date forecastStartTime, Date forecastEndTime){
		Map<String, Object> map=new HashMap<String, Object>(PlatformConstants.SONAR_NUMBER_6);
		
		map.put(origOrg, origOrgCode);
		map.put(objective, objectiveOrgCode);
		map.put(arriveOrLeave, TransportPathConstants.PATHDETAIL_STATUS_NOTLEAVE);
		map.put(countStartTime, forecastStartTime);
		map.put(countEndTime, forecastEndTime);
		map.put("beforeVehicleNo", "");
		
		
		return forecastQuantityJOBDao.selectPathDetailByDepartDeliveryvolume(map);
	}

	/**
	 * 查询出发在途LIST <br/>
	 * 
	 * arriveOrLeave: 状态为 -> 未离开,<br/>
	 * 
	 * ifChangeTime : 是否修改过计划出发时间  -> 否,<br/>
	 * 
	 * 上段车牌号不空 就是出发货量里的在途货量<br/>
	 * 
	 * SQL: <br/>
	 *
	 <pan>
	  SELECT
		(*)
		FROM TFR.T_OPT_PATH_DETAIL 
		WHERE <br/>
		ORIG_ORG_CODE = #{origOrgCode} 
		AND OBJECTIVE_ORG_CODE = #{objectiveOrgCode}
		AND ARRIVE_OR_LEAVE = #{arriveOrLeave}
		AND #{countStartTime} <= MODIFY_START_TIME
		AND #{countEndTime} > MODIFY_START_TIME
		AND BEFORE_VEHICLE_NO = 'N/A'
	</pan>
	 * 
	 * @author yu
	 * @date 2012-11-29 上午11:52:22
	 */
	public List<PathDetailEntity> departInTransit(String origOrgCode, String objectiveOrgCode, Date forecastStartTime, Date forecastEndTime) throws TfrBusinessException {
		Map<String, Object> inTransitMap = new HashMap<String, Object>();
		// 找到状态为未离开的 调整出发时间为否的 上一段车牌号不为空的
		inTransitMap.put(origOrg, origOrgCode);
		inTransitMap.put(objective, objectiveOrgCode);
		inTransitMap.put(arriveOrLeave, TransportPathConstants.PATHDETAIL_STATUS_HANDOVER);
		inTransitMap.put(countStartTime, forecastStartTime);
		inTransitMap.put(countEndTime, forecastEndTime);
		/** 
		 *  是否修改过计划出发时间 -> N
		 */
		inTransitMap.put("ifChangeTime", TransportPathConstants.NOTCHANGETIME);
		return pathDetailDao.queryStartNotBilling(inTransitMap);
	}
	/**
	 * 
	 * @Title: departInTransitV 
	 * @Description: 
	 * @author yuyongxiang-134019-yuyongxiang@deppon.com
	 * @date 2014年3月17日 下午1:41:17 
	 * @param origOrgCode
	 * @param objectiveOrgCode
	 * @param forecastStartTime
	 * @param forecastEndTime
	 * @return
	 * @throws TfrBusinessException    设定文件 
	 * @return List<PathDetailEntity>    返回类型
	 */
	protected List<PathDetailEntity> departInTransitV(String origOrgCode, String objectiveOrgCode, Date forecastStartTime, Date forecastEndTime) throws TfrBusinessException {
		Map<String,Object> map=new HashMap<String, Object>(PlatformConstants.SONAR_NUMBER_7);
		
		map.put(origOrg, origOrgCode);
		map.put(objective, objectiveOrgCode);
		map.put(arriveOrLeave, TransportPathConstants.PATHDETAIL_STATUS_NOTLEAVE);
		map.put(countStartTime, forecastStartTime);
		map.put(countEndTime, forecastEndTime);
		/** 
		 *  是否修改过计划出发时间 -> N
		 */
		map.put("ifChangeTime", TransportPathConstants.NOTCHANGETIME);
		map.put("beforeVehicleNo", "N/A");
		
		return  forecastQuantityJOBDao.selectPathDetailByDepartDeliveryvolume(map);
	}
	/**
	 * 查询出发在库LIST <br/>
	 * @author yu
	 * @date 2012-11-29 上午11:53:54
	 */
	public List<PathDetailEntity> departInventory(String origOrgCode, String objectiveOrgCode, Date forecastStartTime, Date forecastEndTime) throws TfrBusinessException {
		Map<String, Object> inventoryMap = new HashMap<String, Object>(PlatformConstants.SONAR_NUMBER_4);
		//出发部门
		inventoryMap.put(origOrg, origOrgCode);
		//到达部门
		inventoryMap.put(objective, objectiveOrgCode);
		return forecastQuantityJOBDao.queryStockWhitInStock(inventoryMap);
	}
	/**
	 * 
	 * @Title: departInventoryV 
	 * @Description: 查询出发货的派送货量－在库货
	 * @author yuyongxiang-134019-yuyongxiang@deppon.com
	 * @date 2014年3月19日 上午8:30:34 
	 * @param origOrgCode
	 * @param objectiveOrgCode
	 * @param forecastStartTime
	 * @param forecastEndTime
	 * @return
	 * @throws TfrBusinessException    设定文件 
	 * @return List<PathDetailEntity>    返回类型
	 */
	public List<PathDetailEntity> departInventoryV(String origOrgCode, String objectiveOrgCode, Date forecastStartTime, Date forecastEndTime) throws TfrBusinessException {
			
	//	queryStockWhitInStockByOrgCode
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("goodsAreaType", DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION);
		map.put(origOrg, origOrgCode);
		map.put(objective, objectiveOrgCode);
		return forecastQuantityJOBDao.queryStockWhitInStockByOrgCode(map);
	}

	/**
	 * 根据外场编码查询所有辐射营业部
	 * @author huyue
	 * @date 2012-11-30 下午5:08:16
	 */
	public List<String> getSalesDeptListByTransferCode(String transferCode) {
		LineEntity condition = new LineEntity();
		condition.setActive(FossConstants.ACTIVE);
		condition.setDestinationOrganizationCode(transferCode);
		condition.setLineSort(DictionaryValueConstants.BSE_LINE_SORT_SOURCE);
		List<LineEntity> lineList = lineService.queryLineListByCondition(condition);
		List<String> salesDeptCodeList = new ArrayList<String>();
		for (int i = 0; i < lineList.size(); i++) {
			salesDeptCodeList.add(lineList.get(i).getOrginalOrganizationCode());
		}
		Set<String> s = new HashSet<String>();
		s.addAll(salesDeptCodeList);
		List<String> t = new ArrayList<String>();
		//wwb 311396 2016年12月20日11:06:57 原先为
		//t.addAll(t)
		//不明其意，修改为下面
		t.addAll(s);
		return t;
	}

	/**
	 * 根据出发,到达部门判断发车计划类型
	 * @author huyue
	 * @date 2013-1-5 下午6:52:29
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.IForecastService#queryDepartureType(java.lang.String,
	 *      java.lang.String)
	 */
	public String queryDepartureType(String origOrgCode, String destOrgCode) throws TfrBusinessException {
		GoodsAreaEntity goodsAreaEntity = goodsAreaService.queryGoodsAreaByArriveRegionCode(origOrgCode, destOrgCode, null);
		// 如果找不到库区.则表示这个路线没有发车类型
		if (null == goodsAreaEntity) {
			return null;
		} else {
			// 否则返回发车类型
			return goodsAreaEntity.getGoodsAreaUsage();
		}
	}

	/**
	 * 生成导出文件名称
	 * @author huyue
	 * @date 2013-1-7 下午2:51:23
	 */
	public String encodeFileName(String fileName) throws TfrBusinessException {
		try {
			String returnStr;
			String agent = (String) ServletActionContext.getRequest().getHeader("USER-AGENT");
			if (agent != null && agent.indexOf("MSIE") == -1) {
				returnStr = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
			} else {
				returnStr = URLEncoder.encode(fileName, "UTF-8");
			}
			return returnStr;
		} catch (UnsupportedEncodingException e) {
			logger.error("转换文件名编码失败", e);
			throw new TfrBusinessException(StockException.EXPORT_FILE_ERROR_CODE, "");
		}
	}
	
	/**
	 * 导出预测数据到Excel
	 * @author huyue
	 * @date 2013-1-7 下午3:08:17
	 */
	public InputStream exportExcelStream(ForecastQuantityEntity forecastQuantityEntity) throws TfrBusinessException {
		InputStream excelStream = null;
		// 查询最新一批时间
		Date maxStatisticsTime = forecastQuantityDaoPlatform.selectMaxStatisticsTime(forecastQuantityEntity);
		forecastQuantityEntity.setStatisticsTime(maxStatisticsTime);
		List<ForecastQuantityEntity> list = forecastQuantityDaoPlatform.queryforecastQuantityList(forecastQuantityEntity);
		// 行List
		List<List<String>> rowList = new ArrayList<List<String>>();
		for (ForecastQuantityEntity forecastQuantityList : list) {
			// 每行的列List
			List<String> columnList = new ArrayList<String>();
			columnList.add(getNameByCode(forecastQuantityList.getBelongOrgCode()));
			if (null != siteGroupService.querySiteGroupByCode(forecastQuantityList.getRegion())) {
				/** 
				 * 赋值 
				 */
				columnList.add(siteGroupService.querySiteGroupByCode(forecastQuantityList.getRegion()).getName());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			/** 
			 * 赋值 
			 */
			columnList.add(getNameByCode(forecastQuantityList.getRelevantOrgCode()));
			if (null != forecastQuantityList.getWeightTotal()) {
				/** 
				 * 赋值 
				 */
				columnList.add(forecastQuantityList.getWeightTotal().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != forecastQuantityList.getVolumeTotal()) {
				/** 
				 * 赋值 
				 */
				columnList.add(forecastQuantityList.getVolumeTotal().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != forecastQuantityList.getWaybillQtyTotal()) {
				/** 
				 * 赋值 
				 */
				columnList.add(forecastQuantityList.getWaybillQtyTotal().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != forecastQuantityList.getGpsEnabledResWeightTotal()) {
				/** 
				 * 赋值 
				 */
				columnList.add(forecastQuantityList.getGpsEnabledResWeightTotal().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != forecastQuantityList.getGpsEnabledResVolumeTotal()) {
				/** 
				 * 赋值 
				 */
				columnList.add(forecastQuantityList.getGpsEnabledResVolumeTotal().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != forecastQuantityList.getGpsEnabledResQtyTotal()) {
				/** 
				 * 赋值 
				 */
				columnList.add(forecastQuantityList.getGpsEnabledResQtyTotal().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != forecastQuantityList.getPrecisionIfsWeightTotal()) {
				/** 
				 * 赋值 
				 */
				columnList.add(forecastQuantityList.getPrecisionIfsWeightTotal().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != forecastQuantityList.getPrecisionIfsVolumeTotal()) {
				/** 
				 * 赋值 
				 */
				columnList.add(forecastQuantityList.getPrecisionIfsVolumeTotal().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != forecastQuantityList.getPrecisionIfsQtyTotal()) {
				/** 
				 * 赋值 
				 */
				columnList.add(forecastQuantityList.getPrecisionIfsQtyTotal().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != forecastQuantityList.getExpressWeightTotal()) {
				/** 
				 * 赋值 
				 */
				columnList.add(forecastQuantityList.getExpressWeightTotal().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != forecastQuantityList.getExpressVolumeTotal()) {
				/** 
				 * 赋值 
				 */
				columnList.add(forecastQuantityList.getExpressVolumeTotal().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != forecastQuantityList.getExpressQtyTotal()) {
				/** 
				 * 赋值 
				 */
				columnList.add(forecastQuantityList.getExpressQtyTotal().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != forecastQuantityList.getInventoryWeightTotal()) {
				/** 
				 * 赋值 
				 */
				columnList.add(forecastQuantityList.getInventoryWeightTotal().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != forecastQuantityList.getInventoryVolumeTotal()) {
				/** 
				 * 赋值 
				 */
				columnList.add(forecastQuantityList.getInventoryVolumeTotal().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != forecastQuantityList.getInventoryQtyTotal()) {
				/** 
				 * 赋值 
				 */
				columnList.add(forecastQuantityList.getInventoryQtyTotal().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != forecastQuantityList.getBillingWeightTotal()) {
				/** 
				 * 赋值 
				 */
				columnList.add(forecastQuantityList.getBillingWeightTotal().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != forecastQuantityList.getBillingVolumeTotal()) {
				/** 
				 * 赋值 
				 */
				columnList.add(forecastQuantityList.getBillingVolumeTotal().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != forecastQuantityList.getBillingQtyTotal()) {
				/** 
				 * 赋值 
				 */
				columnList.add(forecastQuantityList.getBillingQtyTotal().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != forecastQuantityList.getIntransitWeightTotal()) {
				/** 
				 * 赋值 
				 */
				columnList.add(forecastQuantityList.getIntransitWeightTotal().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != forecastQuantityList.getIntransitVolumeTotal()) {
				/** 
				 * 赋值 
				 */
				columnList.add(forecastQuantityList.getIntransitVolumeTotal().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != forecastQuantityList.getIntransitQtyTotal()) {
				/** 
				 * 赋值 
				 */
				columnList.add(forecastQuantityList.getIntransitQtyTotal().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != forecastQuantityList.getDeviationVolume()) {
				/** 
				 * 赋值 
				 */
				columnList.add(forecastQuantityList.getDeviationVolume().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != forecastQuantityList.getStatisticsTime()) {
				/** 
				 * 赋值 
				 */
				columnList.add(DateUtils.convert(forecastQuantityList.getStatisticsTime(), DateUtils.DATE_TIME_FORMAT));
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			/** 
			 * 赋值 
			 */
			if(StringUtils.equals(forecastQuantityList.getType(),ForecastConstants.FORECAST_ARRIVE)){
				columnList.add("到达");
			}else{
				columnList.add("出发");
			}
			if (null != forecastQuantityList.getForecastTime()) {
				/** 
				 * 赋值 
				 */
				columnList.add(DateUtils.convert(forecastQuantityList.getForecastTime(), DateUtils.DATE_FORMAT));
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			rowList.add(columnList);
		}
		ExportResource forecastList = new ExportResource();
		/** 
		 * 赋值 
		 */
		forecastList.setHeads(TransportPathConstants.FORECAST_ROW_HEADS);
		/** 
		 * 赋值 
		 */
		forecastList.setRowList(rowList);
		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSize(TransportPathConstants.SHEET_SIZE);
		exportSetting.setSheetName(TransportPathConstants.FORECAST_SHEET_NAME);
		ExcelExporter objExcelExportor = new ExcelExporter();
		excelStream =  objExcelExportor.exportBySheet(forecastList, exportSetting);
		return excelStream;
	}

	/**
	 * 导出预测明细数据到Excel
	 * @author huyue
	 * @date 2013-1-8 下午2:07:28
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.IForecastService#detailExportExcelStream(java.lang.String)
	 */
	public InputStream detailExportExcelStream(String forecastQuantityId) throws TfrBusinessException {
		InputStream excelStream = null;
		// 获取开单信息
		BillingEntity billingEntity = new BillingEntity();
		billingEntity.setForecastQuantityId(forecastQuantityId);
		List<BillingEntity> billingList = billingDaoPlatform.querybillingList(billingEntity);
		// 行List
		List<List<String>> rowBillingList = new ArrayList<List<String>>();
		for (BillingEntity billing : billingList) {
			// 每行的列List
			List<String> columnList = new ArrayList<String>();
			/** 
			 * 赋值 
			 */
			columnList.add(getNameByCode(billing.getBelongOrgCode()));
			if (null != siteGroupService.querySiteGroupByCode(billing.getRegion())) {
				/** 
				 * 赋值 
				 */
				columnList.add(siteGroupService.querySiteGroupByCode(billing.getRegion()).getName());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			/** 
			 * 赋值 
			 */
			columnList.add(getNameByCode(billing.getRelevantOrgCode()));
			/** 
			 * 赋值 
			 */
			columnList.add(getNameByCode(billing.getBillingSalesDistrict()));
			/** 
			 * 赋值 
			 */
			columnList.add(getNameByCode(billing.getBillingSalesDepartment()));
			if (null != billing.getBillingWeight()) {
				/** 
				 * 赋值 
				 */
				columnList.add(billing.getBillingWeight().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != billing.getBillingVolume()) {
				/** 
				 * 赋值 
				 */
				columnList.add(billing.getBillingVolume().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != billing.getBillingQty()) {
				/** 
				 * 赋值 
				 */
				columnList.add(billing.getBillingQty().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != billing.getGpsEnabledResWeight()) {
				/** 
				 * 赋值 
				 */
				columnList.add(billing.getGpsEnabledResWeight().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != billing.getGpsEnabledResVolume()) {
				/** 
				 * 赋值 
				 */
				columnList.add(billing.getGpsEnabledResVolume().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != billing.getGpsEnabledResQty()) {
				/** 
				 * 赋值 
				 */
				columnList.add(billing.getGpsEnabledResQty().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != billing.getPrecisionIfsWeight()) {
				/** 
				 * 赋值 
				 */
				columnList.add(billing.getPrecisionIfsWeight().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != billing.getPrecisionIfsVolume()) {
				/** 
				 * 赋值 
				 */
				columnList.add(billing.getPrecisionIfsVolume().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != billing.getPrecisionIfsQty()) {
				/** 
				 * 赋值 
				 */
				columnList.add(billing.getPrecisionIfsQty().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != billing.getExpressWeight()) {
				/** 
				 * 赋值 
				 */
				columnList.add(billing.getExpressWeight().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != billing.getExpressVolume()) {
				/** 
				 * 赋值 
				 */
				columnList.add(billing.getExpressVolume().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != billing.getExpressQty()) {
				/** 
				 * 赋值 
				 */
				columnList.add(billing.getExpressQty().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != billing.getStatisticsTime()) {
				/** 
				 * 赋值 
				 */
				columnList.add(DateUtils.convert(billing.getStatisticsTime(), DateUtils.DATE_TIME_FORMAT));
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			/** 
			 * 赋值 
			 */
			if(StringUtils.equals(billing.getType(),ForecastConstants.FORECAST_ARRIVE)){
				columnList.add("到达");
			}else{
				columnList.add("出发");
			}
			/** 
			 * 赋值 
			 */
			rowBillingList.add(columnList);
		}
		ExportResource billing = new ExportResource();
		/** 
		 * 赋值 
		 */
		billing.setHeads(TransportPathConstants.BILLING_ROW_HEADS);
		/** 
		 * 赋值 
		 */
		if(CollectionUtils.isEmpty(rowBillingList)){
			rowBillingList.add(new ArrayList<String>(0));
		}
		billing.setRowList(rowBillingList);
		// 获取在途信息
		InTransitEntity inTransitEntity = new InTransitEntity();
		/** 
		 * 赋值 
		 */
		inTransitEntity.setForecastQuantityId(forecastQuantityId);
		List<InTransitEntity> inTransitList = inTransitDaoPlatform.queryinTransitList(inTransitEntity);
		// 行List
		List<List<String>> rowInTransitList = new ArrayList<List<String>>();
		for (InTransitEntity inTransit : inTransitList) {
			// 每行的列List
			List<String> columnList = new ArrayList<String>();
			/** 
			 * 赋值 
			 */
			columnList.add(getNameByCode(inTransit.getBelongOrgCode()));
			if (null != siteGroupService.querySiteGroupByCode(inTransit.getRegion())) {
				/** 
				 * 赋值 
				 */
				columnList.add(siteGroupService.querySiteGroupByCode(inTransit.getRegion()).getName());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			/** 
			 * 赋值 
			 */
			columnList.add(getNameByCode(inTransit.getRelevantOrgCode()));
			/** 
			 * 赋值 
			 */
			columnList.add(inTransit.getIntransitVehicleNo());
			if (null != inTransit.getIntransitWeight()) {
				/** 
				 * 赋值 
				 */
				columnList.add(inTransit.getIntransitWeight().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != inTransit.getIntransitVolume()) {
				/** 
				 * 赋值 
				 */
				columnList.add(inTransit.getIntransitVolume().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != inTransit.getIntransitQty()) {
				/** 
				 * 赋值 
				 */
				columnList.add(inTransit.getIntransitQty().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != inTransit.getGpsEnabledResWeight()) {
				/** 
				 * 赋值 
				 */
				columnList.add(inTransit.getGpsEnabledResWeight().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != inTransit.getGpsEnabledResVolume()) {
				/** 
				 * 赋值 
				 */
				columnList.add(inTransit.getGpsEnabledResVolume().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != inTransit.getGpsEnabledResQty()) {
				/** 
				 * 赋值 
				 */
				columnList.add(inTransit.getGpsEnabledResQty().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != inTransit.getPrecisionIfsWeight()) {
				/** 
				 * 赋值 
				 */
				columnList.add(inTransit.getPrecisionIfsWeight().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != inTransit.getPrecisionIfsVolume()) {
				/** 
				 * 赋值 
				 */
				columnList.add(inTransit.getPrecisionIfsVolume().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != inTransit.getPrecisionIfsQty()) {
				/** 
				 * 赋值 
				 */
				columnList.add(inTransit.getPrecisionIfsQty().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != inTransit.getExpressWeight()) {
				/** 
				 * 赋值 
				 */
				columnList.add(inTransit.getExpressWeight().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != inTransit.getExpressVolume()) {
				/** 
				 * 赋值 
				 */
				columnList.add(inTransit.getExpressVolume().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != inTransit.getExpressQty()) {
				/** 
				 * 赋值 
				 */
				columnList.add(inTransit.getExpressQty().toString());
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			if (null != inTransit.getStatisticsTime()) {
				/** 
				 * 赋值 
				 */
				columnList.add(DateUtils.convert(inTransit.getStatisticsTime(), DateUtils.DATE_TIME_FORMAT));
			} else {
				/** 
				 * 赋值 
				 */
				columnList.add(null);
			}
			/** 
			 * 赋值 
			 */
			if(StringUtils.equals(inTransit.getType(),ForecastConstants.FORECAST_ARRIVE)){
				columnList.add("到达");
			}else{
				columnList.add("出发");
			}
			/** 
			 * 赋值 
			 */
			rowInTransitList.add(columnList);
		}
		ExportResource inTransit = new ExportResource();
		/** 
		 * 赋值 
		 */
		inTransit.setHeads(TransportPathConstants.INTRANSIT_ROW_HEADS);
		/** 
		 * 赋值 
		 */
		
		if(CollectionUtils.isEmpty(rowInTransitList)){
			rowInTransitList.add(new ArrayList<String>(0));
		}
		inTransit.setRowList(rowInTransitList);
		ArrayList<ExportResource> exportResources = new ArrayList<ExportResource>();
		/** 
		 * 赋值 
		 */
		exportResources.add(billing);
		/** 
		 * 赋值 
		 */
		exportResources.add(inTransit);
		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSize(TransportPathConstants.SHEET_SIZE);
		ExcelExporter objExcelExportor = new ExcelExporter();
		excelStream = objExcelExportor.exportBySheet(exportResources, exportSetting, new String[] { TransportPathConstants.BILLING_SHEET_NAME, TransportPathConstants.INTRANSIT_SHEET_NAME });
		return excelStream;
	}

	/**
	 * 根据组织code查询缓存获取name
	 * @author huyue
	 * @date 2013-1-22 下午3:42:47
	 */
	public String getNameByCode(String orgCode) {
		String orgName = orgAdministrativeInfoService.queryCommonNameByCommonCodeFromCache(orgCode);
		if (StringUtils.isEmpty(orgName)) {
			//如果没有名称则返回code
			return orgCode;
		} else {
			//否则返回名称
			return orgName;
		}
	}
	
	/**
	 * 线路货量查询
	 * 
	 * 分5个 货物状态 每个状态都是一种 应用场合
	 * 
	 * @author yuyongxiang
	 * @date 2013年7月8日 15:37:29
	 * (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.IForecastService#queryStatisticalInquiries(com.deppon.foss.module.transfer.scheduling.api.shared.vo.ForecastVO)
	 */
	@Override
	@Transactional
	public ForecastVO queryStatisticalInquiries(ForecastVO forecastVO) {
		StatisticalInquiriesDto stDto = forecastVO.getStatisticalInquiriesDto();
		if(StringUtils.isEmpty(stDto.getNextLine())) {
			throw new TfrBusinessException("请选择线路!");
		}
		
//		LineEntity line = lineService.queryLineBySimpleCode(stDto.getNextLine());
//		if(line == null) {
//			throw new TfrBusinessException("线路" + stDto.getNextLine() + "不存在, 请联系相关部门确认!");
//		}
//		
//		if(!StringUtils.equals(stDto.getTransforCenterCode(), line.getOrginalOrganizationCode())) {
//			throw new TfrBusinessException("线路出发部门必须为当前部门!");
//		}
		Map<String, Object> queryMap=new HashMap<String, Object>();
		
		queryMap.put("start", forecastVO.getStart());
		queryMap.put("limit", forecastVO.getLimit());
		queryMap.put(origOrg, stDto.getTransforCenterCode());
		queryMap.put(objective, stDto.getNextLine());
		queryMap.put("wayBillNo", stDto.getWayBillNo());
		queryMap.put("startTime", stDto.getStartTime());
		queryMap.put("endTime", stDto.getEndTime());
		
		//设定运输性质
		List<String> strList=null;
		if("ALL".equals(stDto.getTransportModelCode())){
			//设定所有
			strList=new ArrayList<String>(PlatformConstants.SONAR_NUMBER_5);
			//strList.add(ForecastConstants.FORECAST_PLF);
			strList.add(ForecastConstants.FORECAST_FLF);
			strList.add(ForecastConstants.FORECAST_FSF);
			strList.add(ForecastConstants.FORECAST_LRF);
			strList.add(ForecastConstants.FORECAST_SRF);
		}else{
			//依靠前台选择(前台的有单独的数据元,修改的时候要把前后台两个地方一起修改)
			strList = new ArrayList<String>(1);
			strList.add(stDto.getTransportModelCode());
		}
		// 设置查询条件
		queryMap.put("strList", strList);
				
		/**
		 * 参数封装调整
		 */
		Map<String, String> map=new HashMap<String, String>(PlatformConstants.SONAR_NUMBER_10);
		//map.put(ForecastConstants.FORECAST_PLF, ForecastConstants.FORECAST_PLF_NAME);
		map.put(ForecastConstants.FORECAST_FLF, ForecastConstants.FORECAST_FLF_NAME);
		map.put(ForecastConstants.FORECAST_FSF, ForecastConstants.FORECAST_FSF_NAME);
		map.put(ForecastConstants.FORECAST_LRF, ForecastConstants.FORECAST_LRF_NAME);
		map.put(ForecastConstants.FORECAST_SRF, ForecastConstants.FORECAST_SRF_NAME);
		
		//map.put(ForecastConstants.FORECAST_PLF_NAME, ForecastConstants.FORECAST_PLF);
		map.put(ForecastConstants.FORECAST_FLF_NAME, ForecastConstants.FORECAST_FLF);
		map.put(ForecastConstants.FORECAST_FSF_NAME, ForecastConstants.FORECAST_FSF);
		map.put(ForecastConstants.FORECAST_LRF_NAME, ForecastConstants.FORECAST_LRF);
		map.put(ForecastConstants.FORECAST_SRF_NAME, ForecastConstants.FORECAST_SRF);
		
		

		if(ForecastConstants.FORECAST_NO_TRANSFER_BILLING.equals(stDto.getGoodStatus())) {
			/**1. 开单未交接 -> NOTRANSFERBILLING */
			queryMap.put(arriveOrLeave, TransportPathConstants.PATHDETAIL_STATUS_NOTLEAVE);
			
			queryMap.put("goodsStatus", ForecastConstants.FORECAST_NO_TRANSFER_BILLING_NAME);

			//根据运单号和运输性质查询运单详细
			List<StatisticalInquiriesEntity> tempList = forecastQuantityDaoPlatform.queryStatisticalInquiriesWithWayBillNo(queryMap);
			Map<String,Object> mapCount = forecastQuantityDaoPlatform.queryStatisticalInquiriesWithWayBillNoCount(queryMap);
			
//			//循环处理查询出来的数据
//			for(StatisticalInquiriesEntity  statisticalInquiries:tempList){
//				//根据code获取Name并且赋值
//				statisticalInquiries.setTransportModelName(map.get(statisticalInquiries.getTransportModelCode()));
//				//设定 货物状态
//				statisticalInquiries.setGoodStatus(ForecastConstants.FORECAST_NO_TRANSFER_BILLING_NAME);
//			}
			
			//临时变量赋值到展现层
			forecastVO.setStatisticalInquiriesEntityList(tempList);
			
			//赋值结果总数用于分页
			mapCount = this.objToString(mapCount);
			forecastVO.setTotalCount(String.valueOf(mapCount.get("WAYBILLNOCOUNT")));
			forecastVO.setWeightSum(String.valueOf(mapCount.get("WEIGHTSUM")));
			forecastVO.setVolumeSum(String.valueOf(mapCount.get("VOLUMESUM")));
			forecastVO.setCurrentVolumeSum(String.valueOf(mapCount.get("CURRENTVOLUMESUM")));
			forecastVO.setCurrentWeightSum(String.valueOf(mapCount.get("CURRENTWEIGHTSUM")));
			forecastVO.getStatisticalInquiriesDto().setGoodStatus(ForecastConstants.FORECAST_NO_TRANSFER_BILLING_NAME);
		}else if(ForecastConstants.FORECAST_NO_DEPARTURE_TRANSFER.equals(stDto.getGoodStatus())){
			/**2.交接未出发 ->  NODEPARTURETRANSFER */
			
			//设置查询条件
			List<String> handoverbillStateList = new ArrayList<String>(2);
			////20：已交接 //21：已配载 (集配交接单专属状态)
			handoverbillStateList.add(String.valueOf(LoadConstants.HANDOVERBILL_STATE_FORMAL_HANDOVER));
			handoverbillStateList.add(String.valueOf(LoadConstants.HANDOVERBILL_STATE_ALREADY_ASSEMBLE));

			//设置查询条件
			queryMap.put("handoverbillStateList", handoverbillStateList);
			queryMap.put("taskDetailStatus", "UNDEPART");
			queryMap.put("undepart", "Y");
			queryMap.put("ontheway", null);
			
			queryMap.put("goodsStatus", ForecastConstants.FORECAST_NO_DEPARTURE_TRANSFER_NAME);
			
			//根据交接单的状态查出所对应的运单
			List<StatisticalInquiriesEntity> statisticalInquiriesEntityList = forecastQuantityDaoPlatform.queryStatisticalInquiriesWithHandover(queryMap);
			
			Map<String,Object> mapCount  = forecastQuantityDaoPlatform.queryStatisticalInquiriesWithHandoverCount(queryMap);
			
//			//循环处理查询出来的数据
//			for(StatisticalInquiriesEntity  statisticalInquiries:statisticalInquiriesEntityList){
//				//根据code获取Name并且赋值
//				statisticalInquiries.setTransportModelName(map.get(statisticalInquiries.getTransportModelCode()));
//				//设定 货物状态
//				statisticalInquiries.setGoodStatus(ForecastConstants.FORECAST_NO_DEPARTURE_TRANSFER_NAME);
//			}
			
			//临时变量赋值到展现层
			forecastVO.setStatisticalInquiriesEntityList(statisticalInquiriesEntityList);
			mapCount = this.objToString(mapCount);
			forecastVO.setTotalCount(String.valueOf(mapCount.get("WAYBILLNOCOUNT")));
			forecastVO.setWeightSum(String.valueOf(mapCount.get("WEIGHTSUM")));
			forecastVO.setVolumeSum(String.valueOf(mapCount.get("VOLUMESUM")));
			forecastVO.setCurrentVolumeSum(String.valueOf(mapCount.get("CURRENTVOLUMESUM")));
			forecastVO.setCurrentWeightSum(String.valueOf(mapCount.get("CURRENTWEIGHTSUM")));
			forecastVO.getStatisticalInquiriesDto().setGoodStatus(ForecastConstants.FORECAST_NO_DEPARTURE_TRANSFER_NAME);
		}else if(ForecastConstants.FORECAST_IN_TRANSIT.equals(stDto.getGoodStatus())){
			/**3.在途 -> INTRANSIT */
			//设置查询条件
			List<String> handoverbillStateList = new ArrayList<String>(PlatformConstants.SONAR_NUMBER_3);
			////30 : 已出发
			handoverbillStateList.add(String.valueOf(LoadConstants.HANDOVERBILL_STATE_FORMAL_HANDOVER));
			handoverbillStateList.add(String.valueOf(LoadConstants.HANDOVERBILL_STATE_ALREADY_ASSEMBLE));
			handoverbillStateList.add(String.valueOf(LoadConstants.HANDOVERBILL_STATE_ALREADY_DEPART));
			
			//设置查询条件
			queryMap.put("handoverbillStateList", handoverbillStateList);
			queryMap.put("taskDetailStatus", "ONTHEWAY");
			queryMap.put("undepart", null);
			queryMap.put("ontheway", "Y");
			
			queryMap.put("goodsStatus", ForecastConstants.FORECAST_IN_TRANSIT_NAME);
			
			//根据交接单的状态查出所对应的运单
			List<StatisticalInquiriesEntity> statisticalInquiriesEntityList = forecastQuantityDaoPlatform.queryStatisticalInquiriesWithHandover(queryMap);
			
			Map<String,Object> mapCount = forecastQuantityDaoPlatform.queryStatisticalInquiriesWithHandoverCount(queryMap);
			
			//循环处理查询出来的数据
//			for(StatisticalInquiriesEntity  statisticalInquiries:statisticalInquiriesEntityList){
//				//根据code获取Name并且赋值
//				statisticalInquiries.setTransportModelName(map.get(statisticalInquiries.getTransportModelCode()));
//				//设定 货物状态
//				statisticalInquiries.setGoodStatus(ForecastConstants.FORECAST_IN_TRANSIT_NAME);
//			}
			
			//临时变量赋值到展现层
			forecastVO.setStatisticalInquiriesEntityList(statisticalInquiriesEntityList);
			mapCount = this.objToString(mapCount);
			forecastVO.setTotalCount(String.valueOf(mapCount.get("WAYBILLNOCOUNT")));
			forecastVO.setWeightSum(String.valueOf(mapCount.get("WEIGHTSUM")));
			forecastVO.setVolumeSum(String.valueOf(mapCount.get("VOLUMESUM")));
			forecastVO.setCurrentVolumeSum(String.valueOf(mapCount.get("CURRENTVOLUMESUM")));
			forecastVO.setCurrentWeightSum(String.valueOf(mapCount.get("CURRENTWEIGHTSUM")));
			forecastVO.getStatisticalInquiriesDto().setGoodStatus(ForecastConstants.FORECAST_IN_TRANSIT_NAME);
			
		}else if(ForecastConstants.FORECAST_UNLOADING_NOT_REACHED.equals(stDto.getGoodStatus())){
			/**4.到达未卸车 -> UNLOADINGNOTREACHED */
			
			queryMap.put("goodsStatus", ForecastConstants.FORECAST_UNLOADING_NOT_REACHED_NAME);
			
			//根据交接单的状态查出所对应的运单
			List<StatisticalInquiriesEntity> statisticalInquiriesEntityList = forecastQuantityDaoPlatform.queryStatisticalInquiriesWithUnloadingNotReach(queryMap);
			
			Map<String,Object> mapCount = forecastQuantityDaoPlatform.queryStatisticalInquiriesWithUnloadingNotReachCount(queryMap);
			
			
			//临时变量赋值到展现层
			forecastVO.setStatisticalInquiriesEntityList(statisticalInquiriesEntityList);
			mapCount = this.objToString(mapCount);
			forecastVO.setTotalCount(String.valueOf(mapCount.get("WAYBILLNOCOUNT")));
			forecastVO.setWeightSum(String.valueOf(mapCount.get("WEIGHTSUM")));
			forecastVO.setVolumeSum(String.valueOf(mapCount.get("VOLUMESUM")));
			forecastVO.setCurrentVolumeSum(String.valueOf(mapCount.get("CURRENTVOLUMESUM")));
			forecastVO.setCurrentWeightSum(String.valueOf(mapCount.get("CURRENTWEIGHTSUM")));
			forecastVO.getStatisticalInquiriesDto().setGoodStatus(ForecastConstants.FORECAST_UNLOADING_NOT_REACHED_NAME);
			
		}else if(ForecastConstants.FORECAST_IN_LIBRARY.equals(stDto.getGoodStatus())){
			/**5. 统计货量查询 在库 -> INLIBRARY*/
			
			queryMap.put("goodsStatus", ForecastConstants.FORECAST_IN_LIBRARY_NAME);
			
			//根据交接单的状态查出所对应的运单
			List<StatisticalInquiriesEntity> statisticalInquiriesEntityList = forecastQuantityDaoPlatform.queryStatisticalInquiriesWithInLibrary(queryMap);
			
			Map<String,Object> mapCount = forecastQuantityDaoPlatform.queryStatisticalInquiriesWithInLibraryCount(queryMap);
			
			//循环处理查询出来的数据
//			for(StatisticalInquiriesEntity  statisticalInquiries:statisticalInquiriesEntityList){
//				//根据code获取Name并且赋值
//				statisticalInquiries.setTransportModelName(map.get(statisticalInquiries.getTransportModelCode()));
//				//设定 货物状态
//				statisticalInquiries.setGoodStatus(ForecastConstants.FORECAST_IN_LIBRARY_NAME);
//			}
			
			//临时变量赋值到展现层
			forecastVO.setStatisticalInquiriesEntityList(statisticalInquiriesEntityList);
			mapCount = this.objToString(mapCount);
			forecastVO.setTotalCount(String.valueOf(mapCount.get("WAYBILLNOCOUNT")));
			forecastVO.setWeightSum(String.valueOf(mapCount.get("WEIGHTSUM")));
			forecastVO.setVolumeSum(String.valueOf(mapCount.get("VOLUMESUM")));
			forecastVO.setCurrentVolumeSum(String.valueOf(mapCount.get("CURRENTVOLUMESUM")));
			forecastVO.setCurrentWeightSum(String.valueOf(mapCount.get("CURRENTWEIGHTSUM")));
			forecastVO.getStatisticalInquiriesDto().setGoodStatus(ForecastConstants.FORECAST_IN_LIBRARY_NAME);
			
		}else{
			throw new TfrBusinessException("请选择正确的货物状态!");
		}

		return forecastVO;
	}
	
	/**
	 * 导出统计货量查询到Excel
	 * @author yuyongxiang
	 * @date 2013年7月10日 19:37:00
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.service.IForecastService#detailExportExcelStream(java.lang.String)
	 */
	@Override
	@Transactional
	public InputStream queryStatisticalInquiriesExcelStream(ForecastVO forecastVO) throws TfrBusinessException {
		ForecastVO forecast = queryStatisticalInquiries(forecastVO);
		if(CollectionUtils.isEmpty(forecast.getStatisticalInquiriesEntityList())) {
			throw new TfrBusinessException("无查询结果!");
		}
		InputStream excelStream = null;
		// 行List
		List<List<String>> rowList = new ArrayList<List<String>>();
		for (StatisticalInquiriesEntity statisticalInquiries : forecast.getStatisticalInquiriesEntityList()) {
			// 每行的列List
			List<String> columnList = new ArrayList<String>();
			columnList.add(statisticalInquiries.getNextLine());
			columnList.add(statisticalInquiries.getWayBillNo());
			columnList.add(statisticalInquiries.getGoodStatus());
			columnList.add(statisticalInquiries.getGoodQty());
			columnList.add(statisticalInquiries.getCurrentQty() == null ? "" : statisticalInquiries.getCurrentQty().toString());
			columnList.add(statisticalInquiries.getCurrentWeight() == null ? "" : statisticalInquiries.getCurrentWeight().toString());
			columnList.add(statisticalInquiries.getCurrentVolume() == null ? "" : statisticalInquiries.getCurrentVolume().toString());
			columnList.add(statisticalInquiries.getTransportModelName());
			Date billingTime = statisticalInquiries.getBillingTime();
			columnList.add(String.format("%1$tF %2$tT", billingTime, billingTime));
			columnList.add(statisticalInquiries.getVehicleassembleNo());
			columnList.add(statisticalInquiries.getVehicleNo());
			rowList.add(columnList);
		}
		ExportResource forecastList = new ExportResource();
		
		String[] rowHeads = { "下一线路", "运单号", "货物状态", "货物件数", "当前件数", "重量",
				"体积","运输性质","开单时间", "车次号", "车牌号"};// 定义表头
		forecastList.setHeads(rowHeads);
		/** 
		 * 赋值 
		 */
		forecastList.setRowList(rowList);
		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSize(TransportPathConstants.SHEET_SIZE);
		exportSetting.setSheetName(TransportPathConstants.FORECAST_SHEET_NAME);
		ExcelExporter objExcelExportor = new ExcelExporter();
		excelStream =  objExcelExportor.exportBySheet(forecastList, exportSetting);
		return excelStream;
	}
	
	/**
	 * 提供给派送率的接口
	 * 查询预计某日派送到达货量（票数、重量、体积）
	 * @author 163580
	 * @date 2014-3-26 下午3:14:10
	 * @param deptCodes 部门编号
	 * @param forecastDate 预测日期
	 * @return
	 * @see
	 */
	@Override
	public List<ForecastQuantityEntity> queryForecastQtyByRelevantOrgCode(List<String> deptCodes,
			Date forecastDate) {
		if(CollectionUtils.isEmpty(deptCodes)) {
			return null;
		}
		if(forecastDate == null) {
			return null;
		}
		forecastDate = DateUtils.getStartDatetime(forecastDate);
		return forecastQuantityDaoPlatform.queryForecastQtyByRelevantOrgCode(deptCodes, forecastDate);
	}
	
	/**
	 * 提供给仓库饱和度的接口
	 * 查询预计某日的操作货量(重量)
	 * 未来一天总货量预测值计算公式：   X = A * ((B + D) / (C + E)) * 100%
	 * ( X=未来一天货量预测值，A=前1日货量值，B=前6日货量值，C=前8日货量值，D=前13日货量值，E=前15日货量值)
	 * @author 163580
	 * @date 2014-4-4 上午9:55:56
	 * @param deptCodes 外场code
	 * @param forecastDate
	 * @return
	 * @see
	 */
	@Override
	public List<ForecastQuantityEntity> queryForecastWeightByRelevantOrgCode(List<String> deptCodes,
			Date forecastDate) {
		if(CollectionUtils.isEmpty(deptCodes)) {
			return null;
		}
		if(forecastDate == null) {
			return null;
		}
		
		
		List<ForecastQuantityEntity> forecastQuantitys = new ArrayList<ForecastQuantityEntity>();
		
		ForecastQuantityEntity forecastQuantityParam = new ForecastQuantityEntity();
		forecastQuantityParam.setStatisticsHHMM(DateUtils.convert(DateUtils.convert(DateUtils.convert(forecastDate, "HH"), "HH"), "HHmm"));
		forecastDate = DateUtils.getStartDatetime(forecastDate);
		//业务人员要求使用预测类型的数据
		forecastQuantityParam.setDataType(ForecastConstants.DATA_TYPE_ONE);
		
		for(String deptCode : deptCodes) {
			// 获取该部门对象
			OrgAdministrativeInfoEntity orgAdministrativeInfo = orgAdministrativeInfoService
					.queryOrgAdministrativeInfoByCode(deptCode);
			if(orgAdministrativeInfo == null || !FossConstants.YES.equals(orgAdministrativeInfo.getTransferCenter())) {
				//传入的部门code非外场则返回重量为0
				ForecastQuantityEntity forecastQuantity = new ForecastQuantityEntity();
				forecastQuantity.setRelevantOrgCode(deptCode);
				forecastQuantity.setWeightTotal(BigDecimal.ZERO);
				forecastQuantitys.add(forecastQuantity);
				continue;
			}
			
			forecastQuantityParam.setBelongOrgCode(deptCode);
			//A=前1日货量值
			Date forecastTimeTemp = DateUtils.addDayToDate(forecastDate, ForecastConstants.NUMBER_MINUS_1);
			forecastQuantityParam.setForecastTime(forecastTimeTemp);
			//预测发起日期为前一天的
			forecastQuantityParam.setStatisticsDate(DateUtils.addDayToDate(forecastTimeTemp, -1));
			ForecastQuantityEntity forecastQuantityA = forecastQuantityDaoPlatform.countForecastQtyOneDay(forecastQuantityParam);
			if(forecastQuantityA == null) {
				forecastQuantityA = ForecastQuantityEntity.newForecastQuantityEntity();
			}
			
			//B=前6日货量值
			forecastTimeTemp = DateUtils.addDayToDate(forecastDate, ForecastConstants.NUMBER_MINUS_6);
			forecastQuantityParam.setForecastTime(forecastTimeTemp);
			//预测发起日期为前一天的
			forecastQuantityParam.setStatisticsDate(DateUtils.addDayToDate(forecastTimeTemp, -1));
			ForecastQuantityEntity forecastQuantityB = forecastQuantityDaoPlatform.countForecastQtyOneDay(forecastQuantityParam);
			if(forecastQuantityB == null) {
				forecastQuantityB = ForecastQuantityEntity.newForecastQuantityEntity();
			}
			
			//C=前8日货量值
			forecastTimeTemp = DateUtils.addDayToDate(forecastDate, ForecastConstants.NUMBER_MINUS_8);
			forecastQuantityParam.setForecastTime(forecastTimeTemp);
			//预测发起日期为前一天的
			forecastQuantityParam.setStatisticsDate(DateUtils.addDayToDate(forecastTimeTemp, -1));
			ForecastQuantityEntity forecastQuantityC = forecastQuantityDaoPlatform.countForecastQtyOneDay(forecastQuantityParam);
			if(forecastQuantityC == null) {
				forecastQuantityC = ForecastQuantityEntity.newForecastQuantityEntity();
			}
			
			//D=前13日货量值
			forecastTimeTemp = DateUtils.addDayToDate(forecastDate, ForecastConstants.NUMBER_MINUS_13);
			forecastQuantityParam.setForecastTime(forecastTimeTemp);
			//预测发起日期为前一天的
			forecastQuantityParam.setStatisticsDate(DateUtils.addDayToDate(forecastTimeTemp, -1));
			ForecastQuantityEntity forecastQuantityD = forecastQuantityDaoPlatform.countForecastQtyOneDay(forecastQuantityParam);
			if(forecastQuantityD == null) {
				forecastQuantityD = ForecastQuantityEntity.newForecastQuantityEntity();
			}
			
			//E=前15日货量值
			forecastTimeTemp = DateUtils.addDayToDate(forecastDate, ForecastConstants.NUMBER_MINUS_15);
			forecastQuantityParam.setForecastTime(forecastTimeTemp);
			//预测发起日期为前一天的
			forecastQuantityParam.setStatisticsDate(DateUtils.addDayToDate(forecastTimeTemp, -1));
			ForecastQuantityEntity forecastQuantityE = forecastQuantityDaoPlatform.countForecastQtyOneDay(forecastQuantityParam);
			if(forecastQuantityE == null) {
				forecastQuantityE = ForecastQuantityEntity.newForecastQuantityEntity();
			}
			
			//未来一天总货量预测值计算公式：   X = A * ((B + D) / (C + E)) * 100%
			//( X=未来一天货量预测值，A=前1日货量值，B=前6日货量值，C=前8日货量值，D=前13日货量值，E=前15日货量值。
			//A、B、C、D、E数据均来至之前货量统计中保存的实际货量的统计值)
			ForecastQuantityEntity forecastQuantityX = forecastQuantityA.multiply((forecastQuantityB.add(forecastQuantityD)).divide(forecastQuantityC.add(forecastQuantityE)));
			forecastQuantityX.setBelongOrgCode(deptCode);
			forecastQuantitys.add(forecastQuantityX);
			
//			//如果为外场则遍历所辐射的营业部
//			未来一天操作货量 = 未来一天到达该外场货量+未来一天该外场出发货量+该外场辐射营业部过去一个月内对应星期X的出发货量平均值(以前逻辑)
//			List<NetGroupSiteDto> netGroupSites = lineService.querySourceLineListByTransferCode(deptCode, DictionaryValueConstants.BSE_LINE_TRANSTYPE_QIYUN);
//			if(CollectionUtils.isEmpty(netGroupSites)) {
//				netGroupSites = new ArrayList<NetGroupSiteDto>();
//				NetGroupSiteDto netGroupSiteDto = new NetGroupSiteDto();
//				//设一个不存在的Code
//				netGroupSiteDto.setCode("00X");
//				netGroupSites.add(netGroupSiteDto);
//			}
//			//查询出当前外场出发, 到达当前外场, 当前外场辐射的营业部的四周均量
//			ForecastQuantityEntity forecastQuantity = forecastQuantityDaoPlatform.queryForecastWeightByRelevantOrgCode(deptCode, netGroupSites, forecastDate);
//			forecastQuantitys.add(forecastQuantity);
		}
		
		return forecastQuantitys;
	}
	
	/**
	 * 根据车次号获取当前运单的所有流水
	 * @author 163580
	 * @date 2014-4-3 上午10:23:07
	 * @param forecastVO
	 * @return 
	 * @see com.deppon.foss.module.transfer.platform.api.server.service.IForecastService#querySerialnoByVehicleassembleNo(com.deppon.foss.module.transfer.platform.api.shared.vo.ForecastVO)
	 */
	@Override
	public ForecastVO querySerialnoByVehicleassembleNo(ForecastVO forecastVO) {
		String goodsStatus = forecastVO.getStatisticalInquiriesDto().getGoodStatus();
		String vehicleassembleNo = forecastVO.getStatisticalInquiriesDto().getVehicleassembleNo();
		String wayBillNo = forecastVO.getStatisticalInquiriesDto().getWayBillNo();
		String deptCode = forecastVO.getStatisticalInquiriesDto().getTransforCenterCode();
		String nextLine = forecastVO.getStatisticalInquiriesDto().getNextLine();
		List<SerialnoDto> serialnos = new ArrayList<SerialnoDto>();
		if(ForecastConstants.FORECAST_IN_LIBRARY_NAME.equals(goodsStatus)) {
			//如果为在库
			serialnos = forecastQuantityDaoPlatform.querySerialnoByStock(deptCode, wayBillNo);
		} else if(ForecastConstants.FORECAST_NO_TRANSFER_BILLING_NAME.equals(goodsStatus)) {
			//如果为开单未交接
			serialnos = forecastQuantityDaoPlatform.querySerialnoByPathDetail(nextLine, wayBillNo);
			if(serialnos.size() == 1) {
				SerialnoDto serialnoDto = serialnos.get(0);
				//如果走货路径表只有一条信息, 并且流水号是空的, 说明是一次性全部走货
				//此时在运单货签表中查询出该运单的所有流水
				if(StringUtils.isEmpty(serialnoDto.getSerialno())) {
					serialnos = forecastQuantityDaoPlatform.querySerialnoByLabeledGoods(wayBillNo);
					//如果此流水号为空说明该运单确实没有流水(待补录运单无流水)
				}
			}
		} else {
			//其他三种(交接未出发, 在途, 到达未卸车)
			//根据车次号获取当前运单的所有流水
			serialnos = forecastQuantityDaoPlatform.querySerialnoByVehicleassembleNo(deptCode, vehicleassembleNo, wayBillNo);
		}
		forecastVO.setSerialnos(serialnos);
		return forecastVO;
	}
	
	/**
	 * 
	 * 处理map中的value==null 的时候转化为0
	 * 
	 * 
	 * @author 134019-foss-yuyongxiang
	 * @date 2013-9-10 上午10:36:02
	 * @param map
	 * @return
	 */
	protected Map<String,Object> objToString(Map<String,Object> map){
		
		//创建一个新的临时map来存储新的对象
		Map<String,Object> mapTemp=new HashMap<String, Object>(PlatformConstants.SONAR_NUMBER_3);
		
		mapTemp.put("WAYBILLNOCOUNT", String.valueOf(map.get("WAYBILLNOCOUNT")));
		
		//
		if(null == map.get("WEIGHTSUM") ||"null".equalsIgnoreCase(map.get("WEIGHTSUM").toString())){
			mapTemp.put("WEIGHTSUM",String.valueOf(0));
		}else{
			mapTemp.put("WEIGHTSUM", String.valueOf(map.get("WEIGHTSUM")));
		}

		//
		if(null == map.get("CURRENTWEIGHTSUM") ||"null".equalsIgnoreCase(map.get("CURRENTWEIGHTSUM").toString())){
			mapTemp.put("CURRENTWEIGHTSUM",String.valueOf(0));
		}else{
			mapTemp.put("CURRENTWEIGHTSUM", String.valueOf(map.get("CURRENTWEIGHTSUM")));
		}
		
		
		//
		if(null == map.get("VOLUMESUM") ||"null".equalsIgnoreCase(map.get("VOLUMESUM").toString())){
			mapTemp.put("VOLUMESUM", String.valueOf(0));
		}else{
			mapTemp.put("VOLUMESUM", String.valueOf(map.get("VOLUMESUM")));
		}

		//
		if(null == map.get("CURRENTVOLUMESUM") ||"null".equalsIgnoreCase(map.get("CURRENTVOLUMESUM").toString())){
			mapTemp.put("CURRENTVOLUMESUM", String.valueOf(0));
		}else{
			mapTemp.put("CURRENTVOLUMESUM", String.valueOf(map.get("CURRENTVOLUMESUM")));
		}
		
		
		return mapTemp;
	}
	
	/**
	 * 票数为零时，清除重量和体积的数值
	 * @author 140222
	 * @param forecastQuantityEntity
	 * @date 2014-08-18
	 */
	protected void addForecastQuantityEntityService(ForecastQuantityEntity forecastQuantityEntity){
		if(forecastQuantityEntity!=null){
			int totalSum =0;
			//总件数  1
			if(forecastQuantityEntity.getWaybillQtyTotal()!=null){
				if(forecastQuantityEntity.getWaybillQtyTotal().compareTo(BigDecimal.ZERO)==0){
					forecastQuantityEntity.setWeightTotal(BigDecimal.ZERO);
					forecastQuantityEntity.setVolumeTotal(BigDecimal.ZERO);
					totalSum++;
				}
			}
			
			//获取 卡航票数. 2
			if(forecastQuantityEntity.getGpsEnabledResQtyTotal()!=null){
				if(forecastQuantityEntity.getGpsEnabledResQtyTotal().compareTo(BigDecimal.ZERO)==0){
					forecastQuantityEntity.setGpsEnabledResWeightTotal(BigDecimal.ZERO);
					forecastQuantityEntity.setGpsEnabledResVolumeTotal(BigDecimal.ZERO);
					totalSum++;
				}
			}
			
			//获取 城运票数 3
			if(forecastQuantityEntity.getPrecisionIfsQtyTotal()!=null){
				if(forecastQuantityEntity.getGpsEnabledResQtyTotal().compareTo(BigDecimal.ZERO)==0){
					forecastQuantityEntity.setPrecisionIfsWeightTotal(BigDecimal.ZERO);
					forecastQuantityEntity.setPrecisionIfsVolumeTotal(BigDecimal.ZERO);
					totalSum++;
				}
			}
			
			//4
			if(forecastQuantityEntity.getExpressQtyTotal()!=null){
				if(forecastQuantityEntity.getGpsEnabledResQtyTotal().compareTo(BigDecimal.ZERO)==0){
					forecastQuantityEntity.setExpressWeightTotal(BigDecimal.ZERO);
					forecastQuantityEntity.setExpressVolumeTotal(BigDecimal.ZERO);
					totalSum++;
				}
			}
			
			//获取 在库票数 5
			if(forecastQuantityEntity.getInventoryQtyTotal()!=null){
				if(forecastQuantityEntity.getGpsEnabledResQtyTotal().compareTo(BigDecimal.ZERO)==0){
					forecastQuantityEntity.setInventoryWeightTotal(BigDecimal.ZERO);
					forecastQuantityEntity.setInventoryVolumeTotal(BigDecimal.ZERO);
					totalSum++;
				}
			}
			
			//获取 开单票数 6
			if(forecastQuantityEntity.getBillingQtyTotal()!=null){
				if(forecastQuantityEntity.getGpsEnabledResQtyTotal().compareTo(BigDecimal.ZERO)==0){
					forecastQuantityEntity.setBillingWeightTotal(BigDecimal.ZERO);
					forecastQuantityEntity.setBillingVolumeTotal(BigDecimal.ZERO);
					totalSum++;
				}
			}
			
			//获取 在途票数 7
			if(forecastQuantityEntity.getIntransitQtyTotal()!=null){
				if(forecastQuantityEntity.getGpsEnabledResQtyTotal().compareTo(BigDecimal.ZERO)==0){
					forecastQuantityEntity.setIntransitWeightTotal(BigDecimal.ZERO);
					forecastQuantityEntity.setIntransitVolumeTotal(BigDecimal.ZERO);
					totalSum++;
				}
			}
			
			//7种票数总数
			if(totalSum<PlatformConstants.SONAR_NUMBER_7){
				forecastQuantityDaoPlatform.addforecastQuantity(forecastQuantityEntity);
			}else{//不做增加
				
			}
			
		}
	}
	
}