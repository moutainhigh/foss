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
 *  FILE PATH          :/AirQueryModifyTfrPickupBillAction.java
 * 
 *  FILE NAME          :AirQueryModifyTfrPickupBillAction.java
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

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.transfer.airfreight.api.server.service.IAirQueryModifyTfrPickupBillService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTransPickupDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTransPickupbillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTransPickupBillDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.vo.AirTransPickupBillVo;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;

/**
 * 查询_修改中转提货清单action.
 * @author 099197-foss-zhoudejun
 * @date 2012-12-19 下午4:45:15
 * 
 */
public class AirQueryModifyTfrPickupBillAction extends AbstractAction {
	
	private static final long serialVersionUID = 2945980127872560968L;

	/** 中转提货清单vo*/
	private AirTransPickupBillVo airTransPickupBillVo = new AirTransPickupBillVo();
	
	/** 查询_修改中转提货清单 service*/
	private IAirQueryModifyTfrPickupBillService airQueryModifyTfrPickupBillService;
	
	/**
	 * 根据空运总调、到达网点、制单时间、中转单号、目的站、中转航班
	 * 查询中转提货清单信息.
	 * @return 中转提货清单list
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-19 下午4:54:56
	 */
	@JSON
	public String queryTfrPickupBill() {
		//查询条件
		AirTransPickupBillDto airTransPickupBillDto = airTransPickupBillVo.getAirTransPickupBillDto();
		// 查询中转提货清单信息
		List<AirTransPickupbillEntity> queryTfrPickupBill = airQueryModifyTfrPickupBillService.queryTfrPickupBill(airTransPickupBillDto,this.getLimit(), this.getStart());
		//设置返回结果
		airTransPickupBillVo.setAirTransPickupbillList(queryTfrPickupBill);
		//TODO总大小
		Long totalCount = Long.valueOf(queryTfrPickupBill.size());
		//总数
		this.setTotalCount(totalCount);
		return super.returnSuccess(); 
	}

	/**
	 * 根据中转提货清单id查询明细.
	 * @return the string
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-20 下午1:07:59
	 */
	@JSON
	public String queryTfrPickupBillDetail(){
		//中转提货清单id
		String airTransferPickUpBillId = airTransPickupBillVo.getAirTransPickupBillDto().getAirTransferPickUpBillId();
		//查询中转提货清单明细
		List<AirTransPickupDetailEntity> airTransPickupDetailList =  airQueryModifyTfrPickupBillService.queryTfrPickupBillDetail(airTransferPickUpBillId);
		//设置结果
		airTransPickupBillVo.setAirTransPickupDetailList(airTransPickupDetailList);
		return super.returnSuccess();
	}
	
	/**
	 * 根据运单号查询同一(目的站、到达网点、中转航班、中转日期).
	 * @return the string
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-20 下午4:04:13
	 */
	@JSON
	public String queryToAddTfrPickupBillDetail(){
		//查询条件
		AirTransPickupBillDto airTransPickupBillDto = airTransPickupBillVo.getAirTransPickupBillDto();
		//查询明细
		List<AirPickupbillDetailEntity> airPickupbillDetailList = airQueryModifyTfrPickupBillService.queryToAddTfrPickupBillDetail(airTransPickupBillDto);
		airTransPickupBillVo.setAirPickupbillDetailList(airPickupbillDetailList);
		return super.returnSuccess();
	}
	
	/**
	 * 修改、保存、新增(中转提货清单明细、变更logger日志).
	 * @return the string
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-21 上午11:25:56
	 */
	@JSON
	public String modifyOrSaveTfrPickupBillDetail(){
		try{
			//修改列表
			List<AirTransPickupDetailEntity> modifyTransPickupDetailList = airTransPickupBillVo.getModifyTransPickupDetailList();
			//中转提货清单id
			String airTransferPickUpBillId = airTransPickupBillVo.getAirTransferPickUpBillId();
			//修改、保存、新增
			airQueryModifyTfrPickupBillService.modifyOrSaveTfrPickupBillDetail(modifyTransPickupDetailList,
					airTransferPickUpBillId);
		} catch (BusinessException e) {
			//返回异常信息
			return returnError(e);
		}
		return super.returnSuccess();
	}
	
	/**
	 * 根据中转提货清单id删除中转提货清单
	 * @return the string
	 * @author 105795-foss-wqh
	 * @date 2014-01-13 
	 */
	@JSON
	public String deleteTfrAirPickupbillById(){
		//中转提货清单id
		String airTransferPickUpBillId = airTransPickupBillVo.getAirTransPickupBillDto().getAirTransferPickUpBillId();
		try {
			airQueryModifyTfrPickupBillService.deleteTfrAirPickupbillById(airTransferPickUpBillId);
			
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
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
	 * 设置 查询_修改中转提货清单 service.
	 * @param airQueryModifyTfrPickupBillService the new 查询_修改中转提货清单 service
	 */
	public void setAirQueryModifyTfrPickupBillService(
			IAirQueryModifyTfrPickupBillService airQueryModifyTfrPickupBillService) {
		this.airQueryModifyTfrPickupBillService = airQueryModifyTfrPickupBillService;
	}
}