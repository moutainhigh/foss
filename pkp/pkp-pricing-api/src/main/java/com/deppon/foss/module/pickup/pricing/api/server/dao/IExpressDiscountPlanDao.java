package com.deppon.foss.module.pickup.pricing.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.ExpressDiscountEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ExpressDiscountDto;
/**
 * 
 * Copyright (C) 2015 Asiainfo-Linkage
 *
 *
 * @className:com.deppon.foss.module.pickup.pricing.api.server.dao.IExpressDiscountPlanDao
 * @description:快递折扣方案DAO 定义针对快递折扣主方案信息操作的一系列方法
 *
 * @version:v1.0.0
 * @author:DP-FOSS-YANGKANG
 *
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2015-1-8     DP-FOSS-YANGKANG       v1.0.0        create
 *
 *
 */
public interface IExpressDiscountPlanDao {
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.api.server.dao.IExpressDiscountPlanDao.insertSelective
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
	int insertSelective(ExpressDiscountEntity entity);
	
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.api.server.dao.IExpressDiscountPlanDao.deleteByPrimaryKey
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
	 * @Function: com.deppon.foss.module.pickup.pricing.api.server.dao.IExpressDiscountPlanDao.updateByIdSelective
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
    int updateByIdSelective(ExpressDiscountEntity entity);
    
    /**
     * 
     *
     * @Function: com.deppon.foss.module.pickup.pricing.api.server.dao.IExpressDiscountPlanDao.selectById
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
     * @Function: com.deppon.foss.module.pickup.pricing.api.server.dao.IExpressDiscountPlanDao.queryExpressDiscountPlanListCount
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
     * @Function: com.deppon.foss.module.pickup.pricing.api.server.dao.IExpressDiscountPlanDao.activeExpressDiscountPlan
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
     * @Function: com.deppon.foss.module.pickup.pricing.api.server.dao.IExpressDiscountPlanDao.stopExpressDiscountPlan
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
     * @Function: com.deppon.foss.module.pickup.pricing.api.server.dao.IExpressDiscountPlanDao.queryExpressDiscountPlanById
     * @Description:根据查询条件查询快递折扣方案(不分页)
     *
     * @param entity
     * @return
     *
     * @version:v1.0
     * @author:DP-FOSS-YANGKANG
     * @date:2015-1-12 下午4:48:32
     *
     * Modification History:
     * Date         Author      Version     Description
     * -----------------------------------------------------------------
     * 2015-1-12    DP-FOSS-YANGKANG      v1.0.0         create
     */
    List<ExpressDiscountEntity> queryExpressDiscountPlanByCondition(ExpressDiscountEntity entity);
    
	
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.api.server.dao.IExpressDiscountPlanDao.queryExpressDiscountPlanDetailList
	 * @Description:查询快递折扣方案明细信息(分页)
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
	List<ExpressDiscountDto> queryExpressDiscountPlanDetailList(ExpressDiscountDto entity,int start,int limit);
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.api.server.dao.IExpressDiscountPlanDao.queryExpressDiscountPlanDetailListCount
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
	 * @Function: com.deppon.foss.module.pickup.pricing.api.server.dao.IExpressDiscountPlanDao.insertDetailSelective
	 * @Description:新增快递折扣方案明细信息
	 *
	 * @param entity
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-10 上午10:51:46
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2015-1-10    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	int insertDiscountDetailSelective(ExpressDiscountDto detailEntity);
	
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.api.server.dao.IExpressDiscountPlanDao.updateDiscountDetailSelective
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
	int updateDiscountDetailSelective(ExpressDiscountDto detailEntity);
	
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.api.server.dao.IExpressDiscountPlanDao.deleteDiscountDetailById
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
	int deleteDiscountDetailByIds(List<String> discountDetailIds);
	
    /**
     * 
     *
     * @Function: com.deppon.foss.module.pickup.pricing.api.server.dao.IExpressDiscountPlanDao.queryExpressDiscountDetailByCondition
     * @Description:根据查询条件查询快递折扣方案明细信息（不分页）
     *
     * @param detailEntity
     * @return
     *
     * @version:v1.0
     * @author:DP-FOSS-YANGKANG
     * @date:2015-1-14 上午9:50:10
     *
     * Modification History:
     * Date         Author      Version     Description
     * -----------------------------------------------------------------
     * 2015-1-14    DP-FOSS-YANGKANG      v1.0.0         create
     */
    List<ExpressDiscountDto> queryExpressDiscountDetailByCondition(ExpressDiscountDto detailEntity);
    
    /**
     * 
     *
     * @Function: com.deppon.foss.module.pickup.pricing.api.server.dao.IExpressDiscountPlanDao.findExpressDiscountByPlanNames
     * @Description:根据多个方案名称查出折扣方案
     *
     * @param map
     * @return
     *
     * @version:v1.0
     * @author:王增明
     * @date:2015-07-27 上午14:36:00
     *
     * Modification History:
     * Date         Author      Version     Description
     * -----------------------------------------------------------------
     * 2015-07-27    王增明      v1.0.0         create
     */
	@SuppressWarnings("rawtypes")
	List<ExpressDiscountEntity> findExpressDiscountByPlanNames(Map map);
}
