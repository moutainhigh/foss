package com.deppon.pda.bdm.module.foss.load.server.service.impl.derytranload;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressSendPieceService;
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
import com.deppon.pda.bdm.module.foss.load.server.service.impl.express.KdScanBusinessErrorLogComponent;
import com.deppon.pda.bdm.module.foss.load.shared.domain.KdScanBusinessErrorLog;
import com.deppon.pda.bdm.module.foss.load.shared.domain.derytranload.DeryCancelScanEntity;

/**
 * 接驳派件装车扫描-撤销
 * @ClassName CaclLoadService.java 
 * @Description 
 * @author 245955
 * @date 2015-4-20
 */
public class CaclLoadService implements IBusinessService<Void, DeryCancelScanEntity> {
	private Logger log = Logger.getLogger(getClass());
	private ILoadDao loadDao;
	private IPDAExpressSendPieceService pdaExpressSendPieceService;
	private KdScanBusinessErrorLogComponent kdScanBusinessErrorLogComponent;
	/**
	 * 解析包体
	 * @description 
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-4-20
	 */
	@Override
	public DeryCancelScanEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		DeryCancelScanEntity entity=JsonUtil.parseJsonToObject(DeryCancelScanEntity.class, asyncMsg.getContent());
		entity.setDeptCode(asyncMsg.getDeptCode());
		entity.setPdaCode(asyncMsg.getPdaCode());
		entity.setScanUser(asyncMsg.getUserCode());
		entity.setScanType(asyncMsg.getOperType());
		entity.setUploadTime(asyncMsg.getUploadTime());
		return entity;
	}

	/**
	 * 服务方法
	 * @description 
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-4-20
	 */
	@Override
	public Void service(AsyncMsg asyncMsg, DeryCancelScanEntity cancelScanEntity) throws PdaBusiException {
		       //检验数据合法性
				this.validate(asyncMsg, cancelScanEntity);
				cancelScanEntity.setSyncStatus(Constant.SYNC_STATUS_INIT);
				ExpressDeliverScanDto  entity = new ExpressDeliverScanDto ();
				entity.setTaskNo(cancelScanEntity.getTaskCode()); //任务号
				entity.setWayBillNo(cancelScanEntity.getWblCode());//运单号
				entity.setSerialNo(cancelScanEntity.getLabelCode());//流水号
				entity.setDeviceNo(asyncMsg.getPdaCode());//PDA设备号
				entity.setScanTime(cancelScanEntity.getScanTime());//扫描时间
				entity.setDeliverNo(cancelScanEntity.getDeryCode());//派送单号
				entity.setArrivePieces(cancelScanEntity.getSumPieces());//到达联件数
				entity.setType(TransferPDADictConstants.LOAD_GOODS_STATE_CANCELED);
				entity.setScanState(cancelScanEntity.getScanFlag());//扫描状态  扫描,没有手输
				try {
					//调用FOSS接口
					long startTime = System.currentTimeMillis();				
					pdaExpressSendPieceService.scan(entity);
					long endTime = System.currentTimeMillis();
					QueueMonitorInfo.addTotalFossTime(endTime-startTime);
					log.info("[asyncinfo]撤销派件装车扫描接口消耗时间:"+(endTime-startTime)+"ms");
				} catch (BusinessException e) {
					KdScanBusinessErrorLog kdScanBusinessErrorLog = new KdScanBusinessErrorLog();
					kdScanBusinessErrorLog.setId(cancelScanEntity.getId());
					kdScanBusinessErrorLog.setTaskCode(cancelScanEntity.getTaskCode());
					kdScanBusinessErrorLog.setWaybillCode(cancelScanEntity.getWblCode());
					kdScanBusinessErrorLog.setLabelCode(cancelScanEntity.getLabelCode());
					kdScanBusinessErrorLog.setReason(e.getErrorCode());
					kdScanBusinessErrorLog.setScanStatus(TransferPDADictConstants.LOAD_GOODS_STATE_CANCELED);
					kdScanBusinessErrorLogComponent.saveKdScanBusinessErrorLog(kdScanBusinessErrorLog);
					throw new FossInterfaceException(e.getCause(),e.getErrorCode());
				}
				//保存撤销装车扫描数据			
				loadDao.saveDeryCancelScan(cancelScanEntity);
				return null;
	}

	/**
	 * 操作类型
	 * @description 
	 * @return
	 * @author 245955
	 * @date 2015-4-20
	 */
	@Override
	public String getOperType() {
		return LoadConstant.OPER_TYPE_DELIVERY_TASK_SCAN_CANCEL.VERSION;
	}

	/**
	 * 是否异步
	 * @description 
	 * @return
	 * @author 245955
	 * @date 2015-4-20
	 */
	@Override
	public boolean isAsync() {
		return true;
	}
	private void validate(AsyncMsg asyncMsg, DeryCancelScanEntity entity) throws ArgumentInvalidException{
		//撤销装车扫描非空
		Argument.notNull(entity,"DeryCancelScanEntity");
		//流水号非空
		Argument.hasText(entity.getLabelCode(), "DeryCancelScanEntity.labelCode");
		//任务号非空
		Argument.hasText(entity.getTaskCode(), "DeryCancelScanEntity.taskCode");
		//撤销时间非空
		Argument.notNull(entity.getScanTime(), "DeryCancelScanEntity.scanTime");
	}


	public void setLoadDao(ILoadDao loadDao) {
		this.loadDao = loadDao;
	}
	
	public void setKdScanBusinessErrorLogComponent(
			KdScanBusinessErrorLogComponent kdScanBusinessErrorLogComponent) {
		this.kdScanBusinessErrorLogComponent = kdScanBusinessErrorLogComponent;
	}

	public void setPdaExpressSendPieceService(
			IPDAExpressSendPieceService pdaExpressSendPieceService) {
		this.pdaExpressSendPieceService = pdaExpressSendPieceService;
	}

}
