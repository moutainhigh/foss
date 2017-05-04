package com.deppon.pda.bdm.module.foss.unload.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.Argument;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAUnloadTaskService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAGoodsSerialNoDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.UnloadGoodsDetailDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.UnloadScanDetailDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.UnLoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.core.shared.util.StringUtils;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.UnldCrgDetail;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.UnldScanEntity;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.UnldSerialNoModel;

public class ForceUnldScanService implements IBusinessService<UnldCrgDetail, UnldScanEntity>{
	private IPDAUnloadTaskService pdaUnloadTaskService;
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:34:21
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public UnldScanEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		UnldScanEntity entity = new UnldScanEntity();
		entity = JsonUtil.parseJsonToObject(UnldScanEntity.class, asyncMsg.getContent());
		entity.setDeptCode(asyncMsg.getDeptCode());
		entity.setPdaCode(asyncMsg.getPdaCode());
		entity.setScanUser(asyncMsg.getUserCode());
		entity.setScanType(asyncMsg.getOperType());
		return entity;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:34:29
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Override
	public UnldCrgDetail service(AsyncMsg asyncMsg, UnldScanEntity param)
			throws PdaBusiException {
		this.validate(param);
		UnloadGoodsDetailDto  dto = null;
		try {
			dto = pdaUnloadTaskService.moreGoodsUnloadVerify(param.getTaskCode(),param.getWblCode(),param.getLabelCode());
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		UnloadScanDetailDto req = new UnloadScanDetailDto();
		req.setWayBillNo(param.getWblCode());
		req.setSerialNo(param.getLabelCode());
		req.setTaskNo(param.getTaskCode());
		req.setType(param.getScanStatus());
		req.setWeight(param.getWeight());
		try {
			double volume = Double.parseDouble(param.getVolume());
			if(volume>99999999||volume<0){
				req.setVolume(0);
			}else{
				req.setVolume(volume);
			}
		} catch (Exception e) {
			req.setVolume(0);
		}
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
		req.setBePartial("Y");
		try {
			pdaUnloadTaskService.unloadScan(req);
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		UnldCrgDetail detail = new UnldCrgDetail();
		if(dto != null){
			detail.setTaskCode(dto.getTaskNo());
			detail.setWblCode(dto.getWayBillNo());
			detail.setAcctDeptCode(dto.getReceiveOrgCode());
			/*
			 * wwn 3013-05-31 16:00
			 * */
			detail.setAcctDeptName(StringUtils.convert(dto.getReceiveOrgName()));
			detail.setArrDeptCode(dto.getReachOrgCode());
			/*
			 * wwn 3013-05-31 16:00
			 * */
			detail.setArrDeptName(StringUtils.convert(dto.getReachOrgName()));
			/*
			 * wwn 3013-05-31 16:00
			 * */
			detail.setCargoName(StringUtils.convert(dto.getGoodsName()));//货名
			detail.setVolume(dto.getVolume());
			detail.setWeight(dto.getWeight());
			detail.setTransType(dto.getTransportType());
			detail.setIsNessary(dto.getBePriorityGoods());
			/*
			 * wwn 3013-05-31 16:00
			 * */
			detail.setPacking(StringUtils.convert(dto.getPacking()));//包装
			detail.setIsVal(dto.getIsValue());
			//detail.setChgStatus(dto.getModifyContent());
			//detail.setIsModify(dto.getModifyState());
			detail.setBeContraband(dto.getBeContraband());
			detail.setOrigOrgCode(dto.getOrigOrgCode());
			detail.setDestOrgCode(dto.getDestOrgCode());
			detail.setBillNo(dto.getBillNo());
			List<UnldSerialNoModel> serias = new ArrayList<UnldSerialNoModel>();
			if(dto.getSerialNos()!=null&&!dto.getSerialNos().isEmpty()){
				for (PDAGoodsSerialNoDto seriaDto : dto.getSerialNos()) {
					UnldSerialNoModel ser = new UnldSerialNoModel();
					ser.setIsWrap(seriaDto.getIsUnPacking());
					ser.setSerialNo(seriaDto.getSerialNo());
					serias.add(ser);
				}
			}
			detail.setSerialNo(serias);
			detail.setPieces(dto.getWayBillQty());
			detail.setRcptPieces(0);
			detail.setRemark(dto.getNotes());
			detail.setUnldPieces(dto.getOperateQty());
			detail.setBeContraband(dto.getBeContraband());
			detail.setBePartial("Y");
		}else{
			throw new FossInterfaceException(null,"运单明细为空");
		}
		return detail;
	}
	
	private void validate(UnldScanEntity unldScanEnity) throws ArgumentInvalidException{
		//检验卸车扫描非空
		Argument.notNull(unldScanEnity,"unldScanEnity");
		//检验标签号非空
		Argument.hasText(unldScanEnity.getLabelCode(), "unldScanEntity.labelCode");
		//检验任务号非空
		Argument.hasText(unldScanEnity.getTaskCode(), "unldScanEntity.taskCode");
		//检验扫描时间非空
		Argument.notNull(unldScanEnity.getScanTime(), "unldScanEntity.scanTime");
	}
	@Override
	public String getOperType() {
		return UnLoadConstant.OPER_TYPE_FORCE_UNLD_SCAN.VERSION;
	}

	@Override
	public boolean isAsync() {
		// TODO Auto-generated method stub
		return false;
	}
	public void setPdaUnloadTaskService(IPDAUnloadTaskService pdaUnloadTaskService) {
		this.pdaUnloadTaskService = pdaUnloadTaskService;
	}
	
}
