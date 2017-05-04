package com.deppon.pda.bdm.module.foss.unload.server.service.impl;

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
import com.deppon.pda.bdm.module.foss.unload.shared.domain.QryUnldExceReportEntity;


/** 
  * @ClassName SmtClearExceReportService 
  * @Description  提交清仓差异报告
  * @author 092038 
  * @date 2014-6-9 上午9:48:01 
*/ 
public class SmtUnldExceReportService implements IBusinessService<Void, QryUnldExceReportEntity> {
	private static final Log LOG = LogFactory.getLog(SmtUnldExceReportService.class);
	
    private IPDAUnloadDiffReportService pdaUnloadDiffReportService;
	
	@Override
	public QryUnldExceReportEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
	    QryUnldExceReportEntity clearExceReport = JsonUtil.parseJsonToObject(QryUnldExceReportEntity.class, asyncMsg.getContent());
		return clearExceReport;
	}

	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, QryUnldExceReportEntity param)
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

	private void validate(AsyncMsg asyncMsg, QryUnldExceReportEntity param) {
		Argument.notNull(param, "QryUnldExceReportEntity");
		Argument.hasText(param.getReportCode(), "QryUnldExceReportEntity.reportCode");
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.PdaCode");
		//Argument.notNull(param.getIsForceSmt(), "submitClearTask.isForceSmt");
	}

	@Override
	public String getOperType() {
		return UnLoadConstant.OPER_TYPE_UNLD_EXCE_SUBMIT.VERSION;
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
