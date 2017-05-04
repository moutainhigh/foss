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
 *  PROJECT NAME  : tfr-departure-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/server/dao/ITrackingDao.java
 *  
 *  FILE NAME          :ITrackingDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.api.server.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.LoadDetailEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.StockTrackingEntity;
import com.deppon.foss.module.transfer.departure.api.shared.dto.ActualArriveTimeDTO;
import com.deppon.foss.module.transfer.departure.api.shared.dto.HandoverBillDTO;
import com.deppon.foss.module.transfer.departure.api.shared.dto.OnthewayDTO;
import com.deppon.foss.module.transfer.departure.api.shared.dto.StockTrackingDTO;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;

/**
 * 
 * 运单轨迹服务跟踪
 * @author foss-liubinbin(for IBM)
 * @date 2013-1-8 下午12:52:09
 */
public interface ITrackingDao{
	
	/**
	 * 
	 * 通过运单号查询交接单信息
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 下午12:55:13
	 */
	List<HandoverBillDTO> getHandoverBillByWayBillNo(HandoverBillDTO handoverBillDTO);
	
	/**
	 * 
	 * 通过运单号查询交接单信息
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 下午12:55:13
	 */
	List<HandoverBillDTO> getAllHandoverBillByWayBillNo(HandoverBillDTO handoverBillDTO);
	
	/**
	 * 
	 * 通过运单号查询卸车的轨迹
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 下午12:55:13
	 */
	List<HandoverBillDTO> getUnloadBillByWayBillNo(HandoverBillDTO handoverBillDTO);
	
	/**
	 * 
	 * 通过运单号和生成时间取得运单中第一件货的流水号
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-9 上午10:44:22
	 */
	String getFirstSerialNoByWayBillNo(HandoverBillDTO handoverBillDTO);
	
	/**
	 * 
	 * 通过运单号查询交接单信息
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 下午12:55:13
	 */
	List<OnthewayDTO> getTaskTrackingByWayBillNo(HandoverBillDTO handoverBillDTO);
	
	/**
	 * 
	 * 查询库存轨迹
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 下午12:55:13
	 */
	List<StockTrackingDTO> getInStockTrackingByWayBillNo(StockTrackingDTO stockTrackingDTO);
	
	/**
	 * 
	 * 查询库存轨迹
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 下午12:55:13
	 */
	List<StockTrackingDTO> getOutStockTrackingByWayBillNo(StockTrackingDTO stockTrackingDTO);
	
	/**
	 * 
	 * 查询库存轨迹
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 下午12:55:13
	 */
	List<StockTrackingDTO> getStockTrackingByWayBillNo(StockTrackingDTO stockTrackingDTO);
	
	/**
	 * 
	 * 查询库存轨迹（签收）
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 下午12:55:13
	 */
	List<StockTrackingDTO> getSignStockTrackingByWayBillNo(StockTrackingDTO stockTrackingDTO);
	/**
	 * 
	 * 通过运单号查询正单信息
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 下午12:55:13
	 */
	List<AirWaybillEntity> queryAirWayBillListByWaybillNo(String waybillNo);
	/**
	 * 
	 * 通过运单号查询装车信息
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 下午12:55:13
	 */
	List<LoadDetailEntity> getLoadDetailTrackingByWayBillNo(HandoverBillDTO handoverBillDTO);
	
	/**
	 * 
	 * 通过运单号查询登入获取信息
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 下午12:55:13
	 */
	List<HandoverBillDTO> getPackageAreaInByWayBillNo(HandoverBillDTO handoverBillDTO);
	/**
	 * 
	 * 通过运单号查询入库类型
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 下午12:55:13
	 */
	List<String> getInStockTypesWayBillNo(HandoverBillDTO handoverBillDTO);
	/**
	 * 
	 * 通过运单号查询出库类型
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 下午12:55:13
	 */
	List<String> getOutStockTypesWayBillNo(HandoverBillDTO handoverBillDTO);
	
	/**
	 * 
	 * 通过运单号查询登出获取信息
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 下午12:55:13
	 */
	List<HandoverBillDTO> getPackageAreaOutByWayBillNo(HandoverBillDTO handoverBillDTO);
	/**
	 * 根据运单号获取交接单运单列表
	 * @author 045923-foss-liubinbin
	 * @date 2012-10-24 下午5:19:25
	 * @param handOverBillNo 交接单号
	 */
	List<HandoverBillDTO> queryHandOverBillDetailByWaybillNo(HandoverBillDTO handoverBillDTO);
	/**
	 * 根据运单号获取包装信息
	 * @author 045923-foss-liubinbin
	 * @date 2012-10-24 下午5:19:25
	 * @param handOverBillNo 交接单号
	 */
	List<HandoverBillDTO> queryPackageByWaybillNo(String waybillNo);
	/**
	 * 根据运单号、流水号查询唯一货件库存
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-29 上午11:49:59
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IStockDao#queryUniqueStock(com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity)
	 */
	List<StockTrackingEntity> queryUniqueStock(InOutStockEntity inOutStockEntity);
	/**合伙人营业部交接虚拟库存
	 * 根据运单号、流水号查询唯一货件库存
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @author 360903-foss
	 * @date 2016年11月22日 15:28:59
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IStockDao#queryUniqueStock(com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity)
	 */
	List<StockTrackingEntity> queryUniqueStockSale(InOutStockEntity inOutStockEntity);
	/**
	 * 根据运单号、流水号查询唯一货件库存
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-29 上午11:49:59
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IStockDao#queryUniqueStock(com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity)
	 */
	List<StockTrackingEntity> queryUniqueStockNext(InOutStockEntity inOutStockEntity);
	/**合伙人营业部交接虚拟库存
	 * 根据运单号、流水号查询唯一货件库存
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @author 360903-foss
	 * @date 2016年11月22日 16:52:22
	 */
	List<StockTrackingEntity> queryUniqueStockSaleNext(InOutStockEntity inOutStockEntity);
	/**
	 * 查看签收出库的信息
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-29 上午11:49:59
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IStockDao#queryUniqueStock(com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity)
	 */
	List<StockTrackingEntity> querySignedStockQuery(StockTrackingEntity stockTrackingEntity);
	/**
	 * 查看签收出库明细
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-29 上午11:49:59
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IStockDao#queryUniqueStock(com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity)
	 */
	List<StockTrackingEntity> querySignStockQuery(InOutStockEntity inOutStockEntity);
	/**
	 * 通过运单跟流水号（复数）查询最新状态的交接单
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-29 上午11:49:59
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IStockDao#queryUniqueStock(com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity)
	 */
	public List<StockTrackingEntity> queryNewHandover(StockTrackingEntity stockTrackingEntity);
	/**
	 * 通过交接单取得货件号
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-29 上午11:49:59
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IStockDao#queryUniqueStock(com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity)
	 */
	public List<String> querySerialNobyHandoverBill(StockTrackingEntity stockTrackingEntity);
	
	/**
	 * 通过运单号，流水号查询叉车司机扫描信息
	 * @author heyongdong
	 * @date 2014年9月9日 15:30:45
	 * @param HandoverBillDTO handoverBillDTO
	 * */
	List<HandoverBillDTO> getTrayScanByWayBillNo(HandoverBillDTO handoverBillDTO);


	
	/**
	 * 
	 * <p>查询货物到达，签收情况--for CC</p> 
	 * @author alfred
	 * @date 2014-7-31 下午5:03:52
	 * @param waybillNo
	 * @return
	 * @see
	 */
	@SuppressWarnings("rawtypes")
	Map queryWaybillArrStatus(String waybillNo);
	
	/**
	 * 
	 * <p>查询货物是否外发--for CC</p> 
	 * @author alfred
	 * @date 2014-8-8 下午2:45:25
	 * @param waybillNo
	 * @return
	 * @see
	 */
	@SuppressWarnings("rawtypes")
	Map queryWaybillIsOuter(String waybillNo);
	
	/**
	 * 
	 * <p>查询货物运输中状态--for CC</p> 
	 * @author alfred
	 * @date 2014-8-8 下午2:45:25
	 * @param waybillNo
	 * @return
	 * @see
	 */
	@SuppressWarnings("rawtypes")
	Map queryPathdetail(String waybillNo,String billingOrg,String destOrg);
	
	/**
	 * 
	 * <p>查询部门所在城市</p> 
	 * @author alfred
	 * @date 2014-8-8 下午5:22:19
	 * @param orgcode
	 * @return
	 * @see
	 */
	@SuppressWarnings("rawtypes")
	Map  queryCitynameForCC(String orgcode);
	
	/**
	 * 
	 * <p>查询部门名称进行转换</p> 
	 * 1.专线、偏线、转运中心、枢纽中心转换成运输中心
	 * 2.外场、转运场转换为集散中心
	 * 3.空运总调转换为空运调度中心
	 * 4.营业转换为营业网点
	 * 5.派送部转换为派送网点
	 * @author alfred
	 * @date 2014-8-8 下午5:23:42
	 * @param orgcode
	 * @return
	 * @see
	 */
	@SuppressWarnings("rawtypes")
	Map queryOrgtypeForCC(String orgcode);
	
	/**
	 * 建立卸车任务 zwd 20150105 200968
	 * @param handoverBillDTO
	 * @return
	 */
	List<HandoverBillDTO> queryUnloadTaskByWayBillNo(HandoverBillDTO handoverBillDTO);

	
	/**
	 * 提供给综合查询的接口,返回该运单上分拣信息
	 * @author zwd 200968
	 * @date 2014年12月30日 15:15:31
	 * 
	 * */
	List<HandoverBillDTO> querySortingScanByWayBillNo(HandoverBillDTO handoverBillDTO);
	
	/**
	 * 提供给综合查询的接口,返回该运单包扫描信息
	 * @author zwd 200968
	 * @date 2014年12月30日 15:15:31
	 * 
	 * */
	List<HandoverBillDTO> queryExpressPackageDetailByWayBillNo(HandoverBillDTO handoverBillDTO);
	

	/**
	 * 
	 * <p>二程接驳-添加通过运单查询理货员外场装车交接单轨迹</p> 
	 * @author alfred
	 * @date 2015-8-30 上午10:49:35
	 * @param handoverBillDTO
	 * @param serialNo
	 * @return
	 * @see
	 */
	List<HandoverBillDTO> getTallyerHandoverBilllist(HandoverBillDTO handoverBillDTO);
	
	/**
	 * 
	 * <p>二程接驳-添加通过运单查询司机装车交接单轨迹</p> 
	 * @author alfred
	 * @date 2015-8-30 上午10:50:15
	 * @param handoverBillDTO
	 * @param serialNo
	 * @return
	 * @see
	 */
	List<HandoverBillDTO> getDriverHandoverBilllist(HandoverBillDTO handoverBillDTO);

	/**
	 * 提供给综合查询的接口,返回该运单空运通知客户信息
	 * @author zwd 200968
	 * @date 2015年9月15日 15:15:31
	 * */
	List<HandoverBillDTO> queryAirNotifyCustomersSmsInfoByWayBillNo(HandoverBillDTO handoverBillDTO);
	

	/**
	 * 空运到:代理到机场提货 zwd 2015-08-07 200968
	 * @param handoverBillDTO
	 * @param serialNo
	 * @return
	 */
	List<HandoverBillDTO> queryFlightArriveFromAirWaybillNoPickUp(HandoverBillDTO handoverBillDTO);
	
	/**
	 * 空运到达:货物到达代理处  zwd 2015-08-14 200968
	 * @param handoverBillDTO
	 * @param serialNo
	 * @return
	 */
	List<HandoverBillDTO> queryFlightArriveFromAirWaybillNoGoods(HandoverBillDTO handoverBillDTO);
	
	/**
	 * 添加点单任务货物轨迹 cl 2016-01-15 272681
	 * @param handoverBillDTO
	 * @param serialNo
	 * @return
	 */
	List<HandoverBillDTO> queryOrderTaskByWayBillNo(
			HandoverBillDTO handoverBillDTO);
	
	/**
	 * @author 283244
	 * 提供综合页面查询
	 * 
	 * */
	String  querForBaseInfo(String waybill);
	/**
	 * 通过运单号查询实际到达时间
	 * @author foss-wangruipeng
	 * @param waybillNo
	 * @date 2016-4-26 
	 * @return
	 */
	ActualArriveTimeDTO getArriveTimeByWaybillNo(String waybillNo);
	
	/**
	 * 通过运单号查询入库类型
	 * @author foss-332219
	 * @param waybillNo
	 * @date 2016-12-6 
	 * @return
	 */
	String getInStockTypesWayBillNoToPTP(String waybillNo);
	
	/**
	 * 通过运单号查询到达时间
	 * @author foss-332219
	 * @param waybillNo
	 * @date 2016-12-6 
	 * @return
	 */
	Date getArrivalTimeByWaybillNo(String waybillNo);
	
	
}