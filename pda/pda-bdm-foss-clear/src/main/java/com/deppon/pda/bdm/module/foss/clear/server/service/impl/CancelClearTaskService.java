package com.deppon.pda.bdm.module.foss.clear.server.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPdaStockcheckingService;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.ClearConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.clear.shared.domain.CancelClearTask;

/**
 * 撤销清仓任务业务类
 * 
 * @author 徐俊
 * @version 1.0
 * @created 2012-9-11 上午09:36:58
 */
public class CancelClearTaskService implements IBusinessService<Void, CancelClearTask> {
	private static final Log LOG = LogFactory.getLog(SmtClearTaskService.class);

	private IPdaStockcheckingService pdaStockcheckingService;
	
	@Override
	public CancelClearTask parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		CancelClearTask cancelClearTask = JsonUtil.parseJsonToObject(CancelClearTask.class, asyncMsg.getContent());
		return cancelClearTask;
	}

	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, CancelClearTask param)
			throws PdaBusiException {
		LOG.debug("start cancel task ...");
		this.validateBusinessData(param);
		try {
			pdaStockcheckingService.cancelPdaStTask(param.getTaskCode(), 
					asyncMsg.getPdaCode(), 
					param.getCancelTime(), 
					asyncMsg.getUserCode());
		} catch (BusinessException e) {
			LOG.error("撤销清仓任务异常"+e);
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		LOG.debug("cancel success!");
		return null;
	}

	@Override
	public String getOperType() {
		return ClearConstant.OPER_TYPE_CLEAR_TASK_CANCEL.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

	private void validateBusinessData(CancelClearTask param) {
		Argument.notNull(param, "CancelClearTask");
		Argument.hasText(param.getTaskCode(), "CancelClearTask.taskCode");
		Argument.notNull(param.getCancelTime(), "CancelClearTask.cancelTime");
	}

	public void setPdaStockcheckingService(
			IPdaStockcheckingService pdaStockcheckingService) {
		this.pdaStockcheckingService = pdaStockcheckingService;
	}
	

}
