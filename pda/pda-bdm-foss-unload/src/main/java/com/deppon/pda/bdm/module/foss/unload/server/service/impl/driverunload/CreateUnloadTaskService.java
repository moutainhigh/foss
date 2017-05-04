package com.deppon.pda.bdm.module.foss.unload.server.service.impl.driverunload;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.shared.dto.LoaderDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.UnloadTaskDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.UnloadTaskResultDto;
import com.deppon.foss.module.transfer.unload.api.server.service.ISCPDAUnloadTaskService;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.UnLoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.driverunload.CreateTaskResult;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.driverunload.CreateUnldTaskEntity;

/**
 * 新建卸车任务
 * @ClassName CreateUnloadTaskService.java 
 * @Description 
 * @author 245955
 * @date 2015-5-14
 */
public class CreateUnloadTaskService implements IBusinessService<CreateTaskResult, CreateUnldTaskEntity>{
	
	private ISCPDAUnloadTaskService  scpdaUnloadTaskService;
	@Override
	public CreateUnldTaskEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		CreateUnldTaskEntity model = JsonUtil.parseJsonToObject(CreateUnldTaskEntity.class,asyncMsg.getContent());
		return model;
	}

	/**
	 * (方法详细描述说明、方法参数的具体涵义)
	 * @description 
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-5-14
	 */
	@Override
	public CreateTaskResult service(AsyncMsg asyncMsg, CreateUnldTaskEntity param)
			throws PdaBusiException {
		this.validate(param);
		UnloadTaskDto req = new UnloadTaskDto();
		List<String> billNos=new ArrayList<String>();
		billNos.add(param.getReceptCode());
		req.setBillNos(billNos);//单据编号
		req.setCreateOrgCode(asyncMsg.getDeptCode());//部门编号
		req.setVehicleNo(param.getVehicleNo());//车牌号
		req.setDeviceNo(asyncMsg.getPdaCode());//设备编号
		List<LoaderDto> loaders = new ArrayList<LoaderDto>();
		if (asyncMsg.getUserCode() != null && !asyncMsg.getUserCode().isEmpty()) {
			LoaderDto dto=new LoaderDto();
			dto.setLoaderCode(asyncMsg.getUserCode() );
			loaders.add(dto);
		}		
		req.setLoaderCodes(loaders);//理货员
		req.setOperatorCode(asyncMsg.getUserCode());//操作人编号
		UnloadTaskResultDto res = null;
		try {
		// 调用FOSS接口
		res = scpdaUnloadTaskService.createLoadTask(req);
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(), e.getErrorCode());
		}
		CreateTaskResult result = new CreateTaskResult();
		//返回任务号
		result.setTaskCode(res.getTaskNo());
		return result;
	}

	@Override
	public String getOperType() {
		return UnLoadConstant.OPER_TYPE_UNLOAD_TRAN_CREATE.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

	public void setScpdaUnloadTaskService(ISCPDAUnloadTaskService scpdaUnloadTaskService) {
		this.scpdaUnloadTaskService = scpdaUnloadTaskService;
	}



	/**
     * 参数验证
     * @description 
     * @param createUnldTask
     * @throws ArgumentInvalidException
     * @author 245955
     * @date 2015-5-14
     */
	private void validate(CreateUnldTaskEntity createUnldTaskEntity)
			throws ArgumentInvalidException {
		// 检验单据编号
		Argument.notNull(createUnldTaskEntity.getReceptCode(),"createUnldTaskEntity.receptCode");
	}
}
