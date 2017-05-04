package com.deppon.pda.bdm.module.foss.load.server.service.impl.tallyload;

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
import com.deppon.pda.bdm.module.foss.load.shared.domain.tallyload.SubmitLoadTaskEntity;
import com.deppon.pda.bdm.module.foss.load.shared.exception.LoadHasNoFnshScanUserException;

/**
 * 提交装车任务
 * 
 * @ClassName SmtLoadTaskService.java
 * @Description
 * @author 245955
 * @date 2015-4-13
 */
public class SubmitTranLoadTaskService implements IBusinessService<Void, SubmitLoadTaskEntity> {
	private ILoadDao loadDao;
	// FOSS接驳装车接口
	private IPDAExpressConnectionService pdaExpressConnectionService;

	/**
	 * 提交接驳装车任务
	 * 
	 * @description
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-4-13
	 */
	@Override
	public SubmitLoadTaskEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		SubmitLoadTaskEntity tSubmitLoadTaskEntity = JsonUtil.parseJsonToObject(SubmitLoadTaskEntity.class, asyncMsg.getContent());
		tSubmitLoadTaskEntity.setPdaCode(asyncMsg.getPdaCode());
		tSubmitLoadTaskEntity.setUserCode(asyncMsg.getUserCode());
		return tSubmitLoadTaskEntity;
	}

	/**
	 * 方法详细描述说明、方法参数的具体涵义
	 * 
	 * @description
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-4-14
	 */
	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, SubmitLoadTaskEntity param) throws PdaBusiException {
		this.validate(param);
		try {
			if(loadDao.queryNoSyncScanMsgCount(param.getTaskCode())){
				throw new FossInterfaceException(null,"服务器数据正在同步，请耐心等待");
			}	
			pdaExpressConnectionService.submitConnectionLoadTask(param.getTaskCode(), param.getScanTime(), asyncMsg.getPdaCode(),asyncMsg.getUserCode());
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
	 * 方法详细描述说明、方法参数的具体涵义
	 * @description 
	 * @param entity
	 * @throws ArgumentInvalidException
	 * @author 245955
	 * @date 2015-4-14
	 */
	private void validate(SubmitLoadTaskEntity entity) throws ArgumentInvalidException {
		Argument.notNull(entity, "entity");
		// 提交员工编号非空
		Argument.hasText(entity.getUserCode(), "entity.userCode");
		// 任务号非空
		Argument.hasText(entity.getTaskCode(), "entity.taskCode");
		// 扫描时间非空
		Argument.notNull(entity.getScanTime(), "entity.scanTime");
		// 强制提交检验
		Argument.hasText(entity.getIsForceSmt(), "entity.isForceSmt");
	}

	/**
	 * 操作类型
	 * 
	 * @description
	 * @return
	 * @author 245955
	 * @date 2015-4-14
	 */
	@Override
	public String getOperType() {
		 return LoadConstant.OPER_TYPE_LOAD_SMT_TRAN.VERSION;
	}

	/**
	 * 是否异步
	 */
	@Override
	public boolean isAsync() {
		return false;
	}

	public void setLoadDao(ILoadDao loadDao) {
		this.loadDao = loadDao;
	}
	
	public void setPdaExpressConnectionService(IPDAExpressConnectionService pdaExpressConnectionService) {
		this.pdaExpressConnectionService = pdaExpressConnectionService;
	}
   
}
