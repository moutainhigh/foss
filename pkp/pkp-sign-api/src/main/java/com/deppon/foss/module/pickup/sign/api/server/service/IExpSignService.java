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
import com.deppon.foss.module.pickup.sign.api.shared.dto.CzmSignDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.CzmSignListDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.StockDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.LineSignDto;

/**
 * 
 * 快递签收出库接口
 * @author foss-meiying
 * @date 2014-10-09 上午11:05:51
 * @since
 * @version
 */
public interface IExpSignService extends IService {

	/**
	 * 
	 * 根据条件分页查询到达联信息
	 * @author foss-meiying
	 * @date 2014-10-09 上午11:05:51
	 * @param dto
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	List<SignArriveSheetDto> queryArriveSheetInfoByParamsExp(SignDto dto,
			int start, int limit);

	/**
	 * 
	 * 签收出库---根据条件分页查询到达联总数
	 * 
	 * @author foss-meiying
	 * @date 2014-10-09 上午11:05:51
	 * @param dto
	 * @return
	 * @see
	 */
	Long queryArriveSheetInfoCountByParams(SignDto dto);
	/**
	 * 提交录入的签收信息
	 * @author foss-meiying
	 * @date 2014-10-09 上午11:05:51
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
	 * @date 2014-10-09 上午11:05:51
	 * @param dto  (运单号,当前登录人的部门编码)
	 * @return
	 * @see
	 */
	List<StockDto> queryStock(SignDto dto);
	/**
	 * 根据条件查询到达联信息（总）
	 * @date 2014-10-09 上午11:05:51
	 * @param dto
	 * @param start
	 * @param limet
	 * @return
	 */
	ArriveSheetVo queryArriveSheetByParams(SignDto dto,int start,int limet);
	/**
	 * 根据运单号查询子母件信息
	 * @author 231438-chenjunying
	 * @date 2015-08-24 下午15:35:12
	 * @return
	 * @see
	 */
	CzmSignDto queryCzmInfo(SignDto dto);
	/**
	 * 根据运单号集合查询子母件在库信息
	 * @author 231438-chenjunying
	 * @date 2015-09-02 上午9:35:12
	 * @return
	 * @see
	 */
	List<StockDto> queryCzmInStock(SignDto dto);
	/**
	 * 查询子母件签收限制配置参数的值
	 * @author 231438-chenjunying
	 * @date 2015-09-09 上午10:55:12
	 * @return
	 * @see
	 */
	String queryCzmSignLimit(String valueCode);
	/**
	 * 批量签收(子母件)
	 * @author 231438-chenjunying
	 * 2015-08-27 下午17:35:12
	 */
	String addBatchCzmSign(List<CzmSignListDto> czmSignList,ArriveSheetEntity entity,LineSignDto dto,CurrentInfo currentInfo,String orderNo);
	
	/**
     * 根据条件分页查询合伙人快递签收 到达联信息
     * @author gpz
     * @date 2016年1月21日
     * @param signDto
     * @param start
     * @param limit
     * @return ArriveSheetVo
     */
    ArriveSheetVo queryPtpExpressArriveSheetByParams(SignDto signDto, int start, int limit);
}