/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-changingexp
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/guice/WayBillRfcModule.java
 * 
 * FILE NAME        	: WayBillRfcModule.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.changingexp.client.guice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.client.component.dataaccess.GuiceModule;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IBillingGroupTransFerDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IDepartureStandardDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressLineDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IFreightRouteLineDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILineDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILineItemDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.INetGroupMixDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IOutfieldDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISaleDepartmentDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesDescExpandDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBillingGroupTransFerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IDepartureStandardService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressLineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFreightRouteLineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFreightRouteService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILineItemService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILineService;
import com.deppon.foss.module.base.baseinfo.api.server.service.INetGroupService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISalesDescExpandService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.BillingGroupTransFerDao;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.DepartureStandardDao;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.ExpressLineDao;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.FreightRouteLineDao;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.LineDao;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.LineItemDao;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.NetGroupMixDao;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.OutfieldDao;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.SaleDepartmentDao;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.SalesDescExpandDao;
import com.deppon.foss.module.base.baseinfo.server.service.impl.AdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.BillingGroupTransFerService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.DepartureStandardService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.ExpressLineService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.FreightRouteLineService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.FreightRouteService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.LineItemService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.LineService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.NetGroupService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.OutfieldService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.SaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.SalesDescExpandService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.complex.OrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.server.service.impl.ConfigurationParamsService;
import com.deppon.foss.module.pickup.changingexp.client.service.IWaybillRfcRemotingService;
import com.deppon.foss.module.pickup.changingexp.client.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.changingexp.client.service.IWaybillRfcVarificationRemotingService;
import com.deppon.foss.module.pickup.changingexp.client.service.IWaybillRfcVarificationService;
import com.deppon.foss.module.pickup.changingexp.client.service.impl.WaybillRfcRemotingService;
import com.deppon.foss.module.pickup.changingexp.client.service.impl.WaybillRfcService;
import com.deppon.foss.module.pickup.changingexp.client.service.impl.WaybillRfcVarificationRemotingService;
import com.deppon.foss.module.pickup.changingexp.client.service.impl.WaybillRfcVarificationService;
import com.deppon.foss.module.pickup.common.client.dao.IDataDictionaryValueDao;
import com.deppon.foss.module.pickup.common.client.dao.IDistrictDao;
import com.deppon.foss.module.pickup.common.client.dao.IFreightRouteDao;
import com.deppon.foss.module.pickup.common.client.dao.IInsurGoodsDao;
import com.deppon.foss.module.pickup.common.client.dao.INetGroupDao;
import com.deppon.foss.module.pickup.common.client.dao.IOuterBranchDao;
import com.deppon.foss.module.pickup.common.client.dao.IProductDao;
import com.deppon.foss.module.pickup.common.client.dao.IProhibitGoodsDao;
import com.deppon.foss.module.pickup.common.client.dao.ISalesDepartmentDao;
import com.deppon.foss.module.pickup.common.client.dao.ISysConfigDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.DataDictionaryValueDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.DistrictDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.FreightRouteDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.InsurGoodsDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.NetGroupDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.OuterBranchDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.ProductDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.ProhibitGoodsDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.SalesDepartmentDao;
import com.deppon.foss.module.pickup.common.client.dao.impl.SysConfigDao;
import com.deppon.foss.module.pickup.common.client.service.IBaseDataService;
import com.deppon.foss.module.pickup.common.client.service.ICustomerService;
import com.deppon.foss.module.pickup.common.client.service.IDataDictionaryValueService;
import com.deppon.foss.module.pickup.common.client.service.IOrgInfoService;
import com.deppon.foss.module.pickup.common.client.service.IPriceRebateService;
import com.deppon.foss.module.pickup.common.client.service.IProductService;
import com.deppon.foss.module.pickup.common.client.service.IRuleVerifyService;
import com.deppon.foss.module.pickup.common.client.service.ISalesDepartmentService;
import com.deppon.foss.module.pickup.common.client.service.IWaybillFreightRouteService;
import com.deppon.foss.module.pickup.common.client.service.impl.BaseDataService;
import com.deppon.foss.module.pickup.common.client.service.impl.CustomerService;
import com.deppon.foss.module.pickup.common.client.service.impl.DataDictionaryValueService;
import com.deppon.foss.module.pickup.common.client.service.impl.OrgInfoService;
import com.deppon.foss.module.pickup.common.client.service.impl.PriceRebateService;
import com.deppon.foss.module.pickup.common.client.service.impl.ProductService;
import com.deppon.foss.module.pickup.common.client.service.impl.RuleVerifyService;
import com.deppon.foss.module.pickup.common.client.service.impl.SalesDepartmentService;
import com.deppon.foss.module.pickup.common.client.service.impl.WaybillFreightRouteService;
import com.google.inject.Binder;

/**
 * 
 * 更改单Guice注入映射类
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-10-29 上午9:16:10
 */
public class WayBillRfcModule extends GuiceModule {

	//log
	private static final Log LOG = LogFactory
					.getLog(WayBillRfcModule.class);

	
	@Override
	public void configure(Binder binder) {
		//行政区域 Service接口
		injectBean(binder,IAdministrativeRegionsService.class, AdministrativeRegionsService.class);
		
		//营业部服务
		injectBean(binder,ISalesDepartmentService.class, SalesDepartmentService.class);
		
		// 更改单Service
		injectBean(binder,IWaybillRfcService.class, WaybillRfcService.class);
		
		// 更改单远程Service
		injectBean(binder,IWaybillRfcRemotingService.class, WaybillRfcRemotingService.class);
		
		// 更改单审核提交远程Service
		injectBean(binder,IWaybillRfcVarificationRemotingService.class, WaybillRfcVarificationRemotingService.class);
		
		// 更改单审核提交Service
		injectBean(binder,IWaybillRfcVarificationService.class, WaybillRfcVarificationService.class);
		
		// 数据字典DAO
		injectBean(binder,IDataDictionaryValueDao.class, DataDictionaryValueDao.class);
		
		// 限保物品DAO
		injectBean(binder,IInsurGoodsDao.class, InsurGoodsDao.class);
		
		// 禁运物品DAO
		injectBean(binder,IProhibitGoodsDao.class, ProhibitGoodsDao.class);
		
		// 行政区域DAO
		injectBean(binder,IDistrictDao.class, DistrictDao.class);
		
		// 产品DAO
		injectBean(binder,IProductDao.class, ProductDao.class);
		
		// 营业部DAO
		injectBean(binder,ISalesDepartmentDao.class, SalesDepartmentDao.class);
		
		// 运单基础资料Service
		injectBean(binder,IBaseDataService.class, BaseDataService.class);
		
		// 数据字典Service
		injectBean(binder,IDataDictionaryValueService.class, DataDictionaryValueService.class);
		
		// 走货路径DAO
		injectBean(binder,IFreightRouteDao.class, FreightRouteDao.class);
		
		// 网点组数据DAO
		injectBean(binder,INetGroupDao.class, NetGroupDao.class);
		
		// 系统配置DAO
		injectBean(binder,ISysConfigDao.class, SysConfigDao.class);
		
		// 查询客户信息Service
		injectBean(binder,ICustomerService.class, CustomerService.class);
		
		// 运单业务规则效验Service
		injectBean(binder,IRuleVerifyService.class, RuleVerifyService.class);
		
		// 产品服务接口Service
		injectBean(binder,IProductService.class, ProductService.class);
		
		// 部门Service
		injectBean(binder,IOrgInfoService.class, OrgInfoService.class);
		
		// 组织信息DAO
		injectBean(binder,IOuterBranchDao.class, OuterBranchDao.class);
		
		// 价格服务Service
		injectBean(binder,IPriceRebateService.class, PriceRebateService.class);
		
		//部门 复杂查询 service
		injectBean(binder,IOrgAdministrativeInfoComplexService.class, OrgAdministrativeInfoComplexService.class);
		

		// ***************************************离线查询走货线路*******************
		injectBean(binder,IWaybillFreightRouteService.class, WaybillFreightRouteService.class);
		
		// 走货路径
		injectBean(binder,IFreightRouteService.class, FreightRouteService.class);
		
		// 走货路径Dao
		
		injectBean(binder,com.deppon.foss.module.base.baseinfo.api.server.dao.IFreightRouteDao.class, 
				com.deppon.foss.module.base.baseinfo.server.dao.impl.FreightRouteDao.class);
		
		// 网点组服务类
		
		injectBean(binder,INetGroupService.class, 
				NetGroupService.class);
		
		
		// 网点组Dao

		injectBean(binder,com.deppon.foss.module.base.baseinfo.api.server.dao.INetGroupDao.class, 
				com.deppon.foss.module.base.baseinfo.server.dao.impl.NetGroupDao.class);
		
		// 线路Dao
		injectBean(binder,ILineDao.class, LineDao.class);
		
		// 快递线路Dao
		injectBean(binder,IExpressLineDao.class, ExpressLineDao.class);
		
		// 线段服务类
		injectBean(binder,ILineItemService.class, LineItemService.class);
		
		// 线段Dao
		injectBean(binder,ILineItemDao.class, LineItemDao.class);
		
		// 发车标准服务类
		injectBean(binder,IDepartureStandardService.class, DepartureStandardService.class);
		
		// 走货路径线路服务接口
		injectBean(binder,IFreightRouteLineService.class, FreightRouteLineService.class);
		
		// 走货路径线路Dao
		injectBean(binder,IFreightRouteLineDao.class, FreightRouteLineDao.class);
		
		// 外场 Service接口
		injectBean(binder,IOutfieldService.class, OutfieldService.class);
		
		// 发车标准Dao
		
		injectBean(binder,IDepartureStandardDao.class, DepartureStandardDao.class);
		
		// 线路服务类
		
		injectBean(binder,ILineService.class, LineService.class);
		
		// 快递线路服务类
		injectBean(binder,IExpressLineService.class, ExpressLineService.class);
		
		// 营业部 Service接口
		injectBean(binder,ISaleDepartmentService.class, SaleDepartmentService.class);
		
		// 外场 DAO接口
	
		injectBean(binder,IOutfieldDao.class, OutfieldDao.class);
		
		// 营业部 DAO接口
		
		injectBean(binder,ISaleDepartmentDao.class, SaleDepartmentDao.class);
		
		injectBean(binder, IConfigurationParamsService.class, ConfigurationParamsService.class);
		
		injectBean(binder, INetGroupMixDao.class, NetGroupMixDao.class);
		
		injectBean(binder, IBillingGroupTransFerService.class, BillingGroupTransFerService.class);
		injectBean(binder,IBillingGroupTransFerDao.class, BillingGroupTransFerDao.class);
		
		
		injectBean(binder, ISalesDescExpandService.class, SalesDescExpandService.class);
		injectBean(binder,ISalesDescExpandDao.class, SalesDescExpandDao.class);
		
	

		// ***************************************离线查询走货线路*******************
	}
	
	/**
	 * guice注入bean
	 * @param binder
	 * @param interfaceClass
	 * @param implementClass
	 */
	@SuppressWarnings({"unchecked","rawtypes"})
	private void injectBean(Binder binder , Class interfaceClass, Class implementClass) {
		binder.bind(interfaceClass).to(implementClass).asEagerSingleton();
		LOG.info(" 【RFC 模块】 启动注入 :" +implementClass.getSimpleName()+" 成功");
	}
}