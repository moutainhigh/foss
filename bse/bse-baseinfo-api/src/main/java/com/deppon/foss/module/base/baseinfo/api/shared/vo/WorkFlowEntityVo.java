package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.bpms.module.shared.domain.ApprovalInfo;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DeptTempArrearsWorkFlowDto;

/**
 * @author 078816-WangPeng
 * @date   2014-01-07
 * @description 工作流基类Vo
 *
 */
public class WorkFlowEntityVo implements Serializable {

	/**
	 * VersionUID
	 */
	private static final long serialVersionUID = -6581333142398006263L;
	
	//工作流基类对象
	private DeptTempArrearsWorkFlowDto workFlowEntity;
	
	//工作流基类对象列表
	private List<DeptTempArrearsWorkFlowDto> workFlowEntityList;
	
	/** The approval list. */
	private List<ApprovalInfo> approvalList;
	
	//存放附件铸件列表
	private List<String> attachIdList;
	
	// 返回前台的INT类型属性
	private int returnInt;

	public DeptTempArrearsWorkFlowDto getWorkFlowEntity() {
		return workFlowEntity;
	}

	public void setWorkFlowEntity(DeptTempArrearsWorkFlowDto workFlowEntity) {
		this.workFlowEntity = workFlowEntity;
	}

	public List<DeptTempArrearsWorkFlowDto> getWorkFlowEntityList() {
		return workFlowEntityList;
	}

	public void setWorkFlowEntityList(List<DeptTempArrearsWorkFlowDto> workFlowEntityList) {
		this.workFlowEntityList = workFlowEntityList;
	}

	public List<ApprovalInfo> getApprovalList() {
		return approvalList;
	}

	public void setApprovalList(List<ApprovalInfo> approvalList) {
		this.approvalList = approvalList;
	}

	public int getReturnInt() {
		return returnInt;
	}

	public void setReturnInt(int returnInt) {
		this.returnInt = returnInt;
	}

	public List<String> getAttachIdList() {
		return attachIdList;
	}

	public void setAttachIdList(List<String> attachIdList) {
		this.attachIdList = attachIdList;
	}


}
