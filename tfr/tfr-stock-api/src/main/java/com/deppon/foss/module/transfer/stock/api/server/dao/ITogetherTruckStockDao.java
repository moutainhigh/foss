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
 *  PROJECT NAME  : tfr-stock-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stock/api/server/dao/ITogetherTruckStockDao.java
 *  
 *  FILE NAME          :ITogetherTruckStockDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stock.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.stock.api.shared.domain.TogetherTruckStockEntity;
/**
 * 定义了对合车表的增、删操作
 * @author 097457-foss-wangqiang
 * @date 2012-12-25 上午11:41:53
 */
public interface ITogetherTruckStockDao {
	
	/**
	 * 增加合车记录
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-8 下午12:18:44
	 */
	int addTogetherTruckStock(TogetherTruckStockEntity togetherTruckStock);
	/**
	 * 删除合车记录 
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-31 上午8:56:39
	 */
	int deleteTogetherTruckStock(String waybillNO, String serialNO, String orgCode);
	/**
	 * 查询合车记录
	 * @param waybillNo 运单号
	 * @param serialNo 流水号
	 * @param goodsAreaCode 货区编号
	 * @param orgCode 部门编号
	 * @author 097457-foss-wangqiang
	 * @date 2013-3-19 下午3:50:33
	 */
	List<TogetherTruckStockEntity> queryTogetherTruckStock(String waybillNo, String serialNo, String goodsAreaCode, String orgCode);

}