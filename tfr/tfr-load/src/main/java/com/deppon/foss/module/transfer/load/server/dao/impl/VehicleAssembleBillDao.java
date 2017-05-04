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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/dao/impl/VehicleAssembleBillDao.java
 *  
 *  FILE NAME          :VehicleAssembleBillDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.common.api.shared.dto.WayBillAssembleDto;
import com.deppon.foss.module.transfer.load.api.server.dao.IVehicleAssembleBillDao;
import com.deppon.foss.module.transfer.load.api.shared.domain.MotorstowagebillrecordEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.RewardOrPunishAgreementEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.VehicleAssembleBillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.VehicleAssembleBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.VehicleAssembleBillOptLogEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.DeleteHandOverBillFromVehicleAssembleBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.PrintCarriageContractDataDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.PrintVehicleAssembleBillDetailDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.PrintVehicleAssembleBillHeaderDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryVehicleAssembleBillConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryVehicleAssembleBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.UpdateVehicleAssembleBillStateDto;
import com.deppon.foss.module.transfer.load.api.shared.vo.VehicleAssembleBillVo;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;

/** 
 * @className: VehicleAssembleBillDao
 * @author: ShiWei shiwei@outlook.com
 * @description: 配载单模块dao类
 * @date: 2012-11-8 下午2:44:38
 * 
 */
public class VehicleAssembleBillDao extends iBatis3DaoImpl implements IVehicleAssembleBillDao {
	
	/**
	 * mapper文件命名空间
	 */
	private static final String NAMESPACE = "fosss.load.vehicleassemblebill.";

	/**
	 * 传入配载单基本信息实体和交接单列表实体，保存配载单信息
	 * @author 045923-foss-shiwei
	 * @date 2012-11-8 下午2:45:17
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IVehicleAssembleBillDao#saveVehicleAssembleBill(com.deppon.foss.module.transfer.load.api.shared.domain.VehicleAssembleBillEntity, java.util.List)
	 */
	@Override
	public boolean saveVehicleAssembleBill(VehicleAssembleBillEntity baseEntity,List<VehicleAssembleBillDetailEntity> billDetailEntityList) {
		//保存配载单基本信息
		this.getSqlSession().insert(NAMESPACE + "saveVehicleAssembleBillBasicInfo", baseEntity);
		//保存交接单list
		this.getSqlSession().insert(NAMESPACE + "saveHandOverBillList", billDetailEntityList);
		return true;
	}

	/**
	 * 查询配载单
	 * @author 045923-foss-shiwei
	 * @date 2012-11-13 下午5:11:58
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IVehicleAssembleBillDao#queryVehicleAssembleBillList(com.deppon.foss.module.transfer.load.api.shared.dto.QueryVehicleAssembleBillConditionDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QueryVehicleAssembleBillDto> queryVehicleAssembleBillList(
			QueryVehicleAssembleBillConditionDto conditionDto,int limit,int start) {
		//分页
		RowBounds rowBounds = new RowBounds(start, limit);
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "queryVehicleAssembleBillList", conditionDto,rowBounds);
	}
	
	/**
	 * 无分页查询配载单，用于导出
	 * @author 045923-foss-shiwei
	 * @date 2013-5-29 下午5:25:37
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IVehicleAssembleBillDao#queryVehicleAssembleBillListNoPaging(com.deppon.foss.module.transfer.load.api.shared.dto.QueryVehicleAssembleBillConditionDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QueryVehicleAssembleBillDto> queryVehicleAssembleBillListNoPaging(QueryVehicleAssembleBillConditionDto conditionDto){
		return this.getSqlSession().selectList(NAMESPACE + "queryVehicleAssembleBillList",conditionDto);
	}

	/**
	 * 获取查询到的配载单的总条数
	 * @author 045923-foss-shiwei
	 * @date 2012-11-14 下午4:49:47
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IVehicleAssembleBillDao#getVehicleAssembleBillCount(com.deppon.foss.module.transfer.load.api.shared.dto.QueryVehicleAssembleBillConditionDto)
	 */
	@Override
	public Long getVehicleAssembleBillCount(
			QueryVehicleAssembleBillConditionDto conditionDto) {
		//返回查询结果
		return (Long)this.getSqlSession().selectOne(NAMESPACE + "getVehicleAssembleBillCount", conditionDto);
	}

	/**
	 * 根据车次号获取配载单基本信息
	 * @author 045923-foss-shiwei
	 * @date 2012-11-15 下午4:35:26
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IVehicleAssembleBillDao#queryVehicleAssembleBillByNo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleAssembleBillEntity> queryVehicleAssembleBillByNo(
			String vehicleAssembleNo) {
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "queryVehicleAssembleBillByNo", vehicleAssembleNo);
	}

	/**
	 * 根据配载车次号获取其下交接单列表
	 * @author 045923-foss-shiwei
	 * @date 2012-11-15 下午6:31:21
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IVehicleAssembleBillDao#queryHandOverBillListByVNo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QueryHandOverBillDto> queryHandOverBillListByVNo(
			String vehicleAssembleNo) {
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "queryHandOverBillListByVNo", vehicleAssembleNo);
	}
	
	/**
	 * 获取打印运输合同需要的数据 
	 * @author ibm-zhangyixin
	 * @date 2012-11-16 上午10:23:02
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IVehicleAssembleBillDao#queryPrintCarriageContractData(java.lang.String)
	 */
	@Override
	public PrintCarriageContractDataDto queryPrintCarriageContractData(
			String vehicleAssembleNo) {
		//返回查询结果
		return (PrintCarriageContractDataDto) this.getSqlSession().selectOne(NAMESPACE + "queryPrintCarriageContractData", vehicleAssembleNo);
	}

	/**
	 * 根据配载车次号获取修改日志
	 * @author 045923-foss-shiwei
	 * @date 2012-11-16 上午10:51:29
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IVehicleAssembleBillDao#queryVehicleAssembleBillOptLogByNo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleAssembleBillOptLogEntity> queryVehicleAssembleBillOptLogByNo(
			String vehicleAssembleNo,int limit,int start) {
		//分页
		RowBounds rowBounds = new RowBounds(start, limit);
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "queryVehicleAssembleBillOptLogByNo", vehicleAssembleNo,rowBounds);
	}

	/**
	 * 根据配载车次号获取修改日志的条数
	 * @author 045923-foss-shiwei
	 * @date 2012-11-16 下午2:24:57
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IVehicleAssembleBillDao#queryVehicleAssembleBillOptLogCountByNo(java.lang.String)
	 */
	@Override
	public Long queryVehicleAssembleBillOptLogCountByNo(String vehicleAssembleNo) {
		//返回查询结果
		return (Long)this.getSqlSession().selectOne(NAMESPACE + "getVehicleAssembleBillOptLogCountByNo", vehicleAssembleNo);
	}

	/**
	 * 修改配载单信息
	 * @author 045923-foss-shiwei
	 * @date 2012-11-20 上午11:03:01
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IVehicleAssembleBillDao#updateVehicleAssembleBill(com.deppon.foss.module.transfer.load.api.shared.domain.VehicleAssembleBillEntity, com.deppon.foss.module.transfer.load.api.shared.domain.VehicleAssembleBillDetailEntity, com.deppon.foss.module.transfer.load.api.shared.dto.DeleteHandOverBillFromVehicleAssembleBillDto)
	 */
	@Override
	public boolean updateVehicleAssembleBill(
			VehicleAssembleBillEntity baseEntity,
			List<VehicleAssembleBillDetailEntity> addedHandOverBillList,
			DeleteHandOverBillFromVehicleAssembleBillDto deletedHandOverBillDto,
			List<VehicleAssembleBillOptLogEntity> billOptLogList) {
		//更新基本信息
		if(baseEntity != null){
			this.getSqlSession().update(NAMESPACE + "updateVehicleAssembleBillBaseInfo",baseEntity);
		}
		//插入新增的交接单
		if(addedHandOverBillList != null){
			this.getSqlSession().insert(NAMESPACE + "saveHandOverBillList",addedHandOverBillList);
		}
		//delete删除的交接单
		if(deletedHandOverBillDto != null){
			this.getSqlSession().delete(NAMESPACE + "deleteHandOverBillList",deletedHandOverBillDto);
		}
		//插入修改日志
		if(billOptLogList != null && billOptLogList.size() != 0){
			this.getSqlSession().insert(NAMESPACE + "saveOptLogList",billOptLogList);
		}
		return true;
	}

	/**
	 * 查询出打印配置单中的一部分数据
	 * @author ibm-zhangyixin
	 * @date 2012-11-20 下午2:07:50
	 */
	@Override
	public PrintVehicleAssembleBillHeaderDto queryVehicleAssembleBillPrtDataSource(
			String vehicleAssembleNo) {
		//返回查询结果
		return (PrintVehicleAssembleBillHeaderDto)this.getSqlSession().selectOne(NAMESPACE + "queryVehicleAssembleBillPrtDataSource", vehicleAssembleNo);
	}

	/**
	 * 查询出打印配置单中的一部分数据_根据配载单号查询出装车人
	 * @author ibm-zhangyixin
	 * @date 2012-11-20 下午2:07:50
	 */
	@Override
	public String queryLoaderNameByVehicleassemblebillId(
			String vehicleassemblebillId) {
		//返回查询结果
		return (String)this.getSqlSession().selectOne(NAMESPACE + "queryLoaderNameByVehicleassemblebillId", vehicleassemblebillId);
	}

	/**
	 * 查询出打印配置单中的一部分数据_查询出该配置单下所有交接单的汇总
	 * @author ibm-zhangyixin
	 * @date 2012-11-20 下午2:07:50
	 */
	@Override
	public PrintVehicleAssembleBillHeaderDto querySummaryHandOverBillByVehicleassemblebill(
			String vehicleassemblebillId) {
		//返回查询结果
		return (PrintVehicleAssembleBillHeaderDto)this.getSqlSession().selectOne(NAMESPACE + "querySummaryHandOverBillByVehicleassemblebill", vehicleassemblebillId);
	}

	/**
	 * 查询出打印配置单中的一部分数据_查询出该配置单下运单明细数据
	 * @author ibm-zhangyixin
	 * @date 2012-11-20 下午2:07:50
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PrintVehicleAssembleBillDetailDto> queryVehicleAssembleBillDetailPrtDataSource(
			String vehicleassemblebillId) {
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "queryVehicleAssembleBillDetailPrtDataSource", vehicleassemblebillId);
	}

	/**
	 * 插入打印次数
	 * @author ibm-zhangyixin
	 * @date 2012-11-20 下午2:29:54
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IVehicleAssembleBillDao#insertMotorstowagebillrecord(com.deppon.foss.module.transfer.load.api.shared.domain.MotorstowagebillrecordEntity)
	 */
	@Override
	public int insertMotorstowagebillrecord(
			MotorstowagebillrecordEntity motorstowagebillrecordEntity) {
		//插入打印次数
		this.getSqlSession().insert(NAMESPACE + "insertMotorstowagebillrecord", motorstowagebillrecordEntity);
		return 1;
	}

	/**
	 * 根据交接单号, 运单号, 查询出交接明细
	 * @author ibm-zhangyixin
	 * @date 2012-11-20 下午2:35:35
	 */
	@Override
	public String getHandOverBillSerialNoDetailsByWayBillNo(
			String wayBillNo, String handOverBillNo) {
		Map<String, String> params = new HashMap<String, String>(2);
		params.put("waybillNo", wayBillNo);
		params.put("handOverBillNo", handOverBillNo);
		return (String) this.getSqlSession().selectOne(NAMESPACE + "getHandOverBillSerialNoDetailsByWayBillNo", params);
	}

	/****
	 * 根据配载车次号和封签类型获取封签号list
	 * @author ibm-zhangyixin
	 * @date 2013-3-18 下午2:43:06
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IVehicleAssembleBillDao#querySealNosByVehicleAssembleNo(java.lang.String, java.lang.String)
	 */
	@Override
	public String querySealNosByVehicleAssembleNo(String vehicleAssembleNo,
			String sealType) {
		Map<String, String> params = new HashMap<String, String>(2);
		//配载单号
		params.put("billNo", vehicleAssembleNo);
		//封签类型
		params.put("sealType", sealType);
		//返回结果
		return (String) this.getSqlSession().selectOne(NAMESPACE + "querySealNosByVehicleAssembleNo", params);
	}

	/**
	 * 更新配载单状态
	 * @author 045923-foss-shiwei
	 * @date 2012-11-26 下午8:39:11
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IVehicleAssembleBillDao#updateVehicleAssembleBillState(java.util.List)
	 */
	@Override
	public boolean updateVehicleAssembleBillState(
			List<UpdateVehicleAssembleBillStateDto> updateVehicleAssembleBillStateDtoList) {
		//更新配载单状态
		this.getSqlSession().update(NAMESPACE + "updateVehicleAssembleBillState",updateVehicleAssembleBillStateDtoList);
		return true;
	}

	/**
	 * 根据配载车次号获取车次下的所有运单号的集合
	 * @author 045923-foss-shiwei
	 * @date 2012-12-23 上午9:42:38
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IVehicleAssembleBillDao#queryWaybillNoListByVehicleAssembleNo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryWaybillNoListByVehicleAssembleNo(
			String vehicleAssembleNo) {
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "queryWaybillNoListByVehicleAssembleNo",vehicleAssembleNo);
	}

	/**
	 * 根据运单号查询该运单号的所有汽运配载记录，包括每次的出发时间和预计到达时间
	 * @author 045923-foss-shiwei
	 * @date 2013-1-10 上午11:26:06
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WayBillAssembleDto> queryVehicleAssembleRecordsByWaybillNo(
			String waybillNo) {
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "queryVehicleAssembleRecordsByWaybillNo",waybillNo);
	}

	/**
	 * 用于验证选择的配载单在该车牌下是否还有其他的没选择
	 * @author ibm-zhangyixin
	 * @date 2012-10-31 下午4:00:01
	 */
	@Override
	public Long checkPrintTruckTask(
			VehicleAssembleBillVo vehicleAssembleBillVo) {
		//返回查询结果
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "checkPrintTruckTask", vehicleAssembleBillVo);
	}

	/**
	 * @Title: checkPrintVehicleAssembleBill 
	 * @Description: 校验选择了多少任务车辆  
	 * @param vehicleAssembleBillVo
	 * @return    
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IVehicleAssembleBillDao#checkPrintVehicleAssembleBill(com.deppon.foss.module.transfer.load.api.shared.vo.VehicleAssembleBillVo)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-6-1下午11:53:14
	 */
	@Override
	public Long checkPrintVehicleAssembleBill(
			VehicleAssembleBillVo vehicleAssembleBillVo) {
		//返回查询结果
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "checkPrintVehicleAssembleBill", vehicleAssembleBillVo);
	}

	/** 
	 * @Title: getMotorstowagebillrecordByVehicleAssembleNo 
	 * @Description: 根据配置单号获取运输合同打印记录 
	 * @param vehicleAssembleNo
	 * @return    
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IVehicleAssembleBillDao#getMotorstowagebillrecordByVehicleAssembleNo(java.lang.String)
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-19下午3:29:08
	 */ 
	@Override
	public MotorstowagebillrecordEntity getMotorstowagebillrecordByVehicleAssembleNo(
			String vehicleAssembleNo) {
		return (MotorstowagebillrecordEntity) this.getSqlSession().selectOne(NAMESPACE + "getMotorstowagebillrecordByVehicleAssembleNo", vehicleAssembleNo);
	}

	/**
	 * 插入配载单修改日志
	 * @author 045923-foss-shiwei
	 * @date 2013-6-8 下午9:56:22
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IVehicleAssembleBillDao#addOptLogList(java.util.List)
	 */
	@Override
	public int addOptLogList(
			List<VehicleAssembleBillOptLogEntity> billOptLogList) {
		this.getSqlSession().insert(NAMESPACE + "saveOptLogList",billOptLogList);
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 查询两个部门间的最大配载车次号，用于生成配载单编号
	 * @author 045923-foss-shiwei
	 * @date 2013-7-2 上午9:06:51
	 */
	@Override
	public String queryLatestVehicleAssembleNo(String oriOrgCode,	List<String> destOrgCodeList, String leaveTime){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("oriOrgCode", oriOrgCode);
		params.put("destOrgCodeList", destOrgCodeList);
		Date leaveStartTime = DateUtils.convert(leaveTime, "yyyyMMdd");
		Date leaveEndTime = DateUtils.addDayToDate(leaveStartTime, 1);
		params.put("leaveStartTime", leaveStartTime);
		params.put("leaveEndTime", leaveEndTime);
		
		return (String) this.getSqlSession().selectOne(NAMESPACE + "queryLatestVehicleAssembleNo", params);
	}
	
	/**
	 * 查询配载单通过运单
	 * @author 105869-foss-heyongdong
	 * @date 2013-9-7 10:35:11
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QueryVehicleAssembleBillDto> queryVehicleAssemblyBillByWaybillNo(
			String waybillNo) {
		
		return this.getSqlSession().selectList(NAMESPACE+"queryVehicleAssemblyBillByWaybillNo",waybillNo);
	}
	
	/**
	 * 查询配载单以及相关的任务信息根据配载单号
	 * @author foss-heyongdong
	 * @date 2013年11月27日 14:14:57
	 * 
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List<QueryVehicleAssembleBillDto> queryVehicleAssembleBillInfoByBillNo(String vehicleAssembillNo){
		return this.getSqlSession().selectList(NAMESPACE+"queryVehicleAssembleBillInfoByBillNo", vehicleAssembillNo);
	}
	/**
	 * 新增奖罚协议
	 * @author foss-heyongdong
	 * @date 2013年11月13日 09:31:59
	 * 
	 * */
	@Override
	public int saveVehicleRewardProt(List<RewardOrPunishAgreementEntity> rewardOrPunishAgreementEntity) {
		return this.getSqlSession().insert(NAMESPACE+"saveVehicleRewardProt", rewardOrPunishAgreementEntity);
	}
	
	/**
	 * 查询奖罚信息
	 * @author foss-heyongdong
	 * @date 2013年12月19日 09:37:25
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RewardOrPunishAgreementEntity> queryRewardOrPunishDetail(String vehicleAssembleNo) {
		return this.getSqlSession().selectList(NAMESPACE+"queryRewardOrPunishDetail",vehicleAssembleNo);
	}
	
	/**
	 * 删除奖罚信息
	 * */
	@Override
	public int deletReWardOrPunishAgreement(String vehicleAssembleNo) {
		
		return this.getSqlSession().delete(NAMESPACE+"deletReWardOrPunishAgreement", vehicleAssembleNo);
	}
	
	/**
	 *运单补录跟新配载单装载率
	 *@author foss-heyongdong
	 *@date 2014年2月20日 11:31:43
	 * 
	 */
	@Override
	public int updateVehAssemForMakeUpWaybill(String loadingRate,String vehicleAssembleNo) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("loadingRate", loadingRate);
		params.put("vehicleAssembleNo", vehicleAssembleNo);
		return this.getSqlSession().update(NAMESPACE+"updateVehAssemForMakeUpWaybill", params);
	}
	
	/**
	 * 根据交接单号 查询交接单中第一条运单的运输性质
	 * @Author: 200978  xiaobingcheng
	 * 2014-8-26
	 * @param handoverNo
	 * @return
	 */
	public String queryProductTypeByHandoverNo(String handoverNo){
		return (String) this.getSqlSession().selectOne(NAMESPACE + "queryProductTypeByHandoverNo", handoverNo);
	}

	/**通过车牌号查询在途只装不卸且未出发的车辆
	 *@author 105869
	 *@date 2014年11月26日 14:07:08
	 * @param vehicleNo
	 * @return
	 */
	@Override
	public String queryMidleUnloadVehicleAssemByVehicleNo(String vehicleNo) {
		
		return (String) this.getSqlSession().selectOne(NAMESPACE+"queryMidleUnloadVehicleAssemByVehicleNo", vehicleNo);
	}
	
	/**
	 ** 通过车辆任务id和单据号查询该车辆任务下 所有有运费的配载单号
	 * @author 105869
	 * @date 2015年1月28日 08:56:10
	 * @param  truckTaskDetailId,billNo 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IVehicleAssembleBillDao#queryTruckBillByDetailId(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryTruckBillByDetailId(String truckTaskDetailId,
			String billNo) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("truckTaskDetailId", truckTaskDetailId);
		params.put("billNo", billNo);
		return this.getSqlSession().selectList(NAMESPACE+"queryTruckBillByDetailId",params);
	}
	/**
	 * 当年1月1号到当日长途外请车和合同车累计发车趟数 
	 * 当月1号到当日累计长途外请车和合同车累计发车趟数
	 * @author 105869
	 * @date 2015年1月5日 16:46:35
	 * @return TotalNumberEntityEntity
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IVehicleAssembleBillDao#leasedVehicleDepartureTimes(java.util.Date, java.util.Date)
	 */
	@Override
	public long leasedVehicleDepartureTimes(Date startDate, Date endDate) {
		Map<String, Date> params = new HashMap<String, Date>();
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		return (Long) this.getSqlSession().selectOne(NAMESPACE+"leasedVehicleDepartureTimes",params);
	}
	/**
	 * 给结算接口
	 * <p> 根据运单号   查询配载单数</p> 
	 * @author 189284 
	 * @date 2015-12-17 上午11:41:36
	 * @param waybillNo 
	 * @return
	 * @see
	 */
	@Override
	public Long queryIsVehicleassembleByNo(String waybillNo) {
		 return (Long) this.getSqlSession().selectOne(NAMESPACE+"queryIsVehicleassembleByNo",waybillNo);
	}
	
	
	/**
	* @description 根据快递交接单号 获取交接单信息(长途的未作废的交接单信息)
	* @see com.deppon.foss.module.transfer.load.api.server.dao.IVehicleAssembleBillDao#queryVehicleAssembleBillByNoFromWk(java.lang.String)
	* @author 283250-foss-liuyi
	* @update 2016年5月24日 下午5:21:06
	* @version V1.0
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleAssembleBillEntity> queryVehicleAssembleBillByNoFromWk(
			String vehicleAssembleNo) {
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "queryVehicleAssembleBillByNoFromWk", vehicleAssembleNo);
	}
	
	/**
	 ** 通过车辆任务id和单据号查询该车辆任务下 所有有运费的单据号
	 * @author 332209
	 * @date 2015年1月28日 08:56:10
	 * @param  truckTaskDetailId,billNo 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IVehicleAssembleBillDao#queryTruckBillByDetailId(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryTruckBillByDetailIdAndBillNo(String truckTaskDetailId,
			String billNo) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("truckTaskDetailId", truckTaskDetailId);
		params.put("billNo", billNo);
		return this.getSqlSession().selectList(NAMESPACE+"queryTruckBillByDetailIdAndBillNo",params);
	}
	
	/**
	 * 根据交接单号获取交接单基本信息
	 * @author 045923-foss-shiwei
	 * @date 2012-11-15 下午4:35:26
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IVehicleAssembleBillDao#queryVehicleAssembleBillByNo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleAssembleBillEntity> queryWkHandoverBillByNo(
			String vehicleAssembleNo) {
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE + "queryWkHandoverBillByNo", vehicleAssembleNo);
	}

}