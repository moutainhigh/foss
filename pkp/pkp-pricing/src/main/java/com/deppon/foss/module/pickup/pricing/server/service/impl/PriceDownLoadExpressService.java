package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.base.util.ClientUpdateDataPack;
import com.deppon.foss.base.util.DataBundle;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPricePlanDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceValuationDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionExpressDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPriceDownLoadExpressService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionExpressService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PricePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnExpressEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionExpressEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceValuationEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * @author ibm-foss-sxw
 *
 */
public class PriceDownLoadExpressService implements IPriceDownLoadExpressService{

	private IPriceValuationDao priceValuationDao;  
	private IRegionExpressDao regionExpressDao;
	private static final int BEFOREAMOUNT = 200;
	private IRegionExpressService regionExpressService;
	  IPricePlanDao pricePlanDao;
	/**
	 * 
	 */
	private static final int THOUSAND = 1000;
	
	
	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IPriceDownLoadExpressService#downPriceRegionExpressServerData(com.deppon.foss.base.util.ClientUpdateDataPack)
	 */
	@Override
	public DataBundle downPriceRegionExpressServerData(
			ClientUpdateDataPack clientInfo) {
    	DataBundle dataBundle = new DataBundle();
		if(null == clientInfo){
		    return dataBundle;
		}else{
			PriceRegionExpressEntity entity = new PriceRegionExpressEntity();
		    if(clientInfo.getLastUpdateTime()!=null)
		    {
		    	entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
		    }
		    entity.setActive(FossConstants.ACTIVE);
		    return  dataBundle.setObject(regionExpressDao.selectPriceRegionByCondition(entity));
		}
    }

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IPriceDownLoadExpressService#downPriceRegionExpressOrgDetailServerData(com.deppon.foss.base.util.ClientUpdateDataPack)
	 */
	@Override
	public DataBundle downPriceRegionExpressOrgDetailServerData(
			ClientUpdateDataPack clientInfo) {
    	DataBundle dataBundle = new DataBundle();
		if(null == clientInfo){
		    return dataBundle;
		}else{
			PriceRegioOrgnExpressEntity entity = new PriceRegioOrgnExpressEntity();
		    if(clientInfo.getLastUpdateTime()!=null){
		    	entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
		    }
		    entity.setRegionNature(PricingConstants.PRICING_REGION);
		    entity.setActive(FossConstants.ACTIVE);
		    return  dataBundle.setObject(regionExpressDao.searchRegionOrgByCondition(entity));
		}
    }

	
	public DataBundle downPricingValuationRegionValueAddServerData(ClientUpdateDataPack clientInfo) {
		DataBundle dataBundle = new DataBundle();
    	if(null == clientInfo || null == clientInfo.getOrgCode()){
    	    return dataBundle;
    	} 
    	//根据传入参数获取始发区域ID
		String startRegionId = regionExpressService.findRegionOrgByDeptNo(clientInfo.getOrgCode(), new Date(), null,PricingConstants.PRICING_REGION); 
		if(StringUtil.isNotBlank(startRegionId)) {
			PriceValuationEntity entity = new PriceValuationEntity();
			if(clientInfo.getRegionId() != null && !startRegionId.equals(clientInfo.getRegionId())) {
				dataBundle.setNeedDeleteLocalData(FossConstants.YES);
				entity.setBeginTime(new Date());
				entity.setEndTime(new Date());
			} else if(clientInfo.getRegionId() != null && clientInfo.getLastUpdateTime()!=null) {
		    	entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
			}
		    entity.setActive(FossConstants.ACTIVE);
		    entity.setType(PricingConstants.VALUATION_TYPE_REGIONVALUEADDED);
		    entity.setDeptRegionId(startRegionId);//ee17fcab-0b35-4e78-8b2f-b2cb671e870a
		    List<PriceValuationEntity> valuationEntities = new ArrayList<PriceValuationEntity>();
		    /**
		     * 根据指定区域查询
		     */
		    List<PriceValuationEntity> priceValList1 =  null;
		    if(FossConstants.YES.equals(clientInfo.getPagination())){
		    	priceValList1 =  priceValuationDao.queryByCoditionForDownloadByPage(
		    			entity, (clientInfo.getSyncPage())*THOUSAND -(BEFOREAMOUNT* clientInfo.getSyncPage())  , THOUSAND);	
		    	
		    }else{
		    	priceValList1 =  priceValuationDao.queryByCoditionForDownload(entity);		
		    }
		    		
		    	    		
		    		
		    valuationEntities.addAll(priceValList1);
//		    if(CollectionUtils.isNotEmpty(priceValList1)) {
//		    	//List<<PriceCriteriaDetailEntity>  criteriaDetailEntities = 
//				//if (CollectionUtils.isNotEmpty(criteriaDetailEntities)) {
//					for (int i = 0; i < priceValList1.size(); i++) {
//						PriceValuationEntity valuationEntity = priceValList1.get(i);
//						List<PriceCriteriaDetailEntity> priceCriteriaDetailEntities = new ArrayList<PriceCriteriaDetailEntity>();
//						for (int j = 0; j < criteriaDetailEntities.size(); j++) {
//							PriceCriteriaDetailEntity priceCriteriaDetailEntity = criteriaDetailEntities.get(j);
//							if(StringUtils.equals(valuationEntity.getId(), priceCriteriaDetailEntity.getPricingValuationId())) {
//								priceCriteriaDetailEntities.add(priceCriteriaDetailEntity);
//							}
//						}
//						valuationEntity.setCriteriaDetailEntities(priceCriteriaDetailEntities);
//						
//					}
//				//}
//		    }
		    /**
		     * 根据区域值为ALL查询
		     */
		    entity.setDeptRegionId("ALL");
		    List<PriceValuationEntity> priceValList2 = null;
		    if(FossConstants.YES.equals(clientInfo.getPagination())){
		    	priceValList2 =  priceValuationDao.queryByCoditionForDownloadByPage(
		    			entity, (clientInfo.getSyncPage())*THOUSAND -(BEFOREAMOUNT* clientInfo.getSyncPage())  , THOUSAND);	
		    	
		    }else{
		    	priceValList2 =  priceValuationDao.queryByCoditionForDownload(entity);		
		    }
		    
		    valuationEntities.addAll(priceValList2);
		    
		    
//		    if(CollectionUtils.isNotEmpty(priceValList2)) {
//				List<PriceCriteriaDetailEntity> criteriaDetailEntities = priceCriteriaDetailDao.downLoadPriceCriteriaDetailByCondition(entity);
//				if (CollectionUtils.isNotEmpty(criteriaDetailEntities)) {
//					for (int i = 0; i < priceValList2.size(); i++) {
//						PriceValuationEntity valuationEntity = priceValList2.get(i);
//						List<PriceCriteriaDetailEntity> priceCriteriaDetailEntities = new ArrayList<PriceCriteriaDetailEntity>();
//						for (int j = 0; j < criteriaDetailEntities.size(); j++) {
//							PriceCriteriaDetailEntity priceCriteriaDetailEntity = criteriaDetailEntities.get(j);
//							if(StringUtils.equals(valuationEntity.getId(), priceCriteriaDetailEntity.getPricingValuationId())) {
//								priceCriteriaDetailEntities.add(priceCriteriaDetailEntity);
//							}
//						}
//						valuationEntity.setCriteriaDetailEntities(priceCriteriaDetailEntities);
//						valuationEntities.add(valuationEntity);
//					}
//				}
//		    }
	    	return dataBundle.setObject(valuationEntities);
		} else {
			return dataBundle.setObject(null);
		}
	}
	
	
	
    public DataBundle downPricingValuationPricingServerData(ClientUpdateDataPack clientInfo) {
    	DataBundle dataBundle = new DataBundle();
    	if(null == clientInfo || null == clientInfo.getOrgCode()){
    	    return dataBundle;
    	} 
    	//根据传入参数获取始发区域ID
		String startRegionId = regionExpressService.findRegionOrgByDeptNo(clientInfo.getOrgCode(), new Date(), null,PricingConstants.PRICING_REGION); 
		if(StringUtil.isNotBlank(startRegionId)) {
			PriceValuationEntity entity = new PriceValuationEntity();
			if(clientInfo.getRegionId() != null && !startRegionId.equals(clientInfo.getRegionId())) {
				dataBundle.setNeedDeleteLocalData(FossConstants.YES);
				entity.setBeginTime(new Date());
			    entity.setEndTime(new Date());
			} else if (clientInfo.getRegionId() != null && clientInfo.getLastUpdateTime() != null) {
				entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
			}
			entity.setDeptRegionId(startRegionId);
		    entity.setActive(FossConstants.ACTIVE);
		    entity.setType(PricingConstants.VALUATION_TYPE_PRICING);
		    
		    if(FossConstants.YES.equals(clientInfo.getPagination())){
		    	return dataBundle.setObject(priceValuationDao.downPricingValuationPricingAutoServerDataByPage(entity, 
		    			(clientInfo.getSyncPage())*THOUSAND  -(BEFOREAMOUNT* clientInfo.getSyncPage()) , THOUSAND));	
		    }else{
		    	return dataBundle.setObject(priceValuationDao.downPricingValuationPricingAutoServerData(entity));
		    }
		    // 避免n+1次查询
		    //查询计费明细表
//		    if(CollectionUtils.isNotEmpty(priceValList)) {
//				List<PriceCriteriaDetailEntity> criteriaDetailEntities = priceCriteriaDetailDao.downLoadPriceCriteriaDetailByCondition(entity);
//				if (CollectionUtils.isNotEmpty(criteriaDetailEntities)) {
//					for (int i = 0; i < priceValList.size(); i++) {
//						PriceValuationEntity valuationEntity = priceValList.get(i);
//						List<PriceCriteriaDetailEntity> priceCriteriaDetailEntities = new ArrayList<PriceCriteriaDetailEntity>();
//						for (int j = 0; j < criteriaDetailEntities.size(); j++) {
//							PriceCriteriaDetailEntity priceCriteriaDetailEntity = criteriaDetailEntities.get(j);
//							if(StringUtils.equals(valuationEntity.getId(), priceCriteriaDetailEntity.getPricingValuationId())) {
//								priceCriteriaDetailEntities.add(priceCriteriaDetailEntity);
//							}
//						}
//						valuationEntity.setCriteriaDetailEntities(priceCriteriaDetailEntities);
//					}
//				}
//		    }
		} else {
			return dataBundle.setObject(null);
		}
		
    }
	
	
	 @Override
	 public DataBundle downPricePlanServerData(ClientUpdateDataPack clientInfo) {
		 DataBundle dataBundle = new DataBundle();
		 if(null == clientInfo || null == clientInfo.getOrgCode()){
			 return dataBundle;
		 } 
		 //根据传入参数获取始发区域ID
		 String startRegionId = regionExpressService.findRegionOrgByDeptNo(clientInfo.getOrgCode(), new Date(), null,PricingConstants.PRICING_REGION);  
		 if(StringUtil.isNotBlank(startRegionId)) {
			 PricePlanEntity entity = new PricePlanEntity();
			 if(clientInfo.getRegionId() != null && !startRegionId.equals(clientInfo.getRegionId())) {
				 dataBundle.setNeedDeleteLocalData(FossConstants.YES);
			 } else if(clientInfo.getRegionId() != null && clientInfo.getLastUpdateTime()!=null) {
				 entity.setVersionNo(clientInfo.getLastUpdateTime().getTime());
			 }
			 entity.setActive(FossConstants.ACTIVE);
			 entity.setPriceRegionId(startRegionId);
			 return dataBundle.setObject(pricePlanDao.queryPricePlanBatchInfo(entity));
		 } else {
			 return dataBundle.setObject(null);
		 }
	 }
	
	public IRegionExpressDao getRegionExpressDao() {
		return regionExpressDao;
	}

	public void setRegionExpressDao(IRegionExpressDao regionExpressDao) {
		this.regionExpressDao = regionExpressDao;
	}

	public IPriceValuationDao getPriceValuationDao() {
		return priceValuationDao;
	}

	public void setPriceValuationDao(IPriceValuationDao priceValuationDao) {
		this.priceValuationDao = priceValuationDao;
	}

	public IRegionExpressService getRegionExpressService() {
		return regionExpressService;
	}

	public void setRegionExpressService(IRegionExpressService regionExpressService) {
		this.regionExpressService = regionExpressService;
	}

	public IPricePlanDao getPricePlanDao() {
		return pricePlanDao;
	}

	public void setPricePlanDao(IPricePlanDao pricePlanDao) {
		this.pricePlanDao = pricePlanDao;
	}

	
	
}
