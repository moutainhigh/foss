package com.deppon.pda.bdm.module.foss.unload.server.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAUnloadTaskService;
import com.deppon.foss.module.transfer.pda.api.shared.exception.TransferPDAExceptionCode;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.UnLoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.unload.server.dao.IUnloadDao;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.SubmitUnldTask;
import com.deppon.pda.bdm.module.foss.unload.shared.exception.UnldHasNoFnshScanUserException;
/**
 * 提交卸车任务service实现类
 * @author gaojia
 * @date Sep 7,2012 15:48:30 AM
 * @version 1.0
 * @since
 */
public class SmtUnldTaskService implements IBusinessService<Object, SubmitUnldTask>{
	private IPDAUnloadTaskService pdaUnloadTaskService;
	private IUnloadDao unloadDao;
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:35:26
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public SubmitUnldTask parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		SubmitUnldTask model = new SubmitUnldTask();
		model = JsonUtil.parseJsonToObject(SubmitUnldTask.class, asyncMsg.getContent());
		model.setPdaCode(asyncMsg.getPdaCode());
		model.setUserCode(asyncMsg.getUserCode());
		return model;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:35:30
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Transactional
	@Override
	public Object service(AsyncMsg asyncMsg, SubmitUnldTask param)
			throws PdaBusiException {
		this.validate(param);
		try {
			if(unloadDao.queryNoSyncScanMsgCount(param.getTaskCode())){
				throw new FossInterfaceException(null,"服务器数据正在同步，请耐心等待");
			}
			if(param.getIsForceSmt().equals("1")){
				pdaUnloadTaskService.forceSubmitUnloadTask(param.getTaskCode(), param.getScanTime(), asyncMsg.getPdaCode(), asyncMsg.getUserCode(), null, null);
			}else{
				pdaUnloadTaskService.submitUnloadTask(param.getTaskCode(), param.getScanTime(), asyncMsg.getPdaCode(), asyncMsg.getUserCode(), null, null);
			}
		} catch (BusinessException e) {
			if(e.getErrorCode().equals(TransferPDAExceptionCode.EXCEPTION_EXTIST_UNFINISH_PDA_MESSAGECODE)){
				throw new UnldHasNoFnshScanUserException();
			}else{
				throw new FossInterfaceException(e.getCause(),e.getErrorCode());
			}
		}
		return null;
	}

	@Override
	public String getOperType() {
		// TODO Auto-generated method stub
		return UnLoadConstant.OPER_TYPE_UNLD_TASK_SUBMIT.VERSION;
	}

	@Override
	public boolean isAsync() {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:35:36
	 * @param submitUnldTask
	 * @throws ArgumentInvalidException
	 * @see
	 */
	private void validate(SubmitUnldTask submitUnldTask) throws ArgumentInvalidException{
		//完成任务非空
		Argument.notNull(submitUnldTask, "submitUnldTask");
		//任务号非空
		Argument.hasText(submitUnldTask.getTaskCode(), "submitUnldTask.taskCode");
		//完成时间非空
		Argument.notNull(submitUnldTask.getScanTime(), "submitUnldTask.scanTime");
		//强制提交检验
		Argument.hasText(submitUnldTask.getIsForceSmt(),"submitUnldTask.isForceSmt");
		//员工列表非空
		//Argument.notEmpty(submitUnldTask.getUserCodes(),"submitUnldTask.userCodes");
	}

	public void setPdaUnloadTaskService(IPDAUnloadTaskService pdaUnloadTaskService) {
		this.pdaUnloadTaskService = pdaUnloadTaskService;
	}
	public void setUnloadDao(IUnloadDao unloadDao) {
		this.unloadDao = unloadDao;
	}
	
}
