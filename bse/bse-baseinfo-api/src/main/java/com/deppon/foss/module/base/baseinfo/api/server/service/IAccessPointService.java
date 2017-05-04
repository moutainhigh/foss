package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AcceptPointSalesDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AccessPointEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.AccessPointVo;

/**
 * 接驳点Service
 * @author 198771
 *
 */
public interface IAccessPointService {
	/**
	 * 添加接驳点
	 * @param entity
	 */
	void addAccessPoint(AccessPointEntity entity);
	
	/**
	 * 根据条件查询
	 * @param entity
	 * @return
	 */
	List<AccessPointEntity> queryAccessPoints(AccessPointEntity entity,int start,int limit);

	/**
	 * 根据条件查询总条数
	 * @param entity
	 * @return
	 */
	long getCount(AccessPointEntity entity);
	
	/**
	 * 根据接驳点名称查找
	 */
	List<AccessPointEntity> queryAccessPointsByName(AccessPointEntity entity);
	/**
	 * 根据接驳点编码查找
	 */
	AccessPointEntity queryAccessPointsByCode(AccessPointEntity entity);
	
	/**
	 * 禁用/启用
	 */
	void updateAccessPointStatu(List<String> accessPointCodes);
	/**
	 * 查询省市区信息
	 */
	List<AdministrativeRegionsEntity> queryRegions(AdministrativeRegionsEntity entity);
	/**
	 * 作废
	 */
	void deleteAccessPoint(AccessPointEntity entity);
	/**
	 * 根据省市区编码
	 */
	List<AdministrativeRegionsEntity> queryRegionsByCodes(List<String> codes);
	
	/**
	 * 根据中转场编码
	 * @param entity
	 * @return
	 */
	List<AccessPointEntity> queryAccessPointsByTransferCode(
			AccessPointEntity entity);
	/**
	 * 修改前查询省市区
	 * @return
	 */
	AccessPointVo queryDegreeRegionsByCodes(AccessPointVo vo);
	/**
	 * 修改
	 * @param entity
	 */
	void updateAccessPoint(AccessPointEntity entity);
	
	/**
	 * 根据参数动态查询
	 * @param entity
	 */
	List<AccessPointEntity> queryAccessPointByCommon(AccessPointEntity entity);
	/**
	 * 根据接驳点编码查询接驳点与营业部关系
	 * @param acceptPointCodes
	 * @return
	 */
	List<AcceptPointSalesDeptEntity> queryAcceptPointSalesByAcceptPointCode(
			List<String> acceptPointCodes);
	/**
	 *根据司机工号查询所属部门 对应的外场
	 */
	String queryTransCenterByEmpCode(String empCode);
}
