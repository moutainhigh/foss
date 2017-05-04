package com.deppon.foss.module.pickup.pricing.api.server.service;

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PricePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEcGoodsArriveEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEcGoodsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PricePlanDetailDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryPricePlanDetailBean;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.EcGoodsPriceManageMentVo;

/**
 * @Description:首续重价格方案管理service接口
 * @author cc
 * @date 2016-07-04
 * @version 1.0
 */
public interface IEcGoodsPricePlanService extends IService {

	/**
	 * Excel批量导入价格方案0
	 */
	void addPricePlanBatch(Map<String, List<PricePlanDetailDto>> detailMap,
						   Map<String, PriceRegionEcGoodsEntity> priceRegionEntityMap,
						   Map<String, PriceRegionEcGoodsArriveEntity> priceArrvRegionEntityMap,
						   Map<String, ProductEntity> productEntityMap);

	/**
	 * 分页查询价格方案1
	 */
	List<PricePlanEntity> queryPricePlanBatchInfo(PricePlanEntity pricePlanEntity, int start,int limit);

	/**
	 * 价格方案查询总数1
	 */
	Long queryPricePlanBatchInfoCount(PricePlanEntity pricePlanEntity);

	/**
	 * 分页查询价格方案明细2
	 */
	List<PricePlanDetailDto> queryPricePlanDetailInfo(QueryPricePlanDetailBean queryPricePlanDetailBean,int start,int limit);

	/**
	 * 查询价格方案明细总记录数2
	 */
	Long queryPricePlanDetailInfoCount(QueryPricePlanDetailBean queryPricePlanDetailBean);

	/**
	 * 查询需要修改的价格方案3、9
	 */
	EcGoodsPriceManageMentVo queryCopyPricePlanInfo(String pricePlanId);

	/**
	 * 修改价格方案信息4
	 */
	PricePlanEntity modifyPricePlan(PricePlanEntity priceEntity);

	/**
	 * 删除方案5
	 */
	void deletePricePlan(List<String> pricePlanIds);

	/**
	 * 立即激活价格方案6
	 */
	void immediatelyActivePricePlan(PricePlanEntity pricePlanEntity);

	/**
	 * 批量中止方案7、8
	 */
	void stopPricePlan(PricePlanEntity pricePlanEntity);

	/**
	 * 导出价格方案10
	 */
	ExportResource  exportPricePlan(PricePlanEntity pricePlanEntity);

	/**
	 * 价格方案明细导出11
	 */
	ExportResource  exportPricePlanDetail(QueryPricePlanDetailBean queryPricePlanDetailBean);
}