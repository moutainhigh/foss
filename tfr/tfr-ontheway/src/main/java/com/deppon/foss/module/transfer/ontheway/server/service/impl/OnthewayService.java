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
 *  PROJECT NAME  : tfr-ontheway
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/ontheway/server/service/impl/OnthewayService.java
 *  
 *  FILE NAME          :OnthewayService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.ontheway.server.service.impl;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.module.base.baseinfo.api.server.service.IDepartureStandardService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILineService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DepartureStandardEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.arrival.api.shared.define.ArrivalConstant;
import com.deppon.foss.module.transfer.departure.api.handle.DepartureHandle;
import com.deppon.foss.module.transfer.departure.api.shared.define.DepartureConstant;
import com.deppon.foss.module.transfer.load.api.shared.define.TaskTruckConstant;
import com.deppon.foss.module.transfer.ontheway.api.server.dao.IOnthewayDao;
import com.deppon.foss.module.transfer.ontheway.api.server.service.IOnthewayService;
import com.deppon.foss.module.transfer.ontheway.api.shared.define.OnthewayConstant;
import com.deppon.foss.module.transfer.ontheway.api.shared.domain.ManualEntity;
import com.deppon.foss.module.transfer.ontheway.api.shared.domain.OnthewayEntity;
import com.deppon.foss.module.transfer.ontheway.api.shared.domain.QueryOnthewayEntity;
import com.deppon.foss.module.transfer.stock.api.shared.exception.StockException;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * GPS：
 * 系统默认显示出发部门或到达部门为本部门的本次所有在途任务车辆。
 * （已完成的车辆任务（车辆到达）不显示在界面中，可以通过时间条件查看历史信息）
 * 
 * 
 * 手动跟踪在途车辆界面，录入框录入的车辆信息，记录跟踪方式为手动跟踪；
 * 查询/跟踪在途车辆信息界面，数据来源GPS系统的，记录跟踪方式为GPS跟踪。
 *  查询界面数据显示规则：
 *  车辆状态：“已出发”状态的车辆，默认显示在待跟踪查询界面；
 *  车辆状态：“已到达”状态的车辆，默认不在待跟踪查询界面显示；
 *  待跟踪历史信息，只有在查看历史数据时，才显示。
 *  
 *  
 * 当前状态字段说明：
 * 静止：GPS10s钟获取一次车辆位置信息，假如位置不变，则记录为静止状态。
 * 运行：GPS获取的位移有速度变化记录为运行状态，例如车辆速度为5 Km/h；
 *  离线：GPS设备离线状态，记录车辆的状态为离线；
 *  事故车辆：GPS系统记录发生事故的车辆为“事故车辆”类型。
 *  以上字段：信息直接来源GPS系统，跟踪方式记录为GPS跟踪。
 *  
 *  
 *  手动跟踪在途车辆列表明细说明：
 *  查询结果显示列表：
 *  针对无GPS的长途任务车辆：
 *  系统4个小时检索一次，最后更新时间超过4小时的，都显示在“查询结果显示列表”列表中（需要手动跟踪）；
 *  针对有GPS的长途任务车辆：
 *  系统30分钟检索一次，最后更新时间超过30分钟的，都显示在“查询结果显示列表”列表中（例如，当前状态：离线）；
 *  针对GPS的短途班车及接送货车辆：
 *  系统30分钟检索一次，最后更新时间超过30分钟的，都显示在“查询结果显示列表”列表中（例如，当前状态为：离线）；
 *  无GPS的短途班车及接送货车辆：
 *  系统不检索此车辆，车辆也不要显示在“查询结果显示列表”列表中。
 *  系统检索时间：是按照车辆所处路段的最后一次更新时间算起。
 *  显示框：（待跟踪长途车辆：X条数）=待跟踪长途车辆明细条数一致。
 *  显示框：15分钟刷新一次。
 * 
 * 
 * 
 * 
 * 
 * 
 * 车辆在途的WEB实现
 * @author foss-liubinbin(for IBM)
 * @date 2012-12-24 下午7:06:45
 */
public class OnthewayService implements IOnthewayService
{

	/**
	 * 
	 * 定时任务：“待放行”状态的车辆n分钟（默认30分钟可配置）内放行有效，超过时间值则变为已失效状态
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-10-24 上午11:04:20
	 */
	@Override
	public List<OnthewayEntity> queryOnthewayGrid(
			QueryOnthewayEntity queryEntity, int limit, int start) {
		List<OnthewayEntity> list = onthewayDao.queryOnthewayGrid(queryEntity,
				limit, start);
		
		//DP-VMP-在途跟踪优化 zyx
		List<OnthewayEntity> unlateArrived = new ArrayList<OnthewayEntity>(list.size());
		//否则则需要过滤查询结果
		for(OnthewayEntity ontheway : list) {
			if(!StringUtils.equals(DepartureConstant.DEPART_ITEM_TYPE_LONG, ontheway.getBusinessType()) &&
					!StringUtils.equals(DepartureConstant.DEPART_ITEM_TYPE_SHORT, ontheway.getBusinessType())) {
				//其他类型则不需要处理
				continue;
			}
				
			String sourceCode = ontheway.getOrigOrgCode();
			String targetCode = ontheway.getDestOrgCode();
			Date planArriveTime = ontheway.getPlanArriveTime();
			Date actualDepartTime =  ontheway.getActualDepartTime();
			
			LineEntity condition = new LineEntity();
			condition.setOrginalOrganizationCode(sourceCode);
			condition.setDestinationOrganizationCode(targetCode);
			condition.setValid(FossConstants.YES);
			condition.setActive(FossConstants.ACTIVE);
			
			long aging = 0L;
			if(StringUtils.equals(DepartureConstant.DEPART_ITEM_TYPE_LONG, ontheway.getBusinessType())) {
				//长途
				//对于长途车辆，若车辆归属类型为“公司车”，理论运行时间取值为线路基础数据（运作到运作）中该车辆运行线路的卡车时效；
				//若车辆归属类型为“外请车”，理论运行时间取值为线路基础数据中该车辆运行线路的普车时效。
				// 车辆任务明细的出发、到达部门
				
				LineEntity line = lineService.queryLineEntityByCondition(sourceCode, targetCode);
				if(StringUtils.equals(ontheway.getVehicleOwnerType(), ComnConst.ASSETS_OWNERSHIP_TYPE_LEASED)) {
					//外请车取普车时效
					if(line!=null){
						aging = line.getCommonAging();
					}
				} else if (StringUtils.equals(ontheway.getVehicleOwnerType(), ComnConst.ASSETS_OWNERSHIP_TYPE_COMPANY)) {
					//公司车取卡车时效
					if(line!=null){
						aging = line.getFastAging();
					}
				}
			} else if (StringUtils.equals(DepartureConstant.DEPART_ITEM_TYPE_SHORT, ontheway.getBusinessType())) {
				//短途
				//对于短途车辆，理论运行时间取值为线路基础数据（始发线路）中该线路第一班次的准点到达时间与准点发车时间的差值；
				//若该条线路在线路基础资料中只有一条记录，则理论运行时间取该条记录中的第一班次的准点到达时间与准点发车时间的差值；
				//若该条线路在线路基础资料中有多条记录，则理论运行时间取该多条记录中运输类型为汽运的记录中第一班次的准点到达时间与准点发车时间的差值。
				
				// 根据两点查询线路
				List<LineEntity> lineList = lineService
						.querySimpleLineListByCondition(condition);
				LineEntity line = null;
				if(CollectionUtils.isNotEmpty(lineList)){
					for (int i = 0, size = lineList.size(); i < size; i++) {
						line = lineList.get(i);
						if (DictionaryValueConstants.BSE_LINE_TRANSTYPE_QIYUN.equals(line.getTransType())) {
							break;
						}
					}
				}
				
				if(line == null) {
					continue;
				}
				
				String lineVirtualCode = line.getVirtualCode();
				if (StringUtils.isNotEmpty(lineVirtualCode)) {
					// 根据线路虚拟编码获取对应发车标准
					List<DepartureStandardEntity> departStandards = departureStandardService
							.queryDepartureStandardListByLineVirtualCode(lineVirtualCode);
					if (CollectionUtils.isNotEmpty(departStandards)) {
						// 取第一班发车标准，根据到达出发时间，到达时间计算时效(千分之小时)
						DepartureStandardEntity departStandard = departStandards.get(0);

						DateFormat dateFormat = new SimpleDateFormat("HHmm");

						// 出发时间
						Date leaveTime = null;
						// 到达时间
						Date arriveTime = null;
						try {
							leaveTime = dateFormat.parse(departStandard
									.getLeaveTime());
							arriveTime = dateFormat.parse(departStandard
									.getArriveTime());
						} catch (ParseException e) {
							aging = 0;
						}

						// 天数
						Long arriveDay = departStandard.getArriveDay() == null ? 0L
								: departStandard.getArriveDay();
						// 时效(千分之小时)
						aging = (new BigDecimal(arriveTime.getTime()
								- leaveTime.getTime()).divide(new BigDecimal(
								60 * 60), 0, BigDecimal.ROUND_HALF_UP)).longValue()
								+ arriveDay * 24 * 1000;
					}
				}
			}
			
			long value = planArriveTime.getTime() - actualDepartTime.getTime() - aging * 3600;
			if(value > 0) {
				//在途车辆“预计晚到”定义：预计到达时间-实际出发时间-理论运行时间>0，则认为该车辆预计晚到
				ontheway.setPlanArrivalStatus(ArrivalConstant.DEPART_LATE_ARRIVALED);
			} else {
				unlateArrived.add(ontheway);
			}
		}
		
		//将未晚到的数据移除
		if(StringUtils.equals(queryEntity.getPlanArrivalStatus(), ArrivalConstant.DEPART_LATE_ARRIVALED)) {
			list.removeAll(unlateArrived);
		}
		//end 
		return list;

	}

	/**
	 * 
	 * 取得数量
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-26 上午9:43:28
	 */
	@Override
	public Long getCount(QueryOnthewayEntity queryEntity)
	{
		return onthewayDao.getCount(queryEntity);
	}

	/**
	 * 
	 * 待跟踪长途车辆
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-26 上午9:43:28
	 */
	@Override
	public Long getLongCount(QueryOnthewayEntity queryEntity)
	{
//		queryEntity = new QueryOnthewayEntity();
		//GPS测试是停止、事故车辆、离线的状态
		List<String> status = new ArrayList<String>();
		//停止
		status.add(OnthewayConstant.TRUCK_CURRENT_STATUS_STOP);
		//事故车辆
		status.add(OnthewayConstant.TRUCK_CURRENT_STATUS_ACCIDENTS);
		//离线
		status.add(OnthewayConstant.TRUCK_CURRENT_STATUS_OFFLINE);
		queryEntity.setBusinessType(DepartureConstant.DEPART_ITEM_TYPE_LONG);
		queryEntity.setCurrentStatuslist(status);
		return onthewayDao.getCount(queryEntity);
	}
	/**
	 * 
	 * 待跟踪长途车辆
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-26 上午9:43:28
	 */
	@Override
	public List<OnthewayEntity> getLongGrid(QueryOnthewayEntity queryEntity, int limit, int start)
	{
//		QueryOnthewayEntity queryEntity = new QueryOnthewayEntity();
		//GPS测试是停止、事故车辆、离线的状态
		List<String> status = new ArrayList<String>();
		//停止
		status.add(OnthewayConstant.TRUCK_CURRENT_STATUS_STOP);
		//事故车辆
		status.add(OnthewayConstant.TRUCK_CURRENT_STATUS_ACCIDENTS);
		//离线
		status.add(OnthewayConstant.TRUCK_CURRENT_STATUS_OFFLINE);
		queryEntity.setBusinessType(DepartureConstant.DEPART_ITEM_TYPE_LONG);
		queryEntity.setCurrentStatuslist(status);
//		queryEntity.setOrigOrgCode(entity.getOrigOrgCode());
//		queryEntity.setDestOrgCode(entity.getDestOrgCode());
//		queryEntity.setCurrentStatuslist(queryEntity.getCurrentOrgCodes());
		return onthewayDao.queryOnthewayGrid(queryEntity,limit,start);
	}

	/**
	 * 
	 * 短途GPS离线车辆
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-26 上午9:43:28
	 */
	@Override
	public Long getShortGPSCount(QueryOnthewayEntity queryEntity)
	{
//		queryEntity = new QueryOnthewayEntity();
		//业务类型
		queryEntity.setBusinessType(DepartureConstant.DEPART_ITEM_TYPE_SHORT);
		//跟踪类型
		queryEntity
				.setTrackingType(OnthewayConstant.ONTHEWAY_TRACKING_TYPE_GPS);
		//当前状态
		queryEntity
				.setCurrentStatus(OnthewayConstant.TRUCK_CURRENT_STATUS_OFFLINE);
		queryEntity.setOrigOrgCode(queryEntity.getOrigOrgCode());
		queryEntity.setDestOrgCode(queryEntity.getDestOrgCode());
		queryEntity.setCurrentStatuslist(queryEntity.getCurrentOrgCodes());
		return onthewayDao.getCount(queryEntity);
	}
	/**
	 * 
	 * 短途GPS离线车辆
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-26 上午9:43:28
	 */
	@Override
	public List<OnthewayEntity> getShortGPSGrid(QueryOnthewayEntity queryEntity, int limit, int start)
	{
//		queryEntity = new QueryOnthewayEntity();
		//业务类型
		queryEntity.setBusinessType(DepartureConstant.DEPART_ITEM_TYPE_SHORT);
		//跟踪类型
		queryEntity
				.setTrackingType(OnthewayConstant.ONTHEWAY_TRACKING_TYPE_GPS);
		//当前状态
		queryEntity
				.setCurrentStatus(OnthewayConstant.TRUCK_CURRENT_STATUS_OFFLINE);
		queryEntity.setOrigOrgCode(queryEntity.getOrigOrgCode());
		queryEntity.setDestOrgCode(queryEntity.getDestOrgCode());
		queryEntity.setCurrentStatuslist(queryEntity.getCurrentOrgCodes());
		return onthewayDao.queryOnthewayGrid(queryEntity,limit,start);
	}

	/**
	 * 
	 * 接送获GPS离线车辆
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-26 上午9:43:28
	 */
	@Override
	public Long getPkpGPSCount(QueryOnthewayEntity queryEntity)
	{
//		queryEntity = new QueryOnthewayEntity();
		//业务类型
		queryEntity.setBusinessType(DepartureConstant.DEPART_ITEM_TYPE_PKP);
		//跟踪类型
		queryEntity
				.setTrackingType(OnthewayConstant.ONTHEWAY_TRACKING_TYPE_GPS);
		//当前状态
		queryEntity
				.setCurrentStatus(OnthewayConstant.TRUCK_CURRENT_STATUS_OFFLINE);
		queryEntity.setOrigOrgCode(queryEntity.getOrigOrgCode());
		queryEntity.setDestOrgCode(queryEntity.getDestOrgCode());
		queryEntity.setCurrentStatuslist(queryEntity.getCurrentOrgCodes());
		return onthewayDao.getCount(queryEntity);
	}
	
	/**
	 * 
	 * 接送获GPS离线车辆
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-26 上午9:43:28
	 */
	@Override
	public List<OnthewayEntity> getPkpGPSGrid(QueryOnthewayEntity queryEntity, int limit, int start)
	{
//		queryEntity = new QueryOnthewayEntity();
		//业务类型
		queryEntity.setBusinessType(DepartureConstant.DEPART_ITEM_TYPE_PKP);
		//跟踪类型
		queryEntity
				.setTrackingType(OnthewayConstant.ONTHEWAY_TRACKING_TYPE_GPS);
		//当前状态
		queryEntity
				.setCurrentStatus(OnthewayConstant.TRUCK_CURRENT_STATUS_OFFLINE);
		queryEntity.setOrigOrgCode(queryEntity.getOrigOrgCode());
		queryEntity.setDestOrgCode(queryEntity.getDestOrgCode());
		queryEntity.setCurrentStatuslist(queryEntity.getCurrentOrgCodes());
		return onthewayDao.queryOnthewayGrid(queryEntity,limit,start);
	}

	/**
	 * 
	 * 增加一条在途信息
	 * @author foss-liubinbin(for IBM)
	 * @date 2012-12-26 上午9:43:00
	 */
	@Override
	@Transactional
	public void addManual(ManualEntity manualEntity)
	{
		// 将其他的修改成不是最新的状态
		manualEntity.setIsLatest(OnthewayConstant.IS_NOT_THE_LATEST_TRACKING);
		//更新状态
		onthewayDao.updateManualStatus(manualEntity);
		//ID
		manualEntity.setId(UUIDUtils.getUUID());
		//更新类型
		manualEntity
				.setTrackingType(OnthewayConstant.ONTHEWAY_TRACKING_TYPE_HANDLE);
		//跟踪人编码
		manualEntity.setTrackingUserCode(FossUserContext.getCurrentUser()
				.getEmployee().getEmpCode());
		//跟踪部门
		manualEntity.setTrackingOrgCode(FossUserContext.getCurrentDeptCode());
		//跟踪时间
		manualEntity.setTrackingTime(new Date());
		//是否最新
		manualEntity.setIsLatest(OnthewayConstant.IS_THE_LATEST_TRACKING);
		
		onthewayDao.addManual(manualEntity);
	}
	
	/** 
	 * @Title: encodeFileName 
	 * @Description: 将文件名转成UTF-8编码以防止乱码
	 * @param string
	 * @return    
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskQueryService#encodeFileName(java.lang.String)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-24下午4:14:37
	 */ 
	@Override
	public String encodeFileName(String fileName) {
		try {
			return URLEncoder.encode(fileName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new StockException("将文件名转成UTF-8编码时出错","");
		}
	}

	/** 
	 * @Title: exportLoadWayBillByTaskNo 
	 * @Description: 导出车辆出发到达明细
	 * @param taskNo
	 * @return    
	 * @see com.deppon.foss.module.transfer.unload.api.server.service.IUnloadTaskQueryService#exportLoadWayBillByTaskNo(java.lang.String)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-24下午4:14:37
	 */ 
	@Override
	public InputStream exportTruckDepartOrArriveByTaskNo(String ids) {
		InputStream excelStream = null;
		QueryOnthewayEntity queryEntity  =new QueryOnthewayEntity();
		ids = ids.replaceAll(" ", "");
		queryEntity.setIds(Arrays.asList(ids.split(",")));
		List<OnthewayEntity>  list = queryOnthewayGrid(queryEntity,Integer.MAX_VALUE,Integer.MIN_VALUE);
//		List<UnloadWaybillDetailDto> unloadWayBillDetails = unloadTaskQueryDao.queryUnloadTaskDetailByUnloadTaskNo(taskNo);
		List<List<String>> rowList = new ArrayList<List<String>>();
		for(OnthewayEntity onthewayEntity : list){
			//每行的列List
			List<String> columnList = new ArrayList<String>();
			//车牌号
			columnList.add(onthewayEntity.getVehicleNo());
			
			//当前状态
			if(OnthewayConstant.CURRENT_STATUS_MAP.get(onthewayEntity.getCurrentStatus())==null)
			{
				columnList.add("");
			}else{
				columnList.add(OnthewayConstant.CURRENT_STATUS_MAP.get(onthewayEntity.getCurrentStatus()));
			}
			
			//线路
			columnList.add(onthewayEntity.getLineName());
			
			//出发部门
			columnList.add(onthewayEntity.getOrigOrgName());
			
			//到达部门
			columnList.add(onthewayEntity.getDestOrgName());
			
			
			//出发时间
			columnList.add(DepartureHandle.getDateStr(onthewayEntity.getActualDepartTime()));
			
			//预计到达时间
			columnList.add(DepartureHandle.getDateStr(onthewayEntity.getPlanArriveTime()));
			
			//当前车辆所属路段
			columnList.add(onthewayEntity.getCurrentPlace());
			
			//最后一次跟踪时间
			columnList.add(DepartureHandle.getDateStr(onthewayEntity.getTrackingTime()));
			
			//车辆归属部门
			columnList.add(onthewayEntity.getVehicleOrgName());
			
			//跟踪方式
			if(OnthewayConstant.ONTHEWAY_TRACKING_TYPE_HANDLE.equals(onthewayEntity.getTrackingType()))
			{
				columnList.add("手工跟踪");
			}else if(OnthewayConstant.ONTHEWAY_TRACKING_TYPE_HANDLE.equals(onthewayEntity.getTrackingType()))
			{
				columnList.add("GPS跟踪");
			}else{
				columnList.add("");
			}
			
			//车辆状态
			if(TaskTruckConstant.TASK_TRUCK_STATE_ONTHEWAY.equals(onthewayEntity.getVehicleStatus()))
			{
				columnList.add("在途");
			}else if(TaskTruckConstant.TASK_TRUCK_STATE_ARRIVED.equals(onthewayEntity.getVehicleStatus()))
			{
				columnList.add("到达");
			}else if(TaskTruckConstant.TASK_TRUCK_STATE_UNLOADED.equals(onthewayEntity.getVehicleStatus()))
			{
				columnList.add("卸车");
			}else{
				columnList.add("");
			}
			
			//跟踪人
			columnList.add(onthewayEntity.getTrackingUserName());
			
			//备注
			columnList.add(onthewayEntity.getNotes());
			
			rowList.add(columnList);
		}
		String[] rowHeads = { "车牌号", "当前状态", "线路", "出发部门", "到达部门", "出发时间",
				"预计到达时间", "当前车辆所处路段", "最后一次跟踪时间", "车辆归属部门", "跟踪方式", "车辆状态",
				"跟踪人", "备注"};// 定义表头

		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName("车辆出发到达明细");
		exportSetting.setSize(5000);
		ExportResource exportResource  = new ExportResource();
		exportResource.setHeads(rowHeads);
		exportResource.setRowList(rowList);
		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		excelStream = objExporterExecutor.exportSync(exportResource, exportSetting);
        return excelStream;
	}
	
	/**发车标准service**/
	private IDepartureStandardService departureStandardService;
	
	public void setDepartureStandardService(
			IDepartureStandardService departureStandardService) {
		this.departureStandardService = departureStandardService;
	}

	/**
	 * ****************** 线路服务接口 *************************.
	 */
	private ILineService lineService;
	
	/**
	 * 设置 ******************线路服务接口******* ******************.
	 * 
	 * @param lineService
	 *            the new *************** ***线路服务接口 ************** ***********
	 */
	public void setLineService(ILineService lineService) {
		this.lineService = lineService;
	}

	/********************在途**************************/
	private IOnthewayDao onthewayDao;

	/**
	 * 设置 ******************在途*************************.
	 *
	 * @param onthewayDao the new ******************在途*************************
	 */
	public void setOnthewayDao(IOnthewayDao onthewayDao)
	{
		this.onthewayDao = onthewayDao;
	}

	@Override
	public List<OnthewayEntity> queryOnthewayGridById(String truckTaskDetailId)
	{
		return onthewayDao.queryOnthewayGridById(truckTaskDetailId);
	}

}