package com.deppon.foss.module.pickup.sign.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;

/**
 * 
* @Description: 查询更改理赔状态
* @author 330547---徐杰
* @date 2016-5-25 下午2:26:29 
  @param 
  @return
 */
public interface IClaimStatusService extends IService{
	/**
	 * 
	* @Description: 更改理赔状态
	* @author 330547 
	* @date 2016-6-25
	  @param entity
	  @return
	 */
     int setStatus(WaybillSignResultEntity entity);
 	/**
 	 * 
 	* @Description: 查询理赔状态
 	* @author 330547 
 	* @date 2016-6-25
 	  @param entity
 	  @return
 	 */
     int cancelStatus(WaybillSignResultEntity entity);

  	/**
  	 * 
  	* @Description: 查询理赔状态
  	* @author 330547 
  	* @date 2016-6-25
  	  @param entity
  	  @return
  	 */
      String getStatus(WaybillSignResultEntity entity);	
}
	