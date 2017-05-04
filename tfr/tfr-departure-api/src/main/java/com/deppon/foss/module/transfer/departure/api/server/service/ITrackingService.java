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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/server/service/ITrackingService.java
 *  
 *  FILE NAME          :ITrackingService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.api.server.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.pickup.pickup.api.shared.dto.TrackingWaybillDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.module.transfer.departure.api.shared.domain.LoadDetailEntity;
import com.deppon.foss.module.transfer.departure.api.shared.dto.HandoverBillDTO;
import com.deppon.foss.module.transfer.departure.api.shared.dto.OnthewayDTO;
import com.deppon.foss.module.transfer.departure.api.shared.dto.StockTrackingDTO;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.GeneralQueryDto;
/**
 * 
 * 运单轨迹服务跟踪
 * @author foss-liubinbin(for IBM)
 * @date 2013-1-8 下午12:52:09
 */
public interface ITrackingService{
	/**
	 * 
	 * 通过运单号查询交接单信息
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 下午12:55:13
	 */
	List<HandoverBillDTO> getHandoverBillByWayBillNo(HandoverBillDTO handoverBillDTO,String serialNo);
	/**
	 * 
	 * 通过运单号查询装车的轨迹
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 下午12:55:13
	 */
	List<LoadDetailEntity> getLoadDetailTrackingByWayBillNo(HandoverBillDTO handoverBillDTO, String serialNo);
	
	/**
	 * 
	 * 通过运单号查询所有的轨迹
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 下午12:55:13
	 */
	List<HandoverBillDTO> getAllHandoverBillByWayBillNo(HandoverBillDTO handoverBillDTO);
	/**
	 * 
	 * 通过运单号查询交接单信息
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 下午12:55:13
	 */
	List<HandoverBillDTO> getUnloadBillByWayBillNo(HandoverBillDTO handoverBillDTO,String serialNo);
	/**
	 * 
	 * 通过运单号查询车辆跟踪信息
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 下午12:55:13
	 */
	List<OnthewayDTO> getTaskTrackingByWayBillNo(String waybillNo);

	/**
	 * 
	 * 库存状况查询、库存中（接送货）
	 * @param  waybillNo 运单号
	 * @param createTime 开单时间
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 下午12:55:13
	 */
	List<StockTrackingDTO> getInStockTrackingByWayBillNo(String waybillNo,Date createTime);
	
	/**
	 * 
	 * 库存状况查询、出库（接送货）
	 * @param  waybillNo 运单号
	 * @param createTime 开单时间
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 下午12:55:13
	 */
	List<StockTrackingDTO> getOutStockTrackingByWayBillNo(String waybillNo,Date createTime);
	
	/**
	 * 
	 * 通过运单号查询正单信息
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 下午12:55:13
	 */
	List<AirWaybillEntity> queryAirWayBillListByWaybillNo(String waybillNo);
	
	/**
	 * 根据运单号获取交接单运单列表
	 * @author 045923-foss-liubinbin
	 * @date 2012-10-24 下午5:19:25
	 * @param handOverBillNo 交接单号
	 */
	List<HandoverBillDTO> queryHandOverBillDetailByWaybillNo(String waybillNo, String serialNo);
	
	/**
	 * 
	 * 通过运单号查询包装信息
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 下午12:55:13
	 */
	List<HandoverBillDTO> getPackageByWayBillNo(String waybillNo);
	
	/**
	 * 
	 * 通过运单号查询登入获取信息
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 下午12:55:13
	 */
	List<HandoverBillDTO> getPackageAreaInByWayBillNo(HandoverBillDTO handoverBillDTO, String serialNo,Integer goodsQtyTotal);
	/**
	 * 
	 * 通过运单号查询入库的类型
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 下午12:55:13
	 */
	List<String> getInStockTypesWayBillNo(HandoverBillDTO handoverBillDTO,  String[] types);
	
	/**
	 * 
	 * 通过运单号查询出库的类型
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 下午12:55:13
	 */
	List<String> getOutStockTypesWayBillNo(HandoverBillDTO handoverBillDTO,  String[] types);
	/**
	 * 
	 * 通过运单号查询登出获取信息
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 下午12:55:13
	 */
	List<HandoverBillDTO> getPackageAreaOutByWayBillNo(HandoverBillDTO handoverBillDTO, String serialNo,Integer goodsQtyTotal);
 
	/**
	 * 提供给综合查询的接口,返回运单的状态明细, group by状态 ,出发部门,到达部门,出发时间,到达时间,下一到达时间
	 * 
	 * @author huyue
	 * @date 2012-12-24 上午10:30:50
	 */
	List<GeneralQueryDto> queryWaybillStatusByWaybillNoForPkp(String waybillNo);
	/**
	 * 提供给综合查询的接口,返回该运单叉车司机扫描信息
	 * 
	 * @author heyongdong
	 * @date 2014年9月9日 15:15:31
	 * 
	 * */
	List<HandoverBillDTO> getTrayScanByWayBillNo(HandoverBillDTO handoverBillDTO,String serialNo);
	
	/**
	 * 提供给综合查询的接口,返回该运单上分拣信息
	 * @author zwd 200968
	 * @date 2014年12月30日 15:15:31
	 * 
	 * */
	List<HandoverBillDTO> querySortingScanByWayBillNo(HandoverBillDTO handoverBillDTO,String serialNo);
	
	/**
	 * 提供给综合查询的接口,返回该运单包扫描信息
	 * @author zwd 200968
	 * @date 2014年12月30日 15:15:31
	 * 
	 * */
	List<HandoverBillDTO> queryExpressPackageDetailByWayBillNo(HandoverBillDTO handoverBillDTO,String serialNo);
	

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
	 * @param serialNo
	 * @return
	 */
	List<HandoverBillDTO> queryUnloadTaskByWayBillNo(HandoverBillDTO handoverBillDTO,String serialNo);

	/**
	 * 提供给综合查询的接口,返回该运单通知客户信息
	 * @author zwd 200968
	 * @date 2015年9月15日 9:15:31
	 * @param handoverBillDTO
	 * @param serialNo
	 * @return
	 */
	
	List<HandoverBillDTO> queryAirNotifyCustomersSmsInfoByWayBillNo(HandoverBillDTO handoverBillDTO,String serialNo);
	
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
	List<HandoverBillDTO> getTallyerHandoverBilllist(HandoverBillDTO handoverBillDTO,String serialNo);
	
	
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
	List<HandoverBillDTO> getDriverHandoverBilllist(HandoverBillDTO handoverBillDTO,String serialNo);
	
	
	/**
	 * 空运到达:代理到机场提货 zwd 2015-08-07 200968
	 * @param handoverBillDTO
	 * @param serialNo
	 * @return
	 */
	List<HandoverBillDTO> queryFlightArriveFromAirWaybillNoPickUp(HandoverBillDTO handoverBillDTO,String serialNo);
	
	/**
	 * 空运到达:货物到达代理处 zwd 2015-08-14 200968
	 * @param handoverBillDTO
	 * @param serialNo
	 * @return
	 */
	List<HandoverBillDTO> queryFlightArriveFromAirWaybillNoGoods(HandoverBillDTO handoverBillDTO,String serialNo);
	/**
	 * 添加点单任务货物轨迹 cl 2016-01-15 272681
	 * @param handoverBillDTO
	 * @param serialNo
	 * @return
	 */
	List<HandoverBillDTO> queryOrderTaskByWayBillNo(
			HandoverBillDTO handoverBill, String serialNo);
	
	/**
	 *综合查询实际线路轨迹
	 *@author 283244
	 * 
	 * 
	 * 
	 * */
	
	String  queryActualPath(String  waybill);
	/**
	 * 获取货物运输状态
	 * @param trackingWaybillDtoList
	 * @return
	 */
	List<TrackingWaybillDto> queryTrackingWaybillDtoList(
			List<TrackingWaybillDto> trackingWaybillDtoList);
}