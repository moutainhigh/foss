package com.deppon.pda.bdm.module.foss.unload.server.service.impl.ExceReport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAUnloadDiffReportService;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.UnLoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.unload.server.dao.IUnloadDao;
import com.deppon.pda.bdm.module.foss.unload.server.service.impl.UnldExceReportScanService;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.UnldExceReportScanEntity;

/**
 * 卸车差异报告扫描
 * @author 092038
 *
 */
public class UnloadExceReportScanService implements IBusinessService<Void, UnldExceReportScanEntity>{
	private static final Log LOG = LogFactory.getLog(UnldExceReportScanService.class);
	
	private IUnloadDao unloadDao;
	
    private IPDAUnloadDiffReportService pdaUnloadDiffReportService;

	@Override
	public UnldExceReportScanEntity parseBody(AsyncMsg asyncMsg){
	UnldExceReportScanEntity unldExceReportScanEntity = JsonUtil.parseJsonToObject(UnldExceReportScanEntity.class, asyncMsg.getContent());
	unldExceReportScanEntity.setDeptCode(asyncMsg.getDeptCode()); // 部门
	unldExceReportScanEntity.setPdaCode(asyncMsg.getPdaCode());   // PDA
	unldExceReportScanEntity.setScanUser(asyncMsg.getUserCode()); // user
	unldExceReportScanEntity.setScanType(asyncMsg.getOperType()); // 操作类型
	unldExceReportScanEntity.setId(asyncMsg.getId());			  // ID
	unldExceReportScanEntity.setUploadTime(asyncMsg.getUploadTime()); // 上传时间
	unldExceReportScanEntity.setLabelCode(unldExceReportScanEntity.getSerialNo());
	unldExceReportScanEntity.setScanFlag("1");
	unldExceReportScanEntity.setPieces(1);
	unldExceReportScanEntity.setTaskCode("");
	unldExceReportScanEntity.setScanStatus("1");
	unldExceReportScanEntity.setIsMore("N");
	return unldExceReportScanEntity;
	}

	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, UnldExceReportScanEntity unldExceReportScanEntity)
			throws PdaBusiException {
		LOG.info("clear scan :"+unldExceReportScanEntity.getSerialNo());
		this.validate(asyncMsg,unldExceReportScanEntity);
		try {
			long startTime = System.currentTimeMillis();
			pdaUnloadDiffReportService.handleSCUnloadDiffReport(
					unldExceReportScanEntity.getDeptCode(),
					unldExceReportScanEntity.getReportCode(),
					unldExceReportScanEntity.getWblCode(),
					unldExceReportScanEntity.getSerialNo(),
					unldExceReportScanEntity.getScanTime(),
					unldExceReportScanEntity.getScanUser(),
					unldExceReportScanEntity.getPdaCode()
			        );		
			
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			LOG.info("[asyncinfo]清仓差异报告扫描接口消耗时间:"+(endTime-startTime)+"ms");
		} catch (BusinessException e) {
			LOG.error("扫描数据异常："+e);
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		//保存扫描数据
		unloadDao.saveScanExceReportMsg(unldExceReportScanEntity);
		LOG.info("clear scan success...");
		return null;
	}
    
	private void validate(AsyncMsg asyncMsg, UnldExceReportScanEntity param) {
		Argument.notNull(param, "UnldExceReportScanEntity");
		Argument.hasText(param.getReportCode(), "UnldExceReportScanEntity.reportCode");
		Argument.hasText(param.getWblCode(), "UnldExceReportScanEntity.wblCode");
		Argument.hasText(param.getSerialNo(), "UnldExceReportScanEntity.serialNo");
		Argument.hasText(asyncMsg.getOperType(), "AsyncMsg.OperType");
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.PdaCode");
	}
	@Override
	public String getOperType() {
		return UnLoadConstant.OPER_TYPE_UNLODA_EXCE_SCAN.VERSION;
	}

	@Override
	public boolean isAsync() {
		return true;
	}

	public void setUnloadDao(IUnloadDao unloadDao) {
		this.unloadDao = unloadDao;
	}

	public void setPdaUnloadDiffReportService(
			IPDAUnloadDiffReportService pdaUnloadDiffReportService) {
		this.pdaUnloadDiffReportService = pdaUnloadDiffReportService;
	}
    
}
