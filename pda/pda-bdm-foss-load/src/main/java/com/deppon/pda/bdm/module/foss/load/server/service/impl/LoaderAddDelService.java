package com.deppon.pda.bdm.module.foss.load.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDATransferLoadService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoaderDto;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.load.server.dao.ILoadDao;
import com.deppon.pda.bdm.module.foss.load.shared.domain.LoaderAddDelModel;
import com.deppon.pda.bdm.module.foss.load.shared.domain.LoaderModel;

/**
 * 
 * TODO(增加删除理货员)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Administrator,date:2012-12-3 上午10:26:53,content:TODO </p>
 * @author Administrator
 * @date 2012-12-3 上午10:26:53
 * @since
 * @version
 */
public class LoaderAddDelService implements IBusinessService<Void, LoaderAddDelModel>{
	private IPDATransferLoadService pdaTransferLoadService;
	private static final Logger logger = Logger.getLogger(LoaderAddDelService.class);
	private ILoadDao loadDao;
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:16:57
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public LoaderAddDelModel parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		LoaderAddDelModel entity = JsonUtil.parseJsonToObject(LoaderAddDelModel.class, asyncMsg.getContent());
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
	 * @date 2013-3-20 下午6:17:04
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, LoaderAddDelModel param)
			throws PdaBusiException {
		this.validate(param);
		logger.info("装车增删理货员："+param.getTaskCode()+":"+param.getStatus());
		try {
			List<LoaderDto> unloaders = new ArrayList<LoaderDto>();
			for (LoaderModel unloader : param.getUserCodes()) {
				LoaderDto dto = new LoaderDto();
				dto.setLoaderCode(unloader.getUserCode());
				logger.info(unloader.getUserCode());
				dto.setFlag(unloader.getFlag());
				unloaders.add(dto);
			}
			loadDao.saveLoader(param);
			//调用FOSS接口
			long startTime = System.currentTimeMillis();
			pdaTransferLoadService.modifyLoader(param.getTaskCode(), unloaders,param.getScanTime(), param.getStatus());
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			logger.info("[asyncinfo]增删理货员接口消耗时间:"+(endTime-startTime)+"ms");
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		return null;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:17:11
	 * @param param
	 * @see
	 */
	private void validate(LoaderAddDelModel param) {
		Argument.notNull(param,"LoaderAddDelModel");
		Argument.hasText(param.getTaskCode(), "LoaderAddDelModel.taskCode");
		Argument.notEmpty(param.getUserCodes(), "LoaderAddDelModel.userCodes");
		Argument.hasText(param.getStatus(), "LoaderAddDelModel.status");
		Argument.notNull(param.getScanTime(), "LoaderAddDelModel.scanTime");
	}

	@Override
	public String getOperType() {
		return LoadConstant.OPER_TYPE_LOAD_OPR_DEL_ADD.VERSION;
	}

	@Override
	public boolean isAsync() {
		return true;
	}

	public void setPdaTransferLoadService(
			IPDATransferLoadService pdaTransferLoadService) {
		this.pdaTransferLoadService = pdaTransferLoadService;
	}
	public void setLoadDao(ILoadDao loadDao) {
		this.loadDao = loadDao;
	}

	

	
	
}
