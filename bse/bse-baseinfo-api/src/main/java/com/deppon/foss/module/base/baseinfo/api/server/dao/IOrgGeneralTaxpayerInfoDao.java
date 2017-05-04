package com.deppon.foss.module.base.baseinfo.api.server.dao;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.GeneralTaxpayerInfoEntity;
/**
 * 
 * 同步一般纳税人信息dao接口
 * @author 308861 
 * @date 2016-2-28 下午2:48:41
 * @since
 * @version
 */
public interface IOrgGeneralTaxpayerInfoDao {
	
	/**
	 * 
	 * 插入 
	 * @author 308861 
	 * @date 2016-2-24 下午6:04:58
	 * @param entity
	 * @return
	 * @see
	 */
	GeneralTaxpayerInfoEntity addGeneralTaxpayerInfo(GeneralTaxpayerInfoEntity entity);
	
	/**
	 * 
	 * 修改 
	 * @author 308861 
	 * @date 2016-2-24 下午6:09:28
	 * @param entity
	 * @return
	 * @see
	 */
	GeneralTaxpayerInfoEntity updateTaxpayerInfo(GeneralTaxpayerInfoEntity entity);
	/**
	 * 
	 * 根据唯一标识列作废 
	 * @author 308861 
	 * @date 2016-2-24 下午6:06:06
	 * @param entity
	 * @return
	 * @see
	 */
	GeneralTaxpayerInfoEntity deleteTaxpayerInfo(GeneralTaxpayerInfoEntity entity);
	
	/**
	 * 
	 * 根据CRMID精确查询 
	 * @author 308861 
	 * @date 2016-2-24 下午6:10:57
	 * @param entity
	 * @return
	 * @see
	 */
	GeneralTaxpayerInfoEntity queryTaxpayerInfoById(String crmId);
}
