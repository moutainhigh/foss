package com.deppon.pda.bdm.module.foss.clear.server.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPdaDifferReportService;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.ClearConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.clear.server.dao.IClearDao;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.ClearExceReportScanEntity;


/** 
  * @ClassName ClearExceReportScanService 
  * @Description 上传清仓差异报告扫描明细
  * @author 092038 
  * @date 2014-6-9 上午9:13:34 
*/ 
public class ClearExceReportScanService implements IBusinessService<Void, ClearExceReportScanEntity> {
	private static final Log LOG = LogFactory.getLog(ClearExceReportScanService.class);
		
	private IClearDao clearDao;
	
    private IPdaDifferReportService pdaDifferReportService;
	
	@Transactional
	@Override
	public ClearExceReportScanEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
	    ClearExceReportScanEntity clearExceReportScanEntity = JsonUtil.parseJsonToObject(ClearExceReportScanEntity.class, asyncMsg.getContent());
	    clearExceReportScanEntity.setDeptCode(asyncMsg.getDeptCode()); // 部门
	    clearExceReportScanEntity.setPdaCode(asyncMsg.getPdaCode());   // PDA
	    clearExceReportScanEntity.setScanUser(asyncMsg.getUserCode()); // user
	    clearExceReportScanEntity.setScanType(asyncMsg.getOperType()); // 操作类型
	    clearExceReportScanEntity.setId(asyncMsg.getId());			  // ID
	    clearExceReportScanEntity.setUploadTime(asyncMsg.getUploadTime()); // 上传时间
	    clearExceReportScanEntity.setLabelCode(clearExceReportScanEntity.getSerialNo());
	    clearExceReportScanEntity.setScanFlag("1");
	    clearExceReportScanEntity.setPieces(1);
	    clearExceReportScanEntity.setTaskCode("");
	    clearExceReportScanEntity.setScanStatus("1");
	    clearExceReportScanEntity.setIsMore("N");
		return clearExceReportScanEntity;
	}

	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, ClearExceReportScanEntity clearExceReportScanEntity)
			throws PdaBusiException {
		LOG.info("clear scan :"+clearExceReportScanEntity.getSerialNo());
		this.validate(asyncMsg,clearExceReportScanEntity);
		try {
			long startTime = System.currentTimeMillis();
			pdaDifferReportService.handleDifferDetail(
			        clearExceReportScanEntity.getReportCode(),
			        clearExceReportScanEntity.getWblCode(),
			        clearExceReportScanEntity.getSerialNo(),
			        clearExceReportScanEntity.getScanTime(),
			        clearExceReportScanEntity.getScanUser(),
			        clearExceReportScanEntity.getPdaCode()
			        );			
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			LOG.info("[asyncinfo]清仓差异报告扫描接口消耗时间:"+(endTime-startTime)+"ms");
		} catch (BusinessException e) {
			LOG.error("扫描数据异常："+e);
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		//保存扫描数据
		clearDao.saveScanExceReportMsg(clearExceReportScanEntity);
		LOG.info("clear scan success...");
		return null;
	}
	
	
	private void validate(AsyncMsg asyncMsg, ClearExceReportScanEntity param) {
		Argument.notNull(param, "ClearExceReportScanEntity");
		Argument.hasText(param.getReportCode(), "ClearExceReportScanEntity.reportCode");
		Argument.hasText(param.getWblCode(), "ClearExceReportScanEntity.wblCode");
		Argument.hasText(param.getSerialNo(), "ClearExceReportScanEntity.serialNo");
		Argument.hasText(asyncMsg.getOperType(), "AsyncMsg.OperType");
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.PdaCode");
	}

	@Override
	public String getOperType() {
		return ClearConstant.OPER_TYPE_CLEAR_EXCE_SCAN.VERSION;
	}

	@Override
	public boolean isAsync() {
		return true;
	}

	public void setClearDao(IClearDao clearDao) {
		this.clearDao = clearDao;
	}

    public void setPdaDifferReportService(IPdaDifferReportService pdaDifferReportService) {
        this.pdaDifferReportService = pdaDifferReportService;
    }


}
