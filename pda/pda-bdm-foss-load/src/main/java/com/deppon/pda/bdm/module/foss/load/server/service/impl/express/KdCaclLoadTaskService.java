package com.deppon.pda.bdm.module.foss.load.server.service.impl.express;

import org.springframework.transaction.annotation.Transactional;

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
import com.deppon.pda.bdm.module.foss.load.server.dao.ILoadDao;
import com.deppon.pda.bdm.module.foss.load.shared.domain.KdCancelLoadTask;



/**
 * 撤销快递派送装车任务
 * @author 
 * @date 2013年7月19日15:55:53
 * @version 1.0
 * @since
 */
public class KdCaclLoadTaskService implements IBusinessService<Object, KdCancelLoadTask>{
	private IPDAExpressDeliverLoadService pdaExpressDeliverLoadService;
	private ILoadDao loadDao;
	@Override
	public KdCancelLoadTask parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		KdCancelLoadTask model = JsonUtil.parseJsonToObject(KdCancelLoadTask.class, asyncMsg.getContent());
		model.setPdaCode(asyncMsg.getPdaCode());
		model.setUserCode(asyncMsg.getUserCode());
		return model;
	}
	@Transactional
	@Override
	public Object service(AsyncMsg asyncMsg, KdCancelLoadTask param)
			throws PdaBusiException {
		//校验数据合法性
		this.validate(param);
		try {
			//撤销装车任务调用FOSS接口
			pdaExpressDeliverLoadService.cancelLoadTask(asyncMsg.getTaskCode(),asyncMsg.getPdaCode(),param.getUserCode(),  param.getScanTime());
			loadDao.deleteKdScanBusinessErrorLog(asyncMsg.getTaskCode());
			return null;
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
	}
	private void validate(KdCancelLoadTask kdCancelLoadTask) throws ArgumentInvalidException{
		
		Argument.notNull(kdCancelLoadTask, "kdCancelLoadTask");
		//任务号非空
		Argument.hasText(kdCancelLoadTask.getTaskCode(), "kdCancelLoadTask.taskCode");
		Argument.notNull(kdCancelLoadTask.getScanTime(), "kdCancelLoadTask.scanTime");
	}
	@Override
	public String getOperType() {
		return LoadConstant.OPER_TYPE_KD_LOAD_TASK_CACL.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

	public void setPdaExpressDeliverLoadService(
			IPDAExpressDeliverLoadService pdaExpressDeliverLoadService) {
		this.pdaExpressDeliverLoadService = pdaExpressDeliverLoadService;
	}
	public void setLoadDao(ILoadDao loadDao) {
		this.loadDao = loadDao;
	}
}
