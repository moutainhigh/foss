package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressPrintStarEntity;

/**
 * TODO(ExpressPrintStar的Service接口)
 * @author 187862-dujunhui
 * @date 2014-5-21 上午10:35:14
 * @since
 * @version
 */
public interface IExpressPrintStarService extends IService {
	
	 /**
     * 
     * <p>添加快递打印星标</p> 
     * @author 187862-dujunhui
     * @date 2014-5-21 上午10:38:44
     * @return
     * @see
     */
	ExpressPrintStarEntity addExpressPrintStar(ExpressPrintStarEntity entity);
    
    /**
     * 
     * <p>作废快递打印星标</p> 
     * @author 187862-dujunhui
     * @date 2014-5-21 上午10:39:54
     * @return
     * @see
     */
	ExpressPrintStarEntity deleteExpressPrintStar(ExpressPrintStarEntity entity);

    /**
     * 
     * <p>更新快递打印星标</p> 
     * @author 187862-dujunhui
     * @date 2014-5-21 上午10:41:22
     * @return
     * @see
     */
	ExpressPrintStarEntity updateExpressPrintStar(ExpressPrintStarEntity entity);
    
    /**
     * 
     * <p>根据虚拟代码查询快递打印星标详情</p> 
     * @author 187862-dujunhui
     * @date 2014-5-21 上午10:42:34
     * @return
     * @see
     */
	ExpressPrintStarEntity queryExpressPrintStarByVirtualCode(String virtualCode);

  /**
     * 
     * <p>按条件查询快递打印星标列表</p> 
     * @author 187862-dujunhui
     * @date 2014-5-21 上午10:42:34
     * @return
     * @see
     */
    List<ExpressPrintStarEntity> queryExpressPrintStarByCondition(ExpressPrintStarEntity entity, int start, int limit);
    
    /**
     * 
     * <p>按条件查询快递打印星标列表</p> 
     * @author 187862-dujunhui
     * @date 2014-5-21 下午1:40:22
     * @return
     * @see
     */
    List<ExpressPrintStarEntity> queryExpressPrintStarByCondition(ExpressPrintStarEntity entity, int start, int limit, String userCode, String deptCode);

    /**
     * 
     * <p>按条件查询快递打印星标数量</p> 
     * @author 187862-dujunhui
     * @date 2014-5-21 下午1:42:15
     * @return
     * @see
     */
    long countExpressPrintStarByCondition(ExpressPrintStarEntity entity);

    /**
     * 
     * <p>按条件查询快递打印星标数量</p> 
     * @author 187862-dujunhui
     * @date 2014-5-21 下午1:43:52
     * @return
     * @see
     */
    long countExpressPrintStarByCondition(ExpressPrintStarEntity entity, String userCode, String deptCode);
    
    /**
     * 
     * <p>查询某一外场下的所有快递打印星标</p> 
     * @author 187862-dujunhui
     * @date 2014-5-21 下午1:45:13
     * @return
     * @see
     */
    List<ExpressPrintStarEntity> queryExpressPrintStarListByOrganizationCode(String organizationCode);

    /**
     * 
     * <p>查询某一外场下的所有库区</p> 
     * @author 187862-dujunhui
     * @date 2014-5-21 下午1:47:23
     * @return
     * @see
     */
    List<ExpressPrintStarEntity> querySimpleExpressPrintStarListByOrganizationCode(String organizationCode);
    
    /**
     * 
     * <p>根据外场编码和库区编码取库区名称</p> 
     * @author 187862-dujunhui
     * @date 2014-5-21 下午1:48:22
     * @return
     * @see
     */
    String queryNameByCode(String organizationCode, String code);
    
    /**
     * 
     * <p>根据外场编码和库区编码取库区实体</p> 
     * @author 187862-dujunhui
     * @date 2014-5-21 下午1:49:18
     * @return
     * @see
     */
    ExpressPrintStarEntity queryExpressPrintStarByCode(String organizationCode, String code);
    
    
    /**
     * 
     * <p>根据外场部门编码和目的站部门编码取货区编码</p> 
     * @author 187862-dujunhui
     * @date 2014-5-21 下午1:51:24
     * @param organizationCode 所在外场部门编码
     * @param arriveRegionCode 目的站部门编码
     * @return
     * @see
     * @deprecated 使用 String queryCodeByArriveRegionCode(String organizationCode, String arriveRegionCode, String productCode) 取代,该方法未区分卡，普和混装库区
     */
    String queryCodeByArriveRegionCode(String organizationCode, String arriveRegionCode);
    
    /**
     * 
     * <p>根据库区类型（卡货库区，普货库区，贵重物品库区等）</p> 
     * @author 187862-dujunhui
     * @date 2014-5-21 下午1:52:45
     * @param goodsAreaType 库区类型，数据字典中取值
     * DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXCEPTION 异常货区
     * DictionaryValueConstants.BSE_GOODSAREA_TYPE_VALUABLE  贵重物品货区
     * DictionaryValueConstants.BSE_GOODSAREA_TYPE_PACKING   包装货区
     * DictionaryValueConstants.BSE_GOODSAREA_TYPE_OTHER     偏线货区
     * DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION   驻地派送货区
     * DictionaryValueConstants.BSE_GOODSAREA_TYPE_COMMON    混装货区
     * @return 该类型的库区列表
     * @see
     */
    List<ExpressPrintStarEntity> queryExpressPrintStarListByType(String organizationCode, String goodsAreaType);
 
    /**
     * 
     * <p>根据库区类型（卡货库区，普货库区，贵重物品库区等）</p> 
     * @author 187862-dujunhui
     * @date 2014-5-21 下午1:56:23
     * @param goodsAreaType 库区类型，数据字典中取值
     * DictionaryValueConstants.BSE_GOODSAREA_TYPE_EXCEPTION 异常货区
     * DictionaryValueConstants.BSE_GOODSAREA_TYPE_VALUABLE  贵重物品货区
     * DictionaryValueConstants.BSE_GOODSAREA_TYPE_PACKING   包装货区
     * DictionaryValueConstants.BSE_GOODSAREA_TYPE_OTHER     偏线货区
     * DictionaryValueConstants.BSE_GOODSAREA_TYPE_STATION   驻地派送货区
     * DictionaryValueConstants.BSE_GOODSAREA_TYPE_COMMON    混装货区
     * @return 该类型的库区列表
     * @see
     */
    List<ExpressPrintStarEntity> querySimpleExpressPrintStarListByType(String organizationCode, String goodsAreaType);    
    
    /**
     * 
     * <p>批量作废快递打印星标</p> 
     * @author 187862-dujunhui
     * @date 2014-5-21 下午1:58:37
     * @param virtualCodes
     * @param modifyUser
     * @return
     * @see
     */
    int deleteExpressPrintStars(List<String> virtualCodes, String modifyUser);

    /**
     * 
     * <p>根据外场部门编码和目的站部门编码取货区编码</p> 
     * @author 187862-dujunhui
     * @date 2014-5-21 下午1:59:44
     * @param organizationCode
     * @param arriveRegionCode
     * @param productCode
     * @return
     * @see
     */
    String queryCodeByArriveRegionCode(String organizationCode, String arriveRegionCode, String productCode);


    /**
     * 
     * <p>根据外场部门编码和目的站部门编码取货区实体</p> 
     * @author 187862-dujunhui
     * @date 2014-5-21 下午2:01:23
     * @param organizationCode
     * @param arriveRegionCode
     * @return
     * @see
     */
    ExpressPrintStarEntity queryExpressPrintStarByArriveRegionCode(String organizationCode, String arriveRegionCode);
   /**
    * 
    *<p>根据虚拟编码查询库区实体（从数据库中查询实体，不做填充操作）</p>
    *@author 187862-dujunhui
    *@date   2014-5-21 下午2:02:33
    * @param virtualCode
    * @return
    */
    ExpressPrintStarEntity searchExpressPrintStarEntityByVirtualCode(String virtualCode);
    
    
    /**
     * <p>根据外场编码查询快递货区</p>
     * @author  187862-dujunhui
     * @Date    2014-5-21 下午2:04:12
     * @param   orgCode
     * @return  ExpressPrintStarEntity
     *  
     */
    ExpressPrintStarEntity queryExpressPrintStarByTransCenterCode(String orgCode, String productCode);

}
