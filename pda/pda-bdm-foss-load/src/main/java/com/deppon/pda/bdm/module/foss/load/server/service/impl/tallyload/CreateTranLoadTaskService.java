package com.deppon.pda.bdm.module.foss.load.server.service.impl.tallyload;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressConnectionService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadTaskDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadTaskResultDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoaderDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.load.shared.domain.LoaderModel;
import com.deppon.pda.bdm.module.foss.load.shared.domain.tallyload.CreateLoadTaskResult;
import com.deppon.pda.bdm.module.foss.load.shared.domain.tallyload.CreateLoadTranTaskEntity;

/**
 * 创建理货员接驳任务
 * 
 * @ClassName CreateTranLoadTaskService.java
 * @Description
 * @author 245955
 * @date 2015-4-10
 */
public class CreateTranLoadTaskService implements IBusinessService<CreateLoadTaskResult, CreateLoadTranTaskEntity> {

	// PDA接驳装车任务接口
	private IPDAExpressConnectionService pdaExpressConnectionService;
	
	/**
	 * <p>
	 * TODO(创建理货员接驳任务)
	 * </p>
	 * 
	 * @author 245955
	 * @date 2015-4-10 上午
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public CreateLoadTranTaskEntity  parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		CreateLoadTranTaskEntity model = JsonUtil.parseJsonToObject(CreateLoadTranTaskEntity.class, asyncMsg.getContent());
		model.setPdaCode(asyncMsg.getPdaCode());
		model.setUserCode(asyncMsg.getUserCode());
		return model;
	}

	/**
	 * <p>
	 * TODO(新建理货员接驳任务)
	 * </p>
	 * 
	 * @author 245955
	 * @date 2015-4-10 上午
	 * @param asyncMsg   请求消息
	 * @return
	 * @throws PdaBusiException
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service1(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg,
	 *      java.lang.Object)
	 */
	@Transactional
	@Override
	public CreateLoadTaskResult service(AsyncMsg asyncMsg, CreateLoadTranTaskEntity param) throws PdaBusiException {
		// 数据合法性校验
		this.validate(asyncMsg, param);
		// 封装参数
		LoadTaskDto model = new LoadTaskDto();
		CreateLoadTaskResult result = new CreateLoadTaskResult();
		//model.setLoadTaskType(param.getLoadType());
		model.setCreateTime(param.getScanTime());
		// 封装理货员信息
		List<LoaderDto> loaderCodes = new ArrayList<LoaderDto>();
		if (param.getUserCodes() != null && !param.getUserCodes().isEmpty()) {
			for (LoaderModel loader : param.getUserCodes()) {
				LoaderDto dto = new LoaderDto();
				dto.setLoaderCode(loader.getUserCode());
				dto.setFlag(loader.getFlag());
				loaderCodes.add(dto);
			}
		}
		model.setLoaderCodes(loaderCodes);//理货员
		model.setOperatorCode(asyncMsg.getUserCode());
		model.setCreateOrgCode(asyncMsg.getDeptCode());//部门编号
		model.setPlatformNo(param.getPlatformCode());//月台号
		model.setTaskNo(param.getTaskCode());
		//model.setDeliverBillNo(param.getDeryListCode());
		model.setVehicleNo(param.getTruckCode());//车牌号	
		model.setDestOrgCodes(param.getArrDeptCode());//接驳点
		model.setDeviceNo(asyncMsg.getPdaCode());//设备号
		LoadTaskResultDto res = null;
		// 调用FOSS接口
		try {
			res=pdaExpressConnectionService.createConnectionTask(model);
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(), e.getErrorCode());
		}
		result.setRatedLoad(res.getVehicleDeadWeight());//额定载重
		result.setRatedVolume(res.getVehicleDeadVolume());//额重体积
		result.setTaskCode(res.getTaskNo());//任务号
		return result;
	}

	/**
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author 245955
	 * @date 2015-4-10 上午
	 * @param CreateLoadTranTaskEntity
	 * @throws ArgumentInvalidException
	 * @see
	 */
	private void validate(AsyncMsg asyncMsg,CreateLoadTranTaskEntity entity) throws ArgumentInvalidException {
		 // 检验建立装车任务非空
		 Argument.notNull(entity, "entity");
		 // 检验车牌号非空
		 Argument.hasText(entity.getTruckCode(),"entity.truckCode");
		 // 检验装车类型非空
		 Argument.hasText(entity.getLoadType(),"entity.loadType");
		 // 检验时间非空
	     Argument.notNull(entity.getScanTime(),"entity.scanTime");
	}

	/**
	 * 操作类型
	 * 
	 * @description
	 * @return
	 * @author 245955
	 * @date 2015-4-11
	 */
	@Override
	public String getOperType() {
		return LoadConstant.OPER_TYPE_LOAD_CREATE_TRAN.VERSION;
	}

	/**
	 * 是否异步
	 * 
	 * @description
	 * @return
	 * @author 245955
	 * @date 2015-4-11
	 */
	@Override
	public boolean isAsync() {
		return false;
	}

	public void setPdaExpressConnectionService(IPDAExpressConnectionService pdaExpressConnectionService) {
		this.pdaExpressConnectionService = pdaExpressConnectionService;
	}
}
