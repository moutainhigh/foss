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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/server/dao/IStayHandoverDao.java
 * 
 * FILE NAME        	: IStayHandoverDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.server.dao;


import java.util.List;

import com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.StayHandoverDto;

/**
 * 交接Dao
 * @author foss-meiying
 * @date 2012-11-9 下午6:58:46
 * @since
 * @version
 */
public interface IStayHandoverDao {
	/**
	 * 根据id删除交接信息
	 * @author foss-meiying
	 * @date 2012-11-9 下午6:58:13
	 * @param id
	 * @return
	 * @see
	 */
    int deleteByPrimaryKey(String id);
    /**
     * 添加交接信息
     * @author foss-meiying
     * @date 2012-11-9 下午7:00:59
     * @param record
     * @return
     * @see
     */
    StayHandoverEntity addStayHandover(StayHandoverEntity record);
    
    /**
     * 通过id查询交接信息
     * @author foss-meiying
     * @date 2012-11-9 下午7:01:31
     * @param id
     * @return
     * @see
     */
    StayHandoverEntity queryByPrimaryKey(String id);
    
    /**
     * 通过车牌号查询交接信息
     * @author foss-liubinbin
     * @date 2012-11-9 下午7:01:31
     * @param id
     * @return
     * @see
     */
    List<StayHandoverEntity> queryByVehicleNo(String vehicleNo);
    /**
     * 通过相应参数修改交接信息
     * @author foss-meiying
     * @date 2012-11-9 下午7:01:55
     * @param record
     * @return
     * @see
     */
    int updateByPrimaryKeySelective(StayHandoverEntity record);
    /**
     * 通过条件修改交接信息
     * @author foss-meiying
     * @date 2012-11-9 下午7:02:16
     * @param record
     * @return
     * @see
     */
    int updateByPrimaryKey(StayHandoverEntity record);
    /**
     * 查询接货卡货票数
     * @author foss-meiying
     * @date 2012-12-11 下午3:12:40
     * @param dto
     * @return
     * @see
     */
    Integer queryPickupFastWaybillQtyCount(StayHandoverDto dto);
    /**
     * 根据司机工号查询waybillPending(运单待处理信息)所有货物总件数,总体积,总重量
     * @author foss-meiying
     * @date 2012-12-24 下午4:24:36
     * @param driverCode 司机工号
     * @return
     * @see
     */
    StayHandoverEntity querySumGoodsInfoByParams(String driverCode);
    /**
     * 根据司机工号查询waybillPending(运单待处理信息)所有货物总件数,总体积,总重量
     * @author foss-231438 借鉴meiying的方法修改
     * @date 2015-7-13 下午4:24:36
     * @param StayHandoverDto 司机工号
     * @return
     * @see
     */
    StayHandoverEntity querySumGoodsInfoByParams(StayHandoverDto dto);
    /**
	 * 根据主键修改运单件数(运单件数-传入的件数)
	 * @author foss-meiying
	 * @date 2013-3-17 下午4:56:21
	 * @param record
	 * @return
	 * @see
	 */
	int updateGoodsQtyTotalReduceById(StayHandoverEntity record);
	/**
	 * 根据车号查询waybillPending(运单待处理信息)所有货物总件数,总体积,总重量
	* @author 329757-foss-liuxiangcheng 
	* @date 2016-6-17 下午5:22:24
	* @param @param param
	* @param @return    设定文件 
	* @return StayHandoverEntity    返回类型 
	* @throws
	 */
	StayHandoverEntity querySumGoodsInfoByVo(StayHandoverDto param);
	/**
	 * 根据车号查询接货卡货票数
	* @author 329757-foss-liuxiangcheng 
	* @date 2016-6-17 下午5:23:04
	* @param @param dto
	* @param @return    设定文件 
	* @return Integer    返回类型 
	* @throws
	 */
	Integer queryPickupFastWaybillQtyCountByVo(StayHandoverDto dto);
    
}