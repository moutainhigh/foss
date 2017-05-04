package com.deppon.pda.bdm.module.foss.accept.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaDispatchOrderService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaForwardDiverDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.PdaForwardDiverEntity;

public class QueryForwardListByDriverCodeService implements IBusinessService<List<PdaForwardDiverEntity>, ScanMsgEntity> {

	
	IPdaDispatchOrderService pdaDispatchOrderService;
	
	public void setPdaDispatchOrderService(
			IPdaDispatchOrderService pdaDispatchOrderService) {
		this.pdaDispatchOrderService = pdaDispatchOrderService;
	}

	@Override
	public ScanMsgEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		return null;
	}

	@Override
	public List<PdaForwardDiverEntity> service(AsyncMsg asyncMsg,
			ScanMsgEntity param) throws PdaBusiException {
		List<PdaForwardDiverEntity> pdaForwardDiverEntitys = new ArrayList<PdaForwardDiverEntity>();
		try {
			List<PdaForwardDiverDto>  pdaForwardDiverDtos = pdaDispatchOrderService.queryForwardListByDriverCode(asyncMsg.getUserCode());
			if(pdaForwardDiverDtos!=null){
				for(PdaForwardDiverDto pdaForwardDiverDto : pdaForwardDiverDtos){
					PdaForwardDiverEntity pdaForwardDiverEntity = new PdaForwardDiverEntity();
					pdaForwardDiverEntity.setDriverCode(pdaForwardDiverDto.getDriverCode());
					pdaForwardDiverEntity.setDriverName(pdaForwardDiverDto.getDriverName());
					pdaForwardDiverEntitys.add(pdaForwardDiverEntity);
				}
				
			}
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		return pdaForwardDiverEntitys;
	}

	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_ACCT_QUERYFORWARDLIST_ACTIVITY.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

}
