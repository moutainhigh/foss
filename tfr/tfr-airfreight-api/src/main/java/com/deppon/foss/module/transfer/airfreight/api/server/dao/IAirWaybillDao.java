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
 *  PROJECT NAME  : tfr-airfreight-api
 *  
 *  package_name  : 
 *  
 *  FILE PATH          :/IAirWaybillDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillSerialNoEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirArriveSendInfoDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirStockInfoDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirWayBillDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirWaybillDetailDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.QueryAirArriveInfoDto;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.UploadingEdiDto;

/**
 * 查询航空正单dao 
 * @author 099197-foss-zhoudejun
 * @date 2012-12-25 下午7:29:44
 */
public interface IAirWaybillDao {
	
	/**
	 * 查询航空正单
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-16 下午3:37:43
	 */
	public List<AirWaybillEntity> queryAirWayBillList(AirWayBillDto airWayBillDto,int limit ,int start);
	
	/**
	 * 根据ID查询航空正单
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-16 下午3:37:57
	 */
	public AirWaybillEntity queryResultEntity(AirWayBillDto airWayBillDto);
	
	/**
	 * 获取总记录数
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-16 下午3:38:05
	 */
	public Long getCount(AirWayBillDto airWayBillDto);
	
	/**
	 * 根据ID获取当前航空正单付款状态
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-16 下午3:38:27
	 */
	public String queryStatus(AirWayBillDto airWayBillDto);
	
	/**
	 * 根据ID获取打印数据
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-3 下午3:33:16
	 */
	public List<AirWaybillDetailEntity> queryAirWaybillListByPrint(String id);
	
	/**
	 * 根据ID查询空运外发清单打印数据
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-5 上午10:53:41
	 */
	public AirWaybillEntity queryAirWaybillEntityPrint(String airwaybillId);
	
	/**
	 * 根据ID获取航空正单批量打印数据
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-6 下午2:30:08
	 */
	public List<AirWaybillEntity> queryAirWaybillListPrint(String[] ids);
	
	/**
	 * 提供给结算校验根据航空正单号和代理编码校验是否存在于空运配载单、合大票清单和中转清单中是否存在记录
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-17 下午3:58:41
	 */
	int queryAirWaybillNoPickupBilllJoinTransferBillNo(Map<String,Object> dataMap);
	/**
	 * 根据正单号查询合大票清单表中是否存在数据
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-17 下午5:35:20
	 */
	int queryAirPickupBilllNo(Map<String,Object> dataMap);
	
	/**
	 * 根据正单号查询中转清单表中是否存在数据
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-17 下午5:35:20
	 */
	int queryAirTransferPickupBilllNo(Map<String,Object> dataMap);
	
	/**
	 * 更新航空正单跟踪的相关信息: 实际出发时间、实际到达时间、跟踪状态、修改人、修改时间 
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-21 下午6:25:51
	 */
	public void updateAirWayBillTrack(List<AirWaybillEntity> airWaybillEntityList);
	
	/**
	 * 根据运单号查询该运单是否在航空正单明细中存在记录
	 * @param waybillNo 运单号
	 * @param destOrgCode 操作部门
	 * @return (true/false)true表示存在与之匹配的记录 false未找到与之匹配的记录
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-24 上午11:28:48
	 */
	boolean queryWaybillNoExists(String waybillNo, String destOrgCode);
	
	
	/**
	 * 根据运单号查询该运单是否有航空正单交接单出库记录
	 * @param waybillNo 运单号
	 * @return (true/false)true表示存在与之匹配的记录 false未找到与之匹配的记录
	 * @author 099197-foss-shixiaowei
	 * @date 2012-12-24 上午11:28:48
	 */
	boolean queryWaybillNoStockExists(String waybillNo);
	
	/**
	 * 根据正单号查询运单list
	 * @param  airWaybillNo 正单号
	 * @return List<UploadingEdiDto> 返回list<dto>
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-26 下午1:45:31
	 */
	List<UploadingEdiDto> queryWayBillByAirWaybillNo (String airWaybillNo);
	
	/**
	 * 查询空运到达派送信息录入情况统计
	 * @param  queryAirArriveInfoDto
	 * @return List<AirArriveSendInfoDto>
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-26 下午2:44:53
	 */
	List<AirArriveSendInfoDto> queryFlightArriveSendInfo (QueryAirArriveInfoDto queryAirArriveInfoDto);
	
	/**
	 * 查询空运库存信息
	 * @param  queryAirArriveInfoDto
	 * @return List<AirArriveSendInfoDto>
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-26 下午2:44:53
	 */
	List<AirStockInfoDto> queryAirStockInfo(QueryAirArriveInfoDto queryAirArriveInfoDto);

	/**
	 * 根据ID获取打印数据
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-3 下午3:33:16
	 */
	List<AirWaybillDetailEntity> queryAirWaybillListForPrint(String id);

	/**
	 * 查询航空正单（不分页）
	 * @author 099197-foss-liuzhaowei
	 * @date 2013-05-21 上午11:36:32
	 */
	List<AirWaybillEntity> queryAirWayBillListNoPage(AirWayBillDto airWayBillDto);
	/**
	 * 查询正单中运单个数
	 * @param waybillNo 运单号
	 * @return int
	 * @author 046130-foss-xuduowei
	 * @date 2013-07-12 下午3:16:23
	 */
	int queryWaybillDetailsByWaybillNo(String waybillNo);
	/**
	 * 查询合票中运单个数
	 * @param waybillNo 运单号
	 * @return int
	 * @author 046130-foss-xuduowei
	 * @date 2013-07-12 下午3:16:23
	 */
	int queryPickbillDetailsByWaybillNo(String waybillNo);
	/**
	 * 获取运单的正单制作部门
	 * @param waybillNo 运单号
	 * @return String
	 * @author 046130-foss-xuduowei
	 * @date 2013-07-12 下午3:16:23
	 */
	String queryAirWaybillDept(String waybillNo);
	
	/**
	 * 根据运单号查询所在航空正单的配载类型
	 * @param AirWaybillDetailDto 
	 * @return the list
	 * @author 200968  zwd 
	 * @date 2015-04-24 上午15:36:32
	 */
	List<AirWaybillEntity> queryAirWayBillListByWayBill(AirWaybillDetailDto airWaybillDetailDto);

	/**
	 * 
	* @description 根据正单号查询合大票清单表 校验该正单是否已制作合大票
	* @param airWaybillNo
	* @return true-是 false-否
	* @version 1.0
	* @author 269701-foss-lln
	* @update 2016年4月26日 下午4:05:30
	 */
	 boolean queryIsMakePickByBillNo(String airWaybillNo);
	 /**
	  * 
	 * @description 根据航空正单明细id查询 航空正单流水list
	 * @param detialId
	 * @return
	 * @version 1.0
	 * @author 269701-foss-lln
	 * @update 2016年5月18日 上午10:41:08
	  */
	 List<AirWaybillSerialNoEntity> queryWaybillSerialNoListToOpp(String detialId);
	 /**
	  * 
	 * @description 根据航空正单id 查询航空正单明细list
	 * @param id
	 * @return
	 * @version 1.0
	 * @author 269701-foss-lln
	 * @update 2016年5月18日 上午11:24:06
	  */
	 List<AirWaybillDetailEntity> queryAirWaybillDetialList(String id);

	 /**
	  * 
	 * @description 根据航空正单运单号 查询航空正单明细
	 * @param waybillNo
	 * @return
	 * @version 1.0
	 * @author 268220 chenmin
	 * @update 2016年6月1829日 下午16:33:06
	  */
	AirWaybillDetailEntity queryAirWaybillDetial(String waybillNo);
}