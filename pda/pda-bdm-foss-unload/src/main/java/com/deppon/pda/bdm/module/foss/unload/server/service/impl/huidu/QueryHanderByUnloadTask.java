package com.deppon.pda.bdm.module.foss.unload.server.service.impl.huidu;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.Argument;
import com.deppon.foss.module.transfer.unload.api.server.service.IPdaUnloadOptimizeService;
import com.deppon.foss.module.transfer.unload.api.shared.dto.PdaUnloadTaskDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.UnLoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.QryUnldInfo;

public class QueryHanderByUnloadTask implements IBusinessService<PdaUnloadTaskDto, QryUnldInfo>{
	private IPdaUnloadOptimizeService pdaUnloadOptimizeService;
	private static final Logger logger = Logger.getLogger(QueryHanderByUnloadTask.class);

	@Override
	public QryUnldInfo parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		QryUnldInfo entity = JsonUtil.parseJsonToObject(QryUnldInfo.class, asyncMsg.getContent());
		return entity;
	}

	@Override
	public PdaUnloadTaskDto service(AsyncMsg asyncMsg, QryUnldInfo param) throws PdaBusiException {
        //校验参数	
		this.validate(param);
		PdaUnloadTaskDto dto = null;
        try{
        	logger.info("根据卸车任务号查询交接单号"+param.getTaskCode());
        	//调用foss接口
    		dto = pdaUnloadOptimizeService.queryPdaUnloadTaskByTaskNo(param.getTaskCode());
        } catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		} catch(Exception e){
			throw new FossInterfaceException(e.getCause(),null);
		}
        logger.info("foss的返回值"+dto);
        
		return dto;
	}

	@Override
	public String getOperType() {
		return UnLoadConstant.OPER_TYPE_HANDER_BY_TASK.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}
	
	private void validate(QryUnldInfo entity) throws ArgumentInvalidException{
		//检验取消卸车扫描非空
		Argument.notNull(entity,"QryUnldInfo");
		//任务号非空
		Argument.hasText(entity.getTaskCode(), "QryUnldInfo.taskCode");
	}

	public void setPdaUnloadOptimizeService(
			IPdaUnloadOptimizeService pdaUnloadOptimizeService) {
		this.pdaUnloadOptimizeService = pdaUnloadOptimizeService;
	}


}
