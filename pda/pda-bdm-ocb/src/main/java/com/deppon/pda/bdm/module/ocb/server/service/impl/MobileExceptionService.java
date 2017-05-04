package com.deppon.pda.bdm.module.ocb.server.service.impl;

import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.OcbConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.ocb.server.dao.IMobileExceptionDao;
import com.deppon.pda.bdm.module.ocb.shared.domain.MobileExceptionBean;

public class MobileExceptionService implements IBusinessService<String, MobileExceptionBean> {

	private IMobileExceptionDao mobileExceptionDao;

	@Override
	public MobileExceptionBean parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		MobileExceptionBean bean = JsonUtil.parseJsonToObject(MobileExceptionBean.class,asyncMsg.getContent());
		return bean;
	}

	@Override
	public String service(AsyncMsg asyncMsg, MobileExceptionBean param) throws PdaBusiException {		
		mobileExceptionDao.saveMobileException(param);
		return param.getUuid();
	}

	@Override
	public String getOperType() {
		// TODO Auto-generated method stub
		return OcbConstant.OPER_TYPE_OCB_EXCP_RCV.VERSION;
	}

	@Override
	public boolean isAsync() {
		// TODO Auto-generated method stub
		return false;
	}
	public void setMobileExceptionDao(IMobileExceptionDao mobileExceptionDao) {
		this.mobileExceptionDao = mobileExceptionDao;
	}

}
