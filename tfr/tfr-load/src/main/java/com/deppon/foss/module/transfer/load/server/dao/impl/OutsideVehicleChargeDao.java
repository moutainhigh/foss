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
 *  PROJECT NAME  : tfr-load
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/dao/impl/OutsideVehicleChargeDao.java
 *  
 *  FILE NAME          :OutsideVehicleChargeDao.java
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

import java.util.List;
import org.apache.ibatis.session.RowBounds;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.server.dao.IOutsideVehicleChargeDao;
import com.deppon.foss.module.transfer.load.api.shared.domain.AdjustOutVehicleFeeEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.AdjustOutVehicleFeeLogEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.AdjustOutVehicleFeeDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.AdjustOutVehicleFeeLogDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.TruckTaskBillInfoDto;
import com.deppon.foss.module.transfer.load.api.shared.vo.OutVehicleAssembleBillAndFeeVo;
/**
 *  外请车费用调整 Vo
 * 
 * @author dp-liming
 * @date 2012-11-19 下午 16:30:52
 */
@SuppressWarnings("unchecked")
public class OutsideVehicleChargeDao extends iBatis3DaoImpl implements IOutsideVehicleChargeDao {
	private static final String NAMESPACE = "foss.load.outsidevehiclecharge.";
	@Override
	public List<AdjustOutVehicleFeeEntity> queryOutsideVehicleChargeList(AdjustOutVehicleFeeDto adjustOutVehicleFeeDto, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE+"queryOutsideVehicleChargeList", adjustOutVehicleFeeDto, rowBounds);		
	}
	/**
	 * 未审核记录条数以及审批中记录条数的统计
	 * @author 269701--lln
	 */
	@Override
	public List<AdjustOutVehicleFeeEntity> noAuditAndAuditInCount(AdjustOutVehicleFeeDto adjustOutVehicleFeeDto) {
		return this.getSqlSession().selectList(NAMESPACE+"noAuditAndAuditInCount", adjustOutVehicleFeeDto);		
	}
	
	@Override
	public Long queryCount(AdjustOutVehicleFeeDto adjustOutVehicleFeeDto) {
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryCount", adjustOutVehicleFeeDto);
	}


	@Override
	public int addOutsideVehicleCharge(AdjustOutVehicleFeeEntity adjustOutVehicleFeeEntity) {
		return this.getSqlSession().insert(NAMESPACE+"addOutsideVehicleCharge", adjustOutVehicleFeeEntity);
	}
	/**
	 * 新增外请车费用调整数据日志
	 * @author 269701--foss--lln
	 * @date 2015-07-13
	 */
	@Override
	public int addOutsideVehicleUpdateLog(AdjustOutVehicleFeeLogEntity adjustOutVehicleFeeLogEntity) {
		return this.getSqlSession().insert(NAMESPACE+"addOutsideVehicleUpdateLog", adjustOutVehicleFeeLogEntity);
	}
	/**
	 * 查询外请车费用调整数据日志
	 * 
	 * @author 269701-foss-lln
	 * @date 2015-07-13 下午7:03:23
	 */
	@Override
	public List<AdjustOutVehicleFeeLogEntity> queryOutsideVehicleUpdateLogs(AdjustOutVehicleFeeLogDto adjustOutVehicleFeeLogDto, int limit,
			int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryOutsideVehicleUpdateLogs", adjustOutVehicleFeeLogDto, rowBounds);
	}

	/**
	 * 查询外请车费用调整数据日志总条数
	 * 分页使用
	 * @author 269701-foss-lln
	 * @date 2015-07-13 下午7:03:23
	 */
	@Override
	public Long queryOutsideVehicleUpdateLogsTotalCount(AdjustOutVehicleFeeLogDto adjustOutVehicleFeeLogDto) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryOutsideVehicleUpdateLogsTotalCount", adjustOutVehicleFeeLogDto);
	}

	@Override
	public int updateOutsideVehicleCharge(
			AdjustOutVehicleFeeEntity adjustOutVehicleFeeEntity) {
		return this.getSqlSession().update(NAMESPACE+"updateOutsideVehicleCharge", adjustOutVehicleFeeEntity);
		
	}

	@Override
	public AdjustOutVehicleFeeEntity getOutsideVehicleChargeById(String id) {
		return (AdjustOutVehicleFeeEntity) this.getSqlSession().selectOne(NAMESPACE+ "getOutsideVehicleChargeById", id);
	}

	@Override
	public int deleteOutsideVehicleCharge(String id) {
		
		return  this.getSqlSession().delete(NAMESPACE+"deleteOutsideVehicleCharge", id);
	}

	@Override
	public AdjustOutVehicleFeeDto queryByVehicleassembleNo(
			String vehicleassembleNo) {
		return (AdjustOutVehicleFeeDto) this.getSqlSession().selectOne(NAMESPACE+ "queryByVehicleassembleNo", vehicleassembleNo);
	}

	@Override
	public AdjustOutVehicleFeeDto queryById(String id) {
		AdjustOutVehicleFeeDto adjustOutVehicleFeeDto=(AdjustOutVehicleFeeDto) this.getSqlSession().selectOne(NAMESPACE+ "queryById", id);
		if(adjustOutVehicleFeeDto==null){
			adjustOutVehicleFeeDto=this.queryByIdFromWk(id);
		}
		return adjustOutVehicleFeeDto;
	}
	
	@Override
	public AdjustOutVehicleFeeDto queryByIdFromWk(String id) {
		return (AdjustOutVehicleFeeDto) this.getSqlSession().selectOne(NAMESPACE+ "queryByIdFromWk", id);

	}

	@Override
	public List<AdjustOutVehicleFeeDto> queryOutsideVehicleChargeBy(String vehicleassembleNo) {
		return this.getSqlSession().selectList(NAMESPACE+"queryOutsideVehicleChargeBy", vehicleassembleNo);
	}
	@Override
	public List<AdjustOutVehicleFeeDto> queryOutsideVehicleChargeByFromWk(String vehicleassembleNo) {
		return this.getSqlSession().selectList(NAMESPACE+"queryOutsideVehicleChargeByFromWk", vehicleassembleNo);
	}

	@Override
	public List<AdjustOutVehicleFeeDto> queryOutsideVehicleChargeByVehicleassembleNo(String vehicleassembleNo) {
		return this.getSqlSession().selectList(NAMESPACE+"queryOutsideVehicleChargeByVehicleassembleNo", vehicleassembleNo);
	}

	// add by liangfuxiang 2013-03-15
	@Override
	public Long getBillPayableCountByWaybillNo(String waybillNo) {
		// 返回数量
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryOutsideVehicleChargePayableCount", waybillNo);
	}
	
	/**
	 * 整车是否已到达
	 * @author foss-liuxue(for IBM)
	 * @date 2013-7-5 下午4:35:42
	 */
	@Override
	public Long queryIsArriveCount(String waybillNo) {
		// 返回数量
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryIsArriveCount", waybillNo);
	}
	
	/**
	 * 当为不可结算时，查询出发部门和达到部门用于抛出异常
	 * @author foss-liuxue(for IBM)
	 * @date 2013-6-14 下午4:22:10
	 */
	@Override
	public List<AdjustOutVehicleFeeDto> getUnValidationDept(String waybillNo){
		return this.getSqlSession().selectList(NAMESPACE + "getUnValidationDept", waybillNo);
	}

	@Override
	public Long getAdjustOutVehicleFeeRecordCount(String waybillNo) {
		// 根据配载单号查询是否存在外请车费用调整
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryAdjustOutVehicleFeeRecordCount", waybillNo);
	}

	/** 
	 * @Title: checkOutsideVehicleCharge 
	 * @Description: 审核调整外请车费用 
	 * 只更新审核状态, 审核意见
	 * @param adjustOutVehicleFeeEntity
	 * @return    
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IOutsideVehicleChargeDao#checkOutsideVehicleCharge(com.deppon.foss.module.transfer.load.api.shared.domain.AdjustOutVehicleFeeEntity)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-17下午3:17:10
	 */ 
	@Override
	public int checkOutsideVehicleCharge(
			AdjustOutVehicleFeeEntity adjustOutVehicleFeeEntity) {
		return this.getSqlSession().update(NAMESPACE+"checkOutsideVehicleCharge", adjustOutVehicleFeeEntity);
	}

	  
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IOutsideVehicleChargeDao#queryOutVehicleAssembleBillAndFeeVoList(java.util.List)
	 */
	@Override
	public List<OutVehicleAssembleBillAndFeeVo> queryOutVehicleAssembleBillAndFeeVoList(List<String> vehicleAssembleNoList) {
		return this.getSqlSession().selectList(NAMESPACE + "queryOutVehicleAssembleBillAndFeeVoList", vehicleAssembleNoList);
	}
	
	/**
	 * 根据车辆任务明细ID查询该车辆下单据信息, 只找配载单
	 * @author 163580
	 * @date 2013-12-6 上午11:03:45
	 * @param truckTaskDetailId
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IOutsideVehicleChargeDao#queryTruckTaskBillInfoByTruckTaskDetailId(java.lang.String)
	 */
	@Override
	public List<TruckTaskBillInfoDto> queryTruckTaskBillInfoByTruckTaskDetailId(
			String truckTaskDetailId) {
		return this.getSqlSession().selectList(NAMESPACE + "queryTruckTaskBillInfoByTruckTaskDetailId", truckTaskDetailId);
	}
	
	
	
	
	/**
	* @description 查询快递长途外请车交接单
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.load.api.server.dao.IOutsideVehicleChargeDao#queryTruckTaskBillInfoByTruckTaskDetailIdFromWkBill(java.lang.String)
	* @author 283250-foss-liuyi
	* @update 2016年6月7日 下午4:32:37
	* @version V1.0
	*/
	@Override
	public List<TruckTaskBillInfoDto> queryTruckTaskBillInfoByTruckTaskDetailIdFromWkBill(
			String truckTaskDetailId) {
		return this.getSqlSession().selectList(NAMESPACE + "queryTruckTaskBillInfoByTruckTaskDetailIdFromWkBill", truckTaskDetailId);
	}

	/**
	 * //根据主任务ID获取子任务(根据到达时间排序, 取最早到达的那一条数据)
	 * @author 163580
	 * @date 2013-12-12 上午9:27:16
	 * @param truckTaskId
	 * @return
	 * @see
	 */
	@Override
	public TruckTaskDetailEntity getFirstTruckTaskDetailById(String truckTaskId) {
		return (TruckTaskDetailEntity)this.getSqlSession().selectOne(NAMESPACE + "getFirstTruckTaskDetailById", truckTaskId);
	}

	/**
	 * 根据配载单号查询有时效协议的数据
	 * @author 163580
	 * @date 2014-1-3 上午11:16:42
	 * @param vehicleassembleNo
	 * @return
	 * @see
	 */
	@Override
	public List<AdjustOutVehicleFeeDto> queryOutsideVehicleChargeCauseByVehicleassembleNo(
			String vehicleassembleNo) {
		return this.getSqlSession().selectList(NAMESPACE+"queryOutsideVehicleChargeCauseByVehicleassembleNo", vehicleassembleNo);
	}
	
	
	/**
	* @description 查询快递长度外请交接单信息
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.load.api.server.dao.IOutsideVehicleChargeDao#queryByVehicleassembleNoFromWk(java.lang.String)
	* @author 283250-foss-liuyi
	* @update 2016年6月4日 下午3:15:13
	* @version V1.0
	*/
	@Override
	public AdjustOutVehicleFeeDto queryByVehicleassembleNoFromWk(
			String vehicleassembleNo) {
		return (AdjustOutVehicleFeeDto) this.getSqlSession().selectOne(NAMESPACE+ "queryByVehicleassembleNoFromWk", vehicleassembleNo);
	}
}