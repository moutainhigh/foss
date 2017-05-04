package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressVehiclesDetailEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressVehiclesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ExpressVehiclesException;

public interface IExpressVehiclesService extends IService {
    /**
     * <p>新增一个“快递车辆”实体入库</p> 
     * @author xmm
     * @date 2013-08-13 上午16:58:55
     * @param expressVehiclesEntity “车辆类型（厢式车、挂车、拖头）”的值代码
     * @param createUser 创建人
     * @param ignoreNull true：忽略空值，false：包含空值
     * @return 1：成功；-1：失败
     * @throws ExpressVehiclesException
     * @see
     */
     int addExpressVehicles(ExpressVehiclesEntity expressVehiclesEntity, String createUser, boolean ignoreNull) throws ExpressVehiclesException;
     /**
     * <p>根据“快递车辆”记录ID集合作废（逻辑删除）多条“快递车辆”记录</p> 
     * @author xmm
     * @date 2013-08-13 上午10:18:54
     * @param modifyUser 修改人
     * @param String 记录唯一标识集合
     * @return 1：成功；-1：失败
     * @throws ExpressVehiclesException
     * @see
     */
     int deleteExpressVehicles(List<String> ids, String modifyUser) throws ExpressVehiclesException;
     
     /**
      * <p>修改一个“快递车辆”实体入库</p> 
      * @author xmm
      * @date 2013-08-13 上午10:19:00
      * @param expressVehiclesEntity “快递车辆”实体
      * @param modifyUser 修改人
      * @param ignoreNull true：忽略空值，false：包含空值
      * @return 1：成功；-1：失败
      * @throws ExpressVehiclesException
      * @see
      */
      int updateExpressVehicles(ExpressVehiclesEntity expressVehiclesEntity, String modifyUser,  boolean ignoreNull) throws ExpressVehiclesException;
       /**
        * 
        * <p>queryExpressVehiclesList</p> 
        * @author 189284 
        * @date 2015-6-26 上午8:16:42
        * @param expressVehiclesEntity
        * @param start
        * @param limit
        * @return
        * @see
        */
      List<ExpressVehiclesEntity> queryExpressVehiclesList(
			ExpressVehiclesEntity expressVehiclesEntity, int start, int limit);
      /**
       * 
       * <p>快递车辆查询界面用</p> 
       * @author 189284 
       * @date 2015-6-26 上午8:17:04
       * @param expressVehiclesEntity
       * @param start
       * @param limit
       * @return
       * @see
       */
      List<ExpressVehiclesEntity> queryExpressVehiclesListToView(
  			ExpressVehiclesEntity expressVehiclesEntity, int start, int limit);
			
	  Long queryRecordCount(ExpressVehiclesEntity expressVehiclesEntity);
	  
	  ExpressVehiclesEntity queryExpressVehiclesById(String id);
      
	  /**
	   * 统计按条件查询返回的记录数
	   * 
	   * @author  WangPeng
	   * @Date    2013-8-23 下午1:31:17
	   * @param   enity
	   * @return  Long
	   * 
	   *
	   */
	  Long recordCountByQueryParams(ExpressVehiclesEntity enity);
	  
	  /**
		 * 
		 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
		 * @author 189284 
		 * @date 2015-6-26 上午8:21:24
		 * @param expressVehiclesEntity
		 * @return
		 * @see
		 */
	 Long recordCountByQueryParamsToView(ExpressVehiclesEntity expressVehiclesEntity);
	  /**
	   * 根据条件查询快递车辆信息
	   * 
	   * @author  FOSS-ShenWeiHua-132599
	   * @Date    2014-9-24 下午1:31:17
	   * @param   enity
	   * @return  entity
	   *
	   */
	  List<ExpressVehiclesEntity> queryExpressVehiclesByEntity(ExpressVehiclesEntity entity);
	  
	  /**
	   * 
	   *<p>Title: queryOrgCodeByEmpCode</p>
	   *<p>根据车牌号查询所属开单营业部</p>
	   *@author 130566-ZengJunfan
	   *@date 2014-8-11下午4:41:22
	   * @param empCode
	   * @return
	   */
	  String queryOrgCodeByVehicleNo(String vehicleNo);
	  /**
		 * 根据快递员code查询出所服务的开单组
		 * 232608
		 */
	  String queryExpressVehiclesByCode(String code);
	  
	/**
	 * 
	 * <p>
	 * 同步到OMS
	 * </p>
	 * 
	 * @author foss-qiupeng
	 * @date 2016-03-21
	 * @param entitys
	 * @see
	 */
	void syncToOMSWebsite(List<ExpressVehiclesEntity> entitysList, List<ExpressVehiclesDetailEntity> detailEntitys);
}
