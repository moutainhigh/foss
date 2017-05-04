package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressPrintStarEntity;

/**
 * TODO(ExpressPrintStar的DAO接口)
 * @author 187862-dujunhui
 * @date 2014-5-21 上午10:08:34
 * @since
 * @version
 */
public interface IExpressPrintStarDao {
	
	 /**
     * 
     * <p>添加快递打印星标</p> 
     * @author 187862-dujunhui
     * @date 2014-5-21 上午10:09:22
     * @return
     * @see
     */
	ExpressPrintStarEntity addExpressPrintStar(ExpressPrintStarEntity entity);
    
    /**
     * 
     * <p>作废快递打印星标</p> 
     * @author 187862-dujunhui
     * @date 2014-5-21 上午10:10:31
     * @return
     * @see
     */
	ExpressPrintStarEntity deleteExpressPrintStar(ExpressPrintStarEntity entity);

    /**
     * 
     * <p>更新快递打印星标</p> 
     * @author 187862-dujunhui
     * @date 2014-5-21 上午10:11:23
     * @return
     * @see
     */
	ExpressPrintStarEntity updateExpressPrintStar(ExpressPrintStarEntity entity);
    
    /**
     * 
     * <p>根据虚拟代码查询快递打印星标详情</p> 
     * @author 187862-dujunhui
     * @date 2014-5-21 上午10:12:45
     * @return
     * @see
     */
	ExpressPrintStarEntity queryExpressPrintStarByVirtualCode(String virtualCode);

    /**
     * 
     * <p>按条件查询快递打印星标列表</p> 
     * @author 187862-dujunhui
     * @date 2014-5-21 上午10:14:11
     * @return
     * @see
     */
    List<ExpressPrintStarEntity> queryExpressPrintStarByCondition(ExpressPrintStarEntity entity, int start, int limit);
    
    /**
     * 
     * <p>按条件查询快递打印星标数量</p> 
     * @author 187862-dujunhui
     * @date 2014-5-21 上午10:15:55
     * @return
     * @see
     */
    long countExpressPrintStarByCondition(ExpressPrintStarEntity entity);

    /**
     * 
     * <p>查询某一外场下的所有快递打印星标</p> 
     * @author 187862-dujunhui
     * @date 2014-5-21 上午10:18:12
     * @return
     * @see
     */
    List<ExpressPrintStarEntity> queryExpressPrintStarListByOrganizationCode(String organizationCode, String goodsAreaType);
    
    /**
     * 
     * <p>批量作废快递打印星标</p> 
     * @author 187862-dujunhui
     * @date 2014-5-21 上午10:19:55
     * @return
     * @see
     */
    int deleteExpressPrintStars(List<String> virtualCodes, String modifyUser);
	
	/**
     * 根据外场编码查询快递打印星标货区
     * 
     * @author 187862-dujunhui
     * @Date  2014-5-21 上午10:23:22
     * @return
     * @see
     */
    List<ExpressPrintStarEntity> queryExpressPrintStarByTransCenterCode(String orgCode);

}
