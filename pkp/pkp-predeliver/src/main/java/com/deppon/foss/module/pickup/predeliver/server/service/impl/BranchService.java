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
 *PROJECT NAME  : network-mgr
 *
 *package_name  : com.deppon.network.module.mgr.server.service.impl
 *
 *FILE NAME    :BranchService.java
 *
 *AUTHOR  :  网点规划
 *
 *TIME    :2013-9-16 
 *
 *HOME PAGE    :  http://www.deppon.com
 *
 *COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.predeliver.server.service.impl;

import java.util.Map;

import com.deppon.bpmsapi.module.client.util.BPMSConstant;
import com.deppon.foss.framework.server.context.WebApplicationContextHolder;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IWorkFlowService;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.WorkFlowEntity;

/**
 * 分支者规划
 * 
 * @author 136892
 * 
 */
@SuppressWarnings("rawtypes")
public class BranchService {

	/**
	 * 判断是否配合处理
	 * 
	 * @author 136892
	 * @date 2013-9-12 下午7:51:33
	 */
	public boolean isDeliver(Map map) {
		// String processInstId = String.valueOf(map
		// .get(BPMSConstant.PROCESS_INST_ID));
		String bizCode = String.valueOf(map
				.get(BPMSConstant.BIZCODE));
		IWorkFlowService workFlowService = (IWorkFlowService) WebApplicationContextHolder
				.getWebApplicationContext().getBean("workFlowService");
		WorkFlowEntity entity = new WorkFlowEntity();
		entity.setBizCode(bizCode);
		entity = workFlowService.workFlowQuery(entity);
		if (entity.getCustomerCooperateStatus().equals("Y")) {
			// 若客户不予配合Y-客户不予配合处理；
			return false;
		} else {
			// N-有弃货证明赔偿协议无标签货；
			return true;
		}

	}

}
