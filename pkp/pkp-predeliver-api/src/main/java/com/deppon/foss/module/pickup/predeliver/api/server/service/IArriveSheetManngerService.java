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
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/server/service/IArriveSheetManngerService.java
 * 
 * FILE NAME        	: IArriveSheetManngerService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArrangeArriveSheetDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveSheetDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveSheetWaybillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArrivesheetDeliverDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AutoTaskDTO;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignArriveSheetDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.ArriveSheetMannerException;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.ArriveSheetVo;
import com.deppon.foss.module.pickup.waybill.shared.dto.ActualFreightDto;

/**
 * 到达联管理接口
 * @author dp-dengtingting
 *
 */
public interface IArriveSheetManngerService extends IService {
	
	/**
	 * 查询到达联服务
	 * @author dp-dengtingting
	 * @date 2012-10-13 上午11:03:43
	 */
	 List<ArriveSheetDto> queryArriveSheet(ArriveSheetDto dto,int start, int limit);
	
	/**
	 * 修改到达联
	 * @author dp-dengtingting
	 * @date 2012-10-13 下午5:25:25
	 */
	 int updateArriveSheetData(ArriveSheetEntity arriveSheetEntity);
	 
	 /**
	 * 激活到达联
	 * @author dp-dengtingting
	 * @throws Exception 
	 * @date 2012-10-13 下午5:25:25
	 */
	 int activateArriveSheet(ArriveSheetEntity arriveSheetEntity) throws ArriveSheetMannerException;
	
	/**
	 * 
	 * <p>签收出库---根据条件分页查询到达联</p> 
	 * @author foss-meiying
	 * @date 2012-10-17 下午4:02:44
	 */
	 List<SignArriveSheetDto> queryArriveSheetInfoByParams(SignDto dto,int start,int limit);
	
	/**
	 * 根据条件查询到达联总件数
	 * @author dp-dengtingting
	 * @date 2012-10-19 下午2:52:59
	 */
	 Long getArriveSheetCount(ArriveSheetDto dto);
	/**
	 * 
	 * <p>签收出库---根据条件分页查询到达联总数</p> 
	 * @author foss-meiying
	 * @date 2012-10-18 下午2:12:32
	 * @param dto
	 * @return
	 * @see
	 */
	 Long queryArriveSheetInfoCountByParams(SignDto dto);
	
	/**
	 * 根据运单信息查询可打印到达联的运单信息
	 * @author 097972-foss-dengtingting
	 * @date 2012-10-25 上午11:04:05
	 */
	 List<ArriveSheetWaybillDto> queryWaybillDetailByWaybill(ArriveSheetWaybillDto dto,CurrentInfo currentInfo);
	
	
	
	/**
	 * 根据运单号判断到达联表里是否存在签收。
	 * @author foss-meiying
	 * @date 2012-10-25 上午11:38:56
	 * @return
	 * @see
	 */
	 Long queryArriveExistSign(ArriveSheetEntity entity);
	/**
	 * 修改到达联。根据到达联编号
	 * @author foss-meiying
	 * @date 2012-10-26 下午5:33:37
	 * @param entity
	 * @return
	 * @see
	 */
	 int updateArriveSheetByArrivesheetNo(ArriveSheetEntity entity);
	 /**
	  * 根据条件查询到达联信息。
	  * @author foss-meiying
	  * @date 2012-11-2 上午10:31:55
	  * @return
	  * @see
	  */
	 ArriveSheetEntity queryArriveSheetByEntity(ArriveSheetEntity entity);
	 
	 /**
	  * 根据运单号校验生成到达联
	  * @author 097972-foss-dengtingting
	  * @date 2012-11-12 下午8:44:06
	  */
	 int checkGenerateArriveSheet(ArriveSheetEntity entity);
	 
	 /**
	  * 根据运单打印到达联
	  * @author 097972-foss-dengtingting
	  * @date 2012-11-14 下午2:40:38
	  */
	 String printArriveSheet(ArriveSheetEntity entity);
	 
	 /**
	  * 根据运单号、已排单数校验生成到达联
	  * @author 097972-foss-dengtingting
	  * @date 2012-11-12 下午8:44:06
	  */
	 ArrangeArriveSheetDto checkGenerateArriveSheet(String waybillNo,Integer arrangeGoodsQty);
	 
	 /**
	  * 确认派送 根据运单打印到达联
	  * @author 097972-foss-dengtingting
	  * @date 2012-11-14 下午2:40:38
	  */
	 String printArriveSheet(String waybillNo);

	 /**
	  * 
	  * 根据运单号,获得到达联的LIST
	  * @author ibm-lizhiguo
	  * @date 2012-11-16 下午2:26:23
	  */
	List<ArriveSheetEntity> queryArriveSheetByWaybillNo(ArriveSheetEntity entity);
	
	/**
	 * 作废到达联
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-17 下午3:50:51
	 */
	int invalidArriveSheet(ArriveSheetEntity entity);
	
	/**
	 * 更改打印次数
	 * @author 097972-foss-dengtingting
	 * @date 2012-12-4 上午10:44:35
	 */
	void updatePrintTimes(String arrivesheetNos);
	
	/**
	 * 根据运单号查询运单详细信息
	 * @author 097972-foss-dengtingting
	 * @date 2012-12-10 下午3:57:47
	 */
	ArriveSheetVo queryWaybillDetailByWaybillNo(String waybillNo);
	
	/**
	 * 根据运单、最终库存部门编码 检查是否存在有效的到达联
	 * @author 097972-foss-dengtingting
	 * @date 2012-12-14 上午10:46:49
	 */
	Boolean checkArriveSheetByWaybillNo(String waybillNo,String endStockOrgCode);
	
	/**
	 * 根据条件查询到达联的派送记录 返回 运单、派送单、到达联上相关信息
	 * @author 097972-foss-dengtingting
	 * @date 2012-12-18 下午8:33:05
	 */
	List<ArrivesheetDeliverDto> queryArriveSheetByDriverCode(ArrivesheetDeliverDto dto);
	
	/**
	 * 根据条件查询到达联的派送记录 返回 运单、派送单、到达联上相关信息（快递）
	 * @author WangQianJin
	 * @date 2014-06-06
	 */
	List<ArrivesheetDeliverDto> queryExpressArriveSheetByDriverCode(ArrivesheetDeliverDto dto);
	
	/**
	 * 根据到达联编号查运单运输性质  是否整车运单,
	 * 到达联id,提货人证件号码,证件类型,到达联件数,
	 * 最终配载部门,订单号，提货方式等运单的部分信息
	 * @author foss-meiying
	 * @date 2013-2-28 上午10:32:46
	 * @param arriveSheetNo
	 * @param status
	 * @return
	 * @see
	 */
    SignDto queryPartWaybillpartArrivesheet(String arriveSheetNo,String status);
	
	/**
	 * 签收变更接口  专线
	 * @author foss-meiying
	 * @date 2012-12-19 下午4:43:32
	 * @param oldArriveSheet
	 * @param newArriveSheet
	 * @see
	 */
	void changeArriveSheet(ArriveSheetEntity oldArriveSheet, ArriveSheetEntity newArriveSheet);
	/**
	 * 反签收接口 专线
	 * @author foss-meiying
	 * @date 2012-12-19 下午5:05:34
	 * @param list
	 * @param currentInfo
	 * @see
	 */
	void reverseArriveSheet(List<ArriveSheetEntity> list,CurrentInfo currentInfo);
	
	/**
	 * 根据ID更新arrivesheet
	 * @author foss-meiying
	 * @date 2012-12-22 下午7:56:25
	 * @param entity
	 * @return
	 * @see
	 */
	 Integer updateByPrimaryKeySelective(ArriveSheetEntity entity);
	 /**
	  * 根据运单号,获得到达联的LIST
	  * @author foss-meiying
	  * @date 2012-12-24 下午5:08:59
	  * @param waybillNo
	  * @return
	  * @see
	  */
	 List<ArriveSheetEntity> queryArriveSheetListByCondition(String waybillNo);

	 /**
	  * 
	  * 根据运单号查询签收的到达联集合
	  * @param waybillNo
	  * @return
	  * @author ibm-wangfei
	  * @date Jan 8, 2013 8:57:32 PM
	  */
	List<ArriveSheetEntity> queryArriveSheetListByWaybillNoWithSign(String waybillNo);
    /**
	 * 生成到达联
	 * @author foss-meiying
	 * @date 2013-1-29 上午9:53:41
	 * @param entity
	 * @return
	 * @see
	 */
    int addArriveSheetData(ArriveSheetEntity entity);
    /**
     * 生成到达联编号
     * @author foss-meiying
     * @date 2013-1-29 上午10:36:06
     * @param waybillNo
     * @return
     * @see
     */
    String generateArriveSheetId(String waybillNo);

    /**
     * 
     * <p>更新到达联<br />
     * </p>
     * @author ibm-lizhiguo
     * @version 0.1 2013-3-8
     * @param waybillNo
     * @param deliverymanName
     * @param identifyType
     * @param identifyCode
     * void
     */
	void updateArriveSheetEntityByWaybillNo(String waybillNo,
			String deliverymanName, String identifyType, String identifyCode);
	/**
	 * 
	 * <p>更新到达联的信息更具运单号，更新
	 * DELIVER_REQUIRE送货要求，
	 * IS_SENT_REQUIRED是否必须送货，
	 * IS_NEED_INVOICE是否需要发票，
	 * PRE_NOTICE_CONTENT提前通知内容
	 * 运单号<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2013-3-12
	 * @param arriveSheetEntity
	 * void
	 */
	void updateArriveSheetEntityByWaybillNo(ArriveSheetEntity arriveSheetEntity);
	
	/**
	 * 
	 * 更改运单到达部门插入pkp临时表记录
	 * 
	 * @param dto
	 * 更改到达部门用--原走货路径
	 *            dto.taskDetailId 运单号
	 *            dto.taskDetailType M
	 *            更改到达部门用--新走货路径
	 *            dto.taskDetailId 运单号
	 *            dto.taskDetailType N
	 * @author ibm-wangfei
	 * @date 2013-3-5 10:40:09
	 */
	void insertONTempForPKP(AutoTaskDTO dto);
	
	/**
	 * 
	 * 单件出入库插入pkp临时表记录
	 * 
	 * @param dto
	 * @author ibm-wangfei
	 * @date Feb 25, 2013 1:41:54 PM
	 */
	void insertIOTempForPKP(AutoTaskDTO dto);

	/**
	 * 
	 * 根据运单号，更新到达件数、到达时间
	 * @param waybillNos
	 * @author ibm-wangfei
	 * @date Jun 14, 2013 3:27:06 PM
	 */
	void updateWaybillArriveInfo(String [] waybillNos);

	void updateWaybillArriveDetialInfo(ActualFreightDto actualFreightDto);
	/**
	  * 根据到达联状态 生命周期查询到达联
	  * @author 097972-foss-dengtingting
	  * @date 2012-11-14 上午11:46:18
	  */
	List<ArriveSheetEntity> queryArriveSheetByLifeCycle(ArriveSheetDto dto);
	/**
	 * 根据运单号集合修改合送编码 跟送货要求
	 * @param entity
	 * @return
	 */
	 Integer updateTogetherSendCodeByWaybillNos(ArriveSheetDto dto);
	 /**
		 * <p>签收出库---根据条件分页查询到达联总数 快递相关</p> 
		 * @author foss-yuting
		 * @date 2014-10-09 下午2:12:32
		 * @param dto
		 * @return
		 * @see
		 */
		Long queryArriveSheetInfoCountByParamsExp(SignDto dto);

		
		/**
		 * <p>签收出库---根据条件分页查询到达联 快递相关</p> 
		 * @author foss-yuting
		 * @date 2014-10-09 下午4:02:44
		 */
		List<SignArriveSheetDto> queryArriveSheetInfoByParamsExp(SignDto dto,
					int start, int limit);
		
	/** 根据条件查询到最新一次 达联信息 (分批签收用)。
	 * @author foss-chenjunying
	 * @date 2015-03-25 15:14:55
	 * @return ArrivesheetEntity
	 * @see
	 */
	ArriveSheetEntity queryArriveSheetBySignTimeDesc(ArriveSheetDto dto);
	/** 根据条件查询到达联集合-未签收的
	 * @author 231438-foss-chenjunying
	 * @date 2015-06-23
	 * @return List<ArrivesheetEntity>
	 * @see
	 */
	List<ArriveSheetEntity> queryArriveSheetListNoSign(String waybillNo);
	/**
	 * 
	 * 根据运单号查询派送中的到达联集合
	 * @param waybillNo 运单号
	 * @return
	 * @author 231438-FOSS-chenjunying
	 */
	List<ArriveSheetEntity> queryArriveSheetListDeliver(String waybillNo);
	
	/**
	 * 
	 * <p>合伙人零担签收出库---根据条件分页查询到达联总数</p> 
	 * @author foss-239284
	 * @date 2012-10-18 下午2:12:32
	 * @param dto
	 * @return
	 * @see
	 */
	 Long queryPtpArriveSheetInfoCountByParams(SignDto dto);
	
	/**
	 * 
	 * <p>合伙人零担签收出库---根据条件分页查询到达联</p> 
	 * @author foss-239284
	 * @date 2012-10-17 下午4:02:44
	 */
	 List<SignArriveSheetDto> queryPtpArriveSheetInfoByParams(SignDto dto,int start,int limit);

	/**
	 * 合伙人快递自提签收出库---根据条件分页查询到达联总数
	 * 
	 * @author gpz
	 * @date 2016年2月25日
	 * @param dto 签收出库Dto
	 * @return Long 到达联总数
	 */
	Long queryPtpExpArriveSheetInfoCountByParams(SignDto dto);

	/**
	 * 合伙人快递自提签收出库---根据条件分页查询到达联信息集合
	 * 
	 * @author gpz
	 * @date 2016年2月25日
	 * @param SignDto 签收出库Dto
	 * @param start 第几页
	 * @param limit 每页多少记录
	 * @return List<SignArriveSheetDto> 到达联信息
	 */
	List<SignArriveSheetDto> queryPtpExpArriveSheetInfoByParams(SignDto dto,
			int start, int limit);
}