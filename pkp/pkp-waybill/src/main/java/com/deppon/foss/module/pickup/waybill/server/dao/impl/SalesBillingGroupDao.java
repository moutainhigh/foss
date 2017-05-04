/**
 * 
 */
package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesBillingGroupEntity;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ISalesBillingGroupDao;
import com.deppon.foss.util.define.FossConstants;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class SalesBillingGroupDao extends iBatis3DaoImpl implements ISalesBillingGroupDao {
	/**
	 * 名称空间
	 */
	private static final String NAMESPACE = "pkp.waybill.SalesBillingGroupEntityMapper.";
	
	 /**
	 * 
	 * 功能：插条记录
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	@Override
	public boolean addSalesBillingGroup(
			SalesBillingGroupEntity salesBillingGroup) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", salesBillingGroup.getId());
		String id = (String) this.getSqlSession().selectOne(NAMESPACE + "selectById", map);
		if(ObjectUtils.NULL.equals(ObjectUtils.defaultIfNull(
				id, ObjectUtils.NULL))){
			this.getSqlSession().insert(NAMESPACE + "insertSelective", salesBillingGroup);
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 
	 * 功能：更新条记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	public void updateSalesBillingGroup(
			SalesBillingGroupEntity salesBillingGroup) {
		this.getSqlSession().update(NAMESPACE + "updateByPrimaryKeySelective", salesBillingGroup);

	}

	
	@Override
	public void delete(SalesBillingGroupEntity salesBillingGroup) {
		this.getSqlSession().delete(NAMESPACE + "delete", salesBillingGroup);	
	}
	
	/**
	 * 根据集中开单部门编码查询旗下所有营业部
	 * @author 026123-foss-lifengteng
	 * @date 2013-5-6 下午6:33:35
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SalesBillingGroupEntity> querySalesListByBillingGroupCode(List<String> code){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("active", FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE + "querySalesListByBillingGroupCode", map);
	}
	
	/**
	 * 根据营业部编码查询所属集中开单组
	 * @author 026123-foss-lifengteng
	 * @date 2013-5-6 下午6:33:35
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SalesBillingGroupEntity> queryBillingGroupListBySalesCode(String code){
		Map<String, String> map = new HashMap<String, String>();
		map.put("code", code);
		map.put("active", FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE + "queryBillingGroupListBySalesCode", map);
	}
}
