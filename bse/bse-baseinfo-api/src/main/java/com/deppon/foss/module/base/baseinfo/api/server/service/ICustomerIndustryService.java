package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerIndustryEntity;

/**
 * 客户行业信息Service
 * @author dujunhui-187862
 * @date 2014-9-25 下午3:03:25
 * @since
 * @version
 */
public interface ICustomerIndustryService extends IService{
	/**
	 * 新增客户行业信息
	 * @author dujunhui-187862
	 * @date 2014-9-25 下午3:04:27
	 * @param entity 客户行业信息实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int addCusProfession(CustomerIndustryEntity entity);

	/**
	 * 修改客户行业信息
	 * @author dujunhui-187862
	 * @date 2014-9-25 下午3:06:18
	 * @param entity 客户行业信息实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int updateCusProfession(CustomerIndustryEntity entity);

	/**
	 * <p>根据二级行业编码secDegreeProfessionCode查询客户行业信息实体</p>
	 * @author dujunhui-187862
	 * @date 2014-9-25 下午3:11:37
	 * @param secDegreeProfessionCode
	 * @return
	 * @see
	 */
	CustomerIndustryEntity queryCusProfessionBySecCode(String secDegreeProfessionCode);
	
	/**
	 * <p>查询全部二级行业信息</p>
	 * @author dujunhui-187862
	 * @date 2014-11-6 下午3:17:14
	 * @param 
	 * @return
	 * @see
	 */
	List<CustomerIndustryEntity> queryAllSecProfession();
	
	/**
	 * <p>根据Code数组查询对应主键的二级行业数据</p>
	 * @author dujunhui-187862
	 * @date 2014-11-6 下午3:23:43
	 * @param id
	 * @return
	 * @see
	 */
	List<CustomerIndustryEntity> querySecProfessionByCodes(List<String> codes);
	
	/** 
	 * <p>根据客户编码查询对应的行业数据</p> 
	 * @author 187862
	 * @date 2014-12-16 下午5:32:08
	 * @param id
	 * @return 
	 */
	public CustomerIndustryEntity querySecProfessionByCusCode(String customerCode);
	
}
