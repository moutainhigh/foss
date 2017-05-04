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
 *  PROJECT NAME  : tfr-departure
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/server/service/impl/TrackingService.java
 *  
 *  FILE NAME          :TrackingService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.server.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IOrgAdministrativeInfoDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.pickup.api.shared.define.QueryTrackingWaybillConstants;
import com.deppon.foss.module.pickup.pickup.api.shared.dto.TrackingWaybillDto;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.ILabeledGoodService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillQueryService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillInfoByWaybillNoReultDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.departure.api.server.dao.ITrackingDao;
import com.deppon.foss.module.transfer.departure.api.server.service.ITrackingService;
import com.deppon.foss.module.transfer.departure.api.shared.define.DepartureConstant;
import com.deppon.foss.module.transfer.departure.api.shared.domain.LoadDetailEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.StockTrackingEntity;
import com.deppon.foss.module.transfer.departure.api.shared.dto.ActualArriveTimeDTO;
import com.deppon.foss.module.transfer.departure.api.shared.dto.HandoverBillDTO;
import com.deppon.foss.module.transfer.departure.api.shared.dto.OnthewayDTO;
import com.deppon.foss.module.transfer.departure.api.shared.dto.StockTrackingDTO;
import com.deppon.foss.module.transfer.load.api.server.service.IHandOverBillService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity;
import com.deppon.foss.module.transfer.scheduling.api.define.TransportPathConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.ITransportationPathDao;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ICalculateTransportPathService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TransportPathEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.FeedbackDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.GeneralQueryDto;
import com.deppon.foss.module.transfer.stock.api.server.dao.IWaybillStockDao;
import com.deppon.foss.module.transfer.stock.api.server.service.IStockService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InOutStockEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 跟踪运单执行轨迹.
 * 
 * 放行流水号为唯一，保证GPS系统与FOSS系统传送的车辆出发时间是唯一的。
 * 
 * 
 * 查询轨迹回放时，ＦＯＳＳ系统传送【轨迹回放：FOSS传送给GPS参数】给GPS系统。
 * 
 * 
 * 放行流水号为唯一，保证GPS系统与FOSS系统传送的车辆轨迹信息是一致的。
 * 
 * 
 * 此功能主要为车队调度角色使用
 * 
 * 
 * @author foss-liubinbin(for IBM)
 * @date 2012-12-6 上午11:30:12
 */
public class TrackingService implements ITrackingService {
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LogManager
			.getLogger(TrackingService.class);
	
	/**
	 * 通过部门name得到部门Code
	 * 2015-07-24
	 * 272681
	 */
	private IOrgAdministrativeInfoDao orgAdministrativeInfoDao;
	public void setOrgAdministrativeInfoDao(
			IOrgAdministrativeInfoDao orgAdministrativeInfoDao) {
		this.orgAdministrativeInfoDao = orgAdministrativeInfoDao;
	}
	/**
	 * 运单查询service   根据运单号，查询运单详细信息
	 * 272681 2015-07-26
	 * */
	private IWaybillQueryService waybillQueryService;
	public void setWaybillQueryService(IWaybillQueryService waybillQueryService) {
		this.waybillQueryService = waybillQueryService;
	}
	/**
	 * 运单管理接口
	 * 2016-04-18
	 */
	private IWaybillManagerService waybillManagerService;
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	/**
	 * 库存管理 Service
	 * 2016-04-18
	 */
	private IStockService stockService;
	public void setStockService(IStockService stockService) {
		this.stockService = stockService;
	}
	/**
	 * 运单底层接口
	 * 2016-05-04
	 */
	private IWaybillDao waybillDao;
	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}
	 /**
	  * 运单库存dao
	 * 2016-04-18
	  */
	 private IWaybillStockDao waybillStockDao;
		public void setWaybillStockDao(IWaybillStockDao waybillStockDao) {
			this.waybillStockDao = waybillStockDao;
		}
	 /**
	  * 运单签收结果Service
	 * 2016-04-29
	  */
	 private IWaybillSignResultService waybillSignResultService;
	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}

	/**
	 * 按件查询货物的轨迹
	 * 
	 * @param handoverBillDTO
	 *            the handover bill dto
	 * @param serialNo
	 *            the serial no
	 * @return the handover bill by way bill no
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-10 上午9:17:55
	 * @see com.deppon.foss.module.transfer.departure.api.server.service.ITrackingService#getHandoverBillByWayBillNo(com.deppon.foss.module.transfer.departure.api.shared.dto.HandoverBillDTO)
	 */
	@Override
	public List<HandoverBillDTO> getHandoverBillByWayBillNo(
			HandoverBillDTO handoverBillDTO, String serialNo) {
		String no;
		if (serialNo != null) {
			no = serialNo;
		} else {
			// 取得第一个交接单的第一个流水号
			no = getFirstSerialNoByWayBillNo(handoverBillDTO);
		}
		if (no == null) {
			LOGGER.error("不存在流水号,随便从运单里找一个流水号");
			List<LabeledGoodEntity> labellist = labeledGoodService
					.queryAllSerialByWaybillNo(handoverBillDTO.getWaybillNo());
			if (labellist != null && labellist.size() > 0) {
				no = labellist.get(0).getSerialNo();
			}
		}
		// 根据交接流水明细取得该运单的轨迹
		handoverBillDTO.setSerialNo(no);
		// 查询具体的轨迹、出发、到达
		List<HandoverBillDTO> list = trackingDao
				.getHandoverBillByWayBillNo(handoverBillDTO);
		return list;
	}

	/**
	 * 查询运单所有的轨迹（出发到达）
	 * 
	 * @param handoverBillDTO
	 *            the handover bill dto
	 * @param serialNo
	 *            the serial no
	 * @return the handover bill by way bill no
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-10 上午9:17:55
	 * @see com.deppon.foss.module.transfer.departure.api.server.service.ITrackingService#getHandoverBillByWayBillNo(com.deppon.foss.module.transfer.departure.api.shared.dto.HandoverBillDTO)
	 */
	@Override
	public List<HandoverBillDTO> getAllHandoverBillByWayBillNo(
			HandoverBillDTO handoverBillDTO) {
		// 根据交接流水明细取得该运单的轨迹
		// 查询具体的轨迹、出发、到达
		List<HandoverBillDTO> list = trackingDao
				.getAllHandoverBillByWayBillNo(handoverBillDTO);
		return list;
	}

	/**
	 * 根据运单号查询交接信息
	 * 
	 * @param handoverBillDTO
	 *            the handover bill dto
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-10 上午9:17:55
	 * @see com.deppon.foss.module.transfer.departure.api.server.service.ITrackingService#getHandoverBillByWayBillNo(com.deppon.foss.module.transfer.departure.api.shared.dto.HandoverBillDTO)
	 */
	@Override
	public List<HandoverBillDTO> queryHandOverBillDetailByWaybillNo(
			String waybillNo, String serialNo) {
		HandoverBillDTO handoverBillDTO = new HandoverBillDTO();
		handoverBillDTO.setWaybillNo(waybillNo);
		handoverBillDTO.setSerialNo(serialNo);
		List<HandoverBillDTO> list = trackingDao
				.queryHandOverBillDetailByWaybillNo(handoverBillDTO);
		return list;
	}

	/**
	 * 根据运单号查询包装信息
	 * 
	 * @param handoverBillDTO
	 *            the handover bill dto
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-10 上午9:17:55
	 * @see com.deppon.foss.module.transfer.departure.api.server.service.ITrackingService#getHandoverBillByWayBillNo(com.deppon.foss.module.transfer.departure.api.shared.dto.HandoverBillDTO)
	 */
	@Override
	public List<HandoverBillDTO> getPackageByWayBillNo(String waybillNo) {
		List<HandoverBillDTO> list = trackingDao
				.queryPackageByWaybillNo(waybillNo);
		return list;
	}

	/**
	 * 查询运单所有的轨迹（装车）
	 * 
	 * @param handoverBillDTO
	 *            the handover bill dto
	 * @param serialNo
	 *            the serial no
	 * @return the handover bill by way bill no
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-10 上午9:17:55
	 * @see com.deppon.foss.module.transfer.departure.api.server.service.ITrackingService#getHandoverBillByWayBillNo(com.deppon.foss.module.transfer.departure.api.shared.dto.HandoverBillDTO)
	 */
	@Override
	public List<LoadDetailEntity> getLoadDetailTrackingByWayBillNo(
			HandoverBillDTO handoverBillDTO, String serialNo) {
		String no;
		if (serialNo != null) {
			no = serialNo;
		} else {
			// 取得第一个交接单的第一个流水号
			no = getFirstSerialNoByWayBillNo(handoverBillDTO);
		}
		if (no == null) {
			LOGGER.error("不存在流水号");
			return null;
		}
		// 根据交接流水明细取得该运单的轨迹
		handoverBillDTO.setSerialNo(no);
		// 查询卸车轨迹
		List<LoadDetailEntity> loadlist = trackingDao
				.getLoadDetailTrackingByWayBillNo(handoverBillDTO);
		return loadlist;
	}

	/**
	 * 通过运单号查询登入货区信息
	 * 
	 * @param handoverBillDTO
	 *            the handover bill dto
	 * @param serialNo
	 *            the serial no
	 * @return the handover bill by way bill no
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-10 上午9:17:55
	 * @see com.deppon.foss.module.transfer.departure.api.server.service.ITrackingService#getHandoverBillByWayBillNo(com.deppon.foss.module.transfer.departure.api.shared.dto.HandoverBillDTO)
	 */
	@Override
	public List<HandoverBillDTO> getPackageAreaInByWayBillNo(
			HandoverBillDTO handoverBillDTO, String serialNo,
			Integer goodsQtyTotal) {
		String no;
		if (serialNo != null) {
			no = serialNo;
		} else {
			// 取得第一个交接单的第一个流水号
			no = getFirstSerialNoByWayBillNo(handoverBillDTO);
		}
		if (no == null) {
			LOGGER.error("不存在流水号");
			return null;
		}
		// 根据交接流水明细取得该运单的轨迹
		handoverBillDTO.setSerialNo(no);
		// 查询卸车轨迹
		List<HandoverBillDTO> loadlist = trackingDao
				.getPackageAreaInByWayBillNo(handoverBillDTO);
		if (loadlist != null) {// 当数量大于开单件数时，直接填开单件数 TODO
			for (HandoverBillDTO dto : loadlist) {
				if (dto.getPackedNum() > goodsQtyTotal) {
					dto.setPackedNum(goodsQtyTotal);
				}
			}
		}
		//只显示500条
		List<HandoverBillDTO> result = new ArrayList<HandoverBillDTO>();
		int num = 0;
		if(loadlist != null)
		{
			for(HandoverBillDTO dto : loadlist)
			{
				num++;
				result.add(dto);
				if(num>=LoadConstants.SONAR_NUMBER_499)
				{
					return result;
				}
			}
		}
		return loadlist;
	}

	/**
	 * 通过运单号查询入库类型
	 * 
	 * @param handoverBillDTO
	 *            the handover bill dto
	 * @param serialNo
	 *            the serial no
	 * @return the handover bill by way bill no
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-10 上午9:17:55
	 * @see com.deppon.foss.module.transfer.departure.api.server.service.ITrackingService#getHandoverBillByWayBillNo(com.deppon.foss.module.transfer.departure.api.shared.dto.HandoverBillDTO)
	 */
	@Override
	public List<String> getInStockTypesWayBillNo(
			HandoverBillDTO handoverBillDTO, String[] types) {
		// 查询轨迹
		handoverBillDTO.setInStockTypes(Arrays.asList(types));
		List<String> loadlist = trackingDao
				.getInStockTypesWayBillNo(handoverBillDTO);
		return loadlist;
	}

	/**
	 * 通过运单号查询出库类型
	 * 
	 * @param handoverBillDTO
	 *            the handover bill dto
	 * @param serialNo
	 *            the serial no
	 * @return the handover bill by way bill no
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-10 上午9:17:55
	 * @see com.deppon.foss.module.transfer.departure.api.server.service.ITrackingService#getHandoverBillByWayBillNo(com.deppon.foss.module.transfer.departure.api.shared.dto.HandoverBillDTO)
	 */
	@Override
	public List<String> getOutStockTypesWayBillNo(
			HandoverBillDTO handoverBillDTO, String[] types) {
		// 查询轨迹
		handoverBillDTO.setOutStockTypes(Arrays.asList(types));
		List<String> loadlist = trackingDao
				.getOutStockTypesWayBillNo(handoverBillDTO);
		return loadlist;
	}

	/**
	 * 通过运单号查询登出获取信息
	 * 
	 * @param handoverBillDTO
	 *            the handover bill dto
	 * @param serialNo
	 *            the serial no
	 * @return the handover bill by way bill no
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-10 上午9:17:55
	 * @see com.deppon.foss.module.transfer.departure.api.server.service.ITrackingService#getHandoverBillByWayBillNo(com.deppon.foss.module.transfer.departure.api.shared.dto.HandoverBillDTO)
	 */
	@Override
	public List<HandoverBillDTO> getPackageAreaOutByWayBillNo(
			HandoverBillDTO handoverBillDTO, String serialNo,Integer goodsQtyTotal) {
		// 查询卸车轨迹
		handoverBillDTO.setSerialNo(serialNo);
		List<HandoverBillDTO> loadlist = trackingDao
				.getPackageAreaOutByWayBillNo(handoverBillDTO);
		if(loadlist!=null)
		{
			for(HandoverBillDTO HandoverBillDTO:loadlist)
			{
				if(HandoverBillDTO.getPackedNum()>goodsQtyTotal)
				{
					HandoverBillDTO.setPackedNum(goodsQtyTotal);
				}
			}
		}
		//只显示500条
		List<HandoverBillDTO> result = new ArrayList<HandoverBillDTO>();
		int num = 0;
		if(loadlist != null)
		{
			for(HandoverBillDTO dto : loadlist)
			{
				num++;
				result.add(dto);
				if(num>=LoadConstants.SONAR_NUMBER_499)
				{
					return result;
				}
			}
				}
		return loadlist;
	}

	/**
	 * 按见查询卸车的轨迹
	 * 
	 * @param handoverBillDTO
	 *            the handover bill dto
	 * @param serialNo
	 *            the serial no
	 * @return the handover bill by way bill no
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-10 上午9:17:55
	 * @see com.deppon.foss.module.transfer.departure.api.server.service.ITrackingService#getHandoverBillByWayBillNo(com.deppon.foss.module.transfer.departure.api.shared.dto.HandoverBillDTO)
	 */
	@Override
	public List<HandoverBillDTO> getUnloadBillByWayBillNo(
			HandoverBillDTO handoverBillDTO, String serialNo) {
		String no;
		if (serialNo != null) {
			no = serialNo;
		} else {
			// 取得第一个交接单的第一个流水号
			no = getFirstSerialNoByWayBillNo(handoverBillDTO);
		}
		if (no == null) {
			LOGGER.error("不存在流水号");
		}
		// 根据交接流水明细取得该运单的轨迹
		handoverBillDTO.setSerialNo(no);
		// 查询卸车轨迹
		List<HandoverBillDTO> unloadlist = trackingDao
				.getUnloadBillByWayBillNo(handoverBillDTO);
		return unloadlist;
	}
	
	/**
	 * 提供给综合查询的接口,返回该运单叉车司机扫描信息
	 * 
	 * @author heyongdong
	 * @date 2014年9月9日 15:15:31
	 * @see com.deppon.foss.module.transfer.departure.api.server.service.ITrackingService#getTrayScanByWayBillNo(java.lang.String, java.lang.String)
	 */
	@Override
	public List<HandoverBillDTO> getTrayScanByWayBillNo(HandoverBillDTO handoverBillDTO,String serialNo){
		String no ;
		if(StringUtils.isNotEmpty(serialNo)){
			no = serialNo;
		}else{
			// 取得第一个交接单的第一个流水号
			no = getFirstSerialNoByWayBillNo(handoverBillDTO);
		}
		handoverBillDTO.setSerialNo(no);
		// 查询卸车轨迹
		List<HandoverBillDTO> trayScans = trackingDao.getTrayScanByWayBillNo(handoverBillDTO);
		return trayScans;
	}
	/**
	 * 根据运单号查询轨迹信息.
	 * 
	 * @param waybillNo
	 *            the waybill no
	 * @return the task tracking by way bill no
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-10 上午9:17:55
	 * @see com.deppon.foss.module.transfer.departure.api.server.service.ITrackingService#getTaskTrackingByWayBillNo(java.lang.String)
	 */
	@Override
	public List<OnthewayDTO> getTaskTrackingByWayBillNo(String waybillNo) {
		HandoverBillDTO handoverBillDTO = new HandoverBillDTO();
		handoverBillDTO.setWaybillNo(waybillNo);
		// 取得第一个交接单的第一个流水号
		String serialNo = getFirstSerialNoByWayBillNo(handoverBillDTO);
		if (serialNo == null) {
			LOGGER.error("不存在流水号");
			throw new TfrBusinessException("NOT_EXIST_SERIALS", "不存在流水号");
		}
		// 根据交接流水明细取得该运单的轨迹
		handoverBillDTO.setSerialNo(serialNo);
		List<OnthewayDTO> list = trackingDao
				.getTaskTrackingByWayBillNo(handoverBillDTO);
		return list;
	}

	/**
	 * 通过运单号和生成时间取得运单中第一件货的流水号.
	 * 
	 * @param handoverBillDTO
	 *            the handover bill dto
	 * @return the first serial no by way bill no
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-9 上午10:44:22
	 */
	private String getFirstSerialNoByWayBillNo(HandoverBillDTO handoverBillDTO) {
		// 通过走货路径的接口调用最慢的一般车，从里面取得最慢的一个交接中的一件货
		try {
			// 判断是否分批配载，如果分批配载，取最慢的一条，如果不是，取随机的一条
			TransportPathEntity transportPathEntity = calculateTransportPathService
					.queryTransportPath(handoverBillDTO.getWaybillNo());
			// 判断是否存在条目
			if (null != transportPathEntity) {
				// 如果是分批
				if (StringUtils.equals(
						transportPathEntity.getIfPartialStowage(),
						TransportPathConstants.PARTIALSTOWAGE)) {
					List<String> list = calculateTransportPathService
							.listFastGoodsNo(handoverBillDTO.getWaybillNo());
					if (list != null && list.size() > 0) {
						// 取最后一条数据
						return list.get(list.size() - 1);
					} else {
						// 直接返回空
						return null;
					}
				} else {
					// 获取包装后运单货件数
					List<String> listGoodsNo = calculateTransportPathService
							.listQueryGoodsNo(handoverBillDTO.getWaybillNo());
					if (listGoodsNo != null && listGoodsNo.size() > 0) {
						// 取第一条
						return listGoodsNo.get(0);
					} else {
						return null;
					}
				}
			}
		} catch (TfrBusinessException e) {
			LOGGER.error("不存在流水号");
			return null;
		}
		return null;
	}

	/**
	 * 库存状况查询(已出库)（接送货）.
	 * 
	 * @param waybillNo
	 *            运单号
	 * @param operateTime
	 *            the operate time
	 * @return the out stock tracking by way bill no
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 下午12:55:13
	 */
	@Override
	public List<StockTrackingDTO> getOutStockTrackingByWayBillNo(
			String waybillNo, Date operateTime) {
		StockTrackingDTO stockTrackingDTO = new StockTrackingDTO();
		stockTrackingDTO.setWaybillNo(waybillNo);
		stockTrackingDTO.setOperateTime(operateTime);
		List<StockTrackingDTO> list = trackingDao
				.getOutStockTrackingByWayBillNo(stockTrackingDTO);
		for (StockTrackingDTO dto : list) {
			// 设置状态为已出库
			dto.setStockStatus(DepartureConstant.OUT_STOCK);
		}
		// 最后加一条签收的状态
		List<StockTrackingDTO> signlist = trackingDao
				.getSignStockTrackingByWayBillNo(stockTrackingDTO);
		if (signlist != null && signlist.size() > 0) {
			// 取第一条
			StockTrackingDTO signDTO = signlist.get(0);
			signDTO.setSerialCount(signlist.size());
			signDTO.setWaybillNo(waybillNo);
			signDTO.setStockStatus(DepartureConstant.OUT_STOCK);
			list.add(signDTO);
		}
		return list;
	}

	/**
	 * 库存状况查询(库存中)（接送货）.
	 * 
	 * @param waybillNo
	 *            运单号
	 * @param operateTime
	 *            the operate time
	 * @return the in stock tracking by way bill no
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 下午12:55:13
	 */
	@Override
	public List<StockTrackingDTO> getInStockTrackingByWayBillNo(
			String waybillNo, Date operateTime) {
		StockTrackingDTO stockTrackingDTO = new StockTrackingDTO();
		stockTrackingDTO.setWaybillNo(waybillNo);
		stockTrackingDTO.setOperateTime(operateTime);
		List<StockTrackingDTO> list = trackingDao
				.getInStockTrackingByWayBillNo(stockTrackingDTO);
		// 取得该运单关联的所有的涉及到的出发部门
		List<String> orglist = new ArrayList<String>();
		for (StockTrackingDTO dto : list) {
			orglist.add(dto.getOrgCode());
		}
		if (orglist.size() <= 0) {
			return null;
		}
		stockTrackingDTO.setOrgCodes(orglist);
		// 查询所有的库存记录
		List<StockTrackingDTO> slist = trackingDao
				.getStockTrackingByWayBillNo(stockTrackingDTO);
		List<StockTrackingDTO> resutlist = new ArrayList<StockTrackingDTO>();
		if (list != null) {
			// 判断是否为空
			for (StockTrackingDTO dto : list) {
				for (StockTrackingDTO handdto : slist) {
					if (dto.getOrgCode().equals(handdto.getOrgCode())) {
						// 出发部门一致的
						dto.setWaybillNo(waybillNo);
						dto.setOperateTime(handdto.getOperateTime());
						dto.setStockStatus(DepartureConstant.IN_STOCK);// 设置状态为库存中
						resutlist.add(dto);
						break;
					}
				}
			}
		}
		// 已出库的数据
		List<StockTrackingDTO> outlist = getOutStockTrackingByWayBillNo(
				waybillNo, operateTime);
		resutlist.addAll(outlist);
		// 根据日期来排序
		Collections.sort(resutlist, new StockTrackingDTO());
		return resutlist;
	}

	/**
	 * 通过运单号查询正单信息.
	 * 
	 * @param waybillNo
	 *            the waybill no
	 * @return the air waybill entity
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 下午12:55:13
	 */
	@Override
	public List<AirWaybillEntity> queryAirWayBillListByWaybillNo(String waybillNo) {
		List<AirWaybillEntity> list = trackingDao
				.queryAirWayBillListByWaybillNo(waybillNo);
		return list;
	}

	@Override
	public List<GeneralQueryDto> queryWaybillStatusByWaybillNoForPkp(
			String waybillNo) {
		// 用来存储开单的list，库存的list，交接单的list,签收的list
		List<String> labelSerials = new ArrayList<String>();
		List<String> stockSerials = new ArrayList<String>();
		List<String> signSerials = new ArrayList<String>();
		List<GeneralQueryDto> result = new ArrayList<GeneralQueryDto>();
		// 判断是否分批配载，如果分批配载，取最慢的一条，如果不是，取随机的一条
		TransportPathEntity transportPathEntity = calculateTransportPathService
				.queryTransportPath(waybillNo);
		// 查询开单时的所有的信息
		List<LabeledGoodEntity> labeledGoodList = labeledGoodService
				.queryAllSerialByWaybillNo(waybillNo);
		if (labeledGoodList != null) {
			for (LabeledGoodEntity LabeledGoodEntity : labeledGoodList) {
				if (!labelSerials.contains(LabeledGoodEntity.getSerialNo())) {
					labelSerials.add(LabeledGoodEntity.getSerialNo());
				}
			}
		}
		// 查询所有的库存信息
		InOutStockEntity inOutStockEntity = new InOutStockEntity();
		inOutStockEntity.setWaybillNO(waybillNo);
		List<StockTrackingEntity> stocklist = trackingDao
				.queryUniqueStock(inOutStockEntity);
		// 查询库存中的部门，groupby 当前部门，下一部门
		List<StockTrackingEntity> stockgrouplist = trackingDao
				.queryUniqueStockNext(inOutStockEntity);
		if (stockgrouplist != null && stocklist != null) {
			for (StockTrackingEntity stockTrackingEntity : stockgrouplist) {
				int count = 0;
				for (StockTrackingEntity stockEntity : stocklist) {
					if (StringUtils.equals(stockTrackingEntity.getOrgCode(),
							stockEntity.getOrgCode())
							&& StringUtils.equals(
									stockTrackingEntity.getNextOrgCode(),
									stockEntity.getNextOrgCode())) {
						if (!stockSerials.contains(stockEntity.getSerialNO())) {
							stockSerials.add(stockEntity.getSerialNO());
							count++;
						}
					}
				}
				//sonar-352203
				if (stockSerials == null) {
					continue;
				}
					GeneralQueryDto generalQueryDto = new GeneralQueryDto();
					// 根据部门编码，获取组织信息
					OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService
							.queryOrgAdministrativeInfoByCodeClean(stockTrackingEntity
									.getOrgCode());
					if (orgAdministrativeInfoEntity != null) {
						generalQueryDto
								.setNowPosition(orgAdministrativeInfoEntity
										.getName());
						// 查看当前部门是否是营业部
						if (orgAdministrativeInfoEntity.checkSaleDepartment()) {
							// 营业部库存
							generalQueryDto
									.setAction(TransportPathConstants.GENERAL_QUERY_DESTINSTORE);
						} else {
							// 中转库存
							generalQueryDto
									.setAction(TransportPathConstants.GENERAL_QUERY_TRANSFERCENTERINSTORE);
						}
					}
					orgAdministrativeInfoEntity = orgAdministrativeInfoService
							.queryOrgAdministrativeInfoByCodeClean(stockTrackingEntity
									.getNextOrgCode());
					if (orgAdministrativeInfoEntity != null) {
						generalQueryDto
								.setNextOrgCode(orgAdministrativeInfoEntity
										.getName());
					}
					generalQueryDto.setGoodsCount(count);
					if(stockTrackingEntity.getSerialNoss()!=null){
						generalQueryDto.setSerials(Arrays.asList(stockTrackingEntity.getSerialNoss().split(",")));
						Collections.sort(generalQueryDto.getSerials());
					}
					try {
						// 填入预计到达下一部门时间，预计派送提货时间，如果是分批,通过出发部门，到达部门，件号去查询，否则通过出发部门，到达部门去查询
						FeedbackDto feedbackDto = calculateTransportPathService
								.getNextOrgAndTimeForRWSplitting(waybillNo,
										stockSerials.get(0),
										stockTrackingEntity.getOrgCode());
						if (feedbackDto.getPathDetailEntity() != null) {
							generalQueryDto.setPlanArriveTime(feedbackDto
									.getPathDetailEntity().getPlanArriveTime());
							generalQueryDto.setPlanPickupTime(feedbackDto
									.getPathDetailEntity().getPlanPickupTime());
							// 到达部门为最终到达部门，并且到达部门可自提，设置可自提
							if (StringUtils.equals(
									stockTrackingEntity.getOrgCode(),
									getTranCentreCode(transportPathEntity.getDestOrgCode()))) {
								generalQueryDto
										.setIfAvailedPickup(TransportPathConstants.AVAILEDPICKUP);
							} else {
								generalQueryDto
										.setIfAvailedPickup(TransportPathConstants.NOTAVAILEDPICKUP);
							}
						} else {
							generalQueryDto
									.setIfAvailedPickup(TransportPathConstants.NOTAVAILEDPICKUP);
						}
					} catch (TfrBusinessException e) {
						LOGGER.error("TrackingService.queryWaybillStatusByWaybillNoForPkp 报错：" + StringUtils.substring(e.getMessage(), 0, LoadConstants.SONAR_NUMBER_100));
					}
					result.add(generalQueryDto);
//				}
			}
		}
		//合并营业部交接的虚拟库存数据，用于综合查询  360903 2016年11月22日 17:03:52
		getVirtualStockPTP(inOutStockEntity,stockSerials,result);
		// 如果开单件数等于库存件数，直接返回所有的库存
		if (labelSerials.size() != stockSerials.size()) {
			// 查询签收的信息
			StockTrackingEntity stockEntity = new StockTrackingEntity();
			stockEntity.setWaybillNO(waybillNo);
			// in查询不能超过1000,分批处理
			Map<Integer, List<String>> mapstock = new HashMap<Integer, List<String>>();
			List<String> liststockstock = new ArrayList<String>();
			for (int i = 0; i < stockSerials.size(); i++) {
				liststockstock.add(stockSerials.get(i));
				if ((i % LoadConstants.SONAR_NUMBER_900 == 0) && (i != 0)) {
					mapstock.put(i, liststockstock);
					liststockstock = new ArrayList<String>();
				}
				if ((i == stockSerials.size()-1) && liststockstock.size() > 0) {
					mapstock.put(i, liststockstock);
				}
			}
			Set<Map.Entry<Integer, List<String>>> setstock = mapstock.entrySet();
			if(mapstock.size()==0)
			{
				List<StockTrackingEntity> signgrouplist = trackingDao
						.querySignedStockQuery(stockEntity);
				if (signgrouplist != null) {
					for (StockTrackingEntity trackingEntity : signgrouplist) {
						// 通过部门跟运单号，查询件号
						InOutStockEntity outStockEntity = new InOutStockEntity();
						outStockEntity.setWaybillNO(waybillNo);
						outStockEntity.setOrgCode(trackingEntity.getOrgCode());
						List<StockTrackingEntity> orglist = trackingDao
								.querySignStockQuery(outStockEntity);
						//sonar-352203
						if (orglist == null) {
							continue;
						}
							for (StockTrackingEntity trEntity : orglist) {
								if (!signSerials.contains(trEntity
										.getSerialNO())) {
										signSerials.add(trEntity.getSerialNO());
									}
								}
//							}
						}
					}
			}
			for (Iterator<Map.Entry<Integer, List<String>>> it = setstock
					.iterator(); it.hasNext();) {
//				{//多余的{}
					Map.Entry<Integer, List<String>> entry = (Map.Entry<Integer, List<String>>) it
							.next();
					stockEntity.setSerialNos(entry.getValue());
					//sonar-352203
				if (stockSerials.size() <= 0) {
					continue;
				}
					List<StockTrackingEntity> signgrouplist = trackingDao
							.querySignedStockQuery(stockEntity);
					//sonar-352203
					if (signgrouplist == null) {
						continue;
					}
						for (StockTrackingEntity trackingEntity : signgrouplist) {
							// 通过部门跟运单号，查询件号
							InOutStockEntity outStockEntity = new InOutStockEntity();
							outStockEntity.setWaybillNO(waybillNo);
							outStockEntity.setOrgCode(trackingEntity.getOrgCode());
							List<StockTrackingEntity> orglist = trackingDao
									.querySignStockQuery(outStockEntity);
							//sonar-352203
							if (orglist == null) {
								continue;
							}
								for (StockTrackingEntity trEntity : orglist) {
									if (!signSerials.contains(trEntity
											.getSerialNO())) {
											signSerials.add(trEntity.getSerialNO());
										}
									}
//								}
							}
//						}
//					}
//				}
			}
			// 如果开单件数等于库存件数加签收件数，直接返回
			if (labelSerials.size() != (stockSerials.size() + signSerials
					.size())) {
				// 找到既不在库存中，也不在签收信息中，但是开过单的流水
				labelSerials.removeAll(stockSerials);
				labelSerials.removeAll(signSerials);
				// orderby出发部门，到达部门查找交接明细
				if (labelSerials.size() > 0) {
					StockTrackingEntity stockTrackingEntity = new StockTrackingEntity();
					stockTrackingEntity.setWaybillNO(waybillNo);
					// in查询不能超过1000,分批处理
					Map<Integer, List<String>> map = new HashMap<Integer, List<String>>();
					List<String> liststock = new ArrayList<String>();
					for (int i = 0; i < labelSerials.size(); i++) {
						liststock.add(labelSerials.get(i));
						if ((i % LoadConstants.SONAR_NUMBER_900 == 0) && (i != 0)) {
							map.put(i, liststock);
							liststock = new ArrayList<String>();
						}
						if ((i == labelSerials.size()-1) && liststock.size() > 0) {
							map.put(i, liststock);
						}
					}
					Set<Map.Entry<Integer, List<String>>> set = map.entrySet();
					List<StockTrackingEntity> handovers = new ArrayList<StockTrackingEntity>();
					List<StockTrackingEntity> handoverlist = new ArrayList<StockTrackingEntity>();
					for (Iterator<Map.Entry<Integer, List<String>>> it = set
							.iterator(); it.hasNext();) {
//						{//多出的{}
							Map.Entry<Integer, List<String>> entry = (Map.Entry<Integer, List<String>>) it
									.next();
							stockTrackingEntity.setSerialNos(entry.getValue());
							handoverlist = trackingDao.queryNewHandover(stockTrackingEntity);
							for(StockTrackingEntity entity1:handoverlist)
							{
								boolean flag = true;
								for(StockTrackingEntity entity2:handovers)
								{
									if(StringUtils.equals(entity1.getHandoverNo(),entity2.getHandoverNo()))
									{
										entity2.setGoodsCount(entity2.getGoodsCount()+entity1.getGoodsCount());
										flag = false;
										break;
									}
								}
								if(!flag)
									break;
								handovers.add(entity1);
							}
//						}
					}
					for (StockTrackingEntity StockTrackingEntity : handovers) {
						// 通过交接单号，运单号查找交接单信息
						HandOverBillEntity handOverBillEntity = handOverBillService
								.queryHandOverBillByNo(StockTrackingEntity
										.getHandoverNo());
						//sonar-352203
						if (handOverBillEntity == null) {
							continue;
						}
							stockTrackingEntity
									.setHandoverNo(StockTrackingEntity
											.getHandoverNo());
							List<String> list = trackingDao
									.querySerialNobyHandoverBill(stockTrackingEntity);
							GeneralQueryDto generalQueryDto = new GeneralQueryDto();
							// 根据部门编码，获取组织信息
							OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService
									.queryOrgAdministrativeInfoByCodeClean(handOverBillEntity
											.getDepartDeptCode());
							if (orgAdministrativeInfoEntity != null) {
								//sonar-352203-都为set值-抽取为方法
								initAction(handOverBillEntity, generalQueryDto,
										orgAdministrativeInfoEntity);
							}
							// 根据部门编码，获取组织信息
							generalQueryDto.setNowPosition(handOverBillEntity
									.getDepartDept());
							generalQueryDto.setNextOrgCode(handOverBillEntity
									.getArriveDept());
							generalQueryDto.setGoodsCount(StockTrackingEntity
									.getGoodsCount());
							if(StockTrackingEntity.getSerialNoss()!=null){
								generalQueryDto.setSerials(Arrays.asList(StockTrackingEntity.getSerialNoss().split(",")));
								Collections.sort(generalQueryDto.getSerials());
							}
							try {
								//sonar-352203，报空指针，现将代码还原
								// 填入预计到达下一部门时间，预计派送提货时间，如果是分批,通过出发部门，到达部门，件号去查询，否则通过出发部门，到达部门去查询
								FeedbackDto feedbackDto = calculateTransportPathService
										.getNextOrgAndTimeForRWSplitting(waybillNo, list
												.get(0), handOverBillEntity
												.getDepartDeptCode());
								if (feedbackDto.getPathDetailEntity() != null) {
									generalQueryDto
									.setPlanArriveTime(feedbackDto
											.getPathDetailEntity()
											.getPlanArriveTime());
									generalQueryDto
									.setPlanPickupTime(feedbackDto
											.getPathDetailEntity()
											.getPlanPickupTime());
								}
							} catch (Exception e) {
								LOGGER.error("TrackingService.queryWaybillStatusByWaybillNoForPkp 报错：" + StringUtils.substring(e.getMessage(), 0, LoadConstants.SONAR_NUMBER_100));
							}
							generalQueryDto
							.setIfAvailedPickup(TransportPathConstants.NOTAVAILEDPICKUP);
							
							result.add(generalQueryDto);
//						}
					}
				}

			}

		}
		//判断下一部门是否是外发  工号272681 2015-7-15
		if (result != null) {
		for (GeneralQueryDto ge : result) {
				OrgAdministrativeInfoEntity entity = new OrgAdministrativeInfoEntity();
				entity.setName(ge.getNextOrgCode());
				List<OrgAdministrativeInfoEntity> list = orgAdministrativeInfoDao.queryOrgAdministrativeInfoExactByEntity(entity,0,LoadConstants.SONAR_NUMBER_5) ;
				
				WaybillInfoByWaybillNoReultDto waybill = waybillQueryService.queryWaybillInfoByWaybillNo(waybillNo);
				boolean bbl = false;
				if(waybill != null){
					bbl = WaybillConstants.directDetermineIsExpressByProductCode(waybill.getProductCode());
				    
				}
				
				
				if(CollectionUtils.isNotEmpty(list)){
					String code = null;
					code = list.get(0).getCode();
					if (orgAdministrativeInfoService.queryUnifiedCodeByCode(code) == null && bbl) {
						// 下一部门如果不是德邦的部门，状态信息里面不予显示
						ge.setNextOrgCode(null);
					}
				}else if(bbl){
					// 下一部门如果不是德邦的部门，状态信息里面不予显示
					ge.setNextOrgCode(null);
				}
			}
		}
		return result;
		
	}

	/**
	 * @param handOverBillEntity
	 * @param generalQueryDto
	 * @param orgAdministrativeInfoEntity
	 */
	private void initAction(HandOverBillEntity handOverBillEntity,
			GeneralQueryDto generalQueryDto,
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity) {
		if (orgAdministrativeInfoEntity
				.checkSaleDepartment()) {
			if (LoadConstants.HANDOVERBILL_STATE_ALREADY_ARRIVE == handOverBillEntity
					.getHandOverBillState()) {
				// 营业部到达
				generalQueryDto
						.setAction(TransportPathConstants.GENERAL_QUERY_ARRIVEDEST);
			} else {
				// 营业部出发
				generalQueryDto
						.setAction(TransportPathConstants.GENERAL_QUERY_DEPARTBILLING);
			}
		} else {
			if (LoadConstants.HANDOVERBILL_STATE_ALREADY_ARRIVE == handOverBillEntity
					.getHandOverBillState()) {
				// 已到达中转场
				generalQueryDto
						.setAction(TransportPathConstants.GENERAL_QUERY_ARRIVETRANSFERCENTER);
			} else {
				// 中转运输
				generalQueryDto
						.setAction(TransportPathConstants.GENERAL_QUERY_INTRANSIT);
			}
		}
	}
	
	/**
	 * 驻地部门转成转运场
	 */
	private String getTranCentreCode(String saleDepartCode)
	{
		SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(saleDepartCode);
		if(saleDepartmentEntity!=null){
			if(StringUtils.equals(FossConstants.YES, saleDepartmentEntity.getStation())){
					return saleDepartmentEntity.getTransferCenter();
			}
		}
		return null;

	}

	/**
	 * ************************ 跟踪运单执行轨迹底层实现 *************************.
	 */
	private ITrackingDao trackingDao;
	private ILabeledGoodService labeledGoodService;
	/**
	 * ************************ 走货路径 *************************.
	 */
	private ITransportationPathDao transportationPathDao;
	/**
	 * ************************ 走货路径服务
	 */
	private ICalculateTransportPathService calculateTransportPathService;

	private IHandOverBillService handOverBillService;

	IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	ISaleDepartmentService saleDepartmentService;

	/**
	 * 设置 ************************ 跟踪运单执行轨迹底层实现 *************************.
	 * 
	 * @param trackingDao
	 *            the new *************** ********* 跟踪运单执行轨迹底层实现
	 *            ********************** ***
	 */
	public void setTrackingDao(ITrackingDao trackingDao) {
		this.trackingDao = trackingDao;
	}

	/**
	 * 设置 ************************ 走货路径服务 *************************.
	 * 
	 * @param calculateTransportPathService
	 *            the new *************** ********* 走货路径服务 ******
	 *            *******************
	 */
	public void setCalculateTransportPathService(
			ICalculateTransportPathService calculateTransportPathService) {
		this.calculateTransportPathService = calculateTransportPathService;
	}

	/**
	 * Gets the transportation path dao.
	 * 
	 * @return the transportation path dao
	 */
	public ITransportationPathDao getTransportationPathDao() {
		return transportationPathDao;
	}

	/**
	 * Sets the transportation path dao.
	 * 
	 * @param transportationPathDao
	 *            the new transportation path dao
	 */
	public void setTransportationPathDao(
			ITransportationPathDao transportationPathDao) {
		this.transportationPathDao = transportationPathDao;
	}

	public void setLabeledGoodService(ILabeledGoodService labeledGoodService) {
		this.labeledGoodService = labeledGoodService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setHandOverBillService(IHandOverBillService handOverBillService) {
		this.handOverBillService = handOverBillService;
	}

	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	/**
	 * 
	 * <p>查询货物到达，签收情况--for CC</p> 
	 * @author alfred
	 * @date 2014-7-31 下午5:10:16
	 * @param waybillNo
	 * @return 
	 * @see com.deppon.foss.module.transfer.departure.api.server.service.ITrackingService#queryWaybillArrStatus(java.lang.String)
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public Map queryWaybillArrStatus(String waybillNo) {
		// TODO Auto-generated method stub
		return trackingDao.queryWaybillArrStatus(waybillNo);
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
		return trackingDao.queryWaybillIsOuter(waybillNo);
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
		return trackingDao.queryPathdetail(waybillNo, billingOrg, destOrg);
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
		return trackingDao.queryCitynameForCC(orgcode);
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
		return trackingDao.queryOrgtypeForCC(orgcode);
	}

	/**
	 * 建立卸车任务 zwd 20150105 200968
	 * @param handoverBillDTO
	 * @param serialNo
	 * @return
	 */
	public List<HandoverBillDTO> queryUnloadTaskByWayBillNo(
			HandoverBillDTO handoverBillDTO, String serialNo) {
		String no ;
		if(StringUtils.isNotEmpty(serialNo)){
			no = serialNo;
		}else{
			// 取得第一个交接单的第一个流水号
			no = getFirstSerialNoByWayBillNo(handoverBillDTO);
		}
		handoverBillDTO.setSerialNo(no);
		// 查询卸车任务信息轨迹
		List<HandoverBillDTO> unloadTaskList = trackingDao.queryUnloadTaskByWayBillNo(handoverBillDTO);
		return unloadTaskList;
	}
	/**
	 * 点单任务 cl 20160115 272681
	 * @param handoverBillDTO
	 * @param serialNo
	 * @return
	 */
	public List<HandoverBillDTO> queryOrderTaskByWayBillNo(
			HandoverBillDTO handoverBillDTO, String serialNo) {
		String no ;
		if(StringUtils.isNotEmpty(serialNo)){
			no = serialNo;
		}else{
			// 取得第一个交接单的第一个流水号
			no = getFirstSerialNoByWayBillNo(handoverBillDTO);
		}
		handoverBillDTO.setSerialNo(no);
		// 查询点单任务信息轨迹
		List<HandoverBillDTO> orderTaskList = trackingDao.queryOrderTaskByWayBillNo(handoverBillDTO);
		return orderTaskList;
	}
	/**
	 * 提供给综合查询的接口,返回该运单上分拣信息
	 * @author zwd 200968
	 * @date 2014年12月30日 15:15:31
	 * 
	 * */
	public List<HandoverBillDTO> querySortingScanByWayBillNo(
			HandoverBillDTO handoverBillDTO, String serialNo) {
		String no ;
		if(StringUtils.isNotEmpty(serialNo)){
			no = serialNo;
		}else{
			// 取得第一个交接单的第一个流水号
			no = getFirstSerialNoByWayBillNo(handoverBillDTO);
		}
		handoverBillDTO.setSerialNo(no);
		// 查询上分拣轨迹
		List<HandoverBillDTO> sortingScan = trackingDao.querySortingScanByWayBillNo(handoverBillDTO);
		return sortingScan;
		
	}
	

	
	/**
	 * 提供给综合查询的接口,返回该运单包扫描信息
	 * @author zwd 200968
	 * @date 2014年12月30日 15:15:31
	 * 
	 * */
	public List<HandoverBillDTO> queryExpressPackageDetailByWayBillNo(
			HandoverBillDTO handoverBillDTO, String serialNo) {
		String no ;
		if(StringUtils.isNotEmpty(serialNo)){
			no = serialNo;
		}else{
			// 取得第一个交接单的第一个流水号
			no = getFirstSerialNoByWayBillNo(handoverBillDTO);
		}
		handoverBillDTO.setSerialNo(no);
		// 查询包信息轨迹
		List<HandoverBillDTO> expressPackageDetail = trackingDao.queryExpressPackageDetailByWayBillNo(handoverBillDTO);
		return expressPackageDetail;
	}
	
	/**
	 * 提供给综合查询的接口,返回该运单空运通知客户信息
	 * @author zwd 200968
	 * @date 2015年9月15日 15:15:31
	 * 
	 * */
	public List<HandoverBillDTO> queryAirNotifyCustomersSmsInfoByWayBillNo(
			HandoverBillDTO handoverBillDTO, String serialNo) {
		String no ;
		if(StringUtils.isNotEmpty(serialNo)){
			no = serialNo;
		}else{
			// 取得第一个交接单的第一个流水号
			no = getFirstSerialNoByWayBillNo(handoverBillDTO);
		}
		handoverBillDTO.setSerialNo(no);
		// 查询空运通知信息
		List<HandoverBillDTO> airNotifyCustomersSmsInfo = trackingDao.queryAirNotifyCustomersSmsInfoByWayBillNo(handoverBillDTO);
		return airNotifyCustomersSmsInfo;
	}
	
	
	/**
	 * 空运到达:代理到机场提货 zwd 2015-08-07 200968
	 * @param handoverBillDTO
	 * @param serialNo
	 * @return
	 */
	public List<HandoverBillDTO> queryFlightArriveFromAirWaybillNoPickUp(
			HandoverBillDTO handoverBillDTO, String serialNo) {
		
		String no ;
		if(StringUtils.isNotEmpty(serialNo)){
			no = serialNo;
		}else{
			// 取得第一个交接单的第一个流水号
			no = getFirstSerialNoByWayBillNo(handoverBillDTO);
		}
		handoverBillDTO.setSerialNo(no);
		
		/**
		 * 根据运单号和流水号 去查找空运到达信息
		 */
		List<HandoverBillDTO> airQueryFlightArriveList = new ArrayList<HandoverBillDTO>();
		airQueryFlightArriveList = trackingDao.queryFlightArriveFromAirWaybillNoPickUp(handoverBillDTO);
		return airQueryFlightArriveList;
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
	@Override
	public List<HandoverBillDTO> getTallyerHandoverBilllist(
			HandoverBillDTO handoverBillDTO, String serialNo) {
		String no;
		if (serialNo != null) {
			no = serialNo;
		} else {
			// 取得第一个交接单的第一个流水号
			no = getFirstSerialNoByWayBillNo(handoverBillDTO);
		}
		if (no == null) {
			LOGGER.error("不存在流水号,随便从运单里找一个流水号");
			List<LabeledGoodEntity> labellist = labeledGoodService
					.queryAllSerialByWaybillNo(handoverBillDTO.getWaybillNo());
			if (labellist != null && labellist.size() > 0) {
				no = labellist.get(0).getSerialNo();
			}
		}
		// 根据交接流水明细取得该运单的轨迹
		handoverBillDTO.setSerialNo(no);
		// 查询具体的轨迹、出发、到达
		List<HandoverBillDTO> list = trackingDao.getTallyerHandoverBilllist(handoverBillDTO);
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
	@Override
	public List<HandoverBillDTO> getDriverHandoverBilllist(
			HandoverBillDTO handoverBillDTO, String serialNo) {
		String no;
		if (serialNo != null) {
			no = serialNo;
		} else {
			// 取得第一个交接单的第一个流水号
			no = getFirstSerialNoByWayBillNo(handoverBillDTO);
		}
		if (no == null) {
			LOGGER.error("不存在流水号,随便从运单里找一个流水号");
			List<LabeledGoodEntity> labellist = labeledGoodService
					.queryAllSerialByWaybillNo(handoverBillDTO.getWaybillNo());
			if (labellist != null && labellist.size() > 0) {
				no = labellist.get(0).getSerialNo();
			}
		}
		// 根据交接流水明细取得该运单的轨迹
		handoverBillDTO.setSerialNo(no);
		// 查询具体的轨迹、出发、到达
		List<HandoverBillDTO> list = trackingDao.getDriverHandoverBilllist(handoverBillDTO);
		return list;
	}
	
	/**
	 * 空运到:货物到达代理处 zwd 2015-08-07 200968
	 * @param handoverBillDTO
	 * @param serialNo
	 * @return
	 */
	public List<HandoverBillDTO> queryFlightArriveFromAirWaybillNoGoods(
			HandoverBillDTO handoverBillDTO, String serialNo) {
		
		String no ;
		if(StringUtils.isNotEmpty(serialNo)){
			no = serialNo;
		}else{
			// 取得第一个交接单的第一个流水号
			no = getFirstSerialNoByWayBillNo(handoverBillDTO);
		}
		handoverBillDTO.setSerialNo(no);
		
		/**
		 * 根据运单号和流水号 去查找空运到达信息
		 */
		List<HandoverBillDTO> airQueryFlightArriveList = new ArrayList<HandoverBillDTO>();
		airQueryFlightArriveList = trackingDao.queryFlightArriveFromAirWaybillNoGoods(handoverBillDTO);
		return airQueryFlightArriveList;
	}

	@Override
	public String queryActualPath(String waybill) {
		/**
		 * @author 283244
		 * 综合页面查询走货实际路径轨迹
		 * */
		String actualPath = trackingDao.querForBaseInfo(waybill);
		if(null==actualPath){
			return "";
		}
		return actualPath;
	}

	/**
	 * 运单跟踪界面添加货物状态
	 * 
	 * @param trackingWaybillDtoList
	 * @return
	 */
	@Override
	public List<TrackingWaybillDto> queryTrackingWaybillDtoList(
			List<TrackingWaybillDto> trackingWaybillDtoList) {
		List<TrackingWaybillDto> newTrackingWaybillDtoList = new ArrayList<TrackingWaybillDto>();
		for (int i = 0; i < trackingWaybillDtoList.size(); i++) {
			// 获取运单号
			String waybillNo = trackingWaybillDtoList.get(i).getWaybillNo();
			if (waybillManagerService.queryIsExpressBill(waybillNo, null)) {
				// 为快递，不做处理
				LOGGER.info("为快递单，不处理");
			} else {
				// 非快递
				LOGGER.info("非快递单，查询是否为偏线");
				// 判断是否为偏线
				getTrackingWaybillDto(trackingWaybillDtoList.get(i), waybillNo);
			}
			newTrackingWaybillDtoList.add(trackingWaybillDtoList.get(i));
		}
		return newTrackingWaybillDtoList;
	}

	/**
	 * 判定运单是否为偏线且是否已签收
	 * 
	 * @param condition
	 * @param dto
	 * @param waybillNo
	 */
	private void getTrackingWaybillDto(TrackingWaybillDto dto, String waybillNo) {
		// 判断是否为偏线
		WaybillEntity waybillEntity = waybillDao.queryWaybillByNo(waybillNo);
		// 判断是否为偏线
		if (null != waybillEntity && StringUtils.equals(waybillEntity.getProductCode(), "PLF")) {
			// 为偏线
			LOGGER.info("为偏线");
			// 以FOSS系统货物状态为判断依据(偏线直接显示为空)
			dto.setWayBillStatus("IS-NULL");
		} else {
			// 非偏线
			LOGGER.info("非偏线，判断是否已签收");
			// 设置默认查询条件
			WaybillSignResultEntity waybillSignResultEntity = new WaybillSignResultEntity();
			waybillSignResultEntity.setWaybillNo(waybillNo);
			waybillSignResultEntity.setActive("Y");
			waybillSignResultEntity = waybillSignResultService.queryWaybillSignResultByWaybillNo(waybillSignResultEntity);
			// 判断签收状态是否为空
			if (null != waybillSignResultEntity
					&& null != waybillSignResultEntity.getSignSituation()) {
				String situation = waybillSignResultEntity.getSignSituation();
				// 判断签收状态为正常签收或异常签收
				if (StringUtils.equals(situation, QueryTrackingWaybillConstants.OPERATE_TYPE_NORMAL_SIGN)
						|| StringUtils.equals(situation, QueryTrackingWaybillConstants.OPERATE_TYPE_EXCEPTION_SIGN)) {
					LOGGER.info("已签收，判断为部分签收或全部签收");
					// 根据运单号查询库存件数
					List<Integer> integers = waybillStockDao
							.queryStockQtyByWaybillNo(waybillNo);
					// 判断库存是否为空
					if (CollectionUtils.isNotEmpty(integers)
							&& integers.get(0) != null
							&& integers.get(0).intValue() >= 0) {
						// 部分签收
						LOGGER.info("部分签收");
						// 判断运单状态
						isLaterOrDepartment(dto, waybillNo);
					} else {
						// 已签收
						LOGGER.info("已签收");
						dto.setWayBillStatus("IS-NULL");
					}
				} else {
					LOGGER.info("未签收，判断运单状态");
					isLaterOrDepartment(dto, waybillNo);
				}
			} else {
				// 运单状态为空
				LOGGER.info("签收状态为空");
				isLaterOrDepartment(dto, waybillNo);
			}
		}
	}

	/**
	 * 根据运单号判定运单状态
	 * 
	 * @param dto
	 * @param waybillNo
	 * @param targetOrgCode
	 */
	private void isLaterOrDepartment(TrackingWaybillDto dto, String waybillNo) {
		// 查询运单的实际到达详情
		ActualArriveTimeDTO actualArriveTimeDTOs = trackingDao.getArriveTimeByWaybillNo(waybillNo);
		// 根据运单号查询运单详情
		WaybillEntity waybillEntity = waybillDao.queryWaybillByNo(waybillNo);
		// 判断当实际到达时间、当前库存件数、运单开单总件数不为空 且 当前库存件数是否和开单总件数相等
		if (null != actualArriveTimeDTOs
				&& null != actualArriveTimeDTOs.getArriveTime()
				&& actualArriveTimeDTOs.getQytNumber() != 0
				&& null != waybillEntity
				&& null != waybillEntity.getGoodsQtyTotal()
				&& waybillEntity.getGoodsQtyTotal().intValue() != 0
				&& waybillEntity.getGoodsQtyTotal().intValue() == actualArriveTimeDTOs.getQytNumber()) {
			LOGGER.info("已到达目的站");
			// 到达目的站 该票货物的当前位置是目的部门
			dto.setWayBillStatus("ARRIVE-THE-DESTINATION");
		} else {
			LOGGER.info("未到达目的站");
			// 正常在途 该票货物未到达目的部门
			dto.setWayBillStatus("NORMAL-ON-WAY");
			// 判断是否为分批配载
			String flag = stockService.querySeparateStatusByStock(waybillNo);
			// 库存无法判断时,查询走货路径
			if (null == flag) {
				TransportPathEntity transportPathEntity = transportationPathDao.queryTransportPath(waybillNo);
				if (transportPathEntity != null && transportPathEntity.getIfPartialStowage() != null) {
					flag = transportPathEntity.getIfPartialStowage();
				} else {
					// 当走货路径无法判断时，查询交接记录
					flag = handOverBillService.queryGoodsBeSeparatedFromHandOverBillByWaybillNo(waybillNo);
					if (null == flag) {
						flag = FossConstants.NO;
					}
				}
			}
			if (StringUtils.equals(flag, FossConstants.YES)) {
				LOGGER.info("为分批配载");
				// 分批配载
				// 货物已分批运输，并且有部分或者全部货物未到达最终目的站
				dto.setWayBillStatus("BATCH-LOADING");
			}
			if (null != waybillEntity && null != waybillEntity.getPreArriveTime()) {
				// 当前时间
				Date currTime = new Date();
				if (currTime.after(waybillEntity.getPreArriveTime())) {
					LOGGER.info("时效晚到");
					// 时效晚到 该票货物当前的位置不是目的部门
					dto.setWayBillStatus("TIME-ARRIVE-LATE");
				}
			}
		}
	}
	
	/** 合伙人营业部交接虚拟库存
	 * @author 360903-foss
	 * @date 2016年11月22日 16:20:27
	 */
	private  void getVirtualStockPTP(InOutStockEntity inOutStockEntity,List<String> stockSerials,List<GeneralQueryDto> result){
		// 查询所有的库存信息
		List<StockTrackingEntity> stocklist = trackingDao.queryUniqueStockSale(inOutStockEntity);
		//存放库存流水
		List<String> stockSaleSerials = new ArrayList<String>();
		//实体库没数据走虚拟库存数
		//sonar-352203
		if(CollectionUtils.isEmpty(stocklist)){
			return;
		}
			// 查询虚拟库存中的部门，groupby 当前部门，下一部门
			List<StockTrackingEntity> stockgrouplist = trackingDao.queryUniqueStockSaleNext(inOutStockEntity);
			//sonar-352203
			if (stockgrouplist == null || stocklist == null) {
				return;
			}
				for (StockTrackingEntity stockTrackingEntity : stockgrouplist) {
					int count = 0;
					for (StockTrackingEntity stockEntity : stocklist) {
						if (StringUtils.equals(stockTrackingEntity.getOrgCode(),
								stockEntity.getOrgCode())
								&& StringUtils.equals(
										stockTrackingEntity.getNextOrgCode(),
										stockEntity.getNextOrgCode())) {
							if (!stockSaleSerials.contains(stockEntity.getSerialNO())) {
								stockSaleSerials.add(stockEntity.getSerialNO());
								count++;
							}
						}
					}
					if (stockSaleSerials != null) {
						stockSerials.addAll(stockSaleSerials);
						GeneralQueryDto generalQueryDto = new GeneralQueryDto();
						// 根据部门编码，获取组织信息
						OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(stockTrackingEntity.getOrgCode());
						if (orgAdministrativeInfoEntity != null) {
							generalQueryDto.setNowPosition(orgAdministrativeInfoEntity.getName());
							// 查看当前部门是否是营业部
							if (orgAdministrativeInfoEntity.checkSaleDepartment()) {
								//合伙人虚拟库存
								generalQueryDto.setAction(TransportPathConstants.GENERAL_QUERY_VIRTUALSTORE);
							} 
						}
						orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(stockTrackingEntity.getNextOrgCode());
						if (orgAdministrativeInfoEntity != null) {
							generalQueryDto.setNextOrgCode(orgAdministrativeInfoEntity.getName());
						}
						generalQueryDto.setGoodsCount(count);
						if(stockTrackingEntity.getSerialNoss()!=null){
							generalQueryDto.setSerials(Arrays.asList(stockTrackingEntity.getSerialNoss().split(",")));
							Collections.sort(generalQueryDto.getSerials());
						}
						result.add(generalQueryDto);
					}
				}
//			}
//		}
	}

}