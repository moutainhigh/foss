/*
 * PROJECT NAME: tfr-load-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.api.server.dao
 * FILE    NAME: IPDAExpressDeliverLoadDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.server.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.LoadSerialNoEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadWaybillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadWaybillSendPieceEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoaderParticipationEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAAssignLoadTaskEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.QueryAssignedLoadTaskEntity;
/**
 * 快递派送装车Dao
 * @author dp-duyi
 * @date 2013-7-24 下午3:28:18
 */
public interface IPDAExpressDeliverLoadDao {
	public List<PDAAssignLoadTaskEntity> queryUnfinishedLoadTask(String loaderCode, String loaderOrg, Date queryTimeBegin,
			Date queryTimeEnd);
	public List<LoadWaybillDetailEntity> queryLoadWayBillQty(String taskId);
	public List<LoaderParticipationEntity> queryLoader(String taskId);
	public int updateLoadWayBillQTYByScanTime(LoadWaybillDetailEntity loadWayBill,String serialNo,Date scanTime);
	public int updateLoadSerialNoByLoadTime(LoadSerialNoEntity loadSerialNo);
	public BigDecimal queryWaybillAmount(String waybillNo);
	public int insertLoadSendPieceEntity(LoadWaybillSendPieceEntity entity);
	public List<LoadWaybillDetailEntity> queryLoadWayBillAndDeliverNo(String taskId);
	//PDA查询未完成接驳装车任务
	public List<PDAAssignLoadTaskEntity> queryUnfinishedLoadTask(QueryAssignedLoadTaskEntity condition);
	//PDA查询未完成司机装车任务
	public List<PDAAssignLoadTaskEntity> queryUnfinishDriverLoadTask(QueryAssignedLoadTaskEntity condition);
	//更新运单明细，剔除是否装车字段条件
	public int updateLoadWayBillByScanTime(LoadWaybillDetailEntity loadWayBill,String serialNo,Date scanTime);
}
