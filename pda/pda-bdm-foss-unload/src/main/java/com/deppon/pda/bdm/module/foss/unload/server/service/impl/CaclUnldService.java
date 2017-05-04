package com.deppon.pda.bdm.module.foss.unload.server.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.Argument;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAUnloadTaskService;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.module.transfer.pda.api.shared.dto.UnloadScanDetailDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.UnLoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.unload.server.dao.IUnloadDao;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.UnloadCancelScanEntity;
/**
 * 撤销卸车扫描service实现类
 * @author gaojia
 * @date Sep 10,2012 9:48:30 AM
 * @version 1.0
 * @since
 */

public class CaclUnldService implements IBusinessService<Void, com.deppon.pda.bdm.module.foss.unload.shared.domain.UnloadCancelScanEntity>{
	private IUnloadDao unloadDao;
	private IPDAUnloadTaskService pdaUnloadTaskService;
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:32:59
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public UnloadCancelScanEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		UnloadCancelScanEntity entity = new UnloadCancelScanEntity();
		entity = JsonUtil.parseJsonToObject(UnloadCancelScanEntity.class, asyncMsg.getContent());
		entity.setDeptCode(asyncMsg.getDeptCode());
		entity.setPdaCode(asyncMsg.getPdaCode());
		entity.setScanUser(asyncMsg.getUserCode());
		entity.setScanType(asyncMsg.getOperType());
		entity.setUploadTime(asyncMsg.getUploadTime());
		return entity;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:33:05
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, UnloadCancelScanEntity param)
			throws PdaBusiException {
		this.validate(param);
		unloadDao.saveCaclUnldScan(param);
		UnloadScanDetailDto req = new UnloadScanDetailDto();
		req.setWayBillNo(param.getWblCode());
		req.setSerialNo(param.getLabelCode());
		req.setTaskNo(param.getTaskCode());
		req.setType(TransferPDADictConstants.UNLOAD_GOODS_STATE_CANCELED);
		req.setWeight(param.getWeight());
		req.setVolume(param.getVolume());
		req.setGoodsName(param.getCargoName());
		req.setDeviceNo(asyncMsg.getPdaCode());
		req.setScanState(param.getScanFlag());
		req.setScanTime(param.getScanTime());
		req.setBillNo(param.getBillNo());
		req.setDestOrgCode(param.getDestOrgCode());
		req.setOrigOrgCode(param.getOrigOrgCode());
		req.setTransportType(param.getTransType());
		req.setPack(param.getPacking());
		req.setHandOverQty(param.getRcptPieces());
		req.setBePartial(param.getBePartial());
		req.setIsPackageScan(param.getIfPackage());//是否包扫描
		//运单生效状态 - YES NO
		req.setWayBillStatus(param.getWayBillStatus());
		if(param.getIfPackage()==null || !"Y".equals(param.getIfPackage())){
			Argument.hasText(param.getLabelCode(),"unldScanEntity.labelCode");
		}
		try {
			pdaUnloadTaskService.unloadScan(req);
			return null;
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
	}

	@Override
	public String getOperType() {
		return UnLoadConstant.OPER_TYPE_UNLD_SCAN_CANCEL.VERSION;
	}

	@Override
	public boolean isAsync() {
		return true;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:33:14
	 * @param entity
	 * @throws ArgumentInvalidException
	 * @see
	 */
	private void validate(UnloadCancelScanEntity entity) throws ArgumentInvalidException{
		//检验取消卸车扫描非空
		Argument.notNull(entity,"unloadCancelScanEntity");
		//标签号非空
	//	Argument.hasText(entity.getLabelCode(), "unloadCancelScanEntity.labelCode");
		//任务号非空
		Argument.hasText(entity.getTaskCode(), "unloadCancelScanEntity.taskCode");
		//扫描时间非空
		Argument.notNull(entity.getScanTime(), "unloadCancelScanEntity.scanTime");
	}

	public void setUnloadDao(IUnloadDao unloadDao) {
		this.unloadDao = unloadDao;
	}

	public void setPdaUnloadTaskService(IPDAUnloadTaskService pdaUnloadTaskService) {
		this.pdaUnloadTaskService = pdaUnloadTaskService;
	}
	

}