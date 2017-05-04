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
 *  PROJECT NAME  : tfr-airfreight
 *  
 * package_name  : 
 *  
 *  FILE PATH          :/AirQueryModifyPickupbillAction.java
 * 
 *  FILE NAME          :AirQueryModifyPickupbillAction.java
 *  
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.server.action;

import java.util.List;

import org.springframework.util.CollectionUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirQueryModifyPickupbillService;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirTransPickupBillService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTranDataCollectionEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTransPickupBillDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.vo.AirTransPickupBillVo;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;

/**
 * 修改合大票清单action.
 * @author 099197-foss-zhoudejun
 * @date 2012-12-17 下午2:48:19
 * 
 */
public class AirQueryModifyPickupbillAction extends AbstractAction {
	
	private static final long serialVersionUID = -4903235413946789027L;

	/** 制作中转提货清单service */
	private IAirTransPickupBillService airTransPickupBillService;
	
	/** 查询修改合大票service  */
	private IAirQueryModifyPickupbillService airQueryModifyPickupbillService;

	/** 中转提货清单vo */
	private AirTransPickupBillVo airTransPickupBillVo = new AirTransPickupBillVo();

	/**
	 * 根据航空公司,正单号查询航空正单明细.
	 * @return the string
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-12 下午2:20:29
	 */
	@JSON
	public String queryAirPickupbillDetailList() {
		//获取dto
		AirTransPickupBillDto airTransPickupBillDto = airTransPickupBillVo.getAirTransPickupBillDto();
		//根据dto中的正单号获取正单明细list
			List<AirWaybillDetailEntity> resultAirWaybillDetailList = airTransPickupBillService.queryAirWaybillDetailList(airTransPickupBillDto);
			//判断获取的正单明细list是否为空
			//如果不为空则往下执行
			if (!CollectionUtils.isEmpty(resultAirWaybillDetailList)) {
				//获取list中的第一条元素中的id
				//调用航空正单查询服务
				//返回查询结果
				AirWaybillEntity airWaybillEntity = airTransPickupBillService.queryAirWaybillReceiverInfo(resultAirWaybillDetailList.get(0).getAirWaybillId());
				//设置查询结果
				airTransPickupBillVo.setAirWaybillEntity(airWaybillEntity);
			}
			//设置航空正单明细list
			airTransPickupBillVo.setResultAirWaybillDetailList(resultAirWaybillDetailList);
			//返回成功
		return super.returnSuccess();
	}
	
	/**
	 * 修改、保存、删除 合大票清单.
	 * @return the string
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-18 上午9:00:19
	 */
	public String modifySaveDeleteAirPickupbillDetails(){
		try {
			//根据合大票id获取合大票清单信息
			AirTranDataCollectionEntity airTranDataCollectionEntity = airTransPickupBillVo.getAirTranDataCollectionEntity();
			//获取需要删除的合票list
			List<AirPickupbillDetailEntity> airPickupbillDetailList = airTransPickupBillVo.getAirPickupbillDetailList();
			
			airQueryModifyPickupbillService.modifySaveDeleteAirPickupbillDetails(airTranDataCollectionEntity,airPickupbillDetailList);
			//catch异常
		} catch (BusinessException e) {
			//返回异常信息
			return returnError(e);
		}
		//返回成功信息
		return super.returnSuccess();
	}
	
	/**
	 * 根据运单号查询运单是否可以修改（结算信息是否已审核，核销、运单是否已签收）
	 * @return the string
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-3 下午4:19:53
	 */
	@JSON
	public String checkIfCanModify() {
		try{
			//获取正单号
			String waybillNo = airTransPickupBillVo.getAirTransPickupBillDto().getWaybillNo();
			airQueryModifyPickupbillService.validateAirJointTicketDetail(waybillNo);
		} catch (BusinessException e) {
			//返回异常信息
			return returnError(e);
		}
		//返回成功
		return super.returnSuccess();
	}
	
	/**
	 * 删除合票清单
	 * @return the string
	 * @author foss-105795-wqh
	 * @date 2014-01-21 下午3:19:53
	 */
	@JSON
	public String deleteAirPickupbillById() {
		try{
			//获取正单号
			String airPickupbillId = airTransPickupBillVo.getAirPickupbillEntity().getId();
			airQueryModifyPickupbillService.deleteAirPickupAndDetailByAirPickupId(airPickupbillId);
		} catch (TfrBusinessException e) {
			//返回异常信息
			return returnError(e.getErrorCode());
		}
		//返回成功
		return super.returnSuccess();
	}
	
	
	/**
	 * 获取 中转提货清单vo.
	 * @return the 中转提货清单vo
	 */
	public AirTransPickupBillVo getAirTransPickupBillVo() {
		return airTransPickupBillVo;
	}
	
	/**
	 * 设置 中转提货清单vo.
	 * @param airTransPickupBillVo the new 中转提货清单vo
	 */
	public void setAirTransPickupBillVo(AirTransPickupBillVo airTransPickupBillVo) {
		this.airTransPickupBillVo = airTransPickupBillVo;
	}
	
	/**
	 * 设置 制作中转提货清单service.
	 * @param airTransPickupBillService the new 制作中转提货清单service
	 */
	public void setAirTransPickupBillService(
			IAirTransPickupBillService airTransPickupBillService) {
		this.airTransPickupBillService = airTransPickupBillService;
	}
	
	/**
	 * 设置 查询修改合大票service.
	 * @param airQueryModifyPickupbillService the new 查询修改合大票service
	 */
	public void setAirQueryModifyPickupbillService(
			IAirQueryModifyPickupbillService airQueryModifyPickupbillService) {
		this.airQueryModifyPickupbillService = airQueryModifyPickupbillService;
	}
}