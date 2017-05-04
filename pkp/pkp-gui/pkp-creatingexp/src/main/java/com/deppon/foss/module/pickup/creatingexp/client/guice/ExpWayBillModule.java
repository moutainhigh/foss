/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.guice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressCityDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressCityService;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.ExpressCityDao;
import com.deppon.foss.module.base.baseinfo.server.service.impl.ExpressCityService;
import com.deppon.foss.module.pickup.common.client.service.IWaybillPriceExpressService;
import com.deppon.foss.module.pickup.common.client.service.impl.WaybillPriceExpressService;
import com.deppon.foss.module.pickup.creating.client.guice.WayBillModule;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IEffectiveExpressPlanDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IEffectiveExpressPlanDetailDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IExpressPriceCriteriaDetailDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IExpressPriceValuationDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IExpressPricingValueAddedDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionExpressDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionValueAddDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.ISpecialDiscountDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IEffectiveExpressPlanDetailService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IEffectiveExpressPlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IExpressPriceValuationService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IExpressPricingValueAddedService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionExpressService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionValueAddService;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.EffectiveExpressPlanDao;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.EffectiveExpressPlanDetailDao;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.ExpressPriceCriteriaDetailDao;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.ExpressPriceValuationDao;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.ExpressPricingValueAddedDao;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.RegionExpressDao;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.RegionValueAddDao;
import com.deppon.foss.module.pickup.pricing.server.dao.impl.SpecialDiscountDao;
import com.deppon.foss.module.pickup.pricing.server.service.impl.EffectiveExpressPlanDetailService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.EffectiveExpressPlanService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.ExpressPriceValuationService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.ExpressPricingValueAddedService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.RegionExpressService;
import com.deppon.foss.module.pickup.pricing.server.service.impl.RegionValueAddService;
import com.google.inject.Binder;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class ExpWayBillModule extends WayBillModule{
	//log
	private static Log LOG = LogFactory
					.getLog(ExpWayBillModule.class);
	
	public void configure(Binder binder) {
		super.configure(binder);
		//小件
		injectBean(binder, IExpressCityService.class, ExpressCityService.class);
		injectBean(binder,IExpressCityDao.class, ExpressCityDao.class);
		injectBean(binder,IEffectiveExpressPlanDetailService.class,EffectiveExpressPlanDetailService.class);
		injectBean(binder,IRegionExpressService.class,RegionExpressService.class);
		injectBean(binder,IEffectiveExpressPlanDetailDao.class,EffectiveExpressPlanDetailDao.class);
		injectBean(binder,IEffectiveExpressPlanService.class,EffectiveExpressPlanService.class);
		injectBean(binder,IEffectiveExpressPlanDao.class,EffectiveExpressPlanDao.class);
		injectBean(binder,IRegionExpressDao.class,RegionExpressDao.class);
		injectBean(binder,IWaybillPriceExpressService.class,WaybillPriceExpressService.class);
		injectBean(binder,IRegionValueAddService.class,RegionValueAddService.class);
		injectBean(binder,IRegionValueAddDao.class,RegionValueAddDao.class);
		
		binder.bind(IExpressPricingValueAddedService.class).to(ExpressPricingValueAddedService.class).asEagerSingleton();
		binder.bind(IExpressPriceValuationService.class).to(ExpressPriceValuationService.class).asEagerSingleton();
		binder.bind(IExpressPricingValueAddedDao.class).to(ExpressPricingValueAddedDao.class).asEagerSingleton();
		binder.bind(IExpressPriceValuationDao.class).to(ExpressPriceValuationDao.class).asEagerSingleton();
		binder.bind(IExpressPriceCriteriaDetailDao.class).to(ExpressPriceCriteriaDetailDao.class).asEagerSingleton();
		binder.bind(ISpecialDiscountDao.class).to(SpecialDiscountDao.class).asEagerSingleton();

		//IRegionValueAddService
	}
	
	
	/** guice注入bean
	 * @param binder
	 * @param interfaceClass
	 * @param implementClass
	 */
	@SuppressWarnings({"unchecked","rawtypes"})
	protected void injectBean(Binder binder , Class interfaceClass, Class implementClass) {
		binder.bind(interfaceClass).to(implementClass).asEagerSingleton();
		LOG.info(" 启动注入 :" +implementClass.getSimpleName()+" 成功");
	}
}
