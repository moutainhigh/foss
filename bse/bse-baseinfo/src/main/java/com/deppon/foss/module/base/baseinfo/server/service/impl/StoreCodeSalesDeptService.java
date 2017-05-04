package com.deppon.foss.module.base.baseinfo.server.service.impl;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IStoreCodeSalesDeptDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IStoreCodeSalesDeptService;
/**
 * GXG项目提供给接送货根据客户门店编号查询德邦网点code Service接口实现类
 * @author 151211 
 * @date 2015-9-11 下午2:54:44
 * @since
 * @version
 */
public class StoreCodeSalesDeptService implements  IStoreCodeSalesDeptService{

	/**
	 * IStoreCodeSalesDeptDao
	 */
	private IStoreCodeSalesDeptDao storeCodeSalesDeptDao;
	
	/**
	 * <p>storeCodeSalesDeptDao set</p> 
	 * @author 151211 
	 * @date 2015-9-13 下午4:06:22
	 * @param storeCodeSalesDeptDao
	 * @see
	 */
	public void setStoreCodeSalesDeptDao(
			IStoreCodeSalesDeptDao storeCodeSalesDeptDao) {
		this.storeCodeSalesDeptDao = storeCodeSalesDeptDao;
	}



	/** 
	 * <p>根据客户门店编号查询德邦网点code</p> 
	 * @author 151211 
	 * @date 2015-9-11 下午2:55:33
	 * @param storeCode
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IStoreCodeSalesDeptService#querySalesDeptByStoreCode(java.lang.String)
	 */
	@Override
	public String querySalesDeptByStoreCode(String storeCode) {
		if(StringUtil.isBlank(storeCode)){
			return null;
		}
		else{
			return storeCodeSalesDeptDao.querySalesDeptByStoreCode(storeCode);
		}
		
	}
}
