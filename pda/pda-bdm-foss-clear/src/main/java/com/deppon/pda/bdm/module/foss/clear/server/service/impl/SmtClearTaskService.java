package com.deppon.pda.bdm.module.foss.clear.server.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.common.api.shared.exception.StTaskSubmitException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPdaStockcheckingService;
import com.deppon.foss.module.transfer.pda.api.shared.exception.TransferPDAExceptionCode;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.ClearConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.clear.server.dao.IClearDao;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.SubmitClearTask;
import com.deppon.pda.bdm.module.foss.clear.shared.exception.ClearSubStateException;
import com.deppon.pda.bdm.module.foss.clear.shared.exception.UnfinishedTaskException;

/**
 * 提交清仓任务业务类
 * 
 * @author xujun
 * @version 1.0
 * @created 2012-9-11 上午09:33:57
 */
public class SmtClearTaskService implements IBusinessService<Void, SubmitClearTask> {
	private static final Log LOG = LogFactory.getLog(SmtClearTaskService.class);
	
	private IPdaStockcheckingService pdaStockcheckingService;
	private IClearDao clearDao;
	@Override
	public SubmitClearTask parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		SubmitClearTask clearTask = JsonUtil.parseJsonToObject(SubmitClearTask.class, asyncMsg.getContent());
		return clearTask;
	}

	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, SubmitClearTask param)
			throws PdaBusiException {
		LOG.info("submit cleartask start...");
		this.validate(asyncMsg,param);
		
		try {
			if(clearDao.queryNoSyncScanMsgCount(param.getTaskCode())){
				throw new FossInterfaceException(null,"服务器数据正在同步，请耐心等待");
			}
			if("1".equals(param.getIsForceSmt())){
				pdaStockcheckingService.enforceSubmitPdaStTask(param.getTaskCode(), asyncMsg.getPdaCode(), param.getSmtTime());
			}else{
				pdaStockcheckingService.submitPdaStTask(param.getTaskCode(), asyncMsg.getPdaCode(), param.getSmtTime());
			}
			
		}catch (StTaskSubmitException e){
			throw new ClearSubStateException(e.getNuminfo());
		} catch (BusinessException e) {
			if(e.getErrorCode().equals(TransferPDAExceptionCode.EXCEPTION_STTASK_BRANCH_UNFINISHED)){
				throw new UnfinishedTaskException();
			}else{
				throw new FossInterfaceException(e.getCause(),e.getErrorCode());
			}
		}
		LOG.info("submit success!");
		return null;
	}
	
	private void validate(AsyncMsg asyncMsg, SubmitClearTask param) {
		Argument.notNull(param, "SubmitClearTask");
		Argument.hasText(param.getTaskCode(), "SubmitClearTask.TaskCode");
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.PdaCode");
		//Argument.notNull(param.getIsForceSmt(), "submitClearTask.isForceSmt");
	}

	@Override
	public String getOperType() {
		return ClearConstant.OPER_TYPE_CLEAR_TASK_SUBMIT.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

	public void setPdaStockcheckingService(
			IPdaStockcheckingService pdaStockcheckingService) {
		this.pdaStockcheckingService = pdaStockcheckingService;
	}

	public void setClearDao(IClearDao clearDao) {
		this.clearDao = clearDao;
	}
	
}
