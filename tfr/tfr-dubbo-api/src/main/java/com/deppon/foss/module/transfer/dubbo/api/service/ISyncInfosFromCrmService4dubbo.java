package com.deppon.foss.module.transfer.dubbo.api.service;

import com.deppon.foss.dubbo.crm.api.define.masterdata.CrmSyncDataEntity;

public interface ISyncInfosFromCrmService4dubbo {

	boolean syncInfo(CrmSyncDataEntity crmSyncRequest);

}
