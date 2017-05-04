package com.deppon.pda.bdm.module.foss.unload.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.Argument;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDATrayScanService;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDATrayOfflineScanWaybillEntiy;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDATrayOfflineScanDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.UnLoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.core.shared.util.UUIDUtils;
import com.deppon.pda.bdm.module.foss.unload.server.dao.IUnloadDao;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.BindingScanEntity;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.ExcBindingScanEntity;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.ExcPalletBoundScanEntity;

public class ExcPalletBoundService implements IBusinessService<Void, ExcPalletBoundScanEntity>{
	private static final Logger log = Logger.getLogger(ExcPalletBoundService.class);
	private IPDATrayScanService pdaTrayScanService;
	private IUnloadDao unloadDao;
	@Override
	public ExcPalletBoundScanEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		ExcPalletBoundScanEntity entity = new ExcPalletBoundScanEntity();
		entity = JsonUtil.parseJsonToObject(ExcPalletBoundScanEntity.class,
				asyncMsg.getContent());
		//部门编号
		entity.setDeptCode(asyncMsg.getDeptCode());
		//pda编号
		entity.setPdaCode(asyncMsg.getPdaCode());
		//用户编号
		entity.setScanUser(asyncMsg.getUserCode());
		//扫描类型
		entity.setScanType(asyncMsg.getOperType());
		//上传时间
		entity.setUploadTime(asyncMsg.getUploadTime());
		return entity;
	}
	
	@Override
	@Transactional
	public Void service(AsyncMsg asyncMsg, ExcPalletBoundScanEntity param)
			throws PdaBusiException {
		this.validate(asyncMsg, param);
		unloadDao.saveExcPalletBoundScan(param);
		BindingScanEntity scanEntity = new BindingScanEntity();
		//中转接口实体参数
		PDATrayOfflineScanDto dto = new PDATrayOfflineScanDto();
		List<PDATrayOfflineScanWaybillEntiy> waybillList = new ArrayList<PDATrayOfflineScanWaybillEntiy>();
		
		dto.setForkLiftDept(asyncMsg.getDeptCode());//扫描部门
		dto.setForkLiftDriverCode(asyncMsg.getUserCode());//扫描人
		dto.setOfflineTaskNo(param.getForkBindingNo());//绑定任务号
		dto.setTrayOfflineScanTime(param.getBindingDate());//绑定时间
		
		scanEntity.setBindingDate(param.getBindingDate());
		scanEntity.setBindingNo(param.getForkBindingNo());
		for (ExcBindingScanEntity waybillInfo : param.getBindingScanInfoList()) {
				scanEntity.setId(UUIDUtils.getUUID());
				scanEntity.setSerialNo(waybillInfo.getSerial().toString());
				scanEntity.setWl(waybillInfo.getWblCode());
				unloadDao.saveExcPalletBoundScanDetail(scanEntity);
				for (String serial : waybillInfo.getSerial()) {
					PDATrayOfflineScanWaybillEntiy waybill = new PDATrayOfflineScanWaybillEntiy();
					waybill.setWaybillNo(waybillInfo.getWblCode());
					waybill.setSerialNo(serial);
					waybillList.add(waybill);
				}
		}
		dto.setWaybillEntity(waybillList);
		try {
			long startTime = System.currentTimeMillis();
			pdaTrayScanService.createTrayOfflineScanTask(dto);
			long endTime = System.currentTimeMillis();
			log.info("[asyncinfo]异常叉车扫描接口消耗时间:" + (endTime - startTime) + "ms");
		}catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(), e.getErrorCode());
		}
		return null;
	}
	
	private void validate(AsyncMsg asyncMsg, ExcPalletBoundScanEntity entity) throws PdaBusiException {
		Argument.notNull(asyncMsg, "AsyncMsg");
		Argument.notNull(entity, "PalletSindingEntity");
		//托盘绑定时间
		Argument.notNull(entity.getBindingDate(), "PalletSindingEntity.bindingDate");
		//托盘绑定唯一编码
		Argument.hasText(entity.getForkBindingNo(), "PalletSindingEntity.bindingNo");
//		//运单号
//		Argument.hasText(entity.getWblCode(), "PalletSindingEntity.wblCode");
		//扫描列表
		Argument.notEmpty(entity.getBindingScanInfoList(),"PalletSindingEntity.bindingScanInfoList");
	}
	
	public void setPdaTrayScanService(IPDATrayScanService pdaTrayScanService) {
		this.pdaTrayScanService = pdaTrayScanService;
	}

	public void setUnloadDao(IUnloadDao unloadDao) {
		this.unloadDao = unloadDao;
	}

	@Override
	public String getOperType() {
		return UnLoadConstant.OPER_TYPE_EXC_UNLD_PALLET_BOUND_SCAN.VERSION;
	}

	@Override
	public boolean isAsync() {
		return true;
	}

}
