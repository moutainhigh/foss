package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICusOrderSourceDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusOrderSourceEntity;
import com.deppon.foss.util.define.FossConstants;
/**
 * CRM行业、客户等级、订单来源信息Dao接口实现
 * @author dujunhui-187862
 * @date 2014-9-25 下午4:40:14
 * @since
 * @version
 */
public class CusOrderSourceDao extends SqlSessionDaoSupport implements ICusOrderSourceDao{
	
	private static final String NAMESPACE_CUSORDERSOURCE = "foss.bse.bse-baseinfo.cusOrderSource.";
	
	/**
	 * 新增CRM行业、客户等级、订单来源信息
	 * @author dujunhui-187862
	 * @date 2014-9-25 下午4:41:36
	 * @param entity 信息实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	@Override
	public int addCusOrderSource(CusOrderSourceEntity entity) {
		if(StringUtils.equals(entity.getImportPattern(), NumberConstants.NUMERAL_S_ONE)){
			return this.getSqlSession().
					insert(NAMESPACE_CUSORDERSOURCE + "insertCusProfession", entity);
		}else if(StringUtils.equals(entity.getImportPattern(), NumberConstants.NUMERAL_S_TWO)){
			return this.getSqlSession().
					insert(NAMESPACE_CUSORDERSOURCE + "insertCusDegree", entity);
		}else if(StringUtils.equals(entity.getImportPattern(), NumberConstants.NUMERAL_S_THREE)){
			return this.getSqlSession().
					insert(NAMESPACE_CUSORDERSOURCE + "insertOrderSource", entity);
		}
		return FossConstants.FAILURE;
	}
	
	/**
	 * 修改CRM行业、客户等级、订单来源信息(方法作废)
	 * @author dujunhui-187862
	 * @date 2014-9-25 下午4:41:36
	 * @param entity 信息实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	@Override
	public int updateCusOrderSource(CusOrderSourceEntity entity) {
//		if(StringUtils.equals(entity.getImportPattern(), NumberConstants.NUMERAL_S_ONE)){
//			return this.getSqlSession().
//					update(NAMESPACE_CUSORDERSOURCE + "updateCusProfession",entity);
//		}else if(StringUtils.equals(entity.getImportPattern(), NumberConstants.NUMERAL_S_TWO)){
//			return this.getSqlSession().
//					update(NAMESPACE_CUSORDERSOURCE + "updateCusDegree", entity);
//		}else if(StringUtils.equals(entity.getImportPattern(), NumberConstants.NUMERAL_S_THREE)){
//			return this.getSqlSession().
//					update(NAMESPACE_CUSORDERSOURCE + "updateOrderSource", entity);
//		}
		return FossConstants.FAILURE;
	}
	
	/**
	 * 作废CRM行业、客户等级、订单来源信息
	 * @author dujunhui-187862
	 * @date 2014-11-14 上午9:35:24
	 * @param 
	 * @return 1：成功；-1：失败
	 * @see
	 */
	@Override
	public int deleteCusOrderSource(CusOrderSourceEntity entity) {
		if(StringUtils.equals(entity.getImportPattern(), NumberConstants.NUMERAL_S_ONE)){
			return this.getSqlSession().
					update(NAMESPACE_CUSORDERSOURCE + "deleteCusProfession", entity);
		}else if(StringUtils.equals(entity.getImportPattern(), NumberConstants.NUMERAL_S_TWO)){
			return this.getSqlSession().
					update(NAMESPACE_CUSORDERSOURCE + "deleteCusDegree", entity);
		}else if(StringUtils.equals(entity.getImportPattern(), NumberConstants.NUMERAL_S_THREE)){
			return this.getSqlSession().
					update(NAMESPACE_CUSORDERSOURCE + "deleteOrderSource", entity);
		}
		return FossConstants.FAILURE;
	}
	
	/**
	 * <p>根据主编码查询信息实体</p>
	 * @author dujunhui-187862
	 * @date 2014-9-25 下午4:41:36
	 * @param 
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public CusOrderSourceEntity queryCusOrderSourceByCode(String importPattern, 
			String cusOrderSourceCode) {
		CusOrderSourceEntity entity=new CusOrderSourceEntity();
		if(StringUtils.equals(importPattern, NumberConstants.NUMERAL_S_ONE)){
			entity.setSecDegreeProfessionCode(cusOrderSourceCode);
			List<CusOrderSourceEntity> list=this.getSqlSession().
					selectList(NAMESPACE_CUSORDERSOURCE + "queryCusProfessionByCode",entity);
			return CollectionUtils.isEmpty(list) ? null:list.get(0);
		}else if(StringUtils.equals(importPattern, NumberConstants.NUMERAL_S_TWO)){
			entity.setCustomerDegreeCode(cusOrderSourceCode);
			List<CusOrderSourceEntity> list=this.getSqlSession().
					selectList(NAMESPACE_CUSORDERSOURCE + "queryCusDegreeByCode", entity);
			return CollectionUtils.isEmpty(list) ? null:list.get(0);
		}else if(StringUtils.equals(importPattern, NumberConstants.NUMERAL_S_THREE)){
			entity.setOrderSourceCode(cusOrderSourceCode);
			List<CusOrderSourceEntity> list = this.getSqlSession().
						selectList(NAMESPACE_CUSORDERSOURCE + "queryOrderSourceByCode", entity);
			return CollectionUtils.isEmpty(list) ? null:list.get(0);
		}
		return null;
	}

}
