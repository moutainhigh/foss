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
 *  FILE PATH          :/IAirUnshippedGoodsService.java
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

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirUnshippedGoodsEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillSerialNoEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirUnshippedGoodsDto;

/**
 * 拉货相关操作
 * @author foss-liuxue(for IBM)
 * @date 2012-11-29 下午2:37:37
 */
public interface IAirUnshippedGoodsService extends IService {

	/**
	 * 查询拉货信息
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-3 下午2:24:15
	 */
	List<AirUnshippedGoodsEntity> queryUnshippedGoods(AirUnshippedGoodsDto airUnshippedGoodsDto, int start, int limit);
	
	/**
	 * 获取总记录条数
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-3 下午2:51:38
	 */
	Long getCount(AirUnshippedGoodsDto airUnshippedGoodsDto);
	
	/**
	 * 根据正单号或运单号查找信息
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-11 下午2:52:41
	 */
	List<AirWaybillSerialNoEntity> querySerialNoByWaybillNo(AirUnshippedGoodsDto airUnshippedGoodsDto);
	
	/**
	 * 根据正单号/代单号获取拉货信息
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-11 下午8:03:04
	 */
	AirUnshippedGoodsEntity queryUnshippedGoodsByBillNo(AirUnshippedGoodsDto airUnshippedGoodsDto);
	
	/**
	 * 录入或修改拉货信息
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-12 下午4:42:39
	 */
	int saveOrUpdateAirUnshippedGoods(AirUnshippedGoodsDto airUnshippedGoodsDto);
	
	/**
	 * 拉货入库
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-14 下午3:14:46
	 */
	int unshippedGoodsInStock(AirUnshippedGoodsDto airUnshippedGoodsDto);
	
	/**
	 * 删除拉货记录
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-17 下午3:09:25
	 */
	int deleteAirUnshippedGoods(AirUnshippedGoodsDto airUnshippedGoodsDto);
	
	/**
	 * 根据ID查找拉货信息
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-19 9:57:19
	 */
	AirUnshippedGoodsEntity queryAirUnshippedGoodsById(AirUnshippedGoodsDto airUnshippedGoodsDto);
	
	/**
	 * 起草拉货通知
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-27 下午2:25:21
	 */
	void addUnshippedMessage(AirUnshippedGoodsDto airUnshippedGoodsDto);
	
}