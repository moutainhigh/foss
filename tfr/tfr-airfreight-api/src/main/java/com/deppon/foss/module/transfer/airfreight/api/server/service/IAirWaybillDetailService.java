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
 *  PROJECT NAME  : tfr-airfreight-api
 *  
 *  package_name  : 
 *  
 *  FILE PATH          :/IAirWaybillDetailService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.server.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.AirlinesValueAddEntity;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.FlightPricePlanDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirSerialNoDetail;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillSerialNoEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirWaybillDetailDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.WaybillDetailDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.WaybillInfoForNoticeDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.WayBillAssembleDto;

/**
 * 分单合票
 * @author 099197-foss-zhoudejun
 * @date 2012-10-19 下午5:20:27
 */
public interface IAirWaybillDetailService extends IService {

	/**
	 * 查询空运货量明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-19 下午5:30:01
	 */
	List<AirWaybillDetailEntity> queryWaybillEntity(AirWaybillDetailDto ticketDto);

	/**
	 * 保存航空正单
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-22 下午2:52:46
	 */
	AirWaybillDetailDto addAirWaybillEntity(AirWaybillEntity billEntity,
			List<AirWaybillDetailEntity> waybillList, Map<String, List> delParameterMap, Map<String, List> parameter);

	/**
	 * 保存航空正单明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-22 下午2:53:35
	 */
	int addAirWaybillDetailEntity(
			List<AirWaybillDetailEntity> airWaybillDetailList);

	/**
	 * 保存航空正单流水明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-22 下午2:57:57
	 */
	int addAirWaybillSerialNoEntity(
			List<AirWaybillSerialNoEntity> airSerialNoDetailSerialno);

	/**
	 *  根据库存状态查询流水
	 * @author 099197-foss-zhoudejun
	 * @date 2013-3-8 下午3:24:33
	 */
	List<AirSerialNoDetail> queryStockAirSerialNoDetail(AirWaybillDetailDto dto);
	
	/**
	 * 查询待修改的正单流水明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-6 上午9:34:42
	 */
	@Deprecated
	AirWaybillDetailDto queryWithModifySerailNo (String waybillNo,String airWaybillDetailId);

	/**
	 * 生成合票号
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-31 下午4:13:26
	 */
	String getGenerationJointTicketNo();

	/**
	 * 批量打印运单明细
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-7 上午9:52:35
	 */
	List<AirWaybillDetailEntity> queryAirWaybillBatchPrint(String[] ids);

	/**
	 * 修改航空正单、明细、流水 
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-27 下午6:42:25
	 */
	AirWaybillDetailDto modifyAirWaybill(AirWaybillEntity billEntity,
			List<AirWaybillDetailEntity> airWaybillDetailEntity,
			Map<String,List> addParameterMap,Map<String,List> delParameterMap);
	
	/**
	 * 查询航空公司二字码
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-29 下午1:49:20
	 */
	List<AirlinesEntity> queryAllAirlines();
	
	/**
	 * 调用航空正单基础费率
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-30 下午4:29:20
	 */
	AirlinesValueAddEntity queryRate (String flightCode,
		    String loadOrgCode,String deptAirfieldCode,Date billDate);
	
	/**
	 * 调用系统参数
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-5 上午11:42:08
	 */
	List<ConfigurationParamsEntity> queryConfigurationParamsExactByEntity(int limit, int start);
	
	
	 /**
     * 根据以下条件确定唯一航空公司运价方案明细提供
     * @param flightCode 航班公司编码
     * @param loadOrgCode 配载部门编码
     * @param destDistictCode 到达站编码
     * @param goodsTypeCode 货物类型
     * @param billDate 当前日期 
     * @date 2012-10-31 下午2:36:28
     */
	FlightPricePlanDetailEntity queryFlightMinPriceRate(String flightNo , String flightCode,String loadOrgCode,String destDistictCode ,String goodsTypeCode,BigDecimal weight,Date billDate);
	
	/**
	 * 根据正单号查询运单明细list
	 * @param  airWaybillNo 正单号
	 * @return List<String> 运单号list
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-24 上午9:40:34
	 */
	List<String> queryWaybillNoList(String airWaybillNo);
	
	/**
	 * 根据正单号查询待打印的航空正单
	 * @param airWaybillNo 正单号
	 * @return 航空正单实体 
	 * @author 099197-foss-zhoudejun
	 * @date 2013-1-7 下午5:25:50
	 */
	AirWaybillEntity queryWidthPrintAirWaybill(String airWaybillNo);
	
	/**
	 * 根据运单号查询运单走货轨迹
	 * @param waybillNo 运单号
	 * @return WayBillAssembleDto
	 * @author 099197-foss-zhoudejun
	 * @date 2013-1-10 下午1:59:42
	 */
	List<WayBillAssembleDto> queryWaybillPath(String waybillNo);
	
	/**
	 * 制作唐翼制单
	 * @param airWaybillNo 正单号
	 * @author 099197-foss-zhoudejun
	 * @date 2013-3-15 下午2:07:04
	 */
	void saveTangYiAwb (String deptRegionCode,String arrvRegionCode,String airWaybillNo);
	
	/**
	 * 根据航空正单号查询所属运单的出发部门
	 * @param airWaybillNo 正单号
	 * @author liuzhaowei
	 * @date 2013-7-10 下午2:07:04
	 */
	List<WaybillInfoForNoticeDto> queryWaybillInfoByAirwaybillNo(String airWaybillNo);

	
	/*
	 * 根据运单号查询运单详细信息
	 * @ waybillNo 运单号
	 * @ author wqh
	 * @ date    2013-09-11
	 * */
	WaybillDetailDto queryWaybillDetailByWaybillNo(String waybillNo);
	
	/*
	 * 根据正单id查询运单明细
	 * @ airWaybillId id
	 * @ author wqh
	 * @ date    2013-10-27
	 * */
	List<String> queryWaybillNosByAirWaybillId(String airWaybillId);

    /**
     * 查询快递空运货量明细
     * @author:lianghaisheng
     * @date 2014-09-17
     * @return
     */
    List<AirWaybillDetailEntity> queryPackageEntity(AirWaybillDetailDto airWaybillDetailDto);
    /**
	 * 查询待修改的正单流水明细
	 * @author 335284
	 * @date 2016年8月4日 15:09:05
	 */
	AirWaybillDetailDto queryWithModifySerailNo(AirWaybillDetailDto ticketDto);


}