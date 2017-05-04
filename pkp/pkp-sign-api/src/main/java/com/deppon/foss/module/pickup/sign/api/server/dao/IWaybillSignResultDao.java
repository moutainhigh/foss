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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/server/dao/IWaybillSignResultDao.java
 * 
 * FILE NAME        	: IWaybillSignResultDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.esb.inteface.domain.foss2ptp.PartnerUpdateTakeEffectTimeRequest;
import com.deppon.esb.inteface.domain.gis.HisSignDataRequest;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.AirWaybillCondition;
import com.deppon.foss.module.pickup.sign.api.shared.dto.AirWaybillDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.ContrabandInfoDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.LostCargoInfoDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillSignResultDto;

/**
 * 运单签收结果接口
 * 
 * @author foss-meiying
 * @date 2012-10-23 上午11:40:27
 * @since
 * @version
 */
public interface IWaybillSignResultDao {
	/**
	 * 修改运单签收结果
	 * 
	 * @author foss-meiying
	 * @date 2012-10-23 上午11:41:30
	 * @param entity
	 * @return
	 * @see
	 */
	int updateWaybillSignResult(WaybillSignResultEntity entity);

	/**
	 * 根据运单号,是否有效 查询运单表里的货物总件数
	 * 
	 * @author foss-meiying
	 * @date 2012-10-25 上午10:09:34
	 * @return
	 * @see
	 */
	Integer queryWaybillQty(WaybillSignResultEntity entity);

	/**
	 * 据运单号,active = 'Y'查询运单签结果里的运单信息
	 * 
	 * @author foss-meiying
	 * @date 2012-10-25 上午10:09:34
	 * @return
	 * @see
	 */
	WaybillSignResultEntity queryWaybillSignResult(
			WaybillSignResultEntity entity);
    /**
     * 根据签收时间范围查询运单签结果里的运单信息
     * 
     * @author foss-caodajun 
     * FOSS设置定时任务将签收结果表同步到PTP，每天 05:00,23:00 点进行签收时间同步,
	 * 同步的为前一天签收的数据， PTP保存并更新最新的签收时间.
     */
	List<PartnerUpdateTakeEffectTimeRequest> queryWaybillSignTimeResult(Date signTimeStart,Date signTimeEnd) ;
	
	 /**
	  * 根据运单号，active = 'Y'，和签收状态，查询第一次全部签收的签收结果信息
	  * @author foss-bieyexiong
	  * @date 2015-04-21 下午16:28:01
	  * @param entity
	  * @return 
	  */
	 WaybillSignResultEntity queryFirstSignAllByEntity(WaybillSignResultEntity entity);
	 
	/**
	 * 保存运单签收结果信息
	 * 
	 * @author foss-meiying
	 * @date 2012-11-5 下午1:58:07
	 * @param entity
	 * @return
	 * @see
	 */
	int addWaybillSignResult(WaybillSignResultEntity entity);

	/**
	 * 根据运单号查询运单部分信息
	 * 
	 * @author foss-meiying
	 * @date 2012-11-30 下午4:43:03
	 * @param waybillNo
	 * @return
	 * @see
	 */
	WaybillDto queryWaybillActualFreightPartByWaybillNo(String waybillNo);

	/**
	 * 根据主键查询运单签收结果
	 * 
	 * @author foss-meiying
	 * @date 2012-12-7 上午11:43:53
	 * @param id
	 * @return
	 * @see
	 */
	WaybillSignResultEntity queryWaybillSignResultById(String id);

	/**
	 * 根据运单号查询运单签收结果里第一次添加的签收时间
	 * 
	 * @author foss-meiying
	 * @date 2012-12-11 上午9:16:13
	 * @param waybillNo
	 *            运单号
	 * @return
	 * @see
	 */
	Date queryFirstSignTimeByWaybillNo(String waybillNo);

	/**
	 * 
	 * <p>
	 * 通过ID更新运单签收结果<br />
	 * </p>
	 * 
	 * @author ibm-lizhiguo
	 * @version 0.1 2012-12-17
	 * @param entity
	 * @return int
	 */
	int updateWaybillSignResultById(WaybillSignResultEntity entity);

	/**
	 * 根据传入的待撤销组织CODE,返回当前组织的未签收票数
	 * 
	 * @author foss-meiying
	 * @date 2012-12-17 下午3:00:08
	 * @param lastLoadOrgCode
	 *            最终配载部门
	 * @return
	 * @see
	 */
	int queryNotSignGoodsQtyByOrgCode(String lastLoadOrgCode);
	
	/**
	 * 根据条件查询运单签收结果里的运单号
	 * @author foss-meiying
	 * @date 2012-12-24 下午2:14:21
	 * @param dto
	 * @return
	 * @see
	 */
    List<String> queryWaybillNoByCondition(WaybillSignResultDto dto);
    
    /**
	 * 
	 * 根据运单号查询运单信息
	 * @param waybillNo 运单号
	 * @author 038590-foss-wanghui
	 * @date 2012-12-25 下午9:05:50
	 */
    AirWaybillDto queryWaybillInfoForEdi(AirWaybillCondition condition);
    /**
	 * 
	 * 查询中转库存流水号  通过运单编号,部门编码
	 * @author foss-meiying
	 * @date 2012-10-19 下午6:45:19
	 * @param dto (运单号,部门编码)
	 * @return
	 */
	 List<SignDetailEntity> queryOptStockSerinalNo(ContrabandInfoDto dto);
	 
	 
	/**
	 * 据运单号集合,active = 'Y'查询运单签结果里的运单编号
	 * @author foss-meiying
	 * @date 2012-10-25 上午10:09:34
	 * @return
	 * @see
	 */
	List<String> queryWaybillSignResultWaybillNos(WaybillSignResultDto dto);
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
	  * 据运单号查询运单签结果里的运单信息
	  * @author foss-meiying
	  * @date 2013-7-2 下午3:35:19
	  * @param dto
	  * @return
	  * @see
	  */
	 List<WaybillSignResultEntity> queryWaybillSignResultLit(WaybillSignResultEntity entity);

	 /**
		 * 查询运单签结果里的运单信息用于传给GIS
		 * @author foss-chengjing
		 * @date 2017-03-02 上午8:03:01
		 * @param
		 * @return
		 */
		List<HisSignDataRequest> queryLTLSigfnResultInfoDuringAllYesterday();

}