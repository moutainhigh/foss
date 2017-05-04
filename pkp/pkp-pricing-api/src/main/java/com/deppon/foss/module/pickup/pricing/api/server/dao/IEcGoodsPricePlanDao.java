package com.deppon.foss.module.pickup.pricing.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceCriteriaDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PricePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceValuationEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryExistPricePlanDetailBean;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryPricePlanDetailBean;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ResultPricePlanDetailBean;

/**
 * @Description	首续重价格方案管理dao接口实现
 * @author		348757-cc
 * @date		2016-07-04
 * @version		1.0
 */
public interface IEcGoodsPricePlanDao {

	/**
	 * 分页查询价格方案信息1、10
	 */
	List<PricePlanEntity> queryPricePlanBatchInfo(PricePlanEntity pricePlanEntity,int start, int limit);

	/**
	 * 查询方案总记录数1
	 */
	Long queryPricePlanBatchInfoCount(PricePlanEntity record);

	/**
	 * 分页查询价格方案明细2、11
	 */
	List<ResultPricePlanDetailBean> queryPricePlanDetailInfo(QueryPricePlanDetailBean queryPricePlanDetailBean,int start,int limit);

	/**
	 * 查询价格方案明细总记录数2
	 */
	Long queryPricePlanDetailInfoCount(QueryPricePlanDetailBean queryPricePlanDetailBean);

	/**
	 * 通过Id查询价格方案主信息3、4、6、7、8、9
	 */
	PricePlanEntity selectByPrimaryKey(String id);

	/**
	 * 修改方案4
	 */
	int updateByPrimaryKeySelective(PricePlanEntity record);

	/**
	 * 查询方案的重复信息6
	 */
	List<ResultPricePlanDetailBean> isExistRpeatPricePlanDetailData(QueryExistPricePlanDetailBean queryExistPricePlanDetailBean);

	/**
	 * 激活价格方案6
	 */
	int activePricePlan(String pricePlanId ,Date beginTime);

	/**
	 * 批量插入价格方案和规则明细0、9
	 */
	int insertPricePlanAllBatch(List<PricePlanEntity> pricePlanBatch,List<PriceValuationEntity> priceValuationBatch,List<PriceCriteriaDetailEntity> priceCriteriaDetailEntityBatch);
	
	/**
	 * 批量删除价格方案5
	 */
	void batchDeletePlan(List<String> pricePlanIds);

	/**
	 * 中止价格方案7、8
	 */
	void stopPricePlan(String pricePlanId, Date endTime);
}