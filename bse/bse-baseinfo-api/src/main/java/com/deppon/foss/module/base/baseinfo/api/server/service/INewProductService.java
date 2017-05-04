package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.io.Serializable;

import com.deppon.esb.inteface.domain.crm.XcpSyncRequest;
import com.deppon.esb.inteface.domain.crm.XcpSyncResponse;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NewProductEntity;

public interface INewProductService extends Serializable{

	XcpSyncResponse addNewProductEntity(XcpSyncRequest request);
	
	NewProductEntity queryNewProductByConstomerCode(String customerCode);
}
