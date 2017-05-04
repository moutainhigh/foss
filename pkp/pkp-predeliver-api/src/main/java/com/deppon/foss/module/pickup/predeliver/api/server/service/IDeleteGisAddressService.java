package com.deppon.foss.module.pickup.predeliver.api.server.service;

import com.deppon.deppon.gis.inteface.domain.CollectAddressResponse;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.HandoverBillVo;

/** 
 * @ClassName: IDeleteGisAddressService 
 * @Description: 作废Gis地址库Service
 * @author 237982-foss-fangwenjun 
 * @date 2015-6-3 下午4:36:16 
 *  
 */
public interface IDeleteGisAddressService extends IService {
	
	/**
	 * @Title: deleteAddress
	 * @Description: 删除地址
	 * @param handoverBillVo 已交单Vo对象
	 * @return 响应返回对象
	 */ 
	CollectAddressResponse deleteAddress(HandoverBillVo handoverBillVo);

}
