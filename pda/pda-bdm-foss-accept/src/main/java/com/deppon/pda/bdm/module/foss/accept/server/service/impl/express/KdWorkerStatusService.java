package com.deppon.pda.bdm.module.foss.accept.server.service.impl.express;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.framework.shared.util.Argument;
import com.deppon.foss.module.pickup.pda.api.shared.exception.PdaProcessException;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ExternalInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.accept.server.IPDAToOMSService;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.PdaOrderWorkerOmsDto;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.PdaOrderWorkerOmsResponseDto;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.WorkerStatusEntity;

/** 
  * @ClassName WorkerStatusService 
  * @Description TODO 
  * @author 245955
  * @date 2016-4-6 
  * 开启暂停服务类
*/
public class KdWorkerStatusService implements IBusinessService<Void, WorkerStatusEntity>{

	private IPDAToOMSService pdaToOMSService;
	
	public void setPdaToOMSService(IPDAToOMSService pdaToOMSService) {
		this.pdaToOMSService = pdaToOMSService;
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
		this.validate(asyncMsg, param);
		PdaOrderWorkerOmsDto pdaOrderWorkerOmsDto=new PdaOrderWorkerOmsDto();
		try {
			pdaOrderWorkerOmsDto.setBusinessArea(param.getBusinessArea());
			pdaOrderWorkerOmsDto.setCreatorCode(param.getCreatorCode());
			List<String> expressEmpCodes = new ArrayList<String>();
			String expressEmpCode = param.getCreatorCode();
			expressEmpCodes.add(expressEmpCode);
			pdaOrderWorkerOmsDto.setExpressEmpCodes(expressEmpCodes);
			pdaOrderWorkerOmsDto.setOperateSystem(param.getOperateSystem());
			pdaOrderWorkerOmsDto.setOperateType(param.getOperateType());
			pdaOrderWorkerOmsDto.setPdaNum(asyncMsg.getPdaCode());
		    /*if("OPEN".equals(pdaOrderWorkerOmsDto.getOperateType())){
				expressWorkerStatusService.openExpressWorker(expressWorkerStatusDto);
			}else{
				expressWorkerStatusService.stopExrpessWorker(expressWorkerStatusDto);			
			}*/
			//PDA开启/暂停接口发送数据至OMS开始
			String responseStr = pdaToOMSService.pdaSendWorkerToOMS(pdaOrderWorkerOmsDto);
			// 将返回信息转化成对象
			PdaOrderWorkerOmsResponseDto response = JSONObject.parseObject(responseStr, PdaOrderWorkerOmsResponseDto.class);
			//if(response == null || !"SUCCESS".equals(response.getIsSuccess())){
			if(!"SUCCESS".equals(response.getIsSuccess())){
				throw new PdaProcessException(response.getErrorMsg());
			}
		} catch (ExternalInterfaceException e){
			throw new ExternalInterfaceException(e.getCause(),e.getErrCode());
		} catch (Exception e){
			throw new ExternalInterfaceException(null,"开启/暂停数据未传入");
		}
		return null;
	}
	private void validate(AsyncMsg asyncMsg, WorkerStatusEntity entity) throws ArgumentInvalidException {
		Argument.notNull(entity, "entity");
		//开启/暂停类型
		Argument.notNull(entity.getOperateType(), "entity.operateType");
	}
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_ACCT_KD_WORKERSTATUS_ACTIVITY.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

}
