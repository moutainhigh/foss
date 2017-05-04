package com.deppon.pda.bdm.module.foss.packagemanager.server.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAExpressPackageService;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.PackageManagerConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.packagemanager.shared.domain.CreatePackageTask;

/**
 * 
  * @ClassName CreatePackageTaskService 
  * @Description TODO 创建装车建包任务
  * @author mt 
  * @date 2013年7月30日9:59:11
 */
public class CreatePackageTaskService implements IBusinessService<String, CreatePackageTask> {
	private IPDAExpressPackageService pdaExpressPackageService;
	@Override
	public CreatePackageTask parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		CreatePackageTask model = JsonUtil.parseJsonToObject(CreatePackageTask.class,
				asyncMsg.getContent());
		model.setDeviceNo(asyncMsg.getPdaCode());
		model.setUserCode(asyncMsg.getUserCode());
		return model;
	}
	
	/**
	 * 
	 * <p>TODO(创建装车建包任务)</p> 
	 * @author mt
	 * @date 2013年7月30日9:59:14
	 * @param asyncMsg 请求消息
	 * @param param 创建装车建包任务信息
	 * @return
	 * @throws PdaBusiException 
	 */
	@Transactional
	@Override
	public String service(AsyncMsg asyncMsg, CreatePackageTask param)
			throws PdaBusiException {
		//验证字段非空
		this.validate(param);
		String result = "";
		String expressPackageType = "";
		if(param.getExpressPackageType() !=null && "1".equals(param.getExpressPackageType())){//“1”表示为直达包
			expressPackageType = "THROUGH_ARRIVE";
		}else if(param.getExpressPackageType() !=null && "0".equals(param.getExpressPackageType())){//“0”表示为正常包
			expressPackageType = "NORMAL_ARRIVE";
		}else if(param.getExpressPackageType() !=null && "3".equals(param.getExpressPackageType())){//'3'表示航空件包
			expressPackageType = "EXPRESS_AIR";		
		}else if(param.getExpressPackageType() !=null && "4".equals(param.getExpressPackageType())){//'4'表示空运直达包
			expressPackageType = "AIRTHROUGH_ARRIVE";		
		} else{ //"2"表示二程接驳建包
			expressPackageType = "SECONDCAR_ARRIVE";		
		}
		try{
			result = pdaExpressPackageService.createPackage(param.getPackageCode(), 
					param.getStartOrg(), param.getArriveOrg(), param.getUserCode(), 
					param.getDeviceNo(), param.getCreateTime(),expressPackageType);
		}
		catch(BusinessException e){
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		return result;
	}
	/**
	 * 
	 * <p>TODO字段非空验证</p> 
	 * @author mt
	 * @date 2013年7月30日9:59:18
	 * @param createPackageTask
	 * @throws ArgumentInvalidException
	 * @see
	 */
	private void validate(CreatePackageTask createPackageTask)
			throws ArgumentInvalidException {
		Argument.notNull(createPackageTask, "createPackageTask");
		//包号
		Argument.hasText(createPackageTask.getPackageCode(),"createPackageTask.packageCode");
		//建包员工号
		Argument.hasText(createPackageTask.getUserCode(),"createPackageTask.userCode");
		//到达部门
		Argument.hasText(createPackageTask.getArriveOrg(),"createPackageTask.arriveOrg");
		//设备号
		Argument.hasText(createPackageTask.getDeviceNo(),"createPackageTask.deviceNo");
		//出发部门
		Argument.hasText(createPackageTask.getStartOrg(),"createPackageTask.startOrg");
		//建包时间
		Argument.notNull(createPackageTask.getCreateTime(),"createPackageTask.createTime");
	}
	
	@Override
	public String getOperType() {
		return PackageManagerConstant.OPER_TYPE_PACKAGE_TASK_CREATE.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}
	
	public void setPdaExpressPackageService(
			IPDAExpressPackageService pdaExpressPackageService) {
		this.pdaExpressPackageService = pdaExpressPackageService;
	}
}
