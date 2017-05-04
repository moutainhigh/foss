package com.deppon.foss.module.transfer.stock.api.shared.vo;

import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.FindGoodsAdminDetailEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.FindGoodsAdminEntity;

public class FindGoodsAdminVo extends BaseEntity {

	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/ 
	private static final long serialVersionUID = 1L;
	/**
	 * 找货管理
	 */
	private FindGoodsAdminEntity findGoodsAdminEntity;
	/**
	 * 找货管理list
	 */
	private List<FindGoodsAdminEntity> findGoodsAdminEntitys;
	/**
	 * 找货管理详细
	 */
	private FindGoodsAdminDetailEntity findGoodsAdminDetailEntity;
	/**
	 * 找货管理详细List
	 */
	private List<FindGoodsAdminDetailEntity> findGoodsAdminDetailEntitys;
	/**
	 * 当前部门顶级组织
	 */
	private OrgAdministrativeInfoEntity administrativeInfo;
	/**
	 * 任务编码
	 */
	private String taskNo;
	/**
	 * 获取 
	 * @return findGoodsAdminEntity
	 */
	public FindGoodsAdminEntity getFindGoodsAdminEntity() {
		return findGoodsAdminEntity;
	}
	/**
	 * 设置 
	 * @param findGoodsAdminEntity the findGoodsAdminEntity to set
	 */
	public void setFindGoodsAdminEntity(FindGoodsAdminEntity findGoodsAdminEntity) {
		this.findGoodsAdminEntity = findGoodsAdminEntity;
	}
	/**
	 * 获取 
	 * @return findGoodsAdminEntitys
	 */
	public List<FindGoodsAdminEntity> getFindGoodsAdminEntitys() {
		return findGoodsAdminEntitys;
	}
	/**
	 * 设置 
	 * @param findGoodsAdminEntitys the findGoodsAdminEntitys to set
	 */
	public void setFindGoodsAdminEntitys(
			List<FindGoodsAdminEntity> findGoodsAdminEntitys) {
		this.findGoodsAdminEntitys = findGoodsAdminEntitys;
	}
	/**
	 * 获取 
	 * @return findGoodsAdminDetailEntity
	 */
	public FindGoodsAdminDetailEntity getFindGoodsAdminDetailEntity() {
		return findGoodsAdminDetailEntity;
	}
	/**
	 * 设置 
	 * @param findGoodsAdminDetailEntity the findGoodsAdminDetailEntity to set
	 */
	public void setFindGoodsAdminDetailEntity(
			FindGoodsAdminDetailEntity findGoodsAdminDetailEntity) {
		this.findGoodsAdminDetailEntity = findGoodsAdminDetailEntity;
	}
	/**
	 * 获取 
	 * @return findGoodsAdminDetailEntitys
	 */
	public List<FindGoodsAdminDetailEntity> getFindGoodsAdminDetailEntitys() {
		return findGoodsAdminDetailEntitys;
	}
	/**
	 * 设置 
	 * @param findGoodsAdminDetailEntitys the findGoodsAdminDetailEntitys to set
	 */
	public void setFindGoodsAdminDetailEntitys(
			List<FindGoodsAdminDetailEntity> findGoodsAdminDetailEntitys) {
		this.findGoodsAdminDetailEntitys = findGoodsAdminDetailEntitys;
	}
	/**
	 * 获取 当前部门顶级组织
	 * @return administrativeInfo
	 */
	public OrgAdministrativeInfoEntity getAdministrativeInfo() {
		return administrativeInfo;
	}
	/**
	 * 设置 当前部门顶级组织
	 * @param administrativeInfo the administrativeInfo to set
	 */
	public void setAdministrativeInfo(OrgAdministrativeInfoEntity administrativeInfo) {
		this.administrativeInfo = administrativeInfo;
	}
	/**
	 * 获取  任务编号
	 * @return taskNo
	 */
	public String getTaskNo() {
		return taskNo;
	}
	/**
	 * 设置  任务编号
	 * @param taskNo the taskNo to set
	 */
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
	
	
	

}
