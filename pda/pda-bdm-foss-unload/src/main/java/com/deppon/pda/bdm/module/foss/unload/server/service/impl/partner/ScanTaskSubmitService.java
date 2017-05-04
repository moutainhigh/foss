package com.deppon.pda.bdm.module.foss.unload.server.service.impl.partner;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.Argument;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAOrderTaskService;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.UnLoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.partner.ScanTaskSubmitEntity;
/**
 * 
 * @ClassName: ScanTaskSubmitService 
 * @Description: TODO(扫描任务提交) 
 * @author &268974  wangzhili
 * @date 2016-1-27 上午11:11:23 
 *
 */
public class ScanTaskSubmitService implements IBusinessService<String, ScanTaskSubmitEntity>{

	private static final Logger log = Logger.getLogger(ScanTaskSubmitService.class);
	private IPDAOrderTaskService pdaOrderTaskService;
	
	@Override
	//解析参数
	public ScanTaskSubmitEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		ScanTaskSubmitEntity entity = JsonUtil.parseJsonToObject(ScanTaskSubmitEntity.class, asyncMsg.getContent());
		return entity;
	}

	//方法入口
	@Override
	public String service(AsyncMsg asyncMsg, ScanTaskSubmitEntity param)
			throws PdaBusiException {
		//校验参数的合法性
		this.validate(param);
		log.info("参数值为:"+param);
		String result = null;
		try {
			result = pdaOrderTaskService.finishPdaOrderTask(param.getOrderTaskNo(),param.getUserCode());
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		} catch(Exception e){
			throw new FossInterfaceException(null,"调用foss出现未知异常");
		}
		return result;
	}

	//操作类型
	@Override
	public String getOperType() {
		return UnLoadConstant.OPER_TYPE_SCAN_TASK_SUBMIT.VERSION;
	}

	//同步
	@Override
	public boolean isAsync() {
		return false;
	}
	/**
	 * 
	 * @Title: validate 
	 * @Description: TODO(校验参数的合法性)  
	 * @return void    返回类型 
	 * @param entity
	 * @throws ArgumentInvalidException
	 * @author： 268974  wangzhili
	 */
	private void validate(ScanTaskSubmitEntity entity) throws ArgumentInvalidException{
		//检验扫描任务提交实体非空
		Argument.notNull(entity,"ScanTaskSubmitEntity");
		//检验任务编号非空
		Argument.hasText(entity.getOrderTaskNo(), "entity.orderTaskNo");
		//校验用户编码非空
		Argument.hasText(entity.getUserCode(), "entity.userCode");
	}

	public void setPdaOrderTaskService(IPDAOrderTaskService pdaOrderTaskService) {
		this.pdaOrderTaskService = pdaOrderTaskService;
	}

}
