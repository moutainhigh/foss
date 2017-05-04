package com.deppon.pda.bdm.module.foss.load.server.service.impl.derytranload;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

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
import com.deppon.pda.bdm.module.foss.load.shared.domain.derytranload.DeryLoadScanEntity;

/**
 * 接驳派件扫描
 * @ClassName LoadScanService.java 
 * @Description 
 * @author 245955
 * @date 2015-4-18
 */
@Transactional
public class LoadScanService implements IBusinessService<Void, DeryLoadScanEntity>{
	private ILoadDao loadDao;
	private IPDAExpressSendPieceService pdaExpressSendPieceService;
	private KdScanBusinessErrorLogComponent kdScanBusinessErrorLogComponent;
	private Logger log = Logger.getLogger(getClass());
	
	/**
	 * 解析包体
	 *  <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @description 
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-4-18
	 */
	@Override
	public DeryLoadScanEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		DeryLoadScanEntity entity = JsonUtil.parseJsonToObject(DeryLoadScanEntity.class, asyncMsg.getContent());
		entity.setPdaCode(asyncMsg.getPdaCode());
		entity.setScanUser(asyncMsg.getUserCode());
		entity.setScanType(asyncMsg.getOperType());
		entity.setUploadTime(asyncMsg.getUploadTime());
		entity.setDeptCode(asyncMsg.getDeptCode());	
		return entity;
	}

	/**
	 * 服务方法
	 *  <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @description 
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-4-18
	 */
	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, DeryLoadScanEntity param)
			throws PdaBusiException {
		//校验数据合法性
		this.validate(param);
		param.setSyncStatus(Constant.SYNC_STATUS_INIT);
		ExpressDeliverScanDto  entity = new ExpressDeliverScanDto();
		entity.setTaskNo(param.getTaskCode());//任务号
		entity.setWayBillNo(param.getWblCode());//运单号
		entity.setSerialNo(param.getLabelCode());//流水号
		entity.setDeviceNo(asyncMsg.getPdaCode());//PDA设备号
		entity.setScanTime(param.getScanTime());//扫描时间
		entity.setDeliverNo(param.getDeryCode());//派送单号
		entity.setArrivePieces(param.getSumPieces());//到达联件数
		entity.setType(TransferPDADictConstants.LOAD_GOODS_STATE_NORMAL);//类型  正扫
		entity.setScanState(param.getScanFlag());//扫描状态  扫描,没有手输
		try {
			//调用FOSS接口
			long startTime = System.currentTimeMillis();
			pdaExpressSendPieceService.scan(entity);
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			log.info("[asyncinfo]派件装车扫描接口消耗时间:"+(endTime-startTime)+"ms");
		} catch (BusinessException e) {
			KdScanBusinessErrorLog kdScanBusinessErrorLog = new KdScanBusinessErrorLog();
			kdScanBusinessErrorLog.setId(param.getId());
			kdScanBusinessErrorLog.setTaskCode(param.getTaskCode());
			kdScanBusinessErrorLog.setWaybillCode(param.getWblCode());
			kdScanBusinessErrorLog.setLabelCode(param.getLabelCode());
			kdScanBusinessErrorLog.setReason(e.getErrorCode());
			kdScanBusinessErrorLog.setScanStatus(TransferPDADictConstants.LOAD_GOODS_STATE_NORMAL);
			kdScanBusinessErrorLogComponent.saveKdScanBusinessErrorLog(kdScanBusinessErrorLog);
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		//保存装车扫描信息
		loadDao.saveDeryLoadScan(param);
		return null;		
	}
	
	/**
	 * 参数验证
	 * @description 
	 * @param entity
	 * @throws ArgumentInvalidException
	 * @author 245955
	 * @date 2015-4-18
	 */
	private void validate(DeryLoadScanEntity entity)
			throws ArgumentInvalidException {
		//检验扫描非空
		Argument.notNull(entity, "DeryLoadScanEntity");
		//流水号非空
		Argument.hasText(entity.getLabelCode(), "DeryLoadScanEntity.labelCode");
		//任务号非空
		Argument.hasText(entity.getTaskCode(), "DeryLoadScanEntity.taskCode");
		//扫描时间非空
		Argument.notNull(entity.getScanTime(), "DeryLoadScanEntity.scanTime");
	}
	/**
	 * 操作类型
	 * @description 
	 * @return
	 * @author 245955
	 * @date 2015-4-18
	 */
	@Override
	public String getOperType() {
		return LoadConstant.OPER_TYPE_DELIVERY_TASK_SCAN.VERSION;
	}

	/**
	 * 是否异步
	 * @description 
	 * @return
	 * @author 245955
	 * @date 2015-4-18
	 */
	@Override
	public boolean isAsync() {
		return true;
	}

	public void setLoadDao(ILoadDao loadDao) {
		this.loadDao = loadDao;
	}
	
	public void setPdaExpressSendPieceService(
			IPDAExpressSendPieceService pdaExpressSendPieceService) {
		this.pdaExpressSendPieceService = pdaExpressSendPieceService;
	}

	public void setKdScanBusinessErrorLogComponent(
			KdScanBusinessErrorLogComponent kdScanBusinessErrorLogComponent) {
		this.kdScanBusinessErrorLogComponent = kdScanBusinessErrorLogComponent;
	}
}

