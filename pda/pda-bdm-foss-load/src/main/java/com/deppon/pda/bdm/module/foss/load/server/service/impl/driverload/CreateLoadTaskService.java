package com.deppon.pda.bdm.module.foss.load.server.service.impl.driverload;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaDispatchOrderService;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExpressFeederPickupQueryDto;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressConnectionService;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoadTaskDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.load.shared.domain.driverload.CreateLoadTask;
import com.deppon.pda.bdm.module.foss.load.shared.domain.driverload.CreateLoadTaskResult;

/**
 * 创建司机接驳装车任务
 * 
 * @ClassName KdCreateLoadTaskService.java
 * @Description
 * @author 245955
 * @date 2015-4-21
 */
public class CreateLoadTaskService implements
		IBusinessService<CreateLoadTaskResult, CreateLoadTask> {
	@SuppressWarnings("unused")
	private static final Logger logger = Logger
			.getLogger(CreateLoadTaskService.class);
	private IPdaDispatchOrderService pdaDispatchOrderService;
	private IPDAExpressConnectionService pdaExpressConnectionService;

	/**
	 * <p>
	 * TODO(建立装车任务)
	 * </p>
	 * 
	 * @description
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-4-21
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
	 * <p>
	 * 服务方法
	 * </p>
	 * 
	 * @description
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-4-21
	 */
	@Override
	public CreateLoadTaskResult service(AsyncMsg asyncMsg, CreateLoadTask param)
			throws PdaBusiException {
		// 数据合法性校验
		this.validate(param);
		// 封装参数
		LoadTaskDto model = new LoadTaskDto();
		CreateLoadTaskResult result = new CreateLoadTaskResult();
		ExpressFeederPickupQueryDto queryDto = new ExpressFeederPickupQueryDto();
		queryDto.setDriverCode(asyncMsg.getUserCode()); // 司机工号
		queryDto.setExpressEmpCode(param.getCourierCode()); // 快递员工号
		try {
			   //判定两工号是否存在接驳外场所属关系，限制不同城市间不能建立接驳任务
			   pdaDispatchOrderService.validateExpressFeederUnderOutField(queryDto);
				// 车牌号
				model.setVehicleNo(param.getTruckCode());
				// 设备号
				model.setDeviceNo(asyncMsg.getPdaCode());
				// 操作人
				model.setOperatorCode(asyncMsg.getUserCode());
				// 创建部门编号
				model.setCreateOrgCode(asyncMsg.getDeptCode());
				// 快递员工号
				model.setTallyerCode(param.getCourierCode());
				String res = pdaExpressConnectionService
						.createDriverTask(model);
				result.setTaskCode(res);
			return result;
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(), e.getErrorCode());
		}
	}

	/**
	 * 操作类型
	 * 
	 * @description
	 * @return
	 * @author 245955
	 * @date 2015-4-21
	 */
	@Override
	public String getOperType() {
		return LoadConstant.OPER_TYPE_DRIVER_TASK_CREATE.VERSION;
	}

	/**
	 * 是否异步
	 * 
	 * @description
	 * @return
	 * @author 245955
	 * @date 2015-4-21
	 */
	@Override
	public boolean isAsync() {
		return false;
	}

	/**
	 * <p>
	 * 参数校验
	 * </p>
	 * 
	 * @description
	 * @param kdCreateLoadTask
	 * @throws ArgumentInvalidException
	 * @author 245955
	 * @date 2015-4-21
	 */
	private void validate(CreateLoadTask createLoadTask)
			throws ArgumentInvalidException {
		// 检验时间非空
		Argument.notNull(createLoadTask.getScanTime(),
				"createLoadTask.scanTime");
		// 检验员工工号
		Argument.hasText(createLoadTask.getUserCode(),
				"createLoadTask.userCode");
	}

	public void setPdaExpressConnectionService(
			IPDAExpressConnectionService pdaExpressConnectionService) {
		this.pdaExpressConnectionService = pdaExpressConnectionService;
	}

	public void setPdaDispatchOrderService(
			IPdaDispatchOrderService pdaDispatchOrderService) {
		this.pdaDispatchOrderService = pdaDispatchOrderService;
	}

}
