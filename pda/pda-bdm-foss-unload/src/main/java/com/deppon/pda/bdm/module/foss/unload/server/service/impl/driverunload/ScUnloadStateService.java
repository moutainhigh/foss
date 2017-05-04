package com.deppon.pda.bdm.module.foss.unload.server.service.impl.driverunload;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.unload.api.server.service.ISCPDAUnloadTaskService;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.UnLoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.driverunload.ScUnloadStateEntity;

/**
 * 记录第一次扫描为司机到达时间
 * @ClassName ScUnloadStateService.java 
 * @Description 
 * @author 245955
 * @date 2015-5-18
 */
public class ScUnloadStateService  implements IBusinessService<Void, ScUnloadStateEntity> {
	private ISCPDAUnloadTaskService scpdaUnloadTaskService;
	private static final Log LOG = LogFactory.getLog(ScUnloadStateEntity.class);
	
	/**
	 * 解析包头
	 * @description 
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-5-18
	 */
	@Override
	public ScUnloadStateEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		ScUnloadStateEntity scUnloadStateEntity= JsonUtil.parseJsonToObject(ScUnloadStateEntity.class, asyncMsg.getContent());
		return scUnloadStateEntity;
	}

	/**
	 * 服务方法
	 * @description 
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException
	 * @author 245955
	 * @date 2015-5-18
	 */
	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, ScUnloadStateEntity param) throws PdaBusiException {
		this.validate(param);
		try {		
		 scpdaUnloadTaskService.scUnloadState(param.getTaskCode(), param.getReceptCode());
		} catch (BusinessException e) {
			LOG.error("更新记录异常："+e);
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		return null;
	}

	/**
	 * 操作类型
	 * @description 
	 * @return
	 * @author 245955
	 * @date 2015-5-18
	 */
	@Override
	public String getOperType() {
		return UnLoadConstant.OPER_TYPE_UNLOAD_TRAN_COUNT.VERSION;
	}

	/**
	 * 是否异步
	 * @description 
	 * @return
	 * @author 245955
	 * @date 2015-5-18
	 */
	@Override
	public boolean isAsync() {
		return false;
	}
	/**
	 * 参数验证
	 * @description 
	 * @param submitUnldTask
	 * @throws ArgumentInvalidException
	 * @author 245955
	 * @date 2015-4-15
	 */
	private void validate(ScUnloadStateEntity scUnloadStateEntity) throws ArgumentInvalidException{
		//交接单号
		Argument.notNull(scUnloadStateEntity.getTaskCode(), "scUnloadStateEntity.taskCode");
		//任务号
		Argument.hasText(scUnloadStateEntity.getReceptCode(),"scUnloadStateEntity.receptCode");
	}

	public void setScpdaUnloadTaskService(ISCPDAUnloadTaskService scpdaUnloadTaskService) {
		this.scpdaUnloadTaskService = scpdaUnloadTaskService;
	}
}
