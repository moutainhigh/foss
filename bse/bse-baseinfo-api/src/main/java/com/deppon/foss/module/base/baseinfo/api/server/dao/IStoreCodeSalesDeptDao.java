package com.deppon.foss.module.base.baseinfo.api.server.dao;


/**
 * GXG项目提供给接送货根据客户门店编号查询德邦网点code DAO接口类
 * @author 151211 
 * @date 2015-9-11 下午2:47:39
 * @since
 * @version
 */
public interface IStoreCodeSalesDeptDao {

	/**
	 * <p>GXG项目提供给接送货根据客户门店编号查询德邦网点code</p> 
	 * @author 151211 
	 * @date 2015-9-11 下午2:47:44
	 * @param storeCode
	 * @return
	 * @see
	 */
	String querySalesDeptByStoreCode(String storeCode);
}
