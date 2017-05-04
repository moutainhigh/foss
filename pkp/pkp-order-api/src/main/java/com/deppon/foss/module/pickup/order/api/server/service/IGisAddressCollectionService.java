package com.deppon.foss.module.pickup.order.api.server.service;

import com.deppon.deppon.gis.inteface.domain.CollectAddressResponse;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.order.api.shared.domain.AddressCollectionEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity;
import com.deppon.gis.gisservice.CommonException;

public interface IGisAddressCollectionService  extends IService{
	/**
	 * 调用GIS服务，进行地址记录
	 * */	
	public CollectAddressResponse fossToGisAddressInfo(AddressCollectionEntity entity) throws CommonException ;

	/**
	 * 删除GIS固定库GPS坐标  14.7.23 gcl AUTO-195
	 */
	public CollectAddressResponse delGisAddressInfo(DispatchOrderEntity entity) throws CommonException;
}
