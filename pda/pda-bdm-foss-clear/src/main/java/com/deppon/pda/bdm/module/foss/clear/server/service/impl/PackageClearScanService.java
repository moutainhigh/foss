package com.deppon.pda.bdm.module.foss.clear.server.service.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPdaStockcheckingService;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.ClearConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.clear.server.dao.IClearDao;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.ClearScanEntity;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.PackageClearScanEntity;


/**   
 * @ClassName PackageClearScanService  
 * @Description 包清仓   
 * @author  092038 张贞献  
 * @date 2015-4-13    
 */ 
public class PackageClearScanService implements IBusinessService<Void, PackageClearScanEntity> {
	private static final Log LOG = LogFactory.getLog(PackageClearScanService.class);
		
	private IClearDao clearDao;
	
	private IPdaStockcheckingService pdaStockcheckingService;
	
	@Transactional
	@Override
	public PackageClearScanEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		PackageClearScanEntity packageClearScanEntity = JsonUtil.parseJsonToObject(PackageClearScanEntity.class, asyncMsg.getContent());
		packageClearScanEntity.setDeptCode(asyncMsg.getDeptCode()); // 部门
		packageClearScanEntity.setPdaCode(asyncMsg.getPdaCode());   // PDA
		packageClearScanEntity.setScanUser(asyncMsg.getUserCode()); // user
		packageClearScanEntity.setScanType(asyncMsg.getOperType()); // 操作类型
		packageClearScanEntity.setId(asyncMsg.getId());			  // ID
		packageClearScanEntity.setUploadTime(asyncMsg.getUploadTime()); // 上传时间
		//packageClearScanEntity.setUploadTime(new Date()); // 单元测试
		return packageClearScanEntity;
	}

	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, PackageClearScanEntity clearScanEntity)
			throws PdaBusiException {
		LOG.info("clear scan :"+clearScanEntity.getLabelCode());
		this.validate(asyncMsg,clearScanEntity);
		try {
			long startTime = System.currentTimeMillis();
			pdaStockcheckingService.scanStTaskPackageDetail(clearScanEntity.getTaskCode(),
					clearScanEntity.getPackageNo(),
					clearScanEntity.getScanStatus(), 
					clearScanEntity.getScanTime(), 
					asyncMsg.getUserCode(), 
					asyncMsg.getPdaCode(),
					clearScanEntity.getPosition());
			
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			LOG.info("[asyncinfo]包清仓扫描接口消耗时间:"+(endTime-startTime)+"ms");
		} catch (BusinessException e) {
			LOG.error("扫描数据异常："+e);
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		//保存扫描数据
		clearDao.savePackageScanMsg(clearScanEntity);
		LOG.info("clear scan success...");
		return null;
	}
	
	
	private void validate(AsyncMsg asyncMsg, PackageClearScanEntity param) {
		Argument.notNull(param, "ClearScanEntity");
		Argument.hasText(param.getTaskCode(), "ClearScanEntity.TaskCode");
		Argument.hasText(param.getPackageNo(), "ClearScanEntity.PackageNo");
		Argument.hasText(param.getScanStatus(), "ClearScanEntity.ScanStatus");
		Argument.hasText(asyncMsg.getOperType(), "AsyncMsg.OperType");
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.PdaCode");
	}

	@Override
	public String getOperType() {
		return ClearConstant.OPER_TYPE_CLEAR_PACAGE_SCAN.VERSION;
	}

	@Override
	public boolean isAsync() {
		return true;
	}

	public void setClearDao(IClearDao clearDao) {
		this.clearDao = clearDao;
	}

	public void setPdaStockcheckingService(
			IPdaStockcheckingService pdaStockcheckingService) {
		this.pdaStockcheckingService = pdaStockcheckingService;
	}

}
