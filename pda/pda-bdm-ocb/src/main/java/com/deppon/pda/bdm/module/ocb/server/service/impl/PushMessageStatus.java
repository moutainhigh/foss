package com.deppon.pda.bdm.module.ocb.server.service.impl;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.pickup.pda.api.server.service.IPdaWaybillService;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.OcbConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;

public class PushMessageStatus implements IBusinessService<String, Void> {
	//private static final Log LOG = LogFactory.getLog(PushMessageStatus.class);
	private IPdaWaybillService pdaWaybillService;

	public void setPdaWaybillService(IPdaWaybillService pdaWaybillService) {
		this.pdaWaybillService = pdaWaybillService;
	}

	@Override
	public Void parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String service(AsyncMsg asyncMsg, Void param)
			throws PdaBusiException {
		String waybillCode = asyncMsg.getContent();
		String rtn = "失败";
		if (StringUtils.isNotEmpty(waybillCode)) {
			int result = pdaWaybillService.pushMessageStatus(waybillCode);
			if (result == 1) {
				rtn = "成功";
			}
		} else {
			rtn = "传入运单号为空";
		}
		return rtn;
	}

	@Override
	public String getOperType() {
		return OcbConstant.OPER_TYPE_OCB_PMS_WBC.VERSION;
	}

	@Override
	public boolean isAsync() {
		// TODO Auto-generated method stub
		return false;
	}

}
