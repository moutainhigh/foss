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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stock/server/dao/impl/InOutStockDao.java
 *  
 *  FILE NAME          :InOutStockDao.java
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.dao.IInOutStockDao;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockDetailDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 实现对出入库表的基本操作
 * 
 * @author dp-wangqiang
 * @date 2012-10-12 下午4:07:41
 */
public class InOutStockDao extends iBatis3DaoImpl implements IInOutStockDao {


	/**
	 * 增加入库动作信息
	 * @author dp-wangqiang
	 * @date 2012-10-12 下午4:06:54
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IInOutStockDao#saveInStock(com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity)
	 */
	@Override
	public int addInStock(InOutStockEntity inOutStockEntity) {
		inOutStockEntity.setId(UUIDUtils.getUUID());
		this.getSqlSession().insert(StockConstants.NAME_SPACE + "insertInStock",
				inOutStockEntity);
		return FossConstants.SUCCESS;
	}

	/**
	 * 增加出库动作信息
	 * @author dp-wangqiang
	 * @date 2012-10-12 下午4:07:10
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IInOutStockDao#saveOutStock(com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity)
	 */
	@Override
	public int addOutStock(InOutStockEntity inOutStockEntity) {
		inOutStockEntity.setId(UUIDUtils.getUUID());
		this.getSqlSession().insert(StockConstants.NAME_SPACE + "insertOutStock",
				inOutStockEntity);
		return FossConstants.SUCCESS;
	}
	/**
	 * 查询出库记录
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-3 上午9:17:48
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IInOutStockDao#queryOutStock(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryOutStock(String waybillNO, Date createBillTime){
		@SuppressWarnings("rawtypes")
		Map paramsMap = new HashMap();
		paramsMap.put("waybillNO", waybillNO);
		paramsMap.put("createBillTime", createBillTime);
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "queryOutStock", paramsMap);
	}
	
	/**
	 * 查询入库动作记录
	 * @author 097457-foss-wangqiang
	 * @date 2013-1-17 下午2:39:14
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IInOutStockDao#queryInStockInfo(java.lang.String, java.lang.String, java.lang.String, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InOutStockEntity> queryInStockInfo(String waybillNo, String serialNo, String orgCode, Date createBillTime) {

		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("waybillNo", waybillNo);
		paramsMap.put("serialNo", serialNo);
		paramsMap.put("orgCode", orgCode);
		paramsMap.put("isValid", FossConstants.YES);
		paramsMap.put("createBillTime", createBillTime);
		
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "queryInStockInfo", paramsMap);
	}
	
	
	/**
	* @param waybillNo
	* @param serialNo
	* @param orgCode
	* @param createBillTime 
	* @return
	* @description 查询小于入库时间的入库动作记录
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-27 下午1:54:05
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<InOutStockEntity> queryInStockInfoSmall(String waybillNo,
			String serialNo, String orgCode, Date createBillTime) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("waybillNo", waybillNo);
		paramsMap.put("serialNo", serialNo);
		paramsMap.put("orgCode", orgCode);
		paramsMap.put("isValid", FossConstants.YES);
		paramsMap.put("createBillTime", createBillTime);
		
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "queryInStockInfoSmall", paramsMap);
	}

	/**
	 * 查询出库动作记录
	 * @param waybillNo
	 * @param serialNo
	 * @param orgCode
	 * @param createBillTime
	 * @return
	 * 
	 * @author foss-wuyingjie
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InOutStockEntity> queryOutStockInfo(String waybillNo, String serialNo, String orgCode, Date createBillTime) {
		
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("waybillNo", waybillNo);
		paramsMap.put("serialNo", serialNo);
		paramsMap.put("orgCode", orgCode);
		paramsMap.put("isValid", FossConstants.YES);
		paramsMap.put("createBillTime", createBillTime);
		
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "queryOutStockInfo", paramsMap);
	}
	/**
	 * 更改入库记录运单号
	 * @param oldWaybillNo 原运单号
	 * @param newWaybillNo 新运单号
	 * @param createBillTime 开单时间
	 * @param inOutStockType 出入库类型
	 * @author 097457-foss-wangqiang
	 * @date 2013-3-18 下午4:39:33
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void updateWaybillNoInStock(String oldWaybillNo, String newWaybillNo, Date createBillTime, String inOutStockType){
		@SuppressWarnings("rawtypes")
		Map paramsMap = new HashMap();
		paramsMap.put("oldWaybillNo", oldWaybillNo);
		paramsMap.put("newWaybillNo", newWaybillNo);
		paramsMap.put("createBillTime", createBillTime);
		paramsMap.put("inOutStockType", inOutStockType);
		this.getSqlSession().update(StockConstants.NAME_SPACE + "updateWaybillNoInStock", paramsMap);
	}
	
	/**
	 * 更改出库记录运单号
	 * @param oldWaybillNo 原运单号
	 * @param newWaybillNo 新运单号
	 * @param createBillTime 开单时间
	 * @param inOutStockType 出入库类型
	 * @author 097457-foss-wangqiang
	 * @date 2013-3-18 下午4:39:33
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void updateWaybillNoOutStock(String oldWaybillNo, String newWaybillNo, Date createBillTime, String inOutStockType){
		@SuppressWarnings("rawtypes")
		Map paramsMap = new HashMap();
		paramsMap.put("oldWaybillNo", oldWaybillNo);
		paramsMap.put("newWaybillNo", newWaybillNo);
		paramsMap.put("createBillTime", createBillTime);
		paramsMap.put("inOutStockType", inOutStockType);
		this.getSqlSession().update(StockConstants.NAME_SPACE + "updateWaybillNoOutStock", paramsMap);
	}
	/**
	 * 查询货件出库信息
	 * @author 097457-foss-wangqiang
	 * @date 2013-4-8 上午10:50:38
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IInOutStockDao#queryGoodsOutStock(java.lang.String, java.lang.String, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillStockDetailDto> queryGoodsOutStock(String waybillNo,
			String serialNo, Date createBillTime) {
		@SuppressWarnings("rawtypes")
		Map paramsMap = new HashMap();
		paramsMap.put("waybillNo", waybillNo);
		paramsMap.put("serialNo", serialNo);
		paramsMap.put("createBillTime", createBillTime);
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "queryGoodsOutStock", paramsMap);
	}

	/**
	 * @author niuly
	 * @date 2014-5-24上午8:41:54
	 * @function 根据运单号查询所有的入库部门编码
	 * @param waybillNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryInDeptCodeByWaybillNo(String waybillNo) {
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "queryInDeptCodeByWaybillNo", waybillNo);
	}

}