package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliverySmallZoneEntity;

public interface IExpressDeliverySmallZoneDao {

	
	/**
	 * 新增快递收派货小区
	 * @param entity
	 * @return
	 * @param @param entity
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午2:41:17
	 */
    int addExpressDeliverySmallZone(ExpressDeliverySmallZoneEntity entity);

    /**
     * 根据code作废快递收派货小区信息
     * @param codes
     * @param modifyUser
     * @return
     * @param @param codes
     * @param @param modifyUser
     * @param @return
     * @author 130134
     * @date 2014-4-16 下午2:41:34
     */
    int deleteExpressDeliverySmallZoneByCode(String[] codes, String modifyUser);

   /**
    *  修改快递收派货小区信息
    * @param entity
    * @return
    * @param @param entity
    * @param @return
    * @author 130134
    * @date 2014-4-16 下午2:41:44
    */
    int updateExpressDeliverySmallZone(ExpressDeliverySmallZoneEntity entity);

   /**
    * 根据小区虚拟编码修改小区的区域编码、所属大区
    * @param entity
    * @return
    * @param @param entity
    * @param @return
    * @author 130134
    * @date 2014-4-16 下午2:41:55
    */
    int updateSmallZoneByVirtualCode(ExpressDeliverySmallZoneEntity entity);

   /**
    * 根据所属大区编码修改小区编码、所属大区编码为空
    * @param entity
    * @return
    * @param @param entity
    * @param @return
    * @author 130134
    * @date 2014-4-16 下午2:42:04
    */
    int updateSmallZoneByBigCode(ExpressDeliverySmallZoneEntity entity);

    /**
     * 根据传入对象查询符合条件所有快递收派货小区信息
     * @param entity
     * @param limit
     * @param start
     * @return
     * @param @param entity
     * @param @param limit
     * @param @param start
     * @param @return
     * @author 130134
     * @date 2014-4-16 下午2:42:10
     */
    List<ExpressDeliverySmallZoneEntity> queryExpressDeliverySmallZones(
	    ExpressDeliverySmallZoneEntity entity,int limit, int start);

   /**
    * 统计总记录数
    * @param entity
    * @return
    * @param @param entity
    * @param @return
    * @author 130134
    * @date 2014-4-16 下午2:42:17
    */
    Long queryRecordCount(ExpressDeliverySmallZoneEntity entity);
	/**
	 * 根据传入的管理部门Code、快递收派货大区的区域类型查询符合条件 的快递收派货小区
	 * @param deptCode
	 * @param type
	 * @param bigZoneVirtualCode
	 * @return
	 * @param @param deptCode
	 * @param @param type
	 * @param @param bigZoneVirtualCode
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午2:42:30
	 */
    List<ExpressDeliverySmallZoneEntity> querySmallZonesByDeptCode(String deptCode,
	    String type, String bigZoneVirtualCode);

   /**
    * 根据区域编码查询快递收派货小区详细信息
    * @param regionCode
    * @return
    * @param @param regionCode
    * @param @return
    * @author 130134
    * @date 2014-4-16 下午2:42:56
    */
    ExpressDeliverySmallZoneEntity querySmallZoneByCode(String regionCode);

   /**
    * 验证小区名称是否唯一
    * @param regionName
    * @return
    * @param @param regionName
    * @param @return
    * @author 130134
    * @date 2014-4-16 下午2:43:03
    */
    ExpressDeliverySmallZoneEntity querySmallZoneByName(String regionName);

   /**
    * 根据虚拟编码查询快递收派货小区详细信息
    * @param virtualCode
    * @return
    * @param @param virtualCode
    * @param @return
    * @author 130134
    * @date 2014-4-16 下午2:43:10
    */
    ExpressDeliverySmallZoneEntity querySmallZoneByVirtualCode(String virtualCode);

	  /**
	   *  根据大区的区域编码模糊查询 货小区
	   * @param bigZoneRegionCode
	   * @return
	   * @param @param bigZoneRegionCode
	   * @param @return
	   * @author 130134
	   * @date 2014-4-16 下午2:43:16
	   */
    List<ExpressDeliverySmallZoneEntity> querySmallZonesByBigZoneRegionCode(
	    String bigZoneRegionCode);
	/**
	 * 根据条件查询接送货小区信息
	 * @param entity
	 * @return
	 * @param @param entity
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午2:43:30
	 */
    List<ExpressDeliverySmallZoneEntity> querySmallZonesByNameOrCode(ExpressDeliverySmallZoneEntity entity);
    /**
     * 通过管理部门编码查询小区编码
     * @param entity
     * @return
     * @param @param entity
     * @param @return
     * @author 130134
     * @date 2014-4-16 下午2:43:40
     */
    String queryRegionCodeByManagement(ExpressDeliverySmallZoneEntity entity);

    /**
     * 根据虚拟编码查询小区信息
     * @param virtualCode
     * @return
     * @param @param virtualCode
     * @param @return
     * @author 130134
     * @date 2014-4-16 下午2:44:04
     */
    ExpressDeliverySmallZoneEntity queryExpressDeliverySmallZoneByVirtualCode(String virtualCode);
    /**
     * 根据多个小区编码查询小区信息
     * @param codes
     * @return
     * @param @param codes
     * @param @return
     * @author 130134
     * @date 2014-4-16 下午2:44:26
     */
    List<ExpressDeliverySmallZoneEntity> queryExpressDeliverySmallZoneByCode(String[] codes);
    
    
    ExpressDeliverySmallZoneEntity querySmallZoneByGisId(ExpressDeliverySmallZoneEntity entity);
    
    
    Long queryDataPermissions(String empCode, String deptCode);
    
}
