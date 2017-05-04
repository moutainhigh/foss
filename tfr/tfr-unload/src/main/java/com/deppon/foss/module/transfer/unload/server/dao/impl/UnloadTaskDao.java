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
 *  PROJECT NAME  : tfr-unload
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/server/dao/impl/UnloadTaskDao.java
 *  
 *  FILE NAME          :UnloadTaskDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-unload
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.server.dao.impl
 * FILE    NAME: UnloadTaskDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.server.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadTaskDao;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadBillDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadDiffReportEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadSerialNoDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadTaskEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadWaybillDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.ConfirmUnloadTaskBillsDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.DeleteFromUnloadTaskDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.HandOverAndUnloadDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.PDApreplatformDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.PlanUnloadBillDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QueryArrivedBillInfoByNoDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QueryArrivedBillNoDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadWaybillDetailDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 卸车任务dao类
 * @author dp-duyi
 * @date 2012-11-26 上午10:29:48
 */
@SuppressWarnings("unchecked")
public class UnloadTaskDao extends iBatis3DaoImpl implements IUnloadTaskDao {
	
	/**
	 * mapper文件命名空间
	 */
	private static final String NAMESPACE = "foss.unload.unloadtask.";
	
	/**记录日志*/
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	/** 
	 * 接送货接口:根据运单号查询货物交接、卸车情况
	 * @author dp-duyi
	 * @date 2012-11-26 上午10:46:21
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadTaskDao#queryHandOverAndUnloadByWayBillNo(java.lang.String)
	 */
	@Override
	public List<HandOverAndUnloadDto> queryHandOverAndUnloadByWayBillNo(
			String wayBillNo) {
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE+"queryHandOverAndUnloadByWayBillNo", wayBillNo);
	}
	
	/**
	 * 新增卸车任务时，“快速添加”时，根据车牌号获取本部门待卸的所有单据编号及单据类型list
	 * @author 045923-foss-shiwei
	 * @date 2012-12-12 下午4:13:46
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadTaskDao#queryArrivedBillNoList(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<QueryArrivedBillNoDto> queryArrivedBillNoList(String orgCode,
			String vehicleNo) {
		//构造查询参数
		HashMap map = new HashMap();
		//部门code
		map.put("orgCode", orgCode);
		//车牌号
		map.put("vehicleNo", vehicleNo);
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE+"queryArrivedBillNoList", map);
	}

	/**
	 * 根据交接单号获取待卸的交接单信息list
	 * @author 045923-foss-shiwei
	 * @date 2012-12-12 下午4:38:20
	 */
	@Override
	public List<PlanUnloadBillDto> queryArrivedHandOverBillInfoByNo(QueryArrivedBillInfoByNoDto noDto) {
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE+"queryHandOverBillInfo", noDto);
	}
	
	/**
	* @description 根据交接单号获取待卸的快递交接单信息list
	* @author 328768-foss-gaojianfu
	* @update 2016年5月27日 下午11:08:33
	 */
	@Override
	public List<PlanUnloadBillDto> queryArrivedExpressHandOverBillInfoByNo(QueryArrivedBillInfoByNoDto nosDto) {
		//返回查询结果
	    return this.getSqlSession().selectList(NAMESPACE+"queryExpressHandOverBillInfo", nosDto);
	}

	/**
	 * 根据配载车次号获取待卸的配载单信息list
	 * @author 045923-foss-shiwei
	 * @date 2012-12-12 下午4:38:20
	 */
	@Override
	public List<PlanUnloadBillDto> queryArrivedVehicleAssembleBillInfoByNo(QueryArrivedBillInfoByNoDto noDto) {
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE+"queryVehicleAssembleBillInfo", noDto);
	}

	/**
	 * 新增卸车任务基本信息
	 * @author 045923-foss-shiwei
	 * @date 2012-12-13 下午2:15:04
	 */
	@Override
	public int addUnloadTaskBasicInfo(UnloadTaskEntity baseEntity) {
		//新增卸车任务基本信息
		this.getSqlSession().insert(NAMESPACE+"saveUnloadTaskBaseInfo",baseEntity);
		//返回处理成功
		return FossConstants.SUCCESS;
	}

	/**
	 * 新增卸车任务单据信息
	 * @author 045923-foss-shiwei
	 * @date 2012-12-13 下午2:17:02
	 */
	@Override
	public int addUnloadTaskBillDetailList(
			List<UnloadBillDetailEntity> billDetailList) {
		//新增卸车任务的单据信息
		this.getSqlSession().insert(NAMESPACE+"saveUnloadBillDetailList",billDetailList);
		//返回处理成功
		return FossConstants.SUCCESS;
	}

	/**
	 * 新增卸车任务运单明细list
	 * @author 045923-foss-shiwei
	 * @date 2012-12-13 下午2:18:27
	 */
	@Override
	public int addUnloadTaskWaybillDetailList(List<UnloadWaybillDetailEntity> waybillDetailList) {
		//新增卸车任务运单明细
		for(UnloadWaybillDetailEntity waybillDetail : waybillDetailList){
			this.getSqlSession().insert(NAMESPACE+"saveUnloadWaybillDetailList",waybillDetail);
		}
		//返回处理成功
		return FossConstants.SUCCESS;
	}

	/**
	 * 新增卸车任务流水号明细list
	 * @author 045923-foss-shiwei
	 * @date 2012-12-13 下午2:19:21
	 */
	@Override
	public int addUnloadTaskSerialNoDetailList(List<UnloadSerialNoDetailEntity> serialNoDetailList) {
		//新增卸车任务流水号明细
//		for(UnloadSerialNoDetailEntity entity : serialNoDetailList){
//			this.getSqlSession().insert(NAMESPACE+"saveUnloadSerialNoDetailList",entity);
//		}
		if(CollectionUtils.isNotEmpty(serialNoDetailList)){
			//除去list中卸车明细id和流水号相同
			for(int i=0;i < serialNoDetailList.size()-1;i++){
				UnloadSerialNoDetailEntity serialNoDetail = serialNoDetailList.get(i);
				for(int j=serialNoDetailList.size()-1;j>i;j--){
					UnloadSerialNoDetailEntity tempSerialNoDetail = serialNoDetailList.get(j);
					if(StringUtils.equals(tempSerialNoDetail.getUnloadWaybillDetailId(), serialNoDetail.getUnloadWaybillDetailId())
							&&StringUtils.equals(serialNoDetail.getSerialNo(), tempSerialNoDetail.getSerialNo())){
						serialNoDetailList.remove(j);
					}
				}
			}
		}
		
		//流水号插入数据库
		Connection con = null;
		PreparedStatement ps = null;
		try {
			//获取连接
			con = getSqlSession().getConfiguration().getEnvironment().getDataSource().getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement("insert into tfr.T_OPT_UNLOAD_SERIALNO(ID,UNLOAD_WAYBILL_DETAIL_ID,SCAN_STATE,GOODS_STATE,UNLOAD_TIME,CREATE_TIME,DEVICE_NO,DEVICE_ID,SERIAL_NO,TASK_BEGIN_TIME,UNLOAD_ORG_CODE) values (?,?,?,?,?,?,?,?,?,?,?)");
			for(UnloadSerialNoDetailEntity serialNo : serialNoDetailList){
				Map<String, String> condition  =new HashMap<String, String>();
				condition.put("WaybillDetailId", serialNo.getUnloadWaybillDetailId());
				condition.put("serialNo", serialNo.getSerialNo());
				int count =  (Integer) this.getSqlSession().selectOne(NAMESPACE+"queryUnloadSerialNo", condition);
				if(count==0){
					//ID
					ps.setString(1,serialNo.getId());
					//运单ID
					ps.setString(2, serialNo.getUnloadWaybillDetailId());
					//扫描状态
					ps.setString(ConstantsNumberSonar.SONAR_NUMBER_3, serialNo.getScanStatus());
					//货物状态
					ps.setString(ConstantsNumberSonar.SONAR_NUMBER_4, serialNo.getGoodsStatus());
					/**2013-04-22 增加非空校验*/
					//卸车时间
					if(serialNo.getOptTime() != null){
						ps.setTimestamp(ConstantsNumberSonar.SONAR_NUMBER_5, new Timestamp(serialNo.getOptTime().getTime()));
					}else{
						ps.setTimestamp(ConstantsNumberSonar.SONAR_NUMBER_5, null);
					}
					//创建时间
					if(serialNo.getCreateDate() != null){
						ps.setTimestamp(ConstantsNumberSonar.SONAR_NUMBER_6, new Timestamp(serialNo.getCreateDate().getTime()));
					}else{
						ps.setTimestamp(ConstantsNumberSonar.SONAR_NUMBER_6, new Timestamp(new Date().getTime()));
					}
					//设备编号
					ps.setString(ConstantsNumberSonar.SONAR_NUMBER_7, serialNo.getDeviceNo());
					//设备ID
					ps.setString(ConstantsNumberSonar.SONAR_NUMBER_8, serialNo.getDeviceId());
					//如果流水号为空，设置默认值000
					if(StringUtils.isEmpty(serialNo.getSerialNo())){
						serialNo.setSerialNo("000");
					}
					//流水号
					ps.setString(ConstantsNumberSonar.SONAR_NUMBER_9, serialNo.getSerialNo());
					//任务创建时间
					ps.setTimestamp(ConstantsNumberSonar.SONAR_NUMBER_10, new Timestamp(serialNo.getTaskCreateTime().getTime()));
					//创建部门
					ps.setString(ConstantsNumberSonar.SONAR_NUMBER_11, serialNo.getCreateOrgCode());
					//批处理
					ps.addBatch();
				}
			}
			//批量插入
			ps.executeBatch();
			//事务提交
			con.commit();
		} catch (Exception e) {
			//记录异常日志
			LOGGER.error("插入卸车任务流水号时发生异常：" + e.getMessage());
			try {
				//回滚
				con.rollback();
			} catch (SQLException e1) {
				//记录异常日志
				LOGGER.error("事务回滚发生异常：" + e1.getMessage());
				throw new TfrBusinessException(e1.getMessage());
			}
			throw new TfrBusinessException(e.getMessage());
		}finally{
			//关闭ps
			try {
				if(ps != null){
					ps.close();
				}
			} catch (SQLException e) {
				try {
					//回滚
					con.rollback();
				} catch (SQLException e1) {
					//记录异常日志
					LOGGER.error("事务回滚发生异常：" + e1.getMessage());
					throw new TfrBusinessException(e1.getMessage());
				}
				//记录ps关闭异常信息
				LOGGER.error("关闭prepareStatement异常：" + e.getMessage());
			}finally{
				if(con != null){
					try {
						//关闭connection
						con.close();
					} catch (SQLException e) {
						try {
							//回滚
							con.rollback();
						} catch (SQLException e1) {
							//记录异常日志
							LOGGER.error("事务回滚发生异常：" + e1.getMessage());
							throw new TfrBusinessException(e1.getMessage());
						}
						//记录关闭con异常日志
						LOGGER.error("关闭connection异常：" + e.getMessage());
					}
				}
			}
		}
		//返回处理成功
		return FossConstants.SUCCESS;
	}

	/**
	 * 新增理货员参与卸车情况
	 * @author 045923-foss-shiwei
	 * @date 2012-12-13 下午2:21:30
	 */
	@Override
	public int addLoaderParticipationList(List<LoaderParticipationEntity> loaderList) {
		//新增理货员列表
		this.getSqlSession().insert(NAMESPACE+"saveUnloadLoaderParticipationList",loaderList);
		//返回处理成功
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 根据卸车任务ID获取任务基本信息
	 * @author 045923-foss-shiwei
	 * @date 2012-12-14 上午10:20:38
	 */
	@Override
	public UnloadTaskEntity queryUnloadTaskBaseInfoById(String unloadTaskId) {
		List<UnloadTaskEntity> entityList = this.getSqlSession().selectList(NAMESPACE+"queryUnloadTaskBaseInfoById", unloadTaskId);
		//返回查询结果
		if(CollectionUtils.isNotEmpty(entityList)){
			return entityList.get(0);
		}else{
			return null;
		}
	}

	/**
	 * 根据卸车任务ID获取其下单据列表
	 * @author 045923-foss-shiwei
	 * @date 2012-12-14 上午10:21:24
	 */
	@Override
	public List<UnloadBillDetailEntity> queryUnloadTaskBillDetailListById(	String unloadTaskId) {
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE+"queryUnloadTaskBillDetailListById", unloadTaskId);
	}

	/**
	 * 根据卸车任务ID获取其下卸车员列表
	 * @author 045923-foss-shiwei
	 * @date 2012-12-14 上午10:22:01
	 */
	@Override
	public List<LoaderParticipationEntity> queryLoaderDetailListById(String unloadTaskId) {
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE+"queryLoaderDetailListById", unloadTaskId);
	}

	/**
	 * 根据卸车任务ID获取卸车任务的创建人
	 * @author 045923-foss-shiwei
	 * @date 2013-2-20 上午11:12:22
	 */
	@Override
	public List<LoaderParticipationEntity> queryTaskCreatorLoaderByTaskId(String unloadTaskId){
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE+"queryTaskCreatorLoaderByTaskId", unloadTaskId);
	}
	
	/**
	 * 批量传入单据编号和卸车任务ID，将单据从卸车任务中删除
	 * @author 045923-foss-shiwei
	 * @date 2012-12-14 下午3:05:03
	 */
	@Override
	public int deleteBillDetailListFromUnloadTask(
			DeleteFromUnloadTaskDto deleteDto) {
		//删除卸车任务中的单据
		this.getSqlSession().delete(NAMESPACE+"deleteBillDetailListFromUnloadTask", deleteDto);
		//返回处理成功
		return FossConstants.SUCCESS;
	}

	/**
	 * 批量传入卸车员code和卸车任务ID，将卸车员从卸车员参与情况表中删除
	 * @author 045923-foss-shiwei
	 * @date 2012-12-14 下午3:05:58
	 */
	@Override
	public int deleteLoaderListFromUnloadTask(DeleteFromUnloadTaskDto deleteDto) {
		//删除理货员
		this.getSqlSession().delete(NAMESPACE+"deleteLoaderListFromUnloadTask", deleteDto);
		//返回处理成功
		return FossConstants.SUCCESS;
	}

	/**
	 * 根据ID更新卸车任务状态
	 * @author 045923-foss-shiwei
	 * @date 2012-12-14 下午3:36:54
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public int updateUnloadTaskState(String unloadTaskId, String targetState) {
		//构造参数
		Map map = new HashMap();
		//卸车任务ID
		map.put("unloadTaskId", unloadTaskId);
		//目标状态
		map.put("targetState", targetState);
		//update
		this.getSqlSession().delete(NAMESPACE+"updateUnloadTaskState", map);
		//返回处理成功
		return FossConstants.SUCCESS;
	}

	/**
	 * 根据ID更新卸车任务基本信息
	 * @author 045923-foss-shiwei
	 * @date 2012-12-14 下午3:37:32
	 */
	@Override
	public int updateUnloadTaskBasicInfo(UnloadTaskEntity entity) {
		//更新卸车任务基本信息
		this.getSqlSession().delete(NAMESPACE+"updateUnloadTaskBasicInfo", entity);
		//返回处理成功
		return FossConstants.SUCCESS;
	}

	/**
	 * 根据ID删除卸车任务基本信息
	 * @author 045923-foss-shiwei
	 * @date 2012-12-14 下午7:32:05
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadTaskDao#deleteUnloadTaskBasicInfo(java.lang.String)
	 */
	@Override
	public int deleteUnloadTaskBasicInfo(String unloadTaskId) {
		//删除卸车任务
		this.getSqlSession().delete(NAMESPACE+"deleteUnloadTaskBasicInfo", unloadTaskId);
		//返回处理成功
		return FossConstants.SUCCESS;
	}

	/**
	 * 用于确认短途卸车界面，快速查询时根据运单号获取交接单号list
	 * @author 045923-foss-shiwei
	 * @date 2012-12-19 上午10:42:52
	 */
	@Override
	public List<String> queryHandOverBillListByWaybillNo(String unloadTaskId,String waybillNo) {
		//构造查询参数
		HashMap<String,String> map = new HashMap<String,String>();
		//运单号
		map.put("waybillNo", waybillNo);
		//卸车任务ID
		map.put("unloadTaskId", unloadTaskId);
		//获取交接单号list
		List<String> list =  this.getSqlSession().selectList(NAMESPACE+"queryHandOverBillListByWaybillNo", map);
		//返回查询结果
		return list;
	}

	/**
	 * 用于从交接单中获取所有运单信息
	 * @author 045923-foss-shiwei
	 * @date 2012-12-20 上午10:16:43
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadTaskDao#queryUnloadTaskWaybillDetailByHandOverBillNo(java.lang.String)
	 */
	@Override
	public List<UnloadWaybillDetailEntity> queryUnloadTaskWaybillDetailByHandOverBillNo(String handOverBillNo) {
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE+"queryUnloadTaskWaybillDetailByHandOverBillNo", handOverBillNo);
	}

	
	/**
	 * 用于从配载单中获取所有运单信息
	 * @author 045923-foss-shiwei
	 * @date 2012-12-20 上午10:16:58
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadTaskDao#queryUnloadTaskWaybillDetailByVehicleAssembleNo(java.lang.String)
	 */
	@Override
	public List<UnloadWaybillDetailEntity> queryUnloadTaskWaybillDetailByVehicleAssembleNo(String vehicleAssembleNo) {
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE+"queryUnloadTaskWaybillDetailByVehicleAssembleNo", vehicleAssembleNo);
	}

	/**
	 * 更新卸车任务ID更新卸车人员参与情况的离开任务时间
	 * @author 045923-foss-shiwei
	 * @date 2012-12-20 下午7:58:57
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadTaskDao#updateLoaderLeaveTaskTime(java.lang.String, java.util.Date)
	 */
	@Override
	public int updateLoaderLeaveTaskTime(String unloadTaskId, Date leaveTime) {
		//构造参数
		HashMap<String,Object> map = new HashMap<String,Object>();
		//卸车任务ID
		map.put("unloadTaskId", unloadTaskId);
		//离开时间
		map.put("leaveTime", leaveTime);
		//update
		this.getSqlSession().update(NAMESPACE+"updateLoaderLeaveTaskTime", map);
		//返回处理成功
		return FossConstants.SUCCESS;
	}

	/**
	 * 卸车任务确认界面，校验输入的多货运单号、流水号是否存在
	 * @author 045923-foss-shiwei
	 * @date 2012-12-23 上午11:08:07
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadTaskDao#validateWaybillNoAndSerialNo(java.lang.String, java.lang.String)
	 */
	@Override
	public int validateWaybillNoAndSerialNo(String waybillNo, String serialNo) {
		//构造查询参数
		Map<String,String> map = new HashMap<String,String>();
		//运单号
		map.put("waybillNo", waybillNo);
		//流水号
		map.put("serialNo", serialNo);
		//返回查询结果
		return (Integer) this.getSqlSession().selectOne(NAMESPACE+"validateWaybillNoAndSerialNo",map);
	}

	/**
	 * 校验运单号、流水号是否存在于某长途卸车任务中
	 * @author 045923-foss-shiwei
	 * @date 2012-12-23 下午3:35:42
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadTaskDao#validateNosIsInLongUnloadTask(com.deppon.foss.module.transfer.unload.api.shared.dto.ConfirmUnloadTaskBillsDto)
	 */
	@Override
	public int validateNosIsInLongUnloadTask(ConfirmUnloadTaskBillsDto dto) {
		//返回查询结果
		return (Integer) this.getSqlSession().selectOne(NAMESPACE+"validateNosIsInLongUnloadTask",dto);
	}

	/**
	 * 校验运单号、流水号是否存在于某短途卸车任务中
	 * @author 045923-foss-shiwei
	 * @date 2012-12-23 下午3:36:12
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadTaskDao#validateNosIsInShortUnloadTask(com.deppon.foss.module.transfer.unload.api.shared.dto.ConfirmUnloadTaskBillsDto)
	 */
	@Override
	public int validateNosIsInShortUnloadTask(ConfirmUnloadTaskBillsDto dto) {
		//返回查询结果
		return (Integer) this.getSqlSession().selectOne(NAMESPACE+"validateNosIsInShortUnloadTask",dto);
	}

	/**
	 * 确认卸车任务（长途），快速定位功能，根据运单号获取运单号所在的配载单、交接单
	 * @author 045923-foss-shiwei
	 * @date 2012-12-23 下午5:14:44
	 */
	@Override
	public List<ConfirmUnloadTaskBillsDto> queryBillNosListByWaybillNo(String unloadTaskId, String waybillNo) {
		//构造查询参数
		Map<String,String> map = new HashMap<String,String>();
		//运单号
		map.put("waybillNo", waybillNo);
		//卸车任务ID
		map.put("unloadTaskId", unloadTaskId);
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE+"queryBillNosWhereWaybillNoExists",map);
	}

	/**
	 * 卸车差异模块调用，看某长途卸车中多货的流水号在上一环节是否扫描
	 * @author 045923-foss-shiwei
	 * @date 2012-12-27 下午2:16:28
	 */
	@Override
	public List<String> queryLongDistanceLoadTaskIsScaned(ConfirmUnloadTaskBillsDto dto) {
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE+"queryLongDistanceLoadTaskIsScaned",dto);
	}

	/**
	 * 卸车差异模块调用，看某短途卸车中多货的流水号在上一环节是否扫描
	 * @author 045923-foss-shiwei
	 * @date 2012-12-27 下午2:16:28
	 */
	@Override
	public List<String> queryShortDistanceLoadTaskIsScaned(ConfirmUnloadTaskBillsDto dto) {
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE+"queryShortDistanceLoadTaskIsScaned",dto);
	}

	/**
	 * 根据卸车差异报告id，少货运单号、流水号获取运单所属单据
	 * @author 045923-foss-shiwei
	 * @date 2013-6-25 上午11:06:50
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadTaskDao#queryLackGoodsBillNoByWaybillNoAndSerialNo(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<UnloadWaybillDetailDto> queryLackGoodsBillNoByWaybillNoAndSerialNo(String reportId, String waybillNo, String serialNo) {
		Map<String,String> params = new HashMap<String,String>();
		params.put("id", reportId);
		params.put("waybillNo", waybillNo);
		params.put("serialNo", serialNo);
		return this.getSqlSession().selectList(NAMESPACE+"queryLackGoodsBillNoByWaybillNoAndSerialNo",params);
	}

	/**
	 * 
	 * <p>提供接口给接送货查询卸车任务，配合运单中止需求</p>
	 * @author alfred
	 * @date 2014-5-8 下午3:44:17
	 * @param condition
	 * @return 
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadTaskDao#queryUnloadTaskReport(java.util.Map)
	 */
	@Override
	public List<String> queryUnloadTaskReport(Map<String, String> condition) {
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE+"queryUnloadTaskReport",condition);
	}
	/**
	 * @desc 提供给PDA 查询待卸车辆预分配月台情况
	 * @param currOrgCode 当前部门编码
	 * @param vehicleNo 车牌号  (非必填)
	 * @param platformNo 月台号(非必填)
	 * @param businessType 业务类型(必填)  长途:LONG_DISTANCE ,短途:SHORT_DISTANCE,接送货：DIVISION
	 * @param enterTfrCenterType 入场情况(必填) 已入场 IN,未入场 OUT
	 * @author 105795
	 * @date 2015-05-09
	 * */
	public List<PDApreplatformDto> queryPreplatformNo(String currOrgCode,String vehicleNo,String platformNo,String businessType,String enterTfrCenterType){
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("destOrgCode", currOrgCode);
		map.put("vehicleNo", vehicleNo);
		map.put("platformNo", platformNo);
		map.put("businessType", businessType);
		map.put("enterTfrCenterType", enterTfrCenterType);
        //返回查询结果集
		return this.getSqlSession().selectList(NAMESPACE+"queryPreplatformNo", map);
	}
	/**
	 * @author nly
	 * @date 2015年4月22日 上午10:02:06
	 * @function 根据运单号查询所有的卸车运单明细
	 * @param waybillNo
	 * @return
	 */
	@Override
	public List<UnloadWaybillDetailEntity> queryUnloadWaybillDetailByNo(String waybillNo) {
		return this.getSqlSession().selectList(NAMESPACE+"queryUnloadWaybillDetailByNo",waybillNo);
	}
	
	/**
	 * 用于从商务专递交接单中获取所有运单信息
	 * @author chenlei 272681
	 * @date 2015/8/31
	 */
	@Override
	public List<UnloadWaybillDetailEntity> queryUnloadTaskWaybillDetailByAirHandOverBillNo(String handOverBillNo) {
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE+"queryUnloadTaskWaybillDetailByAirHandOverBillNo", handOverBillNo);
	}
	
	/**
	 * 根据商务专递交接单号获取待卸的交接单信息list
	 * @author 272681 chenlei
	 */
	@Override
	public List<PlanUnloadBillDto> queryArrivedBusinessAirBillInfoByNo(QueryArrivedBillInfoByNoDto noDto) {
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE+"queryBusinessAirBillInfo", noDto);
	}
	

	/**
	 * 根据商务专递卸车任务ID获取其下单据列表
	 * @author 272681 chenlei
	 * @date 2015/9/16
	 */
	@Override
	public List<UnloadBillDetailEntity> queryAirUnloadTaskBillDetailListById(String unloadTaskId) {
		//返回查询结果
		return this.getSqlSession().selectList(NAMESPACE+"queryAirUnloadTaskBillDetailListById", unloadTaskId);
	}
	
	/**
	 * 快速查询时根据商务专递运单号获取交接单号list
	 * @author chenlei 272681
	 */
	@Override
	public List<String> queryAirHandOverBillListByWaybillNo(String unloadTaskId,String waybillNo) {
		//构造查询参数
		HashMap<String,String> map = new HashMap<String,String>();
		//运单号
		map.put("waybillNo", waybillNo);
		//卸车任务ID
		map.put("unloadTaskId", unloadTaskId);
		//获取交接单号list
		List<String> list =  this.getSqlSession().selectList(NAMESPACE+"queryAirHandOverBillListByWaybillNo", map);
		//返回查询结果
		return list;
	}
	
	/**
	 * 校验运单号、流水号是否存在于某商务专递卸车任务中
	 * @author 272681 chenlei
	 * @date 2015/9/8
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IUnloadTaskDao#validateNosIsInBusinessUnloadTask(com.deppon.foss.module.transfer.unload.api.shared.dto.ConfirmUnloadTaskBillsDto)
	 */
	@Override
	public int validateNosIsInBusinessUnloadTask(ConfirmUnloadTaskBillsDto dto) {
		//返回查询结果
		return (Integer) this.getSqlSession().selectOne(NAMESPACE+"validateNosIsInBusinessUnloadTask",dto);
	}

	/**
	* @description 根据卸车任务ID获取卸车任务编号列表
	* @param unloadTaskId
	* @return
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年5月28日 上午7:18:51
	 */
	@Override
	public List<String> queryBillNosByUnloadTaskId(String unloadTaskId) {
		//获取交接单号list
		List<String> list =  this.getSqlSession().selectList(NAMESPACE+"queryBillNosByUnloadTaskId", unloadTaskId);
		return list;
	}
	
	
	/**
	* @description 插入一条卸车差异报告基本信息
	* @param unloadTaskId
	* @return
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年5月28日 上午7:18:51
	 */
	@Override
	public int addUnloadDiffReport(UnloadDiffReportEntity baseEntity) {
		//新增差异报告基本信息
		this.getSqlSession().insert(NAMESPACE+"addUnloadDiffReport",baseEntity);
		//返回处理成功
		return FossConstants.SUCCESS;
	}

	
	
}