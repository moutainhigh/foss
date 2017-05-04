package com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.GeneralTaxpayerInfoEntity;
/**
 * 
 * 一般纳税人信息dao接口
 * @author 308861 
 * @date 2016-2-28 下午2:48:41
 * @since
 * @version
 */
public interface ICommonGeneralTaxpayerInfoDao {
	
	/**
	 * 
	 * 根据发票抬头查询一般纳税人信息 
	 * @author 308861 
	 * @date 2016-2-24 下午6:10:57
	 * @param entity
	 * @return
	 * @see
	 */
	List<GeneralTaxpayerInfoEntity> queryTaxpayerInfoList(GeneralTaxpayerInfoEntity entity,int start, int limit);
	
	/**
	 * 
	 * 查询总条数 ---用于分页 
	 * @author 308861 
	 * @date 2016-2-29 上午9:12:25
	 * @param entity
	 * @return
	 * @see
	 */
	long queryGeneralTaxpayerInfoCount(GeneralTaxpayerInfoEntity entity);
}
