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
 *  
 *  PROJECT NAME  : tfr-scheduling
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/dao/impl/PassInviteApplyDao.java
 * 
 *  FILE NAME     :PassInviteApplyDao.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-scheduling
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.server.dao.impl
 * FILE    NAME: PassInviteApplyDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.server.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IPassInviteApplyDao;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PassInviteApplyEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.InviteVehicleDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.OrgEntity;

/**
 * 外请约车受理
 * @author 104306-foss-wangLong
 * @date 2012-12-15 下午12:54:39
 */
public class PassInviteApplyDao extends iBatis3DaoImpl implements IPassInviteApplyDao {
	
	private static final String NAMESPACE = "foss.scheduling.PassInviteApply.";
	
	/**
	 * 新增
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午12:54:39
	 * @param passInviteApplyEntity
	 * @return 受影响的行数
	 */
	public int addPassInviteApply(PassInviteApplyEntity passInviteApplyEntity) {
		String statement = NAMESPACE + "addPassInviteApply";
		return getSqlSession().insert(statement, passInviteApplyEntity);
	}

	/**
	 * 修改
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午12:54:39
	 * @param passInviteApplyEntity 
	 * @return 受影响的行数
	 */
	public int updatePassInviteApply(PassInviteApplyEntity passInviteApplyEntity) {
		String statement = NAMESPACE + "updatePassInviteApply";
		return getSqlSession().update(statement, passInviteApplyEntity);
	}
	
	/**
	 * 查询集合
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午12:54:39
	 * @param passInviteApplyEntity
	 * @return java.util.List
	 */
	@SuppressWarnings("unchecked")
	public List<PassInviteApplyEntity> queryPassInviteApplyList(PassInviteApplyEntity passInviteApplyEntity) {
		String statement = NAMESPACE + "queryPassInviteApplyList";
		return getSqlSession().selectList(statement, passInviteApplyEntity);
	}
	
	/**
	 * 查询集合 分页
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午12:54:39
	 * @param passInviteApplyEntity
	 * @param start
	 * @param pageSize
	 * @return java.util.List
	 */
	@SuppressWarnings("unchecked")
	public List<PassInviteApplyEntity> queryPassInviteApplyForPage(PassInviteApplyEntity passInviteApplyEntity, int start, int pageSize) {
		String statement = NAMESPACE + "queryPassInviteApplyList";
		RowBounds rowBounds = new RowBounds(start, pageSize);
		return getSqlSession().selectList(statement, passInviteApplyEntity, rowBounds);
	}

	/**
	 * 统计记录数
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午12:54:39
	 * @param passInviteApplyEntity
	 * @return 行数
	 */
	public Long queryCount(PassInviteApplyEntity  passInviteApplyEntity) {
		String statement = NAMESPACE + "queryCount";
		return (Long) getSqlSession().selectOne(statement, passInviteApplyEntity);
	}

	/**
	 * 查询外请车价格
	 * @param inviteNo 约车编号
	 * @param vehicleNo 车牌号
	 * @return inviteCost 价格
	 * @author 099197-foss-zhoudejun
	 * @date 2012-12-29 下午2:07:22
	 * @author_update 134019-yuyongxiang
	 * @date_update 2013年7月16日 11:40:53
	 * TODO
	 */
	@Override
	public BigDecimal queryInviteCost(String inviteNo) {
		String statement = NAMESPACE + "queryInviteCost";
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("inviteNo",inviteNo);
		return (BigDecimal) getSqlSession().selectOne(statement, map);
	}

	/**
	 * 根据外请车车牌号、用车部门code查询请车价格
	 * @return inviteCost
	 * @author 099197-foss-zhoudejun
	 * @date 2013-1-7 上午8:17:25
	 * TODO
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PassInviteApplyEntity> queryPassInvitePrice(String vehicleNo, String applyOrgCode,String status,String useStatus) {
		Map<String,String> dataMap = new HashMap<String, String>();
		String statement = NAMESPACE + "queryPassInvitePrice";
		dataMap.put("vehicleNo", vehicleNo);
		dataMap.put("applyOrgCode",applyOrgCode);
		dataMap.put("status", status);
		dataMap.put("useStatus",useStatus);
		return getSqlSession().selectList(statement,dataMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InviteVehicleDto> queryInviteVehicleListByPage(InviteVehicleDto inviteVehicleDto, int start, int limit) {
		
		return this.getSqlSession().selectList(NAMESPACE + "queryInviteVehicleListByPage", inviteVehicleDto, new RowBounds(start, limit));
	}

	@Override
	public Long queryInviteVehicleCountByPage(InviteVehicleDto inviteVehicleDto) {
		
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryInviteVehicleCountByPage", inviteVehicleDto);
	}
	
	/**
	 * 修改
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 下午12:54:39
	 * @param passInviteApplyEntity 
	 * @return 受影响的行数
	 */
	public int updatePassInviteApplyByInviteNo(PassInviteApplyEntity passInviteApplyEntity) {
		String statement = NAMESPACE + "updatePassInviteApplyByInviteNo";
		return getSqlSession().update(statement, passInviteApplyEntity);
	}

	 /**
     * 根据约车编号查询信息部名称和编码
     * @param inviteNo
     * @author 310248
     * @return
     */
	@Override
	public OrgEntity queryOrgDtoByApplyByInviteNo(String inviteNo) {
			
		return (OrgEntity) this.getSqlSession().selectOne(NAMESPACE+"queryOrgDtoByApplyByInviteNo", inviteNo);
	}
	
}