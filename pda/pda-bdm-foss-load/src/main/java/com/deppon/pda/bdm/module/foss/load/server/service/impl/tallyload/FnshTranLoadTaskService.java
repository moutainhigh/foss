package com.deppon.pda.bdm.module.foss.load.server.service.impl.tallyload;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDATransferLoadService;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.load.server.dao.ILoadDao;
import com.deppon.pda.bdm.module.foss.load.shared.domain.FnshLoadTaskScanEntity;

/*
 * 完成接驳装车任务
 */
public class FnshTranLoadTaskService implements IBusinessService<Void, FnshLoadTaskScanEntity> {
	private ILoadDao loadDao;
	private IPDATransferLoadService pdaTransferLoadService;
	private Logger log = Logger.getLogger(getClass());
	/**
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @description 
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-4-14
	 */
	@Override
	public FnshLoadTaskScanEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		FnshLoadTaskScanEntity tranFnshLoadTaskEntity=JsonUtil.parseJsonToObject(FnshLoadTaskScanEntity.class, asyncMsg.getContent());
		tranFnshLoadTaskEntity.setDeptCode(asyncMsg.getDeptCode());
		tranFnshLoadTaskEntity.setPdaCode(asyncMsg.getPdaCode());
		tranFnshLoadTaskEntity.setScanUser(asyncMsg.getUserCode());
		tranFnshLoadTaskEntity.setScanType(asyncMsg.getOperType());
		tranFnshLoadTaskEntity.setUploadTime(asyncMsg.getUploadTime());
		return tranFnshLoadTaskEntity;
	}
	/**
	 * 服务方法
	 * @description 
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-4-14
	 */
	@Override
	public Void service(AsyncMsg asyncMsg, FnshLoadTaskScanEntity param) throws PdaBusiException {
		this.validate(param);
		param.setSyncStatus(Constant.SYNC_STATUS_INIT);
		//保存完成任务信息
		loadDao.saveFnshLoadTaskScan(param);
		try {
			long startTime = System.currentTimeMillis();
			//调用FOSS接口
			pdaTransferLoadService.finishLoadTask(param.getTaskCode(), param.getScanTime(), asyncMsg.getPdaCode(),asyncMsg.getUserCode());
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			log.info("[asyncinfo]完成接驳装车任务接口消耗时间:"+(endTime-startTime)+"ms");
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
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
	private void validate(FnshLoadTaskScanEntity entity) throws ArgumentInvalidException{
		Argument.notNull(entity,"entity");
		//任务号非空
		Argument.hasText(entity.getTaskCode(), "entity.taskCode");
		//完成时间非空
		Argument.notNull(entity.getScanTime(), "entity.scanTime");
	}
	/**
	 * 操作类型
	 * @description 
	 * @return
	 * @author 245955
	 * @date 2015-4-14
	 */
	@Override
	public String getOperType() {
		return LoadConstant.OPER_TYPE_LOAD_TRAN_FNSH.VERSION;
	}
	/**
	 * 是否异步
	 * @description 
	 * @return
	 * @author 245955
	 * @date 2015-4-14
	 */
	@Override
	public boolean isAsync() {
		return true;
	}
	public void setLoadDao(ILoadDao loadDao) {
		this.loadDao = loadDao;
	}

	 public void setPdaTransferLoadService(
				IPDATransferLoadService pdaTransferLoadService) {
			this.pdaTransferLoadService = pdaTransferLoadService;
	}
}
