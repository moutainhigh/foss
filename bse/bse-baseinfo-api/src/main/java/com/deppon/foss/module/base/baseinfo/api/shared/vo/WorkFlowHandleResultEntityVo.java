package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.WorkFlowHandleResultEntity;

/**
 * @author 078816-WangPeng
 * @date   2014-01-13
 * @description 工作流操作记录Vo
 *
 */
public class WorkFlowHandleResultEntityVo implements Serializable {

	/**
	 * VersionUID
	 */
	private static final long serialVersionUID = -4677044786657292151L;
	
	//工作流操作记录对象
	private WorkFlowHandleResultEntity workFlowHandleResultEntity;
	
	//工作流操作对象列表
	private List<WorkFlowHandleResultEntity> workFlowHandleResultEntityList;

	public List<WorkFlowHandleResultEntity> getWorkFlowHandleResultEntityList() {
		return workFlowHandleResultEntityList;
	}

	public void setWorkFlowHandleResultEntityList(
			List<WorkFlowHandleResultEntity> workFlowHandleResultEntityList) {
		this.workFlowHandleResultEntityList = workFlowHandleResultEntityList;
	}

	public WorkFlowHandleResultEntity getWorkFlowHandleResultEntity() {
		return workFlowHandleResultEntity;
	}

	public void setWorkFlowHandleResultEntity(WorkFlowHandleResultEntity workFlowHandleResultEntity) {
		this.workFlowHandleResultEntity = workFlowHandleResultEntity;
	}

}
