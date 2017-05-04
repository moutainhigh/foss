package com.deppon.foss.module.base.baseinfo.api.server.dao;

import com.deppon.foss.module.base.baseinfo.api.shared.dto.DeptTempArrearsWorkFlowDto;

/**
 * @author 078816-WangPeng
 * @date   2014-02-11
 * @description 工作流操作Dao
 *
 */
public interface IWorkFlowSearchDao {

	/**
	 * @author 078816-WangPeng
	 * @date   2014-02-11
	 * @description 根据条件查询工作流的内容
	 *
	 */
	public DeptTempArrearsWorkFlowDto queryWorkFlowInfos(DeptTempArrearsWorkFlowDto entity);
	
	/**
	 * @author 078816-WangPeng
	 * @date   2014-02-11
	 * @description 保存起草的工作流的内容
	 *
	 */
	public int saveWorkFlowDraftInfos(DeptTempArrearsWorkFlowDto entity);
	
	/**
	 * @author 078816-WangPeng
	 * @date   2014-02-11
	 * @description 保存审批结果
	 *
	 */
	public int saveWorkFlowExamineInfos(DeptTempArrearsWorkFlowDto entity);
	
	/**
	 * @author 078816-WangPeng
	 * @date   2014-02-11
	 * @description 查询从当天0点开始至今的工作流个数
	 *
	 */
	public String recordCurrentDayWorkNums();
}
