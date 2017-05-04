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
 *  http://www.deppon.com/licenses/LICENSE-2.0
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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stock/server/dao/impl/StockAreaLogDao.java
 *  
 *  FILE NAME          :StockAreaLogDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  现状：外场与派送部公用库存，外场卸车即入到派送库区，但是货物实际拉到派送库区可能会有很长的时间间隔，
 *  派送部没办法知道哪些货物实际已经拉到派送库区，因而产生通知了客户来提货，结果货物还没拉到库区，找货时间长引起客户投诉问题 
 * 解决方案：
 * 派送库区增加库位信息，支持PC端及PDA端将货物登入库位操作，
 * 同时派送管理（通知客户、查询货量）、创建派送单（查询货物）支持按照库位查询货物。 
 * 这样，派送部在做派送操作时，即可操作实际到达库区的货物
 *  
 *  
 *  库存操作：
 *  针对驻地派送部门开发的功能。
 *  此模块只是针对在库区中的货物进行库位确认。
 *  
 *  中转做如下改动：

 *  1：库存表（tfr.t_opt_stock)增加一列【库位】
 *  2：运单库存（tfr.t_opt_waybill_stock）增加一列【实际进入派送库位的货件数量】
 *  3：库位和库区的关联关系读取综合的基础资料配置。
 *  4：增加1张库位日志表，T_OPT_STOCK_AREA_LOG日志表,记录库位的历史入库记录

 *  中转增加如下功能：
 *  1：单票入库增加入库类型，【确认入库库位】,选择该类型的时候，界面要增加库位信息，让用户选择。
 *  2：库存查询画面，在驻地派送部账户登录的情况下，要增加查询条件【库位】，以及显示该列。供查询
 *  2：库存界面增加功能按钮【确认入库库位】。
 *  只有是驻地派送部帐号登录才能看到该按钮。并且该按钮被赋予了权限。
 *  增加这个功能允许用户查询未确认的到达的货物，然后勾选批量确认。
 *  
 *  
 *  PDA也可以调用此接口。
 ******************************************************************************/
package com.deppon.foss.module.transfer.stock.server.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.dao.IStockAreaLogDao;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
* @description 对库位的操作
* @version 1.0
* @author 140022-foss-songjie
* @update 2013-8-5 上午11:11:33
*/

public class StockAreaLogDao extends iBatis3DaoImpl implements IStockAreaLogDao {
	
	/**
	* @param inOutStockEntity
	* @return 
	* @description 保存库位记录(跟入库记录一样的表结构，加了库位属性)
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-3 下午2:34:21
	*/
	@Override
	public int insertStockAreaLog(InOutStockEntity inOutStockEntity) {
		inOutStockEntity.setId(UUIDUtils.getUUID());
		this.getSqlSession().insert(StockConstants.NAME_SPACE + "insertStockAreaLog",inOutStockEntity);
		return FossConstants.SUCCESS;
	}
	
	/**
	* @param inOutStockEntity 运单号、流水号、货区编码、部门编号、库位 不能为空
	* @return 
	* @description 修改库存表的库位 
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-3 下午2:34:21
	*/
	@SuppressWarnings("unchecked")
	@Override
	public int updateStockStockPosition(InOutStockEntity inOutStockEntity) {
		@SuppressWarnings("rawtypes")
		Map paramsMap = new HashMap();
		paramsMap.put("waybillNo", inOutStockEntity.getWaybillNO());
		paramsMap.put("serialNO", inOutStockEntity.getSerialNO());
		paramsMap.put("goodsAreaCode", inOutStockEntity.getGoodsAreaCode());
		paramsMap.put("orgCode", inOutStockEntity.getOrgCode());
		paramsMap.put("position", inOutStockEntity.getPosition());
		return this.getSqlSession().update(StockConstants.NAME_SPACE + "updateStockStockPosition",paramsMap);
	}
	
	/**
	* @param inOutStockEntity 运单号、货区编码、部门编号不能为空
	* @return 
	* @description 修改运单库存表的库位件数
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-3 下午2:34:21
	*/
	@SuppressWarnings("unchecked")
	@Override
	public int updateWaybillStockOutStockPosition(
			InOutStockEntity inOutStockEntity) {
		@SuppressWarnings("rawtypes")
		Map paramsMap = new HashMap();
		paramsMap.put("waybillNo", inOutStockEntity.getWaybillNO());
		paramsMap.put("goodsAreaCode", inOutStockEntity.getGoodsAreaCode());
		paramsMap.put("orgCode", inOutStockEntity.getOrgCode());
		return this.getSqlSession().update(StockConstants.NAME_SPACE + "updateWaybillStockOutStockPosition",paramsMap);
	}

}
