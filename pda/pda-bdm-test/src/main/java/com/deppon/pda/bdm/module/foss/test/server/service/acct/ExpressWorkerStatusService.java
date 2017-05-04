package com.deppon.pda.bdm.module.foss.test.server.service.acct;

import java.util.List;

import com.deppon.foss.module.pickup.order.api.server.service.IExpressWorkerStatusService;
import com.deppon.foss.module.pickup.order.api.shared.domain.ExpressWorkerStatusEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.ExpressWorkerCompleteDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.ExpressWorkerStatusDto;
import com.deppon.foss.module.pickup.order.api.shared.vo.ExpressWorerStatusConditionVo;

public class ExpressWorkerStatusService implements IExpressWorkerStatusService{

	@Override
	public int openExpressWorker(ExpressWorkerStatusDto arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ExpressWorkerCompleteDto> queryExpressWorkerComplete(
			ExpressWorerStatusConditionVo arg0) {
		
		return null;
	}

	@Override
	public ExpressWorkerStatusEntity queryOneByEmpcode(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int stopExrpessWorker(ExpressWorkerStatusDto arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

}
