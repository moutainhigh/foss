
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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stock/server/dao/impl/StockDao.java
 *  
 *  FILE NAME          :StockDao.java
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.dao.IStockDao;
import com.deppon.foss.module.transfer.stock.api.shared.domain.*;
import com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockDetailDto;
import com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockDto;
import com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockQueryDto;
import com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockStatisticsDto;
import com.deppon.foss.module.transfer.stock.server.message.annotaion.SaleStockAdd;
import com.deppon.foss.module.transfer.stock.server.message.annotaion.SaleStockDelete;
import com.deppon.foss.module.transfer.stock.server.message.annotaion.StockAdd;
import com.deppon.foss.module.transfer.stock.server.message.annotaion.StockDelete;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 实现了对货件库存的基本操作
 * @author 097457-foss-wangqiang
 * @date 2012-12-25 上午11:56:27
 */

/**
* @description 这里用一句话描述这个类的作用
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年2月18日 上午10:15:34
*/
@SuppressWarnings("unchecked")
public class StockDao extends iBatis3DaoImpl implements IStockDao{
	/**
	 * 根据运单号、部门、货区查询货件库存
	 * @param waybillStockEntity.waybillNO  运单号
	 * @param waybillStockEntity.orgCode  部门编号
	 * @param waybillStockEntity.goodsAreaCode  货区编号
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-29 下午12:17:37
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IStockDao#queryStock(com.deppon.foss.module.transfer.stock.api.shared.domain.WaybillStockEntity)
	 */
	@Override
	public List<StockEntity> queryStock(WaybillStockEntity waybillStockEntity) {
		
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "stockQuery", waybillStockEntity);
		
	}
	
	/**
	 * 根据运单号查询货件库存
	 * @param waybillStockEntity
	 * @return
	 */
	@Override
	public List<StockEntity> queryStockByWaybillNo(
			WaybillStockEntity waybillStockEntity) {
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "stockQueryByWaybillNo", waybillStockEntity);
	}


	/**
	 * 根据运单号、流水号查询唯一货件库存
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-29 上午11:49:59
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IStockDao#queryUniqueStock(com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity)
	 */
	@Override
	public List<StockEntity> queryUniqueStock(InOutStockEntity inOutStockEntity) {
		return this.getSqlSession().selectList("foss.stock." + "uniqueStockQuery", inOutStockEntity);
	}
	/**
	 *  根据货件库存ID的List查询需要导出的货件库存信息
	 * @param idsList 货件ID的List
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-29 下午12:20:55
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IStockDao#queryExportStock(java.util.List)
	 */
	public List<WaybillStockQueryDto> queryExportStock(List<String> idsList){
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "queryExportStock", idsList);
	}
	/**
	 * 根据查询库存界面的查询条件查询需要导出的货件库存信息
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-5 下午3:04:10
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IStockDao#queryExportStock(com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockDto)
	 */
	@Override
	public List<WaybillStockQueryDto> queryExportStock(
			WaybillStockDto waybillStockDto) {
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "queryExportStockAll", waybillStockDto);
	}
	/**
	 * 增加货件库存
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-16 下午2:07:36
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IStockDao#addStock(com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity)
	 */
	@Override
	@StockAdd
	public int addStock(StockEntity stockEntity){
		
		stockEntity.setId(UUIDUtils.getUUID());
		
		this.getSqlSession().insert("foss.stock." + "insertStockEntity", stockEntity);
		
		return FossConstants.SUCCESS;
	}
	/**
	 * 删除货件库存
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-16 下午2:07:45
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IStockDao#deleteStock(InOutStockEntity)
	 */
	@Override
	@StockDelete
	public int deleteStock(InOutStockEntity inOutStockEntity) {
		this.getSqlSession().delete("foss.stock." + "deleteStockEntity", inOutStockEntity);
		return FossConstants.SUCCESS;
	}
	/**
	 * 查询某一运单下未在本部门库存的货件
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-18 上午9:37:16
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IStockDao#queryGoods(com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity, org.apache.ibatis.session.RowBounds)
	 */
	@Override
	public List<StockEntity> queryGoods(InOutStockEntity inOutStockEntity, RowBounds rowBounds) {
		
		if(rowBounds == null){
			return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "goodsQuery", inOutStockEntity);
		}else{
			return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "goodsQuery", inOutStockEntity, rowBounds);
		}
	}
	/**
	 * 查询某一运单下未在本部门库存的货件总数
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-18 上午9:37:08
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IStockDao#queryGoodsCount(com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity)
	 */
	@Override
	public Long queryGoodsCount(InOutStockEntity inOutStockEntity) {
		return (Long)this.getSqlSession().selectOne(StockConstants.NAME_SPACE + "goodsCountQuery",inOutStockEntity);
	}
	/**
	 * 判断货件是否存在该部门库存
	 * @return true： 不存在    false： 存在
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-18 上午9:36:48
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IStockDao#isExistStock(com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity)
	 */
	@Override
	public StockEntity queryStockEntityByNos(InOutStockEntity inOutStockEntity) {
		List<StockEntity> stockList = this.getSqlSession().selectList(StockConstants.NAME_SPACE + "stockExistQuery", inOutStockEntity);
		if(CollectionUtils.isNotEmpty(stockList)){
			return stockList.get(0);//存在
		}
		return null;//不存在
	}
	
	/**
	 * 判断货件是否存在该部门虚拟库存
	 * @return true： 不存在    false： 存在
	 * @author 332219-foss
	 * @date 2016-11-23
	 */
	@Override
	public StockSaleEntity querySaleStockEntityByNos(InOutStockEntity inOutStockEntity) {
		List<StockSaleEntity> stockList = this.getSqlSession().selectList(StockConstants.NAME_SPACE + "stockSaleExistQuery", inOutStockEntity);
		if(CollectionUtils.isNotEmpty(stockList)){
			return stockList.get(0);//存在
		}
		return null;//不存在
	}
	
	@Override
	public List<StockEntity> queryStockByGoodsAreaCode(String deptCode, String goodsAreaCode) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("deptCode", deptCode);
		params.put("goodsAreaCode", goodsAreaCode);
		
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "queryStockByGoodsAreaCode", params);
	}
	
	/**
	 * 更新流水号库存预配状态
	 * @author 045923-foss-shiwei
	 * @date 2012-11-28 下午6:48:55
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IStockDao#updatePreHandOverState(java.util.List)
	 */
	@Override
	public boolean updatePreHandOverState(StockEntity stockEntity) {
		this.getSqlSession().update(StockConstants.NAME_SPACE + "updatePreHandOverState",stockEntity);
		return true;
	}
	
	/**
	 * 更新流水号虚拟库存预配状态
	 * @author 332219-foss
	 * @date 2016-11-24
	 */
	@Override
	public boolean updatePreSaleHandOverState(StockSaleEntity stockSaleEntity) {
		this.getSqlSession().update(StockConstants.NAME_SPACE + "updatePreSaleHandOverState",stockSaleEntity);
		return true;
	}
	
	@Override
	public int isExistOtherGoodsAreaStock(String orgCode, String waybillNO,	String serialNO, String goodsAreaCode) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("orgCode", orgCode);
		params.put("waybillNO", waybillNO);
		params.put("serialNO", serialNO);
		params.put("goodsAreaCode", goodsAreaCode);
		
		return (Integer) this.getSqlSession().selectOne(StockConstants.NAME_SPACE + "isExistOtherGoodsAreaStock", params);
	}
	/**
	 * 根据部门查询库存件数
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-26 下午8:51:13
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IStockDao#queryStockGoodsQty(java.lang.String)
	 */
	@Override
	public List<Long> queryStockGoodsQty(String orgCode) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("orgCode", orgCode);
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "queryStockGoodsQty", paramsMap);
	}
	
	/**
	 * 根据部门查询库存总件数和总票数
	 * @author 272681-foss-chenlei
	 * @date 2016-02-19 下午14:49:26
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#queryStockGoodsQtyAndWaybillQty(java.lang.String)
	 */
	@Override
	public WaybillStockStatisticsDto queryStockGoodsQtyAndWaybillQty(String orgCode) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("orgCode", orgCode);
		return (WaybillStockStatisticsDto)this.getSqlSession().selectOne(StockConstants.NAME_SPACE + "queryStockGoodsQtyAndWaybillQty", paramsMap);
	}
	
	/**
	 * 批量出库
	 * @author 097457-foss-wangqiang
	 * @date 2013-1-26 上午10:44:53
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IStockDao#outStockBatchPC(java.util.List)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	@StockDelete
	public Map outStockBatchPC(List<InOutStockEntity> outStockList) {
		Map paramsMap = new HashMap();
		paramsMap.put("outStockList", outStockList);
		this.getSqlSession().selectOne(StockConstants.NAME_SPACE + "outStockProcedure", paramsMap);
		return paramsMap;
	}
	/**
	* 按照类型批量出库 
	* （装车）LOADING，（交接单）HANDOVER 
	* id (交接单时：交接单号，装车时：taskId)
	* @return: Map
	* @author: Xingmin , 2016-9-23 上午11:17:51
	 */
	@SuppressWarnings("rawtypes")
	@Override
	@StockDelete
	public Map outStockBatchPCByType(List<InOutStockEntity> outStockList,String type, String id) {
		Map paramsMap = new HashMap();
		paramsMap.put("outStockList", outStockList);
		paramsMap.put("type", type);
		paramsMap.put("id", id);
		
		this.getSqlSession().selectOne(StockConstants.NAME_SPACE + "outStockProcedureByType", paramsMap);
		return paramsMap;
	}
	/**
	 * 开单入库
	 * @author 097457-foss-wangqiang
	 * @date 2013-1-26 上午10:44:03
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IStockDao#inStockCreateBill(java.util.List)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	@StockAdd
	public Map inStockCreateBill(List<InOutStockEntity> inStockList) {
		Map paramsMap = new HashMap();
		paramsMap.put("inStockList", inStockList);
		paramsMap.put("waybillStockId", UUIDUtils.getUUID());
		this.getSqlSession().selectOne(StockConstants.NAME_SPACE + "inStockCreateBillProcedure", paramsMap);
		return paramsMap;
	}
	/**
	 * 反签收入库
	 * @author 097457-foss-wangqiang
	 * @date 2013-3-14 上午11:01:18
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IStockDao#inStockReverseSign(java.util.List)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	@StockAdd
	public Map inStockReverseSign(List<InOutStockEntity> inStockList) {
		Map paramsMap = new HashMap();
		paramsMap.put("inStockList", inStockList);
		paramsMap.put("waybillStockId", UUIDUtils.getUUID());
		this.getSqlSession().selectOne(StockConstants.NAME_SPACE + "inStockReverseSignProcedure", paramsMap);
		return paramsMap;
	}
	/**
	 * 移除货件
	 * 1.货件在库存中：出库、删除走货路径
	 * 2.货件不在库存中：不做任何操作
	 * @author 097457-foss-wangqiang
	 * @date 2013-3-14 下午6:09:36
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IStockDao#outStockInvalidGoods(java.util.List)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	@StockDelete
	public Map outStockInvalidGoods(List<InOutStockEntity> outStockList) {
		Map paramsMap = new HashMap();
		paramsMap.put("outStockList", outStockList);
		this.getSqlSession().selectOne(StockConstants.NAME_SPACE + "outStockInvalidGoodsProcedure", paramsMap);
		return paramsMap;
	}
	/**
	 * 更改货件库存运单号
	 * @param oldWaybillNo 原运单号
	 * @param newWaybillNo 新运单号
	 * @author 097457-foss-wangqiang
	 * @date 2013-3-18 下午4:39:33
	 */
	@Override
	public void updateWaybillNo(String oldWaybillNo, String newWaybillNo) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("oldWaybillNo", oldWaybillNo);
		paramsMap.put("newWaybillNo", newWaybillNo);
		this.getSqlSession().selectOne(StockConstants.NAME_SPACE + "updateWaybillNoStock", paramsMap);
	}
	/**
	 * 查询运单里的货件库存信息
	 * @author 097457-foss-wangqiang
	 * @date 2013-4-8 上午10:03:40
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IStockDao#queryWaybillStockDetail(java.lang.String)
	 */
	@Override
	public List<WaybillStockDetailDto> queryWaybillStockDetail(String waybillNo) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("waybillNo", waybillNo);
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "queryWaybillStockDetail", paramsMap);
	}
	/**
	* @param waybillNo 运单号
	* @param orgCode 当前部门编号
	* @return  
	* @description 根据运单号和当前部门编号查询库存
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-6-22 上午11:32:02
	*/
	@Override
	public List<StockEntity> queryStockByWaybillNoOrgCode(String waybillNo,
			String orgCode) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("waybillNO", waybillNo);
		paramsMap.put("orgCode", orgCode);
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "queryStockByWaybillNoOrgCode", paramsMap);
	}
	
	
	/**
	* @description 根据运单号分析库存表里存在的库存件数以及入库时间
	* @param waybillNo
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年3月9日 上午10:15:28
	*/
	@Override
	public List<WaybillStockEntity> queryStockByWaybillNoForGroup(String waybillNo) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("waybillNO", waybillNo);
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "queryStockByWaybillNoForGroup", paramsMap);
	}

	/**
	 * @param waybillNo
	 * @param serialNo
	 * @param orgCode
	 * @description 更新是否已经建包
	 */
	@Override
	public void updateIsPackage(String waybillNo, String serialNo, String orgCode) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("waybillNo", waybillNo);
		paramsMap.put("serialNo", serialNo);
		paramsMap.put("orgCode", orgCode);
		this.getSqlSession().selectOne(StockConstants.NAME_SPACE + "updateIsPackage", paramsMap);
	}

	/**
	* @description 根据当前部门code 以及库区编码 查询库存里下一部门的code以及下一部门的Name(去除重复部门) 用于下拉菜单使用
	* @param orgCode
	* @param goodArea
	* @param orgName 部门名称 用于模糊查询 非必填
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年2月14日 下午3:40:23
	*/
	@Override
	public List<StockEntity> queryNextOrgByStock(String orgCode, String goodArea,String orgName,int start,int limit) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("orgCode", orgCode);
		paramsMap.put("goodArea", goodArea);
		paramsMap.put("orgName", orgName);
		//分页参数
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "queryNextOrgByStock", paramsMap,rowBounds);
	}
	
	/**
	* @description 根据当前部门code 以及库区编码 查询库存里下一部门的code以及下一部门的Name(去除重复部门) 用于下拉菜单使用
	* @param orgCode
	* @param goodArea
	* @param orgName 部门名称 用于模糊查询 非必填
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年2月18日 上午10:15:13
	*/
	@Override
	public Integer queryNextOrgByStockCount(String orgCode, String goodArea,
			String orgName) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("orgCode", orgCode);
		paramsMap.put("goodArea", goodArea);
		paramsMap.put("orgName", orgName);
		return (Integer) this.getSqlSession().selectOne(StockConstants.NAME_SPACE + "queryNextOrgByStockCount", paramsMap);
	}
	
	/**
	* @param waybillNo 运单号
	* @param orgCode   部门编号
	* @return 根据运单号、部门编号来获取运单号、流水号、定位编号
	* @description 
	* @version 1.0
	* @author 200968-foss-zwd
	* @update 2014-12-18 下午5:21:46
	*/

	public List<StockPositionNumberEntity> queryStockPositionNumber(String waybillNo,
			String orgCode) {
		StockPositionNumberEntity stockPositionNumberEntity = new StockPositionNumberEntity();
		stockPositionNumberEntity.setWaybillNO(waybillNo);
		stockPositionNumberEntity.setOrgCode(orgCode);
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "queryStockPositionNumber", stockPositionNumberEntity);
	}

	/**
	 * @return 根据运单号、流水号、部门编号來存储定位编号到库存表中
	 * @description 
	 * @version 1.0
	 * @author 200968-foss-zwd
	 * @update 2014-12-19 上午14:44:46
	 */

	public void saveStockPositionNumber(StockPositionNumberEntity stockPositionNumberEntity) {
		this.getSqlSession().insert(
				StockConstants.NAME_SPACE + "saveStockPositionNumber",
				stockPositionNumberEntity);
	}
	/**
	* @return 查找所有的运单号、流水号、定位编号
	* @description
	* @version 1.0
	* @author 200968-foss-zwd
	* @update 2014-12-20 下午14:08:46
	*/
	public List<StockPositionNumberEntity> queryAllStockPositionNumber() {
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "queryAllStockPositionNumber");
	}

	/**
	* @param waybillNo 运单号
	* @param serialNo  流水号
	* @param orgCode   部门编号
	* @param stockPositionNumber  定位编号
	* @return 根据运单号、流水号、部门编号来更新定位编号
	* @description 
	* @version 1.0
	* @author 200968-foss-zwd
	* @update 2014-12-20 下午15:30:46
	*/
	public void updateStockPositionNumber(String waybillNo, String serialNo,
			String orgCode,String stockPositionNumber) {
		StockEntity stockEntity = new StockEntity();
		stockEntity.setWaybillNO(waybillNo);
		stockEntity.setSerialNO(serialNo);
		stockEntity.setOrgCode(orgCode);
		stockEntity.setStockPositionNumber(stockPositionNumber);
		this.getSqlSession().update(StockConstants.NAME_SPACE + "updateStockPositionNumber", stockEntity);
	}

	/**
	* @param waybillNo 运单号
	* @param serialNo  流水号
	* @return 根据运单号、流水号来删除定位编号
	* @description 
	* @version 1.0
	* @author 200968-foss-zwd
	* @update 2014-12-21 上午9:10:46
	*/
	public void deleteStockPositionNumber(String waybillNo, String serialNo,String orgCode) {
		StockPositionNumberEntity stockPositionNumberEntity = new StockPositionNumberEntity();
		stockPositionNumberEntity.setWaybillNO(waybillNo);
		stockPositionNumberEntity.setSerialNO(serialNo);
		stockPositionNumberEntity.setOrgCode(orgCode);
		this.getSqlSession().delete(StockConstants.NAME_SPACE + "deleteStockPositionNumber", stockPositionNumberEntity);
	}
	/**
	* @param waybillNo 运单号
    * @param orgCode   部门编号
	* @return 根据运单号、流水号、部门编号来获取库存表
	* @description 
	* @version 1.0
	* @author 200968-foss-zwd
	* @update 2015-8-14 下午15:21:46
	*/
	public List<StockEntity> queryStockByWO(String waybillNo, String orgCode) {
		StockEntity stockEntity = new StockEntity();
		stockEntity.setWaybillNO(waybillNo);
		stockEntity.setOrgCode(orgCode);
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "queryStockByWO", stockEntity);
	}
	
	/**
	* @param waybillNo 运单号
	* @param serialNo  流水号
    * @param orgCode   部门编号
	* @return 根据运单号、流水号、部门编号来获取库存表
	* @description 
	* @version 1.0
	* @author 200968-foss-zwd
	* @update 2014-12-25 下午14:21:46
	*/
	public StockEntity queryStockByWSO(String waybillNo, String serialNo,
			String orgCode) {
		StockEntity stockEntity = new StockEntity();
		stockEntity.setWaybillNO(waybillNo);
		stockEntity.setSerialNO(serialNo);
		stockEntity.setOrgCode(orgCode);
		return (StockEntity) this.getSqlSession().selectOne(StockConstants.NAME_SPACE + "queryStockByWSO", stockEntity);
	}
	/**
	 * 更新货区编码和入库时间
	 * @author 216208
	 * @date 2015-02-04 下午6:48:55
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IStockDao#updatePreHandOverState(java.util.List)
	 */
	@Override
	public void updateReturnGoodsState(StockEntity stockEntity) {
		this.getSqlSession().update(StockConstants.NAME_SPACE + "updateReturnGoodsState",stockEntity);
	}
	
	/**
	* @return 批量入库有异常，重新写回 批量入库的临时表。
	* @description 
	* @version 1.0
	* @author 140222-foss-songjie
	* @update 2014-12-25 下午14:31:46
	*/
	@Override
	public void insertPdaUnloadmsg(InOutStockEntity inOutStockEntity) {
		inOutStockEntity.setId(UUIDUtils.getUUID());
		this.getSqlSession().insert("foss.stock." + "insertPdaUnloadmsg", inOutStockEntity);
		
	}

	/**
	* @description 在丢货改善小组超过28天的运单
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年5月8日 下午4:11:23
	*/
	@Override
	public List<String> dayLoseGoodsForGuiji28() {
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "dayLoseGoodsForGuiji28");
	}
	
	/**
	 * @author nly
	 * @date 2015年4月22日 上午8:54:09
	 * @function 查询入库记录
	 * @param waybillNo
	 * @param serialNo
	 * @param inStockTypeList
	 * @return
	 */
	@Override
	public List<InOutStockEntity> queryInStockInfoByType(String waybillNo,	String serialNo, List<String> inStockTypeList) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("waybillNo", waybillNo);
		map.put("serialNo", serialNo);
		map.put("inStockTypeList", inStockTypeList);
		return this.getSqlSession().selectList("foss.stock." + "queryInStockInfoByType", map);
	}
	/**
	 * @Description:根据运单号和流水号查询库存信息（包含货区名称），供接送货调用
	 * @date 2015-7-13 上午10:50:37   
	 * @author 263072 
	 * @param wayBillNo
	 * @param serialNo
	 * @return
	 */
	@Override
	public List<StockEntity> queryStockAndGoodsArea(String wayBillNo, String serialNo) {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("wayBillNo", wayBillNo);
		dataMap.put("serialNO", serialNo);
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "queryStockAndGoodsArea",dataMap);
	}	/**
	 * @author hwy
	 * @date 2015年9月8日 上午11:02:09
	 * @function 子母件丢货找到查询
	 * @param waybillNo
	 * @param serialNo
	 * @return
	 */
	@Override
	public InOutStockEntity queryLostFindGoods(String waybillNo, String serialNo){
		Map<String,Object> map = new HashMap<String,Object>();
        map.put("waybillNo", waybillNo);
        map.put("serialNo", serialNo);
		return   (InOutStockEntity) this.getSqlSession().selectOne("foss.stock." + "queryLostFindGoods", map);
		
	}

	/**
	 * 根据运单号查询货件库存
	 * 按照入库时间排序
	 * 200968 2016-3-16
	 * @param waybillStockEntity
	 * @return
	 */
	@Override
	public List<StockEntity> queryStockByWaybillNoInStockTime(
			WaybillStockEntity waybillStockEntity) {
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "queryStockByWaybillNoInStockTime", waybillStockEntity);
	}
	
	/**
	 * 根据CRM传过来的运单号查询货件库存
	 * @author 273247
	 * @return
	 */
	public List<StockEntity> queryStockByCrmWaybillNo(String waybillNo) {
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "stockQueryByCrmWaybillNo", waybillNo);
	}
	
	/**根据运单编号查询库存中的当前部门
	 * @author 336540
	 * @date 2016-10-16 11:18:20
	 */
	public String stockQueryOrgCodeByWaybillNo(String waybillNo){
		return this.getSqlSession().selectOne(StockConstants.NAME_SPACE + "stockQueryOrgCodeByWaybillNo", waybillNo).toString();
	}

	@Override
	public int queryCountByWaybillNoAndOrgCode(String waybillNo, String orgCode) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("waybillNO", waybillNo);
		params.put("orgCode", orgCode);
		return (Integer)this.getSqlSession().selectOne(StockConstants.NAME_SPACE + "queryCountByWaybillNoAndOrgCode", params);
	}

	@Override
	public List<String> queryGoodsAreaCodes(String orgCode, String goodsAreaType) {
		Map<String, String> params = new HashMap<String, String>(2);
		params.put("orgCode", orgCode);
		params.put("goodsAreaType", goodsAreaType);
		return getSqlSession().selectList(StockConstants.NAME_SPACE + "queryGoodsAreaCodes", params);
	}
	
	/**营业部交接
	 * 根据运单号、流水号查询唯一货件库存
	 * @author 360903 linhua.yan
	 * @date 2016年9月19日 22:06:13
	 */
	@Override
	public List<StockSaleEntity> queryUniqueStockSale(InOutStockEntity inOutStockEntity) {
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "uniqueStockSaleQuery", inOutStockEntity);
	}
	
	/**营业部交接
	 * 增加货件库存
	 * @author 360903 linhua.yan
	 * @date 2016年9月19日 22:41:12
	 */
	@Override
	@SaleStockAdd
	public int addStockSale(StockSaleEntity stockSaleEntity){
		
		stockSaleEntity.setId(UUIDUtils.getUUID());
		
		this.getSqlSession().insert(StockConstants.NAME_SPACE+ "insertStockSaleEntity", stockSaleEntity);
		
		return FossConstants.SUCCESS;
	}
	
	/**营业部交接
	* @description 根据运单号分析库存表里存在的库存件数以及入库时间
	* @param waybillNo
	* @author 360903 linhua.yan
	* @update 2016年9月20日 04:34:37
	*/
	@Override
	public List<WaybillStockSaleEntity> queryStockSaleByWaybillNoForGroup(String waybillNo) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("waybillNo", waybillNo);
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "queryStockSaleByWaybillNoForGroup", paramsMap);
	}
	
	/**营业部交接
	 * 批量出库
	 * @author 360903
	 * @date 2016年9月20日 15:46:19
	 */
	@Override
	@SaleStockDelete
	public int outStockSaleBatchPC(InOutStockEntity outStock) {
		return this.getSqlSession().delete(StockConstants.NAME_SPACE + "outStockSale", outStock);
	}
	
	/**营业部交接
	 * 批量运单出库
	 * @author 360903
	 * @date 2016年9月20日 15:46:19
	 */
	@Override
	public int outWaybillStockSaleBatchPC(String orgCode,List<String> waybillList) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orgCode", orgCode);
		map.put("waybillList", waybillList);
		this.getSqlSession().update(StockConstants.NAME_SPACE + "outWaybillStockSale", map);
		return FossConstants.SUCCESS;
	}
	
	/**营业部交接
	 * 判断货件是否存在该部门库存
	 * @param orgCode 部门
	 * @param waybillNO 运单号
	 * @param serialNO 流水号
	 * @return true： 不存在    false： 存在
	 * @author 360903
	 * @date 2016年9月20日 16:59:52
	 */
	@Override
	public StockSaleEntity queryStockSaleEntityByNos(InOutStockEntity inOutStockEntity) {
		List<StockSaleEntity> stockList = this.getSqlSession().selectList(StockConstants.NAME_SPACE + "stockSaleExistQuery", inOutStockEntity);
		if(CollectionUtils.isNotEmpty(stockList)){
			return stockList.get(0);//存在
		}
		return null;//不存在
	}
	
	/**
	 * 更新流水号库存预配状态
	 * @author 360903
	 * @date 2016年9月20日 17:19:15
	 */
	@Override
	public boolean updatePreHandOverStateSale(StockSaleEntity stockSaleEntity) {
		this.getSqlSession().update(StockConstants.NAME_SPACE + "updatePreHandOverStateSale",stockSaleEntity);
		return true;
	}
	
	/**营业部交接
	 * 查询出库运单集合是否有虚拟库存
	 * @param  运单号集合
	 * @author 360903 linhua.yan
	 * @date 2016年9月19日 22:06:13
	 */
	@Override
	public List<StockSaleEntity> queryInStockInfoBywayBillList(List<String> waybillList,String orgCode){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("waybillList", waybillList);
		map.put("orgCode", orgCode);
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "queryInStockInfoByWayBills", map);
	}
	
	/**营业部交接
	 * 交接单作废入库
	 * @author 360903
	 * @date 2016年10月19日 14:38:02
	 */
	@Override
	public int inStockSalePC(InOutStockEntity inStock){
		this.getSqlSession().update(StockConstants.NAME_SPACE + "inStockSalePC", inStock);
		return FossConstants.SUCCESS;
	}
	
	/**营业部交接
	 * 交接单单票入库出虚拟库
	 * @author 360903
	 * @date 2016年10月20日 19:16:42
	 */
	@Override
	@SaleStockDelete
	public int outStockSalePC(InOutStockEntity outStock){
		this.getSqlSession().update(StockConstants.NAME_SPACE + "outStockSalePC", outStock);
		return FossConstants.SUCCESS;
	}
	
	/**
	 * tfr.t_opt_waybill_stock_sale先查询运单在此部门的库存件数
	 * @author 218427
	 * @date 2016年12月28日 17:20:42
	 */
	public int querySaleCount(String waybillNo, String orgCode){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("waybillNo", waybillNo);
		map.put("orgCode", orgCode);
		return (Integer) this.getSqlSession().selectOne(StockConstants.NAME_SPACE+"querySaleCount",map);
	}
	
	/**
	 * tfr.t_opt_waybill_stock_sale先查询运单在此部门的库存件数 根据相差来判断是更新还是删除
	 * @author 218427
	 * @date 2016年12月28日 17:20:42
	 */
	public void updateSaleCount(int m,String waybillNo,String orgCode){
		Map<String, Object> map =new HashMap<String,Object>();
		map.put("waybillNo", waybillNo);
		map.put("m", m);
		map.put("orgCode", orgCode);
		this.getSqlSession().update(StockConstants.NAME_SPACE+"updateSaleCount",map);
	}
	
	/**
	 * 流水全部出库则删除对应的运单库存记录
	 * @author 218427
	 * @date 2016年12月28日 17:50:21
	 */
	public void deleteSaleCount(String waybillNo,String orgCode){	
		Map<String, String> map = new HashMap<String, String>();
		map.put("waybillNo", waybillNo);
		map.put("orgCode", orgCode);
		this.getSqlSession().delete(StockConstants.NAME_SPACE+"deleteSaleCount",map);
	}
	
	
}