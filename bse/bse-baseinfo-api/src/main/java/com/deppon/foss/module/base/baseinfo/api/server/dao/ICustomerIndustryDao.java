package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerIndustryEntity;
/**
 * 客户行业信息Dao接口
 * @author dujunhui-187862
 * @date 2014-9-25 下午4:11:33
 * @since
 * @version
 */
public interface ICustomerIndustryDao {
	
	/**
	 * 新增客户行业信息
	 * @author dujunhui-187862
	 * @date 2014-9-25 下午4:11:59
	 * @param entity 客户行业信息实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int addCusProfession(CustomerIndustryEntity entity);

	/**
	 * 修改客户发票标记信息
	 * @author dujunhui-187862
	 * @date 2014-9-25 下午4:13:14
	 * @param entity 客户行业信息实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int updateCusProfession(CustomerIndustryEntity entity);

	/**
	 * <p>根据二级行业编码查询客户行业信息实体</p>
	 * @author dujunhui-187862
	 * @date 2014-9-25 下午4:17:02
	 * @param secDegreeProfessionCode
	 * @return
	 * @see
	 */
	CustomerIndustryEntity queryCusCusProfessionBySecCode(String professionCode);
	
	/**
	 * <p>查询全部二级行业信息</p>
	 * @author dujunhui-187862
	 * @date 2014-11-6 下午3:43:26
	 * @param 
	 * @return
	 * @see
	 */
	List<CustomerIndustryEntity> queryAllSecProfession();
	
	/**
	 * <p>根据Code数组查询对应主键的二级行业数据</p>
	 * @author dujunhui-187862
	 * @date 2014-11-6 下午3:45:16
	 * @param id
	 * @return
	 * @see
	 */
	List<CustomerIndustryEntity> querySecProfessionByCodes(List<String> codes);
	
	/**
	 * <p>根据客户编码查询客户行业信息实体</p>
	 * @author dujunhui-187862
	 * @date 2015-4-17 上午10:8:35
	 * @param customerCode
	 * @return
	 * @see
	 */
	public CustomerIndustryEntity queryCusProfessionByCusCode(String customerCode); 
}
