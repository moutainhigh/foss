package com.deppon.pda.bdm.module.foss.unload.server.service.impl.partner;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.Argument;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAOrderTaskService;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAOrderTaskDetailEntity;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.UnLoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.partner.TaskUpdateEntity;
/**
 * 
 * @ClassName: TaskUpdateService 
 * @Description: TODO(任务更新) 
 * @author &268974  wangzhili
 * @date 2016-1-27 上午10:44:35 
 *
 */
public class TaskUpdateService implements IBusinessService<PDAOrderTaskDetailEntity, TaskUpdateEntity>{

	private static final Logger log = Logger.getLogger(TaskUpdateService.class);
	private IPDAOrderTaskService pdaOrderTaskService; 
	
	//解析参数
	@Override
	public TaskUpdateEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		TaskUpdateEntity entity = JsonUtil.parseJsonToObject(TaskUpdateEntity.class, asyncMsg.getContent());
		return entity;
	}

	//入口方法
	@Override
	public PDAOrderTaskDetailEntity service(AsyncMsg asyncMsg, TaskUpdateEntity param)
			throws PdaBusiException {
		//校验参数的合法性
		this.validate(param);
		log.info("参数值为："+param);
		PDAOrderTaskDetailEntity result = new PDAOrderTaskDetailEntity();
		try {
			result = pdaOrderTaskService.queryOrderTaskDetailByOrderTaskNo(param.getOrderTaskNo());
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
		return UnLoadConstant.OPER_TYPE_ORDER_TASK_UPDATE.VERSION;
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
	private void validate(TaskUpdateEntity entity) throws ArgumentInvalidException{
		//检验任务更新实体非空
		Argument.notNull(entity,"TaskUpdateEntity");
		//检验任务编号非空
		Argument.hasText(entity.getOrderTaskNo(), "entity.orderTaskNo");
		
	}


	public void setPdaOrderTaskService(IPDAOrderTaskService pdaOrderTaskService) {
		this.pdaOrderTaskService = pdaOrderTaskService;
	}
	

}
