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
 * PROJECT NAME	: pkp-sign
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/BaseTestCase.java
 * 
 * FILE NAME        	: BaseTestCase.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;

import com.deppon.foss.util.test.DaoDBUnitSupportUnitTests;

/**
 * 
 * job 测试类
 * @author ibm-wangfei
 * @date Dec 25, 2012 2:07:33 PM
 */
@ContextConfiguration( 
        locations = {
        		"classpath:com/deppon/foss/module/pickup/deliver/test/META-INF/spring.xml",
//        		"classpath:com/deppon/foss/module/login/server/META-INF/spring.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/api/server/META-INF/spring.xml",
//        		"classpath:com/deppon/foss/module/pickup/deliver/server/META-INF/spring.xml",
//        		"classpath:com/deppon/foss/framework/server/META-INF/spring.xml",
        		"classpath:com/deppon/foss/module/pickup/predeliver/server/META-INF/spring.xml",
        		"classpath:com/deppon/foss/module/pickup/sign/server/META-INF/spring.xml",
        		"classpath:com/deppon/foss/module/pickup/waybill/server/META-INF/spring.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/spring.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/platformBean.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/lineBean.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/regionalVehicle.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/sitegroup.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/customerInfo.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/agencyBranchOrCompany.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/syncinfosToCrm.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/syncinfosToGps.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/payeeInfo.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/baseInfoDownloadBean.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/financialOrganizations.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/financialOrganizationsComplex.xml",
        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/administrativeRegions.xml",
        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/orgAdministrativeInfo.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/motorcade.xml",
        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/saleDepartment.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/outfield.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/resource.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/role.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/roleResource.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/resourceConflict.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/employee.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/loadAndUnloadSquad.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/porter.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/authorizationBean.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/loadAndUnloadEfficiencyTon.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/loadAndUnloadEfficiencyMan.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/loadAndUnloadEfficiencyVehicle.xml",
        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/workday.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/limitedWarrantyItems.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/prohibitedArticles.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/salesMotorcade.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/salesProduct.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/motorcadeServeDistrict.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/motorcadeServeSalesArea.xml",
        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/orgAdministrativeInfoEmployee.xml",
        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/orgAdministrativeInfoComplex.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/salesDeptAccountant.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/publicBankAccount.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/user-bean.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/vehicle-bean.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/driver-bean.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/whitelist-bean.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/synchronous-bean.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/airlines-bean.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/infodept-bean.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/commonSelector.xml",
//        		"classpath:com/deppon/foss/module/base/baseinfo/server/META-INF/userDeptAuthority.xml",
        		"classpath:com/deppon/foss/module/settlement/common/server/META-INF/spring.xml",
        		"classpath:com/deppon/foss/module/settlement/consumer/server/META-INF/spring.xml",
//        		"classpath:com/deppon/foss/module/pickup/sign/server/META-INF/spring.xml",
//        		"classpath:com/deppon/foss/module/pickup/pricing/server/META-INF/spring.xml",
//        		"classpath:com/deppon/foss/module/pickup/pricing/server/META-INF/pricingRegion.xml",
//        		"classpath:com/deppon/foss/module/pickup/pricing/server/META-INF/pricingValueAdded.xml",
//        		"classpath:com/deppon/foss/module/pickup/pricing/server/META-INF/publishPrice.xml",
//        		"classpath:com/deppon/foss/module/pickup/pricing/server/META-INF/pricingDownServer.xml",
//        		"classpath:com/deppon/foss/module/pickup/pricing/server/META-INF/baseProduct.xml",
//        		"classpath:com/deppon/foss/module/pickup/pricing/server/META-INF/pricingGoodsType.xml",
//        		"classpath:com/deppon/foss/module/pickup/pricing/server/META-INF/pricingProductItem.xml",
//        		"classpath:com/deppon/foss/module/pickup/pricing/server/META-INF/airlinesValueAdd.xml",
//        		"classpath:com/deppon/foss/module/pickup/pricing/server/META-INF/flightPricePlan.xml",
//        		"classpath:com/deppon/foss/module/pickup/pricing/server/META-INF/flightPricePlanDetail.xml",
//        		"classpath:com/deppon/foss/module/pickup/pricing/server/META-INF/billCalculate.xml",
//        		"classpath:com/deppon/foss/module/pickup/pricing/server/META-INF/effectivePlan.xml",
//        		"classpath:com/deppon/foss/module/pickup/pricing/server/META-INF/effectivePlanDetail.xml",
//        		"classpath:com/deppon/foss/module/pickup/pricing/server/META-INF/priceValuation.xml",
//        		"classpath:com/deppon/foss/module/pickup/pricing/server/META-INF/pricePlan.xml",
//        		"classpath:com/deppon/foss/module/pickup/pricing/server/META-INF/PriceDiscount.xml",
//        		"classpath:com/deppon/foss/module/pickup/pricing/server/META-INF/priceEntry.xml",
//        		"classpath:com/deppon/foss/module/pickup/pricing/server/META-INF/discountPriority.xml",
//        		"classpath:com/deppon/foss/module/base/dict/server/META-INF/spring.xml",
        		"classpath:com/deppon/foss/module/base/dict/server/META-INF/dataDictionary.xml",
        		"classpath:com/deppon/foss/module/base/dict/server/META-INF/dataDictionaryValue.xml",
        		"classpath:com/deppon/foss/module/base/dict/server/META-INF/configurationParams.xml",
//        		"classpath:com/deppon/foss/module/base/dict/server/META-INF/dataDictionaryCache.xml",
//        		"classpath:com/deppon/foss/module/base/dict/server/META-INF/dictDownloadBean.xml",
//        		"classpath:com/deppon/foss/module/base/common/server/META-INF/spring.xml",
        		"classpath:com/deppon/foss/module/transfer/stock/server/META-INF/spring.xml"
//        		"classpath:com/deppon/foss/module/transfer/load/server/META-INF/spring.xml",
//        		"classpath:com/deppon/foss/module/pickup/pickup/server/META-INF/spring.xml",
//        		"classpath:com/deppon/foss/module/transfer/partialline/server/META-INF/spring.xml",
//        		"classpath:com/deppon/foss/module/settlement/agency/server/META-INF/spring.xml",
//        		"classpath:com/deppon/foss/module/transfer/scheduling/server/META-INF/spring.xml",
//        		"classpath:com/deppon/foss/module/transfer/scheduling/server/META-INF/platformDispatch.xml",
//        		"classpath:com/deppon/foss/module/transfer/scheduling/server/META-INF/orderVehicle.xml",
//        		"classpath:com/deppon/foss/module/transfer/scheduling/server/META-INF/borrowVehicle.xml",
//        		"classpath:com/deppon/foss/module/transfer/scheduling/server/META-INF/inviteVehicle.xml",
//        		"classpath:com/deppon/foss/module/transfer/common/server/META-INF/spring.xml",
  })

public abstract class BaseTestCase extends DaoDBUnitSupportUnitTests {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * 
     */
    public BaseTestCase() {
    	
    }
    }