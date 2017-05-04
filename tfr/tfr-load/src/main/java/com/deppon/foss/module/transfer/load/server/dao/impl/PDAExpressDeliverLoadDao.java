/*
 * PROJECT NAME: tfr-load
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.server.dao.impl
 * FILE    NAME: PDAExpressDeliverLoadDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.server.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressDeliverLoadDao;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadSerialNoEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadWaybillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadWaybillSendPieceEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAAssignLoadTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.QueryAssignedLoadTaskEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * PDAExpressDeliverLoadDao
 * @author dp-duyi
 * @date 2013-7-24 下午3:32:01
 */
@SuppressWarnings("unchecked")
public class PDAExpressDeliverLoadDao extends iBatis3DaoImpl implements IPDAExpressDeliverLoadDao{
	private static final String NAMESPACE = "tfr-load.";
	/** 
	 * 查询未完成快递装车任务
	 * @author dp-duyi
	 * @date 2013-7-24 下午3:32:28
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressDeliverLoadDao#queryUnfinishedLoadTask(java.lang.String, java.lang.String, java.util.Date, java.util.Date)
	 */
	@Override
	public List<PDAAssignLoadTaskEntity> queryUnfinishedLoadTask(
			String loaderCode, String loaderOrg, Date queryTimeBegin,
			Date queryTimeEnd) {
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("loaderCode", loaderCode);
		condition.put("loaderOrg", loaderOrg);
		condition.put("queryTimeBegin", queryTimeBegin);
		condition.put("queryTimeEnd", queryTimeEnd);
		return this.getSqlSession().selectList(NAMESPACE+"pda_queryUnfinishedExpressDeliverLoadTask", condition);
	}
	/** 
	 * 查询装车件数
	 * @author dp-duyi
	 * @date 2013-8-12 下午3:26:45
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressDeliverLoadDao#queryLoadWayBillQty(java.lang.String)
	 */
	@Override
	public List<LoadWaybillDetailEntity> queryLoadWayBillQty(String taskId) {
		return this.getSqlSession().selectList(NAMESPACE+"queryLoadWayBillQty", taskId);
	}
	/** 
	 * 查询理货员
	 * @author dp-duyi
	 * @date 2013-8-12 下午3:26:45
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressDeliverLoadDao#queryLoader(java.lang.String)
	 */
	@Override
	public List<LoaderParticipationEntity> queryLoader(String taskId) {
		return this.getSqlSession().selectList(NAMESPACE+"queryLoader", taskId);
	}
	/** 
	 * 根据扫描时间、扫描流水号更新装车运单件数
	 * @author dp-duyi
	 * @date 2013-8-12 下午3:26:45
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressDeliverLoadDao#updateLoadWayBillQTY(java.lang.int)
	 */
	@Override
	public int updateLoadWayBillQTYByScanTime(LoadWaybillDetailEntity loadWayBill,
			String serialNo,Date scanTime) {
		if(loadWayBill == null){
			return 0;
		}
		//配合BI项目，增加修改时间字段
		loadWayBill.setModifyDate(new Date());
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("loadWayBill", loadWayBill);
		condition.put("serialNo", serialNo);
		condition.put("scanTime", scanTime);
		if(loadWayBill.getLoadQty()>0){
			condition.put("beLoaded", FossConstants.YES);
		}else{
			condition.put("beLoaded", FossConstants.NO);
		}
		return this.getSqlSession().update(NAMESPACE+"updateLoadWayBillQTYByScanTime", condition);
	}
	/** 
	 * 根据扫描时间、扫描流水号更新装车运单件数
	 * @author dp-duyi
	 * @date 2013-8-12 下午3:26:45
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressDeliverLoadDao#updateLoadSerialNoByLoadTime(java.lang.int)
	 */
	@Override
	public int updateLoadSerialNoByLoadTime(LoadSerialNoEntity loadSerialNo) {
		return this.getSqlSession().update(NAMESPACE+"updateLoadSerialNoByLoadTime", loadSerialNo);
	}
	/**
	 * 
	 * 查询运单到付金额+代收货款 
	 * @author alfred
	 * @date 2014-2-28 下午5:34:32
	 * @param waybillNo
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressDeliverLoadDao#queryWaybillAmount(java.lang.String)
	 */
	@Override
	public BigDecimal queryWaybillAmount(String waybillNo) {
		return (BigDecimal) this.getSqlSession().selectOne(NAMESPACE+"queryWaybillAmount",waybillNo);
	}
	/**
	 * 
	 * <p>插入运单明细和派送单关系表</p> 
	 * @author alfred
	 * @date 2015-5-13 上午11:14:15
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressDeliverLoadDao#insertLoadSendPieceEntity(com.deppon.foss.module.transfer.load.api.shared.domain.LoadWaybillSendPieceEntity)
	 */
	@Override
	public int insertLoadSendPieceEntity(LoadWaybillSendPieceEntity entity) {
		return this.getSqlSession().insert(NAMESPACE+"insertLoadSendPieceEntity", entity);
	}
	
	/**
	 * 
	 * <p>查询装车明细和派送单号</p> 
	 * @author alfred
	 * @date 2015-5-13 下午4:33:57
	 * @param taskId
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressDeliverLoadDao#queryLoadWayBillAndDeliverNo(java.lang.String)
	 */
	@Override
	public List<LoadWaybillDetailEntity> queryLoadWayBillAndDeliverNo(
			String taskId) {
		return this.getSqlSession().selectList(NAMESPACE+"queryLoadWayBillAndDeliverNo", taskId);
	}
	
	/**
	 * 
	 * <p>二程接驳-查询未完成的接驳装车任务</p> 
	 * @author alfred
	 * @date 2015-5-15 下午4:16:03
	 * @param condition
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressDeliverLoadDao#queryUnfinishedLoadTask(com.deppon.foss.module.transfer.pda.api.shared.domain.QueryAssignedLoadTaskEntity)
	 */
	@Override
	public List<PDAAssignLoadTaskEntity> queryUnfinishedLoadTask(
			QueryAssignedLoadTaskEntity condition) {
		return this.getSqlSession().selectList(NAMESPACE+"queryUnfinishedLoadTask", condition);
	}
	/**
	 * 
	 * <p>二程接驳-查询未完成的司机装车任务</p> 
	 * @author alfred
	 * @date 2015-5-19 上午8:49:39
	 * @param condition
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressDeliverLoadDao#queryUnfinishDriverLoadTask(com.deppon.foss.module.transfer.pda.api.shared.domain.QueryAssignedLoadTaskEntity)
	 */
	@Override
	public List<PDAAssignLoadTaskEntity> queryUnfinishDriverLoadTask(
			QueryAssignedLoadTaskEntity condition) {
		return this.getSqlSession().selectList(NAMESPACE+"queryUnfinishDriverLoadTask", condition);
	}
	
	/**
	 * 
	 * <p>二程接驳- 更新装车明细，剔除beLoaded条件 </p> 
	 * @author alfred
	 * @date 2015-7-2 上午8:44:47
	 * @param loadWayBill
	 * @param serialNo
	 * @param scanTime
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDAExpressDeliverLoadDao#updateLoadWayBillByScanTime(com.deppon.foss.module.transfer.load.api.shared.domain.LoadWaybillDetailEntity, java.lang.String, java.util.Date)
	 */
	@Override
	public int updateLoadWayBillByScanTime(LoadWaybillDetailEntity loadWayBill,
			String serialNo, Date scanTime) {
		if(loadWayBill == null){
			return 0;
		}
		//配合BI项目，增加修改时间字段
		loadWayBill.setModifyDate(new Date());
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("loadWayBill", loadWayBill);
		condition.put("serialNo", serialNo);
		condition.put("scanTime", scanTime);
		return this.getSqlSession().update(NAMESPACE+"updateLoadWayBillByScanTime", condition);
	}

}
