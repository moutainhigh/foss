/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-sign
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/dao/impl/StayHandoverDetailDao.java
 * 
 * FILE NAME        	: StayHandoverDetailDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.dao.impl;


import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.sign.api.server.dao.IStayHandoverDetailDao;
import com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.DeliverbillDetailDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.StayHandoverDetailDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.StayHandoverDto;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 交接明细Dao实现
 * @author foss-meiying
 * @date 2012-11-28 下午12:30:00
 * @since
 * @version
 */
public class StayHandoverDetailDao extends iBatis3DaoImpl implements IStayHandoverDetailDao {
	/**
	 * 命名空间
	 */
	private static final String NAMESPACE = "com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverDetailEntity.";

	/**
	 * 
	 * 通过id删除交接明细信息
	 * @author foss-meiying
	 * @date 2012-12-24 上午9:26:26
	 * @param record
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IStayHandoverDetailDao#addStayHandoverDetail(com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverDetailEntity)
	 */
	@Override
	public StayHandoverDetailEntity addStayHandoverDetail(StayHandoverDetailEntity record) {
		//主键id
		record.setId(UUIDUtils.getUUID());
		// 创建时间
		record.setCreateDate(new Date());
		//修改时间
		record.setModifyDate(record.getCreateDate());
		return this.getSqlSession().insert(NAMESPACE+"insertSelective",record) > 0 ? record : null;
	}
	
	/**
	 * 查询WaybillPending(运单带处理信息)里的运单号，货物总件数
	 * @author foss-meiying
	 * @date 2012-12-28 下午2:35:24
	 * @param driverCode 司机工号
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IStayHandoverDetailDao#queryPickupByWaybillPending(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StayHandoverDetailDto> queryPickupByWaybillPending(
			String driverCode) {
		//如果传入的司机工号为空，返回null
		if(StringUtil.isBlank(driverCode)){
			return null;
		}
		StayHandoverDto dto = new StayHandoverDto();
		//司机工号
		dto.setDriverCode(driverCode);
		//未生成交接
		dto.setHandoverStatus(FossConstants.NO);
		return (List<StayHandoverDetailDto>)this.getSqlSession().selectList(NAMESPACE+"selectWaybillInfoByWaybillPending",dto);
	}
	/**
	 * 根据派送单编号 修改交接id 
	 * @author foss-meiying
	 * @date 2013-2-1 上午9:49:55
	 * @param dto 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IStayHandoverDetailDao#updateByDeliverbillNo(com.deppon.foss.module.pickup.sign.api.shared.dto.DeliverbillDetailDto)
	 */
	@Override
	public void updateByDeliverbillNo(DeliverbillDetailDto dto) {
		this.getSqlSession().update(NAMESPACE+"updateByDeliverbillNo",dto);
	}
	/**
	 * 根据运单号查询交接明细信息
	 * @author foss-meiying
	 * @date 2013-3-17 下午4:05:01
	 * @param waybillNo
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IStayHandoverDetailDao#queryByWaybillNo(java.lang.String)
	 */
	@Override
	public StayHandoverDetailEntity queryByWaybillNo(String waybillNo) {
		@SuppressWarnings("unchecked")
		List<StayHandoverDetailEntity> list = this.getSqlSession().selectList(NAMESPACE+"selectByWaybillNo",waybillNo);
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return  null;
	}
	/**
	 * 根据主键修改交接明细信息
	 * @author foss-meiying
	 * @date 2013-3-17 下午4:43:03
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IStayHandoverDetailDao
	 * #updateByPrimaryKeySelective(com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverDetailEntity)
	 */
	@Override
	public int updateByPrimaryKeySelective(StayHandoverDetailEntity entity) {
		entity.setModifyDate(new Date());
		return this.getSqlSession().update(NAMESPACE+"updateByPrimaryKeySelective",entity);
	}

	/**
	 * 根据快递员查询补录的接货信息
	 * @param expressEmpCode
	 * @return
	 */
	public List<StayHandoverDetailDto> queryPickupByWaybillPendingExpress(
			String expressEmpCode) {
		//如果传入的司机工号为空，返回null
		if(StringUtil.isBlank(expressEmpCode)){
			return null;
		}
		StayHandoverDto dto = new StayHandoverDto();
		//司机工号
		dto.setDriverCode(expressEmpCode);
		//未生成交接
		dto.setHandoverStatus(FossConstants.NO);
		return (List<StayHandoverDetailDto>)this.getSqlSession().selectList(NAMESPACE+"queryPickupByWaybillPendingExpress",dto);

	}
	/**
	 * 查询WayBillPending里的运单号，货物总件数
	* @author 329757-foss-liuxiangcheng 
	* @date 2016-7-23 上午8:30:44
	* @param @param vehicleNo
	* @param @return    设定文件
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StayHandoverDetailDto> queryPickupByWaybillPendingByVehicleNo(StayHandoverDto dto) {
		//如果传入的司机车牌号为空，返回null
		if(StringUtil.isBlank(dto.getVehicleNo())){
			return null;
		}
		return (List<StayHandoverDetailDto>)this.getSqlSession().selectList(NAMESPACE+"queryPickupByWaybillPendingByVehicleNo",dto);
	}

	/**
	 * add by 329757
	 * 查询waybillpending表里的货物信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StayHandoverDetailDto> queryPickupByWaybillPendingDetail(
			StayHandoverDto dto) {
		//如果传入的司机车牌号为空，返回null
				if(StringUtil.isBlank(dto.getDriverCode())){
					return null;
				}
				return (List<StayHandoverDetailDto>)this.getSqlSession().selectList(NAMESPACE+"queryPickupByWaybillPendingDetail",dto);
			}
}