package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliverySmallZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ExpressDeliveryZoneException;

public interface IExpressDeliverySmallZoneService {
	
	/**
	 * 新增快递收派小区小区
	 * @param entity
	 * @return
	 * @throws ExpressDeliveryZoneException
	 * @param @param entity
	 * @param @return
	 * @param @throws ExpressDeliveryZoneException
	 * @author 130134
	 * @date 2014-4-16 下午2:52:31
	 */
	int addExpressDeliverySmallZone(ExpressDeliverySmallZoneEntity entity)throws ExpressDeliveryZoneException;

	/**
	 * 根据code作废快递收派小区小区信息
	 * @param codeStr
	 * @param modifyUser
	 * @return
	 * @param @param codeStr
	 * @param @param modifyUser
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午2:52:40
	 */
	int deleteExpressDeliverySmallZoneByCode(String codeStr, String modifyUser);
	/**
	 * 修改快递收派小区小区信息
	 * @param entity
	 * @return
	 * @param @param entity
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午2:52:49
	 */
	int updateExpressDeliverySmallZone(ExpressDeliverySmallZoneEntity entity);

	/**
	 * 根据小区虚拟编码修改小区的区域编码、所属大区
	 * @param entity
	 * @return
	 * @param @param entity
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午2:52:58
	 */
	int updateSmallZoneByVirtualCode(ExpressDeliverySmallZoneEntity entity);
	
	/**
	 * 根据所属大区编码修改小区编码、所属大区编码为空
	 * @param entity
	 * @return
	 * @param @param entity
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午2:53:04
	 */
	int updateSmallZoneByBigCode(ExpressDeliverySmallZoneEntity entity);

	/**
	 * 根据传入对象查询符合条件所有快递收派小区信息
	 * @param entity
	 * @param limit
	 * @param start
	 * @return
	 * @param @param entity
	 * @param @param limit
	 * @param @param start
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午2:53:11
	 */
	List<ExpressDeliverySmallZoneEntity> queryExpressDeliverySmallZones(
			ExpressDeliverySmallZoneEntity entity, int limit, int start);

	/**
	 * 根据条件查询快递收派小区信息
	 * @param entity
	 * @return
	 * @param @param entity
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午2:53:18
	 */
	List<ExpressDeliverySmallZoneEntity> querySmallZonesByNameOrCode(ExpressDeliverySmallZoneEntity entity);

	/**
	 * 统计总记录数
	 * @param entity
	 * @return
	 * @param @param entity
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午2:53:48
	 */
	Long queryRecordCount(ExpressDeliverySmallZoneEntity entity);

	/**
	 * 根据传入的管理部门Code、快递收派小区类型和大区虚拟编码
	 * @param deptCode
	 * @param type
	 * @param bigZoneVirtualCode
	 * @return
	 * @param @param deptCode
	 * @param @param type
	 * @param @param bigZoneVirtualCode
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午2:53:55
	 */
	List<ExpressDeliverySmallZoneEntity> querySmallZonesByDeptCode(String deptCode,
			String type, String bigZoneVirtualCode);

	/**
	 * 根据区域编码查询快递收派小区小区详细信息
	 * @param regionCode
	 * @return
	 * @param @param regionCode
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午2:54:25
	 */
	ExpressDeliverySmallZoneEntity querySmallZoneByCode(String regionCode);


	/**
	 * 验证小区名称是否唯一
	 * @param regionName
	 * @return
	 * @param @param regionName
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午2:54:34
	 */
	ExpressDeliverySmallZoneEntity querySmallZoneByName(String regionName);

	/**
	 * 根据传入条件 导出查询结果
	 * @param entity
	 * @return
	 * @param @param entity
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午2:54:40
	 */
	ExportResource exportSmallZoneList(ExpressDeliverySmallZoneEntity entity);
	
    /**
     * 根据传入对象查询符合条件所有快递收派小区小区信息 （供导出用）	
     * @param entity
     * @param limit
     * @param start
     * @return
     * @throws ExpressDeliveryZoneException
     * @param @param entity
     * @param @param limit
     * @param @param start
     * @param @return
     * @param @throws ExpressDeliveryZoneException
     * @author 130134
     * @date 2014-4-16 下午2:54:52
     */
	List<ExpressDeliverySmallZoneEntity> queryExpressDeliverySmallZonesExport(
			ExpressDeliverySmallZoneEntity entity, int limit, int start)
			throws ExpressDeliveryZoneException;
	/**
	 * 根据所属大区编码修改小区编码、所属大区编码为空
	 * @param virtualCode
	 * @return
	 * @param @param virtualCode
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午3:00:56
	 */
	ExpressDeliverySmallZoneEntity querySmallZoneByVirtualCode(String virtualCode);
	/**
	 * 根据管理编码查询小区编码
	 * @param entity
	 * @return
	 * @param @param entity
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午3:01:03
	 */
	String queryRegionCodeByManagement(ExpressDeliverySmallZoneEntity entity);
	
	
	ExpressDeliverySmallZoneEntity querySmallZoneByGisId(ExpressDeliverySmallZoneEntity entity);
	
	Long queryDataPermissions(String empCode,String deptCode);

	/**
	 * 
	 * <p>
	 * 同步到OMS
	 * </p>
	 * 
	 * @author foss-qiupeng
	 * @date 2016-03-22
	 * @param entitys
	 * @see
	 */
	void syncToOMSWebsite(List<ExpressDeliverySmallZoneEntity> entitys);

}
