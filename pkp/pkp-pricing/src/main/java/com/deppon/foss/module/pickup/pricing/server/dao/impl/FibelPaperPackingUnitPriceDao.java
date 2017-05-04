package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IfibelPaperPackingUnitPriceDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceFibelPaperPackingEntity;
/**
 * 纸纤包装价格的各项单价数据的增删改查
 * @author:218371-foss-zhaoyanjun
 * @date:2014-11-15下午15:53
 */
public class FibelPaperPackingUnitPriceDao extends SqlSessionDaoSupport implements IfibelPaperPackingUnitPriceDao{
	/**
	 * 插入纸纤包装单价
	 */
	private static final String PFPEntity="foss.pkp.pkp-pricing.priceFibelPaperPackingEntityMapper.";
	// 计价方式明细ibatis命名空间
	private static final String PRICING_ENTITY_CRITERIADETAIL = "foss.pkp.pkp-pricing.priceCriteriaDetailEntityMapper.";
	/**
	 * 按照计费规则ID查询纸纤包装计价方式明细
	 */
	private static final String SELECTBYVALUATIONIDANDCODE = "selectByValuationIdAndCode";
	
	/**
	 * 纤纸包装单价插入
	 */
	@Override
	public void insertPricingFibelPaper(
			PriceFibelPaperPackingEntity priceFibelPaperPackingEntity) {
		// TODO Auto-generated method stub
		//创建时间
		priceFibelPaperPackingEntity.setCreateDate(priceFibelPaperPackingEntity.getCreateDate()==null?new Date():priceFibelPaperPackingEntity.getCreateDate());
		//修改时间
		priceFibelPaperPackingEntity.setModifyDate(new Date());
		getSqlSession().insert(PFPEntity + "insertEntity", priceFibelPaperPackingEntity);
	}
	/**
	 * 纤纸包装单价修改
	 */
	@Override
	public void updatePricingFibelPaper(
			PriceFibelPaperPackingEntity priceFibelPaperPackingEntity) {
		getSqlSession().update(PFPEntity + "updateEntity", priceFibelPaperPackingEntity);
	}
	/**
	 * 纸纤包装单价删除
	 */
	@Override
	public void deletePricingFibelPaper(List<String> valuationIds) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("valuationIds", valuationIds);
		getSqlSession().delete(PFPEntity + "deleteEntity", map);
	}
	
	/**
	 * 根据Id查询出CriteriaDetailEntity信息和纸纤包装信息
	 * @author:218371-foss-zhaoyanjun
	 * @date:2014-11-24上午11:21
	 */
	@Override
	public PriceFibelPaperPackingEntity selectByValuationIdAndCode(
			String valuationId) {
		List<PriceFibelPaperPackingEntity> list=getSqlSession().selectList(
				PRICING_ENTITY_CRITERIADETAIL + SELECTBYVALUATIONIDANDCODE,
				valuationId);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
}
