package com.deppon.pda.bdm.module.foss.packaging.server.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAPackagingService;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAPackagingInfoEntity;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.PackagingConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.packaging.shared.domain.WrapedScanEntity;

/**
 * 
 * TODO(打包扫描)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Administrator,date:2012-12-3 下午4:57:09,content:TODO </p>
 * @author Administrator
 * @date 2012-12-3 下午4:57:09
 * @since
 * @version
 */
public class WrapedScanService implements IBusinessService<Void, WrapedScanEntity>{
	//private IWrapDao wrapDao;
	private IPDAPackagingService pdaPackagingService;
	private Logger log = Logger.getLogger(getClass());
	@Override
	public WrapedScanEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		WrapedScanEntity entity = JsonUtil.parseJsonToObject(WrapedScanEntity.class, asyncMsg.getContent());
		entity.setDeptCode(asyncMsg.getDeptCode());
		entity.setPdaCode(asyncMsg.getPdaCode());
		entity.setScanUser(asyncMsg.getUserCode());
		entity.setScanType(asyncMsg.getOperType());
		return entity;
	}
	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, WrapedScanEntity param)
			throws PdaBusiException {
		log.info("包装扫描开始");
		this.validate(param);
		//param.setLabelCode(this.listToString(param.getLabelCodes()));
		//param.setScanStatus(this.listToString(param.getWrapedPeople()));
		//wrapDao.saveWrapScan(param);
		PDAPackagingInfoEntity entity = new PDAPackagingInfoEntity();
		entity.setWayBillNumber(param.getWblCode());
		entity.setPackedMate(param.getWrapType());
		entity.setPackedVolume(BigDecimal.valueOf(param.getWrapVolume()));
		entity.setPlusNum(param.getTrayNumbers());
		entity.setRemark(param.getRemark());
		entity.setPDACode(asyncMsg.getPdaCode());
		entity.setOrgCode(asyncMsg.getDeptCode());
		entity.setOperatorCode(asyncMsg.getUserCode());
		entity.setSerialNo(param.getLabelCodes());
		entity.setEmpCode(param.getWrapedPeople());
		entity.setPackageSupplierCode(param.getPackageSupplierCode());
		try {
			long startTime = System.currentTimeMillis();
			pdaPackagingService.addPackagingInfo(entity);
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			log.info("[asyncinfo]打包扫描接口消耗时间:"+(endTime-startTime)+"ms");
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		log.info("包装扫描成功");
		return null;
	}

	private void validate(WrapedScanEntity param) {
		Argument.notNull(param, "WrapedScanEntity");
		Argument.hasText(param.getWblCode(),  "WrapedScanEntity.wblCode");
		Argument.notNull(param.getScanTime(), "WrapedScanEntity.scanTime");
		Argument.notEmpty(param.getLabelCodes(), "WrapedScanEntity.LabelCodes");
		Argument.notEmpty(param.getWrapedPeople(), "WrapedScanEntity.wrapedPeople");
		Argument.hasText(param.getWrapType(), "WrapedScanEntity.WrapType");
		Argument.isPositiveNum(param.getWrapVolume(), "WrapedScanEntity.WrapVolume");
		//Argument.isPositiveNum(param.getTrayNumbers(), "WrapedScanEntity.TrayNumbers");
	}
	private String listToString(List<String> stringList) {
		if (stringList == null) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		boolean flag = false;
		for (String string : stringList) {
			if (flag) {
				result.append(",");
			} else {
				flag = true;
			}
			result.append(string);
		}
		return result.toString();
	}
	@Override
	public String getOperType() {
		return PackagingConstant.OPER_TYPE_WRAP_SCAN.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

//	public void setWrapDao(IWrapDao wrapDao) {
//		this.wrapDao = wrapDao;
//	}

	public void setPdaPackagingService(IPDAPackagingService pdaPackagingService) {
		this.pdaPackagingService = pdaPackagingService;
	}
	
}
