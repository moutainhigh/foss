package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPopValueAddedDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.pop.PriceIndustryEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.pop.PriceProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.pop.PriceValueAddedEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ResultProductPriceDto;

/**
 * 
 * <p>
 * Description:增值服务dao<br />
 * </p>
 * 
 * @title PopValueAddedDao.java
 * @package com.deppon.foss.module.pickup.pricing.server.dao.impl
 * @author xx
 * @version 0.1 2014年10月15日
 */
public class PopValueAddedDao extends SqlSessionDaoSupport implements IPopValueAddedDao {
	/**
	 * 增值服务ibatis命名空间
	 */
	private static final String PRICING_ENTITY_VALUEADDED = "foss.pkp.pkp-pricing.popValueAddedEntityMapper.";
	// 插入增值服务
	private static final String INSERTSELECTIVE = "insertSelective";
	// 根据名称查询
	private static final String SELECTBYNAME = "selectByName";
	// 根据主键ID查询增值服务
	private static final String SELECTBYPK = "selectByPrimaryKey";
	// 根据条件查询增值服务
	private static final String SELECTBYCONDITIONSQ = "selectByCoditionSq";
	// 根据条件查询增值服务
	private static final String SELECTBYCONDITION = "selectByCodition";
	// 修改增值服务
	private static final String UPDATEVALUEADDED = "updateValueAdded";
	// 根据条件统计增值服务(特殊)
	private static final String COUNTBYCONDITION = "countByCodition";
	// 激活增值服务
	private static final String ACTIVEVALUEADDED = "activeValueAdded";
	// 删除未增值服务
	private static final String DELETEVALUEADDED = "deleteValueAdded";
	// 插入产品
	private static final String INSERTPRODUCT = "insertProduct";
	// 查询产品
	private static final String SEARCHPRODUCTLIST = "searchProductList";
	// 插入行业
	private static final String INSERTINDUSTRY = "InsertIndustry";
	// 查询行业
	private static final String SEARCHINDUSTRYLIST = "searchIndustryList";
	/** 开单查询增值服务费用计算 **/
	private static final String SEARCH_VALUE_ADDED_PRICING_VALUATION = "searchValueAddedPricingValuationByCondition";
	/** 开单查询增值服务费用计算 **/
	private static final String SEARCH_VALUE_ADDED_PRICING_VALUATIONPIC = "searchValueAddedPricingValuationByConditionPic";

	/**
	 * 
	 * <p>
	 * Description:根据名字和类型查询增值服务<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月16日
	 * @param record
	 * @return
	 *
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceValueAddedEntity> selectByName(PriceValueAddedEntity record) {
		return getSqlSession().selectList(PRICING_ENTITY_VALUEADDED + SELECTBYNAME, record);

	}

	/**
	 * 
	 * <p>
	 * Description:根据条件查询增值服务<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月16日
	 * @param record
	 * @return
	 *
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceValueAddedEntity> selectByCoditionSq(PriceValueAddedEntity record) {
		return getSqlSession().selectList(PRICING_ENTITY_VALUEADDED + SELECTBYCONDITIONSQ, record);
	}

	/**
	 * 
	 * <p>
	 * Description:更新增值服务<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月16日
	 * @param record
	 * @return
	 *
	 */
	@Override
	public int updateValueAdded(PriceValueAddedEntity record) {
		return getSqlSession().update(PRICING_ENTITY_VALUEADDED + UPDATEVALUEADDED, record);
	}

	/**
	 * 
	 * <p>
	 * Description:插入增值服务<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月15日
	 * @param record
	 * @return
	 *
	 */
	@Override
	public int insertSelective(PriceValueAddedEntity record) {
		return getSqlSession().insert(PRICING_ENTITY_VALUEADDED + INSERTSELECTIVE, record);
	}

	/**
	 * 
	 * <p>
	 * Description:根据条件查询增值服务带分页<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月16日
	 * @param record
	 * @param start
	 * @param limit
	 * @return
	 *
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceValueAddedEntity> selectByCoditionNew(PriceValueAddedEntity record, int start, int limit) {
		// 设置分页条件
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(PRICING_ENTITY_VALUEADDED + "queryByCodition", record, rowBounds);
	}

	/**
	 * 
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月16日
	 * @param record
	 * @return
	 *
	 */
	@Override
	public Long countByCodition(PriceValueAddedEntity record) {
		return (Long) getSqlSession().selectOne(PRICING_ENTITY_VALUEADDED + COUNTBYCONDITION, record);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PriceValueAddedEntity> selectByCodition(PriceValueAddedEntity record) {
		// 设置分页条件
		return getSqlSession().selectList(PRICING_ENTITY_VALUEADDED + SELECTBYCONDITION, record);

	}

	/**
	 * 
	 * <p>
	 * Description:激活增值服务<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月16日
	 * @param valueAddedIds
	 *
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void activeValueAdded(List<String> valueAddedIds) {
		Map map = new HashMap();
		map.put("valueAddedIds", valueAddedIds);
		map.put("versionNo", new Date().getTime());
		getSqlSession().update(PRICING_ENTITY_VALUEADDED + ACTIVEVALUEADDED, map);
	}

	/**
	 * 
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月16日
	 * @param valueAddedIds
	 *
	 */
	@Override
	public void deleteValueAdded(List<String> valueAddedIds) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("valueAddedIds", valueAddedIds);
		getSqlSession().delete(PRICING_ENTITY_VALUEADDED + DELETEVALUEADDED, map);

	}

	/**
	 * 
	 * <p>
	 * Description:根据主键查询增值服务<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月20日
	 * @param id
	 * @return
	 *
	 */

	@Override
	public PriceValueAddedEntity selectByPrimaryKey(String id) {
		return (PriceValueAddedEntity) getSqlSession().selectOne(PRICING_ENTITY_VALUEADDED + SELECTBYPK, id);

	}

	/**
	 * 
	 * <p>
	 * Description:插入产品信息<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月21日
	 * @param product
	 *
	 */
	@Override
	public void insertPriceProductEntity(PriceProductEntity product) {
		getSqlSession().insert(PRICING_ENTITY_VALUEADDED + INSERTPRODUCT, product);

	}

	/**
	 * 
	 * <p>
	 * Description:插入行业信息<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月21日
	 * @param industry
	 *
	 */
	@Override
	public void insertPriceIndustryEntity(PriceIndustryEntity industry) {
		getSqlSession().insert(PRICING_ENTITY_VALUEADDED + INSERTINDUSTRY, industry);

	}

	/**
	 * 
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月21日
	 * @param valueAdddId
	 *
	 */
	@Override
	public List<PriceProductEntity> selectPriceProductEntityByValueAddedId(String valueAdddId, String tableName) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("tableName", tableName);
		map.put("tableId", valueAdddId);

		return getSqlSession().selectList(PRICING_ENTITY_VALUEADDED + SEARCHPRODUCTLIST, map);
	}

	@Override
	public List<PriceIndustryEntity> selectPriceIndustryEntityByValueAddedId(String valueAdddId, String tableName) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("tableName", tableName);
		map.put("tableId", valueAdddId);

		return getSqlSession().selectList(PRICING_ENTITY_VALUEADDED + SEARCHINDUSTRYLIST, map);
	}

	/**
	 * 
	 * <p>
	 * Description:根据id删除产品<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月21日 void
	 */
	public void deleteProductEntityById(String id) {
		getSqlSession().delete(PRICING_ENTITY_VALUEADDED + "deleteProductById", id);

	}

	/**
	 * 
	 * <p>
	 * Description:根据id删除行业<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月21日 void
	 */
	public void deleteIndustryEntityById(String id) {
		getSqlSession().delete(PRICING_ENTITY_VALUEADDED + "deleteIndustryById", id);

	}

	/**
	 * 根据tableId查询产品list
	 */
	public List<PriceProductEntity> queryProductListByTableId(String tableId) {
		return getSqlSession().selectList(PRICING_ENTITY_VALUEADDED + "queryProductListByTableId", tableId);
	}

	@Override
	public List<ResultProductPriceDto> searchValueAddedPricingValuationByCondition(
			QueryBillCacilateValueAddDto queryBillCacilateValueAddDto) {
		return getSqlSession().selectList(PRICING_ENTITY_VALUEADDED + SEARCH_VALUE_ADDED_PRICING_VALUATION,
				queryBillCacilateValueAddDto);

	}
	@Override
	public List<ResultProductPriceDto> searchValueAddedPricingValuationByConditionPic(
			QueryBillCacilateValueAddDto queryBillCacilateValueAddDto) {
		return getSqlSession().selectList(PRICING_ENTITY_VALUEADDED + SEARCH_VALUE_ADDED_PRICING_VALUATIONPIC,
				queryBillCacilateValueAddDto);

	}
}
