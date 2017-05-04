package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliveryBigZoneEntity;


/**
 * 
 * @author 130134
 *
 */
public interface IExpressDeliveryBigZoneDao {

	/**
	 * 新增快递收派大区
	 * @param entity
	 * @return
	 * @param @param entity
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午2:48:44
	 */
    int addExpressDeliveryBigZone(ExpressDeliveryBigZoneEntity entity);

    /**
     * 根据code作废快递收派大区信息
     * @param codes
     * @param modifyUser
     * @return
     * @param @param codes
     * @param @param modifyUser
     * @param @return
     * @author 130134
     * @date 2014-4-16 下午2:48:50
     */
    int deleteExpressDeliveryBigZoneByCode(String[] codes, String modifyUser);

    /**
     * 修改快递收派大区信息
     * @param entity
     * @return
     * @param @param entity
     * @param @return
     * @author 130134
     * @date 2014-4-16 下午2:48:56
     */
    int updateExpressDeliveryBigZone(ExpressDeliveryBigZoneEntity entity);
	/**
	 * 根据传入对象查询符合条件所有快递收派大区信息
	 * @param entity
	 * @param limit
	 * @param start
	 * @return
	 * @param @param entity
	 * @param @param limit
	 * @param @param start
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午2:49:03
	 */
    List<ExpressDeliveryBigZoneEntity> queryExpressDeliveryBigZones(ExpressDeliveryBigZoneEntity entity,
	    int limit, int start);

    /**
     * 统计总记录数
     * @param entity
     * @return
     * @param @param entity
     * @param @return
     * @author 130134
     * @date 2014-4-16 下午2:49:14
     */
    Long queryRecordCount(ExpressDeliveryBigZoneEntity entity);

    /**
     * 根据区域编码查询快递收派大区详细信息
     * @param regionCode
     * @return
     * @param @param regionCode
     * @param @return
     * @author 130134
     * @date 2014-4-16 下午2:49:21
     */
    ExpressDeliveryBigZoneEntity queryBigzoneByCode(String regionCode);

   /**
    * 根据大区虚拟编码查询快递收派大区详细信息
    * @param virtualCode
    * @return
    * @param @param virtualCode
    * @param @return
    * @author 130134
    * @date 2014-4-16 下午2:49:27
    */
    ExpressDeliveryBigZoneEntity queryBigzoneByVirtualCode(String virtualCode);

     
 
   /**
    * 根据条件查询接送货大区信息
    * @param entity
    * @return
    * @param @param entity
    * @param @return
    * @author 130134
    * @date 2014-4-16 下午2:49:47
    */
    List<ExpressDeliveryBigZoneEntity> queryBigZonesByNameOrCode(ExpressDeliveryBigZoneEntity entity);
    
    /**
     * 根据大区的管理部门编码查询快递收派大区信息
     * @param manageMent
     * @return
     * @param @param manageMent
     * @param @return
     * @author 130134
     * @date 2014-4-16 下午2:50:00
     */
    ExpressDeliveryBigZoneEntity queryBigzoneByManageMent(String manageMent);
    /**
     * 根据快递收派大区的名称查新大区信息
     * @param bigName
     * @return
     * @param @param bigName
     * @param @return
     * @author 130134
     * @date 2014-4-16 下午2:50:29
     */
    public List<ExpressDeliveryBigZoneEntity> queryBigzoneByName(String bigName); 

}
