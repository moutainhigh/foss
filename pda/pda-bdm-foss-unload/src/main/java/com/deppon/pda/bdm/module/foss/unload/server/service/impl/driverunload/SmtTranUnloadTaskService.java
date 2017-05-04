package com.deppon.pda.bdm.module.foss.unload.server.service.impl.driverunload;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.shared.exception.TransferPDAExceptionCode;
import com.deppon.foss.module.transfer.unload.api.server.service.ISCPDAUnloadTaskService;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.UnLoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.unload.server.dao.IUnloadDao;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.driverunload.SubmitTranUnloadEntity;
import com.deppon.pda.bdm.module.foss.unload.shared.exception.UnldHasNoFnshScanUserException;

/**
 * 提交接驳卸车任务
 * @ClassName SmtTranUnloadTaskService.java 
 * @Description 
 * @author 245955
 * @date 2015-4-15
 */
public class SmtTranUnloadTaskService implements IBusinessService<Object, SubmitTranUnloadEntity> {
	private ISCPDAUnloadTaskService scpdaUnloadTaskService;
	private IUnloadDao unloadDao;
	/**
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p>
	 * @description 
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-4-15
	 */
	@Override
	public SubmitTranUnloadEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		SubmitTranUnloadEntity submitTranUnloadEntity=JsonUtil.parseJsonToObject(SubmitTranUnloadEntity.class, asyncMsg.getContent());
		submitTranUnloadEntity.setPdaCode(asyncMsg.getPdaCode());
		submitTranUnloadEntity.setUserCode(asyncMsg.getUserCode());
		return submitTranUnloadEntity;
	}

	/**
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p>
	 * @description 
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-4-15
	 */
	@Transactional
	@Override
	public Object service(AsyncMsg asyncMsg, SubmitTranUnloadEntity param) throws PdaBusiException {
		this.validate(param);
		try {
			if(unloadDao.queryNoSyncScanMsgCount(param.getTaskCode())){
				throw new FossInterfaceException(null,"服务器数据正在同步，请耐心等待");
			}
			scpdaUnloadTaskService.finishSCUnloadTask(param.getTaskCode(), param.getScanTime(), asyncMsg.getPdaCode(), asyncMsg.getUserCode(), null, null);
		} catch (BusinessException e) {
			if(e.getErrorCode().equals(TransferPDAExceptionCode.EXCEPTION_EXTIST_UNFINISH_PDA_MESSAGECODE)){
				throw new UnldHasNoFnshScanUserException();
			}else{
				throw new FossInterfaceException(e.getCause(),e.getErrorCode());
			}
		}
		return null;
	}

	/**
	 * 操作类型
	 * @description 
	 * @return
	 * @author 245955
	 * @date 2015-4-15
	 */
	@Override
	public String getOperType() {
		return UnLoadConstant.OPER_TYPE_UNLOAD_TRAN_SUBMIT.VERSION;
	}
   
	/**
	 * 是否异步
	 * @description 
	 * @return
	 * @author 245955
	 * @date 2015-4-15
	 */
	@Override
	public boolean isAsync() {
		return false;
	}
	/**
	 * 参数验证
	 * @description 
	 * @param submitUnldTask
	 * @throws ArgumentInvalidException
	 * @author 245955
	 * @date 2015-4-15
	 */
	private void validate(SubmitTranUnloadEntity submitTranUnloadEntity) throws ArgumentInvalidException{
		//完成任务非空
		Argument.notNull(submitTranUnloadEntity, "submitTranUnloadEntity");
		//任务号非空
		Argument.hasText(submitTranUnloadEntity.getTaskCode(), "submitTranUnloadEntity.taskCode");
		//完成时间非空
		Argument.notNull(submitTranUnloadEntity.getScanTime(), "submitTranUnloadEntity.scanTime");
		//强制提交检验
		//Argument.hasText(submitTranUnloadEntity.getIsForceSmt(),"submitTranUnloadEntity.isForceSmt");
	}
   
	public void setUnloadDao(IUnloadDao unloadDao) {
		this.unloadDao = unloadDao;
	}

	public void setScpdaUnloadTaskService(ISCPDAUnloadTaskService scpdaUnloadTaskService) {
		this.scpdaUnloadTaskService = scpdaUnloadTaskService;
	}


}
