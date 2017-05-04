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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/dao/IActualFreightDao.java
 * 
 * FILE NAME        	: IActualFreightDao.java
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
package com.deppon.foss.module.pickup.waybill.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.waybill.api.shared.dto.StockMoveDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ActualFreightDto;

/**
 * 
 * 运单状态数据持久层
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-sunrui,date:2012-10-30 下午5:43:53,content
 * </p>
 * 
 * @author foss-sunrui
 * @date 2012-10-30 下午5:43:53
 * @since
 * @version
 */
public interface IActualFreightDao {

	/**
	 * 
	 * <p>
	 * 插入一条记录
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-30 下午5:44:49
	 * @param record
	 * @return
	 * @see
	 */
	int insertSelective(ActualFreightEntity record);

	/**
	 * 
	 * <p>
	 * 按主键查询记录
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-30 下午5:45:07
	 * @param id
	 * @return
	 * @see
	 */
	ActualFreightEntity queryByPrimaryKey(String id);

	/**
	 * 
	 * <p>
	 * 按运单号码查询记录
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-30 下午5:45:07
	 * @param id
	 * @return
	 * @see
	 */
	ActualFreightEntity queryByWaybillNo(String waybillNo);

	/**
	 * 
	 * <p>
	 * 根据主键更新
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-30 下午5:45:25
	 * @param record
	 * @return
	 * @see
	 */
	int updateByPrimaryKeySelective(ActualFreightEntity record);

	/**
	 * 根据运单号减去该运单已生成的到达联件数
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-13 下午5:33:11
	 */
	int updateSubGenerateGoodsQtyByWaybillNo(ActualFreightEntity entity);

	/**
	 * 根据运单号增加该运单已生成的到达联件数
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-12-7 上午9:51:01
	 */
	int updateAddGenerateGoodsQtyByWaybillNo(ActualFreightEntity entity);

	/**
	 * 根据运单号更改运单已生成的到达联
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-12-7 上午10:16:36
	 */
	int updateGenerateGoodsQtyByWaybillNo(ActualFreightEntity entity);

	/**
	 * 
	 * <p>
	 * 新增运单附件信息<br />
	 * </p>
	 * 
	 * @author suyujun
	 * @version 0.1 2012-11-29
	 * @param entity
	 * @return int
	 */
	int insertActualFreightEntity(ActualFreightEntity entity);

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
	 * 
	 * 根据运单号更新运单运单已生成的到达联,到达未出库件数,排单件数
	 * 
	 * @author foss-meiying
	 * @date 2012-12-5 下午3:17:47
	 * @param actualFreightEntity
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
	 * 根据单号更新到达信息
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-3-8 上午11:25:42
	 */
	int updateArriveByWaybillNo(ActualFreightEntity actualFreightEntity);

	/**
	 * 
	 * 根据单号更新
	 * 
	 * @author foss-sunrui
	 * @date 2013-3-23 下午5:19:04
	 * @param record
	 * @return
	 * @see
	 */
	int updateByWaybillNo(ActualFreightEntity record);

	/**
	 * 根据主键更新ActualFreightEntity
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-24
	 * @param record
	 * @return int
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IActualFreightDao#updateByPrimaryKeySelective(com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity)
	 */
	int updateByWaybillNo(ActualFreightEntity record, String oldWaybillNo);
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
	 *  0001	zxy				20130916		新增：BUG-54827  用于6月1号前的运单号失效，删除此数据
	 */
	int deleteActualFreightByWaybillNo(String waybillNo);
	
	/**
	 * 查询是否电子运单
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-8-18 20:38:04
	 * @param eWaybillNo
	 * @return
	 */
	ActualFreightEntity queryActualFreightByWaybillType(String eWaybillNo, String waybillType);
	
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
	 * 
	 * @param actualFreightEntity
	 * @return
	 */
	int updateActualById(ActualFreightEntity actualFreightEntity);
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
	 * 根据运单号更新部分派送交单里通知的信息
	 * @author 159231 meiying
	 * 2015-5-4  下午6:54:23
	 * @param actualFreightEntity
	 * @return
	 */
	int updatePartByWaybillNo(ActualFreightEntity actualFreightEntity);
	/**
	 * 修改预计送货日期
	 * @author 159231 meiying
	 * 2015-5-5  上午10:18:41
	 * @param dto
	 * @return
	 */
	int updateDeliverDateByWaybillNos(ActualFreightDto dto);
}