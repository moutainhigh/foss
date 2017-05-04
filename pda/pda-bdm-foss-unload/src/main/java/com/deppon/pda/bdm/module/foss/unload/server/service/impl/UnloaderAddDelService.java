package com.deppon.pda.bdm.module.foss.unload.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAUnloadTaskService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoaderDto;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.UnLoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.unload.server.dao.IUnloadDao;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.UnloaderAddDelModel;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.UnloaderModel;

public class UnloaderAddDelService implements IBusinessService<Void,UnloaderAddDelModel >{
	private IPDAUnloadTaskService pdaUnloadTaskService;
	private static final Logger logger = Logger.getLogger(UnloaderAddDelService.class);
	private IUnloadDao unloadDao;
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:35:58
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public UnloaderAddDelModel parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		UnloaderAddDelModel entity = JsonUtil.parseJsonToObject(UnloaderAddDelModel.class, asyncMsg.getContent());
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
	 * @date 2013-3-20 下午6:36:01
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, UnloaderAddDelModel param)
			throws PdaBusiException {
		this.validate(param);
		logger.info("卸车增删理货员："+param.getTaskCode()+":"+param.getStatus());
		try {
			List<LoaderDto> unloaders = new ArrayList<LoaderDto>();
			for (UnloaderModel unloader : param.getUserCodes()) {
				LoaderDto dto = new LoaderDto();
				dto.setLoaderCode(unloader.getUserCode());
				dto.setFlag(unloader.getFlag());
				logger.info(unloader.getUserCode());
				unloaders.add(dto);
			}
			unloadDao.saveLoader(param);
			long startTime = System.currentTimeMillis();
			pdaUnloadTaskService.modifyLoader(param.getTaskCode(), unloaders,param.getScanTime(), param.getStatus());
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			logger.info("[asyncinfo]完成卸车任务接口消耗时间:"+(endTime-startTime)+"ms");
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		return null;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:36:10
	 * @return 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#getOperType()
	 */
	@Override
	public String getOperType() {
		return UnLoadConstant.OPER_TYPE_UNLD_OPR_DEL_ADD.VERSION;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:36:14
	 * @return 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#isAsync()
	 */
	@Override
	public boolean isAsync() {
		return true;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:36:06
	 * @param param
	 * @see
	 */
	private void validate(UnloaderAddDelModel param) {
		Argument.notNull(param,"UnloaderAddDelModel");
		Argument.hasText(param.getTaskCode(), "UnloaderAddDelModel.taskCode");
		Argument.notEmpty(param.getUserCodes(), "UnloaderAddDelModel.userCodes");
		Argument.hasText(param.getStatus(), "UnloaderAddDelModel.status");
		Argument.notNull(param.getScanTime(), "UnloaderAddDelModel.scanTime");
	}

	public void setPdaUnloadTaskService(IPDAUnloadTaskService pdaUnloadTaskService) {
		this.pdaUnloadTaskService = pdaUnloadTaskService;
	}
	public void setUnloadDao(IUnloadDao unloadDao) {
		this.unloadDao = unloadDao;
	}
	
}
