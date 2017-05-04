package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.FocusRecordManagementEntity;

public interface IFocusRecordManagementDao {
	/**
	 * <p>新增操作</p> 
	 * @author 232608
	 * @date 2015-6-25 下午2:20:30
	 * @return 
	 * @see
	 */
	void addFocusRecordManagement(FocusRecordManagementEntity entity);
	/**
	 * <p>分页查询</p> 
	 * @author 232608
	 * @date 2015-6-25 下午2:20:30
	 * @return 
	 * @see
	 */
	List<FocusRecordManagementEntity> queryFocusRecordManagementList(FocusRecordManagementEntity entity, int start, int limit);
	/**
	 * <p>根据code查询</p> 
	 * @author 232608
	 * @date 2015-6-25 下午2:20:30
	 * @return 
	 * @see
	 */
	List<FocusRecordManagementEntity> queryFocusRecordManagementByCode(String billingGroupCode);
	/**
	 * <p>分页查询count</p> 
	 * @author 232608
	 * @date 2015-6-25 下午2:20:30
	 * @return 
	 * @see
	 */
	long queryCount(FocusRecordManagementEntity entity);
	/**
	 * <p>提供给新增修改用 查看数据是否已经存在 如果存在不让再添加</p> 
	 * @author 232608
	 * @date 2015-6-25 下午2:20:30
	 * @return 
	 * @see
	 */
	List<FocusRecordManagementEntity> queryIfExist(FocusRecordManagementEntity entity);
	/**
	 * <p>删除 后台是将active状态置为N</p> 
	 * @author 232608
	 * @date 2015-6-25 下午2:20:30
	 * @return 
	 * @see
	 */
	long deleteFocusRecordManagement(List<String> ids);
	/**
	 * <p>查询所有集中开单组信息 提供给接送货</p> 
	 * @author 232608
	 * @date 2015-6-25 下午2:20:30
	 * @return 
	 * @see
	 */
	List<FocusRecordManagementEntity> queryAllBillingGroup();
	
}
