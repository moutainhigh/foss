package com.deppon.pda.bdm.module.foss.clear.server.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPdaDifferReportService;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.ClearConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.QryClearExceReportEntity;


/** 
  * @ClassName SmtClearExceReportService 
  * @Description  提交清仓差异报告
  * @author 092038 
  * @date 2014-6-9 上午9:48:01 
*/ 
public class SmtClearExceReportService implements IBusinessService<Void, QryClearExceReportEntity> {
	private static final Log LOG = LogFactory.getLog(SmtClearExceReportService.class);
	
    private IPdaDifferReportService pdaDifferReportService;
	
	@Override
	public QryClearExceReportEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
	    QryClearExceReportEntity clearExceReport = JsonUtil.parseJsonToObject(QryClearExceReportEntity.class, asyncMsg.getContent());
		return clearExceReport;
	}

	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, QryClearExceReportEntity param)
			throws PdaBusiException {
		LOG.info("submit clearExceReprot start...");
		this.validate(asyncMsg,param);
		
		try {

		    pdaDifferReportService.commitHandleTask(
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

	private void validate(AsyncMsg asyncMsg, QryClearExceReportEntity param) {
		Argument.notNull(param, "QryClearExceReportEntity");
		Argument.hasText(param.getReportCode(), "QryClearExceReportEntity.reportCode");
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.PdaCode");
		//Argument.notNull(param.getIsForceSmt(), "submitClearTask.isForceSmt");
	}

	@Override
	public String getOperType() {
		return ClearConstant.OPER_TYPE_CLEAR_EXCE_SUBMIT.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

    public void setPdaDifferReportService(IPdaDifferReportService pdaDifferReportService) {
        this.pdaDifferReportService = pdaDifferReportService;
    }


	
}
