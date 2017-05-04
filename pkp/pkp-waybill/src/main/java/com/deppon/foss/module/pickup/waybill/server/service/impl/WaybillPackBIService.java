package com.deppon.foss.module.pickup.waybill.server.service.impl;


import java.util.Date;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPackBIDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillPackBIService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPackBIEntity;
import com.deppon.foss.util.define.FossConstants;

public class WaybillPackBIService implements IWaybillPackBIService {

	private IWaybillPackBIDao waybillPackBIDao; 
	
	protected final static Logger LOG = LoggerFactory.getLogger(WaybillPackBIService.class.getName());
	
	@Override
	public int deleteByPrimaryKey(String id) {
		return 0;
	}
	
	@Override
	public int addWaybillPackBIEntity(WaybillEntity waybillEntity) {
		LOG.info("进入 addWaybillPackBIEntity ");
		if(waybillEntity != null && waybillEntity.getWaybillNo() != null){
			WaybillPackBIEntity waybillPackBIEntity =null;
			waybillPackBIEntity = waybillPackBIDao.queryByWaybillNo(waybillEntity.getWaybillNo());
			Date date = new Date();
			if(waybillPackBIEntity != null){
				//waybillPackBI表进行拉链表设计
				waybillPackBIEntity.setModifyTime(date);
				waybillPackBIEntity.setActive(FossConstants.INACTIVE);
				waybillPackBIDao.updateByWaybillNo(waybillPackBIEntity);
				
				try {
					PropertyUtils.copyProperties(waybillPackBIEntity, waybillEntity);
				} catch (Exception e) {
					LOG.error("Excepton", e);
				}
				waybillPackBIEntity.setCreateTime(date); 
				waybillPackBIEntity.setModifyTime(new Date(PricingConstants.ENDTIME));
				return waybillPackBIDao.addWaybillPackBIEntity(waybillPackBIEntity);
			}else{
				try {
					waybillPackBIEntity = new WaybillPackBIEntity();
					PropertyUtils.copyProperties(waybillPackBIEntity, waybillEntity);
				} catch (Exception e) {
					LOG.error("Excepton", e);
				}
				if(waybillPackBIEntity != null){
					waybillPackBIEntity.setCreateTime(date); 
					waybillPackBIEntity.setModifyTime(new Date(PricingConstants.ENDTIME));
					return waybillPackBIDao.addWaybillPackBIEntity(waybillPackBIEntity);
				}else{
					return 0;
				}
			}
		}else{
			return 0;
		} 
	}

	@Override
	public int updateByWaybillNo(WaybillPackBIEntity waybillPackBIEntity) {
		return waybillPackBIDao.updateByWaybillNo(waybillPackBIEntity);
	}

	@Override
	public WaybillPackBIEntity queryByWaybillNo(String waybillNo) {
		return waybillPackBIDao.queryByWaybillNo(waybillNo);
	}

	public void setWaybillPackBIDao(IWaybillPackBIDao waybillPackBIDao) {
		this.waybillPackBIDao = waybillPackBIDao;
	}
	
}
