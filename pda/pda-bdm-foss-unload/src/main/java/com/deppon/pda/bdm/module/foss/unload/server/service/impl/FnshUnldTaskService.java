package com.deppon.pda.bdm.module.foss.unload.server.service.impl;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.Argument;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAUnloadTaskService;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.UnLoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.unload.server.dao.IUnloadDao;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.FnshUnldTaskScanEntity;

public class FnshUnldTaskService implements IBusinessService<Void, FnshUnldTaskScanEntity>{
	private IUnloadDao unloadDao;
	private IPDAUnloadTaskService pdaUnloadTaskService;
	private Logger log = Logger.getLogger(getClass());
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:34:00
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public FnshUnldTaskScanEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		FnshUnldTaskScanEntity entity = new FnshUnldTaskScanEntity();
		entity = JsonUtil.parseJsonToObject(FnshUnldTaskScanEntity.class, asyncMsg.getContent());
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
	 * @date 2013-3-20 下午6:34:04
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, FnshUnldTaskScanEntity param)
			throws PdaBusiException {
		this.validate(param);
		unloadDao.saveFnshUnldScan(param);
		try {
			long startTime = System.currentTimeMillis();
			pdaUnloadTaskService.finishUnloadTask(param.getTaskCode(), param.getScanTime(), asyncMsg.getPdaCode());
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			log.info("[asyncinfo]完成卸车任务接口消耗时间:"+(endTime-startTime)+"ms");
			return null;
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		
		
	}
	@Override
	public String getOperType() {
		return UnLoadConstant.OPER_TYPE_UNLD_TASK_FINISH.VERSION;
	}
	@Override
	public boolean isAsync() {
		return true;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:34:10
	 * @param entity
	 * @throws ArgumentInvalidException
	 * @see
	 */
	private void validate(FnshUnldTaskScanEntity entity) throws ArgumentInvalidException{
		//检验完成任务非空
		Argument.notNull(entity,"FnshUnldTaskScanEntity");
		//检验任务号非空
		Argument.hasText(entity.getTaskCode(), "FnshUnldTaskScanEntity.taskCode");
		//检验完成时间非空
		Argument.notNull(entity.getScanTime(), "FnshUnldTaskScanEntity.scanTime");
	}
	public void setUnloadDao(IUnloadDao unloadDao) {
		this.unloadDao = unloadDao;
	}
	public void setPdaUnloadTaskService(IPDAUnloadTaskService pdaUnloadTaskService) {
		this.pdaUnloadTaskService = pdaUnloadTaskService;
	}
	
}
