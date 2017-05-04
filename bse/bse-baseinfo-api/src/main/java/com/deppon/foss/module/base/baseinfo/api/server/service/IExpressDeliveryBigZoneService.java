package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliveryBigZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ExpressDeliveryZoneException;


public interface IExpressDeliveryBigZoneService {
	
	/**
	 * 新增快递收派大区
	 * @param entity
	 * @param addList
	 * @param deleteList
	 * @return
	 * @param @param entity
	 * @param @param addList
	 * @param @param deleteList
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午1:48:58
	 */
	int addExpressDeliveryBigZone(ExpressDeliveryBigZoneEntity entity, List<String> addList,
			List<String> deleteList);

	/**
	 * 根据code作废快递收派大区信息
	 * @param codeStr
	 * @param modifyUser
	 * @return
	 * @param @param codeStr
	 * @param @param modifyUser
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午1:36:19
	 */
	int deleteExpressDeliveryBigZoneByCode(String codeStr, String modifyUser);

	/**
	 * 修改快递收派大区信息
	 * @param entity
	 * @param addList
	 * @param deleteList
	 * @return
	 * @param @param entity
	 * @param @param addList
	 * @param @param deleteList
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午1:48:49
	 */
	int updateExpressDeliveryBigZone(ExpressDeliveryBigZoneEntity entity,
			List<String> addList, List<String> deleteList);

	/**
	 * 
	 * @param entity
	 * @param limit
	 * @param start
	 * @return
	 * @param @param entity
	 * @param @param limit
	 * @param @param start
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午1:48:38
	 */
	List<ExpressDeliveryBigZoneEntity> queryExpressDeliveryBigZones(ExpressDeliveryBigZoneEntity entity,
			int limit, int start);

	/**
	 * 根据传入对象查询符合条件所有快递收派大区信息 同事 模糊查询 按照 大区 code和name
	 * @param entity
	 * @return
	 * @param @param entity
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午1:48:27
	 */
	List<ExpressDeliveryBigZoneEntity> queryBigZonesByNameOrCode(ExpressDeliveryBigZoneEntity entity);

	/**
	 * 统计总记录数
	 * @param entity
	 * @return
	 * @param @param entity
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午1:48:15
	 */
	Long queryRecordCount(ExpressDeliveryBigZoneEntity entity);

	/**
	 * 根据区域编码查询快递收派大区详细信息
	 * @param regionCode
	 * @return
	 * @param @param regionCode
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午1:48:06
	 */
	ExpressDeliveryBigZoneEntity queryBigzoneByCode(String regionCode);

	/**
	 * 根据大区虚拟编码查询快递收派大区详细信息
	 * @param virtualCode
	 * @return
	 * @param @param virtualCode
	 * @param @return
	 * @author 130134
	 * @date 2014-4-16 下午1:47:55
	 */
	ExpressDeliveryBigZoneEntity queryBigzoneByVirtualCode(String virtualCode);

	/**
	 * 根据传入的管理部门编码生成大区编码
	 * @param orgCode
	 * @return
	 * @throws BusinessException
	 * @param @param orgCode
	 * @param @return
	 * @param @throws BusinessException
	 * @author 130134
	 * @date 2014-4-16 下午1:32:42
	 */
	String generateCode(String orgCode)throws BusinessException;
	
    /**
     * 根据用户输入的条件导出快递收派大区信息
     * @param entity
     * @return
     * @throws ExpressDeliveryZoneException
     * @param @param entity
     * @param @return
     * @param @throws ExpressDeliveryZoneException
     * @author 130134
     * @date 2014-4-16 下午1:47:35
     */
	public ExportResource exportBigZoneList(ExpressDeliveryBigZoneEntity entity)throws ExpressDeliveryZoneException;
	
}
