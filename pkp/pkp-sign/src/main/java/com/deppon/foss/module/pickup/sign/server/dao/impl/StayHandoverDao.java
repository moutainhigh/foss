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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/dao/impl/StayHandoverDao.java
 * 
 * FILE NAME        	: StayHandoverDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.dao.impl;


import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.sign.api.server.dao.IStayHandoverDao;
import com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.StayHandoverDto;
import com.deppon.foss.util.define.FossConstants;
/**
 * 交接Dao实现
 * @author foss-meiying
 * @date 2012-11-28 上午11:50:20
 * @since
 * @version
 */
public class StayHandoverDao extends iBatis3DaoImpl implements IStayHandoverDao {
	/**
	 * 命名空间
	 */
	private static final String NAMESPACE = "com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverEntity.";
	
	/**
	 * 根据id删除交接信息
	 * @author foss-meiying
	 * @date 2012-12-24 上午9:27:18
	 * @param id
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IStayHandoverDao#deleteByPrimaryKey(java.lang.String)
	 */
	@Override
	public int deleteByPrimaryKey(String id) {
		return this.getSqlSession().delete(NAMESPACE+"deleteByPrimaryKey",id);
	}
	
	/**
	 * 添加交接信息
	 * @author foss-meiying
	 * @date 2012-12-24 上午9:27:32
	 * @param record
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IStayHandoverDao#addStayHandover(com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverEntity)
	 */
	@Override
	public StayHandoverEntity addStayHandover(StayHandoverEntity record) {
		int result= this.getSqlSession().insert(NAMESPACE+"insertSelective",record); 
		return result > 0 ? record : null;
		
	}
	
	/**
	 *  通过id查询交接信息
	 * @author foss-meiying
	 * @date 2012-12-24 上午9:27:44
	 * @param id
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IStayHandoverDao#queryByPrimaryKey(java.lang.String)
	 */
	@Override
	public StayHandoverEntity queryByPrimaryKey(String id) {
		return (StayHandoverEntity)this.getSqlSession().selectOne(NAMESPACE+"selectByPrimaryKey",id);
	}
	
	/**
	 *  通过id查询交接信息
	 * @author foss-meiying
	 * @date 2012-12-24 上午9:27:44
	 * @param id
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IStayHandoverDao#queryByPrimaryKey(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StayHandoverEntity> queryByVehicleNo(String vehicleNo) {
		return (List<StayHandoverEntity>)this.getSqlSession().selectList(NAMESPACE+"queryByVehicleNo",vehicleNo);
	}
	
	/**
	 * 通过相应参数修改交接信息
	 * @author foss-meiying
	 * @date 2012-12-24 上午9:27:54
	 * @param record
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IStayHandoverDao#updateByPrimaryKeySelective(com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverEntity)
	 */
	@Override
	public int updateByPrimaryKeySelective(StayHandoverEntity record) {
		return this.getSqlSession().update(NAMESPACE+"updateByPrimaryKeySelective",record);
	}
	
	/**
	 * 通过条件修改交接信息
	 * @author foss-meiying
	 * @date 2012-12-24 上午9:28:04
	 * @param record
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IStayHandoverDao#updateByPrimaryKey(com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverEntity)
	 */
	@Override
	public int updateByPrimaryKey(StayHandoverEntity record) {
		return this.getSqlSession().update(NAMESPACE+"updateByPrimaryKey",record);
	}
	
	/**
	 * 查询接货卡货票数
	 * @author foss-meiying
	 * @date 2012-12-24 上午9:28:15
	 * @param dto
	 * @return 
	 */
	@Override
	public Integer queryPickupFastWaybillQtyCount(StayHandoverDto dto) {
		return (Integer)this.getSqlSession().selectOne(NAMESPACE+"selectCountFastWaybillQtyByParams",dto);
	}
	/**
	 *  根据司机工号查询waybillPending(运单待处理信息)所有货物总件数,总体积,总重量
	 * @author foss-meiying
	 * @date 2012-12-24 上午9:28:25
	 * @return 
	 */
	@Override
	public StayHandoverEntity querySumGoodsInfoByParams(String driverCode) {
		//如果传入的司机工号为空，返回null
		if(StringUtil.isBlank(driverCode)){
			return null;
		}
		StayHandoverDto dto = new StayHandoverDto();
		//司机工号
		dto.setDriverCode(driverCode);
		//未生成交接
		dto.setHandoverStatus(FossConstants.NO);
		return (StayHandoverEntity)this.getSqlSession().selectOne(NAMESPACE+"selectSumGoodsInfoByParams",dto);
	}
	/**
	 *  根据司机工号查询waybillPending(运单待处理信息)所有货物总件数,总体积,总重量
	 * @author foss-231438
	 * @date 2015-07-14 上午9:28:25
	 * @return 
	 */
	@Override
	public StayHandoverEntity querySumGoodsInfoByParams(StayHandoverDto dto) {
		//如果传入的司机工号为空，返回null
		if(StringUtil.isBlank(dto.getDriverCode())){
			return null;
		}
		return (StayHandoverEntity)this.getSqlSession().selectOne(NAMESPACE+"selectSumGoodsInfoByParams",dto);
	}
	/**
	 * 根据司机车牌号waybillPending(运单待处理信息)所有货物总件数,总体积,总重量
	 * add by 329757
	 * 2016-6-17
	 */
	@Override
	public StayHandoverEntity querySumGoodsInfoByVo(StayHandoverDto param) {
		//如果传入的司机工号为空，返回null
		if(StringUtil.isBlank(param.getVehicleNo())){
			return null;
		}
		return (StayHandoverEntity)this.getSqlSession().selectOne(NAMESPACE+"querySumGoodsInfoByVo",param);
	}

	/**
	 * 查询接货卡货票数
	 * add by 329757
	 * 2016-6-17
	 */
	@Override
	public Integer queryPickupFastWaybillQtyCountByVo(StayHandoverDto dto) {
		return (Integer)this.getSqlSession().selectOne(NAMESPACE+"queryPickupFastWaybillQtyCountByVo",dto);
	}
	
	/**
	 * 根据主键修改运单件数(运单件数-传入的件数)
	 * @author foss-meiying
	 * @date 2013-3-17 下午4:57:31
	 * @param record
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.dao.IStayHandoverDao
	 * #updateGoodsQtyTotalById(com.deppon.foss.module.pickup.sign.api.shared.domain.StayHandoverEntity)
	 */
	@Override
	public int updateGoodsQtyTotalReduceById(StayHandoverEntity record) {
		return this.getSqlSession().update(NAMESPACE+"updateGoodsQtyTotalById",record);
	}

}