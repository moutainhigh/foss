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
import com.deppon.pda.bdm.module.foss.packagemanager.server.dao.IPackageDao;
import com.deppon.pda.bdm.module.foss.packagemanager.shared.domain.SubmitPackageTask;

/**
 * 
  * @ClassName CaclPackageTaskService 
  * @Description TODO 提交建包任务
  * @author mt 
  * @date 2013年7月30日9:59:23
 */
public class SmtPackageTaskService implements IBusinessService<Void, SubmitPackageTask> {
	private IPDAExpressPackageService pdaExpressPackageService;
	private IPackageDao packageDao;
	
	/**
	 * @author mt
	 * @date 2013年7月30日9:59:26
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException 
	 */
	@Override
	public SubmitPackageTask parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		SubmitPackageTask model = JsonUtil.parseJsonToObject(SubmitPackageTask.class,
				asyncMsg.getContent());
		model.setDeviceNo(asyncMsg.getPdaCode());
		model.setUserCode(asyncMsg.getUserCode());
		return model;
	}
	
	/**
	 * 
	 * <p>TODO提交建包任务
	 * @author mt
	 * @date 2013年7月30日9:59:29
	 * @param asyncMsg 请求消息
	 * @param param 提交建包任务信息
	 * @return
	 * @throws PdaBusiException 
	 */
	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, SubmitPackageTask param)
			throws PdaBusiException {
		//验证字段非空
		this.validate(param);
		try{
			if(packageDao.queryNoSyncScanMsgCount(param.getPackageCode())){
				throw new FossInterfaceException(null,"服务器数据正在同步，请耐心等待");
			}
			pdaExpressPackageService.submitPackage(param.getPackageCode(), 
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
	private void validate(SubmitPackageTask submitPackageTask)
			throws ArgumentInvalidException {
		Argument.notNull(submitPackageTask, "submitPackageTask");
		//包号
		Argument.hasText(submitPackageTask.getPackageCode(),"submitPackageTask.packageCode");
		//设备号
		Argument.hasText(submitPackageTask.getDeviceNo(),"submitPackageTask.deviceNo");
		//员工号
		Argument.hasText(submitPackageTask.getUserCode(),"submitPackageTask.userCode");
		// 检验时间非空
		Argument.notNull(submitPackageTask.getScanTime(),"submitPackageTask.scanTime");
	}
	
	@Override
	public String getOperType() {
		return PackageManagerConstant.OPER_TYPE_PACKAGE_SUBMIT_TASK.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}
	
	public void setPdaExpressPackageService(
			IPDAExpressPackageService pdaExpressPackageService) {
		this.pdaExpressPackageService = pdaExpressPackageService;
	}
	
	public void setPackageDao(IPackageDao packageDao) {
		this.packageDao = packageDao;
	}
}
