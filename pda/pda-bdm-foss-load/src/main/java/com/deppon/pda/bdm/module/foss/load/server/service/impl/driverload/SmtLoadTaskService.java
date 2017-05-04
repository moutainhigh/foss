package com.deppon.pda.bdm.module.foss.load.server.service.impl.driverload;


import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressConnectionService;
import com.deppon.foss.module.transfer.pda.api.shared.exception.TransferPDAExceptionCode;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.load.server.dao.ILoadDao;
import com.deppon.pda.bdm.module.foss.load.shared.domain.driverload.SubmitLoadTaskEntity;
import com.deppon.pda.bdm.module.foss.load.shared.exception.LoadHasNoFnshScanUserException;
/**
 * 提交装车任务
 * @ClassName SmtLoadTaskService.java 
 * @Description 
 * @author 245955
 * @date 2015-4-24
 */
public class SmtLoadTaskService implements IBusinessService<Void, SubmitLoadTaskEntity>{
	private IPDAExpressConnectionService pdaExpressConnectionService;
	private ILoadDao loadDao;
	/**
	 * 解析包体
	 * @description 
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-4-24
	 */
	@Override
	public SubmitLoadTaskEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		SubmitLoadTaskEntity entity=JsonUtil.parseJsonToObject(SubmitLoadTaskEntity.class,asyncMsg.getContent());
		return entity;
	}
	
	/**
	 * 服务方法
	 * @description 
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-4-24
	 */
	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, SubmitLoadTaskEntity param) throws PdaBusiException {
		this.validate(param);
		try {
			if(loadDao.queryNoSyncScanMsgCount(param.getTaskCode())){
				throw new FossInterfaceException(null,"服务器数据正在同步，请耐心等待");
			}
			pdaExpressConnectionService.submitDriverLoadTask(param.getTaskCode(), param.getScanTime(), asyncMsg.getPdaCode(),asyncMsg.getUserCode());
			
		} catch (BusinessException e) {
			if(e.getErrorCode().equals(TransferPDAExceptionCode.EXCEPTION_EXTIST_UNFINISH_PDA_MESSAGECODE)){
				throw new LoadHasNoFnshScanUserException();
			}else{
				throw new FossInterfaceException(e.getCause(),e.getErrorCode());
			}
		}
		return null;
	}
	/**
	 * 参数验证
	 * @description 
	 * @param submitLoadTask
	 * @throws ArgumentInvalidException
	 * @author 245955
	 * @date 2015-4-24
	 */
	private void validate(SubmitLoadTaskEntity submitLoadTask) throws ArgumentInvalidException{
		Argument.notNull(submitLoadTask, "submitLoadTask");
		//任务号非空
		Argument.hasText(submitLoadTask.getTaskCode(), "submitLoadTask.taskCode");
		//扫描时间非空
		Argument.notNull(submitLoadTask.getScanTime(), "submitLoadTask.scanTime");
		//强制提交检验
		Argument.hasText(submitLoadTask.getIsForceSmt(),"submitLoadTask.isForceSmt");
	}
	

	/**
	 * 操作类型
	 * @description 
	 * @return
	 * @author 245955
	 * @date 2015-4-24
	 */
	@Override
	public String getOperType() {
		return LoadConstant.OPER_TYPE_DRIVER_TASK_SUBMIT.VERSION;
	}

	/**
	 * 是否异步
	 * @description 
	 * @return
	 * @author 245955
	 * @date 2015-4-24
	 */
	@Override
	public boolean isAsync() {
		return false;
	}
	
	public void setPdaExpressConnectionService(IPDAExpressConnectionService pdaExpressConnectionService) {
		this.pdaExpressConnectionService = pdaExpressConnectionService;
	}

	public void setLoadDao(ILoadDao loadDao) {
		this.loadDao = loadDao;
	}
}
