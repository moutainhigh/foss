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
 * PROJECT NAME	: pkp-creating
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/dao/IWaybillDao.java
 * 
 * FILE NAME        	: IWaybillDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.AbandonedGoodsCondition;
import com.deppon.foss.module.pickup.waybill.shared.dto.AbandonedGoodsDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.AccountingQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.AccountingResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmWaybillServiceDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;

/**
 * 
 * 
 ******************************************* 
 * <b style="font-family:微软雅黑"><small>Description:IWaybillDao</small></b> </br>
 * <b style="font-family:微软雅黑"><small>HISTORY</small></b></br> <b
 * style="font-family:微软雅黑"><small> ID DATE PERSON REASON</small></b><br>
 ******************************************** 
 * <div style="font-family:微软雅黑,font-size:70%"> 2012-10-10 sunrui 新增 </div>
 ******************************************** 
 */
public interface IWaybillDao {

	/**
	 * 提交时新增运单
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-5 上午11:35:42
	 */
	int addWaybillEntity(WaybillEntity waybill);

	/**
	 * 修改运单
	 * 
	 * @param waybill
	 */
	int updateWaybill(WaybillEntity waybill);

	/**
	 * 通过运单编号查询运单
	 * 
	 * @param waybill
	 */
	WaybillEntity queryWaybillByNo(String waybillNo);

	/**
	 * 通过订单编号查询运单
	 * 
	 * @param orderNo
	 */
	WaybillEntity queryWaybillByOrderNo(String orderNo);

	/**
	 * 通过运单编号集合查询运单
	 * 
	 * @param waybillNoList
	 */
	List<WaybillEntity> queryWaybillByNoList(List<String> waybillNoList);

	/**
	 * 通过运单系统编号查询运单
	 * 
	 * @param waybill
	 */
	WaybillEntity queryWaybillById(String id);

	/**
	 * 查询最近一次建立的运单
	 * 
	 * @param orgCode
	 */
	WaybillEntity queryLastWaybill(String orgCode);

	/**
	 * 更新在线运单打印次数
	 * 
	 * @author foss-jiangfei
	 * @date 2012-11-15 下午11:48:25
	 * @param waybillNo
	 * @return
	 * @see
	 */
	int updateWaybillPrintTimes(String waybillNo);

	/**
	 * 
	 * <p>
	 * 查询运单<br />
	 * </p>
	 * 
	 * @author suyujun
	 * @version 0.1 2012-11-30
	 * @param waybillId
	 * @return WaybillEntity
	 */
	WaybillEntity getWaybillEntityById(String waybillId);

	/**
	 * 
	 * <p>
	 * 删除运单<br />
	 * </p>
	 * 
	 * @author suyujun
	 * @version 0.1 2012-12-3
	 * @param waybillEntity
	 * @return int
	 */
	int deleteWaybillEntityById(String waybillId);

	/**
	 * 
	 * 查询官网需要的运单信息
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-10 上午9:51:49
	 */
	List<AccountingResultDto> queryWaybillInfosByAccount(
			AccountingQueryDto accountingQueryDto);
	
	/**
     * 
     * 查询运单
     * @author 105089-FOSS-yangtong
     * @date 2012-11-26 下午08:37:46
	 */
	List<WaybillEntity> queryWaybill(WaybillDto waybillDto);

	
	/**
	 * 根据运单号查询运单部分数据（查询付款方式，到付金额，外发代理和币种）
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-13 下午2:47:39
	 */
	WaybillEntity queryPartWaybillByNo(String waybillNo);

	/**
	 * 弃货查询
	 * @author 043260-foss-suyujun
	 * @date 2012-12-15
	 * @param condition
	 * @return List<AbandonedGoodsDto>
	 */
	List<AbandonedGoodsDto> queryAbandonedGoodsDtoList(AbandonedGoodsCondition condition);

	/**
	 * 更新弃货
	 * @author 043260-foss-suyujun
	 * @date 2012-12-17
	 * @param args
	 * @return void
	 * @see
	 */
	void updateAbandonedGoods(Map<String, Object> args);

	/**
	 * 运单明细查询（接口使用）
	 * @author 043260-foss-suyujun
	 * @date 2012-12-18
	 * @param waybillNoList
	 * @return List<CrmWaybillServiceDto>
	 */
	List<CrmWaybillServiceDto> queryWaybillDetail(
			List<String> waybillNoList);

}