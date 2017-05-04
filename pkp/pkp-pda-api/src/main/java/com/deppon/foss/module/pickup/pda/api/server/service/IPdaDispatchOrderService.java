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
 * PROJECT NAME	: pkp-pda-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pda/api/server/service/IPdaDispatchOrderService.java
 * 
 * FILE NAME        	: IPdaDispatchOrderService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pda.api.server.service;

import java.util.List;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDispatchOrderDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaForwardDiverDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaOrderDto;
import com.deppon.foss.module.pickup.pda.api.shared.exception.PdaProcessException;
import com.deppon.foss.module.pickup.waybill.shared.domain.ReturnBillingEWaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.DispatchEWaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillCustomerDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExpressFeederPickupDetailDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExpressFeederPickupQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;

/**
 * 查询未接订单接口
 * @author ibm-wangfei
 * @date Dec 5, 2012 7:38:40 PM
 */
public interface IPdaDispatchOrderService extends IService {
	
	/**
	 * 
	 * 根据司机工号、车牌号未接订单接口接口
	 * @author ibm-wangfei
	 * @date Dec 5, 2012 7:40:00 PM
	 * @param driverCode 司机工号
	 * @param vehicleNo 车牌号
	 */
	List<PdaDispatchOrderDto> queryDispatchOrderByVehicle(String driverCode, String vehicleNo) throws PdaProcessException;

	/**
	 * 根据司机工号、车牌号查询大客户数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-7-30 16:54:08
	 */
	List<EWaybillCustomerDto> queryEWaybillOrderBigCust(EWaybillConditionDto eWaybillConditionDto);

	/**
	 * 根据客户编码查询电子运单数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-7-30 16:54:37
	 */
	List<DispatchEWaybillDto> queryEWaybillOrderByCust(EWaybillConditionDto eWaybillConditionDto);

	/**
	 * 查询散客电子运单数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-7-30 16:54:58
	 */
	List<DispatchEWaybillDto> queryIndividualCustEwaybill(EWaybillConditionDto eWaybillConditionDto);
	
	/**
	 * 
	 * @Title: queryForwardListByDriverCode 
	 * @Description: 根据当前登陆快递员工号查询对应区域下所有快递员集合
	 * @param @param driverCode
	 * @param @return    设定文件 
	 * @return List<PdaForwardDiverDto>    返回类型 
	 * @throws
	 */
	List<PdaForwardDiverDto> queryForwardListByDriverCode(String driverCode);
	/**
	 * 通过原单单号，获取打印信息
	 * @return
	 */
	ReturnBillingEWaybillEntity getReturnWaybillEntity(String originalWaybillNo,String changCode);
	
	/**
	 * 通过原单单号，获取返货信息
	 * @return
	 */
	ReturnBillingEWaybillEntity getReturnWaybillEntity(String originalWaybillNo);
	/**
	 * 根据上传的对应登陆的司机工号信息与填写的对应的快递员信息下拉对应快递员所有所需接货的数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-4-23 14:56:49
	 * @return
	 */
	List<ExpressFeederPickupDetailDto> getExpressFeederPickupDetail(ExpressFeederPickupQueryDto queryDto);
	
	/**
	 * 二程接驳-司机下拉明细后需要更新对应waybill_pending表中的是否完成接货状态
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-5-7 10:12:35
	 */
	ResultDto updatePickupDoneExpressFeederPickupDetail(List<String> waybillNoList);
	
	/**
	 * 二程接驳-校验司机所接快递员所在接驳点所属司机对应外场所管辖的接驳点下
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-5-26 17:23:57
	 * @param queryDto
	 * @return
	 */
	void validateExpressFeederUnderOutField(ExpressFeederPickupQueryDto queryDto);
	
	Long isExistPickUpDoneByWaybillNoList(ExpressFeederPickupQueryDto queryDto);
	
	/**
	 * PDA点击受理按钮,发送短信给客户
	 * @author FOS-219396-chengdaolin
	 * @date 2015-8-25 17:23:57
	 * @param pdaOrderDto 
	 * @return void
	 */
	boolean acceptOrder(PdaOrderDto pdaOrderDto);
}