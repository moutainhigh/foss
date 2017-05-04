package com.deppon.pda.bdm.module.foss.load.server.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.module.transfer.load.api.server.service.ISealInformationService;
import com.deppon.foss.module.transfer.load.api.shared.domain.OptTruckDepartArriveEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.OptTruckTaskResponseEntity;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.LoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.load.shared.exception.LoadException;


/**
 * 装车-WK查询车辆到达/出发信息
 * @author LiuLiPeng - PDA后台 - 314587
 * @date 2016-05-06 17:02:33
 * @version 1.0
 */
public class LoadSearchCarArriveService implements IBusinessService<List<OptTruckTaskResponseEntity>, OptTruckDepartArriveEntity>{
	private static final Log LOG = LogFactory.getLog(LoadSearchCarArriveService.class);
	
	private ISealInformationService itfSealInfomationService;
	
	@Override
	public OptTruckDepartArriveEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		LOG.info("**************解析包体-开始*************");
		OptTruckDepartArriveEntity result = JsonUtil.parseJsonToObject(OptTruckDepartArriveEntity.class, asyncMsg.getContent());
		LOG.info("**************解析包体-结束*************");
		return result;
	}

	@Override
	public List<OptTruckTaskResponseEntity> service(AsyncMsg asyncMsg, OptTruckDepartArriveEntity param) throws PdaBusiException {
		LOG.info("**************开始执行服务方法*************");
		List<OptTruckTaskResponseEntity> result = null;
		try {
			result = itfSealInfomationService.queryTruckTaskByByDeptCodeORCarNum(param);
			if(result.size()==0){
				throw new LoadException("车牌号有误，或找不到目标，请核实信息！");
			}
		} catch (PdaBusiException e) {
			LOG.error("装车 “WK查询车辆到达/出发信息” FOSS接口异常：LoadNewLableAddService", e);
		}
		LOG.info("**************结束执行服务方法*************");
		return result;
	}

	@Override
	public String getOperType() {
		return LoadConstant.WK_LOAD_SEARCH_CAR_ARRIVE_OR_DEPART.VERSION;
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
