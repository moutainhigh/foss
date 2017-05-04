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
import com.deppon.pda.bdm.module.foss.packagemanager.shared.domain.CancelPackageTask;

/**
 * 
  * @ClassName CaclPackageTaskService 
  * @Description TODO 撤销建包任务
  * @author mt 
  * @date 2013年7月30日9:59:23
 */
public class CaclPackageTaskService implements IBusinessService<Void, CancelPackageTask> {
	private IPDAExpressPackageService pdaExpressPackageService;
	
	/**
	 * @author mt
	 * @date 2013年7月30日9:59:26
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException 
	 */
	@Override
	public CancelPackageTask parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		CancelPackageTask model = JsonUtil.parseJsonToObject(CancelPackageTask.class,
				asyncMsg.getContent());
		model.setDeviceNo(asyncMsg.getPdaCode());
		model.setUserCode(asyncMsg.getUserCode());
		return model;
	}
	
	/**
	 * 
	 * <p>TODO建立装车任务
	 * @author mt
	 * @date 2013年7月30日9:59:29
	 * @param asyncMsg 请求消息
	 * @param param 撤销建包任务信息
	 * @return
	 * @throws PdaBusiException 
	 */
	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, CancelPackageTask param)
			throws PdaBusiException {
		//验证字段非空
		this.validate(param);
		try{
			pdaExpressPackageService.cancelPackage(param.getPackageCode(), 
					param.getDeviceNo(), param.getUserCode(), param.getScanTime());
		}
		catch(BusinessException e){
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		return null;
	}
	
	/**
	 * 
	* @Description: TODO 字段非空验证
	* @param cancelPackageTask
	* @throws ArgumentInvalidException
	* @return void    
	* @author mt
	* @date 2013年7月30日9:59:33
	 */
	private void validate(CancelPackageTask cancelPackageTask)
			throws ArgumentInvalidException {
		Argument.notNull(cancelPackageTask, "cancelPackageTask");
		//包号
		Argument.hasText(cancelPackageTask.getPackageCode(),"cancelPackageTask.packageCode");
		//设备号
		Argument.hasText(cancelPackageTask.getDeviceNo(),"cancelPackageTask.deviceNo");
		//员工号
		Argument.hasText(cancelPackageTask.getUserCode(),"cancelPackageTask.userCode");
		// 检验时间非空
		Argument.notNull(cancelPackageTask.getScanTime(),"cancelPackageTask.scanTime");
	}
	
	@Override
	public String getOperType() {
		return PackageManagerConstant.OPER_TYPE_PACKAGE_TASK_CACL.VERSION;
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
