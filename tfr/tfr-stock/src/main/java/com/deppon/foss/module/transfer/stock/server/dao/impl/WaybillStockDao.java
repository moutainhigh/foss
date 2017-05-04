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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stock/server/dao/impl/WaybillStockDao.java
 *  
 *  FILE NAME          :WaybillStockDao.java
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

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.stock.api.define.StockConstants;
import com.deppon.foss.module.transfer.stock.api.server.dao.IWaybillStockDao;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.WaybillStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.WaybillStockSaleEntity;
import com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockDto;
import com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockQueryDto;
import com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockStatisticsDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 实现了对运单库存的基本操作
 * @author 097457-foss-wangqiang
 * @date 2012-12-25 上午11:58:27
 */
@SuppressWarnings("unchecked")
public class WaybillStockDao extends iBatis3DaoImpl implements IWaybillStockDao{
	
	/**
	 * 分页查询运单库存关联运单表
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-16 下午2:02:31
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IWaybillStockDao#queryWaybillStock(com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockDto, int, int)
	 */
	@Override
	public List<WaybillStockQueryDto> queryWaybillStock(
			WaybillStockDto waybillStockDto, int limit, int start) {
		
		RowBounds rowBounds = new RowBounds(start, limit);
		
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "waybillStockJoinQuery",waybillStockDto,rowBounds);
	}
	
	
	/**
	 * 分页查询运单库存关联运单表(快递员)
	 * @author 268084
	 * @date 
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IWaybillStockDao#queryWaybillStock(com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockDto, int, int)
	 */
	@Override
	public List<WaybillStockQueryDto> queryExpressWaybillStock(
			WaybillStockDto waybillStockDto, int limit, int start) {
		
		RowBounds rowBounds = new RowBounds(start, limit);
		
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "ExpressWaybillStockJoinQuery",waybillStockDto,rowBounds);
	}
	
	/**
	 * 查询运单库存总数
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-16 下午2:03:51
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IWaybillStockDao#queryCount(com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockDto)
	 */
	@Override
	public Long queryCount(WaybillStockDto waybillStockDto) {
		return (Long)this.getSqlSession().selectOne(StockConstants.NAME_SPACE + "getCount",waybillStockDto);
	}
	
	/**
	 * 查询运单库存总数(快递)
	 * @author 268084
	 * @date 
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IWaybillStockDao#queryCount(com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockDto)
	 */
	@Override
	public Long queryExpressWaybillCount(WaybillStockDto waybillStockDto) {
		return (Long)this.getSqlSession().selectOne(StockConstants.NAME_SPACE + "getExpressCount",waybillStockDto);
	}
	/**
	 * 增加运单库存
	 * @author dp-wangqiang
	 * @date 2012-10-12 上午11:16:00
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IWaybillStockDao#saveWaybillStock(com.deppon.foss.module.transfer.stock.api.shared.domain.WaybillStockEntity)
	 */
	@Override
	public int addWaybillStock(WaybillStockEntity waybillStockEntity) { 
		waybillStockEntity.setId(UUIDUtils.getUUID());
		this.getSqlSession().insert(StockConstants.NAME_SPACE + "insertWaybillStockEntity", waybillStockEntity);
		return FossConstants.SUCCESS;
	}
	/**
	 * 更新运单库存的在库件数
	 * @author dp-wangqiang
	 * @date 2012-10-12 上午11:16:25
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IWaybillStockDao#updateWaybillStock(com.deppon.foss.module.transfer.stock.api.shared.domain.WaybillStockEntity)
	 */
	@Override
	public int updateWaybillStockInStock(WaybillStockEntity waybillStockEntity) {
		this.getSqlSession().update(StockConstants.NAME_SPACE + "updateWaybillStockInStock", waybillStockEntity);
		return FossConstants.SUCCESS;
	}
	
	/**
	* @description 入库更新运单库存(批量)
	* @param waybillStock
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年3月9日 上午10:40:25
	*/
	@Override
	public int updateWaybillStockInStockBatch(WaybillStockEntity waybillStockEntity) {
		this.getSqlSession().update(StockConstants.NAME_SPACE + "updateWaybillStockInStockBatch", waybillStockEntity);
		return FossConstants.SUCCESS;
	}
	/**
	 * 查询运单库存
	 * @author dp-wangqiang
	 * @date 2012-10-12 上午9:30:38
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IWaybillStockDao#findWaybillStock(com.deppon.foss.module.transfer.stock.api.shared.domain.WaybillStockEntity)
	 */
	@Override
	public List<WaybillStockEntity> queryWaybillStock(
			WaybillStockEntity waybillStock) {
		
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "waybillStockQuery", waybillStock);
	}
	/**
	 * 删除运单库存
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-18 下午2:43:39
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IWaybillStockDao#deleteWaybillStock(com.deppon.foss.module.transfer.stock.api.shared.domain.WaybillStockEntity)
	 */
	@Override
	public int deleteWaybillStock(WaybillStockEntity waybillStock) {  
		this.getSqlSession().delete(StockConstants.NAME_SPACE + "deleteWaybillStock", waybillStock);
		return FossConstants.SUCCESS;
	}
	/**
	 * 分页查询必走货
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-30 下午3:01:07
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IWaybillStockDao#queryPriorityGoods(com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockQueryDto)
	 */
	@Override
	public List<WaybillStockQueryDto> queryPriorityGoods(
			WaybillStockQueryDto waybillStockQueryDto,int limit, int start) {
		
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "queryPriorityGoods", waybillStockQueryDto,rowBounds);
	}
	/**
	 * 查询必走货总记录数
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-30 下午4:04:19
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IWaybillStockDao#queryPriorityGoodsCount(com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockQueryDto)
	 */
	@Override
	public Long queryPriorityGoodsCount(
			WaybillStockQueryDto waybillStockQueryDto) {  
		
		return (Long)this.getSqlSession().selectOne(StockConstants.NAME_SPACE + "queryPriorityGoodsCount",waybillStockQueryDto);
	}
	/**
	 * 查询库存 统计
	 * @author 097457-foss-wangqiang
	 * @date 2013-1-5 上午9:52:37
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IWaybillStockDao#queryWaybillStockStatistics(com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockDto)
	 */
	@Override
	public WaybillStockStatisticsDto queryWaybillStockStatistics(
			WaybillStockDto waybillStockDto) {
		return (WaybillStockStatisticsDto)this.getSqlSession().selectOne(StockConstants.NAME_SPACE + "queryWaybillStockStatistics", waybillStockDto);
	}
	
	/**
	 * 查询库存 统计(快递)
	 * @author 268084
	 * @date 
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IWaybillStockDao#queryWaybillStockStatistics(com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockDto)
	 */
	@Override
	public WaybillStockStatisticsDto queryExpressWaybillStockStatistics(
			WaybillStockDto waybillStockDto) {
		return (WaybillStockStatisticsDto)this.getSqlSession().selectOne(StockConstants.NAME_SPACE + "queryExpressWaybillStockStatistics", waybillStockDto);
	}
	/**
	 * 查询必走货 统计
	 * @author 097457-foss-wangqiang
	 * @date 2013-1-6 下午2:21:15
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IWaybillStockDao#queryPriorityGoodsStatistics(com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockQueryDto)
	 */
	@Override
	public WaybillStockStatisticsDto queryPriorityGoodsStatistics(
			WaybillStockQueryDto waybillStockQueryDto) {
		return (WaybillStockStatisticsDto)this.getSqlSession().selectOne(StockConstants.NAME_SPACE + "queryPriorityGoodsStatistics", waybillStockQueryDto);
	}
	/**
	 * 根据运单号、流水号查询运单库存
	 * @author 097457-foss-wangqiang
	 * @date 2013-3-18 下午4:20:30
	 */
	@Override
	public List<WaybillStockEntity> queryWaybillStockByWaybillOrgCode(
			String waybillNo, String orgCode) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("waybillNo", waybillNo);
		paramsMap.put("orgCode", orgCode);
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "queryWaybillStockByWaybillOrgCode", paramsMap);
	}
	/**
	 * 更改运单库存运单号
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
		this.getSqlSession().update(StockConstants.NAME_SPACE + "updateWaybillNoWaybillStock", paramsMap);
	}
	
	/* (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IWaybillStockDao#queryPriorityGoods(com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockQueryDto)
	 */
	@Override
	public List<WaybillStockQueryDto> queryPriorityGoods(WaybillStockQueryDto waybillStockQueryDto) {
		
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "queryPriorityGoods", waybillStockQueryDto);
	}
	/**
	 * 出库更新运单库存
	 * @author 097457-foss-wangqiang
	 * @date 2013-5-13 下午2:54:20
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IWaybillStockDao#updateWaybillStockOutStock(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public int updateWaybillStockOutStock(String waybillNo,
			String goodsAreaCode, String orgCode) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("waybillNo", waybillNo);
		paramsMap.put("goodsAreaCode", goodsAreaCode);
		paramsMap.put("orgCode", orgCode);
		this.getSqlSession().update(StockConstants.NAME_SPACE + "updateWaybillStockOutStock", paramsMap);
		return FossConstants.SUCCESS;
	}
	/**
	 * 根据运单号查询库存部门编号
	 * @author 097457-foss-wangqiang
	 * @date 2013-5-24 下午4:02:53
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IWaybillStockDao#queryStockOrgCodeByWaybillNo(java.lang.String)
	 */
	@Override
	public List<String> queryStockOrgCodeByWaybillNo(String waybillNo) {
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "queryStockOrgCodeByWaybillNo", waybillNo);
	}
	/**
	 * 根据运单号查询库存件数
	 * @author 097457-foss-wangqiang
	 * @date 2013-5-27 上午10:31:04
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IWaybillStockDao#queryStockQtyByWaybillNo(java.lang.String)
	 */
	@Override
	public List<Integer> queryStockQtyByWaybillNo(String waybillNo) {
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "queryStockQtyByWaybillNo", waybillNo);
	}
	/**
	 * 根据运单号查询运单库存
	 * @author 216208
	 * @date 2015-2-5 上午10:31:04
	 */
	@Override
	public List<WaybillStockEntity> queryWaybillStockByWaybillNo(String waybillNo) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("waybillNo", waybillNo);
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "queryWaybillStockByWaybillNo", paramsMap);
	}
	/**
	 * 根据运单号、部门CODE查询运单库存总件数
	 * @author 216208
	 * @date 2015-2-5 上午10:31:04
	 */
	@Override
	public int querySumStockGoodsQtyByWaybillOrgCode(String waybillNo,
			String orgCode) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("waybillNo", waybillNo);
		paramsMap.put("orgCode", orgCode);
		return (Integer) this.getSqlSession().selectOne(StockConstants.NAME_SPACE + "querySumStockGoodsQtyByWaybillOrgCode", paramsMap);
	}
	
	/**
	 * 根据运单号s、部门CODE查询运单库存总件数
	 * @author 218442
	 * @date 2015-08-28 上午10:31:04
	 */
	@Override
	public List<WaybillStockEntity> querySumStockGoodsQtyByWaybillsOrgCode(String[] waybillNos,
			String orgCode) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("waybillNos", waybillNos);
		paramsMap.put("orgCode", orgCode);
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "querySumStockGoodsQtyByWaybillsOrgCode", paramsMap);
	}
	
	/**
	 * 根据运单号、部门CODE更新运单库存总件数和货区编码
	 * @author 216208
	 * @date 2015-2-5 上午10:31:04
	 */
	@Override
	public int updateWaybillStockGoodsArea(WaybillStockEntity waybillstock) {
		this.getSqlSession().update(StockConstants.NAME_SPACE + "updateWaybillStockGoodsArea", waybillstock);
		return FossConstants.SUCCESS;
	}
	
	
	/**
	* @description 查询上计泡机快递运单的总重量 和 总体积
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IWaybillStockDao#queryBcmWaybillPriorityGoodsStatistics(com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockQueryDto)
	* @author 105869-foss-heyongdong
	* @update 2015年5月22日 上午10:21:51
	* @version V1.0
	*/
	@Override
	public WaybillStockStatisticsDto queryBcmWaybillPriorityGoodsStatistics(
			WaybillStockQueryDto waybillStockQueryDto) {
		// TODO Auto-generated method stub
		return (WaybillStockStatisticsDto) this.getSqlSession().selectOne(StockConstants.NAME_SPACE+"queryBcmWaybillPriorityGoodsStatistics", waybillStockQueryDto);
	}
	
	/**
	* @description 查询不上计泡机快递运单的总总量 和 体积
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IWaybillStockDao#queryNoBcmWaybillPriorityGoodsStatistics(com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockQueryDto)
	* @author 105869-foss-heyongdong
	* @update 2015年5月22日 上午10:22:20
	* @version V1.0
	*/
	@Override
	public WaybillStockStatisticsDto queryNoBcmWaybillPriorityGoodsStatistics(
			WaybillStockQueryDto waybillStockQueryDto) {
		return  (WaybillStockStatisticsDto) this.getSqlSession().selectOne(StockConstants.NAME_SPACE+"queryNoBcmWaybillPriorityGoodsStatistics", waybillStockQueryDto);
	}

	/**
	 * 
	 * 判断是不是快递
	 * author 268084
	 */
	@Override
	public boolean ifIsExpressWaybill(String waybillNo) {
		Integer count=(Integer) this.getSqlSession().selectOne(StockConstants.NAME_SPACE+"ifIsExpressWaybill", waybillNo);
		System.out.println(count+"<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		if(count>0){
			return true;
		}else{
			return false;
		}
	}


	
	/**
	* @description 根据部门code和waybillNo查找最早到达时间
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IWaybillStockDao#queryArrayTime(com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockQueryDto)
	* @author 218381-foss-lijie
	* @update 2015年12月23日 下午3:57:59
	* @version V1.0
	*/
	@Override
	public Date queryArrayTime(WaybillStockQueryDto waybillStockQueryDto) {
		return (Date) this.getSqlSession().selectOne(StockConstants.NAME_SPACE+"queryArrayTime", waybillStockQueryDto);
	}


	
	/**
	* @description 根据部门code和waybillNo查找最早到达时间(快递)
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.dao.IWaybillStockDao#queryExpressArrayTime(com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockQueryDto)
	* @author 218381-foss-lijie
	* @update 2015年12月23日 下午5:18:18
	* @version V1.0
	*/
	@Override
	public Date queryExpressArrayTime(WaybillStockQueryDto waybillStockQueryDto) {
		return (Date) this.getSqlSession().selectOne(StockConstants.NAME_SPACE+"queryExpressArrayTime", waybillStockQueryDto);
	}
	
	/**营业部交接
	 * 分页查询运单库存关联运单表
	 * @author 360903
	 * @date 2016年9月19日 23:03:17
	 */
	@Override
	public List<WaybillStockSaleEntity> queryWaybillStockSale(
			WaybillStockSaleEntity waybillStockSale) {
		return this.getSqlSession().selectList(StockConstants.NAME_SPACE + "waybillStockSaleQuery", waybillStockSale);
	}
	
	/**营业部交接
	 * 增加运单库存
	 * @author 360903 linhua.yan
	 * @date 2016年9月19日 23:20:47
	 */
	@Override
	public int addWaybillStockSale(WaybillStockSaleEntity waybillStockSaleEntity) { 
		waybillStockSaleEntity.setId(UUIDUtils.getUUID());
		this.getSqlSession().insert(StockConstants.NAME_SPACE + "insertWaybillStockSaleEntity", waybillStockSaleEntity);
		return FossConstants.SUCCESS;
	}
	
	/**营业部交接
	 * 更新运单库存的在库件数
	 * @author 360903 linhua.yan
	 * @date 2016年9月19日 23:40:13
	 */
	@Override
	public int updateWaybillStockSaleInStock(WaybillStockSaleEntity waybillStockSaleEntity) {
		this.getSqlSession().update(StockConstants.NAME_SPACE + "updateWaybillStockSaleInStock", waybillStockSaleEntity);
		return FossConstants.SUCCESS;
	}
	
	/**营业部交接
	* @description 入库更新运单库存(批量)
	* @param waybillStock
	* @author 360903
	* @update 2016年9月20日 04:38:13
	*/
	@Override
	public int updateWaybillStockSaleInStockBatch(WaybillStockSaleEntity waybillStockSaleEntity) {
		this.getSqlSession().update(StockConstants.NAME_SPACE + "updateWaybillStockSaleInStockBatch", waybillStockSaleEntity);
		return FossConstants.SUCCESS;
	}
	
	/**营业部交接
	 * 交接单作废入库
	 * @author 360903
	 * @date 2016年10月19日 14:37:39
	 */
	@Override
	public int inWaybillStockSalePC(String orgCode,String waybillNo) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orgCode", orgCode);
		map.put("waybillNo", waybillNo);
		this.getSqlSession().update(StockConstants.NAME_SPACE + "inWaybillStockSalePC", map);
		return FossConstants.SUCCESS;
	}
	
	/**营业部交接
	 * 交接单单票入库出库
	 * @author 360903
	 * @date 2016年10月19日 14:37:39
	 */
	@Override
	public int outWaybillStockSalePC(String waybillNo) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("waybillNo", waybillNo);
		this.getSqlSession().update(StockConstants.NAME_SPACE + "outWaybillStockSalePC", map);
		return FossConstants.SUCCESS;
	}
	
	/**营业部交接
	 * 分批配载出库
	 * @author 360903
	 * @date 2016年10月21日 15:33:32
	 */
	@Override
	public int outWaybillStockSaleSerialNo(InOutStockEntity outStock){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orgCode", outStock.getOrgCode());
		map.put("waybillNo", outStock.getWaybillNO());
		this.getSqlSession().update(StockConstants.NAME_SPACE + "outWaybillStockSaleSerialNo", map);
		return FossConstants.SUCCESS;
	}
	
	/**营业部交接
	 * 批量运单出库
	 * @author 360903
	 * @date 2016年9月20日 15:46:19
	 */
	@Override
	public int outWaybillStockSaleAll(String orgCode,List<String> waybillList) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orgCode", orgCode);
		map.put("waybillList", waybillList);
		this.getSqlSession().update(StockConstants.NAME_SPACE + "outWaybillStockSale", map);
		return FossConstants.SUCCESS;
	}
}