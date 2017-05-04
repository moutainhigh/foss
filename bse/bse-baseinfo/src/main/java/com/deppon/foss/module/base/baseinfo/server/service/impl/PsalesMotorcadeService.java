package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IMotorcadeDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISalesMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesMotorcadeEntity;
import com.deppon.foss.module.pickup.pda.api.server.service.IPsalesMotorcadeService;

public class PsalesMotorcadeService implements IPsalesMotorcadeService{

	private ISalesMotorcadeService salesMotorcadeService;
	private IMotorcadeDao motorcadeDao;
	public void setSalesMotorcadeService(
			ISalesMotorcadeService salesMotorcadeService) {
		this.salesMotorcadeService = salesMotorcadeService;
	}	
	public void setMotorcadeDao(IMotorcadeDao motorcadeDao) {
		this.motorcadeDao = motorcadeDao;
	}


	/**
	 * 调用SalesMotorcadeService查询营业部车队信息
	 */
	@Override
	public List<SalesMotorcadeEntity> querySalesMotorcadeExactByEntity(
			SalesMotorcadeEntity entity) {
		List<SalesMotorcadeEntity> salesMotorcadeEntityList=null;
		//查一下该车队是否为顶级车队，是的话直接查并返回，不是的话找顶级车队再查再返回
		MotorcadeEntity motor = motorcadeDao.queryMotorcadeByCode(entity.getMotorcadeCode());
		if("Y".equals(motor.getIsTopFleet())){
			salesMotorcadeEntityList = salesMotorcadeService
					.querySalesMotorcadeByEntity(entity, 0, java.lang.Integer.MAX_VALUE);			
		}else{
			if(motor.getParentOrgCode()==null||motor.getParentOrgCode().length()==0){
				return null;
			}
			SalesMotorcadeEntity entity2=new SalesMotorcadeEntity();
			entity2.setMotorcadeCode(motor.getParentOrgCode());
			salesMotorcadeEntityList = salesMotorcadeService
					.querySalesMotorcadeByEntity(entity2, 0, java.lang.Integer.MAX_VALUE);	
		}
		return salesMotorcadeEntityList;
	}
}

