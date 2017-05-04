package com.deppon.pda.bdm.module.foss.load.server.service.impl.driverload;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressConnectionService;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.load.shared.domain.driverload.CancelLoadTask;

/**
 * 司机撤销装车任务
 * @author 245955
 *
 */
public class DeleteDriverTaskService implements IBusinessService<Object,CancelLoadTask> {
	private IPDAExpressConnectionService  pdaExpressConnectionService;
	
	/**
	 * 解析包头
	 */
	@Override
	public CancelLoadTask parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		CancelLoadTask model = JsonUtil.parseJsonToObject(CancelLoadTask.class, asyncMsg.getContent());
		model.setUserCode(asyncMsg.getUserCode());
		return model;
	}

	/**
	 * 删除任务Service
	 */
	@Override
	public Object service(AsyncMsg asyncMsg, CancelLoadTask param)
			throws PdaBusiException {
		    //校验数据合法性
			this.validate(param);
			try {
				//撤销装车任务调用FOSS接口
				pdaExpressConnectionService.cancelDriverLoadTask(param.getTaskCode());
				return null;
			  } catch (BusinessException e) {
				throw new FossInterfaceException(e.getCause(),e.getErrorCode());
			}
	}

	/**
	 * 操作类型
	 */
	@Override
	public String getOperType() {
		return LoadConstant.OPER_TYPE_DRIVER_TASK_DELETE.VERSION;
	}

	/**
	 * 是否异步
	 */
	@Override
	public boolean isAsync() {
		return false;
	}

	/**
	 * 参数验证
	 * @param cancelLoadTask
	 * @throws ArgumentInvalidException
	 */
	 private void validate(CancelLoadTask cancelLoadTask) throws ArgumentInvalidException{		
			Argument.notNull(cancelLoadTask, "cancelLoadTask");
			//任务号非空
			Argument.hasText(cancelLoadTask.getTaskCode(), "cancelLoadTask.taskCode");
		}

	public void setPdaExpressConnectionService(
			IPDAExpressConnectionService pdaExpressConnectionService) {
		this.pdaExpressConnectionService = pdaExpressConnectionService;
	}

}
