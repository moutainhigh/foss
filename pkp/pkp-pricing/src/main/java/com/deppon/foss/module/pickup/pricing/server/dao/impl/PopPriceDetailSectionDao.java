package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPopPriceDetailSectionDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.pop.PopPriceDetailSectionEntity;


/****
 * 计价明细分段dao
 * 
 * @author POP-Team-LuoMengXiang
 *
 *@category 2014/10/21
 */
public class PopPriceDetailSectionDao extends SqlSessionDaoSupport implements IPopPriceDetailSectionDao {
    //计价方式分段明细的mybatis的命名空间
	public static final String POP_PRICE_DETAIL_SECTION="foss.pkp.pkp-pricing.PopPriceDetailSectionEntityMapper.";
	//新增计价方式分段明细
	public static final String INSERT_PRICE_DETAIL_SECTION="insertPriceDetailSection";
	//根据ID查询计价方式分段明细记录
	public static final String SELECT_ID_PRICE_DETAIL_SECTION="selectById";
	//根据规则ID查询计价方式分段明细记录
	public static final String SELECT_VALUATIONID_PRICE_DETAIL_SECTION="selectByValuationId";
	//根据规则ID删除计价方式分段明细记录
	public static final String DELETE_VALUATIONID_PRICE_DETAIL_SECTION="deleteByValuationId";
	//根据规则ID修改计价方式分段明细记录
	public static final String UPDATE_VALUATIONID_PRICE_DETAIL_SECTION="updateByValuationId";
	//根据分段ID删除计价方式分段明细记录
	public static final String DELETE_SECTIONID_PRICE_DETAIL_SECTION="deleteBySectionId";
	//修改操作的新增计价分段明细
	public static final String UPDATE_INSERT_PRICE_DETAIL_SECTION="insertSectionDetail";
	//根据价格方案ID查询计价方式分段明细记录
	public static final String QUERY_SECTIONLIST_BY_CONDITION="querySectionListByCondition";
	
	/**
	 * 插入计价方式分段明细
	 * 
	 * @author POP-Team-LuoMengxiang
	 * 
	 * @category 2014/10/21
	 */
	
	public void insertPriceDetailSection(PopPriceDetailSectionEntity record) {
            getSqlSession().insert(POP_PRICE_DETAIL_SECTION+INSERT_PRICE_DETAIL_SECTION,record);	
	}
	/**
	 * 根据ID查询计价方式分段明细记录
	 * @author POP-Team-LuoMengXiang
	 * @category 2014/10/28
	 */
	public List<PopPriceDetailSectionEntity> selectById(
			String  id) {
		return  getSqlSession().selectList(POP_PRICE_DETAIL_SECTION+SELECT_ID_PRICE_DETAIL_SECTION,id);
	}
	/**
	 * 根据规则ID查询计价方式分段明细记录
	 * 
	 * @author POP-Team-LuoMengXiang
	 * 
	 * @category 2014/10/28
	 */
	public List<PopPriceDetailSectionEntity> selectByValuationId(
			String valuationId) {
		return getSqlSession().selectList(POP_PRICE_DETAIL_SECTION+SELECT_VALUATIONID_PRICE_DETAIL_SECTION,valuationId);
	}
	public void deleteByValuationId(String valuationId) {
		  getSqlSession().delete(POP_PRICE_DETAIL_SECTION+DELETE_VALUATIONID_PRICE_DETAIL_SECTION,valuationId);
	}
	/**
	 * 根据规则ID修改计价方式分段明细记录
	 * 
	 * @author Pop-Team-Luomengxiang
	 * 
	 * @category 2014.11.4
	 */
	public void updateByValuationId(
			PopPriceDetailSectionEntity popPriceDetailSectionEntity) {
             getSqlSession().update(POP_PRICE_DETAIL_SECTION+UPDATE_VALUATIONID_PRICE_DETAIL_SECTION,popPriceDetailSectionEntity);		
	}
	/**
	 * 根据分段ID删除计价分段明细信息
	 * 
	 * @author Pop-Team-Luomengxiang
	 * 
	 * @date  2014.11.11
	 */
	public void deleteBySectionId(String sectionID) {
		 getSqlSession().delete(POP_PRICE_DETAIL_SECTION+DELETE_SECTIONID_PRICE_DETAIL_SECTION,sectionID);
	}
   /**
    * 修改操作的新增计价分段明细
    * 
    * @author Pop-Team-Luomengxiang
	 * 
	 * @date  2014.11.12
	 */
	public void insertSectionDetail(
			PopPriceDetailSectionEntity popPriceDetailSectionEntity) {
       getSqlSession().insert(POP_PRICE_DETAIL_SECTION+UPDATE_INSERT_PRICE_DETAIL_SECTION,popPriceDetailSectionEntity);		
	}
	
	/** 
	 * <p>[POP]根据条件查询全部分段价格信息</p> 
	 * @author foss-148246-sunjianbo
	 * @date 2014-11-11 上午11:35:00
	 * @param popPriceDetailSectionEntity
	 * @return 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IPopPriceDetailSectionDao#querySectionListByCondition(com.deppon.foss.module.pickup.pricing.api.shared.domain.pop.PopPriceDetailSectionEntity)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PopPriceDetailSectionEntity> querySectionListByCondition(
			PopPriceDetailSectionEntity popPriceDetailSectionEntity) {
		return getSqlSession().selectList(POP_PRICE_DETAIL_SECTION+QUERY_SECTIONLIST_BY_CONDITION,popPriceDetailSectionEntity);
	}
	
	/**
   	 * <p>根据明细id查询分段信息<p>
   	 * 
   	 * @author Pop-Team-Luomengxiang
   	 * 
   	 * @date  2015.01.24
   	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PopPriceDetailSectionEntity> selectByCriteriaId(
			String criteriaId) {
		return getSqlSession().selectList(
				POP_PRICE_DETAIL_SECTION+"selectByCriteriaId",criteriaId);
	}
	
	/**
	 * <p>根据价格方案id查询出价格分段数最多的分段价格明细记录信息</p> 
	 * @author foss-148246-sunjianbo
	 * @date 2015-2-5 上午9:03:55
	 * @param pricePlanId
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PopPriceDetailSectionEntity> querySectionScopeByPricePlanId(
			String pricePlanId) {
		return getSqlSession().selectList(
				POP_PRICE_DETAIL_SECTION+"querySectionScopeByPricePlanId",pricePlanId);
	}
}
