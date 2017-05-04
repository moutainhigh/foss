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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/server/dao/IArrivesheetDao.java
 * 
 * FILE NAME        	: IArrivesheetDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.pda.api.shared.dto.ValidateArriveSheetDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetLogEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveSheetActualFreightDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveSheetDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveSheetWaybillAddPropertyDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveSheetWaybillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArrivesheetDeliverDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AutoTaskDTO;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignArriveSheetDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ActualFreightDto;

/**
 * 到达联管理接口
 * @author dp-dengtingting
 *
 */
public interface IArrivesheetDao {
	
	/**
	 * 生成到达联
	 * @author dp-dengtingting
	 * @date 2012-10-13 上午10:51:42
	 */
	 int addArriveSheetData(ArriveSheetEntity entity);
	
	/**
	 * 作废、激活、修改到达联
	 * @author dp-dengtingting
	 * @date 2012-10-13 上午10:53:03
	 */
	 int updateArriveSheetData(ArriveSheetEntity entity);
	
	/**
	 * 根据条件查询到达联
	 * @author dp-dengtingting
	 * @date 2012-10-13 上午10:54:42
	 */
	 List<ArriveSheetDto> queryArriveSheetData(ArriveSheetDto dto,int start, int limit); 
	/***
	 * 
	 * <p>签收出库---根据条件查询到达联</p> 
	 * @author foss-meiying
	 * @date 2012-10-17 下午3:58:39
	 * @param dto
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	 List<SignArriveSheetDto> queryArriveSheetInfoByParams(SignDto dto,int start,int limit);

	
	/**
	 * 查询到达联分页模式
	 * @author dp-dengtingting
	 * @date 2012-10-19 下午6:04:44
	 */
	 Long getArriveSheetCount(ArriveSheetDto dto);

	/***
	 * 
	 * <p>签收出库---根据条件分页查询到达联总数</p> 
	 * @author foss-meiying
	 * @date 2012-10-18 下午2:16:05
	 * @param dto
	 * @return
	 * @see
	 */
	 Long queryArriveSheetInfoCountByParams(SignDto dto);
	
	/**
	 * 根据运单号查询已到达运单信息
	 * @author 097972-foss-dengtingting
	 * @date 2012-10-29 下午2:26:31
	 */
	 List<ArriveSheetWaybillDto> queryWaybillDetailByWaybill(ArriveSheetWaybillDto dto);
	

	/**
	 * 根据交接单号查询已到达运单信息
	 * @author 097972-foss-dengtingting
	 * @date 2012-10-29 下午2:29:00
	 */
	 List<ArriveSheetWaybillDto> queryWaybillDetailByHandoverNo(ArriveSheetWaybillDto dto);
	
	/**
	 * 根据预派送单号查询已到达运单信息
	 * @author 097972-foss-dengtingting
	 * @date 2012-10-29 下午2:30:54
	 */
	 List<ArriveSheetWaybillDto> queryWaybillDetailByDeliverbillId(ArriveSheetWaybillDto dto);
	
	/**
	 * 根据客户信息查询已到达运单信息
	 * @author 097972-foss-dengtingting
	 * @date 2012-10-29 下午2:31:25
	 */
	 List<ArriveSheetWaybillDto> queryWaybillDetailByCustomer(ArriveSheetWaybillDto dto);

	/***
	 * 根据运单号判断到达联表里是否存在签收。
	 * @author foss-meiying
	 * @date 2012-10-25 上午11:38:56
	 * @return
	 * @see
	 */
	 Long queryArriveExistSign(ArriveSheetEntity entity);
	
	/***
	 * 修改到达联。根据到达联编号
	 * @author foss-meiying
	 * @date 2012-10-26 下午5:34:25
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
	  * 根据条件查询到达联集合
	  * @author 097972-foss-dengtingting
	  * @date 2012-12-25 上午11:02:56
	  */
	 List<ArriveSheetEntity> queryArriveSheetListByEntity(ArriveSheetEntity entity);
	 
	 /**
	  * 根据运单号查询到达联信息集合
	  * @author 097972-foss-dengtingting
	  * @date 2012-11-13 上午11:17:52
	  */
	 List<ArriveSheetEntity> queryArriveSheetByWaybillNo(ArriveSheetEntity entity);

	 /**
	  * 根据运单号查询最大值到达联编号
	  * @author 097972-foss-dengtingting
	  * @date 2012-11-13 上午11:17:52
	  */
	 Long queryMaxArriveSheetNoByWayBillNo(String waybillNo);
	 
	 /**
	  * 根据到达联状态 生命周期查询到达联
	  * @author 097972-foss-dengtingting
	  * @date 2012-11-14 上午11:46:18
	  */
	 List<ArriveSheetEntity> queryArriveSheetByLifeCycle(ArriveSheetDto dto);
	 
	 /**
	  * 根据到达联编号、状态获得到达联条数
	  * @author 097972-foss-dengtingting
	  * @date 2012-11-27 下午5:45:38
	  */
	 Integer countArriveSheetByNo(ArriveSheetEntity entity);
	 
	 /**
	  * 根据到达联编号更改打印次数
	  * @author 097972-foss-dengtingting
	  * @date 2012-12-4 上午10:40:19
	  */
	 Integer updatePrintTimesByArrivesheetNo(ArriveSheetEntity entity);
	 
	 /**
	  * 根据运单号查询已打印达到联的条数
	  * @author 097972-foss-dengtingting
	  * @date 2012-12-7 下午1:51:36
	  */
	 Integer getCountArriveSheetByWaybillNo(ArriveSheetEntity entity);
	 
	 /**
	  * 根据到达联编号，查询运单信息
	  * @author 097972-foss-dengtingting
	  * @date 2012-12-12 下午3:03:06
	  */
	 ValidateArriveSheetDto queryWaybillByArriveSheetNo(ArriveSheetDto condition);
	 
	 /**
	  * 根据运单号、最终库存部门CODE 查询到达联
	  * @author 097972-foss-dengtingting
	  * @date 2012-12-14 上午11:30:16
	  */
	 Integer queryArriveSheetByWaybillNo(ArriveSheetActualFreightDto dto);
	 
	 /**
	  * 根据ID更新arrivesheet
	  * @author 097972-foss-dengtingting
	  * @date 2012-12-14 下午2:15:09
	  */
	 Integer updateByPrimaryKeySelective(ArriveSheetEntity entity);
	 
	/**
	 * 根据条件查询到达联的派送记录 返回 运单、派送单、到达联上相关信息
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-12-18 下午8:33:05
	 */
	List<ArrivesheetDeliverDto> queryArriveSheetByDriverCode(ArrivesheetDeliverDto dto);
	
	/**
	 * 根据条件查询到达联的派送记录 返回 运单、派送单、到达联上相关信息（快递）
	 * 
	 * @author WangQianJin
	 * @date 2014-06-06
	 */
	List<ArrivesheetDeliverDto> queryExpressArriveSheetByDriverCode(ArrivesheetDeliverDto dto);
	 
	/**
	 * 
	 * 根据到达联编号查运单运输性质  是否整车运单,
	 * 到达联id,提货人证件号码,证件类型,到达联件数,
	 * 最终配载部门,订单号，提货方式等运单的部分信息
	 * @author foss-meiying
	 * @date 2012-12-18 下午9:48:25
	 * @param entity
	 * @return
	 * @see
	 */
	SignDto queryPartWaybillpartArrivesheet(ArriveSheetEntity entity);
	/**
	 * 
	 * <p>通过到达联id，获得到达联信息<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2012-12-20
	 * @param id
	 * @return
	 * ArriveSheetEntity
	 */
	ArriveSheetEntity queryArriveSheetById(String id);

	/**
	 * 
	 * <p>获得初次到达联信息<br />
	 * </p>
	 * @author ibm-lizhiguo
	 * @version 0.1 2012-12-25
	 * @param arrivesheetNo
	 * @return
	 * ArriveSheetEntity
	 */
	ArriveSheetEntity queryArriveSheetCreateTime(String arrivesheetNo);
	/**
	 * 生成到达联
	 * @author foss-meiying
	 * @date 2013-1-29 上午9:53:41
	 * @param entity
	 * @return
	 * @see
	 */
    ArriveSheetEntity addArriveSheet(ArriveSheetEntity entity);

    /**
     * 
     * <p>根据运单号更新到达联的证件号，到达提货人,证件类型<br />
     * </p>
     * @author ibm-lizhiguo
     * @version 0.1 2013-3-8
     * @param entity
     * @return
     * Integer
     */
	Integer updateArriveSheetByWaybillNo(ArriveSheetEntity entity);
	
	/**
	 * 
	 * 根据运单号查询打印到达联信息
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-4-15 下午5:46:58
	 */
	ArriveSheetWaybillAddPropertyDto queryPrintInfoByWaybillNo(WaybillEntity waybillEntity);
	
	/**
	 * 
	 * 更改到达部门插入临时表表记录
	 * 
	 * @param dto
	 * @author ibm-wangfei
	 * @date Mar 5, 2013 10:42:56 AM
	 */
	void insertONTempForPKP(AutoTaskDTO dto);
	
	/**
	 * 
	 * 单件出入库插入pkp临时表记录
	 * 
	 * @param dto
	 * @author ibm-wangfei
	 * @date Feb 25, 2013 1:42:22 PM
	 */
	void insertIOTempForPKP(AutoTaskDTO dto);

	List<ActualFreightDto> queryWaybillAllQty(ArriveSheetWaybillDto dto);

	/**
	 * 
	 * 新增到达联操作日志表
	 * @param arriveSheetLogEntity
	 * @author ibm-wangfei
	 * @date Jul 8, 2013 4:06:03 PM
	 */
	void insertArriveSheetLogEntity(ArriveSheetLogEntity arriveSheetLogEntity);

	/**
	 * 
	 * 按照入库时间进行查询可打印到达联
	 * 
	 * @param dto
	 * @return
	 * @author ibm-wangfei
	 * @date Jul 9, 2013 11:41:29 AM
	 */
	List<ArriveSheetWaybillDto> queryWaybillDetailByInStockTime(ArriveSheetWaybillDto dto);
	
	/**
	 * 按照到达时间进行查询可打印到达联
	 * @param dto
	 * @return
	 */
	List<ArriveSheetWaybillDto> queryWaybillDetailByArriveTime(ArriveSheetWaybillDto dto);
	/**
	 * 根据运单号集合修改合送编码 跟送货要求
	 * @param entity
	 * @return
	 */
	 Integer updateTogetherSendCodeByWaybillNos(ArriveSheetDto dto);
	 /***
		 * 
		 * <p>签收出库---根据条件分页查询到达联总数 快递相关</p> 
		 * @author foss-yuting
		 * @date 2014-10-09 下午2:16:05
		 * @param dto
		 * @return
		 * @see
		 */
		Long queryArriveSheetInfoCountByParamsExp(SignDto dto);

		/***
		 * 
		 * <p>签收出库---根据条件查询到达联 快递相关</p> 
		 * @author foss-yuting
		 * @date 2014-10-09 下午3:58:39
		 * @param dto
		 * @param start
		 * @param limit
		 * @return
		 * @see
		 */
		List<SignArriveSheetDto> queryArriveSheetInfoByParamsExp(SignDto dto,
				int start, int limit);
		/**
		  * 根据运单号 查询最新一次的到达联签收信息
		  * DMANA-9716
		  * @param dto
		  * @author 231438-foss-chenjunying
		  * @date 2015-03-25 上午09:06:55
		  * @return ArriveSheetEntity
		  */
	ArriveSheetEntity queryArriveSheetBySignTimeDesc(ArriveSheetDto dto);	
	
	/***
	 * 
	 * <p>合伙人签收出库---根据条件分页查询到达联总数</p> 
	 * @author foss-239284
	 * @param dto
	 * @return
	 * @see
	 */
	 Long queryPtpArriveSheetInfoCountByParams(SignDto dto);
	 
	/***
	 * 
	 * <p>合伙人零担签收出库---根据条件查询到达联</p> 
	 * @author foss-meiying
	 * @date 2012-10-17 下午3:58:39
	 * @param dto
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	 List<SignArriveSheetDto> queryPtpArriveSheetInfoByParams(SignDto dto,int start,int limit);

	/**
	 * 合伙人快递自提签收出库---根据条件查询到达联总数
	 * 
	 * @author gpz
	 * @date 2016年2月25日
	 * @param SignDto 签收出库Dto
	 * @return Long 到达联总数
	 */
	Long queryPtpExpArriveSheetInfoCountByParams(SignDto dto);

	/**
	 * 合伙人快递自提签收出库---根据条件查询到达联信息集合
	 * @author gpz
	 * @date 2016年2月25日
	 * @param SignDto 签收出库Dto
	 * @param start 第几页
	 * @param limit 每页多少条记录
	 * @return List<SignArriveSheetDto>
	 */
	List<SignArriveSheetDto> queryPtpExpArriveSheetInfoByParams(SignDto dto,
			int start, int limit);
}