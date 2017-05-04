
package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.web.upload.AttachementEntity;

/**
 * 工作流审批DTO TODO(描述类的职责)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2013-7-24 下午12:41:24,content:TODO
 * </p>
 * 
 * @author HuansChr
 * @date 2013-7-24 下午12:41:24
 * @since
 * @version
 */
public class WorkFlowApproveDto implements Serializable {

	/** 描述. */
	private static final long serialVersionUID = 5283334301954836965L;

	// 工作流ID
	/** The id. */
	private String id;
	// 工作流CODE
	/** The flow code. */
	private Long flowCode;
	// 工作流类型
	/** The flow type. */
	private String flowType;
	// 您的决策
	/** The approve type. */
	private String approveType;
	// 审批意见
	/** The opinion. */
	private String opinion;
	// 工作项id
	/** The work item id. */
	private Long workItemId;
	
	//上传的附件
	private List<AttachementEntity> attachementFiles = new ArrayList<AttachementEntity>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getFlowCode() {
		return flowCode;
	}

	public void setFlowCode(Long flowCode) {
		this.flowCode = flowCode;
	}

	public String getFlowType() {
		return flowType;
	}

	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}

	public String getApproveType() {
		return approveType;
	}

	public void setApproveType(String approveType) {
		this.approveType = approveType;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public Long getWorkItemId() {
		return workItemId;
	}

	public void setWorkItemId(Long workItemId) {
		this.workItemId = workItemId;
	}

	public List<AttachementEntity> getAttachementFiles() {
		return attachementFiles;
	}

	public void setAttachementFiles(List<AttachementEntity> attachementFiles) {
		this.attachementFiles = attachementFiles;
	}
	
	

}