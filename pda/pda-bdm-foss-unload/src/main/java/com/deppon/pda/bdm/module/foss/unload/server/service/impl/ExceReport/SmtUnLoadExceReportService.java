package com.deppon.pda.bdm.module.foss.unload.server.service.impl.ExceReport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAUnloadDiffReportService;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.UnLoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.unload.server.service.impl.SmtUnldExceReportService;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.ExceReport.UnldExceReportEntity;

/**
 * 提交卸车差异报告
 * @author 092038
 *
 */
@Transactional
public class SmtUnLoadExceReportService implements IBusinessService<Void, UnldExceReportEntity>{
	private static final Log LOG = LogFactory.getLog(SmtUnldExceReportService.class);
	
    private IPDAUnloadDiffReportService pdaUnloadDiffReportService;

	@Override
	public UnldExceReportEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		UnldExceReportEntity clearExceReport = JsonUtil.parseJsonToObject(UnldExceReportEntity.class, asyncMsg.getContent());
		return clearExceReport;
	}

	@Override
	public Void service(AsyncMsg asyncMsg, UnldExceReportEntity param)
			throws PdaBusiException {
		LOG.info("submit clearExceReprot start...");
		this.validate(asyncMsg,param);
		try {
			pdaUnloadDiffReportService.commitUnloadDiffReport(
		            param.getReportCode(),
		            asyncMsg.getUserCode(),
		            asyncMsg.getPdaCode()
		            );
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		LOG.info("submit success!");
		return null;
	}
    
	private void validate(AsyncMsg asyncMsg, UnldExceReportEntity param) {
		Argument.notNull(param, "QryUnldExceReportEntity");
		Argument.hasText(param.getReportCode(), "QryUnldExceReportEntity.reportCode");
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.PdaCode");
	    //Argument.notNull(param.getIsForceSmt(), "submitClearTask.isForceSmt");
	}
	@Override
	public String getOperType() {
		return UnLoadConstant.OPER_TYPE_UNLODA_EXCE_SUBMIT.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

	public void setPdaUnloadDiffReportService(
			IPDAUnloadDiffReportService pdaUnloadDiffReportService) {
		this.pdaUnloadDiffReportService = pdaUnloadDiffReportService;
	}
    
}
