/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/action/QueryGoodsAction.java
 * 
 * FILE NAME        	: QueryGoodsAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.bpms.module.shared.domain.ApprovalInfo;
import com.deppon.bpmsapi.module.client.domain.WorkItemInfo;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IAbandonGoodsApplicationService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IWorkFlowService;
import com.deppon.foss.module.pickup.predeliver.api.shared.context.FossUserContextHelper;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.WorkFlowEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AbandonedGoodsDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WorkFlowApproveDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.WorkFlowException;
import com.deppon.foss.module.pickup.predeliver.api.shared.util.EncryptUtil;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.WorkFlowExamineVo;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.WorkFlowStatusVo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 工作流action
 * 
 * @author 136892
 * 
 */
public class WorkFlowAction extends AbstractAction implements
		ModelDriven<WorkFlowExamineVo> {

	/** 序列化. */
	private static final long serialVersionUID = 1118011519921068369L;
	private List<ApprovalInfo> approvalList = new ArrayList<ApprovalInfo>();
	private IWorkFlowService workFlowService;
	private WorkFlowApproveDto workFlowApproveDto;
	private IAbandonGoodsApplicationService abandonGoodsApplicationService;
	/**
	 * 封装的workFlowSearhDto
	 */
	private WorkFlowExamineVo workFlowExamineVo = new WorkFlowExamineVo();

	public WorkFlowApproveDto getWorkFlowApproveDto() {
		return workFlowApproveDto;
	}

	public void setWorkFlowApproveDto(WorkFlowApproveDto workFlowApproveDto) {
		this.workFlowApproveDto = workFlowApproveDto;
	}

	public void setAbandonGoodsApplicationService(
			IAbandonGoodsApplicationService abandonGoodsApplicationService) {
		this.abandonGoodsApplicationService = abandonGoodsApplicationService;
	}

	public List<ApprovalInfo> getApprovalList() {
		return approvalList;
	}

	public void setApprovalList(List<ApprovalInfo> approvalList) {
		this.approvalList = approvalList;
	}

	public void setWorkFlowService(IWorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	/**
	 * 工作流追踪
	 * 
	 * @return
	 */
	@JSON
	public String getProcess() {
		 decryptWorkFlowExamineVo();
		approvalList = this.workFlowService.workFlowProcDetail(String
				.valueOf(workFlowApproveDto.getFlowCode()));
		return returnSuccess();
	}

	/**
	 * 工作流详细信息
	 * 
	 * @return
	 */
	@JSON
	public String storeExceptioGoodsQuery() {
		decryptWorkFlowExamineVo();
		String processInstId = workFlowExamineVo.getProcInstId();
		WorkFlowEntity entity = new WorkFlowEntity();
		entity.setFlowCode(processInstId);
		entity = this.workFlowService.workFlowQuery(entity);
		String id = this.workFlowService
				.getIdByWaybillNo(entity.getWaybillNo(),entity.getSerialNumber());
		AbandonedGoodsDetailDto dto = new AbandonedGoodsDetailDto();
		dto = abandonGoodsApplicationService.getAbandonGoodsDetailById(id);

		WorkFlowStatusVo workFlowStatus = workFlowService
				.getWorkFlowStatus(Integer.parseInt(processInstId));

		// 审批状态
		ActionContext.getContext().put("workFlowStatus",
				com.alibaba.fastjson.JSON.toJSON(workFlowStatus));

		// 申请内容
		ActionContext.getContext().put("abandonedGoodsDetail",
				com.alibaba.fastjson.JSON.toJSON(dto));

		// 申请人信息
		ActionContext.getContext().put("proposerInfo",
				com.alibaba.fastjson.JSON.toJSON(entity));
		return returnSuccess();

	}

	/**
	 * 工作流审批页面
	 * 
	 * @return
	 */
	@JSON
	public String storeExceptioGoodsExamine() {
		decryptWorkFlowExamineVo();
		String processInstId = workFlowExamineVo.getProcInstId();
		
		WorkItemInfo itemIds = workFlowService.getWorkItemId(Long.valueOf(processInstId), null);
		if(null==itemIds){
			return "error";
		}

		
		
		WorkFlowEntity entity = new WorkFlowEntity();
		entity.setFlowCode(processInstId);
		entity = this.workFlowService.workFlowQuery(entity);
		String id = this.workFlowService
				.getIdByWaybillNo(entity.getWaybillNo(),entity.getSerialNumber());
		AbandonedGoodsDetailDto dto = new AbandonedGoodsDetailDto();
		dto = abandonGoodsApplicationService.getAbandonGoodsDetailById(id);

		WorkFlowStatusVo workFlowStatus = workFlowService
				.getWorkFlowStatus(Integer.parseInt(processInstId));

		// 审批状态
		ActionContext.getContext().put("workFlowStatus",
				com.alibaba.fastjson.JSON.toJSON(workFlowStatus));

		// 申请内容
		ActionContext.getContext().put("abandonedGoodsDetail",
				com.alibaba.fastjson.JSON.toJSON(dto));

		// 申请人信息
		ActionContext.getContext().put("proposerInfo",
				com.alibaba.fastjson.JSON.toJSON(entity));

		ActionContext.getContext().put("currentUser",
				FossUserContextHelper.getUserName());
		ActionContext.getContext().put("orgName",
				FossUserContextHelper.getOrgName());
		return returnSuccess();
	}

	/**
	 * 工作流审核
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author 136892
	 * @return
	 * @see
	 */
	@JSON
	public String workFlowApprove() {
		try {
			this.workFlowService.workFlowApprove(workFlowApproveDto);
		} catch (WorkFlowException e) {
			return returnError(e);
		}catch(BusinessException e){
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 工作流退回
	 * 
	 * @author 136892
	 * @return
	 * @see
	 */
	@JSON
	public String workFlowGoBack() {
		try {
			
			this.workFlowService.workFlowGoBack(workFlowApproveDto);
		} catch (WorkFlowException e) {
			return returnError(e);
		}
		return returnSuccess();
	}

	@Override
	public WorkFlowExamineVo getModel() {
		return workFlowExamineVo;
	}

	/**
	 * 解密WorkFlowExamineVo
	 * 
	 * @author lqs
	 * @date 2013-9-11 下午3:20:10
	 * @return
	 * @see
	 */
	private void decryptWorkFlowExamineVo() {
		if (!StringUtils.isBlank(workFlowExamineVo.getActivityDefId())) {
			workFlowExamineVo.setActivityDefId(EncryptUtil.decrypt(
					workFlowExamineVo.getActivityDefId(),
					EncryptUtil.DEFAULT_KEY));
		}

		if (!StringUtils.isBlank(workFlowExamineVo.getClaimNo())) {
			workFlowExamineVo.setClaimNo(EncryptUtil.decrypt(
					workFlowExamineVo.getClaimNo(), EncryptUtil.DEFAULT_KEY));
		}
		if (!StringUtils.isBlank(workFlowExamineVo.getWorkItemId())) {
			workFlowExamineVo
					.setWorkItemId(EncryptUtil.decrypt(
							workFlowExamineVo.getWorkItemId(),
							EncryptUtil.DEFAULT_KEY));
		}
		if (!StringUtils.isBlank(workFlowExamineVo.getUserId())) {
			workFlowExamineVo.setUserId(EncryptUtil.decrypt(
					workFlowExamineVo.getUserId(), EncryptUtil.DEFAULT_KEY));
		}
		workFlowExamineVo.setProcInstId(EncryptUtil.decrypt(
				workFlowExamineVo.getProcInstId(), EncryptUtil.DEFAULT_KEY));
		workFlowExamineVo
				.setProcessDefName(EncryptUtil.decrypt(
						workFlowExamineVo.getProcessDefName(),
						EncryptUtil.DEFAULT_KEY));
	}


}