package com.deppon.pda.bdm.module.foss.unload.server.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAUnloadTaskService;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.UnLoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.CancelUnldTask;
/**
 * 撤销卸车任务service实现类
 * @author gaojia
 * @date Sep 7,2012 9:48:30 AM
 * @version 1.0
 * @since
 */
public class CaclUnldTaskService implements IBusinessService<Void, CancelUnldTask >{
	private IPDAUnloadTaskService pdaUnloadTaskService;
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:33:19
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public CancelUnldTask parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		CancelUnldTask model = new CancelUnldTask();
		model = JsonUtil.parseJsonToObject(CancelUnldTask.class,asyncMsg.getContent());
		model.setPdaCode(asyncMsg.getPdaCode());
		model.setUserCode(asyncMsg.getUserCode());
		return model;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:33:23
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, CancelUnldTask param)
			throws PdaBusiException {
		this.validate(param);
		try {
			pdaUnloadTaskService.cancelUnloadTask(param.getTaskCode(), asyncMsg.getPdaCode(), asyncMsg.getUserCode(), param.getScanTime());
			return null;
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		
	}

	@Override
	public String getOperType() {
		return UnLoadConstant.OPER_TYPE_UNLD_TASK_CANCEL.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}
	
	public void setPdaUnloadTaskService(IPDAUnloadTaskService pdaUnloadTaskService) {
		this.pdaUnloadTaskService = pdaUnloadTaskService;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:33:30
	 * @param cancelUnldTask
	 * @throws ArgumentInvalidException
	 * @see
	 */
	private void validate(CancelUnldTask cancelUnldTask) throws ArgumentInvalidException{
		//检验撤销任务非空
		Argument.notNull(cancelUnldTask, "cancelUnldTask");
		//检验任务号非空
		Argument.hasText(cancelUnldTask.getTaskCode(),"cancelUnldTask.taskCode");
		//检验扫描时间非空
		Argument.notNull(cancelUnldTask.getScanTime(), "cancelUnldTask.scanTime");
	}
	
}
