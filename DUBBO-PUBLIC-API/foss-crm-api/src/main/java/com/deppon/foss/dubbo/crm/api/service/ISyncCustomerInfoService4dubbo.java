package com.deppon.foss.dubbo.crm.api.service;

import com.deppon.foss.dubbo.crm.api.define.masterdata.CrmSyncDataEntity;

/**
 * 客户主数据同步
 * @author 335284
 * @since 2016.11.29
 */
public interface ISyncCustomerInfoService4dubbo {

	boolean syncCrmCustomerMasterInfo(CrmSyncDataEntity crmSyncRequest);

}