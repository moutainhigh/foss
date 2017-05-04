package com.deppon.foss.module.generalquery.server.service.impl;

import com.deppon.foss.module.generalquery.server.dao.IEagleEyeDao;
import com.deppon.foss.module.generalquery.shared.domain.EagleEyeEntity;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.GeneralQueryConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
/**
 * @Description 查询鹰眼位置开关
 * @author  268974
 * @date 2016-01-06
 */
public class EagleEyeService implements IBusinessService<EagleEyeEntity, Void>{

	private IEagleEyeDao eagleEyeDao;
	
	


	public void setEagleEyeDao(IEagleEyeDao eagleEyeDao) {
		this.eagleEyeDao = eagleEyeDao;
	}

	@Override
	public Void parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		return null;
	}

	@Override
	public EagleEyeEntity service(AsyncMsg asyncMsg, Void param)
			throws PdaBusiException {
		EagleEyeEntity entity = new EagleEyeEntity();
		entity = eagleEyeDao.queryEagleEyeStatus();
		return entity;
	}

	@Override
	public String getOperType() {
		return GeneralQueryConstant.OPER_TYPE_AID_EAGLEEYE_POSITION.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

}
