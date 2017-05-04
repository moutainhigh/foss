package com.deppon.pda.bdm.module.foss.load.server.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDATransferLoadService;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.load.shared.domain.CancelLoadTask;



/**
 * 撤销装车任务
 * @author gaojia
 * @date Sep 10,2012 14:42:30 PM
 * @version 1.0
 * @since
 */
public class CaclLoadTaskService implements IBusinessService<Object, CancelLoadTask>{
	private IPDATransferLoadService pdaTransferLoadService;
	@Override
	public CancelLoadTask parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		CancelLoadTask model = JsonUtil.parseJsonToObject(CancelLoadTask.class, asyncMsg.getContent());
		model.setPdaCode(asyncMsg.getPdaCode());
		model.setUserCode(asyncMsg.getUserCode());
		return model;
	}
	@Transactional
	@Override
	public Object service(AsyncMsg asyncMsg, CancelLoadTask param)
			throws PdaBusiException {
		//校验数据合法性
		this.validate(param);
		try {
			//撤销装车任务调用FOSS接口
			pdaTransferLoadService.cancelLoadTask(param.getTaskCode(), asyncMsg.getPdaCode(),asyncMsg.getUserCode(), param.getScanTime());
			return null;
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
	}
	private void validate(CancelLoadTask cancelLoadTask) throws ArgumentInvalidException{
		
		Argument.notNull(cancelLoadTask, "cancelLoadTask");
		//任务号非空
		Argument.hasText(cancelLoadTask.getTaskCode(), "cancelLoadTask.taskCode");
		Argument.notNull(cancelLoadTask.getScanTime(), "cancelLoadTask.scanTime");
	}
	@Override
	public String getOperType() {
		return LoadConstant.OPER_TYPE_LOAD_TASK_CACL.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

	public void setPdaTransferLoadService(
			IPDATransferLoadService pdaTransferLoadService) {
		this.pdaTransferLoadService = pdaTransferLoadService;
	}

	
}
