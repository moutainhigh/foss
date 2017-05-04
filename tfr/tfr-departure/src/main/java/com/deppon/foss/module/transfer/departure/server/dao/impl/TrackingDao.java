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
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-departure
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/server/dao/impl/TrackingDao.java
 *  
 *  FILE NAME          :TrackingDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.server.dao.impl;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.module.transfer.departure.api.server.dao.ITrackingDao;
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
 * 
 * @author foss-liubinbin(for IBM)
 * @date 2013-1-8 下午12:52:09
 */
public class TrackingDao extends iBatis3DaoImpl implements ITrackingDao {
	private static final String NAMESPACE = "tfr-tracking.";
	/**
	 * 
	 * 通过运单号查询交接单信息
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 下午12:55:13
	 */
	
	@Override public List<HandoverBillDTO> getHandoverBillByWayBillNo(
			HandoverBillDTO handoverBillDTO) {
		List<HandoverBillDTO> list = this.getSqlSession().selectList(
				NAMESPACE + "getHandoverBillByWayBillNo", handoverBillDTO);
		return list;
	}
	
	/**
	 * 
	 * 通过运单号查询交接单信息
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 下午12:55:13
	 */
	@Override public List<HandoverBillDTO> getAllHandoverBillByWayBillNo(
			HandoverBillDTO handoverBillDTO) {
		List<HandoverBillDTO> list = this.getSqlSession().selectList(
				NAMESPACE + "getAllHandoverBillByWayBillNo", handoverBillDTO);
		return list;
	}
	/**
	 * 
	 * 通过运单号和生成时间取得运单中第一件货的流水号
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-9 上午10:44:22
	 */
	@Override public String getFirstSerialNoByWayBillNo(
			HandoverBillDTO handoverBillDTO) {
		List<String> list = this.getSqlSession().selectList(
				NAMESPACE + "getFirstSerialNoByWayBillNo", handoverBillDTO);
		if (list != null && list.size() > 0) {
			return list.get(0).toString();
		}
		return null;
	}
	/**
	 * 
	 * 通过运单号查询交接单信息
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 下午12:55:13
	 */
	@Override public List<OnthewayDTO> getTaskTrackingByWayBillNo(
			HandoverBillDTO handoverBillDTO) {
		List<OnthewayDTO> list = this.getSqlSession().selectList(
				NAMESPACE + "getTaskTrackingByWayBillNo", handoverBillDTO);
		return list;
	}
	/**
	 * 
	 * 查询库存轨迹
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 下午12:55:13
	 */
	@Override public List<StockTrackingDTO> getInStockTrackingByWayBillNo(
			StockTrackingDTO stockTrackingDTO) {
		List<StockTrackingDTO> list = this.getSqlSession().selectList(
				NAMESPACE + "getInStockTrackingByWayBillNo", stockTrackingDTO);
		return list;
	}
	/**
	 * 
	 * 查询库存轨迹
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 下午12:55:13
	 */
	@Override public List<StockTrackingDTO> getOutStockTrackingByWayBillNo(
			StockTrackingDTO stockTrackingDTO) {
		List<StockTrackingDTO> list = this.getSqlSession().selectList(
				NAMESPACE + "getOutStockTrackingByWayBillNo", stockTrackingDTO);
		return list;
	}
	/**
	 * 
	 * 查询库存轨迹
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 下午12:55:13
	 */
	@Override public List<StockTrackingDTO> getStockTrackingByWayBillNo(
			StockTrackingDTO stockTrackingDTO) {
		List<StockTrackingDTO> list = this.getSqlSession().selectList(
				NAMESPACE + "getStockTrackingByWayBillNo", stockTrackingDTO);
		return list;
	}
	/**
	 * 
	 * 查询库存轨迹（签收）
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 下午12:55:13
	 */
	@Override public List<StockTrackingDTO> getSignStockTrackingByWayBillNo(
			StockTrackingDTO stockTrackingDTO) {
		List<StockTrackingDTO> list = this.getSqlSession()
				.selectList(NAMESPACE + "getSignStockTrackingByWayBillNo",
						stockTrackingDTO);
		return list;
	}
	/**
	 * 
	 * 通过运单号查询正单信息
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 下午12:55:13
	 */
	@Override public List<AirWaybillEntity> queryAirWayBillListByWaybillNo(
			String waybillNo) {
		List<AirWaybillEntity> list = this.getSqlSession().selectList(
				NAMESPACE + "queryAirWayBillListByWaybillNo", waybillNo);
		return list;
	}
	/**
	 * 
	 * 通过运单号查询卸车的轨迹
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 下午12:55:13
	 */
	@Override public List<HandoverBillDTO> getUnloadBillByWayBillNo(
			HandoverBillDTO handoverBillDTO) {
		List<HandoverBillDTO> list = this.getSqlSession().selectList(
				NAMESPACE + "getUnloadBillByWayBillNo", handoverBillDTO);
		return list;
	}

	/**
	 * 
	 * 通过运单号查询装车信息
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 下午12:55:13
	 */
	@Override
	public List<LoadDetailEntity> getLoadDetailTrackingByWayBillNo(HandoverBillDTO handoverBillDTO) {
		List<LoadDetailEntity> list = this.getSqlSession().selectList(
				NAMESPACE + "getLoadDetailTrackingByWayBillNo", handoverBillDTO);
		return list;
	}

	@Override
	public List<HandoverBillDTO> queryHandOverBillDetailByWaybillNo(HandoverBillDTO handoverBillDTO) {
		List<HandoverBillDTO> list = this.getSqlSession().selectList(
				NAMESPACE + "queryHandOverBillDetailByWaybillNo", handoverBillDTO);
		return list;
	}

	/**
	 * 根据运单获取包装信息
	 */
	@Override
	public List<HandoverBillDTO> queryPackageByWaybillNo(String waybillNo) {
		List<HandoverBillDTO> list = this.getSqlSession().selectList(
				NAMESPACE + "queryPackageByWaybillNo", waybillNo);
		return list;
	}

	@Override
	public List<HandoverBillDTO> getPackageAreaInByWayBillNo(
			HandoverBillDTO handoverBillDTO) {
		List<HandoverBillDTO> list = this.getSqlSession().selectList(
				NAMESPACE + "getPackageAreaInByWayBillNo", handoverBillDTO);
		return list;
	}

	@Override
	public List<String> getInStockTypesWayBillNo(
			HandoverBillDTO handoverBillDTO) {
		List<String> list = this.getSqlSession().selectList(
				NAMESPACE + "getInStockTypesWayBillNo", handoverBillDTO);
		return list;
	}
	@Override
	public List<String> getOutStockTypesWayBillNo(
			HandoverBillDTO handoverBillDTO) {
		List<String> list = this.getSqlSession().selectList(
				NAMESPACE + "getOutStockTypesWayBillNo", handoverBillDTO);
		return list;
	}
	
	@Override
	public List<HandoverBillDTO> getPackageAreaOutByWayBillNo(
			HandoverBillDTO handoverBillDTO) {
		List<HandoverBillDTO> list = this.getSqlSession().selectList(
				NAMESPACE + "getPackageAreaOutByWayBillNo", handoverBillDTO);
		return list;
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
	public List<StockTrackingEntity> queryUniqueStock(InOutStockEntity inOutStockEntity) {
		return this.getSqlSession().selectList(NAMESPACE + "uniqueStockQuery", inOutStockEntity);
	}
	/**合伙人营业部交接虚拟库存
	 * 根据运单号、流水号查询唯一货件库存
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @author 360903-foss
	 * @date 2016年11月22日 15:25:58
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IStockDao#queryUniqueStock(com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity)
	 */
	@Override
	public List<StockTrackingEntity> queryUniqueStockSale(InOutStockEntity inOutStockEntity) {
		return this.getSqlSession().selectList(NAMESPACE + "uniqueStockSaleQuery", inOutStockEntity);
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
	public List<StockTrackingEntity> queryUniqueStockNext(InOutStockEntity inOutStockEntity) {
		return this.getSqlSession().selectList(NAMESPACE + "uniqueStockNextQuery", inOutStockEntity);
	}
	/**合伙人营业部交接虚拟库存
	 * 根据运单号、流水号查询唯一货件库存
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @author 360903-foss
	 * @date 2016年11月22日 16:52:58
	 */
	@Override
	public List<StockTrackingEntity> queryUniqueStockSaleNext(InOutStockEntity inOutStockEntity) {
		return this.getSqlSession().selectList(NAMESPACE + "uniqueStockSaleNextQuery", inOutStockEntity);
	}
	/**
	 * 查看签收出库的信息
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-29 上午11:49:59
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IStockDao#queryUniqueStock(com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity)
	 */
	@Override
	public List<StockTrackingEntity> querySignedStockQuery(StockTrackingEntity stockTrackingEntity) {
		return this.getSqlSession().selectList(NAMESPACE + "querySignedStockQuery", stockTrackingEntity);
	}
	/**
	 * 查看签收出库的信息
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-29 上午11:49:59
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IStockDao#queryUniqueStock(com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity)
	 */
	@Override
	public List<StockTrackingEntity> querySignStockQuery(InOutStockEntity inOutStockEntity) {
		return this.getSqlSession().selectList(NAMESPACE + "signStockQuery", inOutStockEntity);
	}
	/**
	 * 通过运单跟流水号（复数）查询最新状态的交接单
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-29 上午11:49:59
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IStockDao#queryUniqueStock(com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity)
	 */
	@Override
	public List<StockTrackingEntity> queryNewHandover(StockTrackingEntity stockTrackingEntity) {
		return this.getSqlSession().selectList(NAMESPACE + "queryNewHandover", stockTrackingEntity);
	}
	/**
	 * 通过交接单取得货件号
	 * @param inOutStockEntity.waybillNO 运单号
	 * @param inOutStockEntity.serialNO  流水号
	 * @author 097457-foss-wangqiang
	 * @date 2012-10-29 上午11:49:59
	 * @see com.deppon.foss.module.transfer.stock.api.server.dao.IStockDao#queryUniqueStock(com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity)
	 */
	@Override
	public List<String> querySerialNobyHandoverBill(StockTrackingEntity stockTrackingEntity) {
		return this.getSqlSession().selectList(NAMESPACE + "querySerialNobyHandoverBill", stockTrackingEntity);
	}

	/**
	 * 
	 * <p>查询货物到达，签收情况--for CC</p> 
	 * @author alfred
	 * @date 2014-7-31 下午5:03:52
	 * @param waybillNo
	 * @return
	 * @see
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public Map queryWaybillArrStatus(String waybillNo) {
		// TODO Auto-generated method stub
		Map paramsMap  = new HashMap();
		paramsMap.put("waybillNo", waybillNo);
		this.getSqlSession().selectOne(NAMESPACE+"queryWaybillArrStatus",paramsMap);
		return paramsMap;
	}

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
	@Override
	public Map queryWaybillIsOuter(String waybillNo) {
		// TODO Auto-generated method stub
		Map paramsMap  = new HashMap();
		paramsMap.put("waybillNo", waybillNo);
		this.getSqlSession().selectOne(NAMESPACE+"queryWaybillIsOuter",paramsMap);
		return paramsMap;
	}

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
	@Override
	public Map queryPathdetail(String waybillNo, String billingOrg,
			String destOrg) {
		// TODO Auto-generated method stub
		Map paramsMap  = new HashMap();
		paramsMap.put("waybillNo", waybillNo);
		paramsMap.put("billingOrg", billingOrg);
		paramsMap.put("destOrg", destOrg);
		this.getSqlSession().selectOne(NAMESPACE+"queryPathdetail",paramsMap);
		return paramsMap;
	}

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
	@Override
	public Map queryCitynameForCC(String orgcode) {
		// TODO Auto-generated method stub
		Map paramsMap  = new HashMap();
		paramsMap.put("orgcode", orgcode);
		this.getSqlSession().selectOne(NAMESPACE+"queryCitynameForCC",paramsMap);
		return paramsMap;
	}

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
	@Override
	public Map queryOrgtypeForCC(String orgcode) {
		// TODO Auto-generated method stub
		Map paramsMap  = new HashMap();
		paramsMap.put("orgcode", orgcode);
		this.getSqlSession().selectOne(NAMESPACE+"queryOrgtypeForCC",paramsMap);
		return paramsMap;
	}
	
	
	/**
	 * 通过运单号，流水号查询叉车司机扫描信息
	 * @author heyongdong
	 * @date 2014年9月9日 15:30:45
	 * @param HandoverBillDTO handoverBillDTO
	 * @see com.deppon.foss.module.transfer.departure.api.server.dao.ITrackingDao#getTrayScanByWayBillNo(com.deppon.foss.module.transfer.departure.api.shared.dto.HandoverBillDTO)
	 */
	
	@Override
	public List<HandoverBillDTO> getTrayScanByWayBillNo(HandoverBillDTO handoverBillDTO) {
		
		return this.getSqlSession().selectList(NAMESPACE+"getTrayScanByWayBillNo",handoverBillDTO);
	}
	
	/**
	 * 建立卸车任务 zwd 20150105 200968
	 * @param handoverBillDTO
	 * @return
	 */
	public List<HandoverBillDTO> queryUnloadTaskByWayBillNo(
			HandoverBillDTO handoverBillDTO) {
		return this.getSqlSession().selectList(NAMESPACE+"queryUnloadTaskByWayBillNo",handoverBillDTO);
	}
	/**
	 * 点单任务 cl 20160115 272681
	 * @param handoverBillDTO
	 * @return
	 */
	public List<HandoverBillDTO> queryOrderTaskByWayBillNo(
			HandoverBillDTO handoverBillDTO) {
		return this.getSqlSession().selectList(NAMESPACE+"queryOrderTaskByWayBillNo",handoverBillDTO);
	}
	/**
	 * 提供给综合查询的接口,返回该运单上分拣信息
	 * @author zwd 200968
	 * @date 2014年12月30日 15:15:31
	 * 
	 * */
	public List<HandoverBillDTO> querySortingScanByWayBillNo(
			HandoverBillDTO handoverBillDTO) {
		return this.getSqlSession().selectList(NAMESPACE+"querySortingScanByWayBillNo",handoverBillDTO);
	}


	
	/**
	 * 提供给综合查询的接口,返回该运单包扫描信息
	 * @author zwd 200968
	 * @date 2014年12月30日 15:15:31
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public List<HandoverBillDTO> queryExpressPackageDetailByWayBillNo(
			HandoverBillDTO handoverBillDTO) {
		return this.getSqlSession().selectList(NAMESPACE+"queryExpressPackageDetailByWayBillNo",handoverBillDTO);
	}
	
	/**
	 * 
	 * <p>二程接驳-添加通过运单查询理货员外场装车交接单轨迹</p> 
	 * @author alfred
	 * @date 2015-8-30 上午10:53:09
	 * @param handoverBillDTO
	 * @param serialNo
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HandoverBillDTO> getTallyerHandoverBilllist(
			HandoverBillDTO handoverBillDTO) {
		List<HandoverBillDTO> list = this.getSqlSession().selectList(
				NAMESPACE + "getTallyerHandoverBilllist", handoverBillDTO);
		return list;
	}


	/**
	 * 
	 * <p>二程接驳-添加通过运单查询司机装车交接单轨迹</p> 
	 * @author alfred
	 * @date 2015-8-30 上午10:53:49
	 * @param handoverBillDTO
	 * @param serialNo
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HandoverBillDTO> getDriverHandoverBilllist(
			HandoverBillDTO handoverBillDTO) {
		List<HandoverBillDTO> list = this.getSqlSession().selectList(
				NAMESPACE + "getDriverHandoverBilllist", handoverBillDTO);
		return list;
	}

	/**
	 * 提供给综合查询的接口,返回该运单空运通知信息
	 * @author zwd 200968
	 * @date 2015年9月15日 9:15:31
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public List<HandoverBillDTO> queryAirNotifyCustomersSmsInfoByWayBillNo(
			HandoverBillDTO handoverBillDTO) {
		return this.getSqlSession().selectList(NAMESPACE+"queryAirNotifyCustomersSmsInfoByWayBillNo",handoverBillDTO);
	}

	/**
	 * 空运到达:代理到机场提货 zwd 2015-08-07 200968
	 * @param handoverBillDTO
	 * @param serialNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HandoverBillDTO> queryFlightArriveFromAirWaybillNoPickUp(
			HandoverBillDTO handoverBillDTO) {
		return this.getSqlSession().selectList(NAMESPACE+"queryFlightArriveFromAirWaybillNoPickUp",handoverBillDTO);
	}
	
	/**
	 * 空运到达:货物到达代理处 zwd 2015-08-14 200968
	 * @param handoverBillDTO
	 * @param serialNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HandoverBillDTO> queryFlightArriveFromAirWaybillNoGoods(
			HandoverBillDTO handoverBillDTO) {
		return this.getSqlSession().selectList(NAMESPACE+"queryFlightArriveFromAirWaybillNoGoods",handoverBillDTO);
	}
	
	/**
	 * @author 283244
	 * 提供综合页面查询
	 * 
	 * */
	public String querForBaseInfo(String waybill) {
		//Map<String,Object> map = new HashMap<String,Object>();
		//map.put("waybill", waybill);
		return (String)this.getSqlSession().selectOne(NAMESPACE+"querForBaseInfo",waybill);
	}
	
	/**
	 * 通过运单号查询实际到达时间
	 */
	@Override
	public ActualArriveTimeDTO getArriveTimeByWaybillNo(String waybillNo) {
		return (ActualArriveTimeDTO)this.getSqlSession().selectOne(NAMESPACE + "getArriveTimeByWaybillNo", waybillNo);
	}
	
	/**
	 * 通过运单号查询入库类型
	 */
	@Override
	public String getInStockTypesWayBillNoToPTP(String waybillNo) {
		return (String) this.getSqlSession().selectOne(NAMESPACE + "getInStockTypesWayBillNoToPTP", waybillNo);
	}
	
	/**
	 * 通过运单号查询到达时间
	 */
	@Override
	public Date getArrivalTimeByWaybillNo(String waybillNo) {
		return (Date)this.getSqlSession().selectOne(NAMESPACE + "getArrivalTimeByWaybillNo", waybillNo);
	}
}