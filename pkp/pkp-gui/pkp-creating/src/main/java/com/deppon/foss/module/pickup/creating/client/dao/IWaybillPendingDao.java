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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/dao/IWaybillPendingDao.java
 * 
 * FILE NAME        	: IWaybillPendingDao.java
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

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillOfflineEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.PdaReceiveGoodsDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto;

/**
 * 
 * 运单暂存数据持久层接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-10-31 下午3:39:09,content:TODO </p>
 * @author foss-sunrui
 * @date 2012-10-31 下午3:39:09
 * @since
 * @version
 */
public interface IWaybillPendingDao {
    
    int deleteByPrimaryKey(String id);
    
    int deleteByWaybillNo(String waybillNo);

    int insert(WaybillPendingEntity record);
    
    /**
     * 批量插入WaybillPendingEntity对象
     * @author 026123-foss-lifengteng
     * @date 2012-11-3 下午4:44:21
     */
    int addWaybillPendingEntityBatch(List<WaybillPendingEntity> pendingList);

    int insertSelective(WaybillPendingEntity record);

    WaybillPendingEntity queryByPrimaryKey(String id);
    
    WaybillPendingEntity queryByWaybillNumber(String number);

    int updateByPrimaryKeySelective(WaybillPendingEntity record);

    int updateByPrimaryKey(WaybillPendingEntity record);
    
    /**
     * <p>
     * (查询运单待处理表)
     * </p>
     * @author 105089-FOSS-yangtong
     * @date 2012-10-16 下午04:22:42
     * @return
     */
    List<WaybillPendingEntity> queryPending(WaybillPendingDto waybillPendingDto);
    
    /**
     * <p>
     * 更改运单状态PENDING
     * </p>
     * @author 105089-FOSS-yangtong
     * @date 2012-10-16 下午04:22:42
     * @return
     * @see
     */
	int updatePendingActive(String id);
	
	/**
	 * 
	 * <p>
	 * 通过运单号/订单号判断运单是否存在
	 * </p>
	 * @author foss-yangtong
	 * @date 2012-10-30 下午7:44:25
	 * @return
	 * @see 
	 */
	WaybillPendingEntity queryPendingByNo(String mixNo);
	
	
	/**
	 * 
	 * <p>
	 * 通过运单号/订单号判断运单是否存在
	 * </p>
	 * @author foss-yangtong
	 * @date 2012-10-30 下午7:44:25
	 * @return
	 * @see 
	 */
	WaybillPendingEntity  queryPendingByWaybillNoAndOrderNo(String waybillNo, String orderNo);

	/**
	 * 查询PDA接货记录
	 * @author 043260-foss-suyujun
	 * @date 2012-12-12
	 * @param args
	 * @return List<PdaReceiveGoodsDto>
	 */
	List<PdaReceiveGoodsDto> queryPdaReceiveGoodsDto(Map<String, Object> args);

	/**
	 * 运单表中查询ＰＤＡ接货记录
	 * @author 043260-foss-suyujun
	 * @date 2012-12-12
	 * @param args
	 * @return List<PdaReceiveGoodsDto>
	 */
	List<PdaReceiveGoodsDto> queryPdaWaybillReceiveGoodsDto(Map<String, Object> args);
	
	/**
	 * 根据运单号更新待处理运单状态为失效
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-22 下午5:03:55
	 */
	int updatePendingActiveByNo(String waybillNo);

	Integer countOfflineActiveWayBill();

	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author foss-sunrui
	 * @date 2013-3-22 下午2:01:10
	 * @param waybillPendingEntity
	 * @return
	 * @see
	 */
	int updateByWaybillNo(WaybillPendingEntity waybillPendingEntity);

	/**
	 * 与queryPending调用的方法是一样的，只是为了返回不一样的实体类型
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-11 上午10:55:18
	 */
	List<WaybillOfflineEntity> queryOfflinePending(WaybillPendingDto waybillPendingDto);

	/**
	 * @param orgCode
	 * @return
	 */
	Integer countOfflineActiveWayBill(String orgCode);

	List<WaybillPendingEntity> queryPendingExpress(
			WaybillPendingDto waybillPendingDto);

}