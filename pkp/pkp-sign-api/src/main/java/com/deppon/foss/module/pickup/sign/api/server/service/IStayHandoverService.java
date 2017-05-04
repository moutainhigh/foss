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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/server/service/IStayHandoverService.java
 * 
 * FILE NAME        	: IStayHandoverService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.server.service;


import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.PdaDto;

/**
 * 交接service
 * @author foss-meiying
 * @date 2012-11-9 下午6:46:40
 * @since
 * @version
 */
public interface IStayHandoverService extends IService {

	/**
	 * 添加交接表
	 * @author foss-meiying
	 * @date 2012-11-9 下午6:49:47
	 * @param entity
	 * @return
	 * @see
	 */
	StayHandoverEntity addStayHandover(StayHandoverEntity entity);
	/**
	 * 删除交接信息
	 * @author foss-meiying
	 * @date 2012-12-10 下午3:25:00
	 * @param id
	 * @return
	 * @see
	 */
    int deleteByPrimaryKey(String id);
    
    /**
     * 通过id查询交接信息
     * @author foss-meiying
     * @date 2012-12-10 下午3:26:10
     * @param id
     * @return
     * @see
     */
    StayHandoverEntity queryByPrimaryKey(String id);
    /**
     * 通过相应参数修改交接信息
     * @author foss-meiying
     * @date 2012-12-10 下午3:27:30
     * @param record
     * @return
     * @see
     */
    int updateByPrimaryKeySelective(StayHandoverEntity record);
    /**
     * 通过条件修改交接信息
     * @author foss-meiying
     * @date 2012-12-10 下午3:27:50
     * @param record
     * @return
     * @see
     */
    /**
     * 根据主键修改交接信息
     * @author foss-meiying
     * @date 2012-12-10 下午3:28:59
     * @param record
     * @return
     * @see
     */
    int updateByPrimaryKey(StayHandoverEntity record);
    /**
     * 
     * @author foss-meiying
     * @date 2012-12-10 下午3:45:44
     * @return
     * @see
     */
    StayHandoverEntity queryPickUpStayHandOverInfo(String driverCode);
    /**
     * 得到接货信息
     * @author foss-meiying
     * @date 2012-12-24 下午4:15:46
     * @param driverCode 司机工号
     * @return
     * @see
     */
    StayHandoverEntity queryPickUpStayHandOverInfo(String driverCode,boolean isExpress);
   /**
    * 在补录PDA运单保存/提交时，若减少了件数，则执行以下操作：
    *  2.2 将删除的PDA货件从中转卸车交接单货件明细表(T_SRV_STAY_HANDOVERSERIAL)中删除。
   	*  2.3 更新中转卸车交接单明细(T_SRV_STAY_HANDOVERDETAIL)的开单件数(GODDS_QTY),减去件数。
   	*  2.4 更新中转卸车交接单表(T_SRV_STAY_HANDOVER)的件数(GOODS_QTY_TOTAL)，减去件数。
    * @author foss-meiying
    * @date 2013-3-17 下午4:14:28
    * @param dto
    * @see
    */
	void makeupPdaReduceGoodsQty(PdaDto dto);
	/**
	 * 在补录PDA运单保存/提交时，若增加了件数，则执行以下操作
	 *   1.2 将新增的PDA货件插入中转卸车交接单货件明细表(T_SRV_STAY_HANDOVERSERIAL)中。
   	 *	 1.3 更新中转卸车交接单明细(T_SRV_STAY_HANDOVERDETAIL)的开单件数(GODDS_QTY),累加上增加的件数。
   	 *	 1.4 更新中转卸车交接单表(T_SRV_STAY_HANDOVER)的件数(GOODS_QTY_TOTAL)，累加上增加的件数
	 * @author foss-meiying
	 * @date 2013-3-17 下午4:16:51
	 * @param dto
	 * @see
	 */
	void makeupPdaAddgoodsQty(PdaDto dto);
	/**
	 * 根据司机车牌号查询接货信息
	 * add by 329757 2016-6-17
	 */
	StayHandoverEntity queryPickUpStayHandOverInfoByVehicleNo(String vehicleNo,
			boolean isDriver);
}