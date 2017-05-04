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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/server/service/IContrabandSignService.java
 * 
 * FILE NAME        	: IContrabandSignService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.ContrabandInfoDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.LostCargoInfoDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.SendElectronicInvoiceSystemDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;

/**
 * 
 * 违禁品签收接口
 * @author foss-meiying
 * @date 2013-1-25 下午2:23:13
 * @since
 * @version
 */
public interface IContrabandSignService extends IService {
	/**
	 * 查询违禁品信息
	 * @author foss-meiying
	 * @date 2013-1-29 下午4:07:04
	 * @param waybillNo
	 * @param currentDeptcode
	 * @return
	 * @see
	 */
	 ContrabandInfoDto queryContrabandInfoByWaybillNo(String waybillNo,String currentDeptcode);
	 /**
	  * 提交违禁品/丢货签收信息
	  * @author foss-meiying
	  * @date 2013-2-18 下午5:11:40
	  * @param currentInfo
	  * @param contrabandInfoDto
	  * @see
	  */
	 SendElectronicInvoiceSystemDto addContrabandInfo(CurrentInfo currentInfo,ContrabandInfoDto contrabandInfoDto,Boolean flag);
	 /**
	  * 根据传入的运单号,入库时间起止查询满足条件的运单信息
	  * @author foss-meiying
	  * @date 2013-2-3 下午3:35:04
	  * @param dto
	  * @param start
	  * @param limit
	  * @return
	  * @see
	  */
	 List<LostCargoInfoDto> queryLostCargoInfoByCondition(LostCargoInfoDto dto,int start, int limit);
	 /**
	  * 根据传入的运单号,入库时间起止查询满足条件的运单信息总数
	  * @author foss-meiying
	  * @date 2013-2-3 下午3:35:19
	  * @param dto
	  * @return
	  * @see
	  */
	 Long queryLostCargoCountByCondition(LostCargoInfoDto dto);
	 /**
	  * 查询中转库存流水号
	  * @author foss-meiying
	  * @date 2013-2-18 下午4:18:19
	  * @param waybillNo
	  * @param currentDeptcode
	  * @return
	  * @see
	  */
	List<SignDetailEntity> queryOptStockSerinalNo(String waybillNo,String currentDeptcode);
	/**
	 * 根据传入的运单号，查询该运单是否有丢货、弃货和违禁品签收
	 * @author foss-meiying
	 * @date 2015-2-03 下午4:36:19
	 * @param waybillNo
	 * @return boolean
	 */
	boolean queryExcepSignResultByWaybillNo(String waybillNo);
	
	/**
	  * 提交违禁品/丢货签收信息
	  * @author foss-WeiXing
	  * @date 2015-2-6 下午5:11:40
	  * @param currentInfo
	  * @param contrabandInfoDto
	  * @see返货需求
	  */
	 void addContrabandInfoForReturnOrder(CurrentInfo currentInfo,ContrabandInfoDto contrabandInfoDto);
	 
	/**
	 * 根据传入的运单和异常出库类型<br/>
	 * 1 判断子母件是否全部丢货或全部弃货或全部违禁品,如果全部丢货或全部弃货或全部违禁品则调用结算红冲接口<br/>
	 * 2 子件有正常签收，最后一件是母件且母件丢货、弃货或者违禁品签收,则调用确认签收接口
	 * 
	 * @param entity 运单entity
	 * @param expType 异常出库类型
	 * @param currentInfo 当前登录人信息
	 */
	void handleAllExceptionGoods(WaybillEntity entity, String expType,
			CurrentInfo currentInfo);
}