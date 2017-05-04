package com.deppon.pda.bdm.module.foss.unload.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAUnloadTaskService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoaderDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.UnloadTaskDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.UnloadTaskResultDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.UnLoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.CreateTaskResult;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.CreateUnldTask;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.UnloaderModel;

/**
 * 建立卸车任务service实现类
 * 
 * @author gaojia
 * @date Sep 7,2012 9:48:30 AM
 * @version 1.0
 * @since
 */
public class CrtUnldTaskService implements
		IBusinessService<CreateTaskResult, CreateUnldTask> {
	private IPDAUnloadTaskService pdaUnloadTaskService;

	/**
	 * 
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author Administrator
	 * @date 2013-3-20 下午6:33:36
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public CreateUnldTask parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		CreateUnldTask model = new CreateUnldTask();
		model = JsonUtil.parseJsonToObject(CreateUnldTask.class,
				asyncMsg.getContent());
		model.setPdaCode(asyncMsg.getPdaCode());
		model.setUserCode(asyncMsg.getUserCode());
		return model;
	}

	/**
	 * 
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author Administrator
	 * @date 2013-3-20 下午6:33:41
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg,
	 *      java.lang.Object)
	 */
	@Transactional
	@Override
	public CreateTaskResult service(AsyncMsg asyncMsg, CreateUnldTask param)
			throws PdaBusiException {
		this.validate(param);
		UnloadTaskDto req = new UnloadTaskDto();
		req.setBillNos(param.getReceiptCodes());
		req.setCreateOrgCode(asyncMsg.getDeptCode());
		req.setCreateTime(param.getScanTime());
		req.setDeviceNo(asyncMsg.getPdaCode());
		// 封装理货员信息
		List<LoaderDto> loaderCodes = new ArrayList<LoaderDto>();
		if (param.getUserCodes() != null && !param.getUserCodes().isEmpty()) {
			for (UnloaderModel loader : param.getUserCodes()) {
				LoaderDto dto = new LoaderDto();
				dto.setLoaderCode(loader.getUserCode());
				dto.setFlag(loader.getFlag());
				loaderCodes.add(dto);
			}
		}
		req.setLoaderCodes(loaderCodes);
		req.setOperatorCode(param.getUserCode());
		req.setOperatorOrgCode(asyncMsg.getDeptCode());
		req.setPlatformNo(param.getPlatformCode());
		req.setTaskNo(param.getTaskCode());
		req.setVehicleNo(param.getTruckCode());
		UnloadTaskResultDto res = null;
		try {
			// 调用FOSS接口
			res = pdaUnloadTaskService.createLoadTask(req);
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(), e.getErrorCode());
		}
		CreateTaskResult result = new CreateTaskResult();

		result.setCreateUser(res.getCreatorCode());
		List<UnloaderModel> loaders = new ArrayList<UnloaderModel>();
		// 封装理货员信息
		if (res.getLoaders() != null && !res.getLoaders().isEmpty()) {
			for (LoaderDto dto : res.getLoaders()) {
				UnloaderModel unloader = new UnloaderModel();
				unloader.setFlag(dto.getFlag());
				unloader.setUserCode(dto.getLoaderCode());
				loaders.add(unloader);
			}
		}
		result.setUserCodes(loaders);
		result.setTaskCode(res.getTaskNo());
		// result.setCrgDetails(crgDetails);
		//author:245960 Date:2015-06-19 comments:需求编码：RPDA2015051604/DN201505150024
		result.setUnloadType(res.getUnloadType());

		return result;
	}

	@Override
	public String getOperType() {
		return UnLoadConstant.OPER_TYPE_UNLD_TASK_CREATE.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

	public void setPdaUnloadTaskService(
			IPDAUnloadTaskService pdaUnloadTaskService) {
		this.pdaUnloadTaskService = pdaUnloadTaskService;
	}

	/**
	 * 
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author Administrator
	 * @date 2013-3-20 下午6:33:49
	 * @param createUnldTask
	 * @throws ArgumentInvalidException
	 * @see
	 */
	private void validate(CreateUnldTask createUnldTask)
			throws ArgumentInvalidException {
		// 检验创建任务非空
		Argument.notNull(createUnldTask, "createUnldTask");

		// 检验车牌号非空
		Argument.hasText(createUnldTask.getTruckCode(),
				"createUnldTask.truckCode");

		// 检验月台号非空
		/*
		 * Argument.hasText(createUnldTask.getPlatformCode(),
		 * "createUnldTask.platformCode");
		 */
		// 检验创建时间
		Argument.notNull(createUnldTask.getScanTime(),
				"createUnldTask.scanTime");

		/*
		 * // 检验卸货员 Argument.notEmpty(createUnldTask.getUserCodes(),
		 * "createUnldTask.userCodes");
		 */
	}

}
