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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/server/service/ISignService.java
 * 
 * FILE NAME        	: ISignService.java
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
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignArriveSheetDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.ArriveSheetVo;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.StockDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.LineSignDto;

/**
 * 
 * 签收出库接口
 * @author foss-meiying
 * @date 2012-10-17 上午10:53:00
 * @since
 * @version
 */
public interface ISignService extends IService {

	/**
	 * 
	 * 根据条件分页查询到达联信息
	 * @author foss-meiying
	 * @date 2012-10-17 上午10:53:08
	 * @param dto
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	List<SignArriveSheetDto> queryArriveSheetInfoByParams(SignDto dto,
			int start, int limit);

	/**
	 * 
	 * 签收出库---根据条件分页查询到达联总数
	 * 
	 * @author foss-meiying
	 * @date 2012-10-18 下午2:39:53
	 * @param dto
	 * @return
	 * @see
	 */
	Long queryArriveSheetInfoCountByParams(SignDto dto);
	/**
	 * 提交录入的签收信息
	 * @author foss-meiying
	 * @date 2012-10-23 上午9:39:36
	 * @param list
	 * 		签收流水号集合
	 * @param entity
	 * 		到达联对象
	 * @param dto
	 * 		签收确认收入服务使用DTO
	 * @param currentInfo
	 * 		当前登录人信息
	 * @param orderNo
	 * 		订单号
	 * @return
	 * @see
	 */
	String addSign(List<SignDetailEntity> list, ArriveSheetEntity entity,LineSignDto dto,CurrentInfo currentInfo,String orderNo);

	/**
	 * 
	 * 查询签收出库流水号
	 * 
	 * @author foss-meiying
	 * @date 2012-10-19 下午6:45:19
	 * @param dto  (运单号,当前登录人的部门编码)
	 * @return
	 * @see
	 */
	List<StockDto> queryStock(SignDto dto);
	/**
	 * 根据条件查询到达联信息（总）
	 * @param dto
	 * @param start
	 * @param limet
	 * @return
	 */
	ArriveSheetVo queryArriveSheetByParams(SignDto dto,int start,int limet);

	/**
	 * 
	 * 更新提前找货的状态
	 * 
	 * @author foss-yuting
	 * @date 2014-11-24 下午6:45:19
	 * @param dto  (运单号,当前登录人的部门编码)
	 * @return
	 * @see
	 */
	String updateInadvanceGoodsBySign(SignDto signDto);
	
	/**
	 * 
	 * 根据到达联号和流水号查询出SignDetailEntity
	 * 
	 * @author foss-WeiXing
	 * @date 2014-12-27 下午6:45:19
	 * @param entity
	 * @return
	 * @see
	 */
	List<SignDetailEntity> signSituationByArrivesheetNoSerialNo(SignDetailEntity signDetailEntity);
	/**
	 * 处理异常查询库区
	 * @author 231438-foss-chenjunying
	 * @param SignDto
	 * @return List<StockDto>
	 * 2015-08-08
	 */
	List<StockDto> dealExceptionQueryStock(SignDto dto);
	/**
	 * 处理dop家装运单（先把数据从暂存表保存到签收日志记录表里，然后再删除暂存表里的数据）
	 * @param waybillNo
	 */
    void dealSignWaybill(String waybillNo,String situation);
    
	/**
	 * 合伙人零担根据条件查询到达联信息（包含入库时间）-239284
	 * @param dto
	 * @param start
	 * @param limet
	 * @return
	 */
	ArriveSheetVo queryPtpArriveSheetByParams(SignDto dto,int start,int limet);
    
	/**
	 * 
	 * 合伙人零担签收出库---根据条件分页查询到达联总数
	 * 
	 * @author foss-239284
	 * @param dto
	 * @return
	 * @see
	 */
	Long queryPtpArriveSheetInfoCountByParams(SignDto dto);
	
	/**
	 * 
	 * 合伙人零担根据条件分页查询到达联信息
	 * @author foss-239284
	 * @param dto
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	List<SignArriveSheetDto> queryPtpArriveSheetInfoByParams(SignDto dto,
			int start, int limit);
}