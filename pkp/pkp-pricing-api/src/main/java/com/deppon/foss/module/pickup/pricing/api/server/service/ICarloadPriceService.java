package com.deppon.foss.module.pickup.pricing.api.server.service;

import java.util.List;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CarloadPriceDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CarloadPriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CarloadPriceOrgEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.CarloadPricePlanDto;

public interface ICarloadPriceService extends IService {
	/**
     * 
     * <p>(查价整车价格参数波动方案信息)</p> 
     * 
     * @author DP-Foss-PanGuoYang
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return List<CarloadPriceEntity>
     */
	List<CarloadPricePlanDto> queryCarloadPricePlanBatchInfo(
			CarloadPricePlanDto carloadPricePlanDto, int start, int limit);
	/**
     * 
     * <p>(查价整车价格参数波动方案信息)</p> 
     * 
     * @author DP-Foss-PanGuoYang
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return Long
     */
	Long queryCarloadPricePlanBatchInfoCount(
			CarloadPricePlanDto carloadPricePlanDto);
	/**
     * 
     * <p>(新增整车价格参数方案)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-9 上午10:04:06
     * 
     * @return
     * 
     * @see
     */
	CarloadPriceEntity addCarloadPricePlan(
			CarloadPriceEntity carloadPriceEntity,
			List<CarloadPriceOrgEntity> carloadPriceOrgEntityList);
	
	/**
     * 
     * <p>(新增整车价格参数方案明细)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-9 上午10:04:06
     * 
     * @return
     * 
     * @see
     */
	List<CarloadPriceDetailEntity> addCarloadPriceDetail(
			CarloadPriceDetailEntity carloadPriceDetailEntity);
	 /**
     * 
     * <p>(修改整车价格参数波动方案明细)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	List<CarloadPriceDetailEntity> updateCarloadPriceDetailPlan(
			CarloadPriceDetailEntity carloadPriceDetailEntity);
	
	/**
     * <p>(删除所选择整车价格参数波动方案明细)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	List<CarloadPriceDetailEntity> deleteCarloadPricePlanDetail(
			List<String> carPlanIds);
	
	/**
     * <p>(激活整车价格参数波动方案)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	void activeCarloadPricePlan(List<String> carPlanIds);
	
	/**
     * <p>(删除整车价格参数波动方案)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	void deleteCarloadPricePlan(List<String> carPlanIds);
	
	/**
     * <p>(立即激活整车价格参数波动方案)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	void immediatelyActiveCarloadPricePlan(CarloadPriceEntity carloadPriceEntity);
	
	/**
     * <p>(立即中止整车价格参数波动方案)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	void immediatelystopCarloadPricePlan(CarloadPriceEntity carloadPriceEntity);
	
	/**
     * <p>(导出整车价格参数波动方案)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	ExportResource exportCarloadPricePlan(
			CarloadPricePlanDto carloadPricePlanDto);
	/**
     * <p>(查询整车价格参数波动方案明细)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	List<CarloadPriceDetailEntity> queryCarloadPricePlanDetailInfo(
			CarloadPricePlanDto carloadPricePlanDto,
			CarloadPriceDetailEntity carloadPriceDetailEntity);
	
	
	CarloadPriceEntity queryCarloadPricePlanAndOrgInfo(
			CarloadPricePlanDto carloadPricePlanDto);
	
	List<CarloadPricePlanDto> queryCarloadPricePlanDtos(
			CarloadPricePlanDto carloadPricePlanDto);
	
	/**
     * <p>(修改整车价格参数波动方案)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */ 
	void updateCarloadPricePlan(
			CarloadPriceEntity carloadPriceEntity ,
			List<CarloadPriceOrgEntity> carloadPriceOrgEntityList);
	
	List<CarloadPriceDetailEntity> carloadPriceDetailList(
			CarloadPricePlanDto carloadPricePlanDto);
	/**
     * <p>(复制整车价格参数波动方案)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */ 
	String copyCarloadPricePlan(CarloadPricePlanDto carloadPricePlanDto);
	
	/**
     * <p>(中止整车价格参数波动方案)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */ 
	void stopCarloadPricePlan(CarloadPriceEntity carloadPriceEntity);
	//分页
	Long queryCarloadPricePlanCount(CarloadPricePlanDto carloadPricePlanDto);
	
	/**
     * <p>(导出整车价格参数波动方案明细)</p> 
     * 
     * @author DP-Foss-潘国仰
     * 
     * @date 2014-04-08 下午09:03:29
     * 
     * @return String
     * 
     * @see
     */
	ExportResource exportCarloadPricePlanDetail(
			CarloadPricePlanDto carloadPricePlanDto);
 	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.server.dao.impl.CarloadPricePlanDao.selectCarloadPricePlanDetailByOrganizationCode
	 * @Description:通过组织编码查询当前版本的整车价格波动参数方案
	 *
	 * @param carloadPricePlanDto
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2014-11-15 下午3:29:29
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2014-11-15    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	public List<CarloadPricePlanDto> selectCarloadPricePlanDetailByOrganizationCode(
				CarloadPricePlanDto carloadPricePlanDto);
}
