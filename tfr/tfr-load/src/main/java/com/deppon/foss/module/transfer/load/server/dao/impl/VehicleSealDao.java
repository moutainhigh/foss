/**
 *  initial comments.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
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
 *  PROJECT NAME  : tfr-load
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/dao/impl/VehicleSealDao.java
 *  
 *  FILE NAME          :VehicleSealDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.server.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.server.dao.IVehicleSealDao;
import com.deppon.foss.module.transfer.load.api.shared.domain.RelationInfoEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SealDestDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SealEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SealOrigDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.BillInfoDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.SealDto;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 绑定与校验装车封签Dao 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:zyx,date:2012-10-11 下午2:28:15,content:</p>
 * @author zyx
 * @date 2012-10-11 下午2:28:15
 * @since
 * @version
 */
public class VehicleSealDao extends iBatis3DaoImpl implements IVehicleSealDao {
	private static final String NAMESPACE = "foss.load.seal.";
	
	/**
	 * <p>查询绑定于校验装车封签</p> 
	 * @author zyx
	 * @date 2012-10-12 上午11:13:40
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SealDto> queryAllvehicleSealList(SealDto params, int limit, int start) {
		//设置分页
		RowBounds rowBounds = new RowBounds(start, limit);
		//查询操作
		return this.getSqlSession().selectList(NAMESPACE + "queryAllvehicleSealList", params, rowBounds);
	}

	/**
	 * 获取总记录数
	 * @author ibm-zhangyixin
	 * @date 2012-12-11 上午11:52:49
	 */
	@Override
	public Long getTotCount(SealDto params) {
		//获取总记录数
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "getTotCount", params);
	}

	/**
	 * 根据车牌号查询绑定的封签明细
	 * @author ibm-zhangyixin
	 * @date 2012-12-11 上午11:53:24
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SealOrigDetailEntity> getSealOrigDetailsBySealVehicleNo(String vehicleNo) {
		//根据车牌号查询绑定的封签明细
		return this.getSqlSession().selectList(NAMESPACE + "getSealOrigDetailsByVehicleNo", vehicleNo);
	}

	/**
	 * 根据车牌号查询出封签
	 * 查询出来的封签可能有多个, 默认取seal_time为最新的一个
	 * @author ibm-zhangyixin
	 * @date 2012-12-11 上午11:53:35
	 */
	@SuppressWarnings("unchecked")
	@Override
	public SealEntity getSealByVehicleNo(String vehicleNo) {
		//根据车牌号查询出封签(order by seal_time desc)
		//查询出来的封签可能有多个, 默认取seal_time为最新的一个
		List<SealEntity> seals = this.getSqlSession().selectList(NAMESPACE + "getSealByVehicleNo", vehicleNo);
		if(seals.size() > 0) {
			return seals.get(0);
		}
		return null;
	}

	/**
	 * 根据车牌号查询出封签 return List
	 * @author ibm-zhangyixin
	 * @date 2012-12-11 上午11:53:35
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SealEntity> getSealsByVehicleNo(String vehicleNo) {
		//根据车牌号查询出封签(order by seal_time desc)
		return this.getSqlSession().selectList(NAMESPACE + "getSealByVehicleNo", vehicleNo);
	}

	/**
	 * 根据封签ID查询出一条封签记录
	 * @author ibm-zhangyixin
	 * @date 2012-12-11 上午11:54:46
	 */
	@Override
	public SealEntity getSealById(String id) {
		//根据封签ID查询出一条封签记录
		return (SealEntity) this.getSqlSession().selectOne(NAMESPACE + "getSealById", id);
	}

	/**
	 * 根据封签ID查询出该ID下所有的出发封签明细
	 * @author ibm-zhangyixin
	 * @date 2012-12-11 上午11:55:14
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SealOrigDetailEntity> getSealOrigDetailsBySealId(String sealId) {
		//根据封签ID查询出该ID下所有的出发封签明细
		return this.getSqlSession().selectList(NAMESPACE + "getSealOrigDetailsBySealId", sealId);
	}

	/**
	 * 根据封签ID查询出该ID下所有的到达封签明细
	 * @author ibm-zhangyixin
	 * @date 2012-12-11 上午11:55:14
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SealDestDetailEntity> getSealDestDetailsBySealId(String sealId) {
		//根据封签ID查询出该ID下所有的到达封签明细
		return this.getSqlSession().selectList(NAMESPACE + "getSealDestDetailsBySealId", sealId);
	}

	/**
	 * 更新封签操作 
	 * @author ibm-zhangyixin
	 * @date 2012-11-1 上午11:34:02
	 */
	@Override
	public int updateSeal(SealEntity seal) {
		//更新封签操作 
		this.getSqlSession().update("updateSealByPrimaryKey", seal);
		return FossConstants.SUCCESS;
	}

	/**
	 * 根据封签ID删除出发封签明细
	 * @author ibm-zhangyixin
	 * @date 2012-11-1 上午11:37:09
	 */
	@Override
	public int deleteSealOrigDetailBySealId(String sealId) {
		//根据封签ID删除出发封签明细
		this.getSqlSession().delete(NAMESPACE + "deleteSealOrigDetailBySealId", sealId);
		return FossConstants.SUCCESS;
	}

	/**
	 * 新增出发封签 
	 * @author ibm-zhangyixin
	 * @date 2012-11-1 上午11:40:21
	 */
	@Override
	public int insertSealOrigDetail(List<SealOrigDetailEntity> sealOrigDetails) {
		//获取connection
		Connection conn = this.getSqlSession().getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("insert into tfr.t_opt_seal_orig_detail (id, seal_no, seal_id, seal_type, operate_time, bind_type) ");
		sql.append("values (?, ?, ?, ?, ?, ?)");
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql.toString());
			for(SealOrigDetailEntity sealOrigDetail : sealOrigDetails) {
				int i = 0;
				ps.setString(++i, sealOrigDetail.getId());
				ps.setString(++i, sealOrigDetail.getSealNo());
				ps.setString(++i, sealOrigDetail.getSealId());
				ps.setString(++i, sealOrigDetail.getSealType());
				ps.setDate(++i, new Date(sealOrigDetail.getOperateTime().getTime()));
				ps.setString(++i, sealOrigDetail.getBindType());
				ps.addBatch();
			}
			//批量插入
			ps.executeBatch();
		} catch (Exception e) {
			throw new TfrBusinessException(e.getMessage());
		}
		return FossConstants.SUCCESS;
	}

	/**
	 * 新增封签 
	 * @author ibm-zhangyixin
	 * @date 2012-11-1 上午11:40:21
	 */
	@Override
	public int insertSeal(SealEntity seal) {
		//新增封签 
		this.getSqlSession().insert("insertSeal", seal);
		return FossConstants.SUCCESS;
	}

	/**
	 * 新增到达封签 
	 * @author ibm-zhangyixin
	 * @date 2012-11-1 上午11:40:21
	 */
	@Override
	public int insertSealDestDetail(List<SealDestDetailEntity> sealDestDetails) {
		Connection conn = this.getSqlSession().getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("insert into tfr.t_opt_seal_dest_detail (id, seal_no, seal_id, seal_type, operate_time, check_type) ");
		sql.append("values (?, ?, ?, ?, ?, ?)");
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql.toString());
			for(SealDestDetailEntity sealDestDetail : sealDestDetails) {
				int i = 0;
				ps.setString(++i, sealDestDetail.getId());
				ps.setString(++i, sealDestDetail.getSealNo());
				ps.setString(++i, sealDestDetail.getSealId());
				ps.setString(++i, sealDestDetail.getSealType());
				ps.setDate(++i, new Date(sealDestDetail.getOperateTime().getTime()));
				ps.setString(++i, sealDestDetail.getCheckType());
				ps.addBatch();
			}
			ps.executeBatch();
		} catch (Exception e) {
			throw new TfrBusinessException(e.getMessage());
		}
		return FossConstants.SUCCESS;
	}

	/**
	 * 根据车牌号, 出发部门 ,取状态为未到达的任务车辆明细
	 * @author ibm-zhangyixin
	 * @date 2012-11-7 上午10:37:48
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TruckTaskDetailEntity> getTruckTaskDetailsByVehicleNo(
			SealDto sealDto) {
		//根据车牌号, 出发部门 ,取状态为未到达的任务车辆明细
		return this.getSqlSession().selectList(NAMESPACE + "getTruckTaskDetailsByVehicleNo", sealDto);
	}

	/**
	 * 根据parentID获取车辆任务信息, 已作废的不会被查询出来
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 下午2:10:45
	 */
	@Override
	public TruckTaskEntity getTruckTaskById(String parentId) {
		//根据parentID获取车辆任务信息
		return (TruckTaskEntity) this.getSqlSession().selectOne(NAMESPACE + "getTruckTaskById", parentId);
	}

	/**
	 * 根据parentID获取车辆任务信息, 已作废的会被查询出来
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 下午2:10:45
	 */
	@Override
	public TruckTaskEntity getTruckTaskByIdCancled(String parentId) {
		//根据parentID获取车辆任务信息
		return (TruckTaskEntity) this.getSqlSession().selectOne(NAMESPACE + "getTruckTaskByIdCancled", parentId);
	}
	
	/**
	 * 根据车牌号查询车辆任务信息
	 * 	取create_time最新的一条
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 下午2:10:45
	 */
	@Override
	public TruckTaskEntity getTruckTaskByVehicleNo(String vehicleNo) {
		//根据车牌号查询车辆任务信息
		//取create_time最新的一条
		return (TruckTaskEntity) this.getSqlSession().selectOne(NAMESPACE + "getTruckTaskByVehicleNo", vehicleNo);
	}

	/**
	 * 根据单号查询车辆任务信息
	 * 	取create_time最新的一条
	 * @author ibm-zhangyixin
	 * @date 2012-12-25 下午2:10:45
	 */
	@Override
	public TruckTaskEntity getTruckTaskByBillNo(String billNo) {
		//根据单号查询车辆任务信息
		//取create_time最新的一条
		return (TruckTaskEntity) this.getSqlSession().selectOne(NAMESPACE + "getTruckTaskByBillNo", billNo);
	}

	/**
	 * 用户编码获取用户名称
	 * @author ibm-zhangyixin
	 * @date 2012-12-11 下午1:44:23
	 */
	@Override
	public String getUserNameByEmpCode(String empCode) {
		//用户编码获取用户名称
		return (String) this.getSqlSession().selectOne(NAMESPACE + "getUserNameByEmpCode", empCode);
	}

	/**
	 * 根据部门编码获取部门名称
	 * @author ibm-zhangyixin
	 * @date 2012-12-11 下午1:44:44
	 */
	@Override
	public String getOrgNameByOrgCode(String orgCode) {
		//根据部门编码获取部门名称
		return (String) this.getSqlSession().selectOne(NAMESPACE + "getOrgNameByOrgCode", orgCode);
	}

	/**
	 * 根据封签ID获取到达部门编号
	 * @author ibm-zhangyixin
	 * @date 2012-12-11 上午10:27:42
	 */
	@Override
	public String getDestOrgCodeBySealId(String id) {
		//根据封签ID获取到达部门编号
		return (String) this.getSqlSession().selectOne(NAMESPACE + "getDestOrgCodeBySealId", id);
	}

	/**
	 * 根据封签ID获取车辆任务明细信息
	 * @author ibm-zhangyixin
	 * @date 2012-12-11 上午11:41:09
	 */
	@Override
	public TruckTaskDetailEntity getTruckTaskDetailBySealId(String id) {
		//根据封签ID获取车辆任务明细信息
		return (TruckTaskDetailEntity) this.getSqlSession().selectOne(NAMESPACE + "getTruckTaskDetailBySealId", id);
	}

	/**
	 * 根据车牌号查询司机信息
	 * (先根据车牌号找到车辆任务明细ID, 再根据车辆任务明细ID关联到交接单查询)
	 * @author ibm-zhangyixin
	 * @date 2013-1-5 下午4:50:27
	 */
	@SuppressWarnings("unchecked")
	@Override
	public RelationInfoEntity queryDriverInfoByTaskDetailId(String id) {
		//根据车牌号查询司机信息
		//(先根据车牌号找到车辆任务明细ID, 再根据车辆任务明细ID关联到交接单查询)
		List<RelationInfoEntity> relationInfos = this.getSqlSession().selectList(NAMESPACE + "queryDriverInfoByTaskDetailId", id);
		if(relationInfos.size() > 0) {
			return relationInfos.get(0);
		}
		return null;
	}

	/****
	 * 根据装车任务ID 查询单据信息
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 上午11:52:05
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IVehicleSealDao#queryBillInfoByTruckTaskId(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BillInfoDto> queryBillInfoByTruckTaskId(Map<String, String> map) {
		return this.getSqlSession().selectList(NAMESPACE + "queryBillInfoByTruckTaskId", map);
	}

	/****
	 * 删除封签
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 上午11:52:27
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IVehicleSealDao#deleteSealById(java.lang.String)
	 */
	@Override
	public void deleteSealById(String id) {
		//删除封签
		this.getSqlSession().delete(NAMESPACE + "deleteSealById", id);
	}

	/**
	 * 
	 * 根据车辆任务ID更新车辆任务明细中的封签ID(vehicle_seal_id)
	 * 	绑定封签时, 车辆状态为 "未出发", "在途" 需更新车辆任务明细vehicle_seal_id
	 *	删除绑定关系时, 同上, 更新车辆任务明细 vehicle_seal_id
	 * @author ibm-zhangyixin
	 * @date 2013-1-14 下午5:12:35
	 */
	@Override
	public void updateTruckTaskDetailByTruckTaskId(SealEntity seal) {
		// 根据车辆任务ID更新车辆任务明细中的封签ID(vehicle_seal_id)
		// 	绑定封签时, 车辆状态为 "未出发", "在途" 需更新车辆任务明细vehicle_seal_id
		//	删除绑定关系时, 同上, 更新车辆任务明细 vehicle_seal_id
		this.getSqlSession().update("updateTruckTaskDetailByTruckTaskId", seal);
	}

	/**
	 * 绑定封签后, 如果车辆任务表中的司机为空, 就使用绑定封签时的司机 将其更新
	 * @author ibm-zhangyixin
	 * @date 2013-1-14 下午5:12:35
	 */
	@Override
	public void updateTruckTaskDriverByTruckTaskId(SealEntity seal) {
		//绑定封签后, 如果车辆任务表中的司机为空, 就使用绑定封签时的司机 将其更新
		this.getSqlSession().update("updateTruckTaskDriverByTruckTaskId", seal);
	}

	/**
	 * 根据车辆任务ID在封签表中找封签
	 * @author ibm-zhangyixin
	 * @date 2013-1-18 下午5:37:04
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SealEntity> querySealByTruckTaskId(SealEntity seal) {
		//根据车辆任务ID在封签表中找封签
		return this.getSqlSession().selectList(NAMESPACE + "querySealByTruckTaskId", seal);
	}

	/****
	 * 根据truckTaskId查询出交接单中的司机信息
	 * @author ibm-zhangyixin
	 * @date 2013-3-15 下午1:39:25
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IVehicleSealDao#queryDriverInfoByTruckTaskId(java.lang.String)
	 */
	@Override
	public SealDto queryDriverInfoByTruckTaskId(String truckTaskId) {
		//根据truckTaskId查询出交接单中的司机信息
		return (SealDto) this.getSqlSession().selectOne(NAMESPACE + "queryDriverInfoByTruckTaskId", truckTaskId);
	}

	/** 
	 * @Title: getTruckTaskDetailsByTruckTaskId 
	 * @Description: 根据truckTaskId获取truckTaskDetail
	 * @param truckTaskId
	 * @return    
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IVehicleSealDao#getTruckTaskDetailsByTruckTaskId(java.lang.String)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-2上午10:25:37
	 */ 
	@SuppressWarnings("unchecked")
	@Override
	public List<TruckTaskDetailEntity> getTruckTaskDetailsByTruckTaskId(
			String truckTaskId) {
		//根据车牌号, 出发部门 ,取状态为未到达的任务车辆明细
		return this.getSqlSession().selectList(NAMESPACE + "getTruckTaskDetailsByTruckTaskId", truckTaskId);
	}

	/** 
	 * @Title: getTruckTaskDetailByTruckId 
	 * @Description: 根据t_opt_truck_task.id找t_opt_truck_task_detail
	 * 返回 t_opt_truck_task_detail.create_time最早的一条 
	 * @param truckTaskId
	 * @return    
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IVehicleSealDao#getTruckTaskDetailByTruckId(java.lang.String)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-18下午2:40:25
	 */ 
	@Override
	public TruckTaskDetailEntity getTruckTaskDetailByTruckId(String truckTaskId) {
		return (TruckTaskDetailEntity) this.getSqlSession().selectOne(NAMESPACE + "getTruckTaskDetailByTruckId", truckTaskId);
	}

	/** 
	 * @Title: querySealOrigDetailsBySealNo 
	 * @Description: 根据封签号查询出所有的出发封签 
	 * @param sealNo
	 * @return    
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IVehicleSealDao#querySealOrigDetailsBySealNo(java.lang.String)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-19下午5:44:15
	 */ 
	@SuppressWarnings("unchecked")
	@Override
	public List<SealOrigDetailEntity> querySealOrigDetailsBySealNo(String sealNo) {
		//根据封签号查询出所有的出发封签 
		return this.getSqlSession().selectList(NAMESPACE + "querySealOrigDetailsBySealNo", sealNo);
	}

	/** 
	 * @Title: querySealDestDetailsBySealNo 
	 * @Description: 根据封签号查询出所有的到达封签
	 * @param sealNo
	 * @return    
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IVehicleSealDao#querySealDestDetailsBySealNo(java.lang.String)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-19下午5:44:15
	 */ 
	@SuppressWarnings("unchecked")
	@Override
	public List<SealDestDetailEntity> querySealDestDetailsBySealNo(String sealNo) {
		//根据封签号查询出所有的到达封签
		return this.getSqlSession().selectList(NAMESPACE + "querySealDestDetailsBySealNo", sealNo);
	}

	/** 
	 * @Title: querySealOrigDetailsBySealNoAndId 
	 * @Description: 根据封签号和封签明细ID查询出所有的出发封签
	 * @param sealNo
	 * @param sealId
	 * @return    
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IVehicleSealDao#querySealOrigDetailsBySealNo(java.lang.String, java.lang.String)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-5-7下午3:57:34
	 */ 
	@SuppressWarnings("unchecked")
	@Override
	public List<SealOrigDetailEntity> querySealOrigDetailsBySealNoAndId(
			String sealNo, String id) {
		Map<String, String> map = new HashMap<String, String>(2);
		map.put("sealNo", sealNo);
		map.put("id", id);
		//根据封签号和封签明细ID查询出所有的出发封签
		return this.getSqlSession().selectList(NAMESPACE + "querySealOrigDetailsBySealNoAndId", map);
	}

	/** 
	 * @Title: queryUnReportSeal 
	 * @Description: 查询出未上报差错的封签 
	 * @return    
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IVehicleSealDao#queryUnReportSeal()
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-5-16下午5:31:58
	 */ 
	@SuppressWarnings("unchecked")
	@Override
	public List<SealEntity> queryUnReportSeal() {
		return this.getSqlSession().selectList(NAMESPACE + "queryUnReportSeal");
	}

	/** 
	 * @Title: querySealDestDetailsBySealId 
	 * @Description: 根据封签ID查出到达封签明细
	 * @param sealId
	 * @return    
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IVehicleSealDao#querySealDestDetailsBySealId(java.lang.String)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-5-16下午5:39:48
	 */ 
	@SuppressWarnings("unchecked")
	@Override
	public List<SealDestDetailEntity> querySealDestDetailsBySealId(String sealId) {
		return this.getSqlSession().selectList(NAMESPACE + "querySealDestDetailsBySealId", sealId);
	}

	/** 
	 * @Title: queryTruckTaskDetailByTruckTaskId 
	 * @Description: 根据车辆任务ID找出所有明细 
	 * @param truckTaskId
	 * @return    
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IVehicleSealDao#queryTruckTaskDetailByTruckTaskId(java.lang.String)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-5-17下午2:39:58
	 */ 
	@SuppressWarnings("unchecked")
	@Override
	public List<TruckTaskDetailEntity> queryTruckTaskDetailByTruckTaskId(
			String truckTaskId, String orgCode) {
		Map<String, String> map = new HashMap<String, String>(2);
		map.put("truckTaskId", truckTaskId);
		map.put("orgCode", orgCode);
		return this.getSqlSession().selectList(NAMESPACE + "queryTruckTaskDetailByTruckTaskId", map);
	}

	/** 
	 * @Title: queryTruckTaskDetailByTruckTaskIdAndOrigCode 
	 * @Description: 根据车辆任务ID,出发部门找出所有明细 
	 * @param truckTaskId
	 * @param orgCode
	 * @return    
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IVehicleSealDao#queryTruckTaskDetailByTruckTaskIdAndOrigCode(java.lang.String, java.lang.String)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-5-31下午3:16:12
	 */ 
	@SuppressWarnings("unchecked")
	@Override
	public List<TruckTaskDetailEntity> queryTruckTaskDetailByTruckTaskIdAndOrigCode(
			String truckTaskId, String orgCode) {
		Map<String, String> map = new HashMap<String, String>(2);
		map.put("truckTaskId", truckTaskId);
		map.put("origCode", orgCode);
		return this.getSqlSession().selectList(NAMESPACE + "queryTruckTaskDetailByTruckTaskIdAndOrigCode", map);
	}

	/** 
	 * @Title: queryUnDistanceHandover 
	 * @Description: 查询当前部门下未配载的交接单 
	 * @param truckTaskId
	 * @param origOrgCode
	 * @return    
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IVehicleSealDao#queryUnDistanceHandover(java.lang.String, java.lang.String)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-6-20下午4:15:34
	 */ 
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryUnDistanceHandover(String truckTaskId,
			String origOrgCode) {
		Map<String, String> map = new HashMap<String, String>(2);
		map.put("truckTaskId", truckTaskId);
		map.put("origOrgCode", origOrgCode);
		return this.getSqlSession().selectList(NAMESPACE + "queryUnDistanceHandover", map);
	}

	/** 
	 * @Title: updateSealToInvalid 
	 * @Description: 删除封签, 逻辑删除
	 * @param seal    
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IVehicleSealDao#updateSealToInvalid(com.deppon.foss.module.transfer.load.api.shared.domain.SealEntity)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-7-12下午3:48:03
	 */ 
	@Override
	public void updateSealToInvalid(SealEntity seal) {
		//更新封签操作 
		this.getSqlSession().update("updateSealToInvalid", seal);
	}

	/**
	 * @Title: queryTruckTaskDetailByTruckTaskId 
	 * @Description: 根据车辆任务ID找出所有明细 
	 * @author 163580
	 * @date 2014-2-24 下午2:39:53
	 * @param truckTaskId
	 * @param destCode
	 * @return
	 * @see
	 */
	@Override
	public Long queryTruckTaskDetailByTruckTaskIdAndDestCode(
			String truckTaskId, String destOrgCode) {
		Map<String, String> map = new HashMap<String, String>(2);
		map.put("truckTaskId", truckTaskId);
		map.put("destOrgCode", destOrgCode);
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryTruckTaskDetailByTruckTaskIdAndDestCode", map);
	}
	
	/**
	 ** 根据车辆任务Id和到达部门查询车辆任务明细
	 * @author 105869
	 * @date 2014年9月29日 17:03:55
	 * @param id
	 * @param currentDeptCode
	 * @return TruckTaskDetailEntity
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IVehicleSealDao#qeuryTruckTaskDetailByTaskIdAndDestOrgCode(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public TruckTaskDetailEntity qeuryTruckTaskDetailByTaskIdAndDestOrgCode(
			String id, String currentDeptCode,String isStatus) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("truckTaskId", id);
		map.put("destOrgCode", currentDeptCode);
		map.put("isStatus", isStatus);
		
		List<TruckTaskDetailEntity> entity =this.getSqlSession().selectList(NAMESPACE+"qeuryTruckTaskDetailByTaskIdAndDestOrgCode", map);
		if(CollectionUtils.isNotEmpty(entity)&&entity.size()>0){
			return entity.get(0);
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<TruckTaskDetailEntity> queryTruckTaskDetailByTruckTaskIdAndCode(
			String id, String deptCode) {
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("truckTaskId", id);
		map.put("orgCode", deptCode);
		return this.getSqlSession().selectList(NAMESPACE+"queryTruckTaskDetailByTruckTaskIdAndCode", map);
	}

	/**
	 *@desc 根据任务车辆ID，状态 PRE，当前校验部门顶级外场 查询预分配月台号
	 *@param orgCode 外场编码，truckTaskId 任务车辆ID
	 *@return String
	 *@author 105795
	 *@date 2015年4月27日下午3:13:57 
	 */
	@SuppressWarnings("unchecked")
	public String queryPrePlatformCodeByTruckTaskId(String orgCode,String truckTaskId){
		Map<String,String> map=new HashMap<String,String>();
		map.put("orgCode", orgCode);
		map.put("truckTaskId", truckTaskId);
		List<String> platformCodes= this.getSqlSession().selectList(NAMESPACE+"queryPrePlatformCodeByTruckTaskId",map);
		if(platformCodes==null||platformCodes.size()==0){
			return null;
		}else{
			return platformCodes.get(0);
		}
		
	}
	
	/**
	 *com.deppon.foss.module.transfer.load.api.server.dao
	 *@desc 根据到达部门、车牌号查询任务车辆id 默认查询7天之内的数据
	 *@param orgCode,vehicleNo
	 *@return String
	 *@author 105795
	 *@date 2015年7月10日 下午4:32:57 
	 */
	public String queryTruckTaskIdByVehicleNo(String orgCode,String vehicleNo){
		
		Map<String,String> map=new HashMap<String,String>();
		map.put("orgCode", orgCode);
		map.put("vehicleNo", vehicleNo);
		return (String) getSqlSession().selectOne(NAMESPACE+"queryTruckTaskIdByVehicleNo", map);
	}
	
}