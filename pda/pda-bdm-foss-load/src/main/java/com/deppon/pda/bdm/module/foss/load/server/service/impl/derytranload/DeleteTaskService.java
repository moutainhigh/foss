package com.deppon.pda.bdm.module.foss.load.server.service.impl.derytranload;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressSendPieceService;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.load.shared.domain.derytranload.DeryCancelLoadTask;

/**
 * 撤销派件任务
 * @author 092038
 *
 */
public class DeleteTaskService implements IBusinessService<Object,DeryCancelLoadTask>{
	private IPDAExpressSendPieceService pdaExpressSendPieceService;

	@Override
	public DeryCancelLoadTask parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		DeryCancelLoadTask model = JsonUtil.parseJsonToObject(DeryCancelLoadTask.class, asyncMsg.getContent());
		model.setPdaCode(asyncMsg.getPdaCode());
		model.setUserCode(asyncMsg.getUserCode());
		return model;
	}

	@Override
	public Object service(AsyncMsg asyncMsg, DeryCancelLoadTask param)
	{
	   //校验数据合法性
		this.validate(param);
		try {
			//撤销装车任务调用FOSS接口
			pdaExpressSendPieceService.cancelLoadTask(param.getTaskCode());
			return null;
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
	 }

	private void validate(DeryCancelLoadTask cancelLoadTask) throws ArgumentInvalidException{
		Argument.notNull(cancelLoadTask, "cancelLoadTask");
		//任务号非空
		Argument.hasText(cancelLoadTask.getTaskCode(), "cancelLoadTask.taskCode");
	}

	@Override
	public String getOperType() {
		return LoadConstant.OPER_TYPE_DELIVERY_TASK_DELETE.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}
	public void setPdaExpressSendPieceService(
			IPDAExpressSendPieceService pdaExpressSendPieceService) {
		this.pdaExpressSendPieceService = pdaExpressSendPieceService;
	}
}
