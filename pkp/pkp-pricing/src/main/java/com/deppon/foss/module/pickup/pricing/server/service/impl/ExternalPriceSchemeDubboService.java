package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.base.baseinfo.api.server.service.IAreaAddressService;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IExternalPriceSchemeDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ExternalPriceSchemeEntity;
import com.deppon.foss.module.pickup.pricing.dubbo.api.server.service.IExternalPriceSchemeService;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.exception.ExternalPriceSchemeException;

/**
 * @author 092020-lipengfei
 *	偏线外发价格方案Service
 */
public class ExternalPriceSchemeDubboService implements IExternalPriceSchemeService {
	
	/**
	 * 偏线外发价格方案Dao
	 * */
	private IExternalPriceSchemeDao externalPriceSchemeDao;
	
	private IAreaAddressService areaAddressService;
	public IAreaAddressService getAreaAddressService() {
		return areaAddressService;
	}
	public void setAreaAddressService(IAreaAddressService areaAddressService) {
		this.areaAddressService = areaAddressService;
	}
	
	public void setExternalPriceSchemeDao(
			IExternalPriceSchemeDao externalPriceSchemeDao) {
		this.externalPriceSchemeDao = externalPriceSchemeDao;
	}
	
	
	/**
	 * @author 092020-lipengfei
	 * @description 根据目的站，外发外场，运输类型，外发单生成时间 查询偏线外发价格方案
	 * @param targetOrgCode
	 * @param outOrgCode
	 * @param transportType
	 * @param externalBillCreateTime
	 * @return ExternalPriceSchemeEntity
	 */
	@Override
	public com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.ExternalPriceSchemeEntity queryAgentOutPriceInfo(
			String targetOrgCode, String outOrgCode, String transportType,
			Date externalBillCreateTime) throws ExternalPriceSchemeException{
		StringBuffer tempBuffer = new StringBuffer();
		if (StringUtils.isEmpty(targetOrgCode)) {
			tempBuffer.append("targetOrgCode ");
		}
		if (StringUtils.isEmpty(outOrgCode)) {
			tempBuffer.append(" outOrgCode ");
		}
		if (StringUtils.isEmpty(transportType)) {
			tempBuffer.append(" transportType  ");
		}
		if (null==externalBillCreateTime) {
			tempBuffer.append(" externalBillCreateTime ");
		}
		if (tempBuffer != null && StringUtils.isNotEmpty(tempBuffer.toString())) {
			throw new ExternalPriceSchemeException("",tempBuffer.toString() + " 不能为空！");
		}
		ExternalPriceSchemeEntity entity = externalPriceSchemeDao.queryAgentOutPriceInfo(targetOrgCode, outOrgCode, transportType, externalBillCreateTime);
		//根据参数查询方案
		return this.convertorEntity(entity);
	}
	
	private com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.ExternalPriceSchemeEntity convertorEntity(ExternalPriceSchemeEntity entity){
		com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.ExternalPriceSchemeEntity dubboEntity = 
				new com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.ExternalPriceSchemeEntity();
		if(entity==null){
			return null;
		}
		dubboEntity.setActive(entity.getActive());
    	dubboEntity.setAgentDeptCode(entity.getAgentDeptCode());
    	dubboEntity.setAgentDeptName(entity.getAgentDeptName());
    	dubboEntity.setBeginTime(entity.getBeginTime());
    	dubboEntity.setCityCode(entity.getCityCode());
    	dubboEntity.setCityName(entity.getCityName());
    	dubboEntity.setCountyCode(entity.getCountyCode());
    	dubboEntity.setCountyName(entity.getCountyName());
    	dubboEntity.setCreateDate(entity.getCreateDate());
    	dubboEntity.setCreateUser(entity.getCreateUser());
    	dubboEntity.setCurrentUsedVersion(entity.getCurrentUsedVersion());
    	dubboEntity.setEndTime(entity.getEndTime());
    	dubboEntity.setExternalOrgCode(entity.getExternalOrgCode());
    	dubboEntity.setExternalOrgName(entity.getExternalOrgName());
    	dubboEntity.setHeavyPrice(entity.getHeavyPrice());
    	dubboEntity.setId(entity.getId());
    	dubboEntity.setLightPrice(entity.getLightPrice());
    	dubboEntity.setLowestPrice(entity.getLowestPrice());
    	dubboEntity.setModifyDate(entity.getModifyDate());
    	dubboEntity.setModifyUser(entity.getModifyUser());
    	dubboEntity.setProvCode(entity.getProvCode());
    	dubboEntity.setProvName(entity.getProvName());
    	dubboEntity.setSchemeName(entity.getSchemeName());
    	dubboEntity.setTransportType(entity.getTransportType());
    	dubboEntity.setVersionNo(entity.getVersionNo());
    	return dubboEntity;
	}
	
	
	
}
