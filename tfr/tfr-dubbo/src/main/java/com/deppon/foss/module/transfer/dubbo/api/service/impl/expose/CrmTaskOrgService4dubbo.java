package com.deppon.foss.module.transfer.dubbo.api.service.impl.expose;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.dubbo.crm.api.define.Org2CrmEntity;
import com.deppon.foss.dubbo.crm.api.define.TaskDeptRequestEntity;
import com.deppon.foss.dubbo.crm.api.define.exception.TfrBusinessException;
import com.deppon.foss.dubbo.crm.api.service.ICrmTaskOrgService4dubbo;
import com.deppon.foss.module.transfer.dubbo.api.service.IMatchTaskOrgService4dubbo;

public class CrmTaskOrgService4dubbo implements ICrmTaskOrgService4dubbo {
	private static final Logger LOG = LoggerFactory.getLogger(CrmTaskOrgService4dubbo.class);

	private IMatchTaskOrgService4dubbo matchTaskOrgService4dubbo;

	@Override
	public List<Org2CrmEntity> matchTaskOrg(TaskDeptRequestEntity taskDeptRequestBean) {
		String waybillNo = taskDeptRequestBean.getWaybillNumber();
		String callMan = taskDeptRequestBean.getCallMan();
		String callType = taskDeptRequestBean.getCallType();
		// 调用类型
		String methodType = taskDeptRequestBean.getMethodType();

		/**
		 * 调用类型 1、methodType == 2 匹配工单短信部门 2、methodType 默认 匹配任务部门
		 */
		if ("2".equals(methodType)) {
			LOG.info("匹配工单短信开始。。。。");
		} else {
			LOG.info("匹配任务部门开始。。。。");
		}

		if (StringUtils.isEmpty(waybillNo)) {
			LOG.error("CRM传进来的运单号为空！");
			throw new TfrBusinessException("CRM传进来的运单号为空！");
		}

		if (StringUtils.isEmpty(callMan)) {
			LOG.error("CRM传进来的来电人为空！");
			throw new TfrBusinessException("CRM传进来的来电人为空！");
		}

		if (StringUtils.isEmpty(callType)) {
			LOG.error("CRM传进来的来电人类型为空！");
			throw new TfrBusinessException("CRM传进来的来电人类型为空！");
		}

		if (StringUtils.isEmpty(methodType)) {
			LOG.error("CRM传进来的调用类型为空！");
			throw new TfrBusinessException("CRM传进来的调用类型为空！");
		}
		LOG.info("TaskDeptRequestEntity = " + waybillNo + ";" + callMan + ";" + callType + ";" + methodType);

		/**
		 * 调用类型 1、methodType == 2 匹配工单短信部门 2、methodType 默认 匹配任务部门
		 */
		if ("2".equals(methodType)) {
			try {
				List<Org2CrmEntity> orgDtoList = new ArrayList<Org2CrmEntity>();
				orgDtoList = getMatchTaskOrgService4dubbo().matchTaskOrg(waybillNo, callType);
				LOG.info("匹配工单短信部门结束。。。。" + orgDtoList.size());
				return orgDtoList;
			} catch (Exception e) {
				LOG.error("匹配工单短信部门异常！" + e.getMessage());
				throw new TfrBusinessException("匹配工单短信部门异常！", e);
			}

		} else {
			try {
				List<Org2CrmEntity> orgDtoList = new ArrayList<Org2CrmEntity>();
				orgDtoList = getMatchTaskOrgService4dubbo().matchTaskOrg(waybillNo, callMan, callType);
				LOG.info("匹配任务部门结束。。。。" + orgDtoList.size());
				return orgDtoList;
			} catch (Exception e) {
				LOG.error("匹配任务部门异常！" + e.getMessage());
				throw new TfrBusinessException("匹配任务部门异常！", e);
			}
		}
	}

	public IMatchTaskOrgService4dubbo getMatchTaskOrgService4dubbo() {
		return matchTaskOrgService4dubbo;
	}

	@Autowired
	public void setMatchTaskOrgService4dubbo(IMatchTaskOrgService4dubbo matchTaskOrgService4dubbo) {
		this.matchTaskOrgService4dubbo = matchTaskOrgService4dubbo;
	}

}
