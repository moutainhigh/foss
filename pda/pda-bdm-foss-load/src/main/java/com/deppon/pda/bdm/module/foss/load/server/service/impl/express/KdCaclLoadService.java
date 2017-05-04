package com.deppon.pda.bdm.module.foss.load.server.service.impl.express;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressDeliverLoadService;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressDeliverScanDto;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.load.server.dao.ILoadDao;
import com.deppon.pda.bdm.module.foss.load.shared.domain.KdLoadCancelScanEntity;
import com.deppon.pda.bdm.module.foss.load.shared.domain.KdScanBusinessErrorLog;




/**
 * 快递派送装车扫描-撤销
 * @author 
 * @date 2013年7月19日15:55:37
 * @version 1.0
 * @since
 */
public class KdCaclLoadService implements IBusinessService<Void,KdLoadCancelScanEntity>{
	
	private Logger log = Logger.getLogger(getClass());
	private ILoadDao loadDao;
	private IPDAExpressDeliverLoadService pdaExpressDeliverLoadService;
	private KdScanBusinessErrorLogComponent kdScanBusinessErrorLogComponent;
	@Override
	public KdLoadCancelScanEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		KdLoadCancelScanEntity entity = JsonUtil.parseJsonToObject(KdLoadCancelScanEntity.class, asyncMsg.getContent());
		entity.setDeptCode(asyncMsg.getDeptCode());
		entity.setPdaCode(asyncMsg.getPdaCode());
		entity.setScanUser(asyncMsg.getUserCode());
		entity.setScanType(asyncMsg.getOperType());
		entity.setUploadTime(asyncMsg.getUploadTime());
		return entity;
	}
	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, KdLoadCancelScanEntity kdLoadCancelScanEntity)
			throws PdaBusiException {
		//检验数据合法性
		this.validate(asyncMsg, kdLoadCancelScanEntity);
		kdLoadCancelScanEntity.setSyncStatus(Constant.SYNC_STATUS_INIT);
		ExpressDeliverScanDto  entity = new ExpressDeliverScanDto ();
		entity.setTaskNo(kdLoadCancelScanEntity.getTaskCode());
		entity.setWayBillNo(kdLoadCancelScanEntity.getWblCode());
		entity.setSerialNo(kdLoadCancelScanEntity.getLabelCode());
		entity.setDeviceNo(asyncMsg.getPdaCode());
		entity.setScanTime(kdLoadCancelScanEntity.getScanTime());
		entity.setType(TransferPDADictConstants.LOAD_GOODS_STATE_CANCELED);
		entity.setScanState(kdLoadCancelScanEntity.getScanFlag());
		try {
			KdScanBusinessErrorLog kdScanBusinessErrorLog = new KdScanBusinessErrorLog();
			kdScanBusinessErrorLog.setId(kdLoadCancelScanEntity.getId());
			kdScanBusinessErrorLog.setTaskCode(kdLoadCancelScanEntity.getTaskCode());
			kdScanBusinessErrorLog.setWaybillCode(kdLoadCancelScanEntity.getWblCode());
			kdScanBusinessErrorLog.setLabelCode(kdLoadCancelScanEntity.getLabelCode());
			kdScanBusinessErrorLog.setReason("");
			kdScanBusinessErrorLog.setScanStatus(TransferPDADictConstants.LOAD_GOODS_STATE_CANCELED);
			kdScanBusinessErrorLogComponent.saveKdScanBusinessErrorLog(kdScanBusinessErrorLog);
			long startTime = System.currentTimeMillis();
			//调用FOSS接口
			pdaExpressDeliverLoadService.scan(entity);
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			log.info("[asyncinfo]撤销快递装车扫描接口消耗时间:"+(endTime-startTime)+"ms");
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		//保存撤销装车扫描数据
		loadDao.saveKdLoadCaclScan(kdLoadCancelScanEntity);
		return null;
	}
	
	private void validate(AsyncMsg asyncMsg, KdLoadCancelScanEntity entity) throws ArgumentInvalidException{
		//撤销装车扫描非空
		Argument.notNull(entity,"kdLoadCancelScanEntity");
		//标签号非空
		Argument.hasText(entity.getLabelCode(), "kdLoadCancelScanEntity.labelCode");
		//任务号非空
		Argument.hasText(entity.getTaskCode(), "kdLoadCancelScanEntity.taskCode");
		//撤销时间非空
		Argument.notNull(entity.getScanTime(), "kdLoadCancelScanEntity.scanTime");
	}
	@Override
	public String getOperType() {
		return LoadConstant.OPER_TYPE_KD_LOAD_CANCEL.VERSION;
	}

	@Override
	public boolean isAsync() {
		return true;
	}
	

	public void setPdaExpressDeliverLoadService(
			IPDAExpressDeliverLoadService pdaExpressDeliverLoadService) {
		this.pdaExpressDeliverLoadService = pdaExpressDeliverLoadService;
	}

	public void setLoadDao(ILoadDao loadDao) {
		this.loadDao = loadDao;
	}
	
	public void setKdScanBusinessErrorLogComponent(
			KdScanBusinessErrorLogComponent kdScanBusinessErrorLogComponent) {
		this.kdScanBusinessErrorLogComponent = kdScanBusinessErrorLogComponent;
	}
}
