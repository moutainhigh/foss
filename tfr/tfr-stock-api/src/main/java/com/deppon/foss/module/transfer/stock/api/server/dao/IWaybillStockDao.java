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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stock/api/server/dao/IWaybillStockDao.java
 *  
 *  FILE NAME          :IWaybillStockDao.java
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

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.WaybillStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.WaybillStockSaleEntity;
import com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockDto;
import com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockQueryDto;
import com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockStatisticsDto;


/**
 * 本接口定义了对运单库存的增、查、改操作
 * @author 097457-foss-wangqiang
 * @date 2012-10-16 下午1:45:25
 */
public interface IWaybillStockDao{
	
	/**
	 * 分页查询运单库存表(快递员)
	 * @param waybillStockDto
	 * @param limit
	 * @param start
	 * @return
	 */
	public List<WaybillStockQueryDto> queryExpressWaybillStock(
			WaybillStockDto waybillStockDto, int limit, int start);
	
	/**
	 * 分页查询运单库存关联运单表
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-16 上午11:50:47
	 */
	List<WaybillStockQueryDto> queryWaybillStock(WaybillStockDto waybillStockDto,
			int limit, int start);
	
	
	
	/**
	* @description 根据部门code和waybillNo查找最早到达时间
	* @param dto
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年12月23日 下午3:55:16
	*/
	Date queryArrayTime(WaybillStockQueryDto dto);
	
	
	/**
	* @description 根据部门code和waybillNo查找最早到达时间(快递)
	* @param dto
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年12月23日 下午5:17:50
	*/
	Date queryExpressArrayTime(WaybillStockQueryDto dto);
	/**
	 * 统计
	 * @author 097457-foss-wangqiang
	 * @date 2013-1-5 上午9:47:55
	 */
	WaybillStockStatisticsDto queryWaybillStockStatistics(WaybillStockDto waybillStockDto);
	/**
	 * 统计(快递)
	 * @author 268084
	 * @date 
	 */
	WaybillStockStatisticsDto queryExpressWaybillStockStatistics(
			WaybillStockDto waybillStockDto);
	/**
	 * 查询运单库存
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-16 上午11:49:34
	 */
	List<WaybillStockEntity> queryWaybillStock(WaybillStockEntity waybillStock);
	/**
	 * 查询运单库存总数
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-16 上午11:51:44
	 */
	Long queryCount(WaybillStockDto waybillStockDto);
	/**
	 * 查询运单库存总数(快递)
	 * @param waybillStockDto
	 * @return
	 */
	Long queryExpressWaybillCount(WaybillStockDto waybillStockDto);
	/**
	 * 增加运单库存
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-16 上午11:52:12
	 */
	int addWaybillStock(WaybillStockEntity waybillStock);
	/**
	 * 入库更新运单库存
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-16 上午11:53:05
	 */
	int updateWaybillStockInStock(WaybillStockEntity waybillStock);
	
	
	/**
	* @description 入库更新运单库存(批量)
	* @param waybillStock
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年3月9日 上午10:40:25
	*/
	int updateWaybillStockInStockBatch(WaybillStockEntity waybillStock);
	/**
	 * 出库更新运单库存
	 * @author 097457-foss-wangqiang
	 * @date 2013-5-13 下午2:51:55
	 */
	int updateWaybillStockOutStock(String waybillNo, String goodsAreaCode, String orgCode);
	/**
	 * 删除运单库存
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-18 下午2:41:15
	 */
	int deleteWaybillStock(WaybillStockEntity waybillStock);
	/**
	 * 分页查询必走货
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-30 下午3:00:20
	 */
	List<WaybillStockQueryDto> queryPriorityGoods(WaybillStockQueryDto waybillStockQueryDto,int limit, int start);
	/**
	 * 查询必走货总记录数
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-30 下午4:03:02
	 */
	Long queryPriorityGoodsCount(WaybillStockQueryDto waybillStockQueryDto);
	/**
	 * 查询必走货 统计
	 * @author 097457-foss-wangqiang
	 * @date 2013-1-6 下午2:18:08
	 */
	WaybillStockStatisticsDto queryPriorityGoodsStatistics(WaybillStockQueryDto waybillStockQueryDto);
	/**
	 * 根据运单号、流水号查询运单库存
	 * @author 097457-foss-wangqiang
	 * @date 2013-3-18 下午4:20:30
	 */
	List<WaybillStockEntity> queryWaybillStockByWaybillOrgCode(String waybillNo, String orgCode);
	/**
	 * 更改运单库存运单号
	 * @param oldWaybillNo 原运单号
	 * @param newWaybillNo 新运单号
	 * @author 097457-foss-wangqiang
	 * @date 2013-3-18 下午4:39:33
	 */
	void updateWaybillNo(String oldWaybillNo, String newWaybillNo);
	
	/**
	 * 
	 * 查询必走货所有信息
	 * @author foss-wuyingjie
	 * @date 2013-04-25 上午 11:00:20
	 * 
	 * @param waybillStockQueryDto
	 * @return
	 */
	List<WaybillStockQueryDto> queryPriorityGoods(WaybillStockQueryDto waybillStockQueryDto);
	/**
	 * 根据运单号查询库存部门编号
	 * @author 097457-foss-wangqiang
	 * @date 2013-5-24 下午3:57:47
	 */
	List<String> queryStockOrgCodeByWaybillNo(String waybillNo);
	/**
	 * 根据运单号查询库存件数
	 * @author 097457-foss-wangqiang
	 * @date 2013-5-27 上午10:29:44
	 */
	List<Integer> queryStockQtyByWaybillNo(String waybillNo);
	/**
	 * 根据运单号查询运单库存
	 * @author 216208
	 * @date 2015-2-5 上午10:31:04
	 */
	public List<WaybillStockEntity> queryWaybillStockByWaybillNo(String waybillNo);
	/**
	 * 根据运单号、部门CODE查询运单库存总件数
	 * @author 216208
	 * @date 2015-2-5 上午10:31:04
	 */
     public int querySumStockGoodsQtyByWaybillOrgCode(String waybillNo, String orgCode);
     /**
 	 * 根据运单号s、部门CODE查询运单库存总件数
 	 * @author 218442
 	 * @date 2015-08-28 上午10:31:04
 	 */
 	public List<WaybillStockEntity> querySumStockGoodsQtyByWaybillsOrgCode(String[] waybillNos,String orgCode);
 	/**
 	 * 根据运单号、部门CODE更新运单库存总件数和货区编码
 	 * @author 216208
 	 * @date 2015-2-5 上午10:31:04
 	 */
     public int updateWaybillStockGoodsArea(WaybillStockEntity waybillstock);
	
	/**
	* @description 查询上计泡机的快递总重量 总体积
	* @param waybillStockQueryDto
	* @return
	* @version 1.0
	* @author 105869-foss-heyongdong
	* @update 2015年5月22日 上午9:41:41
	*/
	public WaybillStockStatisticsDto queryBcmWaybillPriorityGoodsStatistics( WaybillStockQueryDto waybillStockQueryDto);
	
	/**
	* @description 查询不上计泡机的总重量 总体积
	* @param waybillStockQueryDto
	* @return
	* @version 1.0
	* @author 105869-foss-heyongdong
	* @update 2015年5月22日 上午9:43:14
	*/
	public WaybillStockStatisticsDto queryNoBcmWaybillPriorityGoodsStatistics( WaybillStockQueryDto waybillStockQueryDto);

	
	/**
	 * 判断是不是快递
	 * @param waybillNo
	 * @return
	 */
	public boolean ifIsExpressWaybill(String waybillNo);
	
	/**营业部交接
	 * 查询运单库存临时表
	 * @author 360903 linhua.yan
	 * @date 2016年9月19日 23:01:13
	 */
	List<WaybillStockSaleEntity> queryWaybillStockSale(WaybillStockSaleEntity waybillStockSale);
	
	/**营业部交接
	 * 增加运单库存临时表
	 * @author 360903
	 * @date 2016年9月19日 23:18:53
	 */
	int addWaybillStockSale(WaybillStockSaleEntity waybillStockSale);
	
	/**营业部交接
	 * 入库更新运单库存
	 * @author 360903
	 * @date 2016年9月19日 23:36:19
	 */
	int updateWaybillStockSaleInStock(WaybillStockSaleEntity waybillSaleStock);
	
	/**营业部交接
	* @description 入库更新运单库存(批量)
	* @param waybillStock
	* @author 360903
	* @update 2016年9月20日 04:43:29
	*/
	int updateWaybillStockSaleInStockBatch(WaybillStockSaleEntity waybillStockSale);
	
	/**营业部交接
	 * 交接单作废入库
	 * @author 360903
	 * @date 2016年10月20日 15:40:29
	 */
	int inWaybillStockSalePC(String orgCode,String waybillNo);
	
	/**营业部交接
	 * 交接单单票入库出库
	 * @author 360903
	 * @date 2016年10月20日 19:24:07
	 */
	int outWaybillStockSalePC(String waybillNo);
	
	/**营业部交接
	 * 分批配载出库
	 * @author 360903
	 * @date 2016年10月21日 15:33:32
	 */
	int outWaybillStockSaleSerialNo(InOutStockEntity outStock);
	
	/**营业部交接
	 * 批量运单出库
	 * @author 360903
	 * @date 2016年9月20日 15:46:19
	 */
	 int outWaybillStockSaleAll(String orgCode,List<String> waybillList);
}
