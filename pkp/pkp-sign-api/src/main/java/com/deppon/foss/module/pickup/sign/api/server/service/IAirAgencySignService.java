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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/server/service/IAirAgencySignService.java
 * 
 * FILE NAME        	: IAirAgencySignService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.server.service;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.AirAgencyQueryDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.BatchSignDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.ExternalBillInfoDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.SendElectronicInvoiceSystemDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.LineSignDto;

/**
 * 
 * 签收空运偏线货物service
 * 
 * @author foss-meiying
 * @date 2012-10-16 下午2:59:47
 * @since
 * @version
 */
public interface IAirAgencySignService extends IService {
	/**
	 * 
	 * 根据运单号查询运单基本信息
	 * 
	 * @author foss-meiying
	 * @date 2012-10-16 下午3:33:10
	 * @return
	 * @see
	 */
	WaybillDto queryByWaybillNo(String waybillNo);

	/**
	 * 
	 * 根据查询条件(单号，收货人，收货人电话，收货人手机,运输性质)查询空运运单
	 * 
	 * @author foss-meiying
	 * @date 2012-10-16 下午3:33:48
	 * @param entity
	 * @return
	 * @see
	 */
	List<AirAgencyQueryDto> queryAirInfobyParams(AirAgencyQueryDto entity);

	/**
	 * 录入签收信息
	 * 
	 * @author foss-meiying
	 * @date 2012-12-18 下午7:28:03
	 * @param entity
	 *            运单签收结果信息
	 * @param currentInfo
	 *            登录人信息
	 * @param dto
	 *            结算出库参数
	 * @param oldArriveNotoutGoodsQty
	 *            并发控制使用---签收前的到达未出库件数
	 * @return
	 * @see
	 */
	String addWaybillSignResult(WaybillSignResultEntity entity,
			CurrentInfo currentInfo, LineSignDto dto,Integer oldArriveNotoutGoodsQty);

	/**
	 * 根据运单查询偏线外发单信息
	 * 
	 * @author foss-meiying
	 * @date 2012-12-12 下午8:22:32
	 * @param waybillNo
	 * @return
	 * @see
	 */
	ExternalBillInfoDto queryExternalBillByWaybillNo(String waybillNo);
	/**
	 * 反签收（偏线和空运）接口
	 * @author foss-meiying
	 * @date 2013-1-23 下午4:56:09
	 * @param id
	 * @param currentInfo
	 * @see
	 */
	void reverseWaybillSignResult(String id,CurrentInfo currentInfo);
	/**
	 * 签收时执行的一系列操作
	 * @author foss-meiying
	 * @date 2013-3-13 下午4:32:26
	 * @param wayEntity
	 * 			运单签收结果
	 * @param currentInfo
	 * 			当前登录人信息
	 * @param dto
	 * 		 	签收确认收入服务使用DTO
	 * @param oldArriveNotoutGoodsQty
	 * 			签收前的到达未 出库件数
	 * @param waybill
	 * 			运单信息
	 * @return
	 * @see
	 */
	SendElectronicInvoiceSystemDto signOperat(WaybillSignResultEntity wayEntity,CurrentInfo currentInfo, LineSignDto dto,Integer oldArriveNotoutGoodsQty,WaybillEntity waybill);
    /**
     * 查询运单是否已经签收
     * @author foss-meiying
     * @date 2013-3-7 上午11:52:00
     * @param waybillNo
     * @return
     * @see
     */
    WaybillDto queryByWaybillNoIsSign(String waybillNo,String serialNo);

	/**
	 * 签收快递代理理运单
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-12 上午10:17:07
	 */
	String addExpressAgentSignResult(WaybillSignResultEntity wayEntity, CurrentInfo currentInfo, LineSignDto dto, Integer oldArriveNotoutGoodsQty);

	/**
	 * 提供给中转快递代理签收接口
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-13 下午5:40:40
	 */
	String addExpressAgentSignResultForTfr(WaybillSignResultEntity wayEntity);

	/**
	 * 导入签收信息名细
	 * 
	 * @author 026123-foss-lifengteng
	 * @return 
	 * @date 2013-8-16 下午4:39:41
	 */
	void importExpressSignedDetail(Workbook book);
	/**
	 * 导入查询
	 * @param book
	 * @return
	 */
	List<AirAgencyQueryDto> importQueryExpressSignedDetail(Workbook book);
	
	/**
	 * 根据运单快递代理递代理外发单信息，如果存在返回查询结果信息，如果不存在返回null
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-21 上午9:34:45
	 */
	ExternalBillInfoDto queryExpressExternalBillBNo(String waybillNo,String serialNo);

	/**
	 * 导入
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-13 下午5:40:40
	 */
	String addExpressAgentSignResultForImport(WaybillSignResultEntity wayEntity);
	/**
	 * @Description: 批量保存签收落地配运单
	 * @author meiying 159231  
	 * @date 2014-11-25 上午9:35:39
	 * @return String    
	 * @throws
	 */
	String batchAddExpressSign(List<BatchSignDto> batchSignDtos, CurrentInfo currentInfo);
	/**
	 * 
	 * 根据查询条件(单号，收货人，收货人电话，收货人手机,运输性质)查询空运运单
	 * 一次可以查询多个运单号
	 * @author foss-WeiXing
	 * @date 2015-01-15 下午3:33:48
	 * @param entity
	 * @return
	 * @see
	 */
	List<AirAgencyQueryDto> queryAirInfobyParamsWaybillNos(AirAgencyQueryDto entity);
	
	/**
	 * 发送短信
	 */
	boolean sendMess(CurrentInfo currentInfo,String customerSms,WaybillEntity waybill);
	/**
	 * 快递代理签收-发送短信
	 */
	String sendExpressMSG(WaybillEntity waybill,CurrentInfo currentinfo,WaybillSignResultEntity dto);
}