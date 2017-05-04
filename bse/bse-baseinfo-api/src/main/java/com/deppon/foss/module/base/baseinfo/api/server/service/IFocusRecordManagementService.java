package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FocusRecordManagementEntity;

public interface IFocusRecordManagementService extends IService{
	/**
	 * 新增
	 * @param entity
	 */
	void addFocusRecordManagement(FocusRecordManagementEntity entity);
	/**
	 * <p> 分页查询</p> 
	 * @author 232608
	 * @date 2015-6-25 下午2:20:30
	 * @return List<FocusRecordManagementEntity>
	 * @see
	 */
	List<FocusRecordManagementEntity> queryFocusRecordManagementList(FocusRecordManagementEntity entity,int start,int limit);
	/**
	 * <p>根据Code查询</p> 
	 * @author 232608
	 * @date 2015-6-25 下午2:20:30
	 * @return List<FocusRecordManagementEntity>
	 * @see
	 */
	List<FocusRecordManagementEntity> queryFocusRecordManagementByCode(String billingGroupCode);
	/**
	 * <p> 分页查询查询总数</p> 
	 * @author 232608
	 * @date 2015-6-25 下午2:20:30
	 * @return 
	 * @see
	 */
	long queryCount(FocusRecordManagementEntity entity);
	/**
	 * <p>根据id删除</p> 
	 * @author 232608
	 * @date 2015-6-25 下午2:20:30
	 * @return 
	 * @see
	 */
	long deleteFocusRecordManagement(String ids);
	/**
	 * <p>更新</p> 
	 * @author 232608
	 * @date 2015-6-25 下午2:20:30
	 * @return 
	 * @see
	 */
	void updateFocusRecordManagement(FocusRecordManagementEntity entity);
	/**
	 * <p>提供给接送货的接口 查询表中的所有数据</p> 
	 * @author 232608
	 * @date 2015-6-25 下午2:20:30
	 * @return 
	 * @see
	 */
	List<FocusRecordManagementEntity> queryAllBillingGroup();
}
