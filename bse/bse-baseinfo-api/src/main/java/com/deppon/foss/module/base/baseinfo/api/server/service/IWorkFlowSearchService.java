package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.bpms.module.shared.domain.ApprovalInfo;
import com.deppon.bpmsapi.module.client.api.IDpBpmsClientFacade;
import com.deppon.bpmsapi.module.client.domain.WorkItemInfo;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DeptTempArrearsWorkFlowDto;

/**
 * @author 078816-WangPeng
 * @date   2014-02-11
 * @description 工作流操作Service
 *
 */
public interface IWorkFlowSearchService extends IService {

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
	public int saveWorkFlowDraftInfos(DeptTempArrearsWorkFlowDto entity,List<String> idList);
	
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
	 * @description 查询工作流的操作记录
	 *
	 */
	public List<ApprovalInfo> queryWorkFlowTrackRecord(DeptTempArrearsWorkFlowDto entity);
	
	/**
	 * @author 078816-WangPeng
	 * @date   2014-02-12
	 * @description 获取审批中工作项id
	 *
	 */
	public WorkItemInfo getCurrentWorkItemInfo(long processInstId);
	
	/**
	 * @author 078816-WangPeng
	 * @date   2014-02-12
	 * @description 创建客户端实例
	 *
	 */
	public IDpBpmsClientFacade createDpBpmsClientFacadeImpl(String userCode,
			String userName);
	
}
