package com.deppon.pda.bdm.module.foss.load.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDADeliverLoadService;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDATransferLoadService;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadTaskDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadTaskResultDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoaderDto;
import com.deppon.pda.bdm.module.core.server.cache.LoadLimitedWeightCache;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.domain.LoadLimitedWeightEntity;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.load.server.dao.ILoadPdaDao;
import com.deppon.pda.bdm.module.foss.load.shared.domain.CreateLoadTask;
import com.deppon.pda.bdm.module.foss.load.shared.domain.CreateLoadTaskResult;
import com.deppon.pda.bdm.module.foss.load.shared.domain.LoaderModel;

/**
 * 建立装车任务实现类
 * 
 * @author gaojia
 * @date Sep 10,2012 14:42:30 PM
 * @version 1.0
 * @since
 */
public class CreateLoadTaskService implements
		IBusinessService<CreateLoadTaskResult, CreateLoadTask> {
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(CreateLoadTaskService.class);
	

	
	private ILoadPdaDao loadPdaDao;

	public void setLoadPdaDao(ILoadPdaDao loadPdaDao) {
		this.loadPdaDao = loadPdaDao;
	}
	private LoadLimitedWeightCache loadLimitedWeightCache;
	
	private IPDATransferLoadService pdaTransferLoadService;
	private IPDADeliverLoadService pdaDeliverLoadService;
	/**
	 * 
	 * <p>TODO(建立装车任务)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:12:26
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public CreateLoadTask parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		CreateLoadTask model = JsonUtil.parseJsonToObject(CreateLoadTask.class,
				asyncMsg.getContent());
		model.setPdaCode(asyncMsg.getPdaCode());
		model.setUserCode(asyncMsg.getUserCode());
		return model;
	}
	/**
	 * 
	 * <p>TODO(建立装车任务)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:13:13
	 * @param asyncMsg 请求消息
	 * @param param 建立装车任务信息
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Transactional
	@Override
	public CreateLoadTaskResult service(AsyncMsg asyncMsg, CreateLoadTask param)
			throws PdaBusiException {
		//数据合法性校验
		this.validate(param);
		//封装参数
		LoadTaskDto model = new LoadTaskDto();
		CreateLoadTaskResult result = new CreateLoadTaskResult();
		model.setLoadTaskType(param.getLoadType());
		model.setCreateTime(param.getScanTime());
		//将目的站ID转化为CODE
		if(!param.getLoadType().equals(TransferPDADictConstants.LOAD_TASK_TYPE_DELIVER)){
			//Argument.notEmpty(param.getArrDeptCode(), "CreateLoadTask.arrDeptCode");
			//List<String> arrDeptCode  = param.getArrDeptCode();
			//for (String s : param.getArrDeptCode()) {
			//	Argument.hasText(str, "CreateLoadTask.arrDeptCode");
			//.add(str);
			//}
			model.setDestOrgCodes(param.getArrDeptCode());
		}
		model.setDeviceNo(asyncMsg.getPdaCode());
		//设置货物类型
		if(param.getCrgType()==null||param.getCrgType().isEmpty()){
			model.setGoodsType(TransferPDADictConstants.LOAD_GOODS_TYPE_ALL);
		}else{
			model.setGoodsType(param.getCrgType());
		}
		//封装理货员信息
		List<LoaderDto> loaderCodes = new ArrayList<LoaderDto>();
		if(param.getUserCodes()!=null&&!param.getUserCodes().isEmpty()){
			for (LoaderModel loader : param.getUserCodes()) {
				LoaderDto dto = new LoaderDto();
				dto.setLoaderCode(loader.getUserCode());
				dto.setFlag(loader.getFlag());
				loaderCodes.add(dto);
			}
		}
		model.setLoaderCodes(loaderCodes);
		model.setOperatorCode(asyncMsg.getUserCode());
		model.setCreateOrgCode(asyncMsg.getDeptCode());
		model.setPlatformNo(param.getPlatformCode());
		model.setTaskNo(param.getTaskCode());
		model.setDeliverBillNo(param.getDeryListCode());
		model.setVehicleNo(param.getTruckCode());
		//2013-12-3新增转货类型
		model.setTransitGoodsType(param.getTransitGoodsType());
		LoadTaskResultDto res = null;
		//调用FOSS接口
		try {
			if(param.getLoadType().equals(TransferPDADictConstants.LOAD_TASK_TYPE_DELIVER)){
				//派送装车							
				res = pdaDeliverLoadService.createLoadTask(model);												
			}else{
				//中转装车
				//营业部分部装车
				if(param.getDeptType()!=null && "5".equals(param.getDeptType())){
					String expressBranchCode = loadPdaDao.queryExpressBranch(asyncMsg.getUserCode());
					if(expressBranchCode==null || "".equals(expressBranchCode)){
						throw new FossInterfaceException(null,"该营业部找不到对应分部");
					}
					model.setCreateOrgCode(expressBranchCode);
				}								
				res = pdaTransferLoadService.createLoadTask(model);
			}
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		result.setCreateUser(res.getCreatorCode());//创建人
		result.setRatedLoad(res.getVehicleDeadWeight());//额定载重
		result.setRatedVolume(res.getVehicleDeadVolume());//额定净空
		//重量体积转换
		result.setExpressConvertParameter(res.getExpressConvertParameter().doubleValue());
		List<LoaderModel> loaders = new ArrayList<LoaderModel>();//理货员信息
		if(res.getLoaders()!=null&&!res.getLoaders().isEmpty()){
			for (LoaderDto dto : res.getLoaders()) {
				LoaderModel loader = new LoaderModel();
				loader.setUserCode(dto.getLoaderCode());
				loader.setFlag(dto.getFlag());
				loaders.add(loader);
			}
		}
		result.setUserCodes(loaders);
		result.setTaskCode(res.getTaskNo());
		LoadLimitedWeightEntity loadLimitedWeightEntity = null;
		loadLimitedWeightEntity = loadLimitedWeightCache.getLoadLimitedWeight(res.getVehicleLengthName());
		if(loadLimitedWeightEntity==null){
			loadLimitedWeightEntity = loadLimitedWeightCache.getLoadLimitedWeight("其他车型");
		}
		if(loadLimitedWeightEntity!=null){
			result.setAlartLoadRate(loadLimitedWeightEntity.getAlartLoadRate());
			result.setGravityLimit(loadLimitedWeightEntity.getGravityLimit());
		}
		return result;
	}
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author Administrator
	 * @date 2013-3-20 下午6:14:21
	 * @param createLoadTask
	 * @throws ArgumentInvalidException
	 * @see
	 */
	private void validate(CreateLoadTask createLoadTask)
			throws ArgumentInvalidException {
		// 检验建立装车任务非空
		Argument.notNull(createLoadTask, "createLoadTask");
		// 检验车牌号非空
		Argument.hasText(createLoadTask.getTruckCode(),
				"createLoadTask.truckCode");
		// 检验月台号非空
	/*	Argument.hasText(createLoadTask.getPlatformCode(),
				"createLoadTask.platformCode");*/
		Argument.hasText(createLoadTask.getLoadType(),
				"createLoadTask.loadType");
		// 检验时间非空
		Argument.notNull(createLoadTask.getScanTime(),
				"createLoadTask.scanTime");
		// 检验卸车员工非空
		/*Argument.notEmpty(createLoadTask.getUserCodes(),
				"createLoadTask.userCodes");*/
	}
	@Override
	public String getOperType() {
		return LoadConstant.OPER_TYPE_LOAD_TASK_CREATE.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}


	public void setPdaTransferLoadService(
			IPDATransferLoadService pdaTransferLoadService) {
		this.pdaTransferLoadService = pdaTransferLoadService;
	}

	public void setPdaDeliverLoadService(
			IPDADeliverLoadService pdaDeliverLoadService) {
		this.pdaDeliverLoadService = pdaDeliverLoadService;
	}

	public void setLoadLimitedWeightCache(
			LoadLimitedWeightCache loadLimitedWeightCache) {
		this.loadLimitedWeightCache = loadLimitedWeightCache;
	}
}
