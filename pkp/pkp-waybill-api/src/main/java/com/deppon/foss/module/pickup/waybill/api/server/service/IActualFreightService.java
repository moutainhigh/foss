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
 * PROJECT NAME	: pkp-waybill-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/service/IActualFreightService.java
 * 
 * FILE NAME        	: IActualFreightService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.StockMoveDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ActualFreightDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;

/**
 * 
 * 运单状态服务接口
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-sunrui,date:2012-10-30 下午6:43:06,
 * </p>
 * 
 * @author foss-sunrui
 * @date 2012-10-30 下午6:43:06
 * @since
 * @version
 */
public interface IActualFreightService extends IService {

	/**
	 * 
	 * <p>
	 * 更新货物数量
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-30 下午6:43:20
	 * @param waybillNo
	 * @param goodNum
	 * @return
	 * @see
	 */
	ResultDto updateGoodsNum(String waybillNo, int goodNum);

	/**
	 * 
	 * <p>
	 * 按运单号码查询记录
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-11-34 下午5:45:07
	 * @param id
	 * @return
	 * @see
	 */
	ActualFreightEntity queryByWaybillNo(String waybillNo);

	/**
	 * 
	 * <p>
	 * 新增运单附加信息<br />
	 * </p>
	 * 
	 * @author suyujun
	 * @version 0.1 2012-11-29
	 * @param ActualFreightEntity
	 *            void
	 */
	int insertWaybillActualFreight(ActualFreightEntity actualFreightEntity);

	/**
	 * 更新(ActualFreightEntity actualFreightEntity)
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-5
	 * @param actualFreightEntity
	 * @return
	 * @return int
	 * @see
	 */
	int updateWaybillActualFreight(ActualFreightEntity actualFreightEntity);
	/**
	 * @author 200945
	 * 更改pkp.t_srv_actual_Freight表中的部分字段
	 * @param actualFreightEntity
	 * @return
	 */
	int updateActualById(ActualFreightEntity actualFreightEntity);

	/**
	 * 根据运单号更新该运单到达未出库件数 或者付款方式
	 * 
	 * @author foss-meiying
	 * @date 2012-12-5 下午3:17:47
	 * @param ActualFreightDto
	 * @return
	 * @see
	 */
	int updateArriveNotoutGoodsQtyByWaybillNo(ActualFreightDto dto);

	/**
	 * 根据运单号更新运单运单已生成的到达联,到达未出库件数,排单件数
	 * 
	 * @author foss-meiying
	 * @date 2012-12-5 下午3:17:47
	 * @param actualFreightEntity
	 * 
	 * @return
	 * @see
	 */
	int updateActualFreightPartByWaybillNo(ActualFreightEntity actualFreightEntity);

	/**
	 * 
	 * 根据单号更新结清状态
	 * 
	 * @author 043258-foss-zhaobin
	 * @date 2012-12-20 下午7:34:20
	 */
	void updateActualFreightSettleStatusByNo(ActualFreightEntity actualFreightEntity);

	/**
	 * 根据老运单号更新数据
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-25 下午2:07:11
	 */
	int updateByWaybillNo(ActualFreightEntity record, String oldWaybillNo);

	/**
	 * 根据运单号判断ActualFreightEntity是否已存在
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-19 下午3:33:42
	 */
	boolean isExistActualFreight(String waybillNo);
	/**
	 * 根据运单号修改ActualFreight部分信息 （并发控制）
	 * @author foss-meiying
	 * @date 2013-4-19 下午5:23:13
	 * @param dto
	 * @return
	 * @see
	 */
	int updatePartGoodsQtyControlByWaybillNo(ActualFreightDto dto);
	
	/**
	 * 删除
	 * 
	 * @param waybillNo
	 * @return
	 * 
	 *  版本		 用户			时间				内容
	 *  0001	zxy				20130916		新增：BUG-54827   用于6月1号前的运单号失效，删除此数据
	 */
	int deleteActualFreightByWaybillNo(String waybillNo);
	
	/**
	 * 批量激活实际城运信息--电子运单
	 * @author liyongfei--DMANA-2035
	 * @param waybillNoList
	 * @return
	 */
	int setActualFreightActive(List<String> waybillNoList);
	
	/**
	 * 更新城运表状态
	 * @author liyongfei
	 * @param waybillNo
	 * @return
	 */
	int setActualFreightStatus(String waybillNo,String status);
/**
	 * 货物迁移
	 */
	void stockMove(StockMoveDto sto);
	
	/**
	 * 根据运单号进行数据的更新
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-3-15 14:39:52
	 * @param actualFreightEntity
	 * @return
	 */
	int updateByWaybillNoSelective(ActualFreightEntity actualFreightEntity);
	    /**
     * 批量更新
     * @param entitys
     */
	void updateByActualFreightEntitys(List<ActualFreightEntity> entitys);
	/**
	 * 根据运单号更新部分派送交单里通知的信息
	 * @author 159231 meiying
	 * 2015-5-4  下午6:26:14
	 * @param actualFreightEntity
	 * @return
	 */
	int updatePartNotifyByWaybillNo(ActualFreightEntity actualFreightEntity);
	/**
	 * 修改预计送货日期
	 * @author 159231 meiying
	 * 2015-5-5  上午10:20:43
	 * @param dto
	 * @return
	 */
	int updateDeliverDateByWaybillNos(ActualFreightDto dto);
}