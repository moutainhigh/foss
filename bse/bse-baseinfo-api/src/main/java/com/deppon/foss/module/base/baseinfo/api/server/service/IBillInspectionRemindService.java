package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.BillInspectionRemindEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;

public interface IBillInspectionRemindService {

	/**根据条件进行分页查询
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
	 * 新增记录
	 * @param entity
	 * @return
	 */
	public long addBillInspectionRemind(BillInspectionRemindEntity entity);
	/**
	 * 根据主键删除数据
	 * @param ids
	 * @return
	 */
	public long deleteBillInspectionRemind(String[] ids);
	/**
	 * 更新数据
	 * @param entity
	 * @return
	 */
	public long updateBillInspectionRemind(BillInspectionRemindEntity entity);
	
	
	/**
	 * 根据省份 城市区县 区域级别 
	 * @param entity
	 * @param regionCode
	 * @param regionLevCode
	 * @return
	 */
	public BillInspectionRemindEntity queryBillInspectionRemindByRegionCode(OrgAdministrativeInfoEntity entity,String regionCode,String regionLevCode);
	
}
