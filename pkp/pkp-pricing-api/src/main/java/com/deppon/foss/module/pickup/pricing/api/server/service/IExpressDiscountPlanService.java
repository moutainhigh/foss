package com.deppon.foss.module.pickup.pricing.api.server.service;

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ExpressDiscountEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionExpressEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ExpressDiscountDto;
/**
 * 
 * Copyright (C) 2015 Asiainfo-Linkage
 *
 *
 * @className:com.deppon.foss.module.pickup.pricing.api.server.service.IExpressDiscountPlanService
 * @description:快递折扣方案service接口
 *
 * @version:v1.0.0
 * @author:DP-FOSS-YANGKANG
 *
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2015-1-9     DP-FOSS-YANGKANG       v1.0.0        create
 *
 *
 */
public interface IExpressDiscountPlanService extends IService{
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.api.server.service.IExpressDiscountPlanService.insertSelective
	 * @Description:新增快递折扣方案   根据传入实体属性值是否为空进行选择性的插入
	 *
	 * @param record
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-8 下午4:29:00
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2015-1-8    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	void insertSelective(ExpressDiscountEntity entity);
	
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.api.server.service.IExpressDiscountPlanService.deleteByPrimaryKey
	 * @Description:根据快递折扣方案的ID删除方案信息 支持批量删除操作
	 *
	 * @param id
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-8 下午4:31:57
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2015-1-8    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	int deleteByIds(List<String> discountPlanIds);
	
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.api.server.service.IExpressDiscountPlanService.updateByIdSelective
	 * @Description: 修改快递折扣方案信息
	 *
	 * @param entity
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-8 下午4:34:19
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2015-1-8    DP-FOSS-YANGKANG      v1.0.0         create
	 */
    int updateByIdSelective(ExpressDiscountEntity entity,List<String> oldChannelCodes);
    
    /**
     * 
     *
     * @Function: com.deppon.foss.module.pickup.pricing.api.server.service.IExpressDiscountPlanService.selectById
     * @Description:分页查询快递折扣方案 根据传入的实体属性进行查询   其中方案名称进行模糊查询
     *
     * @param id
     * @return
     *
     * @version:v1.0
     * @author:DP-FOSS-YANGKANG
     * @date:2015-1-8 下午4:36:01
     *
     * Modification History:
     * Date         Author      Version     Description
     * -----------------------------------------------------------------
     * 2015-1-8    DP-FOSS-YANGKANG      v1.0.0         create
     */
    List<ExpressDiscountEntity> queryExpressDiscountPlanList(ExpressDiscountEntity entity,int start,int limit);
    
    /**
     * 
     *
     * @Function: com.deppon.foss.module.pickup.pricing.api.server.service.IExpressDiscountPlanService.queryExpressDiscountPlanListCount
     * @Description:查询满足当前查询条件的快递折扣方案的数量
     *
     * @param entity
     * @return
     *
     * @version:v1.0
     * @author:DP-FOSS-YANGKANG
     * @date:2015-1-8 下午4:53:37
     *
     * Modification History:
     * Date         Author      Version     Description
     * -----------------------------------------------------------------
     * 2015-1-8    DP-FOSS-YANGKANG      v1.0.0         create
     */
    Long queryExpressDiscountPlanListCount(ExpressDiscountEntity entity);
    
    /**
     * 
     *
     * @Function: com.deppon.foss.module.pickup.pricing.api.server.service.IExpressDiscountPlanService.activeExpressDiscountPlan
     * @Description:根据方案ID激活方案 
     *
     * @param entity
     * @return
     *
     * @version:v1.0
     * @author:DP-FOSS-YANGKANG
     * @date:2015-1-8 下午5:02:48
     *
     * Modification History:
     * Date         Author      Version     Description
     * -----------------------------------------------------------------
     * 2015-1-8    DP-FOSS-YANGKANG      v1.0.0         create
     */
    void activeExpressDiscountPlan(ExpressDiscountEntity entity);
    /**
     * 
     *
     * @Function: com.deppon.foss.module.pickup.pricing.api.server.service.IExpressDiscountPlanService.stopExpressDiscountPlan
     * @Description:中止快递折扣方案
     *
     * @param entity
     *
     * @version:v1.0
     * @author:DP-FOSS-YANGKANG
     * @date:2015-1-9 下午4:43:05
     *
     * Modification History:
     * Date         Author      Version     Description
     * -----------------------------------------------------------------
     * 2015-1-9    DP-FOSS-YANGKANG      v1.0.0         create
     */
	void stopExpressDiscountPlan(ExpressDiscountEntity entity);
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.api.server.service.IExpressDiscountPlanService.insertDiscountDetailSelective
	 * @Description:新增快递折扣方案明细信息
	 *
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-10 下午2:16:12
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2015-1-10    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	void insertDiscountDetailSelective(ExpressDiscountDto detailEntity);
	
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.api.server.service.IExpressDiscountPlanService.queryExpressDiscountPlanDetailList
	 * @Description:查询快递折扣方案明细信息
	 *
	 * @param entity
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-9 下午6:39:27
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2015-1-9    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	List<ExpressDiscountDto> queryExpressDiscountPlanDetailList(ExpressDiscountDto entity, int start, int limit);
	
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.api.server.service.IExpressDiscountPlanService.queryExpressDiscountPlanDetailListCount
	 * @Description:查询快递折扣方案明细信息记录总数
	 *
	 * @param entity
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-10 上午10:42:15
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2015-1-10    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	Long queryExpressDiscountPlanDetailListCount(ExpressDiscountDto detailEntity);
	
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.api.server.service.IExpressDiscountPlanService.updateDiscountDetailSelective
	 * @Description:修改快递折扣明细信息
	 *
	 * @param entity
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-10 上午10:58:33
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2015-1-10    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	void updateDiscountDetailSelective(ExpressDiscountDto detailEntity);
	
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.api.server.service.IExpressDiscountPlanService.deleteDiscountDetailById
	 * @Description:根据明细ID删除折扣明细信息
	 *
	 * @param detailEntity
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-10 上午11:00:26
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2015-1-10    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	void deleteDiscountDetailById(ExpressDiscountEntity discountEntity,List<String> discountDetailIds);
	
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.api.server.service.IExpressDiscountPlanService.queryExpressDiscountPlanById
	 * @Description:根据快递折扣方案ID查询折扣方案信息
	 *
	 * @param discountEntity
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-12 下午5:14:16
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2015-1-12    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	ExpressDiscountEntity queryExpressDiscountPlanById(ExpressDiscountEntity discountEntity);
	
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.api.server.service.IExpressDiscountPlanService.queryExpressDiscountPlanDetailByCondition
	 * @Description:根据查询条件查询快递折扣方案明细信息(不分页)
	 *
	 * @param detailEntity
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-14 上午10:08:10
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2015-1-14    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	List<ExpressDiscountDto> queryExpressDiscountPlanDetailByCondition(ExpressDiscountDto detailEntity);
	
	/***
	 * 
	* @author: 200945 吴涛
	* @Title: upgradeExpressDiscountPlan 
	* @Description: TODO(用于快递折扣主方案的升级) 
	* @param @param discountEntity
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	void upgradeExpressDiscountPlan(ExpressDiscountEntity discountEntity);
	/***
	 * 
	* @author: 王增明
	* @Title: addDiscountBatch 
	* @Description: TODO(用于快递折扣主方案的批量导入) 
	* @param @param discountEntity
	* @param @return    设定文件 
	* @return  返回类型 
	* @throws
	 */
	
	void addDiscountBatch(Map<String, List<String>> discountMap,
			Map<String, List<String>> detailMap,
			Map<String, GoodsTypeEntity> googsTypeEntityMap,
			Map<String, PriceRegionExpressEntity> priceRegionEntityMap,
			Map<String, ProductEntity> productEntityMap,
			Map<String, CustomerEntity> customerEntityMap,Map<String,String> weeksEntityMap,Map<String,List<DataDictionaryValueEntity>> channelEntityMap);
	/***
	 * 
	* @author: 王增明
	* @Title: findExpressDiscountByPlanNames 
	* @Description: TODO(根据多个方案名称查出返回类型map) 
	* @param @param names
	* @param @return    设定文件 
	* @return Map    返回类型 
	* @throws
	 */
	Map<String, ExpressDiscountEntity> findExpressDiscountByPlanNames(
			List<String> names);
}
