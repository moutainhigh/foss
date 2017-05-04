package com.deppon.pda.bdm.module.foss.load.server.service.impl.tallyload;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDATransferLoadService;
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
 * 撤销装车扫描 
 * @ClassName CaclLoadService.java 
 * @Description 
 * @author 245955
 * @date 2015-5-16
 */
@Transactional
public class CaclLoadService implements IBusinessService<Void,LoadCancelScanEntity>{
	
	private Logger log = Logger.getLogger(getClass());
	private ILoadDao loadDao;
	private IPDATransferLoadService pdaTransferLoadService;
	@Override
	public LoadCancelScanEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
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
		//保存撤销装车扫描数据
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
		//String result = null;
		try {
			long startTime = System.currentTimeMillis();
			//调用FOSS接口
			pdaTransferLoadService.loadScan(entity);
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			log.info("[asyncinfo]撤销装车扫描接口消耗时间:"+(endTime-startTime)+"ms");
			return null;
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
	}
	private void validate(AsyncMsg asyncMsg, LoadCancelScanEntity entity) throws ArgumentInvalidException{
		//撤销装车扫描非空
		Argument.notNull(entity,"LoadCancelScanEntity");
		//标签号非空
	//	Argument.hasText(entity.getLabelCode(), "LoadCancelScanEntity.labelCode");
		//任务号非空
		Argument.hasText(entity.getTaskCode(), "LoadCancelScanEntity.taskCode");
		//撤销时间非空
		Argument.notNull(entity.getScanTime(), "LoadCancelScanEntity.scanTime");
	}
	@Override
	public String getOperType() {
		return LoadConstant.OPER_TYPE_LOAD_TRAN_CANCEL.VERSION;
	}

	@Override
	public boolean isAsync() {
		return true;
	}
	

	public void setPdaTransferLoadService(
			IPDATransferLoadService pdaTransferLoadService) {
		this.pdaTransferLoadService = pdaTransferLoadService;
	}

	public void setLoadDao(ILoadDao loadDao) {
		this.loadDao = loadDao;
	}
}
