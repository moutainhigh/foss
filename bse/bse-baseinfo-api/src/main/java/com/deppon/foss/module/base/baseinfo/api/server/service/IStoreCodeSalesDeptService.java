package com.deppon.foss.module.base.baseinfo.api.server.service;

import com.deppon.foss.framework.service.IService;

/**
 * GXG项目提供给接送货根据客户门店编号查询德邦网点code Service接口类
 * @author 151211 
 * @date 2015-9-11 下午2:34:32
 * @since
 * @version
 */
public interface IStoreCodeSalesDeptService extends IService{


	/**
	 * <p>GXG项目提供给接送货根据客户门店编号查询德邦网点code</p> 
	 * @author 151211 
	 * @date 2015-9-11 下午2:47:49
	 * @param storeCode
	 * @return
	 * @see
	 */
	String querySalesDeptByStoreCode(String storeCode);
}
