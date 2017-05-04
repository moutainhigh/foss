/**
 * initial comments
 */
/**
 * 
 *Copyright 2013 
 *
 *Licensed under the DEPPON License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *  http://www.deppon.com/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 *Contributors:
 *038590-foss-wanghui - initial API and implementation
 *
 *PROJECT NAME  : network-mgr-api
 *
 *package_name  : com.deppon.network.module.mgr.api.server.service
 *
 *FILE NAME    :IWorkFlowService.java
 *
 *AUTHOR  :  网点规划
 *
 *TIME    :2013-9-16 
 *
 *HOME PAGE    :  http://www.deppon.com
 *
 *COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.predeliver.api.server.service;

import java.util.List;
import java.util.Map;

import com.deppon.bpms.module.shared.domain.ApprovalInfo;
import com.deppon.bpms.module.shared.domain.BpmsParticipant;
import com.deppon.bpmsapi.module.client.api.IDpBpmsClientFacade;
import com.deppon.bpmsapi.module.client.domain.WorkItemInfo;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.WorkFlowEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WorkFlowApproveDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.WorkFlowStatusVo;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.primeton.workflow.api.WFServiceException;

/**
 * 工作流service TODO(描述类的职责)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:Administrator,date:2013-7-24 下午1:41:50,content:TODO
 * </p>
 * 
 * @author HuansChr
 * @date 2013-7-24 下午1:41:50
 * @since
 * @version
 */
public interface IWorkFlowService extends IService {

	/**
	 * 查询当前工作项集合
	 */
	public List<ApprovalInfo> workFlowProcDetail(String flowCode);

	/*
	 * 工作流查询
	 */
	public WorkFlowEntity workFlowQuery(WorkFlowEntity entity);

	/**
	 * 创建客户端实例
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author 李龙根
	 * @date 2014-1-20 下午17:30:26
	 * @param userCode
	 * @param userName
	 * @return
	 * @see
	 */
	IDpBpmsClientFacade createDpBpmsClientFacadeImpl(String userCode,
			String userName);

	/**
	 * 根据运单号 查询对应id
	 * 
	 * @param waybillNo
	 * @return
	 */
	public String getIdByWaybillNo(String waybillNo,String serialNumber);

	/**
	 * 获取审批中工作项ID
	 * 
	 * @param processInstId
	 * @param activityDefId
	 * @return
	 * @see com.deppon.network.module.mgr.api.server.service.IWorkFlowService#getWorkItemId(long,
	 *      java.lang.String)
	 */
	public WorkItemInfo getWorkItemId(long processInstId, String activityDefId);

	/**
	 * 工作流审批
	 * 
	 * @param workFlowApproveDto
	 */
	public void workFlowApprove(WorkFlowApproveDto workFlowApproveDto);

	/**
	 * 工作流退回
	 * 
	 * @param workFlowApproveDto
	 */
	public void workFlowGoBack(WorkFlowApproveDto workFlowApproveDto);

	/**
	 * 当前审批状态
	 * 
	 * @param parseInt
	 * @return
	 */
	public WorkFlowStatusVo getWorkFlowStatus(long processInstId);

	/**
	 * 起草工作流
	 * 
	 * @param waybillNo
	 */
	public long createAndStartProcess(String waybillNo,String serialNumber);

	public String queryOrgCodeByUnifiedCode(String unifiedCode);
	
	/**
	 * 中转出库
	 * @author 231434-FOSS-bieyexiong
 	 * @date 2015-5-18 上午10:45:03
	 */
	public void outStock(String waybillNo, CurrentInfo currentInfo);
	
	/**
	 * 更新ActualFreight表中的结清状态为已结清
	 * @author 231434-FOSS-bieyexiong
 	 * @date 2015-5-18 下午14:02:21
	 */
	public void updateActualFreight(ActualFreightEntity actualFreightEntity);
	
	/**
	 * 需要调用业务完结接口
	 * @author 231434-FOSS-bieyexiong
 	 * @date 2015-5-18 下午14:10:29
	 */
	public void overWaybillTransaction(String waybillNo);
}