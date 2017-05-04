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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/stock/api/server/dao/IStockDao.java
 *  
 *  FILE NAME          :IStockDao.java
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
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockPositionNumberEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.StockSaleEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.WaybillStockEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.WaybillStockSaleEntity;
import com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockDetailDto;
import com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockDto;
import com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockQueryDto;
import com.deppon.foss.module.transfer.stock.api.shared.dto.WaybillStockStatisticsDto;
/**
 * 本接口定义了货件库存的增、删、查操作
 * @author 097457-foss-wangqiang
 * @date 2012-10-16 下午1:34:26
 */
public interface IStockDao{
	/**
	 * 根据运单号、部门、货区查询货件库存
	 * @param waybillStockEntity.waybillNO  运单号
	 * @param waybillStockEntity.orgCode  部门编号
	 * @param waybillStockEntity.goodsAreaCode  货区编号
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-16 下午1:34:22
	 */
	List<StockEntity> queryStock(WaybillStockEntity waybillStockEntity);
	
	/**
	 * 根据运单号查询货件库存
	 * @param waybillStockEntity
	 * @return
	 */
	List<StockEntity> queryStockByWaybillNo(WaybillStockEntity waybillStockEntity);
	
	/**
	 * 根据CRM传过来的运单号查询货件库存
	 * @author 273247
	 * @return
	 */
	public List<StockEntity> queryStockByCrmWaybillNo(String waybillNo); 
	
	/**
	 * 根据运单编号查询库存中的当前部门
	 * @author 336540
	 * @date 2016-10-16 11:20:50
	 */
	public String stockQueryOrgCodeByWaybillNo(String waybillNo);
			
	/**
	 * 根据运单号、流水号查询唯一货件库存
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-23 下午3:14:44
	 */
	List<StockEntity> queryUniqueStock(InOutStockEntity inOutStockEntity);
	
	/**
	 * 根据货件库存ID的List查询需要导出的货件库存信息
	 * @param idsList 货件ID的List
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-29 上午11:46:57
	 */
	List<WaybillStockQueryDto> queryExportStock(List<String> idsList);
	/**
	 * 根据查询库存界面的查询条件查询需要导出的货件库存信息
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-5 下午3:01:34
	 */
	List<WaybillStockQueryDto> queryExportStock(WaybillStockDto waybillStockDto);
	
	/**
	 * 增加货件库存
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-16 下午1:36:16
	 */
	int addStock(StockEntity stockEntity);
	/**
	 * 删除货件库存
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-16 下午1:37:44
	 */
	int deleteStock(InOutStockEntity inOutStockEntity);
	/**
	 * 查询某一运单下未在本部门库存的货件
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-18 上午9:32:35
	 */
	List<StockEntity> queryGoods(InOutStockEntity inOutStockEntity, RowBounds rowBounds);
	/**
	 * 查询某一运单下未在本部门库存的货件总数
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-18 上午9:34:09
	 */
	Long queryGoodsCount(InOutStockEntity inOutStockEntity);
	/**
	 * 判断货件是否存在该部门库存
	 * @param orgCode 部门
	 * @param waybillNO 运单号
	 * @param serialNO 流水号
	 * @return true： 不存在    false： 存在
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-18 上午9:31:15
	 */
	StockEntity queryStockEntityByNos(InOutStockEntity inOutStockEntity);
	
	/**
	 * 判断货件是否存在该部门虚拟库存
	 * @param orgCode 部门
	 * @param waybillNO 运单号
	 * @param serialNO 流水号
	 * @return true： 不存在    false： 存在
	 * @author 332219-foss
	 * @date 2012-10-18 上午9:31:15
	 */
	StockSaleEntity querySaleStockEntityByNos(InOutStockEntity inOutStockEntity);
	
	/**
	 * 通过库区编号返回此库区下所有库存信息列表
	 * @author foss-wuyingjie
	 * @date 2012-10-25 下午2:18:00
	 */
	List<StockEntity> queryStockByGoodsAreaCode(String deptCode, String goodsAreaCode);
	
	/**
	 * 传入运单号、流水号、库存部门code，更新流水号库存的预配状态
	 * @author 045923-foss-shiwei
	 * @date 2012-11-28 下午6:46:31
	 */
	boolean updatePreHandOverState(StockEntity stockEntity);

	/**
	 * 传入运单号、流水号、库存部门code，更新流水号库存的预配状态
	 * @author 332219-foss
	 * @date 2016-11-23
	 */
	boolean updatePreSaleHandOverState(StockSaleEntity stockSaleEntity);
	
	/**
	 * 查找本部门非某货区下的货件是否存在，返回个数
	 * @author foss-wuyingjie
	 * @date 2012-11-30 下午5:36:54
	 */
	int isExistOtherGoodsAreaStock(String orgCode, String waybillNO, String serialNO, String goodsAreaCode);
	
	/**
	 * 根据部门查询库存件数
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-26 下午8:50:18
	 */
	List<Long> queryStockGoodsQty(String orgCode);
	
	/**
	 * 批量出库
	 * @author 097457-foss-wangqiang
	 * @date 2013-1-26 上午10:47:48
	 */
	@SuppressWarnings("rawtypes")
	Map outStockBatchPC(List<InOutStockEntity> outStockList);
	/**
	 * 按照类型批量出库
	* @Description: TODO
	* @return: Map
	* @author: Xingmin , 2016-9-23 上午11:19:12
	 */
	@SuppressWarnings("rawtypes")
	 Map outStockBatchPCByType(List<InOutStockEntity> outStockList,String type, String id);
	/**
	 * 开单入库
	 * @author 097457-foss-wangqiang
	 * @date 2013-1-24 下午3:15:42
	 */
	@SuppressWarnings("rawtypes")
	Map inStockCreateBill(List<InOutStockEntity> inStockList);
	/**
	 * 反签收入库
	 * @author 097457-foss-wangqiang
	 * @date 2013-3-14 上午11:00:19
	 */
	@SuppressWarnings("rawtypes")
	Map inStockReverseSign(List<InOutStockEntity> inStockList);
	/**
	 * 移除货件
	 * 1.货件在库存中：出库、删除走货路径
	 * 2.货件不在库存中：不做任何操作
	 * @author 097457-foss-wangqiang
	 * @date 2013-3-14 下午6:09:03
	 */
	@SuppressWarnings("rawtypes")
	Map outStockInvalidGoods(List<InOutStockEntity> outStockList); 
	
	/**
	 * 更改货件库存运单号
	 * @param oldWaybillNo 原运单号
	 * @param newWaybillNo 新运单号
	 * @author 097457-foss-wangqiang
	 * @date 2013-3-18 下午4:39:33
	 */
	void updateWaybillNo(String oldWaybillNo, String newWaybillNo);
	
	/**
	 * 查询运单里的货件库存信息
	 * @param waybillNo
	 * @author 097457-foss-wangqiang
	 * @date 2013-4-8 上午9:58:30
	 */
	List<WaybillStockDetailDto> queryWaybillStockDetail(String waybillNo);
	
	/**
	* @param waybillNo 运单号
	* @param orgCode 当前部门编号
	* @return 
	* @description 根据运单号和当前部门编号查询库存
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-6-22 上午11:32:02
	*/
	List<StockEntity> queryStockByWaybillNoOrgCode(String waybillNo,String orgCode);
	
	
	
	/**
	* @description 根据运单号分析库存表里存在的库存件数以及入库时间
	* @param waybillNo
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年3月9日 上午10:15:28
	*/
	List<WaybillStockEntity> queryStockByWaybillNoForGroup(String waybillNo);
	
	/**
	 * @param waybillNo
	 * @param serialNo
	 * @param orgCode
	 * @description 更新是否已经建包
	 */
	public void updateIsPackage(String waybillNo, String serialNo, String orgCode);
	
	
	
	/**
	* @description 根据当前部门code 以及库区编码 查询库存里下一部门的code以及下一部门的Name(去除重复部门) 用于下拉菜单使用
	* @param orgCode
	* @param goodArea
	* @param orgName 部门名称 用于模糊查询 非必填
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年2月14日 下午3:52:18
	*/
	List<StockEntity> queryNextOrgByStock(String orgCode,String goodArea,String orgName,int start,int limit);
	
	
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
	Integer queryNextOrgByStockCount(String orgCode, String goodArea,String orgName);
	
	/**
	 * @param waybillNo 运单号
	 * @param serialNo  流水号
	 * @param orgCode   部门编号
	 * @param stockPositionNumber  定位编号
	 * @return 根据运单号、流水号、部门编号来获取运单号、流水号、定位编号
	 * @description
	 * @version 1.0
	 * @author 200968-foss-zwd
	 * @update 2014-12-18 下午5:21:46
	 */

	List<StockPositionNumberEntity> queryStockPositionNumber(String waybillNo,
			 String orgCode);

	/**
	 * @param waybillNo 运单号
	 * @param serialNo  流水号
	 * @param orgCode   部门编号
	 * @param stockPositionNumber  定位编号
	 * @return 根据运单号、流水号、部门编号来存储定位编号到库存表中
	 * @description
	 * @version 1.0
	 * @author 200968-foss-zwd
	 * @update 2014-12-19 上午8:19:46
	 */
	void saveStockPositionNumber(StockPositionNumberEntity stockPositionNumberEntity) ;
	/**
	* @param waybillNo 运单号
	* @param serialNo  流水号
	* @param stockPositionNumber  定位编号
	* @return 查找所有的运单号、流水号、定位编号
	* @description 
	* @version 1.0
	* @author 200968-foss-zwd
	* @update 2014-12-20 下午14:09:46
	*/
	public List<StockPositionNumberEntity> queryAllStockPositionNumber();
	
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
			String orgCode,String stockPositionNumber) ;
	
	/**
	* @param waybillNo 运单号
	* @param serialNo  流水号
	* @param stockPositionNumber 定位编号
	* @return 根据运单号、流水号来删除定位编号
	* @description 
	* @version 1.0
	* @author 200968-foss-zwd
	* @update 2014-12-21 上午9:10:46
	*/
	void deleteStockPositionNumber(String waybillNo,String serialNo,String orgCode);

	/**
	* @param waybillNo 运单号
    * @param orgCode   部门编号
	* @return 根据运单号、部门编号来获取库存表
	* @description 
	* @version 1.0
	* @author 200968-foss-zwd
	* @update 2015-8-16 下午15:31:46
	*/
	public List<StockEntity> queryStockByWO(String waybillNo,String orgCode);
	
	
	/**
	* @param waybillNo 运单号
	* @param serialNo  流水号
    * @param orgCode   部门编号
	* @return 根据运单号、流水号、部门编号来获取库存表
	* @description 
	* @version 1.0
	* @author 200968-foss-zwd
	* @update 2014-12-25 下午14:31:46
	*/
	public StockEntity queryStockByWSO(String waybillNo,String serialNo,String orgCode);
	 /**
	    * @param String  waybillNo   原单单号
	 	* @return 返货开单 入库    改变原单的库存状态
	 	* @description 
	 	* @version 1.0
	 	* @author 216208
	 	* @update 2015-02-02 下午14:21:46    String waybillNo
	 	*/
	public void updateReturnGoodsState(StockEntity stockEntity) ;
	
	/**
	* @param waybillNo 运单号
	* @param serialNo  流水号
    * @param orgCode   部门编号
	* @return 批量入库有异常，重新写回 批量入库的临时表。
	* @description 
	* @version 1.0
	* @author 140222-foss-songjie
	* @update 2014-12-25 下午14:31:46
	*/
	public void insertPdaUnloadmsg(InOutStockEntity inOutStockEntity);
	
	
	/**
	* @description 在丢货改善小组超过28天的运单
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年5月8日 下午4:11:23
	*/
	public List<String> dayLoseGoodsForGuiji28();

	List<InOutStockEntity> queryInStockInfoByType(String waybillNo,	String serialNo, List<String> inStockTypeList);
	/**
	 * @Description:根据运单号和流水号查询库存信息（包含货区名称），供接送货调用
	 * @date 2015-7-13 上午10:50:37   
	 * @author 263072 
	 * @param wayBillNo
	 * @param serialNo
	 * @return
	 */
	public List<StockEntity> queryStockAndGoodsArea(String wayBillNo,String serialNo);	/**
	* @description 菜鸟子母件判断 当前单号是否为丢货找到
	* @return
	* @version 1.0
	* @author 218427-foss-hwy
	* @update 2015年9月8日 上午10:11:23
	*/
	public InOutStockEntity queryLostFindGoods(String waybillNo, String serialNo);
	
	/**
	 * 根据运单号查询货件库存
	 * 按照入库时间排序
	 * 200968 2016-3-16
	 * @param waybillStockEntity
	 * @return
	 */
	List<StockEntity> queryStockByWaybillNoInStockTime(WaybillStockEntity waybillStockEntity);




	/**
	 * 根据运单号,部门code查询运单数量
	 * @param waybillNo
	 * @param OrgCode
	 * @return
	 */
	public int queryCountByWaybillNoAndOrgCode(String waybillNo,String orgCode);
	
	
	List<String> queryGoodsAreaCodes(String orgCode, String goodsAreaType);

	/**
	 * 根据部门查询库存总件数和总票数
	 * @author 272681-foss-chenlei
	 * @date 2016-02-19 下午14:49:26
	 * @see com.deppon.foss.module.transfer.stock.api.server.service.IStockService#queryStockGoodsQtyAndWaybillQty(java.lang.String)
	 */
	WaybillStockStatisticsDto queryStockGoodsQtyAndWaybillQty(String orgCode);
	
	/**营业部交接
	 * 根据运单号、流水号查询唯一货件库存 
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @author 360903 linhua.yan 
	 * @date 2016年9月19日
	 */
	List<StockSaleEntity> queryUniqueStockSale(InOutStockEntity inOutStockEntity);
	
	/**营业部交接
	 * 增加货件库存
	 * @author 360903 linhua.yan
	 * @date 2016年9月19日 22:33:22
	 */
	int addStockSale(StockSaleEntity stockSaleEntity);
	
	/**营业部交接
	* @description 根据运单号分析库存表里存在的库存件数以及入库时间
	* @param waybillNo
	* @author 360903 linhua.yan
	* @update 2016年9月20日 04:34:37
	*/
	List<WaybillStockSaleEntity> queryStockSaleByWaybillNoForGroup(String waybillNo);
	
	/**营业部交接
	 * 批量出库
	 * @author 360903
	 * @date 2016年9月20日 16:04:31
	 */
	int outStockSaleBatchPC(InOutStockEntity outStock);
	
	/**营业部交接
	 * PC端操作批量出库
	 * @param currentOrgCode  出发部门
	 * @param destOrgCode   到达部门
	 * @param waybills  出库的运单集合
	 * @author 360903
	 * @date 2016年9月20日 15:40:29
	 */
	int outWaybillStockSaleBatchPC(String orgCode,List<String> waybillList);
	
	/**营业部交接
	 * 判断货件是否存在该部门库存
	 * @param orgCode 部门
	 * @param waybillNO 运单号
	 * @param serialNO 流水号
	 * @author 360903
	 * @date 2016年9月20日 17:06:29
	 */
	StockSaleEntity queryStockSaleEntityByNos(InOutStockEntity inOutStockEntity);
	
	/**营业部交接
	 * 传入运单号、流水号、库存部门code，更新流水号库存的预配状态
	 * @author 360903
	 * @date 2016年9月20日 17:21:25
	 */
	boolean updatePreHandOverStateSale(StockSaleEntity stockSaleEntity);
	
	/**营业部交接
	 * 根据运单查询货件库存 
	 * @param  运单集合
	 * @author 360903 linhua.yan 
	 * @date 2016年9月19日
	 */
	List<StockSaleEntity> queryInStockInfoBywayBillList(List<String> waybillLlist,String orgCode);
	
	/**营业部交接
	 * 交接单作废入库
	 * @author 360903
	 * @date 2016年9月20日 16:04:31
	 */
	int inStockSalePC(InOutStockEntity inStock);
	
	/**营业部交接
	 * 交接单单票入库出虚拟库
	 * @author 360903
	 * @date 2016年10月20日 19:16:42
	 */
	int outStockSalePC(InOutStockEntity outStock);
	
	/**
	 * tfr.t_opt_waybill_stock_sale先查询运单在此部门的库存件数
	 * @author 218427
	 * @date 2016年12月28日 17:20:42
	 */
	int querySaleCount(String waybillNo, String orgCode);

	/**
	 * 更新虚拟库存件数
	 * @author 218427
	 * @date 2016年12月28日 17:28:42
	 */
	void updateSaleCount(int m,String waybillNo,String orgCode);
	
	/**
	 * 流水全部出库则删除对应的运单库存记录
	 * @author 218427
	 * @date 2016年12月28日 17:50:21
	 */
	void deleteSaleCount(String waybillNo,String orgCode);

	
}

