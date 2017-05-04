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
 *FILE NAME    :ParticipantService.java
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.deppon.bpms.module.participant.server.service.IWorkParticipantService;
import com.deppon.bpms.module.shared.domain.BpmsParticipant;
import com.deppon.bpmsapi.module.client.domain.ParticipantRule;
import com.deppon.bpmsapi.module.client.util.BPMSConstant;
import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.server.context.WebApplicationContextHolder;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IWorkFlowService;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.WorkFlowEntity;

/**
 * 参与者规则服务
 * 
 * @author 136892
 * 
 */
public class ParticipantService {
	
	/**
	 * 获取事业部编码，传给工作流平台
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-9-12 下午7:51:07
	 */
	public BpmsParticipant[] getOverpayAreaManager(ParticipantRule rule,
			Map<?, ?> map) {
		IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService = (IOrgAdministrativeInfoComplexService) WebApplicationContextHolder
				.getWebApplicationContext().getBean(
						"orgAdministrativeInfoComplexService");
		String bizCode = String.valueOf(map
				.get(BPMSConstant.BIZCODE));
		IWorkFlowService workFlowService = (IWorkFlowService) WebApplicationContextHolder
				.getWebApplicationContext().getBean("workFlowService");
		WorkFlowEntity entity = new WorkFlowEntity();
		entity.setBizCode(bizCode);
		entity = workFlowService.workFlowQuery(entity);
		String unifiedCode=entity.getRespectiveRegional();

		String orgCode = workFlowService.queryOrgCodeByUnifiedCode(unifiedCode);
		
		// 查询大区类型
		List<String> bizTypes = new ArrayList<String>();
		bizTypes.add(BizTypeConstants.ORG_BIG_REGION);
		// 当前人部门找到当前的上级事业部 然后找事业部的标杆编码
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoComplexService
				.queryOrgAdministrativeInfoByCode(orgCode, bizTypes);
		// 查询大区类型找不到
		if (org == null) {
			bizTypes = new ArrayList<String>();
			// 查询部门类型列表
			bizTypes.add(BizTypeConstants.ORG_DIVISION);
			// 事业部
			// 当前人部门找到当前的上级事业部 然后找事业部的标杆编码
			org = orgAdministrativeInfoComplexService
					.queryOrgAdministrativeInfoByCode(orgCode, bizTypes);
			if (org == null) {
//				// 找不到事业部
//				return setUpResultDto(ResultDto.FAIL,
//						"申请弃货工作流失败,没有大区,没有事业部,请手动起草");
			}
		}
		BpmsParticipant[] bp = new BpmsParticipant[1];
		bp[0] = new BpmsParticipant();
		
		bp[0].setId(org.getUnifiedCode()+"#AREA_MANAGER_PERSON");
		// 设置事业部名称
		bp[0].setName("大区办公室负责人");
		bp[0].setTypeCode(IWorkParticipantService.PART_BY_MAPPING);
		return bp;
	}
	
}
