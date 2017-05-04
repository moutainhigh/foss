package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.BillInspectionRemindEntity;

public interface IBillInspectionRemindDao {

	/**根据条件分页查询
	 * 
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<BillInspectionRemindEntity> queryAll(BillInspectionRemindEntity entity,int start,int limit);
	/**
	 * 根据条件查询记录数
	 * @param entity
	 * @return
	 */
	public Long queryCount(BillInspectionRemindEntity entity);
	
	/**
	 * 新增数据
	 * @param entity
	 * @return
	 */
	public long addBillInspectionRemind(BillInspectionRemindEntity entity);
	/**
	 * 删除记录
	 * @param entity
	 * @return
	 */
	public long deleteBillInspectionRemind(BillInspectionRemindEntity entity);
	/**
	 * 更新记录
	 * @param entity
	 * @return
	 */
	public long updateBillInspectionRemind(BillInspectionRemindEntity entity);
	/**
	 * 更具ID查询记录
	 * @param id
	 * @return
	 */
	public BillInspectionRemindEntity queryBillInspectionRemindById(String id);
	
	/**
	 * 根据省份 城市区县 区域级别 
	 * @param entity
	 * @param regionCode
	 * @param regionLevCode
	 * @return
	 */
	public BillInspectionRemindEntity queryBillInspectionRemindByRegionCode(
			BillInspectionRemindEntity entity);	
}
