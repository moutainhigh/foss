/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-order
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/server/action/VehicleManageAction.java
 * 
 * FILE NAME        	: VehicleManageAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.server.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.order.api.server.service.IExpressWorkerStatusService;
import com.deppon.foss.module.pickup.order.api.server.service.IVehicleManageService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.BindingLeasedTruckDto;
import com.deppon.foss.module.pickup.order.api.shared.define.ActionMessageType;
import com.deppon.foss.module.pickup.order.api.shared.define.TruckConstants;
import com.deppon.foss.module.pickup.order.api.shared.dto.LeasedTruckDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckConditionDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckDtoWithCount;
import com.deppon.foss.module.pickup.order.api.shared.dto.VehicleActualSituationDto;
import com.deppon.foss.module.pickup.order.api.shared.util.ApiUtil;
import com.deppon.foss.module.pickup.order.api.shared.util.PropertyFactory;
import com.deppon.foss.module.pickup.order.api.shared.vo.VehicleVo;
import com.deppon.foss.util.define.FossConstants;


/**
 * 车辆相关操作的Action
 * @author 038590-foss-wanghui
 * @date 2012-10-30 上午11:34:26
 */
public class VehicleManageAction extends AbstractAction {

	// 序列id
	private static final long serialVersionUID = 6425028494475504474L;
	
	// 车辆管理service
	private IVehicleManageService vehicleManageService;
	// 定义Vo
	private VehicleVo vehicleVo = new VehicleVo();
	// 0常量定义
	private static final String ZERO = "0";
	// 参数配置
	private PropertyFactory propertyFactory;
	private IExpressWorkerStatusService expressWorkerStatusService;
	/**
	 * 修改车载信息.
	 * 
	 * @return the string
	 * @author 038590-foss-wanghui
	 * @date 2012-10-23 上午9:50:35
	 */
	@JSON
	public String modifyVehicle(){
		try {
			vehicleManageService.modifyVehicle(vehicleVo
					.getSituationDto());
			return returnSuccess(ActionMessageType.MODIFY_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 查询已用车辆.
	 * 
	 * @return the string
	 * @author 038590-foss-wanghui
	 * @date 2012-10-23 下午3:12:11
	 */
	@JSON
	public String queryUsedVehicle(){
		List<OwnTruckDto> ownTruckDtos = vehicleManageService.queryUsedVehicle(vehicleVo.getOwnTruckConditionDto());
		vehicleVo.setUsedVehicleDtos(ownTruckDtos);
		return returnSuccess();
	}
	
	/**
	 * 手动查询自有车辆.
	 * 
	 * @return the string
	 * @author 038590-foss-wanghui
	 * @date 2012-10-30 下午7:18:20
	 */
	@JSON
	public String queryOwnTruck(){
		OwnTruckDtoWithCount ownTruckDtos = vehicleManageService.queryOwnTruck(vehicleVo.getOwnTruckConditionDto(), start, limit);
		this.setTotalCount(ownTruckDtos.getCount());
		vehicleVo.setOwnTruckDtos(ownTruckDtos.getOwnTruckList());
		return returnSuccess();
	}
	
	/**
	 * 手动查询外请车辆.
	 * 
	 * @return the string
	 * @author 038590-foss-wanghui
	 * @date 2012-10-30 下午7:20:19
	 */
	@JSON
	public String queryLeasedTruck(){
		Long count = vehicleManageService.queryLeasedTruckCount(vehicleVo.getLeasedTruckConditionDto());
		this.setTotalCount(count);
		if(count > 0){
			List<LeasedTruckDto> leasedTruckDtos = vehicleManageService.queryLeasedTruck(vehicleVo.getLeasedTruckConditionDto(),start,limit);
			vehicleVo.setLeasedTruckDtos(leasedTruckDtos);
		}
		return returnSuccess();
	}

	/**
	 * 手动查询已绑定外请车信息
	 * @author liuxiangcheng 329757
	 * @data 2016年5月31日 上午11:51:19
	 * @return
	 */
	@JSON
	public String queryBundLeasedTruck(){
		long count = vehicleManageService.queryBundLeasedTruckCount(vehicleVo.getBindingLeasedTruckDto());
		this.setTotalCount(count);
		if(count > 0){
			List<BindingLeasedTruckDto> bindingLeasedTruckDtos = vehicleManageService.queryBundLeasedTruck(vehicleVo.getBindingLeasedTruckDto(), start, limit);
			vehicleVo.setBindingLeasedTruckDtos(bindingLeasedTruckDtos);
		}
		return returnSuccess();
	}
	
	/**
	 * 根据车牌号查询对应的净空和剩余体积.
	 * 
	 * @return the string
	 * @author 038590-foss-wanghui
	 * @date 2012-12-7 下午12:00:07
	 */
	public String queryVolumeByVehicleNo(){
		try {
			OwnTruckDto ownTruckDto = vehicleManageService
					.queryVolumeByVehicleNo(vehicleVo.getOwnTruckConditionDto());
			vehicleVo.setOwnTruckDto(ownTruckDto);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
/**
	 * 根据车牌号拼接对应的GPS的URL地址.
	 * 
	 * @return the string
	 * @author 038590-foss-wanghui
	 * @date 2012-12-18 下午8:08:07
	 */
	public String queryGpsUrl(){
	//2014-03-13 区别短途车  长途车
		OwnTruckEntity entity=new  OwnTruckEntity();
		String vehicleNo=vehicleVo.getOwnTruckDto().getVehicleNo();
		entity.setVehicleNo(vehicleNo);
		entity=vehicleManageService.queryOwnVehicleBySelective(entity, null);
		String gpsUrl ="";
		if(entity.getUsedType().equals("used_type_pkp") || entity.getUsedType().equals("used_type_bus")){
			//短途gps
			VehicleActualSituationDto actualSituationDto = vehicleManageService.queryTaskByVehicleNo(vehicleVo.getOwnTruckDto().getVehicleNo());
			
			//已完成票数
			String alreadyDeliverGoodsQty=""+(actualSituationDto.getAlreadyDeliverGoodsQty()!= null ? actualSituationDto.getAlreadyDeliverGoodsQty() : Integer.valueOf(ZERO))+(actualSituationDto.getAlreadyPickupGoodsQty() != null ? actualSituationDto.getAlreadyPickupGoodsQty() : Integer.valueOf(ZERO));
			
			//未完成票数
			String noneDeliverGoodsQty=""+(actualSituationDto.getNoneDeliverGoodsQty() != null ? actualSituationDto.getNoneDeliverGoodsQty() : Integer.valueOf(ZERO))+(actualSituationDto.getNonePickupGoodsQty() != null ? actualSituationDto.getNonePickupGoodsQty() : Integer.valueOf(ZERO));
		
			String params="/vehtrack/fossToTracking.action?vehicleNo="+VehicleManageAction.string2Unicode(vehicleVo.getOwnTruckDto().getVehicleNo());
			params=params+","+actualSituationDto.getRemainingVolume();
			params=params+","+actualSituationDto.getRemainingWeight()+"," +noneDeliverGoodsQty
					+","+alreadyDeliverGoodsQty;
			//gpsUrl=URL+参数
			gpsUrl=propertyFactory.getShortGpsUrl()+params;
					
		}else{

			Map<String, String> map = new HashMap<String, String>();
			// 车牌号
			map.put("carnum", vehicleVo.getOwnTruckDto().getVehicleNo());
			VehicleActualSituationDto actualSituationDto = vehicleManageService.queryTaskByVehicleNo(vehicleVo.getOwnTruckDto().getVehicleNo());
			StringBuilder sb = new StringBuilder();
			sb.append("司机:");
			// 司机姓名
			sb.append(vehicleVo.getOwnTruckDto().getDriverName());
			sb.append("$");
			sb.append("已接票数:");
			// 已接票数
			sb.append(actualSituationDto.getAlreadyPickupGoodsQty() != null ? actualSituationDto.getAlreadyPickupGoodsQty() : ZERO);
			sb.append("$");
			sb.append("未接票数:");
			// 未接票数
			sb.append(actualSituationDto.getNonePickupGoodsQty() != null ? actualSituationDto.getNonePickupGoodsQty() : ZERO);
			sb.append("$");
			sb.append("已送票数:");
			// 已送票数
			sb.append(actualSituationDto.getAlreadyDeliverGoodsQty() != null ? actualSituationDto.getAlreadyDeliverGoodsQty() : ZERO);
			sb.append("$");
			sb.append("未送票数:");
			// 未送票数
			sb.append(actualSituationDto.getNoneDeliverGoodsQty() != null ? actualSituationDto.getNoneDeliverGoodsQty() : ZERO);
			// 页面显示信息
			map.put("message", sb.toString());
			// appkey
			map.put("app_key", propertyFactory.getAppKey());
			// 方法名
			map.put("method", propertyFactory.getMethod());
			// 调用工具类拼接URL
			 gpsUrl = ApiUtil.getUri(propertyFactory.getGpsUrl(), propertyFactory.getAppSecret(), map);
		}
		
		vehicleVo.setGpsUrl(gpsUrl);
		return returnSuccess();
	}
	
	
	/**
	 * 
	 * 根据车牌号查询司机信息
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-5-20 下午3:35:58
	 */
	public String querySchedule(){
		// 若排班（和处理订单中排班是同一个排班）中存在该车牌号，则用户在选择司机时自动带出对应的司机
		OwnTruckConditionDto ownTruckConditionDto = new OwnTruckConditionDto();
		// 排班类型--接送货
		ownTruckConditionDto.setSchedulingType(TruckConstants.SCHEDULE_TYPE_DELIVERY);
		// 排班状态--可用
		ownTruckConditionDto.setSchedulingStatus(FossConstants.ACTIVE);
		// 司机状态--工作
		ownTruckConditionDto.setSchedulingPlanType(TruckConstants.PLAN_TYPE_WORK);
		ownTruckConditionDto.setVehicleNo(vehicleVo.getOwnTruckConditionDto().getVehicleNo());
		ownTruckConditionDto.setActive(FossConstants.ACTIVE);
		OwnTruckDto ownTruckDto = vehicleManageService.queryTruckSchedulingByVehicleNo(ownTruckConditionDto);
		vehicleVo.setOwnTruckDto(ownTruckDto);
		return returnSuccess();
	}
	
	/**
	 * 查询车辆当前状态
	 * @author 195406-gcl
	 * @date 2014.7.24
	 */
	@JSON
	public String queryVehicleNoStatus(){
		try {
			String status=expressWorkerStatusService.queryVehicleNoStatus(vehicleVo.getOwnTruckConditionDto().getVehicleNo());
			vehicleVo.setGpsUrl(status);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * Sets the vehicleManageService.
	 * 
	 * @param vehicleManageService the vehicleManageService to see
	 */
	public void setVehicleManageService(IVehicleManageService vehicleManageService) {
		this.vehicleManageService = vehicleManageService;
	}

	public void setExpressWorkerStatusService(
			IExpressWorkerStatusService expressWorkerStatusService) {
		this.expressWorkerStatusService = expressWorkerStatusService;
	}

	/**
	 * Gets the vehicleVo.
	 * 
	 * @return the vehicleVo
	 */
	public VehicleVo getVehicleVo() {
		return vehicleVo;
	}

	/**
	 * Sets the vehicleVo.
	 * 
	 * @param vehicleVo the vehicleVo to see
	 */
	public void setVehicleVo(VehicleVo vehicleVo) {
		this.vehicleVo = vehicleVo;
	}

	/**
	 * Sets the propertyFactory.
	 * 
	 * @param propertyFactory the propertyFactory to see
	 */
	public void setPropertyFactory(PropertyFactory propertyFactory) {
		this.propertyFactory = propertyFactory;
	}
	
	static String string2Unicode(String str) { 
	     String result="";  
	        for (int i = 0; i < str.length(); i++){  
	            int chr1 = (char) str.charAt(i);  
	            if(chr1>=19968&&chr1<=171941){//汉字范围 \u4e00-\u9fa5 (中文)  
	                result+="|u" + Integer.toHexString(chr1);  
	            }else{  
	                result+=str.charAt(i);  
	            }  
	        }  
	        return result; 
	      }
	
}