package com.deppon.pda.bdm.module.foss.load.server.service.impl;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

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
/**
 * 完成装车任务
 * @author gaojia
 * @date Sep 10,2012 14:42:30 PM
 * @version 1.0
 * @since
 */
public class FnshLoadTaskService  implements IBusinessService<Void, FnshLoadTaskScanEntity>{
	private ILoadDao loadDao;
	private IPDATransferLoadService pdaTransferLoadService;
	private Logger log = Logger.getLogger(getClass());
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:14:32
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public FnshLoadTaskScanEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		FnshLoadTaskScanEntity entity = JsonUtil.parseJsonToObject(FnshLoadTaskScanEntity.class, asyncMsg.getContent());
		entity.setDeptCode(asyncMsg.getDeptCode());
		entity.setPdaCode(asyncMsg.getPdaCode());
		entity.setScanUser(asyncMsg.getUserCode());
		entity.setScanType(asyncMsg.getOperType());
		entity.setUploadTime(asyncMsg.getUploadTime());
		return entity;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:14:38
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, FnshLoadTaskScanEntity param)
			throws PdaBusiException {
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
			log.info("[asyncinfo]完成装车任务接口消耗时间:"+(endTime-startTime)+"ms");
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		return null;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:14:44
	 * @param entity
	 * @throws ArgumentInvalidException
	 * @see
	 */
	private void validate(FnshLoadTaskScanEntity entity) throws ArgumentInvalidException{
		Argument.notNull(entity,"fnshLoadTaskScanEntity");
		//任务号非空
		Argument.hasText(entity.getTaskCode(), "fnshLoadTaskScanEntity.taskCode");
		//完成时间非空
		Argument.notNull(entity.getScanTime(), "fnshLoadTaskScanEntity.scanTime");
	}
	@Override
	public String getOperType() {
		return LoadConstant.OPER_TYPE_LOAD_FINISH.VERSION;
	}

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
