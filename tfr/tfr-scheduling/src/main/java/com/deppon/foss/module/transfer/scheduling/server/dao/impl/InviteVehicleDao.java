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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/dao/impl/InviteVehicleDao.java
 * 
 *  FILE NAME     :InviteVehicleDao.java
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
package com.deppon.foss.module.transfer.scheduling.server.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;


import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IInviteVehicleDao;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.InviteVehicleEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.InviteVehicleDto;
import com.deppon.foss.util.UUIDUtils;

 /**
 * 外请约车申请Dao
 * @author 104306-foss-wangLong
 * @date 2012-12-15 上午11:08:37
 */
public class InviteVehicleDao extends iBatis3DaoImpl implements IInviteVehicleDao {
	
	private static final String NAMESPACE = "foss.scheduling.InviteVehicle.";
	
	/**
	 * 新增
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 上午11:08:37
	 * @param inviteVehicleEntity
	 * @return 受影响的行数
	 */
	public int addInviteVehicle(InviteVehicleEntity inviteVehicleEntity) {
		inviteVehicleEntity.setId(UUIDUtils.getUUID());
		String statement = NAMESPACE + "addInviteVehicle";
		return getSqlSession().insert(statement, inviteVehicleEntity);
	}

	/**
	 * 修改
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 上午11:08:37
	 * @param inviteVehicleEntity 
	 * @return 受影响的行数
	 */
	public int updateInviteVehicle(InviteVehicleEntity inviteVehicleEntity) {
		String statement = NAMESPACE + "updateInviteVehicle";
		return getSqlSession().update(statement, inviteVehicleEntity);
	}
	/**
	 * 修改 修改约车总价
	 * @author 134019-foss-yuyongxiang
	 * @date 2013年8月2日 17:41:03
	 * @param totalInviteCost 
	 * @return 受影响的行数
	 */
	@Override
	public int updateInviteVehicleForTotalInviteCost(String inviteNo,BigDecimal totalInviteCost) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("inviteNo", inviteNo);
		map.put("totalInviteCost", totalInviteCost);
		String statement = NAMESPACE + "updateInviteVehicleForTotalInviteCost";
		return getSqlSession().update(statement, map);
	}
	
	/**
	 * 查询集合
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 上午11:08:37
	 * @param inviteVehicleDto
	 * @return java.util.List
	 */
	@SuppressWarnings("unchecked")
	public List<InviteVehicleDto> queryInviteVehicleList(InviteVehicleDto inviteVehicleDto) {
		String statement = NAMESPACE + "queryInviteVehicleList";
		return getSqlSession().selectList(statement, inviteVehicleDto);
	}
	
	/**
	 * 查询集合 分页
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 上午11:08:37
	 * @param inviteVehicleDto
	 * @param start
	 * @param pageSize
	 * @return java.util.List
	 */
	@SuppressWarnings("unchecked")
	public List<InviteVehicleDto> queryInviteVehicleForPage(InviteVehicleDto inviteVehicleDto, int start, int pageSize) {
		String statement = NAMESPACE + "queryInviteVehicleList";
		RowBounds rowBounds = new RowBounds(start, pageSize);
		return getSqlSession().selectList(statement, inviteVehicleDto, rowBounds);
	}

	/**
	 * 统计记录数
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 上午11:08:37
	 * @param inviteVehicleDto
	 * @return 行数
	 */
	public Long queryCount(InviteVehicleDto  inviteVehicleDto) {
		String statement = NAMESPACE + "queryCount";
		return (Long) getSqlSession().selectOne(statement, inviteVehicleDto);
	}
	
	/**
	 * 更新状态
	 * @author 104306-foss-wangLong
	 * @date 2012-12-19 上午11:09:01
	 * @param inviteNoList  外请车编号list
	 * @param status  状态
	 * @return {@link java.lang.Integer}
	 */
	public int updateInviteVehicleApplyStatus(List<String> inviteNoList, String status) {
		String statement = NAMESPACE + "updateInviteVehicleApplyStatus";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("inviteNoList", inviteNoList);
		params.put("status", status);
		return getSqlSession().update(statement, params);
	}
	
	/** 更新开单完成后状态
	 * @author 104306-foss-wangLong
	 * @date 2012-12-19 下午4:23:09
	 * @param inviteNo
	 * @param billStatus
	 */
	public int updateInviteVehicleForFinishBill(String inviteNo, String billStatus) {
		String statement = NAMESPACE + "updateInviteVehicleForFinishBill";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("inviteNo", inviteNo);
		params.put("isFinishBill", billStatus);
		return getSqlSession().update(statement, params);
	}
	
	/**
	 * 更新外请约车使用状态
	 * @author 104306-foss-wangLong
	 * @date 2013-1-7 上午9:11:35
	 * @param inviteNoList 外请编号集合
	 * @param status 状态
	 */
	@Override
	public int updateUseStatus(String inviteNo,String vehicleNo, String status) {
		String statement = NAMESPACE + "updateUseStatus";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("inviteNo", inviteNo);
		params.put("status", status);
		params.put("vehicleNo", vehicleNo);
		return getSqlSession().update(statement, params);
	}
	
	/**
	 * 根据车牌号查询外请车使用状态
	 * @param vehicleNo
	 * @author 099197-foss-zhoudejun
	 * @date 2013-1-12 下午4:32:26
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InviteVehicleEntity> queryInviteUseStatus(String vehicleNo) {
		String statement = NAMESPACE + "queryInviteUseStatus";
		return getSqlSession().selectList(statement,vehicleNo);
	}

	/**
	 * 查询集合
	 * @author 104306-foss-wangLong
	 * @date 2012-12-15 上午11:08:37
	 * @param inviteVehicleEntity
	 * @return java.util.List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InviteVehicleDto> queryInviteVehicleListByNeedPassRecord(InviteVehicleDto inviteVehicleDto) {
		String statement = NAMESPACE + "queryInviteVehicleListByNeedPassRecord";
		
		return getSqlSession().selectList(statement, inviteVehicleDto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InviteVehicleDto> queryCheckVehicleArriveRuleList(InviteVehicleDto inviteVehicleDto) {
		String statement = NAMESPACE + "queryCheckVehicleArriveRuleList";
		
		return getSqlSession().selectList(statement, inviteVehicleDto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InviteVehicleDto> queryInviteVehicleByNo(String inviteNo) {
		String statement = NAMESPACE + "queryInviteVehicleByNo";

		return getSqlSession().selectList(statement, inviteNo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InviteVehicleDto> queryAuditInviteVehicleList(InviteVehicleDto inviteVehicleDto) {
		String statement = NAMESPACE + "queryAuditInviteVehicleList";
		
		return getSqlSession().selectList(statement, inviteVehicleDto);
	}

	@Override
	public String queryVehicleassemblebillByInviteNo(String inviteNo) {
		String statement = NAMESPACE + "queryVehicleassemblebillByInviteNo";
		return (String) getSqlSession().selectOne(statement, inviteNo);
	}

	/**
	 * @author niuly
	 * @date 2014-1-20下午4:48:46
	 * @function 根据约车编号查询约车信息（配载单调用接口）
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryInviteVehicleInfo(String inviteNo) {
		return getSqlSession().selectList(NAMESPACE + "queryInviteVehicleInfo", inviteNo);
	}
}