package com.deppon.pda.bdm.module.foss.load.server.service.impl.driverload;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressPickService;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadScanDetailDto;
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
import com.deppon.pda.bdm.module.foss.load.shared.domain.LoadCancelScanEntity;

/**
 * 司机撤销装车扫描
 * @author 092038
 *
 */
public class CaclLoadScanService implements IBusinessService<Void, LoadCancelScanEntity>{
	private Logger log = Logger.getLogger(getClass());
	private ILoadDao loadDao;
	private IPDAExpressPickService pdaExpressPickService;
	
	@Transactional
	@Override
	public LoadCancelScanEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		LoadCancelScanEntity entity = JsonUtil.parseJsonToObject(LoadCancelScanEntity.class, asyncMsg.getContent());
		entity.setDeptCode(asyncMsg.getDeptCode());
		entity.setPdaCode(asyncMsg.getPdaCode());
		entity.setScanUser(asyncMsg.getUserCode());
		entity.setScanType(asyncMsg.getOperType());
		entity.setUploadTime(asyncMsg.getUploadTime());
		return entity;
	}

	@Override
	public Void service(AsyncMsg asyncMsg, LoadCancelScanEntity loadCancelScanEntity)
			throws PdaBusiException {
		//检验数据合法性
		this.validate(asyncMsg, loadCancelScanEntity);
		loadCancelScanEntity.setSyncStatus(Constant.SYNC_STATUS_INIT);
		//保存装车扫描数据
		loadDao.saveDriverLoadCaclScan(loadCancelScanEntity);
		LoadScanDetailDto entity = new LoadScanDetailDto();
		entity.setTaskNo(loadCancelScanEntity.getTaskCode());
		entity.setWayBillNo(loadCancelScanEntity.getWblCode());
		entity.setSerialNo(loadCancelScanEntity.getLabelCode());
		entity.setDeviceNo(asyncMsg.getPdaCode());
		entity.setScanTime(loadCancelScanEntity.getScanTime());
		entity.setType(TransferPDADictConstants.LOAD_GOODS_STATE_CANCELED);
		entity.setScanState(loadCancelScanEntity.getScanFlag());
		entity.setVolume(loadCancelScanEntity.getVolume());
		entity.setWeight(loadCancelScanEntity.getWeight());
		entity.setGoodsName(loadCancelScanEntity.getCargoName());
		entity.setBeJoinCar(loadCancelScanEntity.getBeJoinCar());
		entity.setStockQty(loadCancelScanEntity.getStockQty());
		entity.setPack(loadCancelScanEntity.getIsWrap());
		entity.setTransportType(loadCancelScanEntity.getTransType());
		entity.setReceiveOrgName(loadCancelScanEntity.getAcctDeptName());
		entity.setReachOrgName(loadCancelScanEntity.getArrDeptName());
		entity.setIsPackageScan(loadCancelScanEntity.getIfPackage());//是否包扫描
		if(loadCancelScanEntity.getIfPackage()==null || !"Y".equals(loadCancelScanEntity.getIfPackage())){
				Argument.hasText(loadCancelScanEntity.getLabelCode(), "loadCancelScanEntity.labelCode");
		}
		try {
			long startTime = System.currentTimeMillis();
			//调用FOSS接口
		pdaExpressPickService.scan(entity);
		long endTime = System.currentTimeMillis();
		QueueMonitorInfo.addTotalFossTime(endTime-startTime);
		log.info("[asyncinfo]装车扫描接口消耗时间:"+(endTime-startTime)+"ms");
		return null;
		} catch (BusinessException e) {
						throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
	}

	@Override
	public String getOperType() {
		return LoadConstant.OPER_TYPE_DRIVER_TASK_CANCEL.VERSION;
	}

	@Override
	public boolean isAsync() {
		return true;
	}
	/**
	 * 参数验证
	 * @description 
	 * @param asyncMsg
	 * @param entity
	 * @throws ArgumentInvalidException
	 * @author 245955
	 * @date 2015-4-23
	 */
	private void validate(AsyncMsg asyncMsg, LoadCancelScanEntity entity) throws ArgumentInvalidException{
		//撤销装车扫描非空
		Argument.notNull(entity,"LoadCancelScanEntity");
		//标签号非空
	    //Argument.hasText(entity.getLabelCode(), "loadCancelScanEntity.labelCode");
		//任务号非空
		Argument.hasText(entity.getTaskCode(), "LoadCancelScanEntity.taskCode");
		//撤销时间非空
		Argument.notNull(entity.getScanTime(), "LoadCancelScanEntity.scanTime");
	}

	public void setLoadDao(ILoadDao loadDao) {
		this.loadDao = loadDao;
	}

	public void setPdaExpressPickService(
			IPDAExpressPickService pdaExpressPickService) {
		this.pdaExpressPickService = pdaExpressPickService;
	}
}
