package com.deppon.pda.bdm.module.foss.unload.server.service.impl.partner;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.Argument;
import com.deppon.foss.module.transfer.pda.api.server.service.IPDAOrderTaskService;
import com.deppon.foss.module.transfer.pda.api.shared.domain.PDAOrderTaskEntity;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.UnLoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.partner.OrderTaskRefreshEntity;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.partner.OrderTaskRefreshResult;
/**
 * 
 * @ClassName: OrderTaskRefreshService 
 * @Description: TODO(点单任务刷新) 
 * @author &268974  wangzhili
 * @date 2016-1-27 上午10:26:31 
 *
 */
public class OrderTaskRefreshService implements IBusinessService<OrderTaskRefreshResult, OrderTaskRefreshEntity>{
	
	private static final Logger log = Logger.getLogger(OrderTaskRefreshService.class);
	
	private IPDAOrderTaskService pdaOrderTaskService;
	/* 
	 * <p>Description: 解析参数</p> 
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg) 
	 * @author： 268974  wangzhili
	 */
	@Override
	public OrderTaskRefreshEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		OrderTaskRefreshEntity entity = JsonUtil.parseJsonToObject(OrderTaskRefreshEntity.class,asyncMsg.getContent());
		return entity;
	}

	/* 
	 * <p>Description: 入口方法</p> 
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException 
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object) 
	 * @author： 268974  wangzhili
	 */
	@Override
	public OrderTaskRefreshResult service(AsyncMsg asyncMsg, OrderTaskRefreshEntity param)
			throws PdaBusiException {
		//校验参数的合法性
		this.validate(param);
		log.info("参数值为："+param);
		List<PDAOrderTaskEntity> result = new ArrayList<PDAOrderTaskEntity>();
		OrderTaskRefreshResult results = new OrderTaskRefreshResult();
		try {
			result = pdaOrderTaskService.refreshOrderTaskByPDA(param.getDeptCode(),param.getOperator());
			results.setPDAOrderTaskEntitys(result);
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		} catch(Exception e){
			throw new FossInterfaceException(null,"调用foss出现未知异常");
		}
		return results;
	}

	//操作类型
	@Override
	public String getOperType() {
		return UnLoadConstant.OPER_TYPE_ORDER_TASK_REF.VERSION;
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
	 * @param  entity
	 * @return void    返回类型 
	 * @throws ArgumentInvalidException
	 * @author： 268974  wangzhili
	 */
	private void validate(OrderTaskRefreshEntity entity) throws ArgumentInvalidException{
		//检验点单任务刷新实体非空
		Argument.notNull(entity,"OrderTaskRefreshEntity");
		//检验部门编码非空
		Argument.hasText(entity.getDeptCode(), "entity.deptCode");
	}

	public void setPdaOrderTaskService(IPDAOrderTaskService pdaOrderTaskService) {
		this.pdaOrderTaskService = pdaOrderTaskService;
	}

	
	

}
