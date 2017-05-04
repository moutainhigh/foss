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
 *  PROJECT NAME  : tfr-stock
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stock/server/dao/impl/TogetherTruckStockDao.java
 *  
 *  FILE NAME          :TogetherTruckStockDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.stock.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.dao.ITogetherTruckStockDao;
import com.deppon.foss.module.transfer.stock.api.shared.domain.TogetherTruckStockEntity;
import com.deppon.foss.module.transfer.stock.server.message.annotaion.TogetherAdd;
import com.deppon.foss.module.transfer.stock.server.message.annotaion.TogetherDelete;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import org.apache.commons.collections.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 实现对合车表的基本操作
 * @author 097457-foss-wangqiang
 * @date 2012-12-25 上午11:57:24
 */
public class TogetherTruckStockDao extends iBatis3DaoImpl implements ITogetherTruckStockDao{
	
	/**
	 * 添加合车记录
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-8 下午12:20:54
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.ITogetherTruckStockDao#addTogetherTruckStock(com.deppon.foss.module.transfer.stock.api.shared.domain.TogetherTruckStockEntity)
	 */
	@Override
	@TogetherAdd
	public int addTogetherTruckStock(TogetherTruckStockEntity togetherTruckStock) {
		List<TogetherTruckStockEntity> togetherTruckList = this.queryTogetherTruckStock(togetherTruckStock.getWaybillNO(), togetherTruckStock.getSerialNO(),
									togetherTruckStock.getNewGoodsAreaCode(), togetherTruckStock.getOrgCode());
		//不存在相同合车记录
		if(CollectionUtils.isEmpty(togetherTruckList)){
			togetherTruckStock.setId(UUIDUtils.getUUID());
			this.getSqlSession().insert(StockConstants.NAME_SPACE + "insertTogetherTruck", togetherTruckStock);
		}else{
			//存在相同合车记录
		}
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 删除合车记录
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-31 上午8:58:11
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.ITogetherTruckStockDao#deleteTogetherTruckStock(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@TogetherDelete
	public int deleteTogetherTruckStock(String waybillNO, String serialNO,
			String orgCode) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("waybillNO", waybillNO);
		paramsMap.put("serialNO", serialNO);
		paramsMap.put("orgCode", orgCode);
		this.getSqlSession().delete(StockConstants.NAME_SPACE + "deleteTogetherTruck", paramsMap);
		return FossConstants.SUCCESS;
	}
	/**
	 * 查询合车记录
	 * @param waybillNo 运单号
	 * @param serialNo 流水号
	 * @param goodsAreaCode 货区编号
	 * @param orgCode 部门编号
	 * @author 097457-foss-wangqiang
	 * @date 2013-3-19 下午3:50:33
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TogetherTruckStockEntity> queryTogetherTruckStock(
			String waybillNo, String serialNo, String goodsAreaCode,
			String orgCode) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("waybillNo", waybillNo);
		paramsMap.put("serialNo", serialNo);
		paramsMap.put("goodsAreaCode", goodsAreaCode);
		paramsMap.put("orgCode", orgCode);
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "queryTogetherTruck", paramsMap);
	}

}