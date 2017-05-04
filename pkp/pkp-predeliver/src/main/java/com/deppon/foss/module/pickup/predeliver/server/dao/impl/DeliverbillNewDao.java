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
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/dao/impl/DeliverbillDao.java
 * 
 * FILE NAME        	: DeliverbillDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillNewDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillNewDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DriverDto;

/**
 * 
 * 派送单DAO实现
 * 
 */
@SuppressWarnings("unchecked")
public class DeliverbillNewDao extends iBatis3DaoImpl implements IDeliverbillNewDao {
	// 派送单 name space
	private static final String NAMESPACE = "com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillNewDao";

	
	/**
	 * 
	 * 根据输入条件，查询符合条件的派送单数量
	 * 
	 * @param deliverbillNewDto
	 *            查询条件
	 * 
	 * @return 符合条件的派送单数量
	 * @author 
	 * @date 
	 */
	@Override
	public Long queryCountByCondition(DeliverbillNewDto deliverbillNewDto) {
		return (Long) this.getSqlSession().selectOne(
				NAMESPACE + ".selectCountByCondition", deliverbillNewDto);
	}

	
	/**
	 * 
	 * 根据输入条件，查询派送单
	 * 
	 * @param deliverbillDto
	 *            查询条件
	 * @return 派送单列表
	 * @author 
	 * @date 
	 */
	@Override
	public List<DeliverbillNewDto> queryByCondition(DeliverbillNewDto deliverbillNewDto) {
		return this.getSqlSession().selectList(
				NAMESPACE + ".selectByCondition", deliverbillNewDto);
	}
	
	/**
	 * 
	 * 根据输入条件，查询派送单
	 * 
	 * @param deliverbillNewDto
	 *            查询条件
	 * @return 派送单列表
	 * @author 
	 * @date 
	 */
	@Override
	public List<DeliverbillNewDto> queryByCondition(DeliverbillNewDto deliverbillNewDto,
			int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(
				NAMESPACE + ".selectByCondition", deliverbillNewDto, rowBounds);
	}
	
	
	/**
	 * 
	 * 查询派送单序列
	 * @author 
	 * @date 
	 */
	@Override
	public String querySequence() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("sequenceName", "DELIVERBILL_SEQ");
		return (String) this.getSqlSession().selectOne(NAMESPACE + ".getNextSequenceValue", params);

	}
	
	/**
	 * 
	 * 根据ID查找派送单
	 * 
	 * @param id
	 *            派送单ID
	 * @return 派送单
	 * @author 
	 * @date 
	 */
	@Override
	public DeliverbillEntity queryById(String id) {
		return (DeliverbillEntity) this.getSqlSession().selectOne(
				NAMESPACE + ".selectByPrimaryKey", id);
	}
	
	
	/**
	 * 
	 * 更新派送单
	 * 
	 * @param deliverbill
	 *            派送单
	 * @return 若成功，返回更新后的派送单；否则返回null
	 * @author 
	 * @date 
	 */
	@Override
	public DeliverbillEntity update(DeliverbillEntity deliverbill) {
		//添加时间字段，派送单修改时更新该时间字段
		deliverbill.setModifyTime(new Date());
		
		int result = this.getSqlSession().update(NAMESPACE + ".updateByPrimaryKeySelective", deliverbill);

		return result > 0 ? deliverbill : null;
	}
	
	/**
	 * 
	 * 更新派送单
	 * @author 
	 * @date 
	 */
	@Override
	public int updateDeliverBill(DeliverbillEntity deliverbill)
	{
		//添加时间字段，派送单修改时更新该时间字段
		deliverbill.setModifyTime(new Date());
		
		int result = this.getSqlSession().update(NAMESPACE + ".updateByPrimaryKeySelective", deliverbill);
		return result > 0 ? result : 0;
	}
	
	
	/**
	 * 
	 * 根据查询条件(工号/姓名/电话号码)查询公司司机
	 * 
	 * @param driverDto
	 *            查询条件
	 * @return 符合条件的公司司机列表
	 * @author 
	 * @date 
	 */
	@Override
	public List<DriverDto> queryDriverListByDriverDto(DriverDto driverDto) {
		return this.getSqlSession().selectList(
				NAMESPACE + ".selectDriverListByDriverDto", driverDto);
	}
	
	
	
	/**
	 * 根据车牌号查询车辆信息
	 * queryByOwnTruck 公司车
	 * queryByLeasedTruck 外请车
	 * @param vehicleNo
	 * @return
	 */
	@Override
	public DeliverbillNewDto queryByOwnTruck(String vehicleNo){
		return (DeliverbillNewDto)this.getSqlSession().selectOne(NAMESPACE+".queryByOwnTruck",vehicleNo);
	}
	@Override
	public DeliverbillNewDto queryByLeasedTruck(String vehicleNo){
		return (DeliverbillNewDto)this.getSqlSession().selectOne(NAMESPACE+".queryByLeasedTruck",vehicleNo);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}