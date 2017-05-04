package com.deppon.pda.bdm.module.foss.load.server.service.impl.tallyload;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressDeliverLoadService;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.load.shared.domain.tallyload.CancelLoadTask;

/**
 * 删除装车任务
 * @ClassName CaclLoadTaskService.java 
 * @Description 
 * @author 245955
 * @date 2015-4-27
 */
public class DeleteLoadTaskService implements IBusinessService<Object, CancelLoadTask> {
	
	private IPDAExpressDeliverLoadService pdaExpressDeliverLoadService;
	
	/**
	 * 解析包体
	 * @description 
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-4-27
	 */
	@Override
	public CancelLoadTask parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		CancelLoadTask model = JsonUtil.parseJsonToObject(CancelLoadTask.class, asyncMsg.getContent());
		model.setPdaCode(asyncMsg.getPdaCode());
		model.setUserCode(asyncMsg.getUserCode());
		return model;
	}

	/**
	 * 服务方法
	 * @description 
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-4-27
	 */
	@Override
	public Object service(AsyncMsg asyncMsg, CancelLoadTask param) throws PdaBusiException {
		//校验数据合法性
		this.validate(param);
		try {
			//撤销装车任务调用FOSS接口
			pdaExpressDeliverLoadService.cancelLoadTask(param.getTaskCode(), asyncMsg.getPdaCode(),asyncMsg.getUserCode(), param.getScanTime());
			return null;
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
	}

	/**
	 * 操作类型
	 * @description 
	 * @return
	 * @author 245955
	 * @date 2015-4-27
	 */
	@Override
	public String getOperType() {
		 return LoadConstant.OPER_TYPE_LOAD_SANCEL_TRAN.VERSION;
	}
	
	/**
	 * 是否异步
	 * @description 
	 * @return
	 * @author 245955
	 * @date 2015-4-27
	 */
	@Override
	public boolean isAsync() {
		return false;
	}
	/**
	 * 参数验证
	 * @description 
	 * @param cancelLoadTask
	 * @throws ArgumentInvalidException
	 * @author 245955
	 * @date 2015-4-27
	 */
   private void validate(CancelLoadTask cancelLoadTask) throws ArgumentInvalidException{		
		Argument.notNull(cancelLoadTask, "cancelLoadTask");
		//任务号非空
		Argument.hasText(cancelLoadTask.getTaskCode(), "cancelLoadTask.taskCode");
		Argument.notNull(cancelLoadTask.getScanTime(), "cancelLoadTask.scanTime");
	}

	public void setPdaExpressDeliverLoadService(IPDAExpressDeliverLoadService pdaExpressDeliverLoadService) {
		this.pdaExpressDeliverLoadService = pdaExpressDeliverLoadService;
	}
   
}
