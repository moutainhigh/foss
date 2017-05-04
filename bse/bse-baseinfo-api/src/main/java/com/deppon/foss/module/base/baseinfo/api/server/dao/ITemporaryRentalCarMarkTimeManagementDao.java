package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TemporaryRentalCarMarkTimeManagementEntity;

/**
 * 临时租车标记时间管理DAO接口
 * @author 218392  张永雪
 * @date 创建时间：2014-12-17 下午5:20:49
 */
public interface ITemporaryRentalCarMarkTimeManagementDao {
	
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
