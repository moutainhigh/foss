package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TemporaryRentalCarMarkTimeManagementEntity;

/**
 * Service 接口
 * @author 218392  张永雪
 * @date 创建时间：2014-12-18 上午10:31:48
 */
public interface ITemporaryRentalCarMarkTimeManagementService extends IService{
	
	/**
	 *  <p> 新增临时租车标记时间管理信息 </p>
	 */
	int addTemporaryRentalCarMarkTimeManagement(TemporaryRentalCarMarkTimeManagementEntity entity); 
	
	/**
	 * <p> 修改临时租车标记时间管理信息 </p>
	 */
	int updateTemporaryRentalCarMarkTimeManagement(TemporaryRentalCarMarkTimeManagementEntity entity);
	
	/**
	 * <p> 作废临时租车标记时间管理信息 </p>
	 */
	int deleteByIdTemporaryRentalCarMarkTimeManagement(List<String> idList);
	
	/**
	 * <p>根据传入的对象查询所有临时租车标记时间管理信息</p>
	 */
	List<TemporaryRentalCarMarkTimeManagementEntity> queryAllTemporaryRentalCarMarkTimeManagement(
			TemporaryRentalCarMarkTimeManagementEntity entity,int limit, int start);
	
	/**
	 * <p>统计总记录数</p>
	 */
	Long queryCount(TemporaryRentalCarMarkTimeManagementEntity entity);
	
	/**
	 * <p>根据部门属性code查询设置时长</p>
	 */
	String querySetTimeByDeptAttributes(String deptAttributes);
	
}
