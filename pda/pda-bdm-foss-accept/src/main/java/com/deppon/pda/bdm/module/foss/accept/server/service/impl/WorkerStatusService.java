package com.deppon.pda.bdm.module.foss.accept.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.order.api.server.service.IExpressWorkerStatusService;
import com.deppon.foss.module.pickup.order.api.shared.dto.ExpressWorkerStatusDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.WorkerStatusEntity;

/** 
  * @ClassName WorkerStatusService 
  * @Description TODO 
  * @author cbb 
  * @date 2014-6-3 下午6:40:16 
*/ 
public class WorkerStatusService implements IBusinessService<Void, WorkerStatusEntity>{

	//private Logger log = Logger.getLogger(getClass());
	
	IExpressWorkerStatusService expressWorkerStatusService;
	
	public void setExpressWorkerStatusService(
			IExpressWorkerStatusService expressWorkerStatusService) {
		this.expressWorkerStatusService = expressWorkerStatusService;
	}

	@Override
	public WorkerStatusEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		//解析包体
		WorkerStatusEntity workerStatusEntity = JsonUtil.parseJsonToObject(WorkerStatusEntity.class, asyncMsg.getContent());
		return workerStatusEntity;
	}

	@Override
	public Void service(AsyncMsg asyncMsg, WorkerStatusEntity param)
			throws PdaBusiException {
		// TODO Auto-generated method stub
		
		ExpressWorkerStatusDto expressWorkerStatusDto = new ExpressWorkerStatusDto();
		try {
			expressWorkerStatusDto.setBusinessArea(param.getBusinessArea());
			expressWorkerStatusDto.setCreatorCode(param.getOperateSystem());
			List<String> expressEmpCodes = new ArrayList<String>();
			String expressEmpCode = param.getCreatorCode();
			expressEmpCodes.add(expressEmpCode);
			expressWorkerStatusDto.setExpressEmpCodes(expressEmpCodes);
			expressWorkerStatusDto.setOperateSystem(param.getOperateSystem());
			expressWorkerStatusDto.setOperateType(param.getOperateType());
			expressWorkerStatusDto.setPDANum(asyncMsg.getPdaCode());
			if("OPEN".equals(expressWorkerStatusDto.getOperateType())){
				expressWorkerStatusService.openExpressWorker(expressWorkerStatusDto);
			}else{
				//NCI项目bug修复 司机快递员接收订单处理是点击暂停报错
				expressWorkerStatusService.stopExrpessWorker(expressWorkerStatusDto,asyncMsg.getUserType());			
			}
			
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}		
		return null;
	}

	@Override
	public String getOperType() {
		// TODO Auto-generated method stub
		return AcceptConstant.OPER_TYPE_ACCT_WORKERSTATUS_ACTIVITY.VERSION;
	}

	@Override
	public boolean isAsync() {
		// TODO Auto-generated method stub
		return false;
	}

}
