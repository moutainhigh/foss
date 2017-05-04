package com.deppon.foss.module.pickup.waybill.server.service.impl;

import com.deppon.foss.module.pickup.waybill.api.server.dao.IActualFreightBIDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightBIService;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightBIEntity;

public class ActualFreightBIService implements IActualFreightBIService {
	
	private IActualFreightBIDao actualFreightBIDao;
	public void setActualFreightBIDao(IActualFreightBIDao actualFreightBIDao) {
		this.actualFreightBIDao = actualFreightBIDao;
	}


	@Override
	public int insert(ActualFreightBIEntity actualFreightBIEntity) {
		return actualFreightBIDao.insert(actualFreightBIEntity);
	}

}
