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
 *  FILE PATH          :/AirQueryModifyPickupbillDao.java
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

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirQueryModifyPickupbillDao;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillLogEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.SerialEntity;

/**
 * 查询修改合大票dao实现
 * @author 099197-foss-zhoudejun
 * @date 2012-12-18 上午9:19:29
 * 
 */
public class AirQueryModifyPickupbillDao extends iBatis3DaoImpl implements
		IAirQueryModifyPickupbillDao {
	
	private static final String NAMESPACE = "foss.airfreight.";
	
	/**
	 * 修改合大票
	 * @param airPickupbillEntity
	 * @return (0或1)
	 * @author foss-liuzhaowei
	 * @date 2013-05-20 上午9:41:51
	 */
	@Override
	public int modifyAirPickupbill(AirPickupbillEntity airPickupbillEntity) {
		return this.getSqlSession().update(NAMESPACE + "modifyAirPickupbill",airPickupbillEntity);
	}

	/**
	 * 批量修改合大票明细list
	 * @param airPickupbillDetailList
	 * @return (0或1)
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-18 上午9:41:51
	 */
	@Override
	public int modifyAirPickupbillDetailList(
			List<AirPickupbillDetailEntity> airPickupbillDetailList) {
		return this.getSqlSession().update(NAMESPACE + "modifyAirPickupbillDetailList",airPickupbillDetailList);
	}

	/**
	 * 批量新增合大票明细list 
	 * @param addAirPickupbillDetailList
	 * @return (0或1)
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-18 上午9:42:58
	 */
	@Override
	public int addAirPickupbillDetailList(
			List<AirPickupbillDetailEntity> addAirPickupbillDetailList) {
		return this.getSqlSession().insert(NAMESPACE + "addAirWaybillDetail",addAirPickupbillDetailList);
	}

	/**
	 * 批量删除合大票明细
	 * @param delelePickupbillIds 
	 * @return (0或1)
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-18 上午9:45:10
	 */
	@Override
	public int deleteAirPickupbillDetailList(List<String> delelePickupbillIds) {
		return this.getSqlSession().delete(NAMESPACE + "deleteAirPickupbillDetailList",delelePickupbillIds);
	}
	
	/**
	 * 根据运单号查询空运运单费用及出港信息
	 * @param waybillNo 运单号
	 * @return AirPickupbillEntity
	 * @author 099197-foss-zhoudejun
	 * @date 2013-1-15 下午2:38:23
	 */
	@Override
	@SuppressWarnings("unchecked")
	public AirPickupbillEntity queryAirPickupbill(String waybillNo) {
		List<AirPickupbillEntity> list  = new ArrayList<AirPickupbillEntity>();
		AirPickupbillEntity airPickupbillEntity = null;
		list = this.getSqlSession().selectList(NAMESPACE + "queryAirPickupbill",waybillNo);
		if(!CollectionUtils.isEmpty(list)){
			airPickupbillEntity = list.get(0);
		}
		return airPickupbillEntity;
	}
	
	/**
	 * 根据运单号查询空运运单合票信息  供综合查询显示内部轨迹
	 * @param waybillNo 运单号
	 * @return List<AirPickupbillEntity>
	 * @date 2013-6-13 下午2:38:23
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<AirPickupbillEntity> queryAirPickupbillListForViewTrack(String waybillNo) {
		return this.getSqlSession().selectList(NAMESPACE + "queryAirPickupbillListForViewTrack",waybillNo);
	}
	
	/**
	 * 根据合大票id查询合大票运单明细
	 * @param airPickupbillId 合大票id 
	 * @return List<AirPickupbillDetailEntity>
	 * @author liuzhaowei
	 * @date 2013-6-25  下午2:38:23
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<AirPickupbillDetailEntity> queryAirPickupbillDetailListByPrimaryId(String airPickupbillId) {
		return this.getSqlSession().selectList(NAMESPACE + "queryAirPickupbillDetailListByPrimaryId",airPickupbillId);
	}

	/**
	 * 根据运单号list查询符合条件的运单原始信息
	 * @param List<pickupDetailIdList> 合大票明细id
	 * @return List<AirPickupbillEntity>
	 * @author 099197-foss-zhoudejun
	 * @date 2013-1-15 下午2:38:23
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<AirPickupbillDetailEntity> queryAirPickupbillDetailListByIds(
			List<String> pickupDetailIdList) {
		return this.getSqlSession().selectList(NAMESPACE + "queryAirPickupbillDetailListByIds",pickupDetailIdList);
	}
	
	/**
	 *  接送货做更改单时同步修改合大票明细信息
	 * @param entity 空运合大票明细
	 * @author liuzhaowei
	 * @date 2013-7-1 下午4:38:23
	 */
	@Override
	public int updatePickupDetailFromModifyWaybill(AirPickupbillDetailEntity entity){
		return this.getSqlSession().update(NAMESPACE + "updatePickupDetailFromModifyWaybill",entity);
	}

	
	/**
	 *  根据航空正单删除合票单据
	 * @param airWaybillNo 正单号
	 * @author wqh
	 * @date 2013-08-28 
	 */
	@Override
	public int deleteAirPickupBillByAirWaybill(String airWaybillNo) {
		return this.getSqlSession().delete(NAMESPACE+"deleteAirPickupBillByAirWaybill",airWaybillNo);
	}

	
	
	
	/**
	 *  根据合票清单Id删除合票单据
	 * @param airPickupbillId id
	 * @author wqh
	 * @date 2013-08-28 
	 */
	@Override
	public int deleteAirPickupBillById(String airPickupbillId) {	
		return getSqlSession().delete(NAMESPACE+"deleteAirPickupBillById", airPickupbillId);
	}

	/**
	 *  根据运单号查询合票清单List
	 * @param waybillNo 运单号
	 * @author wqh
	 * @date 2013-09-08 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirPickupbillEntity> queryAirPickupbillListByWaybillNo(
			String waybillNo) {
		return getSqlSession().selectList(NAMESPACE+"queryAirPickupbillListByWaybillNo", waybillNo);
	}

	/**
	 *  添加合票日志
	 * @param airPickupbillLogEntity 合票日志
	 * @author foss-105795-wqh
	 * @date 2014-01-21 
	 */
	public void addAirPickupbillLog(AirPickupbillLogEntity airPickupbillLogEntity){
		 getSqlSession().insert(NAMESPACE+"addAirPickupbillLog", airPickupbillLogEntity); 

	}

	/**
	 * 
	* @description 批量删除合大票清单流水信息
	* @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirQueryModifyPickupbillDao#deleteAirPickupbillSerialList(java.util.List)
	* @author 269701-foss-lln
	* @update 2016年5月21日 上午11:13:01
	* @version V1.0
	 */
	@Override
	public int deleteAirPickupbillSerialList(List<String> deletePickupDetailList) {
		return this.getSqlSession().delete(NAMESPACE + "deleteAirPickupbillSerialList",deletePickupDetailList);
	}


	/**
	 * 
	* @description 批量新增合大票清单流水数据
	* @see com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirQueryModifyPickupbillDao#addAirPickupbillSerialist(java.util.List)
	* @author 269701-foss-lln
	* @update 2016年5月21日 下午5:16:33
	* @version V1.0
	 */
	@Override
	public void addAirPickupbillSerialist(List<SerialEntity> serialList) {
		 this.getSqlSession().insert(NAMESPACE + "addAirPickupbillSerialist",serialList);

	}
	
}