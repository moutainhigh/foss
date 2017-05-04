package com.deppon.pda.bdm.module.foss.load.server.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.module.transfer.load.api.server.service.ISealInformationService;
import com.deppon.foss.module.transfer.load.api.shared.domain.OptTruckTaskEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ResponseJsonInfoEntity;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.load.shared.exception.LoadException;

/**
 * 装车-WK发车/取消发车/确认车辆到达
 * @author LiuLiPeng - PDA后台 - 314587
 * @date 2016-05-06 17:02:33
 * @version 1.0
 */
public class LoadDepartOrCancelService implements IBusinessService<String, OptTruckTaskEntity>{
	private static final Log LOG = LogFactory.getLog(LoadDepartOrCancelService.class);
	
	private ISealInformationService itfSealInfomationService;
	
	@Override
	public OptTruckTaskEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		LOG.info("**************解析包体-开始*************");
		OptTruckTaskEntity result = JsonUtil.parseJsonToObject(OptTruckTaskEntity.class, asyncMsg.getContent());
		LOG.info("**************解析包体-结束*************");
		return result;
	}

	@Override
	public String service(AsyncMsg asyncMsg, OptTruckTaskEntity param) throws PdaBusiException {
		LOG.info("**************开始执行服务方法*************");
		ResponseJsonInfoEntity bean = itfSealInfomationService.updateDepartureState(param);
		String result = "";
		if(bean.getState()=="1"){
			result = bean.getReturnInfo();
		}else{
			throw new LoadException(bean.getReturnInfo());
		}
		LOG.info("**************结束执行服务方法*************");
		return result;
	}

	@Override
	public String getOperType() {
		return LoadConstant.WK_LOAD_DEPART_OR_CANCEL_OR_CANCEL_DEPART.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

	public ISealInformationService getItfSealInfomationService() {
		return itfSealInfomationService;
	}

	public void setItfSealInfomationService(ISealInformationService itfSealInfomationService) {
		this.itfSealInfomationService = itfSealInfomationService;
	}

}
