package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IAgentDeliveryFeeSchemeDao;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.AgentDeliveryFeeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.AgentDeliveryFeeSchemeEntity;
import com.deppon.foss.module.pickup.pricing.dubbo.api.server.service.IAgentDeliveryFeeSchemeService;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.exception.AgentDeliveryFeeSchemeException;
import com.deppon.foss.util.CollectionUtils;
/**
 * @author 092020-lipengfei
 * 偏线代理送货费方案service
 */
public class AgentDeliveryFeeSchemeDubboService implements IAgentDeliveryFeeSchemeService {
	/**
	 * 偏线代理送货费方案dao
	 */
	private IAgentDeliveryFeeSchemeDao agentDeliveryFeeSchemeDao;
	public void setAgentDeliveryFeeSchemeDao(
			IAgentDeliveryFeeSchemeDao agentDeliveryFeeSchemeDao) {
		this.agentDeliveryFeeSchemeDao = agentDeliveryFeeSchemeDao;
	}
	/**
	 * @author 092020-lipengfei
	 * @description 查询偏线代理送货费方案（产品类型，目的站，重量，体积）(提供给中转使用)
	 * @param productType
	 * @param targetOrgCode
	 * @param weight
	 * @param volumn
	 * @return Integer
	 */
	@Override
	public com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.AgentDeliveryFeeSchemeEntity queryAgentDeliveryCharge(
			String productType, String targetOrgCode, BigDecimal weight,
			BigDecimal volumn) throws AgentDeliveryFeeSchemeException{
		if(StringUtil.isBlank(targetOrgCode)){
			throw new AgentDeliveryFeeSchemeException("目的站不能为空");
		}
		//根据目的站查询偏线代理送货费方案
		AgentDeliveryFeeSchemeEntity entity=agentDeliveryFeeSchemeDao.querySchemeByagentDeptCode(targetOrgCode);
		if(null==entity){
			throw new AgentDeliveryFeeSchemeException("目的站【"+targetOrgCode+"】不存在送货费方案");
		}
		//根据重量、体积、方案ID查询送货费
		List<AgentDeliveryFeeEntity> feeEntityList=agentDeliveryFeeSchemeDao.queryAgentDeliveryFeeBySchemeId(entity.getId());
		//筛选符合条件的内容
		List<AgentDeliveryFeeEntity> agenTitylist = new ArrayList<AgentDeliveryFeeEntity>();
		if(CollectionUtils.isNotEmpty(feeEntityList)){
			for(AgentDeliveryFeeEntity aentity:feeEntityList){
				BigDecimal startPoint = aentity.getStartPoint();
				BigDecimal endPoint= aentity.getTerminalPoint();
				if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(aentity.getPricingManner())){
					if(volumn.compareTo(startPoint)>=0 && volumn.compareTo(endPoint)<0){
						agenTitylist.add(aentity);
					}
				}else if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(aentity.getPricingManner())){
					if(weight.compareTo(startPoint)>=0 && weight.compareTo(endPoint)<0){
						agenTitylist.add(aentity);
					}
				}
			}
		}
		entity.setFeeEntityList(agenTitylist);
		return this.convertorEntity(entity);
	}
	private com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.AgentDeliveryFeeSchemeEntity convertorEntity(
			AgentDeliveryFeeSchemeEntity entity) {
		com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.AgentDeliveryFeeSchemeEntity dubboEntity = 
				new com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.AgentDeliveryFeeSchemeEntity();
		dubboEntity.setAgentDeptCode(entity.getAgentDeptCode());
    	dubboEntity.setAgentDeptName(entity.getAgentDeptName());
    	dubboEntity.setCreateDate(entity.getCreateDate());
    	dubboEntity.setCreateUser(entity.getCreateUser());
    	dubboEntity.setId(entity.getId());
    	dubboEntity.setModifyDate(entity.getModifyDate());
    	dubboEntity.setModifyUser(entity.getModifyUser());
    	dubboEntity.setModifyUserName(entity.getModifyUserName());
    	dubboEntity.setRemark(entity.getRemark());
    	dubboEntity.setSchemeName(entity.getSchemeName());
    	dubboEntity.setTransportType(entity.getTransportType());
    	dubboEntity.setVersionNo(entity.getVersionNo());
    	List<com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.AgentDeliveryFeeEntity> feeDubboEntityList = 
    			new ArrayList<com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.AgentDeliveryFeeEntity>();
    	List<AgentDeliveryFeeEntity> feeEntityList = entity.getFeeEntityList();
    	com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.AgentDeliveryFeeEntity feeEntity = null;
    	for(int i=0;i<feeEntityList.size();i++){
    		AgentDeliveryFeeEntity agenTity = feeEntityList.get(i);
    		feeEntity =new com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.AgentDeliveryFeeEntity();
    		feeEntity.setChargeStandard(agenTity.getChargeStandard());
    		feeEntity.setCreateDate(agenTity.getCreateDate());
    		feeEntity.setCreateUser(agenTity.getCreateUser());
    		feeEntity.setId(agenTity.getId());
    		feeEntity.setLowestPrice(agenTity.getLowestPrice());
    		feeEntity.setModifyDate(agenTity.getModifyDate());
    		feeEntity.setModifyUser(agenTity.getModifyUser());
    		feeEntity.setPricingManner(agenTity.getPricingManner());
    		feeEntity.setSchemeId(agenTity.getSchemeId());
    		feeEntity.setStartPoint(agenTity.getStartPoint());
    		feeEntity.setTerminalPoint(agenTity.getTerminalPoint());
    		feeEntity.setVersionNo(agenTity.getVersionNo());
    		feeDubboEntityList.add(feeEntity);
    	}
    	dubboEntity.setFeeEntityList(feeDubboEntityList);
		return dubboEntity;
	}

}
