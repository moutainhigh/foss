package com.deppon.pda.bdm.module.foss.accept.server.service.impl;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.deppon.foss.module.pickup.order.api.server.service.IExpressWorkerStatusService;
import com.deppon.foss.module.pickup.order.api.shared.define.PdaSignStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.domain.ExpressWorkerStatusEntity;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.foss.accept.server.IPDAToOMSService;

/** 
  * @ClassName QueryOneByEmpcodeService 
  * @Description TODO 
  * @author cbb 
  * @date 2014-6-3 下午7:37:01 
*/ 
public class QueryOneByEmpcodeService implements IBusinessService<String, ScanMsgEntity>{
	
	//private Logger log = Logger.getLogger(getClass());
	
	IExpressWorkerStatusService expressWorkerStatusService;
	 
	public void setExpressWorkerStatusService(
			IExpressWorkerStatusService expressWorkerStatusService) {
		this.expressWorkerStatusService = expressWorkerStatusService;
	}

    private IPDAToOMSService pdaToOMSService;
    
	public void setPdaToOMSService(IPDAToOMSService pdaToOMSService) {
		this.pdaToOMSService = pdaToOMSService;
	}
	
	@Override
	public ScanMsgEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		return null;
	}

	@Override
	public String service(AsyncMsg asyncMsg, ScanMsgEntity param)
			throws PdaBusiException {
		String userType = asyncMsg.getUserType();
		ExpressWorkerStatusEntity expressWorkerStatusEntity = null;
		if(PdaSignStatusConstants.USER_TYPE_COURIER.equals(userType)){
			String responseStr = pdaToOMSService.pdaKdQueryWorkerStatusByDriverCodeOMS(asyncMsg.getUserCode());
			expressWorkerStatusEntity = 
	  				(ExpressWorkerStatusEntity)JSONObject.toBean(JSONObject.fromObject(responseStr), ExpressWorkerStatusEntity.class);
		}else{
			// TODO Auto-generated method stub
			expressWorkerStatusEntity = expressWorkerStatusService.queryOneByEmpcode(asyncMsg.getUserCode());
		}
		if(expressWorkerStatusEntity!=null && "Y".equals(expressWorkerStatusEntity.getActive())){
			return (String)expressWorkerStatusEntity.getWorkStatus();
		}
		return null;
	}

	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_ACCT_QUERYONEBYEMPCODE_ACTIVITY.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

}
