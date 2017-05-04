package com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.InviteCommonExpressageEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.InviteFossVehicleCostEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleDrivingEntity;

public interface ICommonInviteFossVehicleCostDao {
	 /**
	  * 选择器查询
	  * @param entity
	  * @return
	  */
	  long queryCommonBusinessCount(InviteFossVehicleCostEntity entity);
	    
	  /**
	   * 选择器查询
	   * @param entity
	   * @param start
	   * @param limit
	   * @return
	   */
	   List<InviteFossVehicleCostEntity> queryCommonInviteFossVehicleCostByBusiness(InviteFossVehicleCostEntity entity,int start, int limit);
	   /**
		* 选择器查询
		* @param entity
		* @return
        */
	   long queryCommonRegionalCount(InviteFossVehicleCostEntity entity);
		    
	   /**
	    * 选择器查询
	    * @param entity
	    * @param start
	    * @param limit
	    * @return
	    */
	    List<InviteFossVehicleCostEntity> queryCommonInviteFossVehicleCostByRegional(InviteFossVehicleCostEntity entity,int start, int limit);
	    
	    /**
	     * 选择器查询
	     * @param entity
	     * @return
	     */
	   long queryCommonExpressageCount(InviteCommonExpressageEntity entity);
	    
	   /**
	    * 选择器查询
	    * @param entity
	    * @param start
	    * @param limit
	    * @return
	    */
	   List<InviteCommonExpressageEntity> queryCommonInviteFossVehicleCostByExpressage(InviteCommonExpressageEntity entity,int start, int limit);
}
