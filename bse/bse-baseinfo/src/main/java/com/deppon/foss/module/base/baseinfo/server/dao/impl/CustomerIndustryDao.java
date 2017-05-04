package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerIndustryDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerIndustryEntity;
/**
 * 客户行业信息Dao接口实现
 * @author dujunhui-187862
 * @date 2014-9-25 下午4:40:14
 * @since
 * @version
 */
public class CustomerIndustryDao extends SqlSessionDaoSupport implements ICustomerIndustryDao{
	
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.cusProfession.";
	
	/**
	 * 新增客户行业信息
	 * @author dujunhui-187862
	 * @date 2014-9-25 下午4:41:36
	 * @param entity 客户行业信息实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	@Override
	public int addCusProfession(CustomerIndustryEntity entity) {
		return this.getSqlSession().insert(NAMESPACE + "insert", entity);
	}
	
	/**
	 * 修改客户行业信息
	 * @author dujunhui-187862
	 * @date 2014-9-25 下午4:41:36
	 * @param entity 客户行业信息实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	@Override
	public int updateCusProfession(CustomerIndustryEntity entity) {
		return this.getSqlSession().update(NAMESPACE + "update", entity);
	}
	
	/**
	 * <p>根据professionCode查询客户行业信息实体</p>
	 * @author dujunhui-187862
	 * @date 2014-9-25 下午4:41:36
	 * @param professionCode
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public CustomerIndustryEntity queryCusCusProfessionBySecCode(String professionCode) {
		CustomerIndustryEntity entity=new CustomerIndustryEntity();
		entity.setProfessionCode(professionCode);
		List<CustomerIndustryEntity> list = this.getSqlSession().
						selectList(NAMESPACE + "queryCusCusProfessionBySecCode", entity);
		return CollectionUtils.isEmpty(list) ? null:list.get(0);
	}

	/** 
	 * <p>查询全部二级行业信息</p> 
	 * @author 187862
	 * @date 2014-11-6 下午3:45:35
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerIndustryDao#queryAllSecProfession()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerIndustryEntity> queryAllSecProfession() {
		List<CustomerIndustryEntity> list=this.getSqlSession().
				selectList(NAMESPACE + "queryAllSecProfession");
		return CollectionUtils.isEmpty(list) ? null:list;
	}

	/** 
	 * <p>根据Code数组查询对应主键的二级行业数据</p> 
	 * @author 187862
	 * @date 2014-11-6 下午3:45:35
	 * @param id
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ICustomerIndustryDao#querySecProfessionByCodes(java.lang.String[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerIndustryEntity> querySecProfessionByCodes(List<String> codes) {
		Map<String,Object> map=new HashMap<String,Object>(); 
		map.put("codes", codes);
		List<CustomerIndustryEntity> list=this.getSqlSession().
				selectList(NAMESPACE + "querySecProfessionByCodes", map);
		return CollectionUtils.isEmpty(list) ? null:list;
	}
	
	/**
	 * <p>根据客户编码查询客户行业信息实体</p>
	 * @author dujunhui-187862
	 * @date 2015-4-17 上午10:9:24
	 * @param customerCode
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public CustomerIndustryEntity queryCusProfessionByCusCode(String customerCode) {
		CustomerIndustryEntity entity=new CustomerIndustryEntity();
		entity.setCustomerCode(customerCode);
		List<CustomerIndustryEntity> list = this.getSqlSession().
						selectList(NAMESPACE + "queryCusProfessionByCusCode", entity);
		return CollectionUtils.isEmpty(list) ? null:list.get(0);
	}

}
