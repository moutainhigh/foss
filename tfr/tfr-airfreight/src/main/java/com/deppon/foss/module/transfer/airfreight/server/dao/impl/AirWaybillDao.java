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
 *  PROJECT NAME  : tfr-airfreight
 *  
 *  package_name  : 
 *  
 *  FILE PATH          :/AirWaybillDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.airfreight.api.define.AirfreightConstants;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirWaybillDao;
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
 * 查询航空正单dao实现类
 * @author 099197-foss-zhoudejun
 * @date 2012-12-25 下午6:04:26
 */
public class AirWaybillDao extends iBatis3DaoImpl implements IAirWaybillDao {

	private static final String NAMESPACE = "foss.airfreight.";
	
	/**
	 * 查询航空正单
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-16 下午3:37:43
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirWaybillEntity> queryAirWayBillList(AirWayBillDto airWayBillDto,int limit ,int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryAirWayBillList",airWayBillDto,rowBounds);
	}
	
	/**
	 * 查询航空正单（不分页）
	 * @author 099197-foss-liuzhaowei
	 * @date 2013-05-21 上午11:36:32
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirWaybillEntity> queryAirWayBillListNoPage(AirWayBillDto airWayBillDto) {
		return this.getSqlSession().selectList(NAMESPACE + "queryAirWayBillList",airWayBillDto);
	}
	
	/**
	 * 根据ID查询航空正单
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-16 下午3:37:57
	 */
	@Override
	public AirWaybillEntity queryResultEntity(AirWayBillDto airWayBillDto) {
		return (AirWaybillEntity)this.getSqlSession().selectOne(NAMESPACE + "queryAirwaybill",airWayBillDto);
	}

	/**
	 * 获取总记录数
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-16 下午3:38:05
	 */
	@Override
	public Long getCount(AirWayBillDto airWayBillDto) {
		return (Long)this.getSqlSession().selectOne(NAMESPACE + "getCount",airWayBillDto);
	}
	
	/**
	 * 根据ID获取当前航空正单付款状态
	 * @author 099197-foss-zhoudejun
	 * @date 2012-10-16 下午3:38:27
	 */
	@Override
	public String queryStatus(AirWayBillDto airWayBillDto) {
		return (String)this.getSqlSession().selectOne(NAMESPACE + "queryId",airWayBillDto);
	}

	/**
	 * 根据ID获取数据
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-3 下午3:33:16
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirWaybillDetailEntity> queryAirWaybillListByPrint(String id) {
		return this.getSqlSession().selectList(NAMESPACE + "queryAirWaybillListByPrint",id);
	}
	
	/**
	 * 
	* @description 根据航空正单id 查询航空正单明细信息
	* @param id
	* @version 1.0
	* @author 269701-foss-lln
	* @update 2016年5月18日 上午11:23:05
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirWaybillDetailEntity> queryAirWaybillDetialList(String id) {
		return this.getSqlSession().selectList(NAMESPACE + "queryAirWaybillDetialListToOpp",id);
	}
	
	/**
	 * 根据ID获取打印数据
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-3 下午3:33:16
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirWaybillDetailEntity> queryAirWaybillListForPrint(String id) {
		return this.getSqlSession().selectList(NAMESPACE + "queryAirWaybillListForPrint",id);
	}

	/**
	 * 根据ID查询空运外发清单打印数据
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-5 上午10:53:41
	 */
	@Override
	public AirWaybillEntity queryAirWaybillEntityPrint(String airwaybillId) {
		return (AirWaybillEntity)this.getSqlSession().selectOne(NAMESPACE + "queryAirWaybillEntityPrint",airwaybillId);
	}
	/**
	 * 根据ID获取航空正单批量打印数据
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-6 下午2:30:08
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirWaybillEntity> queryAirWaybillListPrint(String[] ids) {
		return this.getSqlSession().selectList(NAMESPACE + "queryAirWaybillListPrint",ids);
	}

	/**
	 * 提供给结算校验根据航空正单号和代理编码校验是否存在于空运配载单、合大票清单和中转清单中是否存在记录
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-17 下午3:58:41
	 */
	@Override
	public int queryAirWaybillNoPickupBilllJoinTransferBillNo(Map<String,Object> dataMap) {
		return (Integer) this.getSqlSession().selectOne(NAMESPACE + "queryAirWaybillNoPickupBilllJoinTransferBillNo",dataMap);
	}
	
	/**
	 * 根据正单号查询合大票清单表中是否存在数据
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-17 下午5:35:20
	 */
	@Override
	public int queryAirPickupBilllNo(Map<String,Object> dataMap) {
		return (Integer)this.getSqlSession().selectOne(NAMESPACE + "queryAirPickupBilllNo",dataMap);
	}
	/**
	 * 根据正单号查询中转清单表中是否存在数据
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-17 下午5:35:20
	 */
	@Override
	public int queryAirTransferPickupBilllNo(Map<String,Object> dataMap) {
		return (Integer)this.getSqlSession().selectOne(NAMESPACE + "queryAirTransferPickupBilllNo",dataMap);
	}

	/**
	 * 更新航空正单跟踪的相关信息: 实际出发时间、实际到达时间、跟踪状态、修改人、修改时间 
	 * @author 099197-foss-zhoudejun
	 * @date 2012-11-21 下午6:25:51
	 */
	@Override
	public void updateAirWayBillTrack(List<AirWaybillEntity> airWaybillEntityList) {
		this.getSqlSession().update(NAMESPACE + "updateAirWayBillTrack",airWaybillEntityList);
	}

	/**
	 * 根据运单号查询该运单是否在航空正单明细中存在记录
	 * @param waybillNo 运单号
	 * @param destOrgCode 操作部门
	 * @return (true/false)true表示存在与之匹配的记录 false未找到与之匹配的记录
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-24 上午11:28:48
	 */
	@Override
	public boolean queryWaybillNoExists(String waybillNo, String destOrgCode) {
		Map<String,String> dataMap = new HashMap<String,String>();
		dataMap.put("waybillNo",waybillNo);
		dataMap.put("destOrgCode",destOrgCode);
		int count = (Integer)this.getSqlSession().selectOne(NAMESPACE + "queryWaybillNoExists",dataMap);
		return count==0?false:true;
	}
	
	/**
	 * 根据运单号查询该运单是否有航空正单交接单出库记录
	 * @param waybillNo 运单号
	 * @return (true/false)true表示存在与之匹配的记录 false未找到与之匹配的记录
	 * @author 099197-foss-shixiaowei
	 * @date 2012-12-24 上午11:28:48
	 */
	@Override
	public boolean queryWaybillNoStockExists(String waybillNo) {
		Map<String,String> dataMap = new HashMap<String,String>();
		dataMap.put("waybillNo",waybillNo);
		dataMap.put("waybillStockState",AirfreightConstants.STOCKING_STATUS_Y);
		int count = (Integer)this.getSqlSession().selectOne(NAMESPACE + "queryWaybillNoStockExists",dataMap);
		return count==0?false:true;
	}
	
	/**
	 * 根据正单号查询运单list
	 * @param  airWaybillNo 正单号
	 * @return List<UploadingEdiDto> 返回list<dto>
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-26 下午1:45:31
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UploadingEdiDto> queryWayBillByAirWaybillNo(String airWaybillNo) {
		return this.getSqlSession().selectList(NAMESPACE +"queryWayBillByAirWaybillNo",airWaybillNo);
	}
	
	/**
	 * 查询空运到达派送信息录入情况统计
	 * @param  queryAirArriveInfoDto
	 * @return List<AirArriveSendInfoDto>
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-26 下午2:44:53
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirArriveSendInfoDto> queryFlightArriveSendInfo(
			QueryAirArriveInfoDto queryAirArriveInfoDto) {
		return this.getSqlSession().selectList(NAMESPACE +"queryFlightArriveSendInfo",queryAirArriveInfoDto);
	}
	
	/**
	 * 查询空运库存信息
	 * @param  queryAirArriveInfoDto
	 * @return List<AirArriveSendInfoDto>
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-26 下午2:44:53
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirStockInfoDto> queryAirStockInfo(
			QueryAirArriveInfoDto queryAirArriveInfoDto) {
		return this.getSqlSession().selectList(NAMESPACE +"queryAirStockInfo",queryAirArriveInfoDto);
	}
	/**
	 * 查询正单中运单个数
	 * @param waybillNo 运单号
	 * @return int
	 * @author 046130-foss-xuduowei
	 * @date 2013-07-12 下午3:16:23
	 */
	@Override
	public int queryWaybillDetailsByWaybillNo(String waybillNo) {
		
		return Integer.parseInt(getSqlSession().selectOne(NAMESPACE + "queryWaybillDetailsByWaybillNo", waybillNo).toString());
	}
	/**
	 * 查询合票中运单个数
	 * @param waybillNo 运单号
	 * @return int
	 * @author 046130-foss-xuduowei
	 * @date 2013-07-12 下午3:16:23
	 */
	@Override
	public int queryPickbillDetailsByWaybillNo(String waybillNo) {
		
		return Integer.parseInt(getSqlSession().selectOne(NAMESPACE + "queryPickbillDetailsByWaybillNo", waybillNo).toString());
	}
	/**
	 * 获取运单的正单制作部门
	 * @param waybillNo 运单号
	 * @return String
	 * @author 046130-foss-xuduowei
	 * @date 2013-07-12 下午3:16:23
	 */
	@Override
	public String queryAirWaybillDept(String waybillNo) {
		Object o = getSqlSession().selectOne(NAMESPACE + "queryAirWaybillDept",waybillNo);
		if(o == null){
			return "";
		}
		return o.toString();
	}

	/**
	 * 根据运单号查询所在航空正单的配载类型
	 * @param AirWaybillDetailDto 
	 * @return the list
	 * @author 200968  zwd 
	 * @date 2015-04-24 上午15:36:32
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirWaybillEntity> queryAirWayBillListByWayBill(
			AirWaybillDetailDto airWaybillDetailDto) {
		return this.getSqlSession().selectList(NAMESPACE + "queryAirWayBillListByWayBill",airWaybillDetailDto);
	}
	/**
	* @description 根据正单号查询合大票表（TFR.T_OPT_AIR_PICKUPBILL）,查看合大票是否生成
	* @version 1.0
	* @author 269701-foss-liulina
	* @param String airWayBill 正单号
	* @return booealn true/已生成合大票；false/未生成合大票
	*/
	public boolean queryIsMakePickByBillNo(String airWayBill){
		Integer count=(Integer)this.getSqlSession().selectOne(NAMESPACE + "queryIsMakePickByBillNo",airWayBill);
	   return count > 0 ? true : false;
	}
	/**
	 * 
	* @description 根据航空正单明细id 查询航空正单流水list
	* @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirWaybillDao#queryWaybillSerialNoListToOpp(java.util.List)
	* @author 269701-foss-lln
	* @update 2016年5月18日 上午10:42:08
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirWaybillSerialNoEntity> queryWaybillSerialNoListToOpp(String detialId) {
		return this.getSqlSession().selectList(NAMESPACE + "queryWaybillSerialNoListToOpp",detialId);
	}
	
	/**
	* @description 根据航空正单明细运单号 查询航空正单明细
	* @param waybillNo 运单号
	* @return AirWaybillDetailEntity
	* @author 268220 chenmin
	* @date 2016-06-29 下午16:36:32
	* @version V1.0
	 */
	@Override
	public AirWaybillDetailEntity queryAirWaybillDetial(String waybillNo) {
		return (AirWaybillDetailEntity) this.getSqlSession().selectOne(NAMESPACE + "queryAirWaybillDetial",waybillNo);
	}
	
	
}