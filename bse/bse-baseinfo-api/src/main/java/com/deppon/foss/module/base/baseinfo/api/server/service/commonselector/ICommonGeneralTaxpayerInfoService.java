package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GeneralTaxpayerInfoEntity;
/**
 * 
 * 一般纳税人信息service接口  公共选择器
 * @author 308861 
 * @date 2016-2-28 下午2:49:39
 * @since
 * @version
 */
public interface ICommonGeneralTaxpayerInfoService extends IService{
	
	/**
	 * 
	 * 查询 一般纳税人信息
	 * @author 308861 
	 * @date 2016-2-28 下午4:40:31
	 * @param entity
	 * @return
	 * @see
	 */
	public List<GeneralTaxpayerInfoEntity> queryTaxpayerInfoList(GeneralTaxpayerInfoEntity entity,int start, int limit);
	
	/**
	 * 
	 * 统计一般纳税人信息总条数   用于分页
	 * @author 308861 
	 * @date 2016-2-29 上午9:51:31
	 * @param entity
	 * @return
	 * @see
	 */
	long queryGeneralTaxpayerInfoCount(GeneralTaxpayerInfoEntity entity);
}
